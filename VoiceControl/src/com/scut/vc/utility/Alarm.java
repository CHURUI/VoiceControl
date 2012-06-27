package com.scut.vc.utility;



import java.util.Calendar;

import com.scut.vc.alarm.AlarmService;
import com.scut.vc.alarm.DatabaseHelper;
import com.scut.vc.alarm.Str2DateTime;
import com.scut.vc.alarm.Str2DateTimeUtil;
import com.scut.vc.ui.AlarmActivity;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;


public class Alarm {

	private Context context;
	private String alarmStr;
	private long alarmTimeCounter = 0;
	private DatabaseHelper mydb ; 
	private Calendar c = Calendar.getInstance();
	private Cursor cursor;
	private String alarmID;
/**
 * 
 * @param context
 * @param alarmStr   ������ַ��������磺�������Ͼŵ㿪��
 */

	public Alarm(Context context, String alarmStr){
		this.context = context;
		this.alarmStr = alarmStr;
	}

	/**
	 * �ú����ǽ�������ַ������н�������ʹ֮�������ݿ��У�ͬʱ����Service��
	 * ��ת��Alarm����
	 */
	public void Execute(){
	
		//���ַ��������������
		Str2DateTime s2dt = new Str2DateTime(alarmStr);
		String dateTimeStr = s2dt.Analysis();
		Str2DateTimeUtil s2dtu = new Str2DateTimeUtil(dateTimeStr);
		alarmTimeCounter = s2dtu.str2LongTimes();
		final String time2Str = s2dtu.getTime2Str();
		final String date2Str = s2dtu.getDate2Str();
		if(c.getTimeInMillis() > alarmTimeCounter){
			new AlertDialog.Builder(context)
			.setTitle("ע�⣡")
			.setMessage("ʱ�����������ѹ���")
			.setNegativeButton("ȷ��", new DialogInterface.OnClickListener() {

				public void onClick(DialogInterface arg0, int arg1) {
					// TODO Auto-generated method stub

				}			
			}).show();
		}else{
			//�����ݴ���������
			new AlertDialog.Builder(context)
			.setTitle("�����ñ�������")
			.setMessage("ȷ�����룺��"+ alarmStr + "��������������?")
			.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {

				public void onClick(DialogInterface arg0, int arg1) {
					// TODO Auto-generated method stub
					mydb = new DatabaseHelper(context);
					ContentValues cv = new ContentValues();
					cv.put(DatabaseHelper.ALARM_STATE,alarmStr);
					cv.put(DatabaseHelper.CLOCK_TIME, time2Str);
					cv.put(DatabaseHelper.CLOCK_DATE, date2Str);
					cv.put(DatabaseHelper.TIMES,alarmTimeCounter);
					mydb.insertTime(cv);
					cursor = mydb.selectAlarmTime();
					if(cursor.getCount()!=0){
						cursor.moveToFirst();
						while(cursor.getPosition() != cursor.getCount()){
							alarmID = cursor.getString(cursor.getColumnIndex(DatabaseHelper.ALARM_ID));
							cursor.moveToNext();
						}
					}
					//ͨ��intent���趨��ʱ�����alarm��id���趨��ʱ�䷢�͸�Service,ͬʱ����AlarmActivity
					 Intent iService = new Intent(context,AlarmService.class);
					 Intent iActivity = new Intent(context,AlarmActivity.class);
					iService.putExtra("ID", alarmID);
					iService.putExtra("Time",alarmTimeCounter);
					iService.setAction("SETTING");
					context.startService(iService);
					context.startActivity(iActivity);
				}			
			}).setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {
				
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					
				}
			}).show();
			
									
		}

	}
}
