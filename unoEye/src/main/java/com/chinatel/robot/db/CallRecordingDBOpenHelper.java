package com.chinatel.robot.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CallRecordingDBOpenHelper extends SQLiteOpenHelper {
	public CallRecordingDBOpenHelper(Context paramContext) {
		super(paramContext, "callrecord.db", null, 1);
	}

	public void onCreate(SQLiteDatabase paramSQLiteDatabase) {
		paramSQLiteDatabase
				.execSQL("create table callrecord (_id integer primary key autoincrement,comego varchar(10),starttime varchar(20),endtime varchar(20),keycode varchar(100))");
	}

	public void onUpgrade(SQLiteDatabase paramSQLiteDatabase, int paramInt1,
			int paramInt2) {
	}
}

/*
 * Location:
 * C:\Users\Administrator\Desktop\小优\U03S源码\机器人本体.apk\classes_dex2jar.jar
 * Qualified Name: com.chinatel.robot.db.CallRecordingDBOpenHelper JD-Core
 * Version: 0.6.2
 */