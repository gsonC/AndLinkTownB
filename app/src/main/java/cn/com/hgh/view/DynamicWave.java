package cn.com.hgh.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DrawFilter;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;

import cn.com.hgh.utils.MathExtend;

/*
 * @创建者     master
 * @创建时间   2016/11/1 19:34
 * @描述       柱形水波纹
 *
 * @更新者     $Author$ 
 * @更新时间   $Date$
 * @更新描述
 */
public class DynamicWave extends View {
	// 波纹颜色
	private static final int WAVE_PAINT_COLOR = 0x880000aa;
	// y = Asin(wx+b)+h
	private static final float STRETCH_FACTOR_A = 2;
	private static final int OFFSET_Y = 0;
	// 第一条水波移动速度
	private static final int TRANSLATE_X_SPEED_ONE = 1;
	// 第二条水波移动速度
	private static final int TRANSLATE_X_SPEED_TWO = 1;
	private float mCycleFactorW;

	private int mTotalWidth, mTotalHeight;
	private float[] mYPositions;
	private float[] mResetOneYPositions;
	private float[] mResetTwoYPositions;
	private int mXOffsetSpeedOne;
	private int mXOffsetSpeedTwo;
	private int mXOneOffset;
	private int mXTwoOffset;

	private Paint mWavePaint;
	private DrawFilter mDrawFilter;

	private int height;


	private Paint roundRectPaint;


	public DynamicWave(Context context, AttributeSet attrs) {
		super(context, attrs);
		// 将dp转化为px，用于控制不同分辨率上移动速度基本一致
		mXOffsetSpeedOne = dipToPx(context, TRANSLATE_X_SPEED_ONE);
		mXOffsetSpeedTwo = dipToPx(context, TRANSLATE_X_SPEED_TWO);

		// 初始绘制波纹的画笔
		mWavePaint = new Paint();
		// 去除画笔锯齿
		mWavePaint.setAntiAlias(true);
		// 设置风格为实线
		mWavePaint.setStyle(Paint.Style.FILL);
		// 设置画笔颜色
		mWavePaint.setColor(Color.parseColor("#f66155"));
		mDrawFilter = new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG);


		//初始绘制圆角矩形的画笔
		roundRectPaint = new Paint();
		//去除画笔锯齿
		roundRectPaint.setAntiAlias(true);
		//设置画笔颜色
		roundRectPaint.setColor(Color.parseColor("#ededed"));
		//设置线宽
		roundRectPaint.setStrokeWidth((float) 3.0);
		//设置实心
		roundRectPaint.setStyle(Paint.Style.FILL_AND_STROKE);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		// 从canvas层面去除绘制时锯齿
		canvas.setDrawFilter(mDrawFilter);
		resetPositonY();


		//画圆角矩形
		RectF r2 = new RectF();
		r2.left = 0;
		r2.top = 0;
		r2.right = mTotalWidth;
		r2.bottom = mTotalHeight;
		canvas.drawRoundRect(r2, 8, 8, roundRectPaint);

		if (height != 0) {

			for (int i = 0; i < mTotalWidth; i++) {

				// 减400只是为了控制波纹绘制的y的在屏幕的位置，大家可以改成一个变量，然后动态改变这个变量，从而形成波纹上升下降效果
				// 绘制第一条水波纹
				canvas.drawLine(i, mTotalHeight - mResetOneYPositions[i] - height, i,
						mTotalHeight,
						mWavePaint);

				// 绘制第二条水波纹
				canvas.drawLine(i, mTotalHeight - mResetTwoYPositions[i] - height, i,
						mTotalHeight,
						mWavePaint);
			}

			// 改变两条波纹的移动点
			mXOneOffset += mXOffsetSpeedOne;
			mXTwoOffset += mXOffsetSpeedTwo;

			// 如果已经移动到结尾处，则重头记录
			if (mXOneOffset >= mTotalWidth) {
				mXOneOffset = 0;
			}
			if (mXTwoOffset > mTotalWidth) {
				mXTwoOffset = 0;
			}
		}
		// 引发view重绘，一般可以考虑延迟20-30ms重绘，空出时间片
		postInvalidate();
	}

	private void resetPositonY() {
		// mXOneOffset代表当前第一条水波纹要移动的距离
		int yOneInterval = mYPositions.length - mXOneOffset;
		// 使用System.arraycopy方式重新填充第一条波纹的数据
		System.arraycopy(mYPositions, mXOneOffset, mResetOneYPositions, 0, yOneInterval);
		System.arraycopy(mYPositions, 0, mResetOneYPositions, yOneInterval, mXOneOffset);

		int yTwoInterval = mYPositions.length - mXTwoOffset;
		System.arraycopy(mYPositions, mXTwoOffset, mResetTwoYPositions, 0,
				yTwoInterval);
		System.arraycopy(mYPositions, 0, mResetTwoYPositions, yTwoInterval, mXTwoOffset);
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		// 记录下view的宽高
		mTotalWidth = w;
		mTotalHeight = h;
		// 用于保存原始波纹的y值
		mYPositions = new float[mTotalWidth];
		// 用于保存波纹一的y值
		mResetOneYPositions = new float[mTotalWidth];
		// 用于保存波纹二的y值
		mResetTwoYPositions = new float[mTotalWidth];

		// 将周期定为view总宽度
		mCycleFactorW = (float) (2 * Math.PI / mTotalWidth);

		// 根据view总宽度得出所有对应的y值
		for (int i = 0; i < mTotalWidth; i++) {
			mYPositions[i] = (float) (STRETCH_FACTOR_A * Math.sin(mCycleFactorW * i) + OFFSET_Y);
		}
	}

	public void setHeight(int filledPercent) {
		int mTotalHeight;

		if (filledPercent < 0)
			this.height = 0;
		else if (filledPercent > 1000)
			this.height = 1000;
		else
			this.height = filledPercent;

	}

	public void setHeightPercentage(int filledPercent, double percentage) {
		if (filledPercent <= 0)
			this.height = 0;
		else if (filledPercent > 1000)
			this.height = 1000;
		else
			this.height = (int) MathExtend.multiply((double) mTotalHeight, percentage);
	}


	private int dipToPx(Context context, int dip) {
		try {
			DisplayMetrics dm = new DisplayMetrics();
			((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay()
					.getMetrics(dm);
			return (int) (dip * dm.density + 0.5f);
		} catch (Exception e) {
			return DisplayMetrics.DENSITY_DEFAULT;
		}
	}


}
