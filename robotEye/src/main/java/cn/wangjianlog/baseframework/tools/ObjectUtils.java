package cn.wangjianlog.baseframework.tools;

public class ObjectUtils
{
  public static <V> int compare(V paramV1, V paramV2)
  {
    if (paramV1 == null)
    {
      if (paramV2 == null)
        return 0;
      return -1;
    }
    if (paramV2 == null)
      return 1;
    return ((Comparable)paramV1).compareTo(paramV2);
  }

  public static boolean isEquals(Object paramObject1, Object paramObject2)
  {
    if (paramObject1 != paramObject2)
      if (paramObject1 == null)
      {
        if (paramObject2 == null);
      }
      else
        while (!paramObject1.equals(paramObject2))
          return false;
    return true;
  }

  public static int[] transformIntArray(Integer[] paramArrayOfInteger)
  {
    int[] arrayOfInt = new int[paramArrayOfInteger.length];
    for (int i = 0; ; i++)
    {
      if (i >= paramArrayOfInteger.length)
        return arrayOfInt;
      arrayOfInt[i] = paramArrayOfInteger[i].intValue();
    }
  }

  public static Integer[] transformIntArray(int[] paramArrayOfInt)
  {
    Integer[] arrayOfInteger = new Integer[paramArrayOfInt.length];
    for (int i = 0; ; i++)
    {
      if (i >= paramArrayOfInt.length)
        return arrayOfInteger;
      arrayOfInteger[i] = Integer.valueOf(paramArrayOfInt[i]);
    }
  }

  public static long[] transformLongArray(Long[] paramArrayOfLong)
  {
    long[] arrayOfLong = new long[paramArrayOfLong.length];
    for (int i = 0; ; i++)
    {
      if (i >= paramArrayOfLong.length)
        return arrayOfLong;
      arrayOfLong[i] = paramArrayOfLong[i].longValue();
    }
  }

  public static Long[] transformLongArray(long[] paramArrayOfLong)
  {
    Long[] arrayOfLong = new Long[paramArrayOfLong.length];
    for (int i = 0; ; i++)
    {
      if (i >= paramArrayOfLong.length)
        return arrayOfLong;
      arrayOfLong[i] = Long.valueOf(paramArrayOfLong[i]);
    }
  }
}

/* Location:           C:\Users\Administrator\Desktop\test111\classes_dex2jar.jar
 * Qualified Name:     cn.wangjianlog.baseframework.tools.ObjectUtils
 * JD-Core Version:    0.6.2
 */