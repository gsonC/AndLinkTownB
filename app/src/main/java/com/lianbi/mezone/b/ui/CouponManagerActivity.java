package com.lianbi.mezone.b.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
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
import butterknife.OnItemClick;

public class CouponManagerActivity extends BaseActivity implements AdapterView.OnItemClickListener {
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
    @Bind(R.id.activity_coupon_list)
    ListView listView;
    @Bind(R.id.iv_empty_act_coupon_detail)
    ImageView iv_empty_act_coupon_detail;
    @Bind(R.id.add)
    TextView add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coupon_manager, NOTYPE);
        ButterKnife.bind(this);
        setPageTitle("优惠券管理");
        List<Map<String, Object>> data = new ArrayList<>();
        Map<String, Object> m0 = new HashMap<>();
        m0.put("coupon_name", "小三优惠券");
        m0.put("coupon_tiaojian", "99.5");
        m0.put("youxiaoqi_from", "2016.03.21");
        m0.put("youxiaoqi_to", "2016.03.21");
        m0.put("is_valid", R.mipmap.effective);
        data.add(m0);

        Map<String, Object> m1 = new HashMap<>();
        m1.put("coupon_name", "搞破鞋");
        m1.put("coupon_tiaojian", "9999");
        m1.put("youxiaoqi_from", "2013.06.21");
        m1.put("youxiaoqi_to", "2013.03.91");
        m1.put("is_valid", R.mipmap.failure);
        data.add(m1);

        Map<String, Object> m2 = new HashMap<>();
        m2.put("coupon_name", "绿帽子王");
        m2.put("coupon_tiaojian", "22.6");
        m2.put("youxiaoqi_from", "2016.05.21");
        m2.put("youxiaoqi_to", "2016.03.29");
        m2.put("is_valid", R.mipmap.effective);
        data.add(m2);

        Map<String, Object> m3 = new HashMap<>();
        m3.put("coupon_name", "谁他妈知道");
        m3.put("coupon_tiaojian", "10002");
        m3.put("youxiaoqi_from", "2016.23.21");
        m3.put("youxiaoqi_to", "2016.13.21");
        m3.put("is_valid", R.mipmap.failure);
        data.add(m3);
        String[] from = new String[]{"coupon_name", "coupon_tiaojian",
                "youxiaoqi_from", "youxiaoqi_to", "is_valid"};
        int[] to = new int[]{R.id.coupon_name, R.id.coupon_tiaojian,
                R.id.youxiaoqi_from, R.id.youxiaoqi_to, R.id.is_valid};
        listView.setAdapter(new SimpleAdapter(CouponManagerActivity.this,
                data, R.layout.coupon_list_item, from, to));
    }

    @Override
    @OnItemClick({R.id.activity_coupon_list})
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        startActivity(new Intent(CouponManagerActivity.this, CouponUsingDetailsActivity.class));
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
                break;
            case R.id.valid_container:
                all_container.setBackgroundColor(getResources().getColor(R.color.white));
                all.setTextColor(getResources().getColor(R.color.colores_news_10));
                valid_container.setBackgroundColor(getResources().getColor(R.color.colores_news_01));
                valid.setTextColor(getResources().getColor(R.color.colores_news_01));
                invalid_container.setBackgroundColor(getResources().getColor(R.color.white));
                invalid.setTextColor(getResources().getColor(R.color.colores_news_10));
                break;
            case R.id.invalid_container:
                all_container.setBackgroundColor(getResources().getColor(R.color.white));
                all.setTextColor(getResources().getColor(R.color.colores_news_10));
                valid_container.setBackgroundColor(getResources().getColor(R.color.white));
                valid.setTextColor(getResources().getColor(R.color.colores_news_10));
                invalid_container.setBackgroundColor(getResources().getColor(R.color.colores_news_01));
                invalid.setTextColor(getResources().getColor(R.color.colores_news_01));
                break;
            case R.id.add:
                startActivity(new Intent(CouponManagerActivity.this, SendNewCouponActivity.class));
                break;
        }
    }
}