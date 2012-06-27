package com.scut.vc.alarm;

import java.util.Calendar;
/**
 * ��������ַ���ת��Ϊ�����ʱ���ʽ
 * @author Administrator
 *
 */

public class Str2DateTime {
	private String alarmStr;                 //������ַ���
	private Calendar cal;                     
	private int year;
	private int month;
	private static int day;
	private static int hour;
	private static int minute;
	
	public Str2DateTime(String alarmStr){
		this.alarmStr = alarmStr;
		cal = Calendar.getInstance();
		year = cal.get(Calendar.YEAR);
		month = cal.get(Calendar.MONTH)+1; 
		day = cal.get(Calendar.DAY_OF_MONTH);
		hour = cal.get(Calendar.HOUR_OF_DAY);
		minute = cal.get(Calendar.MINUTE);
		
	}


	/**
	 * ��������ַ���ͨ���������γ�0000/00/00 00:00��ʽ���ַ���
	 * @return analysis�����ص���ת���õĸ�ʽ
	 */
	public String Analysis(){
		
		String analysis = "";
		if(alarmStr.length()!=0){
			//���ں��С����족�ؼ��ֵĽ���
			if(alarmStr.indexOf("����")!=-1){
				String time[] = new String[2];
				String date = date2Tomorrow();		
				time = formatTime(alarmStr);
				if(alarmStr.contains("����")||alarmStr.contains("����")){
					analysis = date + " " + (Integer.parseInt(time[0])+ 12) + ":" + time[1];
				}else analysis = date + " " + time[0] + ":" + time[1];
				
				//���ں��к��족�ؼ��ֵĽ���
			}else if(alarmStr.indexOf("����")!=-1){
				String time[] = new String[2];
				String date = dateAfterTomorrow();
				time = formatTime(alarmStr);
				if(alarmStr.contains("����")||alarmStr.contains("����")){
					analysis = date + " " + (Integer.parseInt(time[0])+ 12) + ":" + time[1];
				}else analysis = date + " " + time[0] + ":" + time[1];

			}else{

				//Toast.makeText(context, "�����������������", Toast.LENGTH_SHORT);
				String date = formatDate(year,month,day);
				String time[] = new String[2];
				time = formatTime(alarmStr);
				if(alarmStr.contains("����")||alarmStr.contains("����")){
					analysis = date + " " + (Integer.parseInt(time[0])+ 12) + ":" + time[1];
				}else analysis = date + " " + time[0] + ":" + time[1];
			}
		}else{
			//������Ϊ��ʱ�������ӳ�һСʱ���ַ���
			String date = formatDate(year,month,day);
			analysis = date + " " + (hour +1) + ":" +minute;
		}
		 
		return analysis;

	}


