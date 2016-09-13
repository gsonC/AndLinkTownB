package com.lianbi.mezone.b.ui;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.xizhi.mezone.b.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 提现进度，或者提现成功
 */
public class WithdrawingProgressActivity extends BaseActivity {
    @Bind(R.id.withdrawing_time)
    TextView withdrawing_time;//提现时间
    @Bind(R.id.review_icon)
    ImageView review_icon;//审核图标
    @Bind(R.id.review)
    TextView review;//审核说明
    @Bind(R.id.review_time)
    TextView review_time;//审核通过或者审核失败时间
    @Bind(R.id.transferred_to_account_icon)
    ImageView transferred_to_account_icon;//到账图标
    @Bind(R.id.transferred_to_account)
    TextView transferred_to_account;//到账
    @Bind(R.id.transferred_to_account_time)
    TextView transferred_to_account_time;//到账时间

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withdrawing_progress, NOTYPE);
        ButterKnife.bind(WithdrawingProgressActivity.this);
    }
}
