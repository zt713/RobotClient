package com.chinatel.robotclient.activity;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextPaint;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import cn.wangjianlog.baseframework.util.IntentUtil;
import com.chinatel.robotclient.rbuitl.PreferencesUtil;

public class ConnectFailedActivity extends Activity
  implements View.OnClickListener
{
  private TextView go_help;
  private ImageButton go_home;
  private Button go_setting;

  public void onClick(View paramView)
  {
    switch (paramView.getId())
    {
    default:
      return;
    case 2131230745:
      IntentUtil.startActivity(this, ConnectHelpActivity.class, false);
      return;
    case 2131230743:
      PreferencesUtil.removeKey(this, "auto");
//      IntentUtil.startActivity(this, ManualConnectActivity.class, true);
      return;
    case 2131230744:
    }
    finish();
  }

  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(2130903042);
    this.go_help = ((TextView)findViewById(2131230745));
    this.go_help.getPaint().setFlags(8);
    this.go_setting = ((Button)findViewById(2131230743));
    this.go_home = ((ImageButton)findViewById(2131230744));
    this.go_setting.setOnClickListener(this);
    this.go_help.setOnClickListener(this);
    this.go_home.setOnClickListener(this);
  }
}

/* Location:           C:\Users\Administrator\Desktop\test111\classes_dex2jar.jar
 * Qualified Name:     com.chinatel.robotclient.activity.ConnectFailedActivity
 * JD-Core Version:    0.6.2
 */