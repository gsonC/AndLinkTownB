

package cn.com.hgh.view;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.lianbi.mezone.b.bean.CitiesBean;
import com.lianbi.mezone.b.bean.DistrictsBean;
import com.lianbi.mezone.b.bean.ProvincesBean;
import com.xizhi.mezone.b.R;
import com.zbar.lib.addresspop.OnWheelChangedListener;
import com.zbar.lib.addresspop.WheelView;
import com.zbar.lib.addresspop.adapter.ArrayWheelAdapter;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;


/*
 * @创建者     master
 * @创建时间   2016/10/28 13:57
 * @描述       省市厅三级联动
 *
 * @更新者     $Author$
 * @更新时间   $Date$
 * @更新描述
 */
@SuppressLint("ViewConstructor")
public class AddressPopView extends PopupWindow implements OnWheelChangedListener{

	public View mMenuView;
	public TextView mTvGuanbi, mTvWancheng;
	public WheelView mViewProvince, mViewCity, mViewDistrict;
	public OnClickListener mItemsOnClick;
	public Context mContext;
	// 所有省
	public String[] mProvinceDatas;
	// 所有省Code
	public String[] mProvinceCodeDatas;

	// key - 省 value - 市
	public Map<String, String[]> mCitisDatasMap = new HashMap<String, String[]>();
	// key - 省 value - 市Code
	public Map<String, String[]> mCitisCodeDatasMap = new HashMap<String, String[]>();

	// key - 市 values - 区
	public Map<String, String[]> mDistrictDatasMap = new HashMap<String, String[]>();
	// key - 市 values - 区Code
	public Map<String, String[]> mDistrictCodeDatasMap = new HashMap<String, String[]>();

	// key - 区 values - 邮编
	public Map<String, String> mZipcodeDatasMap = new HashMap<String, String>();
	// key - 区 values - 邮编Code
	public Map<String, String> mZipcodeCodeDatasMap = new IdentityHashMap<String, String>();

	// 当前省的名称
	public String mCurrentProviceName;
	// 当前省的Code
	public String mCurrentProviceCode;

	// 当前市的名称
	public String mCurrentCityName;
	// 当前市的名称
	public String mCurrentCityCode;

	// 当前区的名称
	public String mCurrentDistrictName = "";
	// 当前区的邮政编码
	public String mCurrentZipCode = "";

	public AddressPopView(Activity context,
						  OnClickListener itemsOnClick) {
		super(context);
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mContext = context;
		mItemsOnClick = itemsOnClick;
		mMenuView = inflater.inflate(R.layout.alert_newaddress_dialog, null);

		initView();

		initSetListener();

		setUpData();

		initAnimation();

	}

	private void setUpData() {
		initProvinceDatas();
		mViewProvince.setViewAdapter(new ArrayWheelAdapter<String>(mContext,
				mProvinceDatas));
		// 设置可见条目数量
		mViewProvince.setVisibleItems(7);
		mViewCity.setVisibleItems(7);
		mViewDistrict.setVisibleItems(7);
		updateCities();
		updateAreas();
	}

	/**
	 * 初始化View
	 */
	private void initView() {
		mTvGuanbi = (TextView) mMenuView.findViewById(R.id.tv_guanbi);
		mTvWancheng = (TextView) mMenuView.findViewById(R.id.tv_wancheng);
		mViewProvince = (WheelView) mMenuView.findViewById(R.id.id_province);
		mViewCity = (WheelView) mMenuView.findViewById(R.id.id_city);
		mViewDistrict = (WheelView) mMenuView.findViewById(R.id.id_district);
	}

	/**
	 * 初始化Listener
	 */
	private void initSetListener() {
		mTvGuanbi.setOnClickListener(mItemsOnClick);
		mTvWancheng.setOnClickListener(mItemsOnClick);
		mViewProvince.addChangingListener(this);
		mViewCity.addChangingListener(this);
		mViewDistrict.addChangingListener(this);
	}

