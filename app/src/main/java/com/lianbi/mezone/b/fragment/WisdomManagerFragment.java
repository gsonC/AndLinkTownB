package com.lianbi.mezone.b.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.lianbi.mezone.b.app.Constants;
import com.lianbi.mezone.b.bean.ShouyeServiceBean;
import com.lianbi.mezone.b.bean.WebProductManagementBean;
import com.lianbi.mezone.b.httpresponse.API;
import com.lianbi.mezone.b.httpresponse.OkHttpsImp;
import com.lianbi.mezone.b.ui.BaseActivity;
import com.lianbi.mezone.b.ui.DiningTableSettingActivity;
import com.lianbi.mezone.b.ui.H5WebActivty;
import com.lianbi.mezone.b.ui.MainActivity;
import com.lianbi.mezone.b.ui.ServiceMallActivity;
import com.lianbi.mezone.b.ui.WIFIWebActivity;
import com.lianbi.mezone.b.ui.WebActivty;
import com.xizhi.mezone.b.R;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.com.hgh.baseadapter.BaseAdapterHelper;
import cn.com.hgh.baseadapter.QuickAdapter;
import cn.com.hgh.utils.ContentUtils;
import cn.com.hgh.utils.CryptTool;
import cn.com.hgh.utils.JumpIntent;
import cn.com.hgh.utils.WebEncryptionUtil;
import cn.com.hgh.view.MyGridView;

/**
 * @创建者 Administration
 * @创建时间 ＄{DATE} ＄{TIME}
 * @描述 ＄{TOOD}
 * @更新者 ＄auther＄
 * @更新时间 ＄Date＄
 * @更新描述 ＄{TOOD}＄
 */
public class WisdomManagerFragment extends Fragment implements OnClickListener {


	/*@Bind(R.id.ll_wisdommanage_service)
	LinearLayout llWisdommanageService;
	@Bind(R.id.ll_wisdommanage_marketing)
	LinearLayout llWisdommanageMarketing;
	@Bind(R.id.ll_wisdommanage_features)*/ LinearLayout llWisdommanageFeatures;
	@Bind(R.id.ll_wisdommanage_Shouting)
	LinearLayout llWisdommanageShouting;
	@Bind(R.id.ll_wisdommanage_guidance)
	LinearLayout llWisdommanageGuidance;
	@Bind(R.id.ll_wisdommanage_smalltwo)
	LinearLayout llWisdommanageSmalltwo;
	@Bind(R.id.ll_wisdommanage_Servicemall)
	LinearLayout llWisdommanageServicemall;
	@Bind(R.id.GLZX_sc)
	ScrollView GLZXSc;
	@Bind(R.id.swipe_jiaoyiguanli)
	SwipeRefreshLayout swipeJiaoyiguanli;
	@Bind(R.id.ll_wisdommanage_shop)
	LinearLayout llWisdommanageShop;
	@Bind(R.id.gv_shouyeservice)
	MyGridView gv_shouyeservice;
	@Bind(R.id.ll_wisdommanage_shopservice)
	LinearLayout llWisdommanageShopservice;
	@Bind(R.id.ll_wisdommanage_yingxiao)
	LinearLayout llWisdommanageYingxiao;
	@Bind(R.id.ll_wisdommanage_tese)
	LinearLayout llWisdommanageTese;
	private OkHttpsImp httpsImp;
	private MainActivity mMainActivity;
	public static WisdomManagerFragment jiaoYiGuanLiFragment;
	ArrayList<ShouyeServiceBean> mData = new ArrayList<ShouyeServiceBean>();

	/**
	 * 刷新fm数据
	 */
	public void refreshFMDataService() {
		// getMoreServerMall();
	}

