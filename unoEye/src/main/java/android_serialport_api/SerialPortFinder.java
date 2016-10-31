package android_serialport_api;

import android.util.Log;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.Iterator;
import java.util.Vector;

public class SerialPortFinder {
	private static final String TAG = "SerialPort";
	private Vector<Driver> mDrivers = null;

	public String[] getAllDevices() {
		Vector localVector = new Vector();
		try {
			Iterator localIterator1 = getDrivers().iterator();
			while (true) {
				boolean bool = localIterator1.hasNext();
				if (!bool)
					return (String[]) localVector
							.toArray(new String[localVector.size()]);
				Driver localDriver = (Driver) localIterator1.next();
				Iterator localIterator2 = localDriver.getDevices().iterator();
				while (localIterator2.hasNext()) {
					String str = ((File) localIterator2.next()).getName();
					Object[] arrayOfObject = new Object[2];
					arrayOfObject[0] = str;
					arrayOfObject[1] = localDriver.getName();
					localVector.add(String.format("%s (%s)", arrayOfObject));
				}
			}
		} catch (IOException localIOException) {
			while (true)
				localIOException.printStackTrace();
		}
	}

	public String[] getAllDevicesPath() {
		Vector localVector = new Vector();
		try {
			Iterator localIterator1 = getDrivers().iterator();
			while (true) {
				boolean bool = localIterator1.hasNext();
				if (!bool)
					return (String[]) localVector
							.toArray(new String[localVector.size()]);
				Iterator localIterator2 = ((Driver) localIterator1.next())
						.getDevices().iterator();
				while (localIterator2.hasNext())
					localVector.add(((File) localIterator2.next())
							.getAbsolutePath());
			}
		} catch (IOException localIOException) {
			while (true)
				localIOException.printStackTrace();
		}
	}

	Vector<Driver> getDrivers() throws IOException {
		LineNumberReader localLineNumberReader = null;
		if (this.mDrivers == null) {
			this.mDrivers = new Vector();
			localLineNumberReader = new LineNumberReader(new FileReader(
					"/proc/tty/drivers"));
		}
		while (true) {
			String str1 = localLineNumberReader.readLine();
			if (str1 == null) {
				localLineNumberReader.close();
				return this.mDrivers;
			}
			String str2 = str1.substring(0, 21).trim();
			String[] arrayOfString = str1.split(" +");
			if ((arrayOfString.length >= 5)
					&& (arrayOfString[(-1 + arrayOfString.length)]
							.equals("serial"))) {
				Log.d("SerialPort", "Found new driver " + str2 + " on "
						+ arrayOfString[(-4 + arrayOfString.length)]);
				this.mDrivers.add(new Driver(str2,
						arrayOfString[(-4 + arrayOfString.length)]));
			}
		}
	}

	public class Driver {
		private String mDeviceRoot;
		Vector<File> mDevices = null;
		private String mDriverName;

		public Driver(String paramString1, String localObject) {
			this.mDriverName = paramString1;
			this.mDeviceRoot = localObject;
		}

		public Vector<File> getDevices() {
			File[] arrayOfFile = null;
			if (this.mDevices == null) {
				this.mDevices = new Vector();
				arrayOfFile = new File("/dev").listFiles();
			}
			for (int i = 0;; i++) {
				if (i >= arrayOfFile.length)
					return this.mDevices;
				if (arrayOfFile[i].getAbsolutePath().startsWith(
						this.mDeviceRoot)) {
					Log.d("SerialPort", "Found new device: " + arrayOfFile[i]);
					this.mDevices.add(arrayOfFile[i]);
				}
			}
		}

		public String getName() {
			return this.mDriverName;
		}
	}
}

/*
 * Location:
 * C:\Users\Administrator\Desktop\小优\U03S源码\机器人本体.apk\classes_dex2jar.jar
 * Qualified Name: android_serialport_api.SerialPortFinder JD-Core Version:
 * 0.6.2
 */