package com.chinatel.robot.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import com.chinatel.robot.voice.VoiceRecognition;

public class VoiceRecognitionActivity extends Activity {
	public void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);
		String str = getIntent().getStringExtra("key");
		int i = new VoiceRecognition(this).voiceRecognitionCall(str);
		if (i == 0)
			Toast.makeText(this, "没有找到所呼叫的成员", 0).show();
		while (true) {
			finish();
			// return;
			if (i == 1)
				Toast.makeText(this, "呼叫成功", 0).show();
		}
	}
}

/*
 * Location:
 * C:\Users\Administrator\Desktop\小优\U03S源码\机器人本体.apk\classes_dex2jar.jar
 * Qualified Name: com.chinatel.robot.Activity.VoiceRecognitionActivity JD-Core
 * Version: 0.6.2
 */