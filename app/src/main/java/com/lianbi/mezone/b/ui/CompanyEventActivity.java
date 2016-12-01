package com.lianbi.mezone.b.ui;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;

import com.lianbi.mezone.b.httpresponse.API;
import com.lianbi.mezone.b.photo.PopupWindowHelper;
import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXWebpageObject;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.xizhi.mezone.b.R;

import cn.com.hgh.utils.ContentUtils;
import cn.com.hgh.utils.Picture_Base64;
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
	private View pickView;
	private PopupWindow pw;
	private IWXAPI api;
	private String COMPANYEVENTURL = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_webactivty, NOTYPE);
		//通过WXAPIFactory工厂,获取IWXAPI的实例
		api = WXAPIFactory.createWXAPI(this, API.APP_ID, true);
		//将应用的appid注册到微信
		api.registerApp(API.APP_ID);
		COMPANYEVENTURL = getIntent().getStringExtra("CompanyEventUrl");
		initView();
		intShareView();
	}

	private void intShareView() {
		pickView = View.inflate(this, R.layout.wxshare_layout, null);
		Button btn_wxshare_close = (Button) pickView.findViewById(R.id.btn_wxshare_close);
		ImageView img_wxshare_friend = (ImageView) pickView.findViewById(R.id.img_wxshare_friend);
		ImageView img_wxshare_friendcircle = (ImageView) pickView.findViewById(R.id.img_wxshare_friendcircle);
		pickView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				pw.dismiss();
			}
		});
		btn_wxshare_close.setOnClickListener(this);
		img_wxshare_friend.setOnClickListener(this);
		img_wxshare_friendcircle.setOnClickListener(this);

	}

	@Override
	protected void onChildClick(View view) {
		switch (view.getId()) {
			case R.id.img_wxshare_friend:
				pw.dismiss();
				shareWX(0);
				break;
			case R.id.img_wxshare_friendcircle:
				pw.dismiss();
				shareWX(1);
				break;
			case R.id.btn_wxshare_close:
				pw.dismiss();
				break;

		}
	}

	private void shareWX(int flag) {

		//分享网页
		WXWebpageObject webpage = new WXWebpageObject();
		webpage.webpageUrl = API.COMPANYEVENT;

		//用WXTextObject对象初始化一个WXMediaMessage对象
		WXMediaMessage msg = new WXMediaMessage(webpage);
		msg.title = getString(R.string.weixin_title);
		msg.description = getString(R.string.weixin_describe);

		Bitmap thumb = BitmapFactory.decodeResource(getResources(), R.mipmap.icon_wxshare);

		thumb = Bitmap.createScaledBitmap(thumb, 150, 150, true);

		msg.thumbData = Picture_Base64.bmp2byte(thumb,true);

		//构造一个Req
		SendMessageToWX.Req req = new SendMessageToWX.Req();
		req.transaction = buildTransaction("webpage");
		req.message = msg;
		req.scene = flag;
		api.sendReq(req);

	}

	private String buildTransaction(final String type) {
		return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
	}

	private void intPopShareView() {
		if (pw == null) {
			pw = PopupWindowHelper.createPopupWindow(pickView,
					ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
			pw.setAnimationStyle(R.style.slide_up_in_down_out);
		}
		pw.showAtLocation(getWindow().getDecorView(),
				Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM, 0, 0);
	}

	private void initView() {
		dialog = new HttpDialog(this);
		setPageRightResource(R.mipmap.icon_share);
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

		if(TextUtils.isEmpty(COMPANYEVENTURL)){
			mWeb_webactivty.loadUrl(API.MYCOMPANEY);
		}else{
			mWeb_webactivty.loadUrl(COMPANYEVENTURL);
		}
	}

	@Override
	protected void onTitleRightClick1() {
		super.onTitleRightClick1();
		if(api.isWXAppInstalled()){
			intPopShareView();
		}else{
			ContentUtils.showMsg(this, "您还未安装微信客户端,无法使用分享功能");
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
		if (mWeb_webactivty.canGoBack()) {
			mWeb_webactivty.goBack();
		} else {
			finish();
		}
	}
}
