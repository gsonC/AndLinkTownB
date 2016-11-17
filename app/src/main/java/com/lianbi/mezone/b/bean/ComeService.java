package com.lianbi.mezone.b.bean;

import java.io.Serializable;

/**
 * @创建者 Administration
 * @创建时间 ＄{DATE} ＄{TIME}
 * @描述 ＄{TOOD}
 * @更新者 ＄auther＄
 * @更新时间 ＄Date＄
 * @更新描述 ＄{TOOD}＄
 */
public class ComeService implements Serializable {
	private static final long serialVersionUID = -1704216977722159221L;
    String benefitMoney;
	String creatTime;
	String desc;
	String id;
	String isCouponChangeState;
	String isDel;
	String isScoreAlreadyPaid;
	String modifyTime;
	String orderNo;
	String orderPrice;

	String orderStatus;
	String sourceType;
	String storeId;

	String stringCreatTime;
	String tableNum;
	String thirdOrderNo;
	String userId;
    int isRead;
	String payType;

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public int getIsRead() {
		return isRead;
	}

	public void setIsRead(int isRead) {
		this.isRead = isRead;
	}

	public String getBenefitMoney() {
		return benefitMoney;
	}

	public void setBenefitMoney(String benefitMoney) {
		this.benefitMoney = benefitMoney;
	}

	public String getCreatTime() {
		return creatTime;
	}

	public void setCreatTime(String creatTime) {
		this.creatTime = creatTime;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIsCouponChangeState() {
		return isCouponChangeState;
	}

	public void setIsCouponChangeState(String isCouponChangeState) {
		this.isCouponChangeState = isCouponChangeState;
	}

	public String getIsDel() {
		return isDel;
	}

	public void setIsDel(String isDel) {
		this.isDel = isDel;
	}

	public String getIsScoreAlreadyPaid() {
		return isScoreAlreadyPaid;
	}

	public void setIsScoreAlreadyPaid(String isScoreAlreadyPaid) {
		this.isScoreAlreadyPaid = isScoreAlreadyPaid;
	}

	public String getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(String modifyTime) {
		this.modifyTime = modifyTime;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getOrderPrice() {
		return orderPrice;
	}

	public void setOrderPrice(String orderPrice) {
		this.orderPrice = orderPrice;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getSourceType() {
		return sourceType;
	}

	public void setSourceType(String sourceType) {
		this.sourceType = sourceType;
	}

	public String getStoreId() {
		return storeId;
	}

	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}

	public String getStringCreatTime() {
		return stringCreatTime;
	}

	public void setStringCreatTime(String stringCreatTime) {
		this.stringCreatTime = stringCreatTime;
	}

	public String getTableNum() {
		return tableNum;
	}

	public void setTableNum(String tableNum) {
		this.tableNum = tableNum;
	}

	public String getThirdOrderNo() {
		return thirdOrderNo;
	}

	public void setThirdOrderNo(String thirdOrderNo) {
		this.thirdOrderNo = thirdOrderNo;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
}
