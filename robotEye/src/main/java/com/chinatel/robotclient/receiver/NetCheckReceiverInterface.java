package com.chinatel.robotclient.receiver;

import com.chinatel.robotclient.rbuitl.ConnectInfo;

public abstract interface NetCheckReceiverInterface
{
  public abstract void mobileConnected(ConnectInfo paramConnectInfo);

  public abstract void mobileRinging();

  public abstract void noConnected(ConnectInfo paramConnectInfo);

  public abstract void wifiConnected(ConnectInfo paramConnectInfo);
}
