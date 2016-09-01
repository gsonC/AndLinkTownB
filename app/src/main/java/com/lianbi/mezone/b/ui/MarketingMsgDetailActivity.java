package com.lianbi.mezone.b.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.lianbi.mezone.b.bean.MarketingMsgDetail;
import com.lianbi.mezone.b.httpresponse.MyResultCallback;
import com.xizhi.mezone.b.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.hgh.baseadapter.BaseAdapterHelper;
import cn.com.hgh.baseadapter.QuickAdapter;
import cn.com.hgh.utils.Result;
import cn.com.hgh.view.HttpDialog;
import cn.com.hgh.view.MyListView;

/**
 * 已发送短信详情
 *
 * @author
 * @time
 * @date
 */
public class MarketingMsgDetailActivity extends BaseActivity {


    @Bind(R.id.txt_time)
    TextView txtTime;
    @Bind(R.id.txt_sendtime)
    TextView txtSendtime;
    @Bind(R.id.txt_num)
    TextView txtNum;
    @Bind(R.id.txt_sendnum)
    TextView txtSendnum;
    @Bind(R.id.ray_top)
    RelativeLayout rayTop;
    @Bind(R.id.txt_sendcontext)
    TextView txtSendcontext;
    @Bind(R.id.ray_msgcontext)
    RelativeLayout rayMsgcontext;
    @Bind(R.id.txt_memberphone)
    TextView txtMemberphone;
    @Bind(R.id.txt_membergrade)
    TextView txtMembergrade;
    @Bind(R.id.txt_membertag)
    TextView txtMembertag;
    @Bind(R.id.lay_listtmenu)
    LinearLayout layListtmenu;
    @Bind(R.id.v_01)
    View v01;
    @Bind(R.id.lv_actmarketdetail)
    MyListView lvActmarketdetail;
    @Bind(R.id.sv_marketdetail)
    ScrollView svMarketdetail;
    private ArrayList<MarketingMsgDetail> mData = new ArrayList<MarketingMsgDetail>();
    private ArrayList<MarketingMsgDetail> mDatas = new ArrayList<MarketingMsgDetail>();
    HttpDialog dialog;
    private QuickAdapter<MarketingMsgDetail> mAdapter;
    private String pageNo="1";
    private String pageSize="10";
    @OnClick({R.id.txt_sendcontext})
    public void OnClick(View v) {
        switch (v.getId()) {
            case R.id.txt_sendcontext:


            break;

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_marketingmsgdetail, HAVETYPE);
        ButterKnife.bind(this);
        initViewAndData();
    }

    /**
     * 初始化View
     */
    private void initViewAndData() {
        setPageTitle("已发送短信详情");
        dialog = new HttpDialog(this);
        listviewData();
        querySendMsgDetail();
    }

    private void listviewData() {
        mAdapter = new QuickAdapter<MarketingMsgDetail>(MarketingMsgDetailActivity.this,
                R.layout.item_marketingmsgdetail_list, mDatas) {

            @Override
            protected void convert(BaseAdapterHelper helper,
                                   final MarketingMsgDetail item) {

                LinearLayout llt_marketingmsgl = helper
                        .getView(R.id.llt_marketingmsgl);
                TextView tv_sendmobile = helper
                        .getView(R.id.tv_sendmobile);
                TextView tv_membergrade = helper
                        .getView(R.id.tv_membergrade);
                TextView tv_memberlable = helper
                        .getView(R.id.tv_memberlable);
                tv_sendmobile.setText(item.getMobile());
                tv_membergrade.setText(item.getCoupGrade());
                tv_memberlable.setText(item.getCoupNote());
            }
        };
        // 设置适配器
        lvActmarketdetail.setAdapter(mAdapter);


    }

    private void querySendMsgDetail() {
        try {
            okHttpsImp.querySendMsgDetail(new MyResultCallback<String>() {

                @Override
                public void onResponseResult(Result result) {
                    String reString = result.getData();
                    if (reString != null) {
                        JSONObject jsonObject;
                        try {
                            jsonObject = new JSONObject(reString);
                            reString = jsonObject.getString("appsList");
                            if (!TextUtils.isEmpty(reString)) {
                                mData.clear();
                                ArrayList<MarketingMsgDetail> marketingmsgdetailList = (ArrayList<MarketingMsgDetail>) JSON
                                        .parseArray(reString,
                                                MarketingMsgDetail.class);
                                mData.addAll(marketingmsgdetailList);
                                updateView(mData);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    dialog.dismiss();
                }

                @Override
                public void onResponseFailed(String msg) {
                    dialog.dismiss();
                }
            }, userShopInfoBean.getBusinessId(),"",pageNo,pageSize,reqTime,uuid);
        }catch (Exception e){e.printStackTrace();}


    }

    protected void updateView(ArrayList<MarketingMsgDetail> arrayList) {
        mDatas.clear();
        mDatas.addAll(arrayList);
        mAdapter.replaceAll(mDatas);
    }

}




