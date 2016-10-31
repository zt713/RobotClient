package com.chinatel.robot.Activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.AnimationDrawable;
import android.hardware.Camera;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chinatel.robot.R;
import com.chinatel.robot.Util.SoundPlayer;
import com.chinatel.robot.View.CircleImageView;
import com.chinatel.robot.application.MyApplication;
import com.chinatel.robot.db.dao.HomeMemberDao;

import java.io.File;
import java.util.List;

import jni.util.Utils;
import rtc.sdk.iface.Connection;

public class SpeakingBeforeActivity extends Activity {
	private String LOGTAG = "canbot";
	AnimationDrawable animationDrawable;
	private SoundPlayer calling;
	private Camera camera = null;
	private RelativeLayout cameraView;
	private CameraView cv = null;
	private HomeMemberDao homeDao;
	private boolean isClick;
	private boolean isPause;
	String keyCode;
	private Context mContext;
	private long mExitTime;
	private SurfaceView mvLocal = null;
	private CallClientReceiver receiver;
	private SoundPlayer sp;

	private void callFailed(int paramInt) {
		if (this.calling != null) {
			this.calling.stop();
			this.calling = null;
		}
		if (paramInt == 487) {
			this.cameraView.removeAllViews();
			finish();
			return;
		}
		String str = this.homeDao.findMemberByKeyCode(this.keyCode).getName();
		if (paramInt == 487) {
			if (str.equals("爸爸")) {
				SoundPlayer.getInstance(getApplicationContext(), "qly005.mp3")
						.play();
			}
			if (str.equals("妈妈")) {
				SoundPlayer.getInstance(getApplicationContext(), "qly006.mp3")
						.play();
			}
		} else {
			SoundPlayer.getInstance(getApplicationContext(), "qly007.mp3")
					.play();
			if ((paramInt == 486) || (paramInt == 603)) {
				if (str.equals("爸爸"))
					SoundPlayer.getInstance(getApplicationContext(),
							"qly008.mp3").play();
				else if (str.equals("妈妈"))
					SoundPlayer.getInstance(getApplicationContext(),
							"qly009.mp3").play();
				else
					SoundPlayer.getInstance(getApplicationContext(),
							"qly010.mp3").play();
			} else if ((paramInt == 408) || (paramInt == 480))
				if (str.equals("爸爸"))
					SoundPlayer.getInstance(getApplicationContext(),
							"qly011.mp3").play();
				else if (str.equals("妈妈"))
					SoundPlayer.getInstance(getApplicationContext(),
							"qly012.mp3").play();
				else
					SoundPlayer.getInstance(getApplicationContext(),
							"qly013.mp3").play();
		}
		cancalCall();
		return;
	}

	private void callSuccess() {
		/*if (this.calling != null)
			this.calling.stop();*/
		Intent localIntent = new Intent();
		localIntent.setClass(this, SpeakingActivity.class);
		localIntent.putExtra("tag", "fromCall");
		localIntent.putExtra("keyCode", this.keyCode);
		startActivity(localIntent);
		finish();
	}

	private void exit() {
		if (System.currentTimeMillis() - this.mExitTime > 2000L) {
			Toast.makeText(this, "再按一次结束呼叫", 0).show();
			this.mExitTime = System.currentTimeMillis();
			return;
		}
		cancalCall();
	}

	private void register() {
		this.receiver = new CallClientReceiver();
		IntentFilter localIntentFilter = new IntentFilter();
		localIntentFilter
				.addAction("com.chinatel.robot.Activity.BROADCAST_ACTION");
		registerReceiver(this.receiver, localIntentFilter);
	}
	
	private void setUpParams(){
		if(MyApplication.getInstance().mCall!=null){
			Connection mConn = MyApplication.getInstance().mCall;
			if(MyApplication.getInstance().mvLocal==null){
				MyApplication.getInstance().mvLocal =(SurfaceView) mConn.createVideoView(true, this, true);
//				mvLocal.setVisibility(View.INVISIBLE);
			}
		}
		
	}

	private void setupCamera() {
		this.cameraView = ((RelativeLayout) findViewById(R.id.cameraView));
		if ((MyApplication.getInstance().mConnect != null) && (this.cv == null)
				&& (this.mvLocal == null)
				&& (MyApplication.getInstance().mvLocal != null)) {
			Utils.PrintLog(5, this.LOGTAG, "setupCamera");
			this.mvLocal = MyApplication.getInstance().mvLocal;
			MyApplication.getInstance().wManager
					.removeViewImmediate(MyApplication.getInstance().mvLocal);
			this.cameraView.removeAllViews();
			LinearLayout.LayoutParams localLayoutParams = new LinearLayout.LayoutParams(
					-1, -1);
			this.cameraView.addView(this.mvLocal, localLayoutParams);
			MyApplication.getInstance().mvLocal = null;
			MyApplication.getInstance().mConnect.resetVideoViews();
			return;
		}
//		startCamera();
	}

