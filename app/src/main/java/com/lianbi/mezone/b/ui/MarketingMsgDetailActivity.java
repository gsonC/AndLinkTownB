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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.lhh.ptrrv.library.PullToRefreshRecyclerView;
import com.lhh.ptrrv.library.footer.loadmore.BaseLoadMoreView;
import com.lianbi.mezone.b.bean.MarketingMsgDetail;
import com.lianbi.mezone.b.bean.MarketingMsgGl;
import com.lianbi.mezone.b.httpresponse.MyResultCallback;
import com.xizhi.mezone.b.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.hgh.baseadapter.recyclerViewadapter.pullrefreshrecyclerview.DemoLoadMoreView;
import cn.com.hgh.baseadapter.recyclerViewadapter.pullrefreshrecyclerview.DividerItemDecoration;
import cn.com.hgh.baseadapter.recyclerViewadapter.pullrefreshrecyclerview.PullRefreshRecyclerAdapter;
import cn.com.hgh.baseadapter.recyclerViewadapter.pullrefreshrecyclerview.PullRefreshViewHolder;
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
    @Bind(R.id.img_ememberslist_empty)
    ImageView img_ememberslist_empty;
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
    //    @Bind(R.id.lv_actmarketdetail)
    MyListView lvActmarketdetail;
    //    @Bind(R.id.sv_marketdetail)
    ScrollView svMarketdetail;
    @Bind(R.id.mptrrv)
    PullToRefreshRecyclerView mPtrrv;
    private ArrayList<MarketingMsgDetail> mData = new ArrayList<MarketingMsgDetail>();
    private ArrayList<MarketingMsgDetail> mDatas = new ArrayList<MarketingMsgDetail>();
    HttpDialog dialog;
