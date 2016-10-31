package com.chinatel.robotclient.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import com.chinatel.robotclient.activity.IsCalledActivity;

public class IsCallReceiver extends BroadcastReceiver
{
  public void onReceive(Context paramContext, Intent paramIntent)
  {
    Log.i("robot", "is_call");
    paramIntent.setFlags(268435456);
    paramIntent.setClass(paramContext, IsCalledActivity.class);
    paramContext.startActivity(paramIntent);
  }
}

/* Location:           C:\Users\Administrator\Desktop\test111\classes_dex2jar.jar
 * Qualified Name:     com.chinatel.robotclient.receiver.IsCallReceiver
 * JD-Core Version:    0.6.2
 */