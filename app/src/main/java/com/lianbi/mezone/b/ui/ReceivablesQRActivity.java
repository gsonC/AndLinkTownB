package com.lianbi.mezone.b.ui;
/*
 * @创建者     Administrator
 * @创建时间   2016/8/26 11:27
 * @描述       收款二维码
 *
 * @更新者     $Author$ 
 * @更新时间   $Date$
 * @更新描述   ${TODO}
 */

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lianbi.mezone.b.httpresponse.API;
import com.xizhi.mezone.b.R;

import java.io.File;
import java.io.FileOutputStream;

import cn.com.hgh.utils.ContentUtils;
import cn.com.hgh.view.DialogCommon;

public class ReceivablesQRActivity extends BaseActivity implements View.OnLongClickListener {

	private ImageView mImgRqcode;
	private TextView mTvQrAppend;
	private Bitmap mBitmap;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_receivablesqr, NOTYPE);
		initView();
		setLisenter();
	}

	/**
	 * 初始化View
	 */
	private void initView() {
		setPageTitle("收款");
		mImgRqcode = (ImageView) findViewById(R.id.img_rqcode);
		mTvQrAppend = (TextView) findViewById(R.id.tv_qr_append);
		qrCode();
		addUnderLineSpan();
	}

	/**
	 * 二维码
	 */
	private void qrCode() {
		String businessId = userShopInfoBean.getBusinessId();
		String payQR = API.PAYQR+businessId;
		mBitmap = ContentUtils.createQrBitmap(payQR,true,1000,1000);
		mImgRqcode.setImageBitmap(mBitmap);
	}

	/**
	 * 下划线
	 */
	private void addUnderLineSpan() {
		SpannableString spanString = new SpannableString(
				getString(R.string.click_register_agree2));
		UnderlineSpan span = new UnderlineSpan();
		spanString.setSpan(span, 0, spanString.length(),
				Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		mTvQrAppend.append(spanString);
	}

	/**
	 * 添加监听
	 */
	private void setLisenter() {
		mTvQrAppend.setOnClickListener(this);
		mImgRqcode.setOnLongClickListener(this);
	}

	@Override
	protected void onChildClick(View view) {
		super.onChildClick(view);
		switch (view.getId()) {
			case R.id.tv_qr_append:
				Intent intent = new Intent(this, WebActivty.class);
				intent.putExtra(WebActivty.U, API.HOST + API.PAYAGREEMENT);
				intent.putExtra("Re", true);
				startActivity(intent);
				break;
		}
	}


	@Override
	public boolean onLongClick(View v) {
		savePicture();
		return false;
	}

	private void savePicture() {
		try {
			if (null != mBitmap) {
				if (saveImageToGallery(this, mBitmap)) {
					DialogCommon dialogCommon = new DialogCommon(
							this) {

						@Override
						public void onCheckClick() {
							dismiss();
						}

						@Override
						public void onOkClick() {
							Intent intent = new Intent();
							intent.setType("image/*");
							intent.setAction(Intent.ACTION_GET_CONTENT);
							startActivity(intent);
							dismiss();
						}

					};
					dialogCommon.setTextTitle("保存图片成功");
					dialogCommon.setTv_dialog_common_ok("立即查看");
					dialogCommon.setTv_dialog_common_cancel("稍后再看");
					dialogCommon.show();
				}else{
					ContentUtils.showMsg(this, "保存图片失败");
				}
			}

		} catch (Exception e) {

			e.printStackTrace();

		}
	}
	public static boolean saveImageToGallery(Context context, Bitmap bmp) {
		// 首先保存图片
		File appDir = new File(Environment.getExternalStorageDirectory(),
				"老板娘收款");
		if (!appDir.exists()) {
			appDir.mkdir();
		}
		String fileName = System.currentTimeMillis() + ".jpg";
		File file = new File(appDir, fileName);
		try {
			FileOutputStream fos = new FileOutputStream(file);
			bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
			fos.flush();
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

		// 其次把文件插入到系统图库
		try {
			MediaStore.Images.Media.insertImage(context.getContentResolver(),
					file.getAbsolutePath(), fileName, null);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		// 最后通知图库更新
		context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
				Uri.fromFile(new File(file.getPath()))));
		return true;
	}
}