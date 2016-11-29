package com.lianbi.mezone.b.ui;


import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
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
import cn.com.hgh.eventbus.RefreshEvent;
import cn.com.hgh.utils.AbDateUtil;
import cn.com.hgh.utils.AbPullHide;
import cn.com.hgh.utils.AbStrUtil;
import cn.com.hgh.utils.Result;
import cn.com.hgh.view.AbPullToRefreshView;
import cn.com.hgh.view.DialogFinish;
import cn.com.hgh.view.DialogLine;
import cn.com.hgh.view.DialogQrg;
import cn.com.hgh.view.DialogShoukuan;

/**
 *消费结算
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
	public String tableName;
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
		setListview();
	}

	/**
	 * 从首页跳转过来
	 */
	private void getStringFormShouye() {
		String eject = getIntent().getStringExtra("EJECT");
		if ("eject".equals(eject)) {
			imComestoreDetail.setVisibility(View.GONE);
			imComestoreEject.setVisibility(View.VISIBLE);
		}
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

	private void setlisten(){

		actCumptionListview.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {
					case MotionEvent.ACTION_MOVE:
						imComestoreDetail.setVisibility(View.GONE);
						break;
				}

				return false;
			}
		});
	}

	private void initview() {

		setPageTitle(getString(R.string.activity_consumptionsettlement_title));
		//刷新设置
		actCumptionAbpulltorefreshview.setLoadMoreEnable(true);
		actCumptionAbpulltorefreshview.setPullRefreshEnable(true);
		actCumptionAbpulltorefreshview.setOnHeaderRefreshListener(new AbPullToRefreshView.OnHeaderRefreshListener() {
			@Override
			public void onHeaderRefresh(AbPullToRefreshView view) {
				rope = YoYo.with(Techniques.FadeOut).duration(500)
						.playOn(imComestoreEject);
				rope = YoYo.with(Techniques.FadeInRight).duration(1000)
						.playOn(imComestoreDetail);
				imComestoreEject.setVisibility(View.GONE);
				imComestoreDetail.setVisibility(View.VISIBLE);
				getUnPaidOrder(true);
			}
		});
		//尾部刷新设置
		actCumptionAbpulltorefreshview.setOnFooterLoadListener(new AbPullToRefreshView.OnFooterLoadListener() {
			@Override
			public void onFooterLoad(AbPullToRefreshView view) {
				rope = YoYo.with(Techniques.FadeOut).duration(500)
						.playOn(imComestoreEject);
				rope = YoYo.with(Techniques.FadeInRight).duration(1000)
						.playOn(imComestoreDetail);
				imComestoreEject.setVisibility(View.GONE);
				imComestoreDetail.setVisibility(View.VISIBLE);
				getUnPaidOrder(false);
			}
		});
	}

	//初始化适配器
	public QuickAdapter<Consumption> mAdapter;

	private void initListAdapter() {
		mAdapter = new QuickAdapter<Consumption>(this, R.layout.activity_consumption_item, mDatas) {
			@Override
			@OnClick({R.id.tv_consum_shoukuan,R.id.tv_consum_detail})
			protected void convert(BaseAdapterHelper helper, final Consumption item) {
				TextView tv_consum_total = helper.getView(R.id.tv_consum_total);
				TextView tv_consum_time = helper.getView(R.id.tv_consum_time);
				TextView tv_consum_price = helper.getView(R.id.tv_consum_price);
				TextView tv_consum_where = helper.getView(R.id.tv_consum_where);
				TextView tv_consum_shoukuan = helper.getView(R.id.tv_consum_shoukuan);
				tableId=item.getTableId();
				tableName = item.getTableName();
				tv_consum_where.setText(item.getTableName());
				tv_consum_total.setText(item.getProductCount());
				tv_consum_price.setText(item.getUnPaidorderAmt());
				tv_consum_time.setText(item.getCreateTime());

				TextView tv_consum_detail = helper.getView(R.id.tv_consum_detail);
				tv_consum_detail.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						getTssTableInfo(item.getTableId(),tableName);

					}
				});

				tv_consum_shoukuan.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {

						DialogShoukuan dialog=new DialogShoukuan(ConsumptionSettlementActivity.this) {
							@Override
							public void onCheckClick() {
								/*Intent intent=new Intent(ConsumptionSettlementActivity.this,QrImgMainActivity.class);
								startActivity(intent);*/
								getOnlinePayController(item.getTableId());


								dismiss();
							}

							@Override
							public void onOkClick() {
								DialogLine dialogLine=new DialogLine(ConsumptionSettlementActivity.this) {
									@Override
									public void onCheckClick() {
										TssOrdersController(item.getTableId());
										dismiss();
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
								dismiss();
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
								//imComestoreDetail.setVisibility(View.GONE);
							}
							mAdapter.replaceAll(mDatas);
						} catch (JSONException e) {
							e.printStackTrace();
						}
						AbPullHide.hideRefreshView(isResh, actCumptionAbpulltorefreshview);
						if(mDatas.size()==0){
							imComestoreDetail.setVisibility(View.GONE);
							imComestoreEject.setVisibility(View.GONE);
							rope = YoYo.with(Techniques.FadeOut).duration(500)
									.playOn(imComestoreEject);
							rope = YoYo.with(Techniques.FadeInRight).duration(1000)
									.playOn(imComestoreDetail);
							actCumptionAbpulltorefreshview.setVisibility(View.GONE);
							imgCumptionEmpty.setVisibility(View.VISIBLE);
						}else{
							rope = YoYo.with(Techniques.FadeOut).duration(500)
									.playOn(imComestoreDetail);
							rope = YoYo.with(Techniques.FadeInRight).duration(1000)
									.playOn(imComestoreEject);
							imComestoreDetail.setVisibility(View.GONE);
							imComestoreEject.setVisibility(View.VISIBLE);
							actCumptionAbpulltorefreshview.setVisibility(View.VISIBLE);
							imgCumptionEmpty.setVisibility(View.GONE);
						}
					}
				}

				@Override
				public void onResponseFailed(String msg) {
					rope = YoYo.with(Techniques.FadeOut).duration(500)
							.playOn(imComestoreEject);
					rope = YoYo.with(Techniques.FadeInRight).duration(1000)
							.playOn(imComestoreDetail);
					imComestoreDetail.setVisibility(View.GONE);
					imComestoreEject.setVisibility(View.GONE);
					actCumptionAbpulltorefreshview.setVisibility(View.GONE);
					imgCumptionEmpty.setVisibility(View.VISIBLE);
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

	private void getOnlinePayController(String tableId){
		try {
			okHttpsImp.getonlinePay(UserId, BusinessId, tableId, new MyResultCallback<String>() {
						@Override
						public void onResponseResult(Result result) {
							String reString=result.getData();
							if(reString!=null){
								try {
									JSONObject jsonObject=new JSONObject(reString);
									String url=jsonObject.getString("payUrl");
									DialogQrg dialogQrg=new DialogQrg(url,ConsumptionSettlementActivity.this);
									dialogQrg.show();
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

	/**
	 * 现金付款接口
	 */
	private void TssOrdersController(String tableId){
		String reqTime = AbDateUtil.getDateTimeNow();
		String uuid = AbStrUtil.getUUID();
		try {
			okHttpsImp.geteditOrderStatus(uuid, "app", reqTime, UserId, BusinessId, tableId, new MyResultCallback<String>() {
				@Override
				public void onResponseResult(Result result) {

					DialogFinish dialogFinish=new DialogFinish(ConsumptionSettlementActivity.this) {


						@Override
						public void onFinishOkClick() {

						}

						@Override
						public void onFinishClick() {

						}
					};
					dialogFinish.setTv_dialog_finishing_title("支付完成");
					dialogFinish.setTv_dialog_finishing_titlee("顾客离店 请翻桌");
					dialogFinish.show();
					getUnPaidOrder(true);
				}



				@Override
				public void onResponseFailed(String msg) {

				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		} {

		}
	}
	/**
	 * 4.25	桌位详情接口
	 */
  private void getTssTableInfo(final String tableId,final String tableName){
	  String reqTime = AbDateUtil.getDateTimeNow();
	  String uuid = AbStrUtil.getUUID();
	  try {
		  okHttpsImp.gettsstableInfo(uuid, "app", reqTime, BusinessId, tableId, "app", new MyResultCallback<String>() {
			  @Override
			  public void onResponseResult(Result result) {
                String reString=result.getData();
				  if(reString!=null){
					  try {
						  JSONObject jsonObject=new JSONObject(reString);
						  int  tableStatus=jsonObject.getInt("tableStatus");
						  switch (tableStatus){
							 case 0:
								// startActivity(new Intent(ConsumptionSettlementActivity.this,ScanningQRActivity.class).putExtra("TABLENAME",jsonObject.getString("").putExtra("TABLENAME",jsonObject.getString("")));
								 Intent intent=new Intent(ConsumptionSettlementActivity.this,ScanningQRActivity.class);
								 intent.putExtra("TABLEID",tableId);
								 intent.putExtra("TABLENAME",tableName);
								 startActivity(intent);
								 break;
							 case 1:
								 //startActivity(new Intent(ConsumptionSettlementActivity.this,TableHasOrderedActivity.class));
								 Intent intent1 = new Intent(ConsumptionSettlementActivity.this,TableHasOrderedActivity.class);
								 intent1.putExtra("TABLEID",tableId);
								 intent1.putExtra("TABLENAME",tableName);
								 intent1.putExtra("DATA",reString);
								 startActivity(intent1);

								 break;

							 case 2:
								 //startActivity(new Intent(ConsumptionSettlementActivity.this,TableHasPaidActivity.class));
								 Intent intent2 = new Intent(ConsumptionSettlementActivity.this,TableHasPaidActivity.class);
								 intent2.putExtra("TABLEID",tableId);
								 intent2.putExtra("TABLENAME",tableName);
								 intent2.putExtra("DATA",reString);
								 startActivity(intent2);
								 break;

						 }

					  } catch (JSONException e) {
						  e.printStackTrace();
					  }
				  }
			  }

			  @Override
			  public void onResponseFailed(String msg) {

			  }
		  });
	  } catch (Exception e) {
		  e.printStackTrace();
	  }
  }

	@Override
	protected void onDestroy() {
		super.onDestroy();
		ButterKnife.unbind(this);
		EventBus.getDefault().post(new RefreshEvent(0));
	}
	private void setListview(){

			actCumptionListview.setOnScrollListener(new AbsListView.OnScrollListener() {
			private int firstVisibleItem;
			private int totalItemCount;
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
					if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL
							||
						scrollState == AbsListView.OnScrollListener.SCROLL_STATE_FLING
							) {
						rope = YoYo.with(Techniques.FadeOut).duration(500)
								.playOn(imComestoreEject);
						rope = YoYo.with(Techniques.FadeInRight).duration(1000)
								.playOn(imComestoreDetail);
						imComestoreEject.setVisibility(View.GONE);
						imComestoreDetail.setVisibility(View.VISIBLE);
					}else{
						rope = YoYo.with(Techniques.FadeOut).duration(500)
								.playOn(imComestoreDetail);
						rope = YoYo.with(Techniques.FadeInRight).duration(1000)
								.playOn(imComestoreEject);
						imComestoreEject.setVisibility(View.VISIBLE);
						imComestoreDetail.setVisibility(View.GONE);

					}
			}
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
				this.firstVisibleItem = firstVisibleItem;
				this.totalItemCount = totalItemCount;

			}
		});

	}

}