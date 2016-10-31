package com.chinatel.robot.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.chinatel.robot.R;
import com.chinatel.robot.Util.CharUtil;
import com.chinatel.robot.Util.SoundPlayer;
import com.chinatel.robot.application.MyApplication;
import com.chinatel.robot.db.dao.HomeMemberDao;

public class RoleSetActivity extends Activity {
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
		setContentView(R.layout.activity_role_set);
		MyApplication.getInstance().addActivity(this);
		this.dao = new HomeMemberDao(this);
		SoundPlayer.getInstance(getApplicationContext(), "qly021.mp3").play();
		this.role_edit = ((EditText) findViewById(R.id.role_edit));
		this.bt_finish = ((Button) findViewById(R.id.finishBtn));
		this.keycode = getIntent().getStringExtra("keycode");
		this.device = getIntent().getStringExtra("device");
		this.imguri = getIntent().getStringExtra("imguri");
		this.mode = "1";
		findViewById(R.id.backBtn).setOnClickListener(new View.OnClickListener() {
			public void onClick(View paramAnonymousView) {
				RoleSetActivity.this.finish();
			}
		});
		this.bt_finish.setOnClickListener(new View.OnClickListener() {
			public void onClick(View paramAnonymousView) {
				RoleSetActivity.this.name = RoleSetActivity.this.role_edit
						.getText().toString();
				if (RoleSetActivity.this.name.length() < 1) {
					Toast localToast2 = Toast.makeText(RoleSetActivity.this,
							"角色名不能为空",Toast.LENGTH_SHORT);
					localToast2.setGravity(17, 0, 0);
					localToast2.show();
					return;
				}
				if (RoleSetActivity.this.dao
						.findName(RoleSetActivity.this.name)) {
					Toast localToast1 = Toast.makeText(RoleSetActivity.this,
							"该角色名称已存在,请重新输入",Toast.LENGTH_SHORT);
					localToast1.setGravity(17, 0, 0);
					localToast1.show();
					return;
				}
				RoleSetActivity.this.dao
						.add(RoleSetActivity.this.imguri,
								RoleSetActivity.this.name,
								RoleSetActivity.this.device,
								RoleSetActivity.this.mode,
								RoleSetActivity.this.keycode);
				if ("爸爸".equals(RoleSetActivity.this.name))
					SoundPlayer.getInstance(
							RoleSetActivity.this.getApplicationContext(),
							"qly022.mp3").play();
				while (true) {
					MyApplication.getInstance().exit();
					Intent localIntent = new Intent(RoleSetActivity.this,
							MemberManageActivity.class);
					RoleSetActivity.this.startActivity(localIntent);
					if ("妈妈".equals(RoleSetActivity.this.name))
						SoundPlayer.getInstance(
								RoleSetActivity.this.getApplicationContext(),
								"qly023.mp3").play();
					else
						SoundPlayer.getInstance(
								RoleSetActivity.this.getApplicationContext(),
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
					RoleSetActivity.this.role_edit.setText(str);
					RoleSetActivity.this.role_edit.setSelection(str.length());
					Toast localToast = Toast.makeText(RoleSetActivity.this,
							"请输入五个汉字以内的角色名称", Toast.LENGTH_SHORT);
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
				Log.i("robot",
						"onTextChanged:"
								+ paramAnonymousCharSequence.toString());
				String str = paramAnonymousCharSequence.toString().substring(
						paramAnonymousInt1);
				Log.i("robot",
						"onTextChanged:"
								+ paramAnonymousCharSequence.toString()
								+ " start:" + paramAnonymousInt1
								+ "  lastChar:" + str);
				/*if ((str != null) && (!"".equals(str))
						&& (!CharUtil.isChinese(str))) {
					CharSequence localCharSequence = paramAnonymousCharSequence
							.subSequence(0, paramAnonymousInt1);
					RoleSetActivity.this.role_edit.setText(localCharSequence);
					RoleSetActivity.this.role_edit
							.setSelection(localCharSequence.length());
					Toast localToast = Toast.makeText(RoleSetActivity.this,
							"请输入汉字",Toast.LENGTH_SHORT);
					localToast.setGravity(17, 0, 0);
					localToast.show();
				}*/
			}
		});
	}
}

/*
 * Location:
 * C:\Users\Administrator\Desktop\小优\U03S源码\机器人本体.apk\classes_dex2jar.jar
 * Qualified Name: com.chinatel.robot.Activity.RoleSetActivity JD-Core Version:
 * 0.6.2
 */