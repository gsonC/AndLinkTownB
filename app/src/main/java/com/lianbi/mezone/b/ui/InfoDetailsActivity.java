package com.lianbi.mezone.b.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.lianbi.mezone.b.bean.InfoMessageBean;
import com.lianbi.mezone.b.fragment.InfoMessageFragment;
import com.lianbi.mezone.b.fragment.LeaveMessageFragment;
import com.lianbi.mezone.b.httpresponse.MyResultCallback;
import com.xizhi.mezone.b.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cn.com.hgh.utils.ContentUtils;
import cn.com.hgh.utils.Result;
import cn.com.hgh.view.PagerSlidingTabStrip;

public class InfoDetailsActivity extends BaseActivity {
	private ViewPager pager;

	private PagerSlidingTabStrip tabs;
	private LinearLayout ll_top_tabs;
	private RelativeLayout ray_choice;

	private TextView tv_toexamine;
	private TextView tv_deletemessage;
	private TextView tv_seleteall;
	private ImageView iv_selectall;
	private boolean isSeleteAll = false;
	public static final int POSITION0 = 0;
	public static final int POSITION1 = 1;
	public static final int POSITION2 = 2;
	public static final int POSITION3 = 3;
	public static final int READ  = 100;//代表前三个界面
	public static final int LEAVINGMESSAGE  = 101;//代表留言界面
	final String[] titles = { "待接单", "服务", "买单", "留言" };
	InfoMessageFragment allMessageFragment;
	InfoMessageFragment sMessageFragment;
	InfoMessageFragment orderMessageFragment;
	LeaveMessageFragment leavemessagefragment;
	/**
	 * 数据列表
	 */
	ArrayList<InfoMessageBean> arrayList = new ArrayList<InfoMessageBean>();
	ArrayList<InfoMessageBean> arrayList0 = new ArrayList<InfoMessageBean>();
	ArrayList<InfoMessageBean> arrayList1 = new ArrayList<InfoMessageBean>();
	ArrayList<InfoMessageBean> arrayList2 = new ArrayList<InfoMessageBean>();

	ArrayList<InfoMessageBean> currentList = new ArrayList<InfoMessageBean>();

	/**
	 * 当前位置
	 */
	ArrayList<InfoMessageBean> mDatas = new ArrayList<InfoMessageBean>();

