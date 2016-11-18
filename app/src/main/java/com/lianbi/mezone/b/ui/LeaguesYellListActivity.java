package com.lianbi.mezone.b.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.lianbi.mezone.b.bean.LeaguesYellBean;
import com.lianbi.mezone.b.httpresponse.MyResultCallback;
import com.xizhi.mezone.b.R;

import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.com.hgh.baseadapter.BaseAdapterHelper;
import cn.com.hgh.baseadapter.QuickAdapter;
import cn.com.hgh.utils.AbPullHide;
import cn.com.hgh.utils.ContentUtils;
import cn.com.hgh.utils.Result;
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
    @Bind(R.id.iv_leaguesyell_empty)
    ImageView ivLeaguesyellEmpty;
    private Context mContext;
    private ArrayList<LeaguesYellBean> mData = new ArrayList<LeaguesYellBean>();
    private ArrayList<LeaguesYellBean> mDatas = new ArrayList<LeaguesYellBean>();
    private ArrayList<LeaguesYellBean> mSortData = new ArrayList<LeaguesYellBean>();
    LeaguesYellBean  mLeaguesYellBean;
    private QuickAdapter<LeaguesYellBean> mAdapter;
    private static final int REQUEST_CODE_RESULT = 1009;
    private int page =1;
    private Intent  getIntent;
      int whatchild=0;
    private String BusinessId="";
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
        setPageRightText("发起吆喝");
        getIntent=getIntent();
        whatchild=getIntent.getIntExtra("whatchild",0);
        if(!TextUtils.isEmpty(getIntent.getStringExtra("businessId"))){
            BusinessId=getIntent.getStringExtra("businessId");
        }else{
            BusinessId="";
        }
        setLisenter();
        initAdapter();
        getYellData(true);
    }
    private void setLisenter() {
        actLeaguesyellAbpulltorefreshview.setLoadMoreEnable(true);
        actLeaguesyellAbpulltorefreshview.setPullRefreshEnable(true);
        actLeaguesyellAbpulltorefreshview
                .setOnHeaderRefreshListener(new AbPullToRefreshView.OnHeaderRefreshListener() {

                    @Override
                    public void onHeaderRefresh(AbPullToRefreshView view) {
                        getYellData(true);
                    }

                });
        actLeaguesyellAbpulltorefreshview
                .setOnFooterLoadListener(new AbPullToRefreshView.OnFooterLoadListener() {

                    @Override
                    public void onFooterLoad(AbPullToRefreshView view) {
                        getYellData(false);
                    }
                });
    }
    private void initAdapter() {
        mAdapter = new QuickAdapter<LeaguesYellBean>(this,
                R.layout.item_leaguesyelllist, mDatas) {

            @Override
            protected void convert(BaseAdapterHelper helper, final LeaguesYellBean item) {
                ImageView iv_leaguesyelllist_icon = helper.getView(R.id.iv_leaguesyelllist_icon);//
                TextView iv_leaguesyelllist_name = helper.getView(R.id.iv_leaguesyelllist_name);//
                TextView iv_leaguesyelllist_time = helper.getView(R.id.iv_leaguesyelllist_time);//
                TextView iv_leaguesyelllist_response = helper.getView(R.id.iv_leaguesyelllist_response);
                TextView iv_leaguesyelllist_title = helper.getView(R.id.iv_leaguesyelllist_title);//
                TextView iv_leaguesyelllist_content = helper.getView(R.id.iv_leaguesyelllist_content);//
                TextView iv_leaguesyelllist_readmore = helper.getView(R.id.iv_leaguesyelllist_readmore);//
                TextView iv_leaguesyelllist_phone = helper.getView(R.id.iv_leaguesyelllist_phone);//
                TextView iv_leaguesyelllist_address = helper.getView(R.id.iv_leaguesyelllist_address);//

                if(!TextUtils.isEmpty(item.getLogoUrl())) {
                    Glide.with(LeaguesYellListActivity.this).load(item.getLogoUrl()).error(R.mipmap.demo).into(iv_leaguesyelllist_icon);
                }
                if(!TextUtils.isEmpty(item.getBusinessName())) {
                    iv_leaguesyelllist_name.setText(item.getBusinessName());
                }
                if(!TextUtils.isEmpty(item.getCreateTime())) {
                    String time = item.getCreateTime();
                    String year = time.substring(0, 4);
                    String months = time.substring(4, 6);
                    String daytime = time.substring(6, 8);
                    iv_leaguesyelllist_time.setText(year + "-" + months + "-"
                            + daytime );
                }
                if(!TextUtils.isEmpty(item.getMessageTitle())) {
                    iv_leaguesyelllist_title.setText(item.getMessageTitle());
                }
                if(!TextUtils.isEmpty(item.getMessageContent())) {
                    iv_leaguesyelllist_content.setText(item.getMessageContent());
                }
                if(!TextUtils.isEmpty(item.getPhone())) {
                    iv_leaguesyelllist_phone.setText(item.getPhone());
                }
                if(!TextUtils.isEmpty(item.getAddress())) {
                    iv_leaguesyelllist_address.setText(item.getAddress());
                }
                helper.getView(R.id.iv_leaguesyelllist_readmore).setOnClickListener(
                        new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            Intent   intent=new Intent();
                            intent.setClass(LeaguesYellListActivity.this, LeaguesYellDetailsActivity.class);
                            intent.putExtra("leaguesyellbean", item);
                            startActivity(intent);

                        }
                });
                helper.getView(R.id.iv_leaguesyelllist_response).setOnClickListener(
                        new View.OnClickListener() {

                            @Override
                            public void onClick(View v) {
                                ContentUtils.showMsg(LeaguesYellListActivity.this, getString(R.string.underconstruction));
                            }
                        });
            }
        };
        actLeaguesyellListview.setAdapter(mAdapter);
    }
    @Override
    protected void onTitleRightClickTv() {
        super.onTitleRightClickTv();
        Intent intent = new Intent(this, LeaguesPublishYellActivity.class);
        startActivityForResult(intent, REQUEST_CODE_RESULT);
    }
    /**
     * 返回键点击事件
     *
     */
