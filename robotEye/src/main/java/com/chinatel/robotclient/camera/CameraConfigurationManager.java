package com.chinatel.robotclient.camera;

import android.content.Context;
import android.graphics.Point;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.os.Build;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;
import java.util.regex.Pattern;

final class CameraConfigurationManager
{
  private static final Pattern COMMA_PATTERN = Pattern.compile(",");
  private static final int DESIRED_SHARPNESS = 30;
  private static final String TAG = CameraConfigurationManager.class.getSimpleName();
  private static final int TEN_DESIRED_ZOOM = 27;
  private Point cameraResolution;
  private final Context context;
  private int previewFormat;
  private String previewFormatString;
  private Point screenResolution;

  CameraConfigurationManager(Context paramContext)
  {
    this.context = paramContext;
  }

  private static int findBestMotZoomValue(CharSequence paramCharSequence, int paramInt)
  {
    int i = 0;
    String[] arrayOfString = COMMA_PATTERN.split(paramCharSequence);
    int j = arrayOfString.length;
    int k = 0;
    while (true)
    {
      if (k >= j)
        return i;
      String str = arrayOfString[k].trim();
      try
      {
        double d = Double.parseDouble(str);
        int m = (int)(10.0D * d);
        if (Math.abs(paramInt - d) < Math.abs(paramInt - i))
          i = m;
        k++;
      }
      catch (NumberFormatException localNumberFormatException)
      {
      }
    }
  }

  private static Point findBestPreviewSizeValue(CharSequence paramCharSequence, Point paramPoint)
  {
    int i = 0;
    int j = 0;
    int k = 2147483647;
    String[] arrayOfString = COMMA_PATTERN.split(paramCharSequence);
    int m = arrayOfString.length;
    int n = 0;
    if (n >= m)
    {
      if ((i > 0) && (j > 0))
        return new Point(i, j);
    }
    else
    {
      String str = arrayOfString[n].trim();
      int i1 = str.indexOf('x');
      if (i1 < 0)
        Log.w(TAG, "Bad preview-size: " + str);
      while (true)
      {
        int i2;
        int i3;
        int i4;
        while (true)
        {
          n++;
          try
          {
            i2 = Integer.parseInt(str.substring(0, i1));
            i3 = Integer.parseInt(str.substring(i1 + 1));
            i4 = Math.abs(i2 - paramPoint.x) + Math.abs(i3 - paramPoint.y);
            if (i4 != 0)
            i = i2;
            j = i3;
          }
          catch (NumberFormatException localNumberFormatException)
          {
            Log.w(TAG, "Bad preview-size: " + str);
          }
        }
      }
    }
    return null;
  }

  private static Point getCameraResolution(Camera.Parameters paramParameters, Point paramPoint)
  {
    String str = paramParameters.get("preview-size-values");
    if (str == null)
      str = paramParameters.get("preview-size-value");
    Point localPoint = null;
    if (str != null)
    {
      Log.d(TAG, "preview-size-values parameter: " + str);
      localPoint = findBestPreviewSizeValue(str, paramPoint);
    }
    if (localPoint == null)
      localPoint = new Point(paramPoint.x >> 3 << 3, paramPoint.y >> 3 << 3);
    return localPoint;
  }

  private void setFlash(Camera.Parameters paramParameters)
  {
    if ((Build.MODEL.contains("Behold II")) && (CameraManager.SDK_INT == 3))
      paramParameters.set("flash-value", 1);
    while (true)
    {
      paramParameters.set("flash-mode", "off");
      paramParameters.set("flash-value", 2);
    }
  }

  private void setZoom(Camera.Parameters paramParameters)
  {
    String str1 = paramParameters.get("zoom-supported");
    if ((str1 != null) && (!Boolean.parseBoolean(str1)));
    while (true)
    {
      int i = 27;
      String str2 = paramParameters.get("max-zoom");
      if (str2 != null);
      String str3 = null;
	try
      {
        double d = Double.parseDouble(str2);
        int n = (int)(10.0D * d);
        if (i > n)
          i = n;
        str3 = paramParameters.get("taking-picture-zoom-max");
        if (str3 == null);
      }
      catch (NumberFormatException localNumberFormatException2)
      {
        try
        {
          int m = Integer.parseInt(str3);
          if (i > m)
            i = m;
          String str4 = paramParameters.get("mot-zoom-values");
          if (str4 != null)
            i = findBestMotZoomValue(str4, i);
          String str5 = paramParameters.get("mot-zoom-step");
          if (str5 == null);
        }
        catch (NumberFormatException localNumberFormatException21)
        {
          try
          {
            String str31 = null;
            while (true)
            {
              String str4 = null;
              String str5 = null;
              int j = (int)(10.0D * Double.parseDouble(str5.trim()));
              if (j > 1)
              {
                int k = i % j;
                i -= k;
              }
              label154: if ((str2 != null) || (str4 != null))
                paramParameters.set("zoom", String.valueOf(i / 10.0D));
              if (str31 == null)
                break;
              paramParameters.set("taking-picture-zoom", i);
              Log.w(TAG, "Bad max-zoom: " + str2);
            }
            localNumberFormatException21 = localNumberFormatException21;
            Log.w(TAG, "Bad taking-picture-zoom-max: " + str31);
          }
          catch (NumberFormatException localNumberFormatException1)
          {
          }
        }
      }
    }
  }

  Point getCameraResolution()
  {
    return this.cameraResolution;
  }

  int getPreviewFormat()
  {
    return this.previewFormat;
  }

  String getPreviewFormatString()
  {
    return this.previewFormatString;
  }

  Point getScreenResolution()
  {
    return this.screenResolution;
  }

  void initFromCameraParameters(Camera paramCamera)
  {
    Camera.Parameters localParameters = paramCamera.getParameters();
    this.previewFormat = localParameters.getPreviewFormat();
    this.previewFormatString = localParameters.get("preview-format");
    Log.d(TAG, "Default preview format: " + this.previewFormat + '/' + this.previewFormatString);
    Display localDisplay = ((WindowManager)this.context.getSystemService("window")).getDefaultDisplay();
    this.screenResolution = new Point(localDisplay.getWidth(), localDisplay.getHeight());
    Log.d(TAG, "Screen resolution: " + this.screenResolution);
    Log.d(TAG, "Camera resolution: " + this.screenResolution);
    Point localPoint = new Point();
    localPoint.x = this.screenResolution.x;
    localPoint.y = this.screenResolution.y;
    if (this.screenResolution.x < this.screenResolution.y)
    {
      localPoint.x = this.screenResolution.y;
      localPoint.y = this.screenResolution.x;
    }
    this.cameraResolution = getCameraResolution(localParameters, localPoint);
  }

  void setDesiredCameraParameters(Camera paramCamera)
  {
    Camera.Parameters localParameters = paramCamera.getParameters();
    Log.d(TAG, "Setting preview size: " + this.cameraResolution);
    localParameters.setPreviewSize(this.cameraResolution.x, this.cameraResolution.y);
    setFlash(localParameters);
    setZoom(localParameters);
    paramCamera.setDisplayOrientation(90);
    paramCamera.setParameters(localParameters);
  }
}

/* Location:           C:\Users\Administrator\Desktop\test111\classes_dex2jar.jar
 * Qualified Name:     com.chinatel.robotclient.camera.CameraConfigurationManager
 * JD-Core Version:    0.6.2
 */