package com.lianbi.mezone.b.ui;

import android.os.Bundle;

import com.xizhi.mezone.b.R;

/**
 * 编辑制定 --- 说明
 * 
 * @time 下午12:28:42
 * @date 2016-1-18
 * @author hongyu.yang
 * 
 */
public class MemberExplainActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_member_explain, HAVETYPE);
		initView();
	}

	/**
	 * 初始化View
	 */
	private void initView() {
		setPageTitle(getString(R.string.activity_memberexplain_title));
	}

}
