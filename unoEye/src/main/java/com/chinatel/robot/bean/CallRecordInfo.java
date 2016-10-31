package com.chinatel.robot.bean;

public class CallRecordInfo {
	public static final String COME = "come";
	public static final String GO = "go";
	public Integer _id;
	private boolean checked;
	private String comego;
	private String endtime;
	private String keycode;
	private String starttime;

	public String getComego() {
		return this.comego;
	}

	public String getEndtime() {
		return this.endtime;
	}

	public String getKeycode() {
		return this.keycode;
	}

	public String getStarttime() {
		return this.starttime;
	}

	public Integer get_id() {
		return this._id;
	}

	public boolean isChecked() {
		return this.checked;
	}

	public void setChecked(boolean paramBoolean) {
		this.checked = paramBoolean;
	}

	public void setComego(String paramString) {
		this.comego = paramString;
	}

	public void setEndtime(String paramString) {
		this.endtime = paramString;
	}

	public void setKeycode(String paramString) {
		this.keycode = paramString;
	}

	public void setStarttime(String paramString) {
		this.starttime = paramString;
	}

	public void set_id(Integer paramInteger) {
		this._id = paramInteger;
	}
}

/*
 * Location:
 * C:\Users\Administrator\Desktop\小优\U03S源码\机器人本体.apk\classes_dex2jar.jar
 * Qualified Name: com.chinatel.robot.bean.CallRecordInfo JD-Core Version: 0.6.2
 */