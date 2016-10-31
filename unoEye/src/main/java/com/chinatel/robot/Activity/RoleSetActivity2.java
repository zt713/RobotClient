package com.chinatel.robot.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.chinatel.robot.Util.CharUtil;
import com.chinatel.robot.Util.SoundPlayer;
import com.chinatel.robot.application.MyApplication;
import com.chinatel.robot.db.dao.HomeMemberDao;

public class RoleSetActivity2 extends Activity {
	private Button bt_finish;
	private HomeMemberDao dao;
	private String device;
	private String imguri;
	private String keycode;
	private String mode;
	private String name;
	private EditText role_edit;

	public void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);
		setContentView(2130903047);
		MyApplication.getInstance().addActivity(this);
		this.dao = new HomeMemberDao(this);
		SoundPlayer.getInstance(getApplicationContext(), "qly021.mp3").play();
		this.role_edit = ((EditText) findViewById(2131361829));
		this.bt_finish = ((Button) findViewById(2131361807));
		this.keycode = getIntent().getStringExtra("keycode");
		this.device = getIntent().getStringExtra("device");
		this.imguri = getIntent().getStringExtra("imguri");
		this.mode = getIntent().getStringExtra("mode");
		this.name = getIntent().getStringExtra("name2");
		this.role_edit.setText(this.name);
		this.role_edit.setSelection(this.name.length());
		this.mode = "1";
		findViewById(2131361805).setOnClickListener(new View.OnClickListener() {
			public void onClick(View paramAnonymousView) {
				RoleSetActivity2.this.finish();
			}
		});
		this.bt_finish.setOnClickListener(new View.OnClickListener() {
			public void onClick(View paramAnonymousView) {
				RoleSetActivity2.this.name = RoleSetActivity2.this.role_edit
						.getText().toString();
				if (RoleSetActivity2.this.name.length() < 1) {
					Toast localToast2 = Toast.makeText(RoleSetActivity2.this,
							"角色名不能为空", 0);
					localToast2.setGravity(17, 0, 0);
					localToast2.show();
					return;
				}
				if (RoleSetActivity2.this.dao.findName2(
						RoleSetActivity2.this.keycode).equals(
						RoleSetActivity2.this.name)) {
					RoleSetActivity2.this.finish();
					return;
				}
				if (RoleSetActivity2.this.dao
						.findName(RoleSetActivity2.this.name)) {
					Toast localToast1 = Toast.makeText(RoleSetActivity2.this,
							"该角色名称已存在,请重新输入", 0);
					localToast1.setGravity(17, 0, 0);
					localToast1.show();
					return;
				}
				RoleSetActivity2.this.dao.update(RoleSetActivity2.this.imguri,
						RoleSetActivity2.this.name,
						RoleSetActivity2.this.device,
						RoleSetActivity2.this.mode,
						RoleSetActivity2.this.keycode);
				if ("爸爸".equals(RoleSetActivity2.this.name))
					SoundPlayer.getInstance(
							RoleSetActivity2.this.getApplicationContext(),
							"qly022.mp3").play();
				while (true) {
					RoleSetActivity2.this.finish();
					if ("妈妈".equals(RoleSetActivity2.this.name))
						SoundPlayer.getInstance(
								RoleSetActivity2.this.getApplicationContext(),
								"qly023.mp3").play();
					else
						SoundPlayer.getInstance(
								RoleSetActivity2.this.getApplicationContext(),
								"qly024.mp3").play();
				}
			}
		});
		this.role_edit.addTextChangedListener(new TextWatcher() {
			public void afterTextChanged(Editable paramAnonymousEditable) {
				Log.i("robot",
						"afterTextChanged:" + paramAnonymousEditable.toString());
				if (paramAnonymousEditable.length() > 5) {
					String str = paramAnonymousEditable.toString().substring(0,
							5);
					RoleSetActivity2.this.role_edit.setText(str);
					RoleSetActivity2.this.role_edit.setSelection(str.length());
					Toast localToast = Toast.makeText(RoleSetActivity2.this,
							"请输入五个汉字以内的角色名称", 0);
					localToast.setGravity(17, 0, 0);
					localToast.show();
				}
			}

			public void beforeTextChanged(
					CharSequence paramAnonymousCharSequence,
					int paramAnonymousInt1, int paramAnonymousInt2,
					int paramAnonymousInt3) {
				Log.i("robot", "beforeTextChanged:"
						+ paramAnonymousCharSequence.toString());
			}

			public void onTextChanged(CharSequence paramAnonymousCharSequence,
					int paramAnonymousInt1, int paramAnonymousInt2,
					int paramAnonymousInt3) {
				String str = paramAnonymousCharSequence.toString().substring(
						paramAnonymousInt1);
				Log.i("robot",
						"onTextChanged:"
								+ paramAnonymousCharSequence.toString()
								+ " start:" + paramAnonymousInt1
								+ "  lastChar:" + str);
				if ((str != null) && (!"".equals(str))
						&& (!CharUtil.isChinese(str))) {
					CharSequence localCharSequence = paramAnonymousCharSequence
							.subSequence(0, paramAnonymousInt1);
					RoleSetActivity2.this.role_edit.setText(localCharSequence);
					RoleSetActivity2.this.role_edit
							.setSelection(localCharSequence.length());
					Toast localToast = Toast.makeText(RoleSetActivity2.this,
							"请输入汉字", 0);
					localToast.setGravity(17, 0, 0);
					localToast.show();
				}
			}
		});
	}
}

/*
 * Location:
 * C:\Users\Administrator\Desktop\小优\U03S源码\机器人本体.apk\classes_dex2jar.jar
 * Qualified Name: com.chinatel.robot.Activity.RoleSetActivity2 JD-Core Version:
 * 0.6.2
 */