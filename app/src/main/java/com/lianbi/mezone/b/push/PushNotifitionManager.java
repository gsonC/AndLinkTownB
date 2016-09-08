package com.lianbi.mezone.b.push;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.NotificationCompat;

import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SynthesizerListener;
import com.lianbi.mezone.b.app.Constants;
import com.lianbi.mezone.b.bean.PushDataBean;
import com.lianbi.mezone.b.ui.InfoDetailsActivity;
import com.lianbi.mezone.b.ui.WebActivty;
import com.xizhi.mezone.b.R;

public class PushNotifitionManager {

	public Context mContext;
	/**
	 * Notification的ID
	 */
	int notifyId = 100;

	public  PushDataBean               mDatas;
	private NotificationCompat.Builder mBuilder;
	private Intent resultIntent;
	private SpeechSynthesizer mTts;

	public PushNotifitionManager(Context context, PushDataBean data) {
		this.mContext = context;
		this.mDatas = data;
	}

	public void showNotify() {
		NotificationManager mNotificationManager = (NotificationManager) mContext
				.getSystemService(Context.NOTIFICATION_SERVICE);

		initVoice();
		initNotify();

		// Notification mNotification = mBuilder.build();
		// // 设置显示通知时的默认的发声、震动、Light效果
		// mNotification.defaults = Notification.DEFAULT_VIBRATE;
		// // 在通知栏上点击此通知后自动清除此通知
		// mNotification.flags = Notification.FLAG_ONGOING_EVENT;//
		// FLAG_ONGOING_EVENT
		// 在顶部常驻，可以调用下面的清除方法去除
		// FLAG_AUTO_CANCEL
		// 点击和清理可以去调
		//mBuilder.setSmallIcon(R.mipmap.ic_launcher)
		//		.setContentTitle(mDatas.getTitle())
		//		.setContentText(mDatas.getContent());
		// 点击的意图ACTION是跳转到Intent
		String pushtarget = "";
		int tiaozhuan = mDatas.getCallType();
		if(1==tiaozhuan) {
			resultIntent = new Intent(mContext, WebActivty.class);
			resultIntent.putExtra(Constants.NEDDLOGIN, false);
			resultIntent.putExtra("Re", true);
			resultIntent.putExtra(WebActivty.U, mDatas.getJumpUrl());
		}else if(2==tiaozhuan){
			pushtarget = "xindingdan";
			resultIntent = new Intent(mContext, InfoDetailsActivity.class);
			resultIntent.putExtra("TIAOZHUANXIAOXI", pushtarget);
		}else if(3==tiaozhuan){
			pushtarget =  "maidan";
			resultIntent = new Intent(mContext, InfoDetailsActivity.class);
			resultIntent.putExtra("TIAOZHUANXIAOXI", pushtarget);
		}else if(4==tiaozhuan){
			pushtarget = "fuwu";
			resultIntent = new Intent(mContext, InfoDetailsActivity.class);
			resultIntent.putExtra("TIAOZHUANXIAOXI", pushtarget);
		}else{
			pushtarget = "xindingdan";
			resultIntent = new Intent(mContext, InfoDetailsActivity.class);
			resultIntent.putExtra("TIAOZHUANXIAOXI", pushtarget);
		}

		resultIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
		PendingIntent pendingIntent = PendingIntent.getActivity(mContext, 0,
				resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);
		mBuilder.setContentIntent(pendingIntent);
		mNotificationManager.notify(notifyId, mBuilder.build());

	}

	/**
	 * mTts = SpeechSynthesizer.createSynthesizer(maActivity, null);
	 //2.合成参数设置，详见《科大讯飞MSC API手册(Android)》SpeechSynthesizer 类
	 mTts.setParameter(SpeechConstant.VOICE_NAME, "xiaoyan");//设置发音人
	 mTts.setParameter(SpeechConstant.SPEED, "50");//设置语速
	 mTts.setParameter(SpeechConstant.VOLUME, "80");//设置音量，范围0~100
	 mTts.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD); //设置云端
	 mTts.startSpeaking("科大讯飞，让世界聆听我们的声音", mSynListener);
	 */

