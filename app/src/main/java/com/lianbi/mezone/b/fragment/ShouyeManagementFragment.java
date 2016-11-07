package com.lianbi.mezone.b.fragment;

import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.lianbi.mezone.b.bean.ShouYeBannerBean;
import com.lianbi.mezone.b.bean.TestBean;
import com.lianbi.mezone.b.httpresponse.MyResultCallback;
import com.lianbi.mezone.b.httpresponse.OkHttpsImp;
import com.lianbi.mezone.b.ui.MainActivity;
import com.readystatesoftware.viewbadger.BadgeView;
import com.xizhi.mezone.b.R;
import com.zbar.lib.animationslib.Techniques;
import com.zbar.lib.animationslib.YoYo;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

import cn.com.hgh.playview.BaseSliderView;
import cn.com.hgh.playview.BaseSliderView.OnSliderClickListener;
import cn.com.hgh.playview.SliderLayout;
import cn.com.hgh.playview.imp.TextSliderView;
import cn.com.hgh.utils.AbDateUtil;
import cn.com.hgh.utils.AbStrUtil;
import cn.com.hgh.utils.AbViewUtil;
import cn.com.hgh.utils.ContentUtils;
import cn.com.hgh.utils.DynamicWaveTask;
import cn.com.hgh.utils.Result;
import cn.com.hgh.utils.ScreenUtils;
import cn.com.hgh.view.DynamicWave;

/*
 * @创建者     master
 * @创建时间   2016/10/19 18:18
 * @描述		   首页-日常经营
 *
 * @更新者     $Author$ 
 * @更新时间   $Date$
 * @更新描述
 */
