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
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.lianbi.mezone.b.bean.IntegralRecordBean;
import com.lianbi.mezone.b.bean.MemberInfoBean;
import com.xizhi.mezone.b.R;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Random;

import cn.com.hgh.baseadapter.BaseAdapterHelper;
import cn.com.hgh.baseadapter.QuickAdapter;
import cn.com.hgh.utils.AbPullHide;
import cn.com.hgh.utils.MathExtend;
import cn.com.hgh.utils.ScreenUtils;
import cn.com.hgh.view.AbPullToRefreshView;

public class IntegralRecordActivity extends BaseActivity {

	private TextView mTvIntegralMemberfile;
	private TextView mTvIntegralRecordsofconsumption;
	private TextView mTvIntegralIntegralrecord;
	private TextView mTvTotalintegral;
	private TextView mTvConsumptionintegral;
	private TextView mTvSurplusintegral;
	public int curPosition = 0;
	private MemberInfoBean mMemberInfoBean;
	public static final int POSITION0 = 0;
	public static final int POSITION1 = 1;
	public static final int POSITION2 = 2;
	private TextView mTvAllrecord;
	private TextView mTvAccessrecord;
	private TextView mTvUserecord;
	private ListView mActMemberrecordListview;
	private ImageView mActMemberrecordIvEmpty;
	private QuickAdapter<IntegralRecordBean> mAdapter;
	private ArrayList<IntegralRecordBean> mDatas = new ArrayList<>();
	private AbPullToRefreshView mActIntegralrecordAbpulltorefreshview;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_integralrecord, NOTYPE);
		mMemberInfoBean = (MemberInfoBean) getIntent().getSerializableExtra("memberInfo");
		System.out.println("memberInfo" + mMemberInfoBean.getVipPhone());
		initView();
		setLisenter();
		initAdapter();
		getIntegralRecord(true);
	}

	private void initAdapter() {
		System.out.println("arrayList=="+arrayList.size());
		mAdapter = new QuickAdapter<IntegralRecordBean>(this, R.layout.item_consumption, arrayList) {
			@Override
			protected void convert(BaseAdapterHelper helper, IntegralRecordBean item) {
				TextView tv_rc_time = helper.getView(R.id.tv_rc_time);
				TextView tv_rc_thing = helper.getView(R.id.tv_rc_thing);
				TextView tv_rc_where = helper.getView(R.id.tv_rc_where);
				TextView tv_rc_much = helper.getView(R.id.tv_rc_much);

				ScreenUtils.textAdaptationOn720(tv_rc_time, IntegralRecordActivity.this, 24);//消费时间
				ScreenUtils.textAdaptationOn720(tv_rc_thing, IntegralRecordActivity.this, 24);//消费内容
				ScreenUtils.textAdaptationOn720(tv_rc_where, IntegralRecordActivity.this, 24);//消费地点
				ScreenUtils.textAdaptationOn720(tv_rc_much, IntegralRecordActivity.this, 24);//消费金额

				tv_rc_time.setText(item.getRecordTime() + "");
				tv_rc_thing.setText(item.getRecordThing() + "");
				tv_rc_where.setText(item.getRecordWhrer() + "");
				tv_rc_much.setText(MathExtend.roundNew(item.getRecordInteger().divide(new BigDecimal(100)).doubleValue(), 2) + "");
			}
		};
		mActMemberrecordListview.setAdapter(mAdapter);
	}

	private int page = 0;

	ArrayList<IntegralRecordBean> arrayList = new ArrayList<>();
	ArrayList<IntegralRecordBean> arrayList0 = new ArrayList<>();
	ArrayList<IntegralRecordBean> arrayList1 = new ArrayList<>();

	/**
	 * 获取消费记录
	 */
	public void getIntegralRecord(final boolean isResh) {
		ArrayList<IntegralRecordBean> mDatasL = new ArrayList<>();

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
		if(mDatasL != null && mDatasL.size() > 0) {
			arrayList.addAll(mDatasL);
		}
		if (arrayList != null && arrayList.size() > 0) {
			mActMemberrecordIvEmpty
					.setVisibility(View.GONE);
			mActIntegralrecordAbpulltorefreshview
					.setVisibility(View.VISIBLE);
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
		}else{
			mActMemberrecordIvEmpty
					.setVisibility(View.VISIBLE);
			mActIntegralrecordAbpulltorefreshview
					.setVisibility(View.GONE);
		}
		AbPullHide.hideRefreshView(isResh,
				mActIntegralrecordAbpulltorefreshview);
		switch (curPosition){
			case POSITION0:
				mAdapter.replaceAll(arrayList);
				break;
			case POSITION1:
				mAdapter.replaceAll(arrayList0);
				break;
			case POSITION2:
				mAdapter.replaceAll(arrayList1);
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
		mActIntegralrecordAbpulltorefreshview = (AbPullToRefreshView) findViewById(R.id.act_integralrecord_abpulltorefreshview);
		mTvSurplusintegral = (TextView) findViewById(R.id.tv_surplusintegral);
		mTvAllrecord = (TextView) findViewById(R.id.tv_allrecord);
		mTvAccessrecord = (TextView) findViewById(R.id.tv_accessrecord);
		mTvUserecord = (TextView) findViewById(R.id.tv_userecord);
		mActMemberrecordListview = (ListView) findViewById(R.id.act_memberrecord_listview);
		mActMemberrecordIvEmpty = (ImageView) findViewById(R.id.act_memberrecord_iv_empty);
		viewAdapter();
	}

	private void viewAdapter() {
		ArrayList<TextView> tvs25 = new ArrayList<>();
		tvs25.add(mTvIntegralIntegralrecord);
		tvs25.add(mTvIntegralMemberfile);
		tvs25.add(mTvIntegralRecordsofconsumption);
		tvs25.add(mTvTotalintegral);
		tvs25.add(mTvConsumptionintegral);
		tvs25.add(mTvSurplusintegral);
		ScreenUtils.textAdaptationOn720(tvs25, this, 25);

	}

	String position;

	/**
	 * 添加监听
	 */
	private void setLisenter() {
		mActIntegralrecordAbpulltorefreshview.setLoadMoreEnable(true);
		mActIntegralrecordAbpulltorefreshview.setPullRefreshEnable(true);
		mActIntegralrecordAbpulltorefreshview
				.setOnHeaderRefreshListener(new AbPullToRefreshView.OnHeaderRefreshListener() {

					@Override
					public void onHeaderRefresh(AbPullToRefreshView view) {
						getIntegralRecord(true);
					}
				});
		mActIntegralrecordAbpulltorefreshview
				.setOnFooterLoadListener(new AbPullToRefreshView.OnFooterLoadListener() {

					@Override
					public void onFooterLoad(AbPullToRefreshView view) {
						getIntegralRecord(false);
					}
				});
		mTvIntegralMemberfile.setOnClickListener(this);
		mTvIntegralRecordsofconsumption.setOnClickListener(this);
		mTvAllrecord.setOnClickListener(this);
		mTvAccessrecord.setOnClickListener(this);
		mTvUserecord.setOnClickListener(this);
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
			case R.id.tv_allrecord://全部记录
				curPosition = POSITION0;
				mTvAllrecord.setBackgroundResource(R.drawable.member_record_pressed);
				mTvAccessrecord.setBackgroundResource(R.drawable.member_record);
				mTvUserecord.setBackgroundResource(R.drawable.member_record);
				mAdapter.replaceAll(arrayList);
				break;
			case R.id.tv_accessrecord://获取记录
				curPosition = POSITION1;
				mTvAllrecord.setBackgroundResource(R.drawable.member_record);
				mTvAccessrecord.setBackgroundResource(R.drawable.member_record_pressed);
				mTvUserecord.setBackgroundResource(R.drawable.member_record);
				mAdapter.replaceAll(arrayList0);
				break;
			case R.id.tv_userecord://使用记录
				curPosition = POSITION2;
				mTvAllrecord.setBackgroundResource(R.drawable.member_record);
				mTvAccessrecord.setBackgroundResource(R.drawable.member_record);
				mTvUserecord.setBackgroundResource(R.drawable.member_record_pressed);
				mAdapter.replaceAll(arrayList1);
				break;
		}
	}

	@Override
	protected void onTitleLeftClick() {
		super.onTitleLeftClick();
		startActivity(new Intent(IntegralRecordActivity.this, MembersListActivity.class));
		finish();
	}

}