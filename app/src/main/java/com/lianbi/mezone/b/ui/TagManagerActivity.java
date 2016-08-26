package com.lianbi.mezone.b.ui;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.lianbi.mezone.b.bean.SelectTagBean;
import com.lianbi.mezone.b.httpresponse.MyResultCallback;
import com.lianbi.mezone.b.httpresponse.OkHttpsImp;
import com.xizhi.mezone.b.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.ButterKnife;
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

public class TagManagerActivity extends BaseActivity {

	private AbPullToRefreshView act_Tag_abpulltorefreshview;
	private SlideListView2 fm_Tag_listView;
	ArrayList<SelectTagBean> mDatas = new ArrayList<SelectTagBean>();
	private TextView tv_tag, bt_sure;
	private EditText tv_searchtag;
	private int page = 1;
	private String paramLike;
	private String labelId;
	private ImageView imgTagmanagerEmpty;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tag, NOTYPE);
		ButterKnife.bind(this);
		initview();
		initListAdapter();
		setLisenter();
		getvipLabel(true);
		//	getTagList();
	}

	@Override
	protected void onResume() {
		super.onResume();

	}

	private void initview() {
		setPageTitle("标签管理");
		bt_sure = (TextView) findViewById(R.id.bt_sure);
		tv_searchtag = (EditText) findViewById(R.id.tv_searchtag);
		imgTagmanagerEmpty = (ImageView) findViewById(R.id.img_tagmanager_empty);
		fm_Tag_listView = (SlideListView2) findViewById(R.id.fm_Tag_listView);
		fm_Tag_listView.initSlideMode(SlideListView2.MOD_RIGHT);
		act_Tag_abpulltorefreshview = (AbPullToRefreshView) findViewById(R.id.act_Tag_abpulltorefreshview);//AbPullToRefreshView

	}

	private void setLisenter() {
		act_Tag_abpulltorefreshview.setLoadMoreEnable(true);
		act_Tag_abpulltorefreshview.setPullRefreshEnable(true);
		act_Tag_abpulltorefreshview.setOnHeaderRefreshListener(new AbPullToRefreshView.OnHeaderRefreshListener() {

			@Override
			public void onHeaderRefresh(AbPullToRefreshView view) {
				getvipLabel(true);
				//getTagList();
			}

		});
		act_Tag_abpulltorefreshview.setOnFooterLoadListener(new AbPullToRefreshView.OnFooterLoadListener() {

			@Override
			public void onFooterLoad(AbPullToRefreshView view) {
				getvipLabel(false);
				//getTagList();
			}
		});
		bt_sure.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (tv_searchtag.getText().toString() != null) {
					getAddvipLabel();

				}
			}
		});
	}


//初始化适配器

	public QuickAdapter<SelectTagBean> mAdapter;

	private void initListAdapter() {
		mAdapter = new QuickAdapter<SelectTagBean>(TagManagerActivity.this, R.layout.activity_tag_manager, mDatas) {

			@Override
			protected void convert(BaseAdapterHelper helper, final SelectTagBean item) {
				tv_tag = helper.getView(R.id.tv_tag);
				ScreenUtils.textAdaptationOn720(tv_tag, TagManagerActivity.this, 24);
				tv_tag.setText(item.getLabelName());
				System.out.println("tv_tag" + tv_tag);
				labelId = String.valueOf(item.getLabelId());
				helper.getView(R.id.tv_chdelete).setOnClickListener(// 删除
						new OnClickListener() {

							@Override
							public void onClick(View v) {

								fm_Tag_listView.slideBack();
								// 通知服务器
								mDatas.remove(item);
								//Toast.makeText(mActivity, "删除", 0).show();
								mAdapter.replaceAll(mDatas);
								ArrayList<String> ids = new ArrayList<String>();
								ids.add(String.valueOf(item.getLabelId()));
								//	delteLeaveMsg(ids,true);
								DeletevipLabel();
							}
						});
			}

		};

		fm_Tag_listView.setAdapter(mAdapter);
	}

	/**
	 * 获取会员标签列表
	 */

	private void getvipLabel(final boolean isResh) {
		if (isResh) {
			page = 1;
			mDatas.clear();
			mAdapter.replaceAll(mDatas);
		}
		String reqTime = AbDateUtil.getDateTimeNow();
		String uuid = AbStrUtil.getUUID();
		try {
			okHttpsImp.getMemberTag(uuid, "app", reqTime, OkHttpsImp.md5_key, userShopInfoBean.getBusinessId(), page + "", 20 + "", new MyResultCallback<String>() {

				@Override
				public void onResponseResult(Result result) {
					page++;
					String reString = result.getData();
					if (reString != null) {
						try {
							JSONObject jsonObject = new JSONObject(reString);
							reString = jsonObject.getString("vipLabelList");
							ArrayList<SelectTagBean> mDatasL = (ArrayList<SelectTagBean>) JSON.parseArray(reString, SelectTagBean.class);
							System.out.println("mDatasL" + mDatasL);
							if (mDatasL != null && mDatasL.size() > 0) {

								mDatas.addAll(mDatasL);

								System.out.println("mDatas" + mDatas);
							}
							if (mDatas != null && mDatas.size() > 0) {
								imgTagmanagerEmpty.setVisibility(View.GONE);
								act_Tag_abpulltorefreshview.setVisibility(View.VISIBLE);
							} else {
								imgTagmanagerEmpty.setVisibility(View.VISIBLE);
								act_Tag_abpulltorefreshview.setVisibility(View.GONE);

							}
							AbPullHide.hideRefreshView(isResh, act_Tag_abpulltorefreshview);
							mAdapter.replaceAll(mDatas);
							System.out.println("mAdapter" + mAdapter);
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
				}

				@Override
				public void onResponseFailed(String msg) {
					if (isResh) {
						imgTagmanagerEmpty.setVisibility(View.VISIBLE);
						act_Tag_abpulltorefreshview.setVisibility(View.GONE);
					}
					AbPullHide.hideRefreshView(isResh, act_Tag_abpulltorefreshview);
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 添加会员标签
	 */

	String searchtag;

	private void getAddvipLabel() {
		searchtag = tv_searchtag.getText().toString();
		String reqTime = AbDateUtil.getDateTimeNow();
		String uuid = AbStrUtil.getUUID();
		try {
			okHttpsImp.AddMemberTag(uuid, "app", reqTime, OkHttpsImp.md5_key, userShopInfoBean.getBusinessId(), searchtag, new MyResultCallback<String>() {

				@Override
				public void onResponseResult(Result result) {
					tv_searchtag.setText("");
					getvipLabel(true);
					ContentUtils.showMsg(TagManagerActivity.this, "标签添加成功");

				}

				@Override
				public void onResponseFailed(String msg) {
					ContentUtils.showMsg(TagManagerActivity.this, "标签添加失败");
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
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
					getvipLabel(true);
					ContentUtils.showMsg(TagManagerActivity.this, "删除标签成功");
				}

				@Override
				public void onResponseFailed(String msg) {
					ContentUtils.showMsg(TagManagerActivity.this, "删除标签失败");
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
		{

		}

	}


}

