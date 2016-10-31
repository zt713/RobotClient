package com.chinatel.robotclient.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import com.chinatel.robotclient.activity.HomeActivity;

public class BootBroadcastReceiver extends BroadcastReceiver
{
  static final String action_boot = "android.intent.action.BOOT_COMPLETED";

  public void onReceive(Context paramContext, Intent paramIntent)
  {
    if (paramIntent.getAction().equals("android.intent.action.BOOT_COMPLETED"))
    {
      Intent localIntent = new Intent(paramContext, HomeActivity.class);
      localIntent.addFlags(268435456);
      paramContext.startActivity(localIntent);
    }
  }
}

/* Location:           C:\Users\Administrator\Desktop\test111\classes_dex2jar.jar
 * Qualified Name:     com.chinatel.robotclient.receiver.BootBroadcastReceiver
 * JD-Core Version:    0.6.2
 */