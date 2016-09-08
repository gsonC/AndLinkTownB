package com.lianbi.mezone.b.ui;
/*
 * @创建者     Administrator
 * @创建时间   2016/8/11 15:14
 * @描述       消费记录
 *
 * @更新者     $Author$ 
 * @更新时间   $Date$
 * @更新描述   ${TODO}
 */

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.lianbi.mezone.b.bean.MemberConsumptionBean;
import com.lianbi.mezone.b.bean.MemberInfoBean;
import com.lianbi.mezone.b.httpresponse.MyResultCallback;
import com.xizhi.mezone.b.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.ArrayList;

import cn.com.hgh.baseadapter.BaseAdapterHelper;
import cn.com.hgh.baseadapter.QuickAdapter;
import cn.com.hgh.utils.AbDateUtil;
import cn.com.hgh.utils.AbPullHide;
import cn.com.hgh.utils.AbStrUtil;
import cn.com.hgh.utils.MathExtend;
import cn.com.hgh.utils.Result;
import cn.com.hgh.utils.ScreenUtils;
import cn.com.hgh.view.AbPullToRefreshView;

public class RecordsOfConsumptionActivity extends BaseActivity {

	private TextView mTvRecordMemberfile,mTvRecordRecordsofconsumption,mTvRecordIntegralrecord
			,mTvOrdernum,mTvOrdermuch;
	private AbPullToRefreshView mActRecordAbpulltorefreshview;
	private ListView mActRecordListview;
	private ImageView mImgRecordEmpty;
	private MemberInfoBean mMemberInfoBean;
	private int page = 1;
	private ArrayList<MemberConsumptionBean> mDatas = new ArrayList<MemberConsumptionBean>();
	private QuickAdapter<MemberConsumptionBean> mAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_recordofconsumption, NOTYPE);
		mMemberInfoBean = (MemberInfoBean) getIntent().getSerializableExtra("memberInfo");
		initView();
		setLisenter();
		initAdapter();
		getRecordsOfConsumption(true);
	}

	private void initAdapter() {
		mAdapter = new QuickAdapter<MemberConsumptionBean>(this, R.layout.item_consumption, mDatas) {
			@Override
			protected void convert(BaseAdapterHelper helper, MemberConsumptionBean item) {
				TextView tv_rc_time = helper.getView(R.id.tv_rc_time);
				TextView tv_rc_thing = helper.getView(R.id.tv_rc_thing);
				TextView tv_rc_where = helper.getView(R.id.tv_rc_where);
				TextView tv_rc_much = helper.getView(R.id.tv_rc_much);

				ScreenUtils.textAdaptationOn720(tv_rc_time, RecordsOfConsumptionActivity.this, 24);//消费时间
				ScreenUtils.textAdaptationOn720(tv_rc_thing, RecordsOfConsumptionActivity.this, 24);//消费内容
				ScreenUtils.textAdaptationOn720(tv_rc_where, RecordsOfConsumptionActivity.this, 24);//消费来源
				ScreenUtils.textAdaptationOn720(tv_rc_much, RecordsOfConsumptionActivity.this, 24);//消费金额

				tv_rc_time.setText(item.getCreateTime() + "");
				tv_rc_thing.setText(item.getConsumName() + "");
				if(!AbStrUtil.isEmpty(item.getConsumSource())){
					tv_rc_where.setText(item.getConsumSource() + "");
				}else{
					tv_rc_where.setText("无");
				}
				tv_rc_much.setText(MathExtend.roundNew(item.getConsumAmount().divide(new BigDecimal(100)).doubleValue(), 2) + "元");

			}
		};
		mActRecordListview.setAdapter(mAdapter);
	}

	/**
	 * 初始化View
	 */
	private void initView() {
		setPageTitle("会员详情");
		mTvRecordMemberfile = (TextView) findViewById(R.id.tv_record_memberfile);//会员档案
		mTvRecordRecordsofconsumption = (TextView) findViewById(R.id.tv_record_recordsofconsumption);//消费记录
		mTvRecordRecordsofconsumption.setTextColor(RecordsOfConsumptionActivity.this.getResources().getColor(R.color.color_ff8208));
		findViewById(R.id.llt_line).setVisibility(View.VISIBLE);
		findViewById(R.id.view_line2).setVisibility(View.VISIBLE);
		mTvRecordIntegralrecord = (TextView) findViewById(R.id.tv_record_integralrecord);//积分记录
		mTvOrdernum = (TextView) findViewById(R.id.tv_ordernum);//累计下单次数
		mTvOrdermuch = (TextView) findViewById(R.id.tv_ordermuch);//累计消费金额
		mActRecordAbpulltorefreshview = (AbPullToRefreshView) findViewById(R.id.act_record_abpulltorefreshview);
		mActRecordListview = (ListView) findViewById(R.id.act_record_listview);
		mImgRecordEmpty = (ImageView) findViewById(R.id.img_record_empty);
		viewAdapter();
	}

	/**
	 * 文字适配
	 */
	private void viewAdapter() {
		ArrayList<TextView> tvs25 = new ArrayList<>();
		tvs25.add((TextView) findViewById(R.id.tv_record_recordsofconsumption));
		tvs25.add(mTvRecordMemberfile);
		tvs25.add(mTvRecordRecordsofconsumption);
		tvs25.add(mTvOrdernum);
		tvs25.add(mTvOrdermuch);
		ScreenUtils.textAdaptationOn720(tvs25, this, 25);
	}


	/**
	 * 添加监听
	 */
	private void setLisenter() {
		mTvRecordMemberfile.setOnClickListener(this);
		mTvRecordRecordsofconsumption.setOnClickListener(this);
		mTvRecordIntegralrecord.setOnClickListener(this);
		mActRecordAbpulltorefreshview.setLoadMoreEnable(true);
		mActRecordAbpulltorefreshview.setPullRefreshEnable(true);
		mActRecordAbpulltorefreshview
				.setOnHeaderRefreshListener(new AbPullToRefreshView.OnHeaderRefreshListener() {

					@Override
					public void onHeaderRefresh(AbPullToRefreshView view) {
						getRecordsOfConsumption(true);
					}
				});
		mActRecordAbpulltorefreshview
				.setOnFooterLoadListener(new AbPullToRefreshView.OnFooterLoadListener() {

					@Override
					public void onFooterLoad(AbPullToRefreshView view) {
						getRecordsOfConsumption(false);
					}
				});
		mActRecordListview
				.setOnItemClickListener(new AdapterView.OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
											int position, long id) {

					}
				});
	}

	@Override
	protected void onChildClick(View view) {
		super.onChildClick(view);
		switch (view.getId()) {
			case R.id.tv_record_memberfile://会员档案
				Intent file_intent = new Intent(RecordsOfConsumptionActivity.this, AddNewMembersActivity.class);
				file_intent.putExtra("memberInfo", mMemberInfoBean);
				file_intent.putExtra("isShow", true);
				startActivity(file_intent);
				break;
			case R.id.tv_record_integralrecord://积分记录
				Intent integral_intent = new Intent(RecordsOfConsumptionActivity.this, IntegralRecordActivity.class);
				integral_intent.putExtra("memberInfo", mMemberInfoBean);
				startActivity(integral_intent);
				break;
		}
	}

	private void getRecordsOfConsumption(final boolean isResh) {
		if (isResh) {
			page = 1;
			mDatas.clear();
			mAdapter.replaceAll(mDatas);
		}
		String reqTime = AbDateUtil.getDateTimeNow();
		String uuid = AbStrUtil.getUUID();

		try {

			okHttpsImp.getRecordsOfConsumptionByID(uuid, "app", reqTime, userShopInfoBean.getBusinessId(),
					mMemberInfoBean.getVipId(), page + "", 20 + "", new MyResultCallback<String>() {
						@Override
						public void onResponseResult(Result result) {
							page++;
							String reString = result.getData();
							if (!AbStrUtil.isEmpty(reString)) {
								try {
									JSONObject jsonObject = new JSONObject(reString);
									int orderCount = jsonObject.getInt("orderCount");
									BigDecimal consumAmount = new BigDecimal(jsonObject.getInt("consumAmount"));
									mTvOrdernum.setText("累计下单次数  " + orderCount + "次");
									if(!consumAmount.equals(BigDecimal.ZERO)){
										mTvOrdermuch.setText("累计消费金额  " + MathExtend.roundNew(
												consumAmount.divide(new BigDecimal(100)).doubleValue(), 2) + "元");
									}else{
										mTvOrdermuch.setText("累计消费金额  0元");
									}
									reString = jsonObject.getString("consumList");
									ArrayList<MemberConsumptionBean> mDatasL = (ArrayList<MemberConsumptionBean>)
											JSON.parseArray(reString, MemberConsumptionBean.class);
									if (mDatasL != null && mDatasL.size() > 0) {
										mDatas.addAll(mDatasL);
									}
									if (mDatas != null && mDatas.size() > 0) {
										mImgRecordEmpty.setVisibility(View.GONE);
										mActRecordAbpulltorefreshview.setVisibility(View.VISIBLE);
									} else {
										mImgRecordEmpty.setVisibility(View.VISIBLE);
										mActRecordAbpulltorefreshview.setVisibility(View.GONE);
									}
									AbPullHide.hideRefreshView(isResh, mActRecordAbpulltorefreshview);
									mAdapter.replaceAll(mDatas);
								} catch (JSONException e) {
									e.printStackTrace();
								}
							}
						}

						@Override
						public void onResponseFailed(String msg) {
							if (isResh) {
								mImgRecordEmpty
										.setVisibility(View.VISIBLE);
								mActRecordAbpulltorefreshview
										.setVisibility(View.GONE);
							}
							AbPullHide.hideRefreshView(isResh,
									mActRecordAbpulltorefreshview);
						}
					});
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	protected void onTitleLeftClick() {
		super.onTitleLeftClick();
		startActivity(new Intent(RecordsOfConsumptionActivity.this, MembersListActivity.class));
		finish();
	}
}