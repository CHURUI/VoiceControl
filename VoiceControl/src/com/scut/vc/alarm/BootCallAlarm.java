package com.scut.vc.alarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class BootCallAlarm extends BroadcastReceiver{
	/**
	 * ��������ʱ����ø�Receiver������AlarmService��
	 */

	private static final String ACTION = "android.intent.action.BOOT_COMPLETED";
	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		 if (intent.getAction().equals(ACTION)){

             Intent i= new Intent(Intent.ACTION_RUN);
             i.setClass(context, AlarmService.class);
             context.startService(i);
     }
	}

}
