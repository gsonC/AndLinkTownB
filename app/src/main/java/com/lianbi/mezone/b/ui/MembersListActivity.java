package com.lianbi.mezone.b.ui;
/*
 * @创建者     Administrator
 * @创建时间   2016/8/11 9:21
 * @描述       会员列表
 *
 * @更新者     $Author$ 
 * @更新时间   $Date$
 * @更新描述   ${TODO}
 */

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.lianbi.mezone.b.bean.MemberInfoBean;
import com.lianbi.mezone.b.httpresponse.MyResultCallback;
import com.lianbi.mezone.b.httpresponse.OkHttpsImp;
import com.xizhi.mezone.b.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import cn.com.hgh.baseadapter.BaseAdapterHelper;
import cn.com.hgh.baseadapter.QuickAdapter;
import cn.com.hgh.indexscortlist.CharacterParser;
import cn.com.hgh.indexscortlist.PinyinComparator;
import cn.com.hgh.utils.AbAppUtil;
import cn.com.hgh.utils.AbDateUtil;
import cn.com.hgh.utils.AbPullHide;
import cn.com.hgh.utils.AbStrUtil;
import cn.com.hgh.utils.Result;
import cn.com.hgh.utils.ScreenUtils;
import cn.com.hgh.view.AbPullToRefreshView;

public class MembersListActivity extends BaseActivity {

