package com.lianbi.mezone.b.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lianbi.mezone.b.bean.CouponBean;
import com.lianbi.mezone.b.httpresponse.MyResultCallback;
import com.xizhi.mezone.b.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;
import cn.com.hgh.baseadapter.BaseAdapterHelper;
import cn.com.hgh.baseadapter.QuickAdapter;
import cn.com.hgh.utils.AbDateUtil;
import cn.com.hgh.utils.AbStrUtil;
import cn.com.hgh.utils.Result;
import cn.com.hgh.view.AbPullToRefreshView;
import cn.com.hgh.view.DialogCommon;

public class CouponManagerActivity extends BaseActivity implements AdapterView.OnItemClickListener,
        AbPullToRefreshView.OnHeaderRefreshListener, AbPullToRefreshView.OnFooterLoadListener {
    private static final int ALL_IS_SHOWING = 0;
    private static final int VALID_IS_SHOWING = 1;
    private static final int INVALID_IS_SHOWING = -1;
    private int currShowingIs;
    @Bind(R.id.all_container)
    FrameLayout all_container;
    @Bind(R.id.all)
    TextView all;
    @Bind(R.id.valid_container)
    FrameLayout valid_container;
    @Bind(R.id.valid)
    TextView valid;
    @Bind(R.id.invalid_container)
    FrameLayout invalid_container;
    @Bind(R.id.invalid)
    TextView invalid;
    @Bind(R.id.pull_to_refresh_coupon_list)
    AbPullToRefreshView abPullToRefreshView;
    @Bind(R.id.coupon_list)
    ListView listView;
    @Bind(R.id.iv_empty_act_coupon_detail)
    ImageView iv_empty_act_coupon_detail;
    @Bind(R.id.add)
    TextView add;

    private QuickAdapter<CouponBean> mAdapter;
    private List<CouponBean> mData = new ArrayList<>();
    private List<CouponBean> mValideData = new ArrayList<>();
    private List<CouponBean> mInvalideData = new ArrayList<>();

    private int pageSize = 20;
    private int pageNo;
    /**
     * 正在下拉刷新.
     */
    private boolean mPullRefreshing = false;
    /**
     * 正在加载更多.
     */
    private boolean mPullLoading = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coupon_manager, NOTYPE);
        ButterKnife.bind(this);
        setPageTitle(getString(R.string.activity_couponmanager_title));
        currShowingIs = ALL_IS_SHOWING;
        setListener();
        initAdapter();
    }

    @Override
    protected void onResume() {
        super.onResume();
        pageNo = 1;
        getDatas();
    }

    private void getDatas() {
        if (AbStrUtil.isEmpty(userShopInfoBean.getBusinessId())) {
            DialogCommon dialog = new DialogCommon(CouponManagerActivity.this) {
                @Override
                public void onCheckClick() {
                    dismiss();
                    finish();
                }

                @Override
                public void onOkClick() {
                    dismiss();
                    startActivity(new Intent(CouponManagerActivity.this, AddShopActivity.class));
                }
            };
            dialog.setCancelable(false);
            dialog.setCanceledOnTouchOutside(false);
            dialog.setTextTitle("您还没有自己的商铺，是否去新增商铺？");
            dialog.setTv_dialog_common_ok("新增商铺");
            dialog.setTv_dialog_common_cancel("  取消  ");
            dialog.show();
            return;
        }
        initCommonParameter();
        try {
            okHttpsImp.queryStoreCoupByStoreId(uuid, "app", reqTime, userShopInfoBean.getBusinessId(), "", Integer.toString(pageNo), Integer.toString(pageSize),
                    new MyResultCallback<String>() {
                @Override
                public void onResponseResult(Result result) {
                    if (null != result) {
                        JSONObject jsonObject = JSON.parseObject(result.getData());
                        List<CouponBean> l = JSON.parseArray(jsonObject.getString("list"), CouponBean.class);
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
                        Collections.sort(mData);
                        mValideData.clear();
                        mInvalideData.clear();
                        for (CouponBean couponBean : mData) {
                            if (couponBean.getIsValide().trim().equals("Y")) {
                                mValideData.add(couponBean);
                            } else {
                                mInvalideData.add(couponBean);
                            }
                        }
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

    private void switchAdapter() {
        switch (currShowingIs) {
            case ALL_IS_SHOWING:
                showingSelector(mData);
                break;
            case VALID_IS_SHOWING:
                showingSelector(mValideData);
                break;
            case INVALID_IS_SHOWING:
                showingSelector(mInvalideData);
                break;
        }
    }

    private void showingSelector(List<CouponBean> list) {
        if (list.isEmpty()) {
            listView.setVisibility(View.GONE);
            iv_empty_act_coupon_detail.setVisibility(View.VISIBLE);
        } else {
            listView.setVisibility(View.VISIBLE);
            iv_empty_act_coupon_detail.setVisibility(View.GONE);
            mAdapter.replaceAll(list);
        }
    }

    private void initAdapter() {
        mAdapter = new QuickAdapter<CouponBean>(CouponManagerActivity.this, R.layout.coupon_list_item, mData) {
            @Override
            protected void convert(BaseAdapterHelper helper, CouponBean item) {
                TextView coupon_name = helper.getView(R.id.coupon_name);
                TextView coupon_tiaojian = helper.getView(R.id.coupon_tiaojian);
                TextView youxiaoqi_from = helper.getView(R.id.youxiaoqi_from);
                TextView youxiaoqi_to = helper.getView(R.id.youxiaoqi_to);
                ImageView is_valid = helper.getView(R.id.is_valid);

                coupon_name.setText(item.getCoupName());
                try{
                coupon_tiaojian.setText(AbStrUtil.changeF2Y(Long.valueOf(item.getLimitAmt())));
                }catch (Exception e){
                    e.printStackTrace();
                }
                youxiaoqi_from.setText(AbDateUtil.getStringByFormat(item.getBeginTime(), AbDateUtil.dateFormatYMDNew));
                youxiaoqi_to.setText(AbDateUtil.getStringByFormat(item.getEndTime(), AbDateUtil.dateFormatYMDNew));
                if (item.getIsValide().trim().equals("Y")) {
                    is_valid.setImageResource(R.mipmap.effective);
                } else {
                    is_valid.setImageResource(R.mipmap.failure);
                }
            }
        };
        listView.setAdapter(mAdapter);
    }

    private void setListener() {
        abPullToRefreshView.setOnHeaderRefreshListener(this);
        abPullToRefreshView.setOnFooterLoadListener(this);
    }

    @Override
    @OnItemClick({R.id.coupon_list})
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(CouponManagerActivity.this, CouponUsingDetailsActivity.class);
        String couponId = ((CouponBean) parent.getAdapter().getItem(position)).getId();
        intent.putExtra(CouponUsingDetailsActivity.COUPON_ID, couponId);
        startActivity(intent);
    }

    @Override
    @OnClick({R.id.all_container, R.id.valid_container, R.id.invalid_container, R.id.add})
    protected void onChildClick(View view) {
        super.onChildClick(view);
        switch (view.getId()) {
            case R.id.all_container:
                all_container.setBackgroundColor(getResources().getColor(R.color.colores_news_01));
                all.setTextColor(getResources().getColor(R.color.colores_news_01));
                valid_container.setBackgroundColor(getResources().getColor(R.color.white));
                valid.setTextColor(getResources().getColor(R.color.colores_news_10));
                invalid_container.setBackgroundColor(getResources().getColor(R.color.white));
                invalid.setTextColor(getResources().getColor(R.color.colores_news_10));
                currShowingIs = ALL_IS_SHOWING;
                switchAdapter();
                break;
            case R.id.valid_container:
                all_container.setBackgroundColor(getResources().getColor(R.color.white));
                all.setTextColor(getResources().getColor(R.color.colores_news_10));
                valid_container.setBackgroundColor(getResources().getColor(R.color.colores_news_01));
                valid.setTextColor(getResources().getColor(R.color.colores_news_01));
                invalid_container.setBackgroundColor(getResources().getColor(R.color.white));
                invalid.setTextColor(getResources().getColor(R.color.colores_news_10));
                currShowingIs = VALID_IS_SHOWING;
                switchAdapter();
                break;
            case R.id.invalid_container:
                all_container.setBackgroundColor(getResources().getColor(R.color.white));
                all.setTextColor(getResources().getColor(R.color.colores_news_10));
                valid_container.setBackgroundColor(getResources().getColor(R.color.white));
                valid.setTextColor(getResources().getColor(R.color.colores_news_10));
                invalid_container.setBackgroundColor(getResources().getColor(R.color.colores_news_01));
                invalid.setTextColor(getResources().getColor(R.color.colores_news_01));
                currShowingIs = INVALID_IS_SHOWING;
                switchAdapter();
                break;
            case R.id.add:
                startActivity(new Intent(CouponManagerActivity.this, SendNewCouponActivity.class));
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

    protected boolean compareTo(List<CouponBean> data, CouponBean enity) {
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}