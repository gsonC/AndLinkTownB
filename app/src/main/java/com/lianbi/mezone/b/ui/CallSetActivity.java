package com.lianbi.mezone.b.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.lianbi.mezone.b.bean.SelectCallBean;
import com.lianbi.mezone.b.httpresponse.MyResultCallback;
import com.lianbi.mezone.b.httpresponse.OkHttpsImp;
import com.xizhi.mezone.b.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cn.com.hgh.baseadapter.BaseAdapterHelper;
import cn.com.hgh.baseadapter.QuickAdapter;
import cn.com.hgh.utils.AbDateUtil;
import cn.com.hgh.utils.AbPullHide;
import cn.com.hgh.utils.AbStrUtil;
import cn.com.hgh.utils.ContentUtils;
import cn.com.hgh.utils.Result;
import cn.com.hgh.utils.ScreenUtils;
import cn.com.hgh.view.AbPullToRefreshView;
import cn.com.hgh.view.SlideListView2;

public class CallSetActivity extends BaseActivity {
	private AbPullToRefreshView act_Call_abpulltorefreshview;
	ArrayList<SelectCallBean> mData = new ArrayList<SelectCallBean>();
	private SlideListView2 fm_Call_listView;
	ArrayList<SelectCallBean> mDatas = new ArrayList<SelectCallBean>();
	private TextView tv_call_setM, bt_call_sure;
	private EditText tv_call;
	private String paramLike;
	private String labelId;
	private ImageView img_callset_empty;
	String inputcallMessage;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_call_set,NOTYPE);

		initview();
		initListAdapter();
		setLisenter();
		getCallMessage(true);
	}
	private void initview() {
		setPageTitle("呼叫设置");
		bt_call_sure = (TextView) findViewById(R.id.bt_call_sure);
		tv_call = (EditText) findViewById(R.id.tv_call);
		img_callset_empty = (ImageView) findViewById(R.id.img_callset_empty);
		fm_Call_listView = (SlideListView2) findViewById(R.id.fm_Call_listView);
		fm_Call_listView.initSlideMode(SlideListView2.MOD_RIGHT);
		act_Call_abpulltorefreshview = (AbPullToRefreshView) findViewById(R.id.act_Call_abpulltorefreshview);//AbPullToRefreshView

	}

	private void setLisenter() {
		act_Call_abpulltorefreshview.setLoadMoreEnable(true);
		act_Call_abpulltorefreshview.setPullRefreshEnable(true);
		act_Call_abpulltorefreshview.setOnHeaderRefreshListener(new AbPullToRefreshView.OnHeaderRefreshListener() {

			@Override
			public void onHeaderRefresh(AbPullToRefreshView view) {
				getCallMessage(true);
			}

		});
		act_Call_abpulltorefreshview.setOnFooterLoadListener(new AbPullToRefreshView.OnFooterLoadListener() {

			@Override
			public void onFooterLoad(AbPullToRefreshView view) {
				getCallMessage(false);
			}
		});
		bt_call_sure.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				inputcallMessage = tv_call.getText().toString().trim();

				if (!TextUtils.isEmpty(inputcallMessage)) {
					getAddcallLabel(inputcallMessage);
				} else {
					ContentUtils.showMsg(CallSetActivity.this, "请输入呼叫内容");
				}

			}
		});
	}


	//初始化适配器

	public QuickAdapter<SelectCallBean> mAdapter;

	private void initListAdapter() {
		mAdapter = new QuickAdapter<SelectCallBean>(this,R.layout.activity_call_manager,mDatas) {

			@Override
			protected void convert(BaseAdapterHelper helper, final SelectCallBean item) {
				tv_call_setM = helper.getView(R.id.tv_call_setM);
				ScreenUtils.textAdaptationOn720(tv_call_setM, CallSetActivity.this, 24);
				tv_call_setM.setText(item.getContent());
				helper.getView(R.id.tv_call_chdelete).setOnClickListener(// 删除
						new View.OnClickListener() {

							@Override
							public void onClick(View v) {

								fm_Call_listView.slideBack();
							//	labelId = item.getId();

								DeletecallLabel(item.getId());
							}
						});
			}

		};

		fm_Call_listView.setAdapter(mAdapter);
	}

	/**
	 * 查询所有未删除的呼叫内容列表
	 */
	private void getCallMessage(final boolean isResh) {

		String reqTime = AbDateUtil.getDateTimeNow();
		String uuid = AbStrUtil.getUUID();
		try {
			okHttpsImp.getTssCallTypeList(uuid, "app", reqTime,
					OkHttpsImp.md5_key,
					userShopInfoBean.getBusinessId(),
					new MyResultCallback<String>() {

				@Override
				public void onResponseResult(Result result) {

					String reString = result.getData();
					mData.clear();
					if (reString != null) {
						try {
							JSONObject jsonObject = new JSONObject(reString);
							reString = jsonObject.getString("typeList");
							ArrayList<SelectCallBean> mDatasL = (ArrayList<SelectCallBean>) JSON.parseArray(reString, SelectCallBean.class);

								mData.addAll(mDatasL);
							updateView(mData);

							if (mDatasL != null && mDatasL.size() > 0) {
								img_callset_empty.setVisibility(View.GONE);
								act_Call_abpulltorefreshview.setVisibility(View.VISIBLE);
							} else {
								img_callset_empty.setVisibility(View.VISIBLE);
								act_Call_abpulltorefreshview.setVisibility(View.GONE);
							}
							AbPullHide.hideRefreshView(isResh, act_Call_abpulltorefreshview);
							mAdapter.replaceAll(mDatas);

						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
				}


						@Override
						public void onResponseFailed(String msg) {
							if (isResh) {
								img_callset_empty.setVisibility(View.VISIBLE);
								act_Call_abpulltorefreshview.setVisibility(View.GONE);
							}
							AbPullHide.hideRefreshView(isResh, act_Call_abpulltorefreshview);

						}
					});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	protected void updateView(ArrayList<SelectCallBean> arrayList) {
		mDatas.clear();
		mDatas.addAll(arrayList);
		mAdapter.replaceAll(mDatas);
	}
	/**
	 * 添加呼叫内容
	 */
	private void getAddcallLabel(String inputcallMessage) {


		String reqTime = AbDateUtil.getDateTimeNow();
		String uuid = AbStrUtil.getUUID();
		try {
			okHttpsImp.AddCallTag(uuid, "app", reqTime, OkHttpsImp.md5_key,
					userShopInfoBean.getBusinessId(), inputcallMessage,
					new MyResultCallback<String>() {

						@Override
						public void onResponseResult(Result result) {
							tv_call.setText("");
							getCallMessage(true);
							ContentUtils.showMsg(CallSetActivity.this, "呼叫内容添加成功");

						}

						@Override
						public void onResponseFailed(String msg) {
							ContentUtils.showMsg(CallSetActivity.this, "呼叫内容失败");
						}
					});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 删除呼叫内容
	 */
	private void DeletecallLabel(String labelId) {
		String reqTime = AbDateUtil.getDateTimeNow();
		String uuid = AbStrUtil.getUUID();
		try {
			okHttpsImp.DeleteCallTag(uuid, "app", reqTime, OkHttpsImp.md5_key,
					labelId,userShopInfoBean.getBusinessId(), new MyResultCallback<String>() {
				@Override
				public void onResponseResult(Result result) {
					getCallMessage(true);
					ContentUtils.showMsg(CallSetActivity.this, "删除呼叫内容成功");
				}

				@Override
				public void onResponseFailed(String msg) {
					ContentUtils.showMsg(CallSetActivity.this, "删除呼叫内容失败");
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
		{

		}

	}

}


