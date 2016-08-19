package com.lianbi.mezone.b.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xizhi.mezone.b.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import cn.com.hgh.timeselector.TimeSelectorE;
import cn.com.hgh.utils.AbDateUtil;

public class SendNewCouponActivity extends BaseActivity implements TextWatcher, CompoundButton.OnCheckedChangeListener {
    @Bind(R.id.input_coupon_name)
    EditText input_coupon_name;
    @Bind(R.id.coupon_money_num)
    EditText coupon_money_num;
    @Bind(R.id.changeable_text)
    TextView changeable_text;
    @Bind(R.id.minimum)
    EditText minimum;
    @Bind(R.id.valid_period_from)
    TextView valid_period_from;
    @Bind(R.id.valid_period_to)
    TextView valid_period_to;
    @Bind(R.id.selectable_member)
    TextView selectable_member;
    @Bind(R.id.expected_coupon_send_num)
    TextView expected_coupon_send_num;
    @Bind(R.id.is_need_send_sms)
    CheckBox is_need_send_sms;
    @Bind(R.id.selectable_templet)
    TextView selectable_templet;
    @Bind(R.id.editable_sms_content)
    TextView editable_sms_content;
    @Bind(R.id.expected_sms_send_num_parent)
    LinearLayout expected_sms_send_num_parent;
    @Bind(R.id.expected_sms_send_num)
    TextView expected_sms_send_num;
    @Bind(R.id.send_new_coupon)
    TextView send_new_coupon;

    private final String ENDTIME = "2030-01-01 00:00";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_new_coupon, HAVETYPE);
        ButterKnife.bind(this);
        setPageTitle("发送新优惠券");
        changeText(minimum.length());
    }

    private void changeText(int length) {
        StringBuilder stringBuilder = new StringBuilder("          ");
        for (int i = 0; i < length; i++) {
            stringBuilder.append("  ");
        }
        stringBuilder.append("使用，单次消费限用一张，只支持微信商城使用");
        changeable_text.setText(stringBuilder.toString());
    }

    @Override
    @OnClick({R.id.valid_period_from, R.id.valid_period_to, R.id.selectable_member,
            R.id.selectable_templet, R.id.send_new_coupon})
    protected void onChildClick(View view) {
        super.onChildClick(view);
        switch (view.getId()) {
            case R.id.valid_period_from:// 起始时间
                TimeSelectorE timeSelectorFrom = new TimeSelectorE(SendNewCouponActivity.this,
                        new TimeSelectorE.ResultHandler() {
                            @Override
                            public void handle(String time) {
                                valid_period_from.setText(time);
                            }
                        }, AbDateUtil.getCurrentDate(AbDateUtil.dateFormatYMDHM),
                        ENDTIME);
                timeSelectorFrom.setMode(TimeSelectorE.MODE.YMD);
                timeSelectorFrom.setTitle("起始时间");
                timeSelectorFrom.show();
                break;
            case R.id.valid_period_to:// 结束时间
                TimeSelectorE timeSelectorTo = new TimeSelectorE(this,
                        new TimeSelectorE.ResultHandler() {
                            @Override
                            public void handle(String time) {
                                valid_period_to.setText(time);
                            }
                        }, AbDateUtil.getCurrentDate(AbDateUtil.dateFormatYMDHM),
                        ENDTIME);
                timeSelectorTo.setMode(TimeSelectorE.MODE.YMD);
                timeSelectorTo.setTitle("结束时间");
                timeSelectorTo.show();
                break;
            case R.id.selectable_member:
                startActivity(new Intent(SendNewCouponActivity.this, MarketingSelectMemberActivity.class));
                break;
            case R.id.selectable_templet:
                startActivity(new Intent(SendNewCouponActivity.this, MarketingSMSexampleActivity.class));
                break;
            case R.id.send_new_coupon:
                break;
        }
    }

    @Override
    @OnTextChanged({R.id.minimum})
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    @OnTextChanged({R.id.minimum})
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    @OnTextChanged({R.id.minimum})
    public void afterTextChanged(Editable s) {
        changeText(s.length());
    }

    @Override
    @OnCheckedChanged({R.id.is_need_send_sms})
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            selectable_templet.setVisibility(View.VISIBLE);
            editable_sms_content.setVisibility(View.VISIBLE);
            expected_sms_send_num_parent.setVisibility(View.VISIBLE);
        } else {
            selectable_templet.setVisibility(View.GONE);
            editable_sms_content.setVisibility(View.GONE);
            expected_sms_send_num_parent.setVisibility(View.GONE);
        }
    }
}