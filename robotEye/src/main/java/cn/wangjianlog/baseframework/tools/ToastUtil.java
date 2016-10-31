package cn.wangjianlog.baseframework.tools;

import android.content.Context;
import android.content.res.Resources;
import android.widget.Toast;

public class ToastUtil
{
  public static void show(Context paramContext, int paramInt)
  {
    if (paramContext != null)
    {
      String str = paramContext.getResources().getString(paramInt);
      if (!StringUtils.isEmpty(str))
        Toast.makeText(paramContext, str, 0).show();
    }
  }

  public static void show(Context paramContext, String paramString)
  {
    if ((paramContext != null) && (!StringUtils.isEmpty(paramString)))
      Toast.makeText(paramContext, paramString, 0).show();
  }
}

/* Location:           C:\Users\Administrator\Desktop\test111\classes_dex2jar.jar
 * Qualified Name:     cn.wangjianlog.baseframework.tools.ToastUtil
 * JD-Core Version:    0.6.2
 */