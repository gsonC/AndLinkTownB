package cn.com.hgh.view;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.lianbi.mezone.b.bean.CityModel;
import com.lianbi.mezone.b.bean.DistrictModel;
import com.lianbi.mezone.b.bean.ProvinceModel;
import com.xizhi.mezone.b.R;
import com.zbar.lib.addresspop.OnWheelChangedListener;
import com.zbar.lib.addresspop.WheelView;
import com.zbar.lib.addresspop.adapter.ArrayWheelAdapter;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import cn.com.hgh.service.XmlParserHandler;


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
	// key - 省 value - 市
	public Map<String, String[]> mCitisDatasMap = new HashMap<String, String[]>();
	// key - 市 values - 区
	public Map<String, String[]> mDistrictDatasMap = new HashMap<String, String[]>();
	// key - 区 values - 邮编
	public Map<String, String> mZipcodeDatasMap = new HashMap<String, String>();
	// 当前省的名称
	public String mCurrentProviceName;
	// 当前市的名称
	public String mCurrentCityName;
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
			mCurrentZipCode = mZipcodeDatasMap.get(mCurrentDistrictName);
		}
	}

	/**
	 * 根据当前的省，更新市WheelView的信息
	 */
	private void updateCities() {
		int pCurrent = mViewProvince.getCurrentItem();
		mCurrentProviceName = mProvinceDatas[pCurrent];
		String[] cities = mCitisDatasMap.get(mCurrentProviceName);
		if (cities == null) {
			cities = new String[] { "" };
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
		String[] areas = mDistrictDatasMap.get(mCurrentCityName);
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
			// */ 初始化默认选中的省、市、区
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
			// */
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
	}
}
