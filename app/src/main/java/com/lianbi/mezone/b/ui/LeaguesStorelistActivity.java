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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.com.hgh.baseadapter.BaseAdapterHelper;
import cn.com.hgh.baseadapter.QuickAdapter;
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
        getLeaguesStorelist();
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

                Glide.with(LeaguesStorelistActivity.this).load(item.getHeaderUrl()).error(R.mipmap.default_head).into(iv_leaguesstoreclist_storelogo);
                tv_leaguesstoreclis_name.setText(item.getBusinessName());
                tv_leaguesstoreclis_time.setText(item.getCreateTime());
                tv_leaguesstoreclis_address.setText(item.getAddress());
            }
        };
        actLeaguesstorelistListview.setAdapter(mAdapter);
    }

    /**
     * 获取店铺列表数据
     */
    private void getLeaguesStorelist() {
        try {
            okHttpsImp.getBusinessList(
                    UserId,
                    uuid,
                    reqTime,
                    new MyResultCallback<String>() {
                        @Override
                        public void onResponseResult(Result result) {
                            String reString = result.getData();
                            Log.i("tag", "resString 274----->" + reString);
                            try {
                                JSONObject jsonObject = new JSONObject(reString);
                                reString = jsonObject.getString("list");
                                if (!TextUtils.isEmpty(reString)) {
                                    mData.clear();
                                    ArrayList<BusinessListBean> businessbeanlist = (ArrayList<BusinessListBean>) JSON
                                            .parseArray(reString,
                                                    BusinessListBean.class);
                                    mData.addAll(businessbeanlist);
                                    updateView(mData);
                                    ivLeaguesstorelistEmpty.setVisibility(View.GONE);
                                    actLeaguesstorelistAbpulltorefreshview.setVisibility(View.VISIBLE);
                                }else{
                                    ivLeaguesstorelistEmpty.setVisibility(View.VISIBLE);
                                    actLeaguesstorelistAbpulltorefreshview.setVisibility(View.GONE);
                                }

                            } catch (JSONException e) {
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
        mAdapter.notifyDataSetChanged();
    }
}
