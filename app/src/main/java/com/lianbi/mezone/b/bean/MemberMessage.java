package com.lianbi.mezone.b.bean;

import android.widget.ImageView;

import java.io.Serializable;

/**
 * @创建者 Administration
 * @创建时间 ＄{DATE} ＄{TIME}
 * @描述 ＄{TOOD}
 * @更新者 ＄auther＄
 * @更新时间 ＄Date＄
 * @更新描述 ＄{TOOD}＄
 */
public class MemberMessage implements Serializable {
	int id;
	String tv_tagmessage;
	ImageView point_ima;
	ImageView pullgoodsIma;
	String tv_tag;
	String rated;

	String trated;
    String point_goodsName;
	String goodsPoint;
	String pullgoods;
	String pushgoods;
	String changegoods;

	String labelName;

	public String getLabelName() {
		return labelName;
	}

	public void setLabelName(String labelName) {
		this.labelName = labelName;
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

	public String getRated() {
		return rated;
	}

	public void setRated(String rated) {
		this.rated = rated;
	}

	public String getTv_tag() {
		return tv_tag;
	}

	public void setTv_tag(String tv_tag) {
		this.tv_tag = tv_tag;
	}

	public String getGoodsPoint() {
		return goodsPoint;
	}

	public void setGoodsPoint(String goodsPoint) {
		this.goodsPoint = goodsPoint;
	}

	public String getTrated() {
		return trated;
	}

	public void setTrated(String trated) {
		this.trated = trated;
	}

	public String getPullgoods() {
		return pullgoods;
	}

	public void setPullgoods(String pullgoods) {
		this.pullgoods = pullgoods;
	}

	public String getPushgoods() {
		return pushgoods;
	}

	public void setPushgoods(String pushgoods) {
		this.pushgoods = pushgoods;
	}

	public String getChangegoods() {
		return changegoods;
	}

	public void setChangegoods(String changegoods) {
		this.changegoods = changegoods;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPoint_goodsName() {
		return point_goodsName;
	}

	public void setPoint_goodsName(String point_goodsName) {
		this.point_goodsName = point_goodsName;
	}
}