	/**
	 * 初始化科大讯飞语音
	 */
	private void initVoice() {
		mTts = SpeechSynthesizer.createSynthesizer(mContext,null);
		//2.合成参数设置，详见《科大讯飞MSC API手册(Android)》SpeechSynthesizer 类
		mTts.setParameter(SpeechConstant.VOICE_NAME,"xiaoyan");//设置发音人
		mTts.setParameter(SpeechConstant.SPEED, "50");//设置语速
		mTts.setParameter(SpeechConstant.VOLUME, "80");//设置音量，范围0~100
		mTts.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD); //设置云端
		//设置合成音频保存位置（可自定义保存位置），保存在“./sdcard/iflytek.pcm”
		//保存在SD卡需要在AndroidManifest.xml添加写SD卡权限
		//如果不需要保存合成音频，注释该行代码
		//	mTts.setParameter(SpeechConstant.TTS_AUDIO_PATH, "./sdcard/iflytek.pcm");
		//3.开始合成
		//mTts.startSpeaking("语音内容", mSynListener);
	}

	/**
	 * 初始化 Builder
	 */
	private void initNotify() {
	//	long[] vibrates = {0, 1000, 1000, 1000};//此处表示手机先震动1秒，然后静止1秒，然后再震动1秒
		mBuilder = new NotificationCompat.Builder(mContext);
		mBuilder.setSmallIcon(R.mipmap.ic_launcher)
				.setContentTitle(mDatas.getTitle())
				.setContentText(mDatas.getContent())
				.setContentIntent(
						getDefalutIntent(Notification.FLAG_AUTO_CANCEL))
				// .setNumber(number)//显示数量
				.setTicker("")// 通知首次出现在通知栏，带上升动画效果的
				.setWhen(System.currentTimeMillis())// 通知产生的时间，会在通知信息里显示
				.setPriority(Notification.PRIORITY_DEFAULT)// 设置该通知优先级
				.setAutoCancel(true)// 设置这个标志当用户单击面板就可以让通知将自动取消
				.setOngoing(false)// ture，设置他为一个正在进行的通知。他们通常是用来表示一个后台任务,用户积极参与(如播放音乐)或以某种方式正在等待,因此占用设备(如一个文件下载,同步操作,主动网络连接)
				//.setDefaults(Notification.DEFAULT_ALL)// 向通知添加声音、闪灯和振动效果的最简单、最一致的方式是使用当前的用户默认设置，使用defaults属性，可以组合：
				// Notification.DEFAULT_ALL Notification.DEFAULT_SOUND 添加声音 //
				// requires VIBRATE permission
		//		.setVibrate(vibrates)//设置震动
				.setLights(Color.WHITE, 1000, 1000);//设置灯光
		if (2 == mDatas.getCallType()||1 == mDatas.getCallType()) {
			mTts.startSpeaking("老板娘,又有新订单了", mSynListener);
		//	mBuilder.setSound(Uri.parse("android.resource://" + mContext.getPackageName() + "/" + R.raw.neworder));
		} else if (3 == mDatas.getCallType()) {
			mTts.startSpeaking("买单了,老板娘打小票先", mSynListener);
		//	mBuilder.setSound(Uri.parse("android.resource://" + mContext.getPackageName() + "/" + R.raw.checkorder));
		} else if (4 == mDatas.getCallType()) {
		//	mBuilder.setSound(Uri.parse("android.resource://" + mContext.getPackageName() + "/" + R.raw.call));
			mTts.startSpeaking("老板娘,有人呼叫,快去看看吧", mSynListener);
		} else {
			//mBuilder.setDefaults(Notification.DEFAULT_SOUND);
			mBuilder.setSound(MediaStore.Audio.Media.INTERNAL_CONTENT_URI);
		}
	}

	/**
	 * 获取默认的pendingIntent,为了防止2.3及以下版本报错
	 * flags属性: 在顶部常驻:Notification.FLAG_ONGOING_EVENT 点击去除：
	 * Notification.FLAG_AUTO_CANCEL
	 */
	public PendingIntent getDefalutIntent(int flags) {
		PendingIntent pendingIntent = PendingIntent.getActivity(mContext, 1,
				new Intent(), flags);
		return pendingIntent;
	}


	private SynthesizerListener mSynListener = new SynthesizerListener() {
		//恢复播放回调接口
		@Override
		public void onSpeakResumed() {
			// TODO Auto-generated method stub

		}
		//播放进度回调
		//percent为播放进度0~100,beginPos为播放音频在文本中开始位置，endPos表示播放音频在文本中结束位置.
		@Override
		public void onSpeakProgress(int arg0, int arg1, int arg2) {
			// TODO Auto-generated method stub

		}
		//暂停播放
		@Override
		public void onSpeakPaused() {
			// TODO Auto-generated method stub

		}
		//开始播放
		@Override
		public void onSpeakBegin() {
			// TODO Auto-generated method stub

		}
		//会话事件回调接口
		@Override
		public void onEvent(int arg0, int arg1, int arg2, Bundle arg3) {
			// TODO Auto-generated method stub

		}
		//会话结束回调接口，没有错误时，error为null
		@Override
		public void onCompleted(SpeechError arg0) {
			// TODO Auto-generated method stub

		}
		//缓冲进度回调
		//percent为缓冲进度0~100，beginPos为缓冲音频在文本中开始位置，endPos表示缓冲音频在文本中结束位置，info为附加信息。
		@Override
		public void onBufferProgress(int arg0, int arg1, int arg2, String arg3) {
			// TODO Auto-generated method stub

		}
	};


}
