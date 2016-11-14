package com.lianbi.mezone.b.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.lianbi.mezone.b.httpresponse.API;
import com.lianbi.mezone.b.ui.MainActivity;
import com.xizhi.mezone.b.R;

import cn.com.hgh.utils.WebViewInit;
import cn.com.hgh.view.HttpDialog;

/**
 * @author hongyu.yang 财务室
 * @time 上午10:10:47
 * @date 2016-1-12
 */
public class FinancialOfficeFragment extends Fragment {

	private MainActivity mActivity;
	/**
	 * 传进来的标题
	 */
	private String title;
	/**
	 * 标题key
	 */
	public static String T = "title";
	/**
	 * url key
	 */
	public static String U = "url";
	/**
	 * 是否注册来
	 */
	public static boolean Re = false;
	WebView web_webactivty;
	protected HttpDialog dialog;
	String url = "";
	/**
	 * 需要登陆
	 */
	boolean needLogin = false;
	private final static int REQUEST_LOGIN = 4563;
	private boolean isNeedTitle = false;

	@Override
	public View onCreateView(LayoutInflater inflater,
							 @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		mActivity = (MainActivity) getActivity();
		View view = inflater.inflate(R.layout.fm_findscene, null);
		initView(view);
		return view;
	}

	public void setUrl(String url){
		this.url = url;
		if (TextUtils.isEmpty(url)) {
			web_webactivty.loadUrl(API.MYCOMPANEY);
		} else {
			web_webactivty.loadUrl(url);
		}
	}

	private void initView(View view) {

		dialog = new HttpDialog(mActivity);
		web_webactivty = (WebView) view.findViewById(R.id.web_fm_findscene);
		WebViewInit.WebSettingInit(web_webactivty, mActivity);
		web_webactivty.addJavascriptInterface(new MyJs(), "LinktownB");
		web_webactivty.setWebViewClient(new WebViewClient() {
			@Override
			public void onPageFinished(WebView view, String url) {
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

	}

	class MyJs {
		@JavascriptInterface
		public void getData(String type) {

		}
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		web_webactivty.setVisibility(View.GONE);
		web_webactivty.loadUrl("javascript:clearCache();");
		web_webactivty.removeAllViews();
		web_webactivty.destroy();
	}
}
