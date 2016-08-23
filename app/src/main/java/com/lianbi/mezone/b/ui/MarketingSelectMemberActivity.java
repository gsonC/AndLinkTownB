package com.lianbi.mezone.b.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.lhh.ptrrv.library.PullToRefreshRecyclerView;
import com.lhh.ptrrv.library.footer.loadmore.BaseLoadMoreView;
import com.lianbi.mezone.b.bean.MemberInfoSelectBean;
import com.lianbi.mezone.b.httpresponse.MyResultCallback;
import com.lianbi.mezone.b.httpresponse.OkHttpsImp;
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
import cn.com.hgh.indexscortlist.ClearEditText;
import cn.com.hgh.utils.Result;
import cn.com.hgh.view.HttpDialog;

/**
 * 选择要发送的会员
 *
 * @author
 * @time
 * @date
 */
public class MarketingSelectMemberActivity extends BaseActivity {


    @Bind(R.id.et_search)
    ClearEditText etSearch;
    @Bind(R.id.lay_top)
    LinearLayout layTop;
    @Bind(R.id.v_top)
    View vTop;
    @Bind(R.id.btn_sendmsg)
    TextView btnSendmsg;
    @Bind(R.id.txt_memberclass)
    TextView txtMemberclass;
    @Bind(R.id.txt_membersource)
    TextView txtMembersource;
    @Bind(R.id.txt_membertag)
    TextView txtMembertag;
    @Bind(R.id.txt_memberintegral)
    TextView txtMemberintegral;
    @Bind(R.id.llt_integral)
    LinearLayout lltIntegral;
    @Bind(R.id.lay_tag)
    LinearLayout layTag;
    @Bind(R.id.v_02)
    View v02;
    @Bind(R.id.ptrrview)
    PullToRefreshRecyclerView ptrrview;
    @Bind(R.id.cb_selectall)
    CheckBox cbSelectall;
    @Bind(R.id.ray_choice)
    RelativeLayout rayChoice;
    @Bind(R.id.tv_alreadycheck)
    TextView tvAlreadycheck;
    @Bind(R.id.tv_alreadychecknum)
    TextView tvAlreadychecknum;
    @Bind(R.id.ray_people)
    RelativeLayout rayPeople;
    @Bind(R.id.tv_sure)
    TextView tvSure;
    @Bind(R.id.ray_sure)
    RelativeLayout raySure;
    @Bind(R.id.ray_bottom)
    RelativeLayout rayBottom;
    private ArrayList<MemberInfoSelectBean> mData = new ArrayList<MemberInfoSelectBean>();
    private ArrayList<MemberInfoSelectBean> mDatas = new ArrayList<MemberInfoSelectBean>();
    HttpDialog dialog;
    private DataAdapter mAdapter;
    private Context mContext;
    boolean isSort = false;
    private Drawable mDrawableDowm;
    private Drawable mDrawableUp;
    private Drawable mDrawableinitial;
    private String paramLike;
    private int page = 0;
    private static final int DEFAULT_ITEM_SIZE = 20;
    private static final int ITEM_SIZE_OFFSET = 20;
    private static final int TIME = 1000;

    private static final int MSG_CODE_REFRESH = 0;
    private static final int MSG_CODE_LOADMORE = 1;
    int i = 0;
    boolean  isshow=false;
    boolean  isLoadMore=false;

