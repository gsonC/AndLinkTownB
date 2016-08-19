package com.lianbi.mezone.b.ui;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.lianbi.mezone.b.bean.WeiXinProduct;
import com.xizhi.mezone.b.R;

import java.util.ArrayList;

import cn.com.hgh.baseadapter.BaseAdapterHelper;
import cn.com.hgh.baseadapter.QuickAdapter;
import cn.com.hgh.utils.ScreenUtils;

public class ChooseFromWeixinActivity extends BaseActivity {
	private ListView mAct_addmembers_listview;
	private ArrayList<WeiXinProduct> mDatas = new ArrayList<WeiXinProduct>();
	private  QuickAdapter<WeiXinProduct> mAdapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_choose_from_weixin,NOTYPE);

		initView();
		initAdapter();
		getWeiXinList();
	}
	private void initView(){
		setPageTitle("从微信商城产品库选择");
		mAct_addmembers_listview = (ListView) findViewById(R.id.act_addmembers_listview);//列表
	}

	private void initAdapter() {
		mAdapter = new QuickAdapter<WeiXinProduct>(ChooseFromWeixinActivity.this, R.layout.weixin_shop_list, mDatas) {

			@Override
			protected void convert(BaseAdapterHelper helper, WeiXinProduct item) {

				ImageView new_product_ima =helper.getView(R.id.new_product_ima);
				TextView new_product_food =helper.getView(R.id.new_product_food);
				TextView new_product_rated =helper.getView(R.id.new_product_rated);
				TextView new_product_price =helper.getView(R.id.new_product_price);
				TextView new_product_choose =helper.getView(R.id.new_product_choose);
				ScreenUtils.textAdaptationOn720(new_product_food,ChooseFromWeixinActivity.this,24);
				ScreenUtils.textAdaptationOn720(new_product_rated,ChooseFromWeixinActivity.this,24);
				ScreenUtils.textAdaptationOn720(new_product_price,ChooseFromWeixinActivity.this,24);//
				ScreenUtils.textAdaptationOn720(new_product_choose,ChooseFromWeixinActivity.this,24);//

				new_product_food.setText(item.getNew_product_food()+"");
				new_product_rated.setText(item.getNew_product_rated()+"");
				new_product_price.setText(item.getNew_product_price()+"");
				new_product_choose.setText(item.getNew_product_choose()+"");

			}


		};

		mAct_addmembers_listview.setAdapter(mAdapter);
	}

private void getWeiXinList(){
	ArrayList<WeiXinProduct> mDatasL=new ArrayList<WeiXinProduct>();
	for (int i = 0; i < 20; i++) {
		WeiXinProduct bean = new WeiXinProduct();
		bean.setNew_product_food("dsadasda"+i);
		bean.setNew_product_choose("选择"+i);
		bean.setNew_product_rated("普通"+i);
		bean.setNew_product_price("微店"+i);

		mDatasL.add(bean);
	}
	if (mDatasL.size() > 0) {
		mDatas.addAll(mDatasL);
	}
	if (mDatasL.size() > 0) {
		mDatas.addAll(mDatasL);
	}
	mAdapter.replaceAll(mDatas);
}


}
