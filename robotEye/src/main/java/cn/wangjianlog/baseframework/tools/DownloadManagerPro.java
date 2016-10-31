package cn.wangjianlog.baseframework.tools;

import android.app.DownloadManager;
import android.app.DownloadManager.Query;
import android.app.DownloadManager.Request;
import android.database.Cursor;
import android.net.Uri;
import java.lang.reflect.Method;

public class DownloadManagerPro
{
  public static final String COLUMN_LOCAL_FILENAME = "local_filename";
  public static final Uri CONTENT_URI = Uri.parse("content://downloads/my_downloads");
  public static final String METHOD_NAME_PAUSE_DOWNLOAD = "pauseDownload";
  public static final String METHOD_NAME_RESUME_DOWNLOAD = "resumeDownload";
  private static boolean isInitPauseDownload = false;
  private static boolean isInitResumeDownload = false;
  private static Method pauseDownload = null;
  private static Method resumeDownload = null;
  private DownloadManager downloadManager;

  public DownloadManagerPro(DownloadManager paramDownloadManager)
  {
    this.downloadManager = paramDownloadManager;
  }

  private int getInt(long paramLong, String paramString)
  {
    DownloadManager.Query localQuery = new DownloadManager.Query().setFilterById(new long[] { paramLong });
    int i = -1;
    Cursor localCursor = null;
    try
    {
      localCursor = this.downloadManager.query(localQuery);
      if ((localCursor != null) && (localCursor.moveToFirst()))
      {
        int j = localCursor.getInt(localCursor.getColumnIndex(paramString));
        i = j;
      }
      return i;
    }
    finally
    {
      if (localCursor != null)
        localCursor.close();
    }
  }

  private String getString(long paramLong, String paramString)
  {
    DownloadManager.Query localQuery = new DownloadManager.Query().setFilterById(new long[] { paramLong });
    Cursor localCursor = null;
    try
    {
      localCursor = this.downloadManager.query(localQuery);
      Object localObject2 = null;
      if (localCursor != null)
      {
        boolean bool = localCursor.moveToFirst();
        localObject2 = null;
        if (bool)
        {
          String str = localCursor.getString(localCursor.getColumnIndex(paramString));
          localObject2 = str;
        }
      }
      return (String) localObject2;
    }
    finally
    {
      if (localCursor != null)
        localCursor.close();
    }
  }

  private static void initPauseMethod()
  {
    if (isInitPauseDownload)
      return;
    isInitPauseDownload = true;
    try
    {
//      pauseDownload = DownloadManager.class.getMethod("pauseDownload", new Class[] { [J.class });
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }

  private static void initResumeMethod()
  {
    if (isInitResumeDownload)
      return;
    isInitResumeDownload = true;
    try
    {
//      resumeDownload = DownloadManager.class.getMethod("resumeDownload", new Class[] { [J.class });
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }

  public static boolean isExistPauseAndResumeMethod()
  {
    initPauseMethod();
    initResumeMethod();
    return (pauseDownload != null) && (resumeDownload != null);
  }

  public int[] getBytesAndStatus(long paramLong)
  {
    int[] arrayOfInt = new int[3];
    arrayOfInt[0] = -1;
    arrayOfInt[1] = -1;
    DownloadManager.Query localQuery = new DownloadManager.Query().setFilterById(new long[] { paramLong });
    Cursor localCursor = null;
    try
    {
      localCursor = this.downloadManager.query(localQuery);
      if ((localCursor != null) && (localCursor.moveToFirst()))
      {
        arrayOfInt[0] = localCursor.getInt(localCursor.getColumnIndexOrThrow("bytes_so_far"));
        arrayOfInt[1] = localCursor.getInt(localCursor.getColumnIndexOrThrow("total_size"));
        arrayOfInt[2] = localCursor.getInt(localCursor.getColumnIndex("status"));
      }
      return arrayOfInt;
    }
    finally
    {
      if (localCursor != null)
        localCursor.close();
    }
  }

  public int[] getDownloadBytes(long paramLong)
  {
    int[] arrayOfInt1 = getBytesAndStatus(paramLong);
    int[] arrayOfInt2 = new int[2];
    arrayOfInt2[0] = arrayOfInt1[0];
    arrayOfInt2[1] = arrayOfInt1[1];
    return arrayOfInt2;
  }

  public int getErrorCode(long paramLong)
  {
    return getInt(paramLong, "reason");
  }

  public String getFileName(long paramLong)
  {
    return getString(paramLong, "local_filename");
  }

  public int getPausedReason(long paramLong)
  {
    return getInt(paramLong, "reason");
  }

  public int getReason(long paramLong)
  {
    return getInt(paramLong, "reason");
  }

  public int getStatusById(long paramLong)
  {
    return getInt(paramLong, "status");
  }

  public String getUri(long paramLong)
  {
    return getString(paramLong, "uri");
  }

  public int pauseDownload(long[] paramArrayOfLong)
  {
    initPauseMethod();
    if (pauseDownload == null)
      return -1;
    try
    {
      int i = ((Integer)pauseDownload.invoke(this.downloadManager, new Object[] { paramArrayOfLong })).intValue();
      return i;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return -1;
  }

  public int resumeDownload(long[] paramArrayOfLong)
  {
    initResumeMethod();
    if (resumeDownload == null)
      return -1;
    try
    {
      int i = ((Integer)resumeDownload.invoke(this.downloadManager, new Object[] { paramArrayOfLong })).intValue();
      return i;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return -1;
  }

  public static class RequestPro extends DownloadManager.Request
  {
    public static final String METHOD_NAME_SET_NOTI_CLASS = "setNotiClass";
    public static final String METHOD_NAME_SET_NOTI_EXTRAS = "setNotiExtras";
    private static boolean isInitNotiClass = false;
    private static boolean isInitNotiExtras = false;
    private static Method setNotiClass = null;
    private static Method setNotiExtras = null;

    public RequestPro(Uri paramUri)
    {
      super(paramUri);
    }

    // ERROR //
    public void setNotiClass(String paramString)
    {
      // Byte code:
      //   0: aload_0
      //   1: monitorenter
      //   2: getstatic 19	cn/wangjianlog/baseframework/tools/DownloadManagerPro$RequestPro:isInitNotiClass	Z
      //   5: ifne +26 -> 31
      //   8: iconst_1
      //   9: putstatic 19	cn/wangjianlog/baseframework/tools/DownloadManagerPro$RequestPro:isInitNotiClass	Z
      //   12: ldc 4
      //   14: ldc 8
      //   16: iconst_1
      //   17: anewarray 34	java/lang/Class
      //   20: dup
      //   21: iconst_0
      //   22: ldc 36
      //   24: aastore
      //   25: invokevirtual 40	java/lang/Class:getMethod	(Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;
      //   28: putstatic 23	cn/wangjianlog/baseframework/tools/DownloadManagerPro$RequestPro:setNotiClass	Ljava/lang/reflect/Method;
      //   31: aload_0
      //   32: monitorexit
      //   33: getstatic 23	cn/wangjianlog/baseframework/tools/DownloadManagerPro$RequestPro:setNotiClass	Ljava/lang/reflect/Method;
      //   36: ifnull +19 -> 55
      //   39: getstatic 23	cn/wangjianlog/baseframework/tools/DownloadManagerPro$RequestPro:setNotiClass	Ljava/lang/reflect/Method;
      //   42: aload_0
      //   43: iconst_1
      //   44: anewarray 42	java/lang/Object
      //   47: dup
      //   48: iconst_0
      //   49: aload_1
      //   50: aastore
      //   51: invokevirtual 48	java/lang/reflect/Method:invoke	(Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;
      //   54: pop
      //   55: return
      //   56: astore 5
      //   58: aload 5
      //   60: invokevirtual 51	java/lang/Exception:printStackTrace	()V
      //   63: goto -32 -> 31
      //   66: astore_2
      //   67: aload_0
      //   68: monitorexit
      //   69: aload_2
      //   70: athrow
      //   71: astore_3
      //   72: aload_3
      //   73: invokevirtual 51	java/lang/Exception:printStackTrace	()V
      //   76: return
      //
      // Exception table:
      //   from	to	target	type
      //   12	31	56	java/lang/Exception
      //   2	12	66	finally
      //   12	31	66	finally
      //   31	33	66	finally
      //   58	63	66	finally
      //   67	69	66	finally
      //   39	55	71	java/lang/Exception
    }

    // ERROR //
    public void setNotiExtras(String paramString)
    {
      // Byte code:
      //   0: aload_0
      //   1: monitorenter
      //   2: getstatic 21	cn/wangjianlog/baseframework/tools/DownloadManagerPro$RequestPro:isInitNotiExtras	Z
      //   5: ifne +26 -> 31
      //   8: iconst_1
      //   9: putstatic 21	cn/wangjianlog/baseframework/tools/DownloadManagerPro$RequestPro:isInitNotiExtras	Z
      //   12: ldc 4
      //   14: ldc 11
      //   16: iconst_1
      //   17: anewarray 34	java/lang/Class
      //   20: dup
      //   21: iconst_0
      //   22: ldc 36
      //   24: aastore
      //   25: invokevirtual 40	java/lang/Class:getMethod	(Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;
      //   28: putstatic 25	cn/wangjianlog/baseframework/tools/DownloadManagerPro$RequestPro:setNotiExtras	Ljava/lang/reflect/Method;
      //   31: aload_0
      //   32: monitorexit
      //   33: getstatic 25	cn/wangjianlog/baseframework/tools/DownloadManagerPro$RequestPro:setNotiExtras	Ljava/lang/reflect/Method;
      //   36: ifnull +19 -> 55
      //   39: getstatic 25	cn/wangjianlog/baseframework/tools/DownloadManagerPro$RequestPro:setNotiExtras	Ljava/lang/reflect/Method;
      //   42: aload_0
      //   43: iconst_1
      //   44: anewarray 42	java/lang/Object
      //   47: dup
      //   48: iconst_0
      //   49: aload_1
      //   50: aastore
      //   51: invokevirtual 48	java/lang/reflect/Method:invoke	(Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;
      //   54: pop
      //   55: return
      //   56: astore 5
      //   58: aload 5
      //   60: invokevirtual 51	java/lang/Exception:printStackTrace	()V
      //   63: goto -32 -> 31
      //   66: astore_2
      //   67: aload_0
      //   68: monitorexit
      //   69: aload_2
      //   70: athrow
      //   71: astore_3
      //   72: aload_3
      //   73: invokevirtual 51	java/lang/Exception:printStackTrace	()V
      //   76: return
      //
      // Exception table:
      //   from	to	target	type
      //   12	31	56	java/lang/Exception
      //   2	12	66	finally
      //   12	31	66	finally
      //   31	33	66	finally
      //   58	63	66	finally
      //   67	69	66	finally
      //   39	55	71	java/lang/Exception
    }
  }
}

/* Location:           C:\Users\Administrator\Desktop\test111\classes_dex2jar.jar
 * Qualified Name:     cn.wangjianlog.baseframework.tools.DownloadManagerPro
 * JD-Core Version:    0.6.2
 */