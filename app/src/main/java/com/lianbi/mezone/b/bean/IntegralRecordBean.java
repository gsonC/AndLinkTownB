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
import java.math.BigDecimal;

public class IntegralRecordBean implements Serializable {

	private static final long serialVersionUID = -4417227025478435251L;

	private String recordTime;
	private String recordThing;
	private String recordWhrer;
	private BigDecimal recordInteger;
	private int type;

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getRecordTime() {
		return recordTime;
	}

	public void setRecordTime(String recordTime) {
		this.recordTime = recordTime;
	}

	public String getRecordThing() {
		return recordThing;
	}

	public void setRecordThing(String recordThing) {
		this.recordThing = recordThing;
	}

	public String getRecordWhrer() {
		return recordWhrer;
	}

	public void setRecordWhrer(String recordWhrer) {
		this.recordWhrer = recordWhrer;
	}

	public BigDecimal getRecordInteger() {
		return recordInteger;
	}

	public void setRecordInteger(BigDecimal recordInteger) {
		this.recordInteger = recordInteger;
	}
}
