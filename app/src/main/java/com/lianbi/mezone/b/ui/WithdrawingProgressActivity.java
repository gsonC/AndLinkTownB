package com.lianbi.mezone.b.ui;

import android.os.Bundle;

import com.xizhi.mezone.b.R;

import butterknife.ButterKnife;

/**
 * 提现进度，或者提现成功
 */
public class WithdrawingProgressActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withdrawing_progress, NOTYPE);
        ButterKnife.bind(WithdrawingProgressActivity.this);
    }
}
