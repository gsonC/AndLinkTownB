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

import com.bumptech.glide.Glide;
import com.lianbi.mezone.b.bean.MemberMessage;
import com.lianbi.mezone.b.httpresponse.MyResultCallback;
import com.lianbi.mezone.b.httpresponse.OkHttpsImp;
import com.lianbi.mezone.b.photo.FileUtils;
import com.lianbi.mezone.b.photo.PhotoUtills;
import com.lianbi.mezone.b.photo.PickImageDescribe;
import com.xizhi.mezone.b.R;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
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
	private ArrayList<MemberMessage.productImages> imagesDel = new ArrayList<>();
	private List<String> imgreDel = new ArrayList<>();
	private ArrayList<MemberMessage.productImages> images;
	private ArrayList<MemberMessage.productImages> imagesother = new ArrayList<>();
	int isNum;
	private String weiDianimgurl;
	Boolean isSelect = false;
	//是否上架
	private final String SHELVES = "Y";
	String productName= "", productDesc= "", productAmt= "", new_food = "", new_rated= "",
			new_price= "", productId = "", delImageUrls= "";
	String isMain = "N";
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
	String shopSourceId = "";
	boolean isBigpivture = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_integral_goods, HAVETYPE);
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
		mMembermessage = (MemberMessage) getIntent().getSerializableExtra("membermessage");
		productId = getIntent().getStringExtra("new_product_id");
		new_food = getIntent().getStringExtra("new_product_food");
		new_rated = getIntent().getStringExtra("new_product_rated");
		new_price = getIntent().getStringExtra("new_product_price");
		weiDianimgurl = getIntent().getStringExtra("new_product_ima");
		images = (ArrayList<MemberMessage.productImages>) getIntent().getSerializableExtra("new_product_image");
		edCup.setText(new_food);
		edCeramicCup.setText(new_rated);
		edExchangeIntegral.setText(new_price);
		showImage();
		String shopSourceId = getIntent().getStringExtra("shopSourceId");
		if (shopSourceId != null && !shopSourceId.equals("")) {
			this.shopSourceId = shopSourceId;
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		weiDianimgurl = "";
	}

	private void showImage() {

		if(!AbStrUtil.isEmpty(weiDianimgurl)){
			Glide.with(RevisionsActivity.this).load(Uri.parse(weiDianimgurl)).error(R.mipmap.add2).into(imaBigima);
		}

		if (images == null) {
			return;
		}
		int imagessize = images.size();

		if (imagessize > 0) {
			for (int i = 0; i < imagessize; i++) {
				if ("main".equals(images.get(i).getImgDesc())) {
					isNum = i;
					Glide.with(RevisionsActivity.this).load(Uri.parse(images.get(i).getImgUrl())).error(R.mipmap.add2).into(imaBigima);
					imagesother = images;
				}
			}
		}
		imagesother.remove(isNum);
		if (imagesother.size() > 0) {
			Glide.with(RevisionsActivity.this).load(Uri.parse(imagesother.get(0).getImgUrl())).error(R.mipmap.add2).into(smallImaOne);
		}
		if (imagesother.size() > 1) {
			Glide.with(RevisionsActivity.this).load(Uri.parse(imagesother.get(1).getImgUrl())).error(R.mipmap.add2).into(smallImaTwo);
		}
		if (imagesother.size() > 2) {
			Glide.with(RevisionsActivity.this).load(Uri.parse(imagesother.get(2).getImgUrl())).error(R.mipmap.add2).into(smallImaThree);
		}
		if (imagesother.size() > 3) {
			Glide.with(RevisionsActivity.this).load(Uri.parse(imagesother.get(3).getImgUrl())).error(R.mipmap.add2).into(smallImaFour);
		}
		if (imagesother.size() > 4) {
			Glide.with(RevisionsActivity.this).load(Uri.parse(imagesother.get(4).getImgUrl())).error(R.mipmap.add2).into(smallImaFive);
		}

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


				break;
		}
	}

	/**
	 * 拼接图片地址
	 */
	private void Mosaicimage() {
		StringBuilder stringBuilder = new StringBuilder();
		StringBuilder stringBuilderDel = new StringBuilder();

		HashSet hs = new HashSet<>(imgreDel);
		imgreDel.clear();
		imgreDel.addAll(hs);

		if (imgreDel != null && imgreDel.size() > 0) {
			int s = imgreDel.size();
			for (int i = 0; i < s; i++) {
				if (i + 1 == s) {
					stringBuilderDel.append(imgreDel.get(i));
				} else {
					stringBuilderDel.append(imgreDel.get(i) + ",");
				}
			}
		}
		//	if (imagesDel != null && imagesDel.size() > 0) {
		//		Log.i("tag", "imagesDel-187--" + imagesDel.size());
		//		for (int i = 0; i < imagesDel.size(); i++) {
		//			if (i + 1 == imagesDel.size()) {
		//				stringBuilderDel.append(imagesDel.get(i).getImgId());
		//			} else {
		//				stringBuilderDel.append(imagesDel.get(i).getImgId() + ",");
		//			}
		//		}
		//	}
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
			ContentUtils.showMsg(RevisionsActivity.this, "请输入兑换所需积分");
			return;
		}
		GetupdateProduct();
	}

	/**
	 * 修改产品
	 */
	private void GetupdateProduct() {
		if(null==productId){
			productId = "";
		}

		try {
			okHttpsImp.updateProduct(OkHttpsImp.md5_key, uuid, "app",
					reqTime, productId, productName, "01", productDesc,
					productAmt, SHELVES, delImageUrls, imageStr, BusinessId, isMain, shopSourceId, new MyResultCallback<String>() {
						@Override
						public void onResponseResult(Result result) {
							String reString = result.getData();
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


	@SuppressWarnings("static-access")
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		try {
			super.onActivityResult(requestCode, resultCode, intent);
		} catch (Exception e) {

		} finally {
			if (resultCode == Activity.RESULT_OK) {
				switch (requestCode) {
					case PhotoUtills.REQUEST_IMAGE_FROM_ALBUM_AND_CROP://相册选择并裁剪
						Uri uri = intent.getData();
						String filePath = PhotoUtills.getPath(this, uri);
						FileUtils.copyFile(filePath, PhotoUtills.photoCurrentFile.toString(), true);
						photoUtills.startCropImage();
						//file = photoUtills.photoCurrentFile;
						break;
					case PhotoUtills.REQUEST_IMAGE_CROP:
						Bitmap bm = PhotoUtills.getBitmap();
					//	file.add(photoUtills.photoCurrentFile);
						switch (img_flag) {
							case 1:
								imaBigima.setImageBitmap(bm);
								file.add(photoUtills.photoCurrentFile);
								isBigpivture = true;
								imageDealMain();
								break;
							case 2:
								smallImaOne.setImageBitmap(bm);
								imageDealOther(0);
								break;
							case 3:
								smallImaTwo.setImageBitmap(bm);
								imageDealOther(1);
								break;
							case 4:
								smallImaThree.setImageBitmap(bm);
								imageDealOther(2);
								break;
							case 5:
								smallImaFour.setImageBitmap(bm);
								imageDealOther(3);
								break;
							case 6:
								smallImaFive.setImageBitmap(bm);
								imageDealOther(4);
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
	 * 主图修改
	 */
	private void imageDealMain() {
		if (isBigpivture == true) {
			this.isMain = "Y";
		}
		try {
			int imagessize = images.size();

			if (isBigpivture == true) {
				if (imagessize > 0) {
					for (int i = 0; i < imagessize; i++) {
						if ("main".equals(images.get(i).getImgDesc())) {
							imagesDel.add(images.get(i));
						}
					}
				}
			}
		} catch (Exception e2) {
			e2.printStackTrace();
		}
	}

	private String imgId1, imgId2, imgId3, imgId4, imgId5;

	private void imageDealOther(int position) {
		//	int imagesothersize = imagesother.size();
		//	if (imagesother.get(position) != null) {
		//		imagesDel.add(imagesother.get(position));
		//	}

		/**
		 * 问题原因 当图片只有两张时 点击第三个按钮出现越界
		 */
		/**
		 * 思路 新建一个list 把原来存在的图片ID存起来 当用户点击那个 替换那个ID 记录换下的ID 多余的向后添加
		 */

		int s = imagesother.size();
		switch (position) {
			case 0:
				if (s > 0) {
					imgreDel.add(imagesother.get(0).getImgId());
				}
				break;
			case 1:
				if (s > 1) {
					imgreDel.add(imagesother.get(1).getImgId());
				}
				break;
			case 2:
				if (s > 2) {
					imgreDel.add(imagesother.get(2).getImgId());
				}
				break;
			case 3:
				if (s > 3) {
					imgreDel.add(imagesother.get(3).getImgId());
				}
				break;
			case 4:
				if (s > 4) {
					imgreDel.add(imagesother.get(4).getImgId());
				}
				break;
		}

		/**
		 * 有风险 会多次添加 遍历去重(重要)
		 */
		for (String str : imgreDel) {
			System.out.println(str);
		}

	}

	/**
	 * 判断imageview是否已经拥有图片
	 */
	private boolean hasImageView(ImageView imgview) {
		if (null != imgview.getDrawable()) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	protected void onStop() {
		super.onStop();
		isBigpivture = false;

	}
}

