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
import android.widget.TextView;

import com.lianbi.mezone.b.bean.MemberInfoBean;
import com.lianbi.mezone.b.fragment.IntegralRecordFragment;
import com.xizhi.mezone.b.R;

import cn.com.hgh.view.PagerSlidingTabStrip;

public class IntegralRecordActivity extends BaseActivity {

	private TextView mTvIntegralMemberfile;
	private TextView mTvIntegralRecordsofconsumption;
	private TextView mTvIntegralIntegralrecord;
	private TextView mTvTotalintegral;
	private TextView mTvConsumptionintegral;
	private TextView mTvSurplusintegral;
	public static final int POSITION0 = 0;
	public static final int POSITION1 = 1;
	public static final int POSITION2 = 2;
	final String[] titles = { "全部记录", "获取记录", "使用记录"};
	private PagerSlidingTabStrip tabs;
	private ViewPager pager;
	IntegralRecordFragment allRecordFragment;
	IntegralRecordFragment obtainRecordFragment;
	IntegralRecordFragment useRecordFragment;
	public int curPosition = 0;
	private MemberInfoBean mMemberInfoBean;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_integralrecord, HAVETYPE);
		mMemberInfoBean = (MemberInfoBean) getIntent().getSerializableExtra("memberInfo");
		initView();
		setLisenter();
		getIntegralRecord(true);
	}

	private int page =0;

	/**
	 * 获取消费记录
	 */
	private void getIntegralRecord(final boolean isResh) {
		if(isResh){
			page = 0;
		}

		page++;
		

	}

	/**
	 * 初始化View
	 */
	private void initView() {
		setPageTitle("积分记录");
		mTvIntegralMemberfile = (TextView) findViewById(R.id.tv_integral_memberfile);
		mTvIntegralRecordsofconsumption = (TextView) findViewById(R.id.tv_integral_recordsofconsumption);
		mTvIntegralIntegralrecord = (TextView) findViewById(R.id.tv_integral_integralrecord);
		mTvIntegralIntegralrecord.setTextColor(IntegralRecordActivity.this.getResources().getColor(R.color.color_ff8208));
		findViewById(R.id.llt_line).setVisibility(View.VISIBLE);
		findViewById(R.id.view_line3).setVisibility(View.VISIBLE);
		mTvTotalintegral = (TextView) findViewById(R.id.tv_totalintegral);
		mTvConsumptionintegral = (TextView) findViewById(R.id.tv_consumptionintegral);
		mTvSurplusintegral = (TextView) findViewById(R.id.tv_surplusintegral);
		tabs = (PagerSlidingTabStrip) findViewById(R.id.ps_tabs_act_infodetails);
		pager = (ViewPager) findViewById(R.id.pager_act_integralrecord);
		pager.setAdapter(new MyAdapter(getSupportFragmentManager(), titles));
		tabs.setViewPager(pager);
	}


	String position;

	/**
	 * 添加监听
	 */
	private void setLisenter() {
		mTvIntegralMemberfile.setOnClickListener(this);
		mTvIntegralRecordsofconsumption.setOnClickListener(this);
		tabs.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			@Override
			public void onPageSelected(int arg0) {
				curPosition = arg0;
				position = String.valueOf(arg0);
				switch (arg0){
					case 0:
						break;
					case 1:
						break;
					case 2:
						break;
				}
			}

			@Override
			public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

			}

			@Override
			public void onPageScrollStateChanged(int state) {

			}
		});
	}

	@Override
	protected void onChildClick(View view) {
		super.onChildClick(view);
		switch (view.getId()){
			case R.id.tv_integral_memberfile://会员档案
				Intent file_intent = new Intent(IntegralRecordActivity.this,AddNewMembersActivity.class);
				file_intent.putExtra("isShow",true);
				file_intent.putExtra("memberInfo", mMemberInfoBean);
				startActivity(file_intent);
				break;
			case R.id.tv_integral_recordsofconsumption://消费记录
				Intent records_intent = new Intent(IntegralRecordActivity.this,RecordsOfConsumptionActivity.class);
				records_intent.putExtra("memberInfo", mMemberInfoBean);
				startActivity(records_intent);
				break;

		}
	}

	public class MyAdapter extends FragmentPagerAdapter{
		String[] _titles;
		public MyAdapter(FragmentManager fm,String[] titles){
			super(fm);
			_titles = titles;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			return _titles[position];
		}

		@Override
		public int getCount() {
			return _titles.length;
		}
		@Override
		public Fragment getItem(int position) {
			switch (position){
				case POSITION0:
					if (allRecordFragment == null) {
						allRecordFragment = new IntegralRecordFragment();
					}
					return allRecordFragment;
				case POSITION1:
					if (obtainRecordFragment == null) {
						obtainRecordFragment = new IntegralRecordFragment();
					}
					return obtainRecordFragment;
				case POSITION2:
					if (useRecordFragment == null) {
						useRecordFragment = new IntegralRecordFragment();
					}
					return useRecordFragment;
			}
			return null;
		}

	}

}