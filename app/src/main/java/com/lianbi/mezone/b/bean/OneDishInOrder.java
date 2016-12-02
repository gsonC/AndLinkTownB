package com.lianbi.mezone.b.bean;

import java.io.Serializable;

/**
 * Created by zemin.zheng on 2016/11/16.
 */

public class OneDishInOrder implements Serializable {
    private static final long serialVersionUID = 7917189692362848831L;
    private String benefitMoney;//折扣
    private String cookingTime;//烹饪时间
    private String desc;//备注
    private String isDel;//产品状态0:正常 1：已删除
    private String num;//产品数量
    private String price;//会员价
    private String proName;
    private String productId;
    private String originalPrice;//非会员价

    public String getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(String originalPrice) {
        this.originalPrice = originalPrice;
    }

    public OneDishInOrder() {
    }

    public String getProName() {
        return proName;
    }

    public void setProName(String proName) {
        this.proName = proName;
    }

    public String getBenefitMoney() {
        return benefitMoney;
    }

    public void setBenefitMoney(String benefitMoney) {
        this.benefitMoney = benefitMoney;
    }

    public String getCookingTime() {
        return cookingTime;
    }

    public void setCookingTime(String cookingTime) {
        this.cookingTime = cookingTime;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getIsDel() {
        return isDel;
    }

    public void setIsDel(String isDel) {
        this.isDel = isDel;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }
}
