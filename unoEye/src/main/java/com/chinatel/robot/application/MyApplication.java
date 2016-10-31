package com.chinatel.robot.application;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import jni.http.HttpManager;
import jni.http.HttpResult;
import jni.http.RtcHttpClient;
import jni.util.Utils;

import org.json.JSONException;
import org.json.JSONObject;

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

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.Toast;

import com.chinatel.robot.Activity.DialogActivity;
import com.chinatel.robot.Activity.SpeakingActivity;
import com.chinatel.robot.Util.NetWorkUtil;
import com.chinatel.robot.Util.StringUtils;
import com.chinatel.robot.Util.WifiAdmin;
import com.chinatel.robot.Util.WifiApAdmin;
import com.chinatel.robot.bean.HomeMemberInfo;
import com.chinatel.robot.db.dao.HomeMemberDao;
import com.chinatel.robot.receiver.ConnectInfo;
import com.chinatel.robot.receiver.NetCheckReceiver;
import com.chinatel.robot.receiver.NetCheckReceiverInterface;
import com.unisrobot.bttools.BlueToothUtil;
import com.unisrobot.bttools.BtSensorListener;

public class MyApplication extends Application {
	private static String APP_ID = "71184";
	private static String APP_KEY = "2cd20a4d-4329-43d6-a5bd-ef3eaf19bb3a";
	private static MyApplication instance;
	public static int mMCount = 0;
	public static boolean photographing = false;
	private String LOGTAG = "canbot";
	// private BlueToothUtil btu;
	private boolean calling = false;
	int calltype;
	public Connection cons = null;
	public boolean isAvailable = false;
	private BlueToothUtil btu;
	DeviceListener mAListener = new DeviceListener() {
		private void ChangeNetWork() {
			Utils.PrintLog(5, MyApplication.this.LOGTAG, "ChangeNetWork");
		}

		private void onNoNetWork() {
			Utils.PrintLog(5, MyApplication.this.LOGTAG, "onNoNetWork");
			MyApplication.this.isAvailable = false;
			MyApplication.this.mConnect = null;
			if (MyApplication.this.mvLocal != null) {
				MyApplication.this.wManager
						.removeView(MyApplication.this.mvLocal);
				MyApplication.this.mvLocal = null;
				Utils.PrintLog(5, MyApplication.this.LOGTAG, "m Local=null");
			}
			if (MyApplication.this.mvRemote != null) {
				MyApplication.this.wManager
						.removeView(MyApplication.this.mvRemote);
				MyApplication.this.mvRemote = null;
			}
			if (MyApplication.this.mCall != null) {
				MyApplication.this.mCall.disconnect();
				MyApplication.this.mCall = null;
				MyApplication.this.connectSendBro("onDisconnected", 487);
				MyApplication.this.callVideoSendBro("close", 200);
				MyApplication.this.calling = false;
			}
			Toast.makeText(MyApplication.this.getApplicationContext(),
					"网络断开，关闭通话界面", Toast.LENGTH_SHORT).show();
		}

		private void onPoorNetWork() {
			Utils.PrintLog(5, MyApplication.this.LOGTAG, "onPoorNetWork");
		}

		public void onDeviceStateChanged(int paramAnonymousInt) {
			Utils.PrintLog(5, MyApplication.this.LOGTAG,
					"onDeviceStateChanged,result=" + paramAnonymousInt);
			if (paramAnonymousInt == 200) {
				if (!MyApplication.this.isAvailable)
					MyApplication.this.mHandler.post(new Runnable() {
						public void run() {
							Toast.makeText(
									MyApplication.this.getApplicationContext(),
									"千里眼启动成功！", 0).show();
						}
					});
				MyApplication.this.isAvailable = true;
				return;
			}
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
			if (paramAnonymousInt == -1004) {
				Utils.PrintLog(5, MyApplication.this.LOGTAG,
						"onDeviceStateChanged,ReLoginNetwork");
				return;
			}
			if (paramAnonymousInt == -1500) {
				MyApplication.this.isAvailable = false;
				Utils.PrintLog(5, MyApplication.this.LOGTAG,
						"onDeviceStateChanged,DeviceEvt_KickedOff");
				return;
			}
			if (paramAnonymousInt == -1501) {
				Utils.PrintLog(5, MyApplication.this.LOGTAG,
						"onDeviceStateChanged,DeviceEvt_MultiLogin");
				return;
			}
			MyApplication.this.isAvailable = false;
		}

		private boolean mConnectionRobot = false;
		public void onNewCall(Connection paramAnonymousConnection) {
			Utils.PrintLog(5, MyApplication.this.LOGTAG, "onNewCall,call="
					+ paramAnonymousConnection.info());
			int i = 0;
			try {
				JSONObject localJSONObject = new JSONObject(
						paramAnonymousConnection.info().toString());
				MyApplication.this.memberId = localJSONObject.getString("uri");
				MyApplication.this.mConnect = paramAnonymousConnection;
				/*
				 * String[] arrayOfString =
				 * localJSONObject.getString("ci").split(">>>");
				 * MyApplication.this.memberName = arrayOfString[0];
				 * MyApplication.this.memberId = arrayOfString[1];
				 * MyApplication.this.calltype = localJSONObject.getInt("t"); if
				 * (MyApplication.this.calltype != RtcConst.CallType_A_V_M)
				 * return; MyApplication.this.mConnect =
				 * paramAnonymousConnection; if (((MyApplication.mMCount == 0)
				 * && (MyApplication.this.calling)) ||
				 * (MyApplication.photographing)) {
				 * MyApplication.this.mConnect.disconnect();
				 * MyApplication.this.mConnect = null;
				 * MyApplication.this.mHandler.post(new Runnable() { public void
				 * run() { Toast.makeText(
				 * MyApplication.this.getApplicationContext(),
				 * "呼叫/拍照中。。。\n拒绝新连接使用摄像头", 0).show(); } }); }
				 */
				if (!mConnectionRobot) {
					mConnectionRobot = true;
					MyApplication.this.cons = paramAnonymousConnection;
					paramAnonymousConnection
							.setIncomingListener(MyApplication.this.mMListener);
					MyApplication.this.controlIntent(paramAnonymousConnection);
				}else{
					MyApplication.this.stopMic();
					MyApplication.this.mCall = paramAnonymousConnection;
					paramAnonymousConnection.setIncomingListener(MyApplication.this.mCListener);
					MyApplication.this.mHandler.post(new Runnable()
					{
						public void run()
						{
							Toast.makeText(MyApplication.this.getApplicationContext(), "控制端来电", Toast.LENGTH_SHORT).show();
							Intent localIntent = new Intent();
							localIntent.setFlags(268435456);
							localIntent.putExtra("tag", "fromClient");
							localIntent.putExtra("keyCode", MyApplication.this.memberId);
							localIntent.setClass(MyApplication.this.getApplicationContext(), SpeakingActivity.class);
							MyApplication.this.getApplicationContext().startActivity(localIntent);
						}
					});
				}

			} catch (JSONException e) {
				e.printStackTrace();
			}

		}

		public void onNewCall1(Connection paramAnonymousConnection) {
			int i = 0;
			while (true) {
				try {
					Utils.PrintLog(5, MyApplication.this.LOGTAG,
							"onNewCall,call=" + paramAnonymousConnection.info());
					try {
						JSONObject localJSONObject = new JSONObject(
								paramAnonymousConnection.info().toString());
						if (RtcConst.bAddressCfg) {
							String[] arrayOfString = localJSONObject.getString(
									"ci").split(">>>");
							MyApplication.this.memberName = arrayOfString[0];
							MyApplication.this.memberId = arrayOfString[1];
							MyApplication.this.calltype = localJSONObject
									.getInt("t");
							if (MyApplication.this.calltype != RtcConst.CallType_A_V_M)
								break;
							MyApplication.this.mConnect = paramAnonymousConnection;
							if (((MyApplication.mMCount == 0) && (MyApplication.this.calling))
									|| (MyApplication.photographing)) {
								MyApplication.this.mConnect.disconnect();
								MyApplication.this.mConnect = null;
								MyApplication.this.mHandler
										.post(new Runnable() {
											public void run() {
												Toast.makeText(
														MyApplication.this
																.getApplicationContext(),
														"呼叫/拍照中。。。\n拒绝新连接使用摄像头",
														0).show();
											}
										});
							}
						} else {
							MyApplication.this.memberId = localJSONObject
									.getString("uri");
							MyApplication.this.memberName = localJSONObject
									.getString("ci");
							continue;
						}
					} catch (JSONException localJSONException) {
						localJSONException.printStackTrace();
						continue;
					}
				} finally {
				}
				MyApplication.mMCount = 1 + MyApplication.mMCount;
				i = 0;
				// break label423;
				label248: paramAnonymousConnection
						.setIncomingListener(MyApplication.this.mMListener);
				MyApplication.this.controlIntent(paramAnonymousConnection);
			}
			label423: label436: while (true) {
				if (MyApplication.this.cons == null) {
					MyApplication.this.cons = MyApplication.this.mConnect;
					// break label248;
					if (MyApplication.this.mCall != null) {
						paramAnonymousConnection.reject();
						String str = RtcRules.UserToRemoteUri_new(
								MyApplication.this.memberId,
								RtcConst.UEType_Any);
						MyApplication.this.mAcc.sendIm(str, "text/cmd",
								"对方正在视频通话中！");
						Utils.PrintLog(5, MyApplication.this.LOGTAG,
								"onNewCall,reject call");
						break;
					}
					MyApplication.this.stopMic();
					MyApplication.this.mCall = paramAnonymousConnection;
					paramAnonymousConnection
							.setIncomingListener(MyApplication.this.mCListener);
					MyApplication.this.mHandler.post(new Runnable() {
						public void run() {
							Toast.makeText(
									MyApplication.this.getApplicationContext(),
									"控制端来电", 0).show();
							Intent localIntent = new Intent();
							localIntent.setFlags(268435456);
							localIntent.putExtra("tag", "fromClient");
							localIntent.putExtra("keyCode",
									MyApplication.this.memberId);
							localIntent.setClass(
									MyApplication.this.getApplicationContext(),
									SpeakingActivity.class);
							MyApplication.this.getApplicationContext()
									.startActivity(localIntent);
						}
					});
					break;
				}
			}
		}

		public void onQueryStatus(int paramAnonymousInt,
				String paramAnonymousString) {
		}

		public void onReceiveIm(String paramAnonymousString1,
				String paramAnonymousString2, String paramAnonymousString3) {
			if (paramAnonymousString2.equals("text/cmd"))
			{
				Utils.PrintLog(5, MyApplication.this.LOGTAG, "接收到控制命令:" + Integer.parseInt(paramAnonymousString3));
				if (MyApplication.this.btu.moutS != null) {
					int i = Integer.parseInt(paramAnonymousString3);
					MyApplication.this.btu.sendCmd((byte) i);
				}
			}

		}

		public void onSendIm(int paramAnonymousInt) {
			if (paramAnonymousInt == 200) {
				Utils.PrintLog(5, MyApplication.this.LOGTAG, "发送IM成功");
				return;
			}
			Utils.PrintLog(5, MyApplication.this.LOGTAG, "发送IM失败:"
					+ paramAnonymousInt);
		}
	};
	public Device mAcc = null;
	ConnectionListener mCListener = new ConnectionListener() {
		public void onConnected() {
			MyApplication.this.mHandler.post(new Runnable() {
				public void run() {
					Toast.makeText(MyApplication.this.getApplicationContext(),
							"呼叫成功", 0).show();
					Utils.PrintLog(5, MyApplication.this.LOGTAG,
							"机器人拨号onConnected");
				}
			});
		}

		public void onConnecting() {
			MyApplication.this.stopMic();
		}

		public void onDisconnected(final int paramAnonymousInt) {
			Utils.PrintLog(
					5,
					MyApplication.this.LOGTAG,
					"call onDisconnected timerDur"
							+ MyApplication.this.mCall.getCallDuration());
			MyApplication.this.mCall = null;
			MyApplication.this.mHandler.post(new Runnable() {
				public void run() {
					Log.i(LOGTAG, "=========mHandler.post============");
					MyApplication.this.connectSendBro("onDisconnected",
							paramAnonymousInt);
					MyApplication.this.callVideoSendBro("close",
							paramAnonymousInt);
					MyApplication.this.calling = false;
				}
			});
		}

		public void onNetStatus(int paramAnonymousInt,
				String paramAnonymousString) {
		}

		public void onVideo() {
			Utils.PrintLog(5, MyApplication.this.LOGTAG, "onVideo");
			Runnable runnable = new Runnable() {
				public void run() {
					Log.i(LOGTAG, "=========mHandler.post============");
					MyApplication.this.connectSendBro("connected", -1);
					MyApplication.this.callVideoSendBro("onVideo", -1);
					MyApplication.this.calling = false;
				}
			};
			MyApplication.this.mHandler.postDelayed(runnable,0L);
		}

	};
	public Connection mCall = null;
	public RtcClient mClt = null;
	public Connection mConnect = null;
	private Handler mHandler = new Handler(){

		@Override
		public void dispatchMessage(Message msg) {
			// TODO Auto-generated method stub
			super.dispatchMessage(msg);
		}
		
	};
	public ConnectionListener mMListener = new ConnectionListener() {
		public void onConnected() {
			MyApplication.this.mHandler.post(new Runnable() {
				public void run() {
				}
			});
		}

		public void onConnecting() {
		}

		public void onDisconnected(int paramAnonymousInt) {
			Utils.PrintLog(
					5,
					MyApplication.this.LOGTAG,
					"onDisconnected timerDur"
							+ MyApplication.this.mConnect.getCallDuration());
			MyApplication.mMCount = -1 + MyApplication.mMCount;

			if ((MyApplication.this.cons != null)
					&& (MyApplication.this.cons.info() == null))
				MyApplication.this.cons = null;
			if ((MyApplication.this.mConnect.info() != null)
					|| (MyApplication.this.cons == null))
				// break label217;
				MyApplication.this.mConnect = MyApplication.this.cons;
			// break label217;

			MyApplication.this.mConnect = null;
			if (MyApplication.this.calling)
				MyApplication.this.mHandler.post(new Runnable() {
					public void run() {
						Toast.makeText(
								MyApplication.this.getApplicationContext(),
								"刚才监控已打开摄像头，现监控全断开，本界面的预览停止\n   不影响后续界面预览!", 0)
								.show();
					}
				});
			MyApplication.this.mHandler.post(new Runnable() {
				public void run() {
					if (MyApplication.this.mvLocal != null) {
						MyApplication.this.wManager
								.removeViewImmediate(MyApplication.this.mvLocal);
						MyApplication.this.mvLocal = null;
						Utils.PrintLog(5, MyApplication.this.LOGTAG,
								"m Local=null");
					}
					if (MyApplication.this.mvRemote != null) {
						MyApplication.this.wManager
								.removeViewImmediate(MyApplication.this.mvRemote);
						MyApplication.this.mvRemote = null;
					}
				}
			});
		}

		public void onNetStatus(int paramAnonymousInt,
				String paramAnonymousString) {
		}

		public void onVideo(final String paramAnonymousString) {
			Utils.PrintLog(5, MyApplication.this.LOGTAG, "onVideo call info "
					+ paramAnonymousString);
			MyApplication.this.mHandler.postDelayed(new Runnable() {
				public void run() {
					if (MyApplication.this.mCall == null) {
						MyApplication.this.initVideoViews();
						Utils.PrintLog(5, MyApplication.this.LOGTAG,
								"initVideoViews finish");
					}
					if (MyApplication.this.mConnect == null)
						return;
					for (int i = 0;; i++) {
						if (i >= 4) {
							MyApplication.this.mConnect.setCameraAngle(0);
							return;
						}
						if ((MyApplication.this.cons != null)
								&& (MyApplication.this.cons.info()
										.equals(paramAnonymousString)))
							MyApplication.this.cons.buildVideo(MyApplication.this.mvRemote);
					}
				}
			}, 0L);
		}

		@Override
		public void onVideo() {

			MyApplication.this.mHandler.postDelayed(new Runnable() {
				public void run() {
					if (MyApplication.this.mCall == null) {
						MyApplication.this.initVideoViews();
						Utils.PrintLog(5, MyApplication.this.LOGTAG,
								"initVideoViews finish");
						//MyApplication.this.cons.buildVideo(MyApplication.this.mvRemote);
						//setVideoSurfaceVisibility(View.VISIBLE);
					}
					if (MyApplication.this.mConnect == null) {
						return;
					}else {
						MyApplication.this.cons.buildVideo(MyApplication.this.mvRemote);
					}
                       //setVideoSurfaceVisibility(View.VISIBLE);
				}
			}, 0L);

		}
	};

