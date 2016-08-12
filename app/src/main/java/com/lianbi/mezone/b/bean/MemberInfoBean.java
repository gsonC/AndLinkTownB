package com.lianbi.mezone.b.bean;/*
 * @创建者     Administrator
 * @创建时间   2016/8/11 17:23
 * @描述       会员信息
 *
 * @更新者     $Author$ 
 * @更新时间   $Date$
 * @更新描述   ${TODO}
 */

import java.io.Serializable;

public class MemberInfoBean implements Serializable {
	private static final long serialVersionUID = -5076976047607374205L;
	private String memberPhone;
	private String memberCategory;
	private String memberSource;
	private String memberLabel;
	private int memberIntegral;
	String sortLetters;

	public String getSortLetters() {
		return sortLetters;
	}

	public void setSortLetters(String sortLetters) {
		this.sortLetters = sortLetters;
	}

	public String getMemberPhone() {
		return memberPhone;
	}

	public void setMemberPhone(String memberPhone) {
		this.memberPhone = memberPhone;
	}

	public String getMemberCategory() {
		return memberCategory;
	}

	public void setMemberCategory(String memberCategory) {
		this.memberCategory = memberCategory;
	}

	public String getMemberSource() {
		return memberSource;
	}

	public void setMemberSource(String memberSource) {
		this.memberSource = memberSource;
	}

	public String getMemberLabel() {
		return memberLabel;
	}

	public void setMemberLabel(String memberLabel) {
		this.memberLabel = memberLabel;
	}

	public int getMemberIntegral() {
		return memberIntegral;
	}

	public void setMemberIntegral(int memberIntegral) {
		this.memberIntegral = memberIntegral;
	}
}
