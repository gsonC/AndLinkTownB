package com.lianbi.mezone.b.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
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
public class ShouyeManagementFragment extends Fragment implements OnClickListener {

	private MainActivity mActivity;
	private OkHttpsImp mOkHttpsImp;
	private TextView mTv_shouyemanagement_pay,mTv_shouyemanagement_callservice,mTv_shouyemanagement_inshopdetail,mTv_shouyemanagement_inshopservice
			,mTv_include_title_flow,mTv_shouyemanagement_flow_detail,mTv_include_title_membernum,mTv_shouyemanagement_nummember
			,mTv_shouyemanagement_addmember,mTv_shouyemanagement_memberdetail,mTv_include_othertitle_cashier,mTv_include_othertitle_salenum;
	private LinearLayout mLlt_shouyemanagement_flow_show,mLlt_shouyemanagement_salenum_show;
	private CheckBox mChk_oneday_cashier,mChk_oneweek_cashier,mChk_onemouth_cashier,mChk_oneday_salenum,mChk_oneweek_salenum,mChk_onemouth_salenum;

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
		mTv_shouyemanagement_pay = (TextView) view.findViewById(R.id.tv_shouyemanagement_pay);//收款
		mTv_shouyemanagement_callservice = (TextView) view.findViewById(R.id.tv_shouyemanagement_callservice);//呼叫服务
		mTv_shouyemanagement_inshopdetail = (TextView) view.findViewById(R.id.tv_shouyemanagement_inshopdetail);//到店明细
		mTv_shouyemanagement_inshopservice = (TextView) view.findViewById(R.id.tv_shouyemanagement_inshopservice);//到店服务
		mTv_include_title_flow = (TextView) view.findViewById(R.id.ind_shouyemanagement_flow).findViewById(R.id.tv_include_title);//交易流水
		mLlt_shouyemanagement_flow_show = (LinearLayout) view.findViewById(R.id.llt_shouyemanagement_flow_show);//交易流水内容
		mTv_shouyemanagement_flow_detail = (TextView) view.findViewById(R.id.tv_shouyemanagement_flow_detail);//交易流水详细
		mTv_include_title_membernum = (TextView) view.findViewById(R.id.ind_shouyemanagement_membernum).findViewById(R.id.tv_include_title);//会员统计
		mTv_shouyemanagement_nummember = (TextView) view.findViewById(R.id.tv_shouyemanagement_nummember);//会员总数
		mTv_shouyemanagement_addmember = (TextView) view.findViewById(R.id.tv_shouyemanagement_addmember);//今日新增
		mTv_shouyemanagement_memberdetail = (TextView) view.findViewById(R.id.tv_shouyemanagement_memberdetail);//会员详情
		mTv_include_othertitle_cashier = (TextView) view.findViewById(R.id.ind_shouyemanagement_cashier).findViewById(R.id.tv_include_othertitle);//收银统计
		mChk_oneday_cashier = (CheckBox) view.findViewById(R.id.ind_shouyemanagement_cashier).findViewById(R.id.chk_oneday);
		mChk_oneweek_cashier = (CheckBox) view.findViewById(R.id.ind_shouyemanagement_cashier).findViewById(R.id.chk_oneweek);
		mChk_onemouth_cashier = (CheckBox) view.findViewById(R.id.ind_shouyemanagement_cashier).findViewById(R.id.chk_onemouth);
		mTv_include_othertitle_salenum = (TextView) view.findViewById(R.id.ind_shouyemanagement_salenum).findViewById(R.id.tv_include_othertitle);//销量排行
		mChk_oneday_salenum = (CheckBox) view.findViewById(R.id.ind_shouyemanagement_salenum).findViewById(R.id.chk_oneday);
		mChk_oneweek_salenum = (CheckBox) view.findViewById(R.id.ind_shouyemanagement_salenum).findViewById(R.id.chk_oneweek);
		mChk_onemouth_salenum = (CheckBox) view.findViewById(R.id.ind_shouyemanagement_salenum).findViewById(R.id.chk_onemouth);
		mLlt_shouyemanagement_salenum_show = (LinearLayout) view.findViewById(R.id.llt_shouyemanagement_salenum_show);//销量排行内容
		initViewSize();
	}

	/**
	 * 设置首页各个title一级文字大小
	 */
	private void initViewSize() {
		mTv_include_title_flow.setText("交易流水");
		mTv_include_title_membernum.setText("会员统计");
		mTv_include_othertitle_cashier.setText("收银统计");
		mTv_include_othertitle_salenum.setText("销量排行");
	}

	public void setLinten() {
		mTv_shouyemanagement_pay.setOnClickListener(this);
		mTv_shouyemanagement_callservice.setOnClickListener(this);
		mTv_shouyemanagement_inshopdetail.setOnClickListener(this);
		mTv_shouyemanagement_inshopservice.setOnClickListener(this);

		mTv_shouyemanagement_flow_detail.setOnClickListener(this);
		mTv_shouyemanagement_memberdetail.setOnClickListener(this);
	}


	public void getData(){

	}

	@Override
	public void onClick(View view) {
		switch (view.getId()){
			case R.id.tv_shouyemanagement_pay://收款

				break;
			case R.id.tv_shouyemanagement_callservice://呼叫服务

				break;
			case R.id.tv_shouyemanagement_inshopdetail://到店明细

				break;
			case R.id.tv_shouyemanagement_inshopservice://到店服务

				break;
			case R.id.tv_shouyemanagement_flow_detail://交易流水详细

				break;
			case R.id.tv_shouyemanagement_memberdetail://会员详情

				break;
		}
	}

	private void addFlowDetailView(int number){
		mLlt_shouyemanagement_flow_show.removeAllViews();
		if(number>3){
			number = 3;
		}
		for(int i=0;i<number;i++){

		}

	}
}
