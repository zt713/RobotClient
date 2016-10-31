package cn.wangjianlog.baseframework.tools;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class FileUtils
{
  public static final String FILE_EXTENSION_SEPARATOR = ".";
  private static byte[] b = new byte[5120];

  public static void copyFile(String paramString1, String paramString2)
  {
    try
    {
      if (new File(paramString1).exists())
      {
        FileInputStream localFileInputStream = new FileInputStream(paramString1);
        FileOutputStream localFileOutputStream = new FileOutputStream(paramString2);
        byte[] arrayOfByte = new byte[1444];
        while (true)
        {
          int i = localFileInputStream.read(arrayOfByte);
          if (i == -1)
          {
            localFileInputStream.close();
            return;
          }
          localFileOutputStream.write(arrayOfByte, 0, i);
        }
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }

  public static void copyFolder(String paramString1, String paramString2)
  {
    while (true)
    {
      int i;
      try
      {
        new File(paramString2).mkdirs();
        String[] arrayOfString = new File(paramString1).list();
        i = 0;
        if (i >= arrayOfString.length)
          return;
        File localFile;
        FileOutputStream localFileOutputStream = null;
        int j = 0;
        if (paramString1.endsWith(File.separator))
        {
          localFile = new File(paramString1 + arrayOfString[i]);
          if (localFile.isFile())
          {
            FileInputStream localFileInputStream = new FileInputStream(localFile);
            localFileOutputStream = new FileOutputStream(paramString2 + "/" + localFile.getName().toString());
            j = localFileInputStream.read(b);
            if (j == -1)
            {
              localFileOutputStream.flush();
              localFileOutputStream.close();
              localFileInputStream.close();
            }
          }
          else
          {
            if (!localFile.isDirectory())
            copyFolder(paramString1 + "/" + arrayOfString[i], paramString2 + "/" + arrayOfString[i]);
          }
        }
        else
        {
          localFile = new File(paramString1 + File.separator + arrayOfString[i]);
          continue;
        }
        localFileOutputStream.write(b, 0, j);
        continue;
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
        return;
      }
    }
  }

  public static void deleteDir(String paramString)
  {
    File localFile = new File(paramString);
    File[] arrayOfFile = null;
    int i;
    if (localFile.exists())
    {
      if (!localFile.isDirectory())
      arrayOfFile = localFile.listFiles();
      i = 0;
      if (i >= arrayOfFile.length)
        localFile.delete();
    }
    else
    {
      return;
    }
    if (arrayOfFile[i].isFile())
      arrayOfFile[i].delete();
    while (true)
    {
      i++;
      deleteDir(arrayOfFile[i].getAbsolutePath());
    }
  }

  public static boolean deleteFile(String paramString)
  {
    int i = 0;
    if (StringUtils.isBlank(paramString));
    File localFile1;
    do
    {
      localFile1 = new File(paramString);
    }
    while (!localFile1.exists());
    if (localFile1.isFile())
      return localFile1.delete();
    if (!localFile1.isDirectory())
      return false;
    File[] arrayOfFile = localFile1.listFiles();
    int j = arrayOfFile.length;
    if (i >= j)
      return localFile1.delete();
    File localFile2 = arrayOfFile[i];
    if (localFile2.isFile())
      localFile2.delete();
    while (true)
    {
      i++;
      if (localFile2.isDirectory())
        deleteFile(localFile2.getAbsolutePath());
    }
  }

  public static String getFileName(String paramString)
  {
    if (StringUtils.isEmpty(paramString));
    int i;
    do
    {
      i = paramString.lastIndexOf(File.separator);
    }
    while (i == -1);
    return paramString.substring(i + 1);
  }

  public static String getFileNameWithoutExtension(String paramString)
  {
    if (StringUtils.isEmpty(paramString));
    int i;
    int j;
    do
    {
      i = paramString.lastIndexOf(".");
      j = paramString.lastIndexOf(File.separator);
      if (j != -1)
        break;
    }
    while (i == -1);
    return paramString.substring(0, i);
  }

  public static long getFileSize(String paramString)
  {
    if (StringUtils.isBlank(paramString));
    File localFile;
    do
    {
      localFile = new File(paramString);
    }
    while ((!localFile.exists()) || (!localFile.isFile()));
    return localFile.length();
  }

  public static String getFolderName(String paramString)
  {
    if (StringUtils.isEmpty(paramString))
      return paramString;
    int i = paramString.lastIndexOf(File.separator);
    if (i == -1);
    for (String str = ""; ; str = paramString.substring(0, i))
      return str;
  }

  public static boolean isFileExist(String paramString)
  {
    if (StringUtils.isBlank(paramString));
    File localFile;
    do
    {
      localFile = new File(paramString);
    }
    while ((!localFile.exists()) || (!localFile.isFile()));
    return true;
  }

  public static boolean isFolderExist(String paramString)
  {
    if (StringUtils.isBlank(paramString));
    File localFile;
    do
    {
      localFile = new File(paramString);
    }
    while ((!localFile.exists()) || (!localFile.isDirectory()));
    return true;
  }

  public static boolean makeDirs(String paramString)
  {
    String str = getFolderName(paramString);
    if (StringUtils.isEmpty(str))
      return false;
    File localFile = new File(str);
    if ((localFile.exists()) && (localFile.isDirectory()))
      return true;
    return localFile.mkdirs();
  }

  public static boolean makeFolders(String paramString)
  {
    return makeDirs(paramString);
  }

  public static String makeZip(String paramString1, String paramString2, String paramString3)
  {
    try
    {
      File localFile1 = new File(paramString1);
      String str1 = paramString2 + File.separator;
      String str2 = paramString2 + File.separator + paramString3 + ".zip";
      File localFile2 = new File(str1);
      if (!localFile2.exists())
        localFile2.mkdirs();
      File localFile3 = new File(str2);
      if (localFile3.exists())
        localFile3.delete();
      ZipOutputStream localZipOutputStream = new ZipOutputStream(new DataOutputStream(new FileOutputStream(str2)));
      String str3 = zipFile(localFile1, str1, localZipOutputStream, "");
      localZipOutputStream.close();
      return str3;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return null;
  }

  // ERROR //
  public static java.lang.StringBuilder readFile(String paramString1, String paramString2)
  {
	return null;
    // Byte code:
    //   0: new 23	java/io/File
    //   3: dup
    //   4: aload_0
    //   5: invokespecial 26	java/io/File:<init>	(Ljava/lang/String;)V
    //   8: astore_2
    //   9: new 71	java/lang/StringBuilder
    //   12: dup
    //   13: ldc 156
    //   15: invokespecial 76	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   18: astore_3
    //   19: aload_2
    //   20: ifnull +10 -> 30
    //   23: aload_2
    //   24: invokevirtual 87	java/io/File:isFile	()Z
    //   27: ifne +7 -> 34
    //   30: aconst_null
    //   31: astore_3
    //   32: aload_3
    //   33: areturn
    //   34: aconst_null
    //   35: astore 4
    //   37: new 187	java/io/BufferedReader
    //   40: dup
    //   41: new 189	java/io/InputStreamReader
    //   44: dup
    //   45: new 32	java/io/FileInputStream
    //   48: dup
    //   49: aload_2
    //   50: invokespecial 90	java/io/FileInputStream:<init>	(Ljava/io/File;)V
    //   53: aload_1
    //   54: invokespecial 192	java/io/InputStreamReader:<init>	(Ljava/io/InputStream;Ljava/lang/String;)V
    //   57: invokespecial 195	java/io/BufferedReader:<init>	(Ljava/io/Reader;)V
    //   60: astore 5
    //   62: aload 5
    //   64: invokevirtual 198	java/io/BufferedReader:readLine	()Ljava/lang/String;
    //   67: astore 9
    //   69: aload 9
    //   71: ifnonnull +34 -> 105
    //   74: aload 5
    //   76: invokevirtual 199	java/io/BufferedReader:close	()V
    //   79: aload 5
    //   81: ifnull -49 -> 32
    //   84: aload 5
    //   86: invokevirtual 199	java/io/BufferedReader:close	()V
    //   89: aload_3
    //   90: areturn
    //   91: astore 10
    //   93: new 201	java/lang/RuntimeException
    //   96: dup
    //   97: ldc 203
    //   99: aload 10
    //   101: invokespecial 206	java/lang/RuntimeException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   104: athrow
    //   105: aload_3
    //   106: invokevirtual 84	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   109: ldc 156
    //   111: invokevirtual 210	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   114: ifne +10 -> 124
    //   117: aload_3
    //   118: ldc 212
    //   120: invokevirtual 80	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   123: pop
    //   124: aload_3
    //   125: aload 9
    //   127: invokevirtual 80	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   130: pop
    //   131: goto -69 -> 62
    //   134: astore 8
    //   136: aload 5
    //   138: astore 4
    //   140: new 201	java/lang/RuntimeException
    //   143: dup
    //   144: ldc 203
    //   146: aload 8
    //   148: invokespecial 206	java/lang/RuntimeException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   151: athrow
    //   152: astore 6
    //   154: aload 4
    //   156: ifnull +8 -> 164
    //   159: aload 4
    //   161: invokevirtual 199	java/io/BufferedReader:close	()V
    //   164: aload 6
    //   166: athrow
    //   167: astore 7
    //   169: new 201	java/lang/RuntimeException
    //   172: dup
    //   173: ldc 203
    //   175: aload 7
    //   177: invokespecial 206	java/lang/RuntimeException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   180: athrow
    //   181: astore 6
    //   183: aload 5
    //   185: astore 4
    //   187: goto -33 -> 154
    //   190: astore 8
    //   192: aconst_null
    //   193: astore 4
    //   195: goto -55 -> 140
    //
    // Exception table:
    //   from	to	target	type
    //   84	89	91	java/io/IOException
    //   62	69	134	java/io/IOException
    //   74	79	134	java/io/IOException
    //   105	124	134	java/io/IOException
    //   124	131	134	java/io/IOException
    //   37	62	152	finally
    //   140	152	152	finally
    //   159	164	167	java/io/IOException
    //   62	69	181	finally
    //   74	79	181	finally
    //   105	124	181	finally
    //   124	131	181	finally
    //   37	62	190	java/io/IOException
  }

  // ERROR //
  public static java.util.List<String> readFileToList(String paramString1, String paramString2)
  {
	return null;
    // Byte code:
    //   0: new 23	java/io/File
    //   3: dup
    //   4: aload_0
    //   5: invokespecial 26	java/io/File:<init>	(Ljava/lang/String;)V
    //   8: astore_2
    //   9: new 216	java/util/ArrayList
    //   12: dup
    //   13: invokespecial 217	java/util/ArrayList:<init>	()V
    //   16: astore_3
    //   17: aload_2
    //   18: ifnull +10 -> 28
    //   21: aload_2
    //   22: invokevirtual 87	java/io/File:isFile	()Z
    //   25: ifne +7 -> 32
    //   28: aconst_null
    //   29: astore_3
    //   30: aload_3
    //   31: areturn
    //   32: aconst_null
    //   33: astore 4
    //   35: new 187	java/io/BufferedReader
    //   38: dup
    //   39: new 189	java/io/InputStreamReader
    //   42: dup
    //   43: new 32	java/io/FileInputStream
    //   46: dup
    //   47: aload_2
    //   48: invokespecial 90	java/io/FileInputStream:<init>	(Ljava/io/File;)V
    //   51: aload_1
    //   52: invokespecial 192	java/io/InputStreamReader:<init>	(Ljava/io/InputStream;Ljava/lang/String;)V
    //   55: invokespecial 195	java/io/BufferedReader:<init>	(Ljava/io/Reader;)V
    //   58: astore 5
    //   60: aload 5
    //   62: invokevirtual 198	java/io/BufferedReader:readLine	()Ljava/lang/String;
    //   65: astore 9
    //   67: aload 9
    //   69: ifnonnull +34 -> 103
    //   72: aload 5
    //   74: invokevirtual 199	java/io/BufferedReader:close	()V
    //   77: aload 5
    //   79: ifnull -49 -> 30
    //   82: aload 5
    //   84: invokevirtual 199	java/io/BufferedReader:close	()V
    //   87: aload_3
    //   88: areturn
    //   89: astore 10
    //   91: new 201	java/lang/RuntimeException
    //   94: dup
    //   95: ldc 203
    //   97: aload 10
    //   99: invokespecial 206	java/lang/RuntimeException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   102: athrow
    //   103: aload_3
    //   104: aload 9
    //   106: invokeinterface 222 2 0
    //   111: pop
    //   112: goto -52 -> 60
    //   115: astore 8
    //   117: aload 5
    //   119: astore 4
    //   121: new 201	java/lang/RuntimeException
    //   124: dup
    //   125: ldc 203
    //   127: aload 8
    //   129: invokespecial 206	java/lang/RuntimeException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   132: athrow
    //   133: astore 6
    //   135: aload 4
    //   137: ifnull +8 -> 145
    //   140: aload 4
    //   142: invokevirtual 199	java/io/BufferedReader:close	()V
    //   145: aload 6
    //   147: athrow
    //   148: astore 7
    //   150: new 201	java/lang/RuntimeException
    //   153: dup
    //   154: ldc 203
    //   156: aload 7
    //   158: invokespecial 206	java/lang/RuntimeException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   161: athrow
    //   162: astore 6
    //   164: aload 5
    //   166: astore 4
    //   168: goto -33 -> 135
    //   171: astore 8
    //   173: aconst_null
    //   174: astore 4
    //   176: goto -55 -> 121
    //
    // Exception table:
    //   from	to	target	type
    //   82	87	89	java/io/IOException
    //   60	67	115	java/io/IOException
    //   72	77	115	java/io/IOException
    //   103	112	115	java/io/IOException
    //   35	60	133	finally
    //   121	133	133	finally
    //   140	145	148	java/io/IOException
    //   60	67	162	finally
    //   72	77	162	finally
    //   103	112	162	finally
    //   35	60	171	java/io/IOException
  }

  public static void unzip(File paramFile, String paramString)
    throws Exception
  {
    File localFile1 = new File(paramString);
    if (!localFile1.exists())
      localFile1.mkdirs();
    BufferedInputStream localBufferedInputStream = new BufferedInputStream(new FileInputStream(paramFile));
    ZipInputStream localZipInputStream = new ZipInputStream(localBufferedInputStream);
    byte[] arrayOfByte = new byte[10240];
    ZipEntry localZipEntry;
    while (true)
    {
      localZipEntry = localZipInputStream.getNextEntry();
      if (localZipEntry == null)
      {
        localZipInputStream.close();
        localBufferedInputStream.close();
        paramFile.delete();
        return;
      }
      if (!localZipEntry.isDirectory())
        break;
      String str1 = localZipEntry.getName();
      String str2 = str1.substring(0, -1 + str1.length());
      new File(paramString + File.separator + str2).mkdir();
    }
    File localFile2 = new File(paramString + localZipEntry.getName());
    File localFile3 = new File(localFile2.getParent());
    if (!localFile3.exists())
      localFile3.mkdirs();
    localFile2.deleteOnExit();
    FileOutputStream localFileOutputStream = new FileOutputStream(localFile2);
    BufferedOutputStream localBufferedOutputStream = new BufferedOutputStream(localFileOutputStream, 10240);
    while (true)
    {
      int i = localZipInputStream.read(arrayOfByte, 0, 10240);
      if (i == -1)
      {
        localBufferedOutputStream.flush();
        localBufferedOutputStream.close();
        localFileOutputStream.close();
        localZipInputStream.closeEntry();
        break;
      }
      localBufferedOutputStream.write(arrayOfByte, 0, i);
    }
  }

  public static void unzip(InputStream paramInputStream, String paramString)
    throws Exception
  {
    File localFile1 = new File(paramString);
    if (!localFile1.exists())
      localFile1.mkdirs();
    BufferedInputStream localBufferedInputStream = new BufferedInputStream(paramInputStream);
    ZipInputStream localZipInputStream = new ZipInputStream(localBufferedInputStream);
    byte[] arrayOfByte = new byte[10240];
    ZipEntry localZipEntry;
    while (true)
    {
      localZipEntry = localZipInputStream.getNextEntry();
      if (localZipEntry == null)
      {
        localZipInputStream.close();
        localBufferedInputStream.close();
        return;
      }
      if (!localZipEntry.isDirectory())
        break;
      String str1 = localZipEntry.getName();
      String str2 = str1.substring(0, -1 + str1.length());
      new File(paramString + File.separator + str2).mkdir();
    }
    File localFile2 = new File(paramString + localZipEntry.getName());
    localFile2.deleteOnExit();
    FileOutputStream localFileOutputStream = new FileOutputStream(localFile2);
    BufferedOutputStream localBufferedOutputStream = new BufferedOutputStream(localFileOutputStream, 10240);
    while (true)
    {
      int i = localZipInputStream.read(arrayOfByte, 0, 10240);
      if (i == -1)
      {
        localBufferedOutputStream.flush();
        localBufferedOutputStream.close();
        localFileOutputStream.close();
        localZipInputStream.closeEntry();
        break;
      }
      localBufferedOutputStream.write(arrayOfByte, 0, i);
    }
  }

  // ERROR //
  public static boolean writeFile(String paramString, InputStream paramInputStream)
  {
	return false;
    // Byte code:
    //   0: aconst_null
    //   1: astore_2
    //   2: new 35	java/io/FileOutputStream
    //   5: dup
    //   6: aload_0
    //   7: invokespecial 36	java/io/FileOutputStream:<init>	(Ljava/lang/String;)V
    //   10: astore_3
    //   11: sipush 1024
    //   14: newarray byte
    //   16: astore 8
    //   18: aload_1
    //   19: aload 8
    //   21: invokevirtual 42	java/io/InputStream:read	([B)I
    //   24: istore 9
    //   26: iload 9
    //   28: iconst_m1
    //   29: if_icmpne +21 -> 50
    //   32: aload_3
    //   33: invokevirtual 276	java/io/OutputStream:flush	()V
    //   36: aload_3
    //   37: ifnull +11 -> 48
    //   40: aload_3
    //   41: invokevirtual 277	java/io/OutputStream:close	()V
    //   44: aload_1
    //   45: invokevirtual 45	java/io/InputStream:close	()V
    //   48: iconst_1
    //   49: ireturn
    //   50: aload_3
    //   51: aload 8
    //   53: iconst_0
    //   54: iload 9
    //   56: invokevirtual 278	java/io/OutputStream:write	([BII)V
    //   59: goto -41 -> 18
    //   62: astore 7
    //   64: aload_3
    //   65: astore_2
    //   66: new 201	java/lang/RuntimeException
    //   69: dup
    //   70: ldc_w 280
    //   73: aload 7
    //   75: invokespecial 206	java/lang/RuntimeException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   78: athrow
    //   79: astore 5
    //   81: aload_2
    //   82: ifnull +11 -> 93
    //   85: aload_2
    //   86: invokevirtual 277	java/io/OutputStream:close	()V
    //   89: aload_1
    //   90: invokevirtual 45	java/io/InputStream:close	()V
    //   93: aload 5
    //   95: athrow
    //   96: astore 10
    //   98: new 201	java/lang/RuntimeException
    //   101: dup
    //   102: ldc 203
    //   104: aload 10
    //   106: invokespecial 206	java/lang/RuntimeException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   109: athrow
    //   110: astore 4
    //   112: new 201	java/lang/RuntimeException
    //   115: dup
    //   116: ldc 203
    //   118: aload 4
    //   120: invokespecial 206	java/lang/RuntimeException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   123: athrow
    //   124: astore 6
    //   126: new 201	java/lang/RuntimeException
    //   129: dup
    //   130: ldc 203
    //   132: aload 6
    //   134: invokespecial 206	java/lang/RuntimeException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   137: athrow
    //   138: astore 5
    //   140: aload_3
    //   141: astore_2
    //   142: goto -61 -> 81
    //   145: astore 4
    //   147: aload_3
    //   148: astore_2
    //   149: goto -37 -> 112
    //   152: astore 7
    //   154: aconst_null
    //   155: astore_2
    //   156: goto -90 -> 66
    //
    // Exception table:
    //   from	to	target	type
    //   11	18	62	java/io/FileNotFoundException
    //   18	26	62	java/io/FileNotFoundException
    //   32	36	62	java/io/FileNotFoundException
    //   50	59	62	java/io/FileNotFoundException
    //   2	11	79	finally
    //   66	79	79	finally
    //   112	124	79	finally
    //   40	48	96	java/io/IOException
    //   2	11	110	java/io/IOException
    //   85	93	124	java/io/IOException
    //   11	18	138	finally
    //   18	26	138	finally
    //   32	36	138	finally
    //   50	59	138	finally
    //   11	18	145	java/io/IOException
    //   18	26	145	java/io/IOException
    //   32	36	145	java/io/IOException
    //   50	59	145	java/io/IOException
    //   2	11	152	java/io/FileNotFoundException
  }

  // ERROR //
  public static boolean writeFile(String paramString1, String paramString2, boolean paramBoolean)
  {
	return paramBoolean;
    // Byte code:
    //   0: aconst_null
    //   1: astore_3
    //   2: new 283	java/io/FileWriter
    //   5: dup
    //   6: aload_0
    //   7: iload_2
    //   8: invokespecial 286	java/io/FileWriter:<init>	(Ljava/lang/String;Z)V
    //   11: astore 4
    //   13: aload 4
    //   15: aload_1
    //   16: invokevirtual 288	java/io/FileWriter:write	(Ljava/lang/String;)V
    //   19: aload 4
    //   21: invokevirtual 289	java/io/FileWriter:close	()V
    //   24: aload 4
    //   26: ifnull +8 -> 34
    //   29: aload 4
    //   31: invokevirtual 289	java/io/FileWriter:close	()V
    //   34: iconst_1
    //   35: ireturn
    //   36: astore 8
    //   38: new 201	java/lang/RuntimeException
    //   41: dup
    //   42: ldc 203
    //   44: aload 8
    //   46: invokespecial 206	java/lang/RuntimeException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   49: athrow
    //   50: astore 5
    //   52: new 201	java/lang/RuntimeException
    //   55: dup
    //   56: ldc 203
    //   58: aload 5
    //   60: invokespecial 206	java/lang/RuntimeException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   63: athrow
    //   64: astore 6
    //   66: aload_3
    //   67: ifnull +7 -> 74
    //   70: aload_3
    //   71: invokevirtual 289	java/io/FileWriter:close	()V
    //   74: aload 6
    //   76: athrow
    //   77: astore 7
    //   79: new 201	java/lang/RuntimeException
    //   82: dup
    //   83: ldc 203
    //   85: aload 7
    //   87: invokespecial 206	java/lang/RuntimeException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   90: athrow
    //   91: astore 6
    //   93: aload 4
    //   95: astore_3
    //   96: goto -30 -> 66
    //   99: astore 5
    //   101: aload 4
    //   103: astore_3
    //   104: goto -52 -> 52
    //
    // Exception table:
    //   from	to	target	type
    //   29	34	36	java/io/IOException
    //   2	13	50	java/io/IOException
    //   2	13	64	finally
    //   52	64	64	finally
    //   70	74	77	java/io/IOException
    //   13	24	91	finally
    //   13	24	99	java/io/IOException
  }

  private static String zipFile(File paramFile, String paramString1, ZipOutputStream paramZipOutputStream, String paramString2)
    throws FileNotFoundException, IOException
  {
    if (paramFile.exists())
    {
      if (paramFile.isDirectory())
      {
        File[] arrayOfFile = paramFile.listFiles();
        paramZipOutputStream.putNextEntry(new ZipEntry(paramString2 + "/"));
        String str1 = null;
        if (paramString2.length() == 0)
          str1 = "";
        for (int j = 0; ; j++)
        {
          if (j >= arrayOfFile.length)
          {
            str1 = paramString2 + "/";
            break;
          }
          String str2 = arrayOfFile[j].getName();
          zipFile(arrayOfFile[j], paramString1, paramZipOutputStream, str1 + str2);
        }
      }
      paramZipOutputStream.putNextEntry(new ZipEntry(paramString2));
      FileInputStream localFileInputStream = new FileInputStream(paramFile);
      byte[] arrayOfByte = new byte[1048576];
      while (true)
      {
        int i = localFileInputStream.read(arrayOfByte);
        if (i == -1)
        {
          localFileInputStream.close();
          return paramString1;
        }
        paramZipOutputStream.write(arrayOfByte, 0, i);
        paramZipOutputStream.flush();
      }
    }
    return null;
  }
}

/* Location:           C:\Users\Administrator\Desktop\test111\classes_dex2jar.jar
 * Qualified Name:     cn.wangjianlog.baseframework.tools.FileUtils
 * JD-Core Version:    0.6.2
 */