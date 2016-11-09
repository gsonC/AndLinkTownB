package com.lianbi.mezone.b.bean;

import java.io.Serializable;

/*
 * @创建者     master
 * @创建时间   2016/11/9 14:50
 * @描述       首页实时消费bean
 *
 * @更新者     $Author$ 
 * @更新时间   $Date$
 * @更新描述
 */
public class ShopConsumption implements Serializable {
	private static final long serialVersionUID = -4404243319111464093L;

	/**
	 * benefitMoney : 0
	 * creatTime : 1478672232000
	 * desc :
	 * id : 718
	 * isCouponChangeState : N
	 * isDel : 0
	 * isScoreAlreadyPaid : Y
	 * modifyTime : 1478672232000
	 * orderNo : 201611091417120893035763458791
	 * orderPrice : 10
	 * orderStatus : 1
	 * sourceType : tss
	 * storeId : BD2016053018405200000042
	 * stringCreatTime : 14:17
	 * tableNum : 8
	 * thirdOrderNo : 032016110914170700002032
	 * userId : VI082016110712224600004578
	 */

	private int benefitMoney;
	private long creatTime;
	private String desc;
	private int id;
	private String isCouponChangeState;
	private int isDel;
	private String isScoreAlreadyPaid;
	private long modifyTime;
	private String orderNo;
	private String orderPrice;
	private String orderStatus;
	private String sourceType;
	private String storeId;
	private String stringCreatTime;
	private String tableNum;
	private String thirdOrderNo;
	private String userId;
	private boolean sign;

	public boolean isSign() {
		return sign;
	}

	public void setSign(boolean sign) {
		this.sign = sign;
	}

	public int getBenefitMoney() {
		return benefitMoney;
	}

	public void setBenefitMoney(int benefitMoney) {
		this.benefitMoney = benefitMoney;
	}

	public long getCreatTime() {
		return creatTime;
	}

	public void setCreatTime(long creatTime) {
		this.creatTime = creatTime;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getIsCouponChangeState() {
		return isCouponChangeState;
	}

	public void setIsCouponChangeState(String isCouponChangeState) {
		this.isCouponChangeState = isCouponChangeState;
	}

	public int getIsDel() {
		return isDel;
	}

	public void setIsDel(int isDel) {
		this.isDel = isDel;
	}

	public String getIsScoreAlreadyPaid() {
		return isScoreAlreadyPaid;
	}

	public void setIsScoreAlreadyPaid(String isScoreAlreadyPaid) {
		this.isScoreAlreadyPaid = isScoreAlreadyPaid;
	}

	public long getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(long modifyTime) {
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
