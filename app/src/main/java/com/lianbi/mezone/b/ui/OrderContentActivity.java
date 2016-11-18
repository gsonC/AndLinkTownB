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
import android.util.Log;
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

import java.math.BigDecimal;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.hgh.timeselector.TimeSelectorA;
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
    private final String STARTTIME = "2012-01-01 00:00";
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
    private String txnTime="";
    private String beginTime="";
    private int pageNo=0;
    private String pageSize="15";
    private String orderNo="";
    private int listPosition=-1;
    private String orderStatus="";
    private String  endTime="";
    @OnClick({R.id.tv_all, R.id.tv_success, R.id.tv_fail})
    public void OnClick(View v) {
        switch (v.getId()) {

            case R.id.tv_all:
                setScrollToTop();
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
                if(timeNoselected()){
                    ContentUtils.showMsg(OrderContentActivity.this, "请选择查询时间");
                    clearUpdate();
                    return;
                }else{

                }
                getOrderInfo(true,false,isValid);

                break;
            case R.id.tv_success:
                setScrollToTop();
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
                if(timeNoselected()){
                    ContentUtils.showMsg(OrderContentActivity.this, "请选择查询时间");
                    clearUpdate();
                    return;
                }else{

                }
                getOrderInfo(true,false,isValid);
                break;
            case R.id.tv_fail:
                setScrollToTop();
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
                if(timeNoselected()){
                    ContentUtils.showMsg(OrderContentActivity.this, "请选择查询时间");
                    clearUpdate();
                    return;
                }else{

                }
                getOrderInfo(true,false,isValid);
                break;
        }
    }

    @OnClick({R.id.tv_today, R.id.tv_threeday, R.id.tv_onemonth})
    public void OnDate(View v) {
        switch (v.getId()) {

            case R.id.tv_today:
                tvStarttime.setText("");
                tvFinishtime.setText("");
                tvToday.setChecked(true);

                tvThreeday.setChecked(false);
                tvOnemonth.setChecked(false);
                String  txnTime=AbDateUtil.getDateYearMonthDayNowPlus("yyyyMMdd");
                initSearch(
                        "",
                        orderStatus,
                        txnTime,
                        "",
                        ""
                );
                getOrderInfo(true,false,isValid);
                break;
            case R.id.tv_threeday:
                tvStarttime.setText("");
                tvFinishtime.setText("");
                tvToday.setChecked(false);
                tvThreeday.setChecked(true);
                tvOnemonth.setChecked(false);
                beginTime=AbDateUtil.getDateG(2,"yyyyMMdd");
                endTime=AbDateUtil.getDateYearMonthDayNowPlus("yyyyMMdd");
                initSearch(
                        "",
                        orderStatus,
                        "",
                        beginTime,
                        endTime
                );
                getOrderInfo(true,false,isValid);
                break;
            case R.id.tv_onemonth:
                tvStarttime.setText("");
                tvFinishtime.setText("");
                tvToday.setChecked(false);
                tvThreeday.setChecked(false);
                tvOnemonth.setChecked(true);
                beginTime=AbDateUtil.getDateG(29,"yyyyMMdd");
                endTime=AbDateUtil.getDateYearMonthDayNowPlus("yyyyMMdd");
                initSearch(
                        "",
                        orderStatus,
                        "",
                        beginTime,
                        endTime
                );
                getOrderInfo(true,false,isValid);
                break;
        }
    }

    @OnClick({R.id.tv_starttime, R.id.tv_finishtime, R.id.iv_close})
    public void OnTime(View v) {
        switch (v.getId()) {

            case R.id.tv_starttime:
                TimeSelectorA timeSelectorFrom = new TimeSelectorA(OrderContentActivity.this,
                        new TimeSelectorA.ResultHandler() {
                            @Override
                            public void handle(String time) {
                                if (!AbStrUtil.isEmpty(tvFinishtime.getText().toString()) &&
                                        !AbDateUtil.compareTime(time,tvFinishtime.getText().toString(),"yyyyMMdd")) {
                                    ContentUtils.showMsg(OrderContentActivity.this, "开始日期须在结束日期之前！");
                                    tvStarttime.setText("");
                                    beginTime = "";
                                } else {
                                    tvStarttime.setText(time);
                                    beginTime = time;
                                    if (!TextUtils.isEmpty(tvFinishtime.getText().toString()))
                                    {
                                        initSearch(
                                                "",
                                                orderStatus,
                                                "",
                                                beginTime,
                                                endTime
                                        );
                                        someOperation();
                                        getOrderInfo(true,false,isValid);
                                    }
                                }
                            }
                        }, STARTTIME,
                        ENDTIME);
                timeSelectorFrom.setDataFormat("yyyyMMdd");
                timeSelectorFrom.setMode(TimeSelectorA.MODE.YMD);
                timeSelectorFrom.setTitle("起始时间");
                timeSelectorFrom.showCurrent();

                break;
            case R.id.tv_finishtime:
                TimeSelectorA timeSelectorTo = new TimeSelectorA(this,
                        new TimeSelectorA.ResultHandler() {
                            @Override
                            public void handle(String time) {
                                if (!AbStrUtil.isEmpty(tvStarttime.getText().toString()) &&
                                        !AbDateUtil.compareTime(tvStarttime.getText().toString(), time,"yyyyMMdd")) {
                                    ContentUtils.showMsg(OrderContentActivity.this, "结束日期须在开始日期之后！");
                                    tvFinishtime.setText("");
                                    endTime = "";
                                } else {
                                    tvFinishtime.setText(time);
                                    endTime = time;
                                    if (!TextUtils.isEmpty(tvStarttime.getText().toString()))
                                    {
                                        initSearch(
                                                "",
                                                orderStatus,
                                                "",
                                                beginTime,
                                                endTime
                                        );
                                        someOperation();
                                        getOrderInfo(true,false,isValid);
                                    }
                                }
                            }
                        },STARTTIME,
                        ENDTIME);
                timeSelectorTo.setDataFormat("yyyyMMdd");
                timeSelectorTo.setMode(TimeSelectorA.MODE.YMD);
                timeSelectorTo.setTitle("结束时间");
                timeSelectorTo.showCurrent();
                break;
            case R.id.iv_close:
                String  strStarttime=tvStarttime.getText().toString();
                String  strFinishtime=tvFinishtime.getText().toString();
                if(!TextUtils.isEmpty(strStarttime)){
                    beginTime="";
                    tvStarttime.setText("");
                }
                if(!TextUtils.isEmpty(strFinishtime)){
                    endTime="";
                    tvFinishtime.setText("");
                }
                if(timeNoselected()){
                    clearUpdate();
                    return;
                }
                break;
        }
    }
    /*RecyclerView bug，设置RecyclerView滚动到顶*/
    private void  setScrollToTop(){
        switch (intentLayout) {
            case POSITION0:
                if (mWholeFragment != null) {
                    mWholeFragment.stopScroll();
                }
                break;

            case POSITION1:
                if (mPaySuccessFragment != null) {
                    mPaySuccessFragment.stopScroll();
                }
                break;
            case POSITION2:
                if (mPayFailFragment != null) {
                    mPayFailFragment.stopScroll();
                }
                break;
        }
    }
    private   void  someOperation(){
        tvToday.setChecked(false);
        tvThreeday.setChecked(false);
        tvOnemonth.setChecked(false);
    }
    public void   clearUpdate(){
        mDatas.clear();
        clearData();
        tv_num.setText("0");
        tv_rmb.setText("¥0");
    }
    public void  clearData(){
        switch (intentLayout) {
            case POSITION0:
                if (mWholeFragment != null) {
                    swtFmDo(POSITION0,mDatas);
                    mWholeFragment.timeNoselected(true);
                }
                break;

            case POSITION1:
                if (mPaySuccessFragment != null) {
                    swtFmDo(POSITION1,mDatas);
                    mPaySuccessFragment.timeNoselected(true);

                }
                break;
            case POSITION2:
                if (mPayFailFragment != null) {
                    swtFmDo(POSITION2,mDatas);
                    mPayFailFragment.timeNoselected(true);
                }
                break;
        }

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_ordercontent, NOTYPE);
        ButterKnife.bind(this);
        initView();
        orderStatus="03,04";
