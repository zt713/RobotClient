package cn.wangjianlog.baseframework.tools;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Build;
import android.os.Build.VERSION;
import android.telephony.SmsManager;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;
import java.io.File;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SystemUtil
{
  public static final int DEFAULT_THREAD_POOL_SIZE = getDefaultThreadPoolSize();

  public static void callContact(Context paramContext, String paramString)
  {
    paramContext.startActivity(new Intent("android.intent.action.CALL", Uri.parse("tel:" + paramString)));
  }

  public static String getCurrentLanguage(Context paramContext)
  {
    return paramContext.getResources().getConfiguration().locale.getCountry();
  }

  public static Locale getCurrentLocale(Context paramContext)
  {
    return paramContext.getResources().getConfiguration().locale;
  }

  public static int getDefaultThreadPoolSize()
  {
    return getDefaultThreadPoolSize(8);
  }

  public static int getDefaultThreadPoolSize(int paramInt)
  {
    int i = 1 + 2 * Runtime.getRuntime().availableProcessors();
    if (i > paramInt)
      return paramInt;
    return i;
  }

  public static String getNowVersion(Context paramContext)
  {
    String str = null;
    try
    {
      str = paramContext.getPackageManager().getPackageInfo(paramContext.getPackageName(), 0).versionName;
      if (str != null)
      {
        int i = str.length();
        if (i > 0);
      }
      else
      {
        return "";
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return str;
  }

  public static int getScreenWidthOrHeight(Context paramContext, int paramInt)
  {
    WindowManager localWindowManager = (WindowManager)paramContext.getSystemService("window");
    DisplayMetrics localDisplayMetrics = new DisplayMetrics();
    localWindowManager.getDefaultDisplay().getMetrics(localDisplayMetrics);
    if (paramInt == 0)
      return localDisplayMetrics.widthPixels;
    return localDisplayMetrics.heightPixels;
  }

  public static void openInternetSetting(Context paramContext)
  {
    if (Build.VERSION.SDK_INT > 10)
    {
      paramContext.startActivity(new Intent("android.settings.SETTINGS"));
      return;
    }
    paramContext.startActivity(new Intent("android.settings.WIRELESS_SETTINGS"));
  }

  public static void openUrl(Context paramContext, String paramString)
  {
    paramContext.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(paramString)));
  }

  public static void outDeviceInfo(Activity paramActivity)
  {
    DisplayMetrics localDisplayMetrics = new DisplayMetrics();
    paramActivity.getWindowManager().getDefaultDisplay().getMetrics(localDisplayMetrics);
    int i = localDisplayMetrics.widthPixels;
    int j = localDisplayMetrics.heightPixels;
    float f = localDisplayMetrics.density;
    int k = localDisplayMetrics.densityDpi;
    System.out.println("width:" + i);
    System.out.println("height:" + j);
    System.out.println("density:" + f);
    System.out.println("densityDpi:" + k);
  }

  public static void sendEmail(Context paramContext, String[] paramArrayOfString, String paramString1, String paramString2, String paramString3)
  {
    Intent localIntent = new Intent("android.intent.action.SEND");
    if (paramArrayOfString != null)
      localIntent.putExtra("android.intent.extra.EMAIL", paramArrayOfString);
    if (paramString1 != null)
      localIntent.putExtra("android.intent.extra.SUBJECT", paramString1);
    if (paramString2 != null)
      localIntent.putExtra("android.intent.extra.TEXT", paramString2);
    if (paramString3 != null)
    {
      localIntent.putExtra("android.intent.extra.STREAM", Uri.fromFile(new File(paramString3)));
      localIntent.setType("image/png");
    }
    paramContext.startActivity(Intent.createChooser(localIntent, "请选择发送软件"));
  }

  public static void sendSMS(Context paramContext, String paramString1, String paramString2)
  {
    SmsManager localSmsManager = SmsManager.getDefault();
    ArrayList localArrayList = localSmsManager.divideMessage(paramString2);
    for (int i = 0; ; i++)
    {
      if (i >= localArrayList.size())
        return;
      localSmsManager.sendTextMessage(paramString1, null, (String)localArrayList.get(i), null, null);
    }
  }

  public static Bitmap takeScreenShot(Activity paramActivity)
  {
    View localView = paramActivity.getWindow().getDecorView();
    localView.setDrawingCacheEnabled(true);
    localView.buildDrawingCache();
    Bitmap localBitmap1 = localView.getDrawingCache();
    Rect localRect = new Rect();
    paramActivity.getWindow().getDecorView().getWindowVisibleDisplayFrame(localRect);
    int i = localRect.top;
    System.out.println(i);
    Bitmap localBitmap2 = Bitmap.createBitmap(localBitmap1, 0, i, paramActivity.getWindowManager().getDefaultDisplay().getWidth(), paramActivity.getWindowManager().getDefaultDisplay().getHeight() - i);
    localView.destroyDrawingCache();
    Toast.makeText(paramActivity, "截图成功", 0).show();
    return localBitmap2;
  }
}

/* Location:           C:\Users\Administrator\Desktop\test111\classes_dex2jar.jar
 * Qualified Name:     cn.wangjianlog.baseframework.tools.SystemUtil
 * JD-Core Version:    0.6.2
 */