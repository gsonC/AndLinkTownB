package com.lianbi.mezone.b.bean;/*
 * @创建者     Administrator
 * @创建时间   2016/8/11 17:23
 * @描述       会员信息
 *
 * @更新者     $Author$ 
 * @更新时间   $Date$
 * @更新描述   ${TODO}
 */

import java.io.Serializable;

public class  MemberInfoBean implements Serializable {
	private static final long serialVersionUID = -5076976047607374205L;

	/**
	 * vipIdNo : null
	 * vipPhone : 13711336075
	 * vipRemarks : null
	 * vipPhoto : null
	 * vipIntegral : 0
	 * businessId : BD2016072816282300000309
	 * vipAddress : 北京市朝阳区
	 * vipBirthday : null
	 * vipId : VI082016082315240300000310
	 * updateTime : 1471936978000
	 * vipSource : 老板娘app
	 * labels : null
	 * vipCardNo : null
	 * vipRightsInterests : null
	 * vipName : null
	 * createTime : 1471936978000
	 * vipTypeObject : null
	 * cumulativeAmount : 0
	 * vipValidityPeriod : 253402185600000
	 * vipSex : null
	 */

	private String vipIdNo;
	private String vipPhone;
	private String vipRemarks;
	private String vipPhoto;
	private int vipIntegral;
	private String businessId;
	private String vipAddress;
	private String vipBirthday;
	private String vipId;
	private String updateTime;
	private String vipSource;
	private String labels;
	private String vipCardNo;
	private String vipRightsInterests;
	private String vipName;
	private String createTime;
	private String vipTypeObject;
	private String cumulativeAmount;
	private String vipValidityPeriod;
	private int vipSex;
	private String labelName;
	private String vipType;
    private String nickName;

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getVipType() {
		return vipType;
	}

	public void setVipType(String vipType) {
		this.vipType = vipType;
	}

	public String getLabelName() {
		return labelName;
	}

	public void setLabelName(String labelName) {
		this.labelName = labelName;
	}

	public String getVipIdNo() {
		return vipIdNo;
	}

	public void setVipIdNo(String vipIdNo) {
		this.vipIdNo = vipIdNo;
	}

	public String getVipPhone() {
		return vipPhone;
	}

	public void setVipPhone(String vipPhone) {
		this.vipPhone = vipPhone;
	}

	public String getVipRemarks() {
		return vipRemarks;
	}

	public void setVipRemarks(String vipRemarks) {
		this.vipRemarks = vipRemarks;
	}

	public String getVipPhoto() {
		return vipPhoto;
	}

	public void setVipPhoto(String vipPhoto) {
		this.vipPhoto = vipPhoto;
	}

	public int getVipIntegral() {
		return vipIntegral;
	}

	public void setVipIntegral(int vipIntegral) {
		this.vipIntegral = vipIntegral;
	}

	public String getBusinessId() {
		return businessId;
	}

	public void setBusinessId(String businessId) {
		this.businessId = businessId;
	}

	public String getVipAddress() {
		return vipAddress;
	}

	public void setVipAddress(String vipAddress) {
		this.vipAddress = vipAddress;
	}

	public String getVipBirthday() {
		return vipBirthday;
	}

	public void setVipBirthday(String vipBirthday) {
		this.vipBirthday = vipBirthday;
	}

	public String getVipId() {
		return vipId;
	}

	public void setVipId(String vipId) {
		this.vipId = vipId;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public String getVipSource() {
		return vipSource;
	}

	public void setVipSource(String vipSource) {
		this.vipSource = vipSource;
	}

	public String getLabels() {
		return labels;
	}

	public void setLabels(String labels) {
		this.labels = labels;
	}

	public String getVipCardNo() {
		return vipCardNo;
	}

	public void setVipCardNo(String vipCardNo) {
		this.vipCardNo = vipCardNo;
	}

	public String getVipRightsInterests() {
		return vipRightsInterests;
	}

	public void setVipRightsInterests(String vipRightsInterests) {
		this.vipRightsInterests = vipRightsInterests;
	}

	public String getVipName() {
		return vipName;
	}

	public void setVipName(String vipName) {
		this.vipName = vipName;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getVipTypeObject() {
		return vipTypeObject;
	}

	public void setVipTypeObject(String vipTypeObject) {
		this.vipTypeObject = vipTypeObject;
	}

	public String getCumulativeAmount() {
		return cumulativeAmount;
	}

	public void setCumulativeAmount(String cumulativeAmount) {
		this.cumulativeAmount = cumulativeAmount;
	}

	public String getVipValidityPeriod() {
		return vipValidityPeriod;
	}

	public void setVipValidityPeriod(String vipValidityPeriod) {
		this.vipValidityPeriod = vipValidityPeriod;
	}

	public int getVipSex() {
		return vipSex;
	}

	public void setVipSex(int vipSex) {
		this.vipSex = vipSex;
	}
}
