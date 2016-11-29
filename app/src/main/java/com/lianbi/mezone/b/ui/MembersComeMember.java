package com.lianbi.mezone.b.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xizhi.mezone.b.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MembersComeMember extends BaseActivity {

	@Bind(R.id.second_textview)
	TextView secondTextview;
	@Bind(R.id.child_ima)
	ImageView childIma;
	@Bind(R.id.rela_listview)
	RelativeLayout relaListview;
	@Bind(R.id.rela_list)
	RelativeLayout relaList;
	@Bind(R.id.re_linear)
	RelativeLayout reLinear;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_members_come_member,NOTYPE);
		ButterKnife.bind(this);
       setPageTitle("会员列表");
       setPageRightText("折扣设置");
	}

	@Override
	@OnClick({R.id.rela_listview,R.id.rela_list,R.id.re_linear})
	public void onClick(View view) {
		super.onClick(view);
		switch (view.getId()){
			case R.id.rela_listview://会员列表
				startActivity(new Intent(MembersComeMember.this,MembersListActivity.class));

				break;
			case R.id.rela_list://会员等级
				startActivity(new Intent(MembersComeMember.this,MemberClassifyActivity.class));
				break;
			case R.id.re_linear://折扣设置
				startActivity(new Intent(MembersComeMember.this,CountActivity.class));
				break;
		}
	}

	@Override
	protected void onTitleRightClickTv() {
		super.onTitleRightClick1();
      startActivity(new Intent(MembersComeMember.this,MemberAddCategoryActivity.class));
	}


}
