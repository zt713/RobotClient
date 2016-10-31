package cn.wangjianlog.baseframework.tools;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SecurityUtil
{
  public static String md5(String paramString)
    throws NoSuchAlgorithmException
  {
    MessageDigest localMessageDigest = MessageDigest.getInstance("MD5");
    localMessageDigest.update(paramString.getBytes());
    return new BigInteger(1, localMessageDigest.digest()).toString(16);
  }

  public static String md5(String paramString1, String paramString2)
    throws NoSuchAlgorithmException
  {
    MessageDigest localMessageDigest = MessageDigest.getInstance("MD5");
    localMessageDigest.update(paramString1.getBytes());
    localMessageDigest.update(paramString2.getBytes());
    return new BigInteger(1, localMessageDigest.digest()).toString(16);
  }
}

/* Location:           C:\Users\Administrator\Desktop\test111\classes_dex2jar.jar
 * Qualified Name:     cn.wangjianlog.baseframework.tools.SecurityUtil
 * JD-Core Version:    0.6.2
 */