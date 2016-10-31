package com.chinatel.robot.Activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.chinatel.robot.Global;
import com.chinatel.robot.R;
import com.chinatel.robot.Util.NetWorkUtil;
import com.chinatel.robot.Util.SoundPlayer;
import com.chinatel.robot.Util.WifiApAdmin;
import com.chinatel.robot.application.MyApplication;
import com.chinatel.robot.camera.CenterDialog;
import com.chinatel.robot.camera.CenterDialog.CLickListenerInterface;
import com.chinatel.robot.fragment.CallRecordingFragment;
import com.chinatel.robot.fragment.FamilymembersFragment;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.util.EntityUtils;

public class EyesActivity extends Activity implements View.OnClickListener {
	private CallRecordingFragment callRecordingFragment;
	private Button calllog_bt;
	private TextView curTxt;
	private AlertDialog dialog;
	private FamilymembersFragment familymembersFragment;
	private FragmentManager fragmentManager;
	private FragmentTransaction ft;
	private ImageView img_trans_bar;
	private CenterDialog mCenterDialog;
	private Dialog mDialog;
	private DownloadTask mDownLoadTask;
	private long mExitTime;
	private ProgressBar mProgress;
	private volatile boolean mRunning = true;
	private TextView mTv;
	private Button member_bt;
	private String result = null;

	private boolean checkNet() {
		boolean bool = NetWorkUtil.isNetworkConnected(this);
		return (new WifiApAdmin(getApplicationContext()).isWifiApEnabled())
				|| (bool);
	}

