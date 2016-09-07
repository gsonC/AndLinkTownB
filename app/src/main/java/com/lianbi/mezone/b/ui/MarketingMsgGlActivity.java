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
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.lhh.ptrrv.library.PullToRefreshRecyclerView;
import com.lhh.ptrrv.library.footer.loadmore.BaseLoadMoreView;
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
import cn.com.hgh.utils.AbDateUtil;
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
    @Bind(R.id.ptrrv)
    PullToRefreshRecyclerView mPtrrview;
    @Bind(R.id.v_bottom)
    View vBottom;
    @Bind(R.id.text_newmakemsg)
    TextView textNewmakemsg;
    @Bind(R.id.img_ememberslist_empty)
    ImageView img_ememberslist_empty;
    private ArrayList<MarketingMsgGl> mData = new ArrayList<MarketingMsgGl>();
    private ArrayList<MarketingMsgGl> mDatas = new ArrayList<MarketingMsgGl>();
    HttpDialog dialog;
    private Context mContext;
    private DataAdapter mAdapter;
    private String eachgetnum = "15";
    private static final int DEFAULT_ITEM_SIZE = 20;
    private static final int ITEM_SIZE_OFFSET = 20;
    private static final int TIME = 1000;

    private static final int MSG_CODE_REFRESH = 0;
    private static final int MSG_CODE_LOADMORE = 1;
    boolean  isshow=false;
    boolean  isLoadMore=false;
    int i = 0;
    //第几页
    int  page=1;
    boolean  Nodata=false;
    private String  smstotalSendNum="";
    private String  remainSendNum="";
    @OnClick({R.id.text_newmakemsg, R.id.btn_msgpay})
    public void OnClick(View v) {
        switch (v.getId()) {
            case R.id.text_newmakemsg:
                Intent  intent=new Intent();
                intent.setClass(MarketingMsgGlActivity.this,MarketingMsgBulidActivity.class);
                intent.putExtra("smstotalSendNum",smstotalSendNum);
                intent.putExtra("remainSendNum",remainSendNum);
                startActivity(intent);
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
        mContext = this;
        setContentView(R.layout.act_marketingmsg, NOTYPE);
        ButterKnife.bind(this);
        initData();
    }

    /**
     * 初始化View
     */
    private void initData() {
        setPageTitle("营销短信管理");
        dialog = new HttpDialog(this);
        initRecyclerView();
        getMarketingMsgList(false);
    }
    private void initAccess(){
        page=1;
        isLoadMore=false;
        mDatas.clear();
    }
    public void initRecyclerView() {
        mPtrrview.setSwipeEnable(true);//open swipe
        DemoLoadMoreView loadMoreView = new DemoLoadMoreView(this, mPtrrview.getRecyclerView());
        loadMoreView.setLoadmoreString("加载中");
        loadMoreView.setLoadMorePadding(100);
        mPtrrview.setLayoutManager(new LinearLayoutManager(this));
        mPtrrview.setPagingableListener(new PullToRefreshRecyclerView.PagingableListener() {
            @Override
            public void onLoadMoreItems() {
                if (isLoadMore == true) {

                    mPtrrview.onFinishLoading(false, false);

                    return;
                } else {
                    mHandler.sendEmptyMessageDelayed(MSG_CODE_LOADMORE, TIME);
                }
            }
        });
        mPtrrview.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mHandler.sendEmptyMessageDelayed(MSG_CODE_REFRESH, TIME);
            }
        });
        mPtrrview.getRecyclerView().addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL_LIST));
