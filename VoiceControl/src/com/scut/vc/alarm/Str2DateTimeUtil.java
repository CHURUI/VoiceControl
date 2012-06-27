package com.scut.vc.alarm;

import java.util.Calendar;
/**
 * ��ת���õ�ʱ����м��㣬ȡ����Ӧ��ʱ�䣬�Ա���ж����ӵ��趨
 * @author Administrator
 *
 */

public class Str2DateTimeUtil {

	private String dateStr;
	private int year;
	private int month;
	private int day;
	private int hour;
	private int minute;
	private long time2Alarm;
	private String date2Str;
	private String time2Str;
	
	public Str2DateTimeUtil(String dateStr){
		this.dateStr = dateStr;	
	}
	/**
	 * ����Ŀ��ʱ��ľ������ֵ
	 * @return time2Alarm  �����ͣ���ʱ�䵽���ʱ��ֵʱ�����ἤ������
	 */
	public Long str2LongTimes(){		
		String[] strs = dateStr.split("\\ ");
		for(int i =0; i< strs.length ; i++){
			System.out.println(strs[i]);
		}
		
		setDate2Str(strs[0]);
		setTime2Str(strs[1]);
		
		String[] dates = strs[0].split("\\/");
		for(int i =0; i< dates.length ; i++){
			System.out.println(dates[i]);
		}
		year = Integer.parseInt(dates[0]);
		//ӦΪCalendar�еط�Month�Ǵ�0��ʼ����ģ���Ҫ��Ӧ�ļ�1.
		month = Integer.parseInt(dates[1])-1;
		day = Integer.parseInt(dates[2]);
		String[] times = strs[1].split("\\:");
		for(int i =0; i< times.length ; i++){
			System.out.println(times[i]);
		}
		hour = Integer.parseInt(times[0]);
		minute = Integer.parseInt(times[1]);
		
		Calendar  c = Calendar.getInstance();
		System.out.println(c.getTimeInMillis());
		c.clear(); 
		c.set(Calendar.YEAR, year);
		c.set(Calendar.MONTH, month);
		c.set(Calendar.DAY_OF_MONTH, day);
		c.set(Calendar.HOUR_OF_DAY, hour);
		c.set(Calendar.MINUTE, minute);
		time2Alarm = c.getTimeInMillis();
		return time2Alarm;		
	}
	
	public void setDate2Str(String date2Str){
		this.date2Str = date2Str;
	}
	
	public String getDate2Str(){
		
		return date2Str;
	}
	
	
	public void setTime2Str(String time2Str){
		this.time2Str = time2Str;
	}
	public String getTime2Str(){
		return time2Str;
	}
	
}
