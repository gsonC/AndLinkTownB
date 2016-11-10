package com.lianbi.mezone.b.ui;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.lianbi.mezone.b.bean.BusinessListBean;
import com.lianbi.mezone.b.httpresponse.MyResultCallback;
import com.xizhi.mezone.b.R;

import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.com.hgh.baseadapter.BaseAdapterHelper;
import cn.com.hgh.baseadapter.QuickAdapter;
import cn.com.hgh.utils.AbPullHide;
import cn.com.hgh.utils.Result;
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
    @Bind(R.id.iv_leaguesstorelist_empty)
    ImageView ivLeaguesstorelistEmpty;
    private Context mContext;
    private ArrayList<BusinessListBean> mData = new ArrayList<BusinessListBean>();
    private ArrayList<BusinessListBean> mDatas = new ArrayList<BusinessListBean>();
    private QuickAdapter<BusinessListBean> mAdapter;
    private int page = 1;

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
        setLisenter();
        getLeaguesStorelist(true);
    }
    private void setLisenter() {
        actLeaguesstorelistAbpulltorefreshview.setLoadMoreEnable(true);
        actLeaguesstorelistAbpulltorefreshview.setPullRefreshEnable(true);
        actLeaguesstorelistAbpulltorefreshview
                .setOnHeaderRefreshListener(new AbPullToRefreshView.OnHeaderRefreshListener() {

                    @Override
                    public void onHeaderRefresh(AbPullToRefreshView view) {
                        getLeaguesStorelist(true);
                    }

                });
        actLeaguesstorelistAbpulltorefreshview
                .setOnFooterLoadListener(new AbPullToRefreshView.OnFooterLoadListener() {

                    @Override
                    public void onFooterLoad(AbPullToRefreshView view) {
                        getLeaguesStorelist(false);
                    }
                });
    }
    private void initAdapter() {
        mAdapter = new QuickAdapter<BusinessListBean>(this,
                R.layout.item_leaguesstorelist, mDatas) {

            @Override
            protected void convert(BaseAdapterHelper helper, BusinessListBean item) {
                ImageView iv_leaguesstoreclist_storelogo = helper.getView(R.id.iv_leaguesstoreclist_storelogo);//
                TextView tv_leaguesstoreclis_name = helper.getView(R.id.tv_leaguesstoreclis_name);//
                TextView tv_leaguesstoreclis_time = helper.getView(R.id.tv_leaguesstoreclis_time);//
                TextView tv_leaguesstoreclis_address = helper.getView(R.id.tv_leaguesstoreclis_address);
                if(item.getLogoUrl()!=null) {
                    Glide.with(LeaguesStorelistActivity.this).load(item.getLogoUrl()).error(R.mipmap.demo).into(iv_leaguesstoreclist_storelogo);
                }else{
                    Glide.with(LeaguesStorelistActivity.this).load(R.mipmap.demo).error(R.mipmap.demo).into(iv_leaguesstoreclist_storelogo);

                }
                tv_leaguesstoreclis_name.setText(item.getBusinessName());
                if(!TextUtils.isEmpty(item.getCreateTime())){
                    String time=item.getCreateTime().substring(0,10);
                    tv_leaguesstoreclis_time.setText(time);
                }
                tv_leaguesstoreclis_address.setText(item.getAddress());
            }
        };
        actLeaguesstorelistListview.setAdapter(mAdapter);
    }

    /**
     * 获取店铺列表数据
     */
    private void getLeaguesStorelist(final boolean isResh) {
        if (isResh) {
            page = 1;
            mDatas.clear();
        }
        try {
            okHttpsImp.getBusinessList(
                    "120101",
                    uuid,
                    reqTime,
                    new MyResultCallback<String>() {
                        @Override
                        public void onResponseResult(Result result) {
                            page++;
                            String reString = result.getData();
                            Log.i("tag", "resString 274----->" + reString);
                            try {
                                JSONObject jsonObject = new JSONObject(reString);
                                reString = jsonObject.getString("modelList");
                                if (!TextUtils.isEmpty(reString)) {
                                    mData.clear();
                                    ArrayList<BusinessListBean> businessbeanlist = (ArrayList<BusinessListBean>) JSON
                                            .parseArray(reString,
                                                    BusinessListBean.class);
                                    mData.addAll(businessbeanlist);
                                    AbPullHide.hideRefreshView(isResh,actLeaguesstorelistAbpulltorefreshview);
                                    updateView(mData);
                                    ivLeaguesstorelistEmpty.setVisibility(View.GONE);
                                    actLeaguesstorelistAbpulltorefreshview.setVisibility(View.VISIBLE);
                                }else{
                                    AbPullHide.hideRefreshView(isResh,actLeaguesstorelistAbpulltorefreshview);
                                    ivLeaguesstorelistEmpty.setVisibility(View.VISIBLE);
                                    actLeaguesstorelistAbpulltorefreshview.setVisibility(View.GONE);
                                }

                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }

                        @Override
                        public void onResponseFailed(String msg) {
                            ivLeaguesstorelistEmpty.setVisibility(View.VISIBLE);
                            actLeaguesstorelistAbpulltorefreshview.setVisibility(View.GONE);
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    protected void updateView(ArrayList<BusinessListBean> arrayList) {
        mDatas.clear();
        mDatas.addAll(arrayList);
        mAdapter.replaceAll(arrayList);
    }
}
