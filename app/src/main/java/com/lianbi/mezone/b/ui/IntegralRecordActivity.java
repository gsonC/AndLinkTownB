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
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.lianbi.mezone.b.bean.IntegralRecordBean;
import com.lianbi.mezone.b.bean.MemberInfoBean;
import com.lianbi.mezone.b.fragment.IntegralRecordFragment;
import com.xizhi.mezone.b.R;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Random;

import cn.com.hgh.view.PagerSlidingTabStrip;

public class IntegralRecordActivity extends BaseActivity {

	private TextView mTvIntegralMemberfile;
	private TextView mTvIntegralRecordsofconsumption;
	private TextView mTvIntegralIntegralrecord;
	private TextView mTvTotalintegral;
	private TextView mTvConsumptionintegral;
	private TextView mTvSurplusintegral;
	final String[] titles = {"全部记录", "获取记录", "使用记录"};
	private PagerSlidingTabStrip tabs;
	private ViewPager pager;
	IntegralRecordFragment allRecordFragment;
	IntegralRecordFragment obtainRecordFragment;
	IntegralRecordFragment useRecordFragment;
	public int curPosition = 0;
	private MemberInfoBean mMemberInfoBean;
	public static final int POSITION0 = 0;
	public static final int POSITION1 = 1;
	public static final int POSITION2 = 2;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_integralrecord, NOTYPE);
		mMemberInfoBean = (MemberInfoBean) getIntent().getSerializableExtra("memberInfo");
		System.out.println("memberInfo" + mMemberInfoBean.getMemberPhone());
		initView();
		setLisenter();
		getIntegralRecord(true);
	}

	private int page = 0;

	ArrayList<IntegralRecordBean> arrayList = new ArrayList<IntegralRecordBean>();
	ArrayList<IntegralRecordBean> arrayList0 = new ArrayList<IntegralRecordBean>();
	ArrayList<IntegralRecordBean> arrayList1 = new ArrayList<IntegralRecordBean>();

	/**
	 * 获取消费记录
	 */
	public void getIntegralRecord(final boolean isResh) {

		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				ArrayList<IntegralRecordBean> mDatasL = new ArrayList<IntegralRecordBean>();
				if (isResh) {
					page = 0;
				}
				page++;

				for (int i = 0; i < 50; i++) {
					IntegralRecordBean bean = new IntegralRecordBean();
					bean.setRecordTime("2016-08-12 12:1" + i);
					bean.setRecordThing("饮料*" + i);
					bean.setRecordWhrer("微店");
					bean.setRecordInteger(new BigDecimal(1000 + i));
					bean.setType(new Random().nextInt(2));
					mDatasL.add(bean);
				}

				if (isResh) {
					arrayList.clear();
					arrayList0.clear();
					arrayList1.clear();
				}
				arrayList.addAll(mDatasL);
				if (arrayList.size() > 0) {
					for (IntegralRecordBean ir : arrayList) {
						switch (ir.getType()) {
							case POSITION0:
								arrayList0.add(ir);
								break;
							case POSITION1:
								arrayList1.add(ir);
								break;
						}
					}
				}

				swtFmDo(POSITION0,false,arrayList);
				swtFmDo(POSITION1,false,arrayList0);
				swtFmDo(POSITION2,false,arrayList1);

				swtFmHide(curPosition, isResh);
			}
		},80);


	}

	/**
	 * fm隐藏
	 */
	private void swtFmHide(int arg0, boolean isD) {
		switch (arg0){
			case POSITION0:
				if(allRecordFragment!=null){
					allRecordFragment.hidle(isD);
				}
				break;
			case POSITION1:
				if(obtainRecordFragment!=null){
					obtainRecordFragment.hidle(isD);
				}
				break;
			case POSITION2:
				if(useRecordFragment!=null){
					useRecordFragment.hidle(isD);
				}
				break;
		}
	}

	/**
	 * fm做一些事
	 */
	private void swtFmDo(int arg0, boolean reserve, ArrayList<IntegralRecordBean> cuArrayList) {
		switch (arg0){
			case POSITION0:
				if(allRecordFragment !=null){
					System.out.println("cuArrayList++"+cuArrayList.size());
					allRecordFragment.doSomething(reserve,cuArrayList);
				}
				break;
			case POSITION1:
				if(obtainRecordFragment!=null){
					System.out.println("cuArrayList++"+cuArrayList.size());
					obtainRecordFragment.doSomething(reserve,cuArrayList);
				}
				break;
			case POSITION2:
				if(useRecordFragment!=null){
					System.out.println("cuArrayList++"+cuArrayList.size());
					useRecordFragment.doSomething(reserve,cuArrayList);
				}
				break;
		}
	}

	/**
	 * 初始化View
	 */
	private void initView() {
		setPageTitle("会员详情");
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
				switch (arg0) {
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
		switch (view.getId()) {
			case R.id.tv_integral_memberfile://会员档案
				Intent file_intent = new Intent(IntegralRecordActivity.this, AddNewMembersActivity.class);
				file_intent.putExtra("isShow", true);
				file_intent.putExtra("memberInfo", mMemberInfoBean);
				startActivity(file_intent);
				break;
			case R.id.tv_integral_recordsofconsumption://消费记录
				Intent records_intent = new Intent(IntegralRecordActivity.this, RecordsOfConsumptionActivity.class);
				records_intent.putExtra("memberInfo", mMemberInfoBean);
				startActivity(records_intent);
				break;

		}
	}

	public class MyAdapter extends FragmentPagerAdapter {
		String[] _titles;

		public MyAdapter(FragmentManager fm, String[] titles) {
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
			switch (position) {
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

	@Override
	protected void onTitleLeftClick() {
		super.onTitleLeftClick();
		startActivity(new Intent(IntegralRecordActivity.this,MembersListActivity.class));
		finish();
	}
}