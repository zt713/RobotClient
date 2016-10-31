package com.chinatel.robotclient.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import cn.wangjianlog.baseframework.MainApplication;
import cn.wangjianlog.baseframework.tools.NetWorkUtil;
import cn.wangjianlog.baseframework.util.WifiAdmin;

import com.chinatel.robotclient.R;
import com.chinatel.robotclient.rbuitl.PreferencesUtil;

public class ConnectActivity extends Activity
  implements View.OnClickListener
{
  private ConnectionRobotReceiver conReceiver;
  private TextView connect_desc;
  private ImageButton go_account;
  private Context mContext;

  private void connectFailed()
  {
    Intent localIntent = new Intent();
    localIntent.setClass(this, ConnectFailedActivity.class);
    startActivity(localIntent);
    finish();
  }

  private void connectSuccess()
  {
    Intent localIntent = new Intent();
    localIntent.setClass(this, ControlActivity.class);
    startActivity(localIntent);
    finish();
  }

  private void hasInternet()
  {
    this.connect_desc.setText("正在远程连接机器人...");
  }

  private void noInternet()
  {
    this.connect_desc.setText("正在本地连接机器人...");
  }

  private void register()
  {
    this.conReceiver = new ConnectionRobotReceiver();
    IntentFilter localIntentFilter = new IntentFilter();
    localIntentFilter.addAction("com.chinatel.robotclient.activity");
    registerReceiver(this.conReceiver, localIntentFilter);
  }

  private void setConnectInfo()
  {
    int i = NetWorkUtil.getCurrentNetworkInfo(this.mContext);
    if (i == 1){
      hasInternet();
    }
	  new StartConnect().execute(new String[0]);
	  if (i == 2)
	  {
	    if (new WifiAdmin(this.mContext).getSSID().contains("uoes"))
	      noInternet();
	    else
	      hasInternet();
	  }
	  else if (i == 0)
	    this.connect_desc.setText("请检查网络连接");
  }

  public void onClick(View paramView)
  {
    switch (paramView.getId())
    {
    default:
      return;
    case 2131230741:
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
        PreferencesUtil.removeKey(ConnectActivity.this, "auto");
//        IntentUtil.startActivity(ConnectActivity.this, ManualConnectActivity.class, true);
      }
    });
    localBuilder.create().show();
  }

  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(R.layout.activity_connect);
    this.mContext = this;
    this.connect_desc = ((TextView)findViewById(R.id.connect_desc));
    this.go_account = ((ImageButton)findViewById(R.id.go_account));
    setConnectInfo();
    register();
    this.go_account.setOnClickListener(this);
  }

  protected void onDestroy()
  {
    super.onDestroy();
    unregisterReceiver(this.conReceiver);
  }

  public boolean onKeyDown(int paramInt, KeyEvent paramKeyEvent)
  {
    if (paramInt == 4)
    {
      MainApplication.getInstance().disconnect();
      finish();
      return true;
    }
    return super.onKeyDown(paramInt, paramKeyEvent);
  }

  public class ConnectionRobotReceiver extends BroadcastReceiver
  {
    public static final String BROADCAST_ACTION = "com.chinatel.robotclient.activity";
    public static final String CONNECTED = "connected";
    public static final String DISCONNECTED = "onDisconnected";
    public static final String NAME = "connect";

    public ConnectionRobotReceiver()
    {
    }

    public void onReceive(Context paramContext, Intent paramIntent)
    {
      String str = paramIntent.getStringExtra("connect");
      if (str.equals("onDisconnected"))
        ConnectActivity.this.connectFailed();
      if (!str.equals("connected"))return;
      ConnectActivity.this.connectSuccess();
    }
  }

  class StartConnect extends AsyncTask<String, Integer, Integer>
  {
    StartConnect()
    {
    }

    protected Integer doInBackground(String[] paramArrayOfString)
    {
      try
      {
        Thread.sleep(2000L);
      }
      catch (InterruptedException localInterruptedException)
      {
        localInterruptedException.printStackTrace();
      }
      return null;
    }

    protected void onPostExecute(Integer paramInteger)
    {
      super.onPostExecute(paramInteger);
    }
  }
}

/* Location:           C:\Users\Administrator\Desktop\test111\classes_dex2jar.jar
 * Qualified Name:     com.chinatel.robotclient.activity.ConnectActivity
 * JD-Core Version:    0.6.2
 */