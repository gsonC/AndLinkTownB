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
import android.support.v4.widget.SwipeRefreshLayout;
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
import com.lianbi.mezone.b.bean.LeaguesCountList;
import com.lianbi.mezone.b.bean.LeaguesYellBean;
import com.lianbi.mezone.b.bean.ShouYeBannerBean;
import com.lianbi.mezone.b.httpresponse.API;
import com.lianbi.mezone.b.httpresponse.MyResultCallback;
import com.lianbi.mezone.b.httpresponse.OkHttpsImp;
import com.lianbi.mezone.b.ui.CompanyEventActivity;
import com.lianbi.mezone.b.ui.LeaguesDynamicListActivity;
import com.lianbi.mezone.b.ui.LeaguesStorelistActivity;
import com.lianbi.mezone.b.ui.LeaguesYellListActivity;
import com.lianbi.mezone.b.ui.MainActivity;
import com.xizhi.mezone.b.R;

import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.hgh.baseadapter.BaseAdapterHelper;
import cn.com.hgh.baseadapter.QuickAdapter;
import cn.com.hgh.playview.BaseSliderView;
import cn.com.hgh.playview.BaseSliderView.OnSliderClickListener;
import cn.com.hgh.playview.SliderLayout;
import cn.com.hgh.playview.imp.TextSliderView;
import cn.com.hgh.utils.ContentUtils;
import cn.com.hgh.utils.Result;
import cn.com.hgh.utils.ScreenUtils;
import cn.com.hgh.view.ListenedScrollView;
import cn.com.hgh.view.MyListView;