    @OnClick({R.id.tv_sure, R.id.llt_integral})
    public void OnClick(View v) {
        switch (v.getId()) {
            case R.id.tv_sure:


                break;
            case R.id.llt_integral:
                if (isSort) {
                    isSort = false;
                    mDrawableDowm.setBounds(0, 0, mDrawableDowm.getMinimumWidth(), mDrawableDowm.getMinimumHeight());
                    txtMemberintegral.setCompoundDrawables(null, null, mDrawableDowm, null);
//                    startSort(isSort);
                } else {
                    isSort = true;
                    mDrawableUp.setBounds(0, 0, mDrawableUp.getMinimumWidth(), mDrawableUp.getMinimumHeight());
                    txtMemberintegral.setCompoundDrawables(null, null, mDrawableUp, null);
//                    startSort(isSort);
                }
                break;

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(R.layout.act_marketingselectmember, HAVETYPE);
        ButterKnife.bind(this);
        initViewAndData();
    }

    /**
     * 初始化View
     */
    private void initViewAndData() {
        setPageTitle("请选择要发送的会员");
        dialog = new HttpDialog(this);
        mDrawableDowm = ContextCompat.getDrawable(this, R.mipmap.tma_down);
        mDrawableUp = ContextCompat.getDrawable(this, R.mipmap.tma_up);
        mDrawableinitial = ContextCompat.getDrawable(this, R.mipmap.tma_initialdown);
        listviewData();
        getMembersSelsectList();
    }

    private void listviewData() {
        ptrrview.setSwipeEnable(true);//open swipe
        DemoLoadMoreView loadMoreView = new DemoLoadMoreView(this, ptrrview.getRecyclerView());
        loadMoreView.setLoadmoreString("加载中");
        loadMoreView.setLoadMorePadding(100);
        ptrrview.setLayoutManager(new LinearLayoutManager(this));
        ptrrview.setPagingableListener(new PullToRefreshRecyclerView.PagingableListener() {
            @Override
            public void onLoadMoreItems() {
                if (isLoadMore == true) {

                    ptrrview.onFinishLoading(false, false);

                    return;
                } else {
                    mHandler.sendEmptyMessageDelayed(MSG_CODE_LOADMORE, TIME);
                }
            }
        });
        ptrrview.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mHandler.sendEmptyMessageDelayed(MSG_CODE_REFRESH, TIME);
            }
        });
        ptrrview.getRecyclerView().addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL_LIST));
