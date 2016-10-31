package com.chinatel.robot.voice;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;
import com.chinatel.robot.Activity.SpeakingBeforeActivity;
import com.chinatel.robot.application.MyApplication;
import com.chinatel.robot.bean.HomeMemberInfo;
import com.chinatel.robot.db.dao.HomeMemberDao;
import java.util.Iterator;
import java.util.List;
import rtc.sdk.iface.Connection;
import rtc.sdk.iface.ConnectionListener;

public class VoiceRecognition {
	private HomeMemberDao dao;
	private List<HomeMemberInfo> list;
	private Context mContext;

	public VoiceRecognition(Context paramContext) {
		this.mContext = paramContext;
		this.dao = new HomeMemberDao(paramContext);
		setup();
	}

	private int call(String paramString) {
		if ((MyApplication.getInstance().cons == null)
				|| (!MyApplication.getInstance().cons.info().contains(
						paramString))) {
			MyApplication.getInstance().cons.disconnect();
			MyApplication.getInstance().mMListener.onDisconnected(200);
			return 0;
		} else {
			Intent localIntent = new Intent(this.mContext,
					SpeakingBeforeActivity.class);
			localIntent.putExtra("keyCode", paramString);
			this.mContext.startActivity(localIntent);
			Log.i("robot", "跳转到呼叫界面");
			return 1;
		}

	}

	public void setup() {
		this.list = this.dao.findAll();
	}

	public int voiceRecognitionCall(String paramString) {
		if (paramString == null) {
			Toast.makeText(this.mContext, "未指定成员，呼叫失败！", 0).show();
			return -1;
		}
		if (MyApplication.getInstance().mClt == null) {
			Toast.makeText(this.mContext, "千里眼尚未启动！\n开始启动千里眼...", 0).show();
			MyApplication.getInstance().openWifiAp();
			return -1;
		}
		if (!MyApplication.getInstance().isAvailable) {
			Toast.makeText(this.mContext, "千里眼暂时不可用！\n请检查网络连接...", 0).show();
			return -1;
		}
		Iterator localIterator = this.list.iterator();
		HomeMemberInfo localHomeMemberInfo;
		String str1;
		do {
			if (!localIterator.hasNext())
				return 0;
			localHomeMemberInfo = (HomeMemberInfo) localIterator.next();
			str1 = localHomeMemberInfo.getName();
		} while ((!paramString.contains(str1)) && (!str1.contains(paramString)));
		String str2 = localHomeMemberInfo.getKeycode();
		Log.i("robot", "匹配到用户，用户id：" + str2);
		return call(str2);
	}
}

/*
 * Location:
 * C:\Users\Administrator\Desktop\小优\U03S源码\机器人本体.apk\classes_dex2jar.jar
 * Qualified Name: com.chinatel.robot.voice.VoiceRecognition JD-Core Version:
 * 0.6.2
 */