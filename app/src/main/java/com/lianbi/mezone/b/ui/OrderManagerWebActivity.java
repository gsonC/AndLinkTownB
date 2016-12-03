package com.lianbi.mezone.b.ui;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.lianbi.mezone.b.httpresponse.API;
import com.xizhi.mezone.b.R;

import cn.com.hgh.utils.WebViewInit;
import cn.com.hgh.view.HttpDialog;

/*
 * @创建者     master
 * @创建时间   2016/12/3 13:53
 * @描述       订单管理Web
 *
 * @更新者     $Author$ 
 * @更新时间   $Date$
 * @更新描述
 */
public class OrderManagerWebActivity extends BaseActivity {

	private WebView mWeb_webactivty;
	protected HttpDialog dialog;
	private String COMPANYEVENTURL = "";
	private String gobackUrl;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_webactivty, NOTYPE);
		COMPANYEVENTURL = getIntent().getStringExtra("OrderManagerWebUrl");
		initView();
	}

	private void initView() {
		dialog = new HttpDialog(this);
		mWeb_webactivty = (WebView) findViewById(R.id.web_webactivty);
		WebViewInit.WebSettingInit(mWeb_webactivty, this);
		mWeb_webactivty.setWebViewClient(new WebViewClient() {
			@Override
			public void onPageFinished(WebView view, String url) {
				setPageTitle(view.getTitle());
				dialog.dismiss();
			}

			@Override
			public boolean shouldOverrideKeyEvent(WebView view, KeyEvent event) {
				if (event.getKeyCode() == KeyEvent.KEYCODE_BACK
						&& view.canGoBack()) {
					view.goBack();
					return true;
				}
				return super.shouldOverrideKeyEvent(view, event);
			}

			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				view.loadUrl(url);
				return true;
			}

			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				gobackUrl = url;
				dialog.show();

			}

			@Override
			public void onReceivedError(WebView view, int errorCode,
										String description, String failingUrl) {
				dialog.dismiss();
			}
		});

		if (TextUtils.isEmpty(COMPANYEVENTURL)) {
			mWeb_webactivty.loadUrl(API.MYCOMPANEY);
		} else {
			mWeb_webactivty.loadUrl(COMPANYEVENTURL);
		}
	}

	@Override
	protected void onDestroy() {
		mWeb_webactivty.loadUrl("javascript:clearCache();");
		if (mWeb_webactivty != null) {
			ViewGroup parent = (ViewGroup) mWeb_webactivty.getParent();
			if (parent != null) {
				parent.removeView(mWeb_webactivty);
			}
			mWeb_webactivty.removeAllViews();
			mWeb_webactivty.destroy();
		}
		super.onDestroy();
	}

	@Override
	protected void onTitleLeftClick() {
		if (gobackUrl.contains("searchOrders")) {
			mWeb_webactivty.loadUrl(COMPANYEVENTURL);
		} else if (gobackUrl.contains("order/myOrders")) {
			finish();
		} else {
			mWeb_webactivty.goBack();
		}
	}
}
