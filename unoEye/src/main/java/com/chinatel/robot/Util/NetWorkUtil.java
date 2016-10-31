package com.chinatel.robot.Util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

public class NetWorkUtil {
	public static int getConnectedType(Context paramContext) {
		if (paramContext != null) {
			NetworkInfo localNetworkInfo = ((ConnectivityManager) paramContext
					.getSystemService("connectivity")).getActiveNetworkInfo();
			if ((localNetworkInfo != null) && (localNetworkInfo.isAvailable()))
				return localNetworkInfo.getType();
		}
		return -1;
	}

	public static String getMacAddress(Context paramContext) {
		String str = "000000000000";
		try {
			WifiManager localWifiManager = (WifiManager) paramContext
					.getSystemService("wifi");
			Object localObject = null;
			if (localWifiManager == null)
				localObject = null;
			while (localObject != null)
				if (!StringUtils.isEmpty(((WifiInfo) localObject)
						.getMacAddress())) {
					str = ((WifiInfo) localObject).getMacAddress().replace(":",
							"");
					// break;
					WifiInfo localWifiInfo = localWifiManager
							.getConnectionInfo();
					localObject = localWifiInfo;
				} else {
					return str;
				}
		} catch (Exception localException) {
			localException.printStackTrace();
			return str;
		}
		return str;
	}

	public static boolean isMobileConnected(Context paramContext) {
		boolean bool = false;
		if (paramContext != null) {
			NetworkInfo localNetworkInfo = ((ConnectivityManager) paramContext
					.getSystemService("connectivity")).getNetworkInfo(0);
			bool = false;
			if (localNetworkInfo != null)
				bool = localNetworkInfo.isAvailable();
		}
		return bool;
	}

	public static boolean isNetworkConnected(Context paramContext) {
		if (paramContext != null) {
			NetworkInfo localNetworkInfo = ((ConnectivityManager) paramContext
					.getSystemService("connectivity")).getActiveNetworkInfo();
			if (localNetworkInfo != null)
				return localNetworkInfo.isAvailable();
		}
		return false;
	}

	public static boolean isWifiConnected(Context paramContext) {
		if (paramContext != null) {
			NetworkInfo localNetworkInfo = ((ConnectivityManager) paramContext
					.getSystemService("connectivity")).getNetworkInfo(1);
			if (localNetworkInfo != null)
				return localNetworkInfo.isAvailable();
		}
		return false;
	}
}

/*
 * Location:
 * C:\Users\Administrator\Desktop\小优\U03S源码\机器人本体.apk\classes_dex2jar.jar
 * Qualified Name: com.chinatel.robot.Util.NetWorkUtil JD-Core Version: 0.6.2
 */