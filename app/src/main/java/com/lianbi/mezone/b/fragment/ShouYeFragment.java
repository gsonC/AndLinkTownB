package com.lianbi.mezone.b.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;

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
import cn.com.hgh.utils.AbDateUtil;
import cn.com.hgh.utils.AbStrUtil;
import cn.com.hgh.utils.ContentUtils;
import cn.com.hgh.utils.Result;
import cn.com.hgh.utils.ScreenUtils;
import cn.com.hgh.view.SegmentedGroup;

/**
 * @author guanghui.han
 */
public class ShouYeFragment extends Fragment implements OnSliderClickListener,
		MyShopChange {

	private SliderLayout mDemoSlider;
	private MainActivity mActivity;
	private OkHttpsImp okHttpsImp;
	private SwipeRefreshLayout swipe_shouye;
	private ProgressBar ad_siderlayout_progressBar;
	private ArrayList<ShouYeBannerBean> ades_ImageEs = new ArrayList<>();
	private boolean isAdSucceedRequest = false;
	private FragmentManager mFragmentManager;
	final String[] titles = {"智慧经营", "商圈联盟"};
	public static final int POSITION0 = 0;
	public static final int POSITION1 = 1;
	private ShouyeLeaguesFragment mShouyeLeaguesFragment;
	private ShouyeManagementFragment mShouyeManagementFragment;
	private ViewPager pager;
	private LinearLayout ad_llt;
	private RadioButton mRbt_shouye_mag;
	private RadioButton mRbt_shouye_union;
	private SegmentedGroup mSeg;
	private ImageView mImg_shouye_cheques;
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
		listen();
		return view;
	}

	@Override
	public void onStart() {
		super.onStart();
		getAadver();

	}

	private void initView(View view) {
		mFragmentManager = getFragmentManager();
		mSeg = (SegmentedGroup) view.findViewById(R.id.seg);
		mRbt_shouye_mag = (RadioButton) view.findViewById(R.id.rbt_shouye_mag);
		mRbt_shouye_union = (RadioButton) view.findViewById(R.id.rbt_shouye_union);
		mImg_shouye_cheques = (ImageView) view.findViewById(R.id.img_shouye_cheques);
		mRbt_shouye_mag.setChecked(true);
		pager = (ViewPager) view.findViewById(R.id.pager_fm_shouye);
		pager.setAdapter(new MyAdapter(mFragmentManager));
		intBannerView(view);
	}

	/**
	 * 初始化banner
	 */
	private void intBannerView(View view) {
		ad_siderlayout_progressBar = (ProgressBar) view.findViewById(R.id.adeslltview_siderlayout_progressBar);
		ad_siderlayout_progressBar.setVisibility(View.GONE);
		mDemoSlider = (SliderLayout) view.findViewById(R.id.adeslltview_siderlayout);
		ad_llt = (LinearLayout) view.findViewById(R.id.adeslltview_llt);
		mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Default);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.MATCH_PARENT,
				ScreenUtils.getScreenWidth(mActivity) / 4);
		ad_llt.setLayoutParams(params);
		ad_llt.setVisibility(View.GONE);
	}

	private void listen() {
		mImg_shouye_cheques.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				isAgreeAgreement();
			}
		});
		pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			@Override
			public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

			}

			@Override
			public void onPageSelected(int position) {
				switch (position) {
					case POSITION0:
						mRbt_shouye_mag.setChecked(true);
						mRbt_shouye_union.setChecked(false);
						break;
					case POSITION1:
						mRbt_shouye_mag.setChecked(false);
						mRbt_shouye_union.setChecked(true);
						break;
				}
			}

			@Override
			public void onPageScrollStateChanged(int state) {

			}
		});
		mRbt_shouye_mag.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (isChecked) {
					pager.setCurrentItem(0);
				}
			}
		});
		mRbt_shouye_union.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (isChecked) {
					pager.setCurrentItem(1);
				}
			}
		});
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
						if (mShouyeManagementFragment != null) {
							mShouyeManagementFragment.getBannerData(resString);
						}
						if (mShouyeLeaguesFragment != null) {
							mShouyeLeaguesFragment.getBannerData(resString);
						}
//						ades_ImageEs = (ArrayList<ShouYeBannerBean>) JSON
//								.parseArray(resString,
//										ShouYeBannerBean.class);
//						isAdSucceedRequest = true;
//
//						if (ades_ImageEs != null && ades_ImageEs.size() > 0) {
//							for (int i = 0; i < ades_ImageEs.size(); i++) {
//								TextSliderView textSliderView = new TextSliderView(
//										mActivity, i);
//								textSliderView
//										.image(ades_ImageEs.get(i).getImageUrl())
//										.error(R.mipmap.adshouye);
//								textSliderView
//										.setOnSliderClickListener(ShouYeFragment.this);
//								mDemoSlider.addSlider(textSliderView);
//							}
//						} else {
//							for (int i = 0; i < 3; i++) {
//								TextSliderView textSliderView = new TextSliderView(
//										mActivity, i);
//								textSliderView.image(R.mipmap.adshouye);
//								textSliderView
//										.setOnSliderClickListener(ShouYeFragment.this);
//								mDemoSlider.addSlider(textSliderView);
//							}
//						}
//						mDemoSlider
//								.setPresetIndicatorV(SliderLayout.PresetIndicators.Center_Bottom);
//						ad_siderlayout_progressBar.setVisibility(View.GONE);
//						mDemoSlider.setVisibility(View.VISIBLE);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}

				@Override
				public void onResponseFailed(String msg) {
					if (mShouyeManagementFragment != null) {
						mShouyeManagementFragment.getBannerData("");
					}
					if (mShouyeLeaguesFragment != null) {
						mShouyeLeaguesFragment.getBannerData("");
					}
//					mDemoSlider.removeAllSliders();
//					for (int i = 0; i < 3; i++) {
//						TextSliderView textSliderView = new TextSliderView(
//								mActivity, i);
//						textSliderView.image(R.mipmap.adshouye);
//						textSliderView
//								.setOnSliderClickListener(ShouYeFragment.this);
//						mDemoSlider.addSlider(textSliderView);
//					}
//					mDemoSlider
//							.setPresetIndicatorV(SliderLayout.PresetIndicators.Center_Bottom);
//					ad_siderlayout_progressBar.setVisibility(View.GONE);
//					mDemoSlider.setVisibility(View.VISIBLE);

				}
			}, uuid, "app", reqTime);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

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

	/**
	 * banner点击
	 */
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

	private class MyAdapter extends FragmentPagerAdapter {

		public MyAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			switch (position) {
				case POSITION0:
					if (null == mShouyeManagementFragment) {
						mShouyeManagementFragment = new ShouyeManagementFragment();
					}
					return mShouyeManagementFragment;
				case POSITION1:
					if (null == mShouyeLeaguesFragment) {
						mShouyeLeaguesFragment = new ShouyeLeaguesFragment();
					}
					return mShouyeLeaguesFragment;
			}
			return null;
		}

		@Override
		public int getCount() {
			return 2;
		}
	}


	@Override
	public void reFresh() {
		mActivity.refreshFMData();
	}


}
