package com.lianbi.mezone.b.ui;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;

import com.xizhi.mezone.b.R;

import cn.com.hgh.utils.ContentUtils;

public class QrImgMainActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_qr_img_main);
		getQrimg();
	}

	/**
	 * 获取二维码
	 */
	private void getQrimg(){
		String imgUrl = "http://test.xylbn.cn/wcm/authorize/getWechatCode?redirect_url=http://test.xylbn.cn/wcm/tss/BD2016072615481900000287/145/showOrder?type=1";
		Bitmap bitmap = ContentUtils.createQrBitmap(imgUrl,true,1000,1000);
		ImageView QRImg = (ImageView) findViewById(R.id.ima_Qrimg);
		QRImg.setImageBitmap(bitmap);
	}

}
