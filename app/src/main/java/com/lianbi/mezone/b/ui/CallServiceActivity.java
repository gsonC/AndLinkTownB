package com.lianbi.mezone.b.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.lianbi.mezone.b.bean.CallService;
import com.lianbi.mezone.b.httpresponse.MyResultCallback;
import com.lianbi.mezone.b.httpresponse.OkHttpsImp;
import com.xizhi.mezone.b.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.com.hgh.baseadapter.BaseAdapterHelper;
import cn.com.hgh.baseadapter.QuickAdapter;
import cn.com.hgh.utils.AbDateUtil;
import cn.com.hgh.utils.AbPullHide;
import cn.com.hgh.utils.AbStrUtil;
import cn.com.hgh.utils.Result;
import cn.com.hgh.view.AbPullToRefreshView;

/**
 * 呼叫服务
 */
public class CallServiceActivity extends BaseActivity {

	@Bind(R.id.tv_all)
	TextView tvAll;
	@Bind(R.id.all_container)
	FrameLayout allContainer;
	@Bind(R.id.tv_vaild)
	TextView tvVaild;
	@Bind(R.id.valid_container)
	FrameLayout validContainer;
	@Bind(R.id.tv_invalid)
	TextView tvInvalid;
	@Bind(R.id.invalid_container)
	FrameLayout invalidContainer;
	@Bind(R.id.iv_emptyact_calldetail)
	ImageView ivEmptyactCalldetail;
	@Bind(R.id.callservice_list)
	ListView callserviceList;
	@Bind(R.id.pulltorefresh_calllist)
	AbPullToRefreshView pulltorefreshCalllist;

