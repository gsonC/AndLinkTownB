package com.lianbi.mezone.b.bean;

import android.content.Context;

import com.lianbi.mezone.b.app.Constants;

import java.io.Serializable;

import cn.com.hgh.utils.ContentUtils;

public class UserShopInfoBean implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -161511075479344022L;
	private Context context;
	/**
	 * 当前商铺id
	 */
	String businessId = " ";
	/**
	 * 用户id
	 */
	String userId = " ";
	/**
	 * 当前店铺的联系人电话
	 */
	String phone = " ";
	/**
	 * 當前店鋪的地址
	 */
	String address = " ";
	/**
	 * 用户昵称
	 */
	String name = " ";
	/**
	 * 当前店铺名称
	 */
	String shopName = " ";
	/**
	 * 店铺标志
	 */
	String LoGoUrl = "";
	/**
	 * 店铺所在省份编号
	 */
	String shopRovinceid = "";

	/**
	 * 店铺所在城市
	 */
	String cityCode="";
	/**
	 * 店铺所在商圈区域
	 */
	String areaCode="";
	/**
	 * 当前店铺负责人名称
	 */
	String nikeName = " ";
	/**
	 * 个人头像地址
	 */
	String personHeadUrl = " ";
	String industry_id = "";
	public  UserShopInfoBean(Context  context){
	    this.context=context;
	}
    public void  getSharePreString(){
		userId= ContentUtils.getSharePreString(context, Constants.USERTAG, Constants.USERID);
		name=ContentUtils.getSharePreString(context, Constants.USERTAG, Constants.USER_NAME);
		personHeadUrl=ContentUtils.getSharePreString(context, Constants.USERTAG, Constants.USERHEADURL);
		businessId=ContentUtils.getSharePreString(context, Constants.USERTAG, Constants.USERBUSINESSID);
		shopName=ContentUtils.getSharePreString(context, Constants.USERTAG, Constants.USERSHOPNAME);
		LoGoUrl=ContentUtils.getSharePreString(context, Constants.USERTAG, Constants.USERSHOPLOGOURL);
		shopRovinceid=ContentUtils.getSharePreString(context, Constants.USERTAG, Constants.USERSHOPPROVINCEID);
		phone=ContentUtils.getSharePreString(context, Constants.USERTAG, Constants.BUSINESSPHONE);
		cityCode=ContentUtils.getSharePreString(context, Constants.USERTAG, Constants.USERSHOPLEAGUESCITY);
		areaCode=ContentUtils.getSharePreString(context, Constants.USERTAG, Constants.USERSHOPLEAGUESAREA);
		address=ContentUtils.getSharePreString(context, Constants.USERTAG, Constants.USERSHOPADDRESS);

	}
	public String getIndustry_id() {
		return industry_id;
	}

	public void setIndustry_id(String industry_id) {
		this.industry_id = industry_id;
	}

	public String getPersonHeadUrl() {
		return personHeadUrl.trim();
	}

	public void setPersonHeadUrl(String personHeadUrl) {
		this.personHeadUrl = personHeadUrl;
	}

	public String getNikeName() {
		return nikeName.trim();
	}

	public void setNikeName(String nikeName) {
		this.nikeName = nikeName;
	}

	public String getShopName() {
		return shopName.trim();
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public String getPhone() {
		return phone.trim();
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address.trim();
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getName() {
		return name.trim();
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUserId() {
		return userId.trim();
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getBusinessId() {
		return businessId.trim();
	}

	public void setBusinessId(String businessId) {
		this.businessId = businessId;
	}

	public String getLoGoUrl() {
		return LoGoUrl;
	}

	public void setLoGoUrl(String loGoUrl) {
		LoGoUrl = loGoUrl;
	}

	public String getShopRovinceid() {
		return shopRovinceid;
	}

	public void setShopRovinceid(String shopRovinceid) {
		this.shopRovinceid = shopRovinceid;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}
}
