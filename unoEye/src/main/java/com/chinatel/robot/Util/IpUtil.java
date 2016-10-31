package com.chinatel.robot.Util;

import java.io.PrintStream;

public class IpUtil {
	public static int Ip2Int(String paramString) {
		String[] arrayOfString = paramString.split("\\.");
		if (arrayOfString.length != 4)
			return 0;
		byte[] arrayOfByte = new byte[arrayOfString.length];
		for (int i = 0;; i++) {
			if (i >= arrayOfByte.length)
				return byte2Int(arrayOfByte);
			arrayOfByte[i] = ((byte) Integer.parseInt(arrayOfString[i]));
		}
	}

	private static int byte2Int(byte[] paramArrayOfByte) {
		return 0xFF & paramArrayOfByte[0] | 0xFF00 & paramArrayOfByte[1] << 8
				| 0xFF0000 & paramArrayOfByte[2] << 16 | 0xFF000000
				& paramArrayOfByte[3] << 24;
	}

	public static String int2Ip(int paramInt) {
		byte[] arrayOfByte = int2byte(paramInt);
		StringBuilder localStringBuilder = new StringBuilder();
		for (int i = 0;; i++) {
			if (i >= 4)
				return localStringBuilder.toString();
			localStringBuilder.append(0xFF & arrayOfByte[i]);
			if (i < 3)
				localStringBuilder.append(".");
		}
	}

	private static byte[] int2byte(int paramInt) {
		byte[] arrayOfByte = new byte[4];
		arrayOfByte[0] = ((byte) (paramInt & 0xFF));
		arrayOfByte[1] = ((byte) ((0xFF00 & paramInt) >> 8));
		arrayOfByte[2] = ((byte) ((0xFF0000 & paramInt) >> 16));
		arrayOfByte[3] = ((byte) ((0xFF000000 & paramInt) >> 24));
		return arrayOfByte;
	}

	public static void main(String[] paramArrayOfString) {
		String str = int2Ip(Ip2Int("192.168.0.1"));
		System.out.println(str.equals("192.168.0.1"));
	}
}

/*
 * Location:
 * C:\Users\Administrator\Desktop\小优\U03S源码\机器人本体.apk\classes_dex2jar.jar
 * Qualified Name: com.chinatel.robot.Util.IpUtil JD-Core Version: 0.6.2
 */