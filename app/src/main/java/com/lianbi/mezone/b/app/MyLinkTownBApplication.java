package com.lianbi.mezone.b.app;

import android.app.Application;

import com.zhy.http.okhttp.OkHttpUtils;

import java.util.concurrent.TimeUnit;

import cn.com.hgh.utils.DataCleanManager;
import cn.com.hgh.utils.FilePathGet;
import okhttp3.OkHttpClient;

public class MyLinkTownBApplication extends Application {
	@Override
	public void onCreate() {
		super.onCreate();


		// 初始化网络请求
		// 这里可以设置自签名证书
		// OkHttpUtils.getInstance().setCertificates(new InputStream[]{
		// new Buffer()
		// .writeUtf8(CER_12306)
		// .inputStream()});

		//OkHttpUtils.getInstance().debug("OkHttpUtils")
		//		.setConnectTimeout(CONNECTTIMEOUT, TimeUnit.MILLISECONDS);
		// 使用https，但是默认信任全部证书
		//OkHttpUtils.getInstance().setCertificates();

		// 使用这种方式，设置多个OkHttpClient参数
		// OkHttpUtils.getInstance(new OkHttpClient.Builder().build());

		OkHttpClient okHttpClient = new OkHttpClient.Builder()
				//                .addInterceptor(new LoggerInterceptor("TAG"))
				.connectTimeout(10000L, TimeUnit.MILLISECONDS)
				.readTimeout(10000L, TimeUnit.MILLISECONDS)
				.retryOnConnectionFailure(false)
				//其他配置
				.build();

		OkHttpUtils.getInstance(okHttpClient);

	//	OkHttpUtils.getInstance().getOkHttpClient().newBuilder().retryOnConnectionFailure(false);

		// 初始化异常捕获1
		CrashHand handler = CrashHand.getInstance();
		Thread.setDefaultUncaughtExceptionHandler(handler);
		// 初始化异常捕获2

		CrashHandler crashHandler = CrashHandler.getInstance();
		crashHandler.init(getApplicationContext());
		/**
		 * 清除缓存
		 */
		DataCleanManager.cleanExternalCache(getApplicationContext());
		DataCleanManager.cleanCustomCache(FilePathGet
				.createSDCardDir("cacheImages"));

	}


}
