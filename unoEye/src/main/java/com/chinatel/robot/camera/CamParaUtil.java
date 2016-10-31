package com.chinatel.robot.camera;

import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import android.hardware.Camera;
import android.util.Log;

public class CamParaUtil {
	private static final String TAG = "yanzi";
	private static CamParaUtil myCamPara = null;
	private CameraSizeComparator sizeComparator = new CameraSizeComparator();

	public static CamParaUtil getInstance() {
		if (myCamPara == null) {
			myCamPara = new CamParaUtil();
			return myCamPara;
		}
		return myCamPara;
	}

	public boolean equalRate(Camera.Size paramSize, float paramFloat) {
		return Math.abs(paramSize.width / paramSize.height - paramFloat) <= 0.03D;
	}

	public Camera.Size getPropPictureSize(List<Camera.Size> paramList,
			float paramFloat, int paramInt) {
		Collections.sort(paramList, this.sizeComparator);
		int i = 0;
		Iterator localIterator = paramList.iterator();
		while (true) {
			if (!localIterator.hasNext())
				;
			while (true) {
				if (i == paramList.size())
					i = 0;
				// return (Camera.Size)paramList.get(i);
				Camera.Size localSize = (Camera.Size) localIterator.next();
				if ((localSize.width < paramInt)
						|| (!equalRate(localSize, paramFloat)))
					break;
				Log.i("yanzi", "PictureSize : w = " + localSize.width + "h = "
						+ localSize.height);
			}
			i++;
		}
	}

	public Camera.Size getPropPreviewSize(List<Camera.Size> paramList,
			float paramFloat, int paramInt) {
		Collections.sort(paramList, this.sizeComparator);
		int i = 0;
		Iterator localIterator = paramList.iterator();
		while (true) {
			if (!localIterator.hasNext())
				;
			while (true) {
				if (i == paramList.size())
					i = 0;
				// return (Camera.Size)paramList.get(i);
				Camera.Size localSize = (Camera.Size) localIterator.next();
				if ((localSize.width < paramInt)
						|| (!equalRate(localSize, paramFloat)))
					break;
				Log.i("yanzi", "PreviewSize:w = " + localSize.width + "h = "
						+ localSize.height);
			}
			i++;
		}
	}

	public void printSupportFocusMode(Camera.Parameters paramParameters) {
		Iterator localIterator = paramParameters.getSupportedFocusModes()
				.iterator();
		while (true) {
			if (!localIterator.hasNext())
				return;
			String str = (String) localIterator.next();
			Log.i("yanzi", "focusModes--" + str);
		}
	}

	public void printSupportPictureSize(Camera.Parameters paramParameters) {
		List localList = paramParameters.getSupportedPictureSizes();
		for (int i = 0;; i++) {
			if (i >= localList.size())
				return;
			Camera.Size localSize = (Camera.Size) localList.get(i);
			Log.i("yanzi", "pictureSizes:width = " + localSize.width
					+ " height = " + localSize.height);
		}
	}

	public void printSupportPreviewSize(Camera.Parameters paramParameters) {
		List localList = paramParameters.getSupportedPreviewSizes();
		for (int i = 0;; i++) {
			if (i >= localList.size())
				return;
			Camera.Size localSize = (Camera.Size) localList.get(i);
			Log.i("yanzi", "previewSizes:width = " + localSize.width
					+ " height = " + localSize.height);
		}
	}

	public class CameraSizeComparator implements Comparator<Camera.Size> {
		public CameraSizeComparator() {
		}

		public int compare(Camera.Size paramSize1, Camera.Size paramSize2) {
			if (paramSize1.width == paramSize2.width)
				return 0;
			if (paramSize1.width > paramSize2.width)
				return 1;
			return -1;
		}
	}
}

/*
 * Location:
 * C:\Users\Administrator\Desktop\小优\U03S源码\机器人本体.apk\classes_dex2jar.jar
 * Qualified Name: com.chinatel.robot.camera.CamParaUtil JD-Core Version: 0.6.2
 */