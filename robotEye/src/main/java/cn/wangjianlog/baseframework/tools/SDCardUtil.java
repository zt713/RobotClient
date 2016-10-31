package cn.wangjianlog.baseframework.tools;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Environment;
import android.os.StatFs;
import android.widget.Toast;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.channels.FileChannel;

@SuppressLint({"SdCardPath"})
public class SDCardUtil
{
  public static long minSizeSDcard = 50L;

  public static boolean checkSDState(Context paramContext)
  {
    return Environment.getExternalStorageState().equals("mounted");
  }

  public static void copyDatabase2SD(Context paramContext, String paramString)
  {
    File localFile = new File("/data/data/" + paramContext.getPackageName() + "/databases/" + paramString);
    try
    {
      FileInputStream localFileInputStream = new FileInputStream(localFile);
      FileChannel localFileChannel = localFileInputStream.getChannel();
      FileOutputStream localFileOutputStream = new FileOutputStream(new File("/sdcard/" + paramString));
      localFileOutputStream.getChannel().transferFrom(localFileChannel, 0L, localFileChannel.size());
      localFileInputStream.close();
      localFileOutputStream.close();
      return;
    }
    catch (FileNotFoundException localFileNotFoundException)
    {
      localFileNotFoundException.printStackTrace();
      return;
    }
    catch (IOException localIOException)
    {
      localIOException.printStackTrace();
    }
  }

  public static boolean getAvailableSDcard(Context paramContext)
  {
    boolean bool = Environment.getExternalStorageState().equals("mounted");
    System.out.println("+++" + bool);
    if (bool)
    {
      StatFs localStatFs = new StatFs(Environment.getExternalStorageDirectory().getPath());
      if (localStatFs.getBlockSize() * localStatFs.getAvailableBlocks() / 1024L > minSizeSDcard)
      {
        System.out.println("SDcardSize:::" + minSizeSDcard + "KB");
        return true;
      }
      Toast.makeText(paramContext, "SD卡空间不足", 0).show();
      return false;
    }
    Toast.makeText(paramContext, "请在使用转发功能之前插入SD卡", 0).show();
    return false;
  }

  public static File getPhoneCache(Context paramContext)
  {
    return paramContext.getCacheDir();
  }

  public static File getPhoneCustom(Context paramContext, String paramString)
  {
    return paramContext.getDir(paramString, 0);
  }

  public static File getPhoneFiles(Context paramContext)
  {
    return paramContext.getFilesDir();
  }

  public static long getSDAvailableSize()
  {
    if (Environment.getExternalStorageState().equals("mounted"))
    {
      StatFs localStatFs = new StatFs(Environment.getExternalStorageDirectory().getPath());
      return localStatFs.getBlockSize() * localStatFs.getAvailableBlocks();
    }
    return 0L;
  }

  public static String getSDPath()
  {
    return Environment.getExternalStorageDirectory() + "/";
  }
}

/* Location:           C:\Users\Administrator\Desktop\test111\classes_dex2jar.jar
 * Qualified Name:     cn.wangjianlog.baseframework.tools.SDCardUtil
 * JD-Core Version:    0.6.2
 */