//        mPtrrv.addHeaderView(View.inflate(this, R.layout.header, null));
//         mPtrrv.setEmptyView(View.inflate(this,R.layout.activity_memberclassify,null));
        ptrrview.setLoadMoreFooter(loadMoreView);
        ptrrview.getLoadMoreFooter().setOnDrawListener(new BaseLoadMoreView.OnDrawListener() {
            @Override
            public boolean onDrawLoadMore(Canvas c, RecyclerView parent) {
                Log.i("onDrawLoadMore", "draw load more");


                return false;
            }
        });
        for (int i = 0; i < 50; i++) {
//            MemberClassify memberclassify = new MemberClassify();
//            memberclassify.setTypeName("二级会员");
//            memberclassify.setDataSize("101");
//            memberclassify.setTypeDiscountRatio("35");
//            memberclassify.setTypeDiscountRatio("62");
//            mDatas.add(memberclassify);
        }
        mAdapter = new DataAdapter(this, mDatas);
        ptrrview.setAdapter(mAdapter);
        ptrrview.onFinishLoading(true, false);
    }
    Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == MSG_CODE_REFRESH) {
                mAdapter.notifyDataSetChanged();
                ptrrview.setOnRefreshComplete();
                ptrrview.onFinishLoading(true, false);
            } else if (msg.what == MSG_CODE_LOADMORE) {
                if(i==3){
                    Toast.makeText(MarketingSelectMemberActivity.this, "无数据", Toast.LENGTH_SHORT).show();
                    isshow=true;
//                    MemberClassify memberclassify = new MemberClassify();
//                    memberclassify.setTypeName("四级会员");
//                    memberclassify.setDataSize("101");
//                    memberclassify.setTypeDiscountRatio("35");
//                    memberclassify.setTypeDiscountRatio("62");
//                    mDatas.add(memberclassify);
                    mAdapter.notifyDataSetChanged();
                    ptrrview.onFinishLoading(false, false);

                }else{
                    isshow=false;
                    i=i+1;
                    for (int i = 0; i < 50; i++) {
//                        MemberClassify memberclassify = new MemberClassify();
//                        memberclassify.setTypeName("三级会员");
//                        memberclassify.setDataSize("101");
//                        memberclassify.setTypeDiscountRatio("35");
//                        memberclassify.setTypeDiscountRatio("62");
//                        mDatas.add(memberclassify);
                        mAdapter.notifyDataSetChanged();
                        ptrrview.onFinishLoading(true, false);
                    }}

            }
        }
    };

    private void getMembersSelsectList() {
        try {
            okHttpsImp.getMembersList(uuid, "app", reqTime, OkHttpsImp.md5_key,
                    userShopInfoBean.getBusinessId(), paramLike, page + "", 20 + "", new MyResultCallback<String>() {

                        @Override
                        public void onResponseResult(Result result) {
                            String reString = result.getData();
                            if (reString != null) {
                                JSONObject jsonObject;
                                try {
                                    jsonObject = new JSONObject(reString);
                                    reString = jsonObject.getString("businessVipList");
                                    if (!TextUtils.isEmpty(reString)) {
                                        //                          mData.clear();
                                        ArrayList<MemberInfoSelectBean> memberinfoselectList = (ArrayList<MemberInfoSelectBean>) JSON
                                                .parseArray(reString,
                                                        MemberInfoSelectBean.class);
                                        mData.addAll(memberinfoselectList);
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
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void updateView(ArrayList<MemberInfoSelectBean> arrayList) {
        mDatas.clear();
        mDatas.addAll(arrayList);
        mAdapter.notifyDataSetChanged();
    }

    class DataAdapter extends PullRefreshRecyclerAdapter<MemberInfoSelectBean> {


        public DataAdapter(Context context, List<MemberInfoSelectBean> list) {
            super(context, list);
        }


        @Override
        protected PullRefreshViewHolder onCreateNormalViewHolder(ViewGroup parent) {
            return new DataViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_selectmember_list, parent, false));
        }

        class DataViewHolder extends PullRefreshViewHolder {

            private CheckBox cb_selectmember;
            private TextView tv_mb_phone;
            private TextView tv_mb_category;
            private TextView tv_mb_source;
            private TextView tv_mb_label;
            private TextView tv_mb_integral;
            private TextView  tv_loadedall;
            private LinearLayout   lay_item;
            public DataViewHolder(View itemView) {
                super(itemView);
                lay_item= (LinearLayout) itemView.findViewById(R.id.lay_item);
                cb_selectmember = (CheckBox) itemView.findViewById(R.id.cb_selectmember);
                tv_mb_phone = (TextView) itemView.findViewById(R.id.tv_mb_phone);
                tv_mb_category = (TextView) itemView.findViewById(R.id.tv_mb_category);
                tv_mb_source = (TextView) itemView.findViewById(R.id.tv_mb_source);
                tv_mb_label = (TextView) itemView.findViewById(R.id.tv_mb_label);
                tv_mb_integral = (TextView) itemView.findViewById(R.id.tv_mb_integral);
                tv_loadedall = (TextView) itemView.findViewById(R.id.tv_loadedall);
            }

            @Override
            public void onBindViewHolder(int position) {
                if(isshow==true&&position==mDatas.size()-1)
                {
                    isLoadMore=true;
                    lay_item.setVisibility(View.GONE);
                    tv_loadedall.setVisibility(View.VISIBLE);
                }else{
                    tv_mb_phone.setText(mDatas.get(position).getVipPhone());
                    tv_mb_category.setText(mDatas.get(position).getVipType());
                    tv_mb_source.setText(mDatas.get(position).getVipSource());
                    tv_mb_label.setText(mDatas.get(position).getVipRemarks());
                    tv_mb_integral.setText(mDatas.get(position).getVipIntegral());
                    tv_loadedall.setVisibility(View.GONE);
                    lay_item.setVisibility(View.VISIBLE);

                }

            }

            @Override
            protected void onItemClick(View view, int adapterPosition) {
                Toast.makeText(mContext, "This is item " + adapterPosition, Toast.LENGTH_SHORT).show();
            }
        }
    }
}




