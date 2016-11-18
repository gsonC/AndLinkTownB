package com.lianbi.mezone.b.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
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
import cn.com.hgh.utils.AbPullHide;
import cn.com.hgh.utils.AbStrUtil;
import cn.com.hgh.utils.ContentUtils;
import cn.com.hgh.utils.Result;
import cn.com.hgh.view.AbPullToRefreshView;

/**
 * 到店明细
 */
public class ComeDetailActivity extends BaseActivity {


	public static final int POSITION0 = 0;
	public static final int POSITION1 = 1;
	public static final int POSITION2 = 2;

	@Bind(R.id.back)
	ImageView back;
	@Bind(R.id.act_comedeatil_listview)
	ListView actComedeatilListview;
	@Bind(R.id.act_comedeatil_abpulltorefreshview)
	AbPullToRefreshView actComedeatilAbpulltorefreshview;
	@Bind(R.id.tv_addupto)
	TextView tvAddupto;
	@Bind(R.id.tv_rmb)
	TextView tv_rmb;
	@Bind(R.id.lay_bottom)
	LinearLayout layBottom;

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

	private int intentLayout = 0;
	private String txnTime = "";
	private String beginTime = "";
	private int curPage = 1;
	private String pageSize = "15";
	private String orderNo = "";
	private String orderStatus = "";
	private String endTime = "";
	private String dateStatus = "";

