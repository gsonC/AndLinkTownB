package com.lianbi.mezone.b.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xizhi.mezone.b.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CouponUsingDetailsActivity extends BaseActivity {
    @Bind(R.id.all_)
    RelativeLayout all_;
    @Bind(R.id.all)
    TextView all;
    @Bind(R.id.been_used_)
    RelativeLayout been_used_;
    @Bind(R.id.been_used)
    TextView been_used;
    @Bind(R.id.unused_)
    RelativeLayout unused_;
    @Bind(R.id.unused)
    TextView unused;
    @Bind(R.id.invalid_)
    RelativeLayout invalid_;
    @Bind(R.id.invalid)
    TextView invalid;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coupon_using_details, NOTYPE);
        ButterKnife.bind(this);
        setPageTitle("优惠券使用详情");
        List<String> data = new ArrayList<>();
        data.add("测试数据1");
        data.add("测试数据2");
        data.add("测试数据3");
        data.add("测试数据4");
        listView.setAdapter(new ArrayAdapter<>(CouponUsingDetailsActivity.this,
                android.R.layout.simple_list_item_1,
                data));
    }

    @Override
    @OnClick({R.id.all_, R.id.been_used_, R.id.unused_, R.id.invalid_})
    protected void onChildClick(View view) {
        super.onChildClick(view);
        switch (view.getId()) {
            case R.id.all_:
                all_.setBackgroundColor(getResources().getColor(R.color.colores_news_01));
                all.setTextColor(getResources().getColor(R.color.colores_news_01));
                been_used_.setBackgroundColor(getResources().getColor(R.color.colores_news_14));
                been_used.setTextColor(getResources().getColor(R.color.colores_news_10));
                unused_.setBackgroundColor(getResources().getColor(R.color.colores_news_14));
                unused.setTextColor(getResources().getColor(R.color.colores_news_10));
                invalid_.setBackgroundColor(getResources().getColor(R.color.colores_news_14));
                invalid.setTextColor(getResources().getColor(R.color.colores_news_10));
                break;
            case R.id.been_used_:
                been_used_.setBackgroundColor(getResources().getColor(R.color.colores_news_01));
                been_used.setTextColor(getResources().getColor(R.color.colores_news_01));
                all_.setBackgroundColor(getResources().getColor(R.color.colores_news_14));
                all.setTextColor(getResources().getColor(R.color.colores_news_10));
                unused_.setBackgroundColor(getResources().getColor(R.color.colores_news_14));
                unused.setTextColor(getResources().getColor(R.color.colores_news_10));
                invalid_.setBackgroundColor(getResources().getColor(R.color.colores_news_14));
                invalid.setTextColor(getResources().getColor(R.color.colores_news_10));
                break;
            case R.id.unused_:
                unused_.setBackgroundColor(getResources().getColor(R.color.colores_news_01));
                unused.setTextColor(getResources().getColor(R.color.colores_news_01));
                all_.setBackgroundColor(getResources().getColor(R.color.colores_news_14));
                all.setTextColor(getResources().getColor(R.color.colores_news_10));
                been_used_.setBackgroundColor(getResources().getColor(R.color.colores_news_14));
                been_used.setTextColor(getResources().getColor(R.color.colores_news_10));
                invalid_.setBackgroundColor(getResources().getColor(R.color.colores_news_14));
                invalid.setTextColor(getResources().getColor(R.color.colores_news_10));
                break;
            case R.id.invalid_:
                invalid_.setBackgroundColor(getResources().getColor(R.color.colores_news_01));
                invalid.setTextColor(getResources().getColor(R.color.colores_news_01));
                been_used_.setBackgroundColor(getResources().getColor(R.color.colores_news_14));
                been_used.setTextColor(getResources().getColor(R.color.colores_news_10));
                unused_.setBackgroundColor(getResources().getColor(R.color.colores_news_14));
                unused.setTextColor(getResources().getColor(R.color.colores_news_10));
                all_.setBackgroundColor(getResources().getColor(R.color.colores_news_14));
                all.setTextColor(getResources().getColor(R.color.colores_news_10));
                break;
        }
    }
}
