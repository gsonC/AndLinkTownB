package com.lianbi.mezone.b.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.ValueCallback;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lianbi.mezone.b.bean.MemberMessage;
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
	private MemberMessage mMembermessage;
	private ArrayList<ShopIntroduceImageBean> imagesDel = new ArrayList<ShopIntroduceImageBean>();
	private ArrayList<String>  strimagesDel = new ArrayList<String>();
	Boolean isSelect = false;
	String productName, productDesc, productAmt, new_food="", new_rated, new_price, a, productId, delImageUrls, isMain;
	String imageStr = null;
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
		mMembermessage= (MemberMessage)getIntent().getSerializableExtra("membermessage");
		productId = getIntent().getStringExtra("new_product_id");
		System.out.println("productId..." + productId);
		new_food = getIntent().getStringExtra("new_product_food");
		new_rated = getIntent().getStringExtra("new_product_rated");
		new_price = getIntent().getStringExtra("new_product_price");
		a = getIntent().getStringExtra("new_product_ima");
		edCup.setText(new_food);
		edCeramicCup.setText(new_rated);
		edExchangeIntegral.setText(new_price);
		Uri uri = Uri.parse(a);
//        String  uri=mMembermessage.getProductImages().get(0).getImgUrl();
		Glide.with(RevisionsActivity.this).load(uri).error(R.mipmap.default_head).into(imaBigima);

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


	private void Mosaicimage() {
		StringBuilder stringBuilder = new StringBuilder();
		StringBuilder stringBuilderDel = new StringBuilder();

		if (strimagesDel != null && strimagesDel.size() > 0) {
			for (int i = 0; i < strimagesDel.size(); i++) {
				if (i + 1 == strimagesDel.size()) {
					stringBuilderDel.append(strimagesDel.get(i));
				} else {
					stringBuilderDel.append(strimagesDel.get(i) + ",");
				}
			}
		}
		delImageUrls = stringBuilderDel.toString();
		Log.i("tag","172---->"+delImageUrls);
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
								imageDeal(0);
								imaBigima.setImageBitmap(bm);
								isMain = "Y";


								break;
							case 2:
								imageDeal(1);
								smallImaOne.setImageBitmap(bm);
								isMain = "N";


								break;
							case 3:
								imageDeal(2);
								smallImaTwo.setImageBitmap(bm);
								isMain = "N";


								break;
							case 4:
								imageDeal(3);
								smallImaThree.setImageBitmap(bm);
								isMain = "N";


								break;
							case 5:
								imageDeal(4);
								smallImaFour.setImageBitmap(bm);
								isMain = "N";


								break;
							case 6:
								imageDeal(5);
								smallImaFive.setImageBitmap(bm);
								isMain = "N";


								break;

						}
				}
			}

		}
	}

	String mImgId;

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
		System.out.println("productId 297" + productId);
		System.out.println("productName" + productName);
		System.out.println("productDesc" + productDesc);
		System.out.println("productAmt" + productAmt);
		System.out.println("delImageUrls--->" + delImageUrls);
		System.out.println("imageStr" + imageStr);
		System.out.println("店铺id" + userShopInfoBean.getBusinessId());
		System.out.println("isMain" + isMain);

		try {
			okHttpsImp.updateProduct(OkHttpsImp.md5_key, uuid, "app", reqTime,
					productId, productName, "01", productDesc, productAmt, "Y",
					a, imageStr, userShopInfoBean.getBusinessId(),
					isMain, new MyResultCallback<String>() {
				@Override
				public void onResponseResult(Result result) {

					String reString = result.getData();

					System.out.println("reString316" + reString);

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

	private void imageDeal(int position) {
		try {
			strimagesDel.add(mMembermessage.getProductImages().get(position).getImgUrl());
//			files.remove(position);
		} catch (Exception e2) {
		}
//		try {
//			files.remove(position);
//		} catch (Exception e2) {
//		}
//		biMaps.remove(position);
	}
}