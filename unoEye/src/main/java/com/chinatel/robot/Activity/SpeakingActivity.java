package com.chinatel.robot.Activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.KeyguardManager;
import android.app.KeyguardManager.KeyguardLock;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.chinatel.robot.R;
import com.chinatel.robot.Util.DateUtil;
import com.chinatel.robot.Util.SoundPlayer;
import com.chinatel.robot.application.MyApplication;
import com.chinatel.robot.bean.CallRecordInfo;
import com.chinatel.robot.camera.FileUtil;
import com.chinatel.robot.db.dao.CallRecordingDao;
import com.chinatel.robot.receiver.CallVideoReceiver;
import com.chinatel.robot.receiver.CallVideoReceiver.CallVideoReceiverCallBack;
import jni.util.Utils;
import rtc.sdk.common.RtcConst;
import rtc.sdk.iface.Connection;

public class SpeakingActivity extends Activity implements View.OnClickListener {
	public static final String IS_CALL_RECEIVER = "com.chinatel.robot.Activity";
	private String LOGTAG = "canbot";
	private RelativeLayout big_camera;
	private CallRecordInfo info;
	private boolean isClick;
	private RelativeLayout local_camera;
	private Context mContext;
	private long mExitTime;
	private BroadcastReceiver mHomeKeyEventReceiver = new BroadcastReceiver() {
		String SYSTEM_HOME_KEY = "homekey";
		String SYSTEM_HOME_KEY_LONG = "recentapps";
		String SYSTEM_REASON = "reason";

		public void onReceive(Context paramAnonymousContext,
				Intent paramAnonymousIntent) {
			if (paramAnonymousIntent.getAction().equals(
					"android.intent.action.CLOSE_SYSTEM_DIALOGS")) {
				Toast.makeText(SpeakingActivity.this.getApplicationContext(),
						"home", Toast.LENGTH_SHORT).show();
				SpeakingActivity.this.hang_up(false);
			}
		}
	};
	private CallVideoReceiver mReceiver;
	SurfaceView mvLocal = null;
	SurfaceView mvRemote = null;
	private Button speak_camera;
	private Button speak_hang_up;
	private boolean switchClick;

	private void exit() {
		if (System.currentTimeMillis() - this.mExitTime > 2000L) {
			Toast.makeText(this, "再按一次结束视频通话", Toast.LENGTH_SHORT).show();
			this.mExitTime = System.currentTimeMillis();
			return;
		}
		hang_up(false);
	}

	private void registerReceiver() {
		registerReceiver(this.mHomeKeyEventReceiver, new IntentFilter(
				"android.intent.action.CLOSE_SYSTEM_DIALOGS"));
		IntentFilter localIntentFilter = new IntentFilter(
				"com.chinatel.robot_123.receiver.VIDEO_RECIEVER");
		this.mReceiver = new CallVideoReceiver(
				new CallVideoReceiver.CallVideoReceiverCallBack() {
					public void close() {
						SpeakingActivity.this.hang_up(false);
					}

					public void showVideo() {

						Utils.PrintLog(5, SpeakingActivity.this.LOGTAG,
								"call_page_showVideo");
						SpeakingActivity.this.initVideoViews();
						Connection localConnection = MyApplication
								.getInstance().mCall;
						if (null != localConnection) {
							MyApplication.getInstance().mCall.buildVideo(SpeakingActivity.this.mvRemote);
							SpeakingActivity.this.mvLocal.setVisibility(View.VISIBLE);
							SpeakingActivity.this.mvRemote.setVisibility(View.VISIBLE);
							MyApplication.getInstance().mCall.setCameraAngle(0);
						}

					}
				});
		registerReceiver(this.mReceiver, localIntentFilter);
	}

	private void setup() {
		this.big_camera = ((RelativeLayout) findViewById(R.id.big_camera));
		this.local_camera = ((RelativeLayout) findViewById(R.id.local_camera));
		this.speak_camera = ((Button) findViewById(R.id.speak_hang_up));
		this.speak_hang_up = ((Button) findViewById(R.id.speak_hang_up));
		this.speak_camera.setOnClickListener(this);
		this.speak_hang_up.setOnClickListener(this);
	}

