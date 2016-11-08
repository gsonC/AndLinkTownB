package com.lianbi.mezone.b.bean;

import java.io.Serializable;
import java.util.List;

/*
 * @创建者     master
 * @创建时间   2016/11/8 11:33
 * @描述       首页会员营销Bean
 *
 * @更新者     $Author$ 
 * @更新时间   $Date$
 * @更新描述
 */
public class ShopVipMarket implements Serializable {
	private static final long serialVersionUID = 7445937862494734976L;

	private GetConsumptionVipMaxResponseModelBean getConsumptionVipMaxResponseModel;
	/**
	 * weekMaxAmt : 2000
	 * dayMaxAmt : 2000
	 */

	private WeekAndDayMaxAmtResponseModelBean weekAndDayMaxAmtResponseModel;
	/**
	 * dataSize : 2
	 * newVipCount : 0
	 */

	private VipWXCountResponseModelBean vipWXCountResponseModel;

	public GetConsumptionVipMaxResponseModelBean getGetConsumptionVipMaxResponseModel() {
		return getConsumptionVipMaxResponseModel;
	}

	public void setGetConsumptionVipMaxResponseModel(GetConsumptionVipMaxResponseModelBean getConsumptionVipMaxResponseModel) {
		this.getConsumptionVipMaxResponseModel = getConsumptionVipMaxResponseModel;
	}

	public WeekAndDayMaxAmtResponseModelBean getWeekAndDayMaxAmtResponseModel() {
		return weekAndDayMaxAmtResponseModel;
	}

	public void setWeekAndDayMaxAmtResponseModel(WeekAndDayMaxAmtResponseModelBean weekAndDayMaxAmtResponseModel) {
		this.weekAndDayMaxAmtResponseModel = weekAndDayMaxAmtResponseModel;
	}

	public VipWXCountResponseModelBean getVipWXCountResponseModel() {
		return vipWXCountResponseModel;
	}

	public void setVipWXCountResponseModel(VipWXCountResponseModelBean vipWXCountResponseModel) {
		this.vipWXCountResponseModel = vipWXCountResponseModel;
	}

	public static class GetConsumptionVipMaxResponseModelBean {
		/**
		 * consumptionCount : 3
		 * photo : 头像1
		 * nickName : 皮卡丘1
		 */

		private List<VipListBean> vipList;

		public List<VipListBean> getVipList() {
			return vipList;
		}

		public void setVipList(List<VipListBean> vipList) {
			this.vipList = vipList;
		}

		public static class VipListBean {
			private String consumptionCount;
			private String photo;
			private String nickName;

			public String getConsumptionCount() {
				return consumptionCount;
			}

			public void setConsumptionCount(String consumptionCount) {
				this.consumptionCount = consumptionCount;
			}

			public String getPhoto() {
				return photo;
			}

			public void setPhoto(String photo) {
				this.photo = photo;
			}

			public String getNickName() {
				return nickName;
			}

			public void setNickName(String nickName) {
				this.nickName = nickName;
			}
		}
	}

	public static class WeekAndDayMaxAmtResponseModelBean {
		private String weekMaxAmt;
		private String dayMaxAmt;

		public String getWeekMaxAmt() {
			return weekMaxAmt;
		}

		public void setWeekMaxAmt(String weekMaxAmt) {
			this.weekMaxAmt = weekMaxAmt;
		}

		public String getDayMaxAmt() {
			return dayMaxAmt;
		}

		public void setDayMaxAmt(String dayMaxAmt) {
			this.dayMaxAmt = dayMaxAmt;
		}
	}

	public static class VipWXCountResponseModelBean {
		private int dataSize;
		private int newVipCount;

		public int getDataSize() {
			return dataSize;
		}

		public void setDataSize(int dataSize) {
			this.dataSize = dataSize;
		}

		public int getNewVipCount() {
			return newVipCount;
		}

		public void setNewVipCount(int newVipCount) {
			this.newVipCount = newVipCount;
		}
	}
}
