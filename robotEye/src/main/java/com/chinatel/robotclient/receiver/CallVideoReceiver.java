package com.chinatel.robotclient.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import cn.wangjianlog.baseframework.tools.StringUtils;

public class CallVideoReceiver extends BroadcastReceiver
{
  public static final String BROADCAST_ACTION = "com.chinatel.robotclient.receiver.VIDEO_RECIEVER";
  public static final String BUSY = "busy";
  public static final String CLOSE = "close";
  public static final String NAME = "callVideo";
  public static final String ON_VIDEO = "onVideo";
  private CallVideoReceiverCallBack callBack;

  public CallVideoReceiver(CallVideoReceiverCallBack paramCallVideoReceiverCallBack)
  {
    this.callBack = paramCallVideoReceiverCallBack;
  }

  public void onReceive(Context paramContext, Intent paramIntent)
  {
    String str = paramIntent.getStringExtra("callVideo");
    Log.i("robot", "<CallVideoReceiver>msg:" + str);
    if(str.equals("onVideo")) {
      this.callBack.showVideo();
      return;
    }else if(str.equals("close")){
      this.callBack.close();
      return;
    }else if("busy".equals(str)){
      this.callBack.busy();
      return;
    }
  }

  public static abstract interface CallVideoReceiverCallBack
  {
    public abstract void busy();

    public abstract void close();

    public abstract void showVideo();
  }
}

/* Location:           C:\Users\Administrator\Desktop\test111\classes_dex2jar.jar
 * Qualified Name:     com.chinatel.robotclient.receiver.CallVideoReceiver
 * JD-Core Version:    0.6.2
 */