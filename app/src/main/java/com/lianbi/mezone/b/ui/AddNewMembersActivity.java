package com.lianbi.mezone.b.ui;
/*
 * @创建者     Administrator
 * @创建时间   2016/8/11 9:58
 * @描述       ${TODO}
 *
 * @更新者     $Author$ 
 * @更新时间   $Date$
 * @更新描述   ${TODO}
 */

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lianbi.mezone.b.bean.MemberInfoBean;
import com.xizhi.mezone.b.R;

import cn.com.hgh.timeselector.TimeSelectorE;
import cn.com.hgh.utils.AbDateUtil;
import cn.com.hgh.utils.AbStrUtil;
import cn.com.hgh.utils.ContentUtils;
import cn.com.hgh.utils.REGX;
import cn.com.hgh.view.ClearEditText;
import cn.com.hgh.view.ContainsEmojiEditText;

public class AddNewMembersActivity extends BaseActivity {

	private ClearEditText mEditAddmemberPhone;
	private ImageView mImgMemberlabel;
	private EditText mEtMemberbirthday;
	private ClearEditText mEditMembercardnumber;
	private EditText mEtMembercardtermofvalidity;
	private ClearEditText mEditIDnumber;
	private TextView mTvMunberadress;
	private TextView mTvRemarks;
	private ContainsEmojiEditText mEditMembername;
	private LinearLayout mLltAddmemberaddress;
	private LinearLayout mLltAddmemberremarks;
	private String mGender;
	private TextView mTvMemberfile;
	private TextView mTvRecordsofconsumption;
	private TextView mTvIntegralrecord;
	private MemberInfoBean mMemberInfoBean;
	private TextView mTvMunberadressVisable;
	private TextView mTvMunberremarksVisable;
	private View mViewVisibale;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_addnewmembers, HAVETYPE);
		initView();
		setLisenter();
	}

	/**
	 * 初始化View
	 */
	private void initView() {
		LinearLayout llt_memberdetails = (LinearLayout) findViewById(R.id.llt_memberdetails);
		mViewVisibale = findViewById(R.id.view_visibale);
		LinearLayout llt_line = (LinearLayout) findViewById(R.id.llt_line);
		View view_line1 = findViewById(R.id.view_line1);
		boolean isShow = getIntent().getBooleanExtra("isShow", false);
		if (isShow) {
			setPageTitle("会员详情");
			llt_memberdetails.setVisibility(View.VISIBLE);
			llt_line.setVisibility(View.VISIBLE);
			mViewVisibale.setVisibility(View.VISIBLE);
			view_line1.setVisibility(View.VISIBLE);
			mMemberInfoBean = (MemberInfoBean) getIntent().getSerializableExtra("memberInfo");
			System.out.println("memberInfo" + mMemberInfoBean.getMemberPhone());
		} else {
			setPageTitle("添加新会员");
			llt_memberdetails.setVisibility(View.GONE);
			mViewVisibale.setVisibility(View.GONE);
			llt_line.setVisibility(View.GONE);
		}
		setPageRightText("保存");
		mTvMemberfile = (TextView) findViewById(R.id.tv_file_memberfile);//会员记录
		mTvMemberfile.setTextColor(AddNewMembersActivity.this.getResources().getColor(R.color.color_ff8208));
		mTvRecordsofconsumption = (TextView) findViewById(R.id.tv_file_recordsofconsumption);//消费记录
		mTvIntegralrecord = (TextView) findViewById(R.id.tv_file_integralrecord);//积分记录
		mEditAddmemberPhone = (ClearEditText) findViewById(R.id.edit_addmember_phone);//手机号码
		mImgMemberlabel = (ImageView) findViewById(R.id.img_memberlabel);//会员标签
		mEditMembername = (ContainsEmojiEditText) findViewById(R.id.edit_membername);//会员姓名
		mEtMemberbirthday = (EditText) findViewById(R.id.et_memberbirthday);//会员生日
		mEditMembercardnumber = (ClearEditText) findViewById(R.id.edit_membercardnumber);//会员卡号
		mEtMembercardtermofvalidity = (EditText) findViewById(R.id.et_membercardtermofvalidity);//会员有效期
		mEditIDnumber = (ClearEditText) findViewById(R.id.edit_IDnumber);//身份证号
		mTvMunberadress = (TextView) findViewById(R.id.tv_munberadress);//联系地址
		mTvRemarks = (TextView) findViewById(R.id.tv_remarks);//备注说明
		mLltAddmemberaddress = (LinearLayout) findViewById(R.id.llt_addmemberaddress);//联系地址
		mLltAddmemberremarks = (LinearLayout) findViewById(R.id.llt_addmemberremarks);//备注说明
		mTvMunberadressVisable = (TextView) findViewById(R.id.tv_munberadress_visable);
		mTvMunberremarksVisable = (TextView) findViewById(R.id.tv_munberremarks_visable);
	}

	/**
	 * 添加监听
	 */
	private void setLisenter() {
		mTvMemberfile.setOnClickListener(this);
		mTvRecordsofconsumption.setOnClickListener(this);
		mTvIntegralrecord.setOnClickListener(this);
		mLltAddmemberaddress.setOnClickListener(this);
		mLltAddmemberremarks.setOnClickListener(this);
		mEtMembercardtermofvalidity.setOnClickListener(this);
		mEtMemberbirthday.setOnClickListener(this);
	}

	private final int REQUEST_REMARKS = 4862;
	private final int REQUEST_ADDRESS = 4863;
	private final String ENDTIME = "2030-01-01 00:00";

	@Override
	protected void onChildClick(View view) {
		super.onChildClick(view);
		switch (view.getId()) {
			case R.id.tv_file_memberfile://会员档案
				break;
			case R.id.tv_file_recordsofconsumption://消费记录
				Intent records_intent = new Intent(AddNewMembersActivity.this, RecordsOfConsumptionActivity.class);
				records_intent.putExtra("memberInfo", mMemberInfoBean);
				startActivity(records_intent);
				break;
			case R.id.tv_file_integralrecord://积分记录
				Intent integral_intent = new Intent(AddNewMembersActivity.this, IntegralRecordActivity.class);
				integral_intent.putExtra("memberInfo", mMemberInfoBean);
				startActivity(integral_intent);
				break;
			case R.id.et_memberbirthday://会员生日
		//		DateTimePickDialogUtil dateTimePickbirthday = new DateTimePickDialogUtil(AddNewMembersActivity.this, AbDateUtil.getCurrentDate("yyyy年MM月dd日") + "");
		//		dateTimePickbirthday.dateTimePicKDialog(mEtMemberbirthday);

				TimeSelectorE timeSelectorO = new TimeSelectorE(this,
						new TimeSelectorE.ResultHandler() {
							@Override
							public void handle(String time) {
								mEtMemberbirthday.setText(time);
							}
						}, "1980-01-01 00:00:00",
						ENDTIME);
				timeSelectorO.setMode(TimeSelectorE.MODE.YMD);
				timeSelectorO.setTitle("会员生日");
				timeSelectorO.show();
			//	AbDateUtil.getCurrentDate(AbDateUtil.dateFormatYMDHM);
			//	AbDateUtil.getDateByFormat("1980-01-01 00:00:00",AbDateUtil.dateFormatYMDHM);

				break;
			case R.id.et_membercardtermofvalidity://会员卡有效期
			//	DateTimePickDialogUtil dateTimePickvalidity = new DateTimePickDialogUtil(AddNewMembersActivity.this, AbDateUtil.getCurrentDate("yyyy年MM月dd日") + "");
			//	dateTimePickvalidity.dateTimePicKDialog(mEtMembercardtermofvalidity);

				TimeSelectorE timeSelectorP = new TimeSelectorE(this,
						new TimeSelectorE.ResultHandler() {
							@Override
							public void handle(String time) {
								mEtMemberbirthday.setText(time);
							}
						}, AbDateUtil.getCurrentDate(AbDateUtil.dateFormatYMDHM),
						ENDTIME);
				timeSelectorP.setMode(TimeSelectorE.MODE.YMD);
				timeSelectorP.setTitle("会员卡有效期");
				timeSelectorP.show();

				break;
			case R.id.img_memberlabel://会员标签

				break;
			case R.id.llt_addmemberaddress://会员地址
				Intent shop_address = new Intent(this, MemberAdressActivity.class);
				shop_address.putExtra("address", "");
				startActivityForResult(shop_address, REQUEST_ADDRESS);
				break;
			case R.id.llt_addmemberremarks://会员备注
				Intent shop_connect = new Intent(this, MemberRemarksActivity.class);
				shop_connect.putExtra("remarks", "");
				startActivityForResult(shop_connect, REQUEST_REMARKS);
				break;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) {
			switch (requestCode) {
				case REQUEST_REMARKS:
					String remarks = data.getStringExtra("remarks");
					if(AbStrUtil.isEmpty(remarks)){
						mTvMunberremarksVisable.setVisibility(View.VISIBLE);
					}else{
						mTvMunberremarksVisable.setVisibility(View.GONE);
						mTvRemarks.setText(remarks);
					}
					break;
				case REQUEST_ADDRESS:
					String address = data.getStringExtra("address");
					if(AbStrUtil.isEmpty(address)){
						mTvMunberadressVisable.setVisibility(View.VISIBLE);
					}else{
						mTvMunberadressVisable.setVisibility(View.GONE);
						mTvMunberadress.setText(address);
					}

					break;
			}
		}
	}

	/**
	 * 右上角点击事件
	 */
	@Override
	protected void onTitleRightClickTv() {
		verify();
	}

	/**
	 * 数据校检
	 */
	private void verify() {
		String memberPhone = mEditAddmemberPhone.getText().toString().trim();//手机号
		String membername = mEditMembername.getText().toString().trim();//会员姓名
		String memberbirthday = mEtMemberbirthday.getText().toString().trim();//会员生日
		String membercardnumber = mEditMembercardnumber.getText().toString().trim();//会员卡号
		String Membercardtermofvalidity = mEtMembercardtermofvalidity.getText().toString().trim();//会员有效期
		String IDnumber = mEditIDnumber.getText().toString().trim();//会员身份证
		String Munberadress = mTvMunberadress.getText().toString().trim();//联系地址
		String remarks = mTvRemarks.getText().toString().trim();//备注说明
		if (!TextUtils.isEmpty(memberPhone)) {
			if (!memberPhone.matches(REGX.REGX_MOBILE)) {
				ContentUtils.showMsg(AddNewMembersActivity.this, "请输入正确的手机号码");
				return;
			}
		} else {
			ContentUtils.showMsg(AddNewMembersActivity.this, "请输入手机号码");
			return;
		}
		System.out.println("memberPhone" + memberPhone);
		System.out.println("membername" + membername);
		System.out.println("memberbirthday" + memberbirthday);
		System.out.println("membercardnumber" + membercardnumber);
		System.out.println("Membercardtermofvalidity" + Membercardtermofvalidity);
		System.out.println("IDnumber" + IDnumber);
		System.out.println("Munberadress" + Munberadress);
		System.out.println("remarks" + remarks);
		System.out.println("mGender" + mGender);

		ContentUtils.showMsg(AddNewMembersActivity.this,
				"添加成功");
		Intent intent = new Intent();
		setResult(RESULT_OK, intent);
		finish();
	}

}