	/**
	 * Sets the video surface visibility.
	 * 
	 * @param visible
	 *            the new video surface visibility
	 */
	void setVideoSurfaceVisibility(int visible) {
		if (mvLocal != null)
			mvLocal.setVisibility(visible);
		if (mvRemote != null)
			mvRemote.setVisibility(visible);
	}

	public WindowManager.LayoutParams mParams;
	public WindowManager.LayoutParams mParams2;
	private NetCheckReceiver mReceiver;
	private String memberId;
	String memberName;
	public SurfaceView mvLocal = null;
	SurfaceView mvRemote = null;
	private List<Activity> myList = new LinkedList();
	private boolean ok;
	private String tName = "";
	private String userId = "";
	public WindowManager wManager;
	private int waitCount = 0;

	private void OnRegister(String paramString1, String paramString2) {
		Utils.PrintLog(5, this.LOGTAG, "OnRegister:" + paramString1 + "spwd:"
				+ paramString2);
		try {
			JSONObject localJSONObject = SdkSettings.defaultDeviceSetting();
			localJSONObject.put("acc.pwd", paramString2);
			Utils.PrintLog(5, this.LOGTAG, "user:" + paramString1 + "token:"
					+ paramString2);
			localJSONObject.put("acc.appid", APP_ID);
			localJSONObject.put("acc.user", paramString1);
			localJSONObject.put("acc.type", RtcConst.UEType_Current);
			this.mAcc = this.mClt.createDevice(localJSONObject.toString(),
					this.mAListener);
			return;
		} catch (JSONException localJSONException) {
			localJSONException.printStackTrace();
		}
	}

