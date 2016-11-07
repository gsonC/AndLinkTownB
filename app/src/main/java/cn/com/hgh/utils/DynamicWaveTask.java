package cn.com.hgh.utils;

import java.util.Timer;
import java.util.TimerTask;

import cn.com.hgh.view.DynamicWave;

/*
 * @创建者     master
 * @创建时间   2016/11/4 13:32
 * @描述       ${TODO}
 *
 * @更新者     $Author$ 
 * @更新时间   $Date$
 * @更新描述   ${TODO}
 */
public class DynamicWaveTask extends TimerTask {

	private Timer mTimer;
	private DynamicWave mDynamicWave;
	private int mHeight;
	private int start = 1;

	public DynamicWaveTask(Timer timer, DynamicWave dynamicWave, int height) {
		this.mTimer = timer;
		this.mDynamicWave = dynamicWave;
		this.mHeight = height;
	}

	@Override
	public void run() {
		++start;
		if (mHeight >= start) {
			mDynamicWave.setHeight(start);
		} else {
			mTimer.cancel();
		}
	}
}
