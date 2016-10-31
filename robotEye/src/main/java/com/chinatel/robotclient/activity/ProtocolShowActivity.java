package com.chinatel.robotclient.activity;

import android.app.Activity;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.chinatel.robotclient.protocol.show.AsyncTextLoadTask;
import com.chinatel.robotclient.protocol.show.BorderScrollView;
import com.chinatel.robotclient.protocol.show.OnScrollChangedListenerSimple;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ProtocolShowActivity extends Activity
{
  private BufferedReader br;
  private BorderScrollView contentScroll;
  private boolean isLoading;
  private LinearLayout llshow;
  Handler mhandler = new Handler()
  {
    public void handleMessage(Message paramAnonymousMessage)
    {
      super.handleMessage(paramAnonymousMessage);
      switch (paramAnonymousMessage.what)
      {
      default:
        return;
      case 1:
      }
      TextView localTextView = new TextView(ProtocolShowActivity.this);
      localTextView.setTextColor(-7829368);
      localTextView.setTextSize(15.0F);
      WindowManager.LayoutParams localLayoutParams = ProtocolShowActivity.this.getWindow().getAttributes();
      localLayoutParams.flags = (0x400 | localLayoutParams.flags);
      ProtocolShowActivity.this.llshow.addView(localTextView, localLayoutParams);
      localTextView.setText((CharSequence)paramAnonymousMessage.obj);
      ProtocolShowActivity.this.isLoading = false;
    }
  };

  public void back(View paramView)
  {
    if (this.br != null);
    try
    {
      this.br.close();
      finish();
      return;
    }
    catch (IOException localIOException)
    {
      while (true)
        localIOException.printStackTrace();
    }
  }

  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(2130903057);
    Log.e("myrobot", "ProtocolShowActivity oncreate");
    this.llshow = ((LinearLayout)findViewById(2131230834));
    this.contentScroll = ((BorderScrollView)findViewById(2131230832));
    this.contentScroll.setOnScrollChangedListener(new OnScrollChangedListenerSimple()
    {
      public void onScrollBottom()
      {
        synchronized (ProtocolShowActivity.this)
        {
          if (!ProtocolShowActivity.this.isLoading)
          {
            ProtocolShowActivity.this.isLoading = true;
            new AsyncTextLoadTask(ProtocolShowActivity.this, ProtocolShowActivity.this.br, ProtocolShowActivity.this.mhandler).execute(new Object[0]);
          }
          return;
        }
      }
    });
    try
    {
      this.br = new BufferedReader(new InputStreamReader(getAssets().open("usingprotocol.txt")));
      new AsyncTextLoadTask(this, this.br, this.mhandler).execute(new Object[0]);
      Log.e("myrobot", "ProtocolShowActivity oncreate 22222");
      return;
    }
    catch (Exception localException)
    {
      while (true)
        localException.printStackTrace();
    }
  }

  protected void onDestroy()
  {
    super.onDestroy();
    if (this.br != null);
    try
    {
      this.br.close();
      return;
    }
    catch (IOException localIOException)
    {
      localIOException.printStackTrace();
    }
  }
}

/* Location:           C:\Users\Administrator\Desktop\test111\classes_dex2jar.jar
 * Qualified Name:     com.chinatel.robotclient.activity.ProtocolShowActivity
 * JD-Core Version:    0.6.2
 */