	private void controlIntent(final Connection paramConnection) {
		this.mHandler.post(new Runnable() {
			public void run() {
				HomeMemberInfo localHomeMemberInfo = new HomeMemberDao(
						MyApplication.this.getApplicationContext())
						.findMemberByKeyCode(MyApplication.this.memberId);
				if (localHomeMemberInfo != null) {
					String str1 = localHomeMemberInfo.getMode();
					String str2 = localHomeMemberInfo.getName();
					if ((!StringUtils.isEmpty(str1)) && ("1".equals(str1))
							&& (!"陌生人".equals(str2))) {
						paramConnection.accept(RtcConst.CallType_A_V);
						Toast.makeText(
								MyApplication.this.getApplicationContext(),
								str2 + "开始了连接", Toast.LENGTH_SHORT).show();
						return;
					}
					String str3 = RtcRules.UserToRemoteUri_new(
							MyApplication.this.memberId, RtcConst.UEType_Any);
					MyApplication.this.mAcc.sendIm(str3, "text/cmd","呃~机器人未允许您\n的连接请求");
					paramConnection.disconnect();
					MyApplication.this.mMListener.onDisconnected(603);
					Toast.makeText(MyApplication.this.getApplicationContext(),
							"拒绝了" + str2 + "的连接请求", 0).show();
					return;
				}
				Intent localIntent = new Intent();
				localIntent.setFlags(268435456);
				localIntent.setClass(
						MyApplication.this.getApplicationContext(),
						DialogActivity.class);
				Log.i("robot", "设备名称：" + MyApplication.this.memberName);
				localIntent.putExtra("keycode", MyApplication.this.memberId);
				localIntent.putExtra("device", MyApplication.this.memberName);
				MyApplication.this.startActivity(localIntent);
				Toast.makeText(MyApplication.this.getApplicationContext(),
						"收到" + MyApplication.this.memberName + "的新连接请求", 0)
						.show();
			}
		});
	}

