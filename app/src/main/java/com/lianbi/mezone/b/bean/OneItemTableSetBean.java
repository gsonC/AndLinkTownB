package com.lianbi.mezone.b.bean;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by zemin.zheng on 2016/10/28.
 */

public class OneItemTableSetBean implements Serializable {
    private static final long serialVersionUID = -921258936805069595L;
    private ArrayList<TableSetBean> tableSetBeanList = new ArrayList<>();

    public OneItemTableSetBean() {
    }

    public ArrayList<TableSetBean> getTableSetBeanList() {
        return tableSetBeanList;
    }

    public void setTableSetBeanList(ArrayList<TableSetBean> tableSetBeanList) {
        this.tableSetBeanList = tableSetBeanList;
    }
}
