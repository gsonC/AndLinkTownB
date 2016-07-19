package com.lianbi.mezone.b.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lianbi.mezone.b.app.Constants;
import com.lianbi.mezone.b.bean.DataObjecte;
import com.lianbi.mezone.b.bean.InfoMessageBean;
import com.lianbi.mezone.b.httpresponse.API;
import com.lianbi.mezone.b.ui.BaseActivity;
import com.lianbi.mezone.b.ui.InfoDetailsActivity;
import com.lianbi.mezone.b.ui.WebActivty;
import com.xizhi.mezone.b.R;

import java.util.ArrayList;

import cn.com.hgh.baseadapter.BaseAdapterHelper;
import cn.com.hgh.baseadapter.QuickAdapter;
import cn.com.hgh.utils.CryptTool;

public class InfoMessageFragment extends Fragment {

	private ListView fm_messagefragment_listView;
	private ImageView fm_messagefragment_iv_empty;
	ArrayList<InfoMessageBean> mDatas = new ArrayList<InfoMessageBean>();
	private ImageView iv_selectall1;
	private TextView tv_seleteall1,tv_toexamine1,tv_deletemessage1;
	private TextView tv_tablename,tv_messageContent,tv_time;
	private RelativeLayout ray_choice;
	/**
	 * 是否删除
	 */
	boolean isDeted;
	boolean isSeleteAll=false;
	private InfoDetailsActivity mActivity;
	@Override
	public View onCreateView(LayoutInflater inflater,
							 @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fm_infomessage, null);
		mActivity = (InfoDetailsActivity) getActivity();
		initView(view);
		initListAdapter();
		listen();

