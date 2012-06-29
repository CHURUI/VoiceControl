package com.scut.vc.utility;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;

public class WebSearch {

	public static String[] webKey = { "����", "163", "�Ѻ�", "souhu", "����", "sina",
			"��Ѷ", "�ٶ�", "baidu", "�ȸ�", "google", "�Ա�", "taobao", "�»���", "������",
			"������" };

	private String strUrl = "";

	private String strBaiduEngine1 = "http://m.baidu.com/s?word=";// �ٶ��ƶ������������
	private String strBaiduEngine2 = "http://www.baidu.com/s?wd=";// �ٶ���������������

	private String strGoogleEngine1 = "http://www.google.cn/search?q=";// �ȸ����������

	private Activity mActivity;

	public WebSearch(Activity tempActivity) {
		mActivity = tempActivity;
	}

	// ������ҳ����,����Ϊָ������������Ϲؼ��ֵ�URL
	public int Execute(String keyword) {

		SharedPreferences sharedata = mActivity.getSharedPreferences(
				"searchEngine", Context.MODE_WORLD_READABLE);
		String searchEngine = sharedata.getString("searchEngine", "1");// ���������ȷ��ȡ��������ѡ������ݣ����Ե�һ��Ϊֵ
		System.out.println("execute_SearchEngine  =  " + searchEngine);

		if (searchEngine.equals("1")) {// ѡ��Ϊ�ٶ�����������������ͬʱΪĬ�����
			strUrl = strBaiduEngine1;
		} else if (searchEngine.equals("2")) {// ѡ��Ϊ�ȸ�������������
			strUrl = strGoogleEngine1;
		} else {// �������Ļ���ѡ��һ��Ĭ�ϵ���������ѡ��
			strUrl = strBaiduEngine1;
		}

		Intent intent = new Intent();
		intent.setAction(Intent.ACTION_VIEW);
		strUrl += keyword;

		if (keyword.contains("����") || keyword.contains("163")) {
			strUrl = "http://3g.163.com";
		} else if (keyword.contains("�Ѻ�") || keyword.contains("shouhu")) {
			strUrl = "http://wap.sohu.com";
		} else if (keyword.contains("����") || keyword.contains("sina")) {
			strUrl = "http://3g.sina.cn";
		} else if (keyword.contains("��Ѷ")) {
			strUrl = "http://3g.qq.com";
		} else if (keyword.contains("�ٶ�") || keyword.contains("baidu")) {
			strUrl = "http://m.baidu.com";
		} else if (keyword.contains("�ȸ�") || keyword.contains("google")) {
			strUrl = "http://www.google.cn";
		} else if (keyword.contains("�Ա�") || keyword.contains("taobao")) {
			strUrl = "http://m.taobao.com";
		} else if (keyword.contains("�»���")) {
			strUrl = "http://www.xinhuanet.com";
		} else if (keyword.contains("������")) {
			strUrl = "http://wap.people.com.cn";
		} else if (keyword.contains("������")) {
			strUrl = "http://wap.huanqiu.com";
		}

		final Uri uri = Uri.parse(strUrl);
		intent.setData(uri);
		mActivity.startActivity(intent);
		return 0;
	}
}
