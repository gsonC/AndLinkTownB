package com.lianbi.mezone.b.bean;/*
 * @创建者     Administrator
 * @创建时间   2016/7/28 15:53
 * @描述       ${TODO}
 *
 * @更新者     $Author$ 
 * @更新时间   $Date$
 * @更新描述   ${TODO}
 */

import java.io.Serializable;
import java.math.BigDecimal;

public class FinancialOfficeAmountBean implements Serializable {

	private static final long serialVersionUID = 5351012704787935469L;

	private BigDecimal accountTotalIncome;
	private BigDecimal sotreFrozenAmount;
	private BigDecimal storeBalance;
	private BigDecimal storeTodayIncome;
	private BigDecimal storeTotalIncome;
	private BigDecimal storeWithdrawAmount;

	private String endDate;
	private String startDate;
	private BigDecimal rate;
	private BigDecimal cheapRate;

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public BigDecimal getRate() {
		return rate;
	}

	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}

	public BigDecimal getCheapRate() {
		return cheapRate;
	}

	public void setCheapRate(BigDecimal cheapRate) {
		this.cheapRate = cheapRate;
	}

	public BigDecimal getAccountTotalIncome() {
		return accountTotalIncome;
	}

	public void setAccountTotalIncome(BigDecimal accountTotalIncome) {
		this.accountTotalIncome = accountTotalIncome;
	}

	public BigDecimal getSotreFrozenAmount() {
		return sotreFrozenAmount;
	}

	public void setSotreFrozenAmount(BigDecimal sotreFrozenAmount) {
		this.sotreFrozenAmount = sotreFrozenAmount;
	}

	public BigDecimal getStoreBalance() {
		return storeBalance;
	}

	public void setStoreBalance(BigDecimal storeBalance) {
		this.storeBalance = storeBalance;
	}

	public BigDecimal getStoreTodayIncome() {
		return storeTodayIncome;
	}

	public void setStoreTodayIncome(BigDecimal storeTodayIncome) {
		this.storeTodayIncome = storeTodayIncome;
	}

	public BigDecimal getStoreTotalIncome() {
		return storeTotalIncome;
	}

	public void setStoreTotalIncome(BigDecimal storeTotalIncome) {
		this.storeTotalIncome = storeTotalIncome;
	}

	public BigDecimal getStoreWithdrawAmount() {
		return storeWithdrawAmount;
	}

	public void setStoreWithdrawAmount(BigDecimal storeWithdrawAmount) {
		this.storeWithdrawAmount = storeWithdrawAmount;
	}
}
