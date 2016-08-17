package com.lianbi.mezone.b.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.xizhi.mezone.b.R;

import java.util.ArrayList;
import java.util.List;

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
        List<String> data = new ArrayList<String>();
        data.add("测试数据1");
        data.add("测试数据2");
        data.add("测试数据3");
        data.add("测试数据4");
        listView.setAdapter(new ArrayAdapter<>(CouponManagerActivity.this,
                android.R.layout.simple_list_item_1,
                data));
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
                valid_container.setBackgroundColor(getResources().getColor(R.color.colores_news_14));
                valid.setTextColor(getResources().getColor(R.color.colores_news_10));
                invalid_container.setBackgroundColor(getResources().getColor(R.color.colores_news_14));
                invalid.setTextColor(getResources().getColor(R.color.colores_news_10));
                break;
            case R.id.valid_container:
                all_container.setBackgroundColor(getResources().getColor(R.color.colores_news_14));
                all.setTextColor(getResources().getColor(R.color.colores_news_10));
                valid_container.setBackgroundColor(getResources().getColor(R.color.colores_news_01));
                valid.setTextColor(getResources().getColor(R.color.colores_news_01));
                invalid_container.setBackgroundColor(getResources().getColor(R.color.colores_news_14));
                invalid.setTextColor(getResources().getColor(R.color.colores_news_10));
                break;
            case R.id.invalid_container:
                all_container.setBackgroundColor(getResources().getColor(R.color.colores_news_14));
                all.setTextColor(getResources().getColor(R.color.colores_news_10));
                valid_container.setBackgroundColor(getResources().getColor(R.color.colores_news_14));
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