package com.lianbi.mezone.b.ui;

import android.os.Bundle;
import android.widget.TextView;

import com.xizhi.mezone.b.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 收款额度
 */
public class ReceiptsActivity extends BaseActivity {
    @Bind(R.id.today_left_)
    TextView today_left;
    @Bind(R.id.today_total)
    TextView today_total;
    @Bind(R.id.today_has_used)
    TextView today_has_used;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipts, NOTYPE);
        ButterKnife.bind(ReceiptsActivity.this);
    }
}
