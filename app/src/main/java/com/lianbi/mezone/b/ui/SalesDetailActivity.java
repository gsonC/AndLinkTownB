package com.lianbi.mezone.b.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lianbi.mezone.b.bean.SalesMan;
import com.xizhi.mezone.b.R;

import cn.com.hgh.utils.TelPhoneUtills;
import cn.com.hgh.view.CircularImageView;

@SuppressLint("ResourceAsColor")
public class SalesDetailActivity extends BaseActivity {
	static final String[] TITLE = { "店员详情", "修改" };
	TextView tv_sales_detail_employee_number, tv_sales_detail_employee_name,
			tv_sales_detail_employee_phone, tv_sales_detail_employee_position;
	CircularImageView img_sales_detail;
	/**
	 * 店员数据
	 */
	SalesMan sortModel;
	private String phone;
	private final int SALSEEMPLYEEMODIFYACTIVITYCODE = 3005;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_sales_detail, HAVETYPE);
		sortModel = (SalesMan) getIntent().getSerializableExtra("SalesMan");
		initView();
		if (sortModel != null) {
			upView();
		}
	}

	private void initView() {
		setPageTitle(getString(R.string.activity_salesdetail_title));
		setPageRightText("修改");
		setPageRightTextColor(R.color.colores_news_01);
		img_sales_detail = (CircularImageView) findViewById(R.id.img_sales_detail);
		tv_sales_detail_employee_number = (TextView) findViewById(R.id.tv_sales_detail_employee_number);
		tv_sales_detail_employee_name = (TextView) findViewById(R.id.tv_sales_detail_employee_name);
		tv_sales_detail_employee_phone = (TextView) findViewById(R.id.tv_sales_detail_employee_phone);
		tv_sales_detail_employee_position = (TextView) findViewById(R.id.tv_sales_detail_employee_position);
	}

	public void click(View v) {
		switch (v.getId()) {
		case R.id.layout_sales_detail_employee_phone:
			TelPhoneUtills.launchPhone(this, phone);
			break;
		}
	}

	/**
	 * @初实话界面
	 */

	private void upView() {
		phone = sortModel.getSalesclerk_phone();
		tv_sales_detail_employee_number.setText(sortModel.getSalesclerk_id()
				+ "");
		tv_sales_detail_employee_name.setText(sortModel.getSalesclerk_name());
		tv_sales_detail_employee_phone.setText(phone);
		tv_sales_detail_employee_position
				.setText(sortModel.getSalesclerk_job());
		String url = sortModel.getSalesclerk_image();
		Glide.with(this).load(url).error(R.mipmap.defaultpeson)
				.into(img_sales_detail);
	}
	@Override
	protected void onTitleRightClickTv() {
		super.onTitleRightClickTv();
		Intent intent = new Intent(this, SalseEmplyeeModifyActivity.class);
		intent.putExtra("sortmodel", sortModel);
		startActivityForResult(intent, SALSEEMPLYEEMODIFYACTIVITYCODE);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK) {
			switch (requestCode) {
			case SALSEEMPLYEEMODIFYACTIVITYCODE:
				finish();
				break;
			}
		}

		super.onActivityResult(requestCode, resultCode, data);

	}
}
