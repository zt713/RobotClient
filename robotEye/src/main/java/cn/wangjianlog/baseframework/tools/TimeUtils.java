package cn.wangjianlog.baseframework.tools;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtils
{
  public static final SimpleDateFormat DATE_FORMAT_DATE = new SimpleDateFormat("yyyy-MM-dd");
  public static final SimpleDateFormat DEFAULT_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

  public static long getCurrentTimeInLong()
  {
    return System.currentTimeMillis();
  }

  public static String getCurrentTimeInString()
  {
    return getTime(getCurrentTimeInLong());
  }

  public static String getCurrentTimeInString(SimpleDateFormat paramSimpleDateFormat)
  {
    return getTime(getCurrentTimeInLong(), paramSimpleDateFormat);
  }

  public static String getTime(long paramLong)
  {
    return getTime(paramLong, DEFAULT_DATE_FORMAT);
  }

  public static String getTime(long paramLong, SimpleDateFormat paramSimpleDateFormat)
  {
    return paramSimpleDateFormat.format(new Date(paramLong));
  }
}

/* Location:           C:\Users\Administrator\Desktop\test111\classes_dex2jar.jar
 * Qualified Name:     cn.wangjianlog.baseframework.tools.TimeUtils
 * JD-Core Version:    0.6.2
 */