	/**
	 * �Դ�����ַ��������ֽ��н������γ����Ӧ�����֣�ͬʱ�����롱��Ϊ30����
	 * @param alarmStr  ������ַ���
	 * @return String num[]�� ���ش���Сʱ�ͷ��ӵ�����
	 */
	public static String[] formatTime(String alarmStr){
		String num[] = new String[2];
		num [1] = num[0] = "";
		boolean aNum = false;
		int k = 0;
		for(int i=0; i<alarmStr.length(); i++){
			if(alarmStr.charAt(i)=='һ'){
				num[k] += "1";
				aNum = true;
			}else{
				if(alarmStr.charAt(i)=='��'||alarmStr.charAt(i)=='��'){
					num[k] += "2";
					aNum = true;
				}else{
					if(alarmStr.charAt(i)=='��'){
						num[k] += "3";
						aNum = true;
					}else{
						if(alarmStr.charAt(i)=='��'){
							num[k] += "3";
							aNum = true;
						}else{
							if(alarmStr.charAt(i)=='��'){
								num[k] += "4";
								aNum = true;
							}else{
								if(alarmStr.charAt(i)=='��'){
									num[k] += "5";
									aNum = true;
								}else{
									if(alarmStr.charAt(i)=='��'){
										num[k] += "6";
										aNum = true;
									}else{
										if(alarmStr.charAt(i)=='��'){
											num[k] += "7";
											aNum = true;
										}else{
											if(alarmStr.charAt(i)=='��'){
												num[k] += "8";
												aNum = true;
											}else{
												if(alarmStr.charAt(i)=='��'){
													num[k] += "9";
													aNum = true;
												}else{
													if(alarmStr.charAt(i)=='ʮ'){
														if(num[k].length()>0) {
															char t = alarmStr.charAt(i+1);
															if(t!='һ'&&t!='��'&&t!='��'&&
																	t!='��'&&t!='��'&&t!='��'&&
																	t!='��'&&t!='��'&&t!='��'){
																num[k] += "0";
															}
														}
														else {
															num[k] = "1";
															char t = alarmStr.charAt(i+1);
															if(t!='һ'&&t!='��'&&t!='��'&&
																	t!='��'&&t!='��'&&t!='��'&&
																	t!='��'&&t!='��'&&t!='��'){
																num[k] = "10";
															}
														}
														aNum = true;
													}else{
														if(alarmStr.charAt(i)=='��'){
															num[1] = "30";
															break;
														}
														if(aNum&&k==0){
															k++;
															aNum = false;
														}
														if(aNum&&k==1)break;
													}
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
		if(alarmStr.contains("��")&&alarmStr.contains("��")){			
			num[1] = Integer.parseInt(num[0])+ minute +"";
			num[0] = hour +"" ;
		}
		if(num[1].equals("")){
			num[1] ="00";
		}
		return num;
	}

/**
 * ���롰���족�����ڽ��н������Ը����·ݵ�����
 * @return date
 */
    private String date2Tomorrow(){
    	String date = "";
		if(month == 2){
			if(day < 28){
				 date = formatDate(year,month,day +1);
			}else{
				date = formatDate(year,month + 1,1);
			}
		}else if(month == 4|| month == 6|| month == 9 || month == 11){
			if(day < 30){
				date = formatDate(year, month, day +1);
			}else{
				date = formatDate(year,month + 1,1);
			}
			
		}else{
			if(day < 31){
				date = formatDate(year, month, day +1 );
			}else{
				if(month == 12){
					date = formatDate(year+1,1,1);
				}
				date = formatDate(year,month + 1,1);
			}
		}
		return date;
    }
    
    /**
     * ���롰���족�����ڽ��н������Ը����·ݵ�����
     * @return date
     */
    private String dateAfterTomorrow(){
    	String date = "";
    	if(month == 2){
			if(day == 27){
				date = formatDate(year,month + 1,1);
			}else if(day == 28){
				date = formatDate(year,month + 1,2);
			}else{
				
				date = formatDate(year,month,day +2);
			}
		}else if(month == 4|| month == 6|| month == 9 || month == 11){
			if(day == 29){
				date = formatDate(year,month + 1,1);
				
			}else if(day == 30){
				date = formatDate(year,month + 1,2);
			}
			else{
				date = formatDate(year, month, day + 2);
			}
			
		}else{
			if(day == 30){
				if(month == 12){
					date = formatDate(year+1,1,1);
				}
				date = formatDate(year,month + 1,1);
			}else if(day == 31){
				if(month == 12){
					date = formatDate(year+1,1,2);
				}
				date = formatDate(year,month + 1,2);							
			}else{
				date = formatDate(year, month, day);
			}
		}
    	return date;
    }
    
	/**
	 * �Ծ�����ݵĸ�ʽ����
	 * @param y
	 * @param m
	 * @param d
	 * @return
	 */
	private String formatDate(int y,int m, int d){
		StringBuffer sb = new StringBuffer();
		sb.append(y);
		sb.append("/");
		sb.append( m<10 ? "0" + m : ""+ m );
		sb.append("/");
		sb.append( d< 10 ? "0"+ d: "" + d);
		return sb.toString();

	}
}
