package com.lianbi.mezone.b.ui;

import android.os.Bundle;
import android.widget.TextView;

import com.xizhi.mezone.b.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 提现失败原因
 */
public class WithdrawFailedReasonActivity extends BaseActivity {
    @Bind(R.id.reason)
    TextView reason;

    public static final String checkStatus = "CHECK_STATUS";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withdraw_failed_reason, NOTYPE);
        ButterKnife.bind(WithdrawFailedReasonActivity.this);
        setPageTitle("提现失败原因");
        String status = getIntent().getStringExtra(checkStatus);
        CharSequence s = null;
        if ("03".equals(status)) {
            s = getText(R.string.withdraw_failed_reason_1);
        }
        if ("05".equals(status)) {
            s = getText(R.string.withdraw_failed_reason_2);
        }
        reason.setText(s);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}
