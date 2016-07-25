package cn.com.hgh.view;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.xizhi.mezone.b.R;

import java.io.File;
import java.io.FileOutputStream;

import cn.com.hgh.utils.ContentUtils;
import cn.com.hgh.utils.ScreenUtils;

public class ErWeMaDialog extends Dialog implements  View.OnClickListener,
		View.OnLongClickListener {

	private String message = "";
	private LinearLayout dialog_ll;
	private Bitmap bitmap;
	private ImageView mImageView;
	private Context context;
//	private MainActivity mMain;
	public ErWeMaDialog(Context context) {
		this(context, R.style.DialogStyle_1);
//		mMain = (MainActivity) context;
	}

	public ErWeMaDialog(Context context, int theme) {
		super(context, theme);
		setContentView(R.layout.dialog_erweima);
//		mMain = (MainActivity) context;
		this.context=context;
		initialViews();
	}


	private void initialViews() {

		dialog_ll = (LinearLayout) findViewById(R.id.dialog_ll);
//		mImageView = (ImageView) findViewById(R.id.image);
		int screenWidth = ScreenUtils.getScreenWidth(context)*5/6;
		mImageView = new ImageView(context);  //创建imageview
		mImageView.setLayoutParams(new RelativeLayout.LayoutParams(screenWidth,screenWidth));
		mImageView.setScaleType(ImageView.ScaleType.FIT_XY);
		dialog_ll.addView(mImageView);  //添加到布局容器中，显示图片。
	}

	public void setBitmap(Bitmap bitmap) {
		this.bitmap = bitmap;
		mImageView.setImageBitmap(bitmap);
		dialog_ll.setOnClickListener(this);
		mImageView.setOnClickListener(this);
		mImageView.setOnLongClickListener(this);
	}

	public void setColor() {
	}

	public String getMessage() {
		return message;
	}

	@Override
	public void onClick(View v) {
//		mMain.getCount();
		dismiss();
	}
	@Override
	public boolean onLongClick(View v) {
		savePicture();
		return false;
	}
	public void  savePicture(){
		try {

			if (null != bitmap) {
				if (saveImageToGallery(context, bitmap)) {
					DialogCommon dialogCommon = new DialogCommon(
							context) {

						@Override
						public void onCheckClick() {
							dismiss();
						}

						@Override
						public void onOkClick() {
							Intent intent = new Intent();
							intent.setType("image/*");
							intent.setAction(Intent.ACTION_GET_CONTENT);
							context.startActivity(intent);
							dismiss();
						}

					};
					dialogCommon.setTextTitle("保存图片成功");
					dialogCommon.setTv_dialog_common_ok("立即查看");
					dialogCommon.setTv_dialog_common_cancel("稍后再看");
					dialogCommon.show();
				}else{
					ContentUtils.showMsg(context, "保存图片失败");
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
