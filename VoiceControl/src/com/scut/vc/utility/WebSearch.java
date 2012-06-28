package com.scut.vc.utility;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;

public class WebSearch {
	private String strUrl = "";
	
	private String strBaiduEngine1 = "http://m.baidu.com/s?word=";// �ٶ��ƶ������������
	private String strBaiduEngine2 = "http://www.baidu.com/s?wd=";// �ٶ���������������

	private String strGoogleEngine1 = "http://www.google.com.hk/search?q=";// �ȸ���۰����������
	private String strGoogleEngine2 = "http://www.google.com/search?q=";// �ȸ���ʰ����������

	private String strSougouEngine1 = "http://wap.sogou.com/web/searchList.jsp?keyword=";// �ȸ���۰����������

	private Activity mActivity;

	public WebSearch(Activity tempActivity) {
		mActivity = tempActivity;
	}

	// ������ҳ����,����Ϊָ������������Ϲؼ��ֵ�URL
	public int Execute(String keyword) {
		
		// ��ȡ��������ѡ�������
		// ��һ�������汾�ϵļ���
		// SharedPreferences sharedata = getSharedPreferences(
		// "voiceEngine", MODE_WORLD_READABLE | MODE_MULTI_PROCESS);
		SharedPreferences sharedata = mActivity.getSharedPreferences(
				"searchEngine", Context.MODE_WORLD_READABLE);
		String searchEngine = sharedata.getString("searchEngine", "1");// ���������ȷ��ȡ��������ѡ������ݣ����Ե�һ��Ϊֵ
		System.out.println("execute_SearchEngine = " + searchEngine);

		if(searchEngine.equals("1")){//ѡ��Ϊ�ٶ�����������������ͬʱΪĬ�����
			strUrl = strBaiduEngine1;
		}
		else if(searchEngine.equals("2")){//ѡ��Ϊ�ȸ�������������
			strUrl = strGoogleEngine1;
		}
		else if(searchEngine.equals("3")){//ѡ��Ϊ�ѹ�������������
			strUrl = strSougouEngine1;
		}
		else{//�������Ļ���ѡ��һ��Ĭ�ϵ���������ѡ��
			strUrl = strBaiduEngine1;
		}
		
		Intent intent = new Intent();
		intent.setAction(Intent.ACTION_VIEW);
		strUrl += keyword;
		final Uri uri = Uri.parse(strUrl);
		intent.setData(uri);
		mActivity.startActivity(intent);
		return 0;
	}
}
