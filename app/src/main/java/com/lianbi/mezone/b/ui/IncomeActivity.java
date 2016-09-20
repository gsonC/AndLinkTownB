package com.lianbi.mezone.b.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.lianbi.mezone.b.bean.IncomeBean;
import com.lianbi.mezone.b.bean.IncomesBean;
import com.lianbi.mezone.b.httpresponse.MyResultCallback;
import com.lianbi.mezone.b.httpresponse.OkHttpsImp;
import com.lianbi.mezone.b.photo.PopupWindowHelper;
import com.xizhi.mezone.b.R;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import cn.com.hgh.baseadapter.BaseAdapterHelper;
import cn.com.hgh.baseadapter.QuickAdapter;
import cn.com.hgh.utils.AbDateUtil;
import cn.com.hgh.utils.AbPullHide;
import cn.com.hgh.utils.AbStrUtil;
import cn.com.hgh.utils.AbViewUtil;
import cn.com.hgh.utils.ContentUtils;
import cn.com.hgh.utils.MathExtend;
import cn.com.hgh.utils.Result;
import cn.com.hgh.view.AbPullToRefreshView;

public class IncomeActivity extends BaseActivity implements OnClickListener {
	private String optType = "00";
	private TextView mainpoplayout_tvlist, mainpoplayout_tvxia, mainpoplayout_tvincome;
	private AbPullToRefreshView act_income_abpulltorefreshview;
	private ListView act_income_listview;
	private ImageView img_income_empty;
	private int page = 0;
	private List<IncomeBean> datas = new ArrayList<>();
	private List<IncomesBean> mDatas = new ArrayList<>();
	private QuickAdapter<IncomesBean> mAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_income, NOTYPE);
		initView();
		setListener();
		initPickView();
		initAdapter();
		getAmtFlow(true, "00");
	}

	private void initAdapter() {
		mAdapter = new QuickAdapter<IncomesBean>(this, R.layout.item_income_1, mDatas) {
			@Override
			protected void convert(BaseAdapterHelper helper, IncomesBean item) {
				TextView tv_income1_title = helper.getView(R.id.tv_income1_title);
				LinearLayout llt_income_bottom = helper.getView(R.id.llt_income_bottom);
				TextView tv_income2_title = helper.getView(R.id.tv_income2_title);
				TextView tv_income2_time = helper.getView(R.id.tv_income2_time);
				TextView tv_income2_money = helper.getView(R.id.tv_income2_money);

				if (AbStrUtil.isEmpty(item.getTime())) {
					tv_income1_title.setVisibility(View.GONE);
					llt_income_bottom.setVisibility(View.VISIBLE);

					tv_income2_time.setText(item.getCreateTime());

					if("00".equals(item.getOptType())){
						tv_income2_title.setText("收入-扫码收款");
					}else if("01".equals(item.getOptType())){
						tv_income2_title.setText("收入-微店");
					}else if("02".equals(item.getOptType())){
						tv_income2_title.setText("解冻");
					}else if("03".equals(item.getOptType())){
						tv_income2_title.setText("支出-提现打款");
					}else if("04".equals(item.getOptType())){
						tv_income2_title.setText("收入-提现失败金额返还");
					}else if("05".equals(item.getOptType())){
						tv_income2_title.setText("支出-手续费");
					}else if("06".equals(item.getOptType())){
						tv_income2_title.setText("支出-提现手续费");
					}else{
						ContentUtils.showMsg(IncomeActivity.this,"业务类型不能为空");
					}


					if ("03".equals(item.getOptType()) || "05".equals(item.getOptType()) || "06".equals(item.getOptType())) {
						tv_income2_money.setText("-" + MathExtend.roundNew(new BigDecimal(item.getAmount()).divide(new BigDecimal(100)).doubleValue(), 2));
					} else {
						tv_income2_money.setText(MathExtend.roundNew(new BigDecimal(item.getAmount()).divide(new BigDecimal(100)).doubleValue(), 2));
					}
				} else {
					tv_income1_title.setVisibility(View.VISIBLE);
					llt_income_bottom.setVisibility(View.GONE);

					tv_income1_title.setText(item.getTime());
				}

			}
		};
		act_income_listview.setAdapter(mAdapter);
	}

	private void setListener() {
		act_income_abpulltorefreshview.setLoadMoreEnable(true);
		act_income_abpulltorefreshview.setPullRefreshEnable(true);
		act_income_abpulltorefreshview.setOnHeaderRefreshListener(new AbPullToRefreshView.OnHeaderRefreshListener() {

			@Override
			public void onHeaderRefresh(AbPullToRefreshView view) {
				getAmtFlow(true, optType);
			}

		});
		act_income_abpulltorefreshview.setOnFooterLoadListener(new AbPullToRefreshView.OnFooterLoadListener() {

			@Override
			public void onFooterLoad(AbPullToRefreshView view) {
				getAmtFlow(false, optType);
			}
		});
	}

	private void initView() {
		setPageTitle("收入明细");
		setPageRightText("全部");
		act_income_abpulltorefreshview = (AbPullToRefreshView) findViewById(R.id.act_income_abpulltorefreshview);//AbPullToRefreshView
		act_income_listview = (ListView) findViewById(R.id.act_income_listview);//列表
		img_income_empty = (ImageView) findViewById(R.id.img_income_empty);
	}

	/**
	 * pop
	 */
	PopupWindow pw = null;

	/**
	 * popView
	 */
	View pickView;

	public void pickImage() {
		if (pw == null) {
			pw = PopupWindowHelper.createPopupWindow(pickView, (int) AbViewUtil.dip2px(this, 120), (int) AbViewUtil.dip2px(this, 100));
			pw.setAnimationStyle(R.style.slide_up_in_down_out);
		}
		pw.showAsDropDown(tvTitleRight, 0, (int) AbViewUtil.dip2px(this, 2));

	}

	/**
	 * 初始化pop
	 */
	public void initPickView() {
		pickView = View.inflate(this, R.layout.mainpoplayout, null);
		mainpoplayout_tvlist = (TextView) pickView.findViewById(R.id.mainpoplayout_tvlist);
		mainpoplayout_tvxia = (TextView) pickView.findViewById(R.id.mainpoplayout_tvxia);
		mainpoplayout_tvincome = (TextView) pickView.findViewById(R.id.mainpoplayout_tvincome);

		mainpoplayout_tvlist.setOnClickListener(this);
		mainpoplayout_tvxia.setOnClickListener(this);
		mainpoplayout_tvincome.setOnClickListener(this);
	}

	@Override
	public void onClick(View view) {
		super.onClick(view);
		switch (view.getId()) {
			case R.id.mainpoplayout_tvlist:
				optType = "00";
				setPageRightText("全部");
				getAmtFlow(true, optType);
				pw.dismiss();
				break;
			case R.id.mainpoplayout_tvxia:
				optType = "02";
				setPageRightText("支出");
				getAmtFlow(true, optType);
				pw.dismiss();

				break;
			case R.id.mainpoplayout_tvincome:
				optType = "01";
				setPageRightText("收入");
				getAmtFlow(true, optType);
				pw.dismiss();

				break;
		}
	}

	@Override
	protected void onTitleRightClickTv() {
		super.onTitleRightClickTv();
		pickImage();
	}

	/**
	 * 收入明细
	 */
	private void getAmtFlow(final boolean isResh, String optType) {
		String reqTime = AbDateUtil.getDateTimeNow();
		String uuid = AbStrUtil.getUUID();

		if (isResh) {
			page = 0;
			mDatas.clear();
			mAdapter.replaceAll(mDatas);
		}

		try {
			okHttpsImp.getIsAmtFlow(OkHttpsImp.md5_key, userShopInfoBean.getUserId(),
					userShopInfoBean.getBusinessId(), optType, uuid, "app", reqTime, page + "", 20 + "", new MyResultCallback<String>() {
				@Override
				public void onResponseResult(Result result) {
					page++;
					String restring = result.getData();
					if (!TextUtils.isEmpty(restring)) {
						ArrayList<IncomeBean> mDatasL = (ArrayList<IncomeBean>) JSON.parseArray(restring, IncomeBean.class);

						//数据拆分
						if (mDatasL != null && mDatasL.size() > 0&&!"".equals(mDatasL.get(0).getTime())) {

							if (!isResh) {

								int s = mDatas.size();
								String nexttime = getTime(mDatasL.get(0).getTime());
								for (int i = 0; i < s; i++) {
									String time = mDatas.get(i).getTime();
									if (null != time) {
										if (nexttime.equals(time)) {
											mDatasL.get(0).setTime(null);
										}
									}
								}
							}

							int dataLSize = mDatasL.size();

							for (int i = 0; i < dataLSize; i++) {
								List<IncomeBean.data> listbean = mDatasL.get(i).getData();

								int dataListSize = listbean.size();
								IncomesBean bean = new IncomesBean();
								if(null!=mDatasL.get(i).getTime()) {
									bean.setTime(getTime(mDatasL.get(i).getTime() + ""));
									mDatas.add(bean);
								}
								for (int y = 0; y < dataListSize; y++) {
									IncomesBean beans = new IncomesBean();
									beans.setOptType(listbean.get(y).getOptType() + "");
									beans.setAmount(listbean.get(y).getAmount());
									beans.setStoreNo(listbean.get(y).getStoreNo() + "");
									beans.setOptMsg(listbean.get(y).getOptMsg() + "");
									beans.setCreateTime(listbean.get(y).getCreateTime() + "");
									beans.setAccountNo(listbean.get(y).getAccountNo() + "");
									beans.setSettleDate(listbean.get(y).getSettleDate() + "");
									mDatas.add(beans);
								}
							}

						}

						if (null != mDatas && mDatas.size() > 0) {
							img_income_empty.setVisibility(View.GONE);
							act_income_abpulltorefreshview.setVisibility(View.VISIBLE);
						} else {
							img_income_empty.setVisibility(View.VISIBLE);
							act_income_abpulltorefreshview.setVisibility(View.GONE);
						}

						AbPullHide.hideRefreshView(isResh, act_income_abpulltorefreshview);
						mAdapter.replaceAll(mDatas);


					}
				}

				@Override
				public void onResponseFailed(String msg) {
					if (isResh) {
						img_income_empty.setVisibility(View.VISIBLE);
						act_income_abpulltorefreshview.setVisibility(View.GONE);
					}
					AbPullHide.hideRefreshView(isResh, act_income_abpulltorefreshview);
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private String getTime(String time) {
		String year = time.substring(0, 4);
		String mouth = time.substring(4, 6);
		return year + "年" + mouth + "月";
	}
}
