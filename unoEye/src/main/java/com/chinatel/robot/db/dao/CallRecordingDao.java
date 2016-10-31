package com.chinatel.robot.db.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.chinatel.robot.bean.CallRecordInfo;
import com.chinatel.robot.db.CallRecordingDBOpenHelper;
import java.util.ArrayList;
import java.util.List;

public class CallRecordingDao {
	private CallRecordingDBOpenHelper helper;
	private ArrayList localArrayList;

	public CallRecordingDao(Context paramContext) {
		this.helper = new CallRecordingDBOpenHelper(paramContext);
	}

	public void add(CallRecordInfo paramCallRecordInfo) {
		SQLiteDatabase localSQLiteDatabase = this.helper.getWritableDatabase();
		ContentValues localContentValues = new ContentValues();
		localContentValues.put("comego", paramCallRecordInfo.getComego());
		localContentValues.put("starttime", paramCallRecordInfo.getStarttime());
		localContentValues.put("endtime", paramCallRecordInfo.getEndtime());
		localContentValues.put("keycode", paramCallRecordInfo.getKeycode());
		localSQLiteDatabase.insert("callrecord", null, localContentValues);
		localSQLiteDatabase.close();
	}

	public void add(String paramString1, String paramString2,
			String paramString3, String paramString4) {
		SQLiteDatabase localSQLiteDatabase = this.helper.getWritableDatabase();
		ContentValues localContentValues = new ContentValues();
		localContentValues.put("comego", paramString1);
		localContentValues.put("starttime", paramString2);
		localContentValues.put("endtime", paramString3);
		localContentValues.put("keycode", paramString4);
		localSQLiteDatabase.insert("callrecord", null, localContentValues);
		localSQLiteDatabase.close();
	}

	public void delete(String paramString) {
		SQLiteDatabase localSQLiteDatabase = this.helper.getWritableDatabase();
		localSQLiteDatabase.delete("callrecord", "keycode=?",
				new String[] { paramString });
		localSQLiteDatabase.close();
	}

	public void deleteById(int paramInt) {
		SQLiteDatabase localSQLiteDatabase = this.helper.getWritableDatabase();
		String[] arrayOfString = new String[1];
		arrayOfString[0] = paramInt + "";
		localSQLiteDatabase.delete("callrecord", "_id=?", arrayOfString);
		localSQLiteDatabase.close();
	}

	public List<CallRecordInfo> findAll() {
		ArrayList localArrayList = new ArrayList();
		SQLiteDatabase localSQLiteDatabase = this.helper.getReadableDatabase();
		Cursor localCursor = localSQLiteDatabase
				.rawQuery(
						"select comego,starttime,endtime,keycode,_id from callrecord order by _id desc",
						null);
		while (true) {
			if (!localCursor.moveToNext()) {
				localCursor.close();
				localSQLiteDatabase.close();
				return localArrayList;
			}
			CallRecordInfo localCallRecordInfo = new CallRecordInfo();
			String str1 = localCursor.getString(0);
			String str2 = localCursor.getString(1);
			String str3 = localCursor.getString(2);
			String str4 = localCursor.getString(3);
			Integer localInteger = Integer.valueOf(localCursor.getInt(4));
			localCallRecordInfo.setComego(str1);
			localCallRecordInfo.setStarttime(str2);
			localCallRecordInfo.setEndtime(str3);
			localCallRecordInfo.setKeycode(str4);
			localCallRecordInfo.set_id(localInteger);
			localArrayList.add(localCallRecordInfo);
		}
	}

	public List<CallRecordInfo> findPart(int paramInt1, int paramInt2) {
		try {
			Thread.sleep(500L);
			localArrayList = new ArrayList();
			SQLiteDatabase localSQLiteDatabase = this.helper
					.getReadableDatabase();
			String[] arrayOfString = new String[2];
			arrayOfString[0] = String.valueOf(paramInt2);
			arrayOfString[1] = String.valueOf(paramInt1);
			Cursor localCursor = localSQLiteDatabase
					.rawQuery(
							"select comego,strattime,endtime,keycode from callrecord order by _id desc  limit ? offset ? ",
							arrayOfString);
			if (!localCursor.moveToNext()) {
				localCursor.close();
				localSQLiteDatabase.close();
			}
			return localArrayList;
		} catch (InterruptedException localInterruptedException) {
			while (true) {
				ArrayList localArrayList = null;
				Cursor localCursor = null;
				localInterruptedException.printStackTrace();
				// continue;
				CallRecordInfo localCallRecordInfo = new CallRecordInfo();
				String str1 = localCursor.getString(0);
				String str2 = localCursor.getString(1);
				String str3 = localCursor.getString(2);
				String str4 = localCursor.getString(3);
				localCallRecordInfo.setComego(str1);
				localCallRecordInfo.setStarttime(str2);
				localCallRecordInfo.setEndtime(str3);
				localCallRecordInfo.setKeycode(str4);
				localArrayList.add(localCallRecordInfo);
			}
		}
	}
}

/*
 * Location:
 * C:\Users\Administrator\Desktop\小优\U03S源码\机器人本体.apk\classes_dex2jar.jar
 * Qualified Name: com.chinatel.robot.db.dao.CallRecordingDao JD-Core Version:
 * 0.6.2
 */