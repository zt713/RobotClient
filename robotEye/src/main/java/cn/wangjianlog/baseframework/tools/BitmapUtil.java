package cn.wangjianlog.baseframework.tools;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.Region;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;

@SuppressLint({"DefaultLocale"})
public class BitmapUtil
{
  private static BitmapUtil tools = new BitmapUtil();

  private static int computeInitialSampleSize(BitmapFactory.Options paramOptions, int paramInt1, int paramInt2)
  {
    double d1 = paramOptions.outWidth;
    double d2 = paramOptions.outHeight;
    int i;
    int j;
    if (paramInt2 == -1)
    {
      i = 1;
      if (paramInt1 != -1)
      j = 128;
    }
      i = (int)Math.ceil(Math.sqrt(d1 * d2 / paramInt2));
      j = (int)Math.min(Math.floor(d1 / paramInt1), Math.floor(d2 / paramInt1));
      if ((paramInt2 == -1) && (paramInt1 == -1))
        return 1;
	return j;
  }

  public static int computeSampleSize(BitmapFactory.Options paramOptions, int paramInt1, int paramInt2)
  {
    int i = computeInitialSampleSize(paramOptions, paramInt1, paramInt2);
    if (i <= 8)
    {
      int j = 1;
      while (true)
      {
        if (j >= i)
          return j;
        j <<= 1;
      }
    }
    return 8 * ((i + 7) / 8);
  }

  public static BitmapUtil getInstance()
  {
    if (tools == null)
    {
      tools = new BitmapUtil();
      return tools;
    }
    return tools;
  }

  public static Path getPath(Bitmap paramBitmap)
  {
    int i = paramBitmap.getWidth();
    Region localRegion = new Region(0, 0, i, paramBitmap.getHeight());
    Rect localRect = new Rect();
    int[] arrayOfInt = new int[paramBitmap.getWidth() * paramBitmap.getHeight()];
    paramBitmap.getPixels(arrayOfInt, 0, paramBitmap.getWidth(), 0, 0, paramBitmap.getWidth(), paramBitmap.getHeight());
    for (int j = 0; ; j++)
    {
      if (j >= arrayOfInt.length)
        return localRegion.getBoundaryPath();
      if (arrayOfInt[j] == 0)
      {
        int k = j / i;
        int m = j % i;
        localRect.set(m, k, m + 1, k + 1);
        localRegion.op(localRect, Region.Op.DIFFERENCE);
      }
    }
  }

  public static Bitmap getPics(String paramString, int paramInt)
  {
    BitmapFactory.Options localOptions = new BitmapFactory.Options();
    localOptions.inJustDecodeBounds = true;
    BitmapFactory.decodeFile(paramString, localOptions);
    localOptions.inSampleSize = computeSampleSize(localOptions, -1, paramInt);
    localOptions.inJustDecodeBounds = false;
    try
    {
      Bitmap localBitmap = BitmapFactory.decodeFile(paramString, localOptions);
      return localBitmap;
    }
    catch (OutOfMemoryError localOutOfMemoryError)
    {
      localOutOfMemoryError.printStackTrace();
    }
    return null;
  }

