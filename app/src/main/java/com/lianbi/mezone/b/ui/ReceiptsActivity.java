package com.lianbi.mezone.b.ui;

import android.os.Bundle;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lianbi.mezone.b.httpresponse.MyResultCallback;
import com.xizhi.mezone.b.R;

import java.math.BigDecimal;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.com.hgh.utils.MathExtend;
import cn.com.hgh.utils.Result;

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
        setPageTitle("收款额度");
        try {
            okHttpsImp.queryAmtConfig(uuid, reqTime, userShopInfoBean.getUserId(), BusinessId, new MyResultCallback<String>() {
                @Override
                public void onResponseResult(Result result) {
                    if (result != null) {
                        JSONObject jsonObject = JSON.parseObject(result.getData());
                        today_has_used.setText(MathExtend.roundNew(jsonObject.getBigDecimal("useAmt").divide(new BigDecimal(100)).doubleValue(), 2));
                        today_left.setText(MathExtend.roundNew(jsonObject.getBigDecimal("remainAmt").divide(new BigDecimal(100)).doubleValue(), 2));
                        today_total.setText(MathExtend.roundNew(jsonObject.getBigDecimal("defaultAmt").divide(new BigDecimal(100)).doubleValue(), 2));
                    }
                }

                @Override
                public void onResponseFailed(String msg) {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
