package com.lianbi.mezone.b.ui;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.lianbi.mezone.b.bean.IncomeBean;
import com.lianbi.mezone.b.httpresponse.MyResultCallback;
import com.lianbi.mezone.b.httpresponse.OkHttpsImp;
import com.lianbi.mezone.b.photo.PopupWindowHelper;
import com.xizhi.mezone.b.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.com.hgh.utils.AbDateUtil;
import cn.com.hgh.utils.AbPullHide;
import cn.com.hgh.utils.AbStrUtil;
import cn.com.hgh.utils.AbViewUtil;
import cn.com.hgh.utils.Result;
import cn.com.hgh.view.AbPullToRefreshView;

public class IncomeActivity extends BaseActivity implements OnClickListener {
	private String optType = "00";
	private TextView mainpoplayout_tvlist, mainpoplayout_tvxia, mainpoplayout_tvincome;
	private AbPullToRefreshView act_income_abpulltorefreshview;
	private ListView act_income_listview;
	private ImageView img_income_empty;
	private int page = 1;
	private List<IncomeBean> mDatas = new ArrayList<>();
	private List<IncomeBean> datas = new ArrayList<>();
	private MyAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_income, NOTYPE);
		initView();
		setListener();
		initPickView();
		setAdapter();
		getAmtFlow(true, "00");
	}

	private void setAdapter() {
		adapter = new MyAdapter(this, datas);
		act_income_listview.setAdapter(adapter);
	}

	private void setListener() {
		act_income_abpulltorefreshview.setLoadMoreEnable(true);
		act_income_abpulltorefreshview.setPullRefreshEnable(true);
		act_income_abpulltorefreshview.setOnHeaderRefreshListener(new AbPullToRefreshView.OnHeaderRefreshListener() {

			@Override
			public void onHeaderRefresh(AbPullToRefreshView view) {
				getAmtFlow(true, optType);
			}

		});
		act_income_abpulltorefreshview.setOnFooterLoadListener(new AbPullToRefreshView.OnFooterLoadListener() {

			@Override
			public void onFooterLoad(AbPullToRefreshView view) {
				getAmtFlow(false, optType);
			}
		});
	}

	private void initView() {
		setPageTitle("收入明细");
		setPageRightText("全部");
		act_income_abpulltorefreshview = (AbPullToRefreshView) findViewById(R.id.act_income_abpulltorefreshview);//AbPullToRefreshView
		act_income_listview = (ListView) findViewById(R.id.act_income_listview);//列表\
		img_income_empty = (ImageView) findViewById(R.id.img_income_empty);
	}

	/**
	 * pop
	 */
	PopupWindow pw = null;

	/**
	 * popView
	 */
	View pickView;

	public void pickImage() {
		if (pw == null) {
			pw = PopupWindowHelper.createPopupWindow(pickView, (int) AbViewUtil.dip2px(this, 120), (int) AbViewUtil.dip2px(this, 100));
			pw.setAnimationStyle(R.style.slide_up_in_down_out);
		}
		pw.showAsDropDown(tvTitleRight, 0, (int) AbViewUtil.dip2px(this, 2));

	}

	/**
	 * 初始化pop
	 */
	public void initPickView() {
		pickView = View.inflate(this, R.layout.mainpoplayout, null);
		mainpoplayout_tvlist = (TextView) pickView.findViewById(R.id.mainpoplayout_tvlist);
		mainpoplayout_tvxia = (TextView) pickView.findViewById(R.id.mainpoplayout_tvxia);
		mainpoplayout_tvincome = (TextView) pickView.findViewById(R.id.mainpoplayout_tvincome);

		mainpoplayout_tvlist.setOnClickListener(this);
		mainpoplayout_tvxia.setOnClickListener(this);
		mainpoplayout_tvincome.setOnClickListener(this);
	}

	@Override
	public void onClick(View view) {
		super.onClick(view);
		switch (view.getId()) {
			case R.id.mainpoplayout_tvlist:
				optType = "00";
				setPageRightText("全部");
				getAmtFlow(true, optType);
				pw.dismiss();
				break;
			case R.id.mainpoplayout_tvxia:
				optType = "01";
				setPageRightText("收入");
				getAmtFlow(true, optType);
				pw.dismiss();
				break;
			case R.id.mainpoplayout_tvincome:
				optType = "02";
				setPageRightText("支出");
				getAmtFlow(true, optType);
				pw.dismiss();
				break;
		}
	}

	@Override
	protected void onTitleRightClickTv() {
		super.onTitleRightClickTv();
		pickImage();
	}

	/**
	 * 收入明细
	 */
	private void getAmtFlow(final boolean isResh, String optType) {
		String reqTime = AbDateUtil.getDateTimeNow();
		String uuid = AbStrUtil.getUUID();

		if (isResh) {
			page = 1;
			datas.clear();
			datas.addAll(mDatas);
			if(adapter!=null){
				adapter.notifyDataSetChanged();
			}
		}

		try {
			okHttpsImp.getIsAmtFlow(OkHttpsImp.md5_key, userShopInfoBean.getUserId(), userShopInfoBean.getBusinessId(), optType, uuid, "app", reqTime, page + "", 20 + "", new MyResultCallback<String>() {
				@Override
				public void onResponseResult(Result result) {
					page++;
					String restring = result.getData();
					if (!TextUtils.isEmpty(restring)) {
						try {
							JSONObject jsonobject = new JSONObject(restring);
							restring = jsonobject.getString("xxxxxx");
							ArrayList<IncomeBean> mDatasL = (ArrayList<IncomeBean>) JSON.parseArray(restring, IncomeBean.class);

							if (mDatasL != null && mDatasL.size() > 0) {
								mDatas.addAll(mDatasL);
							}
							if (mDatas != null && mDatas.size() > 0) {
								img_income_empty.setVisibility(View.GONE);
								act_income_abpulltorefreshview.setVisibility(View.VISIBLE);
							} else {
								img_income_empty.setVisibility(View.VISIBLE);
								act_income_abpulltorefreshview.setVisibility(View.GONE);
							}
							AbPullHide.hideRefreshView(isResh, act_income_abpulltorefreshview);
							datas.clear();
							datas.addAll(mDatas);
							adapter.notifyDataSetChanged();

						} catch (JSONException e) {
							e.printStackTrace();
						}
					}else{
						img_income_empty.setVisibility(View.VISIBLE);
						act_income_abpulltorefreshview.setVisibility(View.GONE);
					}
				}

				@Override
				public void onResponseFailed(String msg) {
					if (isResh) {
						img_income_empty.setVisibility(View.VISIBLE);
						act_income_abpulltorefreshview.setVisibility(View.GONE);
					}
					AbPullHide.hideRefreshView(isResh, act_income_abpulltorefreshview);
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * adpter
	 */
	public class MyAdapter extends BaseAdapter {

		List<IncomeBean> list;
		Context mContext;
		private LayoutInflater inflater;
		private final int TYPE1 = 0;
		private final int TYPE2 = 1;

		public MyAdapter(Context context, List<IncomeBean> list) {
			this.list = list;
			this.mContext = context;
			inflater = LayoutInflater.from(mContext);
		}

		@Override
		public int getCount() {
			return list.size();
		}

		@Override
		public Object getItem(int position) {
			return list.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public int getViewTypeCount() {
			return 2;
		}

		@Override
		public int getItemViewType(int position) {
			IncomeBean bean = list.get(position);

			//获取标记 判断当前数据属于哪个数据源
			if (true) {
				return TYPE1;
			} else {
				return TYPE2;
			}

		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			//初始化每个holder
			ViewHolder1 holder1 = null;
			ViewHolder2 holder2 = null;
			int type = getItemViewType(position);

			if (convertView == null) {
				switch (type) {
					case TYPE1:
						convertView = inflater.inflate(R.layout.item_income_1, null, false);
						holder1 = new ViewHolder1();
						holder1.tv_income1_title = (TextView) convertView.findViewById(R.id.tv_income1_title);
						convertView.setTag(holder1);
						break;
					case TYPE2:
						convertView = inflater.inflate(R.layout.item_income_2, null, false);
						holder2 = new ViewHolder2();
						holder2.tv_income2_title = (TextView) convertView.findViewById(R.id.tv_income2_title);
						holder2.tv_income2_time = (TextView) convertView.findViewById(R.id.tv_income2_time);
						holder2.tv_income2_money = (TextView) convertView.findViewById(R.id.tv_income2_money);
						convertView.setTag(holder2);
						break;
				}
			} else {
				switch (type) {
					case TYPE1:
						holder1 = (ViewHolder1) convertView.getTag();
						break;
					case TYPE2:
						holder2 = (ViewHolder2) convertView.getTag();
						break;

				}
			}

			//为布局设置数据
			switch (type) {
				case TYPE1:
					holder1.tv_income1_title.setText("");
					break;
				case TYPE2:
					holder2.tv_income2_title.setText("");
					holder2.tv_income2_time.setText("");
					holder2.tv_income2_money.setText("");
					break;
			}

			return convertView;
		}

		public class ViewHolder1 {
			TextView tv_income1_title;
		}

		public class ViewHolder2 {
			TextView tv_income2_title;
			TextView tv_income2_time;
			TextView tv_income2_money;

		}
	}

}
