package com.chinatel.robot.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import com.chinatel.robot.Util.StringUtils;

public class CallVideoReceiver extends BroadcastReceiver {
	public static final String BROADCAST_ACTION_ROBOT = "com.chinatel.robot_123.receiver.VIDEO_RECIEVER";
	public static final String CLOSE = "close";
	public static final String CODE = "code";
	public static final String NAME = "callVideo";
	public static final String ON_VIDEO = "onVideo";
	private CallVideoReceiverCallBack callBack;

	public CallVideoReceiver(
			CallVideoReceiverCallBack paramCallVideoReceiverCallBack) {
		this.callBack = paramCallVideoReceiverCallBack;
	}

	public void onReceive(Context paramContext, Intent paramIntent) {
		String str = paramIntent.getStringExtra("callVideo");
		Log.i("robot", str);
		if ((!StringUtils.isEmpty(str)) && (str.equals("onVideo")))
			this.callBack.showVideo();
		while (!str.equals("close"))
			return;
		this.callBack.close();
	}

	public static abstract interface CallVideoReceiverCallBack {
		public abstract void close();

		public abstract void showVideo();
	}
}