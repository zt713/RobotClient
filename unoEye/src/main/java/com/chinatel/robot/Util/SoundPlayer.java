package com.chinatel.robot.Util;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;

public class SoundPlayer {
	private static SoundPlayer single = null;
	private MediaPlayer mPlayer;

	public static SoundPlayer getInstance(Context paramContext,
			String paramString) {
		if (single == null)
			single = new SoundPlayer();
		if ((paramString != null) && (!paramString.isEmpty()))
			single.setup(paramContext, paramString);
		return single;
	}

	private void setup(Context paramContext, String paramString) {
		try {
			if (this.mPlayer == null)
				this.mPlayer = new MediaPlayer();
			this.mPlayer.reset();
			AssetFileDescriptor localAssetFileDescriptor = paramContext
					.getResources().getAssets().openFd(paramString);
			this.mPlayer.setDataSource(
					localAssetFileDescriptor.getFileDescriptor(),
					localAssetFileDescriptor.getStartOffset(),
					localAssetFileDescriptor.getLength());
			return;
		} catch (Exception localException) {
		}
	}

	public void pause() {
		if (this.mPlayer.isPlaying())
			this.mPlayer.pause();
	}

	public void play() {
		try {
			if (!this.mPlayer.isPlaying()) {
				this.mPlayer.prepare();
				this.mPlayer.start();
			}
			return;
		} catch (Exception localException) {
		}
	}

	public void release() {
		if (this.mPlayer != null) {
			this.mPlayer.release();
			this.mPlayer = null;
		}
	}

	public void setLooping(boolean paramBoolean) {
		this.mPlayer.setLooping(paramBoolean);
	}

	public void setOnCompletionListener(
			MediaPlayer.OnCompletionListener paramOnCompletionListener) {
		if (paramOnCompletionListener != null)
			this.mPlayer.setOnCompletionListener(paramOnCompletionListener);
	}

	public void stop() {
		if (this.mPlayer.isPlaying()) {
			this.mPlayer.stop();
			this.mPlayer.seekTo(0);
		}
	}
}

/*
 * Location:
 * C:\Users\Administrator\Desktop\小优\U03S源码\机器人本体.apk\classes_dex2jar.jar
 * Qualified Name: com.chinatel.robot.Util.SoundPlayer JD-Core Version: 0.6.2
 */