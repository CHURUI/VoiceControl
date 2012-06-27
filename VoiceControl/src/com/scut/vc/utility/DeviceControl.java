/**
 * ������Ҫ������permission * 
 */
// <uses-permission android:name="android.permission.CAMERA" />
//    <uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
//    <uses-permission android:name="android.permission.CHANGE_WIFI_STATE" />
//     <uses-permission android:name="android.permission.CHANGE_NETWORK_STATE"/>
//     <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>
//    <uses-permission android:name="android.permission.WAKE_LOCK" />
//    <uses-permission android:name="android.permission.BLUETOOTH_ADMIN" />
//    <uses-permission android:name="android.permission.BLUETOOTH" />
//    <uses-permission android:name="android.permission.WRITE_SETTINGS"/>

package com.scut.vc.utility;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import android.app.Activity;
import android.app.PendingIntent;
import android.app.PendingIntent.CanceledException;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.hardware.Camera;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.provider.Settings;

/**
 * ����������豸���Ƶ� Ϊ����Ӧandroid2.1 �����õĶ��Ǻ��ϵİ취 û���õ�SETTING��
 * 
 * @author fatboy
 * 
 */
public class DeviceControl {
	/**
	 * �����activity
	 */
	private Activity mActivity;

	private Camera mCamera;// �ֻ�Ͳ
	private Camera.Parameters parameter; // �ֵ�Ͳ����
	private WifiManager mWifiManager;// wifi������
	private BluetoothAdapter mBlueTooth;// ����������
	private ConnectivityManager mConnMan;// �ֻ����ӹ�����

	private Intent mGpsIntent;

	public DeviceControl(Activity activity) {
		mActivity = activity;

		initial();// ��ʼ��
	}

	private void initial() {
		/**
		 * �ֵ�Ͳ��ʼ��
		 */
		//mCamera = Camera.open(Camera.getNumberOfCameras() - 1);
		mCamera = Camera.open();
		parameter = mCamera.getParameters();

		/**
		 * GPS
		 */
		mGpsIntent = new Intent();
		mGpsIntent.setClassName("com.android.settings",
				"com.android.settings.widget.SettingsAppWidgetProvider");
		mGpsIntent.addCategory("android.intent.category.ALTERNATIVE");
		mGpsIntent.setData(Uri.parse("custom:3"));

		// try {
		// PendingIntent.getBroadcast(this, 0, mGpsIntent, 0).send();
		// } catch (CanceledException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }

		/**
		 * WIFI
		 * 
		 */
		mWifiManager = (WifiManager) mActivity
				.getSystemService(Context.WIFI_SERVICE);

		/**
		 * GPRS
		 */
		mConnMan = (ConnectivityManager) mActivity
				.getSystemService(Context.CONNECTIVITY_SERVICE);

		/**
		 * ����
		 */
		mBlueTooth = BluetoothAdapter.getDefaultAdapter();
	}

	/**
	 * ��������
	 * 
	 * @param enable
	 */
	private void EnableBlueTooth(boolean enable) {
		if (enable) {
			mBlueTooth.enable();
		} else {
			mBlueTooth.disable();
		}
	}

	/**
	 * ����WIFI
	 */
	private void EnableWiFi(boolean enable) {
		if (enable) {
			mWifiManager.setWifiEnabled(true);
		} else {
			mWifiManager.setWifiEnabled(false);
		}
	}

	/**
	 * ����GPS
	 */
	private void EnableGps() {
		try {
			PendingIntent.getBroadcast(mActivity, 0, mGpsIntent, 0).send();
		} catch (CanceledException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * �����ֵ�Ͳ
	 * 
	 * @param enable
	 */
	private void EnableTorch(boolean enable) {
		if (enable) {
			parameter.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
			mCamera.setParameters(parameter);
		} else {
			parameter.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
			mCamera.setParameters(parameter);
		}
	}

	/**
	 * ����GPRS
	 * 
	 * @param enable
	 */
	private void EnableGprs(boolean enable) {
		Class<?> conMgrClass = null; // ConnectivityManager��
		Field iConMgrField = null; // ConnectivityManager���е��ֶ�
		Object iConMgr = null; // IConnectivityManager�������
		Class<?> iConMgrClass = null; // IConnectivityManager��
		Method setMobileDataEnabledMethod = null; // setMobileDataEnabled����

		// ȡ��ConnectivityManager��
		try {
			conMgrClass = Class.forName(mConnMan.getClass().getName());
			// ȡ��ConnectivityManager���еĶ���mService
			iConMgrField = conMgrClass.getDeclaredField("mService");
			// ����mService�ɷ���
			iConMgrField.setAccessible(true);
			// ȡ��mService��ʵ������IConnectivityManager
			iConMgr = iConMgrField.get(mConnMan);
			// ȡ��IConnectivityManager��
			iConMgrClass = Class.forName(iConMgr.getClass().getName());
			// ȡ��IConnectivityManager���е�setMobileDataEnabled(boolean)����
			setMobileDataEnabledMethod = iConMgrClass.getDeclaredMethod(
					"setMobileDataEnabled", Boolean.TYPE);
			// ����setMobileDataEnabled�����ɷ���
			setMobileDataEnabledMethod.setAccessible(true);
			// ����setMobileDataEnabled����
			setMobileDataEnabledMethod.invoke(iConMgr, enable);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * ���ط���ģʽ
	 * 
	 * @param enable
	 */
	private void EnableFlyingMode(boolean enable) {
		if (enable) {
			Settings.System.putInt(mActivity.getContentResolver(),
					Settings.System.AIRPLANE_MODE_ON, 1);
			Intent intent = new Intent(Intent.ACTION_AIRPLANE_MODE_CHANGED);
			intent.putExtra("state", true);
			mActivity.sendBroadcast(intent);
		} else {
			Settings.System.putInt(mActivity.getContentResolver(),
					Settings.System.AIRPLANE_MODE_ON, 0);
			Intent intent = new Intent(Intent.ACTION_AIRPLANE_MODE_CHANGED);
			intent.putExtra("state", false);
			mActivity.sendBroadcast(intent);
		}
	}

	/**
	 * ���ϵķ��� ��������GPS�Ƿ����
	 * 
	 * @return
	 */
	private boolean isGpsEnabled() {
		String str = Settings.Secure.getString(mActivity.getContentResolver(),
				Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
		if (str != null) {
			return str.contains("gps");
		} else {
			return false;
		}
	}

	public void Release() {
		mCamera.release();
	}

	public void EnableDevice(String device) {
		if (device.equals("wifi")) {
			EnableWiFi(true);
		} else if (device.equals("gprs")) {
			EnableGprs(true);
		} else if (device.equals("gps")) {
			EnableGps();
		} else if (device.equals("bluetooh")) {
			EnableBlueTooth(true);
		} else if (device.equals("flash")) {
			EnableTorch(true);
		} else if (device.equals("airplanemode")) {
			EnableFlyingMode(true);
		}

	}

	public void DisableDevice(String device) {
		if (device.equals("wifi")) {
			EnableWiFi(false);
		} else if (device.equals("gprs")) {
			EnableGprs(false);
		} else if (device.equals("gps")) {
			EnableGps();
		} else if (device.equals("bluetooh")) {
			EnableBlueTooth(false);
		} else if (device.equals("flash")) {
			EnableTorch(false);
		} else if (device.equals("airplanemode")) {
			EnableFlyingMode(false);
		}
	}

}
