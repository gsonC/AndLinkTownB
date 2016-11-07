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
    private Context mContext;
    private ArrayList<LeaguesYellBean> mData = new ArrayList<LeaguesYellBean>();
    private ArrayList<LeaguesYellBean> mDatas = new ArrayList<LeaguesYellBean>();
    private QuickAdapter<LeaguesYellBean> mAdapter;
    private static final int REQUEST_CODE_RESULT = 1009;

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
        initAdapter();
        getYellData();
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

//                Glide.with(LeaguesYellListActivity.this).load(item.getArea()).error(R.mipmap.default_head).into(iv_leaguesyelllist_icon);
                iv_leaguesyelllist_name.setText(item.getAuthor());
                iv_leaguesyelllist_time.setText(item.getMessageType());
                iv_leaguesyelllist_title.setText(item.getMessageTitle());
                iv_leaguesyelllist_content.setText(item.getMessageContent());
                iv_leaguesyelllist_phone.setText(item.getPhone());
                iv_leaguesyelllist_address.setText(item.getAddress());
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
     * 查询吆喝和商圈动态
     */
    private void getYellData() {
//        ic(String businessId,
//                String area,
//                String businessCircle,
//                String messageType,
//                String pushScope,
//                String author,
//                String phone,
//                String messageTitle,
//                String messageContent,
//                String pageNum,
//                String pageSize,
//                String serNum, String source,
//                String reqTime,
        try {
            okHttpsImp.queryBusinessDynamic(
                    "BD2016052013475900000010",
                    "",
                    "310117",
                    "",
                    "",
                    "",
                    "",
                    "",
                    "",
                    "310000",
                    "",
                    "",
                    uuid,
                    "app",
                    reqTime,
                    new MyResultCallback<String>() {
                        @Override
                        public void onResponseResult(Result result) {
                            String reString = result.getData();
                            Log.i("tag","resString 132----->"+reString);
                            try {
                                JSONObject jsonObject= new JSONObject(reString);
                                reString = jsonObject.getString("list");
                                if (!TextUtils.isEmpty(reString)) {
                                    mData.clear();
                                    ArrayList<LeaguesYellBean> leaguesyellbeanlist = (ArrayList<LeaguesYellBean>) JSON
                                            .parseArray(reString,
                                                    LeaguesYellBean.class);
                                    for(LeaguesYellBean  LeaguesZxy:leaguesyellbeanlist){
                                        if(!LeaguesZxy.getMessageType().equals("MT0000")){
                                            mData.add(LeaguesZxy);
                                        }
                                    }
                                    updateView(mData);

                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }

                        @Override
                        public void onResponseFailed(String msg) {
                            ContentUtils.showMsg(LeaguesYellListActivity.this, "网络访问失败");
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
                    getYellData();
                    break;
            }
        }
    }
}
