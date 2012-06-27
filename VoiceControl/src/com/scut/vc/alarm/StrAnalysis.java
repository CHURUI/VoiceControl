package com.scut.vc.alarm;

import java.util.Calendar;

import android.content.Context;
import android.widget.Toast;

public class StrAnalysis {

	private String str;
	private Calendar cal;
	private int year;
	private int month;
	private int day;
	private static int hour;
	private static int minute;
	//private Context context;
	public StrAnalysis(String str){
		this.str = str;
		//this.context = context;
		cal = Calendar.getInstance();
		year = cal.get(Calendar.YEAR);
		month = cal.get(Calendar.MONTH);
		day = cal.get(Calendar.DAY_OF_MONTH);
		hour = cal.get(Calendar.HOUR_OF_DAY);
		minute = cal.get(Calendar.MINUTE);
	}

	public String Analysis(){
		
		String analysis = "";

		if(str.indexOf("����")!=-1||(str.contains("��")&&str.contains("��"))){
			String date = formatDate(year,month,day);
			String time[] = new String[2];
			time = formatTime(str);
			analysis = date + " " + time[0] + ":" + time[1];

		}else if(str.indexOf("����")!=-1){
			String time[] = new String[2];
			String date = date2Tomorrow();		
			time = formatTime(str);
			analysis = date + " " + time[0] + ":" + time[1];

		}else if(str.indexOf("����")!=-1){
			String time[] = new String[2];
			String date = dateAfterTomorrow();
			time = formatTime(str);
			analysis = date + " " + time[0] + ":" + time[1];
		}else{

			//Toast.makeText(context, "�����������������", Toast.LENGTH_SHORT);
		}
		return analysis;

	}


	public static String[] formatTime(String str){
		String num[] = new String[2];
		num [1] = num[0] = "";
		boolean aNum = false;
		int k = 0;
		for(int i=0; i<str.length(); i++){
			if(str.charAt(i)=='һ'){
				num[k] += "1";
				aNum = true;
			}else{
				if(str.charAt(i)=='��'||str.charAt(i)=='��'){
					num[k] += "2";
					aNum = true;
				}else{
					if(str.charAt(i)=='��'){
						num[k] += "3";
						aNum = true;
					}else{
						if(str.charAt(i)=='��'){
							num[k] += "3";
							aNum = true;
						}else{
							if(str.charAt(i)=='��'){
								num[k] += "4";
								aNum = true;
							}else{
								if(str.charAt(i)=='��'){
									num[k] += "5";
									aNum = true;
								}else{
									if(str.charAt(i)=='��'){
										num[k] += "6";
										aNum = true;
									}else{
										if(str.charAt(i)=='��'){
											num[k] += "7";
											aNum = true;
										}else{
											if(str.charAt(i)=='��'){
												num[k] += "8";
												aNum = true;
											}else{
												if(str.charAt(i)=='��'){
													num[k] += "9";
													aNum = true;
												}else{
													if(str.charAt(i)=='ʮ'){
														if(num[k].length()>0) {
															char t = str.charAt(i+1);
															if(t!='һ'&&t!='��'&&t!='��'&&
																	t!='��'&&t!='��'&&t!='��'&&
																	t!='��'&&t!='��'&&t!='��'){
																num[k] += "0";
															}
														}
														else {
															num[k] = "1";
															char t = str.charAt(i+1);
															if(t!='һ'&&t!='��'&&t!='��'&&
																	t!='��'&&t!='��'&&t!='��'&&
																	t!='��'&&t!='��'&&t!='��'){
																num[k] = "10";
															}
														}
														aNum = true;
													}else{
														if(str.charAt(i)=='��'){
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
		if(str.contains("��")&&str.contains("��")){
			
			int tempMinute = Integer.parseInt(num[0])+ minute;			
			if(tempMinute >= 60){
				num[1] = (tempMinute - 60) +"";
				num[0] = (hour + 1)+"";
			}else{
				num[1] = tempMinute +"";
				num[0] = hour +"" ;
			}
		}
		return num;
	}


    private String date2Tomorrow(){
    	String date = "";
		if(month == 2){
			if(day < 28){
				 date = formatDate(year,month,day);
			}else{
				date = formatDate(year,month + 1,1);
			}
		}else if(month == 4|| month == 6|| month == 9 || month == 11){
			if(day < 30){
				date = formatDate(year, month, day);
			}else{
				date = formatDate(year,month + 1,1);
			}
			
		}else{
			if(day < 31){
				date = formatDate(year, month, day);
			}else{
				if(month == 12){
					date = formatDate(year+1,1,1);
				}
				date = formatDate(year,month + 1,1);
			}
		}
		return date;
    }
    
    
    private String dateAfterTomorrow(){
    	String date = "";
    	if(month == 2){
			if(day == 27){
				date = formatDate(year,month + 1,1);
			}else if(day == 28){
				date = formatDate(year,month + 1,2);
			}else{
				
				date = formatDate(year,month,day);
			}
		}else if(month == 4|| month == 6|| month == 9 || month == 11){
			if(day == 29){
				date = formatDate(year,month + 1,1);
				
			}else if(day == 30){
				date = formatDate(year,month + 1,2);
			}
			else{
				date = formatDate(year, month, day);
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