	private int currShowingIs;
	private static final int ALL_IS_SHOWING = 0;
	private static final int VALID_IS_SHOWING = 1;
	private static final int INVALID_IS_SHOWING = -1;
	private int page = 1;
	private ArrayList<CallService> mDatas = new ArrayList<CallService>();
	private List<CallService> mData = new ArrayList<>();
	private List<CallService> mValideData = new ArrayList<>();
	private List<CallService> mInvalideData = new ArrayList<>();
	/**
	 * 正在下拉刷新.
	 */
	private boolean mPullRefreshing = false;
	/**
	 * 正在加载更多.
	 */
	private boolean mPullLoading = false;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_call_service, NOTYPE);
		ButterKnife.bind(this);
		initView();
		initAdapter();

	}
	@Override
	protected void onResume() {
		super.onResume();

		getPushMessages(true,"");
	}

	private void initView() {
		setPageTitle("呼叫服务");
		setPageRightText("服务设置");
		currShowingIs = ALL_IS_SHOWING;

	}

	@Override
	public void onClick(View view) {
		super.onClick(view);
		switch (view.getId()) {
			case R.id.all_container:
				allContainer.setBackgroundColor(getResources().getColor((R.color.color_3987fd)));
				tvAll.setBackgroundColor(getResources().getColor((R.color.color_3987fd)));
				validContainer.setBackgroundColor(getResources().getColor((R.color.white)));
				tvVaild.setBackgroundColor(getResources().getColor((R.color.colores_news_10)));
				invalidContainer.setBackgroundColor(getResources().getColor((R.color.white)));
				tvInvalid.setBackgroundColor(getResources().getColor((R.color.colores_news_10)));
				switchAdapter();
				break;
			case R.id.valid_container:
				allContainer.setBackgroundColor(getResources().getColor((R.color.white)));
				tvAll.setBackgroundColor(getResources().getColor((R.color.colores_news_10)));
				validContainer.setBackgroundColor(getResources().getColor((R.color.color_3987fd)));
				tvVaild.setBackgroundColor(getResources().getColor((R.color.color_3987fd)));
				invalidContainer.setBackgroundColor(getResources().getColor((R.color.white)));
				tvInvalid.setBackgroundColor(getResources().getColor((R.color.colores_news_10)));
				switchAdapter();
				break;
			case R.id.invalid_container:
				allContainer.setBackgroundColor(getResources().getColor((R.color.white)));
				tvAll.setBackgroundColor(getResources().getColor((R.color.colores_news_10)));
				validContainer.setBackgroundColor(getResources().getColor((R.color.white)));
				tvVaild.setBackgroundColor(getResources().getColor((R.color.colores_news_10)));
				invalidContainer.setBackgroundColor(getResources().getColor((R.color.color_3987fd)));
				tvInvalid.setBackgroundColor(getResources().getColor((R.color.color_3987fd)));
				switchAdapter();
				break;
		}
	}

	private void switchAdapter() {
		switch (currShowingIs) {
			case ALL_IS_SHOWING:
				showingSelect(mData);
				break;
			case VALID_IS_SHOWING:
				showingSelect(mValideData);
				break;

			case INVALID_IS_SHOWING:
				showingSelect(mInvalideData);

				break;

		}

	}

	ListView listView;

	private void showingSelect(List<CallService> list) {
		if (list.isEmpty()) {
			listView.setVisibility(View.GONE);
			callserviceList.setVisibility(View.VISIBLE);
		} else {
			listView.setVisibility(View.VISIBLE);
			callserviceList.setVisibility(View.GONE);
			mAdapter.replaceAll(list);
		}

	}


	@Override
	protected void onTitleRightClickTv() {
		super.onTitleRightClickTv();
		startActivity(new Intent(this, CallSetActivity.class));
	}

	/**
	 * 初始化适配器
	 */
	private QuickAdapter<CallService> mAdapter;

	private void initAdapter() {
		mAdapter = new QuickAdapter<CallService>(this, R.layout.consumption_item) {
			@Override
			protected void convert(BaseAdapterHelper helper, CallService item) {
				TextView tv_callset_table = helper.getView(R.id.tv_callset_table);
				TextView tv_callset_content = helper.getView(R.id.tv_callset_content);
				TextView tv_callset_time = helper.getView(R.id.tv_callset_time);
				TextView tv_callset_yartime = helper.getView(R.id.tv_callset_yartime);
				TextView tv_callset_deal = helper.getView(R.id.tv_callset_deal);

				tv_callset_yartime.setText(item.getTv_callset_yartime());
				tv_callset_table.setText(item.getTv_callset_table());
				tv_callset_content.setText(item.getTv_callset_content());
				tv_callset_table.setText(item.getTv_callset_table());
				helper.getView(R.id.tv_callset_deal).setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {

					}
				});
			}
		};
	}

	/**
	 * 4.13	查询推送消息
	 */

	private void getPushMessages(final boolean isResh, String isRead) {

		String reqTime = AbDateUtil.getDateTimeNow();
		String uuid = AbStrUtil.getUUID();
		try {
			okHttpsImp.getPushMessages(uuid, "app", reqTime, OkHttpsImp.md5_key, userShopInfoBean.getBusinessId(), isRead, new MyResultCallback<String>() {
				@Override
				public void onResponseResult(Result result) {
					String reString = result.getData();
					mDatas.clear();
					if (!reString.isEmpty()) {
						try {
							JSONObject jsonObject = new JSONObject(reString);
							reString = jsonObject.getString("msgList");
							ArrayList<CallService> mDatasL = (ArrayList<CallService>) JSON.parseArray(reString, CallService.class);
							if (mDatasL != null && mDatasL.size() > 0) {
								mDatas.addAll(mDatasL);
							}
							if (mDatas != null && mDatas.size() > 0) {
								pulltorefreshCalllist.setVisibility(View.VISIBLE);
								ivEmptyactCalldetail.setVisibility(View.GONE);
							}else{
								pulltorefreshCalllist.setVisibility(View.GONE);
								ivEmptyactCalldetail.setVisibility(View.VISIBLE);
							}
							AbPullHide.hideRefreshView(isResh,pulltorefreshCalllist);
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
				}

				@Override
				public void onResponseFailed(String msg) {
                      if(isResh){
						  pulltorefreshCalllist.setVisibility(View.GONE);
						  ivEmptyactCalldetail.setVisibility(View.VISIBLE);
					  }
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
		{

		}

	}

}
