package cn.wangjianlog.baseframework;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import com.chinatel.robotclient.rbuitl.ConnectInfo;
import com.chinatel.robotclient.receiver.NetCheckReceiver;
import com.chinatel.robotclient.receiver.NetCheckReceiverInterface;

import org.json.JSONException;
import org.json.JSONObject;

import jni.http.HttpManager;
import jni.http.HttpResult;
import jni.http.RtcHttpClient;
import jni.util.Utils;
import rtc.sdk.clt.RtcClientImpl;
import rtc.sdk.common.RtcConst;
import rtc.sdk.common.SdkSettings;
import rtc.sdk.core.RtcRules;
import rtc.sdk.iface.ClientListener;
import rtc.sdk.iface.Connection;
import rtc.sdk.iface.ConnectionListener;
import rtc.sdk.iface.Device;
import rtc.sdk.iface.DeviceListener;
import rtc.sdk.iface.RtcClient;

public class MainApplication extends Application
{
//  private static String APP_ID = "70115";
//  private static String APP_KEY = "7170a086-419f-42c3-a64e-a1df478bd935";
  private static String APP_ID = "71184";
  private static String APP_KEY = "2cd20a4d-4329-43d6-a5bd-ef3eaf19bb3a";  
  private static MainApplication instance;
  private static DisplayMetrics mDisplayMetrics;
  private String LOGTAG = "canbot";
  public ConnectInfo currentNetInfo;
  private String imContent = "";
  public boolean isAvailable = false;
  DeviceListener mAListener = new DeviceListener()
  {
    private void ChangeNetWork() {
      //test1
      //test21
      Utils.PrintLog(5, MainApplication.this.LOGTAG, "ChangeNetWork");

    }
    private void onNoNetWork()
    {
      Utils.PrintLog(5, MainApplication.this.LOGTAG, "onNoNetWork");
      MainApplication.this.isAvailable = false;
      if (MainApplication.this.mCall != null)
      {
        MainApplication.this.mCall.disconnect();
        MainApplication.this.mCall = null;
        MainApplication.this.connectSendBro("onDisconnected");
        MainApplication.this.callVideoSendBro("close");
        Intent localIntent = new Intent();
        localIntent.setAction("com.chinatel.robotclient.receiver.IsCallCloseReceiver");
        MainApplication.this.sendBroadcast(localIntent);
        Toast.makeText(MainApplication.this.getApplicationContext(), "网络断开，关闭通话界面", Toast.LENGTH_SHORT).show();
        return;
      }
      Toast.makeText(MainApplication.this.getApplicationContext(), "网络断开，将无法进行连接", Toast.LENGTH_SHORT).show();
    }

    private void onPoorNetWork()
    {
      Utils.PrintLog(5, MainApplication.this.LOGTAG, "onPoorNetWork");
    }

    public void onDeviceStateChanged(final int paramAnonymousInt)
    {
      Utils.PrintLog(5, MainApplication.this.LOGTAG, "onDeviceStateChanged,result=" + paramAnonymousInt);
      if (paramAnonymousInt == 200)
      {
        if (!MainApplication.this.isAvailable) {
          MainApplication.this.mHandler.post(new Runnable() {
            public void run() {
              Toast.makeText(MainApplication.this.getApplicationContext(), "千里眼启动(重连)成功！", Toast.LENGTH_SHORT).show();
            }
          });
          MainApplication.this.isAvailable = true;
          return;
        }
      }
      MainApplication.this.isAvailable = false;
      MainApplication.this.mHandler.post(new Runnable()
      {
        public void run()
        {
          Toast.makeText(MainApplication.this.getApplicationContext(), "连接服务器失败: " + paramAnonymousInt + "，将不能进行监控和呼叫", Toast.LENGTH_SHORT).show();
        }
      });
	  	if (paramAnonymousInt == -1001) {
			onNoNetWork();
			return;
		}
		if (paramAnonymousInt == -1002) {
			ChangeNetWork();
			return;
		}
		if (paramAnonymousInt == -1003) {
			onPoorNetWork();
			return;
		}
      if (paramAnonymousInt == -1004)
      {
        Utils.PrintLog(5, MainApplication.this.LOGTAG, "onDeviceStateChanged,ReLoginNetwork");
        return;
      }
      if (paramAnonymousInt == -1500)
      {
        MainApplication.this.isAvailable = false;
        Utils.PrintLog(5, MainApplication.this.LOGTAG, "onDeviceStateChanged,DeviceEvt_KickedOff");
        return;
      }
      if (paramAnonymousInt == -1501)
      {
        Utils.PrintLog(5, MainApplication.this.LOGTAG, "onDeviceStateChanged,DeviceEvt_MultiLogin");
        return;
      }

    }

    public void onNewCall(Connection paramAnonymousConnection)
    {
      Utils.PrintLog(5, MainApplication.this.LOGTAG, "onNewCall,call=" + paramAnonymousConnection.info());
      if (MainApplication.this.mCall != null)
      {
        paramAnonymousConnection.reject();
        Utils.PrintLog(5, MainApplication.this.LOGTAG, "onNewCall,reject call");
        return;
      }
      MainApplication.this.mCall = paramAnonymousConnection;
      paramAnonymousConnection.setIncomingListener(MainApplication.this.mCListener);
      MainApplication.this.mHandler.postDelayed(new Runnable()
      {
        public void run()
        {
          Intent localIntent = new Intent();
          localIntent.setAction("com.chinatel.robotclient.IS_CALL_RECEIVER");
          MainApplication.this.sendBroadcast(localIntent);
        }
      }
      , 0L);
    }

    public void onQueryStatus(int paramAnonymousInt, String paramAnonymousString)
    {
    }

    public void onReceiveIm(String paramAnonymousString1, String paramAnonymousString2, String paramAnonymousString3)
    {
      MainApplication.this.imContent = paramAnonymousString3;
      if (paramAnonymousString2.equals("text/cmd"))
        MainApplication.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            Toast localToast = Toast.makeText(MainApplication.this.getApplicationContext(), MainApplication.this.imContent, Toast.LENGTH_SHORT);
            localToast.setGravity(17, 0, 0);
            localToast.show();
          }
        });
      Utils.PrintLog(5, MainApplication.this.LOGTAG, "onReceiveIm:" + paramAnonymousString1 + paramAnonymousString2 + paramAnonymousString3);
    }

    public void onSendIm(int paramAnonymousInt)
    {
      if (paramAnonymousInt == 200)
      {
        Utils.PrintLog(5, MainApplication.this.LOGTAG, "控制命令发送成功");
        Log.i("robot", "headUp");
        return;
      }
      Utils.PrintLog(5, MainApplication.this.LOGTAG, "控制命令发送失败:" + paramAnonymousInt);
    }
  };
  private Device mAcc = null;
  ConnectionListener mCListener = new ConnectionListener()
  {
    public void onConnected()
    {
      MainApplication.this.mHandler.postDelayed(new Runnable()
      {
        public void run()
        {
        }
      }
      , 1000L);
    }

    public void onConnecting()
    {
    }

    public void onDisconnected(final int paramAnonymousInt)
    {
      Utils.PrintLog(5, MainApplication.this.LOGTAG, "onDisconnected timerDur" + MainApplication.this.mCall.getCallDuration());
      MainApplication.this.mCall = null;
      MainApplication.this.mHandler.post(new Runnable()
      {
        public void run()
        {
          MainApplication.this.connectSendBro("onDisconnected");
          if ((paramAnonymousInt == 486) || (paramAnonymousInt == 603))
            MainApplication.this.callVideoSendBro("busy");
            Intent localIntent = new Intent();
            localIntent.setAction("com.chinatel.robotclient.receiver.IsCallCloseReceiver");
            MainApplication.this.sendBroadcast(localIntent);
            MainApplication.this.callVideoSendBro("close");
        }
      });
    }

    public void onNetStatus(int paramAnonymousInt, String paramAnonymousString)
    {
    }

    public void onVideo()
    {
      Utils.PrintLog(5, MainApplication.this.LOGTAG, "onVideo");
      MainApplication.this.mHandler.postDelayed(new Runnable()
      {
        public void run()
        {
          MainApplication.this.connectSendBro("connected");
          MainApplication.this.callVideoSendBro("onVideo");
        }
      }
      , 0L);
    }
  };
  public Connection mCall = null;
  private RtcClient mClt = null;
  private Handler mHandler = new Handler()
  {
  };
  private NetCheckReceiver mReceiver;
  private WindowManager mWindowManager;
  private String robotIp = "";
  private String tName = "";
  private String userId = "";

  private void OnRegister(String paramString1, String paramString2)
  {
    Utils.PrintLog(5, this.LOGTAG, "OnRegister:" + paramString1 + "spwd:" + paramString2);
    try
    {
      JSONObject localJSONObject = SdkSettings.defaultDeviceSetting();
      localJSONObject.put("acc.pwd", paramString2);
      Utils.PrintLog(5, this.LOGTAG, "user:" + paramString1 + "token:" + paramString2);
      localJSONObject.put("acc.appid", APP_ID);
      localJSONObject.put("acc.user", paramString1);
      localJSONObject.put("acc.type", RtcConst.UEType_Current);
      localJSONObject.put("reg.retry", 9);
      this.mAcc = this.mClt.createDevice(localJSONObject.toString(), this.mAListener);
      return;
    }
    catch (JSONException localJSONException)
    {
      localJSONException.printStackTrace();
    }
  }

  public static float getDensity()
  {
    return mDisplayMetrics.density;
  }

  public static float getHeight()
  {
    return mDisplayMetrics.heightPixels;
  }

  public static MainApplication getInstance()
  {
    return instance;
  }

  public static float getWidth()
  {
    return mDisplayMetrics.widthPixels;
  }

  private void initRtcClientImpl()
  {
    Utils.PrintLog(5, this.LOGTAG, "initRtcClientImpl()");
    this.mClt = new RtcClientImpl();
    this.mClt.initialize(getApplicationContext(), new ClientListener()
    {
      public void onInit(int paramAnonymousInt)
      {
        Utils.PrintLog(5, MainApplication.this.LOGTAG, "onInit,result=" + paramAnonymousInt);
        if (paramAnonymousInt == 0)
        {
          MainApplication.this.mClt.setAudioCodec(RtcConst.ACodec_OPUS);
          MainApplication.this.mClt.setVideoCodec(RtcConst.VCodec_VP8);
          MainApplication.this.mClt.setVideoAttr(RtcConst.Video_HD);
          MainApplication.this.login();
          return;
        }
        MainApplication.this.mClt.release();
        MainApplication.this.mClt = null;
        MainApplication.this.mHandler.post(new Runnable()
        {
          public void run()
          {
            Toast.makeText(MainApplication.this.getApplicationContext(), "千里眼启动失败！\n请检查internet连接和系统时间！", Toast.LENGTH_SHORT).show();
          }
        });
      }
    });
  }

  public static boolean netConnectState()
  {
    NetworkInfo localNetworkInfo = ((ConnectivityManager)instance.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
    return (localNetworkInfo != null) && (localNetworkInfo.isAvailable());
  }

  private void opt_getToken()
  {
    RtcConst.UEAPPID_Current = RtcConst.UEAPPID_Self;
    JSONObject localJSONObject1 = HttpManager.getInstance().CreateTokenJson(0, this.userId, RtcHttpClient.grantedCapabiltyID, "");
    HttpResult localHttpResult = HttpManager.getInstance().getCapabilityToken(localJSONObject1, APP_ID, APP_KEY);
    Utils.PrintLog(5, this.LOGTAG, "获取token:" + localHttpResult.getStatus() + " reason:" + localHttpResult.getErrorMsg());
    JSONObject localJSONObject2 = (JSONObject)localHttpResult.getObject();
    if ((localJSONObject2 != null) && (!localJSONObject2.isNull("code")))
      try
      {
        String str1 = localJSONObject2.getString("code");
        String str2 = localJSONObject2.getString("reason");
        Utils.PrintLog(5, this.LOGTAG, "Response getCapabilityToken code:" + str1 + " reason:" + str2);
        if (str1.equals("0"))
        {
          String str3 = localJSONObject2.getString("capabilityToken");
          Utils.PrintLog(5, this.LOGTAG, "handleMessage getCapabilityToken:" + str3);
          OnRegister(this.userId, str3);
          return;
        }
        Utils.PrintLog(5, this.LOGTAG, "获取token失败 [status:" + localHttpResult.getStatus() + "]" + localHttpResult.getErrorMsg());
        return;
      }
      catch (JSONException localJSONException)
      {
        localJSONException.printStackTrace();
      }
  }

  private void registerNetReceiver()
  {
    IntentFilter localIntentFilter = new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE");
    localIntentFilter.addAction("android.intent.action.PHONE_STATE");
    this.mReceiver = new NetCheckReceiver(new NetCheckReceiverInterface()
    {
      public void mobileConnected(ConnectInfo paramAnonymousConnectInfo)
      {
        Toast.makeText(MainApplication.this.getApplicationContext(), "连接手机网络", Toast.LENGTH_SHORT).show();
        MainApplication.this.setupRobot(paramAnonymousConnectInfo);
        MainApplication.this.currentNetInfo = paramAnonymousConnectInfo;
      }

      public void mobileRinging()
      {
        if (MainApplication.this.mCall != null)
        {
          MainApplication.this.mCall.disconnect();
          MainApplication.this.mCall = null;
          Toast.makeText(MainApplication.this.getApplicationContext(), "有来电，先断开连接！", Toast.LENGTH_SHORT).show();
          MainApplication.this.connectSendBro("onDisconnected");
          MainApplication.this.callVideoSendBro("close");
          Intent localIntent = new Intent();
          localIntent.setAction("com.chinatel.robotclient.receiver.IsCallCloseReceiver");
          MainApplication.this.sendBroadcast(localIntent);
          Utils.PrintLog(5, MainApplication.this.LOGTAG, "挂断voip");
        }
      }

      public void noConnected(ConnectInfo paramAnonymousConnectInfo)
      {
        MainApplication.this.currentNetInfo = paramAnonymousConnectInfo;
      }

      public void wifiConnected(ConnectInfo paramAnonymousConnectInfo)
      {
        if (paramAnonymousConnectInfo.getNetCode() == 21)
          Toast.makeText(MainApplication.this.getApplicationContext(), "连接小优热点", Toast.LENGTH_SHORT).show();
          MainApplication.this.setupRobot(paramAnonymousConnectInfo);
          MainApplication.this.currentNetInfo = paramAnonymousConnectInfo;
          Toast.makeText(MainApplication.this.getApplicationContext(), "连接Internet",Toast.LENGTH_SHORT).show();
          return;
      }
    });
    getApplicationContext().registerReceiver(this.mReceiver, localIntentFilter);
  }

  private void restRegister()
  {
    new Thread()
    {
      public void run()
      {
        MainApplication.this.opt_getToken();
      }
    }
    .start();
  }

  private void setupRobot(ConnectInfo paramConnectInfo)
  {
		Utils.PrintLog(5, this.LOGTAG, "postStatus()");
		// this.userId = ("uoes" + paramConnectInfo.getMacAddress());
		this.userId = "1004";
		Log.i("robot", "账号：" + this.userId);
		initRtcClientImpl();
        this.robotIp="1003";
	}

  public int callRobot(String paramString)
  {
    if ((this.mCall != null) || (!this.isAvailable))
      return -1;
    Utils.PrintLog(5, this.LOGTAG, "MakeCall user:" + paramString);
    JSONObject localJSONObject = new JSONObject();
    try
    {
//        localJSONObject.put("uri", "<sip:" + this.robotIp + ":5060;transport=UDP>");
        localJSONObject.put("uri", RtcRules.UserToRemoteUri_new(paramString, RtcConst.UEType_Any));
        localJSONObject.put("t", RtcConst.CallType_A_V);
        localJSONObject.put("ci", this.tName);
        this.mCall = this.mAcc.connect(localJSONObject.toString(), this.mCListener);
    }
    catch (JSONException localJSONException)
    {
      localJSONException.printStackTrace();
    }
    return 0;
  }

  public void callVideoSendBro(String paramString)
  {
    Utils.PrintLog(5, this.LOGTAG, "发送播放视频的通知");
    Intent localIntent = new Intent();
    localIntent.setAction("com.chinatel.robotclient.receiver.VIDEO_RECIEVER");
    localIntent.putExtra("callVideo", paramString);
    getInstance().sendBroadcast(localIntent);
  }

  public int connectRobot(String paramString)
  {
    if (this.mClt == null)
    {
      Toast.makeText(getApplicationContext(), "千里眼尚未启动成功！\n开始启动千里眼...", Toast.LENGTH_SHORT).show();
      initRtcClientImpl();
      return -1;
    }
    if (this.mCall != null)
    {
      Toast.makeText(getApplicationContext(), "有连接还未释放！", Toast.LENGTH_SHORT).show();
      return -1;
    }
    if (!this.isAvailable)
    {
      Toast.makeText(getApplicationContext(), "千里眼暂时不可用！\n尝试重连...", Toast.LENGTH_SHORT).show();
      login();
      return -1;
    }
    Utils.PrintLog(5, this.LOGTAG, "MakeCall user:" + paramString);
    JSONObject localJSONObject = new JSONObject();
    try
    {
/*        localJSONObject.put("uri", "<sip:" + this.robotIp + ":5060;transport=UDP>");
        localJSONObject.put("ci", this.tName);*/      
        localJSONObject.put("t", RtcConst.CallType_A_V_M);
        localJSONObject.put("ci", this.tName + ">>>" + this.userId);
        localJSONObject.put("uri", RtcRules.UserToRemoteUri_new(paramString, RtcConst.UEType_Any));
        this.mCall = this.mAcc.connect(localJSONObject.toString(), this.mCListener);
      
    }
    catch (JSONException localJSONException)
    {
        localJSONException.printStackTrace();
    }
    return 0;
  }

  public void connectSendBro(String paramString)
  {
    Intent localIntent = new Intent();
    localIntent.setAction("com.chinatel.robotclient.activity");
    localIntent.putExtra("connect", paramString);
    getApplicationContext().sendBroadcast(localIntent);
  }

  public int controlRobot(String paramString, int paramInt)
  {
    if ((this.mAcc == null) || (!this.isAvailable))
      return -1;
    Utils.PrintLog(5, this.LOGTAG, "controlRobot cmd:" + String.valueOf(paramInt));
//    String str = "<sip:" + this.robotIp + ":5060;transport=UDP>";
   String str = RtcRules.UserToRemoteUri_new(paramString, RtcConst.UEType_Any);
    this.mAcc.sendIm(str, "text/cmd", String.valueOf(paramInt));
    return 0;
  }

  public void disconnect()
  {
    Utils.PrintLog(5, this.LOGTAG, "disconnect()");
    if (this.mCall != null)
    {
      this.mCall.disconnect();
      Utils.PrintLog(5, this.LOGTAG, "onCallHangup timerDur" + this.mCall.getCallDuration());
      this.mCall = null;
    }
  }

  public RtcClient getRtcClient()
  {
    return this.mClt;
  }

  public void login()
  {
	if (this.mAcc == null) {
		/*if (RtcConst.bAddressCfg) {
			OnRegister(this.userId, "U03s");
			this.isAvailable = true;
		}*/
      restRegister();
      return;
	} else {
      restRegister();
      return;
    }
//   ((DeviceImpl)this.mAcc).getSip().refresh();
  }

  public void onCreate()
  {
    super.onCreate();
    Log.i("robot", "application oncreate");
    instance = this;
    mDisplayMetrics = new DisplayMetrics();
    this.mWindowManager = ((WindowManager)getSystemService(Context.WINDOW_SERVICE));
    this.mWindowManager.getDefaultDisplay().getMetrics(mDisplayMetrics);
    registerNetReceiver();
  }

  public void onLowMemory()
  {
    super.onLowMemory();
    System.out.println("low memory");
  }

  public void onTerminate()
  {
    disconnect();
    if (this.mAcc != null)
    {
      this.mAcc.release();
      this.mAcc = null;
    }
    if (this.mClt != null)
    {
      this.mClt.release();
      this.mClt = null;
    }
    Utils.PrintLog(5, this.LOGTAG, "onTerminate()");
    super.onTerminate();
    this.mHandler.postDelayed(new Runnable()
    {
      public void run()
      {
        System.exit(0);
      }
    }
    , 1000L);
  }

  public void unregisterReceiver()
  {
    getApplicationContext().unregisterReceiver(this.mReceiver);
  }
}

/* Location:           C:\Users\Administrator\Desktop\test111\classes_dex2jar.jar
 * Qualified Name:     cn.wangjianlog.baseframework.MainApplication
 * JD-Core Version:    0.6.2
 */