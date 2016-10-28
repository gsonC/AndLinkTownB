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

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.lianbi.mezone.b.httpresponse.OkHttpsImp;
import com.lianbi.mezone.b.ui.MainActivity;
import com.xizhi.mezone.b.R;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ShouyeLeaguesFragment extends Fragment {

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
    private final static int COLUMN_COUNT = 6;
    LinearLayout lay_shouyeLeagues_child;
    private Typeface tf;
    protected String[] mParties = new String[]{
            "餐饮", "购物", "住宿", "其他"
    };

    @OnClick({R.id.tv_include_more})
    public void OnClick(View v) {
        switch (v.getId()) {
            case R.id.tv_include_more:

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
        initAnimation();
        return view;
    }

    public void initViewAndData() {
        initViewSize();
        piec_shouyeLeagues_dyn.setUsePercentValues(true);
        piec_shouyeLeagues_dyn.getDescription().setEnabled(false);
        piec_shouyeLeagues_dyn.setExtraOffsets(5, 10, 5, 5);
        piec_shouyeLeagues_dyn.setDragDecelerationFrictionCoef(0.95f);
        piec_shouyeLeagues_dyn.setExtraOffsets(20.f, 0.f, 20.f, 0.f);
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
        setData(4, 100);
        piec_shouyeLeagues_dyn.animateY(1400, Easing.EasingOption.EaseInOutQuad);
        Legend l = piec_shouyeLeagues_dyn.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setEnabled(false);
        piec_shouyeLeagues_dyn.animateY(1400, Easing.EasingOption.EaseInOutQuad);
        for (int i = 0; i < COLUMN_COUNT; i++) {
            vfShouyeleaguesDyn.addView(getLinearLayout(i));
            vfShouyeleaguesDyn.addView(getLinearLayout(i));
            vfShouyeleaguesDyn.addView(getLinearLayout(i));
        }
    }

    /**
     * 设置首页各个title一级文字大小
     */
    private void initViewSize() {
        tvIncludeTitle.setText("商圈动态");
        tvLeavemessageDyn.setText("松江商圈");
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

    private LinearLayout getLinearLayout(final int i) {
        lay_shouyeLeagues_child = (LinearLayout) LayoutInflater.from(mActivity).inflate(
                R.layout.item_shouyeleagues_dyn, null);
        lay_shouyeLeagues_child.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mActivity, "点击--->" + i, Toast.LENGTH_SHORT).show();
            }
        });
        return lay_shouyeLeagues_child;
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
        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);
        colors.add(ColorTemplate.getHoloBlue());
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