	public static MyApplication getInstance() {
		return instance;
	}

	private void initRtcClientImpl() {
		Utils.PrintLog(5, this.LOGTAG, "initRtcClientImpl()");
		this.mClt = new RtcClientImpl();
		this.mClt.initialize(getApplicationContext(), new ClientListener() {
			public void onInit(int paramAnonymousInt) {
				Utils.PrintLog(5, MyApplication.this.LOGTAG, "onInit,result="
						+ paramAnonymousInt);
				if (paramAnonymousInt == 0) {
					MyApplication.this.mClt.setAudioCodec(RtcConst.ACodec_OPUS);
					MyApplication.this.mClt.setVideoCodec(RtcConst.VCodec_VP8);
					MyApplication.this.mClt.setVideoAttr(RtcConst.Video_HD);
					MyApplication.this.login();
					return;
				}
				MyApplication.this.mClt.release();
				MyApplication.this.mClt = null;
				MyApplication.this.mHandler.post(new Runnable() {
					public void run() {
						Toast.makeText(
								MyApplication.this.getApplicationContext(),
								"千里眼初始化失败！\n重新初始化请再次点击千里眼！", 0).show();
					}
				});
			}
		});
	}

	private void opt_getToken() {
		RtcConst.UEAPPID_Current = RtcConst.UEAPPID_Self;
		JSONObject localJSONObject1 = HttpManager.getInstance()
				.CreateTokenJson(0, this.userId,
						RtcHttpClient.grantedCapabiltyID, "");
		HttpResult localHttpResult = HttpManager.getInstance()
				.getCapabilityToken(localJSONObject1, APP_ID, APP_KEY);
		Utils.PrintLog(5, this.LOGTAG, "获取token:" + localHttpResult.getStatus()
				+ " reason:" + localHttpResult.getErrorMsg());
		JSONObject localJSONObject2 = (JSONObject) localHttpResult.getObject();
		if ((localJSONObject2 != null) && (!localJSONObject2.isNull("code")))
			try {
				String str1 = localJSONObject2.getString("code");
				String str2 = localJSONObject2.getString("reason");
				Utils.PrintLog(5, this.LOGTAG,
						"Response getCapabilityToken code:" + str1 + " reason:"
								+ str2);
				if (str1.equals("0")) {
					String str3 = localJSONObject2.getString("capabilityToken");
					Utils.PrintLog(5, this.LOGTAG,
							"handleMessage getCapabilityToken:" + str3);
					OnRegister(this.userId, str3);
					return;
				}
				Utils.PrintLog(5, this.LOGTAG,
						"获取token失败 [status:" + localHttpResult.getStatus()
								+ "]" + localHttpResult.getErrorMsg());
				return;
			} catch (JSONException localJSONException) {
				localJSONException.printStackTrace();
			}
	}

