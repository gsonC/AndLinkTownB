package com.lianbi.mezone.b.bean;

import android.widget.ImageView;

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
public class MemberMessage implements Serializable {
	String id;
	String tv_tagmessage;
	ImageView point_ima;
	ImageView pullgoodsIma;
	String labelName;
	String storeId;
	String productType;
	String productName;
	String productDesc;
	String productPrice;
	String productImgId;
	String isOnline;
	String createTime;
	ArrayList<MemberMessage.productImages> productImages;
	String shopSourceId;
	String updateTime;

	public String getShopSourceId() {
		return shopSourceId;
	}

	public void setShopSourceId(String shopSourceId) {
		this.shopSourceId = shopSourceId;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public ArrayList<MemberMessage.productImages> getProductImages() {
		return productImages;
	}

	public void setProductImages(ArrayList<MemberMessage.productImages> productImages) {
		this.productImages = productImages;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTv_tagmessage() {
		return tv_tagmessage;
	}

	public void setTv_tagmessage(String tv_tagmessage) {
		this.tv_tagmessage = tv_tagmessage;
	}

	public ImageView getPoint_ima() {
		return point_ima;
	}

	public void setPoint_ima(ImageView point_ima) {
		this.point_ima = point_ima;
	}

	public ImageView getPullgoodsIma() {
		return pullgoodsIma;
	}

	public void setPullgoodsIma(ImageView pullgoodsIma) {
		this.pullgoodsIma = pullgoodsIma;
	}

	public String getLabelName() {
		return labelName;
	}

	public void setLabelName(String labelName) {
		this.labelName = labelName;
	}

	public String getStoreId() {
		return storeId;
	}

	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductDesc() {
		return productDesc;
	}

	public void setProductDesc(String productDesc) {
		this.productDesc = productDesc;
	}

	public String getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(String productPrice) {
		this.productPrice = productPrice;
	}

	public String getProductImgId() {
		return productImgId;
	}

	public void setProductImgId(String productImgId) {
		this.productImgId = productImgId;
	}

	public String getIsOnline() {
		return isOnline;
	}

	public void setIsOnline(String isOnline) {
		this.isOnline = isOnline;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public class productImages{
		private String imgDesc;
		private String imgId;
		private String imgUrl;
		private String productId;

		public String getImgDesc() {
			return imgDesc;
		}

		public void setImgDesc(String imgDesc) {
			this.imgDesc = imgDesc;
		}

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

		public String getProductId() {
			return productId;
		}

		public void setProductId(String productId) {
			this.productId = productId;
		}
	}

}
