package com.lianbi.mezone.b.ui;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
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

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.hgh.baseadapter.recyclerViewadapter.CommonRecyclerViewAdapter;
import cn.com.hgh.baseadapter.recyclerViewadapter.CommonRecyclerViewHolder;
import cn.com.hgh.baseadapter.recyclerViewadapter.RecycleViewDivider;
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
    @Bind(R.id.btn_sendmsg)
    TextView btnSendmsg;
    @Bind(R.id.txt_memberclass)
    TextView txtMemberclass;
    @Bind(R.id.txt_membersource)
    TextView txtMembersource;
    @Bind(R.id.txt_membertag)
    TextView txtMembertag;
    @Bind(R.id.txt_memberdiscount)
    TextView txtMemberdiscount;
    @Bind(R.id.txt_memberintegral)
    TextView txtMemberintegral;
    @Bind(R.id.rlv_actmarketing)
    RecyclerView rlvActmarketing;
    @Bind(R.id.iv_selectall)
    ImageView ivSelectall;
    @Bind(R.id.tv_seleteall)
    TextView tvSeleteall;
    @Bind(R.id.ray_choice)
    RelativeLayout rayChoice;
    @Bind(R.id.tv_alreadychecknum)
    TextView tvAlreadychecknum;
    @Bind(R.id.tv_sure)
    TextView tvSure;
    @Bind(R.id.ray_sure)
    RelativeLayout raySure;
    CommonRecyclerViewAdapter mRecyclerViewAdapter;
    RecyclerView  rlv_actmarketingselect;
    private ArrayList<ServiceMallBean> mData = new ArrayList<ServiceMallBean>();
    private ArrayList<ServiceMallBean> mDatas = new ArrayList<ServiceMallBean>();
    HttpDialog dialog;

    @OnClick({R.id.rlv_actmarketing})
    public void OnClick(View v) {
        switch (v.getId()) {
            case R.id.rlv_actmarketing:


                break;

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
//        listviewData();
        getCandownloadMall();
    }

    private void listviewData(ArrayList<ServiceMallBean> mData) {

        //创建一个线性的布局管理器并设置
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rlv_actmarketingselect.setLayoutManager(layoutManager);
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
                    h.setText(R.id.tv_memberclassify, entity.getAppName());
                    h.setText(R.id.tv_memebernum, entity.getAppName());
                    h.setText(R.id.tv_memberdiscount, entity.getAppName());
                    h.setText(R.id.tv_memberratio, entity.getAppName());
                } else {
                    h.setText(R.id.tv_servicename, entity.getAppName());
                }
            }

            //返回item布局的id
            @Override
            public int getLayoutViewId(int viewType) {
                return R.layout.item_memberclassify_list;
            }

            //默认是返回0,所以你可以定义返回1表示使用tag,2表示使用item,
            //这里返回的值将在getLayoutViewId方法中出现
            @Override
            public int getItemType(int position) {
                return 1;
            }
        };
        rlv_actmarketingselect.addItemDecoration(new RecycleViewDivider(MarketingSelectMemberActivity.this, LinearLayoutManager.HORIZONTAL));
        //设置适配器
        rlv_actmarketingselect.setAdapter(mRecyclerViewAdapter);
        //只针对显示name的Item
        mRecyclerViewAdapter.setOnRecyclerViewItemClickListener(new CommonRecyclerViewAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Toast.makeText(MarketingSelectMemberActivity.this, "你点击了第" + position + "个item", Toast.LENGTH_SHORT).show();
            }
        }, 2);

//        //添加item中控件监听
//        mRecyclerViewAdapter.setOnViewInItemClickListener(new CommonRecyclerViewAdapter.OnViewInItemClickListener() {
//            @Override
//            public void onViewInItemClick(View v, int position) {
//                DemoEntity demoEntity = data.get(position);
//                Toast.makeText(MultiLayoutActivity.this, "你点击了第" + position + "个item,name = " + demoEntity.getName(), Toast.LENGTH_SHORT).show();
//            }
//        }, R.id.bt);

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



