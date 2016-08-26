package com.lianbi.mezone.b.ui;

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
import com.lianbi.mezone.b.bean.WeiXinProduct;
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
	@Bind(R.id.act_member_list_edit)
	ClearEditText actMemberListEdit;
	@Bind(R.id.act_addmembers_listview)
	ListView actAddmembersListview;
	@Bind(R.id.act_addmembers_abpulltorefreshview)
	AbPullToRefreshView actAddmembersAbpulltorefreshview;
	@Bind(R.id.img_memberpoint_empty)
	ImageView imgMemberpointEmpty;
	private ListView mAct_addmembers_listview;
	private ArrayList<WeiXinProduct> mDatas = new ArrayList<WeiXinProduct>();
	private QuickAdapter<WeiXinProduct> mAdapter;
	private AbPullToRefreshView act_memberpoint_abpulltorefreshview;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_choose_from_weixin, NOTYPE);
		ButterKnife.bind(this);

		initAdapter();
	}


	/**
	 * 添加监听
	 */
	private void setLisenter() {
		setPageTitle("从微信商城产品库选择");
		// 根据输入框输入值的改变来过滤搜索
		actMemberListEdit.addTextChangedListener(new TextWatcher() {

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
		actMemberListEdit.setOnEditorActionListener(new TextView.OnEditorActionListener() {

			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_UNSPECIFIED) {
					String response = actMemberListEdit.getText().toString().trim();

				}
				AbAppUtil.closeSoftInput(ChooseFromWeixinActivity.this);
				return false;
			}
		});
		actAddmembersAbpulltorefreshview.setLoadMoreEnable(true);
		actAddmembersAbpulltorefreshview.setPullRefreshEnable(true);
		actAddmembersAbpulltorefreshview.setOnHeaderRefreshListener(new AbPullToRefreshView.OnHeaderRefreshListener() {

			@Override
			public void onHeaderRefresh(AbPullToRefreshView view) {
				//getTagList(true);
				getQueryProduct(true);
			}

		});
		actAddmembersAbpulltorefreshview.setOnFooterLoadListener(new AbPullToRefreshView.OnFooterLoadListener() {

			@Override
			public void onFooterLoad(AbPullToRefreshView view) {
				//getTagList(false);
				getQueryProduct(true);
			}
		});
	}

	private void initAdapter() {
		mAdapter = new QuickAdapter<WeiXinProduct>(ChooseFromWeixinActivity.this, R.layout.weixin_shop_list, mDatas) {

			@Override
			protected void convert(BaseAdapterHelper helper, WeiXinProduct item) {

				ImageView new_product_ima = helper.getView(R.id.new_product_ima);
				TextView new_product_food = helper.getView(R.id.new_product_food);
				TextView new_product_rated = helper.getView(R.id.new_product_rated);
				TextView new_product_price = helper.getView(R.id.new_product_price);
				TextView new_product_choose = helper.getView(R.id.new_product_choose);
				ScreenUtils.textAdaptationOn720(new_product_food, ChooseFromWeixinActivity.this, 24);
				ScreenUtils.textAdaptationOn720(new_product_rated, ChooseFromWeixinActivity.this, 24);
				ScreenUtils.textAdaptationOn720(new_product_price, ChooseFromWeixinActivity.this, 24);
				ScreenUtils.textAdaptationOn720(new_product_choose, ChooseFromWeixinActivity.this, 24);

				new_product_food.setText(item.getNew_product_food() + "");
				new_product_rated.setText(item.getNew_product_rated() + "");
				new_product_price.setText(item.getNew_product_price() + "");
				new_product_choose.setText(item.getNew_product_choose() + "");

				helper.getView(R.id.new_product_choose).setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						getQueryProduct(true);
					}
				});
			}


		};

		actAddmembersListview.setAdapter(mAdapter);
	}

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

	private void getQueryProduct(final boolean isResh) {
		if (isResh) {
			mDatas.clear();
			mAdapter.replaceAll(mDatas);
		}
		String reqTime = AbDateUtil.getDateTimeNow();
		String uuid = AbStrUtil.getUUID();

		try {
			okHttpsImp.QueryProduct(OkHttpsImp.md5_key, uuid, "app", reqTime, userShopInfoBean.getBusinessId(), new MyResultCallback<String>() {
				@Override
				public void onResponseResult(Result result) {
					String reString = result.getData();
					System.out.println("reString11111" + reString);
					if (!AbStrUtil.isEmpty(reString)) {
						try {
							JSONObject jsonObject = new JSONObject(reString);
							reString = jsonObject.getString("Data");
							ArrayList<MemberMessage> mDatasL = (ArrayList<MemberMessage>) JSON.parseArray(reString, MemberMessage.class);
							if (mDatasL != null && mDatasL.size() > 0) {
								//mDatas.addAll(mDatasL);

							}
							if (mDatasL != null && mDatas.size() > 0) {
								imgMemberpointEmpty.setVisibility(View.GONE);
								actAddmembersAbpulltorefreshview.setVisibility(View.VISIBLE);

							} else {
								imgMemberpointEmpty.setVisibility(View.VISIBLE);
								actAddmembersAbpulltorefreshview.setVisibility(View.GONE);
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
						imgMemberpointEmpty.setVisibility(View.VISIBLE);
						actAddmembersAbpulltorefreshview.setVisibility(View.GONE);
					}
					AbPullHide.hideRefreshView(isResh, act_memberpoint_abpulltorefreshview);
					ContentUtils.showMsg(ChooseFromWeixinActivity.this, "查询积分商品失败");
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}