package com.lianbi.mezone.b.ui;
/*
 * @创建者     Administrator
 * @创建时间   2016/7/12 15:40
 * @描述       预约功能webactivity
 *
 * @更新者     $Author$ 
 * @更新时间   $Date$
 * @更新描述
 */

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ClipData;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.lianbi.mezone.b.app.Constants;
import com.lianbi.mezone.b.httpresponse.API;
import com.lianbi.mezone.b.photo.PhotoUtills;
import com.xizhi.mezone.b.R;

import java.io.File;

import cn.com.hgh.utils.ContentUtils;
import cn.com.hgh.utils.WebViewInit;
import cn.com.hgh.view.HttpDialog;

@SuppressLint("ResourceAsColor")
public class BookWebActivity extends BaseActivity {
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
	Boolean isSave = false;

	/**
	 * 需要登陆
	 */
	boolean needLogin = false;
	private final static int REQUEST_LOGIN = 4563;
	private WebViewClient webs;
	private ValueCallback<Uri> mUploadMessage;
	private ValueCallback<Uri[]> mFilePathCallback;
	private final static int FILECHOOSER_RESULTCODE = 1;
	private boolean isNeedTitle = false;
	private String MyMsg;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_webactivty, NOTYPE);
		isNeedTitle = getIntent().getBooleanExtra("NEEDNOTTITLE", false);
		Re = getIntent().getBooleanExtra("Re", false);
		needLogin = getIntent().getBooleanExtra(Constants.NEDDLOGIN, false);
		url = getIntent().getStringExtra(U);
		title = getIntent().getStringExtra(T);
		webs = new WebViewClient();
		initView();

		initListener();
	}

	private void initListener() {
		boolean is = webs.shouldOverrideUrlLoading(web_webactivty, "");
		// webs.onPageStarted(view, url, favicon);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		web_webactivty.loadUrl("javascript:clearCache();");

	}

	//	public String getString(String str,String str1 ){
	//		int index = str1.indexOf(str); //str1是想要开始截取的字符。str是被截取的字符。
	//		return str.substring(index+1,str.length());
	//	}

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

		try {
			if (url.contains("storeId")) {
				String bb[] = url.split("storeId");
				MyMsg = API.TOSTORESERVICE + "/wcm/sws/businessInfo/goBusinessInfo?storeId" + bb[1];
			}
		} catch (Exception e) {
			ContentUtils.showMsg(this, "数据异常,为了您的数据安全,请退出重新登陆");
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
				if(url.startsWith("http:")||url.startsWith("https:")){
	//
					return false;
				}
				Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
				startActivity( intent );
				view.loadUrl(url);
				return true;

			}

			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				gobackurl = url;
				dialog.show();
			}

			@Override
			public void onReceivedError(WebView view, int errorCode,
										String description, String failingUrl) {
				dialog.dismiss();
			}
		});

		web_webactivty.setWebChromeClient(new WebChromeClient() {
			@Override
			public boolean onShowFileChooser(WebView webView,
											 ValueCallback<Uri[]> filePathCallback,
											 FileChooserParams fileChooserParams) {
				mFilePathCallback = filePathCallback;
				Intent i = new Intent(Intent.ACTION_GET_CONTENT);
				i.addCategory(Intent.CATEGORY_OPENABLE);
				i.setType("*/*");
				startActivityForResult(Intent.createChooser(i, "File Browser"),
						20000);
				return true;
			}

			@Override
			public void onReceivedTitle(WebView view, String title) {
				// showTitle(title);
			}

			// The undocumented magic method override
			// Eclipse will swear at you if you try to put @Override here
			// For Android 3.0+

			public void openFileChooser(ValueCallback<Uri> uploadMsg) {

				mUploadMessage = uploadMsg;
				Intent i = new Intent(Intent.ACTION_GET_CONTENT);
				i.addCategory(Intent.CATEGORY_OPENABLE);
				i.setType("image/*");
				BookWebActivity.this.startActivityForResult(
						Intent.createChooser(i, "File Chooser"),
						FILECHOOSER_RESULTCODE);

			}

			// For Android 3.0+
			public void openFileChooser(ValueCallback uploadMsg,
										String acceptType) {
				mUploadMessage = uploadMsg;
				Intent i = new Intent(Intent.ACTION_GET_CONTENT);
				i.addCategory(Intent.CATEGORY_OPENABLE);
				i.setType("*/*");
				BookWebActivity.this.startActivityForResult(
						Intent.createChooser(i, "File Browser"),
						FILECHOOSER_RESULTCODE);
			}

			// For Android 4.1
			public void openFileChooser(ValueCallback<Uri> uploadMsg,
										String acceptType, String capture) {
				mUploadMessage = uploadMsg;
				Intent i = new Intent(Intent.ACTION_GET_CONTENT);
				i.addCategory(Intent.CATEGORY_OPENABLE);
				i.setType("image/*");
				BookWebActivity.this.startActivityForResult(
						Intent.createChooser(i, "File Chooser"),
						FILECHOOSER_RESULTCODE);

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

	@Override
	protected void onTitleRightClickTv() {
		super.onTitleRightClickTv();
		Intent intent_login = new Intent(this, LoginActivity.class);
		startActivityForResult(intent_login, REQUEST_LOGIN);
	}

	@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		super.onActivityResult(requestCode, resultCode, intent);
		if (requestCode == FILECHOOSER_RESULTCODE) {
			if (null == mUploadMessage)
				return;
			Uri result = intent == null || resultCode != RESULT_OK ? null
					: intent.getData();
			if (result == null) {
				mUploadMessage.onReceiveValue(null);
				mUploadMessage = null;
				return;
			}
			if ("content".equals(result.getScheme())) {
				String path = PhotoUtills.getPath(this, result);
				// String path = ChoosePictureUtil.getRealPathFromURI(this,
				// result);
				if (path != null)
					result = Uri.fromFile(new File(path));
			}
			mUploadMessage.onReceiveValue(result);
			mUploadMessage = null;
		}
		if (requestCode != 20000 || mFilePathCallback == null) {
			return;
		}
		Uri[] results = null;
		if (resultCode == Activity.RESULT_OK
				&& Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			if (intent == null) {
			} else {
				String dataString = intent.getDataString();
				ClipData clipData = intent.getClipData();
				if (clipData != null) {
					results = new Uri[clipData.getItemCount()];
					for (int i = 0; i < clipData.getItemCount(); i++) {
						ClipData.Item item = clipData.getItemAt(i);
						results[i] = item.getUri();
					}
				}
				if (dataString != null)
					results = new Uri[]{Uri.parse(dataString)};
			}
		}
		mFilePathCallback.onReceiveValue(results);
		mFilePathCallback = null;
	}

	class MyJs {
		@JavascriptInterface
		public void getData(String type) {

		}
	}

	private String gobackurl = "";


	@Override
	protected void onTitleLeftClick() {
		if (gobackurl.contains("showRssCreateProduct") || gobackurl.contains("showRssUpdateProduct") || gobackurl.contains("showRssAppointment")
				|| gobackurl.contains("queryReservationDetail")) {
			web_webactivty.loadUrl(url);//返回一级目录
		} else if (gobackurl.contains("viewMyAuthenticationMsg")) {
			web_webactivty.loadUrl(MyMsg);//返回指定页面
		} else if (gobackurl.contains("productsList") || gobackurl.contains("showOrderDetl") || gobackurl.contains("queryTypeList")) {
			finish();//退出
		} else {
			web_webactivty.goBack();//正常返回
		}
		// if (web_webactivty.canGoBack()) {
		// web_webactivty.goBack();
		// } else {
		// finish();
		// }
	}

}