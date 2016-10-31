package com.chinatel.robotclient.rbuitl;

import android.graphics.drawable.AnimationDrawable;
import android.widget.ImageView;

public class ProgressUtil
{
  public static void rotation(ImageView paramImageView, int paramInt)
  {
    paramImageView.setImageResource(paramInt);
    ((AnimationDrawable)paramImageView.getDrawable()).start();
  }
}

/* Location:           C:\Users\Administrator\Desktop\test111\classes_dex2jar.jar
 * Qualified Name:     com.chinatel.robotclient.rbuitl.ProgressUtil
 * JD-Core Version:    0.6.2
 */