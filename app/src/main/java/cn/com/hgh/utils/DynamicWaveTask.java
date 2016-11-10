package cn.com.hgh.utils;

import java.util.Timer;
import java.util.TimerTask;

import cn.com.hgh.view.DynamicWave;

/*
 * @创建者     master
 * @创建时间   2016/11/4 13:32
 * @描述       首页波浪纹Task
 *
 * @更新者     $Author$ 
 * @更新时间   $Date$
 * @更新描述
 */
public class DynamicWaveTask extends TimerTask {

	private Timer mTimer;
	private DynamicWave mDynamicWave;
	private int mHeight;
	private int start = 1;

	private double first = 0.01;
	private double end;

	public DynamicWaveTask(Timer timer, DynamicWave dynamicWave, int height, double end) {
		this.mTimer = timer;
		this.mDynamicWave = dynamicWave;
		this.mHeight = height;
		this.end = end;
	}

	@Override
	public void run() {
		//	++start;
		//	if (mHeight >= start) {
		//		mDynamicWave.setHeight(start);
		//	} else {
		//		mTimer.cancel();
		//	}

		if (end >= first) {
			mDynamicWave.setHeightPercentage(start, first);
			first = first + 0.01;
		} else {
			mTimer.cancel();
		}

	}
}
