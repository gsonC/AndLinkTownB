package com.lianbi.mezone.b.ui;
/*
 * @创建者     Administrator
 * @创建时间   2016/8/11 15:48
 * @描述       订单明细
 *
 * @更新者     $Author$ 
 * @更新时间   $Date$
 * @更新描述   ${TODO}
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.lianbi.mezone.b.bean.OrderContent;
import com.lianbi.mezone.b.fragment.OrederFragment;
import com.lianbi.mezone.b.httpresponse.MyResultCallback;
import com.xizhi.mezone.b.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.hgh.utils.ContentUtils;
import cn.com.hgh.utils.Result;
import cn.com.hgh.view.NoScrollViewPager;

public class OrderLookUpActivity extends BaseActivity implements
        ViewPager.OnPageChangeListener {

    public static final int POSITION0 = 0;
    public static final int POSITION1 = 1;
    public static final int POSITION2 = 2;

    OrederFragment mWholeFragment;
    OrederFragment mPaySuccessFragment;
    OrederFragment mPayFailFragment;
    private ArrayList<OrderContent> mWholeData = new ArrayList<OrderContent>();
    private ArrayList<OrderContent> mPaySuccessDatas = new ArrayList<OrderContent>();
    private ArrayList<OrderContent> mPayFailDatas = new ArrayList<OrderContent>();
    private ArrayList<OrderContent> mDatas = new ArrayList<OrderContent>();
    /**
     * 当前位置
     */
    public int curPosition;
    private final String ENDTIME = "2030-01-01 00:00";
    @Bind(R.id.tv_all)
    CheckBox tvAll;
    @Bind(R.id.tv_success)
    CheckBox tvSuccess;
    @Bind(R.id.tv_fail)
    CheckBox tvFail;
    @Bind(R.id.vp_orderpager)
    NoScrollViewPager vpOrderpager;

    @Bind(R.id.tv_num)
    TextView tv_num;
    @Bind(R.id.tv_rmb)
    TextView tv_rmb;
