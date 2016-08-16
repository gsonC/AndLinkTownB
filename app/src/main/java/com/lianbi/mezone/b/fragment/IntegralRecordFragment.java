package com.lianbi.mezone.b.fragment;/*
 * @创建者     Administrator
 * @创建时间   2016/8/11 16:12
 * @描述       积分记录Fragment
 *
 * @更新者     $Author$ 
 * @更新时间   $Date$
 * @更新描述   ${TODO}
 */

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.lianbi.mezone.b.bean.IntegralRecordBean;
import com.lianbi.mezone.b.httpresponse.OkHttpsImp;
import com.lianbi.mezone.b.ui.IntegralRecordActivity;
import com.xizhi.mezone.b.R;

import java.math.BigDecimal;
import java.util.ArrayList;

import cn.com.hgh.baseadapter.BaseAdapterHelper;
import cn.com.hgh.baseadapter.QuickAdapter;
import cn.com.hgh.utils.AbPullHide;
import cn.com.hgh.utils.MathExtend;
import cn.com.hgh.utils.ScreenUtils;
import cn.com.hgh.view.AbPullToRefreshView;

public class IntegralRecordFragment extends Fragment{

	private IntegralRecordActivity mActivity;
	private ListView mIntegralRecordListView;
	private ImageView mIntegralRecordImageView;
	private AbPullToRefreshView mFmIntegralrecordAbpulltorefreshview;
	ArrayList<IntegralRecordBean> mDatas = new ArrayList<IntegralRecordBean>();
	QuickAdapter<IntegralRecordBean> mAdapter;
	private OkHttpsImp httpImp;

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		View view = inflater.inflate(R.layout.fm_infomessage, null);
		mActivity = (IntegralRecordActivity) getActivity();
		initView(view);
		initListAdapter();
		listen();
		return view;
	}

	private void listen() {
		mFmIntegralrecordAbpulltorefreshview
				.setOnHeaderRefreshListener(new AbPullToRefreshView.OnHeaderRefreshListener() {

					@Override
					public void onHeaderRefresh(AbPullToRefreshView view) {
						mActivity.getIntegralRecord(true);

					}
				});
		mFmIntegralrecordAbpulltorefreshview
				.setOnFooterLoadListener(new AbPullToRefreshView.OnFooterLoadListener() {

					@Override
					public void onFooterLoad(AbPullToRefreshView view) {
						mActivity.getIntegralRecord(false);
					}
				});

	}

	private void initListAdapter() {
		mAdapter = new QuickAdapter<IntegralRecordBean>(mActivity, R.layout.item_consumption, mDatas) {
			@Override
			protected void convert(BaseAdapterHelper helper, IntegralRecordBean item) {
				TextView tv_rc_time = helper.getView(R.id.tv_rc_time);
				TextView tv_rc_thing = helper.getView(R.id.tv_rc_thing);
				TextView tv_rc_where = helper.getView(R.id.tv_rc_where);
				TextView tv_rc_much = helper.getView(R.id.tv_rc_much);

				ScreenUtils.textAdaptationOn720(tv_rc_time, mActivity, 24);//消费时间
				ScreenUtils.textAdaptationOn720(tv_rc_thing, mActivity, 24);//消费内容
				ScreenUtils.textAdaptationOn720(tv_rc_where, mActivity, 24);//消费地点
				ScreenUtils.textAdaptationOn720(tv_rc_much, mActivity, 24);//消费金额

				tv_rc_time.setText(item.getRecordTime() + "");
				tv_rc_thing.setText(item.getRecordThing() + "");
				tv_rc_where.setText(item.getRecordWhrer() + "");
				tv_rc_much.setText(MathExtend.roundNew(item.getRecordInteger().divide(new BigDecimal(100)).doubleValue(), 2) + "");
			}
		};
		mIntegralRecordListView.setAdapter(mAdapter);
	}

	private void initView(View view) {
		mFmIntegralrecordAbpulltorefreshview = (AbPullToRefreshView) view.findViewById(R.id.fm_integralrecord_abpulltorefreshview);
		mIntegralRecordListView =  (ListView) view.findViewById(R.id.fm_messagefragment_listView);
		mIntegralRecordImageView = (ImageView) view.findViewById(R.id.fm_messagefragment_iv_empty);
		mFmIntegralrecordAbpulltorefreshview.setLoadMoreEnable(false);
		mFmIntegralrecordAbpulltorefreshview.setPullRefreshEnable(false);

	}

	/**
	 * 隐藏刷新
	 */
	public void hidle(boolean isResh){
		AbPullHide.hideRefreshView(isResh,
				mFmIntegralrecordAbpulltorefreshview);
	}

	/**
	 * 数据传递
	 */
	public void doSomething(boolean reserve, ArrayList<IntegralRecordBean> cuArrayList) {
		System.out.println("cuArrayList--"+cuArrayList.size());
		if(cuArrayList!=null&&cuArrayList.size()>0){
			mDatas = cuArrayList;
			mAdapter.replaceAll(mDatas);
		}else{
			if(mDatas.size()>0){
				mFmIntegralrecordAbpulltorefreshview.setVisibility(View.VISIBLE);
				mIntegralRecordImageView.setVisibility(View.GONE);
				mAdapter.replaceAll(mDatas);
			}else{
				mFmIntegralrecordAbpulltorefreshview.setVisibility(View.GONE);
				mIntegralRecordImageView.setVisibility(View.VISIBLE);
			}
		}
	}
}
