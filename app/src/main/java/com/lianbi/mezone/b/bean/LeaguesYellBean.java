package com.lianbi.mezone.b.bean;

/**
 * 商圈吆喝动态实体类
 */
public class LeaguesYellBean {

    String  area;
    String  businessCircle;
    String  messageType;
    String  pushScope;
    String  author;
    String  phone;
    String  messageTitle;
    String  messageContent;
    String  address;
    String  logoUrl;

    boolean  isExpanded;//商圈动态列表用于判断列表是否是展开状态
    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getBusinessCircle() {
        return businessCircle;
    }

    public void setBusinessCircle(String businessCircle) {
        this.businessCircle = businessCircle;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public String getPushScope() {
        return pushScope;
    }

    public void setPushScope(String pushScope) {
        this.pushScope = pushScope;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMessageTitle() {
        return messageTitle;
    }

    public void setMessageTitle(String messageTitle) {
        this.messageTitle = messageTitle;
    }

    public String getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public boolean isExpanded() {
        return isExpanded;
    }

    public void setExpanded(boolean expanded) {
        isExpanded = expanded;
    }
}
