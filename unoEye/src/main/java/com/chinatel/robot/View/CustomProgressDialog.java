package com.chinatel.robot.View;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomProgressDialog extends ProgressDialog {
	private int count = 0;
	private AnimationDrawable mAnimation;
	private Context mContext;
	private ImageView mImageView;
	private String mLoadingTip;
	private TextView mLoadingTv;
	private int mResid;
	private String oldLoadingTip;

	public CustomProgressDialog(Context paramContext, String paramString,
			int paramInt) {
		super(paramContext);
		this.mContext = paramContext;
		this.mLoadingTip = paramString;
		this.mResid = paramInt;
		setCanceledOnTouchOutside(true);
	}

	private void initData() {
		this.mImageView.setBackgroundResource(this.mResid);
		this.mAnimation = ((AnimationDrawable) this.mImageView.getBackground());
		this.mImageView.post(new Runnable() {
			public void run() {
				CustomProgressDialog.this.mAnimation.start();
			}
		});
		this.mLoadingTv.setText(this.mLoadingTip);
	}

	private void initView() {
		setContentView(2130903063);
		this.mLoadingTv = ((TextView) findViewById(2131361888));
		this.mImageView = ((ImageView) findViewById(2131361887));
	}

	protected void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);
		initView();
		initData();
	}

	public void setContent(String paramString) {
		this.mLoadingTv.setText(paramString);
	}
}

/*
 * Location:
 * C:\Users\Administrator\Desktop\小优\U03S源码\机器人本体.apk\classes_dex2jar.jar
 * Qualified Name: com.chinatel.robot.View.CustomProgressDialog JD-Core Version:
 * 0.6.2
 */