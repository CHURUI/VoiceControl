package com.scut.vc.utility;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

public class WebSearch {
	private String strUrl = "";
	private String strWwwEngine = "http://www.baidu.com/s?wd=";// ��������������
	private String strWapEngine = "http://m.baidu.com/s?word=";// �ƶ������������
	private Activity mActivity;

	public WebSearch(Activity tempActivity) {
		mActivity = tempActivity;
	}

	// ������ҳ����,����Ϊָ������������Ϲؼ��ֵ�URL
	public int Execute(String keyword) {
		Intent intent = new Intent();
		intent.setAction(Intent.ACTION_VIEW);
		strUrl = strWapEngine + keyword;
		final Uri uri = Uri.parse(strUrl);
		intent.setData(uri);
		mActivity.startActivity(intent);
		return 0;
	}
}
