package cn.wangjianlog.baseframework.tools;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class MapUtils
{
  public static final String DEFAULT_KEY_AND_VALUE_PAIR_SEPARATOR = ",";
  public static final String DEFAULT_KEY_AND_VALUE_SEPARATOR = ":";

  public static <K, V> K getKeyByValue(Map<K, V> paramMap, V paramV)
  {
    if (isEmpty(paramMap));
    Map.Entry localEntry;
    do
    {
      Iterator localIterator = null;
      while (!localIterator.hasNext())
      {
        localIterator = paramMap.entrySet().iterator();
      }
      localEntry = (Map.Entry)localIterator.next();
    }
    while (!ObjectUtils.isEquals(localEntry.getValue(), paramV));
    return (K) localEntry.getKey();
  }

  public static <K, V> boolean isEmpty(Map<K, V> paramMap)
  {
    return (paramMap == null) || (paramMap.size() == 0);
  }

  public static Map<String, String> parseKeyAndValueToMap(String paramString)
  {
    return parseKeyAndValueToMap(paramString, ":", ",", true);
  }

  public static Map<String, String> parseKeyAndValueToMap(String paramString1, String paramString2, String paramString3, boolean paramBoolean)
  {
    if (StringUtils.isEmpty(paramString1))
    {
      Map<String, String> localObject = null;
	return localObject;
    }
    if (StringUtils.isEmpty(paramString2))
      paramString2 = ":";
    if (StringUtils.isEmpty(paramString3))
      paramString3 = ",";
    Object localObject = new HashMap();
    String[] arrayOfString = paramString1.split(paramString3);
    if (arrayOfString == null)
      return null;
    int i = arrayOfString.length;
    int j = 0;
    int k = 0;
    String str = null;
	if (j < i)
    {
      str = arrayOfString[j];
      if (!StringUtils.isEmpty(str))
      {
        k = str.indexOf(paramString2);
        if (k != -1)
        {
          if (!paramBoolean)
          putMapNotEmptyKey((Map)localObject, str.substring(0, k).trim(), str.substring(k + 1).trim());
        }
      }
    }
      j++;
      putMapNotEmptyKey((Map)localObject, str.substring(0, k), str.substring(k + 1));
	return null;
  }

  public static Map<String, String> parseKeyAndValueToMap(String paramString, boolean paramBoolean)
  {
    return parseKeyAndValueToMap(paramString, ":", ",", paramBoolean);
  }

  public static boolean putMapNotEmptyKey(Map<String, String> paramMap, String paramString1, String paramString2)
  {
    if ((paramMap == null) || (StringUtils.isEmpty(paramString1)))
      return false;
    paramMap.put(paramString1, paramString2);
    return true;
  }

  public static boolean putMapNotEmptyKeyAndValue(Map<String, String> paramMap, String paramString1, String paramString2)
  {
    if ((paramMap == null) || (StringUtils.isEmpty(paramString1)) || (StringUtils.isEmpty(paramString2)))
      return false;
    paramMap.put(paramString1, paramString2);
    return true;
  }

  public static boolean putMapNotEmptyKeyAndValue(Map<String, String> paramMap, String paramString1, String paramString2, String paramString3)
  {
    if ((paramMap == null) || (StringUtils.isEmpty(paramString1)))
      return false;
    if (StringUtils.isEmpty(paramString2));
    while (true)
    {
      paramMap.put(paramString1, paramString3);
      paramString3 = paramString2;
    }
  }

  public static <K, V> boolean putMapNotNullKey(Map<K, V> paramMap, K paramK, V paramV)
  {
    if ((paramMap == null) || (paramK == null))
      return false;
    paramMap.put(paramK, paramV);
    return true;
  }

  public static <K, V> boolean putMapNotNullKeyAndValue(Map<K, V> paramMap, K paramK, V paramV)
  {
    if ((paramMap == null) || (paramK == null) || (paramV == null))
      return false;
    paramMap.put(paramK, paramV);
    return true;
  }

  public static String toJson(Map<String, String> paramMap)
  {
    if ((paramMap == null) || (paramMap.size() == 0))
      return null;
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("{");
    Iterator localIterator = paramMap.entrySet().iterator();
    while (true)
    {
      if (!localIterator.hasNext())
      {
        localStringBuilder.append("}");
        return localStringBuilder.toString();
      }
      Map.Entry localEntry = (Map.Entry)localIterator.next();
      localStringBuilder.append("\"").append((String)localEntry.getKey()).append("\":").append((String)localEntry.getValue());
      if (localIterator.hasNext())
        localStringBuilder.append(",");
    }
  }
}

/* Location:           C:\Users\Administrator\Desktop\test111\classes_dex2jar.jar
 * Qualified Name:     cn.wangjianlog.baseframework.tools.MapUtils
 * JD-Core Version:    0.6.2
 */