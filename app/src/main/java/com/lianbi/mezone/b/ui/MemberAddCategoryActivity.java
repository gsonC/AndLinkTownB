package com.lianbi.mezone.b.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
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
import cn.com.hgh.utils.AbStrUtil;
import cn.com.hgh.utils.ContentUtils;
import cn.com.hgh.utils.REGX;
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
    @Bind(R.id.tv_membership)
    TextView tvMembership;
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
    boolean iserrorvalue;
    HttpDialog dialog;
    String  membertypeId;
    String  nametype;

    String  typeName;
    String  typeId;
    String  typeDiscountRatio;
    String  typeMaxDiscount;
    String  typeConditionMin;
    String  typeConditionMax;
    @OnClick({R.id.tv_addmembercategory,R.id.tv_membership})
    public void OnClick(View v) {
        switch (v.getId()) {
            case R.id.tv_addmembercategory:


            break;
            case R.id.tv_membership:

              Intent  intent=new  Intent();
              intent.setClass(MemberAddCategoryActivity.this,MembersListActivity.class);
              intent.putExtra("typeId",membertypeId);
              startActivity(intent);
            break;

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memberaddcategory, HAVETYPE);
        ButterKnife.bind(this);
        listenDiscountRatio();
        initViewAndData();
    }

    /**
     * 初始化View
     */
    private void initViewAndData() {
        dialog = new HttpDialog(this);
        getIntent = getIntent();
        nametype = getIntent.getStringExtra("type");
        setPageTitle(nametype);
        if (nametype.equals("分类详情")) {
            setPageRightText("修改");
            MemberClassify  memberclassify = (MemberClassify)getIntent.getSerializableExtra("info");
            membertypeId=memberclassify.getTypeId();
            lay_numofmembers.setVisibility(View.VISIBLE);
            tvClassifyvalue.setEnabled(true);
            tvRadiovalue.setEnabled(true);
            tvMaxidiscountvalue.setEnabled(true);
            tvWhatmoney.setEnabled(false);
            tvWhatintegral.setEnabled(false);
            etRangebefore.setEnabled(true);
            etRangeafter.setEnabled(true);
            getMemberTypedetail(membertypeId);
        }else if(nametype.equals("新增分类")){
            setPageRightText("保存");
            lay_numofmembers.setVisibility(View.GONE);
            tvClassifyvalue.setEnabled(true);
            tvRadiovalue.setEnabled(true);
            tvMaxidiscountvalue.setEnabled(true);
            tvWhatmoney.setEnabled(false);
            tvWhatintegral.setEnabled(false);
            etRangebefore.setEnabled(true);
            etRangeafter.setEnabled(true);
        }

    }

    public  void  listenDiscountRatio(){
        tvRadiovalue
                .addTextChangedListener(new TextWatcher() {

                    @Override
                    public void onTextChanged(CharSequence s, int start,
                                              int before, int count) {

                    }

                    @Override
                    public void beforeTextChanged(CharSequence s, int start,
                                                  int count, int after) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        String   strdata=s.toString();
                        if(!strdata.equals("")&&!AbStrUtil.indexOfString(strdata,".")&&Integer.parseInt(strdata)>10){
                           iserrorvalue=true;

                           ContentUtils.showMsg(MemberAddCategoryActivity.this, "请输入0.1-10之间的数值");
                          }else{

                           iserrorvalue=false;
                          }

                    }
                });

    }

    public  boolean  verifyData(){
        if (TextUtils.isEmpty(typeName)) {
            if(!typeName.matches(REGX.REGX_CHINESE_CHECK)){
                ContentUtils.showMsg(MemberAddCategoryActivity.this, "请输入正确的会员类别");
                return true;
            }

        }else{
            ContentUtils.showMsg(MemberAddCategoryActivity.this, "请输入会员类别");
            return  false;
        }
        if (TextUtils.isEmpty(typeDiscountRatio)||iserrorvalue==true) {
            ContentUtils.showMsg(MemberAddCategoryActivity.this, "请输入正确的折扣比例");
            return  true;
        }
        if (TextUtils.isEmpty(typeMaxDiscount)) {
            ContentUtils.showMsg(MemberAddCategoryActivity.this, "请输入单笔最高优惠");
            return  true;
        }
        if (TextUtils.isEmpty(typeConditionMin)) {
            ContentUtils.showMsg(MemberAddCategoryActivity.this, "请输入累计消费下限");
            return  true;
        }
        if (TextUtils.isEmpty(typeConditionMax)) {
            ContentUtils.showMsg(MemberAddCategoryActivity.this, "请输入累计消费上限");
            return  true;
        }
       return  false;
    }
    /*查询此分类详情**/
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
                            if (!TextUtils.isEmpty(memberclassify.getTypeId())) {
                            }

                            if (!TextUtils.isEmpty(memberclassify.getTypeName())) {
                                tvClassifyvalue.setText(memberclassify.getTypeName());
                            }
                            if (memberclassify.getTypeDiscountRatio()!=0) {
                                double discountratio=memberclassify.getTypeDiscountRatio()*0.1;
                                tvRadiovalue.setText(String.valueOf(discountratio));
                            }else{tvRadiovalue.setText(String.valueOf(0));}
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
    /*修改
    此分类详情**/
    public  void getUpDateMemberType(String membertypeId){
        typeName=tvClassifyvalue.getText().toString();
        typeDiscountRatio=tvRadiovalue.getText().toString();
        typeMaxDiscount=tvMaxidiscountvalue.getText().toString();
        typeConditionMin=etRangebefore.getText().toString();
        typeConditionMax=etRangeafter.getText().toString();
        if(verifyData()){
                return;
            }
        if(AbStrUtil.indexOfString(typeDiscountRatio,".")){
            typeDiscountRatio = String.valueOf((int)(Double.parseDouble(typeDiscountRatio)* 10));
        }else {
            typeDiscountRatio = String.valueOf(Integer.parseInt(typeDiscountRatio) * 10);
        }
        Log.i("tag","折扣比例-295------->"+typeDiscountRatio);
        try {
            okHttpsImp.upDateMemberCategories(new MyResultCallback<String>() {

                @Override
                public void onResponseResult(Result result) {
                    String reString = result.getData();
                    ContentUtils.showMsg(MemberAddCategoryActivity.this,"修改成功");
                    Intent intent = new Intent(MemberAddCategoryActivity.this,MemberClassify.class);
                    setResult(RESULT_OK, intent);
                    finish();

                    if (reString != null) {


                    }
                    dialog.dismiss();
                }

                @Override
                public void onResponseFailed(String msg) {
                    dialog.dismiss();
                }
            }, userShopInfoBean.getBusinessId(),typeName,membertypeId,
                    typeDiscountRatio,typeMaxDiscount,typeConditionMin,typeConditionMax, reqTime, uuid);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /*添加
    新的会员分类**/
    public  void getAddMemberType(){
        typeName=tvClassifyvalue.getText().toString();
        typeDiscountRatio=tvRadiovalue.getText().toString();
        typeMaxDiscount=tvMaxidiscountvalue.getText().toString();
        typeConditionMin=etRangebefore.getText().toString();
        typeConditionMax=etRangeafter.getText().toString();
        if(verifyData()){
            return;
        }
        if(AbStrUtil.indexOfString(typeDiscountRatio,".")){
            typeDiscountRatio = String.valueOf((int)(Double.parseDouble(typeDiscountRatio)* 10));
        }else {
            typeDiscountRatio = String.valueOf(Integer.parseInt(typeDiscountRatio) * 10);
        }
        Log.i("tag","折扣比例-340------->"+typeDiscountRatio);

        try {
            okHttpsImp.addMemberCategories(new MyResultCallback<String>() {

                        @Override
                        public void onResponseResult(Result result) {
                            String reString = result.getData();
                            ContentUtils.showMsg(MemberAddCategoryActivity.this,"添加成功");
                            Intent intent = new Intent(MemberAddCategoryActivity.this,MemberClassify.class);
                            setResult(RESULT_OK, intent);
                            finish();

                            if (reString != null) {


                            }
                            dialog.dismiss();
                            }

                             @Override
                            public void onResponseFailed(String msg) {
                                     dialog.dismiss();
                                     }
                            }, userShopInfoBean.getBusinessId(),typeName,
                    typeDiscountRatio,typeMaxDiscount,typeConditionMin,typeConditionMax, reqTime, uuid);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onTitleRightClickTv() {
        super.onTitleRightClickTv();
        if (nametype.equals("分类详情")) {
            getUpDateMemberType(membertypeId);

        }else if(nametype.equals("新增分类")){
            getAddMemberType();

        }
    }
}





