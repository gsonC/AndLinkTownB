package com.lianbi.mezone.b.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.lianbi.mezone.b.httpresponse.MyResultCallback;
import com.lianbi.mezone.b.httpresponse.OkHttpsImp;
import com.lianbi.mezone.b.photo.FileUtils;
import com.lianbi.mezone.b.photo.PhotoUtills;
import com.lianbi.mezone.b.photo.PickImageDescribe;
import com.xizhi.mezone.b.R;

import java.io.File;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.com.hgh.utils.AbDateUtil;
import cn.com.hgh.utils.AbStrUtil;
import cn.com.hgh.utils.ContentUtils;
import cn.com.hgh.utils.Picture_Base64;
import cn.com.hgh.utils.Result;

public class RevisionsActivity extends BaseActivity {

	private ValueCallback<Uri> mUploadMessage;
	private final static int FILECHOOSER_RESULTCODE = 1;
	private MyPhotoUtills photoUtills;
	private final int OPENIMAGEFILE = 20000;
	private ValueCallback<Uri[]> mFilePathCallback;
	private String base64 = "";
	private File file;
	@Bind(R.id.ed_Cup)
	EditText edCup;
	@Bind(R.id.ed_CeramicCup)
	EditText edCeramicCup;
	@Bind(R.id.ed_ExchangeIntegral)
	EditText edExchangeIntegral;
	@Bind(R.id.ima_bigima)
	ImageView imaBigima;
	@Bind(R.id.ima_Smallima)
	ImageView ima_Smallima;
	@Bind(R.id.small_imaOne)
	ImageView smallImaOne;
	@Bind(R.id.small_imaTwo)
	ImageView smallImaTwo;
	@Bind(R.id.small_imaThree)
	ImageView smallImaThree;
	@Bind(R.id.small_imaFour)
	ImageView smallImaFour;
	@Bind(R.id.small_imaFive)
	ImageView smallImaFive;
	@Bind(R.id.bt_sure)
	TextView btSure;
	String productName,productType,productDesc,images,storeId;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_integral_goods, NOTYPE);
		ButterKnife.bind(this);
		initView();

	}

	private void initView() {
		productName=edCup.getText().toString();
		productType=edCeramicCup.getText().toString();
		productDesc=edExchangeIntegral.getText().toString();
		setPageTitle("修改积分商品");
		photoUtills = new MyPhotoUtills(this);
		ima_Smallima.setOnClickListener(this);
		smallImaOne.setOnClickListener(this);
		smallImaTwo.setOnClickListener(this);
		smallImaThree.setOnClickListener(this);
		smallImaFour.setOnClickListener(this);
		smallImaFive.setOnClickListener(this);
		btSure.setOnClickListener(this);

	}

	@Override
	public void onClick(View view) {
		super.onClick(view);
		switch (view.getId()) {
			case R.id.ima_Smallima:
				photoUtills.startPickPhotoFromAlbumWithCrop();
				break;
			case R.id.small_imaOne:
				photoUtills.startPickPhotoFromAlbumWithCrop();
				break;
			case R.id.small_imaTwo:
				photoUtills.startPickPhotoFromAlbumWithCrop();
				break;
			case R.id.small_imaThree:
				photoUtills.startPickPhotoFromAlbumWithCrop();
				break;
			case R.id.small_imaFour:
				photoUtills.startPickPhotoFromAlbumWithCrop();
				break;
			case R.id.small_imaFive:
				photoUtills.startPickPhotoFromAlbumWithCrop();
				break;
			case R.id.bt_sure:
				btSure.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						String imageStr = null;
						if (file != null) {
							imageStr = Picture_Base64.GetImageStr(file.toString());
							GetupdateProduct();
							finish();
						}
					}
				});
				break;
		}
	}

	/**
	 * 修改产品
	 * @param
	 * @param
	 * @param
	 */
	String id;
	private void GetupdateProduct(){
		String reqTime = AbDateUtil.getDateTimeNow();
		String uuid = AbStrUtil.getUUID();
		try {
			okHttpsImp.updateProduct(OkHttpsImp.md5_key,
					uuid,reqTime,"app",id,
					new MyResultCallback<String>() {
				@Override
				public void onResponseResult(Result result) {
					String reString=result.getData();
					System.out.println("reString"+reString);
					ContentUtils.showMsg(RevisionsActivity.this,"修改产品成功");
				}

				@Override
				public void onResponseFailed(String msg) {
					ContentUtils.showMsg(RevisionsActivity.this,"修改产品失败");
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}



	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		super.onActivityResult(requestCode, resultCode, intent);

		if (requestCode == FILECHOOSER_RESULTCODE) {
			if (null == mUploadMessage) return;
			Uri result = intent == null || resultCode != RESULT_OK ? null : intent.getData();
			if (result == null) {
				mUploadMessage.onReceiveValue(null);
				mUploadMessage = null;
				return;
			}
			if ("content".equals(result.getScheme())) {
				String path = PhotoUtills.getPath(this, result);

				if (path != null) result = Uri.fromFile(new File(path));
			}
			mUploadMessage.onReceiveValue(result);
			mUploadMessage = null;
		}
		if (requestCode != OPENIMAGEFILE || mFilePathCallback == null) {
			if (requestCode == PhotoUtills.REQUEST_IMAGE_FROM_ALBUM_AND_CROP || requestCode == PhotoUtills.REQUEST_IMAGE_CROP) {
			} else {
				return;
			}
		}
		Uri[] results = null;
//		&& Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP
		if (resultCode == Activity.RESULT_OK) {
			if (intent == null) {
			} else {
				switch (requestCode) {

					case PhotoUtills.REQUEST_IMAGE_FROM_ALBUM_AND_CROP:
						Uri uri = intent.getData();
						String filePath = PhotoUtills.getPath(this, uri);
						FileUtils.copyFile(filePath, PhotoUtills.photoCurrentFile.toString(), true);
						photoUtills.startCropImage();
						showImage(filePath);
						ima_Smallima.setVisibility(View.GONE);
				}

			}
		}
		if (requestCode != PhotoUtills.REQUEST_IMAGE_FROM_ALBUM_AND_CROP && requestCode != PhotoUtills.REQUEST_IMAGE_CROP) {
			mFilePathCallback.onReceiveValue(results);
			mFilePathCallback = null;
		}

	}

	/*
	   展示图片
	 */
	private void showImage(String filePath) {
		Bitmap bm = BitmapFactory.decodeFile(filePath);
		((ImageView) findViewById(R.id.ima_bigima)).setImageBitmap(bm);
		((ImageView) findViewById(R.id.small_imaOne)).setImageBitmap(bm);
		((ImageView) findViewById(R.id.small_imaTwo)).setImageBitmap(bm);
		((ImageView) findViewById(R.id.small_imaThree)).setImageBitmap(bm);
		((ImageView) findViewById(R.id.small_imaFour)).setImageBitmap(bm);
		((ImageView) findViewById(R.id.small_imaFive)).setImageBitmap(bm);
	}


	/**
	 * 图像裁剪实现类
	 *
	 * @author guanghui.han
	 */
	class MyPhotoUtills extends PhotoUtills {

		public MyPhotoUtills(Context ct) {
			super(ct);
			super.initPickView();
		}

		@Override
		protected PickImageDescribe getPickImageDescribe() {
			if (defaultImageDescribe == null) {
				defaultImageDescribe = new PickImageDescribe();
			}
			// 设置页设置头像，裁剪比例1:1
			defaultImageDescribe.setFile(photoCurrentFile);
			defaultImageDescribe.setOutputX(640);
			defaultImageDescribe.setOutputY(435);
			defaultImageDescribe.setAspectX(4);
			defaultImageDescribe.setAspectY(3);
			defaultImageDescribe.setOutputFormat(DEFAULT_IMG_FORMAT);
			return defaultImageDescribe;
		}
	}

	public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, WebChromeClient.FileChooserParams fileChooserParams) {
		mFilePathCallback = filePathCallback;
		Intent i = new Intent(Intent.ACTION_GET_CONTENT);
		i.addCategory(Intent.CATEGORY_OPENABLE);
		i.setType("*/*");
		startActivityForResult(Intent.createChooser(i, "File Browser"), OPENIMAGEFILE);

		return true;
	}

}