		return view;
	}


	private void initView(View view) {
		iv_selectall1= (ImageView) view.findViewById(R.id.iv_selectall1);
		tv_seleteall1= (TextView) view.findViewById(R.id.tv_seleteall1);
		tv_toexamine1= (TextView) view.findViewById(R.id.tv_toexamine1);
		tv_deletemessage1= (TextView) view.findViewById(R.id.tv_deletemessage1);
		ray_choice= (RelativeLayout) view.findViewById(R.id.ray_choice);
		fm_messagefragment_listView = (ListView) view
				.findViewById(R.id.fm_messagefragment_listView);
		fm_messagefragment_iv_empty = (ImageView) view
				.findViewById(R.id.fm_messagefragment_iv_empty);
	}

	private void listen() {
		ray_choice.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(isSeleteAll){
					isSeleteAll=false;
					iv_selectall1.setBackgroundResource(R.mipmap.message_unchecked);
					tv_seleteall1.setText("全选");
					for(int i=0;i<mDatas.size();i++){
						mDatas.get(i).setS(false);
					}
					mAdapter.replaceAll(mDatas);
				}else{
					isSeleteAll=true;
					iv_selectall1.setBackgroundResource(R.mipmap.message_checked);
					tv_seleteall1.setText("全不选");
					for(int i=0;i<mDatas.size();i++){
						mDatas.get(i).setS(true);
					}
					mAdapter.replaceAll(mDatas);
				}
			}
		});
		tv_deletemessage1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				setExamineAndDelete(true);
			}
		});

		tv_toexamine1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
			//	for(int i=0;i<mDatas.size();i++){
			//		if(!"0".equals(mDatas.get(i).getAuditStatus())){
			//			tv_tablename.setTextColor(Color.RED);
			//			tv_messageContent.setTextColor(Color.RED);
			//			tv_time.setTextColor(Color.RED);

			//		}
			//	}
				setExamineAndDelete(false);
			}
		});
	}

	private void setExamineAndDelete(boolean status1) {
		int s = mDatas.size();
		ArrayList<String> ids = new ArrayList<String>();
		for (int i = 0; i < s; i++) {
			if (mDatas.get(i).isS()) {
				ids.add(mDatas.get(i).getPushId() + "");
			}
		}
		if (status1) {// 删除
			mActivity.delteMsg1(ids, status1);
			iv_selectall1
					.setBackgroundResource(R.mipmap.message_unchecked);
		} else {// 已读
			mActivity.delteMsg1(ids, status1);
			iv_selectall1
					.setBackgroundResource(R.mipmap.message_unchecked);
			tv_seleteall1.setText("全选");
		}
	}
	QuickAdapter<InfoMessageBean> mAdapter;
	private String getUrl() {
		String bussniessId = BaseActivity.userShopInfoBean.getBusinessId();
		//String bussniessId = "BD2016042920160300000028";
		String status = "1";
		String url = API.HOST_WECHAT_MALL;
		DataObjecte data = new DataObjecte();

		data.setOrderStatus(status);
		data.setStoreId(bussniessId);
		data.setFlag("wl");
		data.setSourceType("tss");
		String dataJson = com.alibaba.fastjson.JSONObject.toJSON(data)
				.toString();
		// String url1 = dataJson; url = encryptionUrl(url, dataJson);

		url = encryptionUrl(url, dataJson);


//		String bussniessId = userShopInfoBean.getBusinessId();
//		String url = API.HOST_SUPPLYGOODS_MALL+"storeId="+bussniessId+"&orderStatus=1&sourceType=sws&flag=wl";
		return url;


		// String desStr = CryptTool.getBASE64(dataJson);

//		return url;
	}
	private String encryptionUrl(String url, String dataJson) {
		try {
			// 获得的明文数据
			String desStr = dataJson;
			// 转成字节数组
			byte src_byte[] = desStr.getBytes();

			// MD5摘要
			byte[] md5Str = CryptTool.md5Digest(src_byte);
			// 生成最后的SIGN
			String SING = CryptTool.byteArrayToHexString(md5Str);

			desStr = CryptTool.getBASE64(dataJson);

			return url + "sing=" + SING + "&data=" + desStr;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return "";
	}
	/**
	 * 初始化list Adapter
	 */
	private void initListAdapter() {
		mAdapter = new QuickAdapter<InfoMessageBean>(mActivity,
				R.layout.fm_messageitem, mDatas) {

			protected void convert(final BaseAdapterHelper helper,
								   final InfoMessageBean item) {
				ImageView iv_check1=helper.getView(R.id.iv_check1);
				TextView tv_tablename = helper.getView(R.id.tv_tablename);
				TextView tv_messageContent = helper.getView(R.id.tv_messageContent);
				TextView tv_time = helper.getView(R.id.tv_time);
				RelativeLayout ll_order = helper.getView(R.id.ll_order);
				tv_tablename.setText(item.getTableName());
				tv_messageContent.setText(item.getMsgContent());
				tv_time.setText(item.getCreateTime());
				if("0".equals(item.getIsRead())){
					tv_tablename.setTextColor(0xff18b08a);
					tv_messageContent.setTextColor(0xff18b08a);
					tv_time.setTextColor(0xff18b08a);

					ll_order.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {



							mActivity.changStatus(item.getPushId()+"");
							if(0==item.getMsgType()){
								Intent intent_web=new Intent(getActivity(),WebActivty.class);

								intent_web.putExtra(Constants.NEDDLOGIN, false);
								intent_web.putExtra("NEEDNOTTITLE", false);
								intent_web.putExtra("Re", true);
								intent_web.putExtra(WebActivty.U, getUrl());
								startActivity(intent_web);}
						}
					});

				}else{

					tv_tablename.setTextColor(Color.BLACK);
					tv_messageContent.setTextColor(Color.BLACK);
					tv_time.setTextColor(Color.BLACK);
					ll_order.setClickable(false);
				}if(item.isS()){
					helper.setImageResource(R.id.iv_check1,R.mipmap.message_checked);

				}else{
					helper.setImageResource(R.id.iv_check1,
							R.mipmap.message_unchecked);
				}
				iv_check1.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						if(item.isS()){

							item.setS(false);
							mAdapter.replaceAll(mDatas);
						}else{
							item.setS(true);
							mAdapter.replaceAll(mDatas);
						}
					}
				});

			}
		};
		// 设置适配器
		fm_messagefragment_listView.setAdapter(mAdapter);
	}

	/**
	 *
	 * @param isD 是否删除状态
	 * @param isDel
	 *            是否删除
	 * @param cuArrayList
	 */
	public void doSomething(boolean isD, boolean isDel,
							ArrayList<InfoMessageBean> cuArrayList) {
		isDeted = isD;
		if (cuArrayList != null && cuArrayList.size() > 0) {
			mDatas = cuArrayList;
			mAdapter.replaceAll(mDatas);
			fm_messagefragment_iv_empty.setVisibility(View.GONE);
			fm_messagefragment_listView.setVisibility(View.VISIBLE);
		} else {
			if (mDatas.size() > 0) {
				fm_messagefragment_iv_empty.setVisibility(View.GONE);
				fm_messagefragment_listView.setVisibility(View.VISIBLE);
				mAdapter.replaceAll(mDatas);
			} else {
				fm_messagefragment_iv_empty.setVisibility(View.VISIBLE);
				fm_messagefragment_listView.setVisibility(View.GONE);
			}
		}


	}
}
