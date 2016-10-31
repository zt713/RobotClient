package com.chinatel.robotclient.camera;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class UnarrowedDialog extends Dialog
{
  private CLickConfirmListenerInterface mClickListenerInterface;
  private Context mContext;
  private int mTitleText;

  public UnarrowedDialog(Context paramContext, int paramInt1, int paramInt2, CLickConfirmListenerInterface paramCLickConfirmListenerInterface)
  {
    super(paramContext, paramInt1);
    this.mContext = paramContext;
    this.mTitleText = paramInt2;
    this.mClickListenerInterface = paramCLickConfirmListenerInterface;
  }

  private void initView()
  {
    View localView = LayoutInflater.from(this.mContext).inflate(2130903053, null);
    setContentView(localView);
    TextView localTextView = (TextView)localView.findViewById(2131230824);
    Button localButton = (Button)localView.findViewById(2131230825);
    localTextView.setText(this.mTitleText);
    localButton.setOnClickListener(new ClickListener());
  }

  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    initView();
  }

  public static abstract interface CLickConfirmListenerInterface
  {
    public abstract void doBtn();
  }

  private class ClickListener
    implements View.OnClickListener
  {
    private ClickListener()
    {
    }

    public void onClick(View paramView)
    {
      UnarrowedDialog.this.mClickListenerInterface.doBtn();
    }
  }
}

/* Location:           C:\Users\Administrator\Desktop\test111\classes_dex2jar.jar
 * Qualified Name:     com.chinatel.robotclient.camera.UnarrowedDialog
 * JD-Core Version:    0.6.2
 */