package cn.wangjianlog.baseframework.util;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface.OnKeyListener;
import android.graphics.Bitmap;
import android.os.Message;
import android.text.Editable;
import android.util.Log;
import android.view.KeyEvent;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.EditText;

public class MyWebChromeClient extends WebChromeClient
{
  public void onCloseWindow(WebView paramWebView)
  {
    super.onCloseWindow(paramWebView);
  }

  public boolean onCreateWindow(WebView paramWebView, boolean paramBoolean1, boolean paramBoolean2, Message paramMessage)
  {
    return super.onCreateWindow(paramWebView, paramBoolean1, paramBoolean2, paramMessage);
  }

  public boolean onJsAlert(WebView paramWebView, String paramString1, String paramString2, JsResult paramJsResult)
  {
    AlertDialog.Builder localBuilder = new AlertDialog.Builder(paramWebView.getContext());
    localBuilder.setTitle("对话框").setMessage(paramString2).setPositiveButton("确定", null);
    localBuilder.setOnKeyListener(new DialogInterface.OnKeyListener()
    {
      public boolean onKey(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt, KeyEvent paramAnonymousKeyEvent)
      {
        Log.v("onJsAlert", "keyCode==" + paramAnonymousInt + "event=" + paramAnonymousKeyEvent);
        return true;
      }
    });
    localBuilder.setCancelable(false);
    localBuilder.create().show();
    paramJsResult.confirm();
    return true;
  }

  public boolean onJsBeforeUnload(WebView paramWebView, String paramString1, String paramString2, JsResult paramJsResult)
  {
    return super.onJsBeforeUnload(paramWebView, paramString1, paramString2, paramJsResult);
  }

  public boolean onJsConfirm(WebView paramWebView, String paramString1, String paramString2, final JsResult paramJsResult)
  {
    AlertDialog.Builder localBuilder = new AlertDialog.Builder(paramWebView.getContext());
    localBuilder.setTitle("对话框").setMessage(paramString2).setPositiveButton("确定", new DialogInterface.OnClickListener()
    {
      public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
      {
        paramJsResult.confirm();
      }
    }).setNeutralButton("取消", new DialogInterface.OnClickListener()
    {
      public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
      {
        paramJsResult.cancel();
      }
    });
    localBuilder.setOnCancelListener(new DialogInterface.OnCancelListener()
    {
      public void onCancel(DialogInterface paramAnonymousDialogInterface)
      {
        paramJsResult.cancel();
      }
    });
    localBuilder.setOnKeyListener(new DialogInterface.OnKeyListener()
    {
      public boolean onKey(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt, KeyEvent paramAnonymousKeyEvent)
      {
        Log.v("onJsConfirm", "keyCode==" + paramAnonymousInt + "event=" + paramAnonymousKeyEvent);
        return true;
      }
    });
    localBuilder.create().show();
    return true;
  }

  public boolean onJsPrompt(WebView paramWebView, String paramString1, String paramString2, String paramString3, final JsPromptResult paramJsPromptResult)
  {
    AlertDialog.Builder localBuilder = new AlertDialog.Builder(paramWebView.getContext());
    localBuilder.setTitle("对话框").setMessage(paramString2);
    final EditText localEditText = new EditText(paramWebView.getContext());
    localEditText.setSingleLine();
    localEditText.setText(paramString3);
    localBuilder.setView(localEditText).setPositiveButton("确定", new DialogInterface.OnClickListener()
    {
      public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
      {
        paramJsPromptResult.confirm(localEditText.getText().toString());
      }
    }).setNeutralButton("取消", new DialogInterface.OnClickListener()
    {
      public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
      {
        paramJsPromptResult.cancel();
      }
    });
    localBuilder.setOnKeyListener(new DialogInterface.OnKeyListener()
    {
      public boolean onKey(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt, KeyEvent paramAnonymousKeyEvent)
      {
        Log.v("onJsPrompt", "keyCode==" + paramAnonymousInt + "event=" + paramAnonymousKeyEvent);
        return true;
      }
    });
    localBuilder.create().show();
    return true;
  }

  public void onProgressChanged(WebView paramWebView, int paramInt)
  {
    super.onProgressChanged(paramWebView, paramInt);
  }

  public void onReceivedIcon(WebView paramWebView, Bitmap paramBitmap)
  {
    super.onReceivedIcon(paramWebView, paramBitmap);
  }

  public void onReceivedTitle(WebView paramWebView, String paramString)
  {
    super.onReceivedTitle(paramWebView, paramString);
  }

  public void onRequestFocus(WebView paramWebView)
  {
    super.onRequestFocus(paramWebView);
  }
}

/* Location:           C:\Users\Administrator\Desktop\test111\classes_dex2jar.jar
 * Qualified Name:     cn.wangjianlog.baseframework.util.MyWebChromeClient
 * JD-Core Version:    0.6.2
 */