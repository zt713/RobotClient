package cn.wangjianlog.baseframework.tools;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils
{
  public static String addEllipsisToString(int paramInt, String paramString)
  {
    String str = "";
    if ((!isEmpty(paramString)) && (paramInt > 0))
    {
      if (paramString.length() > paramInt)
        str = paramString.substring(0, paramInt) + "...";
    }
    else
      return str;
    return paramString;
  }

  public static String capitalizeFirstLetter(String paramString)
  {
    if (isEmpty(paramString));
    char c;
    do
    {
      c = paramString.charAt(0);
    }
    while ((!Character.isLetter(c)) || (Character.isUpperCase(c)));
    return paramString.length() + Character.toUpperCase(c) + paramString.substring(1);
  }

  public static boolean check_illegalCharacters(String paramString)
  {
    return Pattern.compile("[^?.,\"。，；;!@#$%\\^&*{}\\[\\]()]+").matcher(paramString).matches();
  }

  public static String fullWidthToHalfWidth(String paramString)
  {
    if (isEmpty(paramString))
      return paramString;
    char[] arrayOfChar = paramString.toCharArray();
    int i = 0;
    if (i >= arrayOfChar.length)
      return new String(arrayOfChar);
    if (arrayOfChar[i] == '　')
      arrayOfChar[i] = ' ';
    while (true)
    {
      i++;
      if ((arrayOfChar[i] >= 65281) && (arrayOfChar[i] <= 65374))
        arrayOfChar[i] = ((char)(arrayOfChar[i] - 65248));
      else
        arrayOfChar[i] = arrayOfChar[i];
    }
  }

  public static String getDecodePaht(String paramString)
  {
    String str1 = paramString.replace("http", "");
    try
    {
      String str2 = "cache_" + URLEncoder.encode(str1.replace("*", ""), "UTF-8");
      return str2;
    }
    catch (UnsupportedEncodingException localUnsupportedEncodingException)
    {
    }
    return "";
  }

  public static String getHrefInnerHtml(String paramString)
  {
    if (isEmpty(paramString))
      paramString = "";
    Matcher localMatcher;
    do
    {
      localMatcher = Pattern.compile(".*<[\\s]*a[\\s]*.*>(.+?)<[\\s]*/a[\\s]*>.*", 2).matcher(paramString);
    }
    while (!localMatcher.matches());
    return localMatcher.group(1);
  }

  public static String halfWidthToFullWidth(String paramString)
  {
    if (isEmpty(paramString))
      return paramString;
    char[] arrayOfChar = paramString.toCharArray();
    int i = 0;
    if (i >= arrayOfChar.length)
      return new String(arrayOfChar);
    if (arrayOfChar[i] == ' ')
      arrayOfChar[i] = '　';
    while (true)
    {
      i++;
      if ((arrayOfChar[i] >= '!') && (arrayOfChar[i] <= '~'))
        arrayOfChar[i] = ((char)(65248 + arrayOfChar[i]));
      else
        arrayOfChar[i] = arrayOfChar[i];
    }
  }

  public static String htmlEscapeCharsToString(String paramString)
  {
    if (isEmpty(paramString))
      return paramString;
    return paramString.replaceAll("&lt;", "<").replaceAll("&gt;", ">").replaceAll("&amp;", "&").replaceAll("&quot;", "\"");
  }

  public static Integer integerValueOf(String paramString)
  {
    Integer localInteger1 = Integer.valueOf(0);
    try
    {
      Integer localInteger2 = Integer.valueOf(paramString);
      return localInteger2;
    }
    catch (NumberFormatException localNumberFormatException)
    {
    }
    return localInteger1;
  }

  public static boolean isBlank(String paramString)
  {
    return (paramString == null) || (paramString.trim().length() == 0);
  }

  public static boolean isCellphone(String paramString)
  {
    return Pattern.compile("[1][358]\\d{9}").matcher(paramString).matches();
  }

  private static boolean isChinese(char paramChar)
  {
    Character.UnicodeBlock localUnicodeBlock = Character.UnicodeBlock.of(paramChar);
    return (localUnicodeBlock == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS) || (localUnicodeBlock == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS) || (localUnicodeBlock == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A) || (localUnicodeBlock == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B) || (localUnicodeBlock == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION) || (localUnicodeBlock == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) || (localUnicodeBlock == Character.UnicodeBlock.GENERAL_PUNCTUATION);
  }

  public static boolean isChinese(String paramString)
  {
    return match("^[一-龥]*$", paramString);
  }

  public static boolean isChinese(String paramString, Integer paramInteger)
  {
    char[] arrayOfChar = paramString.toCharArray();
    for (int i = 0; ; i++)
    {
      if (i >= arrayOfChar.length)
        return false;
      if (isChinese(arrayOfChar[i]))
        return true;
    }
  }

  public static boolean isEmail(String paramString)
  {
    return Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*").matcher(paramString).matches();
  }

  public static boolean isEmpty(String paramString)
  {
    return (paramString == null) || (paramString.length() == 0) || ("null".equals(paramString));
  }

  public static boolean isEquals(String paramString1, String paramString2)
  {
    return ObjectUtils.isEquals(paramString1, paramString2);
  }

  public static boolean isLetter(String paramString)
  {
    return match("^[A-Za-z]+$", paramString);
  }

  public static boolean isNumber(String paramString)
  {
    return match("^[0-9]*$", paramString);
  }

  public static void main(String[] paramArrayOfString)
  {
  }

  private static boolean match(String paramString1, String paramString2)
  {
    return Pattern.compile(paramString1).matcher(paramString2).matches();
  }

  public static String nullStrToEmpty(String paramString)
  {
    if (paramString == null)
      paramString = "";
    return paramString;
  }

  public static String utf8Encode(String paramString)
  {
    if ((!isEmpty(paramString)) && (paramString.getBytes().length != paramString.length()));
    try
    {
      String str = URLEncoder.encode(paramString, "UTF-8");
      paramString = str;
      return paramString;
    }
    catch (UnsupportedEncodingException localUnsupportedEncodingException)
    {
      throw new RuntimeException("UnsupportedEncodingException occurred. ", localUnsupportedEncodingException);
    }
  }

  public static String utf8Encode(String paramString1, String paramString2)
  {
    if ((!isEmpty(paramString1)) && (paramString1.getBytes().length != paramString1.length()))
      try
      {
        String str = URLEncoder.encode(paramString1, "UTF-8");
        return str;
      }
      catch (UnsupportedEncodingException localUnsupportedEncodingException)
      {
        return paramString2;
      }
    return paramString1;
  }
}

/* Location:           C:\Users\Administrator\Desktop\test111\classes_dex2jar.jar
 * Qualified Name:     cn.wangjianlog.baseframework.tools.StringUtils
 * JD-Core Version:    0.6.2
 */