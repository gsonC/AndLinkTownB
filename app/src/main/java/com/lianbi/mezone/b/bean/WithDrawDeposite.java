package com.lianbi.mezone.b.bean;

import java.io.Serializable;
import java.util.Date;

import cn.com.hgh.utils.AbDateUtil;

public class WithDrawDeposite implements Serializable, Comparable<WithDrawDeposite> {
    private static final long serialVersionUID = 6092270372321487489L;
    private String amount;
    private String checkStatus;
    private String outOrderId;
    private String bankAccountNo;
    private String accountNo;
    private String storeNo;
    private String id;
    private Status status;

    public WithDrawDeposite() {
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStoreNo() {
        return storeNo;
    }

    public void setStoreNo(String storeNo) {
        this.storeNo = storeNo;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getBankAccountNo() {
        return bankAccountNo;
    }

    public void setBankAccountNo(String bankAccountNo) {
        this.bankAccountNo = bankAccountNo;
    }

    public String getOutOrderId() {
        return outOrderId;
    }

    public void setOutOrderId(String outOrderId) {
        this.outOrderId = outOrderId;
    }

    public String getCheckStatus() {
        return checkStatus;
    }

    public void setCheckStatus(String checkStatus) {
        this.checkStatus = checkStatus;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    @Override
    public int compareTo(WithDrawDeposite another) {
        Date thisDate = AbDateUtil.getDateByFormat(this.status.getApply(), AbDateUtil.dateFormatYMDHMS);
        Date anotherDate = AbDateUtil.getDateByFormat(another.getStatus().getApply(), AbDateUtil.dateFormatYMDHMS);
        return anotherDate.compareTo(thisDate);
    }
}
