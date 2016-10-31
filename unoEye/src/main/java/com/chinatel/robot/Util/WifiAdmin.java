package com.chinatel.robot.Util;

import android.content.Context;
import android.net.DhcpInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.net.wifi.WifiManager.WifiLock;
import android.util.Log;
import java.io.PrintStream;
import java.util.BitSet;
import java.util.Iterator;
import java.util.List;

public class WifiAdmin {
	private DhcpInfo dhcpinfo;
	private Context mContext;
	private List<WifiConfiguration> mWifiConfigurations;
	private WifiInfo mWifiInfo;
	private List<ScanResult> mWifiList;
	WifiManager.WifiLock mWifiLock;
	private WifiManager mWifiManager;

	public WifiAdmin(Context paramContext) {
		this.mContext = paramContext;
		this.mWifiManager = ((WifiManager) paramContext
				.getSystemService("wifi"));
		this.mWifiInfo = this.mWifiManager.getConnectionInfo();
		this.dhcpinfo = this.mWifiManager.getDhcpInfo();
	}

	public void acquireWifiLock() {
		this.mWifiLock.acquire();
	}

	public void addNetWork(WifiConfiguration paramWifiConfiguration) {
		int i = this.mWifiManager.addNetwork(paramWifiConfiguration);
		boolean bool = this.mWifiManager.enableNetwork(i, true);
		System.out.println("a--" + i);
		System.out.println("b--" + bool);
	}

	public int checkState() {
		return this.mWifiManager.getWifiState();
	}

	public void closeWifi() {
		if (!this.mWifiManager.isWifiEnabled())
			this.mWifiManager.setWifiEnabled(false);
	}

	public void connectWifiByName(String paramString) {
		startScan();
		List localList = getWifiList();
		if (localList != null)
			;
		label149: for (int i = 0;; i++) {
			if (i >= localList.size())
				;
			String str;
			WiFiPwType localWiFiPwType;
			while (true) {
				// return;
				str = ((ScanResult) localList.get(i)).SSID;
				if (!str.equals(paramString))
					break label149;
				localWiFiPwType = getCipherType(this.mContext, str);
				if (getSSID().contains(paramString))
					;
				for (int j = 0; (localWiFiPwType != null) && (j != 0); j = 1) {
					WifiConfiguration localWifiConfiguration1 = isExsits(str);
					if (localWifiConfiguration1 == null)
						// break label123;
						Log.i("robot", "存在这个wifi的配置信息，能够直接连接");
					enableNetWork(localWifiConfiguration1.networkId);
					return;
				}
			}
			/*
			 * label123: WifiConfiguration localWifiConfiguration2 =
			 * createWifiInfo(str, "12345678", localWiFiPwType);
			 * localWifiConfiguration2.priority = 100000;
			 * addNetWork(localWifiConfiguration2);
			 */
			// return;
		}
	}

	public void connetionConfiguration(int paramInt) {
		if (paramInt > this.mWifiConfigurations.size())
			return;
		this.mWifiManager
				.enableNetwork(((WifiConfiguration) this.mWifiConfigurations
						.get(paramInt)).networkId, true);
	}

	public WifiConfiguration createWifiInfo(String paramString1,
			String paramString2, WiFiPwType paramWiFiPwType) {
		WifiConfiguration localWifiConfiguration1 = new WifiConfiguration();
		localWifiConfiguration1.allowedAuthAlgorithms.clear();
		localWifiConfiguration1.allowedGroupCiphers.clear();
		localWifiConfiguration1.allowedKeyManagement.clear();
		localWifiConfiguration1.allowedPairwiseCiphers.clear();
		localWifiConfiguration1.allowedProtocols.clear();
		localWifiConfiguration1.SSID = ("\"" + paramString1 + "\"");
		WifiConfiguration localWifiConfiguration2 = isExsits(paramString1);
		if (localWifiConfiguration2 != null)
			this.mWifiManager.removeNetwork(localWifiConfiguration2.networkId);
		if (paramWiFiPwType == WiFiPwType.NON) {
			localWifiConfiguration1.wepKeys[0] = "";
			localWifiConfiguration1.allowedKeyManagement.set(0);
			localWifiConfiguration1.wepTxKeyIndex = 0;
		}
		if (paramWiFiPwType == WiFiPwType.WEP) {
			localWifiConfiguration1.hiddenSSID = true;
			localWifiConfiguration1.wepKeys[0] = ("\"" + paramString2 + "\"");
			localWifiConfiguration1.allowedAuthAlgorithms.set(1);
			localWifiConfiguration1.allowedGroupCiphers.set(3);
			localWifiConfiguration1.allowedGroupCiphers.set(2);
			localWifiConfiguration1.allowedGroupCiphers.set(0);
			localWifiConfiguration1.allowedGroupCiphers.set(1);
			localWifiConfiguration1.allowedKeyManagement.set(0);
			localWifiConfiguration1.wepTxKeyIndex = 0;
		}
		if (paramWiFiPwType == WiFiPwType.WPA) {
			localWifiConfiguration1.preSharedKey = ("\"" + paramString2 + "\"");
			localWifiConfiguration1.hiddenSSID = true;
			localWifiConfiguration1.allowedAuthAlgorithms.set(0);
			localWifiConfiguration1.allowedGroupCiphers.set(2);
			localWifiConfiguration1.allowedKeyManagement.set(1);
			localWifiConfiguration1.allowedPairwiseCiphers.set(1);
			localWifiConfiguration1.allowedGroupCiphers.set(3);
			localWifiConfiguration1.allowedPairwiseCiphers.set(2);
			localWifiConfiguration1.status = 2;
		}
		return localWifiConfiguration1;
	}

