package com.chinatel.robot.Util;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

public class ViewUtil {
	public static int dipToPixel(Context paramContext, int paramInt) {
		if (paramContext == null)
			return paramInt;
		return (int) (0.5F + paramContext.getResources().getDisplayMetrics().density
				* paramInt);
	}

	public static Point getWindowSize(Context paramContext) {
		Point localPoint = new Point(0, 0);
		if (paramContext == null)
			return localPoint;
		((WindowManager) paramContext.getSystemService("window"))
				.getDefaultDisplay().getSize(localPoint);
		return localPoint;
	}

	public static int pixelToDip(Context paramContext, int paramInt) {
		if (paramContext == null)
			return paramInt;
		float f = paramContext.getResources().getDisplayMetrics().density;
		return (int) (0.5F + paramInt / f);
	}
}

/*
 * Location:
 * C:\Users\Administrator\Desktop\小优\U03S源码\机器人本体.apk\classes_dex2jar.jar
 * Qualified Name: com.chinatel.robot.Util.ViewUtil JD-Core Version: 0.6.2
 */