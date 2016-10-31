package com.chinatel.robotclient.activity;

import android.app.Activity;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PowerManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import cn.wangjianlog.baseframework.MainApplication;
import cn.wangjianlog.baseframework.tools.NetWorkUtil;

import com.chinatel.robotclient.R;
import com.chinatel.robotclient.media.SoundPlayer;
import com.chinatel.robotclient.receiver.IsCallCloseReceiver;

public class IsCalledActivity extends Activity
  implements View.OnTouchListener
{
  public static final String IS_CALL_RECEIVER = "com.chinatel.robotclient.IS_CALL_RECEIVER";
  private final int ANSWER = 1;
  private final int HANG_UP = 2;
  private final int PLAY_SOUND = 0;
  private AnimationDrawable anDrawable;
  private boolean answerStatus;
  private RelativeLayout card;
  Handler handler = new Handler()
  {
    public void handleMessage(Message paramAnonymousMessage)
    {
      switch (paramAnonymousMessage.what)
      {
      case 0:
        IsCalledActivity.this.playerMusic();
        return;
      case 1:
        IsCalledActivity.this.unlock();
        IsCalledActivity.this.sp.stop();
        Log.i("robot", "answer");
        Intent localIntent = new Intent();
        localIntent.putExtra("tag", "isCall");
        localIntent.setClass(IsCalledActivity.this.mContext, CallPageActivity.class);
        IsCalledActivity.this.startActivity(localIntent);
        IsCalledActivity.this.finish();
        return;
      case 2:
        IsCalledActivity.this.sp.stop();
        Log.i("robot", "hang_up");
//      IsCalledActivity.this.addCallLog();
        IsCalledActivity.this.finish();
        MainApplication.getInstance().disconnect();
        return;
      default:
        break;
      }
    }
  };
  private ImageView instruction;
  private TextView is_call_toast;
  int lastY;
  private Context mContext;
  private IsCallCloseReceiver mReceiver;
  int margin = 100;
  PowerManager pm;
  int screenHeight;
  private SoundPlayer sp;
  PowerManager.WakeLock wakeLock;

  private void playerMusic()
  {
    this.sp.setLooping(true);
    this.sp.play();
  }

  private void registerReceiver()
  {
    IntentFilter localIntentFilter = new IntentFilter("com.chinatel.robotclient.receiver.IsCallCloseReceiver");
    this.mReceiver = new IsCallCloseReceiver(new IsCallCloseReceiver.IsCallCloseReceiverListener()
    {
      public void close()
      {
        IsCalledActivity.this.finish();
      }
    });
    registerReceiver(this.mReceiver, localIntentFilter);
  }

  private void setupAnswerDown()
  {
    this.instruction = ((ImageView)findViewById(R.id.instruction));
    this.instruction.setImageResource(R.drawable.animation);
    this.anDrawable = ((AnimationDrawable)this.instruction.getDrawable());
    this.anDrawable.start();
  }

  private void setupInternetStatus()
  {
    this.is_call_toast = ((TextView)findViewById(R.id.is_call_toast));
    int i = NetWorkUtil.getCurrentNetworkInfo(this.mContext);
    if (i == 1)
    {
      this.is_call_toast.setText("当前为非 WiFi 网络,将使用少量手机流量");
      return;
    }
    if (i == 2)
    {
      this.is_call_toast.setText("当前为WiFi网络，通话完全免费");
      return;
    }
    this.is_call_toast.setText("当前没有网络连接，不能进行通话");
  }

 /* public MyCallLog addCallLog()
  {
    try
    {
      CallLogDao localCallLogDao = new CallLogDao(this, MyCallLog.class);
      String str1 = DateUtil.getCurDay();
      String str2 = DateUtil.getCurTime("HH:mm");
      MyCallLog localMyCallLog = (MyCallLog)localCallLogDao.add(new MyCallLog(Integer.valueOf(0), "头像", "小优", "00:00:00", str1, str2, Integer.valueOf(0)));
      return localMyCallLog;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return null;
  }*/

  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    getWindow().addFlags(6815872);
    setContentView(R.layout.activity_is_called);
    this.mContext = this;
    this.sp = SoundPlayer.getInstance(this.mContext, "is_call.mp3");
    this.card = ((RelativeLayout)findViewById(R.id.card));
    this.card.setOnTouchListener(this);
    setupAnswerDown();
    this.screenHeight = (getResources().getDisplayMetrics().heightPixels - this.margin);
    setupInternetStatus();
    this.handler.sendEmptyMessage(0);
    registerReceiver();
  }

  protected void onDestroy()
  {
    super.onDestroy();
    this.sp.stop();
    unregisterReceiver(this.mReceiver);
  }

  public boolean onKeyDown(int paramInt, KeyEvent paramKeyEvent)
  {
    if (paramInt == 4)
    {
      Message localMessage = new Message();
      localMessage.what = 2;
      this.handler.sendMessage(localMessage);
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
    super.onResume();
    this.answerStatus = false;
  }

  public boolean onTouch(View paramView, MotionEvent paramMotionEvent)
  {
    switch (paramMotionEvent.getAction())
    {
      //手指按下时
      case MotionEvent.ACTION_DOWN:
        this.lastY = ((int)paramMotionEvent.getRawY());
        return true;
      case MotionEvent.ACTION_UP:

        return false;
      //手指移动
      case MotionEvent.ACTION_MOVE:
        int i = (int)paramMotionEvent.getRawY() - this.lastY;
        int j = paramView.getLeft();
        int k = i + paramView.getTop();
        int m = paramView.getRight();
        int n = i + paramView.getBottom();
        Log.i("robot", "dy: " + i);
        if (k < this.margin)
        {
          if (!this.answerStatus)
          {
            Message localMessage2 = new Message();
            localMessage2.what = 2;
            this.handler.sendMessage(localMessage2);
          }
          k = this.margin;
          n = k + paramView.getHeight();
          this.answerStatus = true;
        }
        if (n > this.screenHeight)
        {
          if (!this.answerStatus)
          {
            Log.i("robot", "------");
            Message localMessage1 = new Message();
            localMessage1.what = 1;
            this.handler.sendMessage(localMessage1);
          }
          n = this.screenHeight;
          k = n - paramView.getHeight();
          this.answerStatus = true;
        }
        paramView.layout(j, k, m, n);
        this.lastY = ((int)paramMotionEvent.getRawY());

        return true;
      default:

        return false;
    }
  }

  public void unlock()
  {
    ((KeyguardManager)getSystemService(Context.KEYGUARD_SERVICE)).newKeyguardLock("").disableKeyguard();
  }
}

/* Location:           C:\Users\Administrator\Desktop\test111\classes_dex2jar.jar
 * Qualified Name:     com.chinatel.robotclient.activity.IsCalledActivity
 * JD-Core Version:    0.6.2
 */