//      getOrderInfo(true,false,isValid);
    }



    /**
     * 初始化View
     */
    private void initView() {
        setPageTitle(getString(R.string.activity_ordercontent_title));
        viewAdapter();
    }

    private void viewAdapter() {
        vpOrderpager.setAdapter(new MyAdapter(getSupportFragmentManager()));
        vpOrderpager.setCurrentItem(0);
        vpOrderpager.addOnPageChangeListener(this);

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
//        switch (arg0) {
//            case POSITION0:
//                if (mWholeFragment != null) {
//                    swtFmDo(arg0,mWholeData);
//                }
//                break;
//
//            case POSITION1:
//                if (mPaySuccessFragment != null) {
//                    swtFmDo(arg0,mPaySuccessDatas);
//                }
//                break;
//            case POSITION2:
//                if (mPayFailFragment != null) {
//                    swtFmDo(arg0,mPayFailDatas);
//                }
//                break;
//        }
    }
    public void getOrderInfo(final boolean  isResh, final boolean  isLoad,final String  isValid) {
        if (isResh) {
            pageNo =0;
            mDatas.clear();
        }
        Log.i("tag","当前数据数量 ---》"+mDatas.size());
        Log.i("tag","订单号---》 "+orderNo);
        Log.i("tag","订单状态查询---》 "+orderStatus);
        Log.i("tag","交易时间 ---》"+txnTime);
        Log.i("tag","开始时间 ---》"+beginTime);
        Log.i("tag","结束时间 ---》"+endTime);
        Log.i("tag","页数---》"+pageNo);
        Log.i("tag","每页个数---》"+pageSize);
//        "BD2016070614191100000123"
//        BDP20gCtJi160FN041202711""
        try{
            okHttpsImp.getqueryOrderInfo(uuid,"app",
                    reqTime,isValid,
                    "app",BusinessId,orderNo,
                    String.valueOf(pageNo),pageSize,
                    orderStatus,txnTime,beginTime,
                    endTime,new MyResultCallback<String>() {
                        @Override
                        public void onResponseResult(Result result) {
                               pageNo++;
                            String reString = result.getData();
                            if (reString != null) {
                                JSONObject jsonObject;
                                try {
                                    jsonObject = new JSONObject(reString);
                                    reString = jsonObject.getString("list");
                                    mWholeData.clear();
                                    mPaySuccessDatas.clear();
                                    mPayFailDatas.clear();

                                    if (!TextUtils.isEmpty(reString)) {
                                        ArrayList<OrderContent>  baseList = (ArrayList<OrderContent>) JSON
                                                .parseArray(reString,
                                                        OrderContent.class);
                                        int  basesize=baseList.size();
                                        Log.i("tag","查询到的条数"+basesize);
                                        switch (intentLayout) {
                                            case POSITION0:
                                                mWholeFragment.hideRefreshView(isResh,true);
                                                mWholeData.addAll(baseList);
                                                mDatas.addAll(mWholeData);
                                                tv_num.setText(String.valueOf(mDatas.size()));
                                                break;
                                            case POSITION1:
                                                mPaySuccessFragment.hideRefreshView(isResh,true);
                                                mPaySuccessDatas.addAll(baseList);
                                                mDatas.addAll(mPaySuccessDatas);
                                                tv_num.setText(String.valueOf(mDatas.size()));
                                                break;
                                            case POSITION2:
                                                mPayFailFragment.hideRefreshView(isResh,true);
                                                mPayFailDatas.addAll(baseList);
                                                mDatas.addAll(mPayFailDatas);
                                                tv_num.setText(String.valueOf(mDatas.size()));
                                                break;
                                        }
                                        int  mDatasize=mDatas.size();
                                        showData(isResh);
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                        @Override
                        public void onResponseFailed(String msg) {
                            int  mDatasize=mDatas.size();
                            switch (intentLayout) {
                                case POSITION0:
                                    if (mWholeFragment != null) {
                                        mWholeFragment.hideRefreshView(isResh,false);
                                    }
                                    break;

                                case POSITION1:
                                    if (mPaySuccessFragment != null) {
                                        mPaySuccessFragment.hideRefreshView(isResh,false);
                                    }
                                    break;
                                case POSITION2:
                                    if (mPayFailFragment != null) {
                                        mPayFailFragment.hideRefreshView(isResh,false);
                                    }
                                    break;
                            }
                            showData(isResh);
                        }
                    });
        }catch (Exception e){
            e.printStackTrace();
        }


    }
    public void   showData(boolean  isResh){
        int mDatasize=mDatas.size();
        int intTxnAmt=0;

        for (OrderContent ordercontent : mDatas) {
            intTxnAmt=intTxnAmt+ordercontent.getTxnAmt();
        }
        String amt = BigDecimal.valueOf(Long.valueOf(intTxnAmt))
                .divide(new BigDecimal(100)).toString();
        tv_rmb.setText("¥"+amt);
        switch (intentLayout) {
            case POSITION0:
                if (mWholeFragment != null) {
                    swtFmDo(POSITION0,mDatas);
                }
                break;

            case POSITION1:
                if (mPaySuccessFragment != null) {
                    swtFmDo(POSITION1,mDatas);
                }
                break;
            case POSITION2:
                if (mPayFailFragment != null) {
                    swtFmDo(POSITION2,mDatas);
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
        deleteOrder(orderNo);
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
    /**
     *
     * 删除订单
     */
    private  void   deleteOrder(final String  orderNo){
        try{
            okHttpsImp.getDeleteOrderInfo(uuid,"app",
                    reqTime,"N",
                    "app",orderNo,
                    new MyResultCallback<String>() {

                        @Override
                        public void onResponseResult(Result result) {
                            String reString = result.getData();
                            if (reString != null) {
                                JSONObject jsonObject;
                                ContentUtils.showMsg(OrderContentActivity.this, "删除成功");
                                switch (intentLayout) {
                                    case POSITION0:
                                        if (mWholeFragment != null) {
                                            mWholeFragment.upData();
                                        }
                                        break;

                                    case POSITION1:
                                        if (mPaySuccessFragment != null) {
                                            mPaySuccessFragment.upData();
                                        }
                                        break;
                                    case POSITION2:
                                        if (mPayFailFragment != null) {
                                        mPayFailFragment.upData();
                                    }
                                        break;
                                }
//                                mDatas.remove(listPosition);
                                tv_num.setText(String.valueOf(mDatas.size()));
                                int intTxnAmt=0;
                                for (OrderContent ordercontent : mDatas) {
                                    intTxnAmt=intTxnAmt+ordercontent.getTxnAmt();
                                }
                                String amt = BigDecimal.valueOf(Long.valueOf(intTxnAmt))
                                        .divide(new BigDecimal(100)).toString();
                                tv_rmb.setText("¥"+amt);
                                try {
                                    jsonObject = new JSONObject(reString);
                                    if (!TextUtils.isEmpty(reString)) {


                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                        @Override
                        public void onResponseFailed(String msg) {

                        }
                    });
        }catch (Exception e){
            e.printStackTrace();
        }


    }
    //用于判断是没有查到数据还是没有选时间
    public boolean  timeNoselected() {
        if (TextUtils.isEmpty(txnTime)) {
            if (TextUtils.isEmpty(beginTime) || TextUtils.isEmpty(endTime)) {

                return  true;
            }
        }
       return  false;
    }

}