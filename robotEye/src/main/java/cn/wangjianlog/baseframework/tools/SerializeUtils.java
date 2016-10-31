package cn.wangjianlog.baseframework.tools;

public class SerializeUtils
{
  // ERROR //
  public static Object deserialization(java.lang.String paramString)
  {
	return paramString;
    // Byte code:
    //   0: aconst_null
    //   1: astore_1
    //   2: new 18	java/io/ObjectInputStream
    //   5: dup
    //   6: new 20	java/io/FileInputStream
    //   9: dup
    //   10: aload_0
    //   11: invokespecial 23	java/io/FileInputStream:<init>	(Ljava/lang/String;)V
    //   14: invokespecial 26	java/io/ObjectInputStream:<init>	(Ljava/io/InputStream;)V
    //   17: astore_2
    //   18: aload_2
    //   19: invokevirtual 30	java/io/ObjectInputStream:readObject	()Ljava/lang/Object;
    //   22: astore 8
    //   24: aload_2
    //   25: invokevirtual 33	java/io/ObjectInputStream:close	()V
    //   28: aload_2
    //   29: ifnull +7 -> 36
    //   32: aload_2
    //   33: invokevirtual 33	java/io/ObjectInputStream:close	()V
    //   36: aload 8
    //   38: areturn
    //   39: astore 9
    //   41: new 35	java/lang/RuntimeException
    //   44: dup
    //   45: ldc 37
    //   47: aload 9
    //   49: invokespecial 40	java/lang/RuntimeException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   52: athrow
    //   53: astore_3
    //   54: new 35	java/lang/RuntimeException
    //   57: dup
    //   58: ldc 42
    //   60: aload_3
    //   61: invokespecial 40	java/lang/RuntimeException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   64: athrow
    //   65: astore 4
    //   67: aload_1
    //   68: ifnull +7 -> 75
    //   71: aload_1
    //   72: invokevirtual 33	java/io/ObjectInputStream:close	()V
    //   75: aload 4
    //   77: athrow
    //   78: astore 6
    //   80: new 35	java/lang/RuntimeException
    //   83: dup
    //   84: ldc 44
    //   86: aload 6
    //   88: invokespecial 40	java/lang/RuntimeException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   91: athrow
    //   92: new 35	java/lang/RuntimeException
    //   95: dup
    //   96: ldc 37
    //   98: aload 7
    //   100: invokespecial 40	java/lang/RuntimeException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   103: athrow
    //   104: astore 5
    //   106: new 35	java/lang/RuntimeException
    //   109: dup
    //   110: ldc 37
    //   112: aload 5
    //   114: invokespecial 40	java/lang/RuntimeException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   117: athrow
    //   118: astore 4
    //   120: aload_2
    //   121: astore_1
    //   122: goto -55 -> 67
    //   125: astore 7
    //   127: aload_2
    //   128: astore_1
    //   129: goto -37 -> 92
    //   132: astore 6
    //   134: aload_2
    //   135: astore_1
    //   136: goto -56 -> 80
    //   139: astore_3
    //   140: aload_2
    //   141: astore_1
    //   142: goto -88 -> 54
    //   145: astore 7
    //   147: aconst_null
    //   148: astore_1
    //   149: goto -57 -> 92
    //
    // Exception table:
    //   from	to	target	type
    //   32	36	39	java/io/IOException
    //   2	18	53	java/io/FileNotFoundException
    //   2	18	65	finally
    //   54	65	65	finally
    //   80	92	65	finally
    //   92	104	65	finally
    //   2	18	78	java/lang/ClassNotFoundException
    //   71	75	104	java/io/IOException
    //   18	28	118	finally
    //   18	28	125	java/io/IOException
    //   18	28	132	java/lang/ClassNotFoundException
    //   18	28	139	java/io/FileNotFoundException
    //   2	18	145	java/io/IOException
  }

  // ERROR //
  public static void serialization(java.lang.String paramString, Object paramObject)
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore_2
    //   2: new 48	java/io/ObjectOutputStream
    //   5: dup
    //   6: new 50	java/io/FileOutputStream
    //   9: dup
    //   10: aload_0
    //   11: invokespecial 51	java/io/FileOutputStream:<init>	(Ljava/lang/String;)V
    //   14: invokespecial 54	java/io/ObjectOutputStream:<init>	(Ljava/io/OutputStream;)V
    //   17: astore_3
    //   18: aload_3
    //   19: aload_1
    //   20: invokevirtual 58	java/io/ObjectOutputStream:writeObject	(Ljava/lang/Object;)V
    //   23: aload_3
    //   24: invokevirtual 59	java/io/ObjectOutputStream:close	()V
    //   27: aload_3
    //   28: ifnull +7 -> 35
    //   31: aload_3
    //   32: invokevirtual 59	java/io/ObjectOutputStream:close	()V
    //   35: return
    //   36: astore 4
    //   38: new 35	java/lang/RuntimeException
    //   41: dup
    //   42: ldc 42
    //   44: aload 4
    //   46: invokespecial 40	java/lang/RuntimeException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   49: athrow
    //   50: astore 5
    //   52: aload_2
    //   53: ifnull +7 -> 60
    //   56: aload_2
    //   57: invokevirtual 59	java/io/ObjectOutputStream:close	()V
    //   60: aload 5
    //   62: athrow
    //   63: astore 7
    //   65: new 35	java/lang/RuntimeException
    //   68: dup
    //   69: ldc 37
    //   71: aload 7
    //   73: invokespecial 40	java/lang/RuntimeException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   76: athrow
    //   77: astore 6
    //   79: new 35	java/lang/RuntimeException
    //   82: dup
    //   83: ldc 37
    //   85: aload 6
    //   87: invokespecial 40	java/lang/RuntimeException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   90: athrow
    //   91: astore 8
    //   93: new 35	java/lang/RuntimeException
    //   96: dup
    //   97: ldc 37
    //   99: aload 8
    //   101: invokespecial 40	java/lang/RuntimeException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   104: athrow
    //   105: astore 5
    //   107: aload_3
    //   108: astore_2
    //   109: goto -57 -> 52
    //   112: astore 7
    //   114: aload_3
    //   115: astore_2
    //   116: goto -51 -> 65
    //   119: astore 4
    //   121: aload_3
    //   122: astore_2
    //   123: goto -85 -> 38
    //
    // Exception table:
    //   from	to	target	type
    //   2	18	36	java/io/FileNotFoundException
    //   2	18	50	finally
    //   38	50	50	finally
    //   65	77	50	finally
    //   2	18	63	java/io/IOException
    //   56	60	77	java/io/IOException
    //   31	35	91	java/io/IOException
    //   18	27	105	finally
    //   18	27	112	java/io/IOException
    //   18	27	119	java/io/FileNotFoundException
  }
}

/* Location:           C:\Users\Administrator\Desktop\test111\classes_dex2jar.jar
 * Qualified Name:     cn.wangjianlog.baseframework.tools.SerializeUtils
 * JD-Core Version:    0.6.2
 */