package com.lianbi.mezone.b.bean;

import java.io.Serializable;
import java.util.List;

public class IncomeBean implements Serializable {
	private static final long serialVersionUID = -3794854072275976057L;
	private String time;
	private List<data> data;

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public List<IncomeBean.data> getData() {
		return data;
	}

	public void setData(List<IncomeBean.data> data) {
		this.data = data;
	}

	public class data implements Serializable {
		private static final long serialVersionUID = -6373837579548291610L;
		private String optType;
		private int amount;
		private String storeNo;
		private String optMsg;
		private String createTime;
		private String accountNo;
		private String settleDate;

		public String getOptType() {
			return optType;
		}

		public void setOptType(String optType) {
			this.optType = optType;
		}

		public int getAmount() {
			return amount;
		}

		public void setAmount(int amount) {
			this.amount = amount;
		}

		public String getStoreNo() {
			return storeNo;
		}

		public void setStoreNo(String storeNo) {
			this.storeNo = storeNo;
		}

		public String getOptMsg() {
			return optMsg;
		}

		public void setOptMsg(String optMsg) {
			this.optMsg = optMsg;
		}

		public String getCreateTime() {
			return createTime;
		}

		public void setCreateTime(String createTime) {
			this.createTime = createTime;
		}

		public String getAccountNo() {
			return accountNo;
		}

		public void setAccountNo(String accountNo) {
			this.accountNo = accountNo;
		}

		public String getSettleDate() {
			return settleDate;
		}

		public void setSettleDate(String settleDate) {
			this.settleDate = settleDate;
		}
	}
}
