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
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.lianbi.mezone.b.bean.MemberInfoBean;
import com.lianbi.mezone.b.photo.PopupWindowHelper;
import com.xizhi.mezone.b.R;

import java.util.ArrayList;

import cn.com.hgh.timeselector.TimeSelectorE;
import cn.com.hgh.utils.AbDateUtil;
import cn.com.hgh.utils.AbStrUtil;
import cn.com.hgh.utils.ContentUtils;
import cn.com.hgh.utils.REGX;
import cn.com.hgh.utils.ScreenUtils;
import cn.com.hgh.view.ClearEditText;
import cn.com.hgh.view.ContainsEmojiEditText;

public class AddNewMembersActivity extends BaseActivity {

	private ClearEditText mEditAddmemberPhone;
	private EditText mEtMemberbirthday;
	private ClearEditText mEditMembercardnumber;
	private EditText mEtMembercardtermofvalidity;
	private ClearEditText mEditIDnumber;
	private TextView mTvMunberadress;
	private TextView mTvRemarks;
	private ContainsEmojiEditText mEditMembername;
	private LinearLayout mLltAddmemberaddress;
	private LinearLayout mLltAddmemberremarks;
	private TextView mTvMemberfile;
	private TextView mTvRecordsofconsumption;
	private TextView mTvIntegralrecord;
	private MemberInfoBean mMemberInfoBean;
	private TextView mTvMunberadressVisable;
	private TextView mTvMunberremarksVisable;
	private View mViewVisibale;
	private TextView mTvAddmemberSex;
	private View mPickSexView;
	private PopupWindow pw = null;
	private TextView mTvAddmembertag;
	private TextView mEditAddmemberPhone1;
	private TextView mTvMembertype;
	private TextView mTvAddmemberDiscount;
	private TextView mTvAddmemberMax;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_addnewmembers, HAVETYPE);
		initView();
		setLisenter();
		initPickSexView();
	}

	/**
	 * 性别选择框
	 */
	private void initPickSexView() {
		mPickSexView = View.inflate(this, R.layout.addmember_selectsex, null);
		mPickSexView.findViewById(R.id.btn_addmember_selectmale).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mTvAddmemberSex.setText("男");
				pw.dismiss();
			}
		});
		mPickSexView.findViewById(R.id.btn_addmember_selectfemale).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mTvAddmemberSex.setText("女");
				pw.dismiss();
			}
		});
		mPickSexView.findViewById(R.id.btn_addmember_cancel).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				pw.dismiss();
				System.out.println("取消");
			}
		});
		mPickSexView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				pw.dismiss();
			}
		});
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
		setPageRightText("保存");
		mTvMemberfile = (TextView) findViewById(R.id.tv_file_memberfile);//会员档案
		mTvMemberfile.setTextColor(AddNewMembersActivity.this.getResources().getColor(R.color.color_ff8208));
		mTvRecordsofconsumption = (TextView) findViewById(R.id.tv_file_recordsofconsumption);//消费记录
		mTvIntegralrecord = (TextView) findViewById(R.id.tv_file_integralrecord);//积分记录
		mEditAddmemberPhone = (ClearEditText) findViewById(R.id.edit_addmember_phone);//手机号码
		mEditAddmemberPhone1 = (TextView) findViewById(R.id.edit_addmember_phone1);//手机号码
		mTvMembertype = (TextView) findViewById(R.id.tv_membertype);//会员折扣
		mTvAddmemberMax = (TextView) findViewById(R.id.tv_addmember_max);//单笔最高折扣
		mTvAddmemberDiscount = (TextView) findViewById(R.id.tv_addmember_discount);
		mTvAddmembertag = (TextView) findViewById(R.id.tv_addmembertag);//会员标签
		mEditMembername = (ContainsEmojiEditText) findViewById(R.id.edit_membername);//会员姓名
		mTvAddmemberSex = (TextView) findViewById(R.id.tv_addmember_sex);//会员性别
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
		if (isShow) {
			setPageTitle("会员详情");
			llt_memberdetails.setVisibility(View.VISIBLE);
			llt_line.setVisibility(View.VISIBLE);
			mViewVisibale.setVisibility(View.VISIBLE);
			view_line1.setVisibility(View.VISIBLE);
			mMemberInfoBean = (MemberInfoBean) getIntent().getSerializableExtra("memberInfo");
			System.out.println("memberInfo" + mMemberInfoBean.getMemberPhone());
			fillView(mMemberInfoBean);
		} else {
			setPageTitle("添加新会员");
			llt_memberdetails.setVisibility(View.GONE);
			mViewVisibale.setVisibility(View.GONE);
			llt_line.setVisibility(View.GONE);
		}
		viewAdapter();
	}

	/**
	 * 适配
	 */
	private void viewAdapter() {
		ArrayList<TextView> tvs20 = new ArrayList<>();
		tvs20.add((TextView)findViewById(R.id.tv_membertype2));//类别解释
		tvs20.add((TextView)findViewById(R.id.edit_addmember_phone2));//必填

		ArrayList<TextView> tvs25 = new ArrayList<>();
		tvs25.add((TextView)findViewById(R.id.tv_file_memberfile));//会员档案
		tvs25.add((TextView)findViewById(R.id.tv_file_recordsofconsumption));//消费记录
		tvs25.add((TextView)findViewById(R.id.tv_file_integralrecord));//积分记录

		ArrayList<TextView> tvs = new ArrayList<>();
		tvs.add((TextView)findViewById(R.id.edit_addmember_phone1));//手机号码
		tvs.add((TextView)findViewById(R.id.edit_addmember_phone));//手机号码ET

		tvs.add((TextView)findViewById(R.id.tv_membertype1));//会员类别
		tvs.add((TextView)findViewById(R.id.tv_membertype));//普通会员


		tvs.add((TextView)findViewById(R.id.tv_memberdiscount1));//会员折扣
		tvs.add((TextView)findViewById(R.id.tv_addmember_discount));//无
		tvs.add((TextView)findViewById(R.id.tv_addmember_max1));//单笔最高折扣
		tvs.add((TextView)findViewById(R.id.tv_addmember_max));//无
		tvs.add((TextView)findViewById(R.id.tv_addmember_integral));//0
		tvs.add((TextView)findViewById(R.id.tv_addmember_integral1));//会员积分

		tvs.add((TextView)findViewById(R.id.tv_addmember_type1));//会员标签
		tvs.add((TextView)findViewById(R.id.tv_addmembertag));//标签内容

		tvs.add((TextView)findViewById(R.id.tv_addmember_name));//会员姓名
		tvs.add((TextView)findViewById(R.id.edit_membername));//姓名内容

		tvs.add((TextView)findViewById(R.id.tv_addmember_sex1));//会员性别
		tvs.add((TextView)findViewById(R.id.tv_addmember_sex));//男女

		tvs.add((TextView)findViewById(R.id.tv_member_bir));//会员生日
		tvs.add((TextView)findViewById(R.id.et_memberbirthday));//生日日期

		tvs.add((TextView)findViewById(R.id.tv_addmember_careID));//会员卡号
		tvs.add((TextView)findViewById(R.id.edit_membercardnumber));//卡号number

		tvs.add((TextView)findViewById(R.id.tv_membercardtermofvalidity));//会员卡有效期
		tvs.add((TextView)findViewById(R.id.et_membercardtermofvalidity));//有效期时间

		tvs.add((TextView)findViewById(R.id.tv_IDnumber));//身份证号
		tvs.add((TextView)findViewById(R.id.edit_IDnumber));//身份证number

		tvs.add((TextView)findViewById(R.id.tv_munberadress_visable1));//联系地址
		tvs.add((TextView)findViewById(R.id.tv_munberadress_visable));//地址内容
		tvs.add((TextView)findViewById(R.id.tv_munberadress));//联系地址

		tvs.add((TextView)findViewById(R.id.tv_munberremarks_visable1));//备注
		tvs.add((TextView)findViewById(R.id.tv_munberremarks_visable));//备注
		tvs.add((TextView)findViewById(R.id.tv_remarks));//备注

		ScreenUtils.textAdaptationOn720(tvs20,this,20);
		ScreenUtils.textAdaptationOn720(tvs25,this,25);
		ScreenUtils.textAdaptationOn720(tvs,this,27);
	}

	/**
	 * 填充布局
	 */
	private void fillView(MemberInfoBean memberInfoBean) {

	}

	/**
	 * 添加监听
	 */
	private void setLisenter() {
		mTvMemberfile.setOnClickListener(this);
		mTvRecordsofconsumption.setOnClickListener(this);
		mTvIntegralrecord.setOnClickListener(this);
		mTvAddmemberSex.setOnClickListener(this);
		mLltAddmemberaddress.setOnClickListener(this);
		mLltAddmemberremarks.setOnClickListener(this);
		mEtMembercardtermofvalidity.setOnClickListener(this);
		mEtMemberbirthday.setOnClickListener(this);
		mTvAddmembertag.setOnClickListener(this);
	}

	private final int REQUEST_REMARKS = 4862;
	private final int REQUEST_ADDRESS = 4863;
	private final int REQUEST_TAG= 4864;
	private final String ENDTIME = "2030-01-01 00:00";

	@Override
	protected void onChildClick(View view) {
		super.onChildClick(view);
		switch (view.getId()) {
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
			case R.id.tv_addmember_sex://选择性别
				if (pw == null) {
					pw = PopupWindowHelper.createPopupWindow(mPickSexView, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
					pw.setAnimationStyle(R.style.slide_up_in_down_out);
				}
				pw.showAtLocation(this.getWindow().getDecorView(), Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM, 0, 0);
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
						}, "1970-01-01 00:00:00",
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
								mEtMembercardtermofvalidity.setText(time);
							}
						}, AbDateUtil.getCurrentDate(AbDateUtil.dateFormatYMDHM),
						ENDTIME);
				timeSelectorP.setMode(TimeSelectorE.MODE.YMD);
				timeSelectorP.setTitle("会员卡有效期");
				timeSelectorP.show();

				break;
			case R.id.tv_addmembertag://会员标签
				Intent member_tag = new Intent(this,SelectTagActivity.class);
				startActivityForResult(member_tag,REQUEST_TAG);
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
				case REQUEST_TAG:
					String tagContent = data.getStringExtra("tagContent");
					String tagID = data.getStringExtra("tagID");
					if(!AbStrUtil.isEmpty(tagContent)&&!AbStrUtil.isEmpty(tagID)){
						mTvAddmembertag.setText(tagContent);
					}else{
						mTvAddmembertag.setHint("请选择会员标签");
					}
					break;
				case REQUEST_REMARKS://会员备注
					String remarks = data.getStringExtra("remarks");
					if (AbStrUtil.isEmpty(remarks)) {
						mTvMunberremarksVisable.setVisibility(View.VISIBLE);
					} else {
						mTvMunberremarksVisable.setVisibility(View.GONE);
						mTvRemarks.setText(remarks);
					}
					break;
				case REQUEST_ADDRESS://会员地址
					String address = data.getStringExtra("address");
					if (AbStrUtil.isEmpty(address)) {
						mTvMunberadressVisable.setVisibility(View.VISIBLE);
					} else {
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
		String memberTag = mTvAddmembertag.getText().toString().trim();//会员标签
		String memberName = mEditMembername.getText().toString().trim();//会员姓名
		String memberSex = mTvAddmemberSex.getText().toString().trim();//会员性别
		String memberBirthday = mEtMemberbirthday.getText().toString().trim();//会员生日
		String memberCardNumber = mEditMembercardnumber.getText().toString().trim();//会员卡号
		String MemberCardTermOfValidity = mEtMembercardtermofvalidity.getText().toString().trim();//会员有效期
		String IDNumber = mEditIDnumber.getText().toString().trim();//会员身份证
		String MunberAdress = mTvMunberadress.getText().toString().trim();//联系地址
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


		System.out.println("会员电话--" + memberPhone);
		System.out.println("会员标签--" + memberTag);
		System.out.println("会员姓名--" + memberName);
		System.out.println("会员性别--" + memberSex);
		System.out.println("会员生日--" + memberBirthday);
		System.out.println("会员卡号--" + memberCardNumber);
		System.out.println("会员卡有效期--" + MemberCardTermOfValidity);
		System.out.println("会员卡有效期--" + IDNumber);
		System.out.println("会员身份证--" + IDNumber);
		System.out.println("会员联系地址--" + MunberAdress);
		System.out.println("会员备注说明--" + remarks);

		ContentUtils.showMsg(AddNewMembersActivity.this,
				"添加成功");
		Intent intent = new Intent();
		setResult(RESULT_OK, intent);
		finish();
	}

	@Override
	protected void onTitleLeftClick() {
		super.onTitleLeftClick();
		startActivity(new Intent(AddNewMembersActivity.this, MembersListActivity.class));
		finish();
	}
}