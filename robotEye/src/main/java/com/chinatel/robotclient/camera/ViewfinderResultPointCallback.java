package com.chinatel.robotclient.camera;

import com.google.zxing.ResultPoint;
import com.google.zxing.ResultPointCallback;

public final class ViewfinderResultPointCallback
  implements ResultPointCallback
{
  private final ViewfinderView viewfinderView;

  public ViewfinderResultPointCallback(ViewfinderView paramViewfinderView)
  {
    this.viewfinderView = paramViewfinderView;
  }

  public void foundPossibleResultPoint(ResultPoint paramResultPoint)
  {
    this.viewfinderView.addPossibleResultPoint(paramResultPoint);
  }
}

/* Location:           C:\Users\Administrator\Desktop\test111\classes_dex2jar.jar
 * Qualified Name:     com.chinatel.robotclient.camera.ViewfinderResultPointCallback
 * JD-Core Version:    0.6.2
 */