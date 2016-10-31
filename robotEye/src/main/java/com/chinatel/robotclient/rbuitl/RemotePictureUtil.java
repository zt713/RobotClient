package com.chinatel.robotclient.rbuitl;

import android.content.Context;
import cn.wangjianlog.baseframework.MainApplication;
import cn.wangjianlog.baseframework.tools.DateUtil;
import cn.wangjianlog.baseframework.tools.SDCardUtil;
import cn.wangjianlog.baseframework.view.ToastUtil;
import java.io.File;
import rtc.sdk.iface.Connection;

public class RemotePictureUtil
{
  public static void getRemotePicture(Context paramContext)
  {
    String str1 = SDCardUtil.getSDPath() + "/qly/picture/";
    File localFile1 = new File(str1);
    if (!localFile1.exists())
      localFile1.mkdirs();
    String str2 = DateUtil.getCurTime("yyyyMMddHHmmssS");
    String str3 = str1 + str2 + ".jpg";
    File localFile2 = new File(str3);
    try
    {
      if (!localFile2.exists())
        localFile2.createNewFile();
      label99: ToastUtil.showToast(paramContext, 1, "截图保存在/qly/picture/");
      MainApplication.getInstance().mCall.takeRemotePicture(str3);
      return;
    }
    catch (Exception localException)
    {
    }
  }
}

/* Location:           C:\Users\Administrator\Desktop\test111\classes_dex2jar.jar
 * Qualified Name:     com.chinatel.robotclient.rbuitl.RemotePictureUtil
 * JD-Core Version:    0.6.2
 */