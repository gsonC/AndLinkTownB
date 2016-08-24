package com.lianbi.mezone.b.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.lianbi.mezone.b.bean.MemberClassify;
import com.lianbi.mezone.b.httpresponse.MyResultCallback;
import com.xizhi.mezone.b.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.hgh.utils.Result;
import cn.com.hgh.view.HttpDialog;

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
    @Bind(R.id.tv_specificfigures)
    TextView tvSpecificfigures;
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

    @Bind(R.id.lay_numofmembers)
    LinearLayout lay_numofmembers;
    HttpDialog dialog;


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
        dialog = new HttpDialog(this);
        getIntent = getIntent();
        MemberClassify  memberclassify = (MemberClassify)getIntent.getSerializableExtra("info");
        String membertypeId=memberclassify.getTypeId();
        String nametype = getIntent.getStringExtra("type");
        setPageTitle(nametype);
        if (nametype.equals("分类详情")) {
            setPageRightText("修改");
            getMemberTypedetail(membertypeId);

        }else if(nametype.equals("新增分类")){
            setPageRightText("保存");
            lay_numofmembers.setVerticalGravity(View.GONE);
        }

    }

    public  void getMemberTypedetail(String membertypeId){
        try {
            okHttpsImp.getMemberTypedetaiL(new MyResultCallback<String>() {

                @Override
                public void onResponseResult(Result result) {
                    String reString = result.getData();
                    if (reString != null) {
                        MemberClassify memberclassify = JSON.parseObject(reString,
                                MemberClassify.class);

                        if (memberclassify != null) {
                            if (!TextUtils.isEmpty(memberclassify.getTypeName())) {
                                tvClassifyvalue.setText(memberclassify.getTypeName());
                            }
                            if (!TextUtils.isEmpty(memberclassify.getTypeDiscountRatio())) {
                                tvRadiovalue.setText(memberclassify.getTypeDiscountRatio());
                            }
                            if (!TextUtils.isEmpty(String.valueOf(memberclassify.getTypeMaxDiscount()))) {
                                tvMaxidiscountvalue.setText(String.valueOf(memberclassify.getTypeMaxDiscount()));
                            }
                            if (!TextUtils.isEmpty(String.valueOf(memberclassify.getTypeConditionMin()))) {
                                etRangebefore.setText(String.valueOf(memberclassify.getTypeConditionMin()));
                            }
                            if (!TextUtils.isEmpty(String.valueOf(memberclassify.getTypeConditionMin()))) {
                                etRangeafter.setText(String.valueOf(memberclassify.getTypeConditionMax()));
                            }
                            if (!TextUtils.isEmpty(String.valueOf(memberclassify.getThisTypeCount()))) {
                                tvSpecificfigures.setText(String.valueOf(memberclassify.getThisTypeCount()));
                            }

                        }
                        }
                    dialog.dismiss();
                }

                @Override
                public void onResponseFailed(String msg) {
                    dialog.dismiss();
                }
            }, userShopInfoBean.getBusinessId(),membertypeId, reqTime, uuid);
        } catch (Exception e) {
            e.printStackTrace();
        }



    }
    @Override
    protected void onTitleRightClickTv() {
        super.onTitleRightClickTv();
        finish();
    }
}





