package com.lianbi.mezone.b.fragment;

/*
 * @创建者     master
 * @创建时间   2016/10/19 18:18
 * @描述       首页-商圈联盟
 *
 * @更新者     $Author$ 
 * @更新时间   $Date$
 * @更新描述
 */

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.lianbi.mezone.b.bean.LeaguesAllCountList;
import com.lianbi.mezone.b.bean.LeaguesYellBean;
import com.lianbi.mezone.b.bean.ShouYeBannerBean;
import com.lianbi.mezone.b.httpresponse.API;
import com.lianbi.mezone.b.httpresponse.MyResultCallback;
import com.lianbi.mezone.b.httpresponse.OkHttpsImp;
import com.lianbi.mezone.b.ui.LeaguesDynamicListActivity;
import com.lianbi.mezone.b.ui.LeaguesStorelistActivity;
import com.lianbi.mezone.b.ui.LeaguesYellListActivity;
import com.lianbi.mezone.b.ui.MainActivity;
import com.xizhi.mezone.b.R;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.hgh.playview.BaseSliderView;
import cn.com.hgh.playview.BaseSliderView.OnSliderClickListener;
import cn.com.hgh.playview.SliderLayout;
import cn.com.hgh.playview.imp.TextSliderView;
import cn.com.hgh.utils.ContentUtils;
import cn.com.hgh.utils.Result;
import cn.com.hgh.utils.ScreenUtils;
import cn.com.hgh.view.ListenedScrollView;

public class ShouyeLeaguesFragment extends Fragment implements OnChartValueSelectedListener, OnSliderClickListener{

    @Nullable
    @Bind(R.id.vf_shouyeleagues_dyn)
    ViewFlipper vfShouyeleaguesDyn;
    @Bind(R.id.tv_include_title)
    TextView tvIncludeTitle;
    @Bind(R.id.tv_include_more)
    TextView tvIncludeMore;
    @Bind(R.id.iv_shouyeLeagues_recruit)
    ImageView ivShouyeLeaguesRecruit;
    @Bind(R.id.tv_shouyeLeagues_retitle)
    TextView tvShouyeLeaguesRetitle;
    @Bind(R.id.lay_shouyeLeagues_first)
    LinearLayout layShouyeLeaguesFirst;
    @Bind(R.id.tv_shouyeLeagues_recontent)
    TextView tvShouyeLeaguesRecontent;
    @Bind(R.id.iv_shouyeLeagues_news)
    ImageView ivShouyeLeaguesNews;
    @Bind(R.id.tv_shouyeLeagues_newtitle)
    TextView tvShouyeLeaguesNewtitle;
    @Bind(R.id.lay_shouyeLeagues_second)
    LinearLayout layShouyeLeaguesSecond;
    @Bind(R.id.tv_shouyeLeagues_newcontent)
    TextView tvShouyeLeaguesNewcontent;
    @Bind(R.id.iv_shouyeLeagues_discount)
    ImageView ivShouyeLeaguesDiscount;
    @Bind(R.id.tv_shouyeLeagues_discounttitle)
    TextView tvShouyeLeaguesDiscounttitle;
    @Bind(R.id.lay_shouyeLeagues_third)
    LinearLayout layShouyeLeaguesThird;
    @Bind(R.id.tv_shouyeLeagues_discountcontent)
    TextView tvShouyeLeaguesDiscountcontent;
    @Bind(R.id.lay_shouyeLeagues_dyn)
    LinearLayout layShouyeLeaguesDyn;
    @Bind(R.id.tv_leavemessage_dyn)
    TextView tvLeavemessageDyn;
    @Bind(R.id.ind_shouyeLeagues_area)
    LinearLayout indShouyeLeaguesArea;
    @Bind(R.id.piec_shouyeLeagues_dyn)
    PieChart piec_shouyeLeagues_dyn;
    @Bind(R.id.llt_shouyeLeagues_salenum_show)
    RelativeLayout lltShouyeLeaguesSalenumShow;
    @Bind(R.id.tv_shouyeLeagues_restip)
    TextView tvShouyeLeaguesRestip;
    @Bind(R.id.tv_shouyeLeagues_shoppingtip)
    TextView tvShouyeLeaguesShoppingtip;
    @Bind(R.id.tv_shouyeLeagues_staytip)
    TextView tvShouyeLeaguesStaytip;
    @Bind(R.id.tv_shouyeLeagues_othertip)
    TextView tvShouyeLeaguesOthertip;
    @Bind(R.id.iv_shouyeLeagues_restaurant)
    ImageView ivShouyeLeaguesRestaurant;
    @Bind(R.id.tv_shouyeLeagues_restaurant)
    TextView tvShouyeLeaguesRestaurant;
    @Bind(R.id.tv_shouyeLeagues_resnum)
    TextView tvShouyeLeaguesResnum;
    @Bind(R.id.iv_shouyeLeagues_shopping)
    ImageView ivShouyeLeaguesShopping;
    @Bind(R.id.tv_shouyeLeagues_shopping)
    TextView tvShouyeLeaguesShopping;
    @Bind(R.id.tv_shouyeLeagues_shopnum)
    TextView tvShouyeLeaguesShopnum;
    @Bind(R.id.iv_shouyeLeagues_stay)
    ImageView ivShouyeLeaguesStay;
    @Bind(R.id.tv_shouyeLeagues_stay)
    TextView tvShouyeLeaguesStay;
    @Bind(R.id.tv_shouyeLeagues_staynum)
    TextView tvShouyeLeaguesStaynum;
    @Bind(R.id.iv_shouyeLeagues_other)
    ImageView ivShouyeLeaguesOther;
    @Bind(R.id.tv_shouyeLeagues_other)
    TextView tvShouyeLeaguesOther;
    @Bind(R.id.tv_shouyeLeagues_num)
    TextView tvShouyeLeaguesNum;
    @Bind(R.id.adeslltview_siderlayout)
    SliderLayout mDemoSlider;
    @Bind(R.id.adeslltview_siderlayout_progressBar)
    ProgressBar ad_siderlayout_progressBar;
    @Bind(R.id.adeslltview_llt)
    LinearLayout ad_llt;
    @Bind(R.id.sv_shouyeLeagues)
    ListenedScrollView svShouyeLeagues;
    private MainActivity mActivity;
    private OkHttpsImp mOkHttpsImp;
    private ArrayList<LeaguesAllCountList> mAllCountList =
            new ArrayList<LeaguesAllCountList>();
    private ArrayList<LeaguesAddCountlist> mAddCountList =
            new ArrayList<LeaguesAddCountlist>();

