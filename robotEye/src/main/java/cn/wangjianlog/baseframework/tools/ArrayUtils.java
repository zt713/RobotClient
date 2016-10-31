package cn.wangjianlog.baseframework.tools;

public class ArrayUtils
{
  public static int getLast(int[] paramArrayOfInt, int paramInt1, int paramInt2, boolean paramBoolean)
  {
    if (paramArrayOfInt.length == 0)
      throw new IllegalArgumentException("The length of source array must be greater than 0.");
    return ((Integer)getLast(ObjectUtils.transformIntArray(paramArrayOfInt), Integer.valueOf(paramInt1), Integer.valueOf(paramInt2), paramBoolean)).intValue();
  }

  public static long getLast(long[] paramArrayOfLong, long paramLong1, long paramLong2, boolean paramBoolean)
  {
    if (paramArrayOfLong.length == 0)
      throw new IllegalArgumentException("The length of source array must be greater than 0.");
    return ((Long)getLast(ObjectUtils.transformLongArray(paramArrayOfLong), Long.valueOf(paramLong1), Long.valueOf(paramLong2), paramBoolean)).longValue();
  }

  public static <V> V getLast(V[] paramArrayOfV, V paramV1, V paramV2, boolean paramBoolean)
  {
    if (isEmpty(paramArrayOfV))
      return paramV2;
    int i = -1;
    label60: label62: for (int j = 0; ; j++)
    {
      if (j >= paramArrayOfV.length);
      while (true)
      {
        if (i == -1)
        if (i != 0)
        if (!paramBoolean)
          break;
      /*  return paramArrayOfV[(-1 + paramArrayOfV.length)];
        if (!ObjectUtils.isEquals(paramV1, paramArrayOfV[j]))
        i = j;*/
      }
      break;
    }
    label68: return paramArrayOfV[(i - 1)];
  }

  public static <V> V getLast(V[] paramArrayOfV, V paramV, boolean paramBoolean)
  {
    return getLast(paramArrayOfV, paramV, null, paramBoolean);
  }

  public static int getNext(int[] paramArrayOfInt, int paramInt1, int paramInt2, boolean paramBoolean)
  {
    if (paramArrayOfInt.length == 0)
      throw new IllegalArgumentException("The length of source array must be greater than 0.");
    return ((Integer)getNext(ObjectUtils.transformIntArray(paramArrayOfInt), Integer.valueOf(paramInt1), Integer.valueOf(paramInt2), paramBoolean)).intValue();
  }

  public static long getNext(long[] paramArrayOfLong, long paramLong1, long paramLong2, boolean paramBoolean)
  {
    if (paramArrayOfLong.length == 0)
      throw new IllegalArgumentException("The length of source array must be greater than 0.");
    return ((Long)getNext(ObjectUtils.transformLongArray(paramArrayOfLong), Long.valueOf(paramLong1), Long.valueOf(paramLong2), paramBoolean)).longValue();
  }

  public static <V> V getNext(V[] paramArrayOfV, V paramV1, V paramV2, boolean paramBoolean)
  {
    if (isEmpty(paramArrayOfV))
      return paramV2;
    int i = -1;
   for (int j = 0; ; j++)
    {
      if (j >= paramArrayOfV.length);
      while (true)
      {
        if (i == -1)
        if (i != -1 + paramArrayOfV.length)
        if (!paramBoolean)
        return paramArrayOfV[0];
        if (!ObjectUtils.isEquals(paramV1, paramArrayOfV[j]))
        i = j;
      }
    }
//    return paramArrayOfV[(i + 1)];
  }

  public static <V> V getNext(V[] paramArrayOfV, V paramV, boolean paramBoolean)
  {
    return getNext(paramArrayOfV, paramV, null, paramBoolean);
  }

  public static <V> boolean isEmpty(V[] paramArrayOfV)
  {
    return (paramArrayOfV == null) || (paramArrayOfV.length == 0);
  }
}

/* Location:           C:\Users\Administrator\Desktop\test111\classes_dex2jar.jar
 * Qualified Name:     cn.wangjianlog.baseframework.tools.ArrayUtils
 * JD-Core Version:    0.6.2
 */