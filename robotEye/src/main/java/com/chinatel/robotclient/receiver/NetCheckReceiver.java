package com.chinatel.robotclient.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.chinatel.robotclient.rbuitl.ConnectInfo;
import com.chinatel.robotclient.rbuitl.IpUtil;

import cn.wangjianlog.baseframework.tools.NetWorkUtil;
import cn.wangjianlog.baseframework.util.WifiAdmin;

public class NetCheckReceiver extends BroadcastReceiver {
    public static final String BROADCAST_ACTION = "com.chinatel.robotclient.receiver.netreceiver";
    private NetCheckReceiverInterface callBack;
    private Context mContext;
    private WifiAdmin mWifiAdmin;

    public NetCheckReceiver(NetCheckReceiverInterface paramNetCheckReceiverInterface) {
        this.callBack = paramNetCheckReceiverInterface;
    }

    public void initWiFiManager(Context paramContext) {
        this.mWifiAdmin = new WifiAdmin(paramContext);
    }

    public void onReceive(Context paramContext, Intent paramIntent) {
        initWiFiManager(paramContext);
        if (paramIntent.getAction() == "android.net.conn.CONNECTIVITY_CHANGE") {
            NetworkInfo localNetworkInfo2;
            ConnectInfo localConnectInfo;
            NetworkInfo localNetworkInfo1;
            NetworkInfo localNetworkInfo3;
            NetworkInfo.State localState = null;

            Log.i("robot", "网络状态改变：" + paramIntent.getAction());

            ConnectivityManager localConnectivityManager = (ConnectivityManager) paramContext.getSystemService(Context.CONNECTIVITY_SERVICE);
            localNetworkInfo1 = localConnectivityManager.getActiveNetworkInfo();
            localNetworkInfo2 = localConnectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            localNetworkInfo3 = localConnectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            localConnectInfo = new ConnectInfo();
            localConnectInfo.setMacAddress(NetWorkUtil.getMacAddress(paramContext));
            localConnectInfo.setPhoneName(Build.MANUFACTURER);
            if (localNetworkInfo1 != null)
            localConnectInfo.setNetCode(0);
            this.callBack.noConnected(localConnectInfo);

            if (((TelephonyManager) paramContext.getSystemService(Context.TELEPHONY_SERVICE)).getCallState() != 1){
                this.callBack.mobileRinging();
                if (localNetworkInfo1.getType() != 1) {
                    localState = localNetworkInfo1.getState();
                }
            }

            if ((!localNetworkInfo3.isConnected()) || (NetworkInfo.State.CONNECTED != localState)){
//                if (this.mWifiAdmin.getSSID().contains("uoes")) {
                    Log.i("robot", this.mWifiAdmin.getSSID());
                    localConnectInfo.setRobotIP(IpUtil.int2Ip(this.mWifiAdmin.getServerIpAddress()));
                    localConnectInfo.setLocalIp(IpUtil.int2Ip(this.mWifiAdmin.getIpAddress()));
                    localConnectInfo.setNetCode(21);
                    Log.i("robot", "小优热点：==server==" + localConnectInfo.getRobotIP() + " local:" + localConnectInfo.getLocalIp());
                    this.callBack.wifiConnected(localConnectInfo);
                    Log.i("robot", "外网路由器" + this.mWifiAdmin.getSSID());
                    localConnectInfo.setNetCode(22);
//                }
            }
        }
    }
}