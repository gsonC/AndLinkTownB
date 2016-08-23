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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.lhh.ptrrv.library.PullToRefreshRecyclerView;
import com.lhh.ptrrv.library.footer.loadmore.BaseLoadMoreView;
import com.lianbi.mezone.b.bean.MemberClassify;
import com.lianbi.mezone.b.httpresponse.MyResultCallback;
import com.xizhi.mezone.b.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.hgh.baseadapter.recyclerViewadapter.mypullrefreshrecyclerview.DemoLoadMoreView;
import cn.com.hgh.baseadapter.recyclerViewadapter.mypullrefreshrecyclerview.DividerItemDecoration;
import cn.com.hgh.baseadapter.recyclerViewadapter.pullrefreshrecyclerview.PullRefreshRecyclerAdapter;
import cn.com.hgh.baseadapter.recyclerViewadapter.pullrefreshrecyclerview.PullRefreshViewHolder;
import cn.com.hgh.utils.Result;
import cn.com.hgh.view.HttpDialog;

/**
 * 会员分类
 *
 * @author
 * @time
 * @date
 */
public class MemberClassifyActivity extends BaseActivity {


    @Bind(R.id.tv_memberclassify)
    TextView tvMemberclassify;
    @Bind(R.id.tv_membernum)
    TextView tvMembernum;
    @Bind(R.id.tv_memberdiscount)
    TextView tvMemberdiscount;
    @Bind(R.id.tv_integralratio)
    TextView tvIntegralratio;
    @Bind(R.id.lay_top)
    LinearLayout layTop;
    @Bind(R.id.v_01)
    View v01;
    @Bind(R.id.ptrrv)
    PullToRefreshRecyclerView mPtrrv;
    @Bind(R.id.tv_nodata)
    TextView tvNodata;
    @Bind(R.id.text_newcategory)
    TextView textNewcategory;
    private ArrayList<MemberClassify> mData = new ArrayList<MemberClassify>();
    private ArrayList<MemberClassify> mDatas = new ArrayList<MemberClassify>();
    HttpDialog dialog;
    private DataAdapter mAdapter;
    private Context mContext;
    int AAA;
    int i = 0;
    private RecyclerView mRecyclerView;
    private static final int DEFAULT_ITEM_SIZE = 20;
    private static final int ITEM_SIZE_OFFSET = 20;
    private static final int TIME = 1000;

    private static final int MSG_CODE_REFRESH = 0;
    private static final int MSG_CODE_LOADMORE = 1;

    @OnClick({R.id.text_newcategory})
    public void OnClick(View v) {
        switch (v.getId()) {

            case R.id.text_newcategory:
                simpleJump(MemberAddCategoryActivity.class, "新增分类");
                break;

        }
    }

