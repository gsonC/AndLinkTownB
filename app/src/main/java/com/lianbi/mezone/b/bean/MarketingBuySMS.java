package com.lianbi.mezone.b.bean;

import java.io.Serializable;

/**
 * 购买短信
 */
public class MarketingBuySMS implements Serializable {

     int  price;
     int   total;
     int  giveTotal;

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getGiveTotal() {
        return giveTotal;
    }

    public void setGiveTotal(int giveTotal) {
        this.giveTotal = giveTotal;
    }
}

