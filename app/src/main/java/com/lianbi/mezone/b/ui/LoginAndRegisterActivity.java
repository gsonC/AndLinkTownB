package com.lianbi.mezone.b.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.xizhi.mezone.b.R;

import cn.com.hgh.utils.AbViewUtil;
import cn.com.hgh.utils.ContentUtils;

/*
 * @创建者     master
 * @创建时间   2016/11/9 19:10
 * @描述       登陆和注册界面
 *
 * @更新者     $Author$ 
 * @更新时间   $Date$
 * @更新描述
 */
public class LoginAndRegisterActivity extends BaseActivity {

	private TextView mTv_logreg_exp, mTv_logreg_login, mTv_logreg_register;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_loginandregister);

		ImageView img_loginbg = (ImageView) findViewById(R.id.img_loginbg);

		img_loginbg.setImageBitmap(AbViewUtil.readBitMap(this,
				R.mipmap.loginbg));

		mTv_logreg_exp = (TextView) findViewById(R.id.tv_logreg_exp);
		mTv_logreg_login = (TextView) findViewById(R.id.tv_logreg_login);
		mTv_logreg_register = (TextView) findViewById(R.id.tv_logreg_register);

		mTv_logreg_exp.setOnClickListener(this);
		mTv_logreg_login.setOnClickListener(this);
		mTv_logreg_register.setOnClickListener(this);
	}

	@Override
	public void onClick(View view) {
		super.onClick(view);
		switch (view.getId()) {
			case R.id.tv_logreg_exp://快速体验
//				ContentUtils.showMsg(LoginAndRegisterActivity.this, getString(R.string.quicklogon));
				break;
			case R.id.tv_logreg_login://登陆
				startActivity(new Intent(LoginAndRegisterActivity.this,
						LoginActivity.class));
				break;
			case R.id.tv_logreg_register://注册
				startActivity(new Intent(LoginAndRegisterActivity.this,
						RegisterActivity.class));
				break;
		}
		finish();
	}

	/**
	 * 返回键时间间隔
	 */
	private long mExitTime;

	/**
	 * 返回键监听
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK) {

			if ((System.currentTimeMillis() - mExitTime) > 1000) {
				ContentUtils.showMsg(this,
						getResources().getString(R.string.balck_tuichu));

				mExitTime = System.currentTimeMillis();

			} else {

				finish();

			}

			return true;

		}

		return super.onKeyDown(keyCode, event);

	}

}
