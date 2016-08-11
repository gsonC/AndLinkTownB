package com.lianbi.mezone.b.ui;

import android.graphics.Paint;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
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

    @Bind(R.id.txt_sendtime)
    TextView txtSendtime;
    @Bind(R.id.txt_sendnum)
    TextView txtSendnum;
    @Bind(R.id.txt_sendcontext)
    TextView txtSendcontext;
    @Bind(R.id.btn_sendobject)
    TextView btnSendobject;
    @Bind(R.id.lv_actmarketdetail)
    MyListView lvActmarketdetail;
    @Bind(R.id.sv_marketdetail)
    ScrollView svMarketdetail;
    private ArrayList<ServiceMallBean> mData = new ArrayList<ServiceMallBean>();
    private ArrayList<ServiceMallBean> mDatas = new ArrayList<ServiceMallBean>();
    HttpDialog dialog;
    private QuickAdapter<ServiceMallBean> mAdapter;

    @OnClick({R.id.rlv_actmarketing})
    public void OnClick(View v) {
        switch (v.getId()) {
            case R.id.rlv_actmarketing:


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
                R.layout.item_servicemall_list, mDatas) {

            @Override
            protected void convert(BaseAdapterHelper helper,
                                   final ServiceMallBean item) {
                LinearLayout llt_servicemall = helper
                        .getView(R.id.llt_servicemall);
                ImageView img_itemmall = helper
                        .getView(R.id.img_itemmall);
                TextView tv_servicename = helper
                        .getView(R.id.tv_servicename);
                TextView tv_download = helper
                        .getView(R.id.tv_download);
                TextView tv_newprice = helper
                        .getView(R.id.tv_newprice);
                TextView tv_oldprice = helper
                        .getView(R.id.tv_oldprice);
                ImageView img_right = helper
                        .getView(R.id.img_right);
                tv_oldprice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG );
                tv_oldprice.setText("¥"+String.valueOf(item.getOriginalPrice())+String.valueOf(item.getUnit()));
                tv_newprice.setText("¥"+String.valueOf(item.getPresentPrice())+String.valueOf(item.getUnit()));
                tv_servicename.setText(item.getAppName());
                if(item.getDownload().equals("N")){
                    Glide.with(MarketingMsgDetailActivity.this).load(item.getIcoUrl()).error(R.mipmap.default_head).into(img_itemmall);
                    tv_download.setVisibility(View.VISIBLE);
                    img_right.setVisibility(View.INVISIBLE);
                }else
                if(item.getDownload().equals("Y")){
                    Glide.with(MarketingMsgDetailActivity.this).load(item.getIcoUrl()).error(R.mipmap.default_head).into(img_itemmall);
                    tv_download.setVisibility(View.GONE);
                    img_right.setVisibility(View.VISIBLE);
                }
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
//                            updateView(mData);
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




