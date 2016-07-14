package com.lianbi.mezone.b.ui;
/*
 * @创建者     程显威
 * @创建时间   2016/7/4 11:46
 * @描述       预约功能
 *
 * @更新者     $Author$ 
 * @更新时间   $Date$
 * @更新描述
 */

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;

import com.lianbi.mezone.b.app.Constants;
import com.lianbi.mezone.b.httpresponse.API;
import com.xizhi.mezone.b.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class BookFunctionActivity extends BaseActivity {

	@Bind(R.id.llt_productclassification)
	LinearLayout llt_productclassification;
	@Bind(R.id.llt_productlibrary)
	LinearLayout llt_productlibrary;
	@Bind(R.id.llt_reservationorder)
	LinearLayout llt_reservationorder;

//	@OnClick({R.id.llt_productclassification, R.id.llt_productlibrary, R.id.llt_reservationorder})
//	public void onClick(View view) {

//	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_bookfunction, HAVETYPE);
		ButterKnife.bind(this);
		setPageTitle("预约");
		setListen();
	}

	private void setListen() {
		llt_productclassification.setOnClickListener(this);
		llt_productlibrary.setOnClickListener(this);
		llt_reservationorder.setOnClickListener(this);
	}

	@Override
	protected void onChildClick(View view) {
		super.onChildClick(view);
		switch (view.getId()) {
			case R.id.llt_productclassification://产品分类
				JumpWebActivity(1);
				break;
			case R.id.llt_productlibrary://产品库
				JumpWebActivity(2);
				break;
			case R.id.llt_reservationorder://批发订单
				JumpWebActivity(3);
				break;
		}
	}

	/**
	 * 跳转WebActivity(在webactivity加入对应字段否则返回失效)
	 */
	public void JumpWebActivity(int type) {
		Intent intent_web = new Intent(this, BookWebActivity.class);
		intent_web.putExtra("Re", true);
		intent_web.putExtra(Constants.NEDDLOGIN, false);
		intent_web.putExtra("NEEDNOTTITLE", false);
		if (1 == type) {
			if(null!=getUrl(type)&& !TextUtils.isEmpty(getUrl(type))){
				intent_web.putExtra(BookWebActivity.U, getUrl(type));
			}
		} else if (2 == type) {
			if(null!=getUrl(type)&& !TextUtils.isEmpty(getUrl(type))){
				intent_web.putExtra(BookWebActivity.U, getUrl(type));
			}
		} else if (3 == type) {
			if(null!=getUrl(type)&& !TextUtils.isEmpty(getUrl(type))){
			intent_web.putExtra(BookWebActivity.U, getUrl(type));
			}
		} else {
		}
		startActivity(intent_web);
	}


	/**
	 * 获取跳转URL
	 */
	public String getUrl(int type) {
		String bussniessId = userShopInfoBean.getBusinessId();
		switch (type) {
			case 1://产品分类 Product
				String productitemurl = API.TOSTORESERVICE + "/wcm/productType/queryTypeList/" + bussniessId;
				return productitemurl;
			case 2://产品库
				String url = API.TOSTORESERVICE + "/wcm/rss/product/" + bussniessId+"/productsList";
				return url;
			case 3://预约订单
				String bookurl = API.HOST_BOOK_MALL + "storeId=" + bussniessId + "&&flag=wl";
				return bookurl;
		}
		return "";
	}

}