package com.lianbi.mezone.b.ui;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ClipData;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
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
import java.io.FileOutputStream;
import java.io.OutputStream;

import Decoder.BASE64Decoder;
import cn.com.hgh.utils.ContentUtils;
import cn.com.hgh.utils.WebViewInit;
import cn.com.hgh.view.HttpDialog;
@SuppressLint("ResourceAsColor")

public class LineTakeNoWebActivity extends BaseActivity {
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
	private String  base64="";
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
	private String bussinessId;
	private String  mImgId;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_line_take_no_web,NOTYPE);
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
		web_webactivty.stopLoading();
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

		/*try {
			if (gobackurl.contains("routerApplication/viewRouter?")) {
				MyMsg =gobackurl;
			}
		} catch (Exception e) {
			ContentUtils.showMsg(this, "数据异常,为了您的数据安全,请退出重新登陆");
		}*/

		dialog = new HttpDialog(this);
		web_webactivty = (WebView) findViewById(R.id.web_webactivty);
		WebViewInit.WebSettingInit(web_webactivty, this);
		web_webactivty.addJavascriptInterface(new MyJavascript(), "LinktownB");
		web_webactivty.setWebViewClient(new WebViewClient() {
			@Override
			public void onPageFinished(WebView view, String url) {
				System.out.println("url132"+url);
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
				System.out.println("url150"+url);
				return true;
			}

			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				gobackurl = url;
				if (url.contains("routerApplication/viewRouter?")) {
					MyMsg = url;
				}

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
				LineTakeNoWebActivity.this.startActivityForResult(
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
				LineTakeNoWebActivity.this.startActivityForResult(
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
				LineTakeNoWebActivity.this.startActivityForResult(
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



	private String gobackurl = "";


	@Override
	protected void onTitleLeftClick() {
		Log.i("tag","gobackurl----》 "+gobackurl);
		if(!gobackurl.contains("http://")){
			finish();
		}
		if(gobackurl.contains("classifyCigController")||gobackurl.contains("qnsBannerController")
				||gobackurl.contains("qnsWarnConfigController")||gobackurl.contains("viewUserQueueInfo")
				||gobackurl.contains("viewFinishUserQueueInfo")) {
			web_webactivty.loadUrl(url);//返回一级目录
		}else if(gobackurl.contains("qns/"+userShopInfoBean.getBusinessId()+"/showUserQueueList")){
			finish();//退出
		}else{
			web_webactivty.goBack();//正常返回
		}


	}
	/*
			 * 判断字符串是否包含一些字符 indexOf
			 */
	public static boolean indexOfString(String src, String dest) {
		boolean flag = false;
		if (src.indexOf(dest)!=-1) {
			flag = true;
		}
		return flag;
	}
	class MyJavascript {
//		@JavascriptInterface
//		public void getData(String type) {}
		/**
		 * 打开相册
		 * @param flag
		 */
//		@JavascriptInterface
//		public void  photoAlbumcut(boolean  flag)
//		{
//
//			if(flag==true) {
////				    mImgId=imgId;
//					photoUtills.startPickPhotoFromAlbumWithCrop();
//			}else{
//			}
//		}
		@JavascriptInterface
		public void  photoAlbumcut(boolean  flag,String  imgId)
		{

			if(flag==true) {
				mImgId=imgId;
				//photoUtills.startPickPhotoFromAlbumWithCrop();
			}else{
			}
		}
		/**
		 * 返回base64
		 *
		 */
		@JavascriptInterface
		public String  getBase64()
		{

			return  base64;

		}
		/**
		 * 返回图片ID
		 *
		 */
		@JavascriptInterface
		public String  getImgId()
		{

			return  mImgId;

		}

		/**
		 * 保存邀请码
		 *
		 */
		@JavascriptInterface
		public void  saveQrcode(final  String  url)

		{

			if(indexOfString(Uri.parse(url).toString(),"data:image/")){
						String  str=Uri.parse(url).toString();
						String jieguo = str.
								substring(str.indexOf(",")+1,
										str.length());
						GenerateImage(jieguo);
					}
//		   web_webactivty.setOnLongClickListener(new  View.OnLongClickListener(){
//
//				@Override
//				public boolean onLongClick(View view) {
//					System.out.println("url388"+url);
//
//					if(indexOfString(Uri.parse(url).toString(),"data:image/")){
//						String  str=Uri.parse(url).toString();
//						String jieguo = str.
//								substring(str.indexOf(",")+1,
//										str.length());
//						GenerateImage(jieguo);
//					}
//
//
//
//				return  true;
//			}
//			});


		}
	}
	//base64字符串转化成图片
	public   void GenerateImage(String imgStr)
	{   //对字节数组字符串进行Base64解码并生成图片
		if (imgStr == null){ //图像数据为空
			return;}
		BASE64Decoder decoder = new BASE64Decoder();
		try
		{
			//Base64解码
			byte[] b = decoder.decodeBuffer(imgStr);
			for(int i=0;i<b.length;++i)
			{
				if(b[i]<0)
				{//调整异常数据
					b[i]+=256;
				}
			}
			//生成jpeg图片
			File appDir = new File(Environment.getExternalStorageDirectory(),
					"invitationcode");
			if (!appDir.exists()) {
				appDir.mkdir();
			}
			String imgFilePath = System.currentTimeMillis() + ".jpg";
			File file = new File(appDir, imgFilePath);
			OutputStream out = new FileOutputStream(file);
//			out.write(b);
			out.write(b,0,b.length);
			out.flush();
			out.close();
			// 其次把文件插入到系统图库
			try {
				MediaStore.Images.Media.insertImage(this.getContentResolver(),
						file.getAbsolutePath(), imgFilePath, null);
			} catch (Exception e) {
				e.printStackTrace();
			}
			// 最后通知图库更新
			sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
					Uri.fromFile(new File(file.getPath()))));
			ContentUtils.showMsg(LineTakeNoWebActivity.this, "已保存");

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

}
