package com.lianbi.mezone.b.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lianbi.mezone.b.bean.LeaguesYellBean;
import com.xizhi.mezone.b.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.hgh.utils.ContentUtils;

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
    private Intent getIntent;
    LeaguesYellBean  mLeaguesYellBean;
    @OnClick({R.id.iv_leaguesyelllistdetails_response})
    public void OnClick(View v) {
        switch (v.getId()) {
            case R.id.iv_leaguesyelllistdetails_response:

                ContentUtils.showMsg(LeaguesYellDetailsActivity.this, getString(R.string.underconstruction));

                break;
        }
    }
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
        getIntent=getIntent();
        mLeaguesYellBean=(LeaguesYellBean)getIntent.getSerializableExtra("leaguesyellbean");
        Glide.with(LeaguesYellDetailsActivity.this).load
                (mLeaguesYellBean.getLogoUrl()).error(R.mipmap.demo).into(ivLeaguesyelllistdetailsIcon);
        if(!TextUtils.isEmpty(mLeaguesYellBean.getAuthor())) {
            ivLeaguesyelllistdetailsName.setText(mLeaguesYellBean.getAuthor());
        }
        if(!TextUtils.isEmpty(mLeaguesYellBean.getCreateTime())) {
            String time = mLeaguesYellBean.getCreateTime();
            String year = time.substring(0, 4);
            String months = time.substring(4, 6);
            String daytime = time.substring(6, 8);
            ivLeaguesyelllistdetailsTime.setText(year + "-" + months + "-"
                    + daytime );
        }
        if(!TextUtils.isEmpty(mLeaguesYellBean.getMessageTitle())) {
            ivLeaguesyelllistdetailsTitle.setText(mLeaguesYellBean.getMessageTitle());
        }
        if(!TextUtils.isEmpty(mLeaguesYellBean.getMessageContent())) {
            ivLeaguesyelllistdetailsContent.setText(mLeaguesYellBean.getMessageContent());
        }
        if(!TextUtils.isEmpty(mLeaguesYellBean.getPhone())) {
            ivLeaguesyelllistdetailsPhone.setText(mLeaguesYellBean.getPhone());
        }
        if(!TextUtils.isEmpty(mLeaguesYellBean.getAddress())) {
            ivLeaguesyelllistdetailsAddress.setText(mLeaguesYellBean.getAddress());
        }
    }

}
