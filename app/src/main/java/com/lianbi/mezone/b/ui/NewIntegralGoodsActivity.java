package com.lianbi.mezone.b.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.lianbi.mezone.b.photo.FileUtils;
import com.lianbi.mezone.b.photo.PhotoUtills;
import com.lianbi.mezone.b.photo.PickImageDescribe;
import com.xizhi.mezone.b.R;

import java.io.File;

import butterknife.Bind;
import butterknife.ButterKnife;

public class NewIntegralGoodsActivity extends BaseActivity {
	private ValueCallback<Uri> mUploadMessage;
	private final static int FILECHOOSER_RESULTCODE = 1;
	private MyPhotoUtills photoUtills;
	private final int OPENIMAGEFILE = 20000;
	private ValueCallback<Uri[]> mFilePathCallback;
	private String base64 = "";
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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_integral_goods, NOTYPE);
		ButterKnife.bind(this);
		initView();
		listen();

	}

	private void initView() {
		setPageTitle("积分商品");
		photoUtills = new MyPhotoUtills(this);
		ima_Smallima.setOnClickListener(this);
		smallImaOne.setOnClickListener(this);
		smallImaTwo.setOnClickListener(this);
		smallImaThree.setOnClickListener(this);
		smallImaFour.setOnClickListener(this);
		smallImaFive.setOnClickListener(this);

	}

	@Override
	public void onClick(View view) {
		super.onClick(view);
		switch (view.getId()){
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
		}
	}

	private void listen() {
		ima_Smallima.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				photoUtills.startPickPhotoFromAlbumWithCrop();

			}
		});
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
						showImageOne(filePath);
						/*showImageTwo(filePath);
						showImageThree(filePath);
						showImageFour(filePath);
						showImageFive(filePath);*/
						break;

					case PhotoUtills.REQUEST_IMAGE_CROP:
						String photocurrentpath = photoUtills.photoCurrentFile.toString();
//
				}

			}
		}
		if (requestCode != PhotoUtills.REQUEST_IMAGE_FROM_ALBUM_AND_CROP && requestCode != PhotoUtills.REQUEST_IMAGE_CROP) {
			mFilePathCallback.onReceiveValue(results);
			mFilePathCallback = null;
		}

	}

	private void showImage(String filePath) {
		Bitmap bm = BitmapFactory.decodeFile(filePath);
		((ImageView) findViewById(R.id.ima_bigima)).setImageBitmap(bm);

	}
	private void showImageOne(String filePath) {
		Bitmap bm = BitmapFactory.decodeFile(filePath);
		((ImageView) findViewById(R.id.small_imaOne)).setImageBitmap(bm);
	}
	private void showImageTwo(String filePath) {
		Bitmap bm = BitmapFactory.decodeFile(filePath);
		((ImageView) findViewById(R.id.small_imaTwo)).setImageBitmap(bm);
	}
	private void showImageThree(String filePath) {
		Bitmap bm = BitmapFactory.decodeFile(filePath);
		((ImageView) findViewById(R.id.small_imaThree)).setImageBitmap(bm);
	}
	private void showImageFour(String filePath) {
		Bitmap bm = BitmapFactory.decodeFile(filePath);
		((ImageView) findViewById(R.id.small_imaFour)).setImageBitmap(bm);
	}
	private void showImageFive(String filePath) {
		Bitmap bm = BitmapFactory.decodeFile(filePath);
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
//			defaultImageDescribe.setOutputX(480);
//			defaultImageDescribe.setOutputY(360);
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
