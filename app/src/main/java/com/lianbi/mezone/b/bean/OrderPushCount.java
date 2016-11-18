package com.lianbi.mezone.b.bean;

import java.io.Serializable;

/*
 * @创建者     master
 * @创建时间   2016/11/8 20:29
 * @描述       首页推送消息记录
 *
 * @更新者     $Author$ 
 * @更新时间   $Date$
 * @更新描述
 */
public class OrderPushCount implements Serializable {
	private static final long serialVersionUID = 2406594875548208712L;

	/**
	 * pushCount : 0
	 * orderCount : 0
	 * paidCount : 0
	 */

	private int pushCount;
	private int orderCount;
	private int paidCount;

	public int getPushCount() {
		return pushCount;
	}

	public void setPushCount(int pushCount) {
		this.pushCount = pushCount;
	}

	public int getOrderCount() {
		return orderCount;
	}

	public void setOrderCount(int orderCount) {
		this.orderCount = orderCount;
	}

	public int getPaidCount() {
		return paidCount;
	}

	public void setPaidCount(int paidCount) {
		this.paidCount = paidCount;
	}
}
