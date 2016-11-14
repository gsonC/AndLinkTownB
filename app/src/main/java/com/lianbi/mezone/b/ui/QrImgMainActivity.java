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
		String imgUrl = "http://test.xylbn.cn/wcm/authorize/BD2016080113183000000327/151/getWechatCode?redirect_url=http%3A%2F%2Ftest.xylbn.cn%2Fwcm%2F%2Ftss%2FBD2016080113183000000327%2F151%2FshowOrder%3Ftype%3D1";
		Bitmap bitmap = ContentUtils.createQrBitmap(imgUrl,true,1000,1000);
		ImageView QRImg = (ImageView) findViewById(R.id.ima_Qrimg);
		QRImg.setImageBitmap(bitmap);
	}

}
