package com.lianbi.mezone.b.ui;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lianbi.mezone.b.bean.BankBoundInfo;
import com.lianbi.mezone.b.bean.FinancialOfficeAmountBean;
import com.lianbi.mezone.b.bean.Status;
import com.lianbi.mezone.b.httpresponse.API;
import com.lianbi.mezone.b.httpresponse.MyResultCallback;
import com.lianbi.mezone.b.httpresponse.OkHttpsImp;
import com.xizhi.mezone.b.R;

import java.math.BigDecimal;
import java.text.DecimalFormat;

import cn.com.hgh.utils.AbDateUtil;
import cn.com.hgh.utils.AbStrUtil;
import cn.com.hgh.utils.ContentUtils;
import cn.com.hgh.utils.JumpIntent;
import cn.com.hgh.utils.MathExtend;
import cn.com.hgh.utils.Result;
import cn.com.hgh.utils.ScreenUtils;
import cn.com.hgh.view.DiaqlogNow;

/*
 * @创建者     master
 * @创建时间   2016/11/8 9:16
 * @描述       ${TODO}
 *
 * @更新者     $Author$ 
 * @更新时间   $Date$
 * @更新描述   ${TODO}
 */
public class FinancialActivity extends BaseActivity {

	private ImageView iv_recharge, iv_withdrawalsdetails, iv_withdrawals,
			iv_bankcard;
	private TextView tv_totalaccount, tv_shopaccount, tv_availablebalance,
			tv_takeinmoney, tv_shopincometoday, tv_freezingamount, tv_shopaccountword;
	private TextView tv_dongjiejine, tv_keyongyue, tv_tixianzhongyue, tv_dianpujinrishouru,
			tv_finalcial_ruledescription, tv_finalcial_oldrate, tv_finalcial_newrate;
	public double totalaccount = 0, shopaccount = 0, availablebalance = 0,
			takeinmoney = 0, shopincometoday = 0, freezingamount = 0;
	TextView tv_gz_rate, tv_gz_count, tv_Fdiscount_time, tv_Ediscount_time;
	LinearLayout lin_discount, n_safety;
	public boolean isReturn = false;
	public boolean isBank = false;
	private SwipeRefreshLayout swipe_caiwushi;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_financiaactivity, NOTYPE);
		initView();
		getFinancialOfficeClick();
		setLisenter();
	}

	private FinancialOfficeAmountBean financialOfficeAmountBean;

	/**
	 * 获取财务室各项收入
	 */
	private void getFinancialOfficeClick() {
		String reqTime = AbDateUtil.getDateTimeNow();
		String uuid = AbStrUtil.getUUID();
		try {
			okHttpsImp.getFinancialOfficeAmount(OkHttpsImp.md5_key, uuid, "app", reqTime, userShopInfoBean.getUserId(), userShopInfoBean.getBusinessId(), new MyResultCallback<String>() {
				@Override
				public void onResponseResult(Result result) {
					String reString = result.getData();
					if (!TextUtils.isEmpty(reString)) {
						financialOfficeAmountBean = JSON.parseObject(reString, FinancialOfficeAmountBean.class);
						tv_totalaccount.setText(MathExtend.roundNew(financialOfficeAmountBean.getAccountTotalIncome().divide(new BigDecimal(100)).doubleValue(), 2));// 账户总额
						tv_shopaccount.setText(MathExtend.roundNew(financialOfficeAmountBean.getStoreTotalIncome().divide(new BigDecimal(100)).doubleValue(), 2));// 店铺总额
						tv_availablebalance.setText(MathExtend.roundNew(financialOfficeAmountBean.getStoreBalance().divide(new BigDecimal(100)).doubleValue(), 2));// 可用余额
						tv_shopincometoday.setText(MathExtend.roundNew(financialOfficeAmountBean.getStoreTodayIncome().divide(new BigDecimal(100)).doubleValue(), 2));// 店铺今日收入
						tv_freezingamount.setText(MathExtend.roundNew(financialOfficeAmountBean.getSotreFrozenAmount().divide(new BigDecimal(100)).doubleValue(), 2));// 冻结中金额
						tv_takeinmoney.setText(MathExtend.roundNew(financialOfficeAmountBean.getStoreWithdrawAmount().divide(new BigDecimal(100)).doubleValue(), 2));// 提现中余额

						int cardinal = 100;
						double multiplicativecardinal = cardinal;
						DecimalFormat df = new DecimalFormat("0.00");

						if (0 != financialOfficeAmountBean.getRate().compareTo(BigDecimal.ZERO)) {
							tv_finalcial_oldrate.setText(df.format(MathExtend.multiply(financialOfficeAmountBean.getRate()
									.doubleValue(), multiplicativecardinal)) + "%");
						} else {
							tv_finalcial_oldrate.setText("0.00%");
						}

						if (0 != financialOfficeAmountBean.getCheapRate().compareTo(BigDecimal.ZERO)) {
							tv_finalcial_newrate.setText(" " + df.format(MathExtend.multiply(financialOfficeAmountBean.getCheapRate()
									.doubleValue(), multiplicativecardinal)) + "%");

						} else {
							tv_finalcial_newrate.setText(" 0.00%");
						}
					}
				}

				@Override
				public void onResponseFailed(String msg) {
					tv_totalaccount.setText(MathExtend.roundNew(0, 2));
					tv_shopaccount.setText(MathExtend.roundNew(0, 2));
					tv_availablebalance.setText(MathExtend.roundNew(0, 2));
					tv_takeinmoney.setText(MathExtend.roundNew(0, 2));
					tv_shopincometoday.setText(MathExtend.roundNew(0, 2));
				}

			});
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	protected void onResume() {
		super.onResume();
		getIsBand();// 查看银行卡信息
	}

	/**
	 * 查看银行卡信息
	 */
	protected boolean isBand = false;

	private void getIsBand() {

		String reqTime = AbDateUtil.getDateTimeNow();
		String uuid = AbStrUtil.getUUID();
		try {
			okHttpsImp.getIsTrue(OkHttpsImp.md5_key,
					BaseActivity.userShopInfoBean.getUserId(), "01", uuid,
					"app", reqTime, new MyResultCallback<String>() {

						@Override
						public void onResponseResult(Result result) {
							String resString = result.getData();
							if (!TextUtils.isEmpty(resString)) {
								BankBoundInfo bankBoundInfos = JSON
										.parseObject(resString,
												BankBoundInfo.class);
								if (null != bankBoundInfos.getBankAccountNo()
										&& !TextUtils.isEmpty(bankBoundInfos
										.getBankAccountNo())) {
									isBand = true;
								} else {
									isBand = false;
								}
							} else {
								isBand = false;
							}
						}

						@Override
						public void onResponseFailed(String msg) {
							isBand = false;
						}
					});
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void initView() {

		setPageTitle("财务室");
		n_safety = (LinearLayout) findViewById(R.id.n_safety);
		lin_discount = (LinearLayout) findViewById(R.id.lin_discount);
		iv_recharge = (ImageView) findViewById(R.id.iv_recharge);// 充值
		iv_withdrawalsdetails = (ImageView) findViewById(R.id.iv_withdrawalsdetails);// 体现明细
		iv_withdrawals = (ImageView) findViewById(R.id.iv_withdrawals);// 提现
		iv_bankcard = (ImageView) findViewById(R.id.iv_bankcard);// 银行卡
		tv_finalcial_ruledescription = (TextView) findViewById(R.id.tv_finalcial_ruledescription);//规则说明
		tv_finalcial_oldrate = (TextView) findViewById(R.id.tv_finalcial_oldrate);//老费率
		tv_finalcial_newrate = (TextView) findViewById(R.id.tv_finalcial_newrate);//新费率
		tv_finalcial_oldrate.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);//中间加横线
		tv_totalaccount = (TextView) findViewById(R.id.tv_totalaccount);// 账户总额(数字)
		tv_shopaccount = (TextView) findViewById(R.id.tv_shopaccount);// 店铺总额(数字)

		tv_freezingamount = (TextView) findViewById(R.id.tv_freezingamount);//冻结金额(数字)
		tv_availablebalance = (TextView) findViewById(R.id.tv_availablebalance);// 可用余额(数字)
		tv_takeinmoney = (TextView) findViewById(R.id.tv_takeinmoney);// 提现中余额(数字)
		tv_shopincometoday = (TextView) findViewById(R.id.tv_shopincometoday);// 店铺今日收入(数字)

		tv_dongjiejine = (TextView) findViewById(R.id.tv_dongjiejine);// 冻结金额
		tv_keyongyue = (TextView) findViewById(R.id.tv_keyongyue);// 可用余额
		tv_tixianzhongyue = (TextView) findViewById(R.id.tv_tixianzhongyue);// 提现中余额
		tv_dianpujinrishouru = (TextView) findViewById(R.id.tv_dianpujinrishouru);// 店铺总额
		tv_shopaccountword = (TextView) findViewById(R.id.tv_shopaccountword);// 店铺今日收入

		tv_gz_rate = (TextView) findViewById(R.id.tv_gz_rate);//pop手续费
		tv_gz_count = (TextView) findViewById(R.id.tv_gz_count);//pop优惠费率
		tv_Fdiscount_time = (TextView) findViewById(R.id.tv_Fdiscount_time);//开始时间
		tv_Ediscount_time = (TextView) findViewById(R.id.tv_Ediscount_time);//结束时间

		textAdaptation();

		swipe_caiwushi = (SwipeRefreshLayout) findViewById(R.id.swipe_caiwushi);
		swipe_caiwushi.setColorSchemeResources(R.color.colores_news_01, R.color.black);
		swipe_caiwushi.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

			@Override
			public void onRefresh() {
				getFinancialOfficeClick();
				swipe_caiwushi.setRefreshing(false);
			}
		});

	}

	/**
	 * 文字适配
	 */
	private void textAdaptation() {
		ScreenUtils.textAdaptationOn720(tv_dongjiejine, this, 25);
		ScreenUtils.textAdaptationOn720(tv_keyongyue, this, 25);
		ScreenUtils.textAdaptationOn720(tv_tixianzhongyue, this, 25);
		ScreenUtils.textAdaptationOn720(tv_dianpujinrishouru, this, 25);
		ScreenUtils.textAdaptationOn720(tv_shopaccountword, this, 32);

		ScreenUtils.textAdaptationOn720(tv_freezingamount, this, 27);
		ScreenUtils.textAdaptationOn720(tv_shopincometoday, this, 73);
		ScreenUtils.textAdaptationOn720(tv_takeinmoney, this, 27);
		ScreenUtils.textAdaptationOn720(tv_availablebalance, this, 27);
		ScreenUtils.textAdaptationOn720(tv_shopaccount, this, 27);
	}

	/**
	 * 添加监听
	 */
	private void setLisenter() {
		iv_recharge.setOnClickListener(this);
		iv_withdrawalsdetails.setOnClickListener(this);
		iv_withdrawals.setOnClickListener(this);
		iv_bankcard.setOnClickListener(this);
		tv_finalcial_ruledescription.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		boolean isLogin = ContentUtils.getLoginStatus(this);
		boolean re = false;
		switch (v.getId()) {
			case R.id.iv_recharge:// 充值
				ContentUtils.showMsg(this, "正在建设中...");
				break;

			case R.id.iv_withdrawalsdetails:// 收款额度
				re = JumpIntent.jumpLogin_addShop(isLogin, API.TRDATEDETAIL, this);
				if (re) {
					if (isBand) {
						startActivity(new Intent(this, ReceiptsActivity.class));
					} else {
						ContentUtils.showMsg(this, "请您先绑定银行卡!");
					}
				}
				break;
			case R.id.iv_withdrawals:// 提现
				re = JumpIntent.jumpLogin_addShop(isLogin, API.WITHDRAWDEPOSIT, this);
				if (re) {
					getWithdrawingProgress();
				}
				break;
			case R.id.iv_bankcard:// 银行卡
				re = JumpIntent.jumpLogin_addShop(isLogin, API.BANKCARD, this);
				if (re) {
					startActivity(new Intent(this, AddBankCardActivity.class).putExtra("isBand", isBand));
				}
				break;

			case R.id.tv_finalcial_ruledescription://规则说明
				try {
					DiaqlogNow dialog = new DiaqlogNow(this, financialOfficeAmountBean);
					dialog.show();
				} catch (Exception e) {
					ContentUtils.showMsg(this, "数据异常，请稍后再试");
				}

		}
	}

	private void getWithdrawingProgress() {
		String reqTime = AbDateUtil.getDateTimeNow();
		String uuid = AbStrUtil.getUUID();
		try {
			okHttpsImp.queryWithdrawStats(uuid, reqTime, BaseActivity.userShopInfoBean.getUserId(),
					BaseActivity.userShopInfoBean.getBusinessId(), new MyResultCallback<String>() {
						@Override
						public void onResponseResult(Result result) {
							if (result != null) {
								JSONObject jsonObject = JSON.parseObject(result.getData());
								if (jsonObject != null) {
									if (jsonObject.getString("state").equals("00")) {
										startActivity(new Intent(FinancialActivity.this, ShouRuHActivity.class).putExtra("isBand", isBand));
									} else {
										Status status = JSON.parseObject(jsonObject.getString("status"), Status.class);
										Intent i = new Intent(FinancialActivity.this, WithdrawingProgressActivity.class);
										i.putExtra(WithdrawingProgressActivity.FROM, WithdrawingProgressActivity.PROGRESS);
										i.putExtra(WithdrawingProgressActivity.STATUS, status);
										startActivity(i);
									}
								}
							}
						}

						@Override
						public void onResponseFailed(String msg) {

						}
					});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