	private void unregisterReceiver() {
		unregisterReceiver(this.mHomeKeyEventReceiver);
		unregisterReceiver(this.mReceiver);
	}

	public void hang_up(boolean paramBoolean) {
		if (this.isClick)
			return;
		this.isClick = true;
		if ((MyApplication.getInstance().mConnect != null)
				&& (this.mvLocal != null)
				&& (MyApplication.getInstance().mvLocal == null)) {
			Utils.PrintLog(5, this.LOGTAG, "call-->connect");
			MyApplication.getInstance().mvLocal = this.mvLocal;
			this.local_camera.removeAllViews();
			MyApplication.getInstance().wManager.addView(
					MyApplication.getInstance().mvLocal,
					MyApplication.getInstance().mParams);
			this.mvLocal = null;
			MyApplication.getInstance().mConnect.resetVideoViews();
		}
		saveCallLog();
		MyApplication.getInstance().disconnect();
		if (paramBoolean)
			SoundPlayer.getInstance(getApplicationContext(), "qly014.mp3")
					.play();
		finish();
	}

	@SuppressLint("NewApi")
	void initVideoViews() {
		Connection localConnection = MyApplication.getInstance().mCall;
		if ((this.mvLocal != null) || (localConnection == null))
			return;
		Utils.PrintLog(5, this.LOGTAG, "initVideoViews");
		this.mvRemote = ((SurfaceView) localConnection.createVideoView(false,
				this, false));
		this.mvRemote.setVisibility(View.INVISIBLE);
		this.big_camera.addView(this.mvRemote);
		this.mvLocal = ((SurfaceView) localConnection.createVideoView(true,
				this, true));
		this.mvLocal.setVisibility(View.INVISIBLE);
		this.local_camera.addView(this.mvLocal);
		this.mvRemote.setKeepScreenOn(true);
		this.mvRemote.setZOrderMediaOverlay(true);
		AudioManager localAudioManager = (AudioManager) getSystemService(Service.AUDIO_SERVICE);
		localAudioManager.setMode(0);
		localAudioManager.setSpeakerphoneOn(true);
		localAudioManager.setMode(0);
		if ((MyApplication.getInstance().mCall != null)
				&& (MyApplication.getInstance().mvLocal != null)) {
			Utils.PrintLog(5, this.LOGTAG, "connect-->call");
			this.mvLocal = MyApplication.getInstance().mvLocal;
//			MyApplication.getInstance().wManager.removeViewImmediate(MyApplication.getInstance().mvLocal);
			this.local_camera.addView(this.mvLocal,	this.local_camera.getLayoutParams());
			MyApplication.getInstance().mvLocal = null;
//			localConnection.resetVideoViews();
		}else {
			this.mvLocal.setKeepScreenOn(true);
			this.mvLocal.setZOrderMediaOverlay(true);
			this.mvLocal.setZOrderOnTop(true);
		}
	}

