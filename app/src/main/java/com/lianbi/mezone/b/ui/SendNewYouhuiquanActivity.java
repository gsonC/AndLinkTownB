package com.lianbi.mezone.b.ui;

import android.os.Bundle;

import com.xizhi.mezone.b.R;

import butterknife.ButterKnife;

public class SendNewYouhuiquanActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_new_youhuiquan, HAVETYPE);
        ButterKnife.bind(this);
        setPageTitle("发送新优惠券");
    }
}
