package com.chinatel.robot.Activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import com.chinatel.robot.View.ClipImageLayout;
import java.io.ByteArrayOutputStream;

public class HeaderClipActivity extends Activity {
	private ClipImageLayout mClipImageLayout;

	public void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);
		setContentView(2130903043);
		this.mClipImageLayout = ((ClipImageLayout) findViewById(2131361808));
		Uri localUri = (Uri) getIntent().getExtras().get("uri");
		if (localUri != null)
			this.mClipImageLayout.setZoomImage(localUri);
		findViewById(2131361805).setOnClickListener(new View.OnClickListener() {
			public void onClick(View paramAnonymousView) {
				HeaderClipActivity.this.finish();
			}
		});
		findViewById(2131361807).setOnClickListener(new View.OnClickListener() {
			public void onClick(View paramAnonymousView) {
				if (HeaderClipActivity.this.mClipImageLayout != null) {
					Bitmap localBitmap = HeaderClipActivity.this.mClipImageLayout
							.clip();
					ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
					localBitmap.compress(Bitmap.CompressFormat.JPEG, 100,
							localByteArrayOutputStream);
					byte[] arrayOfByte = localByteArrayOutputStream
							.toByteArray();
					Intent localIntent = new Intent();
					localIntent.putExtra("bitmap", arrayOfByte);
					HeaderClipActivity.this.setResult(-1, localIntent);
					HeaderClipActivity.this.finish();
				}
			}
		});
	}
}

/*
 * Location:
 * C:\Users\Administrator\Desktop\小优\U03S源码\机器人本体.apk\classes_dex2jar.jar
 * Qualified Name: com.chinatel.robot.Activity.HeaderClipActivity JD-Core
 * Version: 0.6.2
 */