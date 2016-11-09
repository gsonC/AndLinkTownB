package com.lianbi.mezone.b.bean;

import java.io.Serializable;

/*
 * @创建者     master
 * @创建时间   2016/11/9 9:42
 * @描述       首页消费曲线折线图
 *
 * @更新者     $Author$ 
 * @更新时间   $Date$
 * @更新描述
 */
public class ShopConsumptionCurve implements Serializable {
	private static final long serialVersionUID = -5265837758083842705L;
	private String consumption;
	private String time;

	public String getConsumption() {
		return consumption;
	}

	public void setConsumption(String consumption) {
		this.consumption = consumption;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}
}