	/**
	 * 刷新fm数据
	 */
	public void refreshFMData() {
		if (ContentUtils.getLoginStatus(mMainActivity)) {
			// mDatas.clear();
			mMainActivity.getServiceMall();
			// getServiceMallAll();

		} else {
			// mDatas.clear();
			mMainActivity.getServiceMall();

		}


	}

	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		View view = inflater.inflate(R.layout.fm_wisdomfragment, null);
		jiaoYiGuanLiFragment = this;
		mMainActivity = (MainActivity) getActivity();
		httpsImp = OkHttpsImp.SINGLEOKHTTPSIMP.newInstance(mMainActivity);
		ButterKnife.bind(this, view);
		initView(view);
		listen();
		initListAdapter();
		return view;
	}

	private QuickAdapter<ShouyeServiceBean> mAdapter;

	public void initListAdapter() {
		mAdapter = new QuickAdapter<ShouyeServiceBean>(mMainActivity, R.layout.grid_item, mData) {

			@Override
			protected void convert(final BaseAdapterHelper helper, final ShouyeServiceBean item) {
				RelativeLayout sss = helper.getView(R.id.dsa);
				TextView tv_store_service_introduce = helper.getView(R.id.tv_store_service_introduce);
				ImageView iv_store_service = helper.getView(R.id.iv_store_service);
				int serviceid = item.getDefaultservice();
				switch (serviceid) {
					case -1:
						/*Glide.with(mMainActivity).load("").error(null).into(iv_store_service);
						tv_store_service_introduce.setText(item.getAppName());*/
						break;

					case 1:

						//Glide.with(mMainActivity).load(R.mipmap.icon_servicemall).error(R.mipmap.default_head).into(iv_store_service);
						break;
					case 2:
						/*Glide.with(mMainActivity).load(R.mipmap.icon_receivables).error(R.mipmap.default_head).into(iv_store_service);*/
						break;
					default:
//						if (null != item.getIcoUrl()&&item.getAppCode().equals("wcm")||item.getAppCode().equals("wifi")
//								||item.getAppCode().equals("qns")) {
						Glide.with(mMainActivity).load(item.getIcoUrl()).error(R.mipmap.default_head).into(iv_store_service);
						tv_store_service_introduce.setText(item.getAppName());
//							sss.setVisibility(View.VISIBLE);

//						}else{
//							sss.setVisibility(View.GONE);
//						}
						break;
				}


			}
		};
		gv_shouyeservice.setAdapter(mAdapter);
	}

	private static InputStream is;

	public static InputStream getHttpInputStream(final String url) {
		try {
			new Thread() {
				@Override
				public void run() {
					try {
						URL myFileURL = new URL(url);
						// 获得连接
						HttpURLConnection conn = (HttpURLConnection) myFileURL.openConnection();
						conn.setConnectTimeout(6000);
						conn.setDoInput(true);
						// 不使用缓
						conn.setUseCaches(false);
						is = conn.getInputStream();
						is.close();
						// 把网络访问的代码放在这里
					} catch (Exception e) {
						e.printStackTrace();
					}

				}
			}.start();
			return is;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private SwipeRefreshLayout swipe_jiaoyiguanli;

	/**
	 * 初始化试图
	 */
	private void initView(View view) {
		llWisdommanageServicemall.setOnClickListener(this);
		llWisdommanageShopservice.setOnClickListener(this);
		swipe_jiaoyiguanli = (SwipeRefreshLayout) view.findViewById(R.id.swipe_jiaoyiguanli);
		swipe_jiaoyiguanli.setColorSchemeResources(R.color.colores_news_01, R.color.black);
		swipe_jiaoyiguanli.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

			@Override
			public void onRefresh() {
				/*getAadver();*/
				swipe_jiaoyiguanli.setRefreshing(false);
			}
		});
	}

	@Override
	public void onClick(View view) {
		boolean isLogin = ContentUtils.getLoginStatus(mMainActivity);
		boolean re = false;
		switch (view.getId()) {
			case R.id.ll_wisdommanage_shopservice:
				 startActivity(new Intent(getActivity(),DiningTableSettingActivity.class));
				break;
			case R.id.ll_wisdommanage_Servicemall://服务商城
				re = JumpIntent.jumpLogin_addShop(isLogin, API.SERVICESTORE, mMainActivity);
				if (re) {// 服务商城

					Intent intent_more = new Intent(mMainActivity, ServiceMallActivity.class);
					mMainActivity.startActivityForResult(intent_more, mMainActivity.SERVICEMALLSHOP_CODE);

				}
				break;
		}
	}


	private void listen() {
		gv_shouyeservice.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				int primaryID = 0;
				try {
					primaryID = mData.get(position).getId();
				} catch (Exception e) {
					ContentUtils.showMsg(mMainActivity, "数据异常,为了您的数据安全,请退出重新登陆");
				}
				boolean isLogin = ContentUtils.getLoginStatus(mMainActivity);
				boolean re = false;
				switch (primaryID) {
					case 1:
						re = JumpIntent.jumpLogin_addShop(isLogin, API.SWEEP, mMainActivity);
						if (re) {// 到店服务
							startActivity(new Intent(getActivity(), DiningTableSettingActivity.class));
						}
						break;
					case 2:
						if (isLogin) {// 微信商城
							Intent intent_web = new Intent(mMainActivity, H5WebActivty.class);
							intent_web.putExtra(Constants.NEDDLOGIN, false);
							intent_web.putExtra("NEEDNOTTITLE", false);
							intent_web.putExtra("Re", true);
							intent_web.putExtra(WebActivty.T, "微信商城");
							intent_web.putExtra(WebActivty.U, getSAUrl(API.TOSTORE_MODULE_WCM, 1));
							mMainActivity.startActivity(intent_web);
						}
						break;
					/*case 3:
						if (isLogin) {//货源批发
							Intent intent_web = new Intent(mMainActivity,
									H5WebActivty.class);
							intent_web.putExtra(Constants.NEDDLOGIN, false);
							intent_web.putExtra("NEEDNOTTITLE", false);
							intent_web.putExtra("Re", true);
							intent_web.putExtra(WebActivty.T, "货源批发");
							intent_web.putExtra(WebActivty.U, getSAUrl(API.TOSTORE_Supply_Wholesale,2));
							mMainActivity.startActivity(intent_web);
						}
						break;*/
					/*case 4:
						if(isLogin){//预约界面
							Intent intent = new Intent(mMainActivity, BookFunctionActivity.class);
							startActivity(intent);
						}
						break;*/
					case 5:
						if (isLogin) {//智能WIFI
							Intent intent_web = new Intent(mMainActivity, WIFIWebActivity.class);
							intent_web.putExtra(Constants.NEDDLOGIN, false);
							intent_web.putExtra("NEEDNOTTITLE", false);
							intent_web.putExtra("Re", true);
							intent_web.putExtra(WIFIWebActivity.U, getSAUrl(API.INTELLIGENT_WIFI, 3));
							mMainActivity.startActivity(intent_web);
						}
						break;
					/*case 99:
						re = JumpIntent.jumpLogin_addShop(isLogin, API.SWEEP,
								mMainActivity);
						if (re) {
							//						mActivity.startActivity(new Intent(mActivity, ReceivablesActivity.class));
							//						MagnifyImg();// 收款二维码放大
							isAgreeAgreement();
						}
						break;*/
					/*case 100:
						re = JumpIntent.jumpLogin_addShop(isLogin, API.SERVICESTORE, mMainActivity);
						if (re) {// 服务商城

							Intent intent_more = new Intent(mMainActivity, ServiceMallActivity.class);
							mMainActivity.startActivityForResult(intent_more, mMainActivity.SERVICEMALLSHOP_CODE);

						}
						break;*/
				}
			}
		});
	}

	public String getUrl() {
		String url = API.TOSTORE_PRODUCT_MANAGEMENT;
		String bussniessId = BaseActivity.userShopInfoBean.getBusinessId();
		WebProductManagementBean data = new WebProductManagementBean();
		data.setBusinessId(bussniessId);

		String dataJson = JSONObject.toJSON(data).toString();

		url = encryptionUrl(url, dataJson);
		return url;
	}

	/**
	 * 加密
	 */
	private String encryptionUrl(String url, String dataJson) {
		try {
			// 获得的明文数据
			String desStr = dataJson;
			// 转成字节数组
			byte src_byte[] = desStr.getBytes();

			// MD5摘要
			byte[] md5Str = WebEncryptionUtil.md5Digest(src_byte);
			// 生成最后的SIGN
			String SING = WebEncryptionUtil.byteArrayToHexString(md5Str);

			desStr = CryptTool.getBASE64(dataJson);
			// http://localhost:8080/order/orderContler/?sing=key&data=密文
			return url + "sing=" + SING + "&&data=" + desStr + "&&auth=all";
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return "";
	}


	@Override
	public void onDestroyView() {
		super.onDestroyView();
		ButterKnife.unbind(this);
	}

	public void getServiceMall(ArrayList<ShouyeServiceBean> arraylist) {
		if (arraylist != null && arraylist.size() > 0) {
			mData.clear();
//			mData = arraylist;

			List a = new ArrayList();
			for (ShouyeServiceBean ss : arraylist) {
				if (ss != null && ss.getAppCode().equals("wcm") || ss.getAppCode().equals("qns") || ss.getAppCode().equals("wifi")) {
					mData.add(ss);
				}
			}
			mAdapter.replaceAll(mData);
		}
	}

	private String getSAUrl(String address, int type) {
		String bussniessId = BaseActivity.userShopInfoBean.getBusinessId();
		switch (type) {
			case 1://微信商城
				/*WebProductManagementBean data = new WebProductManagementBean();
				data.setBusinessId(bussniessId);
				String dataJson = com.alibaba.fastjson.JSONObject.toJSON(data)
						.toString();
				String url = encryptionUrl(address, dataJson);*/
				//	return encryptionUrl(address, dataJson);
				return address + "storeId=" + bussniessId;
			/*case 2://货源批发
				return address + "storeId=" + bussniessId;*/
			case 3://智能WIFI
				return address + bussniessId;
		}
		return "";
	}


}