//    @Override
//    protected void onTitleLeftClick() {
//        Intent intent = new Intent(LeaguesYellListActivity.this, MainActivity.class);
//        setResult(RESULT_OK, intent);
//        finish();
//    }
    /**
     * 查询吆喝和商圈动态
     */
    private void getYellData(final boolean isResh) {
        if (isResh) {
            page = 1;
            mDatas.clear();
            mSortData.clear();
        }
        try {
            okHttpsImp.queryBusinessDynamic(
                    BusinessId,                         //BD2016052013475900000010businessId
                    "",//area
                    areaCode,//businessCircle"310117"
                    "",//messageType
                    "",//pushScope
                    "",//businessName
                    "",//phone
                    "",//messageTitle
                    "",//messageContent
                    shopRovinceid,//provinces"310000"
                    page+"",                     //pageNum
                    "50",//pageSize
                    uuid,//
                    "app",//
                    reqTime,//
                    new MyResultCallback<String>() {
                        @Override
                        public void onResponseResult(Result result) {
                            String reString = result.getData();
                            Log.i("tag","resString 132----->"+reString);
                            try {
                                    JSONObject jsonObject = new JSONObject(reString);
                                    reString = jsonObject.getString("list");
                                    if (!TextUtils.isEmpty(reString)) {
                                        mData.clear();
                                        ArrayList<LeaguesYellBean> leaguesyellbeanlist = (ArrayList<LeaguesYellBean>) JSON
                                                .parseArray(reString,
                                                        LeaguesYellBean.class);
                                        for (LeaguesYellBean LeaguesZxy : leaguesyellbeanlist) {
                                            if (LeaguesZxy.getMessageType().equals("MT0000")) {
                                                mData.add(LeaguesZxy);
                                            }
                                        }
                                        if (page == 1 && mData.size() != 0) {
                                            int datasize = mData.size();
                                            for (int i = 0; i < datasize; i++) {
                                                if (i == whatchild) {
                                                    mLeaguesYellBean = mData.get(i);
                                                    mData.remove(i);
                                                }

                                            }
                                            mSortData.add(mLeaguesYellBean);
                                            mSortData.addAll(mData);
                                            updateView(mSortData);
                                        } else if (mData.size() != 0) {
                                            mSortData.addAll(mData);
                                            updateView(mSortData);
                                        }
                                        page++;
                                    }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            if (!mSortData.isEmpty()) {
                                actLeaguesyellAbpulltorefreshview.setVisibility(View.VISIBLE);
                                ivLeaguesyellEmpty.setVisibility(View.GONE);
                            } else {
                                actLeaguesyellAbpulltorefreshview.setVisibility(View.GONE);
                                ivLeaguesyellEmpty.setVisibility(View.VISIBLE);
                            }
                            AbPullHide.hideRefreshView(isResh,actLeaguesyellAbpulltorefreshview);
                        }
                        @Override
                        public void onResponseFailed(String msg) {
                            AbPullHide.hideRefreshView(isResh,actLeaguesyellAbpulltorefreshview);
                            ivLeaguesyellEmpty.setVisibility(View.VISIBLE);
                            actLeaguesyellAbpulltorefreshview.setVerticalGravity(View.GONE);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CODE_RESULT:
                    getYellData(true);
                    break;
            }
        }
    }
}
