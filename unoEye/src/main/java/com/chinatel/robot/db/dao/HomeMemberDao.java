package com.chinatel.robot.db.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.chinatel.robot.bean.HomeMemberInfo;
import com.chinatel.robot.db.HomeMemberDBOpenHelper;
import java.util.ArrayList;
import java.util.List;

public class HomeMemberDao {
	private HomeMemberDBOpenHelper helper;

	public HomeMemberDao(Context paramContext) {
		this.helper = new HomeMemberDBOpenHelper(paramContext);
		add("", "papa", "IPHONE8S 土豪金", "1", "1004");
	}

	private HomeMemberInfo cursorMember(Cursor paramCursor) {
		HomeMemberInfo localHomeMemberInfo = new HomeMemberInfo();
		String str1 = paramCursor.getString(0);
		String str2 = paramCursor.getString(1);
		String str3 = paramCursor.getString(2);
		String str4 = paramCursor.getString(3);
		String str5 = paramCursor.getString(4);
		localHomeMemberInfo.setImguri(str1);
		localHomeMemberInfo.setName(str2);
		localHomeMemberInfo.setDevice(str3);
		localHomeMemberInfo.setMode(str4);
		localHomeMemberInfo.setKeycode(str5);
		return localHomeMemberInfo;
	}

	public void add(String paramString1, String paramString2,
			String paramString3, String paramString4, String paramString5) {
		SQLiteDatabase localSQLiteDatabase = this.helper.getWritableDatabase();
		ContentValues localContentValues = new ContentValues();
		localContentValues.put("imguri", paramString1);
		localContentValues.put("name", paramString2);
		localContentValues.put("device", paramString3);
		localContentValues.put("mode", paramString4);
		localContentValues.put("keycode", paramString5);
		localSQLiteDatabase.insert("homemember", null, localContentValues);
		localSQLiteDatabase.close();
	}

	public void delete(String paramString) {
		SQLiteDatabase localSQLiteDatabase = this.helper.getWritableDatabase();
		localSQLiteDatabase.delete("homemember", "keycode=?",
				new String[] { paramString });
		localSQLiteDatabase.close();
	}

	public List<HomeMemberInfo> findAll() {
		ArrayList localArrayList = new ArrayList();
		SQLiteDatabase localSQLiteDatabase = this.helper.getReadableDatabase();
		Cursor localCursor = localSQLiteDatabase
				.rawQuery(
						"select imguri,name,device,mode,keycode from homemember order by _id desc",
						null);
		while (true) {
			if (!localCursor.moveToNext()) {
				localCursor.close();
				localSQLiteDatabase.close();
				return localArrayList;
			}
			localArrayList.add(cursorMember(localCursor));
		}
	}

	public String findImguri(String paramString) {
		SQLiteDatabase localSQLiteDatabase = this.helper.getReadableDatabase();
		Cursor localCursor = localSQLiteDatabase.rawQuery(
				"select imguri from homemember where keycode=?",
				new String[] { paramString });
		boolean bool = localCursor.moveToNext();
		String str = null;
		if (bool)
			str = localCursor.getString(0);
		localCursor.close();
		localSQLiteDatabase.close();
		return str;
	}

	public boolean findKeyCode(String paramString) {
		SQLiteDatabase localSQLiteDatabase = this.helper.getReadableDatabase();
		Cursor localCursor = localSQLiteDatabase.rawQuery(
				"select * from homemember where keycode=?",
				new String[] { paramString });
		boolean bool1 = localCursor.moveToNext();
		boolean bool2 = false;
		if (bool1)
			bool2 = true;
		localCursor.close();
		localSQLiteDatabase.close();
		return bool2;
	}

	public String findKeycode(String paramString) {
		SQLiteDatabase localSQLiteDatabase = this.helper.getReadableDatabase();
		Cursor localCursor = localSQLiteDatabase.rawQuery(
				"select keycode from homemember where name=?",
				new String[] { paramString });
		boolean bool = localCursor.moveToNext();
		String str = null;
		if (bool)
			str = localCursor.getString(0);
		localCursor.close();
		localSQLiteDatabase.close();
		return str;
	}

