package com.lianbi.mezone.b.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xizhi.mezone.b.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MembersManagementActivity extends BaseActivity {
	ExpandableListView mainlistview = null;
	List<String> parent = null;
	Map<String, List<String>> map = null;
	ExpandableListView expandListView;
	LinearLayout rela_child;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_members_management, NOTYPE);
		initView();
		initData();
		mainlistview.setAdapter(new MyAdapter());

	}

	private void initView() {
		setPageTitle("会员管理");
		expandListView = (ExpandableListView) findViewById(R.id.main_expandablelistview);
		//设置 属性 GroupIndicator 去掉默认向下的箭头
		expandListView.setGroupIndicator(null);
		mainlistview = (ExpandableListView) this.findViewById(R.id.main_expandablelistview);


	}

	// 初始化数据
	public void initData() {

		parent = new ArrayList<String>();
		parent.add("会员信息管理");
		parent.add("会员营销管理");

		map = new HashMap<String, List<String>>();
		List<String> list1 = new ArrayList<String>();
		list1.add("会员列表");
		list1.add("会员分类");
		list1.add("标签管理");

		map.put("会员信息管理", list1);

		List<String> list2 = new ArrayList<String>();
		list2.add("优惠券管理");
		list2.add("营销短信管理");
		list2.add("积分商品管理");
		map.put("会员营销管理", list2);


	}

	//适配器
	class MyAdapter extends BaseExpandableListAdapter {

		//得到子item需要关联的数据
		@Override
		public Object getChild(int groupPosition, int childPosition) {
			String key = parent.get(groupPosition);
			return (map.get(key).get(childPosition));
		}

		//得到子item的ID
		@Override
		public long getChildId(int groupPosition, int childPosition) {
			return childPosition;
		}

		//设置子item的组件
		@Override
		public View getChildView(final int groupPosition,final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
			String key = MembersManagementActivity.this.parent.get(groupPosition);
			String info = map.get(key).get(childPosition);
			if (convertView == null) {
				LayoutInflater inflater = (LayoutInflater) MembersManagementActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				convertView = inflater.inflate(R.layout.layout_children, null);
			}
		//	ImageView child_ima=(ImageView)convertView.findViewById(R.id.child_ima);
			//
			 rela_child= (LinearLayout) findViewById(R.id.rela_child);
			TextView tv = (TextView) convertView.findViewById(R.id.second_textview);
			tv.setText(info);
			tv.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					//会员列表
					if(groupPosition==0&&childPosition==0) {
						startActivity(new Intent(MembersManagementActivity.this,MembersListActivity.class));
					}
					//会员分类
					if(groupPosition==0&&childPosition==1) {
						startActivity(new Intent(MembersManagementActivity.this,MemberClassifyActivity.class));
					}
					//标签管理
					if(groupPosition==0&&childPosition==2){
						startActivity(new Intent(MembersManagementActivity.this,TagManagerActivity.class));
					}
					//优惠券管理
					if(groupPosition==1&&childPosition==0) {
						startActivity(new Intent(MembersManagementActivity.this,CouponManagerActivity.class));
					}
					//营销短信管理
					if(groupPosition==1&&childPosition==1) {
						startActivity(new Intent(MembersManagementActivity.this,MarketingMsgGlActivity.class));
					}
					//积分商城管理
					if(groupPosition==1&&childPosition==2) {
						startActivity(new Intent(MembersManagementActivity.this,MemberPointManage.class));
					}

				}
			});

			return tv;

	}

		//获取当前父item下的子item的个数
		@Override
		public int getChildrenCount(int groupPosition) {
			String key = parent.get(groupPosition);
			int size = map.get(key).size();
			return size;
		}

		//获取当前父item的数据
		@Override
		public Object getGroup(int groupPosition) {
			return parent.get(groupPosition);
		}

		@Override
		public int getGroupCount() {
			return parent.size();
		}

		@Override
		public long getGroupId(int groupPosition) {
			return groupPosition;
		}

		//设置父item组件
		@Override
		public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
			if (convertView == null) {
				LayoutInflater inflater = (LayoutInflater) MembersManagementActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				convertView = inflater.inflate(R.layout.layout_parent, null);
			}
			TextView tv = (TextView) convertView.findViewById(R.id.parent_textview);
			tv.setText(MembersManagementActivity.this.parent.get(groupPosition));
			ImageView expand_ima = (ImageView) convertView.findViewById(R.id.expand_ima);

			// 将默认的箭头修改到右边显示:
			if (isExpanded) {
				expand_ima.setBackgroundResource(R.mipmap.up2);
			} else {
				expand_ima.setBackgroundResource(R.mipmap.down12);
			}

			return convertView;


		}

		@Override
		public boolean hasStableIds() {
			return true;
		}

		@Override
		public boolean isChildSelectable(int groupPosition, int childPosition) {
			return true;
		}
	}

}
