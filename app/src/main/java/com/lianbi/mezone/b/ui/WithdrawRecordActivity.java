package com.lianbi.mezone.b.ui;

import android.content.ComponentName;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.lianbi.mezone.b.bean.WithDrawDeposite;
import com.lianbi.mezone.b.httpresponse.MyResultCallback;
import com.xizhi.mezone.b.R;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;
import cn.com.hgh.baseadapter.BaseAdapterHelper;
import cn.com.hgh.baseadapter.QuickAdapter;
import cn.com.hgh.utils.MathExtend;
import cn.com.hgh.utils.Result;
import cn.com.hgh.view.AbPullToRefreshView;

/**
 * 提现记录
 */
public class WithdrawRecordActivity extends BaseActivity implements AbPullToRefreshView.OnHeaderRefreshListener, AbPullToRefreshView.OnFooterLoadListener {
    @Bind(R.id.withdraw_record)
    AbPullToRefreshView withdrawRecord;
    @Bind(R.id.iv_empty_act_withdraw_record)
    ImageView emptyImg;
    @Bind(R.id.withdraw_record_listview)
    ListView listView;

    private int pageNo = 0;
    private List<WithDrawDeposite> mData = new ArrayList<>();
    private QuickAdapter<WithDrawDeposite> adapter;
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
        setContentView(R.layout.activity_withdraw_record, NOTYPE);
        ButterKnife.bind(WithdrawRecordActivity.this);
        setPageTitle("提现记录");
        setListener();
        initAdapter();
        getDatas();
    }

    private void initAdapter() {
        adapter = new QuickAdapter<WithDrawDeposite>(WithdrawRecordActivity.this, R.layout.withdraw_record_listview_item, mData) {
            @Override
            protected void convert(BaseAdapterHelper helper, final WithDrawDeposite item) {
                TextView order_no = helper.getView(R.id.order_no);
                TextView state = helper.getView(R.id.state);
                TextView withdraw_content = helper.getView(R.id.withdraw_content);
                TextView withdraw_time = helper.getView(R.id.withdraw_time);
                TextView withdraw_money = helper.getView(R.id.withdraw_money);

                order_no.setText(item.getId());

                String status = item.getCheckStatus();
                String sStr = "";
                int colorRes = 0;
                if ("04".equals(status)) {//打款成功
                    sStr = "提现成功";
                    colorRes = R.color.colores_news_06;
                    state.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(WithdrawRecordActivity.this, WithdrawingProgressActivity.class)
                                    .putExtra(WithdrawingProgressActivity.FROM, WithdrawingProgressActivity.SUCESS)
                                    .putExtra(WithdrawingProgressActivity.STATUS, item.getStatus()));
                        }
                    });
                } else {//03、审核拒绝，05、打款失败
                    sStr = "提现失败";
                    colorRes = R.color.colores_news_04;
                    state.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(WithdrawRecordActivity.this, WithdrawFailedReasonActivity.class)
                                    .putExtra(WithdrawFailedReasonActivity.checkStatus, item.getCheckStatus()));
                        }
                    });
                }
                GradientDrawable drawable = (GradientDrawable) state.getBackground();
                drawable.setColor(getResources().getColor(colorRes));
                state.setBackgroundDrawable(drawable);
                state.setText(sStr);

                String bankAccountNo = item.getBankAccountNo();
                String tail = bankAccountNo.substring(bankAccountNo.length() - 4, bankAccountNo.length());
                withdraw_content.setText("转出到卡尾号" + tail);

                double money = BigDecimal
                        .valueOf(Long.valueOf(item.getAmount()))
                        .divide(new BigDecimal(100))
                        .doubleValue();
                withdraw_money.setText("-" + MathExtend.roundNew(money, 2));

                withdraw_time.setText(item.getStatus().getApply());
            }
        };
        listView.setAdapter(adapter);
    }

    private void setListener() {
        withdrawRecord.setOnHeaderRefreshListener(this);
        withdrawRecord.setOnHeaderRefreshListener(this);
    }

    private void getDatas() {
        try {
            okHttpsImp.getWithDrawByUserId(userShopInfoBean.getUserId(),
                    BusinessId, Integer.toString(pageNo), "20", uuid, reqTime, new MyResultCallback<String>() {
                        @Override
                        public void onResponseResult(Result result) {
                            if (result != null) {
                                List<WithDrawDeposite> l = JSON.parseArray(result.getData(), WithDrawDeposite.class);
                                if (!l.isEmpty()) {
                                    for (int i = 0; i < l.size(); i++) {
                                        String checkStatus = l.get(i).getCheckStatus();
                                        if ("01".equals(checkStatus) || "02".equals(checkStatus)) {//剔除01审核中、02审核通过
                                            l.remove(i);
                                            i--;
                                        }
                                    }
                                }
                                if (pageNo == 0) {
                                    mData = l;
                                }
                                if (pageNo > 0) {
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
                                if (mData.isEmpty()) {
                                    listView.setVisibility(View.GONE);
                                    emptyImg.setVisibility(View.VISIBLE);
                                } else {
                                    Collections.sort(mData);
                                    listView.setVisibility(View.VISIBLE);
                                    emptyImg.setVisibility(View.GONE);
                                    adapter.replaceAll(mData);
                                }
                            }
                            refreshingFinish();
                        }

                        @Override
                        public void onResponseFailed(String msg) {
                            if (pageNo > 0)
                                pageNo--;
                            refreshingFinish();
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean compareTo(List<WithDrawDeposite> data, WithDrawDeposite enity) {
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
    public void onFooterLoad(AbPullToRefreshView view) {
        mPullLoading = true;
        pageNo++;
        initCommonParameter();
        getDatas();
    }

    @Override
    public void onHeaderRefresh(AbPullToRefreshView view) {
        mPullRefreshing = true;
        pageNo = 0;
        initCommonParameter();
        getDatas();
    }

    private void refreshingFinish() {
        if (mPullRefreshing) {
            withdrawRecord.onHeaderRefreshFinish();
            mPullRefreshing = false;
        }
        if (mPullLoading) {
            withdrawRecord.onFooterLoadFinish();
            mPullLoading = false;
        }
    }

    @Override
    @OnClick({R.id.iv_empty_act_withdraw_record})
    protected void onChildClick(View view) {
        super.onChildClick(view);
        if (view.getId() == R.id.iv_empty_act_withdraw_record) {
            pageNo = 0;
            initCommonParameter();
            getDatas();
        }
    }
}
