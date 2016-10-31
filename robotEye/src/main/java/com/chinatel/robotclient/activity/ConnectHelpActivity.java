package com.chinatel.robotclient.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class ConnectHelpActivity extends Activity
{
  private Button help_back;

  public void closeHelpPage()
  {
    finish();
  }

  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(2130903043);
    this.help_back = ((Button)findViewById(2131230746));
    this.help_back.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        ConnectHelpActivity.this.closeHelpPage();
      }
    });
  }
}

/* Location:           C:\Users\Administrator\Desktop\test111\classes_dex2jar.jar
 * Qualified Name:     com.chinatel.robotclient.activity.ConnectHelpActivity
 * JD-Core Version:    0.6.2
 */