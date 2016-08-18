package com.lianbi.mezone.b.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.lianbi.mezone.b.bean.MemberMessage;
import com.xizhi.mezone.b.R;

import java.util.ArrayList;

import butterknife.ButterKnife;
import cn.com.hgh.baseadapter.BaseAdapterHelper;
import cn.com.hgh.baseadapter.QuickAdapter;
import cn.com.hgh.utils.AbAppUtil;
import cn.com.hgh.utils.ScreenUtils;
import cn.com.hgh.view.SlideListView2;

public class MemberPointManage extends BaseActivity implements OnClickListener {
	ArrayList<MemberMessage> mDatas = new ArrayList<MemberMessage>();

	private TextView tv_tag, point_goodsName, rated, trated, goodsPoint, pullgoods, pushgoods,
			changegoods, tv_increaseProduct, choose_from_weixin;
	private SlideListView2 fm_messagefragment_listView;
	ImageView point_ima, pullgoodsIma;
	ListView fm_tag_listView;
   private EditText tv_search;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_member_point_manage, NOTYPE);
		ButterKnife.bind(this);
		initView();
		initListAdapter();
		setListen();
		setLisenter();
		getTagList();
		viewAdapter();
	}

	private void initView() {
		setPageTitle("积分商品");
		fm_messagefragment_listView = (SlideListView2) findViewById(R.id.fm_messagefragment_listView);
		fm_messagefragment_listView.initSlideMode(SlideListView2.MOD_RIGHT);
		fm_tag_listView = (ListView) findViewById(R.id.fm_tag_listView);

		tv_increaseProduct = (TextView) findViewById(R.id.tv_increaseProduct);
		choose_from_weixin = (TextView) findViewById(R.id.choose_from_weixin);
		tv_search=(EditText)findViewById(R.id.tv_search);
	}

	private void setListen() {
		tv_increaseProduct.setOnClickListener(this);
		choose_from_weixin.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
			//新增积分商品
			case R.id.tv_increaseProduct:
				startActivity(new Intent(MemberPointManage.this, NewIntegralGoodsActivity.class));
				break;
			//从微信商城产品库选择
			case R.id.choose_from_weixin:
				startActivity(new Intent(MemberPointManage.this, ChooseFromWeixinActivity.class));
				break;
		}
	}
	/**
	 * View适配
	 */
	private void viewAdapter() {
		ScreenUtils.textAdaptationOn720(tv_increaseProduct,this,25);//本周新增会员
		ScreenUtils.textAdaptationOn720(choose_from_weixin,this,25);//累计会员数

	}

	/**
	 * 添加监听
	 */
	private void setLisenter() {
		// 根据输入框输入值的改变来过滤搜索
		tv_search.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// 当输入框里面的值为空，更新为原来的列表，否则为过滤数据列表

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {

			}

			@Override
			public void afterTextChanged(Editable s) {
			}
		});
		tv_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {

			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_UNSPECIFIED) {
					String response = tv_search.getText().toString().trim();

				}
				AbAppUtil.closeSoftInput(MemberPointManage.this);
				return false;
			}
		});

	}
//初始化适配器

	public QuickAdapter<MemberMessage> mAdapter;

	private void initListAdapter() {
		mAdapter = new QuickAdapter<MemberMessage>(MemberPointManage.this, R.layout.activity_point_manager, mDatas) {

			@Override
			protected void convert(final BaseAdapterHelper helper, final MemberMessage item) {
				changegoods = helper.getView(R.id.changegoods);
				point_ima = helper.getView(R.id.point_ima);
				point_goodsName = helper.getView(R.id.point_goodsName);
				rated = helper.getView(R.id.rated);
				trated = helper.getView(R.id.trated);
				goodsPoint = helper.getView(R.id.goodsPoint);
				pullgoods = helper.getView(R.id.pullgoods);
				pullgoodsIma = helper.getView(R.id.pullgoodsIma);
				pushgoods = helper.getView(R.id.pushgoods);
				changegoods = helper.getView(R.id.changegoods);


				changegoods.setText(item.getChangegoods() + "");
				point_goodsName.setText(item.getPoint_goodsName() + "");
				rated.setText(item.getRated() + "");
				trated.setText(item.getTrated() + "");
				goodsPoint.setText(item.getGoodsPoint() + "");
				pullgoods.setText(item.getPullgoods() + "");
				pushgoods.setText(item.getPushgoods() + "");
				changegoods.setText(item.getChangegoods() + "");


				adaption();

				//修改点击事件
				changegoods.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						startActivity(new Intent(MemberPointManage.this, RevisionsActivity.class));
					}
				});
               /*侧滑的点击事件
                */
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
	/**
	 *
	 */
	  private void adaption(){
		  ScreenUtils.textAdaptationOn720(point_goodsName,MemberPointManage.this,24);//
		  ScreenUtils.textAdaptationOn720(rated,MemberPointManage.this,24);//
		  ScreenUtils.textAdaptationOn720(trated,MemberPointManage.this,24);//
		  ScreenUtils.textAdaptationOn720(goodsPoint,MemberPointManage.this,24);//
		  ScreenUtils.textAdaptationOn720(pullgoods,MemberPointManage.this,24);//
		  ScreenUtils.textAdaptationOn720(pushgoods,MemberPointManage.this,24);//
		  ScreenUtils.textAdaptationOn720(changegoods,MemberPointManage.this,24);
		  ScreenUtils.textAdaptationOn720(pullgoods,MemberPointManage.this,24);
	  }


	private void getTagList() {
		ArrayList<MemberMessage> mDatasL = new ArrayList<MemberMessage>();
		for (int i = 0; i < 20; i++) {
			MemberMessage bean = new MemberMessage();
			bean.setPoint_goodsName("你好" + i);
			bean.setRated("你好" + i);
			bean.setTrated("你好" + i);
			bean.setGoodsPoint("你好" + i);
			bean.setPullgoods("你好" + i);
			bean.setPushgoods("你好" + i);
			bean.setChangegoods("你好" + i);
			bean.setTv_tag("你好" + i);


			mDatasL.add(bean);
		}
		if (mDatasL.size() > 0) {
			mDatas.addAll(mDatasL);
		}
		mAdapter.replaceAll(mDatas);

	}

}


