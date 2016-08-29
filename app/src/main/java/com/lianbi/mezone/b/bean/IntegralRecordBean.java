package com.lianbi.mezone.b.bean;/*
 * @创建者     Administrator
 * @创建时间   2016/8/12 17:43
 * @描述       积分记录bean
 *
 * @更新者     $Author$ 
 * @更新时间   $Date$
 * @更新描述   ${TODO}
 */

import java.io.Serializable;

public class IntegralRecordBean implements Serializable {

	private static final long serialVersionUID = -4417227025478435251L;

	private String consumName;
	private String consumSorce;
	private String consumAmount;
	private String createTime;

	public String getConsumName() {
		return consumName;
	}

	public void setConsumName(String consumName) {
		this.consumName = consumName;
	}

	public String getConsumSorce() {
		return consumSorce;
	}

	public void setConsumSorce(String consumSorce) {
		this.consumSorce = consumSorce;
	}

	public String getConsumAmount() {
		return consumAmount;
	}

	public void setConsumAmount(String consumAmount) {
		this.consumAmount = consumAmount;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
}
