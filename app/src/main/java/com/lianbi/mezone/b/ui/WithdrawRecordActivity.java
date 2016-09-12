package com.lianbi.mezone.b.ui;

import android.os.Bundle;

import com.xizhi.mezone.b.R;

import butterknife.ButterKnife;

/**
 * 提现记录
 */
public class WithdrawRecordActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withdraw_record, NOTYPE);
        ButterKnife.bind(WithdrawRecordActivity.this);
    }
}
