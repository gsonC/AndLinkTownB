package com.lianbi.mezone.b.ui;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.lianbi.mezone.b.bean.ServiceMallBean;
import com.lianbi.mezone.b.httpresponse.MyResultCallback;
import com.xizhi.mezone.b.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.hgh.baseadapter.recyclerViewadapter.pullrefreshrecyclerview.PullRefreshRecyclerAdapter;
import cn.com.hgh.baseadapter.recyclerViewadapter.pullrefreshrecyclerview.PullRefreshRecyclerView;
import cn.com.hgh.baseadapter.recyclerViewadapter.pullrefreshrecyclerview.PullRefreshViewHolder;
import cn.com.hgh.baseadapter.recyclerViewadapter.layoutmanager.CustomLinearLayoutManager;
import cn.com.hgh.baseadapter.recyclerViewadapter.layoutmanager.DividerGridItemDecoration;
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

    @Bind(R.id.tv_searchtablenum)
    TextView tvSearchtablenum;
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
    @Bind(R.id.lay_tag)
    LinearLayout layTag;
    @Bind(R.id.v_02)
    View v02;
    @Bind(R.id.rlv_actmarketingselect)
    PullRefreshRecyclerView rlvActmarketingselect;
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
    private ArrayList<ServiceMallBean> mData = new ArrayList<ServiceMallBean>();
    private ArrayList<ServiceMallBean> mDatas = new ArrayList<ServiceMallBean>();
    HttpDialog dialog;
    private DataAdapter mAdapter;
    private Context mContext;

    @OnClick({R.id.tv_sure})
    public void OnClick(View v) {
        switch (v.getId()) {
            case R.id.tv_sure:


            break;

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext=this;
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
        listviewData();
//        getCandownloadMall();
    }

    private void listviewData() {
        rlvActmarketingselect.setOnRefreshListener(new PullRefreshRecyclerView.OnRecyclerRefreshListener() {
            @Override
            public void onPullToRefresh() {
                onLoadUpMore();
            }

            @Override
            public void onLoadingMore() {
                onLoadBottomMore();
            }
        });
        rlvActmarketingselect.setLayoutManager(new CustomLinearLayoutManager(mContext));
        rlvActmarketingselect.addItemDecoration(new DividerGridItemDecoration(mContext));

       initAdapter();
    }
    private void initAdapter() {
        mDatas.clear();
        for (int i = 0; i < 50; i ++) {
            ServiceMallBean  servicemallbean=new ServiceMallBean();
            servicemallbean.setAppName("AAA");
            servicemallbean.setPresentPrice("150");
            mDatas.add(servicemallbean);
        }
        mAdapter = new DataAdapter(mContext, mDatas);
        mAdapter.enableLoadMore(true);
        rlvActmarketingselect.setAdapter(mAdapter);
    }
    private void onLoadUpMore() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                rlvActmarketingselect.onRefreshCompleted();
            }
        }, 2000);
    }
     int  AAA;
    private void onLoadBottomMore() {
//        rlvActmarketingselect.onRefreshCompleted();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                //Load data
                int index = mDatas.size();
                int end = index + 20;
                AAA=end;
                for (int i = index; i < 130; i++) {
                    ServiceMallBean  servicemallbean=new ServiceMallBean();
                    servicemallbean.setAppName("MMMM");
                    servicemallbean.setPresentPrice("160");
                    mDatas.add(servicemallbean);
                }
//                getCandownloadMall();
                mAdapter.notifyDataSetChanged();
                rlvActmarketingselect.onRefreshCompleted();

            }
        }, 2000);
    }



//    private void listviewData() {
//
//        //创建一个线性的布局管理器并设置
//        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
//        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
//        rlvActmarketingselect.setLayoutManager(layoutManager);
//        mRecyclerViewAdapter = new CommonRecyclerViewAdapter<ServiceMallBean>(this, mDatas) {
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
//                    h.setText(R.id.tv_mb_phone, entity.getAppName());
//                    h.setText(R.id.tv_mb_category, entity.getAppName());
//                    h.setText(R.id.tv_mb_source, entity.getAppName());
//                    h.setText(R.id.tv_mb_label, entity.getAppName());
//                } else {
//                    h.setText(R.id.tv_servicename, entity.getAppName());
//                }
//            }
//
//            //返回item布局的id
//            @Override
//            public int getLayoutViewId(int viewType) {
//                return R.layout.item_selectmember_list;
//            }
//
//            //默认是返回0,所以你可以定义返回1表示使用tag,2表示使用item,
//            //这里返回的值将在getLayoutViewId方法中出现
//            @Override
//            public int getItemType(int position) {
//                return 1;
//            }
//        };
//        rlvActmarketingselect.addItemDecoration(new RecycleViewDivider(MarketingSelectMemberActivity.this, LinearLayoutManager.HORIZONTAL));
//        //设置适配器
//        rlvActmarketingselect.setAdapter(mRecyclerViewAdapter);
//        //只针对显示name的Item
//        mRecyclerViewAdapter.setOnRecyclerViewItemClickListener(new CommonRecyclerViewAdapter.OnRecyclerViewItemClickListener() {
//            @Override
//            public void onItemClick(View v, int position) {
//                Toast.makeText(MarketingSelectMemberActivity.this, "你点击了第" + position + "个item", Toast.LENGTH_SHORT).show();
//            }
//        }, 2);
//
////        //添加item中控件监听
////        mRecyclerViewAdapter.setOnViewInItemClickListener(new CommonRecyclerViewAdapter.OnViewInItemClickListener() {
////            @Override
////            public void onViewInItemClick(View v, int position) {
////                DemoEntity demoEntity = data.get(position);
////                Toast.makeText(MultiLayoutActivity.this, "你点击了第" + position + "个item,name = " + demoEntity.getName(), Toast.LENGTH_SHORT).show();
////            }
////        }, R.id.bt);
//
//    }

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
//                          mData.clear();
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
        mAdapter.notifyDataSetChanged();
    }

    class DataAdapter extends PullRefreshRecyclerAdapter<ServiceMallBean> {


        public DataAdapter(Context context, List<ServiceMallBean> list) {
            super(context, list);
        }


        @Override
        protected PullRefreshViewHolder onCreateNormalViewHolder(ViewGroup parent) {
            return new DataViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_selectmember_list, parent, false));
        }

        class DataViewHolder extends PullRefreshViewHolder {

            private TextView tv_mb_phone;
            private TextView tv_mb_category;
            private TextView tv_mb_source;
            private TextView tv_mb_label;

            public DataViewHolder(View itemView) {
                super(itemView);
                tv_mb_phone = (TextView) itemView.findViewById(R.id.tv_mb_phone);
                tv_mb_category = (TextView) itemView.findViewById(R.id.tv_mb_category);
                tv_mb_source = (TextView) itemView.findViewById(R.id.tv_mb_source);
                tv_mb_label = (TextView) itemView.findViewById(R.id.tv_mb_label);

            }

            @Override
            public void onBindViewHolder(int position) {

              tv_mb_phone.setText(mDatas.get(position).getAppName());
              tv_mb_category.setText(mDatas.get(position).getPresentPrice());
              tv_mb_source.setText(mDatas.get(position).getAppName());
              tv_mb_label.setText(mDatas.get(position).getAppName());

            }

            @Override
            protected void onItemClick(View view, int adapterPosition) {
                Toast.makeText(mContext, "This is item " + adapterPosition, Toast.LENGTH_SHORT).show();
            }
        }
    }
}




