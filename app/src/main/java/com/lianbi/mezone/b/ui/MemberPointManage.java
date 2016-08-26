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
import android.widget.TextView.OnEditorActionListener;

import com.alibaba.fastjson.JSON;
import com.lianbi.mezone.b.bean.MemberMessage;
import com.lianbi.mezone.b.httpresponse.MyResultCallback;
import com.lianbi.mezone.b.httpresponse.OkHttpsImp;
import com.xizhi.mezone.b.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cn.com.hgh.baseadapter.BaseAdapterHelper;
import cn.com.hgh.baseadapter.QuickAdapter;
import cn.com.hgh.utils.AbAppUtil;
import cn.com.hgh.utils.AbDateUtil;
import cn.com.hgh.utils.AbPullHide;
import cn.com.hgh.utils.AbStrUtil;
import cn.com.hgh.utils.ContentUtils;
import cn.com.hgh.utils.Result;
import cn.com.hgh.utils.ScreenUtils;
import cn.com.hgh.view.AbPullToRefreshView;
import cn.com.hgh.view.SlideListView2;

public class MemberPointManage extends BaseActivity implements OnClickListener {
	ArrayList<MemberMessage> mDatas = new ArrayList<MemberMessage>();
	private AbPullToRefreshView act_memberpoint_abpulltorefreshview;
	private TextView tv_tag, point_goodsName, rated, trated, goodsPoint, pullgoods, pushgoods,
			changegoods, tv_increaseProduct, choose_from_weixin;
	private SlideListView2 fm_messagefragment_listView;
	ImageView point_ima, pullgoodsIma;
	ListView fm_tag_listView;
   private EditText tv_search;
	ImageView img_memberpoint_empty;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_member_point_manage, NOTYPE);
		initView();
		initListAdapter();
		setListen();
		setLisenter();
		//getTagList(true);
		getQueryProduct(true);
		viewAdapter();
	}

	private void initView() {
		setPageTitle("新增积分商品");
		fm_messagefragment_listView = (SlideListView2) findViewById(R.id.fm_messagefragment_listView);
		fm_messagefragment_listView.initSlideMode(SlideListView2.MOD_RIGHT);

		tv_increaseProduct = (TextView) findViewById(R.id.tv_increaseProduct);
		choose_from_weixin = (TextView) findViewById(R.id.choose_from_weixin);
		tv_search=(EditText) findViewById(R.id.tv_memberpoint_search);//搜索框
		act_memberpoint_abpulltorefreshview = (AbPullToRefreshView) findViewById(R.id.act_memberpoint_abpulltorefreshview);//AbPullToRefreshView
		img_memberpoint_empty = (ImageView) findViewById(R.id.img_memberpoint_empty);//图片

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
				Intent intent=new Intent(MemberPointManage.this, NewIntegralGoodsActivity.class);
				startActivityForResult(intent,RESULT_ADDSHOP);
				break;
			//从微信商城产品库选择
			case R.id.choose_from_weixin:

				startActivityForResult(new Intent(MemberPointManage.this, ChooseFromWeixinActivity.class),RESULT_WEIXIN);
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
		tv_search.setOnEditorActionListener(new OnEditorActionListener() {

			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_UNSPECIFIED) {
					String response = tv_search.getText().toString().trim();

				}
				AbAppUtil.closeSoftInput(MemberPointManage.this);
				return false;
			}
		});
		act_memberpoint_abpulltorefreshview.setLoadMoreEnable(true);
		act_memberpoint_abpulltorefreshview.setPullRefreshEnable(true);
		act_memberpoint_abpulltorefreshview
				.setOnHeaderRefreshListener(new AbPullToRefreshView.OnHeaderRefreshListener() {

					@Override
					public void onHeaderRefresh(AbPullToRefreshView view) {
						//getTagList(true);
						getQueryProduct(true);
					}

				});
		act_memberpoint_abpulltorefreshview
				.setOnFooterLoadListener(new AbPullToRefreshView.OnFooterLoadListener() {

					@Override
					public void onFooterLoad(AbPullToRefreshView view) {
						//getTagList(false);
						getQueryProduct(true);
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
				goodsPoint = helper.getView(R.id.goodsPoint);
				pullgoods = helper.getView(R.id.pullgoods);
				pullgoodsIma = helper.getView(R.id.pullgoodsIma);
				pushgoods = helper.getView(R.id.pushgoods);
				changegoods = helper.getView(R.id.changegoods);


				changegoods.setText(item.getChangegoods() + "");
				point_goodsName.setText(item.getPoint_goodsName() + "");
				rated.setText(item.getRated() + "");
				goodsPoint.setText(item.getGoodsPoint() + "");
				pullgoods.setText(item.getPullgoods() + "");
				pushgoods.setText(item.getPushgoods() + "");
				changegoods.setText(item.getChangegoods() + "");


				//adaption();

				//修改点击事件
				changegoods.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {

						Intent intent=new Intent(MemberPointManage.this, RevisionsActivity.class);
                        startActivityForResult(intent,RESULT_MENMBERCHANGE);
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
		fm_messagefragment_listView.setAdapter(mAdapter);
	}
	private final int RESULT_MENMBERCHANGE=1111;
	private final int RESULT_ADDSHOP=2222;
	private final int RESULT_WEIXIN=3333;

	/**
	 *
	 */
	 /* private void adaption(){
		  ScreenUtils.textAdaptationOn720(point_goodsName,MemberPointManage.this,24);//
		  ScreenUtils.textAdaptationOn720(rated,MemberPointManage.this,24);//
		  ScreenUtils.textAdaptationOn720(trated,MemberPointManage.this,24);//
		  ScreenUtils.textAdaptationOn720(goodsPoint,MemberPointManage.this,24);//
		  ScreenUtils.textAdaptationOn720(pullgoods,MemberPointManage.this,24);//
		  ScreenUtils.textAdaptationOn720(pushgoods,MemberPointManage.this,24);//
		  ScreenUtils.textAdaptationOn720(changegoods,MemberPointManage.this,24);
		  ScreenUtils.textAdaptationOn720(pullgoods,MemberPointManage.this,24);
	  }*/


	/*private void getTagList(final boolean isResh) {
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
		if (mDatas != null && mDatas.size() > 0) {
			img_memberpoint_empty.setVisibility(View.GONE);
			act_memberpoint_abpulltorefreshview.setVisibility(View.VISIBLE);
		} else {
			img_memberpoint_empty.setVisibility(View.VISIBLE);
			act_memberpoint_abpulltorefreshview.setVisibility(View.GONE);
		}
		AbPullHide.hideRefreshView(isResh, act_memberpoint_abpulltorefreshview);
		mAdapter.replaceAll(mDatas);

	}*/

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(resultCode==RESULT_OK){
			switch (resultCode){

				case RESULT_MENMBERCHANGE:
					getQueryProduct(true);
					break;
				case RESULT_ADDSHOP:
					getQueryProduct(true);
					break;
				case RESULT_WEIXIN:
					getQueryProduct(true);
					break;

			}
		}
	}

	/**
	 * 积分商品查询
	 * @param
	 * @param
	 * @param
	 */

	private void getQueryProduct(final boolean isResh)  {
		if(isResh){
			mDatas.clear();
			mAdapter.replaceAll(mDatas);
		}
		String reqTime = AbDateUtil.getDateTimeNow();
		String uuid = AbStrUtil.getUUID();

		try {
			okHttpsImp.QueryProduct(OkHttpsImp.md5_key,
					uuid, "app", reqTime,
					userShopInfoBean.getBusinessId(),
					new MyResultCallback<String>() {
						@Override
						public void onResponseResult(Result result) {
							String reString=result.getData();
							System.out.println("reString11111"+reString);
                     if(!AbStrUtil.isEmpty(reString)){
						 try {
							 JSONObject jsonObject=new JSONObject(reString);
							 reString=jsonObject.getString("Data");
							 ArrayList<MemberMessage> mDatasL= (ArrayList<MemberMessage>) JSON.parseArray(reString,MemberMessage.class);
						   if(mDatasL!=null&&mDatasL.size()>0){
								 mDatas.addAll(mDatasL);

						   }if(mDatasL!=null&&mDatas.size()>0){
								 img_memberpoint_empty.setVisibility(View.GONE);
								 act_memberpoint_abpulltorefreshview.setVisibility(View.VISIBLE);

							 }else{
								 img_memberpoint_empty.setVisibility(View.VISIBLE);
								 act_memberpoint_abpulltorefreshview.setVisibility(View.GONE);
							 }
							 AbPullHide.hideRefreshView(isResh, act_memberpoint_abpulltorefreshview);
							 mAdapter.replaceAll(mDatas);
						  } catch (JSONException e) {
							 e.printStackTrace();
						 }
					 }
						}

						@Override
						public void onResponseFailed(String msg) {
							if (isResh) {
								img_memberpoint_empty.setVisibility(View.VISIBLE);
								act_memberpoint_abpulltorefreshview.setVisibility(View.GONE);
							}
							AbPullHide.hideRefreshView(isResh, act_memberpoint_abpulltorefreshview);
							ContentUtils.showMsg(MemberPointManage.this,"查询积分商品失败");
						}
					});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}


