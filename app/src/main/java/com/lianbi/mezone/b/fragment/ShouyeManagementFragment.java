package com.lianbi.mezone.b.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
public class ShouyeManagementFragment extends Fragment {

	private MainActivity mActivity;
	private OkHttpsImp mOkHttpsImp;

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
		TextView tv_shouyemanagement_pay = (TextView) view.findViewById(R.id.tv_shouyemanagement_pay);
		TextView tv_shouyemanagement_callservice = (TextView) view.findViewById(R.id.tv_shouyemanagement_callservice);
		TextView tv_shouyemanagement_inshopdetail = (TextView) view.findViewById(R.id.tv_shouyemanagement_inshopdetail);
		TextView tv_shouyemanagement_inshopservice = (TextView) view.findViewById(R.id.tv_shouyemanagement_inshopservice);
		TextView tv_include_title_flow = (TextView) view.findViewById(R.id.ind_shouyemanagement_flow).findViewById(R.id.tv_include_title);
		LinearLayout llt_shouyemanagement_flow_show = (LinearLayout) view.findViewById(R.id.llt_shouyemanagement_flow_show);
		TextView tv_shouyemanagement_flow_detail = (TextView) view.findViewById(R.id.tv_shouyemanagement_flow_detail);
		TextView tv_include_title_membernum = (TextView) view.findViewById(R.id.ind_shouyemanagement_membernum).findViewById(R.id.tv_include_title);
		TextView tv_shouyemanagement_nummember = (TextView) view.findViewById(R.id.tv_shouyemanagement_nummember);
		TextView tv_shouyemanagement_addmember = (TextView) view.findViewById(R.id.tv_shouyemanagement_addmember);
		TextView tv_shouyemanagement_memberdetail = (TextView) view.findViewById(R.id.tv_shouyemanagement_memberdetail);
		TextView tv_include_othertitle_salenum = (TextView) view.findViewById(R.id.ind_shouyemanagement_salenum).findViewById(R.id.tv_include_othertitle);
		TextView tv_include_othertitle = (TextView) view.findViewById(R.id.ind_shouyemanagement_salenum).findViewById(R.id.tv_include_othertitle);
		LinearLayout llt_shouyemanagement_salenum_show = (LinearLayout) view.findViewById(R.id.llt_shouyemanagement_salenum_show);
		System.out.println("测试打印");
	}

	private void setLinten() {
	}

	private void getBitmap(){

	}
}