public class ShouyeManagementFragment extends Fragment implements OnClickListener, RadioGroup.OnCheckedChangeListener, OnChartGestureListener,
		OnSliderClickListener,
		OnChartValueSelectedListener {

	private MainActivity mActivity;
	private OkHttpsImp mOkHttpsImp;
	private TextView mTv_include_title_flow, mTv_include_title_membernum, mTv_include_othertitle_cashier,
			mTv_include_othertitle_salenum;
	private RadioButton mChk_oneday_salenum, mChk_oneweek_salenum;
	private RadioGroup mRdoGroup_time_salenum;
	private YoYo.YoYoString rope;
	/**
	 * 首页--SaaS应用服务推荐ID(7张图片ID)
	 */
	private ImageView mImg_shouyemagapp_call, mImg_shouyemagapp_union, mImg_shouyemagapp_sale, mImg_shouyemagapp_opeservice,
			mImg_shouyemagapp_finservice, mImg_shouyemagapp_richbook, mImg_shouyemagapp_busdata;
	/**
	 * 首页--本店会员
	 */
	private TextView mTv_shouyemanagement_todayvip, mTv_shouyemanagement_numvip, mTv_shouyemanagement_muchtoday,
			mTv_shouyemanagement_muchweek, mTv_shouyevip_service_first, mTv_shouyevip_name_first, mTv_shouyevip_service_second,
			mTv_shouyevip_name_second, mTv_shouyevip_service_third, mTv_shouyevip_name_third;
	private ImageView mImg_shouyevip_name_first, mImg_shouyevip_name_second, mImg_shouyevip_name_third;
	/**
	 * 实时消费
	 */
	private LinearLayout mLlt_shouyemanagement_comsum;
	private View mView_fillview;
	/**
	 * 到店服务
	 */
	private TextView mTv_include_title;
	private View mInd_shouyemanagement_inshop;
	private ImageView mImg_shouyemag_order, mImg_shouyemag_call, mImg_shouyemag_condetail;
	/**
	 * 消费曲线
	 */
	private LineChart mChart_shouyemanagement;
	/**
	 * 销量排行
	 */
	private TextView mTv_includesalerank_one, mTv_includesalerank_two, mTv_includesalerank_three,
			mTv_includesalerank_four, mTv_includesalerank_five, mTv_includesalerank_six, mTv_includesalerank_seven;
	private TextView mTv_includesalerank_onebottom, mTv_includesalerank_twobottom, mTv_includesalerank_threebottom,
			mTv_includesalerank_fourbottom, mTv_includesalerank_fivebottom, mTv_includesalerank_sixbottom,
			mTv_includesalerank_sevenbottom;
	private DynamicWave mDyw_includesalerank_one, mDyw_includesalerank_two, mDyw_includesalerank_three,
			mDyw_includesalerank_four, mDyw_includesalerank_five, mDyw_includesalerank_six,
			mDyw_includesalerank_seven;
	private List<DynamicWave> mDynamicWaveList;
	private List<TextView> mSaleRankTopList;
	private List<TextView> mSaleRankBottomList;

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		View view = inflater.inflate(R.layout.fm_shouyemanagement, null);
		mActivity = (MainActivity) getActivity();
		mOkHttpsImp = OkHttpsImp.SINGLEOKHTTPSIMP.newInstance(mActivity);
		intView(view);
		initLineChartView();
		getData();
		getData1();
		getData2();
		getData3(true);
//		getBanner();
		setLinten();
		return view;
	}

	private void getBanner() {
		String reqTime = AbDateUtil.getDateTimeNow();
		String uuid = AbStrUtil.getUUID();

		/**
		 * 轮播图
		 */
		try {
			mOkHttpsImp.getAdvert("F1", new MyResultCallback<String>() {

				@Override
				public void onResponseResult(Result result) {
					mDemoSlider.removeAllSliders();
					String resString = result.getData();
					try {
						JSONObject jsonObject = new JSONObject(resString);
						resString = jsonObject.getString("list");

						ades_ImageEs = (ArrayList<ShouYeBannerBean>) JSON
								.parseArray(resString,
										ShouYeBannerBean.class);

						//getBannerData(resString);

						/*if (ades_ImageEs != null && ades_ImageEs.size() > 0) {
							for (int i = 0; i < ades_ImageEs.size(); i++) {
								TextSliderView textSliderView = new TextSliderView(
										mActivity, i);
								textSliderView
										.image(ades_ImageEs.get(i).getImageUrl())
										.error(R.mipmap.adshouye);
								//textSliderView
								//		.setOnSliderClickListener(ShouYeFragment.this);
								mDemoSlider.addSlider(textSliderView);
							}
						} else {
							for (int i = 0; i < 3; i++) {
								TextSliderView textSliderView = new TextSliderView(
										mActivity, i);
								textSliderView.image(R.mipmap.adshouye);
								//textSliderView
								//		.setOnSliderClickListener(ShouYeFragment.this);
								mDemoSlider.addSlider(textSliderView);
							}
						}
						mDemoSlider
								.setPresetIndicatorV(SliderLayout.PresetIndicators.Center_Bottom);
						ad_siderlayout_progressBar.setVisibility(View.GONE);
						mDemoSlider.setVisibility(View.VISIBLE);*/

					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}

				@Override
				public void onResponseFailed(String msg) {
					mDemoSlider.removeAllSliders();
					for (int i = 0; i < 3; i++) {
						TextSliderView textSliderView = new TextSliderView(
								mActivity, i);
						textSliderView.image(R.mipmap.adshouye);
						//textSliderView
						//		.setOnSliderClickListener(ShouYeFragment.this);
						mDemoSlider.addSlider(textSliderView);
					}
					mDemoSlider
							.setPresetIndicatorV(SliderLayout.PresetIndicators.Center_Bottom);
					ad_siderlayout_progressBar.setVisibility(View.GONE);
					mDemoSlider.setVisibility(View.VISIBLE);

				}
			}, uuid, "app", reqTime);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private ProgressBar ad_siderlayout_progressBar;
	private SliderLayout mDemoSlider;
	private LinearLayout ad_llt;
	private ArrayList<ShouYeBannerBean> ades_ImageEs = new ArrayList<>();

	private void initBanner(View view){
		ad_siderlayout_progressBar = (ProgressBar) view.findViewById(R.id.adeslltview_siderlayout_progressBar);
		ad_siderlayout_progressBar.setVisibility(View.GONE);
		mDemoSlider = (SliderLayout) view.findViewById(R.id.adeslltview_siderlayout);
		ad_llt = (LinearLayout) view.findViewById(R.id.adeslltview_llt);
		mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Default);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				ScreenUtils.getScreenWidth(mActivity) / 4);
		ad_llt.setLayoutParams(params);
	}


	public void getBannerData(String banner){
		ades_ImageEs = (ArrayList<ShouYeBannerBean>) JSON
								.parseArray(banner,
										ShouYeBannerBean.class);
			if (ades_ImageEs != null && ades_ImageEs.size() > 0) {
							for (int i = 0; i < ades_ImageEs.size(); i++) {
								TextSliderView textSliderView = new TextSliderView(
										mActivity, i);
								textSliderView
										.image(ades_ImageEs.get(i).getImageUrl())
										.error(R.mipmap.adshouye);
								textSliderView
										.setOnSliderClickListener(ShouyeManagementFragment.this);
								mDemoSlider.addSlider(textSliderView);
							}
			} else {
							for (int i = 0; i < 3; i++) {
								TextSliderView textSliderView = new TextSliderView(
										mActivity, i);
								textSliderView.image(R.mipmap.adshouye);
								textSliderView
										.setOnSliderClickListener(ShouyeManagementFragment.this);
								mDemoSlider.addSlider(textSliderView);
							}
			}
			mDemoSlider
								.setPresetIndicatorV(SliderLayout.PresetIndicators.Center_Bottom);
			ad_siderlayout_progressBar.setVisibility(View.GONE);
			mDemoSlider.setVisibility(View.VISIBLE);
	}
	/**
	 * 销量排行
	 */
	private void getData3(boolean whichone) {
		List<TextData3> arraylist = new ArrayList<>();
		arraylist.clear();
		if (whichone) {
			for (int i = 0; i < 7; i++) {
				TextData3 tt3 = new TextData3();
				tt3.setTop(i * 10);
				tt3.setCenter(150 + i * 10);
				tt3.setBottom("黄鹤楼" + i);
				arraylist.add(tt3);
			}
		} else {
			for (int i = 0; i < 7; i++) {
				TextData3 tt3 = new TextData3();
				tt3.setTop(i * 10);
				tt3.setCenter(100 + i * 10);
				tt3.setBottom("黄鹤楼" + i);
				arraylist.add(tt3);
			}
		}

		//设置数据
		int j = arraylist.size();
		if (j > 7)
			j = 7;
		for (int i = 0; i < j; i++) {
			Timer timer = new Timer();


			mSaleRankTopList.get(i).setText(arraylist.get(i).getTop() + "");
			//mDynamicWave.get(i).setHeight(arraylist.get(i).getCenter());

			timer.schedule(new DynamicWaveTask(timer, mDynamicWaveList.get(i), arraylist.get(i).getCenter()), 0, 10);

			mSaleRankBottomList.get(i).setText(arraylist.get(i).getBottom());
		}

	}

	public class TextData3 {
		private int top;
		private int center;
		private String bottom;

		public int getTop() {
			return top;
		}

		public void setTop(int top) {
			this.top = top;
		}

		public int getCenter() {
			return center;
		}

		public void setCenter(int center) {
			this.center = center;
		}

		public String getBottom() {
			return bottom;
		}

		public void setBottom(String bottom) {
			this.bottom = bottom;
		}
	}


	/**
	 * 折线图测试数据
	 */
	private void getData2() {

		float[] LineChartData = {10.11f, 20.11f, 30.11f, 40.11f, 50.11f, 60.11f, 77.11f, 88.11f, 99.11f};
		//添加数据
		setChartData(LineChartData);

		//mChart_shouyemanagement.animateX(2000);
		//获取图例(这能在设置数据之后)
		//=/Legend l = mChart_shouyemanagement.getLegend();
		//修改图纸
		//l.setForm(Legend.LegendForm.LINE);
		//刷新图例
		//mChart_shouyemanagement.invalidate();
	}


	/**
	 * (会员营销)测试数据
	 */
	private long testData = 1;

	private void getData1() {
		mTv_shouyemanagement_todayvip.setText(String.valueOf(testData));//设置今日会员数
		mTv_shouyemanagement_numvip.setText(String.valueOf(testData));//设置总共会员数
		String todaymuch = "";
		String weekmuch = "";
		try {
			todaymuch = AbStrUtil.changeF2Y(testData);
			weekmuch = AbStrUtil.changeF2Y(testData);
		} catch (Exception e) {
			e.printStackTrace();
		}
		AbStrUtil.formatTextSize(mTv_shouyemanagement_muchtoday, todaymuch, 2);//设置日客最高单价
		AbStrUtil.formatTextSize(mTv_shouyemanagement_muchweek, weekmuch, 2);//设置周客最高单价

		//第一位会员信息
		AbStrUtil.formatTextSize(mTv_shouyevip_service_first, 20 + " 次", 1);
		AbViewUtil.filletImageView(mActivity, mImg_shouyevip_name_first, "http://172.16.103.154/group1/M00/00/11/rBBnmlffUdiAdm2UAAAb5M8npfY133.jpg", 8);
		mTv_shouyevip_name_first.setText("黄鹤楼上看黄河");
		//第二位会员信息
		AbStrUtil.formatTextSize(mTv_shouyevip_service_second, 10 + " 次", 1);
		AbViewUtil.filletImageView(mActivity, mImg_shouyevip_name_second, "http://172.16.103.154/group1/M00/00/11/rBBnmlffUdiAdm2UAAAb5M8npfY133.jpg", 8);
		mTv_shouyevip_name_second.setText("黄鹤楼上看黄河");
		//第三位会员信息
		AbStrUtil.formatTextSize(mTv_shouyevip_service_third, 5 + " 次", 1);
		AbViewUtil.filletImageView(mActivity, mImg_shouyevip_name_third, "http://172.16.103.154/group1/M00/00/11/rBBnmlffUdiAdm2UAAAb5M8npfY133.jpg", 8);
		mTv_shouyevip_name_third.setText("黄鹤楼上看黄河");
	}

	/**
	 * (实时消费)测试数据
	 */
	private List<TestBean> mDatas = new ArrayList<>();

	private void getData() {
		for (int i = 0; i < 7; i++) {
			TestBean bean = new TestBean();
			bean.setMuch("100" + i);
			bean.setTable(i + "号桌");
			bean.setTime("21:0" + i);
			mDatas.add(bean);
		}

		int x = mDatas.size();

		if (x % 2 != 0) {
			TestBean bean = new TestBean();
			bean.setMuch("");
			bean.setTable("");
			bean.setTime("");
			mDatas.add(bean);
		}
		if (null != mDatas && mDatas.size() > 0) {
			mLlt_shouyemanagement_comsum.setVisibility(View.VISIBLE);
			mView_fillview.setVisibility(View.VISIBLE);
			addComsumDetailView(x);
		} else {
			mLlt_shouyemanagement_comsum.setVisibility(View.GONE);
			mView_fillview.setVisibility(View.GONE);
		}

	}

	private void intView(View view) {

		initBanner(view);

		//到店服务
		mTv_include_title = (TextView) view.findViewById(R.id.ind_shouyemanagement_inshop).findViewById(R.id.tv_include_title);//include到店服务title
		view.findViewById(R.id.ind_shouyemanagement_inshop).findViewById(R.id.tv_include_more).setVisibility(View.INVISIBLE);//include到店服务 更多隐藏
		view.findViewById(R.id.ind_shouyemanagement_inshop).findViewById(R.id.img_include_arrow).setVisibility(View.VISIBLE);//include到店服务 箭头显示
		mInd_shouyemanagement_inshop = view.findViewById(R.id.ind_shouyemanagement_inshop);

		mImg_shouyemag_order = (ImageView) view.findViewById(R.id.img_shouyemag_order);//客户买单
		mImg_shouyemag_call = (ImageView) view.findViewById(R.id.img_shouyemag_call);//响应呼叫
		mImg_shouyemag_condetail = (ImageView) view.findViewById(R.id.img_shouyemag_condetail);//消费流水
		BadgeView badgeView = new BadgeView(mActivity, mImg_shouyemag_condetail);
		badgeView.setBadgeMargin(0, 0);
		badgeView.setText("88");
		badgeView.show();

		/**
		 * 实时消费
		 */
		mTv_include_title_flow = (TextView) view.findViewById(R.id.ind_shouyemanagement_flow).findViewById(R.id.tv_include_title);//include实时消费title
		view.findViewById(R.id.ind_shouyemanagement_flow).findViewById(R.id.tv_include_more).setVisibility(View.INVISIBLE);//inculde实时消费 更多隐藏
		mView_fillview = view.findViewById(R.id.view_fillview);//补位View 如果数据为null GONE
		mLlt_shouyemanagement_comsum = (LinearLayout) view.findViewById(R.id.llt_shouyemanagement_comsum);//实时消费展示View

		/**
		 * 消费曲线
		 */
		mTv_include_othertitle_cashier = (TextView) view.findViewById(R.id.ind_shouyemanagement_cashier).findViewById(R.id.tv_include_title);//include消费曲线title
		view.findViewById(R.id.ind_shouyemanagement_cashier).findViewById(R.id.tv_include_more).setVisibility(View.INVISIBLE);//inculde实时消费 更多隐藏
		view.findViewById(R.id.ind_shouyemanagement_cashier).findViewById(R.id.tv_include_yuan).setVisibility(View.VISIBLE);//include消费曲线(元)显示
		mChart_shouyemanagement = (LineChart) view.findViewById(R.id.chart_shouyemanagement);//折线图


		/**
		 * 首页--销量排行ID(7个波纹柱状图)
		 */
		mTv_include_othertitle_salenum = (TextView) view.findViewById(R.id.ind_shouyemanagement_salenum).findViewById(R.id.tv_include_othertitle);//include销量排行title
		view.findViewById(R.id.ind_shouyemanagement_salenum).findViewById(R.id.tv_include_otheryuan).setVisibility(View.VISIBLE);//include消费曲线(份)显示
		mRdoGroup_time_salenum = (RadioGroup) view.findViewById(R.id.ind_shouyemanagement_salenum).findViewById(R.id.rdoGroup_time);
		mChk_oneday_salenum = (RadioButton) view.findViewById(R.id.ind_shouyemanagement_salenum).findViewById(R.id.rboButton_oneday);//日
		mChk_oneday_salenum.setChecked(true);
		mChk_oneweek_salenum = (RadioButton) view.findViewById(R.id.ind_shouyemanagement_salenum).findViewById(R.id.rboButton_oneweek);//周
		mTv_includesalerank_one = (TextView) view.findViewById(R.id.ind_shouyemanagement_salerank).findViewById(R.id.tv_includesalerank_one);
		mTv_includesalerank_two = (TextView) view.findViewById(R.id.ind_shouyemanagement_salerank).findViewById(R.id.tv_includesalerank_two);
		mTv_includesalerank_three = (TextView) view.findViewById(R.id.ind_shouyemanagement_salerank).findViewById(R.id.tv_includesalerank_three);
		mTv_includesalerank_four = (TextView) view.findViewById(R.id.ind_shouyemanagement_salerank).findViewById(R.id.tv_includesalerank_four);
		mTv_includesalerank_five = (TextView) view.findViewById(R.id.ind_shouyemanagement_salerank).findViewById(R.id.tv_includesalerank_five);
		mTv_includesalerank_six = (TextView) view.findViewById(R.id.ind_shouyemanagement_salerank).findViewById(R.id.tv_includesalerank_six);
		mTv_includesalerank_seven = (TextView) view.findViewById(R.id.ind_shouyemanagement_salerank).findViewById(R.id.tv_includesalerank_seven);

		mDyw_includesalerank_one = (DynamicWave) view.findViewById(R.id.ind_shouyemanagement_salerank).findViewById(R.id.dyw_includesalerank_one);
		mDyw_includesalerank_two = (DynamicWave) view.findViewById(R.id.ind_shouyemanagement_salerank).findViewById(R.id.dyw_includesalerank_two);
		mDyw_includesalerank_three = (DynamicWave) view.findViewById(R.id.ind_shouyemanagement_salerank).findViewById(R.id.dyw_includesalerank_three);
		mDyw_includesalerank_four = (DynamicWave) view.findViewById(R.id.ind_shouyemanagement_salerank).findViewById(R.id.dyw_includesalerank_four);
		mDyw_includesalerank_five = (DynamicWave) view.findViewById(R.id.ind_shouyemanagement_salerank).findViewById(R.id.dyw_includesalerank_five);
		mDyw_includesalerank_six = (DynamicWave) view.findViewById(R.id.ind_shouyemanagement_salerank).findViewById(R.id.dyw_includesalerank_six);
		mDyw_includesalerank_seven = (DynamicWave) view.findViewById(R.id.ind_shouyemanagement_salerank).findViewById(R.id.dyw_includesalerank_seven);

		mTv_includesalerank_onebottom = (TextView) view.findViewById(R.id.ind_shouyemanagement_salerank).findViewById(R.id.tv_includesalerank_onebottom);
		mTv_includesalerank_twobottom = (TextView) view.findViewById(R.id.ind_shouyemanagement_salerank).findViewById(R.id.tv_includesalerank_twobottom);
		mTv_includesalerank_threebottom = (TextView) view.findViewById(R.id.ind_shouyemanagement_salerank).findViewById(R.id.tv_includesalerank_threebottom);
		mTv_includesalerank_fourbottom = (TextView) view.findViewById(R.id.ind_shouyemanagement_salerank).findViewById(R.id.tv_includesalerank_fourbottom);
		mTv_includesalerank_fivebottom = (TextView) view.findViewById(R.id.ind_shouyemanagement_salerank).findViewById(R.id.tv_includesalerank_fivebottom);
		mTv_includesalerank_sixbottom = (TextView) view.findViewById(R.id.ind_shouyemanagement_salerank).findViewById(R.id.tv_includesalerank_sixbottom);
		mTv_includesalerank_sevenbottom = (TextView) view.findViewById(R.id.ind_shouyemanagement_salerank).findViewById(R.id.tv_includesalerank_sevenbottom);

		/**
		 * 首页--会员营销ID
		 */
		mTv_include_title_membernum = (TextView) view.findViewById(R.id.ind_shouyemanagement_membernum).findViewById(R.id.tv_include_title);//会员统计title
		view.findViewById(R.id.ind_shouyemanagement_membernum).findViewById(R.id.tv_include_more).setVisibility(View.INVISIBLE);//inculde实时消费 更多隐藏
		mTv_shouyemanagement_todayvip = (TextView) view.findViewById(R.id.ind_shouyemanagement_shopvip).findViewById(R.id.tv_shouyemanagement_todayvip);//今日总数
		mTv_shouyemanagement_numvip = (TextView) view.findViewById(R.id.ind_shouyemanagement_shopvip).findViewById(R.id.tv_shouyemanagement_numvip);//会员总数
		mTv_shouyemanagement_muchtoday = (TextView) view.findViewById(R.id.ind_shouyemanagement_shopvip).findViewById(R.id.tv_shouyemanagement_muchtoday);//日最高客单价
		mTv_shouyemanagement_muchweek = (TextView) view.findViewById(R.id.ind_shouyemanagement_shopvip).findViewById(R.id.tv_shouyemanagement_muchweek);//周最高客单价
		mTv_shouyevip_service_first = (TextView) view.findViewById(R.id.ind_shouyemanagement_shopvip).findViewById(R.id.tv_shouyevip_service_first);//第一位消费次数
		mImg_shouyevip_name_first = (ImageView) view.findViewById(R.id.ind_shouyemanagement_shopvip).findViewById(R.id.img_shouyevip_name_first);//第一位头像
		mTv_shouyevip_name_first = (TextView) view.findViewById(R.id.ind_shouyemanagement_shopvip).findViewById(R.id.tv_shouyevip_name_first);//第一位名字
		mTv_shouyevip_service_second = (TextView) view.findViewById(R.id.ind_shouyemanagement_shopvip).findViewById(R.id.tv_shouyevip_service_second);//第二位消费次数
		mImg_shouyevip_name_second = (ImageView) view.findViewById(R.id.ind_shouyemanagement_shopvip).findViewById(R.id.img_shouyevip_name_second);//第二位头像
		mTv_shouyevip_name_second = (TextView) view.findViewById(R.id.ind_shouyemanagement_shopvip).findViewById(R.id.tv_shouyevip_name_second);//第二位名字
		mTv_shouyevip_service_third = (TextView) view.findViewById(R.id.ind_shouyemanagement_shopvip).findViewById(R.id.tv_shouyevip_service_third);//第三位消费次数
		mImg_shouyevip_name_third = (ImageView) view.findViewById(R.id.ind_shouyemanagement_shopvip).findViewById(R.id.img_shouyevip_name_third);//第三位头像
		mTv_shouyevip_name_third = (TextView) view.findViewById(R.id.ind_shouyemanagement_shopvip).findViewById(R.id.tv_shouyevip_name_third);//第三位名字

		/**
		 * 首页--SaaS应用服务推荐ID(7张图片)
		 */
		mImg_shouyemagapp_call = (ImageView) view.findViewById(R.id.ind_shouyeLeagues_apprec).findViewById(R.id.img_shouyemagapp_call);//吆喝
		mImg_shouyemagapp_union = (ImageView) view.findViewById(R.id.ind_shouyeLeagues_apprec).findViewById(R.id.img_shouyemagapp_union);//商圈联盟
		mImg_shouyemagapp_sale = (ImageView) view.findViewById(R.id.ind_shouyeLeagues_apprec).findViewById(R.id.img_shouyemagapp_sale);//场景式销售
		mImg_shouyemagapp_opeservice = (ImageView) view.findViewById(R.id.ind_shouyeLeagues_apprec).findViewById(R.id.img_shouyemagapp_opeservice);//运营服务
		mImg_shouyemagapp_finservice = (ImageView) view.findViewById(R.id.ind_shouyeLeagues_apprec).findViewById(R.id.img_shouyemagapp_finservice);//金融服务
		mImg_shouyemagapp_richbook = (ImageView) view.findViewById(R.id.ind_shouyeLeagues_apprec).findViewById(R.id.img_shouyemagapp_richbook);//支付宝典
		mImg_shouyemagapp_busdata = (ImageView) view.findViewById(R.id.ind_shouyeLeagues_apprec).findViewById(R.id.img_shouyemagapp_busdata);//商圈大数据
		initViewSize();
		initSaleRank();

		//initLineChart();
		/**
		 * 设置动画
		 */

		mLlt_shouyemanagement_comsum.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				rope = YoYo.with(Techniques.ZoomOutLeft).duration(1000)

						.playOn(mLlt_shouyemanagement_comsum);

				new Handler().postDelayed(new Runnable() {

					@Override
					public void run() {
						rope = YoYo.with(Techniques.ZoomInRight).duration(1000)

								.playOn(mLlt_shouyemanagement_comsum);
					}
				}, 1000);

			}
		});

	}

	/**
	 * 销量排行View整理
	 */
	private void initSaleRank() {
		mDynamicWaveList = new ArrayList<>();
		mSaleRankTopList = new ArrayList<>();
		mSaleRankBottomList = new ArrayList<>();
		mSaleRankTopList.add(mTv_includesalerank_one);
		mSaleRankTopList.add(mTv_includesalerank_two);
		mSaleRankTopList.add(mTv_includesalerank_three);
		mSaleRankTopList.add(mTv_includesalerank_four);
		mSaleRankTopList.add(mTv_includesalerank_five);
		mSaleRankTopList.add(mTv_includesalerank_six);
		mSaleRankTopList.add(mTv_includesalerank_seven);

		mDynamicWaveList.add(mDyw_includesalerank_one);
		mDynamicWaveList.add(mDyw_includesalerank_two);
		mDynamicWaveList.add(mDyw_includesalerank_three);
		mDynamicWaveList.add(mDyw_includesalerank_four);
		mDynamicWaveList.add(mDyw_includesalerank_five);
		mDynamicWaveList.add(mDyw_includesalerank_six);
		mDynamicWaveList.add(mDyw_includesalerank_seven);

		mSaleRankBottomList.add(mTv_includesalerank_onebottom);
		mSaleRankBottomList.add(mTv_includesalerank_twobottom);
		mSaleRankBottomList.add(mTv_includesalerank_threebottom);
		mSaleRankBottomList.add(mTv_includesalerank_fourbottom);
		mSaleRankBottomList.add(mTv_includesalerank_fivebottom);
		mSaleRankBottomList.add(mTv_includesalerank_sixbottom);
		mSaleRankBottomList.add(mTv_includesalerank_sevenbottom);

	}

	/**
	 * 初始化折线图数据
	 */
	/*
	private void initLineChart() {
		//mChart_shouyemanagement
		mChart_shouyemanagement.setOnChartGestureListener(this);
		mChart_shouyemanagement.setOnChartValueSelectedListener(this);
		//是否绘制北京颜色
		mChart_shouyemanagement.setDrawGridBackground(false);
		//设置描述信息为false
		mChart_shouyemanagement.getDescription().setEnabled(false);
		//设置手势
		mChart_shouyemanagement.setTouchEnabled(false);
		//启用缩放和拖动
		mChart_shouyemanagement.setDragEnabled(false);
		mChart_shouyemanagement.setScaleEnabled(false);
		//如果金庸,缩放可在X轴Y轴分别作
		mChart_shouyemanagement.setPinchZoom(true);
		//设置折线图说明隐藏
		mChart_shouyemanagement.getLegend().setEnabled(false);
		//设置动画
		mChart_shouyemanagement.animateY(2000);
		mChart_shouyemanagement.getAxisRight().setEnabled(false);

		//获取X轴
		XAxis xAxis = mChart_shouyemanagement.getXAxis();
		//设置网格线
		xAxis.enableGridDashedLine(10f, 10f, 0);
		//设置X轴颜色
		xAxis.setGridColor(Color.parseColor("#99e9fc"));
		//设置X轴位置
		xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
		//设置X轴轴体是否绘制
		xAxis.setDrawAxisLine(false);
		//设置X轴一共多少标签
		xAxis.setLabelCount(8);

		//获取Y轴
		YAxis leftAxis = mChart_shouyemanagement.getAxisLeft();
		//重置所有限制线,避免重叠线
		leftAxis.removeAllLimitLines();
		//设置最大值
		leftAxis.setAxisMaximum(200f);
		//设置最小值
		leftAxis.setAxisMinimum(0f);
		//设置Y轴颜色
		leftAxis.setGridColor(Color.parseColor("#99e9fc"));
		//设置Y轴轴体是否绘制
		leftAxis.setDrawAxisLine(false);
		//设置Y轴网格线
		leftAxis.enableGridDashedLine(10f, 10f, 0);
		//设置0线是否绘制
		leftAxis.setDrawZeroLine(true);
		//设置Y轴轴体隐藏
		leftAxis.setDrawLabels(false);
		//限制线是落后的数据(而不是顶部)
		leftAxis.setDrawLimitLinesBehindData(true);

		xAxis.setValueFormatter(new IAxisValueFormatter() {
			@Override
			public String getFormattedValue(float value, AxisBase axis) {
				if (value == 0) {
					return "09:00";
				} else if (value == 1) {
					return "10:00";
				} else if (value == 2) {
					return "11:00";
				} else if (value == 3) {
					return "12:00";
				} else if (value == 4) {
					return "13:00";
				} else if (value == 5) {
					return "14:00";
				} else if (value == 6) {
					return "15:00";
				} else if (value == 7) {
					return "16:00";
				} else if (value == 8) {
					return "17:00";
				}
				return "数据异常";
			}

			@Override
			public int getDecimalDigits() {
				return 0;
			}
		});


		//添加数据
		setChartData(9, 100);

		mChart_shouyemanagement.animateX(2000);
		//获取图例(这能在设置数据之后)
		Legend l = mChart_shouyemanagement.getLegend();
		//修改图纸
		l.setForm(Legend.LegendForm.LINE);
		//刷新图例
		//mChart_shouyemanagement.invalidate();

		mChart_shouyemanagement.invalidate();
	}
*/

	/**
	 * 设置数据
	 */
	private void setChartData(float[] lineChartFloatData) {
		ArrayList<Entry> values = new ArrayList<>();
		int j = lineChartFloatData.length;
		for (int i = 0; i < j; i++) {
			values.add(new Entry(i, lineChartFloatData[i]));
		}
		LineDataSet set1;
		if (mChart_shouyemanagement.getData() != null && mChart_shouyemanagement.getData().getDataSetCount() > 0) {
			set1 = (LineDataSet) mChart_shouyemanagement.getData().getDataSetByIndex(0);
			set1.setValues(values);
			mChart_shouyemanagement.getData().notifyDataChanged();
			mChart_shouyemanagement.notifyDataSetChanged();
		} else {
			//创建一个数据集并给它一个类型
			set1 = new LineDataSet(values, "消费曲线");

			//设置这样的虚线“- - -”
			//set1.enableDashedLine(10f, 5f, 0f);
			//set1.enableDashedHighlightLine(10f, 5f, 0f);
			set1.setColor(Color.parseColor("#4ccdb9"));

			set1.setCircleColor(Color.parseColor("#ff9421"));//设置折现圆点颜色
			set1.setLineWidth(2f);//设置线宽
			set1.setCircleRadius(3f);//设置折线处圆点大小
			set1.setDrawCircleHole(true);//设置折线出圆点时候是否有孔
			set1.setValueTextSize(11f);//设置文字大小
			set1.setValueTextColor(Color.parseColor("#00b4d9"));
			set1.setDrawFilled(false);//设置包括的范围区域填充颜色
			set1.setFormLineWidth(1f);//设置下面线宽度
			set1.setFormLineDashEffect(new DashPathEffect(new float[]{10f, 5f}, 0f));//设置下面线为虚线
			set1.setFormSize(15.f);//设置下面线长度
			//设置精度
			set1.setValueFormatter(new IValueFormatter() {
				@Override
				public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
					return "" + value;
				}
			});

			/**
			 * 设置折现下面背景色 判断API18
			 */
			//if (Utils.getSDKInt() >= 18) {
			// 填冲在API级别18及以上的支持
			//	Drawable drawable = ContextCompat.getDrawable(getActivity(), R.drawable.fade_red);
			//	set1.setFillDrawable(drawable);
			//}
			//else {
			set1.setFillColor(Color.WHITE);
			//}

			ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
			dataSets.add(set1); // 添加数据集

			// 创建数据集的数据对象
			LineData data = new LineData(dataSets);

			// 设置数据
			mChart_shouyemanagement.setData(data);


		}
	}

	/**
	 * 设置首页各个title一级文字大小
	 */
	private void initViewSize() {
		mTv_include_title.setText("到店服务");
		mTv_include_title_flow.setText("实时消费");
		mTv_include_othertitle_cashier.setText("消费曲线");
		mTv_include_title_membernum.setText("会员营销");
		mTv_include_othertitle_salenum.setText("销量排行");
	}

	/**
	 * 点击事件
	 */
	public void setLinten() {
		mRdoGroup_time_salenum.setOnCheckedChangeListener(this);

		/**
		 * 到店服务
		 */
		mInd_shouyemanagement_inshop.setOnClickListener(this);
		mImg_shouyemag_order.setOnClickListener(this);
		mImg_shouyemag_call.setOnClickListener(this);
		mImg_shouyemag_condetail.setOnClickListener(this);

		/**
		 * 首页--SaaS应用服务推荐ID(7张图片ID)
		 */
		mImg_shouyemagapp_call.setOnClickListener(this);
		mImg_shouyemagapp_union.setOnClickListener(this);
		mImg_shouyemagapp_sale.setOnClickListener(this);
		mImg_shouyemagapp_opeservice.setOnClickListener(this);
		mImg_shouyemagapp_finservice.setOnClickListener(this);
		mImg_shouyemagapp_richbook.setOnClickListener(this);
		mImg_shouyemagapp_busdata.setOnClickListener(this);
	}


	/**
	 * 下拉刷新数据
	 */
	public void SwipeRefreshData() {
		System.out.println("刷新");
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {

			/**
			 * 到店服务
			 */
			case R.id.ind_shouyemanagement_inshop:
				ContentUtils.showMsg(mActivity, "到店服务");
				break;
			case R.id.img_shouyemag_order://客户买单
				ContentUtils.showMsg(mActivity, "客户买单");
				break;
			case R.id.img_shouyemag_call://响应呼叫
				ContentUtils.showMsg(mActivity, "响应呼叫");
				break;
			case R.id.img_shouyemag_condetail://消费流水
				ContentUtils.showMsg(mActivity, "消费流水");
				break;
			/**
			 * 首页--SaaS应用服务推荐ID(7张图片ID)
			 */
			case R.id.img_shouyemagapp_call://吆喝
				ContentUtils.showMsg(mActivity, "吆喝");
				break;
			case R.id.img_shouyemagapp_union://商圈联盟
				ContentUtils.showMsg(mActivity, "商圈联盟");
				break;
			case R.id.img_shouyemagapp_sale://场景式销售
				ContentUtils.showMsg(mActivity, "场景式销售");
				break;
			case R.id.img_shouyemagapp_opeservice://运营服务
				ContentUtils.showMsg(mActivity, "运营服务");
				break;
			case R.id.img_shouyemagapp_finservice://金融服务
				ContentUtils.showMsg(mActivity, "金融服务");
				break;
			case R.id.img_shouyemagapp_richbook://支付宝典
				ContentUtils.showMsg(mActivity, "支付宝典");
				break;
			case R.id.img_shouyemagapp_busdata://商圈大数据
				ContentUtils.showMsg(mActivity, "商圈大数据");
				break;
		}
	}

	/**
	 * add实时消费View
	 *
	 * @param number 数量控制
	 */
	private void addComsumDetailView(int number) {
		mLlt_shouyemanagement_comsum.removeAllViews();
		if (number > 8) {
			number = 8;
		}
		number = number / 2 + number % 2;

		for (int i = 0; i < number; i++) {
			View view = LayoutInflater.from(mActivity).inflate(R.layout.item_shouyefragment_comsum, null);
			//addView ID
			TextView tv_shouyecomsum_much1 = (TextView) view.findViewById(R.id.tv_shouyecomsum_much1);
			TextView tv_shouyecomsum_table1 = (TextView) view.findViewById(R.id.tv_shouyecomsum_table1);
			TextView tv_shouyecomsum_time1 = (TextView) view.findViewById(R.id.tv_shouyecomsum_time1);
			TextView tv_shouyecomsum_much2 = (TextView) view.findViewById(R.id.tv_shouyecomsum_much2);
			TextView tv_shouyecomsum_table2 = (TextView) view.findViewById(R.id.tv_shouyecomsum_table2);
			TextView tv_shouyecomsum_time2 = (TextView) view.findViewById(R.id.tv_shouyecomsum_time2);

			mLlt_shouyemanagement_comsum.addView(view, i);
			TestBean bean = mDatas.get(i * 2);
			tv_shouyecomsum_much1.setText(bean.getMuch());
			tv_shouyecomsum_table1.setText(bean.getTable());
			tv_shouyecomsum_time1.setText(bean.getTime());
			TestBean bean1 = mDatas.get(i * 2 + 1);
			tv_shouyecomsum_much2.setText(bean1.getMuch());
			tv_shouyecomsum_table2.setText(bean1.getTable());
			tv_shouyecomsum_time2.setText(bean1.getTime());

		}

	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		if (checkedId == mChk_oneday_salenum.getId()) {
			getData3(true);
		} else if (checkedId == mChk_oneweek_salenum.getId()) {
			System.out.println("55");
			getData3(false);
		}
	}

	/**
	 * 初始化折线图
	 */
	private void initLineChartView() {
		mChart_shouyemanagement.setOnChartGestureListener(this);
		mChart_shouyemanagement.setOnChartValueSelectedListener(this);
		//是否绘制背景颜色
		mChart_shouyemanagement.setDrawGridBackground(false);
		//设置描述信息为false
		mChart_shouyemanagement.getDescription().setEnabled(false);
		//设置手势
		mChart_shouyemanagement.setTouchEnabled(false);
		//启用缩放和拖动
		mChart_shouyemanagement.setDragEnabled(true);
		mChart_shouyemanagement.setScaleEnabled(true);
		//如果设置,缩放可在X轴Y轴分别作
		mChart_shouyemanagement.setPinchZoom(true);
		//设置折线图说明隐藏
		mChart_shouyemanagement.getLegend().setEnabled(false);
		//设置动画
		mChart_shouyemanagement.animateY(2000);
		mChart_shouyemanagement.getAxisRight().setEnabled(false);

		//获取X轴
		XAxis xAxis = mChart_shouyemanagement.getXAxis();
		//设置网格线
		xAxis.enableGridDashedLine(10f, 10f, 0);
		//设置X轴颜色
		xAxis.setGridColor(Color.parseColor("#99e9fc"));
		//设置X轴位置
		xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
		//设置X轴轴体是否绘制
		xAxis.setDrawAxisLine(false);
		//设置X轴一共多少标签
		xAxis.setLabelCount(8);

		//获取Y轴
		YAxis leftAxis = mChart_shouyemanagement.getAxisLeft();
		//重置所有限制线,避免重叠线
		leftAxis.removeAllLimitLines();
		//设置最大值
		leftAxis.setAxisMaximum(200f);
		//设置最小值
		leftAxis.setAxisMinimum(0f);
		//设置Y轴颜色
		leftAxis.setGridColor(Color.parseColor("#99e9fc"));
		//设置Y轴轴体是否绘制
		leftAxis.setDrawAxisLine(false);
		//设置Y轴网格线
		leftAxis.enableGridDashedLine(10f, 10f, 0);
		//设置0线是否绘制
		leftAxis.setDrawZeroLine(true);
		//设置Y轴轴体隐藏
		leftAxis.setDrawLabels(false);
		//限制线是落后的数据(而不是顶部)
		leftAxis.setDrawLimitLinesBehindData(true);

		xAxis.setValueFormatter(new IAxisValueFormatter() {
			@Override
			public String getFormattedValue(float value, AxisBase axis) {
				if (value == 0) {
					return "09:00";
				} else if (value == 1) {
					return "10:00";
				} else if (value == 2) {
					return "11:00";
				} else if (value == 3) {
					return "12:00";
				} else if (value == 4) {
					return "13:00";
				} else if (value == 5) {
					return "14:00";
				} else if (value == 6) {
					return "15:00";
				} else if (value == 7) {
					return "16:00";
				} else if (value == 8) {
					return "17:00";
				}
				return "数据异常";
			}

			@Override
			public int getDecimalDigits() {
				return 0;
			}
		});


	}


	// 折线图手势监听---------------------------------------------------------------

	@Override
	public void onChartGestureStart(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {

	}

	@Override
	public void onChartGestureEnd(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {

	}

	@Override
	public void onChartLongPressed(MotionEvent me) {

	}

	@Override
	public void onChartDoubleTapped(MotionEvent me) {

	}

	@Override
	public void onChartSingleTapped(MotionEvent me) {

	}

	@Override
	public void onChartFling(MotionEvent me1, MotionEvent me2, float velocityX, float velocityY) {

	}

	@Override
	public void onChartScale(MotionEvent me, float scaleX, float scaleY) {

	}

	@Override
	public void onChartTranslate(MotionEvent me, float dX, float dY) {

	}

	// 折线图选中监听---------------------------------------------------------------
	@Override
	public void onValueSelected(Entry e, Highlight h) {

	}

	@Override
	public void onNothingSelected() {

	}
	/**
	 * banner点击
	 */
	@Override
	public void onSliderClick(BaseSliderView slider) {
	}


}
