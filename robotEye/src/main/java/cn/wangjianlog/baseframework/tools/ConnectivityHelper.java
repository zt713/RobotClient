package cn.wangjianlog.baseframework.tools;

import android.content.Context;
import android.net.ConnectivityManager;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ConnectivityHelper
{
  private static Method sMethodGetMobileDataEnabled;
  private static Method sMethodSetMobileDataEnabled;

  static
  {
    initReflectionMethod();
  }

  public static boolean getMobileDataEnabled(Context paramContext)
  {
    ConnectivityManager localConnectivityManager = (ConnectivityManager)paramContext.getSystemService("connectivity");
    if (localConnectivityManager != null)
      initReflectionMethod();
    try
    {
      boolean bool = ((Boolean)sMethodGetMobileDataEnabled.invoke(localConnectivityManager, new Object[0])).booleanValue();
      return bool;
    }
    catch (IllegalArgumentException localIllegalArgumentException)
    {
      localIllegalArgumentException.printStackTrace();
      return false;
    }
    catch (IllegalAccessException localIllegalAccessException)
    {
      while (true)
        localIllegalAccessException.printStackTrace();
    }
    catch (InvocationTargetException localInvocationTargetException)
    {
      while (true)
        localInvocationTargetException.printStackTrace();
    }
  }

  private static void initReflectionMethod()
  {
    try
    {
      sMethodGetMobileDataEnabled = ConnectivityManager.class.getMethod("getMobileDataEnabled", new Class[0]);
      sMethodGetMobileDataEnabled.setAccessible(true);
      Class[] arrayOfClass = new Class[1];
      arrayOfClass[0] = Boolean.TYPE;
      sMethodSetMobileDataEnabled = ConnectivityManager.class.getMethod("setMobileDataEnabled", arrayOfClass);
      sMethodSetMobileDataEnabled.setAccessible(true);
      return;
    }
    catch (SecurityException localSecurityException)
    {
      localSecurityException.printStackTrace();
      return;
    }
    catch (NoSuchMethodException localNoSuchMethodException)
    {
      localNoSuchMethodException.printStackTrace();
    }
  }

  public static void setMobileDataEnabled(Context paramContext, boolean paramBoolean)
  {
    ConnectivityManager localConnectivityManager = (ConnectivityManager)paramContext.getSystemService("connectivity");
    if (localConnectivityManager != null)
      initReflectionMethod();
    try
    {
      Method localMethod = sMethodSetMobileDataEnabled;
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = Boolean.valueOf(paramBoolean);
      localMethod.invoke(localConnectivityManager, arrayOfObject);
      return;
    }
    catch (IllegalArgumentException localIllegalArgumentException)
    {
      localIllegalArgumentException.printStackTrace();
      return;
    }
    catch (IllegalAccessException localIllegalAccessException)
    {
      localIllegalAccessException.printStackTrace();
      return;
    }
    catch (InvocationTargetException localInvocationTargetException)
    {
      localInvocationTargetException.printStackTrace();
    }
  }
}

/* Location:           C:\Users\Administrator\Desktop\test111\classes_dex2jar.jar
 * Qualified Name:     cn.wangjianlog.baseframework.tools.ConnectivityHelper
 * JD-Core Version:    0.6.2
 */