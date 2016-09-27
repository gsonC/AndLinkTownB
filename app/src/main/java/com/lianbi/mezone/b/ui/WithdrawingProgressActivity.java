package com.lianbi.mezone.b.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.lianbi.mezone.b.bean.Status;
import com.xizhi.mezone.b.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.com.hgh.utils.AbDateUtil;
import cn.com.hgh.utils.AbStrUtil;

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

    public static final String STATUS = "status";

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

        Status status = (Status) getIntent().getSerializableExtra(STATUS);
        switch (getIntent().getStringExtra(FROM)) {//从哪里跳转过来的
            case PROGRESS://progress代表从FinancialOfficeFragment或ShouRuHActivity跳转过来，说明提现尚未完成或者提现请求刚刚提交
                setPageTitle("提现进度");
                setPageRightTextColor(R.color.commo_text_color);
                setPageRightTextSize(15);
                setPageRightText("提现记录");
                if (status != null) {
                    String apply = status.getApply();//提现申请时间
                    String audit = status.getAudit();//审核通过时间
                    if (!AbStrUtil.isEmpty(apply)) {
                        withdrawing_time.setText(AbDateUtil.getStringByFormat(apply, AbDateUtil.dateFormatYMDHM_New));
                    }
                    if (AbStrUtil.isEmpty(audit)) {
                        review_icon.setImageResource(R.mipmap.icon_uncheck);
                        review.setText("审核中");
                        transferred_to_account_icon.setImageResource(R.mipmap.icon_uncheck);
                        transferred_to_account.setText("未打款");
                    } else {
                        review_icon.setImageResource(R.mipmap.icon_tick);
                        review.setText("审核通过");
                        review_time.setText(AbDateUtil.getStringByFormat(audit, AbDateUtil.dateFormatYMDHM_New));
                        transferred_to_account_icon.setImageResource(R.mipmap.icon_uncheck);
                        transferred_to_account.setText("未打款");
                    }
                }
                break;
            case SUCESS://sucess代表从WithdrawRecordActivity中点击item跳转过来，说明提现已经成功
                setPageTitle("提现成功");
                review_icon.setImageResource(R.mipmap.icon_tick);
                review.setText("审核通过");
                transferred_to_account_icon.setImageResource(R.mipmap.icon_tick);
                transferred_to_account.setText("到账成功");
                if (status != null) {
                    withdrawing_time.setText(AbDateUtil.getStringByFormat(status.getApply(), AbDateUtil.dateFormatYMDHM_New));
                    review_time.setText(AbDateUtil.getStringByFormat(status.getAudit(), AbDateUtil.dateFormatYMDHM_New));
                    transferred_to_account_time.setText(AbDateUtil.getStringByFormat(status.getSuccess(), AbDateUtil.dateFormatYMDHM_New));
                }
                break;
        }
    }

    @Override
    protected void onTitleRightClickTv() {
        super.onTitleRightClickTv();
        startActivity(new Intent(WithdrawingProgressActivity.this, WithdrawRecordActivity.class));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}
