package com.lianbi.mezone.b.bean;

import java.io.Serializable;
import java.util.Date;

import cn.com.hgh.utils.AbDateUtil;

/**
 * Created by zemin.zheng on 2016/8/25.
 */
public class CouponBean implements Serializable, Comparable<CouponBean> {
    private static final long serialVersionUID = -7853355851081754560L;
    private String isValide;
    private String issuedStoreId;
    private String limitAmt;
    private String remark;
    private String updateTime;
    private String coupName;
    private String coupContent;
    private String createTime;
    private String coupAmt;
    private String beginTime;
    private String endTime;
    private String id;

    public CouponBean() {
    }

    public String getIsValide() {
        return isValide;
    }

    public void setIsValide(String isValide) {
        this.isValide = isValide;
    }

    public String getIssuedStoreId() {
        return issuedStoreId;
    }

    public void setIssuedStoreId(String issuedStoreId) {
        this.issuedStoreId = issuedStoreId;
    }

    public String getLimitAmt() {
        return limitAmt;
    }

    public void setLimitAmt(String limitAmt) {
        this.limitAmt = limitAmt;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getCoupName() {
        return coupName;
    }

    public void setCoupName(String coupName) {
        this.coupName = coupName;
    }

    public String getCoupContent() {
        return coupContent;
    }

    public void setCoupContent(String coupContent) {
        this.coupContent = coupContent;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getCoupAmt() {
        return coupAmt;
    }

    public void setCoupAmt(String coupAmt) {
        this.coupAmt = coupAmt;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public int compareTo(CouponBean another) {
        Date thisDate = AbDateUtil.getDateByFormat(this.createTime, AbDateUtil.dateFormatYMDHMS);
        Date anotherDate = AbDateUtil.getDateByFormat(another.getCreateTime(), AbDateUtil.dateFormatYMDHMS);
        return anotherDate.compareTo(thisDate);
    }
}
