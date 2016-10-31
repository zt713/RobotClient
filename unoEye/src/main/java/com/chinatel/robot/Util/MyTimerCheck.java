package com.chinatel.robot.Util;

public abstract class MyTimerCheck {
	private int mCount = 0;
	private boolean mExitFlag = false;
	private int mSleepTime = 1000;
	private Thread mThread = null;
	private int mTimeOutCount = 1;

	public abstract void doTimeOutWork();

	public abstract void doTimerCheckWork();

	public void exit() {
		this.mExitFlag = true;
	}

	public void start(int paramInt1, int paramInt2) {
		this.mTimeOutCount = paramInt1;
		this.mSleepTime = paramInt2;
		this.mThread.start();
	}
}

/*
 * Location:
 * C:\Users\Administrator\Desktop\小优\U03S源码\机器人本体.apk\classes_dex2jar.jar
 * Qualified Name: com.chinatel.robot.Util.MyTimerCheck JD-Core Version: 0.6.2
 */