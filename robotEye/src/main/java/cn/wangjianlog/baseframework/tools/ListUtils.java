package cn.wangjianlog.baseframework.tools;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ListUtils
{
  public static final String DEFAULT_JOIN_SEPARATOR = ",";

  public static <V> boolean addDistinctEntry(List<V> paramList, V paramV)
  {
    if ((paramList != null) && (!paramList.contains(paramV)))
      return paramList.add(paramV);
    return false;
  }

  public static <V> int addDistinctList(List<V> paramList1, List<V> paramList2)
  {
    if ((paramList1 == null) || (isEmpty(paramList2)))
      return 0;
    int i = paramList1.size();
    Iterator localIterator = paramList2.iterator();
    while (true)
    {
      if (!localIterator.hasNext())
        return paramList1.size() - i;
      Object localObject = (Object)localIterator.next();
      if (!paramList1.contains(localObject))
        paramList1.add((V) localObject);
    }
  }

  public static <V> boolean addListNotNullValue(List<V> paramList, V paramV)
  {
    if ((paramList != null) && (paramV != null))
      return paramList.add(paramV);
    return false;
  }

  public static <V> int distinctList(List<V> paramList)
  {
    if (isEmpty(paramList))
      return 0;
    int i = paramList.size();
    int j = paramList.size();
    int k = 0;
    if (k >= j)
      return i - paramList.size();
    for (int m = k + 1; ; m++)
    {
      if (m >= j)
      {
        k++;
        break;
      }
      if (paramList.get(k).equals(paramList.get(m)))
      {
        paramList.remove(m);
        j = paramList.size();
        m--;
      }
    }
	return k;
  }

  public static <V> V getLast(List<V> paramList, V paramV)
  {
    if (paramList == null)
      return null;
    return (V) ArrayUtils.getLast(paramList.toArray(), paramV, true);
  }

  public static <V> V getNext(List<V> paramList, V paramV)
  {
    if (paramList == null)
      return null;
    return (V) ArrayUtils.getNext(paramList.toArray(), paramV, true);
  }

  public static <V> List<V> invertList(List<V> paramList)
  {
    if (isEmpty(paramList))
      return paramList;
    ArrayList localArrayList = new ArrayList(paramList.size());
    for (int i = -1 + paramList.size(); ; i--)
    {
      if (i < 0)
        return localArrayList;
      localArrayList.add(paramList.get(i));
    }
  }

  public static <V> boolean isEmpty(List<V> paramList)
  {
    return (paramList == null) || (paramList.size() == 0);
  }

  public static <V> boolean isEquals(ArrayList<V> paramArrayList1, ArrayList<V> paramArrayList2)
  {
    if (paramArrayList1 == null)
      if (paramArrayList2 != null);
    while (true)
    {
      if (paramArrayList2 == null)
        return false;
      if (paramArrayList1.size() != paramArrayList2.size())
        return false;
      for (int i = 0; i < paramArrayList1.size(); i++)
        if (!ObjectUtils.isEquals(paramArrayList1.get(i), paramArrayList2.get(i)))
          return false;
    }
  }

  public static String join(List<String> paramList)
  {
    return join(paramList, ",");
  }

  public static String join(List<String> paramList, char paramChar)
  {
    return join(paramList, new String(new char[] { paramChar }));
  }

  public static String join(List<String> paramList, String paramString)
  {
    if (isEmpty(paramList))
      return "";
    if (paramString == null)
      paramString = ",";
    StringBuilder localStringBuilder = new StringBuilder();
    for (int i = 0; ; i++)
    {
      if (i >= paramList.size())
        return localStringBuilder.toString();
      localStringBuilder.append((String)paramList.get(i));
      if (i != -1 + paramList.size())
        localStringBuilder.append(paramString);
    }
  }
}

/* Location:           C:\Users\Administrator\Desktop\test111\classes_dex2jar.jar
 * Qualified Name:     cn.wangjianlog.baseframework.tools.ListUtils
 * JD-Core Version:    0.6.2
 */