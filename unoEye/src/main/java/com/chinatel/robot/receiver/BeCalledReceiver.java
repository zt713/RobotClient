package com.chinatel.robot.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import com.chinatel.robot.Activity.SpeakingActivity;

public class BeCalledReceiver extends BroadcastReceiver {
	public void onReceive(Context paramContext, Intent paramIntent) {
		Log.i("robot", "is_call");
		paramIntent.setFlags(268435456);
		paramIntent.setClass(paramContext, SpeakingActivity.class);
		paramContext.startActivity(paramIntent);
	}
}

/*
 * Location:
 * C:\Users\Administrator\Desktop\小优\U03S源码\机器人本体.apk\classes_dex2jar.jar
 * Qualified Name: com.chinatel.robot.receiver.BeCalledReceiver JD-Core Version:
 * 0.6.2
 */