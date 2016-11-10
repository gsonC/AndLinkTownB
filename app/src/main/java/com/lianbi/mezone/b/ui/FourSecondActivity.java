package com.lianbi.mezone.b.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.lianbi.mezone.b.app.Constants;
import com.lianbi.mezone.b.bean.LoginBackBean;
import com.lianbi.mezone.b.bean.MyShopInfoBean;
import com.lianbi.mezone.b.httpresponse.MyResultCallback;
import com.xizhi.mezone.b.R;

import org.json.JSONException;
import org.json.JSONObject;

import cn.com.hgh.utils.AbDateUtil;
import cn.com.hgh.utils.AbStrUtil;
import cn.com.hgh.utils.ContentUtils;
import cn.com.hgh.utils.Result;

/*
 * @创建者     master
 * @创建时间   2016/11/9 19:03
 * @描述       4秒启动页
 *
 * @更新者     $Author$ 
 * @更新时间   $Date$
 * @更新描述
 */
public class FourSecondActivity extends BaseActivity {

	private TextView mTv_daojishi;
	private MyCountDownTimer mc;
	private boolean mIsLogin;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_foursecond);

		mTv_daojishi = (TextView) findViewById(R.id.tv_daojishi);

		mc = new MyCountDownTimer(4000,1000);
		mc.start();
		//mc.cancel();


		/**
		 * 如果登录过自动登录
		 */
		mIsLogin = ContentUtils.getLoginStatus(this);
		if (mIsLogin) {
			autoLogin();
		}

		/*new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {

				if(isLogin){
					startActivity(new Intent(FourSecondActivity.this,
							MainActivity.class));
				}else{
					startActivity(new Intent(FourSecondActivity.this,
							LoginAndRegisterActivity.class));
				}

				finish();
			}
		}, 4000);*/
	}

	/**
	 * 自动登录
	 */
	private void autoLogin() {
		String username = ContentUtils.getSharePreString(this,
				Constants.SHARED_PREFERENCE_NAME, Constants.USER_NAME);
		String password = ContentUtils.getSharePreString(this,
				Constants.SHARED_PREFERENCE_NAME, Constants.PASS_WORD);
		if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
			return;
		}
		String reqTime = AbDateUtil.getDateTimeNow();
		String uuid = AbStrUtil.getUUID();
		try {
			okHttpsImp.postUserLogin(false, new MyResultCallback<String>() {

				@Override
				public void onResponseResult(Result result) {
					String reString = result.getData();
					ContentUtils.putSharePre(FourSecondActivity.this,
							Constants.SHARED_PREFERENCE_NAME, Constants.LOGINED_IN,
							true);
					try {
						JSONObject jsonObject = new JSONObject(reString);
						String user = (String) jsonObject.getString("userModel");
						LoginBackBean backBean = JSON.parseObject(user,
								LoginBackBean.class);
						String businessInfo = (String) jsonObject
								.getString("businessModel");
						MyShopInfoBean myShopInfoBean = JSON.parseObject(
								businessInfo, MyShopInfoBean.class);
						if (myShopInfoBean != null) {
							userShopInfoBean.setAddress(myShopInfoBean.getAddress());
							userShopInfoBean.setShopName(myShopInfoBean
									.getBusinessName());
							userShopInfoBean.setIndustry_id(myShopInfoBean
									.getIndustryId());
							userShopInfoBean.setNikeName(myShopInfoBean
									.getContactName());
							userShopInfoBean.setPhone(myShopInfoBean.getMobile());
						}
						if (backBean != null) {
							userShopInfoBean.setUserId(backBean.getUserId());
							userShopInfoBean.setBusinessId(backBean
									.getDefaultBusiness());
							userShopInfoBean.setName(backBean.getUsername());
							userShopInfoBean.setPersonHeadUrl(backBean
									.getUserImage());
						}
						//Intent intent = new Intent();
						//intent.setClass(FirstActivity.this, MainActivity.class);
						//startActivity(intent);
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}

				@Override
				public void onResponseFailed(String msg) {
					ContentUtils.putSharePre(FourSecondActivity.this,
							Constants.SHARED_PREFERENCE_NAME, Constants.LOGINED_IN,
							false);
				}
			}, uuid, "app", reqTime, username, password);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	/**
	 * 继承 CountDownTimer 防范
	 *
	 * 重写 父类的方法 onTick() 、 onFinish()
	 */

	class MyCountDownTimer extends CountDownTimer {
		/**
		 *
		 * @param millisInFuture
		 *      表示以毫秒为单位 倒计时的总数
		 *
		 *      例如 millisInFuture=1000 表示1秒
		 *
		 * @param countDownInterval
		 *      表示 间隔 多少微秒 调用一次 onTick 方法
		 *
		 *      例如: countDownInterval =1000 ; 表示每1000毫秒调用一次onTick()
		 *
		 */
		public MyCountDownTimer(long millisInFuture, long countDownInterval) {
			super(millisInFuture, countDownInterval);
		}

		@Override
		public void onFinish() {
			mTv_daojishi.setText("done");

			if(mIsLogin){
				startActivity(new Intent(FourSecondActivity.this,
						MainActivity.class));
			}else{
				startActivity(new Intent(FourSecondActivity.this,
						LoginAndRegisterActivity.class));
			}

			finish();

		}

		@Override
		public void onTick(long millisUntilFinished) {
			mTv_daojishi.setText("倒计时(" + millisUntilFinished / 1000 + ")...");
		}
	}
}
