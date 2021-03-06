package com.lianbi.mezone.b.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

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
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.hgh.baseadapter.recyclerViewadapter.pullrefreshrecyclerview.DemoLoadMoreView;
import cn.com.hgh.baseadapter.recyclerViewadapter.pullrefreshrecyclerview.DividerItemDecoration;
import cn.com.hgh.baseadapter.recyclerViewadapter.pullrefreshrecyclerview.PullRefreshRecyclerAdapter;
import cn.com.hgh.baseadapter.recyclerViewadapter.pullrefreshrecyclerview.PullRefreshViewHolder;
import cn.com.hgh.indexscortlist.ClearEditText;
import cn.com.hgh.utils.AbAppUtil;
import cn.com.hgh.utils.AbStrUtil;
import cn.com.hgh.utils.Result;
import cn.com.hgh.utils.ScreenUtils;
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
    @Bind(R.id.img_ememberslist_empty)
    ImageView img_ememberslist_empty;
    ArrayList<String> phones = new ArrayList<String>();
    private ArrayList<MemberInfoSelectBean> mData = new ArrayList<MemberInfoSelectBean>();
    private ArrayList<MemberInfoSelectBean> mDatas = new ArrayList<MemberInfoSelectBean>();
    HttpDialog dialog;
    private DataAdapter mAdapter;
    private Context mContext;
    boolean isSort = false;
    private Drawable mDrawableDowm;
    private Drawable mDrawableUp;
    private Drawable mDrawableinitial;
    private String paramLike="";
    private int page = 1;
    private static final int DEFAULT_ITEM_SIZE = 20;
    private static final int ITEM_SIZE_OFFSET = 20;
    private static final int TIME = 1000;

    private static final int MSG_CODE_REFRESH = 0;
    private static final int MSG_CODE_LOADMORE = 1;
    public  String  selectsource="selectsource";
    int i = 0;
    boolean  isshow=false;
    boolean  isLoadMore=false;
    boolean  Nodata=false;
    boolean  isSeleteAll=false;
    private int  statisticspeople=0;
    List<Integer> intlist = new ArrayList<Integer>();
    String  sendPhones="";
    @OnClick({R.id.tv_sure, R.id.llt_integral,R.id.cb_selectall})
    public void OnClick(View v) {
        switch (v.getId()) {
            case R.id.tv_sure:
                if(selectsource.equals("MarketingMsgBulid")){

                    Intent intent = new Intent();
                    intent.setClass(MarketingSelectMemberActivity.this,MarketingMsgBulidActivity.class);
                    intent.putExtra("sendphones",sendPhones);
                    intent.putExtra("sendtotal",statisticspeople+"条");
                    setResult(RESULT_OK, intent);
                }else
                if (selectsource.equals("SendNewCoupon")){
                    Intent intent = new Intent();
                    intent.setClass(MarketingSelectMemberActivity.this,SendNewCouponActivity.class);
                    intent.putExtra("sendphones",sendPhones);
                    intent.putExtra("sendtotal",statisticspeople+"条");
                    setResult(RESULT_OK, intent);
                }
                finish();
                break;
            case R.id.cb_selectall:
                upDateSeleteAll(isSeleteAll);

                break;
            case R.id.llt_integral:
                if (isSort) {
                    isSort = false;
                    mDrawableDowm.setBounds(0, 0, mDrawableDowm.getMinimumWidth(), mDrawableDowm.getMinimumHeight());
                    txtMemberintegral.setCompoundDrawables(null, null, mDrawableDowm, null);
                    startSort(isSort);
                } else {
                    isSort = true;
                    mDrawableUp.setBounds(0, 0, mDrawableUp.getMinimumWidth(), mDrawableUp.getMinimumHeight());
                    txtMemberintegral.setCompoundDrawables(null, null, mDrawableUp, null);
                    startSort(isSort);
                }
                break;

        }
    }
    /**
     * 排序
     *
     * @param beginsort true 从小到大 false 从大到小
     */
    private void startSort(final boolean beginsort) {
        Collections.sort(mDatas, new Comparator<MemberInfoSelectBean>() {
            @Override
            public int compare(MemberInfoSelectBean lhs, MemberInfoSelectBean rhs) {
                Integer id1 = lhs.getVipIntegral();
                Integer id2 = rhs.getVipIntegral();
                if (beginsort) {
                    return id1.compareTo(id2);
                } else {
                    return id2.compareTo(id1);
                }
            }
        });
    mAdapter.notifyDataSetChanged();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        mContext = this;
        setContentView(R.layout.act_marketingselectmember, NOTYPE);
        ButterKnife.bind(this);
        initViewAndData();
    }
    private void initAccess(){
        page=1;
        isLoadMore=false;
        mDatas.clear();
    }
    /**
     * 初始化View
     */
    private void initViewAndData() {
        setPageTitle(getString(R.string.activity_marketingselectmember_title));
        dialog = new HttpDialog(this);
        String selectsource=getIntent().getStringExtra("selectsource");
        if(!TextUtils.isEmpty(selectsource)){
            this.selectsource=selectsource;
        }
        mDrawableDowm = ContextCompat.getDrawable(this, R.mipmap.tma_down);
        mDrawableUp = ContextCompat.getDrawable(this, R.mipmap.tma_up);
        mDrawableinitial = ContextCompat.getDrawable(this, R.mipmap.tma_initialdown);
        viewAdapter();
        listviewData();
        setLisenter();
        getMembersSelsectList(false);
    }
    private void setLisenter() {
        etSearch
                .addTextChangedListener(new TextWatcher() {

                    @Override
                    public void onTextChanged(CharSequence s, int start,
                                              int before, int count) {
                        // 当输入框里面的值为空，更新为原来的列表，否则为过滤数据列表
                        paramLike = s.toString();
                        if("".equals(paramLike)){
                            paramLike="";
                            initAccess();
                            getMembersSelsectList(false);
                        }

                    }

                    @Override
                    public void beforeTextChanged(CharSequence s, int start,
                                                  int count, int after) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                    }
                });
        etSearch
                .setOnEditorActionListener(new TextView.OnEditorActionListener() {

                    @Override
                    public boolean onEditorAction(TextView v, int actionId,
                                                  KeyEvent event) {
                        if (actionId == EditorInfo.IME_ACTION_DONE
                                || actionId == EditorInfo.IME_ACTION_UNSPECIFIED) {
                            paramLike = etSearch
                                    .getText().toString().trim();
                            initAccess();
                            getMembersSelsectList(false);                        }
                        AbAppUtil.closeSoftInput(MarketingSelectMemberActivity.this);
                        return false;
                    }
                });

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
        ptrrview.setLoadMoreFooter(loadMoreView);
        ptrrview.getLoadMoreFooter().setOnDrawListener(new BaseLoadMoreView.OnDrawListener() {
            @Override
            public boolean onDrawLoadMore(Canvas c, RecyclerView parent) {

                return false;
            }
        });
        mAdapter = new DataAdapter(this, mDatas);
        ptrrview.setAdapter(mAdapter);
        ptrrview.onFinishLoading(true, false);
    }
    Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == MSG_CODE_REFRESH) {
                initAccess();
                getMembersSelsectList(true);
            } else if (msg.what == MSG_CODE_LOADMORE) {
                if(Nodata==true){
                    MemberInfoSelectBean memberclassify = new MemberInfoSelectBean();
                    mDatas.add(memberclassify);
                    mAdapter.notifyDataSetChanged();
                    ptrrview.onFinishLoading(false, false);
                }else{
                    getMembersSelsectList(false);
                }
            }
        }
    };

    private void getMembersSelsectList(final  boolean isResh) {
        try {
            okHttpsImp.getMembersList(uuid, "app", reqTime, OkHttpsImp.md5_key,
                    userShopInfoBean.getBusinessId(), page + "", 20 + "", new MyResultCallback<String>() {

                        @Override
                        public void onResponseResult(Result result) {
                            String reString = result.getData();
                            int  dataSize;
                            if (reString != null) {
                                JSONObject jsonObject;
                                try {
                                    jsonObject = new JSONObject(reString);
                                    reString = jsonObject.getString("businessVipList");
                                    dataSize = jsonObject.getInt("dataSize");

                                    if(dataSize==0&&mDatas.size()!=0){
                                        Nodata=true;
                                        mAdapter.notifyDataSetChanged();
                                        ptrrview.onFinishLoading(false, false);
                                        return;
                                    }
                                    if (!TextUtils.isEmpty(reString)) {
                                        mData.clear();
                                        ArrayList<MemberInfoSelectBean> memberinfoselectList = (ArrayList<MemberInfoSelectBean>) JSON
                                                .parseArray(reString,
                                                        MemberInfoSelectBean.class);
                                        mData.addAll(memberinfoselectList);
                                        checkboxRefresh(isResh);
                                        updateView(mData);
                                        ptrrview.onFinishLoading(true, false);
                                        if(memberinfoselectList.size()!=0){
                                           page=page+1;
                                        }
                                    }
                                    if(page==1&&mDatas.size()==0){
                                        ptrrview.setVisibility(View.GONE);
                                        img_ememberslist_empty.setVisibility(View.VISIBLE);
                                    }else{
                                        ptrrview.setVisibility(View.VISIBLE);
                                        img_ememberslist_empty.setVisibility(View.GONE);
                                    }
                                    if(isResh==true){
                                        ptrrview.setOnRefreshComplete();
                                        ptrrview.onFinishLoading(true, false);
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
                            ptrrview.setVisibility(View.GONE);
                            if(isResh==true){
                                ptrrview.setOnRefreshComplete();
                            }
                            ptrrview.onFinishLoading(true, false);
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private   void  checkboxRefresh(boolean  isResh){
        if(isResh==true){
            int s=intlist.size();
            for(int in=0;in<s;in++){
                mData.get(intlist.get(in)).setChecked(true);
            }
        }


    }
    protected void updateView(ArrayList<MemberInfoSelectBean> arrayList) {
        mDatas.addAll(arrayList);
        mAdapter.notifyDataSetChanged();
    }

    public  void   upDateSeleteAll(boolean  isSeleteAll){
        if(isSeleteAll){
            int total=mDatas.size();
            for(int i=0;i<total;i++){
                mDatas.get(i).setChecked(false);
            }
            cbSelectall.setText("全选");
            this.isSeleteAll=false;
            mAdapter.notifyDataSetChanged();
        }else{
            int total=mDatas.size();
            for(int i=0;i<total;i++){
                mDatas.get(i).setChecked(true);
            }
            this.isSeleteAll=true;
            cbSelectall.setText("全不选");
            mAdapter.notifyDataSetChanged();
        }
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
            public void onBindViewHolder(final int position) {
                ScreenUtils.textAdaptationOn720(tv_mb_phone, MarketingSelectMemberActivity.this, 24);//本周新增会员
                ScreenUtils.textAdaptationOn720(tv_mb_category, MarketingSelectMemberActivity.this, 24);//本周新增会员
                ScreenUtils.textAdaptationOn720(tv_mb_source, MarketingSelectMemberActivity.this, 24);//本周新增会员
                ScreenUtils.textAdaptationOn720(tv_mb_label, MarketingSelectMemberActivity.this, 24);//本周新增会员
                ScreenUtils.textAdaptationOn720(tv_mb_integral, MarketingSelectMemberActivity.this, 24);//本周新增会员
                if(Nodata==true&&position==mDatas.size()-1)
                {
                    isLoadMore=true;
                    lay_item.setVisibility(View.GONE);
                    tv_loadedall.setVisibility(View.VISIBLE);
                }else{
                    cb_selectmember.setChecked(mDatas.get(position).isChecked());
                    tv_mb_phone.setText(mDatas.get(position).getVipPhone());
                    if(!AbStrUtil.isEmpty(mDatas.get(position).getVipType())){
                        tv_mb_category.setText(mDatas.get(position).getVipType());
                    }else{
                        tv_mb_category.setText("普通会员");
                    }
                    tv_mb_source.setText(mDatas.get(position).getVipSource());
                    if(!AbStrUtil.isEmpty(mDatas.get(position).getLabelName())){
                        tv_mb_label.setText(mDatas.get(position).getLabelName());
                    }else{
                        tv_mb_label.setText("无");
                    }
                    tv_mb_integral.setText(String.valueOf(mDatas.get(position).getVipIntegral()));
                    tv_loadedall.setVisibility(View.GONE);
                    lay_item.setVisibility(View.VISIBLE);
                }

                cb_selectmember.setOnClickListener(new  View.OnClickListener(){
                                                       @Override
                                                       public void onClick(View v) {
                            if(mDatas.get(position).isChecked()){
                             isSeleteAll=true;
                             cbSelectall.setText("全不选");
                            mDatas.get(position).setChecked(false);
                            mAdapter.notifyDataSetChanged();
                                       }else{
                            mDatas.get(position).setChecked(true);
                            mAdapter.notifyDataSetChanged();
                                       }
                            }
                            }
                );

                int  sum=mDatas.size();
                int  statisticspeople=0;
                intlist.clear();
                phones.clear();
                for(int toatl=0;toatl<sum;toatl++){
                    if(mDatas.get(toatl).isChecked()==true){

                        statisticspeople=statisticspeople+1;
                        intlist.add(toatl);
                        phones.add(mDatas.get(toatl).getVipPhone());

                    }
                }
                splitPhones(phones,statisticspeople);
                tvAlreadychecknum.setText(statisticspeople+"人");


            }
            @Override
            protected void onItemClick(View view, final int adapterPosition) {

            }
        }
    }
    public void  splitPhones(ArrayList<String> ids,int statisticspeople
                            ) {
        StringBuffer sb = new StringBuffer();
        int s = ids.size();

        if (s > 0) {
            for (int i = 0; i < s; i++) {
                if (i == (s - 1)) {
                    sb.append(ids.get(i));
                } else {
                    sb.append(ids.get(i) + ",");
                }
            }
        } else {
            sendPhones=sb.toString();
            this.statisticspeople=statisticspeople;
            return;
        }
        sendPhones=sb.toString();
        this.statisticspeople=statisticspeople;
    }
    /**
     * View适配
     */
    private void viewAdapter() {
        ScreenUtils.textAdaptationOn720(btnSendmsg, this, 32);//会员电话
        ScreenUtils.textAdaptationOn720(txtMemberclass, this, 32);//类别
        ScreenUtils.textAdaptationOn720(txtMembersource, this, 32);//来源
        ScreenUtils.textAdaptationOn720(txtMembertag, this, 32);//标签
        ScreenUtils.textAdaptationOn720(txtMemberintegral, this, 32);//积分
    }

}