public class ShouyeLeaguesFragment extends Fragment implements OnChartValueSelectedListener, OnSliderClickListener,
        SwipeRefreshLayout.OnRefreshListener {

    @Nullable
    @Bind(R.id.adeslltview_siderlayout)
    SliderLayout mDemoSlider;
    @Bind(R.id.adeslltview_siderlayout_progressBar)
    ProgressBar ad_siderlayout_progressBar;
    @Bind(R.id.adeslltview_llt)
    LinearLayout ad_llt;
    @Bind(R.id.iv_shouyeLeagues_yell)
    ImageView ivShouyeLeaguesYell;
    @Bind(R.id.v_shouyeLeagues_01)
    View vShouyeLeagues01;
    @Bind(R.id.vf_shouyeleagues_dyn)
    ViewFlipper vfShouyeleaguesDyn;
    @Bind(R.id.v_shouyeLeagues_02)
    View vShouyeLeagues02;
    @Bind(R.id.img_shouyeLeagues_response)
    ImageView imgShouyeLeaguesResponse;
    @Bind(R.id.tv_include_title)
    TextView tvIncludeTitle;
    @Bind(R.id.tv_include_more)
    TextView tvIncludeMore;
    @Bind(R.id.act_shouyeLeaguesdyn_listview)
    MyListView act_shouyeLeaguesdyn_listview;
    @Bind(R.id.tv_leavemessage_dyn)
    TextView tvLeavemessageDyn;
    @Bind(R.id.ind_shouyeLeagues_area)
    LinearLayout indShouyeLeaguesArea;
    @Bind(R.id.piec_shouyeLeagues_dyn)
    PieChart piec_shouyeLeagues_dyn;
    @Bind(R.id.tv_shouyeLeagues_nodata)
    TextView tv_shouyeLeagues_nodata;
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
    @Bind(R.id.sv_shouyeLeagues)
    ListenedScrollView svShouyeLeagues;
    @Bind(R.id.swipe_shouyeleagues)
    SwipeRefreshLayout swipe_shouyeleagues;
    private MainActivity mActivity;
    private OkHttpsImp mOkHttpsImp;
    private ArrayList<LeaguesCountList> mLeaguesCountList =
            new ArrayList<LeaguesCountList>();

    private ArrayList<LeaguesYellBean> mData = new ArrayList<LeaguesYellBean>();
    private ArrayList<LeaguesYellBean> mDataZxy = new ArrayList<LeaguesYellBean>();
    private ArrayList<LeaguesYellBean> mDataZxys = new ArrayList<LeaguesYellBean>();

    private QuickAdapter<LeaguesYellBean> mAdapter;
    private final static int COLUMN_COUNT = 6;
    private int page = 1;
    boolean ScrollChanged = false;

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

    @OnClick({R.id.tv_include_more, R.id.img_shouyeLeagues_response})
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
        initAdapter();
        return view;
    }

    private void setLisenter() {
        swipe_shouyeleagues.setOnRefreshListener(this);
        //设置监听。
        svShouyeLeagues.setOnScrollListener(new ListenedScrollView.OnScrollListener() {
            @Override
            public void onBottomArrived() {
                //滑倒底部了
                if (ScrollChanged == false) {
                    ScrollChanged = true;
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
                ScrollChanged = false;
            }
        });

    }

    public void initViewAndData() {
        initViewSize();
        swipe_shouyeleagues.setColorSchemeResources(R.color.colores_news_01, R.color.black);
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

    private void initAdapter() {
        mAdapter = new QuickAdapter<LeaguesYellBean>(mActivity,
                R.layout.item_shouyeleaguesdyn_list, mDataZxys) {
            @Override
            protected void convert(final BaseAdapterHelper helper, final LeaguesYellBean item) {
                LinearLayout lay_shouyeLeagues_dyn =
                        helper.getView(R.id.lay_shouyeLeagues_dyn);
                ImageView iv_shouyeLeagues_icon = helper.getView(R.id.iv_shouyeLeagues_icon);//
                TextView tv_shouyeLeagues_title = helper.getView(R.id.tv_shouyeLeagues_title);//
                TextView tv_shouyeLeagues_content = helper.getView(R.id.tv_shouyeLeagues_content);//
                if (!TextUtils.isEmpty(item.getMessageType())) {
                    iv_shouyeLeagues_icon.setImageResource(compareMessageType(item.getMessageType()));
                }
                tv_shouyeLeagues_title.setText(item.getMessageTitle());
                tv_shouyeLeagues_content.setText(item.getMessageContent());

                helper.getView(R.id.lay_shouyeLeagues_dyn).setOnClickListener(
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent();
                                intent.setClass(mActivity, LeaguesDynamicListActivity.class);
                                intent.putExtra("thefirsfew", String.valueOf(helper.getPosition()));
                                startActivity(intent);
                            }
                        });
            }
        };
        act_shouyeLeaguesdyn_listview.setAdapter(mAdapter);
    }

    @Override
    public void onRefresh() {
        getDistrictCount();
        getYellAndDynamicData();
        swipe_shouyeleagues.setRefreshing(false);
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
        tvLeavemessageDyn.setText("暂无商圈");
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
        getDistrictCount();
        getYellAndDynamicData();
    }

    /**
     * 查询商圈统计
     */
    private void getDistrictCount() {
        try {
            mOkHttpsImp.districtCount(
                    mActivity.areaCode,         //"120101"
                    mActivity.uuid,
                    mActivity.reqTime,
                    new MyResultCallback<String>() {
                        @Override
                        public void onResponseResult(Result result) {
                            String reString = result.getData();
                            Log.i("tag", "查询商圈统计返回 317----->" + reString);
                            try {
                                JSONObject jsonObject = new JSONObject(reString);
                                String areaName = jsonObject.getString("areaName");
                                String businessCountList = jsonObject.getString("businessCountList");
                                if (!tvLeavemessageDyn.getText().equals(areaName)) {
                                    initData();
                                }
                                tvLeavemessageDyn.setText(areaName);
                                if (!TextUtils.isEmpty(businessCountList)) {
                                    mLeaguesCountList.clear();
                                    ArrayList<LeaguesCountList> allCountList =
                                            (ArrayList<LeaguesCountList>) JSON
                                                    .parseArray(businessCountList,
                                                            LeaguesCountList.class);
                                    mLeaguesCountList.addAll(allCountList);
                                    setPieNetData();
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

    private void setAnimationData() {
        try {
            int LeaguesCountListsize = mLeaguesCountList.size();
            if (LeaguesCountListsize > 0) {
                tvShouyeLeaguesRestaurant.setText(mLeaguesCountList.get(0).getBusinessType() + "");
                tvShouyeLeaguesResnum.setText("+" + mLeaguesCountList.get(0).getAddCount());
            }
            if (LeaguesCountListsize > 1) {
                tvShouyeLeaguesShopping.setText(mLeaguesCountList.get(1).getBusinessType() + "");
                tvShouyeLeaguesShopnum.setText("+" + mLeaguesCountList.get(1).getAddCount());
            }
            if (LeaguesCountListsize > 2) {
                tvShouyeLeaguesStay.setText(mLeaguesCountList.get(2).getBusinessType());
                tvShouyeLeaguesStaynum.setText("+" + mLeaguesCountList.get(2).getAddCount());
            }
            if (LeaguesCountListsize > 3) {
                tvShouyeLeaguesOther.setText(mLeaguesCountList.get(3).getBusinessType());
                tvShouyeLeaguesNum.setText("+" + mLeaguesCountList.get(3).getAddCount());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setPieNetData() {
        String first = "0";
        String two = "0";
        String three = "0";
        String four = "0";
        try {
            int LeaguesCountListsize = mLeaguesCountList.size();
            if (LeaguesCountListsize > 0) {
                tvShouyeLeaguesRestip.setText(mLeaguesCountList.get(0).getBusinessType() + "");
                first = mLeaguesCountList.get(0).getAllCount();
            }
            if (LeaguesCountListsize > 1) {
                tvShouyeLeaguesShoppingtip.setText(mLeaguesCountList.get(1).getBusinessType() + "");
                two = mLeaguesCountList.get(1).getAllCount();
            }
            if (LeaguesCountListsize > 2) {
                tvShouyeLeaguesStaytip.setText(mLeaguesCountList.get(2).getBusinessType() + "");
                three = mLeaguesCountList.get(2).getAllCount();
            }
            if (LeaguesCountListsize > 3) {
                tvShouyeLeaguesOthertip.setText(mLeaguesCountList.get(3).getBusinessType() + "");
                four = mLeaguesCountList.get(3).getAllCount();
            }
            mParties = new String[]{
                    first,
                    two,
                    three,
                    four
            };
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (first.equals("0") &&
                two.equals("0") &&
                three.equals("0") &&
                four.equals("0")
                ) {
            tv_shouyeLeagues_nodata.setVisibility(View.VISIBLE);
            piec_shouyeLeagues_dyn.setVisibility(View.GONE);
        } else {
            tv_shouyeLeagues_nodata.setVisibility(View.GONE);
            piec_shouyeLeagues_dyn.setVisibility(View.VISIBLE);
            setData(4, 100);
        }
    }

    /**
     * 切换商圈
     * 初始化显示
     */
    private void initData() {
        tvShouyeLeaguesRestip.setText("暂无");
        tvShouyeLeaguesShoppingtip.setText("暂无");
        tvShouyeLeaguesStaytip.setText("暂无");
        tvShouyeLeaguesOthertip.setText("暂无");
        tvShouyeLeaguesRestaurant.setText("暂无");
        tvShouyeLeaguesResnum.setText("+" + 0);
        tvShouyeLeaguesShopping.setText("暂无");
        tvShouyeLeaguesShopnum.setText("+" + 0);
        tvShouyeLeaguesStay.setText("暂无");
        tvShouyeLeaguesStaynum.setText("+" + 0);
        tvShouyeLeaguesOther.setText("暂无");
        tvShouyeLeaguesNum.setText("+" + 0);
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
            mOkHttpsImp.queryBusinessDynamic(
                    "",                           //businessId   BD2016052013475900000010
                    mActivity.areaCode,                           //area
                    mActivity.areaCode,                    //businessCircle "310117",
                    "",                           //messageType
                    "",                           //pushScope
                    "",                           //businessName mActivity.ShopName
                    "",                           //phone
                    "",                           //messageTitle
                    "",                           //messageContent
                    mActivity.shopRovinceid,    //provinces "310000",
                    page + "",                     //pageNum
                    "50",                        //pageSize
                    mActivity.uuid,             //serNum
                    "app",                       //source
                    mActivity.reqTime,          //reqTime
                    new MyResultCallback<String>() {
                        @Override
                        public void onResponseResult(Result result) {
                            String reString = result.getData();
                            try {
                                if (!TextUtils.isEmpty(reString)) {
                                    JSONObject jsonObject = new JSONObject(reString);
                                    reString = jsonObject.optString("list");
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
                                        mDataZxy.clear();
                                        for (LeaguesYellBean LeaguesZxy : leaguesyellbeanlist) {
                                            if (!LeaguesZxy.getMessageType().equals("MT0000")
                                                    && mDataZxy.size() < 4) {
                                                mDataZxy.add(LeaguesZxy);
                                            }
                                        }
                                        showdynamic(mDataZxy);
                                    }
                                }
                                updateview(mData);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }

                        @Override
                        public void onResponseFailed(String msg) {
                            showdynamic(mDataZxy);
                            ContentUtils.showMsg(mActivity, "网络访问失败");
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void updateview(ArrayList<LeaguesYellBean> arrayList) {
        vfShouyeleaguesDyn.removeAllViews();
        if (arrayList.isEmpty()) {
            vfShouyeleaguesDyn.stopFlipping();
            vfShouyeleaguesDyn.addView(getLinearLayout(null));
        } else {
            vfShouyeleaguesDyn.startFlipping();
            for (LeaguesYellBean leaguesyellbean : arrayList) {
                vfShouyeleaguesDyn.addView(getLinearLayout(leaguesyellbean));
            }
        }
    }

    protected void showdynamic(ArrayList<LeaguesYellBean> arrayList) {
        mDataZxys.clear();
        if (arrayList.isEmpty()) {
            LeaguesYellBean leaguesyellbean = new LeaguesYellBean();
            leaguesyellbean.setMessageTitle("暂无动态");
            leaguesyellbean.setMessageContent("暂无动态");
            mDataZxy.clear();
            mDataZxy.add(leaguesyellbean);
            mDataZxys.addAll(mDataZxy);
        } else {
            mDataZxys.addAll(arrayList);
        }
        mAdapter.replaceAll(mDataZxys);
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
        if (leaguesyellbean != null) {
            tv_shouyeLeagues_title.setText(leaguesyellbean.getMessageTitle());
        }
        lay_shouyeLeagues_child.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(mActivity, LeaguesYellListActivity.class);
                intent.putExtra("whatchild", vfShouyeleaguesDyn.getDisplayedChild());
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
            entries.add(new PieEntry((float) (stringChangeInt(mParties[i % mParties.length]) + 2), mParties[i % mParties.length]));
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
     * banner点击
     */
    @Override
    public void onSliderClick(BaseSliderView slider) {
        if (ades_ImageEs != null && ades_ImageEs.size() > 0) {
            String url = ades_ImageEs.get(slider.getP()).getBannerUrl();
            if(!TextUtils.isEmpty(url)&&url.startsWith("http")){
                Intent intent = new Intent(mActivity, CompanyEventActivity.class);
                intent.putExtra("CompanyEventUrl", url);
                startActivity(intent);
            }
        }
    }
}
