package com.chinatel.robotclient.camera;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class CenterDialog extends Dialog
{
  private int mBkBtnLeft;
  private int mBkBtnRight;
  private String mCancelBtnText;
  private CLickListenerInterface mClickListenerInterface;
  private String mConfirmBtnText;
  private Context mContext;
  private int mTitleText;

  public CenterDialog(Context paramContext, int paramInt1, int paramInt2, String paramString1, String paramString2, int paramInt3, int paramInt4, CLickListenerInterface paramCLickListenerInterface)
  {
    super(paramContext, paramInt1);
    this.mContext = paramContext;
    this.mTitleText = paramInt2;
    this.mConfirmBtnText = paramString1;
    this.mCancelBtnText = paramString2;
    this.mClickListenerInterface = paramCLickListenerInterface;
    this.mBkBtnLeft = paramInt3;
    this.mBkBtnRight = paramInt4;
  }

  private void initView()
  {
    View localView = LayoutInflater.from(this.mContext).inflate(2130903051, null);
    setContentView(localView);
    TextView localTextView = (TextView)localView.findViewById(2131230816);
    Button localButton1 = (Button)localView.findViewById(2131230817);
    Button localButton2 = (Button)localView.findViewById(2131230818);
    localTextView.setText(this.mTitleText);
    localButton1.setText(this.mCancelBtnText);
    localButton2.setText(this.mConfirmBtnText);
    localButton1.setBackgroundResource(this.mBkBtnLeft);
    localButton2.setBackgroundResource(this.mBkBtnRight);
    localButton1.setOnClickListener(new ClickListener());
    localButton2.setOnClickListener(new ClickListener());
  }

  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    initView();
  }

  public static abstract interface CLickListenerInterface
  {
    public abstract void doBtnLeft();

    public abstract void doBtnRight();
  }

  private class ClickListener
    implements View.OnClickListener
  {
    private ClickListener()
    {
    }

    public void onClick(View paramView)
    {
      switch (paramView.getId())
      {
      default:
        return;
      case 2131230818:
        CenterDialog.this.mClickListenerInterface.doBtnRight();
        return;
      case 2131230817:
      }
      CenterDialog.this.mClickListenerInterface.doBtnLeft();
    }
  }
}

/* Location:           C:\Users\Administrator\Desktop\test111\classes_dex2jar.jar
 * Qualified Name:     com.chinatel.robotclient.camera.CenterDialog
 * JD-Core Version:    0.6.2
 */