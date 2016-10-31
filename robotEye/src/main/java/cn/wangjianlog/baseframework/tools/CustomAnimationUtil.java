package cn.wangjianlog.baseframework.tools;

import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import java.util.Iterator;
import java.util.List;

public class CustomAnimationUtil
{
  public static Animation closeScaleAnimation(View paramView, int paramInt)
  {
    ScaleAnimation localScaleAnimation = new ScaleAnimation(0.1F, 1.0F, 1.0F, 1.0F);
    localScaleAnimation.setDuration(paramInt);
    paramView.startAnimation(localScaleAnimation);
    return localScaleAnimation;
  }

  public static Animation initAlphaAnimation(View paramView, int paramInt)
  {
    AlphaAnimation localAlphaAnimation = new AlphaAnimation(0.1F, 1.0F);
    localAlphaAnimation.setDuration(paramInt);
    paramView.startAnimation(localAlphaAnimation);
    return localAlphaAnimation;
  }

  public static AnimationSet initAnimationSet(View paramView, List<Animation> paramList, int paramInt)
  {
    if (paramList == null)
      return null;
    AnimationSet localAnimationSet = new AnimationSet(true);
    Iterator localIterator = paramList.iterator();
    while (true)
    {
      if (!localIterator.hasNext())
      {
        localAnimationSet.setDuration(paramInt);
        paramView.startAnimation(localAnimationSet);
        return localAnimationSet;
      }
      localAnimationSet.addAnimation((Animation)localIterator.next());
    }
  }

  public static Animation initRotateAnimation(View paramView, int paramInt)
  {
    RotateAnimation localRotateAnimation = new RotateAnimation(0.0F, 360.0F);
    localRotateAnimation.setDuration(paramInt);
    paramView.startAnimation(localRotateAnimation);
    return localRotateAnimation;
  }

  public static Animation initScaleAnimation(View paramView, int paramInt)
  {
    ScaleAnimation localScaleAnimation = new ScaleAnimation(0.1F, 1.0F, 1.0F, 1.0F);
    localScaleAnimation.setDuration(paramInt);
    paramView.startAnimation(localScaleAnimation);
    return localScaleAnimation;
  }

  public static Animation initScaleAnimation(View paramView, int paramInt, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
  {
    ScaleAnimation localScaleAnimation = new ScaleAnimation(paramFloat1, paramFloat2, paramFloat3, paramFloat4);
    localScaleAnimation.setDuration(paramInt);
    paramView.startAnimation(localScaleAnimation);
    return localScaleAnimation;
  }

  public static Animation initTranslateAnimation(View paramView, int paramInt)
  {
    TranslateAnimation localTranslateAnimation = new TranslateAnimation(0.1F, 100.0F, 0.1F, 100.0F);
    paramView.startAnimation(localTranslateAnimation);
    return localTranslateAnimation;
  }
}

/* Location:           C:\Users\Administrator\Desktop\test111\classes_dex2jar.jar
 * Qualified Name:     cn.wangjianlog.baseframework.tools.CustomAnimationUtil
 * JD-Core Version:    0.6.2
 */