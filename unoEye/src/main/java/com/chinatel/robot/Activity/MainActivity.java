package com.chinatel.robot.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.Toast;
import com.chinatel.robot.Util.SystemUtil;
import com.chinatel.robot.Util.ViewUtil;
import com.chinatel.robot.voice.VoiceRecognition;

public class MainActivity extends Activity {
	private Context context;
	private RelativeLayout rlayout;

	public void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);
		finish();
		setContentView(2130903045);
		this.context = this;
		SystemUtil.outDeviceInfo(this);
		this.rlayout = ((RelativeLayout) findViewById(2131361812));
		Button localButton1 = new Button(this);
		RelativeLayout.LayoutParams localLayoutParams1 = new RelativeLayout.LayoutParams(
				new RelativeLayout.LayoutParams(-2, -2));
		localLayoutParams1.leftMargin = ViewUtil.dipToPixel(this, 262);
		localLayoutParams1.topMargin = ViewUtil.dipToPixel(this, 52);
		localButton1.setLayoutParams(localLayoutParams1);
		localButton1.setBackground(getResources().getDrawable(2130837522));
		this.rlayout.addView(localButton1);
		localButton1.setVisibility(8);
		View.OnClickListener local1 = new View.OnClickListener() {
			public void onClick(View paramAnonymousView) {
				Intent localIntent = new Intent(MainActivity.this,
						EyesActivity.class);
				MainActivity.this.startActivity(localIntent);
			}
		};
		localButton1.setOnClickListener(local1);
		Button localButton2 = (Button) findViewById(2131361815);
		View.OnClickListener local2 = new View.OnClickListener() {
			public void onClick(View paramAnonymousView) {
				int i = new VoiceRecognition(MainActivity.this)
						.voiceRecognitionCall("我要找爸爸");
				if (i == 0)
					Toast.makeText(MainActivity.this.context, "没有找到所呼叫的成员", 0)
							.show();
				while (i != 1)
					return;
				Toast.makeText(MainActivity.this.context, "呼叫成功", 0).show();
			}
		};
		localButton2.setOnClickListener(local2);
		Button localButton3 = new Button(this);
		RelativeLayout.LayoutParams localLayoutParams2 = new RelativeLayout.LayoutParams(
				new RelativeLayout.LayoutParams(-2, -2));
		localLayoutParams2.leftMargin = ViewUtil.dipToPixel(this, 631);
		localLayoutParams2.topMargin = ViewUtil.dipToPixel(this, 56);
		localButton3.setLayoutParams(localLayoutParams2);
		localButton3.setBackground(getResources().getDrawable(2130837530));
		this.rlayout.addView(localButton3);
		Button localButton4 = new Button(this);
		RelativeLayout.LayoutParams localLayoutParams3 = new RelativeLayout.LayoutParams(
				new RelativeLayout.LayoutParams(-2, -2));
		localLayoutParams3.leftMargin = ViewUtil.dipToPixel(this, 185);
		localLayoutParams3.topMargin = ViewUtil.dipToPixel(this, 210);
		localButton4.setLayoutParams(localLayoutParams3);
		localButton4.setBackground(getResources().getDrawable(2130837532));
		this.rlayout.addView(localButton4);
		Button localButton5 = new Button(this);
		RelativeLayout.LayoutParams localLayoutParams4 = new RelativeLayout.LayoutParams(
				new RelativeLayout.LayoutParams(-2, -2));
		localLayoutParams4.leftMargin = ViewUtil.dipToPixel(this, 699);
		localLayoutParams4.topMargin = ViewUtil.dipToPixel(this, 210);
		localButton5.setLayoutParams(localLayoutParams4);
		localButton5.setBackground(getResources().getDrawable(2130837534));
		this.rlayout.addView(localButton5);
		Button localButton6 = new Button(this);
		RelativeLayout.LayoutParams localLayoutParams5 = new RelativeLayout.LayoutParams(
				new RelativeLayout.LayoutParams(-2, -2));
		localLayoutParams5.leftMargin = ViewUtil.dipToPixel(this, 266);
		localLayoutParams5.topMargin = ViewUtil.dipToPixel(this, 385);
		localButton6.setLayoutParams(localLayoutParams5);
		localButton6.setBackground(getResources().getDrawable(2130837528));
		this.rlayout.addView(localButton6);
		Button localButton7 = new Button(this);
		RelativeLayout.LayoutParams localLayoutParams6 = new RelativeLayout.LayoutParams(
				new RelativeLayout.LayoutParams(-2, -2));
		localLayoutParams6.leftMargin = ViewUtil.dipToPixel(this, 631);
		localLayoutParams6.topMargin = ViewUtil.dipToPixel(this, 389);
		localButton7.setLayoutParams(localLayoutParams6);
		localButton7.setBackground(getResources().getDrawable(2130837524));
		this.rlayout.addView(localButton7);
		Button localButton8 = new Button(this);
		RelativeLayout.LayoutParams localLayoutParams7 = new RelativeLayout.LayoutParams(
				new RelativeLayout.LayoutParams(-2, -2));
		localLayoutParams7.leftMargin = ViewUtil.dipToPixel(this, 59);
		localLayoutParams7.topMargin = ViewUtil.dipToPixel(this, 475);
		localButton8.setLayoutParams(localLayoutParams7);
		localButton8.setBackground(getResources().getDrawable(2130837614));
		this.rlayout.addView(localButton8);
		View localView1 = findViewById(2131361813);
		View.OnClickListener local3 = new View.OnClickListener() {
			public void onClick(View paramAnonymousView) {
				Intent localIntent = new Intent(MainActivity.this,
						MemberManageActivity.class);
				MainActivity.this.startActivity(localIntent);
			}
		};
		localView1.setOnClickListener(local3);
		View localView2 = findViewById(2131361814);
		View.OnClickListener local4 = new View.OnClickListener() {
			public void onClick(View paramAnonymousView) {
				Intent localIntent = new Intent(MainActivity.this,
						EyesActivity.class);
				MainActivity.this.startActivity(localIntent);
			}
		};
		localView2.setOnClickListener(local4);
	}

	protected void onDestroy() {
		super.onDestroy();
	}
}

/*
 * Location:
 * C:\Users\Administrator\Desktop\小优\U03S源码\机器人本体.apk\classes_dex2jar.jar
 * Qualified Name: com.chinatel.robot.Activity.MainActivity JD-Core Version:
 * 0.6.2
 */