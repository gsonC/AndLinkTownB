package com.lianbi.mezone.b.bean;/*
 * @创建者     Administrator
 * @创建时间   2016/8/15 12:37
 * @描述       选择标签bean
 *
 * @更新者     $Author$ 
 * @更新时间   $Date$
 * @更新描述
 */

import java.io.Serializable;

public class SelectTagBean implements Serializable {
	private static final long serialVersionUID = 48431753712502139L;
	private boolean isChecked;
	private String labelId;
	private String businessId;
	private String labelName;
	private String createTime;
	private String updateTime;

	public String getLabelId() {
		return labelId;
	}

	public void setLabelId(String labelId) {
		this.labelId = labelId;
	}

	public String getBusinessId() {
		return businessId;
	}

	public void setBusinessId(String businessId) {
		this.businessId = businessId;
	}

	public String getLabelName() {
		return labelName;
	}

	public void setLabelName(String labelName) {
		this.labelName = labelName;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public boolean isChecked() {
		return isChecked;
	}

	public void setChecked(boolean checked) {
		isChecked = checked;
	}


}
