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
	private RadioButton mChk_oneday_cashier, mChk_oneweek_cashier, mChk_onemouth_cashier, mChk_oneday_salenum,
			mChk_oneweek_salenum, mChk_onemouth_salenum;
	private RadioGroup mRdoGroup_time_cashier,mRdoGroup_time_salenum;

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

		mTv_include_title_flow = (TextView) view.findViewById(R.id.ind_shouyemanagement_flow).findViewById(R.id.tv_include_title);//交易流水
		mLlt_shouyemanagement_flow_show = (LinearLayout) view.findViewById(R.id.llt_shouyemanagement_flow_show);//交易流水内容
		mTv_include_title_membernum = (TextView) view.findViewById(R.id.ind_shouyemanagement_membernum).findViewById(R.id.tv_include_title);//会员统计
		mTv_include_othertitle_cashier = (TextView) view.findViewById(R.id.ind_shouyemanagement_cashier).findViewById(R.id.tv_include_othertitle);//收银统计
		mRdoGroup_time_cashier = (RadioGroup) view.findViewById(R.id.ind_shouyemanagement_cashier).findViewById(R.id.rdoGroup_time);
		mChk_oneday_cashier = (RadioButton) view.findViewById(R.id.ind_shouyemanagement_cashier).findViewById(R.id.rboButton_oneday);
		mChk_oneweek_cashier = (RadioButton) view.findViewById(R.id.ind_shouyemanagement_cashier).findViewById(R.id.rboButton_oneweek);
		mChk_onemouth_cashier = (RadioButton) view.findViewById(R.id.ind_shouyemanagement_cashier).findViewById(R.id.rboButton_onemouth);
		mTv_include_othertitle_salenum = (TextView) view.findViewById(R.id.ind_shouyemanagement_salenum).findViewById(R.id.tv_include_othertitle);//销量排行
		mRdoGroup_time_salenum = (RadioGroup) view.findViewById(R.id.ind_shouyemanagement_salenum).findViewById(R.id.rdoGroup_time);
		mChk_oneday_salenum = (RadioButton) view.findViewById(R.id.ind_shouyemanagement_salenum).findViewById(R.id.rboButton_oneday);
		mChk_oneweek_salenum = (RadioButton) view.findViewById(R.id.ind_shouyemanagement_salenum).findViewById(R.id.rboButton_oneweek);
		mChk_onemouth_salenum = (RadioButton) view.findViewById(R.id.ind_shouyemanagement_salenum).findViewById(R.id.rboButton_onemouth);
		mLlt_shouyemanagement_salenum_show = (LinearLayout) view.findViewById(R.id.llt_shouyemanagement_salenum_show);//销量排行内容
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
		mTv_include_title_membernum.setText("消费曲线");
		mTv_include_othertitle_cashier.setText("销量统计");
		mTv_include_othertitle_salenum.setText("本店会员");
	}

	public void setLinten() {


		mRdoGroup_time_cashier.setOnCheckedChangeListener(this);
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
	 * @param number 数量控制
	 */
	private void addSalenumDetailView(int number){
		mLlt_shouyemanagement_salenum_show.removeAllViews();
		if (number > 3) {
			number = 3;
		}
		for (int i = 0; i < number; i++) {




		}
	}

	class MyOnclickListener implements OnClickListener{

		int p;

		public MyOnclickListener(int p){
			this.p = p;
		}

		@Override
		public void onClick(View v) {

		}
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		if(checkedId==mChk_oneday_cashier.getId()){
			System.out.println("11");
		}else if(checkedId==mChk_oneweek_cashier.getId()){
			System.out.println("22");
		}else if(checkedId==mChk_onemouth_cashier.getId()){
			System.out.println("33");
		}else if(checkedId==mChk_oneday_salenum.getId()){
			System.out.println("44");
		}else if(checkedId==mChk_oneweek_salenum.getId()){
			System.out.println("55");
		}else if(checkedId==mChk_onemouth_salenum.getId()){
			System.out.println("66");
		}
	}
}
