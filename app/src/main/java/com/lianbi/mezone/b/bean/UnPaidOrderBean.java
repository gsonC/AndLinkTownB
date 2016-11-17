package com.lianbi.mezone.b.bean;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by zemin.zheng on 2016/11/16.
 */

public class UnPaidOrderBean implements Serializable {
    private static final long serialVersionUID = 6129235598609222382L;
    private String createTime;
    private String desc;
    private ArrayList<OneDishInOrder> detailInfo;
    private String orderAmount;
    private String orderNo;
    private String photo;
    private String thirdOrderNo;
    private String userId;
    private String userName;

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public ArrayList<OneDishInOrder> getDetailInfo() {
        return detailInfo;
    }

    public void setDetailInfo(ArrayList<OneDishInOrder> detailInfo) {
        this.detailInfo = detailInfo;
    }

    public String getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(String orderAmount) {
        this.orderAmount = orderAmount;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getThirdOrderNo() {
        return thirdOrderNo;
    }

    public void setThirdOrderNo(String thirdOrderNo) {
        this.thirdOrderNo = thirdOrderNo;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public UnPaidOrderBean() {

    }
}