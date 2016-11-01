package com.lianbi.mezone.b.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lianbi.mezone.b.fragment.OrederFragment;
import com.xizhi.mezone.b.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.com.hgh.utils.AbDateUtil;
import cn.com.hgh.utils.ContentUtils;
import cn.com.hgh.view.NoScrollViewPager;

/**
 * 到店明细
 */
public class ComeDetailActivity extends BaseActivity {

	@Bind(R.id.tv_starttime)
	TextView tvStarttime;
	@Bind(R.id.tv_finishtime)
	TextView tvFinishtime;
	@Bind(R.id.iv_close)
	ImageView ivClose;
	@Bind(R.id.tv_today)
	CheckBox tvToday;
	@Bind(R.id.tv_weekday)
	CheckBox tvWeekday;
	@Bind(R.id.tv_onemonth)
	CheckBox tvOnemonth;
	@Bind(R.id.tv_ji)
	CheckBox tvJi;
	@Bind(R.id.tv_year)
	CheckBox tvYear;
	@Bind(R.id.tv_all)
	CheckBox tvAll;
	@Bind(R.id.tv_success)
	CheckBox tvSuccess;
	@Bind(R.id.tv_fail)
	CheckBox tvFail;
	@Bind(R.id.vp_orderpager)
	NoScrollViewPager vpOrderpager;
	@Bind(R.id.img_empty)
	ImageView imgEmpty;
	@Bind(R.id.tv_addupto)
	TextView tvAddupto;
	@Bind(R.id.tv_rmb)
	TextView tvRmb;
	@Bind(R.id.lay_bottom)
	LinearLayout layBottom;
	private String coupName;
	private String limitAmt;
	private String coupAmt;
	private String isValid="Y";
	private int  intentLayout=0;
	private String txnTime="";
	private String beginTime="";
	private int pageNo=0;
	private String pageSize="15";
	private String orderNo="";
	private int listPosition=-1;
	private String orderStatus="";
	private String  endTime="";
	public static final int POSITION0 = 0;
	public static final int POSITION1 = 1;
	public static final int POSITION2 = 2;
	OrederFragment mWholeFragment;
	OrederFragment mPaySuccessFragment;
	OrederFragment mPayFailFragment;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_come_detail,NOTYPE);
		ButterKnife.bind(this);

	}

       public  void OnDate(View v){
		   switch (v.getId()){
			   case R.id.tv_today:
				   tvStarttime.setText("");
				   tvFinishtime.setText("");
				   tvToday.setChecked(true);
				   tvWeekday.setChecked(false);
				   tvOnemonth.setChecked(true);
				   tvJi.setChecked(false);
				   tvYear.setChecked(false);
				   String  txnTime= AbDateUtil.getDateYearMonthDayNowPlus("yyyyMMdd");
				   initSearch("", orderStatus, txnTime, "", "");
				  // getOrderInfo(true,false,isValid);
				   break;

			   case R.id.tv_weekday:
				   tvStarttime.setText("");
				   tvFinishtime.setText("");
				   tvToday.setChecked(false);
				   tvWeekday.setChecked(true);
				   tvOnemonth.setChecked(false);
				   tvJi.setChecked(false);
				   tvYear.setChecked(false);
				   beginTime=AbDateUtil.getDateG(6,"yyyyMMdd");//往后推几天
				   endTime=AbDateUtil.getDateYearMonthDayNowPlus("yyyyMMdd");
				   initSearch("", orderStatus, "", beginTime, endTime);
				   //getOrderInfo(true,false,isValid);
				   break;
                case R.id.tv_onemonth:
					tvStarttime.setText("");
					tvFinishtime.setText("");
					tvToday.setChecked(false);
					tvWeekday.setChecked(false);
					tvOnemonth.setChecked(true);
					tvJi.setChecked(false);
					tvYear.setChecked(false);
					beginTime=AbDateUtil.getDateG(29,"yyyyMMdd");
					endTime=AbDateUtil.getDateYearMonthDayNowPlus("yyyyMMdd");
					initSearch("", orderStatus, "", beginTime, endTime);
					//getOrderInfo(true,false,isValid);
					break;
			   case R.id.tv_ji:
				   tvStarttime.setText("");
				   tvFinishtime.setText("");
				   tvToday.setChecked(false);
				   tvWeekday.setChecked(false);
				   tvOnemonth.setChecked(false);
				   tvJi.setChecked(true);
				   tvYear.setChecked(false);
				   beginTime=AbDateUtil.getDateG(90,"yyyyMMdd");
				   endTime=AbDateUtil.getDateYearMonthDayNowPlus("yyyyMMdd");
				   initSearch("", orderStatus, "", beginTime, endTime);
				   //getOrderInfo(true,false,isValid);
				   break;
			   case R.id.tv_year:
				   tvStarttime.setText("");
				   tvFinishtime.setText("");
				   tvToday.setChecked(false);
				   tvWeekday.setChecked(false);
				   tvOnemonth.setChecked(false);
				   tvJi.setChecked(false);
				   tvYear.setChecked(true);
				   beginTime=AbDateUtil.getDateG(365,"yyyyMMdd");
				   endTime=AbDateUtil.getDateYearMonthDayNowPlus("yyyyMMdd");
				   initSearch("", orderStatus, "", beginTime, endTime);
				   //getOrderInfo(true,false,isValid);
				   break;
		   }

	   }
	private  void  initSearch(String orderNo, String orderStatus, String txnTime, String beginTime,
							  String endTime) {
		this.isValid="Y";
		this.orderNo="";
		this.orderStatus=orderStatus;
		this.txnTime=txnTime;
		this.beginTime=beginTime;
		this.endTime=endTime;
	}
	private   void  someOperation(){
		tvToday.setChecked(false);
		tvWeekday.setChecked(false);
		tvOnemonth.setChecked(false);
		tvJi.setChecked(false);
		tvYear.setChecked(false);
	}

	@Override
	public void onClick(View view) {
		super.onClick(view);
		switch (view.getId()){
			case R.id.tv_all:
				setScrollToTop();
				vpOrderpager.setCurrentItem(0);
				tvAll.setChecked(true);
				tvSuccess.setChecked(false);
				tvFail.setChecked(false);
				initSearch("", "03,04", txnTime, beginTime, endTime);
				this.intentLayout=POSITION0;
				if(timeNoselected()){
					ContentUtils.showMsg(this, "请选择查询时间");
					clearUpdate();
					return;
				}else{

				}
				//getOrderInfo(true,false,isValid);
				break;
			case  R.id.tv_success:
				setScrollToTop();
				vpOrderpager.setCurrentItem(1);
				tvAll.setChecked(false);
				tvSuccess.setChecked(true);
				tvFail.setChecked(false);
				initSearch("", "03", txnTime, beginTime, endTime);
				this.intentLayout=POSITION1;
				if(timeNoselected()){
					ContentUtils.showMsg(this, "请选择查询时间");
					clearUpdate();
					return;
				}
				break;
			case R.id.tv_fail:
				setScrollToTop();
				vpOrderpager.setCurrentItem(2);
				tvAll.setChecked(false);
				tvSuccess.setChecked(false);
				tvFail.setChecked(true);
				initSearch(
						"",
						"04",
						txnTime,
						beginTime,
						endTime
				);
				this.intentLayout=POSITION2;
				if(timeNoselected()){
					ContentUtils.showMsg(this, "请选择查询时间");
					clearUpdate();
					return;
				}else{

				}
				//getOrderInfo(true,false,isValid);
				break;
		}
	}
	private void  setScrollToTop(){
		switch (intentLayout) {
			case POSITION0:
				if (mWholeFragment != null) {
					mWholeFragment.stopScroll();
				}
				break;

			case POSITION1:
				if (mPaySuccessFragment != null) {
					mPaySuccessFragment.stopScroll();
				}
				break;
			case POSITION2:
				if (mPayFailFragment != null) {
					mPayFailFragment.stopScroll();
				}
				break;
		}
	}
	//用于判断是没有查到数据还是没有选时间
	public boolean  timeNoselected() {
		if (TextUtils.isEmpty(txnTime)) {
			if (TextUtils.isEmpty(beginTime) || TextUtils.isEmpty(endTime)) {

				return  true;
			}
		}
		return  false;
	}
	public void   clearUpdate(){
		/*mDatas.clear();
		clearData();
		tv_num.setText("0");
		tv_rmb.setText("¥0");*/
	}
}
