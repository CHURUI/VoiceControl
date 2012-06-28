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
		//��ȡ����ʶ������ѡ�������
		String voicetEngine = sp.getString("voiceEngine", "error~~"); // list1�ǿؼ���ID
		Editor sharedata_voice = getSharedPreferences("voiceEngine", 0).edit();
		sharedata_voice.putString("voiceEngine", voicetEngine);//voiceEngine�Ǹ��ⲿactivity������id
		sharedata_voice.commit();
		//��ȡ��������ѡ�������
		String searchEngine = sp.getString("searchEngine", "error~~"); // list1�ǿؼ���ID
		Editor sharedata_search = getSharedPreferences("searchEngine", 0).edit();
		sharedata_search.putString("searchEngine", searchEngine);//voiceEngine�Ǹ��ⲿactivity������id
		sharedata_search.commit();

		Toast.makeText(this, "�����ѱ���", Toast.LENGTH_SHORT).show();
	}
}