  public static StateListDrawable getStateListDrawable(Drawable paramDrawable)
  {
    StateListDrawable localStateListDrawable = new StateListDrawable();
    Bitmap localBitmap1 = ((BitmapDrawable)paramDrawable).getBitmap();
    Bitmap localBitmap2 = Bitmap.createBitmap(localBitmap1.getWidth(), localBitmap1.getHeight(), Bitmap.Config.ARGB_8888);
    ColorMatrix localColorMatrix = new ColorMatrix();
    float[] arrayOfFloat = new float[20];
    arrayOfFloat[0] = 1.0F;
    arrayOfFloat[1] = 0.0F;
    arrayOfFloat[2] = 0.0F;
    arrayOfFloat[3] = 0.0F;
    arrayOfFloat[4] = -67;
    arrayOfFloat[5] = 0.0F;
    arrayOfFloat[6] = 1.0F;
    arrayOfFloat[7] = 0.0F;
    arrayOfFloat[8] = 0.0F;
    arrayOfFloat[9] = -67;
    arrayOfFloat[10] = 0.0F;
    arrayOfFloat[11] = 0.0F;
    arrayOfFloat[12] = 1.0F;
    arrayOfFloat[13] = 0.0F;
    arrayOfFloat[14] = -67;
    arrayOfFloat[15] = 0.0F;
    arrayOfFloat[16] = 0.0F;
    arrayOfFloat[17] = 0.0F;
    arrayOfFloat[18] = 1.0F;
    arrayOfFloat[19] = 0.0F;
    localColorMatrix.set(arrayOfFloat);
    Paint localPaint = new Paint();
    localPaint.setColorFilter(new ColorMatrixColorFilter(localColorMatrix));
    new Canvas(localBitmap2).drawBitmap(localBitmap1, 0.0F, 0.0F, localPaint);
    BitmapDrawable localBitmapDrawable = new BitmapDrawable(localBitmap2);
    localStateListDrawable.addState(new int[] { 16842919 }, localBitmapDrawable);
    localStateListDrawable.addState(new int[] { 16842913 }, localBitmapDrawable);
    localStateListDrawable.addState(new int[] { 16842910 }, paramDrawable);
    return localStateListDrawable;
  }

  public static byte[] readStream(InputStream paramInputStream)
    throws Exception
  {
    ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
    byte[] arrayOfByte = new byte[1024];
    while (true)
    {
      int i = paramInputStream.read(arrayOfByte);
      if (i == -1)
      {
        localByteArrayOutputStream.close();
        paramInputStream.close();
        return localByteArrayOutputStream.toByteArray();
      }
      localByteArrayOutputStream.write(arrayOfByte, 0, i);
    }
  }

