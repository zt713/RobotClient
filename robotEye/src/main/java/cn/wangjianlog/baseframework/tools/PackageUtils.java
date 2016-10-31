package cn.wangjianlog.baseframework.tools;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.util.Log;
import java.io.File;
import java.util.List;

public class PackageUtils
{
  public static final int DELETE_FAILED_DEVICE_POLICY_MANAGER = -2;
  public static final int DELETE_FAILED_INTERNAL_ERROR = -1;
  public static final int DELETE_FAILED_INVALID_PACKAGE = -3;
  public static final int DELETE_FAILED_PERMISSION_DENIED = -4;
  public static final int DELETE_SUCCEEDED = 1;
  public static final int INSTALL_FAILED_ALREADY_EXISTS = -1;
  public static final int INSTALL_FAILED_CONFLICTING_PROVIDER = -13;
  public static final int INSTALL_FAILED_CONTAINER_ERROR = -18;
  public static final int INSTALL_FAILED_CPU_ABI_INCOMPATIBLE = -16;
  public static final int INSTALL_FAILED_DEXOPT = -11;
  public static final int INSTALL_FAILED_DUPLICATE_PACKAGE = -5;
  public static final int INSTALL_FAILED_INSUFFICIENT_STORAGE = -4;
  public static final int INSTALL_FAILED_INTERNAL_ERROR = -110;
  public static final int INSTALL_FAILED_INVALID_APK = -2;
  public static final int INSTALL_FAILED_INVALID_INSTALL_LOCATION = -19;
  public static final int INSTALL_FAILED_INVALID_URI = -3;
  public static final int INSTALL_FAILED_MEDIA_UNAVAILABLE = -20;
  public static final int INSTALL_FAILED_MISSING_FEATURE = -17;
  public static final int INSTALL_FAILED_MISSING_SHARED_LIBRARY = -9;
  public static final int INSTALL_FAILED_NEWER_SDK = -14;
  public static final int INSTALL_FAILED_NO_SHARED_USER = -6;
  public static final int INSTALL_FAILED_OLDER_SDK = -12;
  public static final int INSTALL_FAILED_OTHER = -1000000;
  public static final int INSTALL_FAILED_PACKAGE_CHANGED = -23;
  public static final int INSTALL_FAILED_REPLACE_COULDNT_DELETE = -10;
  public static final int INSTALL_FAILED_SHARED_USER_INCOMPATIBLE = -8;
  public static final int INSTALL_FAILED_TEST_ONLY = -15;
  public static final int INSTALL_FAILED_UID_CHANGED = -24;
  public static final int INSTALL_FAILED_UPDATE_INCOMPATIBLE = -7;
  public static final int INSTALL_FAILED_VERIFICATION_FAILURE = -22;
  public static final int INSTALL_FAILED_VERIFICATION_TIMEOUT = -21;
  public static final int INSTALL_PARSE_FAILED_BAD_MANIFEST = -101;
  public static final int INSTALL_PARSE_FAILED_BAD_PACKAGE_NAME = -106;
  public static final int INSTALL_PARSE_FAILED_BAD_SHARED_USER_ID = -107;
  public static final int INSTALL_PARSE_FAILED_CERTIFICATE_ENCODING = -105;
  public static final int INSTALL_PARSE_FAILED_INCONSISTENT_CERTIFICATES = -104;
  public static final int INSTALL_PARSE_FAILED_MANIFEST_EMPTY = -109;
  public static final int INSTALL_PARSE_FAILED_MANIFEST_MALFORMED = -108;
  public static final int INSTALL_PARSE_FAILED_NOT_APK = -100;
  public static final int INSTALL_PARSE_FAILED_NO_CERTIFICATES = -103;
  public static final int INSTALL_PARSE_FAILED_UNEXPECTED_EXCEPTION = -102;
  public static final int INSTALL_SUCCEEDED = 1;
  public static final String TAG = "PackageUtils";

  public static final int install(Context paramContext, String paramString)
  {
    if ((isSystemApplication(paramContext)) || (ShellUtils.checkRootPermission()))
      return installSilent(paramContext, paramString);
    if (installNormal(paramContext, paramString))
      return 1;
    return -3;
  }

  public static boolean installNormal(Context paramContext, String paramString)
  {
    Intent localIntent = new Intent("android.intent.action.VIEW");
    File localFile = new File(paramString);
    if ((localFile == null) || (!localFile.exists()) || (!localFile.isFile()) || (localFile.length() <= 0L))
      return false;
    localIntent.setDataAndType(Uri.parse("file://" + paramString), "application/vnd.android.package-archive");
    localIntent.addFlags(268435456);
    paramContext.startActivity(localIntent);
    return true;
  }

