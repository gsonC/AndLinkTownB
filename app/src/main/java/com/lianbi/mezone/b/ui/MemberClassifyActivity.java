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
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.lhh.ptrrv.library.PullToRefreshRecyclerView;
import com.lhh.ptrrv.library.footer.loadmore.BaseLoadMoreView;
import com.lianbi.mezone.b.bean.MemberClassify;
import com.lianbi.mezone.b.httpresponse.MyResultCallback;
import com.xizhi.mezone.b.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.com.hgh.baseadapter.recyclerViewadapter.pullrefreshrecyclerview.DemoLoadMoreView;
import cn.com.hgh.baseadapter.recyclerViewadapter.pullrefreshrecyclerview.DividerItemDecoration;
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
    @Bind(R.id.img_ememberslist_empty)
    ImageView img_ememberslist_empty;
    @Bind(R.id.tv_nodata)
    TextView tvNodata;
  
   /* @Bind(R.id.text_newcategory)
    TextView textNewcategory;*/
    private ArrayList<MemberClassify> mData = new ArrayList<MemberClassify>();
    private ArrayList<MemberClassify> mDatas = new ArrayList<MemberClassify>();
    HttpDialog dialog;
    private DataAdapter mAdapter;
    private Context mContext;
    int i = 0;
    private RecyclerView mRecyclerView;
    private static final int TIME = 1000;
    private static final int MSG_CODE_REFRESH = 0;
    private static final int MSG_CODE_LOADMORE = 1;
    boolean  isLoadMore=false;
    //更新列表
    private static final int REQUEST_CODE_UPDATA_RESULT = 1009;
//    添加会员类别
    private static final int REQUEST_CODE_ADD_RESULT = 1010;

    boolean  Nodata=false;
    boolean  isResh;


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
        setContentView(R.layout.activity_memberclassify, NOTYPE);
        ButterKnife.bind(this);
        initViewAndData();

    }
    private void initAccess(){
        Nodata=false;
        isLoadMore=false;
        mDatas.clear();
    }
    private void initViewAndData() {
        setPageTitle(getString(R.string.activity_memberclassify_title));
        setPageRightText("折扣设置");
        dialog = new HttpDialog(this);
        initRecyclerView();
        getMemberCategoryList(false);

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
        mPtrrv.setLoadMoreFooter(loadMoreView);
        mPtrrv.getLoadMoreFooter().setOnDrawListener(new BaseLoadMoreView.OnDrawListener() {
            @Override
            public boolean onDrawLoadMore(Canvas c, RecyclerView parent) {

                return false;
            }
        });
        mAdapter = new DataAdapter(this,mDatas);
        mPtrrv.setAdapter(mAdapter);
        mPtrrv.onFinishLoading(true, false);
    }
    Handler  mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == MSG_CODE_REFRESH) {
                initAccess();
                getMemberCategoryList(true);
//                mPtrrv.setOnRefreshComplete();
//                mPtrrv.onFinishLoading(true, false);
            } else if (msg.what == MSG_CODE_LOADMORE) {
                if(Nodata==true){
                        MemberClassify memberclassify = new MemberClassify();
                        mDatas.add(memberclassify);
                        mAdapter.notifyDataSetChanged();
                        mPtrrv.onFinishLoading(false, false);
                }else{
                       getMemberCategoryList(false);
                }
            }
        }
    };

    private void getMemberCategoryList(final  boolean isResh) {
        try {
            okHttpsImp.getMemberCategoryList(new MyResultCallback<String>() {

                @Override
                public void onResponseResult(Result result) {
                    String reString = result.getData();
                    int  dataSize;
                    if (reString != null) {
                        JSONObject jsonObject;
                        try {
                            jsonObject = new JSONObject(reString);
                            reString = jsonObject.getString("list");
                            dataSize = jsonObject.getInt("count");

                            if(dataSize==0&&mDatas.size()!=0){
                                Nodata=true;
                                mAdapter.notifyDataSetChanged();
                                mPtrrv.onFinishLoading(false, false);
                                return;
                            }
                            if (!TextUtils.isEmpty(reString)) {
                                Nodata=false;
                                mData.clear();
                                ArrayList<MemberClassify> memberclassifyList = (ArrayList<MemberClassify>) JSON
                                        .parseArray(reString,
                                                MemberClassify.class);
                                mData.addAll(memberclassifyList);
                                updateView(mData);
                                mPtrrv.onFinishLoading(true, false);
                                /*if(memberclassifyList.size()!=0) {
                                    page = page + 1;
                                }*/
                            }
                           /* if(page==1&&mDatas.size()==0){
                                mPtrrv.setVisibility(View.GONE);
                                img_ememberslist_empty.setVisibility(View.VISIBLE);
                            }else{
                                mPtrrv.setVisibility(View.VISIBLE);
                                img_ememberslist_empty.setVisibility(View.GONE);
                            }*/
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
            } ,uuid, "app", reqTime,BusinessId,"");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    protected void updateView(ArrayList<MemberClassify> arrayList) {
        mDatas.addAll(arrayList);
        mAdapter.notifyDataSetChanged();
    }
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
                if(Nodata==true&&position==mDatas.size()-1)
                {
                    isLoadMore=true;
                    llt_memberclass.setVisibility(View.GONE);
                    img_right.setVisibility(View.GONE);
                    tv_loadedall.setVisibility(View.VISIBLE);
                 }else{
                    tv_memberclassify.setText(mDatas.get(position).getVipGrade());
                    tv_memebernum.setText(String.valueOf(mDatas.get(position).getVipCount()));



                   String ddd= new BigDecimal(mDatas.get(position).getDiscountRate()+"").setScale(1 ,BigDecimal.ROUND_HALF_UP).toString();
                    tv_memberdiscount.setText(ddd);
                    if(mDatas.get(position).getVipGradeName().equals("普通会员")){
                        tv_memberratio.setText("0≤VIP1<3000");
                    }else if(mDatas.get(position).getVipGradeName().equals("VIP1")) {
                        tv_memberratio.setText("300≤VIP2<1000");
                    }else if(mDatas.get(position).getVipGradeName().equals("VIP2")){
                        tv_memberratio.setText("1000≤VIP3<3000");
                    }else if(mDatas.get(position).getVipGradeName().equals("VIP3")){
                        tv_memberratio.setText("3000≤");
                    }else{
                        tv_memberratio.setText("");
                    }

                    tv_loadedall.setVisibility(View.GONE);
                    llt_memberclass.setVisibility(View.VISIBLE);
                   // img_right.setVisibility(View.VISIBLE);

                }
            }

            @Override
            protected void onItemClick(View view, int adapterPosition) {
                /*Intent intent = new Intent();
                intent.setClass(MemberClassifyActivity.this,MemberAddCategoryActivity.class);
                intent.putExtra("type", "分类详情");
                intent.putExtra("info",mDatas.get(adapterPosition));
                startActivityForResult(intent,REQUEST_CODE_UPDATA_RESULT);*/

            }
        }
    }
    public  double  division(double a,double b){
        double  result=0;
        if(b!=0){
            result=a/b;
        }else{
            result=0;
        }
        return    result;
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CODE_UPDATA_RESULT:
                    initAccess();
                    getMemberCategoryList(false);

                break;
                case REQUEST_CODE_ADD_RESULT:
                    initAccess();
                    getMemberCategoryList(false);
                break;
            }
        }
    }

    @Override
    protected void onTitleRightClickTv() {
        super.onTitleRightClickTv();
        Intent intent=new Intent(MemberClassifyActivity.this,CountActivity.class);
       startActivity(intent);
    }
}

