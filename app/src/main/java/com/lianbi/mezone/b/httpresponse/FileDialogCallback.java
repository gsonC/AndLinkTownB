package com.lianbi.mezone.b.httpresponse;

import android.content.Context;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.lzy.okgo.callback.AbsCallback;
import com.lzy.okgo.convert.FileConvert;
import com.lzy.okgo.request.BaseRequest;

import java.io.File;

import cn.com.hgh.utils.ContentUtils;
import cn.com.hgh.view.HttpDialog;
import okhttp3.Call;
import okhttp3.Response;

/*
 * @创建者     master
 * @创建时间   2016/10/19 9:40
 * @描述       文件下载 构建方法可定义文件名称 文件下载位置
 *
 * @更新者     $Author$ 
 * @更新时间   $Date$
 * @更新描述
 */
public abstract class FileDialogCallback extends AbsCallback<File>{

	private FileConvert convert;    //文件转换类

	/**
	 * 请求的dialog
	 */
	private HttpDialog progressDialog;
	/**
	 * 请求的上下文
	 */
	public Context context;

	public FileDialogCallback() {
		this(null);
	}

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
	public void onAfter(@Nullable File file, @Nullable Exception e) {
		super.onAfter(file, e);
		try {
			if (progressDialog != null) {
				progressDialog.dismiss();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public void onSuccess(File file, Call call, Response response) {
		if(file.exists()){
			onResponseResult(file);
		}else{
			onResponseFailed("下载出错");
		}
	}

	/**
	 *
	 * @param currentSize	以下载大小
	 * @param totalSize		总大小
	 * @param progress		下载进度 1为100%
	 * @param networkSpeed	下载速度以B为单位
	 */
	@Override
	public void downloadProgress(long currentSize, long totalSize, float progress, long networkSpeed) {
		super.downloadProgress(currentSize, totalSize, progress, networkSpeed);
		fileDownloadProgress(currentSize,totalSize,progress,networkSpeed);
	}

	@Override
	public void onError(Call call, Response response, Exception e) {
		super.onError(call, response, e);
		onResponseFailed("FILEDOWNLOADFAILED");
		ContentUtils.showMsg(context, "连接超时，请稍后再试");
	}

	public FileDialogCallback(String destFileName) {
		this(Environment.getExternalStorageDirectory() + FileConvert.DM_TARGET_FOLDER, destFileName);
	}

	public FileDialogCallback(String destFileDir, String destFileName) {
		convert = new FileConvert(destFileDir, destFileName);
		convert.setCallback(this);
	}

	@Override
	public File convertSuccess(Response response) throws Exception {
		File file = convert.convertSuccess(response);
		response.close();
		return file;
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
	public abstract void onResponseResult(File result);

	/**
	 * 请求失败回调
	 */
	public abstract void onResponseFailed(String msg);

	/**
	 * 下载进度回掉
	 */
	public abstract void fileDownloadProgress(long currentSize, long totalSize, float progress, long networkSpeed);
}
