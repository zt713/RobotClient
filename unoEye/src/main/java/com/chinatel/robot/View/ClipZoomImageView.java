package com.chinatel.robot.View;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.ScaleGestureDetector.OnScaleGestureListener;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

public class ClipZoomImageView extends ImageView implements
		ScaleGestureDetector.OnScaleGestureListener, View.OnTouchListener,
		ViewTreeObserver.OnGlobalLayoutListener {
	public static float SCALE_MAX = 4.0F;
	private static float SCALE_MID = 2.0F;
	private static final String TAG = ClipZoomImageView.class.getSimpleName();
	private float initScale = 1.0F;
	private boolean isAutoScale;
	private boolean isCanDrag;
	private int lastPointerCount;
	private GestureDetector mGestureDetector;
	private int mHorizontalPadding;
	private float mLastX;
	private float mLastY;
	private ScaleGestureDetector mScaleGestureDetector = null;
	private final Matrix mScaleMatrix = new Matrix();
	private int mTouchSlop;
	private int mVerticalPadding;
	private final float[] matrixValues = new float[9];
	private boolean once = true;

	public ClipZoomImageView(Context paramContext) {
		this(paramContext, null);
	}

	public ClipZoomImageView(Context paramContext,
			AttributeSet paramAttributeSet) {
		super(paramContext, paramAttributeSet);
		setScaleType(ImageView.ScaleType.MATRIX);
		this.mGestureDetector = new GestureDetector(paramContext,
				new GestureDetector.SimpleOnGestureListener() {
					public boolean onDoubleTap(
							MotionEvent paramAnonymousMotionEvent) {
						if (ClipZoomImageView.this.isAutoScale)
							return true;
						float f1 = paramAnonymousMotionEvent.getX();
						float f2 = paramAnonymousMotionEvent.getY();
						if (ClipZoomImageView.this.getScale() < ClipZoomImageView.SCALE_MID) {
							// ClipZoomImageView.this.postDelayed(new
							// ClipZoomImageView.AutoScaleRunnable(ClipZoomImageView.this,
							// ClipZoomImageView.SCALE_MID, f1, f2), 16L);
							ClipZoomImageView.this.isAutoScale = true;
							return true;
						}
						// ClipZoomImageView.this.postDelayed(new
						// ClipZoomImageView.AutoScaleRunnable(ClipZoomImageView.this,
						// ClipZoomImageView.this.initScale, f1, f2), 16L);
						ClipZoomImageView.this.isAutoScale = true;
						return true;
					}
				});
		this.mScaleGestureDetector = new ScaleGestureDetector(paramContext,
				this);
		setOnTouchListener(this);
	}

	private void checkBorder() {
		RectF localRectF = getMatrixRectF();
		int i = getWidth();
		int j = getHeight();
		Log.e(TAG, "rect.width() =  " + localRectF.width()
				+ " , width - 2 * mHorizontalPadding ="
				+ (i - 2 * this.mHorizontalPadding));
		boolean bool1 = 0.01D + localRectF.width() < i - 2
				* this.mHorizontalPadding;
		float f1 = 0.0F;
		if (!bool1) {
			boolean bool4 = localRectF.left < this.mHorizontalPadding;
			f1 = 0.0F;
			if (bool4)
				f1 = -localRectF.left + this.mHorizontalPadding;
			if (localRectF.right < i - this.mHorizontalPadding)
				f1 = i - this.mHorizontalPadding - localRectF.right;
		}
		boolean bool2 = 0.01D + localRectF.height() < j - 2
				* this.mVerticalPadding;
		float f2 = 0.0F;
		if (!bool2) {
			boolean bool3 = localRectF.top < this.mVerticalPadding;
			f2 = 0.0F;
			if (bool3)
				f2 = -localRectF.top + this.mVerticalPadding;
			if (localRectF.bottom < j - this.mVerticalPadding)
				f2 = j - this.mVerticalPadding - localRectF.bottom;
		}
		this.mScaleMatrix.postTranslate(f1, f2);
	}

	private RectF getMatrixRectF() {
		Matrix localMatrix = this.mScaleMatrix;
		RectF localRectF = new RectF();
		Drawable localDrawable = getDrawable();
		if (localDrawable != null) {
			localRectF.set(0.0F, 0.0F, localDrawable.getIntrinsicWidth(),
					localDrawable.getIntrinsicHeight());
			localMatrix.mapRect(localRectF);
		}
		return localRectF;
	}

	private boolean isCanDrag(float paramFloat1, float paramFloat2) {
		return Math.sqrt(paramFloat1 * paramFloat1 + paramFloat2 * paramFloat2) >= this.mTouchSlop;
	}

	public Bitmap clip() {
		Bitmap localBitmap = Bitmap.createBitmap(getWidth(), getHeight(),
				Bitmap.Config.ARGB_8888);
		draw(new Canvas(localBitmap));
		return Bitmap.createBitmap(localBitmap, this.mHorizontalPadding,
				this.mVerticalPadding,
				getWidth() - 2 * this.mHorizontalPadding, getWidth() - 2
						* this.mHorizontalPadding);
	}

	public final float getScale() {
		this.mScaleMatrix.getValues(this.matrixValues);
		return this.matrixValues[0];
	}

	protected void onAttachedToWindow() {
		super.onAttachedToWindow();
		getViewTreeObserver().addOnGlobalLayoutListener(this);
	}

	protected void onDetachedFromWindow() {
		super.onDetachedFromWindow();
		getViewTreeObserver().removeGlobalOnLayoutListener(this);
	}

	public void onGlobalLayout() {
		Drawable localDrawable;
		if (this.once) {
			localDrawable = getDrawable();
			if (localDrawable != null)
				;
		} else {
			return;
		}
		this.mVerticalPadding = ((getHeight() - (getWidth() - 2 * this.mHorizontalPadding)) / 2);
		int i = getWidth();
		int j = getHeight();
		int k = localDrawable.getIntrinsicWidth();
		int m = localDrawable.getIntrinsicHeight();
		float f = 1.0F;
		if ((k < getWidth() - 2 * this.mHorizontalPadding)
				&& (m > getHeight() - 2 * this.mVerticalPadding))
			f = (1.0F * getWidth() - 2 * this.mHorizontalPadding) / k;
		if ((m < getHeight() - 2 * this.mVerticalPadding)
				&& (k > getWidth() - 2 * this.mHorizontalPadding))
			f = (1.0F * getHeight() - 2 * this.mVerticalPadding) / m;
		if ((k < getWidth() - 2 * this.mHorizontalPadding)
				&& (m < getHeight() - 2 * this.mVerticalPadding))
			f = Math.max((1.0F * getWidth() - 2 * this.mHorizontalPadding) / k,
					(1.0F * getHeight() - 2 * this.mVerticalPadding) / m);
		this.initScale = f;
		SCALE_MID = 2.0F * this.initScale;
		SCALE_MAX = 4.0F * this.initScale;
		this.mScaleMatrix.postTranslate((i - k) / 2, (j - m) / 2);
		this.mScaleMatrix.postScale(f, f, getWidth() / 2, getHeight() / 2);
		setImageMatrix(this.mScaleMatrix);
		this.once = false;
	}

	public boolean onScale(ScaleGestureDetector paramScaleGestureDetector) {
		float f1 = getScale();
		float f2 = paramScaleGestureDetector.getScaleFactor();
		if (getDrawable() == null)
			;
		while (((f1 >= SCALE_MAX) || (f2 <= 1.0F))
				&& ((f1 <= this.initScale) || (f2 >= 1.0F)))
			return true;
		if (f2 * f1 < this.initScale)
			f2 = this.initScale / f1;
		if (f2 * f1 > SCALE_MAX)
			f2 = SCALE_MAX / f1;
		this.mScaleMatrix.postScale(f2, f2,
				paramScaleGestureDetector.getFocusX(),
				paramScaleGestureDetector.getFocusY());
		checkBorder();
		setImageMatrix(this.mScaleMatrix);
		return true;
	}

	public boolean onScaleBegin(ScaleGestureDetector paramScaleGestureDetector) {
		return true;
	}

	public void onScaleEnd(ScaleGestureDetector paramScaleGestureDetector) {
	}

	public boolean onTouch(View paramView, MotionEvent paramMotionEvent) {
		if (this.mGestureDetector.onTouchEvent(paramMotionEvent))
			return true;
		this.mScaleGestureDetector.onTouchEvent(paramMotionEvent);
		float f1 = 0.0F;
		float f2 = 0.0F;
		int i = paramMotionEvent.getPointerCount();
		float f3 = 0;
		float f4 = 0;
		/*
		 * for (int j = 0; ; j++) { if (j >= i) { f3 = f1 / i; f4 = f2 / i; if
		 * (i != this.lastPointerCount) { this.isCanDrag = false; this.mLastX =
		 * f3; this.mLastY = f4; } this.lastPointerCount = i; } switch
		 * (paramMotionEvent.getAction()) { default: // return true; case 1:
		 * case 3: this.lastPointerCount = 0; // return true; f1 +=
		 * paramMotionEvent.getX(j); f2 += paramMotionEvent.getY(j); case 2: } }
		 */
		float f5 = f3 - this.mLastX;
		float f6 = f4 - this.mLastY;
		if (!this.isCanDrag)
			this.isCanDrag = isCanDrag(f5, f6);
		if ((this.isCanDrag) && (getDrawable() != null)) {
			RectF localRectF = getMatrixRectF();
			if (localRectF.width() <= getWidth() - 2 * this.mHorizontalPadding)
				f5 = 0.0F;
			if (localRectF.height() <= getHeight() - 2 * this.mVerticalPadding)
				f6 = 0.0F;
			this.mScaleMatrix.postTranslate(f5, f6);
			checkBorder();
			setImageMatrix(this.mScaleMatrix);
		}
		this.mLastX = f3;
		this.mLastY = f4;
		return true;
	}

	public void setHorizontalPadding(int paramInt) {
		this.mHorizontalPadding = paramInt;
	}

	private class AutoScaleRunnable implements Runnable {
		static final float BIGGER = 1.07F;
		static final float SMALLER = 0.93F;
		private float mTargetScale;
		private float tmpScale;
		private float x;
		private float y;
		private float localObject;

		public AutoScaleRunnable(float paramFloat1, float paramFloat2,
				float arg4) {
			this.mTargetScale = paramFloat1;
			this.x = paramFloat2;
			// Object localObject;

			this.y = localObject;
			if (ClipZoomImageView.this.getScale() < this.mTargetScale) {
				this.tmpScale = 1.07F;
				return;
			}
			this.tmpScale = 0.93F;
		}

		public void run() {
			ClipZoomImageView.this.mScaleMatrix.postScale(this.tmpScale,
					this.tmpScale, this.x, this.y);
			ClipZoomImageView.this.checkBorder();
			ClipZoomImageView.this
					.setImageMatrix(ClipZoomImageView.this.mScaleMatrix);
			float f1 = ClipZoomImageView.this.getScale();
			if (((this.tmpScale > 1.0F) && (f1 < this.mTargetScale))
					|| ((this.tmpScale < 1.0F) && (this.mTargetScale < f1))) {
				ClipZoomImageView.this.postDelayed(this, 16L);
				return;
			}
			float f2 = this.mTargetScale / f1;
			ClipZoomImageView.this.mScaleMatrix.postScale(f2, f2, this.x,
					this.y);
			ClipZoomImageView.this.checkBorder();
			ClipZoomImageView.this
					.setImageMatrix(ClipZoomImageView.this.mScaleMatrix);
			ClipZoomImageView.this.isAutoScale = false;
		}
	}
}

/*
 * Location:
 * C:\Users\Administrator\Desktop\小优\U03S源码\机器人本体.apk\classes_dex2jar.jar
 * Qualified Name: com.chinatel.robot.View.ClipZoomImageView JD-Core Version:
 * 0.6.2
 */