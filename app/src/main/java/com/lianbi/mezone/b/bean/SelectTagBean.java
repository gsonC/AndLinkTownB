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
	private String tagContent;
	private boolean isChecked;
	private int id;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean isChecked() {
		return isChecked;
	}

	public void setChecked(boolean checked) {
		isChecked = checked;
	}

	public String getTagContent() {
		return tagContent;
	}

	public void setTagContent(String tagContent) {
		this.tagContent = tagContent;
	}
}
