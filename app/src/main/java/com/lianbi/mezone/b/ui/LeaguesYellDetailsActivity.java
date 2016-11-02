package com.lianbi.mezone.b.ui;

import android.content.Context;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.xizhi.mezone.b.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/*
 * @创建者     master
 * @创建时间   2016/10/19 18:18
 * @描述		   吆喝列表详情页
 *
 * @更新者     $Author$
 * @更新时间   $Date$
 * @更新描述
 */
public class LeaguesYellDetailsActivity extends BaseActivity {

    @Bind(R.id.iv_leaguesyelllistdetails_icon)
    ImageView ivLeaguesyelllistdetailsIcon;
    @Bind(R.id.iv_leaguesyelllistdetails_name)
    TextView ivLeaguesyelllistdetailsName;
    @Bind(R.id.iv_leaguesyelllistdetails_time)
    TextView ivLeaguesyelllistdetailsTime;
    @Bind(R.id.iv_leaguesyelllistdetails_response)
    TextView ivLeaguesyelllistdetailsResponse;
    @Bind(R.id.iv_leaguesyelllistdetails_title)
    TextView ivLeaguesyelllistdetailsTitle;
    @Bind(R.id.iv_leaguesyelllistdetails_content)
    TextView ivLeaguesyelllistdetailsContent;
    @Bind(R.id.iv_leaguesyelllistdetails_phone)
    TextView ivLeaguesyelllistdetailsPhone;
    @Bind(R.id.iv_leaguesyelllistdetails_address)
    TextView ivLeaguesyelllistdetailsAddress;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(R.layout.act_leaguesyelllistdetails, NOTYPE);
        ButterKnife.bind(this);
        initData();
    }

    /**
     * 初始化View
     */
    private void initData() {
        setPageTitle("吆喝详情");
        getYellList();
    }

    /**
     * 获取吆喝数据
     */
    private void getYellList() {


    }
}
