package com.lianbi.mezone.b.bean;

import java.io.Serializable;

/**
 * 已发短信详情
 */
public class MarketingMsgDetail implements Serializable {

	String  mobile;
	String coupGrade;
	String coupNote;

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getCoupGrade() {
		return coupGrade;
	}

	public void setCoupGrade(String coupGrade) {
		this.coupGrade = coupGrade;
	}

	public String getCoupNote() {
		return coupNote;
	}

	public void setCoupNote(String coupNote) {
		this.coupNote = coupNote;
	}
}
