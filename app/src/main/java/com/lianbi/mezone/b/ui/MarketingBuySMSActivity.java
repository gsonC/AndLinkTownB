package com.lianbi.mezone.b.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.RelativeLayout;
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

/**
 * 购买短信
 *
 * @author
 * @time
 * @date
 */
public class MarketingBuySMSActivity extends BaseActivity {

    @Bind(R.id.txt_msg)
    TextView txtMsg;
    @Bind(R.id.txt_alreadysendnum)
    TextView txtAlreadysendnum;
    @Bind(R.id.txt_remainsendmsg)
    TextView txtRemainsendmsg;
    @Bind(R.id.txt_remainsendnum)
    TextView txtRemainsendnum;
    @Bind(R.id.ray_top)
    RelativeLayout rayTop;
    @Bind(R.id.v_01)
    View v01;
    @Bind(R.id.txt_price)
    TextView txtPrice;
    @Bind(R.id.act_buysms_list)
    ListView actBuysmsList;
    private ArrayList<ServiceMallBean> mData = new ArrayList<ServiceMallBean>();
    private ArrayList<ServiceMallBean> mDatas = new ArrayList<ServiceMallBean>();
    HttpDialog dialog;
    QuickAdapter<ServiceMallBean> mAdapter;

    @OnClick({R.id.txt_buymsm})
    public void OnClick(View v) {
        switch (v.getId()) {
            case R.id.txt_buymsm:


            break;

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_marketingbuysms, HAVETYPE);
        ButterKnife.bind(this);
        initViewAndData();
    }

    /**
     * 初始化View
     */
    private void initViewAndData() {
        setPageTitle("短信购买");
        dialog = new HttpDialog(this);
        listviewData();
        getCandownloadMall();
    }

    private void listviewData() {
        mAdapter = new QuickAdapter<ServiceMallBean>(MarketingBuySMSActivity.this,
                R.layout.item_marketingbuysms_list, mDatas) {

            private int index = -1;

            @Override
            protected BaseAdapterHelper getAdapterHelper(int position, View convertView, ViewGroup parent) {

                return super.getAdapterHelper(position, convertView, parent);

            }

            @Override
            protected void convert(final BaseAdapterHelper helper,
                                   final ServiceMallBean item) {
                TextView tv_price = helper
                        .getView(R.id.tv_price);
                TextView tv_priceunit = helper
                        .getView(R.id.tv_priceunit);
                TextView tv_totalnumber = helper
                        .getView(R.id.tv_totalnumber);
                TextView tv_numberofgifts = helper
                        .getView(R.id.tv_numberofgifts);
                CheckBox cb_choice = helper
                        .getView(R.id.cb_choice);

                tv_totalnumber.setText(item.getAppName());
                tv_numberofgifts.setText(item.getAppName());
                helper.getView(R.id.cb_choice).setOnClickListener(
                new  View.OnClickListener(){
                            @Override
                    public void onClick(View v) {
                            index=helper.getPosition();
                            notifyDataSetChanged();
                            }
                        }
                );
                if (index == helper.getPosition()) {// 选中的条目和当前的条目是否相等
                    cb_choice.setChecked(true);
                } else {
                    cb_choice.setChecked(false);
                }
            }
        };
        actBuysmsList.setAdapter(mAdapter);
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




