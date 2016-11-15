package com.lianbi.mezone.b.ui;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.lianbi.mezone.b.bean.LeaguesYellBean;
import com.lianbi.mezone.b.httpresponse.MyResultCallback;
import com.xizhi.mezone.b.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.com.hgh.baseadapter.BaseAdapterHelper;
import cn.com.hgh.baseadapter.QuickAdapter;
import cn.com.hgh.utils.AbPullHide;
import cn.com.hgh.utils.Result;
import cn.com.hgh.view.AbPullToRefreshView;
import cn.com.hgh.view.HttpDialog;

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
    @Bind(R.id.iv_LeaguesDynamicList_empty)
    ImageView ivLeaguesDynamicListEmpty;
    private Context mContext;
    private ArrayList<LeaguesYellBean> mData = new ArrayList<LeaguesYellBean>();
    private ArrayList<LeaguesYellBean> mDatas = new ArrayList<LeaguesYellBean>();
    private QuickAdapter<LeaguesYellBean> mAdapter;
    boolean isExpanded = false;
    private int page = 1;
    HttpDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(R.layout.act_leaguesdynamiclist, NOTYPE);
        ButterKnife.bind(this);
        initData();
        setLisenter();
        initAdapter();
        getLeaguesDynamicData(true);
    }

    /**
     * 初始化View
     */
    private void initData() {
        setPageTitle("商圈动态列表");
    }
    private void setLisenter() {
        actLeaguesdynamiclistAbpulltorefreshview.setLoadMoreEnable(true);
        actLeaguesdynamiclistAbpulltorefreshview.setPullRefreshEnable(true);
        actLeaguesdynamiclistAbpulltorefreshview
                .setOnHeaderRefreshListener(new AbPullToRefreshView.OnHeaderRefreshListener() {

                    @Override
                    public void onHeaderRefresh(AbPullToRefreshView view) {
                        getLeaguesDynamicData(true);
                    }

                });
        actLeaguesdynamiclistAbpulltorefreshview
                .setOnFooterLoadListener(new AbPullToRefreshView.OnFooterLoadListener() {

                    @Override
                    public void onFooterLoad(AbPullToRefreshView view) {
                        getLeaguesDynamicData(false);
                    }
                });
    }
    private void initAdapter() {
        mAdapter = new QuickAdapter<LeaguesYellBean>(this,
                R.layout.item_leaguesdynamiclist, mDatas) {

            boolean   tempExpanded=false;
            @Override
            protected void convert(final BaseAdapterHelper helper, final  LeaguesYellBean item) {
                LinearLayout  lay_leaguesdynamiclist_firsttitle=
                        helper.getView(R.id.lay_leaguesdynamiclist_firsttitle);
                ImageView iv_leaguesdynamiclist_icon = helper.getView(R.id.iv_leaguesdynamiclist_icon);//
                TextView tv_leaguesdynamiclist_firsttitle = helper.getView(R.id.tv_leaguesdynamiclist_firsttitle);//
                ImageView iv_leaguesdynamiclist_expand = helper.getView(R.id.iv_leaguesdynamiclist_expand);//
                TextView tv_leaguesdynamiclist_content = helper.getView(R.id.tv_leaguesdynamiclist_content);

                Glide.with(LeaguesDynamicListActivity.this).load
                        (compareMessageType(item.getMessageType())).error(R.mipmap.icon).into(iv_leaguesdynamiclist_icon);
                tv_leaguesdynamiclist_firsttitle.setText(item.getMessageTitle());
                tv_leaguesdynamiclist_content.setText(item.getMessageContent());
                if (!item.isExpanded()) {
                    iv_leaguesdynamiclist_expand.setImageResource(R.mipmap.down12);
                    tv_leaguesdynamiclist_content.setVisibility(View.GONE);
                } else {
                    iv_leaguesdynamiclist_expand.setImageResource(R.mipmap.up2);
                    tv_leaguesdynamiclist_content.setVisibility(View.VISIBLE);
                }
                helper.getView(R.id.lay_leaguesdynamiclist_firsttitle).setOnClickListener(
                        new View.OnClickListener() {

                            @Override
                            public void onClick(View v) {
                                int   datasize=mDatas.size();
                                for (int i = 0; i < datasize; i++) {
                                    if (i != helper.getPosition()) {
                                        mDatas.get(i).setExpanded(false);
                                    }
                                }
                                if(item.isExpanded()){
                                    item.setExpanded(false);
                                }else{
                                    item.setExpanded(true);
                                }
                                mAdapter.replaceAll(mDatas);
                            }

                });
            }
        };
        actLeaguesdynamiclistListview.setAdapter(mAdapter);
    }
    protected  int  compareMessageType(String messagetype){
        if(messagetype.equals("MT0001")){
            return R.mipmap.icon_news;
        }
        else
        if(messagetype.equals("MT0002")){
            return R.mipmap.icon_cooperate;
        }
        else
        if(messagetype.equals("MT0003")){
            return R.mipmap.icon_discount;
        }
        return 0;
    }
    /**
     * 查询吆喝和商圈动态
     */
    private void getLeaguesDynamicData(final boolean isResh) {
        if (isResh) {
            page =1;
            mDatas.clear();
        }
        try {
            okHttpsImp.queryBusinessDynamic(
                    "",                        //BD2016052013475900000010
                    "",                        //area
                    areaCode ,                  //businessCircle"310117"
                    "",                        //messageType
                    "",                        //pushScope
                    "",                        //businessName
                    "",                        //phone
                    "",                        //messageTitle
                    "",                        //messageContent
                    shopRovinceid,                 //provinces   "310000"
                    page+"",                  //pageNum
                    "50",                       //pageSize
                    uuid,                     //serNum
                    "app",                    //source
                    reqTime,                  //reqTime
                    new MyResultCallback<String>() {
                        @Override
                        public void onResponseResult(Result result) {
                            page++;
                            String reString = result.getData();
                            Log.i("tag","resString 132----->"+reString);
                            try {
                                JSONObject jsonObject= new JSONObject(reString);
                                reString = jsonObject.getString("list");
                                if (!TextUtils.isEmpty(reString)) {
                                    ivLeaguesDynamicListEmpty.setVisibility(View.GONE);
                                    actLeaguesdynamiclistAbpulltorefreshview.setVerticalGravity(View.VISIBLE);
                                    mData.clear();
                                    ArrayList<LeaguesYellBean> leaguesyellbeanlist = (ArrayList<LeaguesYellBean>) JSON
                                            .parseArray(reString,
                                                    LeaguesYellBean.class);
                                    for(LeaguesYellBean  LeaguesZxy:leaguesyellbeanlist){
                                        if(!LeaguesZxy.getMessageType().equals("MT0000")){
                                            mData.add(LeaguesZxy);
                                        }
                                    }
                                    if(mData.size()==0){
                                        ivLeaguesDynamicListEmpty.setVisibility(View.VISIBLE);
                                        actLeaguesdynamiclistAbpulltorefreshview.setVerticalGravity(View.GONE);
                                    }else{
                                        ivLeaguesDynamicListEmpty.setVisibility(View.GONE);
                                        actLeaguesdynamiclistAbpulltorefreshview.setVerticalGravity(View.VISIBLE);
                                        updateView(mData);
                                    }
                                }else{
                                    ivLeaguesDynamicListEmpty.setVisibility(View.VISIBLE);
                                    actLeaguesdynamiclistAbpulltorefreshview.setVerticalGravity(View.GONE);
                                }
                                AbPullHide.hideRefreshView(isResh,actLeaguesdynamiclistAbpulltorefreshview);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }

                        @Override
                        public void onResponseFailed(String msg) {
                            AbPullHide.hideRefreshView(isResh,actLeaguesdynamiclistAbpulltorefreshview);
                            ivLeaguesDynamicListEmpty.setVisibility(View.VISIBLE);
                            actLeaguesdynamiclistAbpulltorefreshview.setVerticalGravity(View.GONE);
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    protected void updateView(ArrayList<LeaguesYellBean> arrayList) {
        mDatas.clear();
        mDatas.addAll(arrayList);
        mAdapter.replaceAll(mDatas);
    }
}
