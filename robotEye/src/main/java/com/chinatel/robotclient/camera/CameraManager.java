package com.chinatel.robotclient.camera;

import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.hardware.Camera;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Handler;
import android.util.Log;
import android.view.SurfaceHolder;
import cn.wangjianlog.baseframework.MainApplication;
import java.io.IOException;

public final class CameraManager
{
  private static final int MAX_FRAME_HEIGHT = 314;
  private static final int MAX_FRAME_WIDTH = 314;
  private static final int MIN_FRAME_HEIGHT = 314;
  private static final int MIN_FRAME_WIDTH = 314;
  private static final String TAG = CameraManager.class.getSimpleName();
public static int SDK_INT = 0;
  private static CameraManager cameraManager;
  private final AutoFocusCallback autoFocusCallback;
  private Camera camera;
  private final CameraConfigurationManager configManager;
  private final Context context;
  private Rect framingRect;
  private Rect framingRectInPreview;
  private boolean initialized;
  private final PreviewCallback previewCallback;
  private boolean previewing;
  private final boolean useOneShotPreviewCallback;

  static
  {
    int i;
	try
    {
      int j = Integer.parseInt(Build.VERSION.SDK);
      i = j;
      SDK_INT = i;
    }
    catch (NumberFormatException localNumberFormatException)
    {
    }
  }

  private CameraManager(Context paramContext)
  {
    this.context = paramContext;
    this.configManager = new CameraConfigurationManager(paramContext);
    if (Integer.parseInt(Build.VERSION.SDK) > 3);
    for (boolean bool = true; ; bool = false)
    {
      this.useOneShotPreviewCallback = bool;
      this.previewCallback = new PreviewCallback(this.configManager, this.useOneShotPreviewCallback);
      this.autoFocusCallback = new AutoFocusCallback();
      return;
    }
  }

  public static CameraManager get()
  {
    return cameraManager;
  }

  public static void init(Context paramContext)
  {
    if (cameraManager == null)
      cameraManager = new CameraManager(paramContext);
  }

  public PlanarYUVLuminanceSource buildLuminanceSource(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    Rect localRect = getFramingRectInPreview();
    int i = this.configManager.getPreviewFormat();
    String str = this.configManager.getPreviewFormatString();
    switch (i)
    {
    default:
      if ("yuv420p".equals(str))
        return new PlanarYUVLuminanceSource(paramArrayOfByte, paramInt1, paramInt2, localRect.left, localRect.top, localRect.width(), localRect.height());
      break;
    case 16:
    case 17:
      return new PlanarYUVLuminanceSource(paramArrayOfByte, paramInt1, paramInt2, localRect.left, localRect.top, localRect.width(), localRect.height());
    }
    throw new IllegalArgumentException("Unsupported picture format: " + i + '/' + str);
  }

  public void closeDriver()
  {
    if (this.camera != null)
    {
      FlashlightManager.disableFlashlight();
      this.camera.release();
      this.camera = null;
    }
  }

  public Rect getFramingRect()
  {
    float f = MainApplication.getDensity();
    Point localPoint = this.configManager.getScreenResolution();
    int i = 0;
    int j = 0;
    if (this.framingRect == null)
    {
      if (this.camera == null)
        return null;
      i = 3 * localPoint.x / 4;
      if (i >= 314)
      i = (int)(157.0F * f);
      j = 3 * localPoint.y / 4;
      if (j >= 314)
      j = (int)(157.0F * f);
    }
    while (true)
    {
      int k = (localPoint.x - i) / 2;
      int m = (int)((localPoint.y - j) / 2 - 100.0F * f);
      this.framingRect = new Rect(k, m, k + i, j + m);
      Log.d(TAG, "Calculated framing rect: " + this.framingRect);
      return this.framingRect;
    }
  }

  public Rect getFramingRectInPreview()
  {
    if (this.framingRectInPreview == null)
    {
      Rect localRect = new Rect(getFramingRect());
      Point localPoint1 = this.configManager.getCameraResolution();
      Point localPoint2 = this.configManager.getScreenResolution();
      localRect.left = (localRect.left * localPoint1.y / localPoint2.x);
      localRect.right = (localRect.right * localPoint1.y / localPoint2.x);
      localRect.top = (localRect.top * localPoint1.x / localPoint2.y);
      localRect.bottom = (localRect.bottom * localPoint1.x / localPoint2.y);
      this.framingRectInPreview = localRect;
    }
    return this.framingRectInPreview;
  }

  public void openDriver(SurfaceHolder paramSurfaceHolder)
    throws IOException
  {
    if (this.camera == null);
    try
    {
      Log.e(TAG, "open ");
      this.camera = Camera.open();
      this.camera.setPreviewDisplay(paramSurfaceHolder);
      if (!this.initialized)
      {
        this.initialized = true;
        this.configManager.initFromCameraParameters(this.camera);
      }
      this.configManager.setDesiredCameraParameters(this.camera);
      FlashlightManager.enableFlashlight();
      return;
    }
    catch (Exception localException)
    {
      Log.e(TAG, "open  error");
    }
  }

  public void requestAutoFocus(Handler paramHandler, int paramInt)
  {
    if ((this.camera != null) && (this.previewing))
    {
      this.autoFocusCallback.setHandler(paramHandler, paramInt);
      this.camera.autoFocus(this.autoFocusCallback);
    }
  }

  public void requestPreviewFrame(Handler paramHandler, int paramInt)
  {
    if ((this.camera != null) && (this.previewing))
    {
      this.previewCallback.setHandler(paramHandler, paramInt);
      if (this.useOneShotPreviewCallback)
        this.camera.setOneShotPreviewCallback(this.previewCallback);
    }
    else
    {
      return;
    }
    this.camera.setPreviewCallback(this.previewCallback);
  }

  public void startPreview()
  {
    if ((this.camera != null) && (!this.previewing))
    {
      this.camera.startPreview();
      this.previewing = true;
    }
  }

  public void stopPreview()
  {
    if ((this.camera != null) && (this.previewing))
    {
      if (!this.useOneShotPreviewCallback)
        this.camera.setPreviewCallback(null);
      this.camera.stopPreview();
      this.previewCallback.setHandler(null, 0);
      this.autoFocusCallback.setHandler(null, 0);
      this.previewing = false;
    }
  }
}

/* Location:           C:\Users\Administrator\Desktop\test111\classes_dex2jar.jar
 * Qualified Name:     com.chinatel.robotclient.camera.CameraManager
 * JD-Core Version:    0.6.2
 */