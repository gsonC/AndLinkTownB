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
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.lianbi.mezone.b.bean.IntegralRecordBean;
import com.lianbi.mezone.b.bean.MemberInfoBean;
import com.lianbi.mezone.b.httpresponse.MyResultCallback;
import com.xizhi.mezone.b.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cn.com.hgh.baseadapter.BaseAdapterHelper;
import cn.com.hgh.baseadapter.QuickAdapter;
import cn.com.hgh.utils.AbDateUtil;
import cn.com.hgh.utils.AbPullHide;
import cn.com.hgh.utils.AbStrUtil;
import cn.com.hgh.utils.Result;
import cn.com.hgh.utils.ScreenUtils;
import cn.com.hgh.view.AbPullToRefreshView;

public class IntegralRecordActivity extends BaseActivity {

	private TextView mTvIntegralMemberfile,mTvIntegralRecordsofconsumption,mTvIntegralIntegralrecord
			,mTvTotalintegral,mTvConsumptionintegral,mTvSurplusintegral,mTvAllrecord,mTvAccessrecord
			,mTvUserecord;
	public int curPosition = 0;
	private MemberInfoBean mMemberInfoBean;
	public static final int POSITION0 = 0;
	public static final int POSITION1 = 1;
	public static final int POSITION2 = 2;
	private ListView mActMemberrecordListview;
	private ImageView mActMemberrecordIvEmpty;
	private QuickAdapter<IntegralRecordBean> mAdapter;
	private ArrayList<IntegralRecordBean> mDatas = new ArrayList<>();
	private AbPullToRefreshView mActIntegralrecordAbpulltorefreshview;
	ArrayList<IntegralRecordBean> arrayList = new ArrayList<>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_integralrecord, NOTYPE);
		mMemberInfoBean = (MemberInfoBean) getIntent().getSerializableExtra("memberInfo");
		initView();
		setLisenter();
		initAdapter();
		getIntegralRecord(true,0);
	}

	private void initAdapter() {
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
				tv_rc_thing.setText(item.getConsumName().replace("[","").replace("{","").replace("}","").replace("]","").replace("\"","").replace(":","*"));
				tv_rc_where.setText(item.getConsumSorce());
				tv_rc_much.setText(item.getConsumAmount());
			}
		};
		mActMemberrecordListview.setAdapter(mAdapter);
	}

	private int page = 1;

	/**
	 * 获取消费记录
	 */
	public void getIntegralRecord(final boolean isResh,final int type) {

		if (isResh) {
			page = 1;
			mDatas.clear();
			mAdapter.replaceAll(mDatas);
		}

		String reqTime = AbDateUtil.getDateTimeNow();
		String uuid = AbStrUtil.getUUID();
		try {
			okHttpsImp.getIntegralRecordByID(uuid, "app", reqTime,userShopInfoBean.getBusinessId()
					,mMemberInfoBean.getVipId()
					, type, page + "", 20 + "",
					new MyResultCallback<String>() {
						@Override
						public void onResponseResult(Result result) {
							page++;
							String reString = result.getData();
							if (!TextUtils.isEmpty(reString)) {
								try {
									JSONObject jsonObject = new JSONObject(reString);
									int totalIntegral = jsonObject.getInt("totalIntegral");
									int totalConsumption = jsonObject.getInt("totalConsumption");
									int residualIntegral = jsonObject.getInt("residualIntegral");
									mTvTotalintegral.setText("获取总积分 "+totalIntegral);
									mTvConsumptionintegral.setText("总消耗积分 "+totalConsumption);
									mTvSurplusintegral.setText("剩余积分 "+residualIntegral);
									reString = jsonObject.getString("integralList");
									ArrayList<IntegralRecordBean> mDatasL = (ArrayList<IntegralRecordBean>) JSON
											.parseArray(reString,IntegralRecordBean.class);
									if (mDatasL != null && mDatasL.size() > 0) {
										mDatas.addAll(mDatasL);
									}
									if (mDatas != null && mDatas.size() > 0) {
										mActMemberrecordIvEmpty
												.setVisibility(View.GONE);
										mActIntegralrecordAbpulltorefreshview
												.setVisibility(View.VISIBLE);
									} else {
										mActMemberrecordIvEmpty
												.setVisibility(View.VISIBLE);
										mActIntegralrecordAbpulltorefreshview
												.setVisibility(View.GONE);
									}
									AbPullHide.hideRefreshView(isResh,
											mActIntegralrecordAbpulltorefreshview);
									mAdapter.replaceAll(mDatas);
								} catch (JSONException e) {
									e.printStackTrace();
								}

							}
						}

						@Override
						public void onResponseFailed(String msg) {
							if (isResh) {
								mActMemberrecordIvEmpty
										.setVisibility(View.VISIBLE);
								mActIntegralrecordAbpulltorefreshview
										.setVisibility(View.GONE);
							}
							AbPullHide.hideRefreshView(isResh,
									mActIntegralrecordAbpulltorefreshview);
						}
					});
		} catch (Exception e) {
			e.printStackTrace();
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
		mTvTotalintegral = (TextView) findViewById(R.id.tv_totalintegral);//总积分
		mTvConsumptionintegral = (TextView) findViewById(R.id.tv_consumptionintegral);//总消耗积分
		mActIntegralrecordAbpulltorefreshview = (AbPullToRefreshView) findViewById(R.id.act_integralrecord_abpulltorefreshview);
		mTvSurplusintegral = (TextView) findViewById(R.id.tv_surplusintegral);//剩余积分
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
						getIntegralRecord(true,curPosition);
					}
				});
		mActIntegralrecordAbpulltorefreshview
				.setOnFooterLoadListener(new AbPullToRefreshView.OnFooterLoadListener() {

					@Override
					public void onFooterLoad(AbPullToRefreshView view) {
						getIntegralRecord(false,curPosition);
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
				getIntegralRecord(true,curPosition);
				break;
			case R.id.tv_accessrecord://获取记录
				curPosition = POSITION1;
				mTvAllrecord.setBackgroundResource(R.drawable.member_record);
				mTvAccessrecord.setBackgroundResource(R.drawable.member_record_pressed);
				mTvUserecord.setBackgroundResource(R.drawable.member_record);
				getIntegralRecord(true,curPosition);
				break;
			case R.id.tv_userecord://使用记录
				curPosition = POSITION2;
				mTvAllrecord.setBackgroundResource(R.drawable.member_record);
				mTvAccessrecord.setBackgroundResource(R.drawable.member_record);
				mTvUserecord.setBackgroundResource(R.drawable.member_record_pressed);
				getIntegralRecord(true,curPosition);
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