package cn.wangjianlog.baseframework.util;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Build.VERSION;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MyWebViewClient extends WebViewClient
{
  private Activity mActivity;

  public MyWebViewClient(Activity paramActivity)
  {
    this.mActivity = paramActivity;
  }

  public void onPageFinished(WebView paramWebView, String paramString)
  {
    super.onPageFinished(paramWebView, paramString);
    if (!paramWebView.getSettings().getLoadsImagesAutomatically())
      paramWebView.getSettings().setLoadsImagesAutomatically(true);
  }

  public void onPageStarted(WebView paramWebView, String paramString, Bitmap paramBitmap)
  {
    super.onPageStarted(paramWebView, paramString, paramBitmap);
  }

  public void onReceivedError(WebView paramWebView, int paramInt, String paramString1, String paramString2)
  {
    super.onReceivedError(paramWebView, paramInt, paramString1, paramString2);
    paramWebView.loadUrl("file:///android_asset/networkerror/index.html");
    paramWebView.addJavascriptInterface(new NetworkErrorJsInterface(paramWebView, paramString2), "webviewInterface");
  }

  public boolean shouldOverrideUrlLoading(WebView paramWebView, String paramString)
  {
    if (paramString.startsWith("tel:"))
    {
      Intent localIntent1 = new Intent("android.intent.action.VIEW", Uri.parse(paramString));
      this.mActivity.startActivity(localIntent1);
    }
    while (true)
    {
      if (paramString.startsWith("mailto:"))
      {
        Intent localIntent2 = new Intent("android.intent.action.SENDTO", Uri.parse(paramString));
        this.mActivity.startActivity(localIntent2);
      }
      else
      {
        paramWebView.loadUrl(paramString);
      }
    }
  }

  class NetworkErrorJsInterface
  {
    String failingUrl;
    WebView view;

    public NetworkErrorJsInterface(WebView paramString, String arg3)
    {
      this.view = paramString;
      this.failingUrl = arg3;
    }

    @JavascriptInterface
    public void refresh()
    {
      this.view.loadUrl(this.failingUrl);
    }

    @JavascriptInterface
    public void setNetwork()
    {
      Intent localIntent;
      if (Build.VERSION.SDK_INT > 10){
        localIntent = new Intent("android.settings.WIRELESS_SETTINGS");
       }else{
        localIntent = new Intent();
        localIntent.setComponent(new ComponentName("com.android.settings", "com.android.settings.WirelessSettings"));
        localIntent.setAction("android.intent.action.VIEW");
    }
        MyWebViewClient.this.mActivity.startActivity(localIntent);
    }
  }
}

/* Location:           C:\Users\Administrator\Desktop\test111\classes_dex2jar.jar
 * Qualified Name:     cn.wangjianlog.baseframework.util.MyWebViewClient
 * JD-Core Version:    0.6.2
 */