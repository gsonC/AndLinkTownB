package com.lianbi.mezone.b.bean;/*
 * @创建者     Administrator
 * @创建时间   2016/8/12 17:02
 * @描述       会员消费bean
 *
 * @更新者     $Author$ 
 * @更新时间   $Date$
 * @更新描述   ${TODO}
 */

import java.io.Serializable;
import java.math.BigDecimal;

public class MemberConsumptionBean implements Serializable {
	private static final long serialVersionUID = -8870942307153826341L;

	private String consumType;
	private String consumName;
	private BigDecimal consumPrice;
	private String consumNum;
	private String consumDiscount;
	private BigDecimal consumAmount;
	private String createTime;
	private String consumSorce;

	public String getConsumType() {
		return consumType;
	}

	public void setConsumType(String consumType) {
		this.consumType = consumType;
	}

	public String getConsumName() {
		return consumName;
	}

	public void setConsumName(String consumName) {
		this.consumName = consumName;
	}

	public BigDecimal getConsumPrice() {
		return consumPrice;
	}

	public void setConsumPrice(BigDecimal consumPrice) {
		this.consumPrice = consumPrice;
	}

	public String getConsumNum() {
		return consumNum;
	}

	public void setConsumNum(String consumNum) {
		this.consumNum = consumNum;
	}

	public String getConsumDiscount() {
		return consumDiscount;
	}

	public void setConsumDiscount(String consumDiscount) {
		this.consumDiscount = consumDiscount;
	}

	public BigDecimal getConsumAmount() {
		return consumAmount;
	}

	public void setConsumAmount(BigDecimal consumAmount) {
		this.consumAmount = consumAmount;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getConsumSorce() {
		return consumSorce;
	}

	public void setConsumSorce(String consumSorce) {
		this.consumSorce = consumSorce;
	}

}
