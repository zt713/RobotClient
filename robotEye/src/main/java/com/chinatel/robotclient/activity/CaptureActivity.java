package com.chinatel.robotclient.activity;

import java.io.IOException;

import android.app.Activity;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Toast;

import com.chinatel.robotclient.camera.CameraManager;
import com.chinatel.robotclient.camera.ViewfinderView;
import com.google.zxing.Result;

public class CaptureActivity extends Activity
  implements SurfaceHolder.Callback
{
  private static final float BEEP_VOLUME = 0.1F;
  private static final long VIBRATE_DURATION = 200L;
private static final ViewfinderView viewfinderView = null;
  private final MediaPlayer.OnCompletionListener beepListener = new MediaPlayer.OnCompletionListener()
  {
    public void onCompletion(MediaPlayer paramAnonymousMediaPlayer)
    {
      paramAnonymousMediaPlayer.seekTo(0);
    }
  };
  private String characterSet;
//  private Vector<BarcodeFormat> decodeFormats;
//  private CaptureActivityHandler handler;
  private boolean hasSurface;
//  private InactivityTimer inactivityTimer;
  private MediaPlayer mediaPlayer;
  private boolean playBeep;
  private SurfaceView surfaceView;
  private boolean vibrate;
//  private ViewfinderView viewfinderView;
private Handler handler;
private Object inactivityTimer;

  private void initBeepSound()
  {
    AssetFileDescriptor localAssetFileDescriptor;
    if ((this.playBeep) && (this.mediaPlayer == null))
    {
      setVolumeControlStream(3);
      this.mediaPlayer = new MediaPlayer();
      this.mediaPlayer.setAudioStreamType(3);
      this.mediaPlayer.setOnCompletionListener(this.beepListener);
      localAssetFileDescriptor = getResources().openRawResourceFd(2131034112);
    }
    try
    {
//      this.mediaPlayer.setDataSource(localAssetFileDescriptor.getFileDescriptor(), localAssetFileDescriptor.getStartOffset(), localAssetFileDescriptor.getLength());
//      localAssetFileDescriptor.close();
      this.mediaPlayer.setVolume(0.1F, 0.1F);
      this.mediaPlayer.prepare();
      return;
    }
    catch (IOException localIOException)
    {
      this.mediaPlayer = null;
    }
  }

  private void initCamera(SurfaceHolder paramSurfaceHolder)
  {
    try
    {
      CameraManager.get().openDriver(paramSurfaceHolder);
//      if (this.handler == null)
//        this.handler = new CaptureActivityHandler(this, this.decodeFormats, this.characterSet);
      return;
    }
    catch (IOException localIOException)
    {
    }
    catch (RuntimeException localRuntimeException)
    {
    }
  }

  private void playBeepSoundAndVibrate()
  {
    if ((this.playBeep) && (this.mediaPlayer != null))
      this.mediaPlayer.start();
    if (this.vibrate)
      ((Vibrator)getSystemService("vibrator")).vibrate(200L);
  }

  public void drawViewfinder()
  {
    this.viewfinderView.drawViewfinder();
  }

  public Handler getHandler()
  {
    return this.handler;
  }

  public ViewfinderView getViewfinderView()
  {
    return this.viewfinderView;
  }

  public void handleDecode(Result paramResult, Bitmap paramBitmap)
  {
//    this.inactivityTimer.onActivity();
    playBeepSoundAndVibrate();
    String str1 = paramResult.getText();
    Log.e("str", str1);
    if ((!str1.contains("\"ad\":\"")) || (!str1.contains("\",\"")))
    {
      Toast.makeText(this, "二维码不匹配", 0).show();
      return;
    }
    String str2 = str1.substring(6 + str1.lastIndexOf("\"ad\":\""), str1.lastIndexOf("\",\"")).replace("-", "");
    String str3 = str2.substring(0, -4 + str2.length());
    Log.e("str", str3);
//    Intent localIntent = new Intent(this, ManualConnectActivity.class);
//    localIntent.putExtra("erwei_result", str3);
//    startActivity(localIntent);
    finish();
  }

  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(2130903045);
    CameraManager.init(getApplication());
//    this.viewfinderView = ((ViewfinderView)findViewById(2131230770));
    this.hasSurface = false;
    this.surfaceView = ((SurfaceView)findViewById(2131230769));
  }

  protected void onDestroy()
  {
//    this.inactivityTimer.shutdown();
    super.onDestroy();
  }

  protected void onPause()
  {
    super.onPause();
    if (this.handler != null)
    {
//      this.handler.quitSynchronously();
      this.handler = null;
    }
    CameraManager.get().closeDriver();
  }

  protected void onResume()
  {
    super.onResume();
//    this.inactivityTimer = new InactivityTimer(this);
    SurfaceHolder localSurfaceHolder = this.surfaceView.getHolder();
    if (this.hasSurface)
      initCamera(localSurfaceHolder);
    while (true)
    {
//      this.decodeFormats = null;
      this.characterSet = null;
      this.playBeep = true;
      if (((AudioManager)getSystemService("audio")).getRingerMode() != 2)
        this.playBeep = false;
      initBeepSound();
      this.vibrate = true;
      localSurfaceHolder.addCallback(this);
      localSurfaceHolder.setType(3);
    }
  }

  public void onScanning(View paramView)
  {
//    startActivity(new Intent(this, ManualConnectActivity.class));
//    finish();
  }

  public void surfaceChanged(SurfaceHolder paramSurfaceHolder, int paramInt1, int paramInt2, int paramInt3)
  {
  }

  public void surfaceCreated(SurfaceHolder paramSurfaceHolder)
  {
    if (!this.hasSurface)
    {
      this.hasSurface = true;
      initCamera(paramSurfaceHolder);
    }
  }

  public void surfaceDestroyed(SurfaceHolder paramSurfaceHolder)
  {
    this.hasSurface = false;
  }
}

/* Location:           C:\Users\Administrator\Desktop\test111\classes_dex2jar.jar
 * Qualified Name:     com.chinatel.robotclient.activity.CaptureActivity
 * JD-Core Version:    0.6.2
 */