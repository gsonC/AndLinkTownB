package com.lianbi.mezone.b.bean;

import java.io.Serializable;

/*
 * @创建者     master
 * @创建时间   2016/11/9 15:40
 * @描述       销量排行
 *
 * @更新者     $Author$ 
 * @更新时间   $Date$
 * @更新描述
 */
public class ShopSaleRank implements Serializable {
	private static final long serialVersionUID = -4040827473463834848L;

	/**
	 * pro_name : 农家小炒肉
	 * saleNum : 11
	 */

	private String pro_name;
	private int saleNum;

	public String getPro_name() {
		return pro_name;
	}

	public void setPro_name(String pro_name) {
		this.pro_name = pro_name;
	}

	public int getSaleNum() {
		return saleNum;
	}

	public void setSaleNum(int saleNum) {
		this.saleNum = saleNum;
	}
}
