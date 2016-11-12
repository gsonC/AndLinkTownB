package com.lianbi.mezone.b.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.lianbi.mezone.b.bean.ComeService;
import com.lianbi.mezone.b.httpresponse.MyResultCallback;
import com.xizhi.mezone.b.R;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.hgh.baseadapter.BaseAdapterHelper;
import cn.com.hgh.baseadapter.QuickAdapter;
import cn.com.hgh.eventbus.ShouyeRefreshEvent;
import cn.com.hgh.timeselector.TimeSelectorA;
import cn.com.hgh.utils.AbDateUtil;
import cn.com.hgh.utils.AbStrUtil;
import cn.com.hgh.utils.ContentUtils;
import cn.com.hgh.utils.Result;
import cn.com.hgh.view.AbPullToRefreshView;

/**
 * 到店明细
 */
public class ComeDetailActivity extends BaseActivity implements AbPullToRefreshView.OnFooterLoadListener, AbPullToRefreshView.OnHeaderRefreshListener {


	public static final int POSITION0 = 0;
	public static final int POSITION1 = 1;
	public static final int POSITION2 = 2;

	@Bind(R.id.back)
	ImageView back;
	@Bind(R.id.tv_amount)
	TextView tvAmount;
	@Bind(R.id.tv_unit)
	TextView tvUnit;
	@Bind(R.id.act_comedeatil_listview)
	ListView actComedeatilListview;
	@Bind(R.id.act_comedeatil_abpulltorefreshview)
	AbPullToRefreshView actComedeatilAbpulltorefreshview;

	private ArrayList<ComeService> mWholeData = new ArrayList<ComeService>();
	private ArrayList<ComeService> mPaySuccessDatas = new ArrayList<ComeService>();
	private ArrayList<ComeService> mPayFailDatas = new ArrayList<ComeService>();
	private ArrayList<ComeService> mDatas = new ArrayList<ComeService>();
	/**
	 * 正在下拉刷新.
	 */
	private boolean mPullRefreshing = false;
	/**
	 * 正在加载更多.
	 */
	private boolean mPullLoading = false;
	/**
	 * 当前位置
	 */
	public int curPosition;
	private final String STARTTIME = "2012-01-01 00:00";
	private final String ENDTIME = "2030-01-01 00:00";
	@Bind(R.id.tv_weekday)
	CheckBox tvWeekday;
	@Bind(R.id.tv_ji)
	CheckBox tvJi;
	@Bind(R.id.tv_year)
	CheckBox tvYear;
	@Bind(R.id.tv_addupto)
	TextView tvAddupto;
	@Bind(R.id.lay_bottom)
	LinearLayout layBottom;
	@Bind(R.id.tv_starttime)
	TextView tvStarttime;
	@Bind(R.id.tv_finishtime)
	TextView tvFinishtime;
	@Bind(R.id.iv_close)
	ImageView ivClose;
	@Bind(R.id.tv_today)
	CheckBox tvToday;
	@Bind(R.id.tv_onemonth)
	CheckBox tvOnemonth;
	@Bind(R.id.tv_all)
	CheckBox tvAll;
	@Bind(R.id.tv_success)
	CheckBox tvSuccess;
	@Bind(R.id.tv_fail)
	CheckBox tvFail;
	@Bind(R.id.img_empty)
	ImageView imgEmpty;

	@Bind(R.id.tv_num)
	TextView tv_num;
	@Bind(R.id.tv_rmb)
	TextView tv_rmb;
	private String coupName;
	private String limitAmt;
	private String coupAmt;
	private String isValid = "Y";
	private int intentLayout = 0;
	private String txnTime = "";
	private String beginTime = "";
	private int pageNo = 0;
	private String pageSize = "15";
	private String orderNo = "";
	private int listPosition = -1;
	private String orderStatus = "";
	private String endTime = "";

