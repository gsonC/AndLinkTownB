package com.lianbi.mezone.b.ui;

import android.os.Bundle;
import android.widget.TextView;

import com.xizhi.mezone.b.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.com.hgh.view.AbPullToRefreshView;
import cn.com.hgh.view.ContainsEmojiEditText;
import cn.com.hgh.view.SlideListView2;

/**
 * 服务设置
 */
public class ServiceSetingActivity extends BaseActivity {

	@Bind(R.id.fm_serviceSet_listView)
	SlideListView2 fmServiceSetListView;
	@Bind(R.id.act_serviceSet_abpulltorefreshview)
	AbPullToRefreshView actServiceSetAbpulltorefreshview;
	@Bind(R.id.tv_serviceSeting_searchtag)
	ContainsEmojiEditText tvServiceSetingSearchtag;
	@Bind(R.id.bt_serviceSeting_sure)
	TextView btServiceSetingSure;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_service_seting, NOTYPE);
		ButterKnife.bind(this);
		initView();
	}

	private void initView() {
		setPageTitle("服务设置");
		actServiceSetAbpulltorefreshview.setLoadMoreEnable(true);
		actServiceSetAbpulltorefreshview.setPullRefreshEnable(true);
		actServiceSetAbpulltorefreshview.setOnHeaderRefreshListener(new AbPullToRefreshView.OnHeaderRefreshListener() {
			@Override
			public void onHeaderRefresh(AbPullToRefreshView view) {

			}
		});
		actServiceSetAbpulltorefreshview.setOnFooterLoadListener(new AbPullToRefreshView.OnFooterLoadListener() {
			@Override
			public void onFooterLoad(AbPullToRefreshView view) {

			}
		});
	}
}
