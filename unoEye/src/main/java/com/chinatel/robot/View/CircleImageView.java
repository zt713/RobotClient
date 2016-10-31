package com.chinatel.robot.View;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.chinatel.robot.R1;

public class CircleImageView extends ImageView {
	private static final Bitmap.Config BITMAP_CONFIG = Bitmap.Config.ARGB_8888;
	private static final int COLORDRAWABLE_DIMENSION = 1;
	private static final int DEFAULT_BORDER_COLOR = -16777216;
	private static final ImageView.ScaleType SCALE_TYPE = ImageView.ScaleType.CENTER_CROP;
	private Bitmap mBitmap;
	private int mBitmapHeight;
	private Paint mBitmapPaint = new Paint();
	private BitmapShader mBitmapShader;
	private int mBitmapWidth;
	private int mBorderColor = -16777216;
	private Paint mBorderPaint = new Paint();
	private float mBorderRadius;
	private RectF mBorderRect = new RectF();
	private int mBorderWidth = 0;
	private float mDrawableRadius;
	private RectF mDrawableRect = new RectF();
	private boolean mReady;
	private boolean mSetupPending;
	private Matrix mShaderMatrix = new Matrix();

	public CircleImageView(Context paramContext) {
		super(paramContext);
		init();
	}

	public CircleImageView(Context paramContext, AttributeSet paramAttributeSet) {
		this(paramContext, paramAttributeSet, 0);
	}

	public CircleImageView(Context paramContext,
			AttributeSet paramAttributeSet, int paramInt) {
		super(paramContext, paramAttributeSet, paramInt);
		TypedArray localTypedArray = paramContext.obtainStyledAttributes(
				paramAttributeSet, R1.styleable.CircleImageView, paramInt, 0);
		this.mBorderWidth = localTypedArray.getDimensionPixelSize(0, 0);
		this.mBorderColor = localTypedArray.getColor(1, -16777216);
		localTypedArray.recycle();
		init();
	}

	private Bitmap getBitmapFromDrawable(Drawable paramDrawable) {
		if (paramDrawable == null)
			return null;
		if ((paramDrawable instanceof BitmapDrawable))
			return ((BitmapDrawable) paramDrawable).getBitmap();
		try {
			if ((paramDrawable instanceof ColorDrawable))
				;
			Bitmap localBitmap;
			for (Object localObject = Bitmap.createBitmap(1, 1, BITMAP_CONFIG);; localObject = localBitmap) {
				Canvas localCanvas = new Canvas((Bitmap) localObject);
				paramDrawable.setBounds(0, 0, localCanvas.getWidth(),
						localCanvas.getHeight());
				paramDrawable.draw(localCanvas);
				localBitmap = Bitmap.createBitmap(
						paramDrawable.getIntrinsicWidth(),
						paramDrawable.getIntrinsicHeight(), BITMAP_CONFIG);
			}
		} catch (OutOfMemoryError localOutOfMemoryError) {
		}
		return null;
	}

	private void init() {
		super.setScaleType(SCALE_TYPE);
		this.mReady = true;
		if (this.mSetupPending) {
			setup();
			this.mSetupPending = false;
		}
	}

