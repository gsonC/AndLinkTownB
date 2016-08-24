package com.lianbi.mezone.b.ui;
/*
 * @创建者     Administrator
 * @创建时间   2016/8/11 11:21
 * @描述       添加备注
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

import cn.com.hgh.utils.AbDateUtil;
import cn.com.hgh.utils.AbStrUtil;
import cn.com.hgh.utils.ContentUtils;

public class MemberRemarksActivity extends BaseActivity {

	private EditText mEditMemberremarks;
	private String remarks;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_memberremarks, NOTYPE);
		remarks = getIntent().getStringExtra("remarks");
		initView();
		setLisenter();
	}

	/**
	 * 初始化View
	 */
	private void initView() {
		setPageTitle("添加备注");
		setPageRightText("保存");
		mEditMemberremarks = (EditText) findViewById(R.id.edit_memberremarks);//会员备注
		if(!AbStrUtil.isEmpty(remarks)){
			mEditMemberremarks.setText(remarks);
		}else{
			mEditMemberremarks.setText("");
		}
	}


	/**
	 * 添加监听
	 */
	private void setLisenter() {
		mEditMemberremarks.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
									  int count) {
				if (s.length() >= 500) {
					ContentUtils.showMsg(MemberRemarksActivity.this, "反馈内容最多500个字");
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
		final String remarks = mEditMemberremarks.getText().toString();
		if (TextUtils.isEmpty(remarks.trim())){
			return;
		}
		String reqTime = AbDateUtil.getDateTimeNow();
		String uuid = AbStrUtil.getUUID();
	//	ContentUtils.showMsg(MemberRemarksActivity.this,
	//			"添加成功");
		Intent intent = new Intent();
		intent.putExtra("remarks", remarks);
		setResult(RESULT_OK, intent);
		finish();
	}
}