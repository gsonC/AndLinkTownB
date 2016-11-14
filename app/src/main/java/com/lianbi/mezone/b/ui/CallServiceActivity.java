package com.lianbi.mezone.b.ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.lianbi.mezone.b.bean.CallService;
import com.lianbi.mezone.b.httpresponse.MyResultCallback;
import com.lianbi.mezone.b.httpresponse.OkHttpsImp;
import com.xizhi.mezone.b.R;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.hgh.baseadapter.BaseAdapterHelper;
import cn.com.hgh.baseadapter.QuickAdapter;
import cn.com.hgh.eventbus.ShouyeRefreshEvent;
import cn.com.hgh.utils.AbDateUtil;
import cn.com.hgh.utils.AbStrUtil;
import cn.com.hgh.utils.ContentUtils;
import cn.com.hgh.utils.Result;
import cn.com.hgh.view.AbPullToRefreshView;
import cn.com.hgh.view.SlideListView2;

/**
 * 呼叫服务
 */
public class CallServiceActivity extends BaseActivity implements AbPullToRefreshView.OnFooterLoadListener, AbPullToRefreshView.OnHeaderRefreshListener {

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

	@Bind(R.id.pulltorefresh_calllist)
	AbPullToRefreshView pulltorefreshCalllist;
	@Bind(R.id.fm_call_listView)
	SlideListView2 fmCallListView;
	private String labelId;
	private QuickAdapter<CallService> mAdapter;
	private TextView tv_callset_deal;
	private int currShowingIs;
	private static final int ALL_IS_SHOWING = 0;
	private static final int VALID_IS_SHOWING = 1;
	private static final int INVALID_IS_SHOWING = -1;
	private List<CallService> mData = new ArrayList<>();//全部
	private List<CallService> mValideData = new ArrayList<>();//已经处理
	private List<CallService> mInvalideData = new ArrayList<>();//未处理
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
		getPushMessages();
	}

	private void initView() {
		setPageTitle("呼叫服务");
		setPageRightText("服务设置");
		setPageRightTextColor(Color.parseColor("#3987fd"));
		currShowingIs = ALL_IS_SHOWING;
		pulltorefreshCalllist.setOnFooterLoadListener(this);
		pulltorefreshCalllist.setOnHeaderRefreshListener(this);
		fmCallListView.initSlideMode(SlideListView2.MOD_RIGHT);

	}

	@Override
	@OnClick({R.id.all_container, R.id.valid_container, R.id.invalid_container})
	public void onClick(View view) {
		super.onClick(view);
		switch (view.getId()) {
			case R.id.all_container:
				allContainer.setBackgroundColor(getResources().getColor((R.color.color_3987fd)));
				tvAll.setTextColor(getResources().getColor((R.color.color_3987fd)));
				validContainer.setBackgroundColor(getResources().getColor((R.color.white)));
				tvVaild.setTextColor(getResources().getColor((R.color.colores_news_10)));
				invalidContainer.setBackgroundColor(getResources().getColor((R.color.white)));
				tvInvalid.setTextColor(getResources().getColor((R.color.colores_news_10)));
				currShowingIs = ALL_IS_SHOWING;
				switchAdapter();
				break;
			case R.id.valid_container:
				allContainer.setBackgroundColor(getResources().getColor((R.color.white)));
				tvAll.setTextColor(getResources().getColor((R.color.colores_news_10)));
				validContainer.setBackgroundColor(getResources().getColor((R.color.color_3987fd)));
				tvVaild.setTextColor(getResources().getColor((R.color.color_3987fd)));
				invalidContainer.setBackgroundColor(getResources().getColor((R.color.white)));
				tvInvalid.setTextColor(getResources().getColor((R.color.colores_news_10)));
				currShowingIs = VALID_IS_SHOWING;
				switchAdapter();
				break;
			case R.id.invalid_container:
				allContainer.setBackgroundColor(getResources().getColor((R.color.white)));
				tvAll.setTextColor(getResources().getColor((R.color.colores_news_10)));
				validContainer.setBackgroundColor(getResources().getColor((R.color.white)));
				tvVaild.setTextColor(getResources().getColor((R.color.colores_news_10)));
				invalidContainer.setBackgroundColor(getResources().getColor((R.color.color_3987fd)));
				tvInvalid.setTextColor(getResources().getColor((R.color.color_3987fd)));
				currShowingIs = INVALID_IS_SHOWING;
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

	private void showingSelect(List<CallService> list) {
		if (list.isEmpty()) {
			fmCallListView.setVisibility(View.GONE);
			ivEmptyactCalldetail.setVisibility(View.VISIBLE);
		} else {
			fmCallListView.setVisibility(View.VISIBLE);
			ivEmptyactCalldetail.setVisibility(View.GONE);
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
	private void initAdapter() {
		mAdapter = new QuickAdapter<CallService>(this, R.layout.consumption_item, mData) {
			@Override
			protected void convert(BaseAdapterHelper helper, final CallService item) {
				TextView tv_callset_table = helper.getView(R.id.tv_callset_table);
				TextView tv_callset_content = helper.getView(R.id.tv_callset_content);
				TextView tv_callset_time = helper.getView(R.id.tv_callset_time);
				TextView tv_callset_yartime = helper.getView(R.id.tv_callset_yartime);
				tv_callset_deal = helper.getView(R.id.tv_callset_deal);

				tv_callset_yartime.setText(item.getModifyTime());
				tv_callset_table.setText(item.getTableName());
				tv_callset_content.setText(item.getMsgContent());
				helper.getView(R.id.tv_chdelete).setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						fmCallListView.slideBack();
						labelId=item.getPushId();
						DeletevipLabel();
					}
				});
				tv_callset_deal.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						getMessage(item.getPushId());

					}
				});
			}
		};
		fmCallListView.setAdapter(mAdapter);
	}

	/**
	 * 4.13	查询推送消息
	 */
	private void getPushMessages() {

		String reqTime = AbDateUtil.getDateTimeNow();
		String uuid = AbStrUtil.getUUID();
		try {
			okHttpsImp.getPushMessages(uuid, "app", reqTime, OkHttpsImp.md5_key, "BD2016072816282300000309", "", new MyResultCallback<String>() {
				@Override
				public void onResponseResult(Result result) {
					String reString = result.getData();
					mData.clear();
					mInvalideData.clear();
					mValideData.clear();
					if (!reString.isEmpty()) {
						try {
							JSONObject jsonObject = new JSONObject(reString);
							reString = jsonObject.getString("msgList");
							ArrayList<CallService> mDatasL = (ArrayList<CallService>) JSON.parseArray(reString, CallService.class);
							if (mDatasL.size() > 0) {
								mData.addAll(mDatasL);
							}
							for (CallService bean : mDatasL) {
								if (bean.getIsRead().equals("0")) {
									mInvalideData.add(bean);
								}
								if (bean.getIsRead().equals("1")) {
									mValideData.add(bean);
								}
							}
						} catch (JSONException e) {
							e.printStackTrace();
						}
						switchAdapter();
					}
					refreshingFinish();
				}

				@Override
				public void onResponseFailed(String msg) {
					refreshingFinish();
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 修改推送消息已读状态
	 */
	private void getMessage(String id) {
		okHttpsImp.modifyPushMessage(new MyResultCallback<String>() {

			@Override
			public void onResponseResult(Result result) {
				getPushMessages();
				tv_callset_deal.setText("已处理");
			}

			@Override
			public void onResponseFailed(String msg) {

			}
		}, BaseActivity.userShopInfoBean.getBusinessId(), id);

	}

	@Override
	public void onFooterLoad(AbPullToRefreshView view) {
		if (!mPullRefreshing && !mPullLoading) {
			mPullLoading = true;
			getPushMessages();
		}
	}

	@Override
	public void onHeaderRefresh(AbPullToRefreshView view) {
		if (!mPullRefreshing && !mPullLoading) {
			mPullRefreshing = true;
			getPushMessages();
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		ButterKnife.unbind(this);
		EventBus.getDefault().post(new ShouyeRefreshEvent(false));
	}

	private void refreshingFinish() {
		if (mPullRefreshing) {
			pulltorefreshCalllist.onHeaderRefreshFinish();
			mPullRefreshing = false;
		}
		if (mPullLoading) {
			pulltorefreshCalllist.onFooterLoadFinish();
			mPullLoading = false;
		}
	}
	/**
	 * 删除标签
	 */
	private void DeletevipLabel() {
		String reqTime = AbDateUtil.getDateTimeNow();
		String uuid = AbStrUtil.getUUID();
		try {
			okHttpsImp.DeleteMemberTag(uuid, "app", reqTime, OkHttpsImp.md5_key, userShopInfoBean.getBusinessId(), labelId, new MyResultCallback<String>() {
				@Override
				public void onResponseResult(Result result) {
					getPushMessages();
					ContentUtils.showMsg(CallServiceActivity.this, "删除标签成功");
				}

				@Override
				public void onResponseFailed(String msg) {
					ContentUtils.showMsg(CallServiceActivity.this, "删除标签失败");
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
		{

		}

	}

}
