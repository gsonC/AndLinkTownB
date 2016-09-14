package com.lianbi.mezone.b.ui;

import android.content.Intent;
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

    /*
    * 从哪里跳转过来的
    * */
    public static final String FROM = "from_name";

    /*
    * 说明提现尚未完成或者提现请求刚刚提交
    * */
    public static final String PROGRESS = "progress";

    /*
    * 说明提现已经成功
    * */
    public static final String SUCESS = "sucess";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withdrawing_progress, NOTYPE);
        ButterKnife.bind(WithdrawingProgressActivity.this);

        switch (getIntent().getStringExtra(FROM)) {//从哪里跳转过来的
            case PROGRESS://progress代表从FinancialOfficeFragment或ShouRuHActivity跳转过来，说明提现尚未完成或者提现请求刚刚提交
                setPageTitle("提现进度");
                setPageRightTextColor(R.color.commo_text_color);
                setPageRightTextSize(15);
                setPageRightText("提现记录");
                break;
            case SUCESS://sucess代表从WithdrawRecordActivity中点击item跳转过来，说明提现已经成功
                setPageTitle("提现成功");
                break;
        }
    }

    @Override
    protected void onTitleRightClickTv() {
        super.onTitleRightClickTv();
        startActivity(new Intent(WithdrawingProgressActivity.this, WithdrawRecordActivity.class));
    }
}
