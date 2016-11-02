package cn.com.hgh.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.util.AttributeSet;
import android.view.View;

/*
 * @创建者     master
 * @创建时间   2016/11/2 10:43
 * @描述       ${TODO}
 *
 * @更新者     $Author$ 
 * @更新时间   $Date$
 * @更新描述   ${TODO}
 */
public class DottedLine extends View {
	public DottedLine(Context context, AttributeSet attrs) {
		super(context, attrs);

	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		Paint paint = new Paint();
		paint.setStyle(Paint.Style.STROKE);
		paint.setColor(Color.parseColor("#b4b4b4"));//颜色可以自己设置
		Path path = new Path();
		path.moveTo(0, 0);//起始坐标
		path.lineTo(0, 500);//终点坐标
		PathEffect effects = new DashPathEffect(new float[]{10, 10, 10, 10}, 1);//设置虚线的间隔和点的长度
		paint.setPathEffect(effects);
		canvas.drawPath(path, paint);
	}
}