	private void registerNetReceiver() {
		IntentFilter localIntentFilter = new IntentFilter();
		localIntentFilter.addAction("android.net.wifi.STATE_CHANGE");
		this.mReceiver = new NetCheckReceiver(new NetCheckReceiverInterface() {
			public void apIsDisEnabled(ConnectInfo paramAnonymousConnectInfo) {
				Log.i("robot", "apIsDisEnabled");
			}

			public void apIsEnabled(ConnectInfo paramAnonymousConnectInfo) {
				Log.i("robot", "apIsEnabled");
			}

			public void wifiAviable() {
				if (MyApplication.this.mClt == null)
					;
				try {
					Thread.sleep(1000L);
					Toast.makeText(MyApplication.this.getApplicationContext(),
							"检测到网络连接！\n千里眼尚未启动！\n开始启动千里眼...", 0).show();
					MyApplication.this.openWifiAp();
					return;
				} catch (InterruptedException localInterruptedException) {
					localInterruptedException.printStackTrace();
				}
			}
		});
		getApplicationContext().registerReceiver(this.mReceiver,
				localIntentFilter);
	}

	private void restRegister() {
		new Thread() {
			public void run() {
				MyApplication.this.opt_getToken();
			}
		}.start();
	}

	private void setupRobot(ConnectInfo paramConnectInfo) {
		Utils.PrintLog(5, this.LOGTAG, "postStatus()");
		// this.userId = ("uoes" + paramConnectInfo.getMacAddress());
		this.userId = "1003";
		Log.i("robot", "账号：" + this.userId);
		initRtcClientImpl();
	}

