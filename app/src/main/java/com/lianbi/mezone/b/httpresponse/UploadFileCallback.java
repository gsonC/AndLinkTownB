package com.lianbi.mezone.b.httpresponse;

import android.content.Context;
import android.text.TextUtils;

import com.lzy.okgo.callback.AbsCallback;
import com.lzy.okgo.request.BaseRequest;

import cn.com.hgh.utils.ContentUtils;
import cn.com.hgh.view.HttpDialog;
import okhttp3.Call;
import okhttp3.Response;

/*
 * @创建者     master
 * @创建时间   2016/10/19 10:01
 * @描述       文件上传
 *
 * @更新者     $Author$ 
 * @更新时间   $Date$
 * @更新描述   文件上传
 */
public abstract class UploadFileCallback<T> extends AbsCallback<T> {

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
	public void onSuccess(T t, Call call, Response response) {

	}

	@Override
	public void onError(Call call, Response response, Exception e) {
		super.onError(call, response, e);
		onResponseFailed("RESULTERROR");
		ContentUtils.showMsg(context, "连接超时，请稍后再试");
	}

	@Override
	public void upProgress(long currentSize, long totalSize, float progress, long networkSpeed) {
		super.upProgress(currentSize, totalSize, progress, networkSpeed);
		onUploadProgress(currentSize,totalSize,progress,networkSpeed);
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
	 * 请求失败回调
	 */
	public abstract void onResponseFailed(String msg);

	/**
	 * 上传进度回掉
	 */
	public abstract void onUploadProgress(long currentSize, long totalSize, float progress, long networkSpeed);
}
