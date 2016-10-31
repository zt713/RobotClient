package cn.wangjianlog.baseframework.view;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.text.TextUtils;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

public class SFProgrssDialog extends Dialog
{
  private static SFProgrssDialog m_progrssDialog;

  private SFProgrssDialog(Context paramContext, int paramInt)
  {
    super(paramContext, paramInt);
  }

  public static SFProgrssDialog createProgrssDialog(Context paramContext)
  {
    m_progrssDialog = new SFProgrssDialog(paramContext, 2131361793);
    m_progrssDialog.setContentView(2130903058);
    m_progrssDialog.getWindow().getAttributes().gravity = 17;
    return m_progrssDialog;
  }

  public void onWindowFocusChanged(boolean paramBoolean)
  {
    if (m_progrssDialog == null)
      return;
    ((AnimationDrawable)((ImageView)m_progrssDialog.findViewById(2131230835)).getBackground()).start();
  }

  public SFProgrssDialog setMessage(String paramString)
  {
    TextView localTextView = (TextView)m_progrssDialog.findViewById(2131230836);
    if (!TextUtils.isEmpty(paramString))
      localTextView.setText(paramString);
    while (true)
    {
      localTextView.setText("");
      return m_progrssDialog;
    }
  }
}

/* Location:           C:\Users\Administrator\Desktop\test111\classes_dex2jar.jar
 * Qualified Name:     cn.wangjianlog.baseframework.view.SFProgrssDialog
 * JD-Core Version:    0.6.2
 */