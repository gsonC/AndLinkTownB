package com.lianbi.mezone.b.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xizhi.mezone.b.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 新增分类
 *
 * @author
 * @time
 * @date
 */
public class MemberAddCategoryActivity extends BaseActivity {


    Intent getIntent;
    @Bind(R.id.tv_memberclassify)
    TextView tvMemberclassify;
    @Bind(R.id.tv_classifyvalue)
    EditText tvClassifyvalue;
    @Bind(R.id.ray_membercategory)
    RelativeLayout rayMembercategory;
    @Bind(R.id.tv_discountradio)
    TextView tvDiscountradio;
    @Bind(R.id.tv_radiovalue)
    EditText tvRadiovalue;
    @Bind(R.id.tv_sale)
    TextView tvSale;
    @Bind(R.id.tv_rangebetween)
    TextView tvRangebetween;
    @Bind(R.id.lay_discountrate)
    LinearLayout layDiscountrate;
    @Bind(R.id.tv_maxidiscount)
    TextView tvMaxidiscount;
    @Bind(R.id.tv_maxidiscountvalue)
    EditText tvMaxidiscountvalue;
    @Bind(R.id.tv_rmb)
    TextView tvRmb;
    @Bind(R.id.lay_singlediscount)
    LinearLayout laySinglediscount;
    @Bind(R.id.tv_integralradio)
    TextView tvIntegralradio;
    @Bind(R.id.tv_whatmoney)
    EditText tvWhatmoney;
    @Bind(R.id.tv_whatintegral)
    EditText tvWhatintegral;
    @Bind(R.id.ray_integralratio1)
    LinearLayout rayIntegralratio1;
    @Bind(R.id.ray_integralratio)
    LinearLayout rayIntegralratio;
    @Bind(R.id.tv_required)
    TextView tvRequired;
    @Bind(R.id.tv_amountpay)
    TextView tvAmountpay;
    @Bind(R.id.et_rangebefore)
    EditText etRangebefore;
    @Bind(R.id.tv_between)
    TextView tvBetween;
    @Bind(R.id.et_rangeafter)
    EditText etRangeafter;
    @Bind(R.id.et_element)
    TextView etElement;
    @Bind(R.id.lay_required)
    RelativeLayout layRequired;
    @Bind(R.id.ray_required)
    LinearLayout rayRequired;
    @Bind(R.id.lay_main)
    LinearLayout layMain;
    @Bind(R.id.tv_addmembercategory)
    TextView tvAddmembercategory;

    @OnClick({R.id.tv_addmembercategory})
    public void OnClick(View v) {
        switch (v.getId()) {
            case R.id.tv_addmembercategory:


                break;

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memberaddcategory, HAVETYPE);
        ButterKnife.bind(this);
        initViewAndData();
    }

    /**
     * 初始化View
     */
    private void initViewAndData() {
        getIntent = getIntent();
        String nametype = getIntent.getStringExtra("type");
        setPageTitle(nametype);

    }


}




