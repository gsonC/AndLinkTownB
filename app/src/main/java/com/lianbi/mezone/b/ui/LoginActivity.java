package com.lianbi.mezone.b.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.igexin.sdk.PushManager;
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
import cn.com.hgh.utils.REGX;
import cn.com.hgh.utils.Result;
import cn.com.hgh.view.CircularImageView;
import cn.com.hgh.view.DialogCommon;

@SuppressLint("ResourceAsColor")
public class LoginActivity extends BaseActivity {
	private EditText edit_login_new_phone, edit_login_new_pwd;

	TextView text_login_forget_pwd, text_login, text_login_retrieve_password;
	private static final int REQUEST_CODE_FORGET_PWD = 1007;
	private static final int REQUEST_CODE_REGIST_USER = 1008;
	private String mClientId;

	private CircularImageView civ_login_head;
	private  int    FROMLOGINPAGE=1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_login, HAVETYPE);
		initView();
		listen();
	}

	private void listen() {
		text_login_forget_pwd.setOnClickListener(this);
		text_login.setOnClickListener(this);
		text_login_retrieve_password.setOnClickListener(this);
	}

	/**
	 * 初始化View
	 */
	private void initView() {
		setPageTitle("登录");
		setPageRightTextColor(R.color.colores_news_01);
		edit_login_new_phone = (EditText) findViewById(R.id.edit_login_phone);
		edit_login_new_pwd = (EditText) findViewById(R.id.edit_login_pwd);
		text_login_forget_pwd = (TextView) findViewById(R.id.text_login_forget_pwd);
		text_login_retrieve_password = (TextView) findViewById(R.id.text_login_retrieve_password);
		civ_login_head = (CircularImageView) findViewById(R.id.civ_login_head);
		String userheadurl = ContentUtils.getSharePreString(this,
				Constants.SHARED_PREFERENCE_NAME, Constants.USERHEADURL);

		Glide.with(this).load(userheadurl).error(R.mipmap.default_head)
				.into(civ_login_head);
		text_login = (TextView) findViewById(R.id.text_login);

	}

	@Override
	protected void onChildClick(View v) {
		super.onChildClick(v);
		switch (v.getId()) {
		case R.id.text_login_forget_pwd:
			Intent intentresetpwd = new Intent(LoginActivity.this,
					ForgetPassWordActivty.class);
			startActivityForResult(intentresetpwd, REQUEST_CODE_FORGET_PWD);
			break;

		case R.id.text_login:
			login();
			break;
		case R.id.text_login_retrieve_password:
			Intent intent = new Intent(LoginActivity.this,
					RegisterActivity.class);
			startActivityForResult(intent, REQUEST_CODE_REGIST_USER);
			break;
		}
	}

	/**
	 * 登陆
	 */
	void login() {
		final String username = edit_login_new_phone.getText().toString()
				.trim();
		final String password = edit_login_new_pwd.getText().toString().trim();

		// 校对手机号
		if (!TextUtils.isEmpty(username)) {
			if (!username.matches(REGX.REGX_MOBILE)) {
				ContentUtils.showMsg(LoginActivity.this, "请输入正确的手机号");
				return;
			}
		} else {
			ContentUtils.showMsg(LoginActivity.this, "手机号不能为空");
			return;
		}
		// 校对密码
		if (TextUtils.isEmpty(password)) {
			ContentUtils.showMsg(LoginActivity.this, "密码不能为空");
			return;
		}

		if (password.length() < 6 || password.length() > 18) {
			ContentUtils.showMsg(LoginActivity.this, "密码长度为6到18位");
			return;
		}
		String reqTime = AbDateUtil.getDateTimeNow();
		String uuid = AbStrUtil.getUUID();

		/**
		 * 获取手机唯一识别码CID
		 */
		if (PushManager.getInstance().getClientid(this) != null) {
			mClientId = PushManager.getInstance().getClientid(this);
		}
		try {
			okHttpsImp.postUserLogin(true, new MyResultCallback<String>() {

				@Override
				public void onResponseResult(Result result) {
					String reString = result.getData();
					ContentUtils.putSharePre(LoginActivity.this,
							Constants.SHARED_PREFERENCE_NAME,
							Constants.LOGINED_IN, true);
					ContentUtils.putSharePre(LoginActivity.this,
							Constants.SHARED_PREFERENCE_NAME,
							Constants.USER_NAME, username);
					ContentUtils.putSharePre(LoginActivity.this,
							Constants.SHARED_PREFERENCE_NAME,
							Constants.PASS_WORD, password);
					try {
						JSONObject jsonObject = new JSONObject(reString);
						String businessInfo = (String) jsonObject
								.getString("businessModel");
						MyShopInfoBean myShopInfoBean=null;
						if (!TextUtils.isEmpty(businessInfo)) {

							myShopInfoBean = JSON.parseObject(
									businessInfo, MyShopInfoBean.class);
							if (myShopInfoBean != null) {
								userShopInfoBean.setAddress(myShopInfoBean
										.getAddress());
								userShopInfoBean.setIndustry_id(myShopInfoBean
										.getIndustryId());
								userShopInfoBean.setShopName(myShopInfoBean
										.getBusinessName());
								userShopInfoBean.setNikeName(myShopInfoBean
										.getContactName());
								userShopInfoBean.setPhone(myShopInfoBean
										.getContactPhone());
								ContentUtils.putSharePre(LoginActivity.this,
										Constants.USERTAG, Constants.USERSHOPNAME, myShopInfoBean.getBusinessName());
								ContentUtils.putSharePre(LoginActivity.this,
										Constants.USERTAG, Constants.USERSHOPLOGOURL, myShopInfoBean.getLogoUrl());
								ContentUtils.putSharePre(LoginActivity.this,
										Constants.USERTAG, Constants.USERSHOPADDRESS,myShopInfoBean.getAddress());
								ContentUtils.putSharePre(LoginActivity.this,
										Constants.USERTAG, Constants.USERSHOPPROVINCEID,myShopInfoBean.getProvinceId());
								ContentUtils.putSharePre(LoginActivity.this,
										Constants.USERTAG, Constants.USERSHOPLEAGUESCITY,myShopInfoBean.getCityCode());
								ContentUtils.putSharePre(LoginActivity.this,
										Constants.USERTAG, Constants.USERSHOPLEAGUESAREA,myShopInfoBean.getAreaCode());



									}
						}
						String user = (String) jsonObject
								.getString("userModel");
						LoginBackBean backBean = JSON.parseObject(user,
								LoginBackBean.class);
						if (backBean != null) {
							userShopInfoBean.setUserId(backBean.getUserId());
							userShopInfoBean.setBusinessId(backBean
									.getDefaultBusiness());
							userShopInfoBean.setName(backBean.getUsername());
							userShopInfoBean.setPersonHeadUrl(backBean
									.getUserImage());
							
							ContentUtils.putSharePre(LoginActivity.this, 
									Constants.USERTAG, Constants.USERID,backBean.getUserId());
							ContentUtils.putSharePre(LoginActivity.this, 
									Constants.USERTAG, Constants.USERNAME, backBean.getUsername());
							ContentUtils.putSharePre(LoginActivity.this,
									Constants.USERTAG, Constants.USERHEADURL, backBean.getUserImage());
							ContentUtils.putSharePre(LoginActivity.this,
									Constants.USERTAG, Constants.USERBUSINESSID, backBean.getDefaultBusiness());
							ContentUtils.putSharePre(LoginActivity.this,
									Constants.USERTAG, Constants.BUSINESSPHONE,backBean.getMobile());

						}
						if(!TextUtils.isEmpty(backBean.getDefaultBusiness())&&
								TextUtils.isEmpty(myShopInfoBean.getCityCode())||
								TextUtils.isEmpty(myShopInfoBean.getAreaCode())||
						        TextUtils.isEmpty(myShopInfoBean.getProvinceId())
								){
							DialogCommon dialogCommon = new DialogCommon(LoginActivity.this) {
								@Override
								public void onOkClick() {
									Intent intent = new Intent();
									intent.setClass(LoginActivity.this,AddShopInfoActivity.class);
									intent.putExtra("fromwhich",FROMLOGINPAGE);
									startActivity(intent);
									dismiss();
//									finish();

								}
								@Override
								public void onCheckClick() {

									dismiss();

								}
							};
							dialogCommon.setTextTitle("店铺信息尚未完善，请补全信息");
							dialogCommon.setTv_dialog_common_ok("确定");
							dialogCommon.setCanceledOnTouchOutside(false);
							dialogCommon.setTv_dialog_common_cancelV(View.GONE);
							dialogCommon.show();

						}else {
							Intent intent = new Intent();
							intent.putExtra("LoginBackBean", backBean);
							intent.setClass(LoginActivity.this, MainActivity.class);
							setResult(RESULT_OK, intent);
							startActivity(intent);
							finish();

						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}

				@Override
				public void onResponseFailed(String msg) {
					ContentUtils.putSharePre(LoginActivity.this,
							Constants.SHARED_PREFERENCE_NAME,
							Constants.LOGINED_IN, false);
				}
			}, uuid, "app", reqTime, username, password);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) {
			if (requestCode == REQUEST_CODE_FORGET_PWD) {// 忘记密码
				edit_login_new_phone.setText(data.getStringExtra("username"));
				edit_login_new_pwd.setText("");
			}
			if (requestCode == REQUEST_CODE_REGIST_USER) {// 注册
				if (data != null) {
					edit_login_new_phone.setText(data
							.getStringExtra("username"));
					edit_login_new_pwd.setText(data.getStringExtra("password"));
				}
			}
		}
	}

	/**
	 * 返回键时间间隔
	 */
	private long mExitTime;

}
