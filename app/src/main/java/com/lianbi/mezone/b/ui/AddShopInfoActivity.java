package com.lianbi.mezone.b.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.lianbi.mezone.b.httpresponse.MyResultCallback;
import com.xizhi.mezone.b.R;

import cn.com.hgh.utils.ContentUtils;
import cn.com.hgh.utils.Result;
import cn.com.hgh.view.AddressPopView;

/**
 * 完善店铺信息
 *
 * @author hongyu.yang
 * @time 下午12:28:32
 * @date 2016-1-13
 */
public class AddShopInfoActivity extends BaseActivity {

    TextView tvAddShopInfoIndustryclass;
    TextView tvAddShopInfoProvincialcity;
    TextView tvAddShopInfoShopname;
    private static final int REQUEST_TYPE = 1699;
    private String  parant_id, parant_name;
    private AddressPopView mAddressPopView;
    //要切换店铺的ID
    private String  businessid="";
    private String  shopname="";
    String provinceId;
    String cityCode;
    String areaCode;
    private  int    fromwhich=0;
    private  final int    FROMLOGINPAGE=1;
    private  final int    FROMCHANGESHOP=2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_add_shopinfo, HAVETYPE);
        businessid=BusinessId;
        initView();
        setLisenter();
    }


    /**
     * 初始化View
     */
    private void initView() {
        tvAddShopInfoIndustryclass=(TextView)findViewById(R.id.tv_addShopInfo_industryclass);
        tvAddShopInfoProvincialcity=(TextView)findViewById(R.id.tv_addShopInfo_provincialcity);
        tvAddShopInfoShopname=(TextView)findViewById(R.id.tv_addShopInfo_shopname);
        setPageTitle("完善店铺信息");
        setPageRightText("提交");
        shopname=getIntent().getStringExtra("shopname");
        fromwhich=getIntent().getIntExtra("fromwhich",0);
        String businessid=getIntent().getStringExtra("businessid");
        if(!TextUtils.isEmpty(businessid)) {
            this.businessid = businessid;
        }
        if(!TextUtils.isEmpty(shopname)) {
            tvAddShopInfoShopname.setText(shopname);
        }else{
            tvAddShopInfoShopname.setText(ShopName);

        }
    }
    /**
     * 添加监听
     */
    private void setLisenter() {
        tvTitleRight.setOnClickListener(this);
        tvAddShopInfoIndustryclass.setOnClickListener(this);
        tvAddShopInfoProvincialcity.setOnClickListener(this);
    }

    @Override
    protected void onChildClick(View view) {
        switch (view.getId()) {
            case R.id.tv_addShopInfo_industryclass:
                Intent intent_type = new Intent(this, SelectTypeActivity.class);
                intent_type.putExtra("isMyShop", false);
                startActivityForResult(intent_type, REQUEST_TYPE);
              break;
            case R.id.tv_addShopInfo_provincialcity:

                mAddressPopView = new AddressPopView(AddShopInfoActivity.this, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        switch (view.getId()) {
                            case R.id.tv_guanbi:
                                mAddressPopView.dismiss();
                                break;
                            case R.id.tv_wancheng:
                                String province = mAddressPopView.mCurrentProviceName;//省
                                provinceId = mAddressPopView.mCurrentProviceCode;//省Code
                                String city = mAddressPopView.mCurrentCityName;//市
                                cityCode = mAddressPopView.mCurrentCityCode;//市Code
                                String county = mAddressPopView.mCurrentDistrictName;//县
                                areaCode = mAddressPopView.mCurrentZipCode;//县Code
                                System.out.println("province"+province);
                                System.out.println("provinceCode"+provinceId);
                                System.out.println("city"+city);
                                System.out.println("cityCode"+cityCode);
                                System.out.println("county"+county);
                                System.out.println("zipcode"+areaCode);
                                mAddressPopView.dismiss();
                                tvAddShopInfoProvincialcity.setText(province
                                        +city+county);
                                break;
                        }
                    }
                });

                mAddressPopView.showAtLocation(AddShopInfoActivity.this
                        .findViewById(R.id.tv_addShopInfo_provincialcity), Gravity.BOTTOM
                        | Gravity.CENTER_HORIZONTAL, 0, 0);
                break;
        }
    }
    @Override
    public void onTitleRightClickTv() {

        submitInfo();
    }

    public void  submitInfo(){

        try {
            okHttpsImp.updateBusinessComplement(
                    uuid,
                    "app",
                    reqTime,
                    businessid,
                    parant_id,
                    provinceId,
                    cityCode,
                    areaCode,
                    new MyResultCallback<String>() {

                        @Override
                        public void onResponseResult(Result result) {
                            ContentUtils.showMsg(AddShopInfoActivity.this,
                                    "完善店铺信息成功");
                            switch (fromwhich){
                                case  FROMLOGINPAGE:
                                    Intent intentmain = new Intent();
                                    intentmain.setClass(AddShopInfoActivity.this, MainActivity.class);
                                    setResult(RESULT_OK, intentmain);
                                    startActivity(intentmain);
                                    finish();
                                break;
                                case  FROMCHANGESHOP:
                                    Intent intentchangeshop = new Intent();
                                    intentchangeshop.setClass(AddShopInfoActivity.this, ChangeShopActivity.class);
                                    setResult(RESULT_OK, intentchangeshop);
                                    startActivity(intentchangeshop);
                                    finish();
                                break;
                            }


                        }

                        @Override
                        public void onResponseFailed(String msg) {

                            ContentUtils.showMsg(AddShopInfoActivity.this,
                                    "完善店铺信息失败");
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    /**
     *
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            super.onActivityResult(requestCode, resultCode, data);
        } catch (Exception e) {
        } finally {
            if (resultCode == RESULT_OK) {
                switch (requestCode) {
                    case REQUEST_TYPE:
                        parant_id = data.getStringExtra("parant_id");
                        parant_name = data.getStringExtra("parant_name");
                        tvAddShopInfoIndustryclass.setText(parant_name);

                    break;
                }
            }
        }
    }
}