	private void stopMic() {
		Log.e("chinatel", "stopMic start");
		this.ok = false;
		Intent localIntent = new Intent("cn.yunzhisheng.action.close.mic2");
		this.waitCount = 0;
		sendBroadcast(localIntent);
		if (this.ok) {
			Log.e("chinatel", "stopMic stop");
			return;
		}
		try {
			Log.e("chinatel", "while");
			Thread.sleep(100L);
			this.waitCount = (1 + this.waitCount);
			if (this.waitCount != 15)
				this.ok = true;
		} catch (InterruptedException localInterruptedException) {
			localInterruptedException.printStackTrace();
		}
	}

	public void addActivity(Activity paramActivity) {
		this.myList.add(paramActivity);
	}

	public int callCanbot(String paramString) {
		if ((this.mCall != null) || (!this.isAvailable))
			// return -1;
			Utils.PrintLog(5, this.LOGTAG, "MakeCall user:" + paramString);
		JSONObject localJSONObject = new JSONObject();
		try {
			localJSONObject.put("uri", RtcRules.UserToRemoteUri_new(
					paramString, RtcConst.UEType_Any));
			localJSONObject.put("t", RtcConst.CallType_A_V);
			localJSONObject.put("ci", TextUtils.isEmpty(this.tName) ? "baba": this.tName);
			Connection mConnect1 = this.mAcc.connect(localJSONObject.toString(),this.mCListener);
			this.mCall = mConnect1;
			this.calling = true;
		} catch (JSONException localJSONException) {
			localJSONException.printStackTrace();
		}
		return 0;
	}

