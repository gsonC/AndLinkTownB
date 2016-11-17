package cn.com.hgh.view;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.xizhi.mezone.b.R;

import cn.com.hgh.utils.AbStrUtil;
import cn.com.hgh.utils.ContentUtils;

/**
 * @创建者 Administration
 * @创建时间 ＄{DATE} ＄{TIME}
 * @描述 ＄{TOOD}
 * @更新者 ＄auther＄
 * @更新时间 ＄Date＄
 * @更新描述 ＄{TOOD}＄
 */
public class DialogQrg extends Dialog{

	private Context context;
	private String mUrl;

	ImageView ima;
	public DialogQrg(String url,Context context) {
		super(context, R.style.DialogStyle_4);
		this.context = context;
		this.mUrl = url;
		initView();

	}

	ImageView qrImg;
	TextView tv_dialog_line_cancel;


	public void setTv_dialog_line_cancel(String tv_dialog_line_cancel) {
		this.tv_dialog_line_cancel.setText(tv_dialog_line_cancel);
	}

	private void initView() {
		DisplayMetrics dm = new DisplayMetrics();
		getWindow().getWindowManager().getDefaultDisplay().getMetrics(dm);
		int screenWidth = dm.widthPixels;
		View view = View.inflate(context, R.layout.dialog_qrg, null);
		setContentView(view);


		Window win = getWindow();
		WindowManager.LayoutParams lp = win.getAttributes();
		lp.gravity = Gravity.CENTER;
		lp.width = (int) (screenWidth * 0.8);
		lp.height = ViewGroup.LayoutParams.WRAP_CONTENT;

		String imgUrl = "http://test.xylbn.cn/wcm/authorize/BD2016080113183000000327/151/getWechatCode?redirect_url=http%3A%2F%2Ftest.xylbn.cn%2Fwcm%2F%2Ftss%2FBD2016080113183000000327%2F151%2FshowOrder%3Ftype%3D1";

		if(!AbStrUtil.isEmpty(mUrl)) {

			Bitmap bitmap = ContentUtils.createQrBitmap(mUrl, true, 1000, 1000);
			qrImg = (ImageView) findViewById(R.id.ima_Qrimg);
			qrImg.setImageBitmap(bitmap);
		}
	}


}