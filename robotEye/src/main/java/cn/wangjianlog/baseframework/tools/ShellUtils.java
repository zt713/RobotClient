package cn.wangjianlog.baseframework.tools;

import java.util.List;

public class ShellUtils
{
  public static final String COMMAND_EXIT = "exit\n";
  public static final String COMMAND_LINE_END = "\n";
  public static final String COMMAND_SH = "sh";
  public static final String COMMAND_SU = "su";

  public static boolean checkRootPermission()
  {
    return execCommand("echo root", true, false).result == 0;
  }

  public static CommandResult execCommand(String paramString, boolean paramBoolean)
  {
    return execCommand(new String[] { paramString }, paramBoolean, true);
  }

  public static CommandResult execCommand(String paramString, boolean paramBoolean1, boolean paramBoolean2)
  {
    return execCommand(new String[] { paramString }, paramBoolean1, paramBoolean2);
  }

  public static CommandResult execCommand(List<String> paramList, boolean paramBoolean)
  {
    if (paramList == null);
    for (String[] arrayOfString = null; ; arrayOfString = (String[])paramList.toArray(new String[0]))
      return execCommand(arrayOfString, paramBoolean, true);
  }

  public static CommandResult execCommand(List<String> paramList, boolean paramBoolean1, boolean paramBoolean2)
  {
    if (paramList == null);
    for (String[] arrayOfString = null; ; arrayOfString = (String[])paramList.toArray(new String[0]))
      return execCommand(arrayOfString, paramBoolean1, paramBoolean2);
  }

  public static CommandResult execCommand(String[] paramArrayOfString, boolean paramBoolean)
  {
    return execCommand(paramArrayOfString, paramBoolean, true);
  }

