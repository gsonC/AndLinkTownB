package com.lianbi.mezone.b.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xizhi.mezone.b.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MembersManagementTwoActivity extends BaseActivity {
	ExpandableListView mainlistview = null;
	List<String> parent = null;
	Map<String, List<String>> map = null;
	ExpandableListView expandListView;


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


		map = new HashMap<String, List<String>>();
		List<String> list1 = new ArrayList<String>();
		list1.add("会员列表");
		list1.add("会员分类");
		list1.add("标签管理");

		map.put("会员信息管理", list1);



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
		public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
			String key = MembersManagementTwoActivity.this.parent.get(groupPosition);
			String info = map.get(key).get(childPosition);
			if (convertView == null) {
				LayoutInflater inflater = (LayoutInflater) MembersManagementTwoActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				convertView = inflater.inflate(R.layout.layout_children, null);
			}
			//ImageView child_ima=(ImageView)findViewById(R.id.child_ima);
			//
			RelativeLayout rela_child= (RelativeLayout)convertView.findViewById(R.id.heheh);
			//	 rela_child= (LinearLayout)convertView.findViewById(R.id.rela_child);
			TextView tv = (TextView) convertView.findViewById(R.id.second_textview);
			tv.setText(info);
			rela_child.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					//会员列表
					if(groupPosition==0&&childPosition==0) {
						startActivity(new Intent(MembersManagementTwoActivity.this,MembersListActivity.class));
					}
					//会员分类
					if(groupPosition==0&&childPosition==1) {
						startActivity(new Intent(MembersManagementTwoActivity.this,MemberClassifyActivity.class));
					}
					//标签管理
					if(groupPosition==0&&childPosition==2){
						startActivity(new Intent(MembersManagementTwoActivity.this,TagManagerActivity.class));
					}
					//优惠券管理
					if(groupPosition==1&&childPosition==0) {
						startActivity(new Intent(MembersManagementTwoActivity.this,CouponManagerActivity.class));
					}
					//营销短信管理
					if(groupPosition==1&&childPosition==1) {
						startActivity(new Intent(MembersManagementTwoActivity.this,MarketingMsgGlActivity.class));
					}
					//积分商城管理
					if(groupPosition==1&&childPosition==2) {
						startActivity(new Intent(MembersManagementTwoActivity.this,MemberPointManage.class));
					}

				}
			});

			return rela_child;

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
				LayoutInflater inflater = (LayoutInflater) MembersManagementTwoActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				convertView = inflater.inflate(R.layout.layout_parent, null);
			}
			TextView tv = (TextView) convertView.findViewById(R.id.parent_textview);
			tv.setText(MembersManagementTwoActivity.this.parent.get(groupPosition));
			ImageView expand_ima = (ImageView) convertView.findViewById(R.id.expand_ima);

			// 将默认的箭头修改到右边显示:
			if (isExpanded) {
				expand_ima.setImageResource(R.mipmap.up2);
			} else {
				expand_ima.setImageResource(R.mipmap.down12);

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
