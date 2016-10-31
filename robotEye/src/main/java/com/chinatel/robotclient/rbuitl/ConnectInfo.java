package com.chinatel.robotclient.rbuitl;

import java.io.Serializable;

public class ConnectInfo
  implements Serializable
{
  public static final int CONNECT_INFO_CODE_0 = 0;
  public static final int CONNECT_INFO_CODE_1 = 1;
  public static final int CONNECT_INFO_CODE_2 = 2;
  public static final int CONNECT_INFO_CODE_21 = 21;
  public static final int CONNECT_INFO_CODE_22 = 22;
  private static final long serialVersionUID = 1L;
  private String localIp;
  private String macAddress;
  private int netCode;
  private String phoneName;
  private String robotIP;

  public String getLocalIp()
  {
    return this.localIp;
  }

  public String getMacAddress()
  {
    return this.macAddress;
  }

  public int getNetCode()
  {
    return this.netCode;
  }

  public String getPhoneName()
  {
    return this.phoneName;
  }

  public String getRobotIP()
  {
    return this.robotIP;
  }

  public void setLocalIp(String paramString)
  {
    this.localIp = paramString;
  }

  public void setMacAddress(String paramString)
  {
    this.macAddress = paramString;
  }

  public void setNetCode(int paramInt)
  {
    this.netCode = paramInt;
  }

  public void setPhoneName(String paramString)
  {
    this.phoneName = paramString;
  }

  public void setRobotIP(String paramString)
  {
    this.robotIP = paramString;
  }

  public String toString()
  {
    return "ConnectInfo [netCode=" + this.netCode + ", macAddress=" + this.macAddress + ", robotIP=" + this.robotIP + ", localIp=" + this.localIp + ", phoneName=" + this.phoneName + "]";
  }
}