//        mPtrrv.addHeaderView(View.inflate(this, R.layout.header, null));
      //  mPtrrview.setEmptyView(View.inflate(this,R.layout.act_register,null));
        mPtrrview.setLoadMoreFooter(loadMoreView);
        mPtrrview.getLoadMoreFooter().setOnDrawListener(new BaseLoadMoreView.OnDrawListener() {
            @Override
            public boolean onDrawLoadMore(Canvas c, RecyclerView parent) {


                return false;
            }
        });
        mAdapter = new DataAdapter(this, mDatas);
        mPtrrview.setAdapter(mAdapter);
        mPtrrview.onFinishLoading(true, false);
    }

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == MSG_CODE_REFRESH) {
                initAccess();
                getMarketingMsgList(true);
//                mAdapter.notifyDataSetChanged();
//                mPtrrview.setOnRefreshComplete();
//                mPtrrview.onFinishLoading(true, false);
            } else if (msg.what == MSG_CODE_LOADMORE) {
                if(Nodata==true){
                    MarketingMsgGl marketingmsggl = new MarketingMsgGl();
                    mDatas.add(marketingmsggl);
                    mAdapter.notifyDataSetChanged();
                    mPtrrview.onFinishLoading(false, false);
                }else{
                    getMarketingMsgList(false);
                }

            }
        }
    };

    private void simpleJump(Class activity, String type) {
        Intent intent = new Intent();
        intent.setClass(MarketingMsgGlActivity.this, activity);
        intent.putExtra("type", type);
        startActivity(intent);
    }

    private void getMarketingMsgList(final  boolean  isResh) {
        String nowtime = AbDateUtil.getDateYearMonthDayNow();
        try {
            okHttpsImp.querySendMsgStatistic(new MyResultCallback<String>() {

                @Override
                public void onResponseResult(Result result) {
                    String reString = result.getData();
                    if (reString != null) {
                        JSONObject jsonObject;
                        try {
                            jsonObject = new JSONObject(reString);
                            reString=jsonObject.optString("list");
                            smstotalSendNum=String.valueOf(jsonObject.getInt("totalSendNum"));
                            remainSendNum=String.valueOf(jsonObject.getInt("remainSendNum"));
                            txtAlreadysendnum.setText(smstotalSendNum);
                            txtRemainsendnum.setText(remainSendNum);
                            if (!TextUtils.isEmpty(reString)) {
                                mData.clear();
                                ArrayList<MarketingMsgGl> msgGlsList = (ArrayList<MarketingMsgGl>) JSON
                                        .parseArray(reString,
                                                MarketingMsgGl.class);
                                mData.addAll(msgGlsList);
                                updateView(mData);
                                if(msgGlsList.size()!=0){
                                    page = page + 1;
                                }
                            }
                            if(mData.size()==0&&mDatas.size()!=0){
                                Nodata=true;
                                mAdapter.notifyDataSetChanged();
                                mPtrrview.onFinishLoading(false, false);
                                return;
                            }
                            if(page==1&&mDatas.size()==0){
                                mPtrrview.setVisibility(View.GONE);
                                img_ememberslist_empty.setVisibility(View.VISIBLE);
                            }else{
                                mPtrrview.setVisibility(View.VISIBLE);
                                img_ememberslist_empty.setVisibility(View.GONE);
                            }
                            if(isResh==true){
                                mPtrrview.setOnRefreshComplete();
                                mPtrrview.onFinishLoading(true, false);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    dialog.dismiss();
                }

                @Override
                public void onResponseFailed(String msg) {
                    img_ememberslist_empty.setVisibility(View.VISIBLE);
                    mPtrrview.setVisibility(View.GONE);
                    if(isResh==true){
                        mPtrrview.setOnRefreshComplete();
                    }
                    mPtrrview.onFinishLoading(true, false);
                    dialog.dismiss();
                }
            }, BusinessId, String.valueOf(page), eachgetnum, nowtime, reqTime, uuid);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void updateView(ArrayList<MarketingMsgGl> arrayList) {
        mDatas.addAll(arrayList);
        mAdapter.notifyDataSetChanged();
    }

    class DataAdapter extends PullRefreshRecyclerAdapter<MarketingMsgGl> {


        public DataAdapter(Context context, List<MarketingMsgGl> list) {
            super(context, list);
        }


        @Override
        protected PullRefreshViewHolder onCreateNormalViewHolder(ViewGroup parent) {
            return new DataViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_marketingmsgl_list, parent, false));
        }

        class DataViewHolder extends PullRefreshViewHolder {

            private LinearLayout llt_marketingmsgl;
            private TextView tv_sendingtime;
            private TextView tv_sendingobject;
            private TextView tv_sendingnum;
            private ImageView img_right;
            private TextView  tv_loadedall;

            public DataViewHolder(View itemView) {
                super(itemView);
                llt_marketingmsgl = (LinearLayout) itemView.findViewById(R.id.llt_marketingmsgl);
                tv_sendingtime = (TextView) itemView.findViewById(R.id.tv_sendingtime);
                tv_sendingobject = (TextView) itemView.findViewById(R.id.tv_sendingobject);
                tv_sendingnum = (TextView) itemView.findViewById(R.id.tv_sendingnum);
                img_right = (ImageView) itemView.findViewById(R.id.img_right);
                tv_loadedall = (TextView) itemView.findViewById(R.id.tv_loadedall);
            }

            @Override
            public void onBindViewHolder(int position) {
                if(Nodata==true&&position==mDatas.size()-1)
                {
                    isLoadMore=true;
                    llt_marketingmsgl.setVisibility(View.GONE);
                    img_right.setVisibility(View.GONE);
                    tv_loadedall.setVisibility(View.VISIBLE);
                }else{
                    tv_sendingtime.setText(mDatas.get(position).getSendDate());
                    tv_sendingobject.setText(mDatas.get(position).getSendMobles());
                    tv_sendingnum.setText(mDatas.get(position).getSendNum());
                    tv_loadedall.setVisibility(View.GONE);
                    llt_marketingmsgl.setVisibility(View.VISIBLE);
                    img_right.setVisibility(View.VISIBLE);
                }


            }

            @Override
            protected void onItemClick(View view, int adapterPosition) {
//                Toast.makeText(mContext, "This is item " + adapterPosition, Toast.LENGTH_SHORT).show();
//                simpleJump(MarketingMsgDetailActivity.class);
                Intent intent = new Intent();
                intent.setClass(MarketingMsgGlActivity.this, MarketingMsgDetailActivity.class);
                intent.putExtra("info",mDatas.get(adapterPosition));
                startActivity(intent);
            }
        }
    }
}
//    private void listviewData(ArrayList<ServiceMallBean> mData) {
//
//        //创建一个线性的布局管理器并设置
//        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
//        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
//        rlvActmarketing.setLayoutManager(layoutManager);
//        mRecyclerViewAdapter = new CommonRecyclerViewAdapter<ServiceMallBean>(this, mData) {
//
//            @Override
//            public void convert(CommonRecyclerViewHolder h, ServiceMallBean entity, int position) {
////                h.setText(R.id.tv_servicename, entity.getAppName());
////                h.setText(R.id.img_right, entity.getAppName());
//
////                if (itemViewType == 1) {
////                } else {
////                    h.setText(R.id.tv_name, entity.getName());
////                }
//                int itemViewType = 1;
//                if (itemViewType == 1) {
//                    h.setText(R.id.tv_sendingtime, entity.getAppName());
//                    h.setText(R.id.tv_sendingobject, entity.getAppName());
//                    h.setText(R.id.tv_sendingnum, entity.getAppName());
//                } else {
//                    h.setText(R.id.tv_servicename, entity.getAppName());
//                }
//            }
//
//            //返回item布局的id
//            @Override
//            public int getLayoutViewId(int viewType) {
//                return R.layout.item_marketingmsgl_list;
//            }
//
//            //默认是返回0,所以你可以定义返回1表示使用tag,2表示使用item,
//            //这里返回的值将在getLayoutViewId方法中出现
//            @Override
//            public int getItemType(int position) {
//                return 1;
//            }
//        };
//        rlvActmarketing.addItemDecoration(new RecycleViewDivider(MarketingMsgGlActivity.this, LinearLayoutManager.HORIZONTAL));
//        //设置适配器
//        rlvActmarketing.setAdapter(mRecyclerViewAdapter);
//        //只针对显示name的Item
//        mRecyclerViewAdapter.setOnRecyclerViewItemClickListener(new CommonRecyclerViewAdapter.OnRecyclerViewItemClickListener() {
//            @Override
//            public void onItemClick(View v, int position) {
//                Toast.makeText(MarketingMsgGlActivity.this, "你点击了第" + position + "个item", Toast.LENGTH_SHORT).show();
//            }
//        }, 2);
//
//        //添加item中控件监听
//        mRecyclerViewAdapter.setOnViewInItemClickListener(new CommonRecyclerViewAdapter.OnViewInItemClickListener() {
//            @Override
//            public void onViewInItemClick(View v, int position) {
//                simpleJump(MarketingMsgDetailActivity.class, "");
//            }
//        }, R.id.llt_marketingmsgl, R.id.img_right);
//
//    }





