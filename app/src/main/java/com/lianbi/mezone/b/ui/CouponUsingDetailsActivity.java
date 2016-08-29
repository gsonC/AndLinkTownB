package com.lianbi.mezone.b.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.FrameLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.xizhi.mezone.b.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.hgh.view.AbPullToRefreshView;

public class CouponUsingDetailsActivity extends BaseActivity implements
        AbPullToRefreshView.OnHeaderRefreshListener, AbPullToRefreshView.OnFooterLoadListener {
    public static final String COUPON_ID = "COUPON_ID";
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coupon_using_details, NOTYPE);
        ButterKnife.bind(this);
        setPageTitle("优惠券使用详情");

        couponId = getIntent().getStringExtra(COUPON_ID);

        abPullToRefreshView.setOnHeaderRefreshListener(this);
        abPullToRefreshView.setOnFooterLoadListener(this);

        List<Map<String, String>> data = new ArrayList<>();
        Map<String, String> m0 = new HashMap<>();
        m0.put("member_phone", "1383838438");
        m0.put("amount_money", "99.5");
        m0.put("amount_time", "2016.03.21(7:25)");
        m0.put("card_status", "未使用");
        data.add(m0);

        Map<String, String> m1 = new HashMap<>();
        m1.put("member_phone", "1383838438");
        m1.put("amount_money", "9");
        m1.put("amount_time", "2013.06.21(5:24)");
        m1.put("card_status", "已失效");
        data.add(m1);

        Map<String, String> m2 = new HashMap<>();
        m2.put("member_phone", "1383838438");
        m2.put("amount_money", "22.6");
        m2.put("amount_time", "2016.05.21(1:12)");
        m2.put("card_status", "未使用");
        data.add(m2);

        Map<String, String> m3 = new HashMap<>();
        m3.put("member_phone", "1383838438");
        m3.put("amount_money", "10002");
        m3.put("amount_time", "2016.23.21(12:00)");
        m3.put("card_status", "已使用");
        data.add(m3);
        String[] from = new String[]{"member_phone", "amount_money",
                "amount_time", "card_status"};
        int[] to = new int[]{R.id.member_phone, R.id.amount_money,
                R.id.amount_time, R.id.card_status};
        listView.setAdapter(new SimpleAdapter(CouponUsingDetailsActivity.this,
                data, R.layout.coupon_using_details_list_item, from, to));
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
                break;
        }
    }

    @Override
    public void onFooterLoad(AbPullToRefreshView view) {

    }

    @Override
    public void onHeaderRefresh(AbPullToRefreshView view) {

    }
}
