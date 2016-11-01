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
 * @描述		   首页商圈动态更多列表
 *
 * @更新者     $Author$
 * @更新时间   $Date$
 * @更新描述
 */
public class LeaguesDynamicListActivity extends BaseActivity {

    @Bind(R.id.act_leaguesdynamiclist_listview)
    ListView actLeaguesdynamiclistListview;
    @Bind(R.id.act_leaguesdynamiclist_abpulltorefreshview)
    AbPullToRefreshView actLeaguesdynamiclistAbpulltorefreshview;
    private Context mContext;
    private ArrayList<LeaguesYellBean> mDatas = new ArrayList<LeaguesYellBean>();
    private QuickAdapter<LeaguesYellBean> mAdapter;
    boolean isExpanded=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(R.layout.act_leaguesdynamiclist, NOTYPE);
        ButterKnife.bind(this);
        initData();
    }

    /**
     * 初始化View
     */
    private void initData() {
        setPageTitle("商圈动态列表");
    }

    private void initAdapter() {
        mAdapter = new QuickAdapter<LeaguesYellBean>(this,
                R.layout.item_leaguesdynamiclist, mDatas) {

            @Override
            protected void convert(BaseAdapterHelper helper, LeaguesYellBean item) {
                ImageView iv_leaguesdynamiclist_icon = helper.getView(R.id.iv_leaguesdynamiclist_icon);//
                TextView tv_leaguesdynamiclist_firsttitle = helper.getView(R.id.tv_leaguesdynamiclist_firsttitle);//
                ImageView iv_leaguesdynamiclist_expand = helper.getView(R.id.iv_leaguesdynamiclist_expand);//
                TextView tv_leaguesdynamiclist_content = helper.getView(R.id.tv_leaguesdynamiclist_content);

                Glide.with(LeaguesDynamicListActivity.this).load(item.getArea()).error(R.mipmap.default_head).into(iv_leaguesdynamiclist_icon);
                tv_leaguesdynamiclist_firsttitle.setText(item.getAuthor());
                tv_leaguesdynamiclist_content.setText(item.getAuthor());
                if (isExpanded) {
                    iv_leaguesdynamiclist_expand.setImageResource(R.mipmap.up2);
                } else {
                    iv_leaguesdynamiclist_expand.setImageResource(R.mipmap.down12);
                }
                //定义当前item是否处于展开状态的字段
            }
        };
        actLeaguesdynamiclistListview.setAdapter(mAdapter);
    }

}
