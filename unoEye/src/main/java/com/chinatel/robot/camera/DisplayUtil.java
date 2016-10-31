package com.chinatel.robot.camera;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.util.DisplayMetrics;
import android.util.Log;

public class DisplayUtil {
	private static final String TAG = "DisplayUtil";

	public static int dip2px(Context paramContext, float paramFloat) {
		return (int) (0.5F + paramFloat
				* paramContext.getResources().getDisplayMetrics().density);
	}

	public static Point getScreenMetrics(Context paramContext) {
		DisplayMetrics localDisplayMetrics = paramContext.getResources()
				.getDisplayMetrics();
		int i = localDisplayMetrics.widthPixels;
		int j = localDisplayMetrics.heightPixels;
		Log.i("DisplayUtil", "Screen---Width = " + i + " Height = " + j
				+ " densityDpi = " + localDisplayMetrics.densityDpi);
		return new Point(i, j);
	}

	public static float getScreenRate(Context paramContext) {
		Point localPoint = getScreenMetrics(paramContext);
		return localPoint.y / localPoint.x;
	}

	public static int px2dip(Context paramContext, float paramFloat) {
		return (int) (0.5F + paramFloat
				/ paramContext.getResources().getDisplayMetrics().density);
	}
}

/*
 * Location:
 * C:\Users\Administrator\Desktop\小优\U03S源码\机器人本体.apk\classes_dex2jar.jar
 * Qualified Name: com.chinatel.robot.camera.DisplayUtil JD-Core Version: 0.6.2
 */