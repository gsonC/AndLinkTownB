package com.lianbi.mezone.b.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xizhi.mezone.b.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class YouhuiquanUsingDetailsActivity extends BaseActivity {
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
    @Bind(R.id.youhuiquan_using_detail_list)
    ListView listView;
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
        setContentView(R.layout.activity_youhuiquan_using_details, NOTYPE);
        ButterKnife.bind(this);
        setPageTitle("优惠券使用详情");
        List<String> data = new ArrayList<>();
        data.add("测试数据1");
        data.add("测试数据2");
        data.add("测试数据3");
        data.add("测试数据4");
        listView.setAdapter(new ArrayAdapter<>(YouhuiquanUsingDetailsActivity.this,
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
                all.setTextColor(Color.parseColor("#ff3c25"));
                been_used_.setBackgroundColor(getResources().getColor(R.color.colores_news_14));
                been_used.setTextColor(Color.parseColor("#ededed"));
                unused_.setBackgroundColor(getResources().getColor(R.color.colores_news_14));
                unused.setTextColor(Color.parseColor("#ededed"));
                invalid_.setBackgroundColor(getResources().getColor(R.color.colores_news_14));
                invalid.setTextColor(Color.parseColor("#ededed"));
                break;
            case R.id.been_used_:
                been_used_.setBackgroundColor(getResources().getColor(R.color.colores_news_01));
                been_used.setTextColor(Color.parseColor("#ff3c25"));
                all_.setBackgroundColor(getResources().getColor(R.color.colores_news_14));
                all.setTextColor(Color.parseColor("#ededed"));
                unused_.setBackgroundColor(getResources().getColor(R.color.colores_news_14));
                unused.setTextColor(Color.parseColor("#ededed"));
                invalid_.setBackgroundColor(getResources().getColor(R.color.colores_news_14));
                invalid.setTextColor(Color.parseColor("#ededed"));
                break;
            case R.id.unused_:
                unused_.setBackgroundColor(getResources().getColor(R.color.colores_news_01));
                unused.setTextColor(Color.parseColor("#ff3c25"));
                all_.setBackgroundColor(getResources().getColor(R.color.colores_news_14));
                all.setTextColor(Color.parseColor("#ededed"));
                been_used_.setBackgroundColor(getResources().getColor(R.color.colores_news_14));
                been_used.setTextColor(Color.parseColor("#ededed"));
                invalid_.setBackgroundColor(getResources().getColor(R.color.colores_news_14));
                invalid.setTextColor(Color.parseColor("#ededed"));
                break;
            case R.id.invalid_:
                invalid_.setBackgroundColor(getResources().getColor(R.color.colores_news_01));
                invalid.setTextColor(Color.parseColor("#ff3c25"));
                been_used_.setBackgroundColor(getResources().getColor(R.color.colores_news_14));
                been_used.setTextColor(Color.parseColor("#ededed"));
                unused_.setBackgroundColor(getResources().getColor(R.color.colores_news_14));
                unused.setTextColor(Color.parseColor("#ededed"));
                all_.setBackgroundColor(getResources().getColor(R.color.colores_news_14));
                all.setTextColor(Color.parseColor("#ededed"));
                break;
        }
    }
}
