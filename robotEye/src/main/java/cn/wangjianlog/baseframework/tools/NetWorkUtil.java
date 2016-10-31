package cn.wangjianlog.baseframework.tools;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;
import android.util.Log;

@SuppressLint({"DefaultLocale"})
public class NetWorkUtil
{
  public static boolean checkNetwork(Context paramContext)
  {
    ConnectivityManager localConnectivityManager = (ConnectivityManager)paramContext.getSystemService("connectivity");
    NetworkInfo localNetworkInfo = localConnectivityManager.getActiveNetworkInfo();
    return (localConnectivityManager != null) && (localNetworkInfo != null) && (localNetworkInfo.isAvailable()) && (localNetworkInfo.isConnected());
  }

  public static void closeVifi(Context paramContext)
  {
    WifiManager localWifiManager = (WifiManager)paramContext.getSystemService("wifi");
    if ((localWifiManager != null) && (!localWifiManager.isWifiEnabled()))
      localWifiManager.setWifiEnabled(true);
  }

  public static int getAPNType(Context paramContext)
  {
    int i = -1;
    NetworkInfo localNetworkInfo = ((ConnectivityManager)paramContext.getSystemService("connectivity")).getActiveNetworkInfo();
    if (localNetworkInfo == null)
      return i;
    int j = localNetworkInfo.getType();
    if (j == 0)
    {
      Log.e("networkInfo.getExtraInfo()", "networkInfo.getExtraInfo() is " + localNetworkInfo.getExtraInfo());
      if (localNetworkInfo.getExtraInfo().toLowerCase().equals("cmnet"))
        i = 3;
    }
	return j;
  }

  public static int getCurrentNetworkInfo(Context paramContext)
  {
    ConnectivityManager localConnectivityManager = (ConnectivityManager)paramContext.getSystemService("connectivity");
    NetworkInfo localNetworkInfo1 = localConnectivityManager.getActiveNetworkInfo();
    NetworkInfo localNetworkInfo2;
	try
    {
      NetworkInfo localNetworkInfo3 = localConnectivityManager.getNetworkInfo(6);
      localNetworkInfo2 = localNetworkInfo3;
      if ((localNetworkInfo1 != null) && (localNetworkInfo1.getState() == NetworkInfo.State.CONNECTED))
      {
        if ((localNetworkInfo1.getTypeName().contains("WIFI")) || ((localNetworkInfo2 != null) && (localNetworkInfo2.isConnected())))
          return 2;
        if (localNetworkInfo1.getType() == 0)
          return 1;
      }
      return 0;
    }
    catch (Exception localException)
    {
    }
	return 0;
  }

  public static String getMacAddress(Context paramContext)
  {
    String str = "000000000000";
    try
    {
      WifiManager localWifiManager = (WifiManager)paramContext.getSystemService("wifi");
      Object localObject = null;
      if (localWifiManager == null)
        localObject = null;
      while (localObject != null)
        if (!StringUtils.isEmpty(((WifiInfo) localObject).getMacAddress()))
        {
          str = ((WifiInfo) localObject).getMacAddress().replace(":", "");
          WifiInfo localWifiInfo = localWifiManager.getConnectionInfo();
          localObject = localWifiInfo;
        }
        else
        {
          return str;
        }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      return str;
    }
    return str;
  }

  public static boolean isMobileConnected(Context paramContext)
  {
    boolean bool = false;
    if (paramContext != null)
    {
      NetworkInfo localNetworkInfo = ((ConnectivityManager)paramContext.getSystemService("connectivity")).getNetworkInfo(0);
      bool = false;
      if (localNetworkInfo != null)
        bool = localNetworkInfo.isAvailable();
    }
    return bool;
  }

  public static boolean isSimCardRady(Context paramContext)
  {
    try
    {
      int i = ((TelephonyManager)paramContext.getSystemService("phone")).getSimState();
      return 5 == i;
    }
    catch (Exception localException)
    {
    }
    return false;
  }

  public static boolean isWifiConnected(Context paramContext)
  {
    if (paramContext != null)
    {
      NetworkInfo localNetworkInfo = ((ConnectivityManager)paramContext.getSystemService("connectivity")).getNetworkInfo(1);
      if (localNetworkInfo != null)
        return localNetworkInfo.isAvailable();
    }
    return false;
  }

  public static void openAndCloseMobileConnected(Context paramContext)
  {
    if (isSimCardRady(paramContext))
    {
      if (ConnectivityHelper.getMobileDataEnabled(paramContext))
        ConnectivityHelper.setMobileDataEnabled(paramContext, true);
    }
    else
      return;
    ConnectivityHelper.setMobileDataEnabled(paramContext, false);
  }

  public static void openAndCloseVifi(Context paramContext)
  {
    WifiManager localWifiManager = (WifiManager)paramContext.getSystemService("wifi");
    if ((localWifiManager != null) && (localWifiManager.isWifiEnabled()))
    {
      localWifiManager.setWifiEnabled(false);
      return;
    }
    localWifiManager.setWifiEnabled(true);
  }

  public static void openVifi(Context paramContext)
  {
    WifiManager localWifiManager = (WifiManager)paramContext.getSystemService("wifi");
    if ((localWifiManager != null) && (localWifiManager.isWifiEnabled()))
      localWifiManager.setWifiEnabled(false);
  }
}

/* Location:           C:\Users\Administrator\Desktop\test111\classes_dex2jar.jar
 * Qualified Name:     cn.wangjianlog.baseframework.tools.NetWorkUtil
 * JD-Core Version:    0.6.2
 */