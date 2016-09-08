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

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lianbi.mezone.b.fragment.OrederFragment;
import com.xizhi.mezone.b.R;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OrderContentActivity extends BaseActivity {

    public static final int POSITION0 = 0;
    public static final int POSITION1 = 1;
    public static final int POSITION2 = 2;
    @Bind(R.id.valid_period_from)
    TextView validPeriodFrom;
    @Bind(R.id.valid_period_to)
    TextView validPeriodTo;
    @Bind(R.id.tv_allrecord)
    TextView tvAllrecord;
    @Bind(R.id.tv_accessrecord)
    TextView tvAccessrecord;
    @Bind(R.id.tv_userecord)
    TextView tvUserecord;
    @Bind(R.id.tv_integral_memberfile)
    TextView tvIntegralMemberfile;
    @Bind(R.id.tv_integral_recordsofconsumption)
    TextView tvIntegralRecordsofconsumption;
    @Bind(R.id.tv_integral_integralrecord)
    TextView tvIntegralIntegralrecord;
    @Bind(R.id.vp_orderpager)
    ViewPager vpOrderpager;
    @Bind(R.id.act_memberrecord_iv_empty)
    ImageView actMemberrecordIvEmpty;
    private int page = 1;
    String position;
    private ArrayList<Fragment> appFragments;
    OrederFragment mWholeFragment;
    OrederFragment mPaySuccessFragment;
    OrederFragment mPayFailFragment;
    @OnClick({R.id.text_newcategory,R.id.tv_userecord})
    public void OnClick(View v) {
        switch (v.getId()) {

            case R.id.text_newcategory:
                vpOrderpager.setCurrentItem(0);
                break;
            case R.id.tv_integral_integralrecord:
                vpOrderpager.setCurrentItem(2);
                break;
            case R.id.tv_userecord:
                vpOrderpager.setCurrentItem(3);
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
        getIntegralRecord(true, 0);
    }

    private void initAdapter() {
    }


    /**
     * 获取消费记录
     */
    public void getIntegralRecord(final boolean isResh, final int type) {


    }

    /**
     * 初始化View
     */
    private void initView() {
        setPageTitle("会员详情");
        viewAdapter();
    }

    private void viewAdapter() {
        if (mWholeFragment == null) {
            mWholeFragment = new OrederFragment();
        }
        if (mPaySuccessFragment == null) {
            mPaySuccessFragment = new OrederFragment();
        }
        if (mPayFailFragment == null) {
            mPayFailFragment = new OrederFragment();
        }
        appFragments.add(mWholeFragment);
        appFragments.add(mPaySuccessFragment);
        appFragments.add(mPayFailFragment);
        appFragments = new ArrayList<Fragment>();
        vpOrderpager.setAdapter(new ViewPageAdapter(getSupportFragmentManager(), appFragments));
        vpOrderpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int arg0) {
                switch (arg0) {
                    case 0:
                    {
                    }
                    break;
                    case 1:
                    {
                    }
                    break;
                    case 2:
                    {
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

    @Override
    protected void onTitleLeftClick() {
        super.onTitleLeftClick();
        startActivity(new Intent(OrderContentActivity.this, MembersListActivity.class));
        finish();
    }
    class ViewPageAdapter extends FragmentPagerAdapter {
        private FragmentManager fManager;
        private ArrayList<Fragment> mlist;

        public ViewPageAdapter(FragmentManager fm, ArrayList<Fragment> list) {
            super(fm);
            fManager = fm;
            mlist = list;
        }

        @Override
        public Fragment getItem(int position) {
            return mlist.get(position);
        }

        @Override
        public int getCount() {
            return mlist.size();
        }

        @Override
        public Fragment instantiateItem(ViewGroup container, int position) {
            Fragment fragment = (Fragment) super.instantiateItem(container, position);
            fManager.beginTransaction().show(fragment).commit();
            return fragment;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            Fragment fragment = mlist.get(position);
            fManager.beginTransaction().hide(fragment).commit();
        }

    }
}