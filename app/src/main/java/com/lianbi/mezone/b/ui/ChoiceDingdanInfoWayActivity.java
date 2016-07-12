package com.lianbi.mezone.b.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.alibaba.fastjson.JSONObject;
import com.lianbi.mezone.b.app.Constants;
import com.lianbi.mezone.b.bean.DataObjecte;
import com.lianbi.mezone.b.httpresponse.API;
import com.xizhi.mezone.b.R;

import cn.com.hgh.utils.ContentUtils;
import cn.com.hgh.utils.CryptTool;
import cn.com.hgh.utils.JumpIntent;

public class ChoiceDingdanInfoWayActivity extends BaseActivity {

	private LinearLayout llt_app_dingdanway, llt_weixin_dingdanway, llt_huoyuan_dingdanway,llt_book_dingdanway;
	private boolean re = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_choice_way, NOTYPE);
		initView();
		setListen();
	}

	private void setListen() {
		llt_app_dingdanway.setOnClickListener(this);
		llt_weixin_dingdanway.setOnClickListener(this);
		llt_huoyuan_dingdanway.setOnClickListener(this);
		llt_book_dingdanway.setOnClickListener(this);
	}

	private void initView() {
		setPageTitle("订单管理");
		llt_app_dingdanway = (LinearLayout) findViewById(R.id.llt_app_dingdanway);
		llt_weixin_dingdanway = (LinearLayout) findViewById(R.id.llt_weixin_dingdanway);
		llt_huoyuan_dingdanway = (LinearLayout) findViewById(R.id.llt_huoyuan_dingdanway);
		llt_book_dingdanway = (LinearLayout) findViewById(R.id.llt_book_dingdanway);

		String DDFU = ContentUtils.getSharePreString(this,
				Constants.SHARED_PREFERENCE_NAME, Constants.DDFW);
		String HHPF = ContentUtils.getSharePreString(this,
				Constants.SHARED_PREFERENCE_NAME, Constants.HHPF);
		String YYDD = ContentUtils.getSharePreString(this,
				Constants.SHARED_PREFERENCE_NAME, Constants.YYDD);

		if (!"1".equals(DDFU)) {
			llt_weixin_dingdanway.setVisibility(View.GONE);
		}

		if (!"3".equals(HHPF)) {
			llt_huoyuan_dingdanway.setVisibility(View.GONE);
		}
		if (!"4".equals(YYDD)) {
			llt_book_dingdanway.setVisibility(View.GONE);
		}
	}

	@Override
	protected void onChildClick(View view) {
		boolean isLogin = ContentUtils.getLoginStatus(this);
		switch (view.getId()) {
			case R.id.llt_app_dingdanway:
				re = JumpIntent.jumpLogin_addShop(isLogin, API.ORDERDETAIL, this);
				if (re) {
					Intent intent = new Intent(this, DingdanInfoActivity.class);
					startActivity(intent);
				}
				break;

			case R.id.llt_weixin_dingdanway:
				re = JumpIntent.jumpLogin_addShop(isLogin, API.ORDERDETAIL, this);
				if (re) {
					Intent intent_web = new Intent(this, WebActivty.class);
					intent_web.putExtra(Constants.NEDDLOGIN, false);
					intent_web.putExtra("NEEDNOTTITLE", false);
					intent_web.putExtra("Re", true);
					intent_web.putExtra(WebActivty.U, getWechatMallUrl());
					startActivity(intent_web);
				}
				break;
			case R.id.llt_huoyuan_dingdanway:
				re = JumpIntent.jumpLogin_addShop(isLogin,API.ORDERDETAIL, this);
				if(re){
					Intent intent_web = new Intent(this,WebActivty.class);
					intent_web.putExtra(Constants.NEDDLOGIN, false);
					intent_web.putExtra("NEEDNOTTITLE", false);
					intent_web.putExtra("Re", true);
					intent_web.putExtra(WebActivty.U, getSupplygoodsUrl());
					startActivity(intent_web);
				}
				break;
			case R.id.llt_book_dingdanway:
				re = JumpIntent.jumpLogin_addShop(isLogin,API.ORDERDETAIL, this);
				if(re){
					Intent intent_web = new Intent(this,WebActivty.class);
					intent_web.putExtra(Constants.NEDDLOGIN, false);
					intent_web.putExtra("NEEDNOTTITLE", false);
					intent_web.putExtra("Re", true);
					intent_web.putExtra(WebActivty.U, getbooksUrl());
					startActivity(intent_web);
				}
				break;
		}
	}

	private String getbooksUrl() {
		String bussniessId = userShopInfoBean.getBusinessId();
		String url = API.HOST_BOOK_MALL+"storeId="+bussniessId+"&&flag=wl";
		return url;
	}

	private String getSupplygoodsUrl(){
		String bussniessId = userShopInfoBean.getBusinessId();
		String url = API.HOST_SUPPLYGOODS_MALL+"storeId="+bussniessId+"&orderStatus=1&sourceType=sws&flag=wl";
		return url;

	}


	private String getWechatMallUrl() {
		String bussniessId = userShopInfoBean.getBusinessId();
		String status = "1";
		String url = API.HOST_WECHAT_MALL;
		DataObjecte data = new DataObjecte();

		data.setOrderStatus(status);
		data.setStoreId(bussniessId);
		data.setFlag("wl");
		data.setSourceType("appAll");
		String dataJson = JSONObject.toJSON(data).toString();

		url = encryptionUrl(url, dataJson);

		return url;
	}

	/**
	 * 加密
	 */
	private String encryptionUrl(String url, String dataJson) {
		try {
			// 获得的明文数据
			String desStr = dataJson;
			// 转成字节数组
			byte src_byte[] = desStr.getBytes();

			// MD5摘要
			byte[] md5Str = CryptTool.md5Digest(src_byte);
			// 生成最后的SIGN
			String SING = CryptTool.byteArrayToHexString(md5Str);

			desStr = CryptTool.getBASE64(dataJson);

			return url + "sing=" + SING + "&data=" + desStr;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return "";
	}

}
