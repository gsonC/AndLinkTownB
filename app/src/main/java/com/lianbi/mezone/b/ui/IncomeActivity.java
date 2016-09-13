package com.lianbi.mezone.b.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.lianbi.mezone.b.httpresponse.MyResultCallback;
import com.lianbi.mezone.b.httpresponse.OkHttpsImp;
import com.lianbi.mezone.b.photo.PopupWindowHelper;
import com.xizhi.mezone.b.R;

import cn.com.hgh.utils.AbDateUtil;
import cn.com.hgh.utils.AbStrUtil;
import cn.com.hgh.utils.AbViewUtil;
import cn.com.hgh.utils.ContentUtils;
import cn.com.hgh.utils.Result;

public class IncomeActivity extends BaseActivity {
	String optType;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_income,NOTYPE);
		initView();
		initPickView();
		getAmtFlow();
	}
	private void initView(){
		setPageTitle("收入明细");
		setPageRightText("全部");
	}
	/**
	 * pop
	 */
	PopupWindow pw = null;

	/**
	 * popView
	 */
	View pickView;
	 TextView mainpoplayout_tvlist,mainpoplayout_tvxia,mainpoplayout_tvincome;
	public void pickImage() {
		if (pw == null) {
			pw = PopupWindowHelper.createPopupWindow(pickView,
					(int) AbViewUtil.dip2px(this, 120),
					(int) AbViewUtil.dip2px(this, 100));
			pw.setAnimationStyle(R.style.slide_up_in_down_out);
		}
		pw.showAsDropDown(tvTitleRight,0,(int) AbViewUtil.dip2px(this, 2));

	}
	/**
	 * 初始化pop
	 */
	public void initPickView() {
		pickView = View.inflate(this, R.layout.mainpoplayout, null);
			 mainpoplayout_tvlist = (TextView) pickView
				.findViewById(R.id.mainpoplayout_tvlist);
		 mainpoplayout_tvxia = (TextView) pickView
				.findViewById(R.id.mainpoplayout_tvxia);
		 mainpoplayout_tvincome = (TextView) pickView
				.findViewById(R.id.mainpoplayout_tvincome);

	}

	@Override
	public void onClick(View view) {
		super.onClick(view);
		switch (view.getId()){
			case R.id.mainpoplayout_tvlist:
				optType="00";
				pw.dismiss();
				Intent intent=new Intent(this,MemberPointManage.class);
				startActivity(intent);
				break;
			case R.id.mainpoplayout_tvxia:
				optType="01";
				pw.dismiss();
				break;
			case R.id.mainpoplayout_tvincome:
				optType="02";
				pw.dismiss();
				break;
		}
	}

	@Override
	protected void onTitleRightClickTv() {
		super.onTitleRightClickTv();
		pickImage();
	}
	/**
	 * 收入明细
	 */
	 private void getAmtFlow(){

		 String reqTime = AbDateUtil.getDateTimeNow();
		 String uuid = AbStrUtil.getUUID();
		 System.out.println("optType"+optType);
		 System.out.println("userShopInfoBean.getUserId()"+userShopInfoBean.getUserId());
		 System.out.println("userShopInfoBean.getBusinessId()"+userShopInfoBean.getBusinessId());
		 try {
			 okHttpsImp.getIsAmtFlow(OkHttpsImp.md5_key,
					 userShopInfoBean.getUserId(),userShopInfoBean.getBusinessId(),
					 optType, uuid, "app", reqTime, new MyResultCallback<String>() {
				 @Override
				 public void onResponseResult(Result result) {
                 String restring=result.getData();
					 System.out.println("restring120"+restring);
				 }
				 @Override
				 public void onResponseFailed(String msg) {
					 ContentUtils.showMsg(IncomeActivity.this,"请求成功");
				 }
			 });
		 } catch (Exception e) {
			 e.printStackTrace();
		 }
	 }

}
