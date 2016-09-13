package com.lianbi.mezone.b.ui;
/*
 * @创建者     Administrator
 * @创建时间   2016/8/11 15:48
 * @描述       积分记录
 *
 * @更新者     $Author$ 
 * @更新时间   $Date$
 * @更新描述   ${TODO}
 */

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
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
import cn.com.hgh.timeselector.TimeSelectorE;
import cn.com.hgh.utils.AbDateUtil;
import cn.com.hgh.utils.AbStrUtil;
import cn.com.hgh.utils.ContentUtils;
import cn.com.hgh.utils.Result;
import cn.com.hgh.view.NoScrollViewPager;

public class OrderContentActivity extends BaseActivity implements
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
    @Bind(R.id.tv_starttime)
    TextView tvStarttime;
    @Bind(R.id.tv_finishtime)
    TextView tvFinishtime;
    @Bind(R.id.iv_close)
    ImageView ivClose;
    @Bind(R.id.tv_today)
    CheckBox tvToday;
    @Bind(R.id.tv_threeday)
    CheckBox tvThreeday;
    @Bind(R.id.tv_onemonth)
    CheckBox tvOnemonth;
    @Bind(R.id.tv_all)
    CheckBox tvAll;
    @Bind(R.id.tv_success)
    CheckBox tvSuccess;
    @Bind(R.id.tv_fail)
    CheckBox tvFail;
    @Bind(R.id.vp_orderpager)
    NoScrollViewPager vpOrderpager;
    @Bind(R.id.img_empty)
    ImageView imgEmpty;

    @Bind(R.id.tv_num)
    TextView tv_num;
    @Bind(R.id.tv_rmb)
    TextView tv_rmb;
    private String coupName;
    private String limitAmt;
    private String coupAmt;
    private String isValid="Y";
    private int  intentLayout=0;
    private int intTxnAmt=0;
    private String txnTime;
    private String beginTime;
    private int pageNo=1;
    private String pageSize="15";
    private String orderNo="";
    private int listPosition=-1;
    private String orderStatus="";
    private String  endTime="";
    @OnClick({R.id.tv_all, R.id.tv_success, R.id.tv_fail})
    public void OnClick(View v) {
        switch (v.getId()) {

            case R.id.tv_all:
                vpOrderpager.setCurrentItem(0);
                tvAll.setChecked(true);
                tvSuccess.setChecked(false);
                tvFail.setChecked(false);
                initSearch(
                    "",
                    "03,04",
                    txnTime,
                    beginTime,
                    endTime
                );
                this.intentLayout=POSITION0;
                getOrderInfo(false,false,isValid);
                break;
            case R.id.tv_success:
                vpOrderpager.setCurrentItem(1);
                tvAll.setChecked(false);
                tvSuccess.setChecked(true);
                tvFail.setChecked(false);
                initSearch(
                        "",
                        "03",
                        txnTime,
                        beginTime,
                        endTime
                );
                this.intentLayout=POSITION1;
                getOrderInfo(false,false,isValid);
                break;
            case R.id.tv_fail:
                vpOrderpager.setCurrentItem(2);
                tvAll.setChecked(false);
                tvSuccess.setChecked(false);
                tvFail.setChecked(true);
                initSearch(
                        "",
                        "04",
                        txnTime,
                        beginTime,
                        endTime
                );
                this.intentLayout=POSITION2;
                getOrderInfo(false,false,isValid);
                break;
        }
    }

    @OnClick({R.id.tv_today, R.id.tv_threeday, R.id.tv_onemonth})
    public void OnDate(View v) {
        switch (v.getId()) {

            case R.id.tv_today:
                tvToday.setChecked(true);
                tvThreeday.setChecked(false);
                tvOnemonth.setChecked(false);
                String  txnTime=AbDateUtil.getDateYearMonthDayNow();
                initSearch(
                        "",
                        orderStatus,
                        txnTime,
                        "",
                        ""
                );
                getOrderInfo(false,false,isValid);
                break;
            case R.id.tv_threeday:
                tvToday.setChecked(false);
                tvThreeday.setChecked(true);
                tvOnemonth.setChecked(false);
                beginTime=AbDateUtil.getDate(3);
                endTime=AbDateUtil.getDateYearMonthDayNow();
                initSearch(
                        "",
                        orderStatus,
                        "",
                        beginTime,
                        endTime
                );
                getOrderInfo(false,false,isValid);
                break;
            case R.id.tv_onemonth:
                tvToday.setChecked(false);
                tvThreeday.setChecked(false);
                tvOnemonth.setChecked(true);
                beginTime=AbDateUtil.getDate(30);
                endTime=AbDateUtil.getDateYearMonthDayNow();
                initSearch(
                        "",
                        orderStatus,
                        "",
                        beginTime,
                        endTime
                );
                getOrderInfo(false,false,isValid);
                break;
        }
    }

    @OnClick({R.id.tv_starttime, R.id.tv_finishtime, R.id.iv_close})
    public void OnTime(View v) {
        switch (v.getId()) {

            case R.id.tv_starttime:
                TimeSelectorE timeSelectorFrom = new TimeSelectorE(OrderContentActivity.this,
                        new TimeSelectorE.ResultHandler() {
                            @Override
                            public void handle(String time) {
                                if (!AbStrUtil.isEmpty(endTime) && !AbDateUtil.compareTime(time, endTime)) {
                                    ContentUtils.showMsg(OrderContentActivity.this, "开始日期须在结束日期之前！");
                                    tvStarttime.setText("");
                                    beginTime = "";
                                } else {
                                    tvStarttime.setText(time);
                                    beginTime = time;
                                    if (!TextUtils.isEmpty(endTime))
                                    {
                                        initSearch(
                                                "",
                                                orderStatus,
                                                "",
                                                beginTime,
                                                endTime
                                        );
                                        getOrderInfo(false,false,isValid);
                                    }
                                }
                            }
                        }, AbDateUtil.getCurrentDate(AbDateUtil.dateFormatYMDHM),
                        ENDTIME);
                timeSelectorFrom.setMode(TimeSelectorE.MODE.YMD);
                timeSelectorFrom.setTitle("起始时间");
                timeSelectorFrom.show();
                break;
            case R.id.tv_finishtime:
                TimeSelectorE timeSelectorTo = new TimeSelectorE(this,
                        new TimeSelectorE.ResultHandler() {
                            @Override
                            public void handle(String time) {
                                if (!AbStrUtil.isEmpty(beginTime) && !AbDateUtil.compareTime(beginTime, time)) {
                                    ContentUtils.showMsg(OrderContentActivity.this, "结束日期须在开始日期之后！");
                                    tvFinishtime.setText("");
                                    endTime = "";
                                } else {
                                    tvFinishtime.setText(time);
                                    endTime = time;
                                    if (!TextUtils.isEmpty(beginTime))
                                    {
                                        initSearch(
                                                "",
                                                orderStatus,
                                                "",
                                                beginTime,
                                                endTime
                                        );
                                        getOrderInfo(false,false,isValid);
                                    }
                                }
                            }
                        }, AbDateUtil.getCurrentDate(AbDateUtil.dateFormatYMDHM),
                        ENDTIME);
                timeSelectorTo.setMode(TimeSelectorE.MODE.YMD);
                timeSelectorTo.setTitle("结束时间");
                timeSelectorTo.show();
                break;
            case R.id.iv_close:

                tvStarttime.setText("");
                tvFinishtime.setText("");
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_ordercontent, NOTYPE);
        ButterKnife.bind(this);
        initView();
        setLisenter();
        initAdapter();
        getOrderInfo(false,false,isValid);
    }

    private void initAdapter() {
    }



    /**
     * 初始化View
     */
    private void initView() {
        setPageTitle("订单搜索");
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


    /**
     * 添加监听
     */
    private void setLisenter() {
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
    public void getOrderInfo(final boolean  isResh, final boolean  isLoad,String  isValid) {
        if (isResh) {
            pageNo =1;
            mDatas.clear();
        }
        try{
            okHttpsImp.getqueryOrderInfo(uuid,"app",
                    reqTime,isValid,
                    "app",BusinessId,orderNo,
                    String.valueOf(pageNo),pageSize,
                    orderStatus,txnTime,beginTime,
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
                                        ContentUtils.showMsg(OrderContentActivity.this, "删除订单成功");
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
                                                case POSITION0:
                                                    mWholeData.add(baseList.get(i));
                                                    mDatas.addAll(mWholeData);
                                                    break;
                                                case POSITION1:
                                                    mPaySuccessDatas.add(baseList.get(i));
                                                    mDatas.addAll(mPaySuccessDatas);
                                                    break;
                                                case POSITION2:
                                                    mPayFailDatas.add(baseList.get(i));
                                                    mDatas.addAll(mPayFailDatas);
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
    /**
     * 删除订单信息
     */
    public void delteOrderMsg(String  orderNo,int listPosition) {

        initDelete(orderNo,listPosition);
        getOrderInfo(false,false,isValid);
        }

    private  void  initDelete(String orderNo,int listPosition){

        this.listPosition=listPosition;
        this.orderNo=orderNo;
        this.isValid="N";
    }
    private  void  initSearch(String orderNo,
                              String orderStatus,
                              String txnTime,
                              String beginTime,
                              String endTime
                              )
    {
        this.isValid="Y";
        this.orderNo="";
        this.orderStatus=orderStatus;
        this.txnTime=txnTime;
        this.beginTime=beginTime;
        this.endTime=endTime;
    }

}