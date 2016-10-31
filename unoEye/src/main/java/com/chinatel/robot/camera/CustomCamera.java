package com.chinatel.robot.camera;

import java.io.IOException;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.chinatel.robot.application.MyApplication;

public class CustomCamera extends Activity implements View.OnClickListener {
	ImageButton btn2 = null;
	private Camera camera = null;
	private CameraView cv = null;
	private boolean isTaking = false;
	LinearLayout l = null;
	private Camera.PictureCallback picture = new Camera.PictureCallback() {
		public void onPictureTaken(byte[] paramAnonymousArrayOfByte,
				Camera paramAnonymousCamera) {
			Bitmap localBitmap = null;
			if (paramAnonymousArrayOfByte != null)
				localBitmap = BitmapFactory.decodeByteArray(
						paramAnonymousArrayOfByte, 0,
						paramAnonymousArrayOfByte.length);
			if (localBitmap != null) {
				ImageUtil.getRotateBitmap(localBitmap, 90.0F);
				String str = FileUtil.saveBitmap(localBitmap);
				if (str != null) {
					Intent localIntent = new Intent();
					localIntent.putExtra("img", str);
					CustomCamera.this.setResult(-1, localIntent);
					CustomCamera.this.finish();
				}
			}
		}
	};

	private void startCamera() {
		this.l.removeAllViews();
		this.cv = new CameraView(this);
		LinearLayout.LayoutParams localLayoutParams = new LinearLayout.LayoutParams(
				-1, -1);
		this.l.addView(this.cv, localLayoutParams);
	}

	public void onClick(View paramView) {
		if ((paramView == this.btn2) && (!this.isTaking)) {
			this.camera.takePicture(null, null, this.picture);
			this.isTaking = true;
		}
	}

	public void onCreate(Bundle paramBundle) {
		MyApplication.getInstance();
		MyApplication.photographing = true;
		super.onCreate(paramBundle);
		requestWindowFeature(1);
		getWindow().setFlags(1024, 1024);
		getWindow().setFormat(-3);
		setContentView(2130903040);
		this.l = ((LinearLayout) findViewById(2131361794));
		this.btn2 = ((ImageButton) findViewById(2131361795));
		this.btn2.setOnClickListener(this);
		startCamera();
	}

	protected void onDestroy() {
		super.onDestroy();
		this.isTaking = false;
		MyApplication.getInstance();
		MyApplication.photographing = false;
	}

	class CameraView extends SurfaceView {
		private SurfaceHolder holder = null;

		public CameraView(Context arg2) {
			super(arg2);
			this.holder.addCallback(new SurfaceHolder.Callback() {
				public void surfaceChanged(
						SurfaceHolder paramAnonymousSurfaceHolder,
						int paramAnonymousInt1, int paramAnonymousInt2,
						int paramAnonymousInt3) {
					Camera.Parameters localParameters = CustomCamera.this.camera
							.getParameters();
					List localList = localParameters.getSupportedPreviewSizes();
					Camera.Size localSize = (Camera.Size) localList.get(0);
					for (int i = 1;; i++) {
						if (i >= localList.size()) {
							localParameters.setPreviewSize(localSize.width,
									localSize.height);
							CustomCamera.this.camera
									.setParameters(localParameters);
							CustomCamera.this.camera.startPreview();
							return;
						}
						if (((Camera.Size) localList.get(i)).width
								* ((Camera.Size) localList.get(i)).height > localSize.width
								* localSize.height)
							localSize = (Camera.Size) localList.get(i);
					}
				}

				public void surfaceCreated(
						SurfaceHolder paramAnonymousSurfaceHolder) {
					CustomCamera.this.camera = Camera.open();
					try {
						CustomCamera.this.camera.setDisplayOrientation(0);
						CustomCamera.this.camera
								.setPreviewDisplay(paramAnonymousSurfaceHolder);
						return;
					} catch (IOException localIOException) {
						CustomCamera.this.camera.release();
						CustomCamera.this.camera = null;
						localIOException.printStackTrace();
					}
				}

				public void surfaceDestroyed(
						SurfaceHolder paramAnonymousSurfaceHolder) {
					CustomCamera.this.camera.stopPreview();
					CustomCamera.this.camera.release();
					CustomCamera.this.camera = null;
				}
			});
		}
	}
}

/*
 * Location:
 * C:\Users\Administrator\Desktop\小优\U03S源码\机器人本体.apk\classes_dex2jar.jar
 * Qualified Name: com.chinatel.robot.camera.CustomCamera JD-Core Version: 0.6.2
 */