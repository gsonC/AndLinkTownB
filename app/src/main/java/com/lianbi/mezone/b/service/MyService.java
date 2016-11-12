package com.lianbi.mezone.b.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.lianbi.mezone.b.app.Constants;
import com.lianbi.mezone.b.httpresponse.FileDialogCallback;
import com.lianbi.mezone.b.httpresponse.OkHttpsImp;

import java.io.File;

import cn.com.hgh.utils.AbDateUtil;
import cn.com.hgh.utils.AbStrUtil;
import cn.com.hgh.utils.ContentUtils;

/*
 * @创建者     master
 * @创建时间   2016/11/10 13:42
 * @描述       下载省市区json文件
 *
 * @更新者     $Author$ 
 * @更新时间   $Date$
 * @更新描述
 */
public class MyService extends Service {

	/**
	 * 文件下载进度
	 */
	public static final int MAX_PRIGRESS = 100;

	private Intent mIntent = new Intent("com.lianbi.mezone.b.service.RECEIVER");

	private OkHttpsImp mOkHttpsImp = OkHttpsImp.SINGLEOKHTTPSIMP.newInstance(this);

	private void startDownLoad() {
		String reqTime = AbDateUtil.getDateTimeNow();
		String uuid = AbStrUtil.getUUID();
		try {
			mOkHttpsImp.getProvinceCode(uuid, reqTime, new FileDialogCallback("json.json") {
				@Override
				public void onResponseResult(File result) {

				}

				@Override
				public void onResponseFailed(String msg) {

				}

				@Override
				public void fileDownloadProgress(long currentSize, long totalSize, float progress, long networkSpeed) {
					if (1 == progress) {
						ContentUtils.putSharePre(MyService.this,
								Constants.SHARED_PREFERENCE_NAME,
								Constants.AREA_CODE, true);
						System.out.println("下载完成");
					} else {
						ContentUtils.putSharePre(MyService.this,
								Constants.SHARED_PREFERENCE_NAME,
								Constants.AREA_CODE, false);
					}

					//mIntent.putExtra("progress", progress);
					//sendBroadcast(mIntent);
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Nullable
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}


	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {

		startDownLoad();

		return super.onStartCommand(intent, flags, startId);


	}


}