	public int curPosition = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_infodetails, NOTYPE);
		initView();
		listen();
		getJumpInfo();
		getPushMessages();
		bottomlisten();
	}
	public void bottomlisten(){
		ray_choice.setOnClickListener(this);
		tv_toexamine.setOnClickListener(this);
		tv_deletemessage.setOnClickListener(this);
	}
	public void  seleteallState(){
		if (isSeleteAll) {
			isSeleteAll = false;
			iv_selectall
					.setBackgroundResource(R.mipmap.message_unchecked);
			tv_seleteall.setText("全选");

		} else {
			isSeleteAll = true;
			iv_selectall
					.setBackgroundResource(R.mipmap.message_checked);
			tv_seleteall.setText("全不选");
		}
	}
	private void whichFragment(int arg0,boolean  isSeleteAll
						 ) {
		switch (arg0) {
			case POSITION0:
				if (allMessageFragment != null) {
					allMessageFragment.upDateFragment(isSeleteAll);
				}

				break;
			case POSITION1:

				if (sMessageFragment != null) {
					sMessageFragment.upDateFragment(isSeleteAll);;
				}
				break;
			case POSITION2:
				if (orderMessageFragment != null) {
					orderMessageFragment.upDateFragment(isSeleteAll);;
				}
				break;
			case POSITION3:
				if (leavemessagefragment != null) {
					leavemessagefragment.upDateFragment(isSeleteAll);;
				}
				break;
		}
	}
	@Override
	protected void onChildClick(View view) {
		super.onChildClick(view);
		switch (view.getId()) {
			case R.id.ray_choice:
				seleteallState();
				whichFragment(curPosition,isSeleteAll);
			break;
			case R.id.tv_toexamine:
				if(curPosition==POSITION3){
	//				leavemessagefragment.afterToexamine();
//					currentList.clear();
//					currentList=leavemessagefragment.getLeaveMessage();
					setExamineAndDelete(false,currentList,LEAVINGMESSAGE);
				}else{
					setExamineAndDelete(false,currentList,READ);
				}

			break;
			case R.id.tv_deletemessage:
				if(curPosition==POSITION3){
//					currentList.clear();
//					currentList=leavemessagefragment.getLeaveMessage();
					setExamineAndDelete(true,currentList,LEAVINGMESSAGE);
				}else{
					setExamineAndDelete(true,currentList,READ);
				}

			break;
		}
	}

	private void getJumpInfo() {
		String jumpinfo = getIntent().getStringExtra("TIAOZHUANXIAOXI");
		if ("fuwu".equals(jumpinfo)) {
			pager.setCurrentItem(1);
			getPushMessages1();
		} else if ("maidan".equals(jumpinfo)) {
			pager.setCurrentItem(2);
			getPushMessages2();
		} else {

		}
	}

	private void initView() {
		setPageTitle(getString(R.string.activity_infodetails_title));
		iv_selectall= (ImageView) findViewById(R.id.iv_selectall);
		tv_seleteall= (TextView) findViewById(R.id.tv_seleteall);
		ray_choice= (RelativeLayout) findViewById(R.id.ray_choice);
		tv_toexamine= (TextView) findViewById(R.id.tv_toexamine);
		tv_deletemessage= (TextView) findViewById(R.id.tv_deletemessage);
		ll_top_tabs = (LinearLayout) findViewById(R.id.ll_top_tabs);
		tabs = (PagerSlidingTabStrip) findViewById(R.id.ps_tabs_act_infodetails);
		pager = (ViewPager) findViewById(R.id.pager_act_infodetails);
		pager.setAdapter(new MyAdapter(getSupportFragmentManager(), titles));
		tabs.setViewPager(pager);
	}

	boolean isDeteled = false;
	String position;

	private void initTabs(ArrayList<InfoMessageBean> currentList, String  showtext){
       this.currentList=currentList;
	   tv_toexamine.setText(showtext);
	    iv_selectall
				.setBackgroundResource(R.mipmap.message_unchecked);
		tv_seleteall.setText("全选");
	}
	private void listen() {
		tabs.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				curPosition = arg0;
				position = String.valueOf(arg0);
				switch (arg0) {
					case 0:
						initTabs(arrayList,"已读");
						swtFmDo(arg0, isDeteled, false, arrayList);
						getPushMessages();
						break;
					case 1:
						initTabs(arrayList0,"已读");
						swtFmDo(arg0, isDeteled, false, arrayList0);
						getPushMessages2();
						break;
					case 2:
						initTabs(arrayList1,"已读");
						swtFmDo(arg0, isDeteled, false, arrayList1);
						getPushMessages1();

						break;
					case 3:
						initTabs(mDatas,"审核");
						swtFmDo(arg0, isDeteled, false, mDatas);
						getShowMessages();

						break;
				}
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {

			}
		});
	}
	
	private void setExamineAndDelete(boolean status,ArrayList<InfoMessageBean>  currentList,
       int type
	) {
		int s = currentList.size();
		ArrayList<String> ids = new ArrayList<String>();
		switch (type){
			case READ:
				for (int i = 0; i < s; i++) {
					if (currentList.get(i).isS()) {
						ids.add(currentList.get(i).getPushId() + "");
					}
				}
				break;
			case LEAVINGMESSAGE:
				for (int i = 0; i < s; i++) {
			    if (currentList.get(i).isS()) {
				ids.add(currentList.get(i).getId() + "");
			    }
		        }
				break;
		}

		switch (type){
			case READ:
			delteAboutOrderMsg(ids, status);
			break;
			case LEAVINGMESSAGE:
			delteLeaveMsg(ids, status);
			break;
		}
		iv_selectall
				.setBackgroundResource(R.mipmap.message_unchecked);
		tv_seleteall.setText("全选");
	}
	private void swtFmDo(int arg0, boolean isD, boolean isDel,
						 ArrayList<InfoMessageBean> cuArrayList) {
		switch (arg0) {
			case POSITION0:
				if (allMessageFragment != null) {
					allMessageFragment.doSomething(isD, isDel, cuArrayList);
				}

				break;
			case POSITION1:

				if (sMessageFragment != null) {
					sMessageFragment.doSomething(isD, isDel, cuArrayList);
				}
				break;
			case POSITION2:
				if (orderMessageFragment != null) {
					orderMessageFragment.doSomething(isD, isDel, cuArrayList);
				}
				break;
			case POSITION3:
				if (leavemessagefragment != null) {
					leavemessagefragment.doSomething(isD, isDel, mDatas);
				}
				break;
		}
	}

	/**
	 * 留言
	 */
	private void getShowMessages() {
		okHttpsImp.getShowMessages(new MyResultCallback<String>() {

			@Override
			public void onResponseResult(Result result) {
				String reString = result.getData();

				if (reString != null) {
					JSONObject jsonObject;
					try {
						jsonObject = new JSONObject(reString);
						String listData = jsonObject.getString("msgList");
						// 清除数据
						mDatas.clear();
						ArrayList<InfoMessageBean> infoMessage = (ArrayList<InfoMessageBean>) JSON
								.parseArray(listData, InfoMessageBean.class);

						mDatas.addAll(infoMessage);
						leavemessagefragment.doSomething(false, false, mDatas);

					} catch (Exception e) {

						e.printStackTrace();
					}

				}

			}

			@Override
			public void onResponseFailed(String msg) {

			}
		}, userShopInfoBean.getBusinessId());
		{

		}

	}

	/**
	 * 待接单
	 */
	private void  getPushMessages() {
		okHttpsImp.getPushMessages(new MyResultCallback<String>() {

			@Override
			public void onResponseResult(Result result) {
				String reString = result.getData();
				if (reString != null) {
					JSONObject jsonObject;
					try {
						jsonObject = new JSONObject(reString);
						reString = jsonObject.getString("msgList");
						arrayList.clear();
						ArrayList<InfoMessageBean> pullMessage = (ArrayList<InfoMessageBean>) JSON
								.parseArray(reString, InfoMessageBean.class);

						arrayList.addAll(pullMessage);
						swtFmDo(POSITION0, false, false, arrayList);

					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			}

			@Override
			public void onResponseFailed(String msg) {

			}
		}, userShopInfoBean.getBusinessId(), "0");

	}

	/**
	 * 服务
	 */
	private void getPushMessages2() {
		okHttpsImp.getPushMessages(new MyResultCallback<String>() {

			@Override
			public void onResponseResult(Result result) {
				String reString = result.getData();
				if (reString != null) {
					JSONObject jsonObject;
					try {
						jsonObject = new JSONObject(reString);
						reString = jsonObject.getString("msgList");
						arrayList0.clear();
						ArrayList<InfoMessageBean> pullMessage = (ArrayList<InfoMessageBean>) JSON
								.parseArray(reString, InfoMessageBean.class);
						arrayList0.addAll(pullMessage);

						swtFmDo(POSITION1, false, false, arrayList0);

					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			}

			@Override
			public void onResponseFailed(String msg) {

			}
		}, userShopInfoBean.getBusinessId(), "2");

	}

	/**
	 * 买单
	 */
	private void getPushMessages1() {
		okHttpsImp.getPushMessages(new MyResultCallback<String>() {

			@Override
			public void onResponseResult(Result result) {
				String reString = result.getData();
				if (reString != null) {
					JSONObject jsonObject;
					try {
						jsonObject = new JSONObject(reString);
						reString = jsonObject.getString("msgList");
						arrayList1.clear();
						ArrayList<InfoMessageBean> pullMessage = (ArrayList<InfoMessageBean>) JSON
								.parseArray(reString, InfoMessageBean.class);

						arrayList1.addAll(pullMessage);

						swtFmDo(POSITION2, false, false, arrayList1);

					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			}

			@Override
			public void onResponseFailed(String msg) {

			}
		}, userShopInfoBean.getBusinessId(), "1");

	}

	/**
	 * 修改推送消息状态
	 */
	public void changStatus(String id) {
		okHttpsImp.postChangeStatus(new MyResultCallback<String>() {

			@Override
			public void onResponseResult(Result result) {
				ContentUtils.showMsg(InfoDetailsActivity.this,"成功");
				getPushMessages();
				getPushMessages1();
				getPushMessages2();
			}

			@Override
			public void onResponseFailed(String msg) {

			}
		}, BaseActivity.userShopInfoBean.getBusinessId(), id);
	}

	/**
	 * 删除或审核留言信息
	 */
	public void delteLeaveMsg(ArrayList<String> ids, boolean status) {

		StringBuffer sb = new StringBuffer();
		int s = ids.size();
		if (s > 0) {
			for (int i = 0; i < s; i++) {
				if (i == (s - 1)) {
					sb.append(ids.get(i));
				} else {
					sb.append(ids.get(i) + ",");

				}
			}
			ContentUtils.showMsg(InfoDetailsActivity.this, sb.toString());
		} else {
			return;
		}
		if (status) {

			okHttpsImp.getDeleteMessages(new MyResultCallback<String>() {

				@Override
				public void onResponseResult(Result result) {

					ContentUtils.showMsg(InfoDetailsActivity.this, "删除留言成功");
					// 刷新页面
					getShowMessages();

				}

				@Override
				public void onResponseFailed(String msg) {

				}
			}, BaseActivity.userShopInfoBean.getBusinessId(), sb.toString());

		} else {

			okHttpsImp.getAuditMessages(new MyResultCallback<String>() {

				@Override
				public void onResponseResult(Result result) {

					ContentUtils.showMsg(InfoDetailsActivity.this, "审核留言成功");
					// 刷新页面
					getShowMessages();

				}

				@Override
				public void onResponseFailed(String msg) {

				}
			}, BaseActivity.userShopInfoBean.getBusinessId(), sb.toString());

		}
	}


	/**
	 * 已读或删除 待接单 服务 买单信息
	 */
	public void delteAboutOrderMsg(ArrayList<String> ids, boolean status1) {
		StringBuffer sb = new StringBuffer();
		int s = ids.size();
		if (s > 0) {
			for (int i = 0; i < s; i++) {
				if (i == (s - 1)) {
					sb.append(ids.get(i));
				} else {
					sb.append(ids.get(i) + ",");

				}
			}
		} else {
			return;
		}

		if (status1) {

			okHttpsImp.modifyPushDelSts(new MyResultCallback<String>() {

				@Override
				public void onResponseResult(Result result) {

					ContentUtils.showMsg(InfoDetailsActivity.this, "删除成功");
					// 刷新页面
					getPushMessages();
					getPushMessages1();
					getPushMessages2();
				}

				@Override
				public void onResponseFailed(String msg) {

				}
			}, BaseActivity.userShopInfoBean.getBusinessId(), sb.toString());

		}

		else {

			okHttpsImp.modifyPushMessage(new MyResultCallback<String>() {

				@Override
				public void onResponseResult(Result result) {

					ContentUtils.showMsg(InfoDetailsActivity.this, "成功");
					getPushMessages();
					getPushMessages1();
					getPushMessages2();

				}

				@Override
				public void onResponseFailed(String msg) {

				}
			}, BaseActivity.userShopInfoBean.getBusinessId(), sb.toString());

		}
	}


	public class MyAdapter extends FragmentPagerAdapter {
		String[] _titles;

		public MyAdapter(FragmentManager fm, String[] titles) {
			super(fm);
			_titles = titles;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			return _titles[position];
		}

		@Override
		public int getCount() {
			return _titles.length;
		}

		@Override
		public Fragment getItem(int position) {
			switch (position) {
				case POSITION0:
					if (allMessageFragment == null) {
						allMessageFragment = new InfoMessageFragment();
					}
					return allMessageFragment;
				case POSITION1:
					if (sMessageFragment == null) {
						sMessageFragment = new InfoMessageFragment();
					}
					return sMessageFragment;
				case POSITION2:
					if (orderMessageFragment == null) {
						orderMessageFragment = new InfoMessageFragment();
					}
					return orderMessageFragment;
				case POSITION3:
					if (leavemessagefragment == null) {
						leavemessagefragment = new LeaveMessageFragment();
					}
					return leavemessagefragment;
			}
			return null;
		}
	}

	public void setCurrentList(ArrayList<InfoMessageBean> currentList) {
		currentList.clear();
		this.currentList = currentList;
	}
}
