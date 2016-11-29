package com.lianbi.mezone.b.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.TextView;

import com.xizhi.mezone.b.R;

import java.util.Arrays;
import java.util.LinkedList;

import cn.com.hgh.spinner.NiceSpinner;

public class CountActivity extends BaseActivity {
/*

	private TextView text;
	private Spinner spinner;
	private ArrayAdapter adapter;
	private String typename;
*/
	private EditText tv_radiovalue;
  private TextView tvCountMoney;
	private LinkedList<String> data;
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
   private void spinner(){
	   NiceSpinner niceSpinner = (NiceSpinner) findViewById(R.id.nice_spinner);
	    tv_radiovalue = (EditText) findViewById(R.id.tv_radiovalue);
	   niceSpinner.setTextColor(Color.BLACK);

	    data=new LinkedList<>(Arrays.asList("普通会员", "VIP1", "VIP2", "VIP3"));
	   niceSpinner.attachDataSource(data);
	   niceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
		   @Override
		   public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
			   if (data.get(position).equals("普通会员")) {
				   tvCountMoney.setText("0≤普通会员<300");
			   }else if(data.get(position).equals("VIP1")){
				   tvCountMoney.setText("300≤VIP2<1000");
			   }
			   else if(data.get(position).equals("VIP2")){
				   tvCountMoney.setText("1000≤VIP3<3000");
			   }else if(data.get(position).equals("VIP3")){
				   tvCountMoney.setText("3000≤");
			   }
		   }

		   @Override
		   public void onNothingSelected(AdapterView<?> parent) {

		   }
	   });

	   }
	/*private void spinner() {


		spinner = (Spinner) findViewById(R.id.spinner);
		*//*spinner.setBackgroundColor(Color.WHITE);*//*
		//将可选内容与ArrayAdapter连接起来
		adapter = ArrayAdapter.createFromResource(this, R.array.songs, R.layout.spinner_item);

		//设置下拉列表的风格
		adapter.setDropDownViewResource(R.layout.dropdown_stytle);

		//将adapter2 添加到spinner中
		spinner.setAdapter(adapter);

		//添加事件Spinner事件监听
		spinner.setOnItemSelectedListener(new SpinnerXMLSelectedListener());
	}

	//使用XML形式操作
	class SpinnerXMLSelectedListener implements AdapterView.OnItemSelectedListener {
		public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long arg3) {
			if (adapter.getItem(position).equals("普通会员")) {
				tvCountMoney.setText("0≤普通会员<300");
			}else if(adapter.getItem(position).equals("VIP1")){
				tvCountMoney.setText("300≤VIP2<1000");
			}
			else if(adapter.getItem(position).equals("VIP2")){
				tvCountMoney.setText("1000≤VIP3<3000");
			}else if(adapter.getItem(position).equals("VIP3")){
				tvCountMoney.setText("3000≤");
			}

		}

		public void onNothingSelected(AdapterView<?> arg0) {

		}

	}*/

	/**
	 * 店铺会员等级折扣比例修改
	 */
     /*private void getupdateDistrictByBusinessId(){
       okHttpsImp.getupdateDistrict(new MyResultCallback<String>() {
		   @Override
		   public void onResponseResult(Result result) {

		   }

		   @Override
		   public void onResponseFailed(String msg) {

		   }
	   },uuid, "app", reqTime,BusinessId,);
	 }*/

  }
