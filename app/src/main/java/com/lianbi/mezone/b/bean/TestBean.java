package com.lianbi.mezone.b.bean;

import java.io.Serializable;

/*
 * @创建者     master
 * @创建时间   2016/10/31 15:08
 * @描述       测试Bean
 *
 * @更新者     $Author$ 
 * @更新时间   $Date$
 * @更新描述
 */
public class TestBean implements Serializable {
	private static final long serialVersionUID = -3608735369557724721L;
	private String much;
	private String table;
	private String time;

	public String getMuch() {
		return much;
	}

	public void setMuch(String much) {
		this.much = much;
	}

	public String getTable() {
		return table;
	}

	public void setTable(String table) {
		this.table = table;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}
}
