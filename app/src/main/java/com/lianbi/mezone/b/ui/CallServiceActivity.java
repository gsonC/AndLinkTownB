package com.lianbi.mezone.b.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.xizhi.mezone.b.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.com.hgh.view.AbPullToRefreshView;

/**
 * 呼叫服务
 */
public class CallServiceActivity extends BaseActivity {

	@Bind(R.id.tv_all)
	TextView tvAll;
	@Bind(R.id.all_container)
	FrameLayout allContainer;
	@Bind(R.id.tv_vaild)
	TextView tvVaild;
	@Bind(R.id.valid_container)
	FrameLayout validContainer;
	@Bind(R.id.tv_invalid)
	TextView tvInvalid;
	@Bind(R.id.invalid_container)
	FrameLayout invalidContainer;
	@Bind(R.id.iv_empty_act_coupon_detail)
	ImageView ivEmptyActCouponDetail;
	@Bind(R.id.coupon_list)
	ListView couponList;
	@Bind(R.id.pull_to_refresh_coupon_list)
	AbPullToRefreshView pullToRefreshCouponList;
   private int currShowingIs;
	private static final int ALL_IS_SHOWING = 0;
	private static final int VALID_IS_SHOWING = 1;
	private static final int INVALID_IS_SHOWING = -1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_call_service, NOTYPE);
		ButterKnife.bind(this);
		initView();
	}

	private void initView() {
		setPageTitle("呼叫服务");
		setPageRightText("服务设置");
		currShowingIs=ALL_IS_SHOWING;
	}

	@Override
	public void onClick(View view) {
		super.onClick(view);
		switch (view.getId()){
			case R.id.all_container:
             allContainer.setBackgroundColor(getResources().getColor((R.color.color_3987fd)));
				tvAll.setBackgroundColor(getResources().getColor((R.color.color_3987fd)));
				validContainer.setBackgroundColor(getResources().getColor((R.color.white)));
				tvVaild.setBackgroundColor(getResources().getColor((R.color.color_3987fd)));
				invalidContainer.setBackgroundColor(getResources().getColor((R.color.white)));
				tvInvalid.setBackgroundColor(getResources().getColor((R.color.color_3987fd)));
				switchAdapter();
				break;
			case R.id.valid_container:
				allContainer.setBackgroundColor(getResources().getColor((R.color.white)));
				tvAll.setBackgroundColor(getResources().getColor((R.color.color_3987fd)));
				validContainer.setBackgroundColor(getResources().getColor((R.color.color_3987fd)));
				tvVaild.setBackgroundColor(getResources().getColor((R.color.color_3987fd)));
				invalidContainer.setBackgroundColor(getResources().getColor((R.color.white)));
				tvInvalid.setBackgroundColor(getResources().getColor((R.color.color_3987fd)));
				switchAdapter();
				break;
			case R.id.invalid_container:
				allContainer.setBackgroundColor(getResources().getColor((R.color.white)));
				tvAll.setBackgroundColor(getResources().getColor((R.color.color_3987fd)));
				validContainer.setBackgroundColor(getResources().getColor((R.color.white)));
				tvVaild.setBackgroundColor(getResources().getColor((R.color.color_3987fd)));
				invalidContainer.setBackgroundColor(getResources().getColor((R.color.color_3987fd)));
				tvInvalid.setBackgroundColor(getResources().getColor((R.color.color_3987fd)));
				switchAdapter();
				break;
		}
	}

	private void switchAdapter(){
		switch (currShowingIs){
			case ALL_IS_SHOWING:

				break;
			case VALID_IS_SHOWING:

				break;

			case INVALID_IS_SHOWING:

				break;

		}
	}

	@Override
	protected void onTitleRightClick1() {
		super.onTitleRightClick1();
		startActivity(new Intent(this,ServiceSetingActivity.class));
	}
}
