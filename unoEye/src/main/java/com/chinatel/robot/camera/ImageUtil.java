package com.chinatel.robot.camera;

import android.graphics.Bitmap;
import android.graphics.Matrix;

public class ImageUtil {
	public static Bitmap getRotateBitmap(Bitmap paramBitmap, float paramFloat) {
		Matrix localMatrix = new Matrix();
		localMatrix.postRotate(paramFloat);
		return Bitmap.createBitmap(paramBitmap, 0, 0, paramBitmap.getWidth(),
				paramBitmap.getHeight(), localMatrix, false);
	}
}

/*
 * Location:
 * C:\Users\Administrator\Desktop\小优\U03S源码\机器人本体.apk\classes_dex2jar.jar
 * Qualified Name: com.chinatel.robot.camera.ImageUtil JD-Core Version: 0.6.2
 */