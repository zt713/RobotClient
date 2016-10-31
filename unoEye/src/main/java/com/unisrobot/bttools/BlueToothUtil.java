package com.unisrobot.bttools;

import android.util.Log;
import android_serialport_api.SerialPort;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class BlueToothUtil {
	public static final int ARSE_SENSOR = 129;
	public static final int CHEST_SENSOR = 128;
	public static final int HEAD_SENSOR = 113;
	public static final int LEFT_EAR_SENSOR = 127;
	public static final int LEFT_HAND_SENSOR = 125;
	public static final int NECK_SENSOR = 115;
	public static final int RIGHT_EAR_SENSOR = 126;
	public static final int RIGHT_HAND_SENSOR = 124;
	BtSensorListener mBtSensorListener;
	protected SerialPort mSerialPort = null;
	public InputStream minS = null;
	public OutputStream moutS = null;
	private boolean ok;

	public BlueToothUtil(BtSensorListener paramBtSensorListener) {
		this.mBtSensorListener = paramBtSensorListener;
		try {
			this.mSerialPort = new SerialPort(new File("/dev/ttyS3"), 9600, 0);
			this.minS = this.mSerialPort.getInputStream();
			this.moutS = this.mSerialPort.getOutputStream();
			return;
		} catch (SecurityException localSecurityException) {
			localSecurityException.printStackTrace();
			return;
		} catch (IOException localIOException) {
			localIOException.printStackTrace();
		}
	}

	private void read() {
		if (this.minS == null)
			Log.e("btread", "minS == null");
		byte[] arrayOfByte1;
		do {
			arrayOfByte1 = new byte[1024];
		} while (1 == 0);
		while (true) {
			int i;
			byte[] arrayOfByte3;
			int k;
			int m;
			String localObject = null;
			try {
				byte[] arrayOfByte2 = new byte[1024];
				this.minS.read(arrayOfByte2);
				if (!Integer.toHexString(0xFF & arrayOfByte2[0]).equals("42"))
					break;
				arrayOfByte1[0] = arrayOfByte2[0];
				i = 0 + 1;
				arrayOfByte3 = new byte[1024];
				int j = this.minS.read(arrayOfByte3);
				k = 0;
				if (k >= j) {
					if (!Integer.toHexString(0xFF & arrayOfByte3[0])
							.equals("a"))
						// continue;
						localObject = "";
					m = 0;
					if (m < i)
						// break label220;
						Log.e("收到的数据", "收到的数据000   " + (String) localObject);
					readAction(0xFF & arrayOfByte1[4]);
				}
			} catch (IOException localIOException) {
				Log.e("btread",
						"bt read...error..." + localIOException.toString());
				return;
			}
			arrayOfByte1[i] = arrayOfByte3[k];
			i++;
			k++;
			// continue;
			// String str = localObject + " " + Integer.toHexString(0xFF &
			// arrayOfByte1[m]);
			// Object localObject = str;
			// m++;
		}
	}

	private void startBtRead() {
		new Thread(new Runnable() {
			public void run() {
				BlueToothUtil.this.read();
			}
		}).start();
	}

	private void write(byte[] paramArrayOfByte) {
		try {
			byte[] arrayOfByte = new byte[3 + paramArrayOfByte.length];
			arrayOfByte[0] = 83;
			arrayOfByte[1] = 75;
			for (int i = 0;; i++) {
				if (i >= paramArrayOfByte.length) {
					arrayOfByte[(2 + paramArrayOfByte.length)] = 10;
					this.moutS.write(arrayOfByte);
					return;
				}
				arrayOfByte[(i + 2)] = paramArrayOfByte[i];
			}
		} catch (IOException localIOException) {
		}
	}

	public void readAction(int paramInt) {
		if (this.ok)
			this.mBtSensorListener.onSensorTouch(paramInt);
	}

	public void registerBtSensorBR() {
		this.ok = true;
	}

	public void sendCmd(byte paramByte) {
		byte[] arrayOfByte = new byte[2];
		arrayOfByte[1] = paramByte;
		arrayOfByte[0] = 1;
		write(arrayOfByte);
	}

	public void sendSystemCmd(byte paramByte) {
		write(new byte[] { 3, paramByte, 0, 0 });
	}

	public void unRegisterBtSensorBR() {
		this.ok = false;
	}
}

/*
 * Location:
 * C:\Users\Administrator\Desktop\小优\U03S源码\机器人本体.apk\classes_dex2jar.jar
 * Qualified Name: com.unisrobot.bttools.BlueToothUtil JD-Core Version: 0.6.2
 */