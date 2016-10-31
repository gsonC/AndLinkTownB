package com.lianbi.mezone.b.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.lianbi.mezone.b.httpresponse.OkHttpsImp;
import com.lianbi.mezone.b.ui.MainActivity;
import com.xizhi.mezone.b.R;

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
	private TextView
			mTv_include_title_flow,
			mTv_include_title_membernum,
			mTv_include_othertitle_cashier, mTv_include_othertitle_salenum;
	private LinearLayout mLlt_shouyemanagement_flow_show, mLlt_shouyemanagement_salenum_show;
	private RadioButton mChk_oneday_salenum,
			mChk_oneweek_salenum;
	private RadioGroup mRdoGroup_time_salenum;

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		View view = inflater.inflate(R.layout.fm_shouyemanagement, null);
		mActivity = (MainActivity) getActivity();
		mOkHttpsImp = OkHttpsImp.SINGLEOKHTTPSIMP.newInstance(mActivity);
		intView(view);
		setLinten();
		return view;

	}

	private void intView(View view) {

		view.findViewById(R.id.llt_shouyemag_inshopservice);//到店服务
		view.findViewById(R.id.img_shouyemag_order);//客户买单
		view.findViewById(R.id.img_shouyemag_call);//响应呼叫
		view.findViewById(R.id.img_shouyemag_condetail);//消费流水

		/**
		 * 实时消费
		 */
		mTv_include_title_flow = (TextView) view.findViewById(R.id.ind_shouyemanagement_flow).findViewById(R.id.tv_include_title);//include实时消费title
		view.findViewById(R.id.ind_shouyemanagement_flow).findViewById(R.id.tv_include_more).setVisibility(View.INVISIBLE);//inculde实时消费 更多隐藏
		mLlt_shouyemanagement_flow_show = (LinearLayout) view.findViewById(R.id.llt_shouyemanagement_flow_show);//实时消费内容

		/**
		 * 消费曲线
		 */
		mTv_include_othertitle_cashier = (TextView) view.findViewById(R.id.ind_shouyemanagement_cashier).findViewById(R.id.tv_include_title);//include消费曲线title
		view.findViewById(R.id.ind_shouyemanagement_cashier).findViewById(R.id.tv_include_more).setVisibility(View.INVISIBLE);//inculde实时消费 更多隐藏
		view.findViewById(R.id.ind_shouyemanagement_cashier).findViewById(R.id.tv_include_yuan).setVisibility(View.VISIBLE);//include消费曲线(元)显示

		/**
		 * 首页--销量排行ID(7个波纹柱状图)
		 */
		mTv_include_othertitle_salenum = (TextView) view.findViewById(R.id.ind_shouyemanagement_salenum).findViewById(R.id.tv_include_othertitle);//include销量排行title
		view.findViewById(R.id.ind_shouyemanagement_salenum).findViewById(R.id.tv_include_otheryuan).setVisibility(View.VISIBLE);//include消费曲线(份)显示
		mRdoGroup_time_salenum = (RadioGroup) view.findViewById(R.id.ind_shouyemanagement_salenum).findViewById(R.id.rdoGroup_time);
		mChk_oneday_salenum = (RadioButton) view.findViewById(R.id.ind_shouyemanagement_salenum).findViewById(R.id.rboButton_oneday);//日
		mChk_oneday_salenum.setChecked(true);
		mChk_oneweek_salenum = (RadioButton) view.findViewById(R.id.ind_shouyemanagement_salenum).findViewById(R.id.rboButton_oneweek);//周
		mLlt_shouyemanagement_salenum_show = (LinearLayout) view.findViewById(R.id.llt_shouyemanagement_salenum_show);//销量排行内容

		/**
		 * 首页--本店会员ID
		 */
		mTv_include_title_membernum = (TextView) view.findViewById(R.id.ind_shouyemanagement_membernum).findViewById(R.id.tv_include_title);//会员统计title
		view.findViewById(R.id.ind_shouyemanagement_membernum).findViewById(R.id.tv_include_more).setVisibility(View.INVISIBLE);//inculde实时消费 更多隐藏
		view.findViewById(R.id.ind_shouyemanagement_shopvip).findViewById(R.id.tv_shouyemanagement_todayvip);//今日新增
		view.findViewById(R.id.ind_shouyemanagement_shopvip).findViewById(R.id.tv_shouyemanagement_numvip);//会员总数
		view.findViewById(R.id.ind_shouyemanagement_shopvip).findViewById(R.id.tv_shouyemanagement_muchtoday);//日最高客单价
		view.findViewById(R.id.ind_shouyemanagement_shopvip).findViewById(R.id.tv_shouyemanagement_muchweek);//周最高客单价
		view.findViewById(R.id.ind_shouyemanagement_shopvip).findViewById(R.id.tv_shouyevip_service_first);//第一位消费次数
		view.findViewById(R.id.ind_shouyemanagement_shopvip).findViewById(R.id.img_shouyevip_name_first);//第一位头像
		view.findViewById(R.id.ind_shouyemanagement_shopvip).findViewById(R.id.tv_shouyevip_name_first);//第一位名字
		view.findViewById(R.id.ind_shouyemanagement_shopvip).findViewById(R.id.tv_shouyevip_service_second);//第二位消费次数
		view.findViewById(R.id.ind_shouyemanagement_shopvip).findViewById(R.id.img_shouyevip_name_second);//第二位头像
		view.findViewById(R.id.ind_shouyemanagement_shopvip).findViewById(R.id.tv_shouyevip_name_second);//第二位名字
		view.findViewById(R.id.ind_shouyemanagement_shopvip).findViewById(R.id.tv_shouyevip_service_third);//第三位消费次数
		view.findViewById(R.id.ind_shouyemanagement_shopvip).findViewById(R.id.img_shouyevip_name_third);//第三位头像
		view.findViewById(R.id.ind_shouyemanagement_shopvip).findViewById(R.id.tv_shouyevip_name_third);//第三位名字

		/**
		 * 首页--SaaS应用服务推荐ID(7张图片)
		 */
		view.findViewById(R.id.ind_shouyeLeagues_apprec).findViewById(R.id.img_shouyemagapp_call);//吆喝
		view.findViewById(R.id.ind_shouyeLeagues_apprec).findViewById(R.id.img_shouyemagapp_union);//商圈联盟
		view.findViewById(R.id.ind_shouyeLeagues_apprec).findViewById(R.id.img_shouyemagapp_sale);//场景式销售
		view.findViewById(R.id.ind_shouyeLeagues_apprec).findViewById(R.id.img_shouyemagapp_opeservice);//运营服务
		view.findViewById(R.id.ind_shouyeLeagues_apprec).findViewById(R.id.img_shouyemagapp_finservice);//金融服务
		view.findViewById(R.id.ind_shouyeLeagues_apprec).findViewById(R.id.img_shouyemagapp_richbook);//支付宝典
		view.findViewById(R.id.ind_shouyeLeagues_apprec).findViewById(R.id.img_shouyemagapp_busdata);//商圈大数据
		initViewSize();
		//BadgeView badgeView = new BadgeView(mActivity,mTv_shouyemanagement_pay);
		//badgeView.setText("1");
		//badgeView.show();
	}

	/**
	 * 设置首页各个title一级文字大小
	 */
	private void initViewSize() {
		mTv_include_title_flow.setText("实时消费");
		mTv_include_othertitle_cashier.setText("消费曲线");
		mTv_include_title_membernum.setText("本店会员");
		mTv_include_othertitle_salenum.setText("销量排行");

	}

	public void setLinten() {


		mRdoGroup_time_salenum.setOnCheckedChangeListener(this);
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


		}
	}

	/**
	 * add交易流水View
	 *
	 * @param number 数量控制
	 */
	private void addFlowDetailView(int number) {
		mLlt_shouyemanagement_flow_show.removeAllViews();
		if (number > 3) {
			number = 3;
		}
		for (int i = 0; i < number; i++) {

		}

	}

	/**
	 * add销量排行View
	 *
	 * @param number 数量控制
	 */
	private void addSalenumDetailView(int number) {
		mLlt_shouyemanagement_salenum_show.removeAllViews();
		if (number > 3) {
			number = 3;
		}
		for (int i = 0; i < number; i++) {


		}
	}

	class MyOnclickListener implements OnClickListener {

		int p;

		public MyOnclickListener(int p) {
			this.p = p;
		}

		@Override
		public void onClick(View v) {

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
