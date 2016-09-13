package com.lianbi.mezone.b.bean;

import java.io.Serializable;

public class PushDataBean implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 5426769992680535808L;

	String title;
	String content;
	String img;
	int callType;//1跳转URL 2新订单 3呼叫买单 4呼叫服务
	String jumpUrl;
	String type;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getJumpUrl() {
		return jumpUrl;
	}

	public void setJumpUrl(String jumpUrl) {
		this.jumpUrl = jumpUrl;
	}


	public int getCallType() {
		return callType;
	}

	public void setCallType(int callType) {
		this.callType = callType;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

}
