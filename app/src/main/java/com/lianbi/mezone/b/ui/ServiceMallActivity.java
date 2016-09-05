package com.lianbi.mezone.b.ui;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.lianbi.mezone.b.bean.ServiceMallBean;
import com.lianbi.mezone.b.httpresponse.API;
import com.lianbi.mezone.b.httpresponse.MyResultCallback;
import com.xizhi.mezone.b.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cn.com.hgh.baseadapter.BaseAdapterHelper;
import cn.com.hgh.baseadapter.QuickAdapter;
import cn.com.hgh.utils.ContentUtils;
import cn.com.hgh.utils.JumpIntent;
import cn.com.hgh.utils.Result;
import cn.com.hgh.view.HttpDialog;

/**
 * 服务商城
 */
public class ServiceMallActivity extends BaseActivity {

	private ArrayList<ServiceMallBean> mDatas = new ArrayList<ServiceMallBean>();
	private ArrayList<ServiceMallBean> mData = new ArrayList<ServiceMallBean>();
	HttpDialog dialog;
	private static final int REQUEST_CODE_RESULT = 1000;
	private QuickAdapter<ServiceMallBean> mAdapter;
	private ListView  listview_service;
	private ImageView iv_store_service,iv_servicemall_empty;
	private ServiceMallBean  mServiceMallBean;
	//桌位设置
	public static final int   TABLESETTING=1;
	//微信商城
	public static final int  WECHATMALL =2;
	//货源批发
	public static final int  SUPPLYWHOLESALE =3;
	//预约
	public static final int  RESERVATION =4;
    //智能wifi
	public static final int   INTELLIGENTWIFI=5;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setResult(RESULT_OK);
		setContentView(R.layout.act_servicemallactivity, NOTYPE);
		Glide.get(this).clearMemory();
		initView();
		initListAdapter();
		dialog = new HttpDialog(this);
		dialog.show();
		getCandownloadMall();// 获取可供下载服务商城列表
	}
	private void  simpleJump(Class activity){
		Intent intent=new Intent();
		intent.setClass(ServiceMallActivity.this,activity);
		startActivity(intent);
	}
	private void initListAdapter() {
		mAdapter = new QuickAdapter<ServiceMallBean>(ServiceMallActivity.this,
				R.layout.item_servicemall_list, mDatas) {

			@Override
			protected void convert(BaseAdapterHelper helper,
					final ServiceMallBean item) {
				LinearLayout  llt_servicemall = helper
						.getView(R.id.llt_servicemall);
				ImageView img_itemmall = helper
						.getView(R.id.img_itemmall);
				TextView tv_servicename = helper
						.getView(R.id.tv_servicename);
				TextView tv_download = helper
						.getView(R.id.tv_download);
				TextView tv_newprice = helper
						.getView(R.id.tv_newprice);
				TextView tv_oldprice = helper
						.getView(R.id.tv_oldprice);
				ImageView img_right = helper
						.getView(R.id.img_right);
				tv_oldprice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG );
				tv_oldprice.setText("¥"+String.valueOf(item.getOriginalPrice())+String.valueOf(item.getUnit()));
				tv_newprice.setText("¥"+String.valueOf(item.getPresentPrice())+String.valueOf(item.getUnit()));
				tv_servicename.setText(item.getAppName());
				if(item.getDownload().equals("N")){
				  Glide.with(ServiceMallActivity.this).load(item.getIcoUrl()).error(R.mipmap.default_head).into(img_itemmall);
				  tv_download.setVisibility(View.VISIBLE);
				  img_right.setVisibility(View.INVISIBLE);
				}else 
			    if(item.getDownload().equals("Y")){
				  Glide.with(ServiceMallActivity.this).load(item.getIcoUrl()).error(R.mipmap.default_head).into(img_itemmall);
				  tv_download.setVisibility(View.GONE);
				  img_right.setVisibility(View.VISIBLE);
				}
				helper.getView(R.id.tv_download).setOnClickListener(
						new OnClickListener() {

							@Override
							public void onClick(View v) {
								String  isfdownload=item.getDownload();
								final String  serviceId=String.valueOf(item.getId());
								int primaryID = item.getId();
								mServiceMallBean=item;
								 if(isfdownload.equals("N")){
									dialog.setMessage("下载中...");
									dialog.show();
									new Handler().postDelayed(new Runnable() {

										@Override
										public void run() {
											
										 goDownloadMall(serviceId,mServiceMallBean);

										}
									}, 2000);
									
								}
							} 
						});
				
				helper.getView(R.id.llt_servicemall).setOnClickListener(
						new OnClickListener() {

							@Override
							public void onClick(View v) {
								final String  serviceId=String.valueOf(item.getId());
								boolean isLogin = ContentUtils.getLoginStatus(ServiceMallActivity.this);
								int     primaryID = item.getId();
								String  introduceurl=item.getIntroduceUrl();
								String  isfdownload=item.getDownload();
								String  isappname=item.getAppName();
								if(isfdownload.equals("Y")){
									switch (primaryID) {
										case TABLESETTING:
											simpleJump(TableSetActivity.class);
										break;
										case WECHATMALL:
											JumpIntent.jumpWebActivty
													(ServiceMallActivity.this,H5WebActivty.class,
													isLogin,API.TOSTORE_MODULE_WCM,WECHATMALL,
													false,false,true,"");
											break;
										case SUPPLYWHOLESALE:
											JumpIntent.jumpWebActivty
													(ServiceMallActivity.this,H5WebActivty.class,
															isLogin,API.TOSTORE_Supply_Wholesale,SUPPLYWHOLESALE,
															false,false,true,isappname);
											break;
										case RESERVATION:
											simpleJump(BookFunctionActivity.class);
											break;
										case INTELLIGENTWIFI:
											JumpIntent.jumpWebActivty
													(ServiceMallActivity.this,WIFIWebActivity.class,
															isLogin,API.INTELLIGENT_WIFI,INTELLIGENTWIFI,
															false,false,true,"");
											break;
									}
							        
								}else if(isfdownload.equals("N")){
									  
									  Intent intent = new Intent(
									  ServiceMallActivity.this,
									  MallIntroduceActivity.class);
									  intent.putExtra("serviceid",serviceId);
									  intent.putExtra("introduceurl",introduceurl);
									  startActivityForResult(intent,REQUEST_CODE_RESULT);
								}
							} 
						});
			}
		};
		// 设置适配器
		listview_service.setAdapter(mAdapter);
	}

	private void initView() {
		setPageTitle("服务商城");
		listview_service= (ListView) findViewById(R.id.activity_servicemall_list);
		iv_servicemall_empty = (ImageView) findViewById(R.id.iv_servicemall_empty);
	}

	private void  goDownloadMall(String  serviceId,final ServiceMallBean  mServiceMallBean){
		okHttpsImp.getdownloadServer(new MyResultCallback<String>() {

			@Override
			public void onResponseResult(Result result) {
				String reString = result.getData();
				if (reString != null) {
					JSONObject jsonObject;
					try {
					ContentUtils.showMsg(ServiceMallActivity.this, "下载成功");
					mServiceMallBean.setDownload("Y");
					updateView(mData);
					finish();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				dialog.dismiss();
			}

			@Override
			public void onResponseFailed(String msg) {
				dialog.dismiss();
			}
		}, userShopInfoBean.getBusinessId(),serviceId);
		
		
	}
	private void  getCandownloadMall(){
		okHttpsImp.getCandownloadServerMall(new MyResultCallback<String>() {

			@Override
			public void onResponseResult(Result result) {
				String reString = result.getData();
				if (reString != null) {
					JSONObject jsonObject;
					try {
						jsonObject = new JSONObject(reString);
						reString = jsonObject.getString("appsList");
						if (!TextUtils.isEmpty(reString)) {
							mData.clear();
							ArrayList<ServiceMallBean> downloadListMall = (ArrayList<ServiceMallBean>) JSON
									.parseArray(reString,
											ServiceMallBean.class);
							mData.addAll(downloadListMall);
							updateView(mData);
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
				dialog.dismiss();
			}

			@Override
			public void onResponseFailed(String msg) {
				dialog.dismiss();
			}
		}, userShopInfoBean.getBusinessId());
		
	} 

	protected void updateView(ArrayList<ServiceMallBean> arrayList) {
		mDatas.clear();
		mDatas.addAll(arrayList);
		mAdapter.replaceAll(mDatas);
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) {
			switch (requestCode) {
			case REQUEST_CODE_RESULT:

				getCandownloadMall();// 获取可供下载服务商城列表

				break;
			}
		}
	}

}
