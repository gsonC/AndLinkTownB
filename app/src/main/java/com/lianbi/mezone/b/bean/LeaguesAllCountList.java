package com.lianbi.mezone.b.bean;

import java.io.Serializable;

/**
 * Created by chen.sun on 2016/11/8.
 */
public class LeaguesAllCountList implements Serializable {

    String  allCount;
    String  businessType;

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
