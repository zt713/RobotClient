package cn.wangjianlog.baseframework.tools;

import android.annotation.SuppressLint;
import java.io.PrintStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@SuppressLint({"SimpleDateFormat"})
public class DateUtil
{
  public static final String DATE_STRING1 = "yyyy-MM-dd HH:mm:ss";
  public static final String DATE_STRING2 = "yyyy-MM-dd";
  public static final String DATE_STRING3 = "yyyy/MM/dd HH:mm:ss";

  @SuppressLint({"SimpleDateFormat"})
  public static String getCurDay()
  {
    Date localDate = new Date(System.currentTimeMillis());
    return new SimpleDateFormat("yyyy-MM-dd").format(localDate);
  }

  public static String getCurTime()
  {
    Date localDate = new Date(System.currentTimeMillis());
    return new SimpleDateFormat("yyyy-MM-ddHHmmssS").format(localDate);
  }

  public static String getCurTime(String paramString)
  {
    Date localDate = new Date(System.currentTimeMillis());
    return new SimpleDateFormat(paramString).format(localDate);
  }

  public static String getCurTimeS()
  {
    Date localDate = new Date(System.currentTimeMillis());
    return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(localDate);
  }

  public static Date getDateByString(String paramString1, String paramString2)
  {
    try
    {
      Date localDate = new SimpleDateFormat(paramString2).parse(paramString1);
      return localDate;
    }
    catch (ParseException localParseException)
    {
      System.out.println("转换日期出错了，请检查字符串是否是日期格式");
    }
    return null;
  }

  public static String getFormateDate(Date paramDate)
  {
    SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    String str = "";
    if (paramDate != null)
      str = localSimpleDateFormat.format(paramDate);
    return str;
  }

  public static String getFormateDate(Date paramDate, String paramString)
  {
    SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat(paramString);
    String str = "";
    if (paramDate != null)
      str = localSimpleDateFormat.format(paramDate);
    return str;
  }

  public static Date getSimpleDate(String paramString)
  {
    Object localObject = null;
    if (paramString != null)
    {
      boolean bool = "".equals(paramString);
      localObject = null;
      if (bool);
    }
    try
    {
      Date localDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(paramString);
      return (Date) localObject;
    }
    catch (ParseException localParseException)
    {
      localParseException.printStackTrace();
    }
    return null;
  }

  public static Date getSimpleDate(String paramString1, String paramString2)
  {
    Object localObject = null;
    if (paramString1 != null)
    {
      boolean bool = "".equals(paramString1);
      localObject = null;
      if (bool);
    }
    try
    {
      Date localDate = new SimpleDateFormat(paramString2).parse(paramString1);
      localObject = localDate;
      return (Date) localObject;
    }
    catch (ParseException localParseException)
    {
      localParseException.printStackTrace();
    }
    return null;
  }
}

/* Location:           C:\Users\Administrator\Desktop\test111\classes_dex2jar.jar
 * Qualified Name:     cn.wangjianlog.baseframework.tools.DateUtil
 * JD-Core Version:    0.6.2
 */