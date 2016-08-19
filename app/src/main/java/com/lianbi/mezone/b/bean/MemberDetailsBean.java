package com.lianbi.mezone.b.bean;/*
 * @创建者     Administrator
 * @创建时间   2016/8/19 11:27
 * @描述       会员详情
 *
 * @更新者     $Author$ 
 * @更新时间   $Date$
 * @更新描述   ${TODO}
 */

import java.io.Serializable;

public class MemberDetailsBean implements Serializable {
	private static final long serialVersionUID = -3682072123166665401L;
	private String vipId;
	private String businessId;
	private String vipType;
	private int typeDiscountRatio;
	private int typeMaxDiscount;
	private int vipPhone;
	private String vipName;
	private String vipSex;
	private String vipIdNo;
	private String vipCardNo;
	private String vipAddress;
	private String vipRightsInterests;
	private String vipBirthday;
	private int vipIntegral;
	private int cumulativeAmount;
	private String vipValidityPeriod;
	private String vipRemarks;
	private lablelList lablelLists;

	public lablelList getLablelLists() {
		return lablelLists;
	}

	public void setLablelLists(lablelList lablelLists) {
		this.lablelLists = lablelLists;
	}

	public class lablelList{
		private String businessId;
		private String labelId;
		private String labelName;

		public String getBusinessId() {
			return businessId;
		}

		public void setBusinessId(String businessId) {
			this.businessId = businessId;
		}

		public String getLabelId() {
			return labelId;
		}

		public void setLabelId(String labelId) {
			this.labelId = labelId;
		}

		public String getLabelName() {
			return labelName;
		}

		public void setLabelName(String labelName) {
			this.labelName = labelName;
		}
	}

	public String getVipId() {
		return vipId;
	}

	public void setVipId(String vipId) {
		this.vipId = vipId;
	}

	public String getBusinessId() {
		return businessId;
	}

	public void setBusinessId(String businessId) {
		this.businessId = businessId;
	}

	public String getVipType() {
		return vipType;
	}

	public void setVipType(String vipType) {
		this.vipType = vipType;
	}

	public int getTypeDiscountRatio() {
		return typeDiscountRatio;
	}

	public void setTypeDiscountRatio(int typeDiscountRatio) {
		this.typeDiscountRatio = typeDiscountRatio;
	}

	public int getTypeMaxDiscount() {
		return typeMaxDiscount;
	}

	public void setTypeMaxDiscount(int typeMaxDiscount) {
		this.typeMaxDiscount = typeMaxDiscount;
	}

	public int getVipPhone() {
		return vipPhone;
	}

	public void setVipPhone(int vipPhone) {
		this.vipPhone = vipPhone;
	}

	public String getVipName() {
		return vipName;
	}

	public void setVipName(String vipName) {
		this.vipName = vipName;
	}

	public String getVipSex() {
		return vipSex;
	}

	public void setVipSex(String vipSex) {
		this.vipSex = vipSex;
	}

	public String getVipIdNo() {
		return vipIdNo;
	}

	public void setVipIdNo(String vipIdNo) {
		this.vipIdNo = vipIdNo;
	}

	public String getVipCardNo() {
		return vipCardNo;
	}

	public void setVipCardNo(String vipCardNo) {
		this.vipCardNo = vipCardNo;
	}

	public String getVipAddress() {
		return vipAddress;
	}

	public void setVipAddress(String vipAddress) {
		this.vipAddress = vipAddress;
	}

	public String getVipRightsInterests() {
		return vipRightsInterests;
	}

	public void setVipRightsInterests(String vipRightsInterests) {
		this.vipRightsInterests = vipRightsInterests;
	}

	public String getVipBirthday() {
		return vipBirthday;
	}

	public void setVipBirthday(String vipBirthday) {
		this.vipBirthday = vipBirthday;
	}

	public int getVipIntegral() {
		return vipIntegral;
	}

	public void setVipIntegral(int vipIntegral) {
		this.vipIntegral = vipIntegral;
	}

	public int getCumulativeAmount() {
		return cumulativeAmount;
	}

	public void setCumulativeAmount(int cumulativeAmount) {
		this.cumulativeAmount = cumulativeAmount;
	}

	public String getVipValidityPeriod() {
		return vipValidityPeriod;
	}

	public void setVipValidityPeriod(String vipValidityPeriod) {
		this.vipValidityPeriod = vipValidityPeriod;
	}

	public String getVipRemarks() {
		return vipRemarks;
	}

	public void setVipRemarks(String vipRemarks) {
		this.vipRemarks = vipRemarks;
	}
}
