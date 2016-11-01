package com.lianbi.mezone.b.ui;

import android.content.Context;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lianbi.mezone.b.bean.LeaguesYellBean;
import com.xizhi.mezone.b.R;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.com.hgh.baseadapter.BaseAdapterHelper;
import cn.com.hgh.baseadapter.QuickAdapter;
import cn.com.hgh.view.AbPullToRefreshView;

/*
 * @创建者     master
 * @创建时间   2016/10/19 18:18
 * @描述		   店铺列表
 *
 * @更新者     $Author$
 * @更新时间   $Date$
 * @更新描述
 */
public class LeaguesStorelistActivity extends BaseActivity {

    @Bind(R.id.act_leaguesstorelist_listview)
    ListView actLeaguesstorelistListview;
    @Bind(R.id.act_leaguesstorelist_abpulltorefreshview)
    AbPullToRefreshView actLeaguesstorelistAbpulltorefreshview;
    private Context mContext;
    private ArrayList<LeaguesYellBean> mDatas = new ArrayList<LeaguesYellBean>();
    private QuickAdapter<LeaguesYellBean> mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(R.layout.act_leaguesstorelist, NOTYPE);
        ButterKnife.bind(this);
        initData();
    }
    /**
     * 初始化View
     */
    private void initData() {
        setPageTitle("店铺列表");
        initAdapter();
        getYellList();
    }
    private void initAdapter() {
        mAdapter = new QuickAdapter<LeaguesYellBean>(this,
                R.layout.item_leaguesstorelist, mDatas) {

            @Override
            protected void convert(BaseAdapterHelper helper, LeaguesYellBean item) {
                ImageView iv_leaguesstoreclist_storelogo = helper.getView(R.id.iv_leaguesstoreclist_storelogo);//
                TextView tv_leaguesstoreclis_name = helper.getView(R.id.tv_leaguesstoreclis_name);//
                TextView tv_leaguesstoreclis_time = helper.getView(R.id.tv_leaguesstoreclis_time);//
                TextView tv_leaguesstoreclis_address = helper.getView(R.id.tv_leaguesstoreclis_address);

                Glide.with(LeaguesStorelistActivity.this).load(item.getArea()).error(R.mipmap.default_head).into(iv_leaguesstoreclist_storelogo);
                tv_leaguesstoreclis_name.setText(item.getAuthor());
                tv_leaguesstoreclis_time.setText(item.getAuthor());
                tv_leaguesstoreclis_address.setText(item.getAuthor());
            }
        };
        actLeaguesstorelistListview.setAdapter(mAdapter);
    }

    /**
     * 获取吆喝数据
     */
    private void getYellList() {




    }
}