  // ERROR //
  public static void savePic(Bitmap paramBitmap, String paramString1, String paramString2)
  {
    // Byte code:
    //   0: new 201	java/io/File
    //   3: dup
    //   4: aload_1
    //   5: invokespecial 204	java/io/File:<init>	(Ljava/lang/String;)V
    //   8: astore_3
    //   9: aload_3
    //   10: invokevirtual 208	java/io/File:exists	()Z
    //   13: ifne +8 -> 21
    //   16: aload_3
    //   17: invokevirtual 211	java/io/File:mkdir	()Z
    //   20: pop
    //   21: aconst_null
    //   22: astore 4
    //   24: aconst_null
    //   25: astore 5
    //   27: new 213	java/io/FileOutputStream
    //   30: dup
    //   31: new 215	java/lang/StringBuilder
    //   34: dup
    //   35: aload_1
    //   36: invokestatic 221	java/lang/String:valueOf	(Ljava/lang/Object;)Ljava/lang/String;
    //   39: invokespecial 222	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   42: getstatic 226	java/io/File:separator	Ljava/lang/String;
    //   45: invokevirtual 230	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   48: aload_2
    //   49: invokevirtual 230	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   52: invokevirtual 234	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   55: invokespecial 235	java/io/FileOutputStream:<init>	(Ljava/lang/String;)V
    //   58: astore 6
    //   60: new 237	java/io/BufferedOutputStream
    //   63: dup
    //   64: aload 6
    //   66: invokespecial 240	java/io/BufferedOutputStream:<init>	(Ljava/io/OutputStream;)V
    //   69: astore 7
    //   71: aload 7
    //   73: ifnull +15 -> 88
    //   76: aload_0
    //   77: getstatic 246	android/graphics/Bitmap$CompressFormat:PNG	Landroid/graphics/Bitmap$CompressFormat;
    //   80: bipush 90
    //   82: aload 7
    //   84: invokevirtual 250	android/graphics/Bitmap:compress	(Landroid/graphics/Bitmap$CompressFormat;ILjava/io/OutputStream;)Z
    //   87: pop
    //   88: aload 7
    //   90: ifnull +82 -> 172
    //   93: aload 7
    //   95: invokevirtual 253	java/io/BufferedOutputStream:flush	()V
    //   98: aload 6
    //   100: invokevirtual 254	java/io/FileOutputStream:close	()V
    //   103: return
    //   104: astore 9
    //   106: aload 9
    //   108: invokevirtual 255	java/io/FileNotFoundException:printStackTrace	()V
    //   111: aload 5
    //   113: ifnull -10 -> 103
    //   116: aload 5
    //   118: invokevirtual 253	java/io/BufferedOutputStream:flush	()V
    //   121: aload 4
    //   123: invokevirtual 254	java/io/FileOutputStream:close	()V
    //   126: return
    //   127: astore 12
    //   129: aload 12
    //   131: invokevirtual 256	java/io/IOException:printStackTrace	()V
    //   134: return
    //   135: astore 10
    //   137: aload 5
    //   139: ifnull +13 -> 152
    //   142: aload 5
    //   144: invokevirtual 253	java/io/BufferedOutputStream:flush	()V
    //   147: aload 4
    //   149: invokevirtual 254	java/io/FileOutputStream:close	()V
    //   152: aload 10
    //   154: athrow
    //   155: astore 11
    //   157: aload 11
    //   159: invokevirtual 256	java/io/IOException:printStackTrace	()V
    //   162: goto -10 -> 152
    //   165: astore 8
    //   167: aload 8
    //   169: invokevirtual 256	java/io/IOException:printStackTrace	()V
    //   172: return
    //   173: astore 10
    //   175: aload 6
    //   177: astore 4
    //   179: aconst_null
    //   180: astore 5
    //   182: goto -45 -> 137
    //   185: astore 10
    //   187: aload 7
    //   189: astore 5
    //   191: aload 6
    //   193: astore 4
    //   195: goto -58 -> 137
    //   198: astore 9
    //   200: aload 6
    //   202: astore 4
    //   204: aconst_null
    //   205: astore 5
    //   207: goto -101 -> 106
    //   210: astore 9
    //   212: aload 7
    //   214: astore 5
    //   216: aload 6
    //   218: astore 4
    //   220: goto -114 -> 106
    //
    // Exception table:
    //   from	to	target	type
    //   27	60	104	java/io/FileNotFoundException
    //   116	126	127	java/io/IOException
    //   27	60	135	finally
    //   106	111	135	finally
    //   142	152	155	java/io/IOException
    //   93	103	165	java/io/IOException
    //   60	71	173	finally
    //   76	88	185	finally
    //   60	71	198	java/io/FileNotFoundException
    //   76	88	210	java/io/FileNotFoundException
  }

  public byte[] Bitmap2Bytes(Bitmap paramBitmap)
  {
    ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
    paramBitmap.compress(Bitmap.CompressFormat.PNG, 100, localByteArrayOutputStream);
    return localByteArrayOutputStream.toByteArray();
  }

  public InputStream Bitmap2InputStream(Bitmap paramBitmap)
  {
    ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
    paramBitmap.compress(Bitmap.CompressFormat.JPEG, 100, localByteArrayOutputStream);
    return new ByteArrayInputStream(localByteArrayOutputStream.toByteArray());
  }

  public InputStream Bitmap2InputStream(Bitmap paramBitmap, int paramInt)
  {
    ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
    paramBitmap.compress(Bitmap.CompressFormat.PNG, paramInt, localByteArrayOutputStream);
    return new ByteArrayInputStream(localByteArrayOutputStream.toByteArray());
  }

  public InputStream Byte2InputStream(byte[] paramArrayOfByte)
  {
    return new ByteArrayInputStream(paramArrayOfByte);
  }

  public Bitmap Bytes2Bitmap(byte[] paramArrayOfByte)
  {
    if (paramArrayOfByte.length != 0)
      return BitmapFactory.decodeByteArray(paramArrayOfByte, 0, paramArrayOfByte.length);
    return null;
  }

  public Drawable Bytes2Drawable(byte[] paramArrayOfByte)
  {
    return bitmap2Drawable(Bytes2Bitmap(paramArrayOfByte));
  }

