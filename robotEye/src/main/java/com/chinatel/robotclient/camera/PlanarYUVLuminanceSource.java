package com.chinatel.robotclient.camera;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import com.google.zxing.LuminanceSource;

public final class PlanarYUVLuminanceSource extends LuminanceSource
{
  private final int dataHeight;
  private final int dataWidth;
  private final int left;
  private final int top;
  private final byte[] yuvData;

  public PlanarYUVLuminanceSource(byte[] paramArrayOfByte, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6)
  {
    super(paramInt5, paramInt6);
    if ((paramInt3 + paramInt5 > paramInt1) || (paramInt4 + paramInt6 > paramInt2))
      throw new IllegalArgumentException("Crop rectangle does not fit within image data.");
    this.yuvData = paramArrayOfByte;
    this.dataWidth = paramInt1;
    this.dataHeight = paramInt2;
    this.left = paramInt3;
    this.top = paramInt4;
  }

  public int getDataHeight()
  {
    return this.dataHeight;
  }

  public int getDataWidth()
  {
    return this.dataWidth;
  }

  public byte[] getMatrix()
  {
    int i = getWidth();
    int j = getHeight();
    byte[] arrayOfByte1;
    if ((i == this.dataWidth) && (j == this.dataHeight))
      arrayOfByte1 = this.yuvData;
    while (true)
    {
      int k = 0;
	arrayOfByte1 = new byte[k];
      int m = this.top * this.dataWidth + this.left;
      if (i == this.dataWidth)
      {
        System.arraycopy(this.yuvData, m, arrayOfByte1, 0, k);
        return arrayOfByte1;
      }
      byte[] arrayOfByte2 = this.yuvData;
      for (int n = 0; n < j; n++)
      {
        System.arraycopy(arrayOfByte2, m, arrayOfByte1, n * i, i);
        m += this.dataWidth;
      }
    }
  }

  public byte[] getRow(int paramInt, byte[] paramArrayOfByte)
  {
    if ((paramInt < 0) || (paramInt >= getHeight()))
      throw new IllegalArgumentException("Requested row is outside the image: " + paramInt);
    int i = getWidth();
    if ((paramArrayOfByte == null) || (paramArrayOfByte.length < i))
      paramArrayOfByte = new byte[i];
    int j = (paramInt + this.top) * this.dataWidth + this.left;
    System.arraycopy(this.yuvData, j, paramArrayOfByte, 0, i);
    return paramArrayOfByte;
  }

  public boolean isCropSupported()
  {
    return true;
  }

  public Bitmap renderCroppedGreyscaleBitmap()
  {
    int i = getWidth();
    int j = getHeight();
    int[] arrayOfInt = new int[i * j];
    byte[] arrayOfByte = this.yuvData;
    int k = this.top * this.dataWidth + this.left;
    int m = 0;
    if (m >= j)
    {
      Bitmap localBitmap = Bitmap.createBitmap(i, j, Bitmap.Config.ARGB_8888);
      localBitmap.setPixels(arrayOfInt, 0, i, 0, 0, i, j);
      return localBitmap;
    }
    int n = m * i;
    for (int i1 = 0; ; i1++)
    {
      if (i1 >= i)
      {
        k += this.dataWidth;
        m++;
        break;
      }
      int i2 = 0xFF & arrayOfByte[(k + i1)];
      arrayOfInt[(n + i1)] = (0xFF000000 | 65793 * i2);
    }
	return null;
  }
}

/* Location:           C:\Users\Administrator\Desktop\test111\classes_dex2jar.jar
 * Qualified Name:     com.chinatel.robotclient.camera.PlanarYUVLuminanceSource
 * JD-Core Version:    0.6.2
 */