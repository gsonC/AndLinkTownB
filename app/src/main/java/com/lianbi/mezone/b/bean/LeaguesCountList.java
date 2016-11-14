package com.lianbi.mezone.b.bean;

import java.io.Serializable;

/**
 * 商圈统计实体类
 */
public class LeaguesCountList implements Serializable {

    String  addCount;
    String  allCount;
    String  businessType;

    public String getAddCount() {
        return addCount;
    }

    public void setAddCount(String addCount) {
        this.addCount = addCount;
    }

    public String getAllCount() {
        return allCount;
    }

    public void setAllCount(String allCount) {
        this.allCount = allCount;
    }

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }
}
