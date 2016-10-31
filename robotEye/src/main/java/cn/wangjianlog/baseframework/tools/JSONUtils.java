package cn.wangjianlog.baseframework.tools;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JSONUtils
{
  public static boolean getBoolean(String paramString1, String paramString2, Boolean paramBoolean)
  {
    if (StringUtils.isEmpty(paramString1))
      return paramBoolean.booleanValue();
    try
    {
      boolean bool = getBoolean(new JSONObject(paramString1), paramString2, paramBoolean);
      return bool;
    }
    catch (JSONException localJSONException)
    {
      localJSONException.printStackTrace();
    }
    return paramBoolean.booleanValue();
  }

  public static boolean getBoolean(JSONObject paramJSONObject, String paramString, Boolean paramBoolean)
  {
    if ((paramJSONObject == null) || (StringUtils.isEmpty(paramString)))
      return paramBoolean.booleanValue();
    try
    {
      boolean bool = paramJSONObject.getBoolean(paramString);
      return bool;
    }
    catch (JSONException localJSONException)
    {
      localJSONException.printStackTrace();
    }
    return paramBoolean.booleanValue();
  }

  public static double getDouble(String paramString1, String paramString2, double paramDouble)
  {
    return getDouble(paramString1, paramString2, Double.valueOf(paramDouble)).doubleValue();
  }

  public static double getDouble(JSONObject paramJSONObject, String paramString, double paramDouble)
  {
    return getDouble(paramJSONObject, paramString, Double.valueOf(paramDouble)).doubleValue();
  }

  public static Double getDouble(String paramString1, String paramString2, Double paramDouble)
  {
    if (StringUtils.isEmpty(paramString1))
      return paramDouble;
    try
    {
      Double localDouble = getDouble(new JSONObject(paramString1), paramString2, paramDouble);
      return localDouble;
    }
    catch (JSONException localJSONException)
    {
      localJSONException.printStackTrace();
    }
    return paramDouble;
  }

  public static Double getDouble(JSONObject paramJSONObject, String paramString, Double paramDouble)
  {
    if ((paramJSONObject == null) || (StringUtils.isEmpty(paramString)))
      return paramDouble;
    try
    {
      Double localDouble = Double.valueOf(paramJSONObject.getDouble(paramString));
      return localDouble;
    }
    catch (JSONException localJSONException)
    {
      localJSONException.printStackTrace();
    }
    return paramDouble;
  }

  public static int getInt(String paramString1, String paramString2, int paramInt)
  {
    return getInt(paramString1, paramString2, Integer.valueOf(paramInt)).intValue();
  }

  public static int getInt(JSONObject paramJSONObject, String paramString, int paramInt)
  {
    return getInt(paramJSONObject, paramString, Integer.valueOf(paramInt)).intValue();
  }

  public static Integer getInt(String paramString1, String paramString2, Integer paramInteger)
  {
    if (StringUtils.isEmpty(paramString1))
      return paramInteger;
    try
    {
      Integer localInteger = getInt(new JSONObject(paramString1), paramString2, paramInteger);
      return localInteger;
    }
    catch (JSONException localJSONException)
    {
      localJSONException.printStackTrace();
    }
    return paramInteger;
  }

  public static Integer getInt(JSONObject paramJSONObject, String paramString, Integer paramInteger)
  {
    if ((paramJSONObject == null) || (StringUtils.isEmpty(paramString)))
      return paramInteger;
    try
    {
      Integer localInteger = Integer.valueOf(paramJSONObject.getInt(paramString));
      return localInteger;
    }
    catch (JSONException localJSONException)
    {
      localJSONException.printStackTrace();
    }
    return paramInteger;
  }

  public static JSONArray getJSONArray(String paramString1, String paramString2, JSONArray paramJSONArray)
  {
    if (StringUtils.isEmpty(paramString1))
      return paramJSONArray;
    try
    {
      JSONArray localJSONArray = getJSONArray(new JSONObject(paramString1), paramString2, paramJSONArray);
      return localJSONArray;
    }
    catch (JSONException localJSONException)
    {
      localJSONException.printStackTrace();
    }
    return paramJSONArray;
  }

  public static JSONArray getJSONArray(JSONObject paramJSONObject, String paramString, JSONArray paramJSONArray)
  {
    if ((paramJSONObject == null) || (StringUtils.isEmpty(paramString)))
      return paramJSONArray;
    try
    {
      JSONArray localJSONArray = paramJSONObject.getJSONArray(paramString);
      return localJSONArray;
    }
    catch (JSONException localJSONException)
    {
      localJSONException.printStackTrace();
    }
    return paramJSONArray;
  }

  public static JSONObject getJSONObject(String paramString1, String paramString2, JSONObject paramJSONObject)
  {
    if (StringUtils.isEmpty(paramString1))
      return paramJSONObject;
    try
    {
      JSONObject localJSONObject = getJSONObject(new JSONObject(paramString1), paramString2, paramJSONObject);
      return localJSONObject;
    }
    catch (JSONException localJSONException)
    {
      localJSONException.printStackTrace();
    }
    return paramJSONObject;
  }

  public static JSONObject getJSONObject(JSONObject paramJSONObject1, String paramString, JSONObject paramJSONObject2)
  {
    if ((paramJSONObject1 == null) || (StringUtils.isEmpty(paramString)))
      return paramJSONObject2;
    try
    {
      JSONObject localJSONObject = paramJSONObject1.getJSONObject(paramString);
      return localJSONObject;
    }
    catch (JSONException localJSONException)
    {
      localJSONException.printStackTrace();
    }
    return paramJSONObject2;
  }

  public static long getLong(String paramString1, String paramString2, long paramLong)
  {
    return getLong(paramString1, paramString2, Long.valueOf(paramLong)).longValue();
  }

  public static long getLong(JSONObject paramJSONObject, String paramString, long paramLong)
  {
    return getLong(paramJSONObject, paramString, Long.valueOf(paramLong)).longValue();
  }

  public static Long getLong(String paramString1, String paramString2, Long paramLong)
  {
    if (StringUtils.isEmpty(paramString1))
      return paramLong;
    try
    {
      Long localLong = getLong(new JSONObject(paramString1), paramString2, paramLong);
      return localLong;
    }
    catch (JSONException localJSONException)
    {
      localJSONException.printStackTrace();
    }
    return paramLong;
  }

  public static Long getLong(JSONObject paramJSONObject, String paramString, Long paramLong)
  {
    if ((paramJSONObject == null) || (StringUtils.isEmpty(paramString)))
      return paramLong;
    try
    {
      Long localLong = Long.valueOf(paramJSONObject.getLong(paramString));
      return localLong;
    }
    catch (JSONException localJSONException)
    {
      localJSONException.printStackTrace();
    }
    return paramLong;
  }

  public static Map<String, String> getMap(String paramString1, String paramString2)
  {
    if (paramString1 == null)
      return null;
    if (paramString1.length() == 0)
      return new HashMap();
    try
    {
      Map localMap = getMap(new JSONObject(paramString1), paramString2);
      return localMap;
    }
    catch (JSONException localJSONException)
    {
      localJSONException.printStackTrace();
    }
    return null;
  }

  public static Map<String, String> getMap(JSONObject paramJSONObject, String paramString)
  {
    return parseKeyAndValueToMap(getString(paramJSONObject, paramString, null));
  }

  public static String getString(String paramString1, String paramString2, String paramString3)
  {
    if (StringUtils.isEmpty(paramString1))
      return paramString3;
    try
    {
      String str = getString(new JSONObject(paramString1), paramString2, paramString3);
      return str;
    }
    catch (JSONException localJSONException)
    {
      localJSONException.printStackTrace();
    }
    return paramString3;
  }

  public static String getString(JSONObject paramJSONObject, String paramString1, String paramString2)
  {
    if ((paramJSONObject == null) || (StringUtils.isEmpty(paramString1)))
      return paramString2;
    try
    {
      String str = paramJSONObject.getString(paramString1);
      return str;
    }
    catch (JSONException localJSONException)
    {
      localJSONException.printStackTrace();
    }
    return paramString2;
  }

  public static String[] getStringArray(String paramString1, String paramString2, String[] paramArrayOfString)
  {
    if (StringUtils.isEmpty(paramString1))
      return paramArrayOfString;
    try
    {
      String[] arrayOfString = getStringArray(new JSONObject(paramString1), paramString2, paramArrayOfString);
      return arrayOfString;
    }
    catch (JSONException localJSONException)
    {
      localJSONException.printStackTrace();
    }
    return paramArrayOfString;
  }

  public static String[] getStringArray(JSONObject paramJSONObject, String paramString, String[] paramArrayOfString)
  {
    if ((paramJSONObject == null) || (StringUtils.isEmpty(paramString)));
    while (true)
    {
      try
      {
        JSONArray localJSONArray = paramJSONObject.getJSONArray(paramString);
        if (localJSONArray != null)
        {
          String[] arrayOfString = new String[localJSONArray.length()];
          for (int i = 0; ; i++)
          {
            if (i >= localJSONArray.length())
              return arrayOfString;
            arrayOfString[i] = localJSONArray.getString(i);
          }
        }
      }
      catch (JSONException localJSONException)
      {
        localJSONException.printStackTrace();
      }
    }
  }

  public static Map<String, String> parseKeyAndValueToMap(String paramString)
  {
    if (StringUtils.isEmpty(paramString))
      return null;
    try
    {
      Map localMap = parseKeyAndValueToMap(new JSONObject(paramString));
      return localMap;
    }
    catch (JSONException localJSONException)
    {
      localJSONException.printStackTrace();
    }
    return null;
  }

  public static Map<String, String> parseKeyAndValueToMap(JSONObject paramJSONObject)
  {
    Object localObject;
    if (paramJSONObject == null)
      localObject = null;
    while (true)
    {
      localObject = new HashMap();
      Iterator localIterator = paramJSONObject.keys();
      while (localIterator.hasNext())
      {
        String str = (String)localIterator.next();
        MapUtils.putMapNotEmptyKey((Map)localObject, str, getString(paramJSONObject, str, ""));
      }
    }
  }
}

/* Location:           C:\Users\Administrator\Desktop\test111\classes_dex2jar.jar
 * Qualified Name:     cn.wangjianlog.baseframework.tools.JSONUtils
 * JD-Core Version:    0.6.2
 */