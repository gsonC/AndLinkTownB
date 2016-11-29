package com.lianbi.mezone.b.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.xizhi.mezone.b.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.com.hgh.utils.ContentUtils;

/**
 * 商户营销
 */
public class BusinessMarketingActivity extends BaseActivity {

	@Bind(R.id.ll_bussinessMaking_manage)
	LinearLayout llBussinessMakingManage;
	@Bind(R.id.ll_bussinessMaking_youhuijuan)
	LinearLayout llBussinessMakingYouhuijuan;
	@Bind(R.id.ll_bussinessMaking_wenzhang)
	LinearLayout llBussinessMakingWenzhang;
	@Bind(R.id.ll_bussinessMaking_message)
	LinearLayout llBussinessMakingMessage;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_business_marketing, NOTYPE);
		ButterKnife.bind(this);
		initView();
	}

	private void initView() {
		setPageTitle(getString(R.string.activity_businessmarketing_title));
		llBussinessMakingManage.setOnClickListener(this);
		llBussinessMakingYouhuijuan.setOnClickListener(this);
		llBussinessMakingWenzhang.setOnClickListener(this);
		llBussinessMakingMessage.setOnClickListener(this);

	}

	@Override
	public void onClick(View view) {
		super.onClick(view);
		switch (view.getId()){
			case R.id.ll_bussinessMaking_manage://会员管理
				Intent intent=new Intent(this,MembersComeMember.class);
				startActivity(intent);
				break;
			case R.id.ll_bussinessMaking_youhuijuan://优惠券管理
				//startActivity(new Intent(this,CouponManagerActivity.class));
				ContentUtils.showMsg(this,"正在建设中");
				break;
			case R.id.ll_bussinessMaking_wenzhang://营销信息管理
				//startActivity(new Intent(this,MarketingMsgGlActivity.class));
				ContentUtils.showMsg(this,"正在建设中");
				break;
			case R.id.ll_bussinessMaking_message://营销文章
				ContentUtils.showMsg(this,"正在建设中");
				break;

		}
	}

}
