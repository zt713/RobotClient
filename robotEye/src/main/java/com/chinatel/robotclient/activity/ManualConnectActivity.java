package com.chinatel.robotclient.activity;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.AsyncTask.Status;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import cn.wangjianlog.baseframework.MainApplication;
import com.chinatel.robotclient.Global;
import com.chinatel.robotclient.R;
import com.chinatel.robotclient.camera.CenterDialog;
import com.chinatel.robotclient.camera.CenterDialog.CLickListenerInterface;
import com.chinatel.robotclient.camera.UnarrowedDialog;
import com.chinatel.robotclient.camera.UnarrowedDialog.CLickConfirmListenerInterface;
import com.chinatel.robotclient.rbuitl.PreferencesUtil;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.util.EntityUtils;

public class ManualConnectActivity extends Activity
  implements TextWatcher
{
  private AsyncTask<Void, Void, String> asyncTask;
  int count = 0;
  private ProgressDialog dialog;
  private InputMethodManager imm;
  private Button mBtnChoice;
  private Button mBtnConnect2;
  private CenterDialog mConnectDialog;
  private EditText mEditName;
  private EditText mEditPassword;
  private boolean mFlag = false;
  Handler mHandler = new Handler()
  {
    public void handleMessage(Message paramAnonymousMessage)
    {
      switch (paramAnonymousMessage.what)
      {

      case 1:
        ManualConnectActivity.this.mcount = 0;
        break;
      case 2:
        if (ManualConnectActivity.this.asyncTask != null)
          ManualConnectActivity.this.asyncTask = null;
        String str = Global.APP_DOWNLOAD_URL_PARENT2 + "checkpassword?name=" + "U03s" + ManualConnectActivity.this.userName + "&password=" + ManualConnectActivity.this.userPassword;
        ManualConnectActivity.this.asyncTask = new LoginAsyncTask(str);
        ManualConnectActivity.this.asyncTask.execute(new Void[0]);
        break;
        default:
          break;
      }
    }
  };
  private BroadcastReceiver mHomeKeyEventReceiver = new BroadcastReceiver()
  {
    String SYSTEM_HOME_KEY = "homekey";
    String SYSTEM_HOME_KEY_LONG = "recentapps";
    String SYSTEM_REASON = "reason";

    public void onReceive(Context paramAnonymousContext, Intent paramAnonymousIntent)
    {
      if (paramAnonymousIntent.getAction().equals("android.intent.action.CLOSE_SYSTEM_DIALOGS"))
        ManualConnectActivity.this.finish();
    }
  };
  private RelativeLayout mLayout;
  private LayoutInflater mLayoutInflater;
  private List<String> mList;
  private ListAdapter mListAdapter;
  private ListView mListView;
  private PopupWindow mPopWindow;
  private final int mPopWindowHeight = 122;
  private View mPopWindowView;
  private final int mPopWindowWidth = 340;
  private UnarrowedDialog mUnarrowedDialog;
  private int mcount;
  int offsetX = (int)(63.0F * MainApplication.getDensity());
  int offsetY = (int)(-6.0F * MainApplication.getDensity());
  private String userName;
  private String userPassword;

  private void createConnectDialog()
  {
    if (this.mConnectDialog == null)
      this.mConnectDialog = new CenterDialog(this, 2131361804, 2131296272, "", "", 2130837521, 2130837551, new CLickListenerInterface()
      {
        public void doBtnLeft()
        {
          ManualConnectActivity.this.mConnectDialog.dismiss();
        }

        public void doBtnRight()
        {
          ManualConnectActivity.this.mConnectDialog.dismiss();
          ManualConnectActivity.this.startActivity(new Intent("android.settings.SETTINGS"));
        }
      });
  }

  private void createUnarrowedDialog()
  {
    if (this.mUnarrowedDialog == null)
      this.mUnarrowedDialog = new UnarrowedDialog(this, 2131361804, 2131296273, new CLickConfirmListenerInterface()
      {
        public void doBtn()
        {
          ManualConnectActivity.this.mUnarrowedDialog.dismiss();
        }
      });
    this.mUnarrowedDialog.show();
  }

  private List<String> getData()
  {
    ArrayList localArrayList = new ArrayList();
    SharedPreferences localSharedPreferences = getSharedPreferences("username", 0);
    localSharedPreferences.edit();
    String str = localSharedPreferences.getString("spinner", "");
    String[] arrayOfString = new String[0];
    if ((str != null) && (!str.isEmpty()) && (!str.equals("")))
    {
      Log.e("ManualConnectActivity", "names =  " + str);
      if (str.contains(","))
        arrayOfString = str.split(",");
    }
    else
    {
      for (int i = -1 + arrayOfString.length; ; i--)
      {
        if (i < 0)
          return localArrayList;
        localArrayList.add(arrayOfString[i]);
      }
    }
    localArrayList.add(str);
    return localArrayList;
  }

  private void initView()
  {
    this.mLayoutInflater = ((LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE));
    this.mPopWindowView = this.mLayoutInflater.inflate(R.layout.popwindow, null);
    this.mListView = ((ListView)this.mPopWindowView.findViewById(R.id.listview_popwindow));
    this.mList = getData();
    this.mListAdapter = new ListAdapter();
    this.mListView.setAdapter(this.mListAdapter);
    this.mListView.setOnItemClickListener(new OnItemClickListener()
    {
      public void onItemClick(AdapterView<?> paramAnonymousAdapterView, View paramAnonymousView, int paramAnonymousInt, long paramAnonymousLong)
      {
        ManualConnectActivity.this.mEditName.setText((CharSequence)ManualConnectActivity.this.mList.get(paramAnonymousInt));
        ManualConnectActivity.this.mEditName.setSelection(ManualConnectActivity.this.mEditName.getText().length());
        ManualConnectActivity.this.mEditPassword.setText("");
        ManualConnectActivity.this.mPopWindow.dismiss();
      }
    });
    this.mLayout.post(new Runnable()
    {
      public void run()
      {
        int i = ManualConnectActivity.this.mLayout.getWidth();
        int j = (int)(122.0F * MainApplication.getDensity());
        Log.e("conn", i + "<>" + j);
        ManualConnectActivity.this.mPopWindow = new PopupWindow(ManualConnectActivity.this.mPopWindowView, i, j);
        ManualConnectActivity.this.mPopWindow.getContentView().setOnTouchListener(new OnTouchListener()
        {
          public boolean onTouch(View paramAnonymous2View, MotionEvent paramAnonymous2MotionEvent)
          {
            ManualConnectActivity.this.mPopWindow.setFocusable(false);
            ManualConnectActivity.this.mPopWindow.dismiss();
            return true;
          }
        });
        ManualConnectActivity.this.mListView.setOnKeyListener(new OnKeyListener()
        {
          public boolean onKey(View paramAnonymous2View, int paramAnonymous2Int, KeyEvent paramAnonymous2KeyEvent)
          {
            ManualConnectActivity.this.mPopWindow.dismiss();
            return true;
          }
        });
      }
    });
  }

  public static boolean isNetworkConnected(Context paramContext)
  {
    NetworkInfo localNetworkInfo = ((ConnectivityManager)paramContext.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
    return (localNetworkInfo != null) && (localNetworkInfo.isConnectedOrConnecting());
  }

  private void saveData(String paramString)
  {
    SharedPreferences localSharedPreferences = getSharedPreferences("username", 0);
    Editor localEditor = localSharedPreferences.edit();
    String str1 = localSharedPreferences.getString("spinner", "");
    if ((str1 != null) && (!str1.isEmpty()) && (!str1.equals("")))
    {
      Log.e("ManualConnectActivity", "saveData names =  " + str1);
      String[] arrayOfString;
      if (!str1.contains(paramString))
      {
        arrayOfString = str1.split(",");
        if (arrayOfString.length < 5)
        {
          String str3 = str1 + "," + paramString;
          localEditor.putString("spinner", str3);
          localEditor.commit();
          Log.e("ManualConnectActivity", "saveData111 names =  " + str3);
        }
      }
      else
      {
        return;
      }
      String str2 = "";
      for (int i = 1; ; i++)
      {
        if (i >= arrayOfString.length)
        {
          localEditor.putString("spinner", str2 + paramString);
          localEditor.commit();
          Log.e("ManualConnectActivity", "saveData222 names =  " + str1);
          return;
        }
        str2 = str2 + arrayOfString[i] + ",";
      }
    }
    localEditor.putString("spinner", paramString);
    localEditor.commit();
    Log.e("ManualConnectActivity", "saveData333  ");
  }

  public void afterTextChanged(Editable paramEditable)
  {
  }

  public void beforeTextChanged(CharSequence paramCharSequence, int paramInt1, int paramInt2, int paramInt3)
  {
  }

  public void cance()
  {
    if ((this.dialog != null) && (this.dialog.isShowing()))
    {
      this.dialog.dismiss();
      this.dialog = null;
    }
  }

  public void login()
  {
    this.count = 0;
    Log.e("login", "userName=" + this.userName + "::: userPassword=" + this.userPassword);
    if (!isNetworkConnected(getApplicationContext()))
    {
      Toast.makeText(this, "网络不可用，不能登录连接千里眼", 0).show();
      PreferencesUtil.removeKey(this, "auto");
      cance();
      return;
    }
    if (this.userName == null)
    {
      Toast.makeText(this, "机器人ID不能为空", 0).show();
      PreferencesUtil.removeKey(this, "auto");
      cance();
      return;
    }
    if (this.userPassword.contains(" "))
    {
      Toast.makeText(this, "密码不能包含空格符", 0).show();
      PreferencesUtil.removeKey(this, "auto");
      cance();
      return;
    }
    if (this.userPassword == null)
    {
      Toast.makeText(this, "密码不能为空", 0).show();
      PreferencesUtil.removeKey(this, "auto");
      cance();
      return;
    }
    if ((this.userPassword.length() > 16) || (this.userPassword.length() < 8))
    {
      Toast.makeText(this, "密码格式不对，正确为：8-16个字符，区分大小写", 0).show();
      PreferencesUtil.removeKey(this, "auto");
      cance();
      return;
    }
    if (!this.mBtnChoice.isSelected())
    {
      Toast.makeText(this, "请选择已阅读并同意《使用条款和隐私政策》", 0).show();
      PreferencesUtil.removeKey(this, "auto");
      cance();
      return;
    }
    this.asyncTask = new LoginAsyncTask(Global.APP_DOWNLOAD_URL_PARENT + "checkpassword?name=" + "U03s" + this.userName + "&password=" + this.userPassword);
    this.asyncTask.execute(new Void[0]);
  }

  public void onBackPressed()
  {
    if (this.mcount == 1)
    {
      super.onBackPressed();
      return;
    }
    Toast.makeText(this, "再按一次关闭千里眼", 0).show();
    this.mcount = 1;
    this.mHandler.sendEmptyMessageDelayed(1, 2000L);
  }

  public void onClickChoice(View paramView)
  {
    if (!this.mBtnChoice.isSelected())
    {
      this.mBtnChoice.setSelected(true);
      return;
    }
    this.mBtnChoice.setSelected(false);
  }

  public void onClickConnect1(View paramView)
  {
    startActivity(new Intent(this, CaptureActivity.class));
    finish();
  }

  public void onClickConnect2(View paramView)
  {
    this.dialog = new ProgressDialog(this);
    this.dialog.setMessage("正在加载数据，请稍候......");
    this.dialog.setCancelable(false);
    this.dialog.show();
    this.userName = this.mEditName.getText().toString();
    this.userPassword = this.mEditPassword.getText().toString();
//    login();
    login1();
  }

  private void login1(){
    ManualConnectActivity.this.saveData(ManualConnectActivity.this.userName);
    Intent localIntent = new Intent(ManualConnectActivity.this, ConnectRobotActivity.class);
    localIntent.putExtra("key", ManualConnectActivity.this.userName);
    ManualConnectActivity.this.startActivity(localIntent);
    PreferencesUtil.putString(ManualConnectActivity.this, "key", ManualConnectActivity.this.userName);
    PreferencesUtil.putString(ManualConnectActivity.this, "userPassword", ManualConnectActivity.this.userPassword);
    PreferencesUtil.putString(ManualConnectActivity.this, "auto", "auto");
    ManualConnectActivity.this.cance();
    ManualConnectActivity.this.finish();


  }

  public void onClickDelete(View paramView)
  {
    this.mEditName.setText("");
  }

  public void onClickForgetPassword(View paramView)
  {
  }

  public void onClickSpinner(View paramView)
  {
    this.mEditName.setFocusable(true);
    this.mEditName.setFocusableInTouchMode(true);
    this.mEditName.requestFocus();
    if (!this.mPopWindow.isShowing())
    {
      this.mPopWindow.setFocusable(true);
      this.mPopWindow.setOutsideTouchable(true);
      this.mPopWindow.update();
      this.mPopWindow.showAsDropDown(this.mLayout, 0, this.offsetY);
    }
  }

  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setRequestedOrientation(1);
    setContentView(R.layout.activity_connect_manual);
    this.mEditName = ((EditText)findViewById(R.id.edit_name));
    this.mEditPassword = ((EditText)findViewById(R.id.edit_password));
    this.mBtnConnect2 = ((Button)findViewById(R.id.btn_connect2));
    this.mLayout = ((RelativeLayout)findViewById(R.id.layout_input1));
    this.mEditName.addTextChangedListener(this);
    this.mEditPassword.addTextChangedListener(this);
    this.mBtnChoice = ((Button)findViewById(R.id.btn_choice));
    this.mBtnChoice.setSelected(true);
    initView();
    String str1 = getIntent().getStringExtra("erwei_result");
    if ((str1 != null) && (!str1.isEmpty()) && (!str1.equals("")))
    {
      this.mEditName.setText(str1.trim());
      this.mEditName.setSelection(str1.length());
      this.mEditPassword.setText("");
      this.mFlag = true;
    }
   else
    {
      registerReceiver(this.mHomeKeyEventReceiver, new IntentFilter("android.intent.action.CLOSE_SYSTEM_DIALOGS"));
      String str2 = PreferencesUtil.getString(this, "key");
      if ((str2 != null) && (str2.length() > 6))
      {
        this.mEditName.setText(str2.trim());
        this.mEditName.setSelection(str2.length());
        this.mFlag = true;
      }
      String str3 = PreferencesUtil.getString(this, "userPassword");
      if ((str3 != null) && (str3.length() > 6))
        this.mEditPassword.setText(str3);
      else
        this.mEditPassword.setText("");
    }
  }

  @SuppressLint("NewApi")
  protected void onDestroy()
  {
    unregisterReceiver(this.mHomeKeyEventReceiver);
    super.onDestroy();
    cance();
    if ((this.asyncTask != null) && (this.asyncTask.getStatus() != Status.FINISHED))
      this.asyncTask.cancel(true);
  }

  protected void onPause()
  {
    super.onPause();
  }

  protected void onResume()
  {
    super.onResume();
    Log.e("myrobot", this.mEditName.getText().toString());
    if (this.mEditName.getText().length() == 0)
      this.mBtnConnect2.setEnabled(false);
    else
    {
      if (!MainApplication.netConnectState())
      {
        createConnectDialog();
        if (this.mConnectDialog != null)
          this.mConnectDialog.show();
      }
      if (MainApplication.getInstance().isAvailable)
      {
        String str = PreferencesUtil.getString(this, "auto");
        if ((str != null) && (str.equals("auto")) && (this.mEditName.getText() != null) && (this.mEditName.getText().toString().length() > 0) && (this.mEditPassword.getText() != null) && (this.mEditPassword.getText().toString().length() > 6))
          onClickConnect2(null);
      }
      this.mBtnConnect2.setEnabled(true);
    }
  }

  public void onTextChanged(CharSequence paramCharSequence, int paramInt1, int paramInt2, int paramInt3)
  {
    Log.e("myrobot", "dioayong+" + paramInt3);
    if (this.mEditName.getText().length() > 0)
    {
      this.mBtnConnect2.setEnabled(true);
      return;
    }
    this.mBtnConnect2.setEnabled(false);
  }

  public void protocolshow(View paramView)
  {
    Log.e("myrobot", "go to ProtocolShowActivity");
    startActivity(new Intent(this, ProtocolShowActivity.class));
  }

  private class ListAdapter extends BaseAdapter
  {
    private ListAdapter()
    {
    }

    public int getCount()
    {
      return ManualConnectActivity.this.mList.size();
    }

    public Object getItem(int paramInt)
    {
      return Integer.valueOf(paramInt);
    }

    public long getItemId(int paramInt)
    {
      return paramInt;
    }

    public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
    {
      Log.e("position", "position=" + paramInt);
      ViewHolder localViewHolder;
      if (paramView == null)
      {
        localViewHolder = new ViewHolder();
        paramView = ManualConnectActivity.this.mLayoutInflater.inflate(2130903055, null);
        localViewHolder.tv_name = ((TextView)paramView.findViewById(2131230827));
        paramView.setTag(localViewHolder);
      }
     else
      {
        localViewHolder = (ViewHolder)paramView.getTag();
      }
      localViewHolder.tv_name.setText((CharSequence)ManualConnectActivity.this.mList.get(paramInt));
      return paramView;
    }

    private class ViewHolder
    {
      public TextView tv_name;

      private ViewHolder()
      {
      }
    }
  }

  @SuppressLint("NewApi")
  public class LoginAsyncTask extends AsyncTask<Void, Void, String>
  {
    private String strResult = null;
    String url = null;

    public LoginAsyncTask(String arg2)
    {
      this.url = arg2;
    }

    protected String doInBackground(Void[] paramArrayOfVoid)
    {
      HttpGet localHttpGet = new HttpGet(this.url);
      BasicHttpParams localBasicHttpParams = new BasicHttpParams();
      try
      {
        HttpConnectionParams.setConnectionTimeout(localBasicHttpParams, 3000);
        HttpConnectionParams.setSoTimeout(localBasicHttpParams, 5000);
        DefaultHttpClient localDefaultHttpClient = new DefaultHttpClient(localBasicHttpParams);
        if (isCancelled())
          return null;
        HttpResponse localHttpResponse = localDefaultHttpClient.execute(localHttpGet);
        if (localHttpResponse.getStatusLine().getStatusCode() == 200)
        {
          this.strResult = EntityUtils.toString(localHttpResponse.getEntity()).trim();
          Log.e("result", "result " + this.strResult.trim());
          return this.strResult;
        }
        return "请求出错";
      }
      catch (ClientProtocolException localClientProtocolException)
      {
        PreferencesUtil.removeKey(ManualConnectActivity.this, "auto");
        ManualConnectActivity.this.cance();
        localClientProtocolException.printStackTrace();
        return "请求出错1";
      }
      catch (IOException localIOException)
      {
        PreferencesUtil.removeKey(ManualConnectActivity.this, "auto");
        ManualConnectActivity.this.cance();
        localIOException.printStackTrace();
      }
      return "请求出错2";
    }

    protected void onPostExecute(String paramString)
    {
      Log.e("connect", "onPostExecute  result=" + paramString);
      if ((paramString != null) && (paramString.trim().equalsIgnoreCase("1")))
      {
        ManualConnectActivity.this.saveData(ManualConnectActivity.this.userName);
        Intent localIntent = new Intent(ManualConnectActivity.this, ConnectRobotActivity.class);
        localIntent.putExtra("key", ManualConnectActivity.this.userName);
        ManualConnectActivity.this.startActivity(localIntent);
        PreferencesUtil.putString(ManualConnectActivity.this, "key", ManualConnectActivity.this.userName);
        PreferencesUtil.putString(ManualConnectActivity.this, "userPassword", ManualConnectActivity.this.userPassword);
        PreferencesUtil.putString(ManualConnectActivity.this, "auto", "auto");
        ManualConnectActivity.this.cance();
        ManualConnectActivity.this.finish();
      }
      do
      {
        if ((paramString != null) && (paramString.trim().equalsIgnoreCase("0")))
        {
          Log.e("connect", "count=" + ManualConnectActivity.this.count + "  ;; result=" + paramString);
          if (ManualConnectActivity.this.count == 0)
          {
            ManualConnectActivity localManualConnectActivity = ManualConnectActivity.this;
            localManualConnectActivity.count = (1 + localManualConnectActivity.count);
            ManualConnectActivity.this.mHandler.sendEmptyMessageDelayed(2, 100L);
            return;
          }
          Toast.makeText(ManualConnectActivity.this, "用户名或密码不正确", 0).show();
          PreferencesUtil.removeKey(ManualConnectActivity.this, "auto");
          ManualConnectActivity.this.cance();
          return;
        }
      }
      while (paramString == null);
      Toast.makeText(ManualConnectActivity.this, "网络异常，请求服务器失败", 0).show();
      PreferencesUtil.removeKey(ManualConnectActivity.this, "auto");
      ManualConnectActivity.this.cance();
    }
  }
}

/* Location:           C:\Users\Administrator\Desktop\test111\classes_dex2jar.jar
 * Qualified Name:     com.chinatel.robotclient.activity.ManualConnectActivity
 * JD-Core Version:    0.6.2
 */