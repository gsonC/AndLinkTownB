package com.lianbi.mezone.b.ui;
/*
 * @创建者     Administrator
 * @创建时间   2016/8/11 9:58
 * @描述       添加会员会员信息管理
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

import com.alibaba.fastjson.JSON;
import com.lianbi.mezone.b.bean.MemberDetailsBean;
import com.lianbi.mezone.b.bean.MemberInfoBean;
import com.lianbi.mezone.b.httpresponse.MyResultCallback;
import com.lianbi.mezone.b.httpresponse.OkHttpsImp;
import com.lianbi.mezone.b.photo.PopupWindowHelper;
import com.lzy.okgo.OkGo;
import com.xizhi.mezone.b.R;

import java.math.BigDecimal;
import java.util.ArrayList;

import cn.com.hgh.timeselector.TimeSelectorE;
import cn.com.hgh.utils.AbDateUtil;
import cn.com.hgh.utils.AbStrUtil;
import cn.com.hgh.utils.ContentUtils;
import cn.com.hgh.utils.MathExtend;
import cn.com.hgh.utils.REGX;
import cn.com.hgh.utils.Result;
import cn.com.hgh.utils.ScreenUtils;
import cn.com.hgh.view.ClearEditText;
import cn.com.hgh.view.ContainsEmojiEditText;

public class AddNewMembersActivity extends BaseActivity {

	private ClearEditText mEditAddmemberPhone, mEditMembercardnumber, mEditIDnumber;
	private EditText mEtMemberbirthday, mEtMembercardtermofvalidity;
	private TextView mTvMunberadress, mTvRemarks, mTvMemberfile, mTvRecordsofconsumption, mTvIntegralrecord,
			mTvMunberadressVisable, mTvMunberremarksVisable, mTvAddmemberSex,
			mTvAddmembertag, mEditAddmemberPhone1, mTvMembertype, mTvAddmemberDiscount, mTvAddmemberMax, mTvAddmemberIntegral;
	private ContainsEmojiEditText mEditMembername;
	private LinearLayout mLltAddmemberaddress, mLltAddmemberremarks;
	private MemberInfoBean mMemberInfoBean;
	private View mViewVisibale, mPickSexView;
	private PopupWindow pw = null;
	private boolean mIsShow;
	private String mVipLabel = "";
	private String vipId = "";

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
		mIsShow = getIntent().getBooleanExtra("isShow", false);
		setPageRightText("保存");
		mTvMemberfile = (TextView) findViewById(R.id.tv_file_memberfile);//会员档案
		mTvMemberfile.setTextColor(AddNewMembersActivity.this.getResources().getColor(R.color.color_ff8208));
		mTvRecordsofconsumption = (TextView) findViewById(R.id.tv_file_recordsofconsumption);//消费记录
		mTvIntegralrecord = (TextView) findViewById(R.id.tv_file_integralrecord);//积分记录
		mEditAddmemberPhone = (ClearEditText) findViewById(R.id.edit_addmember_phone);//手机号码
		mEditAddmemberPhone1 = (TextView) findViewById(R.id.edit_addmember_phone1);//手机号码
		mTvMembertype = (TextView) findViewById(R.id.tv_membertype);//会员类别
		mTvAddmemberDiscount = (TextView) findViewById(R.id.tv_addmember_discount);//会员折扣
		mTvAddmemberMax = (TextView) findViewById(R.id.tv_addmember_max);//单笔最高折扣
		mTvAddmemberIntegral = (TextView) findViewById(R.id.tv_addmember_integral);//会员积分
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
		if (mIsShow) {
			setPageTitle("会员详情");
			llt_memberdetails.setVisibility(View.VISIBLE);
			llt_line.setVisibility(View.VISIBLE);
			mViewVisibale.setVisibility(View.VISIBLE);
			view_line1.setVisibility(View.VISIBLE);
			mMemberInfoBean = (MemberInfoBean) getIntent().getSerializableExtra("memberInfo");
			vipId = mMemberInfoBean.getVipId();
			getMemberInfo();

		} else {
			setPageTitle("添加新会员");
			llt_memberdetails.setVisibility(View.GONE);
			mViewVisibale.setVisibility(View.GONE);
			llt_line.setVisibility(View.GONE);
		}
		viewAdapter();
	}

	/**
	 * 获取会员详情
	 */
	private void getMemberInfo() {
		String reqTime = AbDateUtil.getDateTimeNow();
		String uuid = AbStrUtil.getUUID();
		try {
			okHttpsImp.getMemberDetails(uuid, "app", reqTime, OkHttpsImp.md5_key,
					userShopInfoBean.getBusinessId(), mMemberInfoBean.getVipId(), new MyResultCallback<String>() {
				@Override
				public void onResponseResult(Result result) {
					String reString = result.getData();
					if (!AbStrUtil.isEmpty(reString)) {
						MemberDetailsBean memberDetails = JSON.parseObject(reString,
								MemberDetailsBean.class);
						fillView(memberDetails);
					}
				}

				@Override
				public void onResponseFailed(String msg) {
					ContentUtils.showMsg(AddNewMembersActivity.this, "获取会员信息失败");
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 适配
	 */
	private void viewAdapter() {
		ArrayList<TextView> tvs20 = new ArrayList<>();
		tvs20.add((TextView) findViewById(R.id.tv_membertype2));//类别解释
		tvs20.add((TextView) findViewById(R.id.edit_addmember_phone2));//必填

		ArrayList<TextView> tvs25 = new ArrayList<>();
		tvs25.add((TextView) findViewById(R.id.tv_file_memberfile));//会员档案
		tvs25.add((TextView) findViewById(R.id.tv_file_recordsofconsumption));//消费记录
		tvs25.add((TextView) findViewById(R.id.tv_file_integralrecord));//积分记录

		ArrayList<TextView> tvs = new ArrayList<>();
		tvs.add((TextView) findViewById(R.id.edit_addmember_phone1));//手机号码
		tvs.add((TextView) findViewById(R.id.edit_addmember_phone));//手机号码ET

		tvs.add((TextView) findViewById(R.id.tv_membertype1));//会员类别
		tvs.add((TextView) findViewById(R.id.tv_membertype));//普通会员


		tvs.add((TextView) findViewById(R.id.tv_memberdiscount1));//会员折扣
		tvs.add((TextView) findViewById(R.id.tv_addmember_discount));//无
		tvs.add((TextView) findViewById(R.id.tv_addmember_max1));//单笔最高折扣
		tvs.add((TextView) findViewById(R.id.tv_addmember_max));//无
		tvs.add((TextView) findViewById(R.id.tv_addmember_integral));//0
		tvs.add((TextView) findViewById(R.id.tv_addmember_integral1));//会员积分

		tvs.add((TextView) findViewById(R.id.tv_addmember_type1));//会员标签
		tvs.add((TextView) findViewById(R.id.tv_addmembertag));//标签内容

		tvs.add((TextView) findViewById(R.id.tv_addmember_name));//会员姓名
		tvs.add((TextView) findViewById(R.id.edit_membername));//姓名内容

		tvs.add((TextView) findViewById(R.id.tv_addmember_sex1));//会员性别
		tvs.add((TextView) findViewById(R.id.tv_addmember_sex));//男女

		tvs.add((TextView) findViewById(R.id.tv_member_bir));//会员生日
		tvs.add((TextView) findViewById(R.id.et_memberbirthday));//生日日期

		tvs.add((TextView) findViewById(R.id.tv_addmember_careID));//会员卡号
		tvs.add((TextView) findViewById(R.id.edit_membercardnumber));//卡号number

		tvs.add((TextView) findViewById(R.id.tv_membercardtermofvalidity));//会员卡有效期
		tvs.add((TextView) findViewById(R.id.et_membercardtermofvalidity));//有效期时间

		tvs.add((TextView) findViewById(R.id.tv_IDnumber));//身份证号
		tvs.add((TextView) findViewById(R.id.edit_IDnumber));//身份证number

		tvs.add((TextView) findViewById(R.id.tv_munberadress_visable1));//联系地址
		tvs.add((TextView) findViewById(R.id.tv_munberadress_visable));//地址内容
		tvs.add((TextView) findViewById(R.id.tv_munberadress));//联系地址

		tvs.add((TextView) findViewById(R.id.tv_munberremarks_visable1));//备注
		tvs.add((TextView) findViewById(R.id.tv_munberremarks_visable));//备注
		tvs.add((TextView) findViewById(R.id.tv_remarks));//备注

		ScreenUtils.textAdaptationOn720(tvs20, this, 20);
		ScreenUtils.textAdaptationOn720(tvs25, this, 25);
		ScreenUtils.textAdaptationOn720(tvs, this, 27);
	}

	/**
	 * 填充布局
	 */
	private void fillView(MemberDetailsBean memberDetails) {
		if (memberDetails != null) {
			wordsAdapter(memberDetails.getVipPhone(), "请输入手机号码", mEditAddmemberPhone);
			wordsAdapter(memberDetails.getVipTypeObject().getTypeName(), "普通会员", mTvMembertype);
			Integer typeDiscountRatio = memberDetails.getVipTypeObject().getTypeDiscountRatio();
			Integer TypeMaxDiscount = memberDetails.getVipTypeObject().getTypeMaxDiscount();
			int vipIntegral = memberDetails.getVipIntegral();
			memberDiscount(typeDiscountRatio,mTvAddmemberDiscount,1);
			memberDiscount(TypeMaxDiscount,mTvAddmemberMax,2);
			mTvAddmemberIntegral.setText(vipIntegral+"");

			if(!"老板娘app".equals(memberDetails.getVipSource())){
				mEditAddmemberPhone.setFocusable(false);
			}

			if(null!=memberDetails.getLabels()&&memberDetails.getLabels().size()>0){
				int size = memberDetails.getLabels().size();
				StringBuffer labelName = new StringBuffer();
				for (int i=0;i<size;i++){
					if (i == (size - 1)) {
						labelName.append(memberDetails.getLabels().get(i).getLabelName());
					} else {
						labelName.append(memberDetails.getLabels().get(i).getLabelName() + ",");
					}
				}
				mTvAddmembertag.setText(labelName.toString());
			}else{
				mTvAddmembertag.setHint("请选择会员标签");
			}
			wordsAdapter(memberDetails.getVipName(), "请输入会员姓名", mEditMembername);
			if (2 == memberDetails.getVipSex()) {
				mTvAddmemberSex.setText("女");
			} else {
				mTvAddmemberSex.setText("男");
			}
			String vipBirthday = memberDetails.getVipBirthday();
			String vipValidityPeriod = memberDetails.getVipValidityPeriod();
			memberDate(vipBirthday,mEtMemberbirthday,1);
			memberDate(vipValidityPeriod,mEtMembercardtermofvalidity,2);
			wordsAdapter(memberDetails.getVipCardNo(), "请输入会员卡号", mEditMembercardnumber);
			wordsAdapter(memberDetails.getVipIdNo(), "请输入身份证号", mEditIDnumber);
			if (!TextUtils.isEmpty(memberDetails.getVipAddress())) {
				mTvMunberadressVisable.setVisibility(View.GONE);
				mTvMunberadress.setText(memberDetails.getVipAddress());
			} else {
				mTvMunberadressVisable.setVisibility(View.VISIBLE);
			}
			if (!TextUtils.isEmpty(memberDetails.getVipRemarks())) {
				mTvMunberremarksVisable.setVisibility(View.GONE);
				mTvRemarks.setText(memberDetails.getVipRemarks());
			} else {
				mTvMunberremarksVisable.setVisibility(View.VISIBLE);
			}
		}

	}

	/**
	 * 会员日期填充
	 */
	private void memberDate(String dates,TextView tvs,int i){
		if(!AbStrUtil.isEmpty(dates)){
			tvs.setText( AbDateUtil.getStringByFormat(Long.valueOf(dates)
					, AbDateUtil.dateFormatYMD));
		}else{
			switch (i){
				case 1:
					tvs.setHint("点击设置会员生日");
					break;
				case 2:
					tvs.setHint("点击设置会员卡有效期");
					break;
			}
		}
	}

	/**
	 * 会员折扣填充
	 */
	private void memberDiscount(Integer discount,TextView tvs,int i){
		int ratio=0;
		int several=0;
		if(1==i){
			ratio = 10;
			several = 1;
		}else{
			ratio = 100;
			several = 0;
		}

		if(discount!=null){
			tvs.setText(MathExtend.roundNew(new BigDecimal(discount)
					.divide(new BigDecimal(ratio)).doubleValue(), several));
		}else{
			tvs.setText("无");
		}
	}

	/**
	 * 会员EditText填充
	 */
	private void wordsAdapter(String memberAttribute, String hintwords, TextView tvs) {
		if (!TextUtils.isEmpty(memberAttribute)) {
			tvs.setText(memberAttribute + "");
		} else {
			tvs.setHint(hintwords + "");
		}
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
	private final int REQUEST_TAG = 4864;
	private final String ENDTIME = "2030-01-01 00:00";
	private final String STARTTIME = "1970-01-01 00:00:00";

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
			//	String birthday = mEtMemberbirthday.getText().toString().trim();
			//	String choseDate ="";
			//	if(!TextUtils.isEmpty(birthday)) {
			//		choseDate = AbDateUtil.getStringByFormat(birthday+" 00:00:00", AbDateUtil.dateFormatYMDHMS);
			//	}
				TimeSelectorE timeSelectorO = new TimeSelectorE(this,
						new TimeSelectorE.ResultHandler() {
							@Override
							public void handle(String time) {
								mEtMemberbirthday.setText(time);
							}
						}, STARTTIME,
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
				Intent member_tag = new Intent(this, SelectTagActivity.class);
				String lablename =mTvAddmembertag.getText().toString().trim();
				member_tag.putExtra("tagName",lablename);
				startActivityForResult(member_tag, REQUEST_TAG);
				break;
			case R.id.llt_addmemberaddress://会员地址
				Intent shop_address = new Intent(this, MemberAdressActivity.class);
				if (mIsShow) {
					shop_address.putExtra("address", mMemberInfoBean.getVipAddress());
				}
				startActivityForResult(shop_address, REQUEST_ADDRESS);
				break;
			case R.id.llt_addmemberremarks://会员备注
				Intent shop_connect = new Intent(this, MemberRemarksActivity.class);
				if (mIsShow) {
					shop_connect.putExtra("remarks", mMemberInfoBean.getVipRemarks());
				}
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
					mVipLabel = data.getStringExtra("tagID");
					if (!"".equals(tagContent)) {
						mTvAddmembertag.setText(tagContent);
					} else {
						mTvAddmembertag.setText("");
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

	@Override
	protected void onTitleRightClickTv() {
		super.onTitleRightClickTv();
		verify();
	}

	/**
	 * 数据校检
	 */
	private void verify() {
		String vipPhone = mEditAddmemberPhone.getText().toString().trim();//手机号
		String vipName = mEditMembername.getText().toString().trim();//会员姓名
		String vipSex = mTvAddmemberSex.getText().toString().trim();//会员性别
		if ("男".equals(vipSex)) {
			vipSex = "1";
		} else {
			vipSex = "2";
		}
		String vipBirthday = mEtMemberbirthday.getText().toString().trim();//会员生日
		String vipCardNo = mEditMembercardnumber.getText().toString().trim();//会员卡号
		String vipValidityPeriod = mEtMembercardtermofvalidity.getText().toString().trim();//会员有效期
		String vipIdNo = mEditIDnumber.getText().toString().trim();//会员身份证
		String vipAddress = mTvMunberadress.getText().toString().trim();//联系地址
		String vipRemarks = mTvRemarks.getText().toString().trim();//备注说明
		if (!TextUtils.isEmpty(vipPhone)) {
			if (!vipPhone.matches(REGX.REGX_MOBILE)) {
				ContentUtils.showMsg(AddNewMembersActivity.this, "请输入正确的手机号码");
				return;
			}
		} else {
			ContentUtils.showMsg(AddNewMembersActivity.this, "请输入手机号码");
			return;
		}
		String reqTime = AbDateUtil.getDateTimeNow();
		String uuid = AbStrUtil.getUUID();
		try {
			okHttpsImp.addOrUpdateMember(uuid, "app", reqTime, OkHttpsImp.md5_key, userShopInfoBean.getBusinessId(),
					mIsShow, vipPhone, vipId, vipName, vipSex, mVipLabel,
					vipIdNo, vipCardNo, vipAddress, vipBirthday,
					vipValidityPeriod, vipRemarks, new MyResultCallback<String>() {
						@Override
						public void onResponseResult(Result result) {
							if (mIsShow) {
								ContentUtils.showMsg(AddNewMembersActivity.this,
										"修改成功");
							} else {
								ContentUtils.showMsg(AddNewMembersActivity.this,
										"添加成功");
							}
							Intent intent = new Intent();
							setResult(RESULT_OK, intent);
							finish();
						}

						@Override
						public void onResponseFailed(String msg) {
							if (mIsShow) {
								ContentUtils.showMsg(AddNewMembersActivity.this,
										"修改成功");
							} else {
								ContentUtils.showMsg(AddNewMembersActivity.this,
										"添加成功");
							}
						}
					});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void onTitleLeftClick() {
		super.onTitleLeftClick();
		startActivity(new Intent(AddNewMembersActivity.this, MembersListActivity.class));
		finish();
	}
}