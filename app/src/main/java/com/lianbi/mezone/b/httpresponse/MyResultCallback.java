package com.lianbi.mezone.b.httpresponse;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.request.BaseRequest;

import cn.com.hgh.utils.ContentUtils;
import cn.com.hgh.utils.LogUtils;
import cn.com.hgh.utils.Result;
import cn.com.hgh.view.HttpDialog;
import okhttp3.Call;
import okhttp3.Response;

public abstract class MyResultCallback<T> extends StringCallback {
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
	public void onSuccess(String s, Call call, Response response) {
		LogUtils.i("服务器返回数据----->",s);
		if (!TextUtils.isEmpty(s)) {
			try {
				Result srb = JSON.parseObject(s, Result.class);
				if (srb != null) {
					if (srb.getCode() == API.SUCCESS_EXIST) {
						onResponseResult(srb);
					} else {
						onResponseFailed("RESULTERROR");
						ContentUtils.showMsg(context, srb.getMsg());
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}



	@Override
	public void onError(Call call, Response response, Exception e) {
		super.onError(call, response, e);
		onResponseFailed("RESULTERROR");
		ContentUtils.showMsg(context, "连接超时，请稍后再试");
	}

	@Override
	public void onAfter(@Nullable String s, @Nullable Exception e) {
		super.onAfter(s, e);
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
	 * 
	 * @param result
	 *            返回结果
	 */
	public abstract void onResponseResult(Result result);

	/**
	 * 请求失败回调
	 */
	public abstract void onResponseFailed(String msg);
}