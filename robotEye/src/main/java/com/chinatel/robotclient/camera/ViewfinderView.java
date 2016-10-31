package com.chinatel.robotclient.camera;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import cn.wangjianlog.baseframework.MainApplication;
import com.google.zxing.ResultPoint;
import java.util.Collection;
import java.util.HashSet;

public final class ViewfinderView extends View
{
  private static final long ANIMATION_DELAY = 300L;
  private static final int OPAQUE = 255;
  private static int[] arrayOfInt;
  private static final int[] SCANNER_ALPHA = arrayOfInt;
  private final int frameColor;
  private final int laserColor;
  private Collection<ResultPoint> lastPossibleResultPoints;
  private final int mAll = this.mDrawalbeID.length;
  private int mCount = 0;
  private final int[] mDrawalbeID = { 2130837611, 2130837618, 2130837619, 2130837620, 2130837621, 2130837622, 2130837623, 2130837624, 2130837625, 2130837612, 2130837613, 2130837614, 2130837615, 2130837616, 2130837617 };
  private final int maskColor;
  private final Paint paint = new Paint();
  private Collection<ResultPoint> possibleResultPoints;
  private Bitmap resultBitmap;
  private final int resultColor;
  private final int resultPointColor;
  private int scannerAlpha;
  private int x = 0;
  private int xOffSet = (int)(3.0F * MainApplication.getDensity());
  private int yOffSet = (int)(3.0F * MainApplication.getDensity());

  static
  {
    int[] arrayOfInt = new int[8];
    arrayOfInt[1] = 64;
    arrayOfInt[2] = 128;
    arrayOfInt[3] = 192;
    arrayOfInt[4] = 255;
    arrayOfInt[5] = 192;
    arrayOfInt[6] = 128;
    arrayOfInt[7] = 64;
  }

  public ViewfinderView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    Resources localResources = getResources();
    this.maskColor = localResources.getColor(2131099673);
    this.resultColor = localResources.getColor(2131099675);
    this.frameColor = localResources.getColor(2131099671);
    this.laserColor = localResources.getColor(2131099672);
    this.resultPointColor = localResources.getColor(2131099674);
    this.scannerAlpha = 0;
    this.possibleResultPoints = new HashSet(5);
  }

  public void addPossibleResultPoint(ResultPoint paramResultPoint)
  {
    this.possibleResultPoints.add(paramResultPoint);
  }

  public void drawResultBitmap(Bitmap paramBitmap)
  {
    this.resultBitmap = paramBitmap;
    invalidate();
  }

  public void drawViewfinder()
  {
    this.resultBitmap = null;
    invalidate();
  }

  public void onDraw(Canvas paramCanvas)
  {
    Rect localRect = CameraManager.get().getFramingRect();
    if (localRect == null)
      return;
    int i = paramCanvas.getWidth();
    int j = paramCanvas.getHeight();
    Paint localPaint = this.paint;
    if (this.resultBitmap != null);
    for (int k = this.resultColor; ; k = this.maskColor)
    {
      localPaint.setColor(k);
      paramCanvas.drawRect(0.0F, 0.0F, i, localRect.top, this.paint);
      paramCanvas.drawRect(0.0F, localRect.top, localRect.left, 1 + localRect.bottom, this.paint);
      paramCanvas.drawRect(1 + localRect.right, localRect.top, i, 1 + localRect.bottom, this.paint);
      paramCanvas.drawRect(0.0F, 1 + localRect.bottom, i, j, this.paint);
      this.paint.setColor(this.laserColor);
      paramCanvas.drawBitmap(BitmapFactory.decodeResource(getResources(), 2130837516), localRect.left, localRect.top, this.paint);
      this.scannerAlpha = ((1 + this.scannerAlpha) % SCANNER_ALPHA.length);
      paramCanvas.drawBitmap(BitmapFactory.decodeResource(getResources(), this.mDrawalbeID[this.mCount]), localRect.left + this.xOffSet, localRect.top + this.yOffSet, this.paint);
      this.mCount = (1 + this.mCount);
      if (this.mCount > -1 + this.mAll)
        this.mCount = 0;
      postInvalidateDelayed(300L, localRect.left, localRect.top, localRect.right, localRect.bottom);
      return;
    }
  }
}

/* Location:           C:\Users\Administrator\Desktop\test111\classes_dex2jar.jar
 * Qualified Name:     com.chinatel.robotclient.camera.ViewfinderView
 * JD-Core Version:    0.6.2
 */