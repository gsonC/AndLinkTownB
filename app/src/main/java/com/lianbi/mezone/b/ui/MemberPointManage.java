package com.lianbi.mezone.b.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.lianbi.mezone.b.bean.TagMessage;
import com.xizhi.mezone.b.R;

import java.util.ArrayList;

import cn.com.hgh.baseadapter.BaseAdapterHelper;
import cn.com.hgh.baseadapter.QuickAdapter;
import cn.com.hgh.view.SlideListView2;

public class MemberPointManage extends BaseActivity {
	ArrayList<TagMessage> mDatas = new ArrayList<TagMessage>();
	private TextView tv_tag,point_goodsName,rated,trated,goodsPoint,pullgoods,pushgoods,changegoods;
	private SlideListView2 fm_messagefragment_listView;
	ImageView point_ima,pullgoodsIma;
	ListView fm_tag_listView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_member_point_manage, NOTYPE);
		initView();
		initListAdapter();
	}

	private void initView() {
		setPageTitle("积分商品");
		fm_messagefragment_listView = (SlideListView2) findViewById(R.id.fm_messagefragment_listView);
		fm_messagefragment_listView.initSlideMode(SlideListView2.MOD_RIGHT);
		fm_tag_listView=(ListView)findViewById(R.id.fm_tag_listView);

	}



//初始化适配器

	public QuickAdapter<TagMessage> mAdapter;

	private void initListAdapter() {
		mAdapter = new QuickAdapter<TagMessage>(MemberPointManage.this, R.layout.activity_point_manager, mDatas) {

			@Override
			protected void convert(final BaseAdapterHelper helper, final TagMessage item) {

				 point_ima=helper.getView(R.id.point_ima);
				 point_goodsName=helper.getView(R.id.point_goodsName);
				 rated=helper.getView(R.id.rated);
				 trated=helper.getView(R.id.trated);
				 goodsPoint=helper.getView(R.id.goodsPoint);
				 pullgoods=helper.getView(R.id.pullgoods);
				 pullgoodsIma=helper.getView(R.id.pullgoodsIma);
				 pushgoods=helper.getView(R.id.pushgoods);
				 changegoods=helper.getView(R.id.changegoods);
				 tv_tag = helper.getView(R.id.tv_tag);


				tv_tag.setText(item.getTv_tagmessage());


				helper.getView(R.id.tv_chdelete).setOnClickListener(// 删除
						new View.OnClickListener() {

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
