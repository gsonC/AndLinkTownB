package cn.com.hgh.utils;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;

import com.lianbi.mezone.b.app.Constants;
import com.lianbi.mezone.b.bean.WebProductManagementBean;
import com.lianbi.mezone.b.ui.AddShopActivity;
import com.lianbi.mezone.b.ui.BaseActivity;
import com.lianbi.mezone.b.ui.H5WebActivty;
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
	 * @param type
	 *            类型
	 * @param at
	 *            class 跳转的类
	 */
	public static boolean jumpH5WebActivty(boolean isLogin, String type,String  title,
											 Activity at) {
		if (!isLogin) {
			return isLogin;
		} else {
			Intent intent_web = new Intent(at,
					H5WebActivty.class);
			intent_web.putExtra(Constants.NEDDLOGIN, false);
			intent_web.putExtra("NEEDNOTTITLE", false);
			intent_web.putExtra("Re", true);
			intent_web.putExtra(WebActivty.T, title);
			intent_web.putExtra(WebActivty.U, type);
			at.startActivity(intent_web);

		}
		return false;
	}


	public static String getUrl(String address) {
		String bussniessId = BaseActivity.userShopInfoBean.getBusinessId();
		String url = address;
		WebProductManagementBean data = new WebProductManagementBean();
		data.setBusinessId(bussniessId);
		String dataJson = com.alibaba.fastjson.JSONObject.toJSON(data)
				.toString();
		url = encryptionUrl(url, dataJson);
		return url;
	}
	public String getSupplyWholesaleUrl(String address) {
		String bussniessId = BaseActivity.userShopInfoBean.getBusinessId();
		return address + "storeId=" + bussniessId;
	}

	/**
	 * 加密
	 */
	private static String encryptionUrl(String url, String dataJson) {
		try {
			// 获得的明文数据
			String desStr = dataJson;
			// 转成字节数组
			byte src_byte[] = desStr.getBytes();

			// MD5摘要
			byte[] md5Str = WebEncryptionUtil.md5Digest(src_byte);
			// 生成最后的SIGN
			String SING = WebEncryptionUtil.byteArrayToHexString(md5Str);

			desStr = CryptTool.getBASE64(dataJson);
			// http://localhost:8080/order/orderContler/?sing=key&data=密文
			return url + "sing=" + SING + "&&data=" + desStr + "&&auth=wcm";
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return "";
	}
}
