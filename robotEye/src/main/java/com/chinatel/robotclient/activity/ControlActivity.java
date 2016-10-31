package com.chinatel.robotclient.activity;

import jni.util.Utils;
import rtc.sdk.iface.Connection;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;
import cn.wangjianlog.baseframework.MainApplication;

import com.chinatel.robotclient.R;
import com.chinatel.robotclient.media.SoundPlayer;
import com.chinatel.robotclient.rbuitl.PreferencesUtil;
import com.chinatel.robotclient.rbuitl.RemotePictureUtil;
import com.chinatel.robotclient.receiver.CallVideoReceiver;
import com.chinatel.robotclient.view.CallLogWindow;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;
import com.nineoldandroids.view.ViewHelper;

public class ControlActivity extends Activity
  implements View.OnClickListener, View.OnTouchListener
{
  private final int CONTROL_BTN_APPEAR = 1500;
  private int HIDE_TIME = 5;
  private final int IS_BUSY_SOUND = 0;
  private String LOGTAG = "canbot";
  private final int SLEET_ONE_SECOND = 1000;
  private int TIME = 1000;
  private final int bodyDuanHouTui = 139;
  private final int bodyDuanQianjin = 138;
  private final int bodyDuanYouZhuan = 165;
  private final int bodyDuanZuoZhuan = 164;
  private final int bodyHouTui = 2;
  private final int bodyQianjin = 1;
  private final int bodyShenTing = 5;
  private final int bodyYouZhuan = 4;
  private final int bodyZuoZhuan = 3;
  private ImageButton body_down;
  private ImageButton body_left;
  private ImageButton body_right;
  private ImageButton body_stop;
  private ImageButton body_up;
  private ImageButton camera_control;
  private boolean connectStaus;
  private RelativeLayout control_body;
  private RelativeLayout control_head;
  private ImageView control_info;
  private ImageButton currentBtn;
  private int currentTime = 0;
  private ImageButton go_account;
  private ImageButton go_call_log;
  private ImageButton go_call_page;
  Handler handler = new Handler()
  {
    public void handleMessage(Message paramAnonymousMessage)
    {
      super.handleMessage(paramAnonymousMessage);
      switch (paramAnonymousMessage.what)
      {
      case 1000:
        ControlActivity.this.callLogHide();
        return;
      case 0:
    	  ControlActivity.this.playBusySound();
    	  break;
      default:
    	  return;
      }
    }
  };
  private final int headDiTou = 163;
  private final int headDianTouHuiZhong = 13;
  private final int headTaiTou = 162;
  private final int headTouZhuanYou = 160;
  private final int headTouZhuanZuo = 161;
  private final int headYaoTouHuiZhong = 9;
  private ImageButton head_center;
  private ImageButton head_down;
  private ImageButton head_left;
  private ImageButton head_right;
  private ImageButton head_up;
  private boolean isClick = false;
  private boolean isRemote = false;
  private Context mContext;
  private long mExitTime;
  private CallVideoReceiver mReceiver;
  SurfaceView mvLocal = null;
  SurfaceView mvRemote = null;
  private String robotName;
  private Runnable runnable;
  private SoundPlayer sp;
  private RelativeLayout video_camera;
  private RelativeLayout video_control;

  private void executeCmd(int paramInt)
  {
    MainApplication.getInstance().controlRobot("1003", paramInt);
  }

  private void exit()
  {
    if (System.currentTimeMillis() - this.mExitTime > 2000L)
    {
      Toast.makeText(this, "再按一次关闭千里眼", Toast.LENGTH_SHORT).show();
      this.mExitTime = System.currentTimeMillis();
    }
    while (this.isClick)
      return;
    this.isClick = true;
    close();
  }

  private void registerReceiver()
  {
    IntentFilter localIntentFilter = new IntentFilter("com.chinatel.robotclient.receiver.VIDEO_RECIEVER");
    this.mReceiver = new CallVideoReceiver(new CallVideoReceiver.CallVideoReceiverCallBack()
    {
      public void busy()
      {
        ControlActivity.this.close();
      }

      public void close()
      {
        ControlActivity.this.close();
      }

      public void showVideo()
      {
        Log.i("robot", "控制界面showVideo()");
        ControlActivity.this.initVideoViews();
        MainApplication.getInstance().mCall.buildVideo(ControlActivity.this.mvRemote);
        ControlActivity.this.mvLocal.setVisibility(View.VISIBLE);
        ControlActivity.this.mvRemote.setVisibility(View.VISIBLE);
      }
    });
    registerReceiver(this.mReceiver, localIntentFilter);
  }

  private void selectedBtn(View paramView)
  {
    Log.i("robot", "方向按钮");
    if (this.currentBtn != null)
      this.currentBtn.setSelected(false);
    ImageButton localImageButton = (ImageButton)paramView;
    localImageButton.setSelected(true);
    this.currentBtn = localImageButton;
    callLogHide();
  }

  private void setupControl(){

    this.control_head = ((RelativeLayout)findViewById(R.id.control_head));
    this.control_body = ((RelativeLayout)findViewById(R.id.control_body));
    this.head_up = ((ImageButton)findViewById(R.id.head_up));
    this.head_down = ((ImageButton)findViewById(R.id.head_down));
    this.head_left = ((ImageButton)findViewById(R.id.head_left));
    this.head_right = ((ImageButton)findViewById(R.id.head_right));
    this.head_center = ((ImageButton)findViewById(R.id.head_center));
    this.body_up = ((ImageButton)findViewById(R.id.body_up));
    this.body_down = ((ImageButton)findViewById(R.id.body_down));
    this.body_left = ((ImageButton)findViewById(R.id.body_left));
    this.body_right = ((ImageButton)findViewById(R.id.body_right));
    this.body_stop = ((ImageButton)findViewById(R.id.body_stop));
    this.video_control = ((RelativeLayout)findViewById(R.id.video_control));
    this.video_camera = ((RelativeLayout)findViewById(R.id.video_camera));
    this.camera_control = ((ImageButton)findViewById(R.id.camera_control));
    this.go_call_page = ((ImageButton)findViewById(R.id.go_call_page));
    this.control_info = ((ImageView)findViewById(R.id.control_info));
    this.video_control.setOnTouchListener(this);
    this.control_head.setOnClickListener(this);
    this.control_body.setOnClickListener(this);
    this.head_up.setOnClickListener(this);
    this.head_down.setOnClickListener(this);
    this.head_left.setOnClickListener(this);
    this.head_right.setOnClickListener(this);
    this.head_center.setOnClickListener(this);
    this.body_up.setOnClickListener(this);
    this.body_down.setOnClickListener(this);
    this.body_left.setOnClickListener(this);
    this.body_right.setOnClickListener(this);
    this.body_stop.setOnClickListener(this);
    this.video_control.setOnClickListener(this);
    this.camera_control.setOnClickListener(this);
    this.go_call_page.setOnClickListener(this);
    if (this.isRemote)
    {
      this.control_info.setImageResource(R.drawable.remote_control);
      this.go_call_page.setVisibility(View.VISIBLE);
      if (isConnectStaus())
        this.control_info.setImageResource(R.drawable.is_calling);
    }
    if (!isConnectStaus())
    {
      headShow();
      bodyShow();
      return;
    }
    this.control_info.setImageResource(R.drawable.remote_control);
    this.go_call_page.setVisibility(View.GONE);
    if (isConnectStaus())
      this.control_info.setImageResource(R.drawable.is_calling);
    else
      this.control_info.setImageResource(R.drawable.local_message);

  }


  private void setupTimer()
  {
    this.runnable = new Runnable()
    {
      public void run()
      {
        ControlActivity.this.handler.postDelayed(this, ControlActivity.this.TIME);
        if ((ControlActivity.this.currentTime == 0) && (ControlActivity.this.control_head.getVisibility() == 4))
        {
          ControlActivity.this.headShow();
          ControlActivity.this.bodyShow();
        }
        ControlActivity localControlActivity = ControlActivity.this;
        localControlActivity.currentTime = (1 + localControlActivity.currentTime);
        System.out.println("-------" + ControlActivity.this.currentTime);
        if (ControlActivity.this.currentTime == ControlActivity.this.HIDE_TIME)
        {
          ControlActivity.this.headHide();
          ControlActivity.this.bodyHide();
        }
      }
    };
    this.handler.postDelayed(this.runnable, this.TIME);
  }

  private void setupTopBtn()
  {
    this.go_account = ((ImageButton)findViewById(R.id.go_account2));
    this.go_account.setOnClickListener(this);
    ViewHelper.setAlpha(this.go_account, 0.0F);
    this.go_call_log = ((ImageButton)findViewById(R.id.go_call_log));
    this.go_call_log.setOnClickListener(this);
    ViewHelper.setAlpha(this.go_call_log, 0.0F);
  }

  private void showControl()
  {
    this.currentTime = 0;
  }

  private void stopTimer()
  {
    this.handler.removeCallbacks(this.runnable);
  }

  private void unregisterReceiver()
  {
    if (this.mReceiver != null)
    {
      unregisterReceiver(this.mReceiver);
      this.mReceiver = null;
    }
  }

  public void bodyHide()
  {
    this.control_body.setVisibility(View.INVISIBLE);
    ObjectAnimator localObjectAnimator = ObjectAnimator.ofFloat(this.control_body, "alpha", new float[] { 1.0F, 0.0F });
    localObjectAnimator.setDuration(1500L);
    localObjectAnimator.start();
  }

  public void bodyShow()
  {
    this.control_body.setVisibility(View.VISIBLE);
    ObjectAnimator localObjectAnimator = ObjectAnimator.ofFloat(this.control_body, "alpha", new float[] { 0.0F, 1.0F });
    localObjectAnimator.setDuration(1500L);
    localObjectAnimator.start();
  }

  public void callLogHide()
  {
    float f = ViewHelper.getAlpha(this.go_account);
    Log.i("robot", "alphaHome : " + f);
    if (f < 1.0F)
      return;
    ObjectAnimator localObjectAnimator1 = ObjectAnimator.ofFloat(this.go_call_log, "alpha", new float[] { 1.0F, 0.0F });
    localObjectAnimator1.setDuration(700L);
    localObjectAnimator1.start();
    ObjectAnimator localObjectAnimator2 = ObjectAnimator.ofFloat(this.go_account, "alpha", new float[] { 1.0F, 0.0F });
    localObjectAnimator2.setDuration(700L);
    localObjectAnimator2.start();
  }

  public void close()
  {
    MainApplication.getInstance().disconnect();
    unregisterReceiver();
    finish();
  }

  public void headHide()
  {
    this.control_head.setVisibility(View.INVISIBLE);
    ObjectAnimator localObjectAnimator = ObjectAnimator.ofFloat(this.control_head, "alpha", new float[] { 1.0F, 0.0F });
    localObjectAnimator.setDuration(1500L);
    localObjectAnimator.start();
  }

  public void headShow()
  {
    this.control_head.setVisibility(View.VISIBLE);
    ObjectAnimator localObjectAnimator = ObjectAnimator.ofFloat(this.control_head, "alpha", new float[] { 0.0F, 1.0F });
    localObjectAnimator.setDuration(1500L);
    localObjectAnimator.start();
  }

  @SuppressLint("NewApi")
  void initVideoViews()
  {
    Connection localConnection = MainApplication.getInstance().mCall;
    if ((this.mvLocal != null) || (localConnection == null))
      return;
    Utils.PrintLog(5, this.LOGTAG, "initVideoViews");
    this.mvLocal = ((SurfaceView)localConnection.createVideoView(true, this, true));
    this.mvLocal.setVisibility(View.INVISIBLE);
    this.video_camera.addView(this.mvLocal);
    this.mvLocal.setKeepScreenOn(true);
    this.mvLocal.setZOrderMediaOverlay(true);
    this.mvRemote = ((SurfaceView)localConnection.createVideoView(false, this, false));
    this.mvRemote.setVisibility(View.INVISIBLE);
    this.video_control.addView(this.mvRemote);
    this.mvRemote.setKeepScreenOn(true);
    this.mvRemote.setZOrderMediaOverlay(true);
  }

  public void isBusySound()
  {
    this.handler.sendEmptyMessage(0);
  }

  public boolean isConnectStaus()
  {
    return this.connectStaus;
  }

  public void onClick(View paramView)
  {
    int i = paramView.getId();
    showControl();
    switch (i)
    {
    case R.id.control_body:
    case R.id.control_info:
    case R.id.go_call_page:
    	  Intent localIntent = new Intent();
          localIntent.putExtra("tag", "controlPage");
          localIntent.setClass(this.mContext, CallPageActivity.class);
          this.mContext.startActivity(localIntent);
          close();
          return;
    case R.id.go_call_log:
      new CallLogWindow(this).show(this.go_call_log, 0, -this.go_call_log.getHeight());
      return;
    case R.id.camera_control:
      RemotePictureUtil.getRemotePicture(this.mContext);
      return;
    case R.id.go_account2:
      AlertDialog.Builder localBuilder = new AlertDialog.Builder(this);
      localBuilder.setMessage("切换帐号");
      localBuilder.setNegativeButton("否", new DialogInterface.OnClickListener()
      {
        public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
        {
          paramAnonymousDialogInterface.dismiss();
        }
      });
      localBuilder.setPositiveButton("是", new DialogInterface.OnClickListener()
      {
        public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
        {
          paramAnonymousDialogInterface.dismiss();
          ControlActivity.this.close();
          PreferencesUtil.removeKey(ControlActivity.this, "auto");
//          IntentUtil.startActivity(ControlActivity.this, ManualConnectActivity.class, false);
        }
      });
      localBuilder.create().show();
      return;
    case R.id.head_up:
      Log.i("robot", "headup");
      selectedBtn(paramView);
      executeCmd(162);
      return;
    case R.id.head_down:
      selectedBtn(paramView);
      executeCmd(163);
      return;
    case R.id.head_left:
      selectedBtn(paramView);
      executeCmd(161);
      return;
    case R.id.head_right:
      selectedBtn(paramView);
      executeCmd(160);
      return;
    case R.id.head_center:
    	Log.i("robot", "head_center");
    	selectedBtn(paramView);
      if ((this.currentBtn == this.head_left) || (this.currentBtn == this.head_right))
      {
        executeCmd(9);
        Log.i("robot", "headYaoTouHuiZhong");
      }

        if ((this.currentBtn == this.head_up) || (this.currentBtn == this.head_down))
        {
          executeCmd(13);
          Log.i("robot", "headDianTouHuiZhong");
        }

    case R.id.body_up:
      selectedBtn(paramView);
      executeCmd(138);
      return;
    case R.id.body_down:
      selectedBtn(paramView);
      executeCmd(139);
      return;
    case R.id.body_left:
      selectedBtn(paramView);
      executeCmd(164);
      return;
    case R.id.body_right:
      selectedBtn(paramView);
      executeCmd(165);
      return;
    case R.id.body_stop:
    	selectedBtn(paramView);
    	executeCmd(5);
    }
  }

  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(R.layout.activity_control);
    this.mContext = this;
    int i = MainApplication.getInstance().currentNetInfo.getNetCode();
    if ((i != 21) && (i != 0))
      this.isRemote = true;
    setConnectStaus(false);
    setupTopBtn();
    topBtnShow();
    setupControl();
    registerReceiver();
    this.sp = SoundPlayer.getInstance(this.mContext, "is_busy.mp3");
    Log.i("robot", "ControlActivity onCreate()");
    String str = getIntent().getStringExtra("tag");
    if(MainApplication.getInstance().mCall == null) {
      if ((str != null) && (str.equals("isCall"))) {
        MainApplication.getInstance().connectRobot(PreferencesUtil.getString(this.mContext, "robot_name"));
        Log.i("robot", "通话界面挂断电话回到控制界面");
      }
      if ((str != null) && (str.equals("isBusy"))) {
        MainApplication.getInstance().connectRobot(PreferencesUtil.getString(this.mContext, "robot_name"));
        Log.i("robot", "通话界面挂断电话回到控制界面");
        isBusySound();
        return;
      }
    }

    initVideoViews();
    MainApplication.getInstance().mCall.buildVideo(this.mvRemote);
    this.mvLocal.setVisibility(View.VISIBLE);
    this.mvRemote.setVisibility(View.VISIBLE);
  }

  protected void onDestroy()
  {
    super.onDestroy();
  }

  public boolean onKeyDown(int paramInt, KeyEvent paramKeyEvent)
  {
    if (paramInt == 4)
    {
      exit();
      return true;
    }
    return super.onKeyDown(paramInt, paramKeyEvent);
  }

  protected void onPause()
  {
    super.onPause();
    this.sp.stop();
    stopTimer();
  }

  protected void onResume()
  {
    super.onResume();
    if (!isConnectStaus())
      setupTimer();
    this.robotName = PreferencesUtil.getString(this.mContext, "robot_name");
  }

  public boolean onTouch(View paramView, MotionEvent paramMotionEvent)
  {
    switch (paramMotionEvent.getAction())
    {
    case 0:
    	topBtnShow();
    	return false;
    default:
    	return true;
    }
  }

  public void playBusySound()
  {
    this.sp.play();
  }

  public void setConnectStaus(boolean paramBoolean)
  {
    this.connectStaus = paramBoolean;
  }

  public void topBtnShow()
  {
    float f = ViewHelper.getAlpha(this.go_account);
    Log.i("robot", "alphaHome : " + f);
    if (f > 0.0F)
      return;
    AnimatorSet localAnimatorSet = new AnimatorSet();
    Animator[] arrayOfAnimator = new Animator[3];
    arrayOfAnimator[0] = ObjectAnimator.ofFloat(this.go_call_log, "translationY", new float[] { -100.0F, 0.0F });
    arrayOfAnimator[1] = ObjectAnimator.ofFloat(this.go_call_log, "alpha", new float[] { 0.0F, 1.0F });
    arrayOfAnimator[2] = ObjectAnimator.ofFloat(this.go_account, "alpha", new float[] { 0.0F, 1.0F });
    localAnimatorSet.playTogether(arrayOfAnimator);
    localAnimatorSet.setDuration(700L);
    localAnimatorSet.addListener(new Animator.AnimatorListener()
    {
      public void onAnimationCancel(Animator paramAnonymousAnimator)
      {
      }

      public void onAnimationEnd(Animator paramAnonymousAnimator)
      {
        ControlActivity.this.handler.sendEmptyMessageDelayed(1000, 1000L);
      }

      public void onAnimationRepeat(Animator paramAnonymousAnimator)
      {
      }

      public void onAnimationStart(Animator paramAnonymousAnimator)
      {
      }
    });
    localAnimatorSet.start();
  }
}

/* Location:           C:\Users\Administrator\Desktop\test111\classes_dex2jar.jar
 * Qualified Name:     com.chinatel.robotclient.activity.ControlActivity
 * JD-Core Version:    0.6.2
 */