	/**
	 * 初始化Animation
	 */
	private void initAnimation() {
		// 设置SelectPicPopupWindow的View
		this.setContentView(mMenuView);
		// 设置SelectPicPopupWindow弹出窗体的宽
		this.setWidth(LayoutParams.FILL_PARENT);
		// 设置SelectPicPopupWindow弹出窗体的高
		this.setHeight(LayoutParams.WRAP_CONTENT);
		// 设置SelectPicPopupWindow弹出动画
		this.setAnimationStyle(R.style.slide_up_in_down_out);
		// 设置SelectPicPopupWindow弹出窗体可点击
		this.setFocusable(true);
		// 实例化一个ColorDrawable颜色为半透明
		ColorDrawable dw = new ColorDrawable(0xb0000000);
		// 设置SelectPicPopupWindow弹出窗体的背景
		this.setBackgroundDrawable(dw);
		// mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
		mMenuView.setOnTouchListener(new OnTouchListener() {

			public boolean onTouch(View v, MotionEvent event) {

				int height = mMenuView.findViewById(R.id.pop_layout).getTop();
				int y = (int) event.getY();
				if (event.getAction() == MotionEvent.ACTION_UP) {
					if (y < height) {
						dismiss();
					}
				}
				return true;
			}
		});

	}

	@Override
	public void onChanged(WheelView wheel, int oldValue, int newValue) {
		if (wheel == mViewProvince) {
			updateCities();
		} else if (wheel == mViewCity) {
			updateAreas();
		} else if (wheel == mViewDistrict) {
			mCurrentDistrictName = mDistrictDatasMap.get(mCurrentCityName)[newValue];
			//mCurrentZipCode = mZipcodeDatasMap.get(mCurrentDistrictName);
			mCurrentZipCode = mZipcodeCodeDatasMap.get(mCurrentDistrictName);
		}
	}

	/**
	 * 根据当前的省，更新市WheelView的信息
	 */
	private void updateCities() {
		int pCurrent = mViewProvince.getCurrentItem();
		mCurrentProviceName = mProvinceDatas[pCurrent];
		mCurrentProviceCode = mProvinceCodeDatas[pCurrent];
		String[] cities = mCitisDatasMap.get(mCurrentProviceName);
		String[] citiesCode = mCitisCodeDatasMap.get(mCurrentProviceName);

		if (cities == null||citiesCode ==null) {
			cities = new String[] { "" };
			citiesCode = new String[]{""};
		}
		mViewCity
				.setViewAdapter(new ArrayWheelAdapter<String>(mContext, cities));
		mViewCity.setCurrentItem(0);
		updateAreas();
	}

	/**
	 * 根据当前的市，更新区WheelView的信息
	 */
	private void updateAreas() {
		int pCurrent = mViewCity.getCurrentItem();
		mCurrentCityName = mCitisDatasMap.get(mCurrentProviceName)[pCurrent];
		mCurrentCityCode = mCitisCodeDatasMap.get(mCurrentProviceName)[pCurrent];
		String[] areas = mDistrictDatasMap.get(mCurrentCityName);
		String[] areasCode = mDistrictCodeDatasMap.get(mCurrentCityCode);
		if (areas == null) {
			areas = new String[] { "" };
		}
		mViewDistrict.setViewAdapter(new ArrayWheelAdapter<String>(mContext,
				areas));
		mViewDistrict.setCurrentItem(0);
	}

