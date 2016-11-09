package com.lianbi.mezone.b.ui;

import android.content.Intent;
import android.os.Bundle;

import com.lianbi.mezone.b.app.Constants;
import com.xizhi.mezone.b.R;

import cn.com.hgh.utils.ContentUtils;

/*
 * @创建者     master
 * @创建时间   2016/11/9 18:55
 * @描述       刚进入判断界面
 *
 * @更新者     $Author$ 
 * @更新时间   $Date$
 * @更新描述
 */
public class JumpActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_first);
		/**
		 * 是否第一次
		 */
		final boolean isNoFirstUse = ContentUtils.getSharePreBoolean(this,
				Constants.SHARED_PREFERENCE_NAME, Constants.IS_FIRST);

		if (!isNoFirstUse) {
			ContentUtils.putSharePre(JumpActivity.this,
					Constants.SHARED_PREFERENCE_NAME,
					Constants.IS_FIRST, true);
			startActivity(new Intent(JumpActivity.this,
					GuiderActivity.class));
		} else {
			startActivity(new Intent(JumpActivity.this,
					FourSecondActivity.class));
		}
		finish();
	}
}
