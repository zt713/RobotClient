package com.chinatel.robot.Util;

import android.graphics.Bitmap;
import android.os.Environment;
import android.view.View;
import android.view.View.MeasureSpec;
import java.io.File;

public class BMapUtil {
	private final String mImagePath = Environment.getExternalStorageDirectory()
			.getPath() + "/robotimage";

	public static Bitmap getBitmapFromView(View paramView) {
		paramView.destroyDrawingCache();
		paramView.measure(View.MeasureSpec.makeMeasureSpec(0, 0),
				View.MeasureSpec.makeMeasureSpec(0, 0));
		paramView.layout(0, 0, paramView.getMeasuredWidth(),
				paramView.getMeasuredHeight());
		paramView.setDrawingCacheEnabled(true);
		return paramView.getDrawingCache(true);
	}

	public String getImageDir() {
		if ("mounted".equals(Environment.getExternalStorageState())) {
			File localFile = new File(this.mImagePath);
			if (!localFile.exists())
				localFile.mkdirs();
		}
		return this.mImagePath;
	}
}

/*
 * Location:
 * C:\Users\Administrator\Desktop\小优\U03S源码\机器人本体.apk\classes_dex2jar.jar
 * Qualified Name: com.chinatel.robot.Util.BMapUtil JD-Core Version: 0.6.2
 */