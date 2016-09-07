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
import cn.com.hgh.utils.AbStrUtil;
import cn.com.hgh.utils.ContentUtils;
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
    MemberClassify  mMemberClassify;
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
//      listenDiscountRatio();
        initViewAndData();
    }

    /**
     * 初始化View
     */
    String name;
    private void initViewAndData() {
        dialog = new HttpDialog(this);
        getIntent = getIntent();
        nametype = getIntent.getStringExtra("type");
        setPageTitle(nametype);
        mMemberClassify=(MemberClassify)getIntent.getSerializableExtra("info");
        if(mMemberClassify!=null&&mMemberClassify.getTypeName().equals("普通会员")){
            tvClassifyvalue.setEnabled(false);
            tvRadiovalue.setEnabled(false);
            tvMaxidiscountvalue.setEnabled(false);
            etRangebefore.setEnabled(false);
            etRangeafter.setEnabled(false);
            setPageRightTextVisibility(View.GONE);
        }else
        if(mMemberClassify!=null&&!mMemberClassify.getTypeName().equals("普通会员"))
        {
            tvClassifyvalue.setEnabled(false);
            tvRadiovalue.setEnabled(false);
            tvMaxidiscountvalue.setEnabled(false);
            etRangebefore.setEnabled(false);
            etRangeafter.setEnabled(false);
            setPageRightText("修改");
            setPageRightTextVisibility(View.VISIBLE);
        }

        if (nametype.equals("分类详情")) {
            name=mMemberClassify.getTypeName();
            membertypeId=mMemberClassify.getTypeId();
            lay_numofmembers.setVisibility(View.VISIBLE);
            tvWhatmoney.setEnabled(false);
            tvWhatintegral.setEnabled(false);
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

    private  boolean  checkDiscountRatio(String  strdata){
        int  lenth=strdata.length();
        switch (lenth){
            case 1:
             if(strdata.contains(".")) {
                 ContentUtils.showMsg(MemberAddCategoryActivity.this, "请输入0.1-10之间的数值");
                 return  true;
             }
               break;
            case 2:
             if(strdata.contains(".")) {
                ContentUtils.showMsg(MemberAddCategoryActivity.this, "请输入0.1-10之间的数值");
                return  true;
             }else{
                int  inttwo=Integer.parseInt(strdata);
                if(inttwo>10){
                    ContentUtils.showMsg(MemberAddCategoryActivity.this, "请输入0.1-10之间的数值");
                return  true;
                }
             }
                break;
            case 3:
              if(strdata.contains(".")) {
                  if(strdata.charAt(0)=='.'||strdata.charAt(lenth-1)=='.'){
                      ContentUtils.showMsg(MemberAddCategoryActivity.this, "请输入0.1-10之间的数值");
                      return  true;
                  }
              }else{
                int  inttress=Integer.parseInt(strdata);
                if(inttress>10){
                   ContentUtils.showMsg(MemberAddCategoryActivity.this, "请输入0.1-10之间的数值");
                   return  true;
                }
              }
            break;
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

        if (TextUtils.isEmpty(typeName)) {
            ContentUtils.showMsg(MemberAddCategoryActivity.this, "请输入正确的会员类别");
            return;
        }
        if (TextUtils.isEmpty(typeDiscountRatio)||checkDiscountRatio(typeDiscountRatio)) {
            ContentUtils.showMsg(MemberAddCategoryActivity.this, "请输入正确的折扣比例");
            return;
        }
        if (TextUtils.isEmpty(typeMaxDiscount)) {
            ContentUtils.showMsg(MemberAddCategoryActivity.this, "请输入单笔最高优惠");
            return;
        }
        if (TextUtils.isEmpty(typeConditionMin)) {
            ContentUtils.showMsg(MemberAddCategoryActivity.this, "请输入累计消费下限");
            return;
        }
        if (TextUtils.isEmpty(typeConditionMax)) {
            ContentUtils.showMsg(MemberAddCategoryActivity.this, "请输入累计消费上限");
            return;
        }
        int  int_maxdiscount=Integer.parseInt(typeMaxDiscount)*100;
        int  int_conditionmin=Integer.parseInt(typeConditionMin)*100;
        int  int_conditionmax=Integer.parseInt(typeConditionMax)*100;
        typeMaxDiscount=String.valueOf(int_maxdiscount);
        typeConditionMin=String.valueOf(int_conditionmin);
        typeConditionMax=String.valueOf(int_conditionmax);

        if(AbStrUtil.indexOfString(typeDiscountRatio,".")){
            typeDiscountRatio = String.valueOf((int)(Double.parseDouble(typeDiscountRatio)* 10));
        }else {
            typeDiscountRatio = String.valueOf(Integer.parseInt(typeDiscountRatio) * 10);
        }
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
        if (TextUtils.isEmpty(typeName)) {
            ContentUtils.showMsg(MemberAddCategoryActivity.this, "请输入正确的会员类别");
            return;
        }
        if (TextUtils.isEmpty(typeDiscountRatio)||checkDiscountRatio(typeDiscountRatio)) {
            ContentUtils.showMsg(MemberAddCategoryActivity.this, "请输入正确的折扣比例");
            return;
        }
        if (TextUtils.isEmpty(typeMaxDiscount)) {
            ContentUtils.showMsg(MemberAddCategoryActivity.this, "请输入单笔最高优惠");
            return;
        }
        if (TextUtils.isEmpty(typeConditionMin)) {
            ContentUtils.showMsg(MemberAddCategoryActivity.this, "请输入累计消费下限");
            return;
        }
        if (TextUtils.isEmpty(typeConditionMax)) {
            ContentUtils.showMsg(MemberAddCategoryActivity.this, "请输入累计消费上限");
            return;
        }
        int  int_maxdiscount=Integer.parseInt(typeMaxDiscount)*100;
        int  int_conditionmin=Integer.parseInt(typeConditionMin)*100;
        int  int_conditionmax=Integer.parseInt(typeConditionMax)*100;
        typeMaxDiscount=String.valueOf(int_maxdiscount);
        typeConditionMin=String.valueOf(int_conditionmin);
        typeConditionMax=String.valueOf(int_conditionmax);

        if(AbStrUtil.indexOfString(typeDiscountRatio,".")){
            typeDiscountRatio = String.valueOf((int)(Double.parseDouble(typeDiscountRatio)* 10));
        }else {
            typeDiscountRatio = String.valueOf(Integer.parseInt(typeDiscountRatio) * 10);
        }

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
            if(getPageRightText().equals("修改")){
                setPageRightText("保存");
                tvClassifyvalue.setEnabled(true);
                tvRadiovalue.setEnabled(true);
                tvMaxidiscountvalue.setEnabled(true);
            }else
            if(getPageRightText().equals("保存")){
               getUpDateMemberType(membertypeId);
            }
        }else if(nametype.equals("新增分类")){
            getAddMemberType();

        }
    }
}
//
//
//    public  void  listenDiscountRatio(){
//        tvRadiovalue
//                .addTextChangedListener(new TextWatcher() {
//
//                    @Override
//                    public void onTextChanged(CharSequence s, int start,
//                                              int before, int count) {
//
//                    }
//
//                    @Override
//                    public void beforeTextChanged(CharSequence s, int start,
//                                                  int count, int after) {
//
//                    }
//
//                    @Override
//                    public void afterTextChanged(Editable s) {
//                        String   strdata=s.toString();
//
//                        checkDiscountRatio(strdata);
//                    }
//                });
//
//    }