  public byte[] Drawable2Bytes(Drawable paramDrawable)
  {
    return Bitmap2Bytes(drawable2Bitmap(paramDrawable));
  }

  public InputStream Drawable2InputStream(Drawable paramDrawable)
  {
    return Bitmap2InputStream(drawable2Bitmap(paramDrawable));
  }

  public Bitmap InputStream2Bitmap(InputStream paramInputStream)
  {
    return BitmapFactory.decodeStream(paramInputStream);
  }

  public byte[] InputStream2Bytes(InputStream paramInputStream)
  {
    Object localObject = "";
    byte[] arrayOfByte = new byte[1024];
    try
    {
      while (true)
      {
        if (paramInputStream.read(arrayOfByte, 0, 1024) == -1)
          return ((String)localObject).getBytes();
        String str = localObject + new String(arrayOfByte).trim();
        localObject = str;
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return null;
  }

  public Drawable InputStream2Drawable(InputStream paramInputStream)
  {
    return bitmap2Drawable(InputStream2Bitmap(paramInputStream));
  }

  public Drawable bitmap2Drawable(Bitmap paramBitmap)
  {
    return new BitmapDrawable(paramBitmap);
  }

  public Bitmap drawable2Bitmap(Drawable paramDrawable)
  {
    int i = paramDrawable.getIntrinsicWidth();
    int j = paramDrawable.getIntrinsicHeight();
    if (paramDrawable.getOpacity() != -1);
    for (Bitmap.Config localConfig = Bitmap.Config.ARGB_8888; ; localConfig = Bitmap.Config.RGB_565)
    {
      Bitmap localBitmap = Bitmap.createBitmap(i, j, localConfig);
      Canvas localCanvas = new Canvas(localBitmap);
      paramDrawable.setBounds(0, 0, paramDrawable.getIntrinsicWidth(), paramDrawable.getIntrinsicHeight());
      paramDrawable.draw(localCanvas);
      return localBitmap;
    }
  }

  public byte[] getImage(String paramString)
    throws Exception
  {
    try
    {
      int i = paramString.lastIndexOf("/");
      String str1 = paramString.substring(0, i + 1);
      String str2 = paramString.substring(i + 1, paramString.length());
      HttpURLConnection localHttpURLConnection = (HttpURLConnection)new URL(str1 + URLEncoder.encode(str2, "utf-8")).openConnection();
      localHttpURLConnection.setConnectTimeout(5000);
      localHttpURLConnection.setRequestMethod("GET");
      InputStream localInputStream = localHttpURLConnection.getInputStream();
      if (localHttpURLConnection.getResponseCode() == 200)
      {
        byte[] arrayOfByte = readStream(localInputStream);
        return arrayOfByte;
      }
    }
    catch (Exception localException)
    {
    }
    return null;
  }

  public List<String> getPictures(String paramString)
  {
    ArrayList localArrayList = new ArrayList();
    File[] arrayOfFile = new File(paramString).listFiles();
    if (arrayOfFile == null)
      localArrayList = null;
    while (true)
    {
      return localArrayList;
      /*for (int i = 0; i < arrayOfFile.length; i++)
      {
        File localFile = arrayOfFile[i];
        if (localFile.isFile())
          try
          {
            int j = localFile.getPath().lastIndexOf(".");
            if (j > 0)
            {
              String str = localFile.getPath().substring(j);
              if ((str.toLowerCase().equals(".jpg")) || (str.toLowerCase().equals(".jpeg")) || (str.toLowerCase().equals(".bmp")) || (str.toLowerCase().equals(".png")) || (str.toLowerCase().equals(".gif")))
                localArrayList.add(localFile.getPath());
            }
          }
          catch (Exception localException)
          {
            localException.printStackTrace();
          }
      }*/
    }
  }
}

/* Location:           C:\Users\Administrator\Desktop\test111\classes_dex2jar.jar
 * Qualified Name:     cn.wangjianlog.baseframework.tools.BitmapUtil
 * JD-Core Version:    0.6.2
 */