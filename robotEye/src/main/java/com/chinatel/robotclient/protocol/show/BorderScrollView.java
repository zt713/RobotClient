package com.chinatel.robotclient.protocol.show;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.ScrollView;

public class BorderScrollView extends ScrollView
{
  private long millis;
  private OnScrollChangedListener onScrollChangedListener;

  public BorderScrollView(Context paramContext)
  {
    super(paramContext);
  }

  public BorderScrollView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }

  public BorderScrollView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
  }

  protected int computeScrollDeltaToGetChildRectOnScreen(Rect paramRect)
  {
    return 0;
  }

  public OnScrollChangedListener getOnScrollChangedListener()
  {
    return this.onScrollChangedListener;
  }

  public int getTotalVerticalScrollRange()
  {
    return computeVerticalScrollRange();
  }

  protected void onScrollChanged(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    super.onScrollChanged(paramInt1, paramInt2, paramInt3, paramInt4);
    if (this.onScrollChangedListener == null);
    do
    {
      long l = System.currentTimeMillis();
      this.onScrollChangedListener.onScrollChanged(paramInt1, paramInt2, paramInt3, paramInt4);
      if ((l - this.millis > 1000L) && (paramInt4 + getHeight() != getTotalVerticalScrollRange()) && (paramInt2 + getHeight() == getTotalVerticalScrollRange()))
      {
        this.onScrollChangedListener.onScrollBottom();
        this.millis = l;
      }
    }
    while ((paramInt4 == 0) || (paramInt2 != 0));
    this.onScrollChangedListener.onScrollTop();
  }

  public void setOnScrollChangedListener(OnScrollChangedListener paramOnScrollChangedListener)
  {
    this.onScrollChangedListener = paramOnScrollChangedListener;
  }
}

/* Location:           C:\Users\Administrator\Desktop\test111\classes_dex2jar.jar
 * Qualified Name:     com.chinatel.robotclient.protocol.show.BorderScrollView
 * JD-Core Version:    0.6.2
 */