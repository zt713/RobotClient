package com.chinatel.robotclient.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class IsCallCloseReceiver extends BroadcastReceiver
{
  public static final String BROADCAST_DISCONTED = "com.chinatel.robotclient.receiver.IsCallCloseReceiver";
  private IsCallCloseReceiverListener listener;

  public IsCallCloseReceiver(IsCallCloseReceiverListener paramIsCallCloseReceiverListener)
  {
    this.listener = paramIsCallCloseReceiverListener;
  }

  public void onReceive(Context paramContext, Intent paramIntent)
  {
    Log.i("robot", "is_call_close");
    this.listener.close();
  }

  public static abstract interface IsCallCloseReceiverListener
  {
    public abstract void close();
  }
}

/* Location:           C:\Users\Administrator\Desktop\test111\classes_dex2jar.jar
 * Qualified Name:     com.chinatel.robotclient.receiver.IsCallCloseReceiver
 * JD-Core Version:    0.6.2
 */