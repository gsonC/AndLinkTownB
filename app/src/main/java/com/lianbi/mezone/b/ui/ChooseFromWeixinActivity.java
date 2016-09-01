package com.lianbi.mezone.b.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.lianbi.mezone.b.bean.MemberMessage;
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
import cn.com.hgh.indexscortlist.ClearEditText;
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
	ClearEditText actWeixinListEdit;
	@Bind(R.id.act_weixin_listview)
	ListView actWeixinListview;
	@Bind(R.id.act_weixin_abpulltorefreshview)
	AbPullToRefreshView actWeixinAbpulltorefreshview;

	private ListView mAct_addmembers_listview;
	private ArrayList<MemberMessage> mDatas = new ArrayList<MemberMessage>();
	private QuickAdapter<MemberMessage> mAdapter;
	private int page = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_choose_from_weixin, NOTYPE);
		ButterKnife.bind(this);
		setLisenter();
		initAdapter();
		getWeixinQueryProduct(true);
	}


	/**
	 * 添加监听
	 */
	private void setLisenter() {
		setPageTitle("从微信商城产品库选择");
		// 根据输入框输入值的改变来过滤搜索
		actWeixinListEdit.addTextChangedListener(new TextWatcher() {

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
		actWeixinListEdit.setOnEditorActionListener(new TextView.OnEditorActionListener() {

			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_UNSPECIFIED) {
					String response = actWeixinListEdit.getText().toString().trim();

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
				getWeixinQueryProduct(true);
			}

		});
		actWeixinAbpulltorefreshview.setOnFooterLoadListener(new AbPullToRefreshView.OnFooterLoadListener() {

			@Override
			public void onFooterLoad(AbPullToRefreshView view) {
				getWeixinQueryProduct(true);
			}
		});
	}

	private void initAdapter() {
		mAdapter = new QuickAdapter<MemberMessage>(ChooseFromWeixinActivity.this, R.layout.weixin_shop_list, mDatas) {

			@Override
			protected void convert(BaseAdapterHelper helper, final MemberMessage item) {

				ImageView new_product_ima = helper.getView(R.id.new_product_ima);
				TextView new_product_food = helper.getView(R.id.new_product_food);
				TextView new_product_rated = helper.getView(R.id.new_product_rated);
				TextView new_product_price = helper.getView(R.id.new_product_price);
				TextView new_product_choose = helper.getView(R.id.new_product_choose);

				ScreenUtils.textAdaptationOn720(new_product_food, ChooseFromWeixinActivity.this, 24);
				ScreenUtils.textAdaptationOn720(new_product_rated, ChooseFromWeixinActivity.this, 24);
				ScreenUtils.textAdaptationOn720(new_product_price, ChooseFromWeixinActivity.this, 24);
				ScreenUtils.textAdaptationOn720(new_product_choose, ChooseFromWeixinActivity.this, 24);

				new_product_food.setText(item.getProductName());
				new_product_rated.setText(item.getProductDesc());
				new_product_price.setText(item.getProductPrice());


				helper.getView(R.id.new_product_choose).setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						Intent intent = new Intent(ChooseFromWeixinActivity.this, RevisionsActivity.class);
						intent.putExtra("new_product_id", item.getId());
						intent.putExtra("new_product_food", item.getProductName());
						intent.putExtra("new_product_rated", item.getProductDesc());
						intent.putExtra("new_product_price", item.getProductPrice());
						startActivityForResult(intent, RESULT_WEIXIN);
					}
				});
			}


		};

		actWeixinListview.setAdapter(mAdapter);
	}

	private final int RESULT_WEIXIN = 4444;
	/*private void getWeiXinList() {
		ArrayList<WeiXinProduct> mDatasL = new ArrayList<WeiXinProduct>();
		for (int i = 0; i < 20; i++) {
			WeiXinProduct bean = new WeiXinProduct();
			bean.setNew_product_food("dsadasda" + i);
			bean.setNew_product_choose("选择" + i);
			bean.setNew_product_rated("普通" + i);
			bean.setNew_product_price("微店" + i);

			mDatasL.add(bean);
		}
		if (mDatasL.size() > 0) {
			mDatas.addAll(mDatasL);
		}
		if (mDatasL.size() > 0) {
			mDatas.addAll(mDatasL);
		}
		mAdapter.replaceAll(mDatas);
	}*/

	/**
	 * 积分商品查询
	 *
	 * @param
	 * @param
	 * @param
	 */


	private void getWeixinQueryProduct(final boolean isResh) {
		if (isResh) {
			page = 1;
			mDatas.clear();
			mAdapter.replaceAll(mDatas);
		}
		String reqTime = AbDateUtil.getDateTimeNow();
		String uuid = AbStrUtil.getUUID();

		try {
			okHttpsImp.QueryProduct(uuid, "app", reqTime, OkHttpsImp.md5_key, "BDP200eWiZ16cbs041217820", new MyResultCallback<String>() {
				@Override
				public void onResponseResult(Result result) {
					String reString = result.getData();
					System.out.println("reString" + reString);
					if (reString != null) {
						try {
							JSONObject jsonObject = new JSONObject(reString);
							reString = jsonObject.getString("products");
							ArrayList<MemberMessage> mDatasL = (ArrayList<MemberMessage>) JSON.parseArray(reString, MemberMessage.class);
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
					ContentUtils.showMsg(ChooseFromWeixinActivity.this, "查询积分商品失败");
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}