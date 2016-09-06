package com.lianbi.mezone.b.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.lhh.ptrrv.library.PullToRefreshRecyclerView;
import com.lhh.ptrrv.library.footer.loadmore.BaseLoadMoreView;
import com.lianbi.mezone.b.bean.SmsTemplate;
import com.lianbi.mezone.b.httpresponse.MyResultCallback;
import com.xizhi.mezone.b.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.com.hgh.baseadapter.recyclerViewadapter.commonrecyclerview.RecycleViewDivider;
import cn.com.hgh.baseadapter.recyclerViewadapter.pullrefreshrecyclerview.DemoLoadMoreView;
import cn.com.hgh.baseadapter.recyclerViewadapter.pullrefreshrecyclerview.PullRefreshRecyclerAdapter;
import cn.com.hgh.baseadapter.recyclerViewadapter.pullrefreshrecyclerview.PullRefreshViewHolder;
import cn.com.hgh.utils.AbStrUtil;
import cn.com.hgh.utils.Result;
import cn.com.hgh.view.HttpDialog;

/**
 * 选择短信模板
 *
 * @author
 * @time
 * @date
 */
public class MarketingSMSexampleActivity extends BaseActivity {


    @Bind(R.id.ptrrv)
    PullToRefreshRecyclerView mptrrv;
    @Bind(R.id.img_ememberslist_empty)
    ImageView img_ememberslist_empty;
    private ArrayList<SmsTemplate> mData = new ArrayList<SmsTemplate>();
    private ArrayList<SmsTemplate> mDatas = new ArrayList<SmsTemplate>();
    HttpDialog dialog;

    private DataAdapter mAdapter;
    //    QuickAdapter<SmsTemplate> mAdapter;
    private static final int TIME = 1000;

    private static final int MSG_CODE_REFRESH = 0;
    private static final int MSG_CODE_LOADMORE = 1;
    boolean isLoadMore = false;
    boolean Nodata = false;
    boolean isResh;
    //更新列表
    private static final int REQUEST_CODE_UPDATA_RESULT = 1009;
    //    添加会员类别
    private static final int REQUEST_CODE_ADD_RESULT = 1010;
    //第几页
    private int page = 1;
    //每页个数
    private String eachpullnum = "10";
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
    private String templateType="";
    public static final String TEMPLATE_TYPE = "TEMPLATE_TYPE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_marketingsmsexample, NOTYPE);
        ButterKnife.bind(this);
        initViewAndData();
    }

    /**
     * 初始化View
     */
    private void initViewAndData() {
        templateType = getIntent().getStringExtra(TEMPLATE_TYPE);
        setPageTitle("选择短信模板");
        dialog = new HttpDialog(this);
        initRecyclerView();
        queryAllSMSTemplate(true);
    }

    public void initRecyclerView() {
        mptrrv.setSwipeEnable(true);//open swipe
        DemoLoadMoreView loadMoreView = new DemoLoadMoreView(this, mptrrv.getRecyclerView());
        loadMoreView.setLoadmoreString("加载中");
        loadMoreView.setLoadMorePadding(100);
        mptrrv.setLayoutManager(new LinearLayoutManager(this));
        mptrrv.setPagingableListener(new PullToRefreshRecyclerView.PagingableListener() {
            @Override
            public void onLoadMoreItems() {
                if (isLoadMore == true) {

                    mptrrv.onFinishLoading(false, false);

                    return;
                } else {
                    mHandler.sendEmptyMessageDelayed(MSG_CODE_LOADMORE, TIME);
                }
            }
        });
        mptrrv.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mHandler.sendEmptyMessageDelayed(MSG_CODE_REFRESH, TIME);
            }
        });
