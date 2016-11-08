package com.lianbi.mezone.b.ui;


import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.lianbi.mezone.b.bean.Consumption;
import com.xizhi.mezone.b.R;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.com.hgh.baseadapter.BaseAdapterHelper;
import cn.com.hgh.baseadapter.QuickAdapter;
import cn.com.hgh.view.AbPullToRefreshView;

/**
 * 消费结算
 */
public class ConsumptionSettlementActivity extends BaseActivity {

	@Bind(R.id.act_cumption_listview)
	ListView actCumptionListview;
	@Bind(R.id.act_cumption_abpulltorefreshview)
	AbPullToRefreshView actCumptionAbpulltorefreshview;
	@Bind(R.id.img_cumption_empty)
	ImageView imgCumptionEmpty;
   private int page=1;
	ArrayList<Consumption> mDatas=new ArrayList<Consumption>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_consumption_settlement, NOTYPE);
		ButterKnife.bind(this);
		initview();
		initListAdapter();
	}

	private void initview() {
		setPageTitle("消费结算");
		//刷新设置
		actCumptionAbpulltorefreshview.setLoadMoreEnable(true);
		actCumptionAbpulltorefreshview.setPullRefreshEnable(true);
		actCumptionAbpulltorefreshview.setOnHeaderRefreshListener(new AbPullToRefreshView.OnHeaderRefreshListener() {
			@Override
			public void onHeaderRefresh(AbPullToRefreshView view) {

			}
		});
		//尾部刷新设置
		actCumptionAbpulltorefreshview.setOnFooterLoadListener(new AbPullToRefreshView.OnFooterLoadListener() {
			@Override
			public void onFooterLoad(AbPullToRefreshView view) {

			}
		});
	}

	//初始化适配器
	public QuickAdapter<Consumption> mAdapter;

	private void initListAdapter() {
		mAdapter = new QuickAdapter<Consumption>(this, R.layout.activity_consumption_item) {
			@Override
			protected void convert(BaseAdapterHelper helper, Consumption item) {
				TextView tv_consum_total=helper.getView(R.id.tv_consum_total);
				TextView tv_consum_time=helper.getView(R.id.tv_consum_time);
				TextView tv_consum_price=helper.getView(R.id.tv_consum_price);
				TextView tv_consum_daytime=helper.getView(R.id.tv_consum_daytime);
				TextView tv_consum_where=helper.getView(R.id.tv_consum_where);
				TextView tv_consum_shoukuan=helper.getView(R.id.tv_consum_shoukuan);
			}
		};
		actCumptionListview.setAdapter(mAdapter);
	}


}