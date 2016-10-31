package cn.wangjianlog.baseframework.tools;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ResourceUtils
{
  public static String geFileFromAssets(Context paramContext, String paramString)
  {
    if ((paramContext == null) || (StringUtils.isEmpty(paramString)))
      return null;
    StringBuilder localStringBuilder = new StringBuilder("");
    try
    {
      BufferedReader localBufferedReader = new BufferedReader(new InputStreamReader(paramContext.getResources().getAssets().open(paramString)));
      while (true)
      {
        String str = localBufferedReader.readLine();
        if (str == null)
          return localStringBuilder.toString();
        localStringBuilder.append(str);
      }
    }
    catch (IOException localIOException)
    {
      localIOException.printStackTrace();
    }
    return null;
  }

  public static String geFileFromRaw(Context paramContext, int paramInt)
  {
    if (paramContext == null)
      return null;
    StringBuilder localStringBuilder = new StringBuilder();
    try
    {
      BufferedReader localBufferedReader = new BufferedReader(new InputStreamReader(paramContext.getResources().openRawResource(paramInt)));
      while (true)
      {
        String str = localBufferedReader.readLine();
        if (str == null)
          return localStringBuilder.toString();
        localStringBuilder.append(str);
      }
    }
    catch (IOException localIOException)
    {
      localIOException.printStackTrace();
    }
    return null;
  }
}

/* Location:           C:\Users\Administrator\Desktop\test111\classes_dex2jar.jar
 * Qualified Name:     cn.wangjianlog.baseframework.tools.ResourceUtils
 * JD-Core Version:    0.6.2
 */