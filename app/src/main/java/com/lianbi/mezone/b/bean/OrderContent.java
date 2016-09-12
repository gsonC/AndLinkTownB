package com.lianbi.mezone.b.bean;

import java.io.Serializable;

public class OrderContent implements Serializable {

	String  orderID;
	String  paystate;
	String  ordertime;
	String  orderprice;


	public String getOrderID() {
		return orderID;
	}

	public void setOrderID(String orderID) {
		this.orderID = orderID;
	}

	public String getPaystate() {
		return paystate;
	}

	public void setPaystate(String paystate) {
		this.paystate = paystate;
	}

	public String getOrdertime() {
		return ordertime;
	}

	public void setOrdertime(String ordertime) {
		this.ordertime = ordertime;
	}

	public String getOrderprice() {
		return orderprice;
	}

	public void setOrderprice(String orderprice) {
		this.orderprice = orderprice;
	}
}
