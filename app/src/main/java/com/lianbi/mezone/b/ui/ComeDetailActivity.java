package com.lianbi.mezone.b.ui;

import cn.com.hgh.playview.ViewPagerEx;
import cn.com.hgh.utils.AbDateUtil;
import cn.com.hgh.utils.AbStrUtil;

/**
 * 到店明细
 */
public class ComeDetailActivity extends BaseActivity implements ViewPagerEx.OnPageChangeListener{



	/**
	 * 4.24	到店明细接口
	 */
	private void getOrder(){
		String reqTime = AbDateUtil.getDateTimeNow();
		String uuid = AbStrUtil.getUUID();

	}

	@Override
	public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

	}

	@Override
	public void onPageSelected(int position) {

	}

	@Override
	public void onPageScrollStateChanged(int state) {

	}
}
