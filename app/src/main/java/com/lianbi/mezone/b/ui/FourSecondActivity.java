package com.lianbi.mezone.b.ui;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.lianbi.mezone.b.app.Constants;
import com.lianbi.mezone.b.bean.LoginBackBean;
import com.lianbi.mezone.b.bean.MyShopInfoBean;
import com.lianbi.mezone.b.httpresponse.MyResultCallback;
import com.xizhi.mezone.b.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import cn.com.hgh.utils.AbDateUtil;
import cn.com.hgh.utils.AbStrUtil;
import cn.com.hgh.utils.AbViewUtil;
import cn.com.hgh.utils.ContentUtils;
import cn.com.hgh.utils.Result;
import cn.com.hgh.view.DialogCommon;

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
	private MsgReceiver mMsgReceiver;
	private Intent mIntent;
	private ImageView mImg_first_foursecond;
	private boolean isClick = false;
	private LoginBackBean mBackBean;
	private MyShopInfoBean mMyShopInfoBean;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_foursecond);

		mTv_daojishi = (TextView) findViewById(R.id.tv_daojishi);
		mImg_first_foursecond = (ImageView) findViewById(R.id.img_first_foursecond);
		mImg_first_foursecond.setImageBitmap(AbViewUtil.readBitMap(this,
				R.mipmap.first_foursecond));
		mTv_daojishi.setOnClickListener(this);
		mc = new MyCountDownTimer(4000, 1000);
		mc.start();
		//mc.cancel();

		/**
		 * 启动下载省市区Code Service
		 */
		startDownLoadAreaCodeService();
		if (isLogin) {
			autoLogin();
		}


	}

	@Override
	protected void onStart() {
		super.onStart();

	}

	@Override
	protected void onChildClick(View view) {
		super.onChildClick(view);
		switch (view.getId()) {
			case R.id.tv_daojishi:
				isClick = true;
				if (isLogin) {
					checkID();
					//startActivity(new Intent(FourSecondActivity.this,
					//		MainActivity.class));

				} else {
					startActivity(new Intent(FourSecondActivity.this,
							LoginAndRegisterActivity.class));
					finish();
				}


				break;
		}
	}

	/**
	 * 启动下载省市区Code Service
	 */
	private void startDownLoadAreaCodeService() {
		/**
		 * 动态注册广播
		 */
		mMsgReceiver = new MsgReceiver();
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction("com.lianbi.mezone.b.service.RECEIVER");
		registerReceiver(mMsgReceiver, intentFilter);
		mIntent = new Intent();
		mIntent.setAction("com.lianbi.mezone.b.MSG_ACTION");
		Intent eintent = new Intent(createExplicitFromImplicitIntent(this, mIntent));
		startService(eintent);
	}

	/**
	 * 下载广播回掉
	 */
	public class MsgReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			float progress = intent.getFloatExtra("progress", 0);
		}
	}

	/**
	 * 解决android5.0隐式启动问题
	 */
	public static Intent createExplicitFromImplicitIntent(Context context, Intent implicitIntent) {
		// Retrieve all services that can match the given intent
		PackageManager pm = context.getPackageManager();
		List<ResolveInfo> resolveInfo = pm.queryIntentServices(implicitIntent, 0);

		// Make sure only one match was found
		if (resolveInfo == null || resolveInfo.size() != 1) {
			return null;
		}

		// Get component info and create ComponentName
		ResolveInfo serviceInfo = resolveInfo.get(0);
		String packageName = serviceInfo.serviceInfo.packageName;
		String className = serviceInfo.serviceInfo.name;
		ComponentName component = new ComponentName(packageName, className);

		// Create a new intent. Use the old one for extras and such reuse
		Intent explicitIntent = new Intent(implicitIntent);

		// Set the component to be explicit
		explicitIntent.setComponent(component);

		return explicitIntent;
	}

	@Override
	protected void onDestroy() {
		unregisterReceiver(mMsgReceiver);
		super.onDestroy();
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
						mBackBean = JSON.parseObject(user,
								LoginBackBean.class);
						String businessInfo = (String) jsonObject
								.getString("businessModel");
						mMyShopInfoBean = JSON.parseObject(
								businessInfo, MyShopInfoBean.class);
						if (mMyShopInfoBean != null) {
							userShopInfoBean.setAddress(mMyShopInfoBean.getAddress());
							userShopInfoBean.setShopName(mMyShopInfoBean
									.getBusinessName());
							userShopInfoBean.setIndustry_id(mMyShopInfoBean
									.getIndustryId());
							userShopInfoBean.setNikeName(mMyShopInfoBean
									.getContactName());
							userShopInfoBean.setPhone(mMyShopInfoBean.getMobile());
						}
						if (mBackBean != null) {
							userShopInfoBean.setUserId(mBackBean.getUserId());
							userShopInfoBean.setBusinessId(mBackBean
									.getDefaultBusiness());
							userShopInfoBean.setName(mBackBean.getUsername());
							userShopInfoBean.setPersonHeadUrl(mBackBean
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
			e.printStackTrace();
		}
	}


	/**
	 * 继承 CountDownTimer 防范
	 * <p/>
	 * 重写 父类的方法 onTick() 、 onFinish()
	 */

	class MyCountDownTimer extends CountDownTimer {
		/**
		 * @param millisInFuture    表示以毫秒为单位 倒计时的总数
		 *                          <p/>
		 *                          例如 millisInFuture=1000 表示1秒
		 * @param countDownInterval 表示 间隔 多少微秒 调用一次 onTick 方法
		 *                          <p/>
		 *                          例如: countDownInterval =1000 ; 表示每1000毫秒调用一次onTick()
		 */
		public MyCountDownTimer(long millisInFuture, long countDownInterval) {
			super(millisInFuture, countDownInterval);
		}

		@Override
		public void onFinish() {
			if (!isClick) {

				if (isLogin) {

					checkID();

					//startActivity(new Intent(FourSecondActivity.this, MainActivity.class));

				} else {
					startActivity(new Intent(FourSecondActivity.this,
							LoginAndRegisterActivity.class));
					finish();
				}


			}
		}

		@Override
		public void onTick(long millisUntilFinished) {
			mTv_daojishi.setText(millisUntilFinished / 1000 + "S跳过");
		}
	}

	private int FROMLOGINPAGE = 1;

	private void checkID() {
        if(mBackBean==null||
		   mMyShopInfoBean==null){
			startActivity(new Intent(FourSecondActivity.this,
					LoginAndRegisterActivity.class));
			finish();
		}
		else
		if (!checkAreaID()) {
			startActivity(new Intent(FourSecondActivity.this,
					MainActivity.class));
			finish();
		} else {
			DialogCommon dialogCommon = new DialogCommon(FourSecondActivity.this) {
				@Override
				public void onOkClick() {
					Intent intent = new Intent();
					intent.setClass(FourSecondActivity.this, AddShopInfoActivity.class);
					intent.putExtra("fromwhich", FROMLOGINPAGE);
					startActivity(intent);
					dismiss();
					finish();

				}

				@Override
				public void onCheckClick() {

					dismiss();
					finish();

				}
			};
			dialogCommon.setTextTitle("店铺信息尚未完善，请补全信息");
			dialogCommon.setTv_dialog_common_ok("确定");
			dialogCommon.setCanceledOnTouchOutside(false);
			dialogCommon.setTv_dialog_common_cancelV(View.GONE);
			dialogCommon.show();
		}

	}


	private boolean checkAreaID() {
		return (!TextUtils.isEmpty(mBackBean.getDefaultBusiness()) &&
				TextUtils.isEmpty(mMyShopInfoBean.getCityCode()) ||
				TextUtils.isEmpty(mMyShopInfoBean.getAreaCode()) ||
				TextUtils.isEmpty(mMyShopInfoBean.getProvinceId())
		);
	}

}
