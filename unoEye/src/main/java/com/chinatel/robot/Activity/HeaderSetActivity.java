package com.chinatel.robot.Activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.chinatel.robot.R;
import com.chinatel.robot.Util.BMapUtil;
import com.chinatel.robot.Util.SoundPlayer;
import com.chinatel.robot.View.RoundImageView;
import com.chinatel.robot.View.SelectTypeDialog;
import com.chinatel.robot.application.MyApplication;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class HeaderSetActivity extends Activity {
	private static final int FLAG_CHOOSE_IMG = 1001;
	private static final int FLAG_CHOOSE_PHONE = 1002;
	private static final int FLAG_MODIFY_FINISH = 1003;
	private static String localTempImageFileName = "";
	private BMapUtil bmaputil;
	private byte[] bs;
	private String device;
	private String fileName;
	private RoundImageView headerImg;
	private String keycode;
	private File take_photo;

	private void copyFile(String paramString1, String paramString2) {
		int i = 0;
		try {
			if (new File(paramString1).exists()) {
				FileInputStream localFileInputStream = new FileInputStream(
						paramString1);
				FileOutputStream localFileOutputStream = new FileOutputStream(
						paramString2);
				byte[] arrayOfByte = new byte[1444];
				while (true) {
					int j = localFileInputStream.read(arrayOfByte);
					if (j == -1) {
						localFileInputStream.close();
						return;
					}
					i += j;
					System.out.println(i);
					localFileOutputStream.write(arrayOfByte, 0, j);
				}
			}
		} catch (Exception localException) {
			System.out.println("复制单个文件操作出错");
			localException.printStackTrace();
		}
	}

	private void setPicToView(Intent paramIntent) {
		if (paramIntent.getExtras() != null) {
			this.bs = paramIntent.getByteArrayExtra("bitmap");
			Bitmap localBitmap = BitmapFactory.decodeByteArray(this.bs, 0,
					this.bs.length);
			if (localBitmap != null)
				this.headerImg.setImageBitmap(localBitmap);
			new Thread(new Runnable() {
				public void run() {
					HeaderSetActivity.this.bmaputil = new BMapUtil();
					new File(HeaderSetActivity.this.bmaputil.getImageDir())
							.mkdirs();
					HeaderSetActivity.this.fileName = (HeaderSetActivity.this.bmaputil
							.getImageDir() + "/" + System.currentTimeMillis() + ".jpg");
					try {
						FileOutputStream localFileOutputStream = new FileOutputStream(
								HeaderSetActivity.this.fileName);
						localFileOutputStream.write(HeaderSetActivity.this.bs);
						localFileOutputStream.flush();
						localFileOutputStream.close();
						if ((HeaderSetActivity.this.take_photo != null)
								&& (HeaderSetActivity.this.take_photo.exists()))
							HeaderSetActivity.this.take_photo.delete();
						return;
					} catch (Exception localException) {
						localException.printStackTrace();
					}
				}
			}).start();
		}
	}

	protected void onActivityResult(int paramInt1, int paramInt2,
			Intent paramIntent) {
		if ((paramInt1 == 1001) && (paramInt2 == -1))
			if (paramIntent != null)
				startPhotoClip(paramIntent.getData());
		do {
			String str = null;
			do {
				if ((paramInt1 != 1002) || (paramInt2 != -1))
					break;
				str = paramIntent.getStringExtra("img");
			} while (str == null);
			this.take_photo = new File(str);
			startPhotoClip(Uri.fromFile(this.take_photo));
		} while ((paramInt1 != 1003) || (paramInt2 != -1)
				|| (paramIntent == null));
		findViewById(R.id.jumpBtn).setVisibility(View.GONE);
		findViewById(R.id.nextBtn).setVisibility(View.VISIBLE);
		setPicToView(paramIntent);
	}

	public void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);
		setContentView(R.layout.activity_header_set);
		this.headerImg = ((RoundImageView) findViewById(R.id.headerImg));
		this.headerImg.setType(1);
		MyApplication.getInstance().addActivity(this);
		SoundPlayer.getInstance(getApplicationContext(), "qly020.mp3").play();
		this.device = getIntent().getStringExtra("device");
		this.keycode = getIntent().getStringExtra("keycode");
		findViewById(R.id.jumpBtn).setOnClickListener(new View.OnClickListener() {
			public void onClick(View paramAnonymousView) {
				Intent localIntent = new Intent(HeaderSetActivity.this,
						RoleSetActivity.class);
				localIntent.putExtra("imguri", "");
				localIntent.putExtra("device", HeaderSetActivity.this.device);
				localIntent.putExtra("keycode", HeaderSetActivity.this.keycode);
				HeaderSetActivity.this.startActivity(localIntent);
			}
		});
		findViewById(R.id.nextBtn).setOnClickListener(new View.OnClickListener() {
			public void onClick(View paramAnonymousView) {
				Intent localIntent = new Intent(HeaderSetActivity.this,
						RoleSetActivity.class);
				localIntent.putExtra("imguri", HeaderSetActivity.this.fileName);
				localIntent.putExtra("device", HeaderSetActivity.this.device);
				localIntent.putExtra("keycode", HeaderSetActivity.this.keycode);
				HeaderSetActivity.this.startActivity(localIntent);
			}
		});
		this.headerImg.setOnClickListener(new View.OnClickListener() {
			public void onClick(View paramAnonymousView) {
				SelectTypeDialog.Builder localBuilder = new SelectTypeDialog.Builder(
						HeaderSetActivity.this);
				localBuilder.setTitle("请选择");
				/*
				 * localBuilder.setLocalPicture(new
				 * DialogInterface.OnClickListener() { public void
				 * onClick(DialogInterface paramAnonymous2DialogInterface, int
				 * paramAnonymous2Int) { Intent localIntent = new Intent();
				 * localIntent.setAction("android.intent.action.PICK");
				 * localIntent.setType("image/*");
				 * HeaderSetActivity.this.startActivityForResult(localIntent,
				 * 1001); paramAnonymous2DialogInterface.dismiss(); } });
				 * localBuilder.setTakePicture(new
				 * DialogInterface.OnClickListener() { public void
				 * onClick(DialogInterface paramAnonymous2DialogInterface, int
				 * paramAnonymous2Int) { if
				 * (MyApplication.getInstance().mConnect != null)
				 * Toast.makeText(HeaderSetActivity.this, "摄像头已被监控占用，无法拍照！",
				 * 0).show(); while (true) {
				 * paramAnonymous2DialogInterface.dismiss(); return; Intent
				 * localIntent = new Intent();
				 * localIntent.setClass(HeaderSetActivity.this,
				 * CustomCamera.class);
				 * HeaderSetActivity.this.startActivityForResult(localIntent,
				 * 1002); } } }); localBuilder.setNegativeButton("", new
				 * DialogInterface.OnClickListener() { public void
				 * onClick(DialogInterface paramAnonymous2DialogInterface, int
				 * paramAnonymous2Int) {
				 * paramAnonymous2DialogInterface.dismiss(); } });
				 */
				localBuilder.create().show();
			}
		});
	}

	public void startPhotoClip(Uri paramUri) {
		Log.v("-----------", paramUri.getPath());
		Intent localIntent = new Intent(this, HeaderClipActivity.class);
		localIntent.putExtra("uri", paramUri);
		startActivityForResult(localIntent, 1003);
	}
}

/*
 * Location:
 * C:\Users\Administrator\Desktop\小优\U03S源码\机器人本体.apk\classes_dex2jar.jar
 * Qualified Name: com.chinatel.robot.Activity.HeaderSetActivity JD-Core
 * Version: 0.6.2
 */