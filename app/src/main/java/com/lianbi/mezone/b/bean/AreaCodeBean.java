package com.lianbi.mezone.b.bean;

import java.io.Serializable;
import java.util.List;

/*
 * @创建者     master
 * @创建时间   2016/11/10 15:58
 * @描述       ${TODO}
 *
 * @更新者     $Author$ 
 * @更新时间   $Date$
 * @更新描述   ${TODO}
 */
public class AreaCodeBean implements Serializable {
	private static final long serialVersionUID = 1735304288275161421L;

	/**
	 * provinceCode : 110000
	 * provinceName : 北京市
	 * region : [{"cityCode":"110100","cityName":"北京市","state":[{"district":"东城区","districtId":"110101"},{"district":"西城区","districtId":"110102"},{"district":"崇文区","districtId":"110103"},{"district":"宣武区","districtId":"110104"},{"district":"朝阳区","districtId":"110105"},{"district":"丰台区","districtId":"110106"},{"district":"石景山区","districtId":"110107"},{"district":"海淀区","districtId":"110108"},{"district":"门头沟区","districtId":"110109"},{"district":"房山区","districtId":"110111"},{"district":"通州区","districtId":"110112"},{"district":"顺义区","districtId":"110113"},{"district":"昌平区","districtId":"110114"},{"district":"大兴区","districtId":"110115"},{"district":"怀柔区","districtId":"110116"},{"district":"平谷区","districtId":"110117"}]}]
	 */

	private String provinceCode;
	private String provinceName;
	/**
	 * cityCode : 110100
	 * cityName : 北京市
	 * state : [{"district":"东城区","districtId":"110101"},{"district":"西城区","districtId":"110102"},{"district":"崇文区","districtId":"110103"},{"district":"宣武区","districtId":"110104"},{"district":"朝阳区","districtId":"110105"},{"district":"丰台区","districtId":"110106"},{"district":"石景山区","districtId":"110107"},{"district":"海淀区","districtId":"110108"},{"district":"门头沟区","districtId":"110109"},{"district":"房山区","districtId":"110111"},{"district":"通州区","districtId":"110112"},{"district":"顺义区","districtId":"110113"},{"district":"昌平区","districtId":"110114"},{"district":"大兴区","districtId":"110115"},{"district":"怀柔区","districtId":"110116"},{"district":"平谷区","districtId":"110117"}]
	 */

	private List<RegionBean> region;

	public String getProvinceCode() {
		return provinceCode;
	}

	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}

	public String getProvinceName() {
		return provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	public List<RegionBean> getRegion() {
		return region;
	}

	public void setRegion(List<RegionBean> region) {
		this.region = region;
	}

	public static class RegionBean {
		private String cityCode;
		private String cityName;
		/**
		 * district : 东城区
		 * districtId : 110101
		 */

		private List<StateBean> state;

		public String getCityCode() {
			return cityCode;
		}

		public void setCityCode(String cityCode) {
			this.cityCode = cityCode;
		}

		public String getCityName() {
			return cityName;
		}

		public void setCityName(String cityName) {
			this.cityName = cityName;
		}

		public List<StateBean> getState() {
			return state;
		}

		public void setState(List<StateBean> state) {
			this.state = state;
		}

		public static class StateBean {
			private String district;
			private String districtId;

			public String getDistrict() {
				return district;
			}

			public void setDistrict(String district) {
				this.district = district;
			}

			public String getDistrictId() {
				return districtId;
			}

			public void setDistrictId(String districtId) {
				this.districtId = districtId;
			}
		}
	}
}
