package com.lianbi.mezone.b.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.alibaba.fastjson.JSON;
import com.lianbi.mezone.b.bean.ServiceMallBean;
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
import cn.com.hgh.view.HttpDialog;
import cn.com.hgh.view.MyListView;

/**
 * 选择短信模板
 *
 * @author
 * @time
 * @date
 */
public class MarketingSMSexampleActivity extends BaseActivity {


    @Bind(R.id.act_smsexample_list)
    MyListView actSmsexampleList;
    private ArrayList<ServiceMallBean> mData = new ArrayList<ServiceMallBean>();
    private ArrayList<ServiceMallBean> mDatas = new ArrayList<ServiceMallBean>();
    HttpDialog dialog;
    QuickAdapter<ServiceMallBean> mAdapter;
//    @OnClick({R.id.rlv_actmarketing})
//    public void OnClick(View v) {
//        switch (v.getId()) {
//            case R.id.rlv_actmarketing:
//
//
//                break;
//
//        }
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_marketingsmsexample, HAVETYPE);
        ButterKnife.bind(this);
        initViewAndData();
    }

    /**
     * 初始化View
     */
    private void initViewAndData() {
        setPageTitle("选择短信模板");
        dialog = new HttpDialog(this);
        listviewData();
        getCandownloadMall();
    }

    private void listviewData() {
        mAdapter = new QuickAdapter<ServiceMallBean>(MarketingSMSexampleActivity.this,
                R.layout.item_marketingsmsexample_list, mDatas) {

            @Override
            protected void convert(BaseAdapterHelper helper,
                                   final ServiceMallBean item) {
                RelativeLayout ray_choice = helper
                        .getView(R.id.ray_choice);
                CheckBox cb_smstemplate= helper
                        .getView(R.id.cb_smstemplate);
                EditText  et_smstemplate= helper
                        .getView(R.id.et_smstemplate);

                et_smstemplate.setText(item.getAppName());
            }
        };
        // 设置适配器
        actSmsexampleList.setAdapter(mAdapter);


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




