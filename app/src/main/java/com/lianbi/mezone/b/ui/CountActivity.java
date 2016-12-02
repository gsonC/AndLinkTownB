package com.lianbi.mezone.b.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.TextView;

import com.lianbi.mezone.b.httpresponse.MyResultCallback;
import com.xizhi.mezone.b.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.LinkedList;

import cn.com.hgh.spinner.NiceSpinner;
import cn.com.hgh.utils.AbDateUtil;
import cn.com.hgh.utils.AbStrUtil;
import cn.com.hgh.utils.ContentUtils;
import cn.com.hgh.utils.Result;

public class CountActivity extends BaseActivity {

	private EditText tv_radiovalue;
	private TextView tvCountMoney;
	private LinkedList<String> data;

	private NiceSpinner niceSpinner;
	private String nicespinnerText;
	private String disC;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_count, NOTYPE);
		initview();
		spinner();

	}

	private void initview() {
		setPageTitle("折扣设置");
		setPageRightText("保存");
		tvCountMoney = (TextView) findViewById(R.id.tv_countMoney);
		tvCountMoney.setText("0≤普通会员<300");
	}

	private void spinner() {
		niceSpinner = (NiceSpinner) findViewById(R.id.nice_spinner);
		tv_radiovalue = (EditText) findViewById(R.id.tv_radiovalue);

		niceSpinner.setTextColor(Color.BLACK);

		data = new LinkedList<>(Arrays.asList("普通会员", "VIP1", "VIP2", "VIP3"));
		niceSpinner.attachDataSource(data);
		niceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				nicespinnerText = niceSpinner.getText().toString();
				if (data.get(position).equals("普通会员")) {
					getMemberCategoryList("VP0");
					tv_radiovalue.setText(disC);
					tvCountMoney.setText("0≤普通会员<300");
				} else if (data.get(position).equals("VIP1")) {
					getMemberCategoryList("VP1");
					tv_radiovalue.setText(disC);
					tvCountMoney.setText("300≤VIP2<1000");
				} else if (data.get(position).equals("VIP2")) {
					getMemberCategoryList("VP2");
					tv_radiovalue.setText(disC);
					tvCountMoney.setText("1000≤VIP3<3000");
				} else if (data.get(position).equals("VIP3")) {
					getMemberCategoryList("VP3");
					tv_radiovalue.setText(disC);
					tvCountMoney.setText("3000≤");
				}

			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {

			}
		});

	}

	/**
	 * 店铺会员等级折扣比例修改
	 */
	private void getupdateDistrictByBusinessId() {
		String reqTime = AbDateUtil.getDateTimeNow();
		String uuid = AbStrUtil.getUUID();

	String	input=tv_radiovalue.getText().toString();
		try {
			okHttpsImp.getupdateDistrict(new MyResultCallback<String>() {
				@Override
				public void onResponseResult(Result result) {
					finish();
				}

				@Override
				public void onResponseFailed(String msg) {
					ContentUtils.showMsg(CountActivity.this,"sssss");
				}
			}, uuid, "app", reqTime, BusinessId, nicespinnerText,input);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void onTitleRightClickTv() {
		super.onTitleRightClickTv();
		getupdateDistrictByBusinessId();
	}
/**
 * 店铺会员等级折扣比例修改
 */
private void getMemberCategoryList(String vipGrade){
	try {
		okHttpsImp.getMemberCategoryList(new MyResultCallback<String>() {
			@Override
			public void onResponseResult(Result result) {
				String reString = result.getData();
				if (reString != null) {
					try {
						JSONObject jsonObject=new JSONObject(reString);
						reString=jsonObject.getString("list");
						 disC=(new BigDecimal(jsonObject.getInt("discountRate"))).setScale(1,BigDecimal.ROUND_HALF_UP).toString();

					} catch (JSONException e) {
						e.printStackTrace();
					}
				}

			}

			@Override
			public void onResponseFailed(String msg) {

			}
		},uuid, "app", reqTime,BusinessId,vipGrade);
	} catch (Exception e) {
		e.printStackTrace();
	}
}
}