	public void onClick(View paramView) {

	    switch (paramView.getId())
	    {
	    default:
	      return;
	    case R.id.speak_camera:
	      Toast.makeText(this, "截图保存在/qly/picture/", 0).show();
	      String str = FileUtil.getSDPath();
	      MyApplication.getInstance().mCall.takeRemotePicture(str + "/qly/picture/");
	      return;
	    case R.id.speak_hang_up:
	      hang_up(true);
	      return;
	    case R.id.local_camera:
	    	this.local_camera.removeAllViews();
	    	this.big_camera.removeAllViews();
	    }
	    boolean bool1;
	   /* label112: SurfaceView localSurfaceView1;
	    label135: boolean bool3;
	    label161: RelativeLayout localRelativeLayout2;
	    if (this.switchClick)
	    {
	      bool1 = false;
	      this.switchClick = bool1;
	      RelativeLayout localRelativeLayout1 = this.local_camera;
	      if (!this.switchClick)
	        break label231;
	      localSurfaceView1 = this.mvRemote;
	      localRelativeLayout1.addView(localSurfaceView1);
	      SurfaceView localSurfaceView2 = this.mvLocal;
	      boolean bool2 = this.switchClick;
	      bool3 = false;
	      if (!bool2)
	        break label240;
	      localSurfaceView2.setZOrderOnTop(bool3);
	      this.mvRemote.setZOrderOnTop(this.switchClick);
	      localRelativeLayout2 = this.big_camera;
	      if (!this.switchClick)
	        break label246;
	    }
	    label231: label240: label246: for (SurfaceView localSurfaceView3 = this.mvLocal; ; localSurfaceView3 = this.mvRemote)
	    {
	      localRelativeLayout2.addView(localSurfaceView3);
	      if (MyApplication.getInstance().mCall == null)
	        break;
	      MyApplication.getInstance().mCall.resetVideoViews();
	      return;
	      bool1 = true;
	      break label112;
	      localSurfaceView1 = this.mvLocal;
	      break label135;
	      bool3 = true;
	      break label161;
	    }*/
	  
		
	}

	protected void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);
		getWindow().addFlags(6815872);
		setContentView(R.layout.activity_speaking);
		this.mContext = this;
		setup();
		Log.i("robot", "onCreate");
		Intent localIntent = getIntent();
		String str1 = localIntent.getStringExtra("tag");
		String str2 = localIntent.getStringExtra("keyCode");
		registerReceiver();
		this.info = new CallRecordInfo();
		if ("fromClient".equals(str1)) {
			if (MyApplication.getInstance().mCall != null)
				MyApplication.getInstance().mCall.accept(RtcConst.CallType_A_V);
			this.info.setComego("come");
		}
		this.info.setStarttime(DateUtil.getCurTime("yyyy-MM-ddHH:mm:ss"));
		this.info.setKeycode(str2);
		((RelativeLayout) findViewById(R.id.local_camera))
				.setOnClickListener(this);
		if ("fromCall".equals(str1)) {
			MyApplication.getInstance().callVideoSendBro("onVideo", -1);
			this.info.setComego("go");
		}
	}

	protected void onDestroy() {
		Utils.PrintLog(5, this.LOGTAG, "call destroy");
		super.onDestroy();
		unregisterReceiver();
	}

	public boolean onKeyDown(int paramInt, KeyEvent paramKeyEvent) {
		AudioManager localAudioManager = (AudioManager) getSystemService("audio");
		Log.e(this.LOGTAG, "Current audio mode sssss : " + 3);
		int i = localAudioManager.getStreamVolume(3);
		switch (paramInt) {
		default:
		case 24:
		case 25:
			while (true) {
				/*
				 * return super.onKeyDown(paramInt, paramKeyEvent);
				 * localAudioManager.setStreamVolume(3, i + 1, 1);
				 * Log.e(this.LOGTAG, "Current audio volume up sssss");
				 * continue; localAudioManager.setStreamVolume(3, i - 1, 1);
				 * Log.e(this.LOGTAG, "Current audio volume up sssss");
				 */
			}
		case 4:
		}
		exit();
		return true;
	}

	protected void onResume() {
		if (MyApplication.getInstance().mCall != null)
			MyApplication.getInstance().mCall.resetVideoViews();
		super.onResume();
	}

	public void saveCallLog() {
		this.info.setEndtime(DateUtil.getCurTime("yyyy-MM-ddHH:mm:ss"));
		new CallRecordingDao(this.mContext).add(this.info);
	}

	public void unlock() {
		((KeyguardManager) getSystemService("keyguard")).newKeyguardLock("")
				.disableKeyguard();
	}
}

/*
 * Location:
 * C:\Users\Administrator\Desktop\小优\U03S源码\机器人本体.apk\classes_dex2jar.jar
 * Qualified Name: com.chinatel.robot.Activity.SpeakingActivity JD-Core Version:
 * 0.6.2
 */