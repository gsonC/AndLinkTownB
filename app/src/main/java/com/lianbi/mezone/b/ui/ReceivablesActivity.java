package com.lianbi.mezone.b.ui;
/*
 * @创建者     Administrator
 * @创建时间   2016/8/26 10:49
 * @描述       电子支付服务协议
 *
 * @更新者     $Author$ 
 * @更新时间   $Date$
 * @更新描述
 */

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;

import com.lianbi.mezone.b.httpresponse.API;
import com.xizhi.mezone.b.R;

public class ReceivablesActivity extends BaseActivity implements
		OnCheckedChangeListener{

	private CheckBox mCbReceivanles;
	private TextView mTvReceivablesAgree,mTvAgree,mTvReceivablesAppend;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_receivables, NOTYPE);
		initView();
		setLisenter();
	}

	/**
	 * 初始化View
	 */
	private void initView() {
		setPageTitle("收款");
		mCbReceivanles = (CheckBox) findViewById(R.id.cb_receivanles);
		mTvReceivablesAgree = (TextView) findViewById(R.id.tv_receivables_agree);
		mTvAgree = (TextView) findViewById(R.id.tv_agree);
		mTvReceivablesAppend = (TextView) findViewById(R.id.tv_receivables_append);
		addUnderLineSpan();
	}

	/**
	 * 下划线
	 */
	private void addUnderLineSpan() {
		SpannableString spanString = new SpannableString(
				getString(R.string.click_register_agree2));
		UnderlineSpan span = new UnderlineSpan();
		spanString.setSpan(span, 0, spanString.length(),
				Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		mTvReceivablesAppend.append(spanString);
	}

	/**
	 * 添加监听
	 */
	private void setLisenter() {
		mTvReceivablesAppend.setOnClickListener(this);
		mTvReceivablesAgree.setOnClickListener(this);
		mCbReceivanles.setOnCheckedChangeListener(this);
	}

	@Override
	protected void onChildClick(View view) {
		super.onChildClick(view);
		switch (view.getId()){
			case R.id.tv_receivables_append:
				Intent intent = new Intent(this, WebActivty.class);
				intent.putExtra(WebActivty.U, API.HOST + API.PAYAGREEMENT);
				intent.putExtra("Re", true);
				startActivity(intent);
				break;
			case R.id.tv_receivables_agree:
				startActivity(new Intent(this,ReceivablesQRActivity.class));
				break;
		}
	}

	@SuppressLint("ResourceAsColor")
	@Override
	public void onCheckedChanged(CompoundButton cb, boolean check) {
		if (check) {
			mTvReceivablesAgree.setBackgroundResource(R.drawable.shape_login);
			mTvReceivablesAgree.setClickable(true);
		} else {
			mTvReceivablesAgree
					.setBackgroundResource(R.drawable.shape_check_login);
			mTvReceivablesAgree.setClickable(false);
		}
	}
}