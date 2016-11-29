package com.xizhi.mezone.b.wxapi;

import android.content.Intent;
import android.os.Bundle;

import com.lianbi.mezone.b.httpresponse.API;
import com.lianbi.mezone.b.ui.BaseActivity;
import com.lianbi.mezone.b.ui.MainActivity;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import cn.com.hgh.utils.ContentUtils;

/*
 * @创建者     master
 * @创建时间   2016/11/25 9:53
 * @描述       微信分享回掉
 *
 * @更新者     $Author$ 
 * @更新时间   $Date$
 * @更新描述
 */
public class WXEntryActivity extends BaseActivity implements IWXAPIEventHandler {

	private IWXAPI api;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		api = WXAPIFactory.createWXAPI(this, API.APP_ID, false);
		api.registerApp(API.APP_ID);
		api.handleIntent(getIntent(),this);
	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		setIntent(intent);
		api.handleIntent(intent, this);
	}

	@Override
	public void onReq(BaseReq baseReq) {
		try {
			Intent intent = new Intent(this, MainActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(intent);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onResp(BaseResp baseResp) {
		switch (baseResp.errCode){
			case BaseResp.ErrCode.ERR_OK:
				ContentUtils.showMsg(this,"分享成功");
				break;
			case BaseResp.ErrCode.ERR_USER_CANCEL:
				ContentUtils.showMsg(this,"分享取消");
				break;
			case BaseResp.ErrCode.ERR_AUTH_DENIED:
				ContentUtils.showMsg(this,"分享被拒绝");
				break;
			default:
				ContentUtils.showMsg(this,"未知原因,分享失败");
				break;
		}
		this.finish();
	}


}
