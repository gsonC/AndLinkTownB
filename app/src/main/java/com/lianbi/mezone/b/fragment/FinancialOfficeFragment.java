package com.lianbi.mezone.b.fragment;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lianbi.mezone.b.app.Constants;
import com.lianbi.mezone.b.bean.BankBoundInfo;
import com.lianbi.mezone.b.bean.FinancialOfficeAmountBean;
import com.lianbi.mezone.b.bean.Status;
import com.lianbi.mezone.b.httpresponse.API;
import com.lianbi.mezone.b.httpresponse.MyResultCallback;
import com.lianbi.mezone.b.httpresponse.OkHttpsImp;
import com.lianbi.mezone.b.ui.AddBankCardActivity;
import com.lianbi.mezone.b.ui.BaseActivity;
import com.lianbi.mezone.b.ui.MainActivity;
import com.lianbi.mezone.b.ui.ReceiptsActivity;
import com.lianbi.mezone.b.ui.ShouRuHActivity;
import com.lianbi.mezone.b.ui.WithdrawingProgressActivity;
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

/**
 * @author hongyu.yang 财务室
 * @time 上午10:10:47
 * @date 2016-1-12
 */
public class FinancialOfficeFragment extends Fragment implements
        OnClickListener {

    private MainActivity mMainActivity;
    private OkHttpsImp okHttpsImp;
    private ImageView iv_recharge, iv_withdrawalsdetails, iv_withdrawals,
            iv_bankcard;
    private TextView tv_totalaccount, tv_shopaccount, tv_availablebalance,
            tv_takeinmoney, tv_shopincometoday, tv_freezingamount, tv_shopaccountword;
    private TextView tv_dongjiejine, tv_keyongyue, tv_tixianzhongyue, tv_dianpujinrishouru,
            tv_finalcial_ruledescription, tv_finalcial_oldrate, tv_finalcial_newrate;
    public double totalaccount = 0, shopaccount = 0, availablebalance = 0,
            takeinmoney = 0, shopincometoday = 0, freezingamount = 0;
    TextView tv_gz_rate, tv_gz_count, tv_Fdiscount_time, tv_Ediscount_time;
    LinearLayout lin_discount,n_safety;

    /**
     * 刷新fm数据
     */
    public void refreshFMData() {
        boolean isLogin = ContentUtils.getLoginStatus(mMainActivity);
        if (isLogin) {
            n_safety.setVisibility(View.GONE);
            lin_discount.setVisibility(View.VISIBLE);
        } else {
            n_safety.setVisibility(View.VISIBLE);
            lin_discount.setVisibility(View.GONE);
        }
        if (ContentUtils.getLoginStatus(mMainActivity)) {
            getIsBand();
        } else {
            tv_totalaccount.setText(MathExtend.roundNew(0, 2));
            tv_shopaccount.setText(MathExtend.roundNew(0, 2));
            tv_availablebalance.setText(MathExtend.roundNew(0, 2));
            tv_takeinmoney.setText(MathExtend.roundNew(0, 2));
            tv_shopincometoday.setText(MathExtend.roundNew(0, 2));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fm_caiwushifragment, null);
        mMainActivity = (MainActivity) getActivity();// 获得activity实例
        okHttpsImp = OkHttpsImp.SINGLEOKHTTPSIMP.newInstance(mMainActivity);
        initView(view);
        setLisenter();
        return view;
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

    public boolean isReturn = false;
    public boolean isBank = false;
    private SwipeRefreshLayout swipe_caiwushi;

    @Override
    public void onResume() {
        super.onResume();

        isReturn = ContentUtils.getSharePreBoolean(mMainActivity,
                Constants.SHARED_PREFERENCE_NAME, Constants.SEARCHFINANCIAL);
        isBank = ContentUtils.getSharePreBoolean(mMainActivity,
                Constants.SHARED_PREFERENCE_NAME, Constants.ISBANKRETURN);
        if (ContentUtils.getLoginStatus(mMainActivity) && isBank) {
            isBank = false;
            ContentUtils.putSharePre(mMainActivity,
                    Constants.SHARED_PREFERENCE_NAME, Constants.ISBANKRETURN,
                    isBank);
            getIsBand();// 查看银行卡信息
        }

        if (ContentUtils.getLoginStatus(mMainActivity) && isReturn) {
            isReturn = false;

            ContentUtils.putSharePre(mMainActivity,
                    Constants.SHARED_PREFERENCE_NAME, Constants.SEARCHFINANCIAL,
                    isReturn);

            mMainActivity.getCount();
        }

    }

    /**
     * 初始化View
     *
     * @param view
     */
    private void initView(View view) {
        n_safety = (LinearLayout) view.findViewById(R.id.n_safety);
        lin_discount = (LinearLayout) view.findViewById(R.id.lin_discount);
        iv_recharge = (ImageView) view.findViewById(R.id.iv_recharge);// 充值
        iv_withdrawalsdetails = (ImageView) view
                .findViewById(R.id.iv_withdrawalsdetails);// 体现明细
        iv_withdrawals = (ImageView) view.findViewById(R.id.iv_withdrawals);// 提现
        iv_bankcard = (ImageView) view.findViewById(R.id.iv_bankcard);// 银行卡
        tv_finalcial_ruledescription = (TextView) view.findViewById(R.id.tv_finalcial_ruledescription);//规则说明
        tv_finalcial_oldrate = (TextView) view.findViewById(R.id.tv_finalcial_oldrate);//老费率
        tv_finalcial_newrate = (TextView) view.findViewById(R.id.tv_finalcial_newrate);//新费率
        tv_finalcial_oldrate.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);//中间加横线
        tv_totalaccount = (TextView) view.findViewById(R.id.tv_totalaccount);// 账户总额(数字)
        tv_shopaccount = (TextView) view.findViewById(R.id.tv_shopaccount);// 店铺总额(数字)

        tv_freezingamount = (TextView) view.findViewById(R.id.tv_freezingamount);//冻结金额(数字)
        tv_availablebalance = (TextView) view
                .findViewById(R.id.tv_availablebalance);// 可用余额(数字)
        tv_takeinmoney = (TextView) view.findViewById(R.id.tv_takeinmoney);// 提现中余额(数字)
        tv_shopincometoday = (TextView) view
                .findViewById(R.id.tv_shopincometoday);// 店铺今日收入(数字)

        tv_dongjiejine = (TextView) view
                .findViewById(R.id.tv_dongjiejine);// 冻结金额
        tv_keyongyue = (TextView) view
                .findViewById(R.id.tv_keyongyue);// 可用余额
        tv_tixianzhongyue = (TextView) view
                .findViewById(R.id.tv_tixianzhongyue);// 提现中余额
        tv_dianpujinrishouru = (TextView) view
                .findViewById(R.id.tv_dianpujinrishouru);// 店铺总额
        tv_shopaccountword = (TextView) view.findViewById(R.id.tv_shopaccountword);// 店铺今日收入

        tv_gz_rate = (TextView) view.findViewById(R.id.tv_gz_rate);//pop手续费
        tv_gz_count = (TextView) view.findViewById(R.id.tv_gz_count);//pop优惠费率
        tv_Fdiscount_time = (TextView) view.findViewById(R.id.tv_Fdiscount_time);//开始时间
        tv_Ediscount_time = (TextView) view.findViewById(R.id.tv_Ediscount_time);//结束时间

        textAdaptation();

        swipe_caiwushi = (SwipeRefreshLayout) view.findViewById(R.id.swipe_caiwushi);
        swipe_caiwushi.setColorSchemeResources(R.color.colores_news_01, R.color.black);
        swipe_caiwushi.setOnRefreshListener(new OnRefreshListener() {

            @Override
            public void onRefresh() {
                mMainActivity.getCount();
                swipe_caiwushi.setRefreshing(false);
            }
        });
    }

    /**
     * 文字适配
     */
    private void textAdaptation() {
        ScreenUtils.textAdaptationOn720(tv_dongjiejine, mMainActivity, 25);
        ScreenUtils.textAdaptationOn720(tv_keyongyue, mMainActivity, 25);
        ScreenUtils.textAdaptationOn720(tv_tixianzhongyue, mMainActivity, 25);
        ScreenUtils.textAdaptationOn720(tv_dianpujinrishouru, mMainActivity, 25);
        ScreenUtils.textAdaptationOn720(tv_shopaccountword, mMainActivity, 32);

        ScreenUtils.textAdaptationOn720(tv_freezingamount, mMainActivity, 27);
        ScreenUtils.textAdaptationOn720(tv_shopincometoday, mMainActivity, 73);
        ScreenUtils.textAdaptationOn720(tv_takeinmoney, mMainActivity, 27);
        ScreenUtils.textAdaptationOn720(tv_availablebalance, mMainActivity, 27);
        ScreenUtils.textAdaptationOn720(tv_shopaccount, mMainActivity, 27);
    }

    private FinancialOfficeAmountBean financialOfficeAmountBean;

    /**
     * 设置财务室信息
     */
    public void setFinancialOfficeAmount(FinancialOfficeAmountBean financialOfficeAmountBean) {

        this.financialOfficeAmountBean = financialOfficeAmountBean;
        this.totalaccount = financialOfficeAmountBean.getAccountTotalIncome().doubleValue();
        this.shopaccount = financialOfficeAmountBean.getStoreTotalIncome().doubleValue();
        this.availablebalance = financialOfficeAmountBean.getStoreBalance().doubleValue();
        this.takeinmoney = financialOfficeAmountBean.getStoreWithdrawAmount().doubleValue();
        this.freezingamount = financialOfficeAmountBean.getSotreFrozenAmount().doubleValue();

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

    /**
     * 设置财务室信息
     */
    public void setPriceTotal(double money, int positon) {
        switch (positon) {
            case 0:// 账户总额
                this.totalaccount = money;
                tv_totalaccount.setText(MathExtend.roundNew(money, 2));
                break;
            case 1:// 店铺总额
                this.shopaccount = money;
                tv_shopaccount.setText(MathExtend.roundNew(money, 2));
                break;
            case 2:// 可用余额
                this.availablebalance = money;
                tv_availablebalance.setText(MathExtend.roundNew(money, 2));
                break;
            case 3:// 提现中余额
                this.takeinmoney = money;
                tv_takeinmoney.setText(MathExtend.roundNew(money, 2));
                break;
            case 4:// 店铺今日收入
                this.shopincometoday = money;
                tv_shopincometoday.setText(MathExtend.roundNew(money, 2));
                break;
            case 5:
                this.freezingamount = money;
                tv_freezingamount.setText(MathExtend.roundNew(money, 2));
                break;
        }
    }

    /**
     * 设置店铺今日收入
     */
    public void setShopPriceTotal(double ff) {
        tv_shopaccount.setText(MathExtend.roundNew(ff, 2));
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
        boolean isLogin = ContentUtils.getLoginStatus(mMainActivity);
        boolean re = false;
        switch (v.getId()) {
            case R.id.iv_recharge:// 充值
                ContentUtils.showMsg(mMainActivity, "正在建设中...");
                break;

            case R.id.iv_withdrawalsdetails:// 收款额度
                re = JumpIntent.jumpLogin_addShop(isLogin, API.TRDATEDETAIL, mMainActivity);
                if (re) {
                    if (isBand) {
                        startActivity(new Intent(mMainActivity, ReceiptsActivity.class));
                    } else {
                        ContentUtils.showMsg(mMainActivity, "请您先绑定银行卡!");
                    }
                }
                break;
            case R.id.iv_withdrawals:// 提现
                re = JumpIntent.jumpLogin_addShop(isLogin, API.WITHDRAWDEPOSIT, mMainActivity);
                if (re) {
                    getWithdrawingProgress();
                }
                break;
            case R.id.iv_bankcard:// 银行卡
                re = JumpIntent.jumpLogin_addShop(isLogin, API.BANKCARD, mMainActivity);
                if (re) {
                    startActivity(new Intent(mMainActivity, AddBankCardActivity.class).putExtra("isBand", isBand));
                }
                break;

            case R.id.tv_finalcial_ruledescription://规则说明
                try{
                    DiaqlogNow dialog = new DiaqlogNow(mMainActivity, financialOfficeAmountBean);
                    dialog.show();
                }catch (Exception e){
                    ContentUtils.showMsg(mMainActivity,"数据异常，请稍后再试");
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
                                        startActivity(new Intent(mMainActivity, ShouRuHActivity.class).putExtra("isBand", isBand));
                                    } else {
                                        Status status = JSON.parseObject(jsonObject.getString("status"), Status.class);
                                        Intent i = new Intent(mMainActivity, WithdrawingProgressActivity.class);
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