  // ERROR //
  public static CommandResult execCommand(String[] paramArrayOfString, boolean paramBoolean1, boolean paramBoolean2)
  {
	return null;
    // Byte code:
    //   0: iconst_m1
    //   1: istore_3
    //   2: aload_0
    //   3: ifnull +8 -> 11
    //   6: aload_0
    //   7: arraylength
    //   8: ifne +18 -> 26
    //   11: new 31	cn/wangjianlog/baseframework/tools/ShellUtils$CommandResult
    //   14: dup
    //   15: iload_3
    //   16: aconst_null
    //   17: aconst_null
    //   18: invokespecial 59	cn/wangjianlog/baseframework/tools/ShellUtils$CommandResult:<init>	(ILjava/lang/String;Ljava/lang/String;)V
    //   21: astore 4
    //   23: aload 4
    //   25: areturn
    //   26: aconst_null
    //   27: astore 5
    //   29: aconst_null
    //   30: astore 6
    //   32: aconst_null
    //   33: astore 7
    //   35: aconst_null
    //   36: astore 8
    //   38: aconst_null
    //   39: astore 9
    //   41: aconst_null
    //   42: astore 10
    //   44: invokestatic 65	java/lang/Runtime:getRuntime	()Ljava/lang/Runtime;
    //   47: astore 20
    //   49: iload_1
    //   50: ifeq +251 -> 301
    //   53: ldc 17
    //   55: astore 21
    //   57: aload 20
    //   59: aload 21
    //   61: invokevirtual 69	java/lang/Runtime:exec	(Ljava/lang/String;)Ljava/lang/Process;
    //   64: astore 5
    //   66: new 71	java/io/DataOutputStream
    //   69: dup
    //   70: aload 5
    //   72: invokevirtual 77	java/lang/Process:getOutputStream	()Ljava/io/OutputStream;
    //   75: invokespecial 80	java/io/DataOutputStream:<init>	(Ljava/io/OutputStream;)V
    //   78: astore 22
    //   80: aload_0
    //   81: arraylength
    //   82: istore 23
    //   84: iconst_0
    //   85: istore 24
    //   87: iload 24
    //   89: iload 23
    //   91: if_icmplt +217 -> 308
    //   94: aload 22
    //   96: ldc 8
    //   98: invokevirtual 84	java/io/DataOutputStream:writeBytes	(Ljava/lang/String;)V
    //   101: aload 22
    //   103: invokevirtual 87	java/io/DataOutputStream:flush	()V
    //   106: aload 5
    //   108: invokevirtual 91	java/lang/Process:waitFor	()I
    //   111: istore_3
    //   112: aconst_null
    //   113: astore 9
    //   115: aconst_null
    //   116: astore 7
    //   118: aconst_null
    //   119: astore 8
    //   121: aconst_null
    //   122: astore 6
    //   124: iload_2
    //   125: ifeq +103 -> 228
    //   128: new 93	java/lang/StringBuilder
    //   131: dup
    //   132: invokespecial 94	java/lang/StringBuilder:<init>	()V
    //   135: astore 25
    //   137: new 93	java/lang/StringBuilder
    //   140: dup
    //   141: invokespecial 94	java/lang/StringBuilder:<init>	()V
    //   144: astore 26
    //   146: new 96	java/io/BufferedReader
    //   149: dup
    //   150: new 98	java/io/InputStreamReader
    //   153: dup
    //   154: aload 5
    //   156: invokevirtual 102	java/lang/Process:getInputStream	()Ljava/io/InputStream;
    //   159: invokespecial 105	java/io/InputStreamReader:<init>	(Ljava/io/InputStream;)V
    //   162: invokespecial 108	java/io/BufferedReader:<init>	(Ljava/io/Reader;)V
    //   165: astore 27
    //   167: new 96	java/io/BufferedReader
    //   170: dup
    //   171: new 98	java/io/InputStreamReader
    //   174: dup
    //   175: aload 5
    //   177: invokevirtual 111	java/lang/Process:getErrorStream	()Ljava/io/InputStream;
    //   180: invokespecial 105	java/io/InputStreamReader:<init>	(Ljava/io/InputStream;)V
    //   183: invokespecial 108	java/io/BufferedReader:<init>	(Ljava/io/Reader;)V
    //   186: astore 28
    //   188: aload 27
    //   190: invokevirtual 115	java/io/BufferedReader:readLine	()Ljava/lang/String;
    //   193: astore 29
    //   195: aload 29
    //   197: ifnonnull +204 -> 401
    //   200: aload 28
    //   202: invokevirtual 115	java/io/BufferedReader:readLine	()Ljava/lang/String;
    //   205: astore 31
    //   207: aload 31
    //   209: ifnonnull +203 -> 412
    //   212: aload 26
    //   214: astore 9
    //   216: aload 25
    //   218: astore 8
    //   220: aload 28
    //   222: astore 7
    //   224: aload 27
    //   226: astore 6
    //   228: aload 22
    //   230: ifnull +8 -> 238
    //   233: aload 22
    //   235: invokevirtual 118	java/io/DataOutputStream:close	()V
    //   238: aload 6
    //   240: ifnull +8 -> 248
    //   243: aload 6
    //   245: invokevirtual 119	java/io/BufferedReader:close	()V
    //   248: aload 7
    //   250: ifnull +8 -> 258
    //   253: aload 7
    //   255: invokevirtual 119	java/io/BufferedReader:close	()V
    //   258: aload 5
    //   260: ifnull +8 -> 268
    //   263: aload 5
    //   265: invokevirtual 122	java/lang/Process:destroy	()V
    //   268: aload 8
    //   270: ifnonnull +308 -> 578
    //   273: aconst_null
    //   274: astore 15
    //   276: aload 9
    //   278: ifnonnull +310 -> 588
    //   281: aconst_null
    //   282: astore 16
    //   284: new 31	cn/wangjianlog/baseframework/tools/ShellUtils$CommandResult
    //   287: dup
    //   288: iload_3
    //   289: aload 15
    //   291: aload 16
    //   293: invokespecial 59	cn/wangjianlog/baseframework/tools/ShellUtils$CommandResult:<init>	(ILjava/lang/String;Ljava/lang/String;)V
    //   296: astore 17
    //   298: aload 17
    //   300: areturn
    //   301: ldc 14
    //   303: astore 21
    //   305: goto -248 -> 57
    //   308: aload_0
    //   309: iload 24
    //   311: aaload
    //   312: astore 34
    //   314: aload 34
    //   316: ifnonnull +6 -> 322
    //   319: goto +556 -> 875
    //   322: aload 22
    //   324: aload 34
    //   326: invokevirtual 126	java/lang/String:getBytes	()[B
    //   329: invokevirtual 130	java/io/DataOutputStream:write	([B)V
    //   332: aload 22
    //   334: ldc 11
    //   336: invokevirtual 84	java/io/DataOutputStream:writeBytes	(Ljava/lang/String;)V
    //   339: aload 22
    //   341: invokevirtual 87	java/io/DataOutputStream:flush	()V
    //   344: goto +531 -> 875
    //   347: astore 11
    //   349: aload 22
    //   351: astore 10
    //   353: aload 11
    //   355: invokevirtual 133	java/io/IOException:printStackTrace	()V
    //   358: aload 10
    //   360: ifnull +8 -> 368
    //   363: aload 10
    //   365: invokevirtual 118	java/io/DataOutputStream:close	()V
    //   368: aload 6
    //   370: ifnull +8 -> 378
    //   373: aload 6
    //   375: invokevirtual 119	java/io/BufferedReader:close	()V
    //   378: aload 7
    //   380: ifnull +8 -> 388
    //   383: aload 7
    //   385: invokevirtual 119	java/io/BufferedReader:close	()V
    //   388: aload 5
    //   390: ifnull -122 -> 268
    //   393: aload 5
    //   395: invokevirtual 122	java/lang/Process:destroy	()V
    //   398: goto -130 -> 268
    //   401: aload 25
    //   403: aload 29
    //   405: invokevirtual 137	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   408: pop
    //   409: goto -221 -> 188
    //   412: aload 26
    //   414: aload 31
    //   416: invokevirtual 137	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   419: pop
    //   420: goto -220 -> 200
    //   423: astore 18
    //   425: aload 22
    //   427: astore 10
    //   429: aload 26
    //   431: astore 9
    //   433: aload 25
    //   435: astore 8
    //   437: aload 28
    //   439: astore 7
    //   441: aload 27
    //   443: astore 6
    //   445: aload 18
    //   447: invokevirtual 138	java/lang/Exception:printStackTrace	()V
    //   450: aload 10
    //   452: ifnull +8 -> 460
    //   455: aload 10
    //   457: invokevirtual 118	java/io/DataOutputStream:close	()V
    //   460: aload 6
    //   462: ifnull +8 -> 470
    //   465: aload 6
    //   467: invokevirtual 119	java/io/BufferedReader:close	()V
    //   470: aload 7
    //   472: ifnull +8 -> 480
    //   475: aload 7
    //   477: invokevirtual 119	java/io/BufferedReader:close	()V
    //   480: aload 5
    //   482: ifnull -214 -> 268
    //   485: aload 5
    //   487: invokevirtual 122	java/lang/Process:destroy	()V
    //   490: goto -222 -> 268
    //   493: astore 14
    //   495: aload 14
    //   497: invokevirtual 133	java/io/IOException:printStackTrace	()V
    //   500: goto -112 -> 388
    //   503: astore 19
    //   505: aload 19
    //   507: invokevirtual 133	java/io/IOException:printStackTrace	()V
    //   510: goto -30 -> 480
    //   513: astore 12
    //   515: aload 10
    //   517: ifnull +8 -> 525
    //   520: aload 10
    //   522: invokevirtual 118	java/io/DataOutputStream:close	()V
    //   525: aload 6
    //   527: ifnull +8 -> 535
    //   530: aload 6
    //   532: invokevirtual 119	java/io/BufferedReader:close	()V
    //   535: aload 7
    //   537: ifnull +8 -> 545
    //   540: aload 7
    //   542: invokevirtual 119	java/io/BufferedReader:close	()V
    //   545: aload 5
    //   547: ifnull +8 -> 555
    //   550: aload 5
    //   552: invokevirtual 122	java/lang/Process:destroy	()V
    //   555: aload 12
    //   557: athrow
    //   558: astore 13
    //   560: aload 13
    //   562: invokevirtual 133	java/io/IOException:printStackTrace	()V
    //   565: goto -20 -> 545
    //   568: astore 33
    //   570: aload 33
    //   572: invokevirtual 133	java/io/IOException:printStackTrace	()V
    //   575: goto -317 -> 258
    //   578: aload 8
    //   580: invokevirtual 141	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   583: astore 15
    //   585: goto -309 -> 276
    //   588: aload 9
    //   590: invokevirtual 141	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   593: astore 16
    //   595: goto -311 -> 284
    //   598: astore 12
    //   600: aload 22
    //   602: astore 10
    //   604: aconst_null
    //   605: astore 7
    //   607: aconst_null
    //   608: astore 6
    //   610: goto -95 -> 515
    //   613: astore 12
    //   615: aload 22
    //   617: astore 10
    //   619: aconst_null
    //   620: astore 7
    //   622: aconst_null
    //   623: astore 6
    //   625: goto -110 -> 515
    //   628: astore 12
    //   630: aload 22
    //   632: astore 10
    //   634: aconst_null
    //   635: astore 7
    //   637: aconst_null
    //   638: astore 6
    //   640: goto -125 -> 515
    //   643: astore 12
    //   645: aload 22
    //   647: astore 10
    //   649: aload 27
    //   651: astore 6
    //   653: aconst_null
    //   654: astore 7
    //   656: goto -141 -> 515
    //   659: astore 12
    //   661: aload 22
    //   663: astore 10
    //   665: aload 28
    //   667: astore 7
    //   669: aload 27
    //   671: astore 6
    //   673: goto -158 -> 515
    //   676: astore 18
    //   678: aconst_null
    //   679: astore 9
    //   681: aconst_null
    //   682: astore 7
    //   684: aconst_null
    //   685: astore 10
    //   687: aconst_null
    //   688: astore 8
    //   690: aconst_null
    //   691: astore 6
    //   693: goto -248 -> 445
    //   696: astore 18
    //   698: aload 22
    //   700: astore 10
    //   702: aconst_null
    //   703: astore 9
    //   705: aconst_null
    //   706: astore 7
    //   708: aconst_null
    //   709: astore 8
    //   711: aconst_null
    //   712: astore 6
    //   714: goto -269 -> 445
    //   717: astore 18
    //   719: aload 22
    //   721: astore 10
    //   723: aload 25
    //   725: astore 8
    //   727: aconst_null
    //   728: astore 9
    //   730: aconst_null
    //   731: astore 7
    //   733: aconst_null
    //   734: astore 6
    //   736: goto -291 -> 445
    //   739: astore 18
    //   741: aload 22
    //   743: astore 10
    //   745: aload 26
    //   747: astore 9
    //   749: aload 25
    //   751: astore 8
    //   753: aconst_null
    //   754: astore 7
    //   756: aconst_null
    //   757: astore 6
    //   759: goto -314 -> 445
    //   762: astore 18
    //   764: aload 22
    //   766: astore 10
    //   768: aload 26
    //   770: astore 9
    //   772: aload 25
    //   774: astore 8
    //   776: aload 27
    //   778: astore 6
    //   780: aconst_null
    //   781: astore 7
    //   783: goto -338 -> 445
    //   786: astore 11
    //   788: aconst_null
    //   789: astore 9
    //   791: aconst_null
    //   792: astore 7
    //   794: aconst_null
    //   795: astore 10
    //   797: aconst_null
    //   798: astore 8
    //   800: aconst_null
    //   801: astore 6
    //   803: goto -450 -> 353
    //   806: astore 11
    //   808: aload 22
    //   810: astore 10
    //   812: aload 25
    //   814: astore 8
    //   816: aconst_null
    //   817: astore 9
    //   819: aconst_null
    //   820: astore 7
    //   822: aconst_null
    //   823: astore 6
    //   825: goto -472 -> 353
    //   828: astore 11
    //   830: aload 22
    //   832: astore 10
    //   834: aload 26
    //   836: astore 9
    //   838: aload 25
    //   840: astore 8
    //   842: aconst_null
    //   843: astore 7
    //   845: aconst_null
    //   846: astore 6
    //   848: goto -495 -> 353
    //   851: astore 11
    //   853: aload 22
    //   855: astore 10
    //   857: aload 26
    //   859: astore 9
    //   861: aload 25
    //   863: astore 8
    //   865: aload 27
    //   867: astore 6
    //   869: aconst_null
    //   870: astore 7
    //   872: goto -519 -> 353
    //   875: iinc 24 1
    //   878: goto -791 -> 87
    //   881: astore 11
    //   883: aload 22
    //   885: astore 10
    //   887: aload 26
    //   889: astore 9
    //   891: aload 25
    //   893: astore 8
    //   895: aload 28
    //   897: astore 7
    //   899: aload 27
    //   901: astore 6
    //   903: goto -550 -> 353
    //
    // Exception table:
    //   from	to	target	type
    //   80	84	347	java/io/IOException
    //   94	112	347	java/io/IOException
    //   128	137	347	java/io/IOException
    //   308	314	347	java/io/IOException
    //   322	344	347	java/io/IOException
    //   188	195	423	java/lang/Exception
    //   200	207	423	java/lang/Exception
    //   401	409	423	java/lang/Exception
    //   412	420	423	java/lang/Exception
    //   363	368	493	java/io/IOException
    //   373	378	493	java/io/IOException
    //   383	388	493	java/io/IOException
    //   455	460	503	java/io/IOException
    //   465	470	503	java/io/IOException
    //   475	480	503	java/io/IOException
    //   44	49	513	finally
    //   57	80	513	finally
    //   353	358	513	finally
    //   445	450	513	finally
    //   520	525	558	java/io/IOException
    //   530	535	558	java/io/IOException
    //   540	545	558	java/io/IOException
    //   233	238	568	java/io/IOException
    //   243	248	568	java/io/IOException
    //   253	258	568	java/io/IOException
    //   80	84	598	finally
    //   94	112	598	finally
    //   128	137	598	finally
    //   308	314	598	finally
    //   322	344	598	finally
    //   137	146	613	finally
    //   146	167	628	finally
    //   167	188	643	finally
    //   188	195	659	finally
    //   200	207	659	finally
    //   401	409	659	finally
    //   412	420	659	finally
    //   44	49	676	java/lang/Exception
    //   57	80	676	java/lang/Exception
    //   80	84	696	java/lang/Exception
    //   94	112	696	java/lang/Exception
    //   128	137	696	java/lang/Exception
    //   308	314	696	java/lang/Exception
    //   322	344	696	java/lang/Exception
    //   137	146	717	java/lang/Exception
    //   146	167	739	java/lang/Exception
    //   167	188	762	java/lang/Exception
    //   44	49	786	java/io/IOException
    //   57	80	786	java/io/IOException
    //   137	146	806	java/io/IOException
    //   146	167	828	java/io/IOException
    //   167	188	851	java/io/IOException
    //   188	195	881	java/io/IOException
    //   200	207	881	java/io/IOException
    //   401	409	881	java/io/IOException
    //   412	420	881	java/io/IOException
  }

  public static class CommandResult
  {
    public String errorMsg;
    public int result;
    public String successMsg;

    public CommandResult(int paramInt)
    {
      this.result = paramInt;
    }

    public CommandResult(int paramInt, String paramString1, String paramString2)
    {
      this.result = paramInt;
      this.successMsg = paramString1;
      this.errorMsg = paramString2;
    }
  }
}

/* Location:           C:\Users\Administrator\Desktop\test111\classes_dex2jar.jar
 * Qualified Name:     cn.wangjianlog.baseframework.tools.ShellUtils
 * JD-Core Version:    0.6.2
 */