	@SuppressLint("NewApi")
	private void checkVersion() {
		new AsyncTask<Void, Void, String>() {
			protected String doInBackground(Void[] paramAnonymousArrayOfVoid) {
				Object localObject = "";
				HttpGet localHttpGet;
				BasicHttpParams localBasicHttpParams;
				try {
					String str = Global.APP_DOWNLOAD_URL_PARENT
							+ "getVersionCode?app=com.chinatel.robot&version="
							+ EyesActivity.this.getPackageManager()
									.getPackageInfo("com.chinatel.robot", 0).versionCode;
					localObject = str;
					Log.e("check", (String) localObject);
					localHttpGet = new HttpGet((String) localObject);
					localBasicHttpParams = new BasicHttpParams();
					HttpConnectionParams.setConnectionTimeout(
							localBasicHttpParams, 200);
					HttpConnectionParams.setSoTimeout(localBasicHttpParams,
							2000);
					HttpResponse localHttpResponse = new DefaultHttpClient(
							localBasicHttpParams).execute(localHttpGet);
					if (localHttpResponse.getStatusLine().getStatusCode() == 200) {
						EyesActivity.this.result = EntityUtils
								.toString(localHttpResponse.getEntity());
						Log.e("result", EyesActivity.this.result);
					}
				} catch (PackageManager.NameNotFoundException localNameNotFoundException) {

				} catch (ClientProtocolException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return EyesActivity.this.result;
			}

			protected void onPostExecute(String paramAnonymousString) {
				Log.e("result", "onPostExecute result=" + paramAnonymousString);
				if (paramAnonymousString != null)
					paramAnonymousString = paramAnonymousString.trim();
				if ((paramAnonymousString == null)
						|| (paramAnonymousString.isEmpty())
						|| (paramAnonymousString.equals("0"))) {
					Log.e("result", "onPostExecute22 result="
							+ paramAnonymousString);
					return;
				}
				EyesActivity.this.showUpdateDialog();
			}
		}.execute(new Void[0]);
	}

	private void exit() {
		if (System.currentTimeMillis() - this.mExitTime > 2000L) {
			Toast.makeText(this, "再按一次退出千里眼", 0).show();
			this.mExitTime = System.currentTimeMillis();
			return;
		}
		finish();
	}

	private void hideFragments(FragmentTransaction paramFragmentTransaction) {
		if (this.familymembersFragment != null)
			paramFragmentTransaction.hide(this.familymembersFragment);
		if (this.callRecordingFragment != null)
			paramFragmentTransaction.hide(this.callRecordingFragment);
	}

	private void imgTrans(TextView paramTextView) {
		int i = this.curTxt.getLeft() + this.curTxt.getWidth() / 2
				- this.img_trans_bar.getWidth() / 2;
		int j = paramTextView.getLeft() + paramTextView.getWidth() / 2
				- this.img_trans_bar.getWidth() / 2;
		TranslateAnimation localTranslateAnimation = new TranslateAnimation(i,
				j, 0.0F, 0.0F);
		localTranslateAnimation.setDuration(300L);
		localTranslateAnimation.setFillAfter(true);
		this.img_trans_bar.startAnimation(localTranslateAnimation);
		this.curTxt = paramTextView;
	}

	private void install(File paramFile, Context paramContext) {
		Intent localIntent = new Intent("android.intent.action.VIEW");
		localIntent.setFlags(268435456);
		localIntent.setAction("android.intent.action.VIEW");
		localIntent.setDataAndType(Uri.fromFile(paramFile),
				"application/vnd.android.package-archive");
		paramContext.startActivity(localIntent);
	}

	private void setTabSelection(int paramInt) {
		this.ft = this.fragmentManager.beginTransaction();
		hideFragments(this.ft);
		switch (paramInt) {
		case R.id.member_bt:
			imgTrans(this.member_bt);
			setTxtColor(this.member_bt);
			if (this.familymembersFragment == null) {
				this.familymembersFragment = new FamilymembersFragment();
				this.ft.add(R.id.content, this.familymembersFragment);
			} else {
				this.ft.show(this.familymembersFragment);
			}
			break;
		case R.id.calllog_bt:
			imgTrans(this.calllog_bt);
			setTxtColor(this.calllog_bt);
			if (this.callRecordingFragment == null) {
				this.callRecordingFragment = new CallRecordingFragment();
				this.ft.add(R.id.content, this.callRecordingFragment);
			} else {
				this.ft.show(this.callRecordingFragment);
			}
			break;
		default:
			break;
		}
		this.ft.commit();
	}

	private void setTxtColor(TextView paramTextView) {
		switch (paramTextView.getId()) {
		default:
			return;
		case R.id.member_bt:
			this.member_bt.setTextColor(-1);
			this.calllog_bt.setTextColor(-1929379841);
			return;
		case R.id.calllog_bt:
		}
		this.calllog_bt.setTextColor(-1);
		this.member_bt.setTextColor(-1929379841);
	}

	private void showDownLoadDialog() {
		this.mDialog = new Dialog(this, 2131230730);
		View localView = LayoutInflater.from(this).inflate(
				R.layout.dialog_download, null);
		this.mProgress = ((ProgressBar) localView
				.findViewById(R.id.progress_download));
		this.mTv = ((TextView) localView.findViewById(R.id.tv_download));
		this.mDialog.setContentView(localView);
		this.mDialog.setCancelable(false);
		this.mDialog.show();
		this.mDownLoadTask = new DownloadTask();
		Log.e("DownloadTask", "开始下载 " + Global.APP_DOWNLOAD_URL_PARENT
				+ this.result);
		DownloadTask localDownloadTask = this.mDownLoadTask;
		String[] arrayOfString = new String[1];
		arrayOfString[0] = (Global.APP_DOWNLOAD_URL_PARENT + this.result);
		localDownloadTask.execute(arrayOfString);
	}

	private void showNetDialog() {
		SoundPlayer.getInstance(this, "qly001").play();
		AlertDialog.Builder localBuilder = new AlertDialog.Builder(this);
		View localView = View.inflate(this, R.layout.dialog_settingwifi, null);
		Button localButton1 = (Button) localView
				.findViewById(R.id.bt_dialog_settingwifi);
		Button localButton2 = (Button) localView
				.findViewById(R.id.bt_dialog_cancel);
		localButton1.setOnClickListener(this);
		localButton2.setOnClickListener(this);
		this.dialog = localBuilder.create();
		this.dialog.setView(localView, 0, 0, 0, 0);
		this.dialog.show();
	}

	private void showProgress(int paramInt) {
		this.mProgress.setProgress(paramInt);
		this.mTv.setText("已下载：" + paramInt + "%");
	}

	private void showUpdateDialog() {
		if (this.mCenterDialog == null) {
			this.mCenterDialog = new CenterDialog(this, 2131230730, 2131165198,
					"更新", "放弃", 2130837563, 2130837572,
					new CenterDialog.CLickListenerInterface() {
						public void doBtnLeft() {
							EyesActivity.this.mCenterDialog.dismiss();
						}

						public void doBtnRight() {
							EyesActivity.this.mCenterDialog.dismiss();
							EyesActivity.this.showDownLoadDialog();
						}
					});
			this.mCenterDialog.setCancelable(false);
		}
		this.mCenterDialog.show();
	}

	public void onClick(View paramView) {
		switch (paramView.getId()) {
		default:
			return;
		case R.id.member_bt:
			setTabSelection(R.id.member_bt);
			return;
		case R.id.calllog_bt:
			setTabSelection(R.id.calllog_bt);
			return;
		case R.id.bt_dialog_settingwifi:
			startActivity(new Intent("android.settings.WIRELESS_SETTINGS"));
			this.dialog.dismiss();
			return;
		case R.id.bt_dialog_cancel:
			this.dialog.cancel();
		}
	}

	public void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);
		if (MyApplication.getInstance().mClt == null) {
			Toast.makeText(this, "尚未初始化！\n开始初始化...", 0).show();
			MyApplication.getInstance().openWifiAp();
			finish();
			return;
		}
		setContentView(R.layout.activity_eyes);
		this.fragmentManager = getFragmentManager();
		this.member_bt = ((Button) findViewById(R.id.member_bt));
		this.calllog_bt = ((Button) findViewById(R.id.calllog_bt));
		this.img_trans_bar = ((ImageView) findViewById(R.id.img_trans_bar));
		this.member_bt.setOnClickListener(this);
		this.calllog_bt.setOnClickListener(this);
		this.curTxt = this.member_bt;
		setTabSelection(R.id.member_bt);
		if (!checkNet()) {
			showNetDialog();
			return;
		}
		/**
		 * 升级版本，改功能需要应用服务器的支持 ，暂不需要。
		 */
		// checkVersion();
	}

	public boolean onKeyDown(int paramInt, KeyEvent paramKeyEvent) {
		if (paramInt == 4) {
			exit();
			return true;
		}
		return super.onKeyDown(paramInt, paramKeyEvent);
	}

	protected void onPause() {
		SoundPlayer.getInstance(this, null).release();
		super.onPause();
	}

	private class DownloadTask extends AsyncTask<String, Integer, Boolean> {
		private File dirfile;
		private File file;
		private int progress;
		private URL sURL;

		public DownloadTask() {
		}

		protected Boolean doInBackground(String[] paramArrayOfString) {
			try {
				this.sURL = new URL(paramArrayOfString[0]);
				HttpURLConnection localHttpURLConnection = (HttpURLConnection) this.sURL
						.openConnection();
				localHttpURLConnection.setDoInput(true);
				localHttpURLConnection.setRequestMethod("GET");
				localHttpURLConnection.setRequestProperty("Content-Type",
						"application/x-www-form-urlencoded;charset=utf-8");
				localHttpURLConnection.connect();
				long l1 = localHttpURLConnection.getContentLength();
				Log.e("DownloadTask", "length:" + l1);
				Log.e("DownloadTask", "length:" + l1 + ",getResponseCode:"
						+ localHttpURLConnection.getResponseCode());
				if (localHttpURLConnection.getResponseCode() == 200) {
					this.dirfile = new File(
							Environment.getExternalStorageDirectory()
									+ "/AI/MyApp");
					if (!this.dirfile.exists())
						this.dirfile.mkdirs();
					this.file = new File(this.dirfile + "/" + "Robot.apk");
					if (!this.file.exists())
						this.file.createNewFile();
					Log.e("DownloadTask",
							"dirfile:" + this.dirfile.getAbsolutePath());
					Log.e("DownloadTask", "file:" + this.file.getAbsolutePath());
					BufferedInputStream localBufferedInputStream = new BufferedInputStream(
							localHttpURLConnection.getInputStream());
					BufferedOutputStream localBufferedOutputStream = new BufferedOutputStream(
							new FileOutputStream(this.file));
					byte[] arrayOfByte = new byte[1024];
					long l2 = 0L;
					while (true) {
						int i = localBufferedInputStream.read(arrayOfByte);
						if ((i <= 0) || (!EyesActivity.this.mRunning)) {
							localBufferedInputStream.close();
							localBufferedOutputStream.close();
							return Boolean.valueOf(true);
						}
						Log.e("DownloadTask", " len:" + i);
						localBufferedOutputStream.write(arrayOfByte, 0, i);
						l2 += i;
						int j = (int) (100.0D * (l2 / l1));
						Log.e("DownloadTask", "百分比:" + j + "<-->" + "总长度：" + l1
								+ "<--->" + "已下载：" + l2);
						Integer[] arrayOfInteger = new Integer[1];
						arrayOfInteger[0] = Integer.valueOf(j);
						publishProgress(arrayOfInteger);
						Boolean localBoolean = Boolean.valueOf(false);
						return localBoolean;
					}
				}
			} catch (MalformedURLException localMalformedURLException) {
				localMalformedURLException.printStackTrace();
				return Boolean.valueOf(false);
			} catch (IOException localIOException) {
				localIOException.printStackTrace();
			}
			return Boolean.valueOf(false);
		}

		protected void onPostExecute(Boolean paramBoolean) {
			if (paramBoolean.booleanValue())
				EyesActivity.this.install(this.file,
						EyesActivity.this.getApplicationContext());
			while (true) {
				EyesActivity.this.mDialog.dismiss();
				Toast.makeText(EyesActivity.this.getApplicationContext(),
						"访问服务器失败", 1000).show();
				return;
			}
		}

		protected void onProgressUpdate(Integer[] paramArrayOfInteger) {
			EyesActivity.this.showProgress(paramArrayOfInteger[0].intValue());
		}
	}
}

/*
 * Location:
 * C:\Users\Administrator\Desktop\小优\U03S源码\机器人本体.apk\classes_dex2jar.jar
 * Qualified Name: com.chinatel.robot.Activity.EyesActivity JD-Core Version:
 * 0.6.2
 */