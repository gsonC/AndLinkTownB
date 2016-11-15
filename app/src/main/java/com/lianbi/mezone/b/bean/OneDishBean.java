package com.lianbi.mezone.b.bean;

import java.io.Serializable;

/**
 * Created by zemin.zheng on 2016/11/14.
 */

public class OneDishBean implements Serializable {
    private static final long serialVersionUID = 2131697627820082034L;

    private String allCookingTime;
    private String benefitMoney;
    private String businessId;
    private String businessName;
    private String createTime;
    private String eachCookingTime;
    private String num;
    private String orderNo;
    private String personNum;
    private String price;
    private String proName;
    private String sourceType;
    private String tableId;
    private String tableName;
    private String totalAmount;

    public OneDishBean() {
    }

    public String getAllCookingTime() {
        return allCookingTime;
    }

    public void setAllCookingTime(String allCookingTime) {
        this.allCookingTime = allCookingTime;
    }

    public String getBenefitMoney() {
        return benefitMoney;
    }

    public void setBenefitMoney(String benefitMoney) {
        this.benefitMoney = benefitMoney;
    }

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getEachCookingTime() {
        return eachCookingTime;
    }

    public void setEachCookingTime(String eachCookingTime) {
        this.eachCookingTime = eachCookingTime;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getPersonNum() {
        return personNum;
    }

    public void setPersonNum(String personNum) {
        this.personNum = personNum;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getProName() {
        return proName;
    }

    public void setProName(String proName) {
        this.proName = proName;
    }

    public String getSourceType() {
        return sourceType;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }

    public String getTableId() {
        return tableId;
    }

    public void setTableId(String tableId) {
        this.tableId = tableId;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }
}