	private void startCamera() {
		this.cameraView.removeAllViews();
		this.cv = new CameraView(this.mContext);
		LinearLayout.LayoutParams localLayoutParams = new LinearLayout.LayoutParams(-1, -1);
		this.cameraView.addView(this.cv, localLayoutParams);
	}

	public void cancalCall() {
		if (this.isClick)
			return;
		this.isClick = true;
		MyApplication.getInstance().disconnect();
		finish();
	}

	protected void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);
		setContentView(R.layout.activity_speaking_before);
		this.mContext = this;
		this.sp = SoundPlayer.getInstance(this.mContext, "qly003.mp3");
		this.sp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
			public void onCompletion(MediaPlayer paramAnonymousMediaPlayer) {
				if (!SpeakingBeforeActivity.this.isPause) {
					SpeakingBeforeActivity.this.calling = SoundPlayer
							.getInstance(SpeakingBeforeActivity.this.mContext,
									"qly004.mp3");
					SpeakingBeforeActivity.this.calling.play();
				}
			}
		});
		this.sp.play();
		this.keyCode = getIntent().getStringExtra("keyCode");
		CircleImageView localCircleImageView = (CircleImageView) findViewById(R.id.img_speaker_head);
		TextView localTextView = (TextView) findViewById(R.id.tv_speaker_name);
		this.homeDao = new HomeMemberDao(this);
		Button localButton = (Button) findViewById(R.id.bt_hangup);
		if (this.keyCode != null) {
			MyApplication.getInstance().callCanbot(this.keyCode);
			File localFile = new File(this.homeDao.findImguri(this.keyCode));
			if ((localFile != null) && (localFile.exists()))
				localCircleImageView.setImageURI(Uri.fromFile(localFile));
			localTextView.setText(this.homeDao.findName2(this.keyCode));
		}
		localButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View paramAnonymousView) {
				SpeakingBeforeActivity.this.finish();
				MyApplication.getInstance().disconnect();
			}
		});
		ImageView localImageView = (ImageView) findViewById(R.id.animation_head);
		localImageView.setImageResource(R.drawable.animation_head);
		this.animationDrawable = ((AnimationDrawable) localImageView.getDrawable());
		this.animationDrawable.start();
		register();
	}

	protected void onDestroy() {
		this.mvLocal = null;
		super.onDestroy();
		unregisterReceiver(this.receiver);
	}

	public boolean onKeyDown(int paramInt, KeyEvent paramKeyEvent) {
		if (paramInt == 4) {
			exit();
			return true;
		}
		return super.onKeyDown(paramInt, paramKeyEvent);
	}

	protected void onPause() {
		super.onPause();
		releaseCamera();
		this.isPause = true;
		if (this.calling != null)
			this.calling.stop();
		if ((MyApplication.getInstance().mCall != null)
				&& (this.mvLocal != null)) {
			Utils.PrintLog(5, this.LOGTAG, "calling destroy");
			MyApplication.getInstance().mvLocal = this.mvLocal;
			this.cameraView.removeAllViews();
			MyApplication.getInstance().wManager.addView(
					MyApplication.getInstance().mvLocal,
					MyApplication.getInstance().mParams);
			this.mvLocal = null;
			MyApplication.getInstance().mConnect.resetVideoViews();
		}
	}

	protected void onResume() {
		super.onResume();
//		setupCamera();
		setUpParams();
	}

	public void releaseCamera() {
		if (this.camera != null) {
			this.camera.stopPreview();
			this.camera.release();
			this.camera = null;
		}
	}
	public class CallClientReceiver extends BroadcastReceiver {
		public static final String BROADCAST_ACTION = "com.chinatel.robot.Activity.BROADCAST_ACTION";
		public static final String CALL_CONNECTED = "connected";
		public static final String CALL_DISCONNECTED = "onDisconnected";
		public static final String CODE = "code";
		public static final String NAME = "call";

		public CallClientReceiver() {
		}

		public void onReceive(Context paramContext, Intent paramIntent) {
			String str = paramIntent.getStringExtra("call");
			int i = paramIntent.getIntExtra("code", -1);
			if (str.equals("onDisconnected"))
				if (i == 0)
					SpeakingBeforeActivity.this.startCamera();
			if (!str.equals("connected")) {
				// return;
				SpeakingBeforeActivity.this.callFailed(i);
				return;
			}
			SpeakingBeforeActivity.this.callSuccess();
		}
	}

	class CameraView extends SurfaceView {
		private SurfaceHolder holder = null;

		public CameraView(Context arg2) {
			super(arg2);
			holder = this.getHolder();
			this.holder.addCallback(new SurfaceHolder.Callback() {
				public void surfaceChanged(
						SurfaceHolder paramAnonymousSurfaceHolder,
						int paramAnonymousInt1, int paramAnonymousInt2,
						int paramAnonymousInt3) {
					if (SpeakingBeforeActivity.this.camera == null)
						return;
					Camera.Parameters localParameters = SpeakingBeforeActivity.this.camera
							.getParameters();
					List localList = localParameters.getSupportedPreviewSizes();
					Camera.Size localSize = (Camera.Size) localList.get(0);
					for (int i = 1;; i++) {
						if (i >= localList.size()) {
							localParameters.setPreviewSize(localSize.width,
									localSize.height);
							SpeakingBeforeActivity.this.camera
									.setParameters(localParameters);
							SpeakingBeforeActivity.this.camera.startPreview();
							return;
						}
						if (((Camera.Size) localList.get(i)).width
								* ((Camera.Size) localList.get(i)).height > localSize.width
								* localSize.height)
							localSize = (Camera.Size) localList.get(i);
					}
				}

				// ERROR //
				public void surfaceCreated(
						SurfaceHolder paramAnonymousSurfaceHolder) {
					// Byte code:
					// 0: aload_0
					// 1: getfield 16
					// com/chinatel/robot/Activity/SpeakingBeforeActivity$CameraView$1:this$1
					// Lcom/chinatel/robot/Activity/SpeakingBeforeActivity$CameraView;
					// 4: invokestatic 25
					// com/chinatel/robot/Activity/SpeakingBeforeActivity$CameraView:access$0
					// (Lcom/chinatel/robot/Activity/SpeakingBeforeActivity$CameraView;)Lcom/chinatel/robot/Activity/SpeakingBeforeActivity;
					// 7: invokestatic 31
					// com/chinatel/robot/Activity/SpeakingBeforeActivity:access$3
					// (Lcom/chinatel/robot/Activity/SpeakingBeforeActivity;)Landroid/hardware/Camera;
					// 10: ifnonnull +16 -> 26
					// 13: aload_0
					// 14: getfield 16
					// com/chinatel/robot/Activity/SpeakingBeforeActivity$CameraView$1:this$1
					// Lcom/chinatel/robot/Activity/SpeakingBeforeActivity$CameraView;
					// 17: invokestatic 25
					// com/chinatel/robot/Activity/SpeakingBeforeActivity$CameraView:access$0
					// (Lcom/chinatel/robot/Activity/SpeakingBeforeActivity$CameraView;)Lcom/chinatel/robot/Activity/SpeakingBeforeActivity;
					// 20: invokestatic 83 android/hardware/Camera:open
					// ()Landroid/hardware/Camera;
					// 23: invokestatic 87
					// com/chinatel/robot/Activity/SpeakingBeforeActivity:access$4
					// (Lcom/chinatel/robot/Activity/SpeakingBeforeActivity;Landroid/hardware/Camera;)V
					// 26: aload_0
					// 27: getfield 16
					// com/chinatel/robot/Activity/SpeakingBeforeActivity$CameraView$1:this$1
					// Lcom/chinatel/robot/Activity/SpeakingBeforeActivity$CameraView;
					// 30: invokestatic 25
					// com/chinatel/robot/Activity/SpeakingBeforeActivity$CameraView:access$0
					// (Lcom/chinatel/robot/Activity/SpeakingBeforeActivity$CameraView;)Lcom/chinatel/robot/Activity/SpeakingBeforeActivity;
					// 33: invokestatic 31
					// com/chinatel/robot/Activity/SpeakingBeforeActivity:access$3
					// (Lcom/chinatel/robot/Activity/SpeakingBeforeActivity;)Landroid/hardware/Camera;
					// 36: iconst_0
					// 37: invokevirtual 91
					// android/hardware/Camera:setDisplayOrientation (I)V
					// 40: aload_0
					// 41: getfield 16
					// com/chinatel/robot/Activity/SpeakingBeforeActivity$CameraView$1:this$1
					// Lcom/chinatel/robot/Activity/SpeakingBeforeActivity$CameraView;
					// 44: invokestatic 25
					// com/chinatel/robot/Activity/SpeakingBeforeActivity$CameraView:access$0
					// (Lcom/chinatel/robot/Activity/SpeakingBeforeActivity$CameraView;)Lcom/chinatel/robot/Activity/SpeakingBeforeActivity;
					// 47: invokestatic 31
					// com/chinatel/robot/Activity/SpeakingBeforeActivity:access$3
					// (Lcom/chinatel/robot/Activity/SpeakingBeforeActivity;)Landroid/hardware/Camera;
					// 50: aload_1
					// 51: invokevirtual 94
					// android/hardware/Camera:setPreviewDisplay
					// (Landroid/view/SurfaceHolder;)V
					// 54: return
					// 55: astore_2
					// 56: aload_0
					// 57: getfield 16
					// com/chinatel/robot/Activity/SpeakingBeforeActivity$CameraView$1:this$1
					// Lcom/chinatel/robot/Activity/SpeakingBeforeActivity$CameraView;
					// 60: invokestatic 25
					// com/chinatel/robot/Activity/SpeakingBeforeActivity$CameraView:access$0
					// (Lcom/chinatel/robot/Activity/SpeakingBeforeActivity$CameraView;)Lcom/chinatel/robot/Activity/SpeakingBeforeActivity;
					// 63: ldc 96
					// 65: iconst_0
					// 66: invokestatic 102 android/widget/Toast:makeText
					// (Landroid/content/Context;Ljava/lang/CharSequence;I)Landroid/widget/Toast;
					// 69: invokevirtual 105 android/widget/Toast:show ()V
					// 72: aload_0
					// 73: getfield 16
					// com/chinatel/robot/Activity/SpeakingBeforeActivity$CameraView$1:this$1
					// Lcom/chinatel/robot/Activity/SpeakingBeforeActivity$CameraView;
					// 76: invokestatic 25
					// com/chinatel/robot/Activity/SpeakingBeforeActivity$CameraView:access$0
					// (Lcom/chinatel/robot/Activity/SpeakingBeforeActivity$CameraView;)Lcom/chinatel/robot/Activity/SpeakingBeforeActivity;
					// 79: aconst_null
					// 80: invokestatic 87
					// com/chinatel/robot/Activity/SpeakingBeforeActivity:access$4
					// (Lcom/chinatel/robot/Activity/SpeakingBeforeActivity;Landroid/hardware/Camera;)V
					// 83: return
					// 84: astore_3
					// 85: aload_0
					// 86: getfield 16
					// com/chinatel/robot/Activity/SpeakingBeforeActivity$CameraView$1:this$1
					// Lcom/chinatel/robot/Activity/SpeakingBeforeActivity$CameraView;
					// 89: invokestatic 25
					// com/chinatel/robot/Activity/SpeakingBeforeActivity$CameraView:access$0
					// (Lcom/chinatel/robot/Activity/SpeakingBeforeActivity$CameraView;)Lcom/chinatel/robot/Activity/SpeakingBeforeActivity;
					// 92: invokestatic 31
					// com/chinatel/robot/Activity/SpeakingBeforeActivity:access$3
					// (Lcom/chinatel/robot/Activity/SpeakingBeforeActivity;)Landroid/hardware/Camera;
					// 95: invokevirtual 108 android/hardware/Camera:release ()V
					// 98: aload_0
					// 99: getfield 16
					// com/chinatel/robot/Activity/SpeakingBeforeActivity$CameraView$1:this$1
					// Lcom/chinatel/robot/Activity/SpeakingBeforeActivity$CameraView;
					// 102: invokestatic 25
					// com/chinatel/robot/Activity/SpeakingBeforeActivity$CameraView:access$0
					// (Lcom/chinatel/robot/Activity/SpeakingBeforeActivity$CameraView;)Lcom/chinatel/robot/Activity/SpeakingBeforeActivity;
					// 105: aconst_null
					// 106: invokestatic 87
					// com/chinatel/robot/Activity/SpeakingBeforeActivity:access$4
					// (Lcom/chinatel/robot/Activity/SpeakingBeforeActivity;Landroid/hardware/Camera;)V
					// 109: aload_3
					// 110: invokevirtual 111
					// java/io/IOException:printStackTrace ()V
					// 113: return
					//
					// Exception table:
					// from to target type
					// 0 26 55 java/lang/Exception
					// 26 54 84 java/io/IOException
				}

				public void surfaceDestroyed(
						SurfaceHolder paramAnonymousSurfaceHolder) {
					SpeakingBeforeActivity.this.releaseCamera();
				}
			});
		}
	}
}

/*
 * Location:
 * C:\Users\Administrator\Desktop\小优\U03S源码\机器人本体.apk\classes_dex2jar.jar
 * Qualified Name: com.chinatel.robot.Activity.SpeakingBeforeActivity JD-Core
 * Version: 0.6.2
 */