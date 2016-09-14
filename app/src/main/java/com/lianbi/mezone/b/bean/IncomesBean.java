package com.lianbi.mezone.b.bean;/*
 * @创建者     Administrator
 * @创建时间   2016/9/14 9:23
 * @描述       ${TODO}
 *
 * @更新者     $Author$ 
 * @更新时间   $Date$
 * @更新描述   ${TODO}
 */

import java.io.Serializable;

public class IncomesBean implements Serializable {
	private static final long serialVersionUID = -3422923969793696896L;
	private String time;
	private String optType;
	private int amount;
	private String storeNo;
	private String optMsg;
	private String createTime;
	private String accountNo;
	private String settleDate;

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getOptType() {
		return optType;
	}

	public void setOptType(String optType) {
		this.optType = optType;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public String getStoreNo() {
		return storeNo;
	}

	public void setStoreNo(String storeNo) {
		this.storeNo = storeNo;
	}

	public String getOptMsg() {
		return optMsg;
	}

	public void setOptMsg(String optMsg) {
		this.optMsg = optMsg;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public String getSettleDate() {
		return settleDate;
	}

	public void setSettleDate(String settleDate) {
		this.settleDate = settleDate;
	}
}
