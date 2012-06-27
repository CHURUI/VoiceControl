package com.scut.vc.ui;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.scut.vc.alarm.AlarmService;
import com.scut.vc.alarm.DatabaseHelper;
import com.scut.vc.alarm.MyAlarmAdapter;
import com.scut.vc.alarm.Str2DateTime;
import com.scut.vc.alarm.Str2DateTimeUtil;
import com.scut.vc.ui.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;

/**
 * ��Activity�Ƕ�������������õ�
 * @author Administrator
 *
 */

public class AlarmActivity extends Activity{

	
	ImageButton  voiceBtn;               
	DatabaseHelper mydb;                              //����һ�����ݿ�ʵ��
	private ListView alarmListView;
	private Cursor cursor;
	private List<String> ids;                        //�����ݿ������������¼���id�洢��һ��������
	private List<String> tempTimes;                   //�ݴ����ݿ��е�ʱ����Ϣ
	private List<String> tempDate;
	private List<String> states;
	private String id ="";                             //���������id
	public long times=0;
	AlertDialog builder = null;    
	Calendar c=Calendar.getInstance();

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.clock);
		mydb = new DatabaseHelper(AlarmActivity.this);
		refresh();	    
		NewAlarm();
		//StopAlarmService();		    
	}

	/**
	 * �½�һ�������¼�
	 */
	public void NewAlarm()
	{
		voiceBtn=(ImageButton)findViewById(R.id.clock_voice);
		voiceBtn.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View v)
			{
				final Intent isAlarmService = new Intent(AlarmActivity.this,AlarmService.class);
				String test = "������㿪��";
				Str2DateTime s2dt = new Str2DateTime(test);
				String test1 = s2dt.Analysis();
				Str2DateTimeUtil s2dtu = new Str2DateTimeUtil(test1);
				times = s2dtu.str2LongTimes();
				String time2str = s2dtu.getTime2Str();
				String date2str = s2dtu.getDate2Str();
				if(c.getTimeInMillis() > times){
					new AlertDialog.Builder(AlarmActivity.this)
					.setTitle("ע�⣡")
					.setMessage("ʱ�����������ѹ���")
					.setNegativeButton("ȷ��", new DialogInterface.OnClickListener() {

						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							
						}						
					}).show();
				}else{
					mydb = new DatabaseHelper(AlarmActivity.this);
					ContentValues cv = new ContentValues();
					cv.put(DatabaseHelper.ALARM_STATE, test);
					cv.put(DatabaseHelper.CLOCK_TIME, time2str);
					cv.put(DatabaseHelper.CLOCK_DATE, date2str);
					cv.put(DatabaseHelper.TIMES, times);
					mydb.insertTime(cv);
					cursor = mydb.selectAlarmTime();
					if(cursor.getCount()!=0){
						cursor.moveToFirst();
						while(cursor.getPosition() != cursor.getCount()){
							id = cursor.getString(cursor.getColumnIndex(DatabaseHelper.ALARM_ID));
							cursor.moveToNext();

						}		   
					}

					Log.e("Work", "id  " + id );
					//ͨ��intent���趨��ʱ�����alarm��id���趨��ʱ�䷢�͸�Service
					isAlarmService.putExtra("ID", id);
					isAlarmService.putExtra("Time", times);

					isAlarmService.setAction("SETTING");
					startService(isAlarmService);

					//���¶�alarmListView�������������䲢ˢ��
					refresh();

				}
				//ͨ��intent����id��ʱ�����Service�Ϳ�����
				Log.d("Work", "Now Time  " + c.getTimeInMillis());
				Log.d("Work", "Now Time  2  " + c.getTime());
				Log.v("Work", "Long Time " + times + " " + time2str + " "+date2str);


			}
		});
	}


	/**
	 * ����listview�������ݿ��е�������䵽listview��
	 */
	public void refresh(){
		cursor = mydb.selectAlarmTime();
		ids = new ArrayList<String>();
		tempTimes = new ArrayList<String>();
		states = new ArrayList<String>();
		tempDate = new ArrayList<String>();
		int count = cursor.getCount();
		//ֻ�����ݿ���������ʱ��������
		if(count>0){
			for(int i = 0; i< count; i ++){
				cursor.moveToPosition(i);
				ids.add(cursor.getString(0));
				states.add(cursor.getString(1));
				tempTimes.add(cursor.getString(2));
				tempDate.add(cursor.getString(3));

			}			
		}else{
		}	
		cursor.close();
		alarmListView = (ListView)findViewById(R.id.alarmList);
		alarmListView.setAdapter(new MyAlarmAdapter(AlarmActivity.this, states,tempTimes,tempDate,ids));
	}

	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {

		if(keyCode == KeyEvent.KEYCODE_BACK){
			builder = new AlertDialog.Builder(AlarmActivity.this)
			.setIcon(R.drawable.clock)
			.setTitle("��ܰ��ʾ��")
			.setMessage("���Ƿ�Ҫ���ص������棿")
			.setPositiveButton("ȷ��",
					new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog,
						int whichButton) {
					Intent i = new Intent(AlarmActivity.this,MainActivity.class);
					startActivity(i);
					AlarmActivity.this.finish();
					
				}
			})
			.setNegativeButton("ȡ��",
					new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog,
						int whichButton) {
					builder.dismiss();
				}
			}).show();
		}
		return true;
	}

}


