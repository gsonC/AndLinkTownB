package com.lianbi.mezone.b.ui;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.igexin.sdk.PushManager;
import com.lianbi.mezone.b.app.Constants;
import com.lianbi.mezone.b.bean.AppUpDataBean;
import com.lianbi.mezone.b.bean.ShouyeServiceBean;
import com.lianbi.mezone.b.fragment.FinancialOfficeFragment;
import com.lianbi.mezone.b.fragment.GlzxPagerFragment;
import com.lianbi.mezone.b.fragment.MineFragment;
import com.lianbi.mezone.b.fragment.ShouYeFragment;
import com.lianbi.mezone.b.fragment.WisdomManagerFragment;
import com.lianbi.mezone.b.httpresponse.API;
import com.lianbi.mezone.b.httpresponse.MyResultCallback;
import com.lianbi.mezone.b.impl.MyShopChange;
import com.lianbi.mezone.b.photo.PopupWindowHelper;
import com.lianbi.mezone.b.push.PushDemoReceiver;
import com.lianbi.mezone.b.receiver.Downloader;
import com.lianbi.mezone.b.service.MyService;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.xizhi.mezone.b.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cn.com.hgh.utils.AbAppUtil;
import cn.com.hgh.utils.AbDateUtil;
import cn.com.hgh.utils.AbStrUtil;
import cn.com.hgh.utils.AbViewUtil;
import cn.com.hgh.utils.ContentUtils;
import cn.com.hgh.utils.JumpIntent;
import cn.com.hgh.utils.Result;
import cn.com.hgh.view.DialogCommon;
import cn.com.hgh.view.HttpDialog;
import cn.com.hgh.view.expandablebuttonmenu.ExpandableButtonMenu;
import cn.com.hgh.view.expandablebuttonmenu.ExpandableMenuOverlay;

@SuppressLint({"ResourceAsColor", "HandlerLeak"})
public class MainActivity extends BaseActivity implements MyShopChange {
	FrameLayout fm_funcpage0, fm_funcpage1, fm_funcpage2, fm_funcpage3;
	RadioButton rb_shouye, rb_jiaoyiguanli, rb_caiwushi, rb_mine;
	private ImageView img_main_red;
	private ExpandableMenuOverlay em_releasesceneyell;
	private OnCheckedChangeListener checkListener;
	public static final int POSITION0 = 0;
	public static final int POSITION1 = 1;
	public static final int POSITION2 = 2;
	public static final int POSITION3 = 3;
	public static boolean isChangSHpe = false;
	private IWXAPI api;
	MainActivity mActivity;
	private final int POSITION = 2;

