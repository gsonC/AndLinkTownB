package com.lianbi.mezone.b.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;

import com.alibaba.fastjson.JSON;
import com.lianbi.mezone.b.bean.ShouYeBannerBean;
import com.lianbi.mezone.b.httpresponse.MyResultCallback;
import com.lianbi.mezone.b.httpresponse.OkHttpsImp;
import com.lianbi.mezone.b.impl.MyShopChange;
import com.lianbi.mezone.b.ui.BaseActivity;
import com.lianbi.mezone.b.ui.MainActivity;
import com.lianbi.mezone.b.ui.ReceivablesActivity;
import com.lianbi.mezone.b.ui.ReceivablesQRActivity;
import com.xizhi.mezone.b.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cn.com.hgh.playview.BaseSliderView;
import cn.com.hgh.playview.BaseSliderView.OnSliderClickListener;
import cn.com.hgh.playview.SliderLayout;
import cn.com.hgh.playview.imp.TextSliderView;
import cn.com.hgh.utils.AbDateUtil;
import cn.com.hgh.utils.AbStrUtil;
import cn.com.hgh.utils.ContentUtils;
import cn.com.hgh.utils.Result;
import cn.com.hgh.utils.ScreenUtils;

/**
 * @author guanghui.han
 */
public class ShouYeFragment extends Fragment implements OnSliderClickListener,
		MyShopChange {

	private SliderLayout mDemoSlider;
	private LinearLayout ad_llt;
	private MainActivity mActivity;
	private OkHttpsImp okHttpsImp;
	private SwipeRefreshLayout swipe_shouye;
	private ProgressBar ad_siderlayout_progressBar;
	private ArrayList<ShouYeBannerBean> ades_ImageEs = new ArrayList<>();
	private boolean isAdSucceedRequest = false;
	private RadioButton mRadioButton_management,mRadioButton_leagues;
	public static final int POSITION0 = 0;
	public static final int POSITION1 = 1;
	private int clickPosition = 3;
	private OnCheckedChangeListener checkListener;
	private FrameLayout mFm_shouye_management,mFm_shouye_leagues;
	private ShouyeManagementFragment mShouyeManagementFragment;
	private ShouyeLeaguesFragment mShouyeLeaguesFragment;
	private FragmentManager mFragmentManager;
	/**
	 * 刷新fm数据
	 */
	public void refreshFMData() {
		if (ContentUtils.getLoginStatus(mActivity)) {
			// mDatas.clear();
			//mActivity.getServiceMall();
			// getServiceMallAll();

		} else {
			// mDatas.clear();
			//mActivity.getServiceMall();

		}
		if (!isAdSucceedRequest) {
			getAadver();
		}
	}


	@Override
	public View onCreateView(LayoutInflater inflater,
							 @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		View view = inflater.inflate(R.layout.fm_shouyefragment, null);
		mActivity = (MainActivity) getActivity();
		okHttpsImp = OkHttpsImp.SINGLEOKHTTPSIMP.newInstance(mActivity);
		initView(view);
		initFragment();
		check_Button();
		setListen();
		changeFuncPage(POSITION0);
		getAadver();
		return view;
	}

	private void initFragment() {
		mFragmentManager = getFragmentManager();
		mShouyeManagementFragment = new ShouyeManagementFragment();
		mShouyeLeaguesFragment = new ShouyeLeaguesFragment();
		mFragmentManager.beginTransaction().replace(R.id.fm_shouye_management,mShouyeManagementFragment).commit();
		mFragmentManager.beginTransaction().replace(R.id.fm_shouye_leagues,mShouyeLeaguesFragment).commit();
	}


	private void initView(View view) {
		ad_siderlayout_progressBar = (ProgressBar) view
				.findViewById(R.id.adeslltview_siderlayout_progressBar);
		ad_siderlayout_progressBar.setVisibility(View.GONE);
		swipe_shouye = (SwipeRefreshLayout) view.findViewById(R.id.swipe_shouye);
		swipe_shouye.setColorSchemeResources(R.color.colores_news_01, R.color.black);
		mRadioButton_management = (RadioButton) view.findViewById(R.id.rboButton_shouyefragment_management);//日常经营
		mRadioButton_leagues = (RadioButton) view.findViewById(R.id.rboButton_shouyefragment_leagues);//商圈联盟
		mFm_shouye_management = (FrameLayout) view.findViewById(R.id.fm_shouye_management);//日常经营
		mFm_shouye_leagues = (FrameLayout) view.findViewById(R.id.fm_shouye_leagues);//商圈联盟
		intAdView(view);
	}

	/**
	 * radiobutton监听
	 */
	private void check_Button() {
		checkListener = new OnCheckedChangeListener(){
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if(isChecked){
					if(mRadioButton_management==buttonView){
						mRadioButton_leagues.setChecked(false);
						mActivity.setPosition(POSITION0);
						changeFuncPage(POSITION0);
					}else if(mRadioButton_leagues==buttonView){
						mRadioButton_management.setChecked(false);
						changeFuncPage(POSITION1);
					}
				}
			}
		};
	}

	private void setListen() {
		swipe_shouye.setOnRefreshListener(new OnRefreshListener() {

			@Override
			public void onRefresh() {
				getAadver();
				swipe_shouye.setRefreshing(false);
			}
		});
		mRadioButton_management.setOnCheckedChangeListener(checkListener);
		mRadioButton_leagues.setOnCheckedChangeListener(checkListener);
	}

	/**
	 * 切换页面
	 * @param position 代表哪页
	 */
	private void changeFuncPage(int position) {
		this.clickPosition = position;
		if(position<POSITION0){
			return;
		}
		if(position==POSITION0){
			mRadioButton_management.setChecked(true);
			mFm_shouye_management.setVisibility(View.VISIBLE);
			mFm_shouye_leagues.setVisibility(View.GONE);
		}else if(position==POSITION1){
			mRadioButton_leagues.setChecked(true);
			mFm_shouye_leagues.setVisibility(View.VISIBLE);
			mFm_shouye_management.setVisibility(View.GONE);
		}
	}

	/**
	 * 获取广告
	 */
	public void getAadver() {
		String reqTime = AbDateUtil.getDateTimeNow();
		String uuid = AbStrUtil.getUUID();

		/**
		 * 轮播图
		 */
		try {
			okHttpsImp.getAdvert("F1", new MyResultCallback<String>() {

				@Override
				public void onResponseResult(Result result) {
					mDemoSlider.removeAllSliders();
					String resString = result.getData();
					try {
						JSONObject jsonObject = new JSONObject(resString);
						resString = jsonObject.getString("list");

						ades_ImageEs = (ArrayList<ShouYeBannerBean>) JSON
								.parseArray(resString,
										ShouYeBannerBean.class);
						isAdSucceedRequest = true;

						if (ades_ImageEs != null && ades_ImageEs.size() > 0) {
							for (int i = 0; i < ades_ImageEs.size(); i++) {
								TextSliderView textSliderView = new TextSliderView(
										mActivity, i);
								textSliderView
										.image(ades_ImageEs.get(i).getImageUrl())
										.error(R.mipmap.adshouye);
								textSliderView
										.setOnSliderClickListener(ShouYeFragment.this);
								mDemoSlider.addSlider(textSliderView);
							}
						} else {
							for (int i = 0; i < 3; i++) {
								TextSliderView textSliderView = new TextSliderView(
										mActivity, i);
								textSliderView.image(R.mipmap.adshouye);
								textSliderView
										.setOnSliderClickListener(ShouYeFragment.this);
								mDemoSlider.addSlider(textSliderView);
							}
						}
						mDemoSlider
								.setPresetIndicatorV(SliderLayout.PresetIndicators.Center_Bottom);
						ad_siderlayout_progressBar.setVisibility(View.GONE);
						mDemoSlider.setVisibility(View.VISIBLE);


					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}

				@Override
				public void onResponseFailed(String msg) {
					mDemoSlider.removeAllSliders();
					for (int i = 0; i < 3; i++) {
						TextSliderView textSliderView = new TextSliderView(
								mActivity, i);
						textSliderView.image(R.mipmap.adshouye);
						textSliderView
								.setOnSliderClickListener(ShouYeFragment.this);
						mDemoSlider.addSlider(textSliderView);
					}
					mDemoSlider
							.setPresetIndicatorV(SliderLayout.PresetIndicators.Center_Bottom);
					ad_siderlayout_progressBar.setVisibility(View.GONE);
					mDemoSlider.setVisibility(View.VISIBLE);

				}
			}, uuid, "app", reqTime);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * 广告轮播图
	 *
	 * @param view
	 */
	private void intAdView(View view) {
		mDemoSlider = (SliderLayout) view
				.findViewById(R.id.adeslltview_siderlayout);
		ad_llt = (LinearLayout) view.findViewById(R.id.adeslltview_llt);
		mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Default);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				ScreenUtils.getScreenWidth(mActivity) / 4);
		ad_llt.setLayoutParams(params);
	}

	/**
	 * 用户是否同意协议
	 */
	private void isAgreeAgreement() {
		String reqTime = AbDateUtil.getDateTimeNow();
		String uuid = AbStrUtil.getUUID();
		String bussniessId = BaseActivity.userShopInfoBean.getBusinessId();
		try {
			okHttpsImp.getMemberAgreement(uuid, "app", reqTime, bussniessId, new MyResultCallback<String>() {
				@Override
				public void onResponseResult(Result result) {
					String reString = result.getData();
					if (!TextUtils.isEmpty(reString)) {
						try {
							JSONObject jsonObject = new JSONObject(reString);
							boolean results = jsonObject.getBoolean("results");
							if (results) {
								mActivity.startActivity(new Intent(mActivity, ReceivablesQRActivity.class));
							} else {
								mActivity.startActivity(new Intent(mActivity, ReceivablesActivity.class));
							}
						} catch (JSONException e) {
							e.printStackTrace();
						}

					}
				}

				@Override
				public void onResponseFailed(String msg) {
					ContentUtils.showMsg(mActivity, "连接超时,请稍后再试");
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onSliderClick(BaseSliderView slider) {
		/*
		if (ades_ImageEs != null && ades_ImageEs.size() > 0) {
			String url = ades_ImageEs.get(slider.getP()).getBannerUrl();
			Intent intent = new Intent(mActivity, WebActivty.class);
			//			intent.putExtra(WebActivty.T, ades_ImageEs.get(slider.getP())
			//					.getBannerTitle());
			intent.putExtra(WebActivty.U, url);
			intent.putExtra("Re", true);
			startActivity(intent);
		}
		*/
	}

	@Override
	public void reFresh() {
		mActivity.refreshFMData();
	}


}
