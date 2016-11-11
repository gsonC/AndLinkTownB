package com.lianbi.mezone.b.bean;

import java.io.Serializable;
import java.util.List;

/*
 * @创建者     master
 * @创建时间   2016/11/10 19:05
 * @描述       市的集合
 *
 * @更新者     $Author$ 
 * @更新时间   $Date$
 * @更新描述
 */
public class CitiesBean implements Serializable {
	private static final long serialVersionUID = 419162280631541817L;
	private String cityCode;
	private String cityName;
	private List<DistrictsBean> state;

	public CitiesBean(){
		super();
	}

	public CitiesBean(String cityCode,String cityName,List<DistrictsBean> state){
		super();
		this.cityCode = cityCode;
		this.cityName = cityName;
		this.state = state;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public List<DistrictsBean> getState() {
		return state;
	}

	public void setState(List<DistrictsBean> state) {
		this.state = state;
	}
}
