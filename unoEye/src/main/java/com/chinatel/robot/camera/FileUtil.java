package com.chinatel.robot.camera;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.os.Environment;
import android.util.Log;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileUtil {
	public static final String DST_FOLDER_NAME = "PlayCamera";
	private static final String TAG = "FileUtil";
	private static final File parentPath = Environment
			.getExternalStorageDirectory();
	private static String storagePath = "";

	public static String getSDPath() {
		return parentPath.getAbsolutePath() + "/";
	}

	public static String initPath() {
		if (storagePath.equals("")) {
			storagePath = parentPath.getAbsolutePath() + "/" + "PlayCamera";
			File localFile = new File(storagePath);
			if (!localFile.exists())
				localFile.mkdir();
		}
		return storagePath;
	}

	public static String saveBitmap(Bitmap paramBitmap) {
		String str1 = initPath();
		long l = System.currentTimeMillis();
		String str2 = str1 + "/" + l + ".jpg";
		Log.i("FileUtil", "saveBitmap:jpegName = " + str2);
		try {
			BufferedOutputStream localBufferedOutputStream = new BufferedOutputStream(
					new FileOutputStream(str2));
			paramBitmap.compress(Bitmap.CompressFormat.JPEG, 100,
					localBufferedOutputStream);
			localBufferedOutputStream.flush();
			localBufferedOutputStream.close();
			Log.i("FileUtil", "saveBitmap成功");
			return str2;
		} catch (IOException localIOException) {
			Log.i("FileUtil", "saveBitmap:失败");
			localIOException.printStackTrace();
		}
		return null;
	}
}

/*
 * Location:
 * C:\Users\Administrator\Desktop\小优\U03S源码\机器人本体.apk\classes_dex2jar.jar
 * Qualified Name: com.chinatel.robot.camera.FileUtil JD-Core Version: 0.6.2
 */