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
import com.lianbi.mezone.b.bean.LeaguesYellBean;
import com.lianbi.mezone.b.httpresponse.API;
import com.lianbi.mezone.b.httpresponse.MyResultCallback;
import com.lianbi.mezone.b.httpresponse.OkHttpsImp;
import com.lianbi.mezone.b.ui.LeaguesDynamicListActivity;
import com.lianbi.mezone.b.ui.LeaguesStorelistActivity;
import com.lianbi.mezone.b.ui.LeaguesYellListActivity;
import com.lianbi.mezone.b.ui.MainActivity;
import com.xizhi.mezone.b.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.hgh.utils.ContentUtils;
import cn.com.hgh.utils.Result;

public class ShouyeLeaguesFragment extends Fragment implements OnChartValueSelectedListener {

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
    private MainActivity mActivity;
    private OkHttpsImp mOkHttpsImp;
    private ArrayList<LeaguesYellBean> mData = new ArrayList<LeaguesYellBean>();
    private ArrayList<LeaguesYellBean> mDataZxy = new ArrayList<LeaguesYellBean>();
    private final static int COLUMN_COUNT = 6;
    LinearLayout lay_shouyeLeagues_child;
    private Typeface tf;
    protected String[] mParties = new String[]{
            "餐饮", "购物", "住宿", "其他"
    };
    public static final int[] PIECHART_COLORS = {
            Color.rgb(255,188, 156), Color.rgb(255,186,29), Color.rgb(179,144,252),
            Color.rgb(22, 204, 205)
    };
    @OnClick({R.id.tv_include_more})
    public void OnClick(View v) {
        switch (v.getId()) {
            case R.id.tv_include_more:
                Intent   intent=new Intent();
                intent.setClass(mActivity, LeaguesDynamicListActivity.class);
                startActivity(intent);
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
        getYellData();
        initAnimation();
        return view;
    }

    public void initViewAndData() {
        initViewSize();
        piec_shouyeLeagues_dyn.setUsePercentValues(true);
        piec_shouyeLeagues_dyn.getDescription().setEnabled(false);
        piec_shouyeLeagues_dyn.setExtraOffsets(5, 10, 5, 5);
        piec_shouyeLeagues_dyn.setDragDecelerationFrictionCoef(0.95f);
        piec_shouyeLeagues_dyn.setDrawHoleEnabled(true);
        piec_shouyeLeagues_dyn.setHoleColor(Color.WHITE);
        piec_shouyeLeagues_dyn.setTransparentCircleColor(Color.WHITE);
        piec_shouyeLeagues_dyn.setTransparentCircleAlpha(110);
        piec_shouyeLeagues_dyn.setHoleRadius(58f);
        piec_shouyeLeagues_dyn.setTransparentCircleRadius(61f);
        piec_shouyeLeagues_dyn.setDrawCenterText(true);
        piec_shouyeLeagues_dyn.setRotationAngle(0);
        piec_shouyeLeagues_dyn.setRotationEnabled(true);
        piec_shouyeLeagues_dyn.setHighlightPerTapEnabled(true);
        piec_shouyeLeagues_dyn.setOnChartValueSelectedListener(this);
        setData(4, 100);
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
    @Override
    public void onValueSelected(Entry e, Highlight h) {

        if (e == null)
            return;
        Intent   intent=new Intent();
        intent.setClass(mActivity, LeaguesStorelistActivity.class);
        startActivity(intent);
        Log.i("VAL SELECTED",
                "Value: " + e.getY() + ", index: " + h.getX()
                        + ", DataSet index: " + h.getDataSetIndex());
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
    /**
     * 查询吆喝和商圈动态
     */
    private void getYellData() {
//        ic(String businessId,
//                String area,
//                String businessCircle,
//                String messageType,
//                String pushScope,
//                String author,
//                String phone,
//                String messageTitle,
//                String messageContent,
//                String pageNum,
//                String pageSize,
//                String serNum, String source,
//                String reqTime,
        try {
            mOkHttpsImp.queryBusinessDynamic(
                    "BD2016052013475900000010",
                    "area",
                    "310117",
                    "",
                    "",
                    "",
                    "",
                    "",
                    "",
                    "",
                    "",
                    mActivity.uuid,
                    "app",
                    mActivity.reqTime,
                    new MyResultCallback<String>() {
                @Override
                public void onResponseResult(Result result) {
                    String reString = result.getData();
                    Log.i("tag","resString 274----->"+reString);
                    try {
                        JSONObject jsonObject= new JSONObject(reString);
                        reString = jsonObject.getString("list");
                        if (!TextUtils.isEmpty(reString)) {
                            mData.clear();
                            ArrayList<LeaguesYellBean> leaguesyellbeanlist = (ArrayList<LeaguesYellBean>) JSON
                                    .parseArray(reString,
                                            LeaguesYellBean.class);
                            mData.addAll(leaguesyellbeanlist);
                            updateview(mData);

                            mDataZxy.clear();
                            for(LeaguesYellBean  LeaguesZxy:leaguesyellbeanlist){
                              if(!LeaguesZxy.getMessageType().equals("MT0000")){
                                  mDataZxy.addAll(leaguesyellbeanlist);
                              }
                            }
                            showdynamic(mDataZxy);
                        }

                    } catch (JSONException e) {
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
        for(LeaguesYellBean   leaguesyellbean:arrayList){
            vfShouyeleaguesDyn.addView(getLinearLayout(leaguesyellbean));
        }
    }
    protected void showdynamic(ArrayList<LeaguesYellBean> arrayList) {
        Glide.with(mActivity).load(compareMessageType(arrayList.get(0).getMessageType())).
                error(R.mipmap.default_head).into(ivShouyeLeaguesRecruit);
        tvShouyeLeaguesRetitle.setText(arrayList.get(0).getMessageTitle());
        tvShouyeLeaguesRecontent.setText(arrayList.get(0).getMessageContent());
        Glide.with(mActivity).load(compareMessageType(arrayList.get(1).getMessageType())).
                error(R.mipmap.default_head).into(ivShouyeLeaguesNews);
        tvShouyeLeaguesNewtitle.setText(arrayList.get(1).getMessageTitle());
        tvShouyeLeaguesNewcontent.setText(arrayList.get(1).getMessageContent());
        Glide.with(mActivity).load(compareMessageType(arrayList.get(2).getMessageType())).
                error(R.mipmap.default_head).into(ivShouyeLeaguesDiscount);
        tvShouyeLeaguesDiscounttitle.setText(arrayList.get(2).getMessageTitle());
        tvShouyeLeaguesDiscountcontent.setText(arrayList.get(2).getMessageContent());
    }
    protected  int  compareMessageType(String messagetype){
        if(messagetype.equals("MT0001")){
            return R.mipmap.icon_recruit;
        }
        else
        if(messagetype.equals("MT0002")){
            return R.mipmap.icon_news;
        }
        else
        if(messagetype.equals("MT0003")){
            return R.mipmap.icon_discount;
        }
        return R.mipmap.icon_recruit;
    }
    private LinearLayout getLinearLayout(LeaguesYellBean leaguesyellbean) {
        lay_shouyeLeagues_child = (LinearLayout) LayoutInflater.from(mActivity).inflate(
                R.layout.item_shouyeleagues_dyn, null);
        TextView   tv_shouyeLeagues_title=(TextView)lay_shouyeLeagues_child.findViewById(R.id.tv_shouyeLeagues_title);
        tv_shouyeLeagues_title.setText(leaguesyellbean.getMessageTitle());
        lay_shouyeLeagues_child.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent   intent=new Intent();
                intent.setClass(mActivity, LeaguesYellListActivity.class);
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
        for (int i = 0; i < count ; i++) {
            entries.add(new PieEntry((float) ((Math.random() * mult) + mult / 5), mParties[i % mParties.length]));
        }
        PieDataSet dataSet = new PieDataSet(entries, "Election Results");
        dataSet.setSliceSpace(0f);
        dataSet.setSelectionShift(5f);
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


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