    private void simpleJump(Class activity, String type) {
        Intent intent = new Intent();
        intent.setClass(MemberClassifyActivity.this, activity);
        intent.putExtra("type", type);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(R.layout.activity_memberclassify, HAVETYPE);
        ButterKnife.bind(this);
        initViewAndData();

    }
    /**
     * 初始化View
     */
    private void initViewAndData() {
        setPageTitle("会员分类");
        dialog = new HttpDialog(this);
        initRecyclerView();
        getMemberCategoryList();
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
                if(isLoadMore==true){

                    mPtrrv.onFinishLoading(false, false);

                    return;
                }else {
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
//        mPtrrv.addHeaderView(View.inflate(this, R.layout.header, null));
//         mPtrrv.setEmptyView(View.inflate(this,R.layout.activity_memberclassify,null));
        mPtrrv.setLoadMoreFooter(loadMoreView);
        mPtrrv.getLoadMoreFooter().setOnDrawListener(new BaseLoadMoreView.OnDrawListener() {
            @Override
            public boolean onDrawLoadMore(Canvas c, RecyclerView parent) {
                Log.i("onDrawLoadMore","draw load more");


                return false;
            }
        });
        for (int i = 0; i < 50; i++) {
            MemberClassify memberclassify = new MemberClassify();
            memberclassify.setTypeName("二级会员");
            memberclassify.setDataSize("101");
            memberclassify.setTypeDiscountRatio("35");
            memberclassify.setTypeDiscountRatio("62");
            mDatas.add(memberclassify);
        }
        mAdapter = new DataAdapter(this,mDatas);
        mPtrrv.setAdapter(mAdapter);
        mPtrrv.onFinishLoading(true, false);
    }
    Handler  mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == MSG_CODE_REFRESH) {
                mAdapter.notifyDataSetChanged();
                mPtrrv.setOnRefreshComplete();
                mPtrrv.onFinishLoading(true, false);
            } else if (msg.what == MSG_CODE_LOADMORE) {
                  if(i==3){
                       Toast.makeText(MemberClassifyActivity.this, "无数据", Toast.LENGTH_SHORT).show();
                        isshow=true;
                        MemberClassify memberclassify = new MemberClassify();
                        memberclassify.setTypeName("四级会员");
                        memberclassify.setDataSize("101");
                        memberclassify.setTypeDiscountRatio("35");
                        memberclassify.setTypeDiscountRatio("62");
                        mDatas.add(memberclassify);
                        mAdapter.notifyDataSetChanged();
                        mPtrrv.onFinishLoading(false, false);

                    }else{
                        isshow=false;
                        i=i+1;
                        for (int i = 0; i < 50; i++) {
                        MemberClassify memberclassify = new MemberClassify();
                        memberclassify.setTypeName("三级会员");
                        memberclassify.setDataSize("101");
                        memberclassify.setTypeDiscountRatio("35");
                        memberclassify.setTypeDiscountRatio("62");
                        mDatas.add(memberclassify);
                            mAdapter.notifyDataSetChanged();
                            mPtrrv.onFinishLoading(true, false);
                    }}

            }
        }
    };

    private void getMemberCategoryList() {
        try {
            okHttpsImp.getMemberCategoryList(new MyResultCallback<String>() {

                @Override
                public void onResponseResult(Result result) {
                    String reString = result.getData();
                    if (reString != null) {
                        JSONObject jsonObject;
                        try {
                            jsonObject = new JSONObject(reString);
                            reString = jsonObject.getString("vipTypeList");
                            if (!TextUtils.isEmpty(reString)) {
                                mData.clear();
                                ArrayList<MemberClassify> memberclassifyList = (ArrayList<MemberClassify>) JSON
                                        .parseArray(reString,
                                                MemberClassify.class);
                                mData.addAll(memberclassifyList);
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
            }, userShopInfoBean.getBusinessId(), "", "", reqTime, uuid);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void updateView(ArrayList<MemberClassify> arrayList) {
        mDatas.clear();
        mDatas.addAll(arrayList);
        mAdapter.notifyDataSetChanged();
    }

    boolean  isshow=false;
    boolean  isLoadMore=false;

    class DataAdapter extends PullRefreshRecyclerAdapter<MemberClassify> {


        public DataAdapter(Context context, List<MemberClassify> list) {
            super(context, list);
        }


        @Override
        protected PullRefreshViewHolder onCreateNormalViewHolder(ViewGroup parent) {
            return new DataViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_memberclassify_list, parent, false));
        }

        class DataViewHolder extends PullRefreshViewHolder {

            private RelativeLayout ray_itemmemberify;
            private LinearLayout llt_memberclass;
            private TextView tv_memberclassify;
            private TextView tv_memebernum;
            private TextView tv_memberdiscount;
            private TextView tv_memberratio;
            private ImageView img_right;
            private TextView  tv_loadedall;

            public DataViewHolder(View itemView) {
                super(itemView);
                ray_itemmemberify = (RelativeLayout) itemView.findViewById(R.id.ray_itemmemberify);
                llt_memberclass = (LinearLayout) itemView.findViewById(R.id.llt_memberclass);
                tv_memberclassify = (TextView) itemView.findViewById(R.id.tv_memberclassify);
                tv_memebernum = (TextView) itemView.findViewById(R.id.tv_memebernum);
                tv_memberdiscount = (TextView) itemView.findViewById(R.id.tv_memberdiscount);
                tv_memberratio = (TextView) itemView.findViewById(R.id.tv_memberratio);
                img_right = (ImageView) itemView.findViewById(R.id.img_right);
                tv_loadedall = (TextView) itemView.findViewById(R.id.tv_loadedall);
            }

            @Override
            public void onBindViewHolder(int position) {
                if(isshow==true&&position==mDatas.size()-1)
                {
                    isLoadMore=true;
                    llt_memberclass.setVisibility(View.GONE);
                    img_right.setVisibility(View.GONE);
                    tv_loadedall.setVisibility(View.VISIBLE);
                 }else{
                    tv_memberclassify.setText(mDatas.get(position).getTypeName());
                    tv_memebernum.setText(mDatas.get(position).getDataSize());
                    tv_memberdiscount.setText(mDatas.get(position).getTypeDiscountRatio());
                    tv_memberratio.setText(mDatas.get(position).getTypeDiscountRatio());
                    tv_loadedall.setVisibility(View.GONE);
                    llt_memberclass.setVisibility(View.VISIBLE);
                    img_right.setVisibility(View.VISIBLE);

                }
            }

            @Override
            protected void onItemClick(View view, int adapterPosition) {
                simpleJump(MemberAddCategoryActivity.class, "分类详情");
                Toast.makeText(mContext, "This is item " + adapterPosition, Toast.LENGTH_SHORT).show();

            }
        }
    }
}

//    private void listviewData(ArrayList<MemberClassify> mData) {
//
//        //创建一个线性的布局管理器并设置
//        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
//        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
//        rlvActclassify.setLayoutManager(layoutManager);
//        mRecyclerViewAdapter = new CommonRecyclerViewAdapter<MemberClassify>(this, mData) {
//
//            @Override
//            public void convert(CommonRecyclerViewHolder h, MemberClassify entity, int position) {
////                h.setText(R.id.tv_servicename, entity.getAppName());
////                h.setText(R.id.img_right, entity.getAppName());
//
////                if (itemViewType == 1) {
////                } else {
////                    h.setText(R.id.tv_name, entity.getName());
////                }
//                int itemViewType = 1;
//                if (itemViewType == 1) {
//                    h.setText(R.id.tv_memberclassify, entity.getTypeName());
//                    h.setText(R.id.tv_memebernum, entity.getDataSize());
//                    h.setText(R.id.tv_memberdiscount, entity.getTypeDiscountRatio());
//                    h.setText(R.id.tv_memberratio, entity.getTypeDiscountRatio());
//                } else {
//                    h.setText(R.id.tv_servicename, entity.getTypeName());
//                }
//            }
//
//            //返回item布局的id
//            @Override
//            public int getLayoutViewId(int viewType) {
//                return R.layout.item_memberclassify_list;
//            }
//
//            //默认是返回0,所以你可以定义返回1表示使用tag,2表示使用item,
//            //这里返回的值将在getLayoutViewId方法中出现
//            @Override
//            public int getItemType(int position) {
//                return 1;
//            }
//        };
//        rlvActclassify.addItemDecoration(new RecycleViewDivider(MemberClassifyActivity.this, LinearLayoutManager.HORIZONTAL));
//        //设置适配器
//        rlvActclassify.setAdapter(mRecyclerViewAdapter);
//        //全部的item都起作用
//        mRecyclerViewAdapter.setOnRecyclerViewItemClickListener(new CommonRecyclerViewAdapter.OnRecyclerViewItemClickListener() {
//            @Override
//            public void onItemClick(View v, int position) {
////                Toast.makeText(MemberClassifyActivity.this, "你点击了第" + position + "全部的item都起作用", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        //只针对显示name的Item
//        mRecyclerViewAdapter.setOnRecyclerViewItemClickListener(new CommonRecyclerViewAdapter.OnRecyclerViewItemClickListener() {
//            @Override
//            public void onItemClick(View v, int position) {
////                Toast.makeText(MemberClassifyActivity.this, "你点击了第" + position + "只针对显示name的Item", Toast.LENGTH_SHORT).show();
//            }
//        }, 1);
//
//        //添加item中控件监听
//        mRecyclerViewAdapter.setOnViewInItemClickListener(new CommonRecyclerViewAdapter.OnViewInItemClickListener() {
//            @Override
//            public void onViewInItemClick(View v, int position) {
////                Toast.makeText(MemberClassifyActivity.this, "你点击了第添加item中控件监听", Toast.LENGTH_SHORT).show();
//                simpleJump(MemberAddCategoryActivity.class, "分类详情");
//            }
//        }, R.id.llt_memberclass, R.id.img_right);
//
//    }