  public static int installSilent(Context paramContext, String paramString)
  {
    int i = 1;
    if ((paramString == null) || (paramString.length() == 0))
    {
      i = -3;
      return i;
    }
    File localFile = new File(paramString);
    if ((localFile == null) || (localFile.length() <= 0L) || (!localFile.exists()) || (!localFile.isFile()))
      return -3;
    String str = "LD_LIBRARY_PATH=/vendor/lib:/system/lib pm install -r " + paramString.replace(" ", "\\ ");
    if (isSystemApplication(paramContext));
    ShellUtils.CommandResult localCommandResult;
    for (boolean bool = false; ; )
    {
      localCommandResult = ShellUtils.execCommand(str, bool);
      if ((localCommandResult.successMsg != null) && ((localCommandResult.successMsg.contains("Success")) || (localCommandResult.successMsg.contains("success"))))
        break;
      Log.e("PackageUtils", "installSilent successMsg:" + localCommandResult.successMsg + ", ErrorMsg:" + localCommandResult.errorMsg);
      if (localCommandResult.errorMsg != null)
      return -1000000;
    }
    label198: if (localCommandResult.errorMsg.contains("INSTALL_FAILED_ALREADY_EXISTS"))
      return -1;
    if (localCommandResult.errorMsg.contains("INSTALL_FAILED_INVALID_APK"))
      return -2;
    if (localCommandResult.errorMsg.contains("INSTALL_FAILED_INVALID_URI"))
      return -3;
    if (localCommandResult.errorMsg.contains("INSTALL_FAILED_INSUFFICIENT_STORAGE"))
      return -4;
    if (localCommandResult.errorMsg.contains("INSTALL_FAILED_DUPLICATE_PACKAGE"))
      return -5;
    if (localCommandResult.errorMsg.contains("INSTALL_FAILED_NO_SHARED_USER"))
      return -6;
    if (localCommandResult.errorMsg.contains("INSTALL_FAILED_UPDATE_INCOMPATIBLE"))
      return -7;
    if (localCommandResult.errorMsg.contains("INSTALL_FAILED_SHARED_USER_INCOMPATIBLE"))
      return -8;
    if (localCommandResult.errorMsg.contains("INSTALL_FAILED_MISSING_SHARED_LIBRARY"))
      return -9;
    if (localCommandResult.errorMsg.contains("INSTALL_FAILED_REPLACE_COULDNT_DELETE"))
      return -10;
    if (localCommandResult.errorMsg.contains("INSTALL_FAILED_DEXOPT"))
      return -11;
    if (localCommandResult.errorMsg.contains("INSTALL_FAILED_OLDER_SDK"))
      return -12;
    if (localCommandResult.errorMsg.contains("INSTALL_FAILED_CONFLICTING_PROVIDER"))
      return -13;
    if (localCommandResult.errorMsg.contains("INSTALL_FAILED_NEWER_SDK"))
      return -14;
    if (localCommandResult.errorMsg.contains("INSTALL_FAILED_TEST_ONLY"))
      return -15;
    if (localCommandResult.errorMsg.contains("INSTALL_FAILED_CPU_ABI_INCOMPATIBLE"))
      return -16;
    if (localCommandResult.errorMsg.contains("INSTALL_FAILED_MISSING_FEATURE"))
      return -17;
    if (localCommandResult.errorMsg.contains("INSTALL_FAILED_CONTAINER_ERROR"))
      return -18;
    if (localCommandResult.errorMsg.contains("INSTALL_FAILED_INVALID_INSTALL_LOCATION"))
      return -19;
    if (localCommandResult.errorMsg.contains("INSTALL_FAILED_MEDIA_UNAVAILABLE"))
      return -20;
    if (localCommandResult.errorMsg.contains("INSTALL_FAILED_VERIFICATION_TIMEOUT"))
      return -21;
    if (localCommandResult.errorMsg.contains("INSTALL_FAILED_VERIFICATION_FAILURE"))
      return -22;
    if (localCommandResult.errorMsg.contains("INSTALL_FAILED_PACKAGE_CHANGED"))
      return -23;
    if (localCommandResult.errorMsg.contains("INSTALL_FAILED_UID_CHANGED"))
      return -24;
    if (localCommandResult.errorMsg.contains("INSTALL_PARSE_FAILED_NOT_APK"))
      return -100;
    if (localCommandResult.errorMsg.contains("INSTALL_PARSE_FAILED_BAD_MANIFEST"))
      return -101;
    if (localCommandResult.errorMsg.contains("INSTALL_PARSE_FAILED_UNEXPECTED_EXCEPTION"))
      return -102;
    if (localCommandResult.errorMsg.contains("INSTALL_PARSE_FAILED_NO_CERTIFICATES"))
      return -103;
    if (localCommandResult.errorMsg.contains("INSTALL_PARSE_FAILED_INCONSISTENT_CERTIFICATES"))
      return -104;
    if (localCommandResult.errorMsg.contains("INSTALL_PARSE_FAILED_CERTIFICATE_ENCODING"))
      return -105;
    if (localCommandResult.errorMsg.contains("INSTALL_PARSE_FAILED_BAD_PACKAGE_NAME"))
      return -106;
    if (localCommandResult.errorMsg.contains("INSTALL_PARSE_FAILED_BAD_SHARED_USER_ID"))
      return -107;
    if (localCommandResult.errorMsg.contains("INSTALL_PARSE_FAILED_MANIFEST_MALFORMED"))
      return -108;
    if (localCommandResult.errorMsg.contains("INSTALL_PARSE_FAILED_MANIFEST_EMPTY"))
      return -109;
    if (localCommandResult.errorMsg.contains("INSTALL_FAILED_INTERNAL_ERROR"))
      return -110;
    return -1000000;
  }

