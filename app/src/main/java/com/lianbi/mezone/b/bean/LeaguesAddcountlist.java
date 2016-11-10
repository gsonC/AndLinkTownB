package com.lianbi.mezone.b.bean;

import java.io.Serializable;

/**
 * Created by chen.sun on 2016/11/7.
 */
public class LeaguesAddCountlist implements Serializable {

        String  addCount;
        String  businessType;

        public String getAddCount() {
            return addCount;
        }

        public void setAddCount(String addCount) {
            this.addCount = addCount;
        }

        public String getBusinessType() {
            return businessType;
        }

        public void setBusinessType(String businessType) {
            this.businessType = businessType;
        }

}