	public void callVideoSendBro(String paramString, int paramInt) {
		Utils.PrintLog(5, this.LOGTAG, "通知通话界面" + paramString);
		Intent localIntent = new Intent();
		localIntent.setAction("com.chinatel.robot_123.receiver.VIDEO_RECIEVER");
		localIntent.putExtra("callVideo", paramString);
		localIntent.putExtra("code", paramInt);
		sendBroadcast(localIntent);
	}

	public void connectSendBro(String paramString, int paramInt) {
		Intent localIntent = new Intent();
		localIntent.setAction("com.chinatel.robot.Activity.BROADCAST_ACTION");
		localIntent.putExtra("call", paramString);
		localIntent.putExtra("code", paramInt);
		getApplicationContext().sendBroadcast(localIntent);
	}

	public void disconnect() {
		Utils.PrintLog(5, this.LOGTAG, "disconnect()");
		if (this.mCall != null) {
			this.mCall.disconnect();
			Utils.PrintLog(5, this.LOGTAG,
					"onCallHangup timerDur" + this.mCall.getCallDuration());
			this.mCall = null;
			this.calling = false;
		}
	}

	public void exit() {
		try {
			Iterator localIterator = this.myList.iterator();
			if (!localIterator.hasNext())
				return;
			Activity localActivity = (Activity) localIterator.next();
			if (localActivity != null)
				localActivity.finish();
		} catch (Exception localException) {
			localException.printStackTrace();
		}
	}

	@SuppressLint("NewApi")
	void initVideoViews1() {
		if ((this.mvLocal != null) || (this.mConnect == null)) {
			return;
		}
		Utils.PrintLog(5, this.LOGTAG, "initVideoViews");
		this.mvRemote = ((SurfaceView) this.mConnect.createVideoView(false,
				this, true));
		this.wManager.addView(this.mvRemote, this.mParams2);
		this.mvLocal = ((SurfaceView) this.mConnect.createVideoView(true, this,
				true));
		this.wManager.addView(this.mvLocal, this.mParams);
		this.mvLocal.setZOrderMediaOverlay(true);
	}

  /*  public void initVideoViews() {
		if (mvLocal != null)
			return;
		if (mConnect != null)
			mvLocal = (SurfaceView) mConnect.createVideoView(true, this, true);
		mvLocal.setVisibility(View.INVISIBLE);
		this.wManager.addView(mvLocal, this.mParams);
		mvLocal.setKeepScreenOn(true);
		mvLocal.setZOrderMediaOverlay(true);
		mvLocal.setZOrderOnTop(true);

		if (mConnect != null)
			mvRemote = (SurfaceView) mConnect
					.createVideoView(false, this, true);
		mvRemote.setVisibility(View.INVISIBLE);
		mvRemote.setKeepScreenOn(true);
		mvRemote.setZOrderMediaOverlay(true);
		mvRemote.setZOrderOnTop(true);
		this.wManager.addView(mvRemote, this.mParams2);
	}*/

