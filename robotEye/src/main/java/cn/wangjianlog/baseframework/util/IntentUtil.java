package cn.wangjianlog.baseframework.util;

import android.app.Activity;
import android.content.Intent;

public class IntentUtil
{
  public static void startActivity(Activity paramActivity, Class<? extends Activity> paramClass)
  {
    startActivity(paramActivity, paramClass, false);
  }

  public static void startActivity(Activity paramActivity, Class<? extends Activity> paramClass, boolean paramBoolean)
  {
    startActivityData(null, null, paramActivity, paramClass, paramBoolean);
  }

  public static void startActivityData(String paramString1, String paramString2, Activity paramActivity, Class<? extends Activity> paramClass, boolean paramBoolean)
  {
    Intent localIntent = new Intent();
    if (paramString1 != null)
      localIntent.putExtra(paramString1, paramString2);
    localIntent.setClass(paramActivity, paramClass);
    paramActivity.startActivity(localIntent);
    if (paramBoolean)
      paramActivity.finish();
  }
}

/* Location:           C:\Users\Administrator\Desktop\test111\classes_dex2jar.jar
 * Qualified Name:     cn.wangjianlog.baseframework.util.IntentUtil
 * JD-Core Version:    0.6.2
 */