//    private QuickAdapter<MarketingMsgDetail> mAdapter;
    private String pageNo = "1";
    private String pageSize = "10";
    private static final int TIME = 1000;
    private  MarketingMsgGl  mMarketingMsgGl;
    private static final int MSG_CODE_REFRESH = 0;
    private static final int MSG_CODE_LOADMORE = 1;
    boolean  isLoadMore=false;
    private static final int REQUEST_CODE_UPDATA_RESULT = 1009;
    private static final int REQUEST_CODE_ADD_RESULT = 1010;
    private DataAdapter mAdapter;
    String  batchNo="";
    boolean  Nodata=false;
    boolean  isResh;
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
        setContentView(R.layout.act_marketingmsgdetail, NOTYPE);
        ButterKnife.bind(this);
        initViewAndData();
    }
    private void initAccess(){
        pageNo="1";
        Nodata=false;
        isLoadMore=false;
        mDatas.clear();
    }
    public void initRecyclerView() {
        mPtrrv.setSwipeEnable(true);//open swipe
        DemoLoadMoreView loadMoreView = new DemoLoadMoreView(this, mPtrrv.getRecyclerView());
        loadMoreView.setLoadmoreString("加载中");
        loadMoreView.setLoadMorePadding(100);
        mPtrrv.setLayoutManager(new LinearLayoutManager(this));
        mPtrrv.setPagingableListener(new PullToRefreshRecyclerView.PagingableListener() {
            @Override
            public void onLoadMoreItems() {
                if (isLoadMore == true) {

                    mPtrrv.onFinishLoading(false, false);

                    return;
                } else {
                    mHandler.sendEmptyMessageDelayed(MSG_CODE_LOADMORE, TIME);
                }
            }
        });
        mPtrrv.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mHandler.sendEmptyMessageDelayed(MSG_CODE_REFRESH, TIME);
            }
        });
        mPtrrv.getRecyclerView().addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL_LIST));
        mPtrrv.setLoadMoreFooter(loadMoreView);
        mPtrrv.getLoadMoreFooter().setOnDrawListener(new BaseLoadMoreView.OnDrawListener() {
            @Override
            public boolean onDrawLoadMore(Canvas c, RecyclerView parent) {

                return false;
            }
        });
        mAdapter = new DataAdapter(this, mDatas);
        mPtrrv.setAdapter(mAdapter);
        mPtrrv.onFinishLoading(true, false);
    }

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == MSG_CODE_REFRESH) {
                initAccess();
                querySendMsgDetail(true);
//                getMemberCategoryList(true);
//                mPtrrv.setOnRefreshComplete();
//                mPtrrv.onFinishLoading(true, false);
            } else if (msg.what == MSG_CODE_LOADMORE) {
                if (Nodata == true) {
                    MarketingMsgDetail marketingmsgdetail = new MarketingMsgDetail();
                    mDatas.add(marketingmsgdetail);
                    mAdapter.notifyDataSetChanged();
                    mPtrrv.onFinishLoading(false, false);
                } else {
                    querySendMsgDetail(false);
                }
            }
        }
    };

    /**
     * 初始化View
     */
    private void initViewAndData() {
        setPageTitle("已发送短信详情");
        dialog = new HttpDialog(this);
//        listviewData();
        mMarketingMsgGl=(MarketingMsgGl)getIntent().getSerializableExtra("info");
        String sendtime=mMarketingMsgGl.getSendDate();
        String sendnum=mMarketingMsgGl.getSendNum();
        String batchNo=mMarketingMsgGl.getBatchNo();
        if(batchNo!=null&&!batchNo.equals("")){
            this.batchNo=batchNo;
        }
        if(sendtime!=null&&!sendtime.equals("")){
            txtSendtime.setText(sendtime);
        }
        if(sendnum!=null&&!sendnum.equals("")){
            txtSendnum.setText(sendnum);
        }
        initRecyclerView();
        querySendMsgDetail(false);
    }

    private void querySendMsgDetail(final  boolean isResh) {
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
                            if(pageNo.equals("1")&&mDatas.size()==0){
                                mPtrrv.setVisibility(View.GONE);
                                img_ememberslist_empty.setVisibility(View.VISIBLE);
                            }else{
                                mPtrrv.setVisibility(View.VISIBLE);
                                img_ememberslist_empty.setVisibility(View.GONE);
                            }
                            if(isResh==true){
                                mPtrrv.setOnRefreshComplete();
                                mPtrrv.onFinishLoading(true, false);
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
                    img_ememberslist_empty.setVisibility(View.VISIBLE);
                    mPtrrv.setVisibility(View.GONE);
                    if(isResh==true){
                        mPtrrv.setOnRefreshComplete();
                    }
                    mPtrrv.onFinishLoading(true, false);
                }
            }, userShopInfoBean.getBusinessId(),batchNo, pageNo, pageSize, reqTime, uuid);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    protected void updateView(ArrayList<MarketingMsgDetail> arrayList) {
        mDatas.addAll(arrayList);
        mAdapter.notifyDataSetChanged();
    }
    class DataAdapter extends PullRefreshRecyclerAdapter<MarketingMsgDetail> {


        public DataAdapter(Context context, List<MarketingMsgDetail> list) {
            super(context, list);
        }


        @Override
        protected PullRefreshViewHolder onCreateNormalViewHolder(ViewGroup parent) {
            return new DataViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_marketingmsgdetail_list, parent, false));
        }

        class DataViewHolder extends PullRefreshViewHolder {

            private LinearLayout llt_marketingmsgl;
            private TextView tv_sendmobile;
            private TextView tv_membergrade;
            private TextView tv_memberlable;
            private TextView tv_loadedall;


            public DataViewHolder(View itemView) {
                super(itemView);
                llt_marketingmsgl = (LinearLayout) itemView.findViewById(R.id.llt_marketingmsgl);
                tv_sendmobile = (TextView) itemView.findViewById(R.id.tv_sendmobile);
                tv_membergrade = (TextView) itemView.findViewById(R.id.tv_membergrade);
                tv_memberlable = (TextView) itemView.findViewById(R.id.tv_memberlable);
                tv_loadedall = (TextView) itemView.findViewById(R.id.tv_memberlable);
                tv_loadedall = (TextView) itemView.findViewById(R.id.tv_loadedall);
            }
            @Override
            public void onBindViewHolder(int position) {
                if(Nodata==true&&position==mDatas.size()-1)
                {
                    isLoadMore=true;
                    llt_marketingmsgl.setVisibility(View.GONE);
                    tv_loadedall.setVisibility(View.VISIBLE);
                }else{
                    tv_sendmobile.setText(mDatas.get(position).getMobile());
                    tv_membergrade.setText(String.valueOf(mDatas.get(position).getCoupGrade()));
                    tv_memberlable.setText(String.valueOf(mDatas.get(position).getCoupNote()));
                    llt_marketingmsgl.setVisibility(View.VISIBLE);
                    tv_loadedall.setVisibility(View.GONE);

                }
            }

            @Override
            protected void onItemClick(View view, int adapterPosition) {
                Intent intent = new Intent();
                intent.setClass(MarketingMsgDetailActivity.this,MemberAddCategoryActivity.class);
                intent.putExtra("type", "分类详情");
                intent.putExtra("info",mDatas.get(adapterPosition));
                startActivityForResult(intent,REQUEST_CODE_UPDATA_RESULT);

            }
        }
    }
    //    private void listviewData() {
//        mAdapter = new QuickAdapter<MarketingMsgDetail>(MarketingMsgDetailActivity.this,
//                R.layout.item_marketingmsgdetail_list, mDatas) {
//
//            @Override
//            protected void convert(BaseAdapterHelper helper,
//                                   final MarketingMsgDetail item) {
//
//                LinearLayout llt_marketingmsgl = helper
//                        .getView(R.id.llt_marketingmsgl);
//                TextView tv_sendmobile = helper
//                        .getView(R.id.tv_sendmobile);
//                TextView tv_membergrade = helper
//                        .getView(R.id.tv_membergrade);
//                TextView tv_memberlable = helper
//                        .getView(R.id.tv_memberlable);
//                tv_sendmobile.setText(item.getMobile());
//                tv_membergrade.setText(item.getCoupGrade());
//                tv_memberlable.setText(item.getCoupNote());
//            }
//        };
//        // 设置适配器
//        lvActmarketdetail.setAdapter(mAdapter);
//
//
//    }
}