  public static boolean isSystemApplication(Context paramContext)
  {
    if (paramContext == null)
      return false;
    return isSystemApplication(paramContext, paramContext.getPackageName());
  }

  public static boolean isSystemApplication(Context paramContext, String paramString)
  {
    if (paramContext == null)
      return false;
    return isSystemApplication(paramContext.getPackageManager(), paramString);
  }

  public static boolean isSystemApplication(PackageManager paramPackageManager, String paramString)
  {
    if ((paramPackageManager == null) || (paramString == null) || (paramString.length() == 0));
	return false;
  }

  public static Boolean isTopActivity(Context paramContext, String paramString)
  {
    if ((paramContext == null) || (StringUtils.isEmpty(paramString)));
    List localList;
    do
    {
      localList = ((ActivityManager)paramContext.getSystemService("activity")).getRunningTasks(1);
    }
    while (ListUtils.isEmpty(localList));
    try
    {
      Boolean localBoolean = Boolean.valueOf(paramString.equals(((ActivityManager.RunningTaskInfo)localList.get(0)).topActivity.getPackageName()));
      return localBoolean;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return Boolean.valueOf(false);
  }

  public static final int uninstall(Context paramContext, String paramString)
  {
    if ((isSystemApplication(paramContext)) || (ShellUtils.checkRootPermission()))
      return uninstallSilent(paramContext, paramString);
    if (uninstallNormal(paramContext, paramString))
      return 1;
    return -3;
  }

  public static boolean uninstallNormal(Context paramContext, String paramString)
  {
    if ((paramString == null) || (paramString.length() == 0))
      return false;
    Intent localIntent = new Intent("android.intent.action.DELETE", Uri.parse(32 + "package:" + paramString));
    localIntent.addFlags(268435456);
    paramContext.startActivity(localIntent);
    return true;
  }

  public static int uninstallSilent(Context paramContext, String paramString)
  {
    return uninstallSilent(paramContext, paramString, true);
  }

  public static int uninstallSilent(Context paramContext, String paramString, boolean paramBoolean)
  {
    int i = 1;
    if ((paramString == null) || (paramString.length() == 0))
    {
      i = -3;
      return i;
    }
    StringBuilder localStringBuilder = new StringBuilder().append("LD_LIBRARY_PATH=/vendor/lib:/system/lib pm uninstall");
    String str1;
    String str2 = null;
	if (paramBoolean)
    {
      str1 = " -k ";
      str2 = str1 + paramString.replace(" ", "\\ ");
      if (!isSystemApplication(paramContext));
    }
    ShellUtils.CommandResult localCommandResult;
    label179: for (boolean bool = false; ;)
    {
      localCommandResult = ShellUtils.execCommand(str2, bool);
      if ((localCommandResult.successMsg != null) && ((localCommandResult.successMsg.contains("Success")) || (localCommandResult.successMsg.contains("success"))))
        break;
      Log.e("PackageUtils", "uninstallSilent successMsg:" + localCommandResult.successMsg + ", ErrorMsg:" + localCommandResult.errorMsg);
      if (localCommandResult.errorMsg != null)
      return -1;
      str1 = " ";
    }
	return i;
  }
}

/* Location:           C:\Users\Administrator\Desktop\test111\classes_dex2jar.jar
 * Qualified Name:     cn.wangjianlog.baseframework.tools.PackageUtils
 * JD-Core Version:    0.6.2
 */