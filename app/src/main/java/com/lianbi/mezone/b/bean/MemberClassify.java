package com.lianbi.mezone.b.bean;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 会员分类
 */
public class MemberClassify  implements Serializable {

    String   typeId;
    String typeName;
    String dataSize;
    int typeDiscountRatio;
    int   thisTypeCount;
    int  typeMaxDiscount;
    int  typeConditionMin;
    int  typeConditionMax;
   String vipCount;
   String vipGrade;
    BigDecimal discountRate;

    public BigDecimal getDiscountRate() {
        return discountRate;
    }

    public void setDiscountRate(BigDecimal discountRate) {
        this.discountRate = discountRate;
    }

    String vipGradeName;



    public String getVipGradeName() {
        return vipGradeName;
    }

    public void setVipGradeName(String vipGradeName) {
        this.vipGradeName = vipGradeName;
    }



    public String getVipGrade() {
        return vipGrade;
    }

    public void setVipGrade(String vipGrade) {
        this.vipGrade = vipGrade;
    }

    public String getVipCount() {
        return vipCount;
    }

    public void setVipCount(String vipCount) {
        this.vipCount = vipCount;
    }

    public int getTypeMaxDiscount() {
        return typeMaxDiscount;
    }

    public void setTypeMaxDiscount(int typeMaxDiscount) {
        this.typeMaxDiscount = typeMaxDiscount;
    }

    public int getTypeConditionMin() {
        return typeConditionMin;
    }

    public void setTypeConditionMin(int typeConditionMin) {
        this.typeConditionMin = typeConditionMin;
    }

    public int getTypeConditionMax() {
        return typeConditionMax;
    }

    public void setTypeConditionMax(int typeConditionMax) {
        this.typeConditionMax = typeConditionMax;
    }

    public int getThisTypeCount() {
        return thisTypeCount;
    }

    public void setThisTypeCount(int thisTypeCount) {
        this.thisTypeCount = thisTypeCount;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getDataSize() {
        return dataSize;
    }

    public void setDataSize(String dataSize) {
        this.dataSize = dataSize;
    }

    public int getTypeDiscountRatio() {
        return typeDiscountRatio;
    }

    public void setTypeDiscountRatio(int typeDiscountRatio) {
        this.typeDiscountRatio = typeDiscountRatio;
    }
}
