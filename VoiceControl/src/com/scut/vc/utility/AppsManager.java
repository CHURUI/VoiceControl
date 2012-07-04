package com.scut.vc.utility;

import java.util.ArrayList;
import java.util.List;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.graphics.drawable.Drawable;

public class AppsManager {

	private Activity mActivity;//������Activity
	private static int NULL_PACKAGE = -1;// û�д������
	private static int PACKAGE_FOUND = 0;// �ɹ�����
	private ArrayList<Package_Info> appsList;// ���س���װ�б�
	
	public AppsManager(Activity activity) {
		mActivity = activity;
	}
	

	/**
	 * ɨ��ϵͳ�Ѿ���װ��� �������Ϊfalse�򲻷���ϵͳ�� �������Ϊtrue�򷵻�ϵͳ�� ����ֵ���ǰ����˳����嵥���б�
	 * Ĭ��ȫ����ȡ������ȡ����ϵͳ��
	 * 
	 * @param getSysPackages
	 * @return
	 */
	public void ScanAppsList(boolean getSysPackages) {
		appsList = new ArrayList<Package_Info>();
		List<PackageInfo> packsList = mActivity.getPackageManager()
				.getInstalledPackages(0);
		for (int i = 0; i < packsList.size(); i++) {
			PackageInfo packInfo = packsList.get(i);
			if ((!getSysPackages) && (packInfo.versionName == null)) {
				continue;
			}
			Package_Info newInfo = new Package_Info();
			newInfo.mAppName = packInfo.applicationInfo.loadLabel(
					mActivity.getPackageManager()).toString();
			newInfo.mPackageName = packInfo.packageName;
			newInfo.mVersionName = packInfo.versionName;
			newInfo.mVersionCode = packInfo.versionCode;
			newInfo.mIcon = packInfo.applicationInfo.loadIcon(mActivity
					.getPackageManager());
			appsList.add(newInfo);
		}
	}

	/**
	 * ���س���װ�б�
	 * @return
	 */
	public ArrayList<Package_Info> getInstalledAppsList() {
		ScanAppsList(true);
		return appsList;
	}
	
	/**
	 * ���ذ�����Ϣ
	 * 
	 * @author fatboy
	 * 
	 */
	public class Package_Info {
		public String mAppName = "";
		public String mPackageName = "";
		public String mVersionName = "";
		public int mVersionCode = 0;
		public Drawable mIcon;
		
		public Package_Info(){
		}
		
		public Package_Info(String strAppName, String strPackageName){
			this.mAppName = strAppName;
			this.mPackageName = strPackageName;
		}
		
		public String GetAppName() {
			return this.mAppName;
		}

		public String GetPackageName() {
			return this.mPackageName;
		}

		public void SetAppName(String strAppName) {
			this.mAppName = strAppName;
		}

		public void SetPackageName(String strPackageName) {
			this.mPackageName = strPackageName;
		}
		
	}

	// ��Ӧ��,����Ϊָ���İ���
	public int Execute(String packname) {
		if (packname.equals("")) {
			return NULL_PACKAGE;// δ����Ҫ��ѯ�İ���
		}
		Intent intent = new Intent();
		intent = mActivity.getPackageManager().getLaunchIntentForPackage(
				packname);
		mActivity.startActivity(intent);
		return PACKAGE_FOUND;
	}

}
