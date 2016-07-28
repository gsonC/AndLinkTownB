package cn.com.hgh.utils;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;

import com.lianbi.mezone.b.app.Constants;
import com.lianbi.mezone.b.bean.WebProductManagementBean;
import com.lianbi.mezone.b.ui.AddShopActivity;
import com.lianbi.mezone.b.ui.BaseActivity;
import com.lianbi.mezone.b.ui.MainActivity;
import com.lianbi.mezone.b.ui.WebActivty;

public class JumpIntent {
	/**
	 * 跳转介绍2次
	 *
	 * @param isLogin
	 *            是否登录
	 * @param type
	 *            类型
	 * @param at
	 *            class 跳转的类
	 * @param at
	 */
	//微信商城
	public static final int  WECHATMALL =2;
	//货源批发
	public static final int  SUPPLYWHOLESALE =3;
	//智能wifi
	public static final int   INTELLIGENTWIFI=5;
	public static boolean jumpLogin_addShop(boolean isLogin, String type,
			Activity at) {
		if (isLogin) {
			if (TextUtils
					.isEmpty(BaseActivity.userShopInfoBean.getBusinessId())) {
//				ChangeShopActivity.myShopChange = JiaoYiGuanLiFragment.jiaoYiGuanLiFragment;
				Intent intent_more = new Intent(at, AddShopActivity.class);
				at.startActivity(intent_more);
			} else {
				return isLogin;
			}
		} else {
			Intent intent_web = new Intent(at, WebActivty.class);
			intent_web.putExtra(Constants.NEDDLOGIN, true);
			intent_web.putExtra(WebActivty.T, "功能介绍");
			intent_web.putExtra(WebActivty.U, type);
			at.startActivityForResult(intent_web, MainActivity.REQUEST_CHANKAN);
		}
		return false;
	}

	/**
	 * 跳转介绍1次
	 * 
	 * @param isLogin
	 *            是否登录
	 * @param type
	 *            类型
	 * @param at
	 *            class 跳转的类
	 * @param at
	 */
	public static boolean jumpLogin_addShop1(boolean isLogin, String type,
			Activity at) {
		if (isLogin) {
			return isLogin;
		} else {
			Intent intent_web = new Intent(at, WebActivty.class);
			intent_web.putExtra(Constants.NEDDLOGIN, true);
			intent_web.putExtra(WebActivty.T, "功能介绍");
			intent_web.putExtra(WebActivty.U, type);
			at.startActivityForResult(intent_web, MainActivity.REQUEST_CHANKAN);
		}
		return false;
	}

	/**
	 *
	 * 服务商城菜单向H5页面跳转
	 * @param isLogin
	 *            是否登录
	 * @param
	 *
	 * @param oldactivity
	 *            class 跳转的类
	 *             allWebActivity(Class activity,
	Boolean neddlogin,
	Boolean neednottitle,
	Boolean re,
	String t,
	String u
	 */
	public  static  boolean jumpWebActivty
	       (Activity oldactivity,Class newactivity,
			boolean isLogin,
			String   urladdress,int type,
			boolean neddlogin,
			boolean neednottitle,
			boolean re,
			String  title
			) {
		if (!isLogin) {
			return isLogin;
		} else {
			String weburl=getSAUrl(urladdress,type);
			Intent intent_web = new Intent(oldactivity,
					newactivity);
			intent_web.putExtra(Constants.NEDDLOGIN, neddlogin);
			intent_web.putExtra(Constants.NEEDNOTTITLE, neednottitle);
			intent_web.putExtra(Constants.RE, re);
			intent_web.putExtra(Constants.TITLE, title);
			intent_web.putExtra(Constants.WEBURL,weburl);
			oldactivity.startActivity(intent_web);
		}
		return false;
	}
	public static  String getSAUrl(String urladdress,int type){
		String bussniessId = BaseActivity.userShopInfoBean.getBusinessId();
		switch (type){
			case WECHATMALL://微信商城
				WebProductManagementBean data = new WebProductManagementBean();
				data.setBusinessId(bussniessId);
				String dataJson = com.alibaba.fastjson.JSONObject.toJSON(data)
						.toString();
				String url = CryptTool.encryptionUrl(urladdress, dataJson);
				return url;
			case SUPPLYWHOLESALE://货源批发
				return urladdress + "storeId=" + bussniessId;
			case INTELLIGENTWIFI://智能WIFI
				return urladdress+bussniessId;
		}
		return "";
	}

}
