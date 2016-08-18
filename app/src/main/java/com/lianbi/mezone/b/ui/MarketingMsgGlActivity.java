package com.lianbi.mezone.b.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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
import cn.com.hgh.baseadapter.recyclerViewadapter.CommonRecyclerViewAdapter;
import cn.com.hgh.baseadapter.recyclerViewadapter.CommonRecyclerViewHolder;
import cn.com.hgh.baseadapter.recyclerViewadapter.RecycleViewDivider;
import cn.com.hgh.utils.Result;
import cn.com.hgh.view.HttpDialog;

/**
 * 营销短信管理
 *
 * @author
 * @time
 * @date
 */
public class MarketingMsgGlActivity extends BaseActivity {


    CommonRecyclerViewAdapter mRecyclerViewAdapter;
    @Bind(R.id.txt_msg)
    TextView txtMsg;
    @Bind(R.id.txt_alreadysendnum)
    TextView txtAlreadysendnum;
    @Bind(R.id.txt_remainsendmsg)
    TextView txtRemainsendmsg;
    @Bind(R.id.txt_remainsendnum)
    TextView txtRemainsendnum;
    @Bind(R.id.btn_msgpay)
    TextView btnMsgpay;
    @Bind(R.id.ray_top)
    LinearLayout rayTop;
    @Bind(R.id.v_01)
    View v01;
    @Bind(R.id.btn_sendmsg)
    TextView btnSendmsg;
    @Bind(R.id.btn_sendobject)
    TextView btnSendobject;
    @Bind(R.id.btn_sendnum)
    TextView btnSendnum;
    @Bind(R.id.lay_tag)
    LinearLayout layTag;
    @Bind(R.id.v_02)
    View v02;
    @Bind(R.id.rlv_actmarketing)
    RecyclerView rlvActmarketing;
    @Bind(R.id.text_newmakemsg)
    TextView textNewmakemsg;
    private ArrayList<ServiceMallBean> mData = new ArrayList<ServiceMallBean>();
    private ArrayList<ServiceMallBean> mDatas = new ArrayList<ServiceMallBean>();
    HttpDialog dialog;

    @OnClick({R.id.text_newmakemsg, R.id.btn_msgpay})
    public void OnClick(View v) {
        switch (v.getId()) {
            case R.id.text_newmakemsg:
                simpleJump(MarketingMsgBulidActivity.class);

                break;
            case R.id.btn_msgpay:

                simpleJump(MarketingBuySMSActivity.class);
                break;

        }
    }

    private void simpleJump(Class activity) {
        Intent intent = new Intent();
        intent.setClass(MarketingMsgGlActivity.this, activity);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_marketingmsg, HAVETYPE);
        ButterKnife.bind(this);
        initViewAndData();
    }

    /**
     * 初始化View
     */
    private void initViewAndData() {
        setPageTitle("营销短信管理");
        dialog = new HttpDialog(this);
//        listviewData();
        getCandownloadMall();
    }

    private void listviewData(ArrayList<ServiceMallBean> mData) {

        //创建一个线性的布局管理器并设置
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rlvActmarketing.setLayoutManager(layoutManager);
        mRecyclerViewAdapter = new CommonRecyclerViewAdapter<ServiceMallBean>(this, mData) {

            @Override
            public void convert(CommonRecyclerViewHolder h, ServiceMallBean entity, int position) {
//                h.setText(R.id.tv_servicename, entity.getAppName());
//                h.setText(R.id.img_right, entity.getAppName());

//                if (itemViewType == 1) {
//                } else {
//                    h.setText(R.id.tv_name, entity.getName());
//                }
                int itemViewType = 1;
                if (itemViewType == 1) {
                    h.setText(R.id.tv_sendingtime, entity.getAppName());
                    h.setText(R.id.tv_sendingobject, entity.getAppName());
                    h.setText(R.id.tv_sendingnum, entity.getAppName());
                } else {
                    h.setText(R.id.tv_servicename, entity.getAppName());
                }
            }

            //返回item布局的id
            @Override
            public int getLayoutViewId(int viewType) {
                return R.layout.item_marketingmsgl_list;
            }

            //默认是返回0,所以你可以定义返回1表示使用tag,2表示使用item,
            //这里返回的值将在getLayoutViewId方法中出现
            @Override
            public int getItemType(int position) {
                return 1;
            }
        };
        rlvActmarketing.addItemDecoration(new RecycleViewDivider(MarketingMsgGlActivity.this, LinearLayoutManager.HORIZONTAL));
        //设置适配器
        rlvActmarketing.setAdapter(mRecyclerViewAdapter);
        //只针对显示name的Item
        mRecyclerViewAdapter.setOnRecyclerViewItemClickListener(new CommonRecyclerViewAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Toast.makeText(MarketingMsgGlActivity.this, "你点击了第" + position + "个item", Toast.LENGTH_SHORT).show();
            }
        }, 2);

        //添加item中控件监听
        mRecyclerViewAdapter.setOnViewInItemClickListener(new CommonRecyclerViewAdapter.OnViewInItemClickListener() {
            @Override
            public void onViewInItemClick(View v, int position) {
                simpleJump(MarketingMsgDetailActivity.class, "");
            }
        }, R.id.llt_marketingmsgl, R.id.img_right);

    }

    private void simpleJump(Class activity, String type) {
        Intent intent = new Intent();
        intent.setClass(MarketingMsgGlActivity.this, activity);
        intent.putExtra("type", type);
        startActivity(intent);
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
                            listviewData(mData);
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


}




