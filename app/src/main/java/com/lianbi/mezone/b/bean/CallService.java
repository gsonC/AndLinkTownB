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
public class CallService implements Serializable {
	private static final long serialVersionUID = 5653463899216784845L;
	String tv_callset_table;
	String tv_callset_content;
	String tv_callset_time;
	String tv_callset_yartime;
	String tv_callset_deal;

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public String getTv_callset_table() {
		return tv_callset_table;
	}

	public void setTv_callset_table(String tv_callset_table) {
		this.tv_callset_table = tv_callset_table;
	}

	public String getTv_callset_content() {
		return tv_callset_content;
	}

	public void setTv_callset_content(String tv_callset_content) {
		this.tv_callset_content = tv_callset_content;
	}

	public String getTv_callset_time() {
		return tv_callset_time;
	}

	public void setTv_callset_time(String tv_callset_time) {
		this.tv_callset_time = tv_callset_time;
	}

	public String getTv_callset_yartime() {
		return tv_callset_yartime;
	}

	public void setTv_callset_yartime(String tv_callset_yartime) {
		this.tv_callset_yartime = tv_callset_yartime;
	}

	public String getTv_callset_deal() {
		return tv_callset_deal;
	}

	public void setTv_callset_deal(String tv_callset_deal) {
		this.tv_callset_deal = tv_callset_deal;
	}
}
