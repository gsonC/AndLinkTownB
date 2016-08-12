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

import com.lianbi.mezone.b.ui.IntegralRecordActivity;
import com.xizhi.mezone.b.R;

public class IntegralRecordFragment extends Fragment{

	private IntegralRecordActivity mActivity;
	private ListView mIntegralRecordListView;
	private ImageView mIntegralRecordImageView;

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fm_infomessage, null);
		mActivity = (IntegralRecordActivity) getActivity();
		initView(view);
		initListAdapter();
		listen();
		return view;
	}

	private void listen() {
	}

	private void initListAdapter() {
	}

	private void initView(View view) {
		mIntegralRecordListView =  (ListView) view.findViewById(R.id.fm_messagefragment_listView);
		mIntegralRecordImageView = (ImageView) view.findViewById(R.id.fm_messagefragment_iv_empty);
	}


}
