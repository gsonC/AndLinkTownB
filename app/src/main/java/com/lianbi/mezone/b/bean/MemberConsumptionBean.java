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
	private String consumptionTime;
	private String consumptionThing;
	private String consumptionWhere;
	private BigDecimal consumptionMuch;

	public String getConsumptionTime() {
		return consumptionTime;
	}

	public void setConsumptionTime(String consumptionTime) {
		this.consumptionTime = consumptionTime;
	}

	public String getConsumptionThing() {
		return consumptionThing;
	}

	public void setConsumptionThing(String consumptionThing) {
		this.consumptionThing = consumptionThing;
	}

	public String getConsumptionWhere() {
		return consumptionWhere;
	}

	public void setConsumptionWhere(String consumptionWhere) {
		this.consumptionWhere = consumptionWhere;
	}

	public BigDecimal getConsumptionMuch() {
		return consumptionMuch;
	}

	public void setConsumptionMuch(BigDecimal consumptionMuch) {
		this.consumptionMuch = consumptionMuch;
	}
}
