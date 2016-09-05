package com.lianbi.mezone.b.bean;

import java.io.Serializable;

/**
 * @创建者 Administration
 * @创建时间 ＄{DATE} ＄{TIME}
 * @描述 ＄{TOOD}
 * @更新者 ＄auther＄
 * @更新时间 ＄Date＄
 * @更新描述 ＄{TOOD}＄
 */
public class WeiXinBean implements Serializable {
	String id;
	String path;
	String price;
	String productDesc;
	String proName;

	public String getProductDesc() {
		return productDesc;
	}

	public void setProductDesc(String productDesc) {
		this.productDesc = productDesc;
	}

	String proType;
   String shopSourceId;
	public String getId() {
		return id;
	}

	public String getShopSourceId() {
		return shopSourceId;
	}

	public void setShopSourceId(String shopSourceId) {
		this.shopSourceId = shopSourceId;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getProName() {
		return proName;
	}

	public void setProName(String proName) {
		this.proName = proName;
	}

	public String getProType() {
		return proType;
	}

	public void setProType(String proType) {
		this.proType = proType;
	}
}
