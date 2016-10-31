package cn.wangjianlog.baseframework.tools;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class ParcelUtils
{
  public static boolean readBoolean(Parcel paramParcel)
  {
    return paramParcel.readInt() == 1;
  }

  public static <K extends Parcelable, V extends Parcelable> Map<K, V> readHashMap(Parcel paramParcel, ClassLoader paramClassLoader)
  {
    Object localObject = null;
    if (paramParcel == null);
    while (true)
    {
      return (Map<K, V>) localObject;
    }
  }

  public static Map<String, String> readHashMapStringAndString(Parcel paramParcel)
  {
    Object localObject = null;
    if (paramParcel == null);
    while (true)
    {
      int i = paramParcel.readInt();
      localObject = null;
      if (i != -1)
      {
        localObject = new HashMap();
        for (int j = 0; j < i; j++)
          ((Map)localObject).put(paramParcel.readString(), paramParcel.readString());
      }
    }
  }

  public static <V extends Parcelable> Map<String, V> readHashMapStringKey(Parcel paramParcel, ClassLoader paramClassLoader)
  {
    Object localObject = null;
    if (paramParcel == null);
    while (true)
    {
      int i = paramParcel.readInt();
      localObject = null;
      if (i != -1)
      {
        localObject = new HashMap();
        for (int j = 0; j < i; j++)
          ((Map)localObject).put(paramParcel.readString(), paramParcel.readParcelable(paramClassLoader));
      }
    }
  }

  public static void writeBoolean(boolean paramBoolean, Parcel paramParcel)
  {
    if (paramBoolean);
    for (int i = 1; ; i = 0)
    {
      paramParcel.writeInt(i);
      return;
    }
  }

  public static <K extends Parcelable, V extends Parcelable> void writeHashMap(Map<K, V> paramMap, Parcel paramParcel, int paramInt)
  {
    if (paramMap != null)
    {
      paramParcel.writeInt(paramMap.size());
      Iterator localIterator = paramMap.entrySet().iterator();
      while (true)
      {
        if (!localIterator.hasNext())
          return;
        Map.Entry localEntry = (Map.Entry)localIterator.next();
        paramParcel.writeParcelable((Parcelable)localEntry.getKey(), paramInt);
        paramParcel.writeParcelable((Parcelable)localEntry.getValue(), paramInt);
      }
    }
    paramParcel.writeInt(-1);
  }

  public static void writeHashMapStringAndString(Map<String, String> paramMap, Parcel paramParcel, int paramInt)
  {
    if (paramMap != null)
    {
      paramParcel.writeInt(paramMap.size());
      Iterator localIterator = paramMap.entrySet().iterator();
      while (true)
      {
        if (!localIterator.hasNext())
          return;
        Map.Entry localEntry = (Map.Entry)localIterator.next();
        paramParcel.writeString((String)localEntry.getKey());
        paramParcel.writeString((String)localEntry.getValue());
      }
    }
    paramParcel.writeInt(-1);
  }

  public static <V extends Parcelable> void writeHashMapStringKey(Map<String, V> paramMap, Parcel paramParcel, int paramInt)
  {
    if (paramMap != null)
    {
      paramParcel.writeInt(paramMap.size());
      Iterator localIterator = paramMap.entrySet().iterator();
      while (true)
      {
        if (!localIterator.hasNext())
          return;
        Map.Entry localEntry = (Map.Entry)localIterator.next();
        paramParcel.writeString((String)localEntry.getKey());
        paramParcel.writeParcelable((Parcelable)localEntry.getValue(), paramInt);
      }
    }
    paramParcel.writeInt(-1);
  }
}

/* Location:           C:\Users\Administrator\Desktop\test111\classes_dex2jar.jar
 * Qualified Name:     cn.wangjianlog.baseframework.tools.ParcelUtils
 * JD-Core Version:    0.6.2
 */