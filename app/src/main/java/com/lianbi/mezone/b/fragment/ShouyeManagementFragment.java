package com.lianbi.mezone.b.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.RelativeSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.lianbi.mezone.b.bean.TestBean;
import com.lianbi.mezone.b.httpresponse.OkHttpsImp;
import com.lianbi.mezone.b.ui.MainActivity;
import com.readystatesoftware.viewbadger.BadgeView;
import com.xizhi.mezone.b.R;
import com.zbar.lib.animationslib.Techniques;
import com.zbar.lib.animationslib.YoYo;

import java.util.ArrayList;
import java.util.List;

import cn.com.hgh.utils.AbStrUtil;
import cn.com.hgh.utils.ContentUtils;

/*
 * @创建者     master
 * @创建时间   2016/10/19 18:18
 * @描述		   首页-日常经营
 *
 * @更新者     $Author$ 
 * @更新时间   $Date$
 * @更新描述
 */
public class ShouyeManagementFragment extends Fragment implements OnClickListener, RadioGroup.OnCheckedChangeListener {

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

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		View view = inflater.inflate(R.layout.fm_shouyemanagement, null);
		mActivity = (MainActivity) getActivity();
		mOkHttpsImp = OkHttpsImp.SINGLEOKHTTPSIMP.newInstance(mActivity);
		intView(view);
		getData();
		getData1();
		setLinten();
		return view;

	}

	/**
	 * (本店会员)测试数据
	 */
	private long testData = 123456;
	private void getData1() {
		String ss = "";
		try {
			ss = AbStrUtil.changeF2Y(testData);
		} catch (Exception e) {
			e.printStackTrace();
		}
		/**
		 * SpannableString 使一个textView展示不同文字大小 new RelativeSizeSpan(0.8f)代表正常字体的0.8倍
		 */
		SpannableString msp = new SpannableString(ss);
		msp.setSpan(new RelativeSizeSpan(0.8f), ss.length()-2, ss.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		mTv_shouyemanagement_muchtoday.setText(msp);
	}

	/**
	 * (实时消费)测试数据
	 */
	private List<TestBean> mDatas = new ArrayList<>();

	private void getData() {
		for (int i = 0; i < 10; i++) {
			TestBean bean = new TestBean();
			bean.setMuch("100" + i);
			bean.setTable(i + "号桌");
			bean.setTime("21:0" + i);
			mDatas.add(bean);
		}
		if (null != mDatas && mDatas.size() > 0) {
			mLlt_shouyemanagement_comsum.setVisibility(View.VISIBLE);
			addComsumDetailView(10);
		} else {
			mLlt_shouyemanagement_comsum.setVisibility(View.GONE);
			mView_fillview.setVisibility(View.GONE);
		}

	}


	private void intView(View view) {

		//到店服务
		mTv_include_title = (TextView) view.findViewById(R.id.ind_shouyemanagement_inshop).findViewById(R.id.tv_include_title);//include到店服务title
		view.findViewById(R.id.ind_shouyemanagement_inshop).findViewById(R.id.tv_include_more).setVisibility(View.INVISIBLE);//include到店服务 更多隐藏
		view.findViewById(R.id.ind_shouyemanagement_inshop).findViewById(R.id.img_include_arrow).setVisibility(View.VISIBLE);//include到店服务 箭头显示
		mInd_shouyemanagement_inshop = view.findViewById(R.id.ind_shouyemanagement_inshop);

		mImg_shouyemag_order = (ImageView) view.findViewById(R.id.img_shouyemag_order);//客户买单
		mImg_shouyemag_call = (ImageView) view.findViewById(R.id.img_shouyemag_call);//响应呼叫
		mImg_shouyemag_condetail = (ImageView) view.findViewById(R.id.img_shouyemag_condetail);//消费流水
		BadgeView badgeView = new BadgeView(mActivity, mImg_shouyemag_order);
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

		/**
		 * 首页--本店会员ID
		 */
		mTv_include_title_membernum = (TextView) view.findViewById(R.id.ind_shouyemanagement_membernum).findViewById(R.id.tv_include_title);//会员统计title
		view.findViewById(R.id.ind_shouyemanagement_membernum).findViewById(R.id.tv_include_more).setVisibility(View.INVISIBLE);//inculde实时消费 更多隐藏
		mTv_shouyemanagement_todayvip = (TextView) view.findViewById(R.id.ind_shouyemanagement_shopvip).findViewById(R.id.tv_shouyemanagement_todayvip);//今日新增
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
		initLineChart();
		/**
		 * 设置动画
		 */

		mLlt_shouyemanagement_comsum.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				System.out.println("点击");
				ContentUtils.showMsg(mActivity, "点击");
				rope = YoYo.with(Techniques.FlipInY).duration(1000)

						.playOn(mLlt_shouyemanagement_comsum);
			}
		});

	}

	/**
	 * 初始化折线图数据
	 */
	private void initLineChart() {

	}


	/**
	 * 设置首页各个title一级文字大小
	 */
	private void initViewSize() {
		mTv_include_title.setText("到店服务");
		mTv_include_title_flow.setText("实时消费");
		mTv_include_othertitle_cashier.setText("消费曲线");
		mTv_include_title_membernum.setText("本店会员");
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
	 * 本店会员数据填充
	 */
	private void setMyShopVIPData(){

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
			System.out.println("44");
		} else if (checkedId == mChk_oneweek_salenum.getId()) {
			System.out.println("55");
		}
	}
}
