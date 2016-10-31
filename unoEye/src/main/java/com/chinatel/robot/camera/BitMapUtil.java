package com.chinatel.robot.camera;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;

public class BitMapUtil {
	public static Bitmap adjustPhotoRotation(Bitmap paramBitmap, int paramInt) {
		Matrix localMatrix = new Matrix();
		localMatrix.setRotate(paramInt, paramBitmap.getWidth() / 2.0F,
				paramBitmap.getHeight() / 2.0F);
		float f1 = 0;
		if (paramInt == 90)
			f1 = paramBitmap.getHeight();
		for (float f2 = 0.0F;; f2 = paramBitmap.getWidth()) {
			float[] arrayOfFloat = new float[9];
			localMatrix.getValues(arrayOfFloat);
			float f3 = arrayOfFloat[2];
			float f4 = arrayOfFloat[5];
			localMatrix.postTranslate(f1 - f3, f2 - f4);
			Bitmap localBitmap = Bitmap.createBitmap(paramBitmap.getHeight(),
					paramBitmap.getWidth(), Bitmap.Config.ARGB_8888);
			Paint localPaint = new Paint();
			new Canvas(localBitmap).drawBitmap(paramBitmap, localMatrix,
					localPaint);
			// return localBitmap;
			f1 = paramBitmap.getHeight();
		}
	}
}

/*
 * Location:
 * C:\Users\Administrator\Desktop\小优\U03S源码\机器人本体.apk\classes_dex2jar.jar
 * Qualified Name: com.chinatel.robot.camera.BitMapUtil JD-Core Version: 0.6.2
 */