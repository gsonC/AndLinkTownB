package com.lianbi.mezone.b.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.ValueCallback;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lianbi.mezone.b.bean.ShopIntroduceImageBean;
import com.lianbi.mezone.b.httpresponse.MyResultCallback;
import com.lianbi.mezone.b.httpresponse.OkHttpsImp;
import com.lianbi.mezone.b.photo.FileUtils;
import com.lianbi.mezone.b.photo.PhotoUtills;
import com.lianbi.mezone.b.photo.PickImageDescribe;
import com.xizhi.mezone.b.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.com.hgh.utils.AbDateUtil;
import cn.com.hgh.utils.AbStrUtil;
import cn.com.hgh.utils.ContentUtils;
import cn.com.hgh.utils.Picture_Base64;
import cn.com.hgh.utils.Result;

public class NewIntegralGoodsActivity extends BaseActivity {
	private ValueCallback<Uri> mUploadMessage;
	private final static int FILECHOOSER_RESULTCODE = 1;
	private MyPhotoUtills photoUtills;
	private final int OPENIMAGEFILE = 20000;
	private ValueCallback<Uri[]> mFilePathCallback;
	private String base64 = "";
	private List<File> file;
	private int img_flag;
	private ArrayList<ShopIntroduceImageBean> imagesDel = new ArrayList<ShopIntroduceImageBean>();
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
	String productName, productDesc, productAmt;
	String imageStr = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_integral_goods, NOTYPE);
		ButterKnife.bind(this);
		initView();

	}

	private void initView() {
		file = new ArrayList<File>();
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
				img_flag = 1;
				photoUtills.startPickPhotoFromAlbumWithCrop();
				break;
			case R.id.small_imaOne:
				img_flag = 2;
				photoUtills.startPickPhotoFromAlbumWithCrop();
				break;
			case R.id.small_imaTwo:
				img_flag = 3;
				photoUtills.startPickPhotoFromAlbumWithCrop();

				break;
			case R.id.small_imaThree:
				img_flag = 4;
				photoUtills.startPickPhotoFromAlbumWithCrop();
				break;
			case R.id.small_imaFour:
				img_flag = 5;
				photoUtills.startPickPhotoFromAlbumWithCrop();
				break;
			case R.id.small_imaFive:
				img_flag = 6;
				photoUtills.startPickPhotoFromAlbumWithCrop();
				break;
			case R.id.bt_sure:

				getImageUrl();
				getProduct();


				break;
		}
	}

	/**
	 * 获得图片路径并裁剪
	 */
   private void getImageUrl(){
	   StringBuilder stringBuilder = new StringBuilder();
	   StringBuilder stringBuilderDel = new StringBuilder();
	   String delImageUrls = "";
	   if (imagesDel != null && imagesDel.size() > 0) {
		   for (int i = 0; i < imagesDel.size(); i++) {
			   if (i + 1 == imagesDel.size()) {
				   stringBuilderDel.append(imagesDel.get(i).getImageUrl());
			   } else {
				   stringBuilderDel.append(imagesDel.get(i).getImageUrl() + ",");
			   }
		   }
	   }
	   delImageUrls = stringBuilderDel.toString();
	   if (file != null && file.size() > 0) {
		   for (int i = 0; i < file.size(); i++) {
			   if (i + 1 == file.size()) {
				   stringBuilder.append(Picture_Base64.GetImageStr(file.get(i).toString()));
			   } else {
				   stringBuilder.append(Picture_Base64.GetImageStr(file.get(i).toString()) + ",");
			   }
		   }
	   }
	   imageStr = stringBuilder.toString();
	   System.out.println("imageStr" + imageStr);
	   productName = edCup.getText().toString().trim();
	   productDesc = edCeramicCup.getText().toString().trim();
	   productAmt = edExchangeIntegral.getText().toString().trim();
	   if (TextUtils.isEmpty(productName)) {
		   ContentUtils.showMsg(NewIntegralGoodsActivity.this, "请输入商品名称");
		   return;
	   }
	   if (TextUtils.isEmpty(productDesc)) {
		   ContentUtils.showMsg(NewIntegralGoodsActivity.this, "请输入商品简介");
		   return;
	   }
	   if (TextUtils.isEmpty(productAmt)) {
		   ContentUtils.showMsg(NewIntegralGoodsActivity.this, "请输入商品名价格");
		   return;
	   }
   }

	/**
	 * 新增产品接口
	 *
	 * @param
	 * @param
	 * @param
	 */
	private void getProduct() {
		String reqTime = AbDateUtil.getDateTimeNow();
		String uuid = AbStrUtil.getUUID();
		String productName = edCup.getText().toString();
		String productDesc = edCeramicCup.getText().toString();
		String productAmt = edExchangeIntegral.getText().toString();
		try {
			okHttpsImp.addProduct(OkHttpsImp.md5_key, uuid, "app", reqTime, productName, "01", productDesc, productAmt, imageStr, userShopInfoBean.getBusinessId(), new MyResultCallback<String>() {
				@Override
				public void onResponseResult(Result result) {
					String reString = result.getData();
					System.out.println("aDDreString220" + reString);
					ContentUtils.showMsg(NewIntegralGoodsActivity.this, "新增成功");
					Intent intent = new Intent();
					setResult(RESULT_OK, intent);
					finish();
				}

				@Override
				public void onResponseFailed(String msg) {
					ContentUtils.showMsg(NewIntegralGoodsActivity.this, "新增失败");
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("static-access")
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
			if (requestCode == PhotoUtills.REQUEST_IMAGE_FROM_ALBUM_AND_CROP) {

			} else {
				return;
			}
		}
		if(requestCode == PhotoUtills.REQUEST_IMAGE_CROP){
			Glide.with(this).load(file).into(imaBigima);
			Glide.with(this).load(file).into(smallImaOne);
		}
		Uri[] results = null;
//		&& Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP
		if (resultCode == Activity.RESULT_OK) {
			if (intent == null) {
			} else {
				switch (requestCode) {

					case PhotoUtills.REQUEST_IMAGE_FROM_ALBUM_AND_CROP://相册选择并裁剪
						Uri uri = intent.getData();
						String filePath = PhotoUtills.getPath(this, uri);
						FileUtils.copyFile(filePath, PhotoUtills.photoCurrentFile.toString(), true);
						photoUtills.startCropImage();


						Bitmap bm = BitmapFactory.decodeFile(filePath);
						//file = photoUtills.photoCurrentFile;
						file.add(photoUtills.photoCurrentFile);
						switch (img_flag) {
							case 1:
								((ImageView) findViewById(R.id.ima_bigima)).setImageBitmap(bm);
								ima_Smallima.setVisibility(View.GONE);
								break;
							case 2:
								((ImageView) findViewById(R.id.small_imaOne)).setImageBitmap(bm);
								break;
							case 3:
								((ImageView) findViewById(R.id.small_imaTwo)).setImageBitmap(bm);
								break;
							case 4:
								((ImageView) findViewById(R.id.small_imaThree)).setImageBitmap(bm);
								break;
							case 5:
								((ImageView) findViewById(R.id.small_imaFour)).setImageBitmap(bm);
								break;
							case 6:
								((ImageView) findViewById(R.id.small_imaFive)).setImageBitmap(bm);
								break;

							case PhotoUtills.REQUEST_IMAGE_CROP:
								Glide.with(this).load(file).into(imaBigima);
								String photocurrentpath = photoUtills.photoCurrentFile.toString();
								base64 = Picture_Base64.GetImageStr(photocurrentpath);
								int currentapiVersion = android.os.Build.VERSION.SDK_INT;
								break;
						}

				}
			}
			if (requestCode != PhotoUtills.REQUEST_IMAGE_FROM_ALBUM_AND_CROP && requestCode != PhotoUtills.REQUEST_IMAGE_CROP) {
				mFilePathCallback.onReceiveValue(results);
				mFilePathCallback = null;
			}

		}
	}

	 String  mImgId;

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

}