	/**
	 * 以下载数据
	 */
	ArrayList<ShouyeServiceBean> mData = new ArrayList<ShouyeServiceBean>();
	/**
	 * 当前的位置
	 */
	public int curPosition = -1;
	Fragment fm_shouye, fm_wisdommanage, fm_caiwushi, fm_mine;
	/**
	 * 店铺位置经纬度
	 */
	Double mLongitude, mLatitude;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.act_mainactivity, HAVETYPE);

		initView();

		//initPickView();

		initFragment();

		check_button();

		listen();

		initGetui();

		changeFuncPage(POSITION0);

		//LocationUtills.initLocationClient(this, this);

		postCID();

		getUpData();

	}

	public void postCID() {
		if (isLogin) {
			postClientId();
		}
	}

	private ArrayList<ShouyeServiceBean> mDatas = new ArrayList<ShouyeServiceBean>();

	/**
	 * 根据用户下载服务对应订单管理显示对应订单项
	 */
	private void typeUserDownload(ArrayList<ShouyeServiceBean> datas) {
		ContentUtils.putSharePre(MainActivity.this, Constants.SHARED_PREFERENCE_NAME, Constants.DDFW, "0");
		ContentUtils.putSharePre(MainActivity.this, Constants.SHARED_PREFERENCE_NAME, Constants.HHPF, "0");
		ContentUtils.putSharePre(MainActivity.this, Constants.SHARED_PREFERENCE_NAME, Constants.YYDD, "0");
		ContentUtils.putSharePre(MainActivity.this, Constants.SHARED_PREFERENCE_NAME, Constants.HAS_PRODUCT, false);
		for (int i = 0; i < datas.size(); i++) {
			if (1 == datas.get(i).getId()) {
				ContentUtils.putSharePre(MainActivity.this, Constants.SHARED_PREFERENCE_NAME, Constants.DDFW, "1");
			} else if (2 == datas.get(i).getId()) {
				ContentUtils.putSharePre(MainActivity.this, Constants.SHARED_PREFERENCE_NAME, Constants.HHPF, "3");
			} else if (4 == datas.get(i).getId()) {
				ContentUtils.putSharePre(MainActivity.this, Constants.SHARED_PREFERENCE_NAME, Constants.YYDD, "4");
			}
		}
		for (int i = 0; i < datas.size(); i++) {
			ShouyeServiceBean bean = datas.get(i);
			if (bean != null && !TextUtils.isEmpty(bean.getHasProduct()) && bean.getHasProduct().equals("Y")) {
				ContentUtils.putSharePre(MainActivity.this, Constants.SHARED_PREFERENCE_NAME, Constants.HAS_PRODUCT, true);
				break;
			}
		}
	}

	/**
	 * 个推服务初始化
	 */
	private void initGetui() {
		PushManager.getInstance().initialize(this.getApplicationContext());
	}

	@Override
	protected void onDestroy() {
		PushDemoReceiver.payloadData.delete(0, PushDemoReceiver.payloadData.length());
		Intent mIntentService = new Intent(MainActivity.this, MyService.class);
		stopService(mIntentService);
		super.onDestroy();
	}

	/**
	 * status 1:已更新；2：自定义更新；3：必须更新
	 */
	public void getUpData() {
		final String vName = AbAppUtil.getAppVersionName(this);
		String reqTime = AbDateUtil.getDateTimeNow();
		String uuid = AbStrUtil.getUUID();
		try {
			okHttpsImp.getEdition(uuid, "app", reqTime, vName, new MyResultCallback<String>() {

				@Override
				public void onResponseResult(Result result) {
					final AppUpDataBean uB = com.alibaba.fastjson.JSONObject.parseObject(result.getData(), AppUpDataBean.class);
					String status = uB.getCoerceModify();
					if (!uB.getVersion().equals("V" + AbAppUtil.getAppVersionName(MainActivity.this))) {
						((MineFragment) fm_mine).setRedDotShow();
						if (status.equals("Y")) {
							mustUp = true;
							DialogCommon dialogCommon = new DialogCommon(MainActivity.this) {

								@Override
								public void onOkClick() {
									mustUp = false;
									downApp(uB);
									dismiss();
								}

								@Override
								public void onCheckClick() {
									dismiss();

								}
							};
							dialogCommon.setOnDismissListener(new DialogInterface.OnDismissListener() {

								@Override
								public void onDismiss(DialogInterface arg0) {
									if (mustUp) {
										MainActivity.this.exit();
									}

								}
							});
							dialogCommon.setTextTitle("必须更新了:" + uB.getVersion());
							dialogCommon.setTv_dialog_common_ok("更新");
							dialogCommon.setTv_dialog_common_cancelV(View.GONE);
							dialogCommon.show();
						} else if (status.equals("N")) {
							String edition = "V" + vName;
							if (!edition.equals(uB.getVersion())) {
								DialogCommon dialogCommon = new DialogCommon(MainActivity.this) {

									@Override
									public void onOkClick() {
										dismiss();
										downApp(uB);
									}

									@Override
									public void onCheckClick() {
										dismiss();
									}
								};
								dialogCommon.setTextTitle("有更新了:" + uB.getVersion());
								dialogCommon.setTv_dialog_common_ok("更新");
								dialogCommon.setTv_dialog_common_cancel("取消");
								dialogCommon.show();
							}
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

	/**
	 * 是否比更
	 */
	protected boolean mustUp = true;

	private void downApp(AppUpDataBean uB) {
		String url = uB.getUrl();
		Downloader downloader = Downloader.getInstance(MainActivity.this);
		long id = downloader.download(url);
		ContentUtils.putSharePre(MainActivity.this, Constants.SHARED_PREFERENCE_NAME, Constants.APPDOWNLOAD_ID, id);
	}

	HttpDialog httpDialog;

	/**
	 * 定位
	 */
	// @Override
	// protected void onTitleLeftClick() {
	// if (httpDialog == null) {
	// httpDialog = new HttpDialog(this);
	// httpDialog.setMessage("正在定位...");
	// }
	// httpDialog.show();
	// LocationUtills.initLocationClient(this, this);
	// }

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
		pw.showAsDropDown(ivTitleRight, 0, (int) AbViewUtil.dip2px(this, 2));
	}

	/**
	 * 初始化pop
	 */
	public void initPickView() {
		pickView = View.inflate(this, R.layout.mainpoplayout, null);
		TextView mainpoplayout_tvlist = (TextView) pickView.findViewById(R.id.mainpoplayout_tvlist);
		TextView mainpoplayout_tvxia = (TextView) pickView.findViewById(R.id.mainpoplayout_tvxia);
		mainpoplayout_tvlist.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// 订单列表
				pw.dismiss();
				Intent intent_more = new Intent(MainActivity.this, OrderProductListActivity.class);
				startActivity(intent_more);
			}
		});
		mainpoplayout_tvxia.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// 订单生成
				pw.dismiss();
				startActivity(new Intent(MainActivity.this, OrderProductActivity.class));
			}
		});

	}

	// @Override
	// protected void onTitleTextClick() {
	// super.onTitleTextClick();
	// Intent intent_more = new Intent(this, SimapleActivity.class);
	// startActivity(intent_more);
	// }

	@Override
	protected void onTitleRightClick1() {
		super.onTitleRightClick1();
		boolean re = false;
		switch (curPosition) {
			case POSITION0:// 二维码扫描
				// re = JumpIntent.jumpLogin_addShop(isLogin, API.SWEEP, this);
				// if (re) {
				// // pickImage();
				// startActivityForResult((new Intent(this,
				// OrderProductActivity.class)),
				// GlzxPagerFragment.OrderProductActivity_code);
				// }
				break;
			case POSITION3:// 其他
				re = JumpIntent.jumpLogin_addShop1(isLogin, API.OTHER, this);
				if (re) {
					Intent intent_more = new Intent(this, OtherActivity.class);
					startActivityForResult(intent_more, OTHERACTIVITY_CODE);
				}
				break;

		}
	}

	/**
	 * 是否切换店铺
	 */
	private boolean isChange = false;

	@Override
	protected void onTitleRightClickTv() {
		super.onTitleRightClickTv();
		boolean re = false;
		switch (curPosition) {
			case POSITION0:
				re = JumpIntent.jumpLogin_addShop(isLogin, API.SHOPS, this);
				if (re) {
					isChange = true;
					ChangeShopActivity.myShopChange = this;
					Intent intent_more = new Intent(this, ChangeShopActivity.class);
					startActivity(intent_more);
				}
				break;
			case POSITION2:
				re = JumpIntent.jumpLogin_addShop(isLogin, API.SHOPS, this);
				if (re) {
					//Intent intent = new Intent(this, IncomeActivity.class);
					//startActivity(intent);
					//break;
					Intent intent1 = new Intent(MainActivity.this, CompanyEventActivity.class);
					intent1.putExtra("CompanyEventUrl",API.SCENECOLLECTION);
					startActivity(intent1);
//					ContentUtils.showMsg(MainActivity.this, "敬请期待");
					break;
				}
			case POSITION3:
				if (isLogin) {
					DialogCommon dialog = new DialogCommon(MainActivity.this) {
						@Override
						public void onCheckClick() {
							dismiss();
						}

						@Override
						public void onOkClick() {
							ContentUtils.putSharePre(MainActivity.this, Constants.SHARED_PREFERENCE_NAME, Constants.USERHEADURL, userShopInfoBean.getPersonHeadUrl());

							ContentUtils.putSharePre(MainActivity.this, Constants.SHARED_PREFERENCE_NAME, Constants.LOGINED_IN, false);
							userShopInfoBean = null;
							refreshFMData();
							setPageRightTextVisibility(View.INVISIBLE);
							Intent intent = new Intent();
							intent.setClass(MainActivity.this, LoginAndRegisterActivity.class);
							startActivity(intent);
							finish();
							dismiss();
							ContentUtils.showMsg(MainActivity.this, "已退出登录");
						}
					};
					dialog.setTextTitle("是否退出");
					dialog.setTv_dialog_common_ok("退出");
					dialog.setTv_dialog_common_cancel("取消");
					dialog.show();

				} else {
					ContentUtils.showMsg(this, "请先登录");
				}
				break;
		}
	}

	public static final int REQUEST_CHANKAN = 12453;
	private final int OTHERACTIVITY_CODE = 3002;
	public final int SERVICESHOPACTIVITY_CODE = 30089;
	public static final int MYSHOPACTIVITY_CODE = 2004;
	public static final int SERVICEMALLSHOP_CODE = 30726;

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) {
			switch (requestCode) {
				case REQUEST_CHANKAN:// 登录成功返回
					// getServiceMall();
					//postClientId();
					userShopInfoBean.getSharePreString();
					initCommonParameter();
					refreshFMData();
					setPageRightTextVisibility(View.VISIBLE);
					break;
				case OTHERACTIVITY_CODE:// 其他返回
					refreshFMData();
				case SERVICESHOPACTIVITY_CODE:// 服务商昵称返回
					// ((ShouYeFragment)fm_shouye).refreshService();
					break;
				case GlzxPagerFragment.OrderProductActivity_code:// 扫码回调
					// reFShouP();
					break;
				// case MYSHOPACTIVITY_CODE:// 我的商户回调
				// refreshFMData();
				// break;
				case SERVICEMALLSHOP_CODE: //服务商城返回
					getServiceMall();
					break;
			}
		}
	}

	private String mClientId;

	private void postClientId() {
		if (!TextUtils.isEmpty(userShopInfoBean.getBusinessId())) {
			/**
			 * 获取手机唯一识别码CID
			 */
			if (PushManager.getInstance().getClientid(this) != null) {
				mClientId = PushManager.getInstance().getClientid(this);
			}
			String reqTime = AbDateUtil.getDateTimeNow();
			String uuid = AbStrUtil.getUUID();
			try {
				okHttpsImp.postPhoneClientId(uuid, "app", reqTime, userShopInfoBean.getUserId(), userShopInfoBean.getBusinessId(), mClientId, "01", new MyResultCallback<String>() {

					@Override
					public void onResponseResult(Result result) {
					}

					@Override
					public void onResponseFailed(String msg) {

					}
				});
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 刷新fm数据
	 */
	public void refreshFMData() {
		((ShouYeFragment) fm_shouye).refreshFMData();
		((WisdomManagerFragment) fm_wisdommanage).refreshFMData();
		//((FinancialOfficeFragment) fm_caiwushi).refreshFMData();
		((MineFragment) fm_mine).refreshFMData();
	}

	/**
	 * 初始化四大布局
	 */
	private void initFragment() {
		fm_shouye = new ShouYeFragment();
		fm_wisdommanage = new WisdomManagerFragment();
		fm_caiwushi = new FinancialOfficeFragment();
		fm_mine = new MineFragment();

		fm.beginTransaction().replace(R.id.fm_funcpage0, fm_shouye).commit();
		fm.beginTransaction().replace(R.id.fm_funcpage1, fm_wisdommanage).commit();
		fm.beginTransaction().replace(R.id.fm_funcpage2, fm_caiwushi).commit();
		fm.beginTransaction().replace(R.id.fm_funcpage3, fm_mine).commit();

	}

	/**
	 * 监听器
	 */
	private void listen() {
		rb_shouye.setOnCheckedChangeListener(checkListener);
		rb_jiaoyiguanli.setOnCheckedChangeListener(checkListener);
		rb_caiwushi.setOnCheckedChangeListener(checkListener);
		rb_mine.setOnCheckedChangeListener(checkListener);
		em_releasesceneyell.setOnMenuButtonClickListener(new ExpandableButtonMenu.OnMenuButtonClick() {
			@Override
			public void onClick(ExpandableButtonMenu.MenuButton action) {
				switch (action) {
					case LEFT:
						em_releasesceneyell.mButtonMenu.toggle();
						Intent intent = new Intent(MainActivity.this, LeaguesPublishYellActivity.class);
						intent.putExtra("isfrommainactivity","true");
                        startActivity(intent);
						break;
					case RIGHT:
						em_releasesceneyell.mButtonMenu.toggle();
						Intent intent1 = new Intent(MainActivity.this, CompanyEventActivity.class);
						intent1.putExtra("CompanyEventUrl",API.SCENECOLLECTION);
						startActivity(intent1);
						break;
				}
			}
		});
	}

	private int clickPosition = 5;

	/**
	 * 切换页面
	 *
	 * @param position
	 */
	public void changeFuncPage(int position) {
		this.clickPosition = position;
		if (position < POSITION0)
			return;
		if (position == POSITION0) {
			curPosition = POSITION0;
			//	titleShouYe();
			//	setPageBackVisibility(View.INVISIBLE);
			setPageTitleVisibility(View.GONE);
			//	setPageRightImageVisibility();
			rb_shouye.setChecked(true);
			fm_funcpage0.setVisibility(View.VISIBLE);
			fm_funcpage1.setVisibility(View.GONE);
			fm_funcpage2.setVisibility(View.GONE);
			fm_funcpage3.setVisibility(View.GONE);
		} else if (position == POSITION1) {
			curPosition = POSITION1;
			setPageTitleVisibility(View.VISIBLE);
			//setPageRightTextVisibility(View.GONE);
			((WisdomManagerFragment) fm_wisdommanage).refreshFMData();
			setPageTitle("智慧应用");
			setPageRightText("发起场景");
			tvTitleLeft.setText("智慧应用");
			tvTitleRight.setVisibility(View.INVISIBLE);
			tvTitleLeft.setVisibility(View.INVISIBLE);
			setPageBackVisibility(View.INVISIBLE);
			setPageRightImageVisibility();
			rb_jiaoyiguanli.setChecked(true);
			fm_funcpage2.setVisibility(View.GONE);
			fm_funcpage3.setVisibility(View.GONE);
			fm_funcpage0.setVisibility(View.GONE);
			fm_funcpage1.setVisibility(View.VISIBLE);
		} else if (position == POSITION2) {
			curPosition = POSITION2;
			setPageTitleVisibility(View.VISIBLE);
			//	setPageRightTextVisibility(View.GONE);
			setPageTitle("发现场景");
			setPageRightText("发起场景");
			tvTitleLeft.setText("发起场景");
			tvTitleLeft.setVisibility(View.INVISIBLE);
			setPageRightTextColor(R.color.commo_text_color);
			((FinancialOfficeFragment) fm_caiwushi).setUrl(API.FIND_SENCE);
			setPageBackVisibility(View.INVISIBLE);
			setPageRightImageVisibility();
			rb_caiwushi.setChecked(true);
			fm_funcpage2.setVisibility(View.VISIBLE);
			fm_funcpage0.setVisibility(View.GONE);
			fm_funcpage3.setVisibility(View.GONE);
			fm_funcpage1.setVisibility(View.GONE);
		} else if (position == POSITION3) {
			curPosition = POSITION3;
			setPageTitleVisibility(View.VISIBLE);
			((MineFragment) fm_mine).refreshFMData();
			setPageTitle("我的");
			setPageRightText("退出登录");
			setPageRightTextColor(R.color.commo_text_color);
			tvTitleLeft.setText("退出登录");
			tvTitleLeft.setVisibility(View.INVISIBLE);
			setPageBackVisibility(View.INVISIBLE);
			setPageRightImageVisibility();
			rb_mine.setChecked(true);
			fm_funcpage2.setVisibility(View.GONE);
			fm_funcpage0.setVisibility(View.GONE);
			fm_funcpage3.setVisibility(View.VISIBLE);
			fm_funcpage1.setVisibility(View.GONE);
			if (isLogin) {
				setPageRightTextVisibility(View.VISIBLE);
			} else {
				setPageRightTextVisibility(View.INVISIBLE);
			}
		}
	}

	/**
	 * 底部监听
	 */
	private void check_button() {
		checkListener = new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (isChecked) {
					if (rb_shouye == buttonView) {
						rb_mine.setChecked(false);
						rb_jiaoyiguanli.setChecked(false);
						rb_caiwushi.setChecked(false);
						changeFuncPage(POSITION0);
					} else if (rb_jiaoyiguanli == buttonView) {
						rb_shouye.setChecked(false);
						rb_mine.setChecked(false);
						rb_caiwushi.setChecked(false);
						changeFuncPage(POSITION1);
					} else if (rb_caiwushi == buttonView) {
						rb_shouye.setChecked(false);
						rb_mine.setChecked(false);
						rb_jiaoyiguanli.setChecked(false);
						changeFuncPage(POSITION2);
					} else if (rb_mine == buttonView) {
						rb_shouye.setChecked(false);
						rb_jiaoyiguanli.setChecked(false);
						rb_caiwushi.setChecked(false);
						changeFuncPage(POSITION3);
					}
				}
			}
		};
	}

	//private MsgReceiver mMsgReceiver;
	//private Intent mIntent;

	/**
	 * 初始化视图
	 */
	private void initView() {
		fm_funcpage0 = (FrameLayout) findViewById(R.id.fm_funcpage0);
		fm_funcpage1 = (FrameLayout) findViewById(R.id.fm_funcpage1);
		fm_funcpage2 = (FrameLayout) findViewById(R.id.fm_funcpage2);
		fm_funcpage3 = (FrameLayout) findViewById(R.id.fm_funcpage3);
		rb_shouye = (RadioButton) findViewById(R.id.rb_shouye);
		rb_jiaoyiguanli = (RadioButton) findViewById(R.id.rb_jiaoyiguanli);
		rb_caiwushi = (RadioButton) findViewById(R.id.rb_caiwushi);
		rb_mine = (RadioButton) findViewById(R.id.rb_mine);
		img_main_red = (ImageView) findViewById(R.id.img_main_red);
		em_releasesceneyell = (ExpandableMenuOverlay) findViewById(R.id.em_releasesceneyell);

	}

	private void setShoyYeTitle() {
		if (isLogin) {
			try {
				if (POSITION0 == clickPosition) {
					setPageTitle(ShopName);
				}
			} catch (Exception e) {
				setPageTitle("首页");
			}
		} else {

			setPageTitle("首页");
		}
	}

	/**
	 * 返回键时间间隔
	 */
	private long mExitTime;

	/**
	 * 返回键监听
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK) {

			if ((System.currentTimeMillis() - mExitTime) > 1000) {
				ContentUtils.showMsg(this, getResources().getString(R.string.balck_tuichu));

				mExitTime = System.currentTimeMillis();

			} else {

				//				activityManager.exit();
				//设置返回键跳转到桌面，当进程被kill则跳转3秒倒计时
				Intent intent = new Intent();
				intent.setAction("android.intent.action.MAIN");
				intent.addCategory("android.intent.category.HOME");
				startActivity(intent);
			}

			return true;

		}

		return super.onKeyDown(keyCode, event);

	}

	/**
	 * 设置主页红点显示与否
	 */
	public void setRedPoint(int visible) {
		img_main_red.setVisibility(visible);
	}

	/**
	 * 定位回调
	 */

	/**
	 * @Override public void location(double lng, double lat, String address) {
	 * if (httpDialog != null) {
	 * httpDialog.dismiss();
	 * }
	 * ContentUtils.putSharePre(this, Constants.SHARED_PREFERENCE_NAME, Constants.LATITUDE, lat + "");
	 * ContentUtils.putSharePre(this, Constants.SHARED_PREFERENCE_NAME, Constants.LONGITUDE, lng + "");
	 * ContentUtils.putSharePre(this, Constants.SHARED_PREFERENCE_NAME, Constants.ADDRESS, address);
	 * }
	 */
	@Override
	public void reFresh() {
		userShopInfoBean.getSharePreString();
		initCommonParameter();
		MainActivity.this.refreshFMData();
	}

	public void reFShouP() {

	}

	/**
	 * 首页下拉刷新
	 *
	 * @param position 代表第几页
	 */
	public void SwipeRefreshShouyeData(int position) {
		if (POSITION0 == position) {
			//((ShouyeManagementFragment) mShouyeManagementFragment).SwipeRefreshData();
		} else {

		}

	}

	/**
	 * 获取已有的服务商城列表
	 */
	public void getServiceMall() {
		if (ContentUtils.getLoginStatus(this)) {
			okHttpsImp.getMoreServerMall(new MyResultCallback<String>() {

				@Override
				public void onResponseResult(Result result) {
					String reString = result.getData();
					if (!TextUtils.isEmpty(reString)) {
						try {
							JSONObject jsonObject = new JSONObject(reString);
							reString = jsonObject.getString("appsList");
							if (null != reString) {
								mDatas.clear();
								mDatas = (ArrayList<ShouyeServiceBean>) JSON.parseArray(reString, ShouyeServiceBean.class);

								//								typeUserDownload(mDatas);
/*
								ShouyeServiceBean service = new ShouyeServiceBean();
								service.setDefaultservice(2);
								service.setAppName("收款");
								service.setId(99);
								mDatas.add(0, service);*/
								//
								//								ShouyeServiceBean endservie = new ShouyeServiceBean();
								//								endservie.setDefaultservice(1);
								//								endservie.setAppName("服务商城");
								//								endservie.setId(100);
								//								mDatas.add(mDatas.size(), endservie);
								//								setFill();
								Log.i("tag", "  " + mDatas.size());
								((WisdomManagerFragment) fm_wisdommanage).getServiceMall(mDatas);
							}
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
				}

				@Override
				public void onResponseFailed(String msg) {
					filltheseats();
				}
			}, BaseActivity.userShopInfoBean.getBusinessId());
		} else {

			new Handler().postDelayed(new Runnable() {

				@Override
				public void run() {
					filltheseats();
				}
			}, 50);

		}
	}

	/**
	 * 用户已下载服务添加收款和服务商城
	 */
	public void filltheseats() {
		/*mDatas.clear();
		ShouyeServiceBean service = new ShouyeServiceBean();
		service.setDefaultservice(2);
		service.setAppName("收款");
		service.setId(99);
		mDatas.add(service);*/

		/*ShouyeServiceBean endservie = new ShouyeServiceBean();
		endservie.setIcoUrl("http");
		endservie.setDefaultservice(1);
		endservie.setAppName("服务商城");
		endservie.setId(100);
		mDatas.add(endservie);*/
		setFill();
		((WisdomManagerFragment) fm_wisdommanage).getServiceMall(mDatas);
	}

	/**
	 * 补位填充
	 */
	public void setFill() {
		if (0 != mDatas.size() % 3) {
			int numfill = 0;
			switch (mDatas.size() % 3) {
				case 1:
					numfill = 2;
					break;
				case 2:
					numfill = 1;
					break;
				case 3:
					numfill = 0;
					break;
			}
			for (int i = 0; i < numfill; i++) {
				ShouyeServiceBean filltheseats2 = new ShouyeServiceBean();
				filltheseats2.setDefaultservice(-1);
				filltheseats2.setAppName("");
				filltheseats2.setId(-1);
				mDatas.add(filltheseats2);
			}
		}
	}


}
