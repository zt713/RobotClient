package com.chinatel.robot.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class HomeMemberDBOpenHelper extends SQLiteOpenHelper {
	public HomeMemberDBOpenHelper(Context paramContext) {
		super(paramContext, "homemember.db", null, 1);
	}

	public void onCreate(SQLiteDatabase paramSQLiteDatabase) {
		paramSQLiteDatabase
				.execSQL("create table homemember (_id integer primary key autoincrement,imguri varchar(50),name varchar(10),device varchar(20),mode varchar(10),keycode varchar(100))");
	}

	public void onUpgrade(SQLiteDatabase paramSQLiteDatabase, int paramInt1,
			int paramInt2) {
	}
}

/*
 * Location:
 * C:\Users\Administrator\Desktop\小优\U03S源码\机器人本体.apk\classes_dex2jar.jar
 * Qualified Name: com.chinatel.robot.db.HomeMemberDBOpenHelper JD-Core Version:
 * 0.6.2
 */