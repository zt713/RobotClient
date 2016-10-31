package com.chinatel.robotclient.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import cn.wangjianlog.baseframework.MainApplication;
import cn.wangjianlog.baseframework.util.IntentUtil;
import com.chinatel.robotclient.rbuitl.PreferencesUtil;

public class ConnectRobotActivity extends Activity
{
  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    String str = getIntent().getStringExtra("key");
//    int i = MainApplication.getInstance().connectRobot("uoes" + str);
    int i = MainApplication.getInstance().connectRobot("1003");
    /*PreferencesUtil.putString(this, "robot_name", "uoes" + str);
    if (i != -1)

    
    {
      finish();
      IntentUtil.startActivity(this, ConnectFailedActivity.class, false);
      return;
    }*/

    IntentUtil.startActivity(this, ConnectActivity.class, false);
  }

  protected void onDestroy()
  {
    super.onDestroy();
  }

  protected void onPause()
  {
    super.onPause();
  }
}

/* Location:           C:\Users\Administrator\Desktop\test111\classes_dex2jar.jar
 * Qualified Name:     com.chinatel.robotclient.activity.ConnectRobotActivity
 * JD-Core Version:    0.6.2
 */