	public HomeMemberInfo findMemberByKeyCode(String paramString) {
		SQLiteDatabase localSQLiteDatabase = this.helper.getReadableDatabase();
		Cursor localCursor = localSQLiteDatabase
				.rawQuery(
						"select imguri,name,device,mode,keycode from homemember where keycode=?",
						new String[] { paramString });
		boolean bool = localCursor.moveToNext();
		HomeMemberInfo localHomeMemberInfo = null;
		if (bool)
			localHomeMemberInfo = cursorMember(localCursor);
		localCursor.close();
		localSQLiteDatabase.close();
		return localHomeMemberInfo;
	}

	public String findMode(String paramString) {
		SQLiteDatabase localSQLiteDatabase = this.helper.getReadableDatabase();
		Cursor localCursor = localSQLiteDatabase.rawQuery(
				"select mode from homemember where keycode=?",
				new String[] { paramString });
		boolean bool = localCursor.moveToNext();
		String str = null;
		if (bool)
			str = localCursor.getString(3);
		localCursor.close();
		localSQLiteDatabase.close();
		return str;
	}

	public boolean findName(String paramString) {
		SQLiteDatabase localSQLiteDatabase = this.helper.getReadableDatabase();
		Cursor localCursor = localSQLiteDatabase.query("homemember", null,
				"name=?", new String[] { paramString }, null, null, null);
		boolean bool1 = localCursor.moveToNext();
		boolean bool2 = false;
		if (bool1)
			bool2 = true;
		localCursor.close();
		localSQLiteDatabase.close();
		return bool2;
	}

	public String findName2(String paramString) {
		SQLiteDatabase localSQLiteDatabase = this.helper.getReadableDatabase();
		Cursor localCursor = localSQLiteDatabase.rawQuery(
				"select name from homemember where keycode=?",
				new String[] { paramString });
		boolean bool = localCursor.moveToNext();
		String str = null;
		if (bool)
			str = localCursor.getString(0);
		localCursor.close();
		localSQLiteDatabase.close();
		return str;
	}

	public List<HomeMemberInfo> findNoIntercept() {
		ArrayList localArrayList = new ArrayList();
		SQLiteDatabase localSQLiteDatabase = this.helper.getReadableDatabase();
		Cursor localCursor = localSQLiteDatabase
				.rawQuery(
						"select imguri,name,device,mode,keycode from homemember where mode=1 order by _id desc",
						null);
		if (!localCursor.moveToNext()) {
			localCursor.close();
			localSQLiteDatabase.close();
			return localArrayList;
		}
		localArrayList.add(cursorMember(localCursor));
		return localArrayList;
	}

	public List<HomeMemberInfo> findPart(int paramInt1, int paramInt2) {
		try {
			Thread.sleep(500L);
			ArrayList localArrayList = new ArrayList();
			SQLiteDatabase localSQLiteDatabase = this.helper
					.getReadableDatabase();
			String[] arrayOfString = new String[2];
			arrayOfString[0] = String.valueOf(paramInt2);
			arrayOfString[1] = String.valueOf(paramInt1);
			Cursor localCursor = localSQLiteDatabase
					.rawQuery(
							"select imguri,name,device,mode,keycode from homemember order by _id desc  limit ? offset ? ",
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
				HomeMemberInfo localHomeMemberInfo = new HomeMemberInfo();
				String str1 = localCursor.getString(0);
				String str2 = localCursor.getString(1);
				String str3 = localCursor.getString(2);
				String str4 = localCursor.getString(3);
				String str5 = localCursor.getString(4);
				localHomeMemberInfo.setImguri(str1);
				localHomeMemberInfo.setName(str2);
				localHomeMemberInfo.setDevice(str3);
				localHomeMemberInfo.setMode(str4);
				localHomeMemberInfo.setKeycode(str5);
				localArrayList.add(localHomeMemberInfo);
			}
		}
	}

	public void update(String paramString1, String paramString2,
			String paramString3, String paramString4, String paramString5) {
		SQLiteDatabase localSQLiteDatabase = this.helper.getWritableDatabase();
		ContentValues localContentValues = new ContentValues();
		localContentValues.put("imguri", paramString1);
		localContentValues.put("name", paramString2);
		localContentValues.put("device", paramString3);
		localContentValues.put("mode", paramString4);
		localSQLiteDatabase.update("homemember", localContentValues,
				"keycode=?", new String[] { paramString5 });
		localSQLiteDatabase.close();
	}
}

/*
 * Location:
 * C:\Users\Administrator\Desktop\小优\U03S源码\机器人本体.apk\classes_dex2jar.jar
 * Qualified Name: com.chinatel.robot.db.dao.HomeMemberDao JD-Core Version:
 * 0.6.2
 */