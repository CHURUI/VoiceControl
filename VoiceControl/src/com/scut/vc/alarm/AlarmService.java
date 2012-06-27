package com.scut.vc.alarm;


import java.util.Calendar;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.database.Cursor;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class AlarmService extends Service{

	/**
	 * ��Service��Ϊ�˷���������ض����õ�
	 * @return null
	 */
	private static AlarmService as = null;
	private DatabaseHelper mydb;                //���ݿ�
	private String idOfAlarm ="";                     //�����
	private long alarmTime = 0;                    //�������趨��ʱ���뵱ǰϵͳʱ��Ĳ�ֵ
	private int counter = 0;                      //�����ڿ�������ʱ������onstart��������Ĳ���
	private String delId = "";

	/*
	 * ����һ����̬��������Serviceʵ�壬���������ط����Ի�ȡ.
	 * private static Service getService as = null; �����洢�Լ���ʵ��
	 * ��onCreate()��ʹ��sms = this�����洢ʵ��
	 * ��дһ����̬��getService()������ʵ��Ϳ�����
	 */
	public static Service getService()
	{
		return as;
	}

	public void onCreate()
	{
		super.onCreate();
		as = this;
		Log.v("Work", "----1----" + counter);
		if(counter ==0){
			reCountTime();	
			counter = 1;
			Log.v("Work", "----2----" + counter);
		}
	}

	/*
	 * reCountTime()��Ϊ�����¼������ӵ�ʱ�䣬���ʵ��ʱ�����
	 * Ԥ��ʱ�䣬�򲻻��з�Ӧ�������������趨����ʱ��
	 */
	public void reCountTime(){
		mydb = new DatabaseHelper(AlarmService.this);
		//alarmList = mydb.getAllAlarmTime();
		Cursor cursor = mydb.selectAlarmTime();
		Calendar cl = Calendar.getInstance();
		int count = cursor.getCount();
		if(count>0){
			for(int i = 0; i< count; i++){
				cursor.moveToPosition(i);
				int alarmId = Integer.parseInt(cursor.getString(0));
				//String states = cursor.getString(1);
				long times = cursor.getLong(4);
				AlarmManager am = (AlarmManager)getSystemService(Service.ALARM_SERVICE);
				if(times > cl.getTimeInMillis()){
					Intent it = new Intent(this,CallAlarm.class);
					it.putExtra("ID", alarmId);
					PendingIntent pit = PendingIntent.getBroadcast
					(this, alarmId, it, 0);
					am.set(AlarmManager.RTC_WAKEUP, 
							times, pit);
				}else{
					Toast.makeText(AlarmService.this, "�����ˣ�", Toast.LENGTH_SHORT);
				}
			}
		}	
	}

	/*
	 * (non-Javadoc)
	 * @see android.app.Service#onStart(android.content.Intent, int)
	 * ÿ������һ��alarmʱ���ͻ��ٴε��øú���
	 */
	public void onStart(Intent intent, int startId)
	{
		super.onStart(intent, startId);
		
		
//		
		if(intent.getAction().equals("DELID")){
			
			delId = intent.getStringExtra("DELID"); 
			Log.e("Work", "  ---delID---- " + delId);
			AlarmManager am = (AlarmManager)getSystemService(Service.ALARM_SERVICE);
			Intent it = new Intent(AlarmService.this,CallAlarm.class);
			//it.putExtra(DatabaseHelper.ALARM_ID, delId);
			PendingIntent pi = PendingIntent.getBroadcast
			(this, Integer.parseInt(delId), it, 0);
			am.cancel(pi);
		}else if(intent.getAction().equals("SETTING")){
			Log.e("Work", "Wrong!!!!");
			//��ȡAlarmManager
			alarmTime = intent.getLongExtra("Time",0);
			idOfAlarm = intent.getStringExtra("ID");
		   
		  //  
			//�����ȡ������idΪ��ʱ�����趨Ϊ0��
//			if(idOfAlarm.equals("")){
//				Log.v("Work", "ID  111");
//				idOfAlarm ="0";
//			}
			Log.e("Work", "ID "+ idOfAlarm +" Time "+  alarmTime);
			Log.v("Work", "----2----" + counter);
			if(counter > 1){
				AlarmManager am = (AlarmManager)getSystemService(Service.ALARM_SERVICE);		
				Intent it = new Intent(AlarmService.this,CallAlarm.class);
				PendingIntent pit = PendingIntent.getBroadcast
				(this, Integer.parseInt(idOfAlarm), it, 0);
				am.set(AlarmManager.RTC_WAKEUP, 
						alarmTime, pit);
				Toast.makeText(this, "AlarmSet Finish!", Toast.LENGTH_SHORT).show();		
			}
			counter++;
			Log.v("Work", "----3----" + counter);
		}
		 
		

	}
	/*
	 * (non-Javadoc)
	 * @see android.app.Service#onDestroy()
	 * Ŀǰ��û�κ�����
	 */
	public void onDestroy()
	{
		super.onDestroy();
		AlarmManager am = (AlarmManager)getSystemService
		(Service.ALARM_SERVICE);

	}

	@Override
	public IBinder onBind(Intent intent)
	{
		// TODO Auto-generated method stub
		return null;
	}	

}
