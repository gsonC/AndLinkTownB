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
public class Consumption implements Serializable {
	private static final long serialVersionUID = -1479787686875580498L;
     String tv_consum_total;
	String tv_consum_time;
	String tv_consum_price;
	String tv_consum_detail;
	String tv_consum_daytime;
	String tv_consum_where;
	String tv_consum_shoukuan;

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public String getTv_consum_total() {
		return tv_consum_total;
	}

	public void setTv_consum_total(String tv_consum_total) {
		this.tv_consum_total = tv_consum_total;
	}

	public String getTv_consum_time() {
		return tv_consum_time;
	}

	public void setTv_consum_time(String tv_consum_time) {
		this.tv_consum_time = tv_consum_time;
	}

	public String getTv_consum_price() {
		return tv_consum_price;
	}

	public void setTv_consum_price(String tv_consum_price) {
		this.tv_consum_price = tv_consum_price;
	}

	public String getTv_consum_detail() {
		return tv_consum_detail;
	}

	public void setTv_consum_detail(String tv_consum_detail) {
		this.tv_consum_detail = tv_consum_detail;
	}

	public String getTv_consum_daytime() {
		return tv_consum_daytime;
	}

	public void setTv_consum_daytime(String tv_consum_daytime) {
		this.tv_consum_daytime = tv_consum_daytime;
	}

	public String getTv_consum_where() {
		return tv_consum_where;
	}

	public void setTv_consum_where(String tv_consum_where) {
		this.tv_consum_where = tv_consum_where;
	}

	public String getTv_consum_shoukuan() {
		return tv_consum_shoukuan;
	}

	public void setTv_consum_shoukuan(String tv_consum_shoukuan) {
		this.tv_consum_shoukuan = tv_consum_shoukuan;
	}
}
