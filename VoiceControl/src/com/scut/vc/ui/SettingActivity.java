package com.scut.vc.ui;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.widget.Toast;

public class SettingActivity extends PreferenceActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		addPreferencesFromResource(R.xml.setting);

		/*// ��ȡ���ݣ����ڷ������
		final SharedPreferences sp = PreferenceManager
				.getDefaultSharedPreferences(this);
		System.out.println("0000000000000");
		String selectEngine = sp.getString("voiceEngine", "error~~");
		System.out.println("selectEngine = " + selectEngine);
		System.out.println("111111111111");*/

	}

	@Override
	protected void onPause() {
		super.onPause();
		
		// ��ȡ���ݣ����ύ����������activity�������ݵĶ�ȡ
		final SharedPreferences sp = PreferenceManager
				.getDefaultSharedPreferences(this);
		String selectEngine = sp.getString("voiceEngine", "error~~"); // list1�ǿؼ���ID
		Editor sharedata = getSharedPreferences("voiceEngine", 0).edit();
		sharedata.putString("voiceEngine", selectEngine);//voiceEngine�Ǹ��ⲿactivity������id
		sharedata.commit();

		Toast.makeText(this, "�����ѱ���", Toast.LENGTH_SHORT).show();
	}
}
