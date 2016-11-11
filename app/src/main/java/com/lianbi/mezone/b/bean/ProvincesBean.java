package com.lianbi.mezone.b.bean;

import java.io.Serializable;
import java.util.List;

/*
 * @创建者     master
 * @创建时间   2016/11/10 19:04
 * @描述       省的集合
 *
 * @更新者     $Author$ 
 * @更新时间   $Date$
 * @更新描述
 */
public class ProvincesBean implements Serializable {
	private static final long serialVersionUID = 72657229420518665L;
	private String provinceName;
	private String provinceCode;
	private List<CitiesBean> region;

	public ProvincesBean(){
		super();
	}

	public ProvincesBean(String provinceName,String provinceCode,List<CitiesBean> region){
		super();
		this.provinceName =provinceName;
		this.provinceCode = provinceCode;
		this.region = region;
	}

	public String getProvinceName() {
		return provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	public String getProvinceCode() {
		return provinceCode;
	}

	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}

	public List<CitiesBean> getRegion() {
		return region;
	}

	public void setRegion(List<CitiesBean> region) {
		this.region = region;
	}
}
