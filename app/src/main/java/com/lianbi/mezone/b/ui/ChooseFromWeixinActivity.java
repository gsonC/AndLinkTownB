package com.lianbi.mezone.b.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.lianbi.mezone.b.bean.WeiXinBean;
import com.lianbi.mezone.b.httpresponse.MyResultCallback;
import com.lianbi.mezone.b.httpresponse.OkHttpsImp;
import com.xizhi.mezone.b.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
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

public class ChooseFromWeixinActivity extends BaseActivity {


	@Bind(R.id.img_weixinshop_empty)
	ImageView imgWeixinshopEmpty;
	@Bind(R.id.act_weixin_list_edit)
	EditText actWeixinListEdit;
	@Bind(R.id.act_weixin_listview)
	ListView actWeixinListview;
	@Bind(R.id.act_weixin_abpulltorefreshview)
	AbPullToRefreshView actWeixinAbpulltorefreshview;

	private ListView mAct_addmembers_listview;
	private ArrayList<WeiXinBean> mDatas = new ArrayList<WeiXinBean>();
	private QuickAdapter<WeiXinBean> mAdapter;
	private int page = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_choose_from_weixin, NOTYPE);
		ButterKnife.bind(this);
		setLisenter();
		initAdapter();
		getWeixinQueryProduct(true,"");
	}

	/**
	 * 添加监听
	 */
	private void setLisenter() {
		setPageTitle(getString(R.string.activity_choosefromweixin_title));
		// 根据输入框输入值的改变来过滤搜索
		actWeixinListEdit.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// 当输入框里面的值为空，更新为原来的列表，否则为过滤数据列表
				if("".equals(s.toString())){
					editSuit("");
				}
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {

			}

			@Override
			public void afterTextChanged(Editable s) {
			}
		});
		actWeixinListEdit.setOnEditorActionListener(new TextView.OnEditorActionListener() {

			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_UNSPECIFIED) {
					String response = actWeixinListEdit.getText().toString().trim();
					editSuit(response);
				}
				AbAppUtil.closeSoftInput(ChooseFromWeixinActivity.this);
				return false;
			}
		});
		actWeixinAbpulltorefreshview.setLoadMoreEnable(true);
		actWeixinAbpulltorefreshview.setPullRefreshEnable(true);
		actWeixinAbpulltorefreshview.setOnHeaderRefreshListener(new AbPullToRefreshView.OnHeaderRefreshListener() {

			@Override
			public void onHeaderRefresh(AbPullToRefreshView view) {
				String input = actWeixinListEdit.getText().toString().trim();
				getWeixinQueryProduct(true,input);
			}

		});
		actWeixinAbpulltorefreshview.setOnFooterLoadListener(new AbPullToRefreshView.OnFooterLoadListener() {

			@Override
			public void onFooterLoad(AbPullToRefreshView view) {
				String input = actWeixinListEdit.getText().toString().trim();
				getWeixinQueryProduct(false,input);
			}
		});
	}
	/**
	 * 匹配输入框
	 */
	private void editSuit(String input) {
		ArrayList<WeiXinBean> arrayList = new ArrayList<WeiXinBean>();
		if (TextUtils.isEmpty(input)) {
			arrayList = mDatas;
		} else {
			arrayList.clear();
			for (WeiXinBean weixin : mDatas) {
				if ((weixin.getProductDesc().contains(input)) || (weixin.getProductName().contains(input))
						|| (weixin.getProductPrice().contains(input))) {
					arrayList.add(weixin);
				}
			}
		}
		mAdapter.replaceAll(arrayList);
	}

	private void initAdapter() {
		mAdapter = new QuickAdapter<WeiXinBean>(ChooseFromWeixinActivity.this, R.layout.weixin_shop_list, mDatas)  {

			@Override
			protected void convert(BaseAdapterHelper helper, final WeiXinBean item) {

				ImageView new_product_ima = helper.getView(R.id.new_product_ima);
				TextView new_product_food = helper.getView(R.id.new_product_food);
				TextView new_product_rated = helper.getView(R.id.new_product_rated);
				TextView new_product_price = helper.getView(R.id.new_product_price);
				TextView new_product_choose = helper.getView(R.id.new_product_choose);

				ScreenUtils.textAdaptationOn720(new_product_food, ChooseFromWeixinActivity.this, 32);
				ScreenUtils.textAdaptationOn720(new_product_rated, ChooseFromWeixinActivity.this, 23);
				ScreenUtils.textAdaptationOn720(new_product_price, ChooseFromWeixinActivity.this, 27);
				ScreenUtils.textAdaptationOn720(new_product_choose, ChooseFromWeixinActivity.this, 23);

				new_product_food.setText(item.getProductName());
				new_product_rated.setText(item.getProductDesc());
				new_product_price.setText(item.getProductPrice());
				String uri ="";
				try {
					uri = item.getProductImages().get(0).getImgUrl();
				}catch (Exception e){
					e.printStackTrace();
				}
				Glide.with(ChooseFromWeixinActivity.this).load(uri).error(R.mipmap.default_head).into(new_product_ima);

				helper.getView(R.id.new_product_choose).setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						Intent intent = new Intent(ChooseFromWeixinActivity.this, RevisionsActivity.class);
						intent.putExtra("new_product_food", item.getProductName());
						intent.putExtra("new_product_rated", item.getProductDesc());
						intent.putExtra("new_product_images", item.getProductImages());
						intent.putExtra("shopSourceId",item.getShopSourceId());
						startActivityForResult(intent, RESULT_WEIXIN);
					}
				});
			}


		};

		actWeixinListview.setAdapter(mAdapter);
	}

	private final int RESULT_WEIXIN = 4444;

	/**
	 * 微信商品查询
	 *
	 */
	String shopSourceId;

	private void getWeixinQueryProduct(final boolean isResh,String input) {
		if (isResh) {
			page = 1;
			mDatas.clear();
			mAdapter.replaceAll(mDatas);
		}
		String reqTime = AbDateUtil.getDateTimeNow();
		String uuid = AbStrUtil.getUUID();

		try {
			okHttpsImp.QueryFromWinxin(OkHttpsImp.md5_key,uuid, "app", reqTime,
					userShopInfoBean.getBusinessId(), page + "", 20 + "",input,new MyResultCallback<String>() {
				@Override
				public void onResponseResult(Result result) {
					page++;
					String reString = result.getData();
					if (reString != null) {
						try {
							JSONObject jsonObject = new JSONObject(reString);
							reString = jsonObject.getString("products");
							ArrayList<WeiXinBean> mDatasL = (ArrayList<WeiXinBean>) JSON.parseArray(reString, WeiXinBean.class);
							if (mDatasL != null && mDatasL.size() > 0) {
								mDatas.addAll(mDatasL);

							}
							if (mDatasL != null && mDatas.size() > 0) {
								imgWeixinshopEmpty.setVisibility(View.GONE);
								actWeixinAbpulltorefreshview.setVisibility(View.VISIBLE);

							} else {
								imgWeixinshopEmpty.setVisibility(View.VISIBLE);
								actWeixinAbpulltorefreshview.setVisibility(View.GONE);
							}
							AbPullHide.hideRefreshView(isResh, actWeixinAbpulltorefreshview);
							mAdapter.replaceAll(mDatas);
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
				}

				@Override
				public void onResponseFailed(String msg) {
					if (isResh) {
						imgWeixinshopEmpty.setVisibility(View.VISIBLE);
						actWeixinAbpulltorefreshview.setVisibility(View.GONE);
					}
					AbPullHide.hideRefreshView(isResh, actWeixinAbpulltorefreshview);
					ContentUtils.showMsg(ChooseFromWeixinActivity.this, "查询微信列表失败");
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) {
			switch (requestCode) {

				case RESULT_WEIXIN:
					getWeixinQueryProduct(true,"");
					break;

			}
		}
	}
}