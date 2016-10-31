package cn.wangjianlog.baseframework.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.WebSettings;
import cn.wangjianlog.baseframework.view.ToastUtil;

public class Util
{
  public static final String JAVASCRIPT_NAME = "android";
  public static final String URL = "url";

  public static void callContact(Activity paramActivity, String paramString)
  {
    Intent localIntent = new Intent("android.intent.action.DIAL", Uri.parse("tel:" + paramString));
    localIntent.setFlags(268435456);
    paramActivity.startActivity(localIntent);
  }

  public static String getAppMetaData(Context paramContext, String paramString)
  {
    if ((paramContext == null) || (TextUtils.isEmpty(paramString)));
      try
      {
        PackageManager localPackageManager = paramContext.getPackageManager();
        if (localPackageManager != null)
        {
          ApplicationInfo localApplicationInfo = localPackageManager.getApplicationInfo(paramContext.getPackageName(), 128);
          if ((localApplicationInfo != null) && (localApplicationInfo.metaData != null))
          {
            String str = localApplicationInfo.metaData.getString(paramString);
            return str;
          }
        }
      }
      catch (PackageManager.NameNotFoundException localNameNotFoundException)
      {
        localNameNotFoundException.printStackTrace();
      }
    return null;
  }

  public static String getMetaData(Context paramContext, String paramString)
  {
    try
    {
      Object localObject = paramContext.getPackageManager().getApplicationInfo(paramContext.getPackageName(), 128).metaData.get(paramString);
      if (localObject != null)
      {
        String str = localObject.toString();
        return str;
      }
    }
    catch (Exception localException)
    {
      Log.e("debug_test", "get token error", localException);
    }
    return null;
  }

  public static boolean isConnected(Context paramContext)
  {
    if (NetworkUtil.isNetworkConnected(paramContext))
      return true;
    ToastUtil.showToast(paramContext, 0, paramContext.getResources().getString(2131296259));
    return false;
  }

  public static void printLog(String paramString)
  {
    Log.w("debug_test", paramString);
  }

  public static void setVewSettings(WebSettings paramWebSettings)
  {
    if (paramWebSettings == null)
      return;
    paramWebSettings.setJavaScriptEnabled(true);
    paramWebSettings.setCacheMode(2);
    paramWebSettings.setAppCacheEnabled(false);
    paramWebSettings.setDomStorageEnabled(true);
    paramWebSettings.setJavaScriptCanOpenWindowsAutomatically(true);
    if (Build.VERSION.SDK_INT >= 19)
    {
      paramWebSettings.setLoadsImagesAutomatically(true);
      return;
    }
    paramWebSettings.setLoadsImagesAutomatically(false);
  }

  public static String strchange(String paramString)
  {
    if ((paramString == null) || ("".equals(paramString)))
      paramString = null;
    while (paramString.indexOf(".") <= 0)
      return paramString;
    return paramString.substring(0, paramString.indexOf("."));
  }
}

/* Location:           C:\Users\Administrator\Desktop\test111\classes_dex2jar.jar
 * Qualified Name:     cn.wangjianlog.baseframework.util.Util
 * JD-Core Version:    0.6.2
 */