	private void setup() {
		if (!this.mReady)
			this.mSetupPending = true;
		while (this.mBitmap == null)
			return;

		if (null == this.mBitmapPaint) {
			this.mBitmapPaint = new Paint();
		}
		if (null == this.mBorderPaint) {
			this.mBorderPaint = new Paint();
		}
		if (null == this.mDrawableRect) {
			this.mDrawableRect = new RectF();
		}
		if (null == this.mShaderMatrix) {
			mShaderMatrix = new Matrix();
		}
		if (null == this.mBorderRect) {
			mBorderRect = new RectF();
		}

		this.mBitmapShader = new BitmapShader(this.mBitmap,
				Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
		this.mBitmapPaint.setAntiAlias(true);
		this.mBitmapPaint.setShader(this.mBitmapShader);
		this.mBorderPaint.setStyle(Paint.Style.STROKE);
		this.mBorderPaint.setAntiAlias(true);
		this.mBorderPaint.setColor(this.mBorderColor);
		this.mBorderPaint.setStrokeWidth(this.mBorderWidth);
		this.mBitmapHeight = this.mBitmap.getHeight();
		this.mBitmapWidth = this.mBitmap.getWidth();
		this.mBorderRect.set(0.0F, 0.0F, getWidth(), getHeight());
		this.mBorderRadius = Math.min(
				(this.mBorderRect.height() - this.mBorderWidth) / 2.0F,
				(this.mBorderRect.width() - this.mBorderWidth) / 2.0F);
		this.mDrawableRect.set(this.mBorderWidth, this.mBorderWidth,
				this.mBorderRect.width() - this.mBorderWidth,
				this.mBorderRect.height() - this.mBorderWidth);
		this.mDrawableRadius = Math.min(this.mDrawableRect.height() / 2.0F,
				this.mDrawableRect.width() / 2.0F);
		// updateShaderMatrix();
		invalidate();
	}

	private void updateShaderMatrix() {
		float f1 = 0.0F;
		this.mShaderMatrix.set(null);
		float f2 = 0;
		if (this.mBitmapWidth * this.mDrawableRect.height() > this.mDrawableRect
				.width() * this.mBitmapHeight)
			f2 = this.mDrawableRect.height() / this.mBitmapHeight;
		for (float f3 = 0.5F * (this.mDrawableRect.width() - f2
				* this.mBitmapWidth);; f3 = 0.0F) {
			this.mShaderMatrix.setScale(f2, f2);
			this.mShaderMatrix.postTranslate((int) (f3 + 0.5F)
					+ this.mBorderWidth, (int) (f1 + 0.5F) + this.mBorderWidth);
			this.mBitmapShader.setLocalMatrix(this.mShaderMatrix);
			// return;
			f2 = this.mDrawableRect.width() / this.mBitmapWidth;
			f1 = 0.5F * (this.mDrawableRect.height() - f2 * this.mBitmapHeight);
		}
	}

	public int getBorderColor() {
		return this.mBorderColor;
	}

	public int getBorderWidth() {
		return this.mBorderWidth;
	}

	public ImageView.ScaleType getScaleType() {
		return SCALE_TYPE;
	}

	protected void onDraw(Canvas paramCanvas) {
		if (getDrawable() == null)
			;
		do {
			// return;
			paramCanvas.drawCircle(getWidth() / 2, getHeight() / 2,
					this.mDrawableRadius, this.mBitmapPaint);
		} while (this.mBorderWidth == 0);
		paramCanvas.drawCircle(getWidth() / 2, getHeight() / 2,
				this.mBorderRadius, this.mBorderPaint);
	}

	protected void onSizeChanged(int paramInt1, int paramInt2, int paramInt3,
			int paramInt4) {
		super.onSizeChanged(paramInt1, paramInt2, paramInt3, paramInt4);
		setup();
	}

	public void setBorderColor(int paramInt) {
		if (paramInt == this.mBorderColor)
			return;
		this.mBorderColor = paramInt;
		this.mBorderPaint.setColor(this.mBorderColor);
		invalidate();
	}

	public void setBorderWidth(int paramInt) {
		if (paramInt == this.mBorderWidth)
			return;
		this.mBorderWidth = paramInt;
		setup();
	}

	public void setImageBitmap(Bitmap paramBitmap) {
		super.setImageBitmap(paramBitmap);
		this.mBitmap = paramBitmap;
		setup();
	}

	public void setImageDrawable(Drawable paramDrawable) {
		super.setImageDrawable(paramDrawable);
		this.mBitmap = getBitmapFromDrawable(paramDrawable);
		setup();
	}

	public void setImageResource(int paramInt) {
		super.setImageResource(paramInt);
		this.mBitmap = getBitmapFromDrawable(getDrawable());
		setup();
	}

	public void setImageURI(Uri paramUri) {
		super.setImageURI(paramUri);
		this.mBitmap = getBitmapFromDrawable(getDrawable());
		setup();
	}

	public void setScaleType(ImageView.ScaleType paramScaleType) {
		if (paramScaleType != SCALE_TYPE)
			throw new IllegalArgumentException(String.format(
					"ScaleType %s not supported.",
					new Object[] { paramScaleType }));
	}
}

/*
 * Location:
 * C:\Users\Administrator\Desktop\小优\U03S源码\机器人本体.apk\classes_dex2jar.jar
 * Qualified Name: com.chinatel.robot.View.CircleImageView JD-Core Version:
 * 0.6.2
 */