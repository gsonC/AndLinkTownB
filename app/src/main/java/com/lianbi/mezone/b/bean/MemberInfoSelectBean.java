package com.lianbi.mezone.b.bean;/*
 * @创建者     Administrator
 * @创建时间   2016/8/11 17:23
 * @描述       选择要发送的会员
 *
 * @更新者     $Author$ 
 * @更新时间   $Date$
 * @更新描述   ${TODO}
 */

import java.io.Serializable;

public class MemberInfoSelectBean implements Serializable {
	private static final long serialVersionUID = -5076976047607374205L;
	private String vipId;
	private String businessId;
	private String vipPhone;
	private String vipName;
	private String vipType;
	private String vipSex;
	private String vipIdNo;
	private String vipCardNo;
	private String vipAddress;
	private String vipRightsInterests;
	private String vipBirthday;
	private String vipValidityPeriod;
	private int cumulativeAmount;
	private int vipIntegral;
	private String vipRemarks;
	private String updateTime;
	private String createTime;
	private int vipWeekCount;
	private int vipCount;
	private String labelName;
	private String vipSource;
	private boolean isChecked;


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

	public String getVipPhone() {
		return vipPhone;
	}

	public void setVipPhone(String vipPhone) {
		this.vipPhone = vipPhone;
	}

	public String getVipName() {
		return vipName;
	}

	public void setVipName(String vipName) {
		this.vipName = vipName;
	}

	public String getVipType() {
		return vipType;
	}

	public void setVipType(String vipType) {
		this.vipType = vipType;
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

	public String getVipValidityPeriod() {
		return vipValidityPeriod;
	}

	public void setVipValidityPeriod(String vipValidityPeriod) {
		this.vipValidityPeriod = vipValidityPeriod;
	}

	public int getCumulativeAmount() {
		return cumulativeAmount;
	}

	public void setCumulativeAmount(int cumulativeAmount) {
		this.cumulativeAmount = cumulativeAmount;
	}

	public int getVipIntegral() {
		return vipIntegral;
	}

	public void setVipIntegral(int vipIntegral) {
		this.vipIntegral = vipIntegral;
	}

	public String getVipRemarks() {
		return vipRemarks;
	}

	public void setVipRemarks(String vipRemarks) {
		this.vipRemarks = vipRemarks;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public int getVipWeekCount() {
		return vipWeekCount;
	}

	public void setVipWeekCount(int vipWeekCount) {
		this.vipWeekCount = vipWeekCount;
	}

	public int getVipCount() {
		return vipCount;
	}

	public void setVipCount(int vipCount) {
		this.vipCount = vipCount;
	}

	public String getLabelName() {
		return labelName;
	}

	public void setLabelName(String labelName) {
		this.labelName = labelName;
	}

	public String getVipSource() {
		return vipSource;
	}

	public void setVipSource(String vipSource) {
		this.vipSource = vipSource;
	}

	public boolean isChecked() {
		return isChecked;
	}

	public void setChecked(boolean checked) {
		isChecked = checked;
	}

}
