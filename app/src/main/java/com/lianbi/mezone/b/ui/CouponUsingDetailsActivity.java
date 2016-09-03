package com.lianbi.mezone.b.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lianbi.mezone.b.bean.CouponUsingDetails;
import com.lianbi.mezone.b.httpresponse.MyResultCallback;
import com.xizhi.mezone.b.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.hgh.baseadapter.BaseAdapterHelper;
import cn.com.hgh.baseadapter.QuickAdapter;
import cn.com.hgh.utils.AbDateUtil;
import cn.com.hgh.utils.AbStrUtil;
import cn.com.hgh.utils.ContentUtils;
import cn.com.hgh.utils.Result;
import cn.com.hgh.view.AbPullToRefreshView;

public class CouponUsingDetailsActivity extends BaseActivity implements
        AbPullToRefreshView.OnHeaderRefreshListener, AbPullToRefreshView.OnFooterLoadListener {
    public static final String COUPON_ID = "COUPON_ID";
    private static final int ALL_IS_SHOWING = 0;
    private static final int USED_IS_SHOWING = 1;
    private static final int UNUSED_IS_SHOWING = 2;
    private static final int INVALID_IS_SHOWING = 3;
    private int currShowingIs;
    @Bind(R.id.all_)
    FrameLayout all_;
    @Bind(R.id.all)
    TextView all;
    @Bind(R.id.been_used_)
    FrameLayout been_used_;
    @Bind(R.id.been_used)
    TextView been_used;
    @Bind(R.id.unused_)
    FrameLayout unused_;
    @Bind(R.id.unused)
    TextView unused;
    @Bind(R.id.invalid_)
    FrameLayout invalid_;
    @Bind(R.id.invalid)
    TextView invalid;
    @Bind(R.id.pull_to_refresh_coupon_using_detail_list)
    AbPullToRefreshView abPullToRefreshView;
    @Bind(R.id.coupon_using_detail_list)
    ListView listView;
    @Bind(R.id.iv_empty_act_coupon_using_detail)
    ImageView iv_empty_act_coupon_using_detail;
    @Bind(R.id.all_num)
    TextView all_num;
    @Bind(R.id.used_num)
    TextView used_num;
    @Bind(R.id.unused_num)
    TextView unused_num;
    @Bind(R.id.invalid_num)
    TextView invalid_num;

    private String couponId;

    private int pageSize = 20;
    private int pageNo = 1;
    /**
     * 正在下拉刷新.
     */
    private boolean mPullRefreshing = false;
    /**
     * 正在加载更多.
     */
    private boolean mPullLoading = false;

    private QuickAdapter<CouponUsingDetails> mAdapter;
    private List<CouponUsingDetails> mData = new ArrayList<>();
    private List<CouponUsingDetails> mUsedData = new ArrayList<>();
    private List<CouponUsingDetails> mUnusedData = new ArrayList<>();
    private List<CouponUsingDetails> mInvalidData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coupon_using_details, NOTYPE);
        ButterKnife.bind(this);
        setPageTitle("优惠券使用详情");
        currShowingIs = ALL_IS_SHOWING;

        couponId = getIntent().getStringExtra(COUPON_ID);

        abPullToRefreshView.setOnHeaderRefreshListener(this);
        abPullToRefreshView.setOnFooterLoadListener(this);

        initAdapter();
        getDatas();
    }

    private void initAdapter() {
        mAdapter = new QuickAdapter<CouponUsingDetails>(CouponUsingDetailsActivity.this,
                R.layout.coupon_using_details_list_item, mData) {
            @Override
            protected void convert(BaseAdapterHelper helper, CouponUsingDetails item) {
                TextView member_phone = helper.getView(R.id.member_phone);
                TextView amount_money = helper.getView(R.id.amount_money);
                TextView amount_time = helper.getView(R.id.amount_time);
                TextView card_status = helper.getView(R.id.card_status);

                String vipPhone = item.getVipPhone();
                String amount = item.getAmount();
                String useTime = item.getUseTime();
                String state = item.getState();
                if (!AbStrUtil.isEmpty(vipPhone)) {
                    member_phone.setText(vipPhone);
                }
                if (!AbStrUtil.isEmpty(amount)) {
                    amount_money.setText(amount);
                }
                if (!AbStrUtil.isEmpty(useTime)) {
                    if (useTime.trim().length() > 10)
                        amount_time.setText(AbDateUtil.getStringByFormat(useTime, AbDateUtil.dateFormatYMDHMNew));
                    else
                        amount_time.setText(AbDateUtil.getStringByFormat(useTime, AbDateUtil.dateFormatYMDNew));
                }
                if (!AbStrUtil.isEmpty(state)) {
                    switch (state) {
                        case "01":
                            card_status.setText("未使用");
                            break;
                        case "02":
                            card_status.setText("已使用");
                            break;
                        case "03":
                            card_status.setText("已失效");
                            break;
                    }
                }
            }
        };
        listView.setAdapter(mAdapter);
    }

    private void getDatas() {
        initCommonParameter();
        try {
            okHttpsImp.getCouponUsingDetailListByCoupId(uuid, "app", reqTime, couponId, "",
                    Integer.toString(pageNo), Integer.toString(pageSize),
                    new MyResultCallback<String>() {
                        @Override
                        public void onResponseResult(Result result) {
                            if (result != null) {
                                JSONObject jsonObject = JSON.parseObject(result.getData());
                                all_num.setText(jsonObject.getString("allCount"));
                                used_num.setText(jsonObject.getString("useCount"));
                                unused_num.setText(jsonObject.getString("noUseCount"));
                                invalid_num.setText(jsonObject.getString("failCount"));
                                List<CouponUsingDetails> l = JSON.parseArray(jsonObject.getString("list"), CouponUsingDetails.class);
                                if (l.size() < pageSize) {
                                    ContentUtils.showMsg(CouponUsingDetailsActivity.this, "已加载全部");
                                }
                                if (pageNo == 1) {
                                    mData = l;
                                }
                                if (pageNo > 1) {
                                    if (l.isEmpty()) {
                                        pageNo--;
                                    } else {
                                        for (int i = 0; i < l.size(); i++) {
                                            if (compareTo(mData, l.get(i))) {
                                                l.remove(i);
                                                i--;
                                            }
                                        }
                                        mData.addAll(l);
                                    }
                                }
                                //除了“已使用”之外，所有的都按照创建时间倒序排列
                                Collections.sort(mData, new Comparator<CouponUsingDetails>() {
                                    @Override
                                    public int compare(CouponUsingDetails lhs, CouponUsingDetails rhs) {
                                        Date lhsDate = AbDateUtil.getDateByFormat(lhs.getCreateTime(), AbDateUtil.dateFormatYMDHMS);
                                        Date rhsDate = AbDateUtil.getDateByFormat(rhs.getCreateTime(), AbDateUtil.dateFormatYMDHMS);
                                        return rhsDate.compareTo(lhsDate);
                                    }
                                });
                                mUnusedData.clear();
                                mUsedData.clear();
                                mInvalidData.clear();
                                for (CouponUsingDetails entry : mData) {
                                    switch (entry.getState()) {
                                        case "01":
                                            mUnusedData.add(entry);
                                            break;
                                        case "02":
                                            mUsedData.add(entry);
                                            break;
                                        case "03":
                                            mInvalidData.add(entry);
                                            break;
                                    }
                                }
                                //“已使用”的按照使用时间倒序排列
                                Collections.sort(mUsedData, new Comparator<CouponUsingDetails>() {
                                    @Override
                                    public int compare(CouponUsingDetails lhs, CouponUsingDetails rhs) {
                                        Date lhsDate = AbDateUtil.getDateByFormat(lhs.getUseTime(), AbDateUtil.dateFormatYMDHMS);
                                        Date rhsDate = AbDateUtil.getDateByFormat(rhs.getUseTime(), AbDateUtil.dateFormatYMDHMS);
                                        return rhsDate.compareTo(lhsDate);
                                    }
                                });
                                switchAdapter();
                            }
                            refreshingFinish();
                        }

                        @Override
                        public void onResponseFailed(String msg) {
                            if (pageNo > 1)
                                pageNo--;
                            refreshingFinish();
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean compareTo(List<CouponUsingDetails> data, CouponUsingDetails enity) {
        int s = data.size();
        if (enity != null) {
            for (int i = 0; i < s; i++) {
                if (enity.getId().equals(data.get(i).getId())) {
                    return true;
                }
            }
        }
        return false;
    }

    private void switchAdapter() {
        switch (currShowingIs) {
            case ALL_IS_SHOWING:
                showingSelector(mData);
                break;
            case USED_IS_SHOWING:
                showingSelector(mUsedData);
                break;
            case UNUSED_IS_SHOWING:
                showingSelector(mUnusedData);
                break;
            case INVALID_IS_SHOWING:
                showingSelector(mInvalidData);
                break;
        }
    }

    private void showingSelector(List<CouponUsingDetails> list) {
        if (list.isEmpty()) {
            listView.setVisibility(View.GONE);
            iv_empty_act_coupon_using_detail.setVisibility(View.VISIBLE);
        } else {
            listView.setVisibility(View.VISIBLE);
            iv_empty_act_coupon_using_detail.setVisibility(View.GONE);
            mAdapter.replaceAll(list);
        }
    }

    @Override
    @OnClick({R.id.all_, R.id.been_used_, R.id.unused_, R.id.invalid_})
    protected void onChildClick(View view) {
        super.onChildClick(view);
        switch (view.getId()) {
            case R.id.all_:
                all_.setBackgroundColor(getResources().getColor(R.color.colores_news_01));
                all.setTextColor(getResources().getColor(R.color.colores_news_01));
                been_used_.setBackgroundColor(getResources().getColor(R.color.white));
                been_used.setTextColor(getResources().getColor(R.color.colores_news_10));
                unused_.setBackgroundColor(getResources().getColor(R.color.white));
                unused.setTextColor(getResources().getColor(R.color.colores_news_10));
                invalid_.setBackgroundColor(getResources().getColor(R.color.white));
                invalid.setTextColor(getResources().getColor(R.color.colores_news_10));
                currShowingIs = ALL_IS_SHOWING;
                switchAdapter();
                break;
            case R.id.been_used_:
                been_used_.setBackgroundColor(getResources().getColor(R.color.colores_news_01));
                been_used.setTextColor(getResources().getColor(R.color.colores_news_01));
                all_.setBackgroundColor(getResources().getColor(R.color.white));
                all.setTextColor(getResources().getColor(R.color.colores_news_10));
                unused_.setBackgroundColor(getResources().getColor(R.color.white));
                unused.setTextColor(getResources().getColor(R.color.colores_news_10));
                invalid_.setBackgroundColor(getResources().getColor(R.color.white));
                invalid.setTextColor(getResources().getColor(R.color.colores_news_10));
                currShowingIs = USED_IS_SHOWING;
                switchAdapter();
                break;
            case R.id.unused_:
                unused_.setBackgroundColor(getResources().getColor(R.color.colores_news_01));
                unused.setTextColor(getResources().getColor(R.color.colores_news_01));
                all_.setBackgroundColor(getResources().getColor(R.color.white));
                all.setTextColor(getResources().getColor(R.color.colores_news_10));
                been_used_.setBackgroundColor(getResources().getColor(R.color.white));
                been_used.setTextColor(getResources().getColor(R.color.colores_news_10));
                invalid_.setBackgroundColor(getResources().getColor(R.color.white));
                invalid.setTextColor(getResources().getColor(R.color.colores_news_10));
                currShowingIs = UNUSED_IS_SHOWING;
                switchAdapter();
                break;
            case R.id.invalid_:
                invalid_.setBackgroundColor(getResources().getColor(R.color.colores_news_01));
                invalid.setTextColor(getResources().getColor(R.color.colores_news_01));
                been_used_.setBackgroundColor(getResources().getColor(R.color.white));
                been_used.setTextColor(getResources().getColor(R.color.colores_news_10));
                unused_.setBackgroundColor(getResources().getColor(R.color.white));
                unused.setTextColor(getResources().getColor(R.color.colores_news_10));
                all_.setBackgroundColor(getResources().getColor(R.color.white));
                all.setTextColor(getResources().getColor(R.color.colores_news_10));
                currShowingIs = INVALID_IS_SHOWING;
                switchAdapter();
                break;
        }
    }

    @Override
    public void onFooterLoad(AbPullToRefreshView view) {
        mPullLoading = true;
        pageNo++;
        getDatas();
    }

    @Override
    public void onHeaderRefresh(AbPullToRefreshView view) {
        mPullRefreshing = true;
        pageNo = 1;
        getDatas();
    }

    private void refreshingFinish() {
        if (mPullRefreshing) {
            abPullToRefreshView.onHeaderRefreshFinish();
            mPullRefreshing = false;
        }
        if (mPullLoading) {
            abPullToRefreshView.onFooterLoadFinish();
            mPullLoading = false;
        }
    }
}