//        mptrrv.getRecyclerView().addItemDecoration(new DividerItemDecoration(this,
//                DividerItemDecoration.VERTICAL_LIST));
        mptrrv.getRecyclerView().addItemDecoration(new RecycleViewDivider(
                this, LinearLayoutManager.VERTICAL, 20, getResources().getColor(R.color.colores_news_14)));
        mptrrv.setLoadMoreFooter(loadMoreView);
        mptrrv.getLoadMoreFooter().setOnDrawListener(new BaseLoadMoreView.OnDrawListener() {
            @Override
            public boolean onDrawLoadMore(Canvas c, RecyclerView parent) {

                return false;
            }
        });
        mAdapter = new DataAdapter(this, mDatas);
        mptrrv.setAdapter(mAdapter);
        mptrrv.onFinishLoading(true, false);
    }

    private void initAccess() {
        page = 1;
        Nodata = false;
        isLoadMore = false;
        mDatas.clear();
    }

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == MSG_CODE_REFRESH) {

                queryAllSMSTemplate(true);
//              mPtrrv.setOnRefreshComplete();
//              mPtrrv.onFinishLoading(true, false);
            } else if (msg.what == MSG_CODE_LOADMORE) {
                queryAllSMSTemplate(false);
            }
        }
    };


    private void queryAllSMSTemplate(final boolean isResh) {
        try {
            okHttpsImp.queryAllSMSTemplate(new MyResultCallback<String>() {

                @Override
                public void onResponseResult(Result result) {
                    String reString = result.getData();
                    if (reString != null) {
                        JSONObject jsonObject;
                        try {
                            jsonObject = new JSONObject(reString);
                            reString = jsonObject.getString("queryList");
                            if (!TextUtils.isEmpty(reString)) {
                                mData.clear();
                                ArrayList<SmsTemplate> smstemplatelist = (ArrayList<SmsTemplate>) JSON
                                        .parseArray(reString,
                                                SmsTemplate.class);
                                int listsize=smstemplatelist.size();
//                                for (int i = 0; i < listsize; i++) {
//                                    SmsTemplate smsTemplate = smstemplatelist.get(i);
//                                    if (smsTemplate.getTemplateType()!=null&&!smsTemplate.getTemplateType().trim().equals(templateType)) {
//                                        smstemplatelist.remove(i);
//                                        i--;
//                                    }
//                                }
                                mData.addAll(smstemplatelist);
                                updateView(mData);
                            }
                            if (mDatas.size() == 0) {
                                mptrrv.setVisibility(View.GONE);
                                img_ememberslist_empty.setVisibility(View.VISIBLE);
                            } else {
                                mptrrv.setVisibility(View.VISIBLE);
                                img_ememberslist_empty.setVisibility(View.GONE);
                            }
                            if(isResh==true){
                                mptrrv.setOnRefreshComplete();
                                mptrrv.onFinishLoading(true, false);
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
                    mptrrv.setVisibility(View.GONE);
                    img_ememberslist_empty.setVisibility(View.VISIBLE);
                    if(isResh==true){
                        mptrrv.setOnRefreshComplete();
                    }
                    mptrrv.onFinishLoading(true, false);

                }
            }, "M", reqTime, uuid);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    protected void updateView(ArrayList<SmsTemplate> arrayList) {
        mDatas.clear();
        mDatas.addAll(arrayList);
        mAdapter.notifyDataSetChanged();
    }

    class DataAdapter extends PullRefreshRecyclerAdapter<SmsTemplate> {


        public DataAdapter(Context context, List<SmsTemplate> list) {
            super(context, list);
        }


        @Override
        protected PullRefreshViewHolder onCreateNormalViewHolder(ViewGroup parent) {
            return new DataViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_marketingsmsexample_list, parent, false));
        }

        class DataViewHolder extends PullRefreshViewHolder {
            private LinearLayout lay_sms;
            private RelativeLayout ray_choice;
            private CheckBox cb_smstemplate;
            private EditText et_smstemplate;
            private TextView tv_loadedall;

            public DataViewHolder(View itemView) {
                super(itemView);
                lay_sms = (LinearLayout) itemView.findViewById(R.id.lay_sms);
                ray_choice = (RelativeLayout) itemView.findViewById(R.id.ray_choice);
                cb_smstemplate = (CheckBox) itemView.findViewById(R.id.cb_smstemplate);
                et_smstemplate = (EditText) itemView.findViewById(R.id.et_smstemplate);
                tv_loadedall = (TextView) itemView.findViewById(R.id.tv_loadedall);

            }


            @Override
            public void onBindViewHolder(final int position) {
                String  smstemplate=initTemplate(mDatas.get(position).getContent());
                et_smstemplate.setText(smstemplate);
                tv_loadedall.setVisibility(View.GONE);
                lay_sms.setVisibility(View.VISIBLE);
                cb_smstemplate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (cb_smstemplate.isChecked()) {
                            mDatas.get(position).setCheck(true);
                        } else {
                            mDatas.get(position).setCheck(false);
                        }
                        if(templateType.equals("B")){
                        Intent intent = new Intent();
                        intent.setClass(MarketingSMSexampleActivity.this, MarketingMsgBulidActivity.class);
                        intent.putExtra("info",initTemplate(mDatas.get(position).getContent()));
                        intent.putExtra("templateID", mDatas.get(position).getTemplateMark());
                        setResult(RESULT_OK, intent);}
                        else
                        if(templateType.equals("P")){
                        Intent intent = new Intent();
                        intent.setClass(MarketingSMSexampleActivity.this, SendNewCouponActivity.class);
                        intent.putExtra("info",mDatas.get(position).getContent());
                        intent.putExtra("templateID", mDatas.get(position).getTemplateMark());
                        setResult(RESULT_OK, intent);}
                        finish();

                    }
                });
            }

            @Override
            protected void onItemClick(View view, final int adapterPosition) {


            }


        }
    }
    private  String  initTemplate(String template) {
        String  temp="";
        temp=AbStrUtil.stringReplace(template,"businessName",ShopName);
        return  temp.toString();
    }
}
//    private void listviewData() {
//        mAdapter = new QuickAdapter<SmsTemplate>(MarketingSMSexampleActivity.this,
//                R.layout.item_marketingsmsexample_list, mDatas) {
//            private int index = -1;
//
//            @Override
//            protected void convert(final BaseAdapterHelper helper,
//                                   final SmsTemplate item) {
//                RelativeLayout ray_choice = helper
//                        .getView(R.id.ray_choice);
//                CheckBox cb_smstemplate= helper
//                        .getView(R.id.cb_smstemplate);
//                EditText  et_smstemplate= helper
//                        .getView(R.id.et_smstemplate);
//
//                et_smstemplate.setText(item.getContent());
//                helper.getView(R.id.cb_smstemplate).setOnClickListener(
//                        new  View.OnClickListener(){
//                            @Override
//                            public void onClick(View v) {
//                                Intent intent=new Intent();
//                                intent.setClass(MarketingSMSexampleActivity.this,MarketingMsgBulidActivity.class);
//                                intent.putExtra("smscontext",item.getContent());
//                                startActivity(intent);
////                              index=helper.getPosition();
////                              notifyDataSetChanged();
//
//                            }
//                        }
//                );
//                if (index == helper.getPosition()) {// 选中的条目和当前的条目是否相等
//                    cb_smstemplate.setChecked(true);
//                } else {
//                    cb_smstemplate.setChecked(false);
//                }
//            }
//        };
//        // 设置适配器
//        actSmsexampleList.setAdapter(mAdapter);
//
//
//    }





