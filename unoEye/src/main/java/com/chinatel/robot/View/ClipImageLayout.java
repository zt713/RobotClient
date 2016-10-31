package com.chinatel.robot.View;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.provider.MediaStore.Images.Media;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import java.io.FileNotFoundException;
import java.io.IOException;

public class ClipImageLayout extends RelativeLayout {
	private ClipImageBorderView mClipImageView;
	private Context mContext;
	private int mHorizontalPadding = 20;
	private ClipZoomImageView mZoomImageView;

	public ClipImageLayout(Context paramContext, AttributeSet paramAttributeSet) {
		super(paramContext, paramAttributeSet);
		this.mContext = paramContext;
	}

	public Bitmap clip() {
		return this.mZoomImageView.clip();
	}

	public void setHorizontalPadding(int paramInt) {
		this.mHorizontalPadding = paramInt;
	}

	public void setZoomImage(Uri paramUri) {
		this.mZoomImageView = new ClipZoomImageView(this.mContext);
		this.mClipImageView = new ClipImageBorderView(this.mContext);
		RelativeLayout.LayoutParams localLayoutParams = new RelativeLayout.LayoutParams(
				-1, -1);
		try {
			BitmapDrawable localBitmapDrawable = new BitmapDrawable(
					MediaStore.Images.Media.getBitmap(
							this.mContext.getContentResolver(), paramUri));
			this.mZoomImageView.setImageDrawable(localBitmapDrawable);
			addView(this.mZoomImageView, localLayoutParams);
			addView(this.mClipImageView, localLayoutParams);
			this.mHorizontalPadding = ((int) TypedValue
					.applyDimension(1, this.mHorizontalPadding, getResources()
							.getDisplayMetrics()));
			this.mZoomImageView.setHorizontalPadding(this.mHorizontalPadding);
			this.mClipImageView.setHorizontalPadding(this.mHorizontalPadding);
			return;
		} catch (FileNotFoundException localFileNotFoundException) {
			while (true)
				localFileNotFoundException.printStackTrace();
		} catch (IOException localIOException) {
			while (true)
				localIOException.printStackTrace();
		}
	}
}

/*
 * Location:
 * C:\Users\Administrator\Desktop\小优\U03S源码\机器人本体.apk\classes_dex2jar.jar
 * Qualified Name: com.chinatel.robot.View.ClipImageLayout JD-Core Version:
 * 0.6.2
 */