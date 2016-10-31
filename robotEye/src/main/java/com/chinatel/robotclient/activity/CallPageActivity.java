package com.chinatel.robotclient.activity;

import java.util.Date;

import jni.util.Utils;
import rtc.sdk.iface.Connection;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.os.Build;
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
import cn.wangjianlog.baseframework.tools.DateUtil;
import cn.wangjianlog.baseframework.view.ToastUtil;

import com.chinatel.robotclient.R;
import com.chinatel.robotclient.rbuitl.PreferencesUtil;
import com.chinatel.robotclient.rbuitl.ProgressUtil;
import com.chinatel.robotclient.rbuitl.RemotePictureUtil;
import com.chinatel.robotclient.receiver.CallVideoReceiver;
import com.chinatel.robotclient.view.CallLogWindow;
import com.nineoldandroids.view.ViewHelper;

public class CallPageActivity extends Activity
  implements View.OnClickListener, View.OnTouchListener
{
  private String LOGTAG = "canbot";
  private final int SLEET_ONE_SECOND = 1000;
  private ImageButton call_log_page_btn;
  private ImageButton camera_call_page;
  private Date closeDate;
  private ImageButton go_account;
  Handler handler = new Handler()
  {
    public void handleMessage(Message paramAnonymousMessage)
    {
      switch (paramAnonymousMessage.what)
      {
      case 1000:
        CallPageActivity.this.callLogHide();
        break;
      default:
        break;
      }
    }
  };
  private ImageButton hang_up;
  private boolean isClick;
  private AudioManager mAudioManager;
  private Context mContext;
  private long mExitTime;
  private CallVideoReceiver mReceiver;
  SurfaceView mvLocal = null;
  SurfaceView mvRemote = null;
  private Date openDate;
  private ImageView progress_big;
  private ImageView progress_small;
  private boolean switchClick;
  private String tag;
  private RelativeLayout video_call_page_bg;
  private RelativeLayout video_call_page_small;

  private void addCallLogOut()
  {
    try
    {/*
      CallLogDao localCallLogDao = new CallLogDao(this, MyCallLog.class);
      this.closeDate = new Date();
      long l = this.closeDate.getTime() - this.openDate.getTime();
      Time localTime = new Time("GMT+8");
      localTime.set(l);
      StringBuffer localStringBuffer1 = new StringBuffer();
      StringBuffer localStringBuffer2 = new StringBuffer();
      StringBuffer localStringBuffer3 = new StringBuffer();
      localStringBuffer1.append(localTime.hour);
      localStringBuffer2.append(localTime.minute);
      localStringBuffer3.append(localTime.second);
      if (localStringBuffer1.length() == 1)
        localStringBuffer1.insert(0, 0);
      if (localStringBuffer2.length() == 1)
        localStringBuffer2.insert(0, 0);
      if (localStringBuffer3.length() == 1)
        localStringBuffer3.insert(0, 0);
      String str = localStringBuffer1 + ":" + localStringBuffer2 + ":" + localStringBuffer3;
      Log.i("robot", "diff:" + str);
      this.callLog.setDuration(str);
      localCallLogDao.add(this.callLog);
      return;
    */}
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }

  private void busyCloseCallPage()
  {
    MainApplication.getInstance().disconnect();
    addCallLogOut();
    Intent localIntent = new Intent();
    localIntent.putExtra("tag", "isBusy");
    localIntent.setClass(this.mContext, ControlActivity.class);
    startActivity(localIntent);
    unregisterReceiver();
    finish();
  }

  private void closeCallPage()
  {
    if (this.isClick)
      return;
    this.isClick = true;
    MainApplication.getInstance().disconnect();
    addCallLogOut();
    if ((this.tag != null) && (this.tag.equals("controlPage")))
    {
      Intent localIntent = new Intent();
      localIntent.putExtra("tag", "isCall");
      localIntent.setClass(this.mContext, ControlActivity.class);
      startActivity(localIntent);
    }
    unregisterReceiver();
    finish();
  }

  private void exit()
  {
    if (System.currentTimeMillis() - this.mExitTime > 2000L)
    {
      Toast.makeText(this, "再按一次关闭视频通话", 0).show();
      this.mExitTime = System.currentTimeMillis();
      return;
    }
    closeCallPage();
  }

  private void initCallLogOut()
  {
    String str1 = DateUtil.getCurDay();
    String str2 = DateUtil.getCurTime("HH:mm");
    this.openDate = new Date();
//    this.callLog = new MyCallLog(Integer.valueOf(1), "", "小优", "00:00:00", str1, str2, Integer.valueOf(1));
  }

  private void loadingBig()
  {
    this.progress_big = ((ImageView)findViewById(R.id.progress_big));
    ProgressUtil.rotation(this.progress_big, R.drawable.progress_big);
  }

  private void loadingSmall()
  {
    this.progress_small = ((ImageView)findViewById(R.id.progress_small));
    ProgressUtil.rotation(this.progress_small, R.drawable.progress_small);
  }

  private void registerReceiver()
  {
    IntentFilter localIntentFilter = new IntentFilter("com.chinatel.robotclient.receiver.VIDEO_RECIEVER");
    this.mReceiver = new CallVideoReceiver(new CallVideoReceiver.CallVideoReceiverCallBack()
    {
      public void busy()
      {
        CallPageActivity.this.busyCloseCallPage();
      }

      public void close()
      {
        CallPageActivity.this.tag = null;
        CallPageActivity.this.closeCallPage();
      }
      public void showVideo()
      {
          MainApplication.getInstance().getRtcClient().enableSpeaker(CallPageActivity.this.mAudioManager, Boolean.valueOf(true));
          Utils.PrintLog(5, CallPageActivity.this.LOGTAG, "call_page_showVideo");
          Log.i("robot", "通话界面showVideo");
          CallPageActivity.this.initVideoViews();
          Connection localConnection = MainApplication.getInstance().mCall;
          if (localConnection != null){
            MainApplication.getInstance().mCall.buildVideo(CallPageActivity.this.mvRemote);
            CallPageActivity.this.mvLocal.setVisibility(View.VISIBLE);
            CallPageActivity.this.mvRemote.setVisibility(View.VISIBLE);
            MainApplication.getInstance().mCall.setCameraAngle(0);
          }
      }
    });
    registerReceiver(this.mReceiver, localIntentFilter);
  }

  private void setup()
  {
    this.call_log_page_btn = ((ImageButton)findViewById(R.id.call_log_page_btn));
    this.hang_up = ((ImageButton)findViewById(R.id.hang_up));
    this.go_account = ((ImageButton)findViewById(R.id.go_account3));
    ViewHelper.setAlpha(this.go_account, 0.0F);
    ViewHelper.setAlpha(this.call_log_page_btn, 0.0F);
    this.video_call_page_bg = ((RelativeLayout)findViewById(R.id.video_call_page_bg));
    this.video_call_page_small = ((RelativeLayout)findViewById(R.id.video_call_page_small));
    this.camera_call_page = ((ImageButton)findViewById(R.id.camera_call_page));
    this.video_call_page_bg.setOnClickListener(this);
    this.video_call_page_bg.setOnTouchListener(this);
    this.video_call_page_small.setOnClickListener(this);
    this.hang_up.setOnClickListener(this);
    this.call_log_page_btn.setOnClickListener(this);
    this.camera_call_page.setOnClickListener(this);
    this.go_account.setOnClickListener(this);
    loadingSmall();
    loadingBig();
  }

  private void unregisterReceiver()
  {
    unregisterReceiver(this.mReceiver);
  }

  public void callLogHide()
  {
//    float f = ViewHelper.getAlpha(this.go_account);
//    Log.i("robot", "alphaHome : " + f);
//    if (f == 0.0F)
//      return;
//    ObjectAnimator localObjectAnimator1 = ObjectAnimator.ofFloat(this.call_log_page_btn, "alpha", new float[] { 1.0F, 0.0F });
//    localObjectAnimator1.setDuration(700L);
//    localObjectAnimator1.start();
//    ObjectAnimator localObjectAnimator2 = ObjectAnimator.ofFloat(this.go_account, "alpha", new float[] { 1.0F, 0.0F });
//    localObjectAnimator2.setDuration(700L);
//    localObjectAnimator2.start();
  }

  int getInCallStream()
  {
    if (((Build.BRAND.equalsIgnoreCase("archos")) && (Build.DEVICE.equalsIgnoreCase("g7a"))) || ((Build.BRAND.equalsIgnoreCase("Huawei")) && (Build.DEVICE.equalsIgnoreCase("hwC8813Q"))) || ((Build.BRAND.equalsIgnoreCase("Huawei")) && (Build.DEVICE.equalsIgnoreCase("hwc8813"))) || ((Build.BRAND.equalsIgnoreCase("Xiaomi")) && (Build.MODEL.contains("MI 3"))) || ((Build.BRAND.equalsIgnoreCase("Xiaomi")) && (Build.MODEL.contains("MI 2"))) || ((Build.BRAND.equalsIgnoreCase("samsung")) && (Build.DEVICE.equalsIgnoreCase("klte"))) || ((Build.BRAND.equalsIgnoreCase("samsung")) && (Build.DEVICE.equalsIgnoreCase("klteduosctc"))) || ((Build.BRAND.equalsIgnoreCase("samsung")) && (Build.DEVICE.equalsIgnoreCase("hllte"))) || ((Build.BRAND.equalsIgnoreCase("samsung")) && (Build.DEVICE.equalsIgnoreCase("trltechn"))) || ((Build.BRAND.equalsIgnoreCase("Huawei")) && (Build.DEVICE.equalsIgnoreCase("hwB199"))) || ((Build.BRAND.equalsIgnoreCase("TCL")) && (Build.DEVICE.equalsIgnoreCase("Diablo_LTE"))) || ((Build.BRAND.equalsIgnoreCase("Xiaomi")) && (Build.MODEL.contains("MI 4"))))
      return 3;
    return 0;
  }

  void initVideoViews()
  {
    Connection localConnection = MainApplication.getInstance().mCall;
    if ((this.mvLocal != null) || (localConnection == null))
      return;
    Utils.PrintLog(5, this.LOGTAG, "initVideoViews");
    this.mvRemote = ((SurfaceView)localConnection.createVideoView(false, this, false));
    this.mvRemote.setVisibility(View.INVISIBLE);
    this.video_call_page_bg.addView(this.mvRemote);
    this.mvRemote.setKeepScreenOn(true);
    this.mvRemote.setZOrderMediaOverlay(true);
    this.mvLocal = ((SurfaceView)localConnection.createVideoView(true, this, true));
    this.mvLocal.setVisibility(View.INVISIBLE);
    this.video_call_page_small.addView(this.mvLocal);
    this.mvLocal.setKeepScreenOn(true);
    this.mvLocal.setZOrderMediaOverlay(true);
    this.mvLocal.setZOrderOnTop(true);
  }

  public void onClick(View paramView)
  {
    switch (paramView.getId())
    {
    case 2131230732:
    case 2131230733:
    case 2131230735:
    default:
      return;
    case 2131230738:
      new CallLogWindow(this).show(this.call_log_page_btn, 0, -this.call_log_page_btn.getHeight());
      return;
    case 2131230737:
      closeCallPage();
      return;
    case 2131230734:
      this.video_call_page_small.removeAllViews();
      this.video_call_page_bg.removeAllViews();
      boolean bool1;
      SurfaceView localSurfaceView1 = null;
      boolean bool3;
      RelativeLayout localRelativeLayout2 = null;
      if (this.switchClick)
      {
        bool1 = false;
        this.switchClick = bool1;
        RelativeLayout localRelativeLayout1 = this.video_call_page_small;
        if (!this.switchClick)
        localSurfaceView1 = this.mvRemote;
        localRelativeLayout1.addView(localSurfaceView1);
        SurfaceView localSurfaceView2 = this.mvLocal;
        boolean bool2 = this.switchClick;
        bool3 = false;
        if (!bool2)
        localSurfaceView2.setZOrderOnTop(bool3);
        this.mvRemote.setZOrderOnTop(this.switchClick);
        localRelativeLayout2 = this.video_call_page_bg;
        if (!this.switchClick){
        	
        }
      }
      for (SurfaceView localSurfaceView3 = this.mvLocal; ; localSurfaceView3 = this.mvRemote)
      {
        localRelativeLayout2.addView(localSurfaceView3);
        if (MainApplication.getInstance().mCall == null)
          break;
        MainApplication.getInstance().mCall.resetVideoViews();
        localSurfaceView1 = this.mvLocal;
      }
    case 2131230736:
      label107: label239: label245: RemotePictureUtil.getRemotePicture(this.mContext);
      label132: label159: label230: return;
    case 2131230739:
    }
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
        MainApplication.getInstance().disconnect();
        CallPageActivity.this.addCallLogOut();
        CallPageActivity.this.unregisterReceiver();
        PreferencesUtil.removeKey(CallPageActivity.this, "auto");
//        IntentUtil.startActivity(CallPageActivity.this, ManualConnectActivity.class, true);
      }
    });
    localBuilder.create().show();
  }

  protected void onCreate(Bundle paramBundle) {
    super.onCreate(paramBundle);
    setContentView(R.layout.activity_call_page);
    this.mContext = this;
    Log.i("robot", "==============");
    setup();
    topBtnShow();
    registerReceiver();
    this.tag = getIntent().getStringExtra("tag");
    if (this.tag != null) {
      this.mAudioManager = ((AudioManager) getSystemService(Context.AUDIO_SERVICE));
      if (!this.tag.equals("isCall")) {
        if (this.tag.equals("controlPage")) {
//          MainApplication.getInstance().callRobot(PreferencesUtil.getString(this.mContext, "robot_name"));
          initCallLogOut();
          MainApplication.getInstance().callRobot("1003");
          Log.i("robot", "从控制界面跳通话界面");
          return;
        }

      }
      Log.i("robot", "从来电界面跳通话界面");
      if (MainApplication.getInstance().mCall == null) {
        unregisterReceiver();
        finish();
        return;
      }
      MainApplication.getInstance().mCall.accept(3);
    }
  }
  protected void onDestroy()
  {
    super.onDestroy();
  }

  public boolean onKeyDown(int paramInt, KeyEvent paramKeyEvent)
  {
    int i = getInCallStream();
    Log.e(this.LOGTAG, "Current audio mode sssss : " + i);
    int j = this.mAudioManager.getStreamVolume(i);
    MainApplication.getInstance().getRtcClient().enableSpeaker(this.mAudioManager, Boolean.valueOf(true));
    if (paramInt == 24)
    {
      this.mAudioManager.setStreamVolume(i, j + 1, 1);
      Log.e(this.LOGTAG, "Current audio volume up sssss");
      return true;
    }
    if (paramInt == 25)
    {
      this.mAudioManager.setStreamVolume(i, j - 1, 1);
      Log.e(this.LOGTAG, "Current audio volume up sssss");
      return true;
    }
    if (paramInt == 4)
    {
      exit();
      return true;
    }
    if (paramInt == 3)
    {
      ToastUtil.showToast(this, 0, "回到app首页，视频通话不挂断");
      return true;
    }
    return super.onKeyDown(paramInt, paramKeyEvent);
  }

  protected void onPause()
  {
    super.onPause();
  }

  protected void onResume()
  {
    MainApplication.getInstance().mCall.resetVideoViews();
    super.onResume();
  }

  public boolean onTouch(View paramView, MotionEvent paramMotionEvent)
  {
    switch (paramMotionEvent.getAction())
    {
    default:
    case 0:
    }
    while (true)
    {
      topBtnShow();
      return false;
    }
  }

  public void topBtnShow()
  {/*
    float f = ViewHelper.getAlpha(this.go_account);
    Log.i("robot", "alphaHome : " + f);
    if (f > 0.0F)
      return;
    AnimatorSet localAnimatorSet = new AnimatorSet();
    Animator[] arrayOfAnimator = new Animator[3];
//    arrayOfAnimator[0] = ObjectAnimator.ofFloat(this.call_log_page_btn, "translationY", new float[] { -100.0F, 0.0F });
//    arrayOfAnimator[1] = ObjectAnimator.ofFloat(this.call_log_page_btn, "alpha", new float[] { 0.0F, 1.0F });
//    arrayOfAnimator[2] = ObjectAnimator.ofFloat(this.go_account, "alpha", new float[] { 0.0F, 1.0F });
    localAnimatorSet.playTogether(arrayOfAnimator);
    localAnimatorSet.setDuration(700L);
    localAnimatorSet.addListener(new Animator.AnimatorListener()
    {
      public void onAnimationCancel(Animator paramAnonymousAnimator)
      {
      }

      public void onAnimationEnd(Animator paramAnonymousAnimator)
      {
        CallPageActivity.this.handler.sendEmptyMessageDelayed(1000, 1000L);
      }

      public void onAnimationRepeat(Animator paramAnonymousAnimator)
      {
      }

      public void onAnimationStart(Animator paramAnonymousAnimator)
      {
      }
    });
    localAnimatorSet.start();
  */}
}

/* Location:           C:\Users\Administrator\Desktop\test111\classes_dex2jar.jar
 * Qualified Name:     com.chinatel.robotclient.activity.CallPageActivity
 * JD-Core Version:    0.6.2
 */