	private EditText mAct_member_list_edit;
	private TextView mTv_newaddmember,mTv_cumulativemember,mTv_addnewmember,mTvIntegral,
			mTvMemberphone,mTvMembercategory,mTvMembersource,mTvMemberlable;
	private AbPullToRefreshView mAct_addmembers_abpulltorefreshview;
	private ListView mAct_addmembers_listview;
	private ImageView mImg_ememberslist_empty;
	private LinearLayout mLltIntegral;
	private Drawable mDrawableDowm,mDrawableUp,mDrawableinitial;
	private int page = 1;
	private ArrayList<MemberInfoBean> mDatas = new ArrayList<MemberInfoBean>();
	private QuickAdapter<MemberInfoBean> mAdapter;
	/**
	 * 汉字转拼音类
	 */
	private CharacterParser mCharacterParser;
	private List<MemberInfoBean> SourceDateList = new ArrayList<MemberInfoBean>();
	/**
	 * 根据拼音来排雷list数据
	 */
	private PinyinComparator mPinyinComparator;
	private String paramLike = "";
	private String typeID = "";
	private final int REQUEST_ADDMEMBER = 1357;
	private final int REQUEST_CHANGMEMBERINFO = 1538;
	boolean isSort = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mCharacterParser = CharacterParser.getInstance();
		mPinyinComparator = new PinyinComparator();
		setContentView(R.layout.act_memberslist, NOTYPE);
		initView();
		setLisenter();
		initAdapter();
		initGetIntent();
		getMembersList(true);
	}
    private   void  initGetIntent(){
		String typeID=getIntent().getStringExtra("typeId");
        if(typeID!=null&&!typeID.equals("")){
			this.typeID=typeID;
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		typeID = "";
	}

	@Override
	protected void onResume() {
		super.onResume();
	//	mAct_member_list_edit.setText("");
		mDrawableinitial.setBounds(0, 0, mDrawableinitial.getMinimumWidth(), mDrawableinitial.getMinimumHeight());
		//mTvIntegral.setCompoundDrawables(null, null, mDrawableinitial, null);
	}

	private void initAdapter() {
		mAdapter = new QuickAdapter<MemberInfoBean>(this,
				R.layout.item_member_list, mDatas) {

			@Override
			protected void convert(BaseAdapterHelper helper, MemberInfoBean item) {
				ImageView tv_mb_photo = helper.getView(R.id.tv_mb_photo);//头像

				TextView tv_mb_nickname = helper.getView(R.id.tv_mb_nickname);//昵称
				TextView tv_mb_vipType = helper.getView(R.id.tv_mb_vipType);//类别
				TextView tv_mb_vipPhone = helper.getView(R.id.tv_mb_vipPhone);//手机号
				/*ScreenUtils.textAdaptationOn720(tv_mb_phone, MembersListActivity.this, 24);//本周新增会员
				ScreenUtils.textAdaptationOn720(tv_mb_category, MembersListActivity.this, 24);//本周新增会员
				ScreenUtils.textAdaptationOn720(tv_mb_source, MembersListActivity.this, 24);//本周新增会员
				ScreenUtils.textAdaptationOn720(tv_mb_label, MembersListActivity.this, 24);//本周新增会员
				ScreenUtils.textAdaptationOn720(tv_mb_integral, MembersListActivity.this, 24);//本周新增会员*/
				Glide.with(MembersListActivity.this).load(item.getVipPhoto()).error(R.mipmap.default_head).into(tv_mb_photo);
				tv_mb_nickname.setText(item.getNickName());
				if(!AbStrUtil.isEmpty(item.getVipType())){
					tv_mb_vipType.setText(item.getVipType());
				}else{
					tv_mb_vipType.setText("普通会员");
				}
				tv_mb_vipPhone.setText(item.getVipPhone());
				/*if(!AbStrUtil.isEmpty(item.getLabelName())){
					tv_mb_label.setText(item.getVipPhone());
				}else{
					tv_mb_label.setText("无");
				}
				tv_mb_integral.setText(item.getVipIntegral() + "");*/

			}
		};
		mAct_addmembers_listview.setAdapter(mAdapter);
	}

	/**
	 * 初始化View
	 */
	private void initView() {
		setPageTitle("会员列表");
		mAct_member_list_edit = (EditText) findViewById(R.id.act_member_list_edit);//搜索框
		mTv_newaddmember = (TextView) findViewById(R.id.tv_newaddmember);//本周新增会员
		mTv_cumulativemember = (TextView) findViewById(R.id.tv_cumulativemember);//累计会员总数
		mTvMemberphone = (TextView) findViewById(R.id.tv_memberphone);//会员电话
		mTvMembercategory = (TextView) findViewById(R.id.tv_membercategory);//类别
		mTvMembersource = (TextView) findViewById(R.id.tv_membersource);//来源
		mTvMemberlable = (TextView) findViewById(R.id.tv_memberlable);//标签
		//mTvIntegral = (TextView) findViewById(R.id.tv_integral);//积分
		mAct_addmembers_abpulltorefreshview = (AbPullToRefreshView) findViewById(R.id.act_addmembers_abpulltorefreshview);//AbPullToRefreshView
		mAct_addmembers_listview = (ListView) findViewById(R.id.act_addmembers_listview);//列表
		mTv_addnewmember = (TextView) findViewById(R.id.tv_addnewmember);//新增会员
		mImg_ememberslist_empty = (ImageView) findViewById(R.id.img_ememberslist_empty);//图片
	//	mLltIntegral = (LinearLayout) findViewById(R.id.llt_integral);//积分排序
		viewAdapter();
		mDrawableDowm = ContextCompat.getDrawable(this, R.mipmap.tma_down);
		mDrawableUp = ContextCompat.getDrawable(this, R.mipmap.tma_up);
		mDrawableinitial = ContextCompat.getDrawable(this, R.mipmap.tma_initialdown);
	}

	/**
	 * View适配
	 */
	private void viewAdapter() {
		ScreenUtils.textAdaptationOn720(mTv_newaddmember, this, 25);//本周新增会员
		ScreenUtils.textAdaptationOn720(mTv_cumulativemember, this, 25);//累计会员数
		ScreenUtils.textAdaptationOn720(mTvMemberphone, this, 32);//会员电话
		ScreenUtils.textAdaptationOn720(mTvMembercategory, this, 32);//类别
		ScreenUtils.textAdaptationOn720(mTvMembersource, this, 32);//来源
		ScreenUtils.textAdaptationOn720(mTvMemberlable, this, 32);//标签
		//ScreenUtils.textAdaptationOn720(mTvIntegral, this, 32);//积分
	}

	/**
	 * 获取会员列表
	 */
	private void getMembersList(final boolean isResh) {

		if (isResh) {
			page = 1;
			mDatas.clear();
			mAdapter.replaceAll(mDatas);
		}
		String reqTime = AbDateUtil.getDateTimeNow();
		String uuid = AbStrUtil.getUUID();

		try {
			okHttpsImp.getMembersList(uuid, "app", reqTime, OkHttpsImp.md5_key,
					userShopInfoBean.getBusinessId(), page + "", 20 + "", new MyResultCallback<String>() {

						@Override
						public void onResponseResult(Result result) {
							page++;
							String reString= result.getData();
							System.out.println("reString209"+reString);
							if (!AbStrUtil.isEmpty(reString)) {

								try {
									JSONObject jsonObject = new JSONObject(reString);
									reString = jsonObject.getString("businessVipWXList");
									System.out.println("reString215"+reString);

									mTv_newaddmember.setText("本周新增会员:"+jsonObject.getInt("vipWeekCount"));
									mTv_cumulativemember.setText("累计会员数:"+jsonObject.getInt("vipCount"));
									ArrayList<MemberInfoBean> mDatasL = (ArrayList<MemberInfoBean>) JSON
											.parseArray(reString, MemberInfoBean.class);
									if (mDatasL != null && mDatasL.size() > 0) {
										mDatas.addAll(mDatasL);
										SourceDateList.addAll(mDatasL);
									}
									if (mDatas != null && mDatas.size() > 0) {
										mImg_ememberslist_empty.setVisibility(View.GONE);
										mAct_addmembers_abpulltorefreshview.setVisibility(View.VISIBLE);
									} else {
										mImg_ememberslist_empty.setVisibility(View.VISIBLE);
										mAct_addmembers_abpulltorefreshview.setVisibility(View.GONE);
									}
									AbPullHide.hideRefreshView(isResh, mAct_addmembers_abpulltorefreshview);
									mAdapter.replaceAll(mDatas);

								} catch (JSONException e) {
									e.printStackTrace();
								}

							}
						}

						@Override
						public void onResponseFailed(String msg) {
							if (isResh) {
								mImg_ememberslist_empty
										.setVisibility(View.VISIBLE);
								mAct_addmembers_abpulltorefreshview
										.setVisibility(View.GONE);
							}
							AbPullHide.hideRefreshView(isResh,
									mAct_addmembers_abpulltorefreshview);
						}
					});
		} catch (Exception e) {
			e.printStackTrace();
		}


	}

	/**
	 * 添加监听
	 */
	private void setLisenter() {
		// 根据输入框输入值的改变来过滤搜索
		mAct_member_list_edit
				.addTextChangedListener(new TextWatcher() {

					@Override
					public void onTextChanged(CharSequence s, int start,
											  int before, int count) {
						// 当输入框里面的值为空，更新为原来的列表，否则为过滤数据列表
						paramLike = s.toString();
		//				filterData(s.toString());
						if("".equals(paramLike)){
							filterData("");
						}
					}

					@Override
					public void beforeTextChanged(CharSequence s, int start,
												  int count, int after) {

					}

					@Override
					public void afterTextChanged(Editable s) {
					}
				});
		mAct_member_list_edit
				.setOnEditorActionListener(new OnEditorActionListener() {

					@Override
					public boolean onEditorAction(TextView v, int actionId,
												  KeyEvent event) {
						if (actionId == EditorInfo.IME_ACTION_DONE
								|| actionId == EditorInfo.IME_ACTION_UNSPECIFIED) {
							paramLike = mAct_member_list_edit
									.getText().toString().trim();
							filterData(paramLike);
						}
						AbAppUtil.closeSoftInput(MembersListActivity.this);
						return false;
					}
				});

		mAct_addmembers_abpulltorefreshview.setLoadMoreEnable(true);
		mAct_addmembers_abpulltorefreshview.setPullRefreshEnable(true);
		mAct_addmembers_abpulltorefreshview
				.setOnHeaderRefreshListener(new AbPullToRefreshView.OnHeaderRefreshListener() {

					@Override
					public void onHeaderRefresh(AbPullToRefreshView view) {
						mDrawableinitial.setBounds(0, 0, mDrawableinitial.getMinimumWidth(), mDrawableinitial.getMinimumHeight());
					//	mTvIntegral.setCompoundDrawables(null, null, mDrawableinitial, null);
						String params = mAct_member_list_edit.getText().toString().trim();
						getMembersList(true);
					}

				});
		mAct_addmembers_abpulltorefreshview
				.setOnFooterLoadListener(new AbPullToRefreshView.OnFooterLoadListener() {

					@Override
					public void onFooterLoad(AbPullToRefreshView view) {
						mDrawableinitial.setBounds(0, 0, mDrawableinitial.getMinimumWidth(), mDrawableinitial.getMinimumHeight());
					//	mTvIntegral.setCompoundDrawables(null, null, mDrawableinitial, null);
						String params = mAct_member_list_edit.getText().toString().trim();
						getMembersList(false);
					}
				});

		/**
		 * 点击跳转
		 */
		mAct_addmembers_listview
				.setOnItemClickListener(new AdapterView.OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
											int position, long id) {
						//点击传值
						Intent file_intent = new Intent(MembersListActivity.this, AddNewMembersActivity.class);
						file_intent.putExtra("memberInfo", mDatas.get(position));
						file_intent.putExtra("isShow", true);
						startActivityForResult(file_intent, REQUEST_CHANGMEMBERINFO);
	//					mAct_member_list_edit.setText("");
					}
				});

		mTv_addnewmember.setOnClickListener(this);
		//.setOnClickListener(this);
	}

	@Override
	protected void onChildClick(View view) {
		super.onChildClick(view);
		switch (view.getId()) {
			case R.id.llt_integral://积分点击排序
				if (isSort) {
					isSort = false;
					mDrawableDowm.setBounds(0, 0, mDrawableDowm.getMinimumWidth(), mDrawableDowm.getMinimumHeight());
					//mTvIntegral.setCompoundDrawables(null, null, mDrawableDowm, null);
					startSort(isSort);
				} else {
					isSort = true;
					mDrawableUp.setBounds(0, 0, mDrawableUp.getMinimumWidth(), mDrawableUp.getMinimumHeight());
					//mTvIntegral.setCompoundDrawables(null, null, mDrawableUp, null);
					startSort(isSort);
				}
				break;

			case R.id.tv_addnewmember://添加新会员
				Intent intent = new Intent(MembersListActivity.this, AddNewMembersActivity.class);
				intent.putExtra("isShow", false);
				startActivityForResult(intent, REQUEST_ADDMEMBER);
		//		mAct_member_list_edit.setText("");
				break;

		}
	}

	/**
	 * 排序
	 *
	 * @param beginsort true 从小到大 false 从大到小
	 */
	private void startSort(final boolean beginsort) {
		Collections.sort(mDatas, new Comparator<MemberInfoBean>() {
			@Override
			public int compare(MemberInfoBean lhs, MemberInfoBean rhs) {
				Integer id1 = lhs.getVipIntegral();
				Integer id2 = rhs.getVipIntegral();
				if (beginsort) {
					return id1.compareTo(id2);
				} else {
					return id2.compareTo(id1);
				}
			}
		});
		mAdapter.replaceAll(mDatas);

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) {
			switch (requestCode) {
				case REQUEST_ADDMEMBER://添加会员返回
					getMembersList(true);
					break;
				case REQUEST_CHANGMEMBERINFO://修改会员信息返回
					getMembersList(true);
					break;
			}
		}
	}

	/**
	 * 根据输入条件改变listview
	 */
	private void filterData(String filterStr) {
	//	if (!AbStrUtil.isEmpty(filterStr)) {
			getMembersList(true);
	//	}else{
	//		System.out.println("SourceDateList"+SourceDateList.size());
	//		mAdapter.replaceAll(SourceDateList);
	//	}

	}

}