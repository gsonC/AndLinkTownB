package com.lianbi.mezone.b.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.alibaba.fastjson.JSONObject;
import com.lianbi.mezone.b.bean.WebProductManagementBean;
import com.lianbi.mezone.b.httpresponse.API;
import com.lianbi.mezone.b.httpresponse.OkHttpsImp;
import com.lianbi.mezone.b.ui.BaseActivity;
import com.lianbi.mezone.b.ui.BusinessMarketingActivity;
import com.lianbi.mezone.b.ui.DiningTableSettingActivity;
import com.lianbi.mezone.b.ui.MainActivity;
import com.lianbi.mezone.b.ui.ServiceMallActivity;
import com.xizhi.mezone.b.R;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.com.hgh.utils.ContentUtils;
import cn.com.hgh.utils.CryptTool;
import cn.com.hgh.utils.JumpIntent;
import cn.com.hgh.utils.WebEncryptionUtil;

/**
 * @创建者 Administration
 * @创建时间 ＄{DATE} ＄{TIME}
 * @描述 ＄{TOOD}
 * @更新者 ＄auther＄
 * @更新时间 ＄Date＄
 * @更新描述 ＄{TOOD}＄
 */
public class WisdomManagerFragment extends Fragment implements OnClickListener {


	@Bind(R.id.ll_wisdommanage_service)
	LinearLayout llWisdommanageService;
	@Bind(R.id.ll_wisdommanage_marketing)
	LinearLayout llWisdommanageMarketing;
	@Bind(R.id.ll_wisdommanage_features)
	LinearLayout llWisdommanageFeatures;
	@Bind(R.id.ll_wisdommanage_Shouting)
	LinearLayout llWisdommanageShouting;
	@Bind(R.id.ll_wisdommanage_guidance)
	LinearLayout llWisdommanageGuidance;
	@Bind(R.id.ll_wisdommanage_smalltwo)
	LinearLayout llWisdommanageSmalltwo;
	@Bind(R.id.ll_wisdommanage_Servicemall)
	ImageView llWisdommanageServicemall;
	@Bind(R.id.GLZX_sc)
	ScrollView GLZXSc;
	@Bind(R.id.swipe_jiaoyiguanli)
	SwipeRefreshLayout swipeJiaoyiguanli;
	@Bind(R.id.ll_wisdommanage_shop)
	LinearLayout llWisdommanageShop;
	private OkHttpsImp httpsImp;
	private MainActivity mMainActivity;
	public static WisdomManagerFragment jiaoYiGuanLiFragment;

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
			if (BaseActivity.userShopInfoBean != null && !TextUtils.isEmpty(BaseActivity.userShopInfoBean.getBusinessId())) {

			} else {
			}
		} else {
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
		return view;
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

	private void listen() {
		llWisdommanageService.setOnClickListener(this);
		llWisdommanageMarketing.setOnClickListener(this);
		llWisdommanageShouting.setOnClickListener(this);
		llWisdommanageGuidance.setOnClickListener(this);
		llWisdommanageSmalltwo.setOnClickListener(this);
		llWisdommanageFeatures.setOnClickListener(this);
		llWisdommanageServicemall.setOnClickListener(this);
		GLZXSc.setOnClickListener(this);
		swipeJiaoyiguanli.setOnClickListener(this);
		llWisdommanageFeatures.setOnClickListener(this);
	}

	@Override
	public void onClick(View view) {
		boolean isLogin = ContentUtils.getLoginStatus(mMainActivity);
		boolean re = false;
		switch (view.getId()) {
			case R.id.ll_wisdommanage_service://到店服务
				re = JumpIntent.jumpLogin_addShop(isLogin, API.SWEEP, mMainActivity);
				if (re) {
					startActivity(new Intent(getActivity(), DiningTableSettingActivity.class));
				}

				break;
			case R.id.ll_wisdommanage_marketing://商户营销
				re = JumpIntent.jumpLogin_addShop(isLogin, API.SWEEP, mMainActivity);
				if (re) {
					startActivity(new Intent(getActivity(), BusinessMarketingActivity.class));
				}

				break;
			case R.id.ll_wisdommanage_features://店铺特色

				break;
			case R.id.ll_wisdommanage_Shouting://商圈吆喝

				break;
			case R.id.ll_wisdommanage_guidance://专家指导

				break;
			case R.id.ll_wisdommanage_smalltwo://智能小二

				break;
			case R.id.ll_wisdommanage_shop://运营服务

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
}