	@SuppressLint("NewApi")
	void initVideoViews()
	{
		if ((this.mvLocal != null) || (this.mConnect == null))
			return;
		Utils.PrintLog(5, this.LOGTAG, "initVideoViews");
		this.mvRemote = ((SurfaceView)this.mConnect.createVideoView(false, this, true));
		this.wManager.addView(this.mvRemote, this.mParams2);
		this.mvLocal = ((SurfaceView)this.mConnect.createVideoView(true, this, true));
		this.wManager.addView(this.mvLocal, this.mParams);
		this.mvLocal.setZOrderMediaOverlay(true);
	}

	public void login() {
		if (this.mAcc == null) {
			if (RtcConst.bAddressCfg) {
				OnRegister(this.userId, "U03s");
				this.isAvailable = true;
			}
		} else
			return;
		restRegister();
	}

	public void onCreate() {
		super.onCreate();
		instance = this;
		openWifiAp();
		registerNetReceiver();
		// this.cons = new Connection();
		this.wManager = ((WindowManager) getApplicationContext()
				.getSystemService(Context.WINDOW_SERVICE));
		this.mParams = new WindowManager.LayoutParams();
		this.mParams.type = 2005;
		WindowManager.LayoutParams localLayoutParams1 = this.mParams;
		localLayoutParams1.flags = (0x8 | localLayoutParams1.flags);
		this.mParams.width =1;
		this.mParams.height =1;
		this.mParams.x = 0;
		this.mParams.y = 0;
		this.mParams2 = new WindowManager.LayoutParams();
		this.mParams2.type = 2005;
		WindowManager.LayoutParams localLayoutParams2 = this.mParams2;
		localLayoutParams2.flags = (0x8 | localLayoutParams2.flags);
		this.mParams2.width = 250;
		this.mParams2.height = 250;
		mParams2.gravity = Gravity.RIGHT|Gravity.TOP;
		this.btu = new BlueToothUtil(new BtSensorListener()
		{
			public void onSensorTouch(int paramAnonymousInt)
			{
				Utils.PrintLog(5, MyApplication.this.LOGTAG, "onSensorTouch " + paramAnonymousInt);
			}
		});
		this.btu.registerBtSensorBR();
		return;
	}

	public void onTerminate() {
		if (this.mAcc != null) {
			this.mAcc.release();
			this.mAcc = null;
		}
		if (this.mClt != null) {
			this.mClt.release();
			this.mClt = null;
		}
		// this.btu.unRegisterBtSensorBR();
		Utils.PrintLog(5, this.LOGTAG, "onTerminate()");
		super.onTerminate();
		this.mHandler.postDelayed(new Runnable() {
			public void run() {
				System.exit(0);
			}
		}, 1000L);
	}

	public void openWifiAp() {
		if (new WifiApAdmin(getApplicationContext()).isWifiApEnabled()) {
			Log.i("robot", "热点直接成功");
			ConnectInfo localConnectInfo2 = new ConnectInfo();
			localConnectInfo2.setNetCode(21);
			localConnectInfo2.setMacAddress(NetWorkUtil
					.getMacAddress(getApplicationContext()));
			setupRobot(localConnectInfo2);
			return;
		}
		if (new WifiAdmin(getApplicationContext()).checkState() == 3) {
			Utils.PrintLog(5, this.LOGTAG, "连接的外网wifi()");
			ConnectInfo localConnectInfo1 = new ConnectInfo();
			localConnectInfo1.setNetCode(22);
			localConnectInfo1.setMacAddress(NetWorkUtil
					.getMacAddress(getApplicationContext()));
			setupRobot(localConnectInfo1);
			return;
		}
		Toast.makeText(getApplicationContext(), "没有网络，无法启动千里眼！", 0).show();
	}

	public void unregisterReceiver() {
		getApplicationContext().unregisterReceiver(this.mReceiver);
	}
}

/*
 * Location:
 * C:\Users\Administrator\Desktop\小优\U03S源码\机器人本体.apk\classes_dex2jar.jar
 * Qualified Name: com.chinatel.robot.application.MyApplication JD-Core Version:
 * 0.6.2
 */