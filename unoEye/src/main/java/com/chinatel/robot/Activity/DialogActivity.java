package com.chinatel.robot.Activity;

import rtc.sdk.common.RtcConst;
import rtc.sdk.core.RtcRules;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.chinatel.robot.R;
import com.chinatel.robot.application.MyApplication;
import com.chinatel.robot.db.dao.HomeMemberDao;

public class DialogActivity extends Activity implements View.OnClickListener {
	private Button bt_cancel;
	private Button bt_sure;
	private String keyCode;
	private String name;
	private TextView tv_connect_request_info;

	public void onClick(View paramView) {
		switch (paramView.getId()) {
		default:
			return;
		case R.id.bt_connect_request_sure:
			if (MyApplication.getInstance().mConnect != null)
				MyApplication.getInstance().mConnect
						.accept(RtcConst.CallType_A_V);
			while (true) {
				Intent localIntent = new Intent(this, HeaderSetActivity.class);
				localIntent.putExtra("keycode", this.keyCode);
				localIntent.putExtra("device", this.name);
				startActivity(localIntent);
				finish();
				Toast.makeText(this, "建立连接失败", 0).show();
				return;
			}
		case R.id.bt_connect_request_cancel:
		}
		for (int i = 0;; i++) {
			if (i >= 4)
				;
			while (true) {
				String str = RtcRules.UserToRemoteUri_new(this.keyCode,
						RtcConst.UEType_Any);
				MyApplication.getInstance().mAcc.sendIm(str, "text/cmd",
						"呃~机器人未允许您\n的连接请求");
				new HomeMemberDao(this).add("", "陌生人", this.name, "0",
						this.keyCode);
				finish();
				if ((MyApplication.getInstance().cons == null)
						|| (!MyApplication.getInstance().cons.info().contains(
								this.keyCode)))
					break;
				MyApplication.getInstance().cons.disconnect();
				MyApplication.getInstance().mMListener.onDisconnected(603);
			}
		}
	}

	protected void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);
		setContentView(R.layout.activity_dialog);
		this.bt_sure = ((Button) findViewById(R.id.bt_connect_request_sure));
		this.bt_cancel = ((Button) findViewById(R.id.bt_connect_request_cancel));
		this.tv_connect_request_info = ((TextView) findViewById(R.id.tv_connect_request_info));
		this.bt_sure.setOnClickListener(this);
		this.bt_cancel.setOnClickListener(this);
		Intent localIntent = getIntent();
		this.keyCode = localIntent.getStringExtra("keycode");
		this.name = localIntent.getStringExtra("device");
		String str = "是否允许移动设备(" + this.name + ")连接使用机器人小优?";
		this.tv_connect_request_info.setText(str);
	}
}