//    @Bind(R.id.img_empty)
//    ImageView imgEmpty;
    private String coupName;
    private String limitAmt;
    private String coupAmt;
    private String beginTime;
    private int listPosition=-1;
    private int  intentLayout=0;
    private int pageNo=1;
    private int intTxnAmt=0;
    private String isValid="Y";
    private String pageSize="15";
    private String orderNo="";
    private String orderStatus="";
    private String  txnTime="";
    private String  startTime="";
    private String  endTime="";
    @OnClick({R.id.tv_all, R.id.tv_success, R.id.tv_fail})
    public void OnClick(View v) {
            switch (v.getId()) {

            case R.id.tv_all:
                tvSuccess.setChecked(false);
                tvFail.setChecked(false);
                vpOrderpager.setCurrentItem(0);
                initQuery("03,04",POSITION0);
                getOrderInfo(true,false,isValid);
                break;
            case R.id.tv_success:
                vpOrderpager.setCurrentItem(1);
                tvAll.setChecked(false);
                tvFail.setChecked(false);
                initQuery("03",POSITION1);
                getOrderInfo(true,false,isValid);
                break;
            case R.id.tv_fail:
                vpOrderpager.setCurrentItem(2);
                tvAll.setChecked(false);
                tvSuccess.setChecked(false);
                initQuery("04",POSITION2);
                getOrderInfo(true,false,isValid);
                break;
        }
    }
    private  void  initQuery(String orderStatus,int intentLayout){
        this.orderStatus=orderStatus;
        this.intentLayout=intentLayout;
        this.isValid="Y";
    }
    @Override
    protected void onTitleRightClickTv() {
        super.onTitleRightClickTv();
        Intent intent=new  Intent();
        intent.setClass(OrderLookUpActivity.this,OrderContentActivity.class);
        startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_orderlookup, NOTYPE);
        ButterKnife.bind(this);
        initView();
        getOrderInfo(true,false,isValid);
    }


    /**
     * 初始化View
     */
    private void initView() {
        setPageTitle("订单明细");
        tvAll.setChecked(true);
        setPageRightResource(R.mipmap.search_bar_icon_normal);
        viewAdapter();
    }

    private void viewAdapter() {
        vpOrderpager.setAdapter(new MyAdapter(getSupportFragmentManager()));
        vpOrderpager.setCurrentItem(0);
        vpOrderpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int arg0) {
                switch (arg0) {
                    case 0: {
                    }
                    break;
                    case 1: {
                    }
                    break;
                    case 2: {
                    }
                    break;
                }
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {

            }
        });

    }


    @Override
    protected void onTitleRightClick1() {
        super.onTitleLeftClick();
        startActivity(new Intent(OrderLookUpActivity.this, OrderContentActivity.class));
    }

    class MyAdapter extends FragmentPagerAdapter {

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case POSITION0:
                    if (mWholeFragment == null) {
                        mWholeFragment = new OrederFragment();
                    }
                    return mWholeFragment;

                case POSITION1:
                    if (mPaySuccessFragment == null) {
                        mPaySuccessFragment = new OrederFragment();
                    }
                    return mPaySuccessFragment;
                case POSITION2:
                    if (mPayFailFragment == null) {
                        mPayFailFragment = new OrederFragment();
                    }
                    return mPayFailFragment;
            }
            return null;
        }

        @Override
        public int getCount() {
            return 3;
        }

    }

    @Override
    public void onPageScrollStateChanged(int arg0) {

    }

    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {
    }

    @Override
    public void onPageSelected(int arg0) {
        curPosition = arg0;
        switch (arg0) {
            case POSITION0:
                if (mWholeFragment != null) {
                    swtFmDo(arg0,mWholeData);
                }
                break;

            case POSITION1:
                if (mPaySuccessFragment != null) {
                    swtFmDo(arg0,mPaySuccessDatas);
                }
                break;
            case POSITION2:
                if (mPayFailFragment != null) {
                    swtFmDo(arg0,mPayFailDatas);
                }
                break;
        }
    }

    private void swtFmDo(int position,
                         ArrayList<OrderContent> arrayList) {
        switch (position) {
            case POSITION0:
                if (mWholeFragment != null) {
                    mWholeFragment.doSomthing(arrayList, position);
                }
                break;

            case POSITION1:
                if (mPaySuccessFragment != null) {
                    mPaySuccessFragment.doSomthing(arrayList, position);
                }
                break;
            case POSITION2:
                if (mPayFailFragment != null) {
                    mPayFailFragment.doSomthing(arrayList, position);
                }
                break;
        }
    }

    public void getOrderInfo(final boolean  isResh,final boolean  isLoad,String  isValid) {
        if (isResh) {
            pageNo =1;
            mDatas.clear();
        }
        try{
            okHttpsImp.getqueryOrderInfo(uuid,"app",
                    reqTime,isValid,
                    "app",BusinessId,orderNo,
                    String.valueOf(pageNo),pageSize,
                    orderStatus,txnTime,startTime,
                    endTime,new MyResultCallback<String>() {

                @Override
                public void onResponseResult(Result result) {
                    if(isLoad==true){
                        pageNo++;
                    }
                    String reString = result.getData();
                    if (reString != null) {
                        JSONObject jsonObject;
                        try {
                            jsonObject = new JSONObject(reString);
                            reString = jsonObject.getString("list");
                            mWholeData.clear();
                            mPaySuccessDatas.clear();
                            mPayFailDatas.clear();
                            if(TextUtils.isEmpty(reString)){
                                ContentUtils.showMsg(OrderLookUpActivity.this, "删除订单成功");
                                mDatas.remove(listPosition);
                                tv_num.setText(String.valueOf(mDatas.size()));
                            }
                            if (!TextUtils.isEmpty(reString)) {
                                ArrayList<OrderContent>  baseList = (ArrayList<OrderContent>) JSON
                                        .parseArray(reString,
                                                OrderContent.class);
                                int  basesize=baseList.size();
                                for(int i=0;i<basesize;i++){
                                    switch (intentLayout) {
                                        case 0:
                                            mWholeData.add(baseList.get(i));
                                            mDatas.addAll(mWholeData);
                                            tv_num.setText(String.valueOf(mDatas.size()));
                                            break;
                                        case 1:
                                            mPaySuccessDatas.add(baseList.get(i));
                                            mDatas.addAll(mPaySuccessDatas);
                                            tv_num.setText(String.valueOf(mDatas.size()));
                                            break;
                                        case 2:
                                            mPayFailDatas.add(baseList.get(i));
                                            mDatas.addAll(mPayFailDatas);
                                            tv_num.setText(String.valueOf(mDatas.size()));
                                            break;
                                    }
                                }

                                showData(isResh);

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
                @Override
                public void onResponseFailed(String msg) {
                    showData(isResh);
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }

    }
    public void   showData(boolean  isResh){
        for (OrderContent ordercontent : mDatas) {
            intTxnAmt=intTxnAmt+ordercontent.getTxnAmt();
        }
        tv_rmb.setText("¥"+intTxnAmt);
        switch (intentLayout) {
            case POSITION0:
                if (mWholeFragment != null) {
                    swtFmDo(POSITION0,mDatas);
                    mWholeFragment.hideRefreshView(isResh);
                }
                break;

            case POSITION1:
                if (mPaySuccessFragment != null) {
                    swtFmDo(POSITION1,mDatas);
                    mPaySuccessFragment.hideRefreshView(isResh);
                }
                break;
            case POSITION2:
                if (mPayFailFragment != null) {
                    swtFmDo(POSITION2,mDatas);
                    mPayFailFragment.hideRefreshView(isResh);
                }
                break;
        }
    }
    /**
     * 删除订单信息
     */
    public void delteOrderMsg(String  orderNo,int listPosition) {

        initDelete(orderNo,listPosition);
        getOrderInfo(true,false,isValid);
    }

    private  void  initDelete(String orderNo,int listPosition){
        this.listPosition=listPosition;
        this.orderNo=orderNo;
        this.isValid="N";
    }

}