    private ArrayList<LeaguesYellBean> mData = new ArrayList<LeaguesYellBean>();
    private ArrayList<LeaguesYellBean> mDataZxy = new ArrayList<LeaguesYellBean>();
    private final static int COLUMN_COUNT = 6;
    private int page = 0;
    boolean  ScrollChanged=false;

    LinearLayout lay_shouyeLeagues_child;
    private Typeface tf;
    //    protected String[] mParties = new String[]{
//            "餐饮", "购物", "住宿", "其他"
//    };
    protected String[] mParties = null;
    public static final int[] PIECHART_COLORS = {
            Color.rgb(255, 118, 156), Color.rgb(255, 186, 29), Color.rgb(179, 144, 252),
            Color.rgb(22, 204, 205)
    };
    private ArrayList<ShouYeBannerBean> ades_ImageEs = new ArrayList<>();

    @OnClick({R.id.tv_include_more,R.id.img_shouyeLeagues_response})
    public void OnClick(View v) {
        switch (v.getId()) {
            case R.id.tv_include_more:
                Intent intent = new Intent();
                intent.setClass(mActivity, LeaguesDynamicListActivity.class);
                startActivity(intent);
                break;
            case R.id.img_shouyeLeagues_response:
                ContentUtils.showMsg(mActivity, getString(R.string.underconstruction));
                break;
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fm_shouyeleagues, null);
        ButterKnife.bind(this, view);
        mActivity = (MainActivity) getActivity();
        mOkHttpsImp = OkHttpsImp.SINGLEOKHTTPSIMP.newInstance(mActivity);
        initViewAndData();
        setLisenter();

        return view;
    }
    private void setLisenter() {
        //设置监听。
        svShouyeLeagues.setOnScrollListener(new ListenedScrollView.OnScrollListener(){
            @Override
            public void onBottomArrived() {
                //滑倒底部了
                if(ScrollChanged==false){
                    ScrollChanged=true;
                    initAnimation();
                }
            }

            @Override
            public void onScrollStateChanged(ListenedScrollView view, int scrollState) {
                //滑动状态改变
            }

            @Override
            public void onScrollChanged(int l, int t, int oldl, int oldt) {
                //滑动位置改变
                ScrollChanged=false;
            }
        });
    }
    public void initViewAndData() {
        initViewSize();
        mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Default);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                ScreenUtils.getScreenWidth(mActivity) / 4);
        ad_llt.setLayoutParams(params);
        piec_shouyeLeagues_dyn.setUsePercentValues(false);
        piec_shouyeLeagues_dyn.getDescription().setEnabled(false);
        piec_shouyeLeagues_dyn.setExtraOffsets(5, 10, 5, 5);
        piec_shouyeLeagues_dyn.setDragDecelerationFrictionCoef(0.95f);
        piec_shouyeLeagues_dyn.setDrawHoleEnabled(true);
        piec_shouyeLeagues_dyn.setHoleColor(Color.WHITE);
        piec_shouyeLeagues_dyn.setTransparentCircleColor(Color.WHITE);
        piec_shouyeLeagues_dyn.setTransparentCircleAlpha(0);
        piec_shouyeLeagues_dyn.setHoleRadius(40f);
        piec_shouyeLeagues_dyn.setTransparentCircleRadius(61f);
        piec_shouyeLeagues_dyn.setDrawCenterText(true);
        piec_shouyeLeagues_dyn.setRotationAngle(0);
        piec_shouyeLeagues_dyn.setRotationEnabled(false);
        piec_shouyeLeagues_dyn.setHighlightPerTapEnabled(true);
        piec_shouyeLeagues_dyn.setOnChartValueSelectedListener(this);
        piec_shouyeLeagues_dyn.animateY(1400, Easing.EasingOption.EaseInOutQuad);
        Legend l = piec_shouyeLeagues_dyn.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(0f);
        l.setYOffset(0f);
        l.setEnabled(false);
        piec_shouyeLeagues_dyn.setEntryLabelColor(Color.WHITE);
        piec_shouyeLeagues_dyn.setEntryLabelTextSize(12f);
    }
    public void userLoginStatus(boolean isLogin) {
        if (isLogin) {
//            getYellAndDynamicData();
//            getDistrictCount();
        } else {


        }
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {

        if (e == null)
            return;
        Intent intent = new Intent();
        intent.setClass(mActivity, LeaguesStorelistActivity.class);
        startActivity(intent);
    }

    @Override
    public void onNothingSelected() {
        Log.i("PieChart", "nothing selected");
    }

    /**
     * 设置首页各个title一级文字大小
     */
    private void initViewSize() {
        tvIncludeTitle.setText("商圈动态");
        tvLeavemessageDyn.setText("松江商圈");
    }

    /**
     * 获取绝对路径
     */
    private String getAbsoluteUrl(String relativeUrl) {
        if (relativeUrl.startsWith("http://"))
            return relativeUrl;
        return API.HOST + relativeUrl;
    }

    @Override
    public void onStart() {
        super.onStart();
        getYellAndDynamicData();
        getDistrictCount();
    }

    /**
     * 查询商圈统计
     */
    private void getDistrictCount() {
        try {
            mOkHttpsImp.districtCount(
                    "120101",
                    mActivity.uuid,
                    mActivity.reqTime,
                    new MyResultCallback<String>() {
                        @Override
                        public void onResponseResult(Result result) {
                            String reString = result.getData();
                            Log.i("tag", "查询商圈统计返回 317----->" + reString);
                            try {
                                JSONObject jsonObject = new JSONObject(reString);
                                String str_allCountList = jsonObject.getString("allCountList");
                                String str_addCountList = jsonObject.getString("addCountList");
//                              String strcutCount = jsonObject.getString("cutCountList");
                                /**
                                 * 总数量
                                 */
                                if (!TextUtils.isEmpty(str_allCountList)) {
                                    mAllCountList.clear();
                                    ArrayList<LeaguesAllCountList> allCountList =
                                            (ArrayList<LeaguesAllCountList>) JSON
                                            .parseArray(str_allCountList,
                                                    LeaguesAllCountList.class);
                                    startSort(false);
                                    mAllCountList.addAll(allCountList);

                                    setPieNetData();
                                }
                                /**
                                 * 新增数量
                                 */
                                if (!TextUtils.isEmpty(str_addCountList)) {
                                    mAddCountList.clear();
                                    ArrayList<LeaguesAddCountlist> addCountList =
                                            (ArrayList<LeaguesAddCountlist>) JSON
                                            .parseArray(str_addCountList,
                                                    LeaguesAddCountlist.class);
                                    //排序取出
                                    for(LeaguesAddCountlist  leaguesaddcountlist:addCountList){
                                        if(mAllCountList.get(0).getBusinessType().equals
                                                (leaguesaddcountlist.getBusinessType())
                                                ){
                                           mAddCountList.add(leaguesaddcountlist);
                                        }else
                                        if(mAllCountList.get(1).getBusinessType().equals
                                                (leaguesaddcountlist.getBusinessType())
                                                ){
                                            mAddCountList.add(leaguesaddcountlist);
                                        }else
                                        if(mAllCountList.get(2).getBusinessType().equals
                                                (leaguesaddcountlist.getBusinessType())
                                                ){
                                            mAddCountList.add(leaguesaddcountlist);
                                        }
                                        else{
                                            mAddCountList.add(leaguesaddcountlist);
                                        }
                                    }
                                    setAnimationData();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }

                        @Override
                        public void onResponseFailed(String msg) {
                            ContentUtils.showMsg(mActivity, "网络访问失败");
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void  setAnimationData(){
        int allcount = 0;
        String first = "0";
        String two = "0";
        String three = "0";
        try {
            for(LeaguesAddCountlist  leaguesaddcountlist:mAddCountList){
                if(leaguesaddcountlist.getBusinessType().equals(mAllCountList.get(0).getBusinessType())
                        ){
                    tvShouyeLeaguesRestaurant.setText(leaguesaddcountlist.getBusinessType());
                    tvShouyeLeaguesResnum.setText("+" + leaguesaddcountlist.getAddCount());
                    first = String.valueOf(leaguesaddcountlist.getAddCount());
                }else
                if(leaguesaddcountlist.getBusinessType().equals(mAllCountList.get(1).getBusinessType())
                        ){
                    tvShouyeLeaguesShopping.setText(leaguesaddcountlist.getBusinessType());
                    tvShouyeLeaguesShopnum.setText("+" + leaguesaddcountlist.getAddCount());
                    two = String.valueOf(leaguesaddcountlist.getAddCount());
                }else
                if(leaguesaddcountlist.getBusinessType().equals(mAllCountList.get(2).getBusinessType())
                        ){
                    tvShouyeLeaguesStay.setText(leaguesaddcountlist.getBusinessType());
                    tvShouyeLeaguesStay.setText("+" + leaguesaddcountlist.getAddCount());
                    three = String.valueOf(leaguesaddcountlist.getAddCount());
                }
            }

            if (!tvShouyeLeaguesRestaurant.getText().equals(mAllCountList.get(0).getBusinessType())) {
                tvShouyeLeaguesRestaurant.setText(mAllCountList.get(0).getBusinessType());
                tvShouyeLeaguesResnum.setText("+" + 0);
                first = String.valueOf(0);
            }
            if (!tvShouyeLeaguesShopping.getText().equals(mAllCountList.get(1).getBusinessType())) {
                tvShouyeLeaguesShopping.setText(mAllCountList.get(1).getBusinessType());
                tvShouyeLeaguesShopnum.setText("+" + 0);
                two = String.valueOf(0);
            }
            if (!tvShouyeLeaguesStay.getText().equals(mAllCountList.get(2).getBusinessType())) {
                tvShouyeLeaguesStay.setText(mAllCountList.get(2).getBusinessType());
                tvShouyeLeaguesStaynum.setText("+" + 0);
                three = String.valueOf(0);
            }
            tvShouyeLeaguesOther.setText("其他");
            for (LeaguesAddCountlist mAdddistrictcountbean : mAddCountList) {
                allcount = allcount + stringChangeInt(mAdddistrictcountbean.getAddCount());
            }
            allcount = allcount - stringChangeInt(first) -
                    stringChangeInt(two) -
                    stringChangeInt(three);

            tvShouyeLeaguesNum.setText("+" + String.valueOf(allcount));
        }catch (Exception  e){
            e.printStackTrace();
        }
    }
    private void setPieNetData() {
        int allcount = 0;
        String first = "0";
        String two = "0";
        String three = "0";
        try {
            if (mAllCountList.get(0) != null) {
                tvShouyeLeaguesRestip.setText(mAllCountList.get(0).getBusinessType() + "");
                first = mAllCountList.get(0).getAllCount();
            }
            if (mAllCountList.get(1) != null) {
                tvShouyeLeaguesShoppingtip.setText(mAllCountList.get(1).getBusinessType() + "");
                two = mAllCountList.get(1).getAllCount();
            }
            if (mAllCountList.get(2) != null) {
                tvShouyeLeaguesStaytip.setText(mAllCountList.get(2).getBusinessType() + "");
                three = mAllCountList.get(2).getAllCount();
            }
            tvShouyeLeaguesOthertip.setText("其他");
            for (LeaguesAllCountList  mAlldistrictcountbean : mAllCountList) {
                allcount = allcount + stringChangeInt(mAlldistrictcountbean.getAllCount());
            }
            allcount = allcount - stringChangeInt(first) -
                    stringChangeInt(two) -
                    stringChangeInt(three);
            mParties = new String[]{
                    first,
                    two,
                    three,
                    String.valueOf(allcount)
            };
        } catch (Exception e) {
            e.printStackTrace();
        }
        setData(4, 100);
    }

    private int stringChangeInt(String count) {
        int temp = 0;
        if (!TextUtils.isEmpty(count)) {
            temp = Integer.parseInt(count);
        }
        return temp;
    }

    /**
     * 查询吆喝和商圈动态
     */
    private void getYellAndDynamicData() {
        try {
            Log.i("tag", "省份代码code--->" + mActivity.shopRovinceid);
            mOkHttpsImp.queryBusinessDynamic(
                    "",                           //businessId   BD2016052013475900000010
                    "",                           //area
                    "310117",                    //businessCircle
                    "",                           //messageType
                    "",                           //pushScope
                    "",                           //businessName mActivity.ShopName
                    "",                           //phone
                    "",                           //messageTitle
                    "",                           //messageContent
                    "310000",                    //provinces
                    page+"",                     //pageNum
                    "15",                        //pageSize
                    mActivity.uuid,             //serNum
                    "app",                       //source
                    mActivity.reqTime,          //reqTime
                    new MyResultCallback<String>() {
                        @Override
                        public void onResponseResult(Result result) {
                            String reString = result.getData();
                            Log.i("tag", "resString 274----->" + reString);
                            try {
                                JSONObject jsonObject = new JSONObject(reString);
                                reString = jsonObject.getString("list");
                                if (!TextUtils.isEmpty(reString)) {
                                    mData.clear();
                                    ArrayList<LeaguesYellBean> leaguesyellbeanlist = (ArrayList<LeaguesYellBean>) JSON
                                            .parseArray(reString,
                                                    LeaguesYellBean.class);
                                    for (LeaguesYellBean LeaguesZxy : leaguesyellbeanlist) {
                                        if (LeaguesZxy.getMessageType().equals("MT0000")) {
                                            mData.add(LeaguesZxy);
                                        }
                                    }
                                    updateview(mData);

                                    mDataZxy.clear();
                                    for (LeaguesYellBean LeaguesZxy : leaguesyellbeanlist) {
                                        if (!LeaguesZxy.getMessageType().equals("MT0000")) {
                                            mDataZxy.add(LeaguesZxy);
                                        }
                                    }
                                    showdynamic(mDataZxy);
                                }

                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }

                        @Override
                        public void onResponseFailed(String msg) {
                            ContentUtils.showMsg(mActivity, "网络访问失败");
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void updateview(ArrayList<LeaguesYellBean> arrayList) {
        vfShouyeleaguesDyn.removeAllViews();
        for (LeaguesYellBean leaguesyellbean : arrayList) {
            vfShouyeleaguesDyn.addView(getLinearLayout(leaguesyellbean));
        }
    }

    protected void showdynamic(ArrayList<LeaguesYellBean> arrayList) {
        try {
            Glide.with(mActivity).load(compareMessageType(arrayList.get(0).getMessageType())).
                    error(R.mipmap.demo).into(ivShouyeLeaguesRecruit);
            tvShouyeLeaguesRetitle.setText(arrayList.get(0).getMessageTitle());
            tvShouyeLeaguesRecontent.setText(arrayList.get(0).getMessageContent());
            Glide.with(mActivity).load(compareMessageType(arrayList.get(1).getMessageType())).
                    error(R.mipmap.demo).into(ivShouyeLeaguesNews);
            tvShouyeLeaguesNewtitle.setText(arrayList.get(1).getMessageTitle());
            tvShouyeLeaguesNewcontent.setText(arrayList.get(1).getMessageContent());
            Glide.with(mActivity).load(compareMessageType(arrayList.get(2).getMessageType())).
                    error(R.mipmap.demo).into(ivShouyeLeaguesDiscount);
            tvShouyeLeaguesDiscounttitle.setText(arrayList.get(2).getMessageTitle());
            tvShouyeLeaguesDiscountcontent.setText(arrayList.get(2).getMessageContent());
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    protected int compareMessageType(String messagetype) {
        if (messagetype.equals("MT0001")) {
            return R.mipmap.icon_news;
        } else if (messagetype.equals("MT0002")) {
            return R.mipmap.icon_cooperate;
        } else if (messagetype.equals("MT0003")) {
            return R.mipmap.icon_discount;
        }
        return R.mipmap.icon_news;
    }

    private LinearLayout getLinearLayout(LeaguesYellBean leaguesyellbean) {
        lay_shouyeLeagues_child = (LinearLayout) LayoutInflater.from(mActivity).inflate(
                R.layout.item_shouyeleagues_dyn, null);
        TextView tv_shouyeLeagues_title = (TextView) lay_shouyeLeagues_child.findViewById(R.id.tv_shouyeLeagues_title);
        tv_shouyeLeagues_title.setText(leaguesyellbean.getMessageTitle());
        lay_shouyeLeagues_child.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(mActivity, LeaguesYellListActivity.class);
                intent.putExtra("whatchild",vfShouyeleaguesDyn.getDisplayedChild());
                startActivity(intent);
            }
        });
        return lay_shouyeLeagues_child;
    }

    //初始化动画
    private void initAnimation() {
        Animation animation = AnimationUtils.loadAnimation(mActivity, R.anim.img_animation);
        LinearInterpolator lin = new LinearInterpolator();//设置动画匀速运动
        animation.setInterpolator(lin);
        ivShouyeLeaguesRestaurant.startAnimation(animation);
        ivShouyeLeaguesShopping.startAnimation(animation);
        ivShouyeLeaguesStay.startAnimation(animation);
        ivShouyeLeaguesOther.startAnimation(animation);
    }

    private void setData(int count, float range) {

        float mult = range;
        ArrayList<PieEntry> entries = new ArrayList<PieEntry>();
        for (int i = 0; i < count; i++) {
            entries.add(new PieEntry((float) (stringChangeInt(mParties[i % mParties.length]) + 1), mParties[i % mParties.length]));
        }
        PieDataSet dataSet = new PieDataSet(entries, "Election Results");
        dataSet.setDrawValues(false);
        dataSet.setSliceSpace(0f);
        dataSet.setSelectionShift(0f);
        ArrayList<Integer> colors = new ArrayList<Integer>();
        for (int c : PIECHART_COLORS)
            colors.add(c);
        dataSet.setColors(colors);
        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.WHITE);
        piec_shouyeLeagues_dyn.setData(data);
        piec_shouyeLeagues_dyn.highlightValues(null);
        piec_shouyeLeagues_dyn.invalidate();
    }

    public void getBannerData(String banner) {
        mDemoSlider.removeAllSliders();
        ades_ImageEs = (ArrayList<ShouYeBannerBean>) JSON
                .parseArray(banner,
                        ShouYeBannerBean.class);
        if (ades_ImageEs != null && ades_ImageEs.size() > 0) {
            for (int i = 0; i < ades_ImageEs.size(); i++) {
                TextSliderView textSliderView = new TextSliderView(
                        mActivity, i);
                textSliderView
                        .image(ades_ImageEs.get(i).getImageUrl())
                        .error(R.mipmap.adshouye);
                textSliderView
                        .setOnSliderClickListener(ShouyeLeaguesFragment.this);
                mDemoSlider.addSlider(textSliderView);
            }
        } else {
            for (int i = 0; i < 3; i++) {
                TextSliderView textSliderView = new TextSliderView(
                        mActivity, i);
                textSliderView.image(R.mipmap.adshouye);
                textSliderView
                        .setOnSliderClickListener(ShouyeLeaguesFragment.this);
                mDemoSlider.addSlider(textSliderView);
            }
        }
        mDemoSlider
                .setPresetIndicatorV(SliderLayout.PresetIndicators.Center_Bottom);
        ad_siderlayout_progressBar.setVisibility(View.GONE);
        mDemoSlider.setVisibility(View.VISIBLE);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
    /**
     * 排序
     *
     * @param beginsort true 从小到大 false 从大到小
     */
    private void startSort(final boolean beginsort) {
        Collections.sort(mAllCountList, new Comparator<LeaguesAllCountList>() {
            @Override
            public int compare(LeaguesAllCountList lhs, LeaguesAllCountList rhs) {
                Integer id1 = Integer.parseInt(lhs.getAllCount());
                Integer id2 = Integer.parseInt(rhs.getAllCount());
                if (beginsort) {
                    return id1.compareTo(id2);
                } else {
                    return id2.compareTo(id1);
                }
            }
        });

    }

    /**
     * banner点击
     */
    @Override
    public void onSliderClick(BaseSliderView slider) {
    }

}
