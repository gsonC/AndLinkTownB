package com.lianbi.mezone.b.ui;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.lianbi.mezone.b.bean.MemberMessage;
import com.lianbi.mezone.b.httpresponse.MyResultCallback;
import com.xizhi.mezone.b.R;

import java.util.ArrayList;

import butterknife.ButterKnife;
import cn.com.hgh.baseadapter.BaseAdapterHelper;
import cn.com.hgh.baseadapter.QuickAdapter;
import cn.com.hgh.utils.ContentUtils;
import cn.com.hgh.utils.Result;
import cn.com.hgh.utils.ScreenUtils;
import cn.com.hgh.view.SlideListView2;

public class TagManagerActivity extends BaseActivity {


	private SlideListView2 fm_messagefragment_listView;
	ArrayList<MemberMessage> mDatas = new ArrayList<MemberMessage>();
	private TextView tv_tag,bt_sure;
	private EditText tv_searchtag;
   private ListView fm_tag_listView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tag, NOTYPE);
		ButterKnife.bind(this);
		initview();
		initListAdapter();
		listen();
		getTagList();
	}

	@Override
	protected void onResume() {
		super.onResume();

	}

	private void initview() {
		setPageTitle("标签管理");
		bt_sure=(TextView)findViewById(R.id.bt_sure);
		tv_searchtag = (EditText) findViewById(R.id.tv_searchtag);
		fm_messagefragment_listView = (SlideListView2) findViewById(R.id.fm_messagefragment_listView);
		fm_messagefragment_listView.initSlideMode(SlideListView2.MOD_RIGHT);
		fm_tag_listView=(ListView)findViewById(R.id.fm_tag_listView);
	}

	private void listen() {

		bt_sure.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (tv_searchtag.getText().toString() != null) {


				}
			}
		});
	}

//初始化适配器

	public QuickAdapter<MemberMessage> mAdapter;

	private void initListAdapter() {
		mAdapter = new QuickAdapter<MemberMessage>(TagManagerActivity.this, R.layout.activity_tag_manager, mDatas) {

			@Override
			protected void convert(BaseAdapterHelper helper, final MemberMessage item) {
				tv_tag = helper.getView(R.id.tv_tag);
				ScreenUtils.textAdaptationOn720(tv_tag, TagManagerActivity.this, 24);
				tv_tag.setText(item.getTv_tag()+"");


				helper.getView(R.id.tv_chdelete).setOnClickListener(// 删除
						new OnClickListener() {

							@Override
							public void onClick(View v) {

								fm_messagefragment_listView.slideBack();
								// 通知服务器
								mDatas.remove(item);
								//Toast.makeText(mActivity, "删除", 0).show();
								mAdapter.replaceAll(mDatas);
								ArrayList<String> ids = new ArrayList<String>();
								ids.add(String.valueOf(item.getId()));
							//	delteLeaveMsg(ids,true);

							}
						});
			}

		};

		fm_tag_listView.setAdapter(mAdapter);
	}

	/**
	 *
	 */
	private void getTagList() {
		ArrayList<MemberMessage> mDatasL = new ArrayList<MemberMessage>();
		for (int i = 0; i < 20; i++) {
			MemberMessage bean = new MemberMessage();
			bean.setTv_tag("你好" + i);

			mDatasL.add(bean);
		}
		if (mDatasL.size() > 0) {
			mDatas.addAll(mDatasL);
		}
		mAdapter.replaceAll(mDatas);

	}
	/**
	 * 删除或审核留言信息
	 */
	public void delteLeaveMsg(ArrayList<String> ids, boolean status) {

		StringBuffer sb = new StringBuffer();
		int s = ids.size();
		if (s > 0) {
			for (int i = 0; i < s; i++) {
				if (i == (s - 1)) {
					sb.append(ids.get(i));
				} else {
					sb.append(ids.get(i) + ",");

				}
			}
			ContentUtils.showMsg(TagManagerActivity.this, sb.toString());
		} else {
			return;
		}
		if (status) {

			okHttpsImp.getDeleteMessages(new MyResultCallback<String>() {

				@Override
				public void onResponseResult(Result result) {

					ContentUtils.showMsg(TagManagerActivity.this, "删除留言成功");
					// 刷新页面
					//getShowMessages();

				}

				@Override
				public void onResponseFailed(String msg) {

				}
			}, BaseActivity.userShopInfoBean.getBusinessId(), sb.toString());

		}
		}
	}

