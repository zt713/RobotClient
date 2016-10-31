package cn.wangjianlog.baseframework.tools;

import java.util.Random;

public class RandomUtils
{
  public static final String CAPITAL_LETTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
  public static final String LETTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
  public static final String LOWER_CASE_LETTERS = "abcdefghijklmnopqrstuvwxyz";
  public static final String NUMBERS = "0123456789";
  public static final String NUMBERS_AND_LETTERS = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

  public static int getIntRandom(int paramInt1, int paramInt2)
  {
    return (int)(Math.random() * (paramInt2 + 1 - paramInt1) + paramInt1);
  }

  public static String getRandom(String paramString, int paramInt)
  {
    if (StringUtils.isEmpty(paramString))
      return null;
    return getRandom(paramString.toCharArray(), paramInt);
  }

  public static String getRandom(char[] paramArrayOfChar, int paramInt)
  {
    if ((paramArrayOfChar == null) || (paramArrayOfChar.length == 0) || (paramInt < 0))
      return null;
    StringBuilder localStringBuilder = new StringBuilder(paramInt);
    Random localRandom = new Random();
    for (int i = 0; ; i++)
    {
      if (i >= paramInt)
        return localStringBuilder.toString();
      localStringBuilder.append(paramArrayOfChar[localRandom.nextInt(paramArrayOfChar.length)]);
    }
  }

  public static String getRandomCapitalLetters(int paramInt)
  {
    return getRandom("ABCDEFGHIJKLMNOPQRSTUVWXYZ", paramInt);
  }

  public static String getRandomLetters(int paramInt)
  {
    return getRandom("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ", paramInt);
  }

  public static String getRandomLowerCaseLetters(int paramInt)
  {
    return getRandom("abcdefghijklmnopqrstuvwxyz", paramInt);
  }

  public static String getRandomNumbers(int paramInt)
  {
    return getRandom("0123456789", paramInt);
  }

  public static String getRandomNumbersAndLetters(int paramInt)
  {
    return getRandom("0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ", paramInt);
  }
}

/* Location:           C:\Users\Administrator\Desktop\test111\classes_dex2jar.jar
 * Qualified Name:     cn.wangjianlog.baseframework.tools.RandomUtils
 * JD-Core Version:    0.6.2
 */