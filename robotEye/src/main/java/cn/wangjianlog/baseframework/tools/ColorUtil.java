package cn.wangjianlog.baseframework.tools;

import android.graphics.Color;
import java.io.PrintStream;
import java.util.Random;

public class ColorUtil
{
  public static int ParseColor()
  {
    Random localRandom = new Random();
    return Color.argb(255, localRandom.nextInt(255), localRandom.nextInt(255), localRandom.nextInt(255));
  }

  public static String getRandColorCode()
  {
    Random localRandom = new Random();
    String str1 = Integer.toHexString(localRandom.nextInt(256)).toUpperCase();
    String str2 = Integer.toHexString(localRandom.nextInt(256)).toUpperCase();
    String str3 = Integer.toHexString(localRandom.nextInt(256)).toUpperCase();
    if (str1.length() == 1)
      str1 = "0" + str1;
    if (str2.length() == 1)
      str2 = "0" + str2;
    if (str3.length() == 1)
      str3 = "0" + str3;
    return str1 + str2 + str3;
  }

  public static void main(String[] paramArrayOfString)
  {
    System.out.println(String.valueOf(ParseColor()));
  }
}

/* Location:           C:\Users\Administrator\Desktop\test111\classes_dex2jar.jar
 * Qualified Name:     cn.wangjianlog.baseframework.tools.ColorUtil
 * JD-Core Version:    0.6.2
 */