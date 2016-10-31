package com.chinatel.robot.db.dao;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import com.chinatel.robot.db.HomeMemberDBOpenHelper;

public class HomeMemberContentProvider extends ContentProvider {
	private HomeMemberDBOpenHelper dbOpenHelper = null;

	public int delete(Uri paramUri, String paramString,
			String[] paramArrayOfString) {
		SQLiteDatabase localSQLiteDatabase = this.dbOpenHelper
				.getWritableDatabase();
		int i = localSQLiteDatabase.delete("homemember", paramString,
				paramArrayOfString);
		localSQLiteDatabase.close();
		return i;
	}

	public String getType(Uri paramUri) {
		return null;
	}

	public Uri insert(Uri paramUri, ContentValues paramContentValues) {
		return ContentUris.withAppendedId(
				paramUri,
				this.dbOpenHelper.getWritableDatabase().insert("homemember",
						null, paramContentValues));
	}

	public boolean onCreate() {
		this.dbOpenHelper = new HomeMemberDBOpenHelper(getContext());
		return true;
	}

	public Cursor query(Uri paramUri, String[] paramArrayOfString1,
			String paramString1, String[] paramArrayOfString2,
			String paramString2) {
		return this.dbOpenHelper.getReadableDatabase().query("homemember",
				paramArrayOfString1, paramString1, paramArrayOfString2, null,
				null, paramString2);
	}

	public int update(Uri paramUri, ContentValues paramContentValues,
			String paramString, String[] paramArrayOfString) {
		SQLiteDatabase localSQLiteDatabase = this.dbOpenHelper
				.getWritableDatabase();
		int i = localSQLiteDatabase.update("homemember", paramContentValues,
				paramString, paramArrayOfString);
		localSQLiteDatabase.close();
		return i;
	}
}

/*
 * Location:
 * C:\Users\Administrator\Desktop\小优\U03S源码\机器人本体.apk\classes_dex2jar.jar
 * Qualified Name: com.chinatel.robot.db.dao.HomeMemberContentProvider JD-Core
 * Version: 0.6.2
 */