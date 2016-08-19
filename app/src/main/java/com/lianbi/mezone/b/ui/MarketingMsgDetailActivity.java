package com.lianbi.mezone.b.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.lianbi.mezone.b.bean.ServiceMallBean;
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
    private ArrayList<ServiceMallBean> mData = new ArrayList<ServiceMallBean>();
    private ArrayList<ServiceMallBean> mDatas = new ArrayList<ServiceMallBean>();
    HttpDialog dialog;
    private QuickAdapter<ServiceMallBean> mAdapter;

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
        getCandownloadMall();
    }

    private void listviewData() {
        mAdapter = new QuickAdapter<ServiceMallBean>(MarketingMsgDetailActivity.this,
                R.layout.item_marketingmsgdetail_list, mDatas) {

            @Override
            protected void convert(BaseAdapterHelper helper,
                                   final ServiceMallBean item) {

                LinearLayout llt_marketingmsgl = helper
                        .getView(R.id.llt_marketingmsgl);
                TextView tv_sendingtime = helper
                        .getView(R.id.tv_sendingtime);
                TextView tv_sendingobject = helper
                        .getView(R.id.tv_sendingobject);
                TextView tv_sendingnum = helper
                        .getView(R.id.tv_sendingnum);
                tv_sendingtime.setText(item.getAppName());
                tv_sendingobject.setText("¥" + String.valueOf(item.getOriginalPrice()) + String.valueOf(item.getUnit()));
                tv_sendingnum.setText("¥" + String.valueOf(item.getPresentPrice()) + String.valueOf(item.getUnit()));

            }
        };
        // 设置适配器
        lvActmarketdetail.setAdapter(mAdapter);


    }

    private void getCandownloadMall() {
        okHttpsImp.getCandownloadServerMall(new MyResultCallback<String>() {

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
                            ArrayList<ServiceMallBean> downloadListMall = (ArrayList<ServiceMallBean>) JSON
                                    .parseArray(reString,
                                            ServiceMallBean.class);
                            mData.addAll(downloadListMall);
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
        }, userShopInfoBean.getBusinessId());

    }

    protected void updateView(ArrayList<ServiceMallBean> arrayList) {
        mDatas.clear();
        mDatas.addAll(arrayList);
        mAdapter.replaceAll(mDatas);
    }

}




