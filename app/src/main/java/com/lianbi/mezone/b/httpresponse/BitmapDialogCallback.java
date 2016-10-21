package com.lianbi.mezone.b.httpresponse;

/*
 * @创建者     master
 * @创建时间   2016/10/18 17:28
 * @描述       向服务器请求bitmap
 *
 * @更新者     $Author$ 
 * @更新时间   $Date$
 * @更新描述
 */


import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.lianbi.mezone.b.photo.AbImageUtil;
import com.lzy.okgo.callback.BitmapCallback;
import com.lzy.okgo.request.BaseRequest;

import cn.com.hgh.utils.ContentUtils;
import cn.com.hgh.utils.LogUtils;
import cn.com.hgh.view.HttpDialog;
import okhttp3.Call;
import okhttp3.Response;

public abstract class BitmapDialogCallback extends BitmapCallback {

	/**
	 * 请求的dialog
	 */
	private HttpDialog progressDialog;
	/**
	 * 请求的上下文
	 */
	public Context context;

	@Override
	public void onBefore(BaseRequest request) {
		super.onBefore(request);
		showDialog();
	}

	public void showDialog() {
		try {
			if (progressDialog != null) {
				progressDialog.show();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onSuccess(Bitmap bitmap, Call call, Response response) {
		LogUtils.i("服务器返回Bitmap大小----->",AbImageUtil.getByteCount(bitmap,
				Bitmap.CompressFormat.PNG)+"");

		if (null != bitmap && AbImageUtil.getByteCount(bitmap, Bitmap.CompressFormat.PNG) > 0) {
			onResponseSuccess(bitmap);
		} else {
			onResponseFailed("RESULTERROR");
			ContentUtils.showMsg(context, "图片获取失败");
		}
	}

	@Override
	public void onError(Call call, Response response, Exception e) {
		super.onError(call, response, e);
		onResponseFailed("RESULTERROR");
		ContentUtils.showMsg(context, "连接超时，请稍后再试");
	}

	@Override
	public void onAfter(@Nullable Bitmap bitmap, @Nullable Exception e) {
		super.onAfter(bitmap, e);
		try {
			if (progressDialog != null) {
				progressDialog.dismiss();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * 设置progressDialog
	 *
	 * @param pg
	 */
	public void setDialog(String pg) {
		progressDialog = new HttpDialog(context);
		if (!TextUtils.isEmpty(pg)) {
			progressDialog.setMessage(pg);
		}
	}

	/**
	 * 设置上下文
	 *
	 * @param ct
	 */
	public void setContext(Context ct) {
		if (ct != null) {
			context = ct;
		}
	}

	/**
	 * 请求成功回调
	 */
	public abstract void onResponseSuccess(Bitmap bitmap);

	/**
	 * 请求失败回调
	 */
	public abstract void onResponseFailed(String msg);

}
