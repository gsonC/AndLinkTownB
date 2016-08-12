package com.lianbi.mezone.b.ui;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lianbi.mezone.b.bean.TagMessage;
import com.xizhi.mezone.b.R;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.com.hgh.baseadapter.BaseAdapterHelper;
import cn.com.hgh.baseadapter.QuickAdapter;
import cn.com.hgh.view.ContainsEmojiEditText;
import cn.com.hgh.view.SlideListView2;

public class TagManagerActivity extends BaseActivity {

	@Bind(R.id.fm_messagefragment_listView)
	SlideListView2 fmMessagefragmentListView;
	@Bind(R.id.tv_searchtag)
	ContainsEmojiEditText tvSearchtag;
	@Bind(R.id.bt_sure)
	TextView btSure;
	@Bind(R.id.point_biaoqian)
	RelativeLayout pointBiaoqian;
	@Bind(R.id.fm_tag_listView)
	ListView fmTagListView;
	private SlideListView2 fm_messagefragment_listView;
	ArrayList<TagMessage> mDatas = new ArrayList<TagMessage>();
	private TextView tv_tag;
	private EditText tv_searchtag;
	private TextView bt_sure;
	ListView fm_tag_listView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tag, NOTYPE);
		ButterKnife.bind(this);
		initview();
		initListAdapter();
		listen();
	}

	private void initview() {
		setPageTitle("标签管理");
		tv_searchtag = (EditText) findViewById(R.id.tv_searchtag);
		bt_sure = (TextView) findViewById(R.id.bt_sure);
		fm_messagefragment_listView = (SlideListView2) findViewById(R.id.fm_messagefragment_listView);
		fm_messagefragment_listView.initSlideMode(SlideListView2.MOD_RIGHT);
		fm_tag_listView = (ListView) findViewById(R.id.fm_tag_listView);
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

	public QuickAdapter<TagMessage> mAdapter;

	private void initListAdapter() {
		mAdapter = new QuickAdapter<TagMessage>(TagManagerActivity.this, R.layout.activity_tag_manager, mDatas) {

			@Override
			protected void convert(final BaseAdapterHelper helper, final TagMessage item) {


				tv_tag = helper.getView(R.id.tv_tag);


				tv_tag.setText(item.getTv_tagmessage());


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


							}
						});


			}

		};

		fm_tag_listView.setAdapter(mAdapter);
	}

}
