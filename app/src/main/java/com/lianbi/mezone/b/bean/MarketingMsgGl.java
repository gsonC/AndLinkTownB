package com.lianbi.mezone.b.bean;

import java.io.Serializable;

/**
 * 营销短信管理
 */
public class MarketingMsgGl implements Serializable {

	String  batchNo;
	String  sendDate;
	String  sendMobles;
	String  sendNum;

	public String getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}

	public String getSendDate() {
		return sendDate;
	}

	public void setSendDate(String sendDate) {
		this.sendDate = sendDate;
	}

	public String getSendMobles() {
		return sendMobles;
	}

	public void setSendMobles(String sendMobles) {
		this.sendMobles = sendMobles;
	}

	public String getSendNum() {
		return sendNum;
	}

	public void setSendNum(String sendNum) {
		this.sendNum = sendNum;
	}
}
