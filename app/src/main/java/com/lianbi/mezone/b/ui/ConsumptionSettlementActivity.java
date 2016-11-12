package com.lianbi.mezone.b.ui;


import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.lianbi.mezone.b.bean.Consumption;
import com.lianbi.mezone.b.httpresponse.MyResultCallback;
import com.lianbi.mezone.b.httpresponse.OkHttpsImp;
import com.xizhi.mezone.b.R;
import com.zbar.lib.animationslib.Techniques;
import com.zbar.lib.animationslib.YoYo;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.hgh.baseadapter.BaseAdapterHelper;
import cn.com.hgh.baseadapter.QuickAdapter;
import cn.com.hgh.eventbus.ShouyeRefreshEvent;
import cn.com.hgh.utils.AbDateUtil;
import cn.com.hgh.utils.AbPullHide;
import cn.com.hgh.utils.AbStrUtil;
import cn.com.hgh.utils.Result;
import cn.com.hgh.view.AbPullToRefreshView;
import cn.com.hgh.view.DialogLine;
import cn.com.hgh.view.DialogShoukuan;

/**
 * 客户买单
 */
public class ConsumptionSettlementActivity extends BaseActivity {
	@Bind(R.id.act_cumption_listview)
	ListView actCumptionListview;
	@Bind(R.id.act_cumption_abpulltorefreshview)
	AbPullToRefreshView actCumptionAbpulltorefreshview;
	@Bind(R.id.img_cumption_empty)
	ImageView imgCumptionEmpty;
	ArrayList<Consumption> mDatas = new ArrayList<Consumption>();
	@Bind(R.id.im_comestore_detail)
	ImageView imComestoreDetail;
	@Bind(R.id.im_comestore_eject)
	ImageView imComestoreEject;
	public String tableId;
	private YoYo.YoYoString rope;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_consumption_settlement, NOTYPE);
		ButterKnife.bind(this);
		initview();
		getStringFormShouye();
		initListAdapter();
		getUnPaidOrder(true);
		setlisten();
	}

	/**
	 * 动画
	 */
	@OnClick({R.id.im_comestore_detail, R.id.im_comestore_eject})
	public void OnClick(View v) {
		switch (v.getId()) {
			case R.id.im_comestore_detail://点击隐藏 到店显示
				rope = YoYo.with(Techniques.FadeOut).duration(500)
						.playOn(imComestoreDetail);
				rope = YoYo.with(Techniques.FadeInRight).duration(1000)
						.playOn(imComestoreEject);
				imComestoreEject.setVisibility(View.VISIBLE);
				break;
			case R.id.im_comestore_eject://跳转到店
				startActivity(new Intent(ConsumptionSettlementActivity.this, DiningTableSettingActivity.class));
				break;
		}
	}

	private void getStringFormShouye() {
		String eject = getIntent().getStringExtra("EJECT");
		if ("eject".equals(eject)) {
			imComestoreDetail.setVisibility(View.VISIBLE);
			imComestoreEject.setVisibility(View.GONE);
		}
	}

	private void setlisten() {

		actCumptionListview.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {
					case MotionEvent.ACTION_MOVE:
						imComestoreDetail.setVisibility(View.VISIBLE);
						break;
				}

				return false;
			}
		});
	}


	private void initview() {

		setPageTitle("客户买单");
		//	imComestoreDetail.setOnClickListener(this);
		//	imComestoreDetail.setOnClickListener(new View.OnClickListener() {
		//		@Override
		//		public void onClick(View v) {
		//			startActivity(new Intent(ConsumptionSettlementActivity.this, DiningTableSettingActivity.class));
		//		}
		//	});
		//刷新设置
		actCumptionAbpulltorefreshview.setLoadMoreEnable(true);
		actCumptionAbpulltorefreshview.setPullRefreshEnable(true);
		actCumptionAbpulltorefreshview.setOnHeaderRefreshListener(new AbPullToRefreshView.OnHeaderRefreshListener() {
			@Override
			public void onHeaderRefresh(AbPullToRefreshView view) {
				getUnPaidOrder(true);
			}
		});
		//尾部刷新设置
		actCumptionAbpulltorefreshview.setOnFooterLoadListener(new AbPullToRefreshView.OnFooterLoadListener() {
			@Override
			public void onFooterLoad(AbPullToRefreshView view) {
				getUnPaidOrder(false);
			}
		});
	}

	//初始化适配器
	public QuickAdapter<Consumption> mAdapter;

	private void initListAdapter() {
		mAdapter = new QuickAdapter<Consumption>(this, R.layout.activity_consumption_item, mDatas) {
			@Override
			@OnClick({R.id.tv_consum_shoukuan})
			protected void convert(BaseAdapterHelper helper, final Consumption item) {
				TextView tv_consum_total = helper.getView(R.id.tv_consum_total);
				TextView tv_consum_time = helper.getView(R.id.tv_consum_time);
				TextView tv_consum_price = helper.getView(R.id.tv_consum_price);
				TextView tv_consum_daytime = helper.getView(R.id.tv_consum_daytime);
				TextView tv_consum_where = helper.getView(R.id.tv_consum_where);
				TextView tv_consum_shoukuan = helper.getView(R.id.tv_consum_shoukuan);
				tableId = item.getTableId();

				tv_consum_where.setText(item.getTableName());
				tv_consum_total.setText(item.getProductCount());
				tv_consum_price.setText(item.getUnPaidorderAmt());
				tv_consum_time.setText(item.getCreateTime());

				tv_consum_shoukuan.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						DialogShoukuan dialog = new DialogShoukuan(ConsumptionSettlementActivity.this) {
							@Override
							public void onCheckClick() {
								getOnlinePayController(item.getTableId());
								Intent intent = new Intent(ConsumptionSettlementActivity.this, QrImgMainActivity.class);
								startActivity(intent);
							}

							@Override
							public void onOkClick() {
								DialogLine dialogLine = new DialogLine(ConsumptionSettlementActivity.this) {
									@Override
									public void onCheckClick() {

									}

									@Override
									public void onOkClick() {
										dismiss();
									}
								};
								dialogLine.setTextTitle("是否选择现金支付");
								dialogLine.setTv_dialog_line_cancel("否");
								dialogLine.setTv_dialog_line_ok("是");
								dialogLine.show();
							}
						};

						dialog.setTv_dialog_shoukuan_cancel("取消");
						dialog.setTv_dialog_shoukuan_title("在线收款");
						dialog.setTv_dialog_shoukuan_titlee("现金收款");
						dialog.show();
					}

				});

			}
		};
		actCumptionListview.setAdapter(mAdapter);
	}


	/**
	 * 4.20	查询店铺的待支付信息
	 */

	private void getUnPaidOrder(final boolean isResh) {
		String reqTime = AbDateUtil.getDateTimeNow();
		String uuid = AbStrUtil.getUUID();
		try {
			okHttpsImp.getUnPaidOrder(uuid, "app", reqTime, OkHttpsImp.md5_key, userShopInfoBean.getBusinessId(), new MyResultCallback<String>() {
				@Override
				public void onResponseResult(Result result) {
					String reString = result.getData();
					mDatas.clear();
					if (reString != null) {
						try {
							JSONObject jsonObject = new JSONObject(reString);
							reString = jsonObject.getString("orderList");
							ArrayList<Consumption> mDatasL = (ArrayList<Consumption>) JSON.parseArray(reString, Consumption.class);

							if (mDatasL != null && mDatasL.size() > 0) {

								mDatas.addAll(mDatasL);

							}
							if (mDatas != null && mDatas.size() > 0) {
								actCumptionAbpulltorefreshview.setVisibility(View.VISIBLE);
								imgCumptionEmpty.setVisibility(View.GONE);
							} else {
								actCumptionAbpulltorefreshview.setVisibility(View.GONE);
								imgCumptionEmpty.setVisibility(View.VISIBLE);
								//							imComestoreDetail.setVisibility(View.GONE);
							}
							AbPullHide.hideRefreshView(isResh, actCumptionAbpulltorefreshview);
							mAdapter.replaceAll(mDatas);
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
				}

				@Override
				public void onResponseFailed(String msg) {
					if (isResh) {
						actCumptionAbpulltorefreshview.setVisibility(View.GONE);
						imgCumptionEmpty.setVisibility(View.VISIBLE);
						imgCumptionEmpty.setVisibility(View.GONE);
					}
					AbPullHide.hideRefreshView(isResh, actCumptionAbpulltorefreshview);
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
		{

		}
	}

	/**
	 * 4.23	在线支付
	 */

	private void getOnlinePayController(String tableId) {
		String reqTime = AbDateUtil.getDateTimeNow();
		String uuid = AbStrUtil.getUUID();
		try {
			okHttpsImp.getonlinePay(uuid, "app", reqTime, UserId, BusinessId, tableId, new MyResultCallback<String>() {
						@Override
						public void onResponseResult(Result result) {
							String reString = result.getData();
							if (reString != null) {
								try {
									JSONObject jsonObject = new JSONObject(reString);
									String url = jsonObject.getString("payUrl");
									System.out.println("url246" + url);
								} catch (JSONException e) {
									e.printStackTrace();
								}

							}
						}

						@Override
						public void onResponseFailed(String msg) {

						}
					}


			);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		ButterKnife.unbind(this);
		EventBus.getDefault().post(new ShouyeRefreshEvent(false));
	}
}