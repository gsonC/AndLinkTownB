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
import android.widget.ImageView;
import android.widget.TextView;

import com.lianbi.mezone.b.fragment.OrederFragment;
import com.xizhi.mezone.b.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.hgh.timeselector.TimeSelectorE;
import cn.com.hgh.utils.AbDateUtil;
import cn.com.hgh.utils.AbStrUtil;
import cn.com.hgh.utils.ContentUtils;
import cn.com.hgh.view.NoScrollViewPager;

public class OrderContentActivity extends BaseActivity implements
        ViewPager.OnPageChangeListener {

    public static final int POSITION0 = 0;
    public static final int POSITION1 = 1;
    public static final int POSITION2 = 2;

    OrederFragment mWholeFragment;
    OrederFragment mPaySuccessFragment;
    OrederFragment mPayFailFragment;
    @Bind(R.id.tv_starttime)
    TextView tvStarttime;
    @Bind(R.id.tv_finishtime)
    TextView tvFinishtime;
    @Bind(R.id.iv_close)
    ImageView ivClose;
    @Bind(R.id.tv_today)
    TextView tvToday;
    @Bind(R.id.tv_threeday)
    TextView tvThreeday;
    @Bind(R.id.tv_onemonth)
    TextView tvOnemonth;
    @Bind(R.id.tv_all)
    TextView tvAll;
    @Bind(R.id.tv_success)
    TextView tvSuccess;
    @Bind(R.id.tv_fail)
    TextView tvFail;
    @Bind(R.id.vp_orderpager)
    NoScrollViewPager vpOrderpager;
    @Bind(R.id.img_empty)
    ImageView imgEmpty;
    /**
     * 当前位置
     */
    public int curPosition;
    private final String ENDTIME = "2030-01-01 00:00";
    private String coupName;
    private String limitAmt;
    private String coupAmt;
    private String beginTime;
    private String endTime;
    @OnClick({R.id.tv_all, R.id.tv_success, R.id.tv_fail})
    public void OnClick(View v) {
        switch (v.getId()) {

            case R.id.tv_all:
                vpOrderpager.setCurrentItem(0);
                break;
            case R.id.tv_success:
                vpOrderpager.setCurrentItem(1);
                break;
            case R.id.tv_fail:
                vpOrderpager.setCurrentItem(2);
                break;
        }
    }
    @OnClick({R.id.tv_today, R.id.tv_threeday, R.id.tv_onemonth})
    public void OnDate(View v) {
        switch (v.getId()) {

            case R.id.tv_today:
                vpOrderpager.setCurrentItem(0);
                break;
            case R.id.tv_threeday:
                vpOrderpager.setCurrentItem(1);
                break;
            case R.id.tv_onemonth:
                vpOrderpager.setCurrentItem(2);
                break;
        }
    }
    @OnClick({R.id.tv_starttime, R.id.tv_finishtime,R.id.iv_close})
    public void OnTime(View v) {
        switch (v.getId()) {

            case R.id.tv_starttime:
                TimeSelectorE timeSelectorFrom = new TimeSelectorE(OrderContentActivity.this,
                        new TimeSelectorE.ResultHandler() {
                            @Override
                            public void handle(String time) {
                                if (!AbStrUtil.isEmpty(endTime) && !AbDateUtil.compareTime(time, endTime)) {
                                    ContentUtils.showMsg(OrderContentActivity.this, "结束日期须在开始日期之后！");
                                    tvStarttime.setText("");
                                    beginTime = "";
                                } else {
                                    tvStarttime.setText(time);
                                    beginTime = time;
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
                                    tvStarttime.setText("");
                                    endTime = "";
                                } else {
                                    tvFinishtime.setText(time);
                                    endTime = time;
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
        vpOrderpager.setAdapter(new MyAdapter(getSupportFragmentManager()));
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

    @Override
    protected void onTitleLeftClick() {
        super.onTitleLeftClick();
        startActivity(new Intent(OrderContentActivity.this, MembersListActivity.class));
        finish();
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

                }
                break;

            case POSITION1:
                if (mPaySuccessFragment != null) {

                }
                break;
            case POSITION2:
                if (mPayFailFragment != null) {

                }
                break;
        }
    }

}