	public void createWifiLock() {
		this.mWifiLock = this.mWifiManager.createWifiLock("test");
	}

	public void disConnectionWifi(int paramInt) {
		this.mWifiManager.disableNetwork(paramInt);
		this.mWifiManager.disconnect();
	}

	public void enableNetWork(int paramInt) {
		this.mWifiManager.enableNetwork(paramInt, true);
	}

	public String getBSSID() {
		if (this.mWifiInfo == null)
			return "NULL";
		return this.mWifiInfo.getBSSID();
	}

	public WiFiPwType getCipherType(Context paramContext, String paramString) {
		startScan();
		List localList = getWifiList();
		WiFiPwType localWiFiPwType = null;
		Iterator localIterator = localList.iterator();
		while (true) {
			if (!localIterator.hasNext())
				return localWiFiPwType;
			ScanResult localScanResult = (ScanResult) localIterator.next();
			String str1 = localScanResult.SSID.trim();
			if ((!StringUtils.isEmpty(str1)) && (str1.equals(paramString))) {
				String str2 = localScanResult.capabilities;
				if (!StringUtils.isEmpty(str2))
					if ((str2.contains("WPA")) || (str2.contains("wpa")))
						localWiFiPwType = WiFiPwType.WPA;
					else if ((str2.contains("WEP")) || (str2.contains("wep")))
						localWiFiPwType = WiFiPwType.WEP;
					else
						localWiFiPwType = WiFiPwType.NON;
			}
		}
	}

	public List<WifiConfiguration> getConfiguration() {
		return this.mWifiConfigurations;
	}

	public int getIpAddress() {
		if (this.mWifiInfo == null)
			return 0;
		return this.mWifiInfo.getIpAddress();
	}

	public String getMacAddress() {
		if (this.mWifiInfo == null)
			return "NULL";
		return this.mWifiInfo.getMacAddress();
	}

	public int getNetWordId() {
		if (this.mWifiInfo == null)
			return 0;
		return this.mWifiInfo.getNetworkId();
	}

	public String getSSID() {
		if (this.mWifiInfo == null)
			return "NULL";
		return this.mWifiInfo.getSSID();
	}

	public int getServerIpAddress() {
		if (this.dhcpinfo == null)
			return 0;
		return this.dhcpinfo.serverAddress;
	}

	public String getWifiInfo() {
		if (this.mWifiInfo == null)
			return "NULL";
		return this.mWifiInfo.toString();
	}

	public List<ScanResult> getWifiList() {
		return this.mWifiList;
	}

	public WifiConfiguration isExsits(String paramString) {
		Iterator localIterator = this.mWifiManager.getConfiguredNetworks()
				.iterator();
		WifiConfiguration localWifiConfiguration;
		do {
			if (!localIterator.hasNext())
				return null;
			localWifiConfiguration = (WifiConfiguration) localIterator.next();
		} while (!localWifiConfiguration.SSID.equals("\"" + paramString + "\""));
		return localWifiConfiguration;
	}

	public StringBuffer lookUpScan() {
		StringBuffer localStringBuffer = new StringBuffer();
		for (int i = 0;; i++) {
			if (i >= this.mWifiList.size())
				return localStringBuffer;
			localStringBuffer.append("Index_" + new Integer(i + 1).toString()
					+ ":");
			localStringBuffer.append(
					((ScanResult) this.mWifiList.get(i)).toString()).append(
					"\n");
		}
	}

	public void openWifi() {
		if (!this.mWifiManager.isWifiEnabled())
			this.mWifiManager.setWifiEnabled(true);
	}

	public void releaseWifiLock() {
		if (this.mWifiLock.isHeld())
			this.mWifiLock.acquire();
	}

	public void startScan() {
		this.mWifiManager.startScan();
		this.mWifiList = this.mWifiManager.getScanResults();
		this.mWifiConfigurations = this.mWifiManager.getConfiguredNetworks();
	}

	public static enum WiFiPwType {
		NON, WEP, WPA
		/*
		 * static { WiFiPwType[] arrayOfWiFiPwType = new WiFiPwType[3];
		 * arrayOfWiFiPwType[0] = NON; arrayOfWiFiPwType[1] = WEP;
		 * arrayOfWiFiPwType[2] = WPA; }
		 */
	}
}

/*
 * Location:
 * C:\Users\Administrator\Desktop\小优\U03S源码\机器人本体.apk\classes_dex2jar.jar
 * Qualified Name: com.chinatel.robot.Util.WifiAdmin JD-Core Version: 0.6.2
 */