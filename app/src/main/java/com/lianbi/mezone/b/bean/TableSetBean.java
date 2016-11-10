package com.lianbi.mezone.b.bean;

import java.io.Serializable;

public class TableSetBean implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 8927806972434126948L;

    private String tableId;//主键唯一标识
    private String createTime;//创建时间
    private int tradeStatus;//店铺营业标识 0休息 1 营业
    private int tableStatus;//桌位状态 0-空位，1-已下单，2-已支付
    private String tableName;//桌位名称
    private String codeUrl;//二维码相对路径
    private String modifyTime;//修改时间
    private String storeId;//店铺id
    private int selectStatus;//0代表删除选择按钮不显示，1代表删除选择按钮显示但未被选择，2代表删除选择按钮显示且已被选择
    private boolean isNew;
    private String presetCount;
    private String orderAmt;
    private String actualCount;


    public String getActualCount() {
        return actualCount;
    }

    public void setActualCount(String actualCount) {
        this.actualCount = actualCount;
    }

    public String getOrderAmt() {
        return orderAmt;
    }

    public void setOrderAmt(String orderAmt) {
        this.orderAmt = orderAmt;
    }

    public String getPresetCount() {
        return presetCount;
    }

    public void setPresetCount(String presetCount) {
        this.presetCount = presetCount;
    }

    public boolean isNew() {
        return isNew;
    }

    public void setNew(boolean aNew) {
        isNew = aNew;
    }

    public int getSelectStatus() {
        return selectStatus;
    }

    public void setSelectStatus(int selectStatus) {
        this.selectStatus = selectStatus;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getTableId() {
        return tableId;
    }

    public void setTableId(String tableId) {
        this.tableId = tableId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public int getTradeStatus() {
        return tradeStatus;
    }

    public void setTradeStatus(int tradeStatus) {
        this.tradeStatus = tradeStatus;
    }

    public int getTableStatus() {
        return tableStatus;
    }

    public void setTableStatus(int tableStatus) {
        this.tableStatus = tableStatus;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getCodeUrl() {
        return codeUrl;
    }

    public void setCodeUrl(String codeUrl) {
        this.codeUrl = codeUrl;
    }

    public String getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(String modifyTime) {
        this.modifyTime = modifyTime;
    }

    public TableSetBean() {
    }
}
