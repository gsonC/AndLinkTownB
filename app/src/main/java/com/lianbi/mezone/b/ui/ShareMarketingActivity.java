package com.lianbi.mezone.b.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lianbi.mezone.b.app.Constants;
import com.xizhi.mezone.b.R;

import cn.com.hgh.utils.ContentUtils;

public class ShareMarketingActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_marketing, NOTYPE);
        setPageTitle("分享营销");

        final View share_group_buying = findViewById(R.id.share_group_buying);
        ((ImageView) share_group_buying.findViewById(R.id.image_tip)).setImageResource(R.mipmap.fenxiangtuangou);
        ((TextView) share_group_buying.findViewById(R.id.menu_name)).setText("分享团购");
        final RelativeLayout shareBuyingSwitch = (RelativeLayout) share_group_buying.findViewById(R.id.switch_container);
        final View shareBuyingSwitchOpen = shareBuyingSwitch.findViewById(R.id.switch_open);
        final View shareBuyingSwitchClose = shareBuyingSwitch.findViewById(R.id.switch_close);
        shareBuyingSwitch.setVisibility(View.VISIBLE);
        shareBuyingSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean shareGroupBuyingCanOpen = ContentUtils.getSharePreBoolean(ShareMarketingActivity.this,
                        Constants.SHARED_PREFERENCE_NAME, Constants.SHARE_GROUP_BUYING);
                if (shareGroupBuyingCanOpen) {
                    shareBuyingSwitch.setBackgroundResource(R.drawable.switch_background_shape_2);
                    shareBuyingSwitchOpen.setVisibility(View.GONE);
                    shareBuyingSwitchClose.setVisibility(View.VISIBLE);
                    share_group_buying.setClickable(false);
                    ContentUtils.putSharePre(ShareMarketingActivity.this,
                            Constants.SHARED_PREFERENCE_NAME,
                            Constants.SHARE_GROUP_BUYING, false);
                } else {
                    shareBuyingSwitch.setBackgroundResource(R.drawable.switch_background_shape_1);
                    shareBuyingSwitchOpen.setVisibility(View.VISIBLE);
                    shareBuyingSwitchClose.setVisibility(View.GONE);
                    share_group_buying.setClickable(true);
                    share_group_buying.setOnClickListener(this);
                    ContentUtils.putSharePre(ShareMarketingActivity.this,
                            Constants.SHARED_PREFERENCE_NAME,
                            Constants.SHARE_GROUP_BUYING, true);
                }
            }
        });
        boolean shareGroupBuyingCanOpen = ContentUtils.getSharePreBoolean(ShareMarketingActivity.this,
                Constants.SHARED_PREFERENCE_NAME, Constants.SHARE_GROUP_BUYING);
        if (shareGroupBuyingCanOpen) {
            shareBuyingSwitch.setBackgroundResource(R.drawable.switch_background_shape_1);
            shareBuyingSwitchOpen.setVisibility(View.VISIBLE);
            shareBuyingSwitchClose.setVisibility(View.GONE);
            share_group_buying.setClickable(true);
            share_group_buying.setOnClickListener(this);
        } else {
            shareBuyingSwitch.setBackgroundResource(R.drawable.switch_background_shape_2);
            shareBuyingSwitchOpen.setVisibility(View.GONE);
            shareBuyingSwitchClose.setVisibility(View.VISIBLE);
            share_group_buying.setClickable(false);
        }
        share_group_buying.findViewById(R.id.in_building).setVisibility(View.GONE);
        share_group_buying.findViewById(R.id.divide_line).setVisibility(View.VISIBLE);

        View investigation = findViewById(R.id.investigation);
        ((ImageView) investigation.findViewById(R.id.image_tip)).setImageResource(R.mipmap.wenquan);
        ((TextView) investigation.findViewById(R.id.menu_name)).setText("问卷调查");
        investigation.findViewById(R.id.switch_container).setVisibility(View.GONE);
        investigation.findViewById(R.id.in_building).setVisibility(View.VISIBLE);
        investigation.findViewById(R.id.divide_line).setVisibility(View.VISIBLE);

        View click_praise_get_hongbao = findViewById(R.id.click_praise_receive_hongbao);
        ((ImageView) click_praise_get_hongbao.findViewById(R.id.image_tip)).setImageResource(R.mipmap.dianzan);
        ((TextView) click_praise_get_hongbao.findViewById(R.id.menu_name)).setText("点赞领红包");
        click_praise_get_hongbao.findViewById(R.id.switch_container).setVisibility(View.GONE);
        click_praise_get_hongbao.findViewById(R.id.in_building).setVisibility(View.VISIBLE);
        click_praise_get_hongbao.findViewById(R.id.divide_line).setVisibility(View.VISIBLE);

        View store_showing = findViewById(R.id.store_showing);
        ((ImageView) store_showing.findViewById(R.id.image_tip)).setImageResource(R.mipmap.dianpuzhanshi);
        ((TextView) store_showing.findViewById(R.id.menu_name)).setText("店铺展示");
        store_showing.findViewById(R.id.switch_container).setVisibility(View.GONE);
        store_showing.findViewById(R.id.in_building).setVisibility(View.VISIBLE);
        store_showing.findViewById(R.id.divide_line).setVisibility(View.GONE);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.share_group_buying:
                startActivity(new Intent(ShareMarketingActivity.this, ShareGroupBuyingActivity.class));
                break;
        }
    }
}