	@Override
	@OnClick({R.id.tv_all, R.id.tv_success, R.id.tv_fail, R.id.back, R.id.tv_today, R.id.tv_weekday, R.id.tv_onemonth, R.id.tv_ji, R.id.tv_year, R.id.tv_starttime, R.id.tv_finishtime, R.id.iv_close})
	protected void onChildClick(View v) {
		super.onChildClick(v);
		switch (v.getId()) {
			case R.id.back:
				onTitleLeftClick();
				break;
			case R.id.tv_all:
				tvAll.setChecked(true);
				tvSuccess.setChecked(false);
				tvFail.setChecked(false);
				this.intentLayout = POSITION0;
				initSearch(beginTime, endTime, "", dateStatus, 1);
				getOrder(true, false);

				break;
			case R.id.tv_success:
				tvAll.setChecked(false);
				tvSuccess.setChecked(true);
				tvFail.setChecked(false);
				this.intentLayout = POSITION1;
				initSearch(beginTime, endTime, "1", dateStatus, 1);
				getOrder(true, false);
				break;
			case R.id.tv_fail:

				tvAll.setChecked(false);
				tvSuccess.setChecked(false);
				tvFail.setChecked(true);
				this.intentLayout = POSITION2;
				initSearch(beginTime, endTime, "91", dateStatus, 1);
				getOrder(true, false);
				break;
			case R.id.tv_today:
				tvStarttime.setText("");
				tvFinishtime.setText("");
				tvToday.setChecked(true);
				tvWeekday.setChecked(false);
				tvOnemonth.setChecked(false);
				tvJi.setChecked(false);
				tvYear.setChecked(false);
				endTime = getTime(AbDateUtil.getDateYearMonthDayNowPlus("yyyyMMdd"));
				initSearch("", endTime, orderStatus, "00", 1);
				getOrder(true, false);
				break;
			case R.id.tv_weekday:
				tvStarttime.setText("");
				tvFinishtime.setText("");
				tvToday.setChecked(false);
				tvWeekday.setChecked(true);
				tvOnemonth.setChecked(false);
				tvJi.setChecked(false);
				tvYear.setChecked(false);
				beginTime = getTime(AbDateUtil.getDateG(6, "yyyyMMdd"));
				endTime = getTime(AbDateUtil.getDateYearMonthDayNowPlus("yyyyMMdd"));
				initSearch("", endTime, orderStatus, "01", 1);
				getOrder(true, false);
				break;
			case R.id.tv_onemonth:
				tvStarttime.setText("");
				tvFinishtime.setText("");
				tvToday.setChecked(false);
				tvWeekday.setChecked(false);
				tvOnemonth.setChecked(true);
				tvJi.setChecked(false);
				tvYear.setChecked(false);
				beginTime = getTime(AbDateUtil.getDateG(29, "yyyyMMdd"));
				endTime = getTime(AbDateUtil.getDateYearMonthDayNowPlus("yyyyMMdd"));

				initSearch("", endTime, orderStatus, "02", 1);
				getOrder(true, false);
				break;
			case R.id.tv_ji:
				tvStarttime.setText("");
				tvFinishtime.setText("");
				tvToday.setChecked(false);
				tvWeekday.setChecked(false);
				tvOnemonth.setChecked(false);
				tvJi.setChecked(true);
				tvYear.setChecked(false);
				beginTime = getTime(AbDateUtil.getDateG(89, "yyyyMMdd"));
				endTime = getTime(AbDateUtil.getDateYearMonthDayNowPlus("yyyyMMdd"));
				initSearch("", endTime, orderStatus, "03", 1);
				getOrder(true, false);
				break;
			case R.id.tv_year:
				tvStarttime.setText("");
				tvFinishtime.setText("");
				tvToday.setChecked(false);
				tvWeekday.setChecked(false);
				tvOnemonth.setChecked(false);
				tvJi.setChecked(false);
				tvYear.setChecked(true);
				beginTime = getTime(AbDateUtil.getDateG(364, "yyyyMMdd"));
				endTime = getTime(AbDateUtil.getDateYearMonthDayNowPlus("yyyyMMdd"));

				initSearch("", endTime, orderStatus, "04", 1);
				getOrder(true, false);
				break;
			case R.id.tv_starttime:
				TimeSelectorA timeSelectorFrom = new TimeSelectorA(ComeDetailActivity.this, new TimeSelectorA.ResultHandler() {
					@Override
					public void handle(String time) {
						if (!AbStrUtil.isEmpty(tvFinishtime.getText().toString()) && !AbDateUtil.compareTime(time, tvFinishtime.getText().toString(), "yyyyMMdd")) {
							ContentUtils.showMsg(ComeDetailActivity.this, "开始日期须在结束日期之前！");
							tvStarttime.setText("");
							beginTime = "";
						} else {
							tvStarttime.setText(getTime(time));
							beginTime = time;
							if (!TextUtils.isEmpty(tvFinishtime.getText().toString())) {
								someOperation();
								initSearch(beginTime, endTime, orderStatus, "", 1);
								getOrder(true, false);
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
							tvFinishtime.setText(getTime(time));
							endTime = time;
							if (!TextUtils.isEmpty(tvStarttime.getText().toString())) {
								someOperation();
								initSearch(beginTime, endTime, orderStatus, "", 1);
								getOrder(true, false);
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
				String startTime = tvStarttime.getText().toString();
				String finishTime = tvFinishtime.getText().toString();
				initSearch(startTime, finishTime, orderStatus, "", 1);
				getOrder(true, false);
				break;
		}
	}


	private void someOperation() {
		tvToday.setChecked(false);
		tvWeekday.setChecked(false);
		tvOnemonth.setChecked(false);
		tvJi.setChecked(false);
		tvYear.setChecked(false);
	}

	public void clearUpdate() {
		mDatas.clear();

	}


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_come_detail);
		ButterKnife.bind(this);
		initAdapter();
		endTime = getTime(AbDateUtil.getDateYearMonthDayNowPlus("yyyyMMdd"));
		//endTime =getTime(AbDateUtil.getDateYearMonthDayNowPlus("yyyyMMdd"));
		setListen();
		initSearch(beginTime, endTime, orderStatus, "00", 1);
		getOrder(true, false);

	}

	private void setListen() {
		actComedeatilAbpulltorefreshview.setLoadMoreEnable(true);
		actComedeatilAbpulltorefreshview.setPullRefreshEnable(true);
		actComedeatilAbpulltorefreshview.setOnHeaderRefreshListener(new AbPullToRefreshView.OnHeaderRefreshListener() {
			@Override
			public void onHeaderRefresh(AbPullToRefreshView view) {
				getOrder(true,false);
			}
		});

		actComedeatilAbpulltorefreshview.setOnFooterLoadListener(new AbPullToRefreshView.OnFooterLoadListener() {

			@Override
			public void onFooterLoad(AbPullToRefreshView view) {
				getOrder(false,true);
			}
		});
	}

	/**
	 * 4.24	到店明细接口
	 */
	private void getOrder(final boolean isResh, final boolean isLoad) {
		String reqTime = AbDateUtil.getDateTimeNow();
		String uuid = AbStrUtil.getUUID();

		if (isResh) {
			curPage = 1;
			mDatas.clear();
		}

		try {
			okHttpsImp.getOrderInfo(uuid,                      //serNum
					"app",                     //source
					reqTime,                   //reqTime
					UserId,//userId
					BusinessId,//businessId
					beginTime,                  //startTime
					endTime,                     //endTime
					"",                         //sourceType
					orderStatus,                        //orderStatus
					dateStatus,                //dateStatus
					1 + "",                    //curPage
					10 + "",                       //pageSize

					new MyResultCallback<String>() {
						@Override
						public void onResponseResult(Result result) {
							curPage++;
							String reString = result.getData();
							mWholeData.clear();
							mPaySuccessDatas.clear();
							mPayFailDatas.clear();
							if (reString != null) {
								JSONObject jsonObject;
								try {
									jsonObject = new JSONObject(reString);
									long amtCount = jsonObject.getLong("amtCount");

									/*long amt = amtCount * 100;
//											BigDecimal.valueOf(Long.valueOf(amtCount))
//											.divide(new BigDecimal(100)).toString();*/
									try {
										tv_rmb.setText("¥"+AbStrUtil.changeF2Y(amtCount));
									} catch (Exception e) {
										e.printStackTrace();
									}
								/*	tv_rmb.setText("¥" + amt);*/
									reString = jsonObject.getString("responsePageList");
									ArrayList<ComeService> mDatasL = (ArrayList<ComeService>) JSON.parseArray(reString, ComeService.class);
									Log.i("tag", "363--->" + orderStatus);
									if (orderStatus.equals("")) {
										mWholeData.addAll(mDatasL);
									} else if (orderStatus.equals("1")) {
										mPaySuccessDatas.addAll(mDatasL);
									} else if (orderStatus.equals("91")) {
										mPayFailDatas.addAll(mDatasL);
									}
									switchAdapter();
									AbPullHide.hideRefreshView(isResh, actComedeatilAbpulltorefreshview);

								} catch (Exception e) {
									e.printStackTrace();
								}
							}
							refreshingFinish();
						}

						@Override
						public void onResponseFailed(String msg) {
							refreshingFinish();
							tv_rmb.setText("¥0");
						}
					});
		} catch (Exception e) {
			e.printStackTrace();
		}


	}


	private void initSearch(String beginTime, String endTime, String orderStatus, String dateStatus, int curPage) {
		this.beginTime = beginTime;
		this.endTime = endTime;
		this.orderStatus = orderStatus;
		this.dateStatus = dateStatus;
		this.curPage = curPage;
	}

	private void initSear(String beginTime, String endTime, int curPage) {
		this.beginTime = beginTime;
		this.endTime = endTime;
		this.curPage = curPage;
	}

	/**
	 * 初始化适配器
	 */
	private QuickAdapter<ComeService> mAdapter;
	private void initAdapter() {
		mAdapter = new QuickAdapter<ComeService>(this, R.layout.comeservice_item, mDatas) {
			@Override
			protected void convert(BaseAdapterHelper helper, ComeService item) {
				TextView tv_table = helper.getView(R.id.tv_table);
				TextView tv_timee = helper.getView(R.id.tv_timee);
				TextView tv_moneyprice = helper.getView(R.id.tv_moneyprice);
				TextView tv_number = helper.getView(R.id.tv_number);
				TextView tv_cash = helper.getView(R.id.tv_cash);
				tv_cash.setText(item.getPayType());
				switch (item.getPayType()){
					case  "ALL":
						tv_cash.setText("支付宝支付");
						break;
					case  "WCP":
						tv_cash.setText("微信支付");
						break;
					case  "UNP":
						tv_cash.setText("银联支付");
						break;
					case  "POS":
						tv_cash.setText("POS机支付");
						break;
					case  "CAS":
						tv_cash.setText("现金支付");
						break;
					case  "OTH":
						tv_cash.setText("");
						break;
				}

				tv_number.setText(getOrderTime(item.getOrderNo()));
				TextView tv_new = helper.getView(R.id.tv_new);

				       if(item.getIsRead()==0){
						tv_new.setText("");
					}else if(item.getIsRead()==1){
						tv_new.setText("新");
						//tv_new.setTextColor(getResources().getColor((R.color.color_fd1a00)));
					}

				tv_table.setText(item.getTableNum());
				try {
					tv_moneyprice.setText("¥"+AbStrUtil.changeF2Y(Long.parseLong(item.getOrderPrice())));
				} catch (Exception e) {
					e.printStackTrace();
				}

				tv_timee.setText(AbDateUtil.getSpecialFormatTimeFromTimeMillisString(item.getCreatTime(),AbDateUtil.dateFormatYMDHMS));

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
		int mDatasize = mDatas.size();
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
		mDatas.addAll(list);
		if (list.isEmpty()) {
			actComedeatilListview.setVisibility(View.GONE);
			imgEmpty.setVisibility(View.VISIBLE);
		} else {
			actComedeatilListview.setVisibility(View.VISIBLE);
			imgEmpty.setVisibility(View.GONE);
			mAdapter.replaceAll(mDatas);
		}
	}


	@Override
	protected void onDestroy() {
		super.onDestroy();
		ButterKnife.unbind(this);
		EventBus.getDefault().post(new ShouyeRefreshEvent(false));
	}

	private void refreshingFinish() {
		if (mPullRefreshing) {
			actComedeatilAbpulltorefreshview.onHeaderRefreshFinish();
			mPullRefreshing = false;
		}
		if (mPullLoading) {
			actComedeatilAbpulltorefreshview.onFooterLoadFinish();
			mPullLoading = false;
		}
	}

	private String getTime(String time) {
		String year = time.substring(0, 4);
		String mouth = time.substring(4, 6);
		String day = time.substring(6, 8);
		return year + "-" + mouth + "-" + day;
	}
	private String getOrderTime(String ordertime) {
		String year = ordertime.substring(0, 8);
		String mouth= ordertime.substring(8,26);
		String day = ordertime.substring(26,30);
		return year  +"****" +day;
	}
	private String getOrderTi(String time) {
		String year = time.substring(0, 4);
		String mouth = time.substring(4, 6);
		String day = time.substring(6, 8);

		String house = time.substring(6, 8);
		String minute = time.substring(6, 8);
		String miao = time.substring(6, 8);
		return year + "-" + mouth + "-" + day+"   "+house+":"+minute+":"+miao+"";
	}


}