	/**
	 * 解析省市区的XML数据
	 */
	protected void initProvinceDatas() {

		List<ProvincesBean> provinceList = null;
		//AssetManager asset = mContext.getAssets();
		try {
			//InputStream input = asset.open("province_data.xml");
			// 创建一个解析xml的工厂对象
			//SAXParserFactory spf = SAXParserFactory.newInstance();
			// 解析xml
			//SAXParser parser = spf.newSAXParser();
			//XmlParserHandler handler = new XmlParserHandler();
			//parser.parse(input, handler);
			//input.close();
			// 获取解析出来的数据
			//provinceList = handler.getDataList();

			//File jsonFile = new File("sdcard/download/json.json");
			BufferedReader br = null;

			//if(jsonFile.exists()&&jsonFile.length()>0){
			//	InputStream is = new FileInputStream(jsonFile);
			//	br = new BufferedReader(new InputStreamReader(is,"GB2312"));
			//}else{
				InputStream other = mContext.getResources().getAssets().open("json.json");
				br = new BufferedReader(new InputStreamReader(other));
			//}
			//InputStream other = mContext.getResources().getAssets().open("json.json");
			//InputStream is = new FileInputStream(jsonFile);
			//BufferedReader br = new BufferedReader(new InputStreamReader(is,"GB2312"));
			StringBuffer stringBuffer = new StringBuffer();
			String str = null;
			while ((str = br.readLine()) != null) {
				stringBuffer.append(str);
			}
			JSONObject jsonObject = new JSONObject(stringBuffer.toString());
			String citylist = (String) jsonObject
					.getString("window.LocalList");
			provinceList = (ArrayList<ProvincesBean>) JSON.parseArray(citylist, ProvincesBean.class);
			provinceList.remove(provinceList.size()-1);
			provinceList.remove(provinceList.size()-1);
			provinceList.remove(provinceList.size()-1);
			System.out.println("--------"+provinceList.size());
			// */ 初始化默认选中的省、市、区
			if (provinceList != null && !provinceList.isEmpty()) {
				//mCurrentProviceName = provinceList.get(0).getName();
				mCurrentProviceName = provinceList.get(0).getProvinceName();
				mCurrentProviceCode = provinceList.get(0).getProvinceCode();
				//List<CityModel> cityList = provinceList.get(0).getCityList();
				List<CitiesBean> cityList = provinceList.get(0).getRegion();
				if (cityList != null && !cityList.isEmpty()) {
					//mCurrentCityName = cityList.get(0).getName();
					mCurrentCityName = cityList.get(0).getCityName();
					mCurrentCityCode = cityList.get(0).getCityCode();
					//List<DistrictModel> districtList = cityList.get(0)
					//		.getDistrictList();
					List<DistrictsBean> districtList = cityList.get(0)
							.getState();
					//mCurrentDistrictName = districtList.get(0).getName();
					mCurrentDistrictName = districtList.get(0).getDistrict();
					//mCurrentZipCode = districtList.get(0).getZipcode();
					mCurrentZipCode = districtList.get(0).getDistrictId();
				}
			}
			// */
			mProvinceDatas = new String[provinceList.size()];
			mProvinceCodeDatas = new String[provinceList.size()];
			for (int i = 0; i < provinceList.size(); i++) {
				// 遍历所有省的数据
				//mProvinceDatas[i] = provinceList.get(i).getName();
				mProvinceDatas[i] = provinceList.get(i).getProvinceName();
				mProvinceCodeDatas[i] = provinceList.get(i).getProvinceCode();
				//List<CityModel> cityList = provinceList.get(i).getCityList();
				List<CitiesBean> cityList = provinceList.get(i).getRegion();
				String[] cityNames = new String[cityList.size()];
				String[] cityCodes = new String[cityList.size()];
				for (int j = 0; j < cityList.size(); j++) {
					// 遍历省下面的所有市的数据
					//cityNames[j] = cityList.get(j).getName();
					cityNames[j] = cityList.get(j).getCityName();
					cityCodes[j] = cityList.get(j).getCityCode();
					//List<DistrictModel> districtList = cityList.get(j)
					//		.getDistrictList();
					List<DistrictsBean> districtList = cityList.get(j)
							.getState();
					String[] distrinctNameArray = new String[districtList
							.size()];
					String[] distrinctCodeArray = new String[districtList
							.size()];
					DistrictsBean[] distrinctArray = new DistrictsBean[districtList
							.size()];

					for (int k = 0; k < districtList.size(); k++) {
						// 遍历市下面所有区/县的数据
						//DistrictModel districtModel = new DistrictModel(
						//		districtList.get(k).getName(), districtList
						//		.get(k).getZipcode());
						DistrictsBean districtModel = new DistrictsBean(
								districtList.get(k).getDistrict(),districtList
						.get(k).getDistrictId());

						// 区/县对于的邮编，保存到mZipcodeDatasMap
						//mZipcodeDatasMap.put(districtList.get(k).getName(),
						//		districtList.get(k).getZipcode());
						mZipcodeDatasMap.put(districtList.get(k).getDistrict(),
								districtList.get(k).getDistrictId());
						mZipcodeCodeDatasMap.put(districtList.get(k).getDistrict(),
								districtList.get(k).getDistrictId());
						distrinctArray[k] = districtModel;

						//distrinctNameArray[k] = districtModel.getName();
						distrinctNameArray[k] = districtModel.getDistrict();
						distrinctCodeArray[k] = districtModel.getDistrictId();
					}
					// 市-区/县的数据，保存到mDistrictDatasMap
					mDistrictDatasMap.put(cityNames[j], distrinctNameArray);
					mDistrictCodeDatasMap.put(cityNames[j],distrinctCodeArray);
				}
				// 省-市的数据，保存到mCitisDatasMap
				//mCitisDatasMap.put(provinceList.get(i).getName(), cityNames);
				mCitisDatasMap.put(provinceList.get(i).getProvinceName(),cityNames);
				mCitisCodeDatasMap.put(provinceList.get(i).getProvinceName(),cityCodes);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

		}

/*
		List<ProvinceModel> provinceList = null;
		AssetManager asset = mContext.getAssets();
		try {
			InputStream input = asset.open("province_data.xml");
			// 创建一个解析xml的工厂对象
			SAXParserFactory spf = SAXParserFactory.newInstance();
			// 解析xml
			SAXParser parser = spf.newSAXParser();
			XmlParserHandler handler = new XmlParserHandler();
			parser.parse(input, handler);
			input.close();
			// 获取解析出来的数据
			provinceList = handler.getDataList();
			// *//* 初始化默认选中的省、市、区
			if (provinceList != null && !provinceList.isEmpty()) {
				mCurrentProviceName = provinceList.get(0).getName();
				List<CityModel> cityList = provinceList.get(0).getCityList();
				if (cityList != null && !cityList.isEmpty()) {
					mCurrentCityName = cityList.get(0).getName();
					List<DistrictModel> districtList = cityList.get(0)
							.getDistrictList();
					mCurrentDistrictName = districtList.get(0).getName();
					mCurrentZipCode = districtList.get(0).getZipcode();
				}
			}
			// *//*
			mProvinceDatas = new String[provinceList.size()];
			for (int i = 0; i < provinceList.size(); i++) {
				// 遍历所有省的数据
				mProvinceDatas[i] = provinceList.get(i).getName();
				List<CityModel> cityList = provinceList.get(i).getCityList();
				String[] cityNames = new String[cityList.size()];
				for (int j = 0; j < cityList.size(); j++) {
					// 遍历省下面的所有市的数据
					cityNames[j] = cityList.get(j).getName();
					List<DistrictModel> districtList = cityList.get(j)
							.getDistrictList();
					String[] distrinctNameArray = new String[districtList
							.size()];
					DistrictModel[] distrinctArray = new DistrictModel[districtList
							.size()];
					for (int k = 0; k < districtList.size(); k++) {
						// 遍历市下面所有区/县的数据
						DistrictModel districtModel = new DistrictModel(
								districtList.get(k).getName(), districtList
								.get(k).getZipcode());
						// 区/县对于的邮编，保存到mZipcodeDatasMap
						mZipcodeDatasMap.put(districtList.get(k).getName(),
								districtList.get(k).getZipcode());
						distrinctArray[k] = districtModel;
						distrinctNameArray[k] = districtModel.getName();
					}
					// 市-区/县的数据，保存到mDistrictDatasMap
					mDistrictDatasMap.put(cityNames[j], distrinctNameArray);
				}
				// 省-市的数据，保存到mCitisDatasMap
				mCitisDatasMap.put(provinceList.get(i).getName(), cityNames);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

		}
		*/
	}
}

