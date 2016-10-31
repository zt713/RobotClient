package cn.wangjianlog.baseframework.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkUtil
{
  public static boolean isNetworkConnected(Context paramContext)
  {
    if (paramContext != null)
    {
      NetworkInfo localNetworkInfo = ((ConnectivityManager)paramContext.getSystemService("connectivity")).getActiveNetworkInfo();
      if (localNetworkInfo != null)
        return localNetworkInfo.isAvailable();
    }
    return false;
  }
}

/* Location:           C:\Users\Administrator\Desktop\test111\classes_dex2jar.jar
 * Qualified Name:     cn.wangjianlog.baseframework.util.NetworkUtil
 * JD-Core Version:    0.6.2
 */