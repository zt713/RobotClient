package com.chinatel.robot;

import java.util.List;

import android.test.AndroidTestCase;
import android.util.Log;

import com.chinatel.robot.bean.HomeMemberInfo;
import com.chinatel.robot.db.dao.HomeMemberDao;

public class RTCTest extends AndroidTestCase {
	private static final String TAG = "DBTest";

	private HomeMemberDao dao;

	public void testFetchMember() {
		this.dao = new HomeMemberDao(getContext());
		List<HomeMemberInfo> members = dao.findNoIntercept();
		// List<HomeMemberInfo> members = dao.findAll();
		for (HomeMemberInfo member : members) {
			Log.e(TAG,
					"=================" + member.getName() + ""
							+ member.getKeycode());
		}
	}

	public void testAddMember() {
		this.dao = new HomeMemberDao(getContext());
		/*
		 * localHomeMemberInfo.setImguri(str1);
		 * localHomeMemberInfo.setName(str2);
		 * localHomeMemberInfo.setDevice(str3);
		 * localHomeMemberInfo.setMode(str4);
		 * localHomeMemberInfo.setKeycode(str5);
		 */
		dao.add("", "papa", "IPHONE8S 土豪金", "1", "1002");

	}

}
