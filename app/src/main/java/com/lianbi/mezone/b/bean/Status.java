package com.lianbi.mezone.b.bean;

import java.io.Serializable;

/**
 * Created by zemin.zheng on 2016/9/14.
 */
public class Status implements Serializable {
    private static final long serialVersionUID = -6886052417874576695L;
    private String apply;//提现申请时间
    private String audit;//审核通过时间
    private String success;//打款成功时间

    public String getApply() {
        return apply;
    }

    public void setApply(String apply) {
        this.apply = apply;
    }

    public String getAudit() {
        return audit;
    }

    public void setAudit(String audit) {
        this.audit = audit;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public Status() {
    }
}
