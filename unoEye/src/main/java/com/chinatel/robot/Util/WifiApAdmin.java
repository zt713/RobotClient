package com.chinatel.robot.Util;

import android.content.Context;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.util.Log;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.BitSet;

public class WifiApAdmin {
	public static final String TAG = "WifiApAdmin";
	public static final int WIFI_AP_STATE_DISABLED = 11;
	public static final int WIFI_AP_STATE_DISABLING = 10;
	public static final int WIFI_AP_STATE_ENABLED = 13;
	public static final int WIFI_AP_STATE_ENABLING = 12;
	public static final int WIFI_AP_STATE_FAILED = 14;
	private Context mContext = null;
	private String mPasswd = "";
	private String mSSID = "";
	private WifiManager mWifiManager = null;

	public WifiApAdmin(Context paramContext) {
		this.mContext = paramContext;
		this.mWifiManager = ((WifiManager) this.mContext
				.getSystemService("wifi"));
	}

	public static void closeWifiAp(Context paramContext) {
		closeWifiAp((WifiManager) paramContext.getSystemService("wifi"));
	}

	private static void closeWifiAp(WifiManager paramWifiManager) {
		if (isWifiApEnabled(paramWifiManager))
			;
		try {
			Method localMethod1 = paramWifiManager.getClass().getMethod(
					"getWifiApConfiguration", new Class[0]);
			localMethod1.setAccessible(true);
			WifiConfiguration localWifiConfiguration = (WifiConfiguration) localMethod1
					.invoke(paramWifiManager, new Object[0]);
			Class localClass = paramWifiManager.getClass();
			Class[] arrayOfClass = new Class[2];
			arrayOfClass[0] = WifiConfiguration.class;
			arrayOfClass[1] = Boolean.TYPE;
			Method localMethod2 = localClass.getMethod("setWifiApEnabled",
					arrayOfClass);
			Object[] arrayOfObject = new Object[2];
			arrayOfObject[0] = localWifiConfiguration;
			arrayOfObject[1] = Boolean.valueOf(false);
			localMethod2.invoke(paramWifiManager, arrayOfObject);
			return;
		} catch (NoSuchMethodException localNoSuchMethodException) {
			localNoSuchMethodException.printStackTrace();
			return;
		} catch (IllegalArgumentException localIllegalArgumentException) {
			localIllegalArgumentException.printStackTrace();
			return;
		} catch (IllegalAccessException localIllegalAccessException) {
			localIllegalAccessException.printStackTrace();
			return;
		} catch (InvocationTargetException localInvocationTargetException) {
			localInvocationTargetException.printStackTrace();
		}
	}

	private static boolean isWifiApEnabled(WifiManager paramWifiManager) {
		try {
			Method localMethod = paramWifiManager.getClass().getMethod(
					"isWifiApEnabled", new Class[0]);
			localMethod.setAccessible(true);
			boolean bool = ((Boolean) localMethod.invoke(paramWifiManager,
					new Object[0])).booleanValue();
			return bool;
		} catch (NoSuchMethodException localNoSuchMethodException) {
			localNoSuchMethodException.printStackTrace();
			return false;
		} catch (Exception localException) {
			while (true)
				localException.printStackTrace();
		}
	}

	public int getWifiApState(Context paramContext) {
		WifiManager localWifiManager = (WifiManager) paramContext
				.getSystemService("wifi");
		try {
			int i = ((Integer) localWifiManager.getClass()
					.getMethod("getWifiApState", new Class[0])
					.invoke(localWifiManager, new Object[0])).intValue();
			Log.i("WifiApAdmin", "wifi state:  " + i);
			return i;
		} catch (Exception localException) {
			Log.e("WifiApAdmin", "Cannot get WiFi AP state" + localException);
		}
		return 14;
	}

	public boolean isWifiApEnabled() {
		int i = getWifiApState(this.mContext);
		return (12 == i) || (13 == i);
	}

	public void startWifiAp(String paramString1, String paramString2) {
		startWifiAp(paramString1, paramString2, null);
	}

	public void startWifiAp(String paramString1, String paramString2,
			final WifiApAdminCallBack paramWifiApAdminCallBack) {
		this.mSSID = paramString1;
		this.mPasswd = paramString2;
		if (this.mWifiManager.isWifiEnabled())
			this.mWifiManager.setWifiEnabled(false);
		stratWifiAp();
		new MyTimerCheck() {
			public void doTimeOutWork() {
				exit();
			}

			public void doTimerCheckWork() {
				if (WifiApAdmin.isWifiApEnabled(WifiApAdmin.this.mWifiManager)) {
					Log.v("WifiApAdmin", "Wifi enabled success!");
					if (paramWifiApAdminCallBack != null)
						paramWifiApAdminCallBack.success();
					exit();
					return;
				}
				Log.v("WifiApAdmin", "Wifi enabled failed!");
			}
		}.start(15, 1000);
	}

	public void stratWifiAp() {
		try {
			Class localClass = this.mWifiManager.getClass();
			Class[] arrayOfClass = new Class[2];
			arrayOfClass[0] = WifiConfiguration.class;
			arrayOfClass[1] = Boolean.TYPE;
			Method localMethod = localClass.getMethod("setWifiApEnabled",
					arrayOfClass);
			WifiConfiguration localWifiConfiguration = new WifiConfiguration();
			localWifiConfiguration.SSID = this.mSSID;
			localWifiConfiguration.preSharedKey = this.mPasswd;
			localWifiConfiguration.allowedAuthAlgorithms.set(0);
			localWifiConfiguration.allowedProtocols.set(1);
			localWifiConfiguration.allowedProtocols.set(0);
			localWifiConfiguration.allowedKeyManagement.set(1);
			localWifiConfiguration.allowedPairwiseCiphers.set(2);
			localWifiConfiguration.allowedPairwiseCiphers.set(1);
			localWifiConfiguration.allowedGroupCiphers.set(3);
			localWifiConfiguration.allowedGroupCiphers.set(2);
			WifiManager localWifiManager = this.mWifiManager;
			Object[] arrayOfObject = new Object[2];
			arrayOfObject[0] = localWifiConfiguration;
			arrayOfObject[1] = Boolean.valueOf(true);
			localMethod.invoke(localWifiManager, arrayOfObject);
			return;
		} catch (IllegalArgumentException localIllegalArgumentException) {
			localIllegalArgumentException.printStackTrace();
			return;
		} catch (IllegalAccessException localIllegalAccessException) {
			localIllegalAccessException.printStackTrace();
			return;
		} catch (InvocationTargetException localInvocationTargetException) {
			localInvocationTargetException.printStackTrace();
			return;
		} catch (SecurityException localSecurityException) {
			localSecurityException.printStackTrace();
			return;
		} catch (NoSuchMethodException localNoSuchMethodException) {
			localNoSuchMethodException.printStackTrace();
		}
	}

	public static abstract interface WifiApAdminCallBack {
		public abstract void success();
	}
}

/*
 * Location:
 * C:\Users\Administrator\Desktop\小优\U03S源码\机器人本体.apk\classes_dex2jar.jar
 * Qualified Name: com.chinatel.robot.Util.WifiApAdmin JD-Core Version: 0.6.2
 */