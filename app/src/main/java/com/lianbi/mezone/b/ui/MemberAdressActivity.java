package com.lianbi.mezone.b.ui;
/*
 * @创建者     Administrator
 * @创建时间   2016/8/11 12:45
 * @描述       ${TODO}
 *
 * @更新者     $Author$ 
 * @更新时间   $Date$
 * @更新描述   ${TODO}
 */

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.EditText;

import com.xizhi.mezone.b.R;

import cn.com.hgh.utils.AbStrUtil;
import cn.com.hgh.utils.ContentUtils;

public class MemberAdressActivity extends BaseActivity {
	private EditText mEditMemberaddress;
	private String address;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_memberadress, NOTYPE);
		address = getIntent().getStringExtra("address");
		initView();
		setLisenter();
	}

	/**
	 * 初始化View
	 */
	private void initView() {
		setPageTitle(getString(R.string.activity_memberadress_title));
		setPageRightText("保存");
		mEditMemberaddress = (EditText) findViewById(R.id.edit_memberaddress);
		if(!AbStrUtil.isEmpty(address)){
			mEditMemberaddress.setText(address);
		}else{
			mEditMemberaddress.setText("");
		}

	}

	/**
	 * 添加监听
	 */
	private void setLisenter() {
		mEditMemberaddress.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
									  int count) {
				if (s.length() >= 500) {
					ContentUtils.showMsg(MemberAdressActivity.this, "会员地址最多500个字");
				}
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
										  int after) {

			}

			@Override
			public void afterTextChanged(Editable s) {

			}
		});
	}

	/**
	 * 右上角点击事件
	 */
	@Override
	protected void onTitleRightClickTv() {
		final String remarks = mEditMemberaddress.getText().toString().trim();
		if (TextUtils.isEmpty(remarks)){
			return;
		}
	//	String reqTime = AbDateUtil.getDateTimeNow();
	//	String uuid = AbStrUtil.getUUID();
	//	ContentUtils.showMsg(MemberAdressActivity.this,
	//			"添加成功");
		Intent intent = new Intent();
		intent.putExtra("address", remarks);
		setResult(RESULT_OK, intent);
		finish();
	}
}