package com.lianbi.mezone.b.bean;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * @创建者 Administration
 * @创建时间 ＄{DATE} ＄{TIME}
 * @描述 ＄{TOOD}
 * @更新者 ＄auther＄
 * @更新时间 ＄Date＄
 * @更新描述 ＄{TOOD}＄
 */
public class WeiXinBean implements Serializable {
	private static final long serialVersionUID = -238866149790757127L;
	String productDesc;
	String shopSourceId;
	String productName;
	String productPrice;
	String productType;
	ArrayList<productImages> productImages;

	public String getProductDesc() {
		return productDesc;
	}

	public void setProductDesc(String productDesc) {
		this.productDesc = productDesc;
	}

	public String getShopSourceId() {
		return shopSourceId;
	}

	public void setShopSourceId(String shopSourceId) {
		this.shopSourceId = shopSourceId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(String productPrice) {
		this.productPrice = productPrice;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public ArrayList<WeiXinBean.productImages> getProductImages() {
		return productImages;
	}

	public void setProductImages(ArrayList<WeiXinBean.productImages> productImages) {
		this.productImages = productImages;
	}

	public class productImages implements Serializable{
		String imgId;
		String imgUrl;
		String imgDesc;

		public String getImgId() {
			return imgId;
		}

		public void setImgId(String imgId) {
			this.imgId = imgId;
		}

		public String getImgUrl() {
			return imgUrl;
		}

		public void setImgUrl(String imgUrl) {
			this.imgUrl = imgUrl;
		}

		public String getImgDesc() {
			return imgDesc;
		}

		public void setImgDesc(String imgDesc) {
			this.imgDesc = imgDesc;
		}
	}
}
