package com.lianbi.mezone.b.ui;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.lianbi.mezone.b.app.Constants;
import com.lianbi.mezone.b.httpresponse.API;
import com.xizhi.mezone.b.R;

import cn.com.hgh.utils.WebViewInit;
import cn.com.hgh.view.HttpDialog;

/**
 * 简单通用web
 * 
 * @author guanghui.han
 * 
 */

@SuppressLint("ResourceAsColor")
public class WebMoreServiceActivty extends BaseActivity {
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
	String url;
	/**
	 * 需要登陆
	 */
	boolean needLogin = false;
	private final static int REQUEST_LOGIN = 4563;
	private boolean isNeedTitle = false;
	private String gobackurl="";
	private String MyMsg="";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_webactivty, NOTYPE);
		isNeedTitle = getIntent().getBooleanExtra("NEEDNOTTITLE", false);
		Re = getIntent().getBooleanExtra("Re", false);
		needLogin = getIntent().getBooleanExtra(Constants.NEDDLOGIN, false);
		title = getIntent().getStringExtra(T);
		url = getIntent().getStringExtra(U);
		initView();

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		web_webactivty.loadUrl("javascript:clearCache();");
		web_webactivty.removeAllViews();
		web_webactivty.destroy();
	}

	/**
	 * 初始化视图控件
	 */
	void initView() {
		if (isNeedTitle) {
			setPageTitleVisibility(View.GONE);
		} else {
			setPageTitle(title);
			if (needLogin) {
				setPageRightText("登录");
				setPageRightTextColor(R.color.colores_news_01);
			}
		}
		dialog = new HttpDialog(this);
		web_webactivty = (WebView) findViewById(R.id.web_webactivty);
		WebViewInit.WebSettingInit(web_webactivty, this);
		web_webactivty.addJavascriptInterface(new MyJs(), "LinktownB");
		web_webactivty.setWebViewClient(new WebViewClient() {
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
				gobackurl = url;
				if (gobackurl.contains("index")) {
					MyMsg =gobackurl;
				}
//				Log.i("tag","更多服务url------>"+gobackurl);
				dialog.show();
			}

			@Override
			public void onReceivedError(WebView view, int errorCode,
					String description, String failingUrl) {
				dialog.dismiss();
			}
		});
		if (TextUtils.isEmpty(url)) {
			web_webactivty.loadUrl(API.MYCOMPANEY);
		} else if (Re) {
			web_webactivty.loadUrl(url);
		} else {
			web_webactivty.loadUrl(API.HOSTWEBCUR + url);
		}
	}



	class MyJs {
		@JavascriptInterface
		public void getData(String type) {

		}
	}


	@Override
	protected void onTitleLeftClick() {
		if (gobackurl.contains("wap/?appId")){
			web_webactivty.loadUrl(url);//返回第一级目录
		}else if (gobackurl.contains("toDetail")){
               if(MyMsg.contains("index")){
				   web_webactivty.loadUrl(MyMsg);
			   }else{
				   web_webactivty.loadUrl(url);//返回第一级目录
			   }
			   MyMsg="";
		}
		 else if (gobackurl.contains("toList") || gobackurl.contains("index")) {
			finish();//退出
		} else {
			web_webactivty.goBack();//返回
		}
	}
	
	
}
