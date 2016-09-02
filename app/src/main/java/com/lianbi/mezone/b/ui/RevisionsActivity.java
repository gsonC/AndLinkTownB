package com.lianbi.mezone.b.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.ValueCallback;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

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

public class RevisionsActivity extends BaseActivity {
	private ValueCallback<Uri> mUploadMessage;
	private final static int FILECHOOSER_RESULTCODE = 1;
	private MyPhotoUtills photoUtills;
	private final int OPENIMAGEFILE = 20000;
	private ValueCallback<Uri[]> mFilePathCallback;
	private String base64 = "";
	private List<File> file;
	private int img_flag;
	private ArrayList<ShopIntroduceImageBean> imagesDel = new ArrayList<ShopIntroduceImageBean>();
	String productId;
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
    String new_food,new_rated,new_price;
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
		productId = getIntent().getStringExtra("new_product_id");
		System.out.println("productId..."+productId);
		new_food=getIntent().getStringExtra("new_product_food");
		new_rated=getIntent().getStringExtra("new_product_rated");
		new_price=getIntent().getStringExtra("new_product_price");
		edCup.setText(new_food);
		edCeramicCup.setText(new_rated);
		edExchangeIntegral.setText(new_price);
	}
	String isMain;
	@Override
	public void onClick(View view) {
		super.onClick(view);

		switch (view.getId()) {
			case R.id.ima_Smallima:
				img_flag = 1;
				photoUtills.startPickPhotoFromAlbumWithCrop();
				isMain="Y";
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


				Mosaicimage();
				GetupdateProduct();


				break;
		}
	}

	/**
	 * 拼接图片地址
	 *
	 * @param
	 * @param
	 * @param
	 */
	String delImageUrls = "";
	private void Mosaicimage() {
		StringBuilder stringBuilder = new StringBuilder();
		StringBuilder stringBuilderDel = new StringBuilder();

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
			ContentUtils.showMsg(RevisionsActivity.this, "请输入商品名称");
			return;
		}
		if (TextUtils.isEmpty(productDesc)) {
			ContentUtils.showMsg(RevisionsActivity.this, "请输入商品简介");
			return;
		}
		if (TextUtils.isEmpty(productAmt)) {
			ContentUtils.showMsg(RevisionsActivity.this, "请输入商品名价格");
			return;
		}

	}

	@SuppressWarnings("static-access")
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		super.onActivityResult(requestCode, resultCode, intent);

		if (resultCode == Activity.RESULT_OK) {
			if (intent == null) {
			} else {
				switch (requestCode) {

					case PhotoUtills.REQUEST_IMAGE_FROM_ALBUM_AND_CROP://相册选择并裁剪
						Uri uri = intent.getData();
						String filePath = PhotoUtills.getPath(this, uri);
						FileUtils.copyFile(filePath, PhotoUtills.photoCurrentFile.toString(), true);
						photoUtills.startCropImage();
						//file = photoUtills.photoCurrentFile;
						file.add(photoUtills.photoCurrentFile);
						break;


					case PhotoUtills.REQUEST_IMAGE_CROP:
						Bitmap bm = PhotoUtills.getBitmap();

						switch (img_flag) {
							case 1:
								imaBigima.setImageBitmap(bm);
								break;
							case 2:
								smallImaOne.setImageBitmap(bm);
								break;
							case 3:
								smallImaTwo.setImageBitmap(bm);
								break;
							case 4:
								smallImaThree.setImageBitmap(bm);
								break;
							case 5:
								smallImaFour.setImageBitmap(bm);
								break;
							case 6:
								smallImaFive.setImageBitmap(bm);
								break;

						}
				}
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


	/**
	 * 修改产品
	 */
	private void GetupdateProduct() {
		String reqTime = AbDateUtil.getDateTimeNow();
		String uuid = AbStrUtil.getUUID();
		try {
			okHttpsImp.updateProduct(OkHttpsImp.md5_key,
					uuid, reqTime, "app",
					productName, productDesc, productAmt,userShopInfoBean.getBusinessId(),imageStr,delImageUrls,productId,isMain,
					new MyResultCallback<String>() {
				@Override
				public void onResponseResult(Result result) {
					String reString = result.getData();
					finish();
					System.out.println("reString291" + reString);
					ContentUtils.showMsg(RevisionsActivity.this, "修改产品成功");
					Intent intent = new Intent();
					setResult(RESULT_OK, intent);
					finish();
				}

				@Override
				public void onResponseFailed(String msg) {
					ContentUtils.showMsg(RevisionsActivity.this, "修改产品失败");
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


}