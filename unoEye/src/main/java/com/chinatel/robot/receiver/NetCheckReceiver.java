package com.chinatel.robot.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.NetworkInfo;
import android.net.wifi.p2p.WifiP2pManager;
import android.net.wifi.p2p.WifiP2pManager.Channel;
import android.os.Parcelable;
import android.util.Log;

public class NetCheckReceiver extends BroadcastReceiver {
	private WifiP2pManager.Channel mChannel;
	private Context mContext;
	private WifiP2pManager mManager;
	private NetCheckReceiverInterface netInterface;

	public NetCheckReceiver(
			NetCheckReceiverInterface paramNetCheckReceiverInterface) {
		this.netInterface = paramNetCheckReceiverInterface;
	}

	public void onReceive(Context paramContext, Intent paramIntent) {
		this.mManager = ((WifiP2pManager) paramContext
				.getSystemService("wifip2p"));
		this.mChannel = this.mManager.initialize(paramContext,
				paramContext.getMainLooper(), null);
		String str = paramIntent.getAction();
		Log.i("robot", str);
		if ("android.net.wifi.p2p.STATE_CHANGED".equals(str))
			if (paramIntent.getIntExtra("wifi_p2p_state", -1) == 2)
				;
		Object localParcelable;
		// label62: Parcelable localParcelable;
		do {
			do {
				do {
					// break label62;
					// do
					// return;
					/*
					 * while ("android.net.wifi.p2p.PEERS_CHANGED".equals(str));
					 * if
					 * (!"android.net.wifi.p2p.CONNECTION_STATE_CHANGE".equals
					 * (str)) break;
					 */
				} while (this.mManager == null);
				NetworkInfo localNetworkInfo = (NetworkInfo) paramIntent
						.getParcelableExtra("networkInfo");
				Log.i("robot", "");
				if (localNetworkInfo.isConnected()) {
					this.netInterface.apIsEnabled(null);
					return;
				}
				this.netInterface.apIsDisEnabled(null);
				return;
			} while (("android.net.wifi.p2p.THIS_DEVICE_CHANGED".equals(str))
					|| (!"android.net.wifi.STATE_CHANGE".equals(str)));
			// localParcelable = paramIntent.getParcelableExtra("networkInfo");
		} while ((localParcelable == null)
				|| (!((NetworkInfo) localParcelable).isAvailable()));
		// this.netInterface.wifiAviable();
	}
}

/*
 * Location:
 * C:\Users\Administrator\Desktop\小优\U03S源码\机器人本体.apk\classes_dex2jar.jar
 * Qualified Name: com.chinatel.robot.receiver.NetCheckReceiver JD-Core Version:
 * 0.6.2
 */