package com.lianbi.mezone.b.ui;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.xizhi.mezone.b.R;

import cn.com.hgh.utils.ContentUtils;
import cn.com.hgh.utils.WebViewInit;
import cn.com.hgh.view.HttpDialog;

/*
 * @创建者     master
 * @创建时间   2016/11/24 15:54
 * @描述       首页场景式售卖
 *
 * @更新者     $Author$ 
 * @更新时间   $Date$
 * @更新描述
 */
public class CompanyEventActivity extends BaseActivity {

	private WebView mWeb_webactivty;
	protected HttpDialog dialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_webactivty, NOTYPE);
		initView();
	}

	private void initView() {
		dialog = new HttpDialog(this);
		setPageRightResource(R.mipmap.icon_back);
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
				dialog.show();
			}

			@Override
			public void onReceivedError(WebView view, int errorCode,
										String description, String failingUrl) {
				dialog.dismiss();
			}
		});
		mWeb_webactivty.loadUrl("https://www.baidu.com/");
	}

	@Override
	protected void onTitleRightClick1() {
		super.onTitleRightClick1();
		ContentUtils.showMsg(this,"微信分享");
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		mWeb_webactivty.loadUrl("javascript:clearCache();");
		mWeb_webactivty.removeAllViews();
		mWeb_webactivty.destroy();
	}

	@Override
	protected void onTitleLeftClick() {
		if (mWeb_webactivty.canGoBack()) {
			mWeb_webactivty.goBack();
		} else {
			finish();
		}
	}
}
