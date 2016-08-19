package com.lianbi.mezone.b.ui;/*
 * @创建者     Administrator
 * @创建时间   2016/8/15 12:26
 * @描述       选择标签
 *
 * @更新者     $Author$ 
 * @更新时间   $Date$
 * @更新描述
 */

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.lianbi.mezone.b.bean.SelectTagBean;
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
import cn.com.hgh.utils.Result;
import cn.com.hgh.view.AbPullToRefreshView;

public class SelectTagActivity extends BaseActivity {


	private AbPullToRefreshView mActSelecttagAbpulltorefreshview;
	private ListView mActSelecttagListview;
	private ImageView mActSelecttagIvEmpty;
	private int page = 0;
	private ArrayList<SelectTagBean> mDatas = new ArrayList<>();
	private QuickAdapter<SelectTagBean> mAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_selecttag, NOTYPE);
		initView();
		setListen();
		initAdapter();
		getTag(true);
	}

	private void initView() {
		setPageTitle("选择标签");
		setPageRightText("确定");
		mActSelecttagAbpulltorefreshview = (AbPullToRefreshView) findViewById(R.id.act_selecttag_abpulltorefreshview);
		mActSelecttagListview = (ListView) findViewById(R.id.act_selecttag_listview);
		mActSelecttagIvEmpty = (ImageView) findViewById(R.id.act_selecttag_iv_empty);
	}

	@Override
	protected void onTitleRightClickTv() {
		super.onTitleRightClickTv();

		ArrayList<String> tagContent = new ArrayList<>();
		ArrayList<String> tagID = new ArrayList<>();
		StringBuffer sb = new StringBuffer();
		StringBuffer sb1 = new StringBuffer();
		for (SelectTagBean stb : mDatas) {
			if (stb.isChecked()) {
				tagID.add(String.valueOf(stb.getLabelId()));
				tagContent.add(String.valueOf(stb.getLabelName()));
			}
		}

		int s = tagID.size();

		if (s > 0) {
			for (int i = 0; i < s; i++) {
				if (i == (s - 1)) {
					sb.append(tagContent.get(i));
					sb1.append(tagID.get(i));
				} else {
					sb.append(tagContent.get(i) + ",");
					sb1.append(tagID.get(i) + ",");
				}
			}
		}

		Intent intent = new Intent();
		intent.putExtra("tagContent", sb.toString());
		intent.putExtra("tagID", sb1.toString());
		setResult(RESULT_OK, intent);
		finish();
	}

	private void setListen() {
		mActSelecttagAbpulltorefreshview.setLoadMoreEnable(true);
		mActSelecttagAbpulltorefreshview.setPullRefreshEnable(true);
		mActSelecttagAbpulltorefreshview
				.setOnHeaderRefreshListener(new AbPullToRefreshView.OnHeaderRefreshListener() {

					@Override
					public void onHeaderRefresh(AbPullToRefreshView view) {
						getTag(true);
					}
				});
		mActSelecttagAbpulltorefreshview
				.setOnFooterLoadListener(new AbPullToRefreshView.OnFooterLoadListener() {

					@Override
					public void onFooterLoad(AbPullToRefreshView view) {
						getTag(false);
					}
				});
		mActSelecttagListview
				.setOnItemClickListener(new AdapterView.OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
											int position, long id) {

					}
				});
	}

	private void initAdapter() {
		mAdapter = new QuickAdapter<SelectTagBean>(this, R.layout.item_selecttag, mDatas) {
			@Override
			protected void convert(final BaseAdapterHelper helper, final SelectTagBean item) {

				LinearLayout llt_selecttag = helper.getView(R.id.llt_selecttag);
				ImageView img_selecttag_check = helper.getView(R.id.img_selecttag_check);
				TextView tv_selecttag_content = helper.getView(R.id.tv_selecttag_content);

				tv_selecttag_content.setText(item.getLabelName() + "");

				if (item.isChecked()) {
					helper.setImageResource(R.id.img_selecttag_check, R.mipmap.message_checked);
				} else {
					helper.setImageResource(R.id.img_selecttag_check,
							R.mipmap.message_unchecked);
				}
				llt_selecttag.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						if (item.isChecked()) {
							item.setChecked(false);
							mAdapter.replaceAll(mDatas);
						} else {
							item.setChecked(true);
							mAdapter.replaceAll(mDatas);
						}
					}
				});

			}
		};
		mActSelecttagListview.setAdapter(mAdapter);
	}

	private void getTag(final boolean isResh) {

		if (isResh) {
			page = 0;
			mDatas.clear();
			mAdapter.replaceAll(mDatas);
		}
		String reqTime = AbDateUtil.getDateTimeNow();
		String uuid = AbStrUtil.getUUID();
		try {
			okHttpsImp.getMemberTag(uuid, "app", reqTime, OkHttpsImp.md5_key
					, userShopInfoBean.getBusinessId(), page + "", 20 + "", new MyResultCallback<String>() {
						@Override
						public void onResponseResult(Result result) {
							page++;
							String reString = result.getData();
							if(!TextUtils.isEmpty(reString)){
								try {
									JSONObject jsonObject = new JSONObject(reString);
									String vipLabelList = (String) jsonObject
											.getString("vipLabelList");
									String dataSize = (String) jsonObject
											.getString("dataSize");
									ArrayList<SelectTagBean> mDatasL = (ArrayList<SelectTagBean>) JSON
											.parseArray(vipLabelList,SelectTagBean.class);
									if (mDatasL.size() > 0) {
										mDatas.addAll(mDatasL);
									}
									if (mDatas != null && mDatas.size() > 0) {
										mActSelecttagIvEmpty.setVisibility(View.GONE);
										mActSelecttagAbpulltorefreshview.setVisibility(View.VISIBLE);
									} else {
										mActSelecttagIvEmpty.setVisibility(View.VISIBLE);
										mActSelecttagAbpulltorefreshview.setVisibility(View.GONE);
									}
									AbPullHide.hideRefreshView(isResh, mActSelecttagAbpulltorefreshview);
									mAdapter.replaceAll(mDatas);
								} catch (JSONException e) {
									e.printStackTrace();
								}

							}
						}

						@Override
						public void onResponseFailed(String msg) {
							if (isResh) {
								mActSelecttagIvEmpty
										.setVisibility(View.VISIBLE);
								mActSelecttagAbpulltorefreshview
										.setVisibility(View.GONE);
							}
							AbPullHide.hideRefreshView(isResh,
									mActSelecttagAbpulltorefreshview);
						}
					});
		} catch (Exception e) {
			e.printStackTrace();
		}



	}
}
