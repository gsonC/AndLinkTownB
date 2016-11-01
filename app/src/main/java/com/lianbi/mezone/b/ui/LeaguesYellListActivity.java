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
 * @描述		   首页商圈吆喝列表
 *
 * @更新者     $Author$
 * @更新时间   $Date$
 * @更新描述
 */
public class LeaguesYellListActivity extends BaseActivity {

    @Bind(R.id.act_leaguesyell_listview)
    ListView actLeaguesyellListview;
    @Bind(R.id.act_leaguesyell_abpulltorefreshview)
    AbPullToRefreshView actLeaguesyellAbpulltorefreshview;
    private Context mContext;
    private ArrayList<LeaguesYellBean> mDatas = new ArrayList<LeaguesYellBean>();
    private QuickAdapter<LeaguesYellBean> mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(R.layout.act_leaguesyelllist, NOTYPE);
        ButterKnife.bind(this);
        initData();
    }
    /**
     * 初始化View
     */
    private void initData() {
        setPageTitle("吆喝列表");
        initAdapter();
        getYellList();
    }
    private void initAdapter() {
        mAdapter = new QuickAdapter<LeaguesYellBean>(this,
                R.layout.item_leaguesyelllist, mDatas) {

            @Override
            protected void convert(BaseAdapterHelper helper, LeaguesYellBean item) {
                ImageView iv_leaguesyelllist_icon = helper.getView(R.id.iv_leaguesyelllist_icon);//
                TextView iv_leaguesyelllist_name = helper.getView(R.id.iv_leaguesyelllist_name);//
                TextView iv_leaguesyelllist_time = helper.getView(R.id.iv_leaguesyelllist_time);//
                TextView iv_leaguesyelllist_response = helper.getView(R.id.iv_leaguesyelllist_response);
                TextView iv_leaguesyelllist_title = helper.getView(R.id.iv_leaguesyelllist_title);//
                TextView iv_leaguesyelllist_content = helper.getView(R.id.iv_leaguesyelllist_content);//
                TextView iv_leaguesyelllist_readmore = helper.getView(R.id.iv_leaguesyelllist_readmore);//
                TextView iv_leaguesyelllist_phone = helper.getView(R.id.iv_leaguesyelllist_phone);//
                TextView iv_leaguesyelllist_address = helper.getView(R.id.iv_leaguesyelllist_address);//

                Glide.with(LeaguesYellListActivity.this).load(item.getArea()).error(R.mipmap.default_head).into(iv_leaguesyelllist_icon);
                iv_leaguesyelllist_name.setText(item.getAuthor());
                iv_leaguesyelllist_name.setText(item.getAuthor());
                iv_leaguesyelllist_name.setText(item.getAuthor());
                iv_leaguesyelllist_name.setText(item.getAuthor());
                iv_leaguesyelllist_name.setText(item.getAuthor());
                iv_leaguesyelllist_name.setText(item.getAuthor());
                iv_leaguesyelllist_name.setText(item.getAuthor());
            }
        };
        actLeaguesyellListview.setAdapter(mAdapter);
    }

    /**
     *获取吆喝数据
     */
    private void getYellList() {


    }
}
