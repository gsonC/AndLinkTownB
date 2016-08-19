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
public class WeiXinProduct implements Serializable {
	private static final long serialVersionUID = -5076976047607374205L;
  private   String new_product_ima;
	private String new_product_food;
	private String new_product_rated;
	private String new_product_price;
	private String new_product_choose;

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public String getNew_product_ima() {
		return new_product_ima;
	}

	public void setNew_product_ima(String new_product_ima) {
		this.new_product_ima = new_product_ima;
	}

	public String getNew_product_food() {
		return new_product_food;
	}

	public void setNew_product_food(String new_product_food) {
		this.new_product_food = new_product_food;
	}

	public String getNew_product_rated() {
		return new_product_rated;
	}

	public void setNew_product_rated(String new_product_rated) {
		this.new_product_rated = new_product_rated;
	}

	public String getNew_product_price() {
		return new_product_price;
	}

	public void setNew_product_price(String new_product_price) {
		this.new_product_price = new_product_price;
	}

	public String getNew_product_choose() {
		return new_product_choose;
	}

	public void setNew_product_choose(String new_product_choose) {
		this.new_product_choose = new_product_choose;
	}
}
