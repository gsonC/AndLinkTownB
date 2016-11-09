package com.lianbi.mezone.b.bean;

import java.io.Serializable;

/**
 * @创建者 Administration
 * @创建时间 ＄{DATE} ＄{TIME}
 * @描述 ＄{TOOD}
 * @更新者 ＄auther＄
 * @更新时间 ＄Date＄
 * @更新描述 ＄{TOOD}＄
 */
public class Consumption implements Serializable {
	private static final long serialVersionUID = -1479787686875580498L;
     String createTime;
	String tableId;
	String productCount;
	String unPaidorderAmt;
	String tableName;

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getTableId() {
		return tableId;
	}

	public void setTableId(String tableId) {
		this.tableId = tableId;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getUnPaidorderAmt() {
		return unPaidorderAmt;
	}

	public void setUnPaidorderAmt(String unPaidorderAmt) {
		this.unPaidorderAmt = unPaidorderAmt;
	}

	public String getProductCount() {
		return productCount;
	}

	public void setProductCount(String productCount) {
		this.productCount = productCount;
	}
}
