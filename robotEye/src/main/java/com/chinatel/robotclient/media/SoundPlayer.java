package com.chinatel.robotclient.media;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.media.MediaPlayer;

public class SoundPlayer
{
  private static SoundPlayer single = null;
  private MediaPlayer mPlayer;

  public static SoundPlayer getInstance(Context paramContext, String paramString)
  {
    if (single == null)
      single = new SoundPlayer();
    single.setup(paramContext, paramString);
    return single;
  }

  private void setup(Context paramContext, String paramString)
  {
    try
    {
      if (this.mPlayer == null)
        this.mPlayer = new MediaPlayer();
      this.mPlayer.reset();
      AssetFileDescriptor localAssetFileDescriptor = paramContext.getResources().getAssets().openFd(paramString);
      this.mPlayer.setDataSource(localAssetFileDescriptor.getFileDescriptor(), localAssetFileDescriptor.getStartOffset(), localAssetFileDescriptor.getLength());
      return;
    }
    catch (Exception localException)
    {
    }
  }

  public void play()
  {
    try
    {
      if ((this.mPlayer != null) && (!this.mPlayer.isPlaying()))
      {
        this.mPlayer.prepare();
        this.mPlayer.start();
      }
      return;
    }
    catch (Exception localException)
    {
    }
  }

  public void setLooping(boolean paramBoolean)
  {
    if (this.mPlayer != null)
      this.mPlayer.setLooping(paramBoolean);
  }

  public void stop()
  {
    if ((this.mPlayer != null) && (this.mPlayer.isPlaying()))
      this.mPlayer.stop();
    if (this.mPlayer != null)
      this.mPlayer.release();
    this.mPlayer = null;
  }
}

/* Location:           C:\Users\Administrator\Desktop\test111\classes_dex2jar.jar
 * Qualified Name:     com.chinatel.robotclient.media.SoundPlayer
 * JD-Core Version:    0.6.2
 */