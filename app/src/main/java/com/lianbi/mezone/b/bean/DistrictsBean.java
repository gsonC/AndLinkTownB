package com.lianbi.mezone.b.bean;

import java.io.Serializable;

/*
 * @创建者     master
 * @创建时间   2016/11/10 19:07
 * @描述       区的集合
 *
 * @更新者     $Author$ 
 * @更新时间   $Date$
 * @更新描述
 */
public class DistrictsBean implements Serializable {
	private static final long serialVersionUID = -7723722557954222030L;
	private String district;
	private String districtId;

	public DistrictsBean(){
		super();
	}

	public DistrictsBean(String district,String districtId){
		super();
		this.district = district;
		this.districtId = districtId;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getDistrictId() {
		return districtId;
	}

	public void setDistrictId(String districtId) {
		this.districtId = districtId;
	}
}
