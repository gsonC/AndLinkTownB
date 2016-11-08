package com.lianbi.mezone.b.ui;


import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.lianbi.mezone.b.bean.Consumption;
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
import cn.com.hgh.utils.AbDateUtil;
import cn.com.hgh.utils.AbPullHide;
import cn.com.hgh.utils.AbStrUtil;
import cn.com.hgh.utils.Result;
import cn.com.hgh.view.AbPullToRefreshView;

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

	ArrayList<Consumption> mDatas=new ArrayList<Consumption>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_consumption_settlement, NOTYPE);
		ButterKnife.bind(this);
		initview();
		initListAdapter();
		getUnPaidOrder(true);
	}

	private void initview() {
		setPageTitle("客户买单");
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
		mAdapter = new QuickAdapter<Consumption>(this, R.layout.activity_consumption_item,mDatas) {
			@Override
			protected void convert(BaseAdapterHelper helper, Consumption item) {
				TextView tv_consum_total=helper.getView(R.id.tv_consum_total);
				TextView tv_consum_time=helper.getView(R.id.tv_consum_time);
				TextView tv_consum_price=helper.getView(R.id.tv_consum_price);
				TextView tv_consum_daytime=helper.getView(R.id.tv_consum_daytime);
				TextView tv_consum_where=helper.getView(R.id.tv_consum_where);
				TextView tv_consum_shoukuan=helper.getView(R.id.tv_consum_shoukuan);


				tv_consum_where.setText(item.getTableName());
				tv_consum_total.setText(item.getProductCount());
				tv_consum_price.setText(item.getUnPaidorderAmt());
				tv_consum_time.setText(item.getCreateTime());
			}
		};
		actCumptionListview.setAdapter(mAdapter);
	}

	/**
	 * 4.20	查询店铺的待支付信息
	 */

	private void getUnPaidOrder(final boolean isResh){
        String reqTime= AbDateUtil.getDateTimeNow();
		String uuid= AbStrUtil.getUUID();
		try {
			okHttpsImp.getUnPaidOrder(uuid,"app",reqTime, OkHttpsImp.md5_key,
					userShopInfoBean.getBusinessId(),new MyResultCallback<String>() {
						@Override
						public void onResponseResult(Result result) {
	                      String reString=result.getData();
							mDatas.clear();
							if(reString!=null){
								try {
									JSONObject jsonObject=new JSONObject(reString);
									reString=jsonObject.getString("orderList");
									ArrayList<Consumption> mDatasL= (ArrayList<Consumption>) JSON.parseArray(reString,Consumption.class);

									if (mDatasL != null && mDatasL.size() > 0) {

										mDatas.addAll(mDatasL);

									}
									if(mDatas!=null&&mDatas.size()>0){
										actCumptionAbpulltorefreshview.setVisibility(View.VISIBLE);
										imgCumptionEmpty.setVisibility(View.GONE);
									}else{
										actCumptionAbpulltorefreshview.setVisibility(View.GONE);
										imgCumptionEmpty.setVisibility(View.VISIBLE);
									}
									AbPullHide.hideRefreshView(isResh,actCumptionAbpulltorefreshview);
									mAdapter.replaceAll(mDatas);
								} catch (JSONException e) {
									e.printStackTrace();
								}
							}
						}

						@Override
						public void onResponseFailed(String msg) {
                          if(isResh){
							  actCumptionAbpulltorefreshview.setVisibility(View.GONE);
							  imgCumptionEmpty.setVisibility(View.VISIBLE);
						  }
							AbPullHide.hideRefreshView(isResh,actCumptionAbpulltorefreshview);
						}
					});
		} catch (Exception e) {
			e.printStackTrace();
		} {

		}
	}

}