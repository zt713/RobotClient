package cn.wangjianlog.baseframework.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class ToastUtil
{
  public static void showToast(Context paramContext, int paramInt, String paramString)
  {
    Toast localToast = new Toast(paramContext);
    View localView = LayoutInflater.from(paramContext).inflate(2130903059, null);
    ((TextView)localView.findViewById(2131230837)).setText(paramString);
    localToast.setDuration(paramInt);
    localToast.setView(localView);
    localToast.setGravity(17, 0, 0);
    localToast.show();
  }
}

/* Location:           C:\Users\Administrator\Desktop\test111\classes_dex2jar.jar
 * Qualified Name:     cn.wangjianlog.baseframework.view.ToastUtil
 * JD-Core Version:    0.6.2
 */