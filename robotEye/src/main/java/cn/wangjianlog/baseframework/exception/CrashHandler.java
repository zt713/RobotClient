package cn.wangjianlog.baseframework.exception;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

public class CrashHandler
  implements Thread.UncaughtExceptionHandler
{
  private static CrashHandler INSTANCE = new CrashHandler();
  public static final String TAG = CrashHandler.class.getSimpleName();
  private DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
  private Map<String, String> infos = new HashMap();
  private Context mContext;
  private Thread.UncaughtExceptionHandler mDefaultHandler;

  public static CrashHandler getInstance()
  {
    return INSTANCE;
  }

  private boolean handleException(Throwable paramThrowable)
  {
    if (paramThrowable == null)
      return true;
    paramThrowable.getStackTrace();
    paramThrowable.getMessage();
    collectDeviceInfo(this.mContext);
    saveCrashInfo2File(paramThrowable);
    return true;
  }

  // ERROR //
  private String saveCrashInfo2File(Throwable paramThrowable)
  {
	return null;
    // Byte code:
    //   0: new 78	java/lang/StringBuffer
    //   3: dup
    //   4: invokespecial 79	java/lang/StringBuffer:<init>	()V
    //   7: astore_2
    //   8: aload_0
    //   9: getfield 40	cn/wangjianlog/baseframework/exception/CrashHandler:infos	Ljava/util/Map;
    //   12: invokeinterface 85 1 0
    //   17: invokeinterface 91 1 0
    //   22: astore_3
    //   23: aload_3
    //   24: invokeinterface 97 1 0
    //   29: ifne +266 -> 295
    //   32: new 99	java/io/StringWriter
    //   35: dup
    //   36: invokespecial 100	java/io/StringWriter:<init>	()V
    //   39: astore 4
    //   41: new 102	java/io/PrintWriter
    //   44: dup
    //   45: aload 4
    //   47: invokespecial 105	java/io/PrintWriter:<init>	(Ljava/io/Writer;)V
    //   50: astore 5
    //   52: aload_1
    //   53: aload 5
    //   55: invokevirtual 109	java/lang/Throwable:printStackTrace	(Ljava/io/PrintWriter;)V
    //   58: aload_1
    //   59: invokevirtual 113	java/lang/Throwable:getCause	()Ljava/lang/Throwable;
    //   62: astore 6
    //   64: aload 6
    //   66: ifnonnull +302 -> 368
    //   69: aload 5
    //   71: invokevirtual 116	java/io/PrintWriter:close	()V
    //   74: aload_2
    //   75: aload 4
    //   77: invokevirtual 119	java/lang/Object:toString	()Ljava/lang/String;
    //   80: invokevirtual 123	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   83: pop
    //   84: aconst_null
    //   85: astore 8
    //   87: invokestatic 129	java/lang/System:currentTimeMillis	()J
    //   90: lstore 13
    //   92: aload_0
    //   93: getfield 49	cn/wangjianlog/baseframework/exception/CrashHandler:formatter	Ljava/text/DateFormat;
    //   96: new 131	java/util/Date
    //   99: dup
    //   100: invokespecial 132	java/util/Date:<init>	()V
    //   103: invokevirtual 138	java/text/DateFormat:format	(Ljava/util/Date;)Ljava/lang/String;
    //   106: astore 15
    //   108: new 140	java/lang/StringBuilder
    //   111: dup
    //   112: ldc 142
    //   114: invokespecial 143	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   117: aload 15
    //   119: invokevirtual 146	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   122: ldc 148
    //   124: invokevirtual 146	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   127: lload 13
    //   129: invokevirtual 151	java/lang/StringBuilder:append	(J)Ljava/lang/StringBuilder;
    //   132: ldc 153
    //   134: invokevirtual 146	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   137: invokevirtual 154	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   140: astore 16
    //   142: invokestatic 159	android/os/Environment:getExternalStorageState	()Ljava/lang/String;
    //   145: ldc 161
    //   147: invokevirtual 167	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   150: istore 17
    //   152: aconst_null
    //   153: astore 8
    //   155: iload 17
    //   157: ifeq +125 -> 282
    //   160: aload_0
    //   161: getfield 64	cn/wangjianlog/baseframework/exception/CrashHandler:mContext	Landroid/content/Context;
    //   164: invokevirtual 173	android/content/Context:getResources	()Landroid/content/res/Resources;
    //   167: ldc 174
    //   169: invokevirtual 180	android/content/res/Resources:getString	(I)Ljava/lang/String;
    //   172: astore 18
    //   174: new 140	java/lang/StringBuilder
    //   177: dup
    //   178: invokespecial 181	java/lang/StringBuilder:<init>	()V
    //   181: invokestatic 185	android/os/Environment:getExternalStorageDirectory	()Ljava/io/File;
    //   184: invokevirtual 188	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   187: ldc 190
    //   189: invokevirtual 146	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   192: aload 18
    //   194: invokevirtual 146	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   197: ldc 192
    //   199: invokevirtual 146	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   202: invokevirtual 154	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   205: astore 19
    //   207: new 194	java/io/File
    //   210: dup
    //   211: aload 19
    //   213: invokespecial 195	java/io/File:<init>	(Ljava/lang/String;)V
    //   216: astore 20
    //   218: aload 20
    //   220: invokevirtual 198	java/io/File:exists	()Z
    //   223: ifne +9 -> 232
    //   226: aload 20
    //   228: invokevirtual 201	java/io/File:mkdirs	()Z
    //   231: pop
    //   232: new 203	java/io/FileOutputStream
    //   235: dup
    //   236: new 140	java/lang/StringBuilder
    //   239: dup
    //   240: aload 19
    //   242: invokestatic 207	java/lang/String:valueOf	(Ljava/lang/Object;)Ljava/lang/String;
    //   245: invokespecial 143	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   248: aload 16
    //   250: invokevirtual 146	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   253: invokevirtual 154	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   256: invokespecial 208	java/io/FileOutputStream:<init>	(Ljava/lang/String;)V
    //   259: astore 21
    //   261: aload 21
    //   263: aload_2
    //   264: invokevirtual 209	java/lang/StringBuffer:toString	()Ljava/lang/String;
    //   267: invokevirtual 213	java/lang/String:getBytes	()[B
    //   270: invokevirtual 217	java/io/FileOutputStream:write	([B)V
    //   273: aload 21
    //   275: invokevirtual 218	java/io/FileOutputStream:close	()V
    //   278: aload 21
    //   280: astore 8
    //   282: aload 8
    //   284: ifnull +8 -> 292
    //   287: aload 8
    //   289: invokevirtual 218	java/io/FileOutputStream:close	()V
    //   292: aload 16
    //   294: areturn
    //   295: aload_3
    //   296: invokeinterface 222 1 0
    //   301: checkcast 224	java/util/Map$Entry
    //   304: astore 25
    //   306: aload 25
    //   308: invokeinterface 227 1 0
    //   313: checkcast 163	java/lang/String
    //   316: astore 26
    //   318: aload 25
    //   320: invokeinterface 230 1 0
    //   325: checkcast 163	java/lang/String
    //   328: astore 27
    //   330: aload_2
    //   331: new 140	java/lang/StringBuilder
    //   334: dup
    //   335: aload 26
    //   337: invokestatic 207	java/lang/String:valueOf	(Ljava/lang/Object;)Ljava/lang/String;
    //   340: invokespecial 143	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   343: ldc 232
    //   345: invokevirtual 146	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   348: aload 27
    //   350: invokevirtual 146	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   353: ldc 234
    //   355: invokevirtual 146	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   358: invokevirtual 154	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   361: invokevirtual 123	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   364: pop
    //   365: goto -342 -> 23
    //   368: aload 6
    //   370: aload 5
    //   372: invokevirtual 109	java/lang/Throwable:printStackTrace	(Ljava/io/PrintWriter;)V
    //   375: aload 6
    //   377: invokevirtual 113	java/lang/Throwable:getCause	()Ljava/lang/Throwable;
    //   380: astore 6
    //   382: goto -318 -> 64
    //   385: astore 23
    //   387: aload 23
    //   389: invokevirtual 236	java/io/IOException:printStackTrace	()V
    //   392: aload 16
    //   394: areturn
    //   395: astore 11
    //   397: aload 8
    //   399: ifnull +8 -> 407
    //   402: aload 8
    //   404: invokevirtual 218	java/io/FileOutputStream:close	()V
    //   407: aconst_null
    //   408: areturn
    //   409: astore 12
    //   411: aload 12
    //   413: invokevirtual 236	java/io/IOException:printStackTrace	()V
    //   416: goto -9 -> 407
    //   419: astore 9
    //   421: aload 8
    //   423: ifnull +8 -> 431
    //   426: aload 8
    //   428: invokevirtual 218	java/io/FileOutputStream:close	()V
    //   431: aload 9
    //   433: athrow
    //   434: astore 10
    //   436: aload 10
    //   438: invokevirtual 236	java/io/IOException:printStackTrace	()V
    //   441: goto -10 -> 431
    //   444: astore 9
    //   446: aload 21
    //   448: astore 8
    //   450: goto -29 -> 421
    //   453: astore 22
    //   455: aload 21
    //   457: astore 8
    //   459: goto -62 -> 397
    //
    // Exception table:
    //   from	to	target	type
    //   287	292	385	java/io/IOException
    //   87	152	395	java/lang/Exception
    //   160	232	395	java/lang/Exception
    //   232	261	395	java/lang/Exception
    //   402	407	409	java/io/IOException
    //   87	152	419	finally
    //   160	232	419	finally
    //   232	261	419	finally
    //   426	431	434	java/io/IOException
    //   261	278	444	finally
    //   261	278	453	java/lang/Exception
  }

  public void collectDeviceInfo(Context paramContext)
  {
    try
    {
      PackageInfo localPackageInfo = paramContext.getPackageManager().getPackageInfo(paramContext.getPackageName(), 1);
      String str1;
      int i;
      int j;
      if (localPackageInfo != null)
      {
        if (localPackageInfo.versionName == null)
        {
          str1 = "null";
          String str2 = localPackageInfo.versionCode+"";
          this.infos.put("versionName", str1);
          this.infos.put("versionCode", str2);
        }
      }
      else
      {
//        arrayOfField = Build.class.getDeclaredFields();
//        i = arrayOfField.length;
        j = 0;
      }
      while (true)
      {
//        if (j >= i)
//        {
//          return;
//          str1 = localPackageInfo.versionName;
//          break;
//        }
//        Field localField = arrayOfField[j];
//        try
//        {
//          localField.setAccessible(true);
//          this.infos.put(localField.getName(), localField.get(null).toString());
//          label150: j++;
//        }
//        catch (Exception localException)
//        {
//          break label150;
//        }
      }
    }
    catch (PackageManager.NameNotFoundException localNameNotFoundException)
    {
//      break label82;
    }
  }

  public void init(Context paramContext)
  {
    this.mContext = paramContext;
    this.mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
    Thread.setDefaultUncaughtExceptionHandler(this);
  }

  public void uncaughtException(Thread paramThread, Throwable paramThrowable)
  {
    if ((!handleException(paramThrowable)) && (this.mDefaultHandler != null))
    {
      this.mDefaultHandler.uncaughtException(paramThread, paramThrowable);
      return;
    }
    try
    {
      Thread.sleep(3000L);
      return;
    }
    catch (InterruptedException localInterruptedException)
    {
    }
  }
}

/* Location:           C:\Users\Administrator\Desktop\test111\classes_dex2jar.jar
 * Qualified Name:     cn.wangjianlog.baseframework.exception.CrashHandler
 * JD-Core Version:    0.6.2
 */