	@OnClick({R.id.tv_all, R.id.tv_success, R.id.tv_fail, R.id.back})
	public void OnClick(View v) {
		switch (v.getId()) {
			case R.id.back:
				onTitleLeftClick();
				break;
			case R.id.tv_all:

				tvAll.setChecked(true);
				tvSuccess.setChecked(false);
				tvFail.setChecked(false);
				initSearch( "", txnTime, beginTime, endTime);
				this.intentLayout = POSITION0;
				if (timeNoselected()) {
					ContentUtils.showMsg(ComeDetailActivity.this, "请选择查询时间");
					clearUpdate();
					return;
				} else {

				}
				switchAdapter();
				getOrder(true, false, "");

				break;
			case R.id.tv_success:

				tvAll.setChecked(false);
				tvSuccess.setChecked(true);
				tvFail.setChecked(false);
				initSearch( "1", txnTime, beginTime, endTime);
				this.intentLayout = POSITION1;
				if (timeNoselected()) {
					ContentUtils.showMsg(ComeDetailActivity.this, "请选择查询时间");
					clearUpdate();
					return;
				} else {

				}

				switchAdapter();
				getOrder(true, false, "1");
				break;
			case R.id.tv_fail:

				tvAll.setChecked(false);
				tvSuccess.setChecked(false);
				tvFail.setChecked(true);
				initSearch( "91", txnTime, beginTime, endTime);
				this.intentLayout = POSITION2;
				if (timeNoselected()) {
					ContentUtils.showMsg(ComeDetailActivity.this, "请选择查询时间");
					clearUpdate();
					return;
				} else {

				}
				switchAdapter();
				getOrder(true, false, "91");
				break;
		}
	}

	@OnClick({R.id.tv_today, R.id.tv_weekday, R.id.tv_onemonth, R.id.tv_ji, R.id.tv_year})
	public void OnDate(View v) {
		switch (v.getId()) {

			case R.id.tv_today:
				tvStarttime.setText("");
				tvFinishtime.setText("");
				tvToday.setChecked(true);
				tvWeekday.setChecked(false);
				tvOnemonth.setChecked(false);
				tvJi.setChecked(false);
				tvYear.setChecked(false);
				String txnTime = AbDateUtil.getDateYearMonthDayNowPlus("yyyyMMdd");
				initSearch( "", txnTime, "", "");
				getOrder(true, false, "00");
				break;
			case R.id.tv_weekday:
				tvStarttime.setText("");
				tvFinishtime.setText("");
				tvToday.setChecked(false);
				tvWeekday.setChecked(true);
				tvOnemonth.setChecked(false);
				tvJi.setChecked(false);
				tvYear.setChecked(false);
				beginTime = AbDateUtil.getDateG(6, "yyyyMMdd");
				endTime = AbDateUtil.getDateYearMonthDayNowPlus("yyyyMMdd");
				initSearch( "", "", beginTime, endTime);
				getOrder(true, false, "01");
				break;
			case R.id.tv_onemonth:
				tvStarttime.setText("");
				tvFinishtime.setText("");
				tvToday.setChecked(false);
				tvWeekday.setChecked(false);
				tvOnemonth.setChecked(true);
				tvJi.setChecked(false);
				tvYear.setChecked(false);
				beginTime = AbDateUtil.getDateG(29, "yyyyMMdd");
				endTime = AbDateUtil.getDateYearMonthDayNowPlus("yyyyMMdd");
				initSearch( orderStatus, "", beginTime, endTime);
				getOrder(true, false, "02");
				break;
			case R.id.tv_ji:
				tvStarttime.setText("");
				tvFinishtime.setText("");
				tvToday.setChecked(false);
				tvWeekday.setChecked(false);
				tvOnemonth.setChecked(false);
				tvJi.setChecked(true);
				tvYear.setChecked(false);
				beginTime = AbDateUtil.getDateG(89, "yyyyMMdd");
				endTime = AbDateUtil.getDateYearMonthDayNowPlus("yyyyMMdd");
				initSearch( "", "", beginTime, endTime);

				getOrder(true, false, "03");
				break;
			case R.id.tv_year:
				tvStarttime.setText("");
				tvFinishtime.setText("");
				tvToday.setChecked(false);
				tvWeekday.setChecked(false);
				tvOnemonth.setChecked(false);
				tvJi.setChecked(false);
				tvYear.setChecked(true);
				beginTime = AbDateUtil.getDateG(364, "yyyyMMdd");
				endTime = AbDateUtil.getDateYearMonthDayNowPlus("yyyyMMdd");
				initSearch( "", "", beginTime, endTime);
				getOrder(true, false, "04");
				break;
		}
	}

	@OnClick({R.id.tv_starttime, R.id.tv_finishtime, R.id.iv_close})
	public void OnTime(View v) {
		switch (v.getId()) {

			case R.id.tv_starttime:
				TimeSelectorA timeSelectorFrom = new TimeSelectorA(ComeDetailActivity.this, new TimeSelectorA.ResultHandler() {
					@Override
					public void handle(String time) {
						if (!AbStrUtil.isEmpty(tvFinishtime.getText().toString()) && !AbDateUtil.compareTime(time, tvFinishtime.getText().toString(), "yyyyMMdd")) {
							ContentUtils.showMsg(ComeDetailActivity.this, "开始日期须在结束日期之前！");
							tvStarttime.setText("");
							beginTime = "";
						} else {
							tvStarttime.setText(time);
							beginTime = time;
							if (!TextUtils.isEmpty(tvFinishtime.getText().toString())) {
								initSearch( orderStatus, "", beginTime, endTime);
								someOperation();
								getOrder(true, false, "");
							}
						}
					}
				}, STARTTIME, ENDTIME);
				timeSelectorFrom.setDataFormat("yyyyMMdd");
				timeSelectorFrom.setMode(TimeSelectorA.MODE.YMD);
				timeSelectorFrom.setTitle("起始时间");
				timeSelectorFrom.showCurrent();

				break;
			case R.id.tv_finishtime:
				TimeSelectorA timeSelectorTo = new TimeSelectorA(this, new TimeSelectorA.ResultHandler() {
					@Override
					public void handle(String time) {
						if (!AbStrUtil.isEmpty(tvStarttime.getText().toString()) && !AbDateUtil.compareTime(tvStarttime.getText().toString(), time, "yyyyMMdd")) {
							ContentUtils.showMsg(ComeDetailActivity.this, "结束日期须在开始日期之后！");
							tvFinishtime.setText("");
							endTime = "";
						} else {
							tvFinishtime.setText(time);
							endTime = time;
							if (!TextUtils.isEmpty(tvStarttime.getText().toString())) {
								initSearch( orderStatus, "", beginTime, endTime);
								someOperation();
								getOrder(true, false, "");
							}
						}
					}
				}, STARTTIME, ENDTIME);
				timeSelectorTo.setDataFormat("yyyyMMdd");
				timeSelectorTo.setMode(TimeSelectorA.MODE.YMD);
				timeSelectorTo.setTitle("结束时间");
				timeSelectorTo.showCurrent();
				break;
			case R.id.iv_close:
				String strStarttime = tvStarttime.getText().toString();
				String strFinishtime = tvFinishtime.getText().toString();
				if (!TextUtils.isEmpty(strStarttime)) {
					beginTime = "";
					tvStarttime.setText("");
				}
				if (!TextUtils.isEmpty(strFinishtime)) {
					endTime = "";
					tvFinishtime.setText("");
				}
				if (timeNoselected()) {
					clearUpdate();
					return;
				}
				break;
		}
	}


	private void someOperation() {
		tvToday.setChecked(false);
		tvOnemonth.setChecked(false);
	}

	public void clearUpdate() {
		mDatas.clear();
		tv_num.setText("0");
		tv_rmb.setText("¥0");
	}


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_come_detail);
		ButterKnife.bind(this);
		getOrder(true, false, "");
		initAdapter();
	}

	/**
	 * 4.24	到店明细接口
	 */


	private void getOrder(final boolean isResh, final boolean isLoad, String dateStatus) {
		String reqTime = AbDateUtil.getDateTimeNow();
		String uuid = AbStrUtil.getUUID();

		if (isResh) {
			pageNo = 0;
			mDatas.clear();
		}
		try {
			okHttpsImp.getOrderInfo(uuid, "app", reqTime,
					"VI082016110712224600004578",
					"BD2016053018405200000042",

				/*	orderStatus,
					dateStatus,*/
					beginTime,
					endTime,
					"tss",
					0 + "",
					10 + "",

					new MyResultCallback<String>() {
				@Override
				public void onResponseResult(Result result) {
					pageNo++;
					String reString = result.getData();
					System.out.println("reString412" + reString);
					mWholeData.clear();
					mPaySuccessDatas.clear();
					mPayFailDatas.clear();
					if (reString != null) {
						JSONObject jsonObject;
						try {
							jsonObject = new JSONObject(reString);
							reString = jsonObject.getString("responsePageList");
							ArrayList<ComeService> mDatasL = (ArrayList<ComeService>) JSON.parseArray(reString, ComeService.class);
							if (mDatasL.size() > 0) {
								mWholeData.addAll(mDatasL);
							}
							for (ComeService bean : mDatasL) {
								if (bean.getOrderStatus().equals("1")) {
									mPaySuccessDatas.add(bean);
								}
								if (bean.getOrderStatus().equals("99")) {
									mPayFailDatas.add(bean);
								}
							}

						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
				}

				@Override
				public void onResponseFailed(String msg) {
					int mDatasize = mDatas.size();


				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}


	}



	private void initSearch( String orderStatus, String txnTime, String beginTime, String endTime) {

		this.orderStatus = orderStatus;
		this.txnTime = txnTime;
		this.beginTime = beginTime;
		this.endTime = endTime;
	}


	/**
	 * 初始化适配器
	 */
	private QuickAdapter<ComeService> mAdapter;
	private void initAdapter() {
		mAdapter = new QuickAdapter<ComeService>(this, R.layout.comeservice_item, mWholeData) {
			@Override
			protected void convert(BaseAdapterHelper helper, ComeService item) {
				TextView tv_number = helper.getView(R.id.tv_number);
				TextView tv_table = helper.getView(R.id.tv_table);

				TextView tv_timee = helper.getView(R.id.tv_timee);
				TextView tv_moneyprice = helper.getView(R.id.tv_moneyprice);

				tv_number.setText(item.getThirdOrderNo());
				tv_table.setText(item.getTableNum());
				tv_moneyprice.setText(item.getOrderPrice());
				tv_timee.setText(item.getOrderNo());

			}
		};
		actComedeatilListview.setAdapter(mAdapter);
	}


	//用于判断是没有查到数据还是没有选时间
	public boolean timeNoselected() {
		if (TextUtils.isEmpty(txnTime)) {
			if (TextUtils.isEmpty(beginTime) || TextUtils.isEmpty(endTime)) {

				return true;
			}
		}
		return false;
	}

	private void switchAdapter() {
		switch (intentLayout) {
			case POSITION0:
				showingSelect(mWholeData);
				break;
			case POSITION1:
				showingSelect(mPaySuccessDatas);
				break;
			case POSITION2:
				showingSelect(mPayFailDatas);
				break;
		}
	}

	private void showingSelect(List<ComeService> list) {
		if (list.isEmpty()) {
			actComedeatilListview.setVisibility(View.GONE);
			actComedeatilAbpulltorefreshview.setVisibility(View.VISIBLE);
		} else {
			actComedeatilListview.setVisibility(View.VISIBLE);
			actComedeatilAbpulltorefreshview.setVisibility(View.GONE);
			mAdapter.replaceAll(list);
		}
	}

	@Override
	public void onFooterLoad(AbPullToRefreshView view) {
		if (!mPullRefreshing && !mPullLoading) {
			mPullLoading = true;
			getOrder(true, false, "");
		}
	}

	@Override
	public void onHeaderRefresh(AbPullToRefreshView view) {
		if (!mPullRefreshing && !mPullLoading) {
			mPullRefreshing = true;
			getOrder(true, false, "");
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		ButterKnife.unbind(this);
		EventBus.getDefault().post(new ShouyeRefreshEvent(false));
	}
}