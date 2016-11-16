package com.lianbi.mezone.b.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.lianbi.mezone.b.httpresponse.MyResultCallback;
import com.xizhi.mezone.b.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.hgh.utils.AbDateUtil;
import cn.com.hgh.utils.ContentUtils;
import cn.com.hgh.utils.REGX;
import cn.com.hgh.utils.Result;
import cn.com.hgh.view.DialogCommon;

/*
 * @创建者     master
 * @创建时间   2016/10/19 18:18
 * @描述		   首页商圈吆喝列表
 *
 * @更新者     $Author$
 * @更新时间   $Date$
 * @更新描述
 */
public class LeaguesPublishYellActivity extends BaseActivity {


    @Bind(R.id.et_leaguespublishyell_title)
    EditText etLeaguespublishyellTitle;
    @Bind(R.id.et_leaguespublishyell_contactnum)
    EditText etLeaguespublishyellContactnum;
    @Bind(R.id.et_leaguespublishyell_saysomething)
    EditText etLeaguespublishyellSaysomething;
    @Bind(R.id.lay_leaguespublishyell_publish)
    LinearLayout layLeaguespublishyellPublish;

    String strTitle="";
    String strContactnum="";
    String strSaysomething="";

    @OnClick({R.id.lay_leaguespublishyell_publish})
    public void OnClick(View v) {
        switch (v.getId()) {

            case R.id.lay_leaguespublishyell_publish:
                if(verify()){
                    goPublishYell();
                }
            break;

        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_leaguespublishyell, NOTYPE);
        ButterKnife.bind(this);
        initData();
    }

    /**
     * 初始化View
     */
    private void initData() {
        setPageTitle("发布吆喝");

    }
    private boolean verify() {
        String strTitle = etLeaguespublishyellTitle.getText().toString().trim();//
        String strContactnum = etLeaguespublishyellContactnum.getText().toString().trim();//
        String strSaysomething = etLeaguespublishyellSaysomething.getText().toString().trim();//
        if (TextUtils.isEmpty(strTitle)) {
            ContentUtils.showMsg(LeaguesPublishYellActivity.this, "请输入标题");
            return  false;
        }
        if (TextUtils.isEmpty(strSaysomething)) {
            ContentUtils.showMsg(LeaguesPublishYellActivity.this, "请输入吆喝内容");
            return  false;
        }
        if (!TextUtils.isEmpty(strContactnum)) {
            if (!strContactnum.matches(REGX.REGX_MOBILE)) {
                ContentUtils.showMsg(LeaguesPublishYellActivity.this, "请输入正确的手机号码");
                return  false;
            }
        } else {
            ContentUtils.showMsg(LeaguesPublishYellActivity.this, "请输入手机号码");
            return   false;
        }
        this.strTitle=strTitle;
        this.strContactnum=strContactnum;
        this.strSaysomething=strSaysomething;
        return  true;
    }
    private   void  goPublishYell(){
        Log.i("tag","商铺名称----->"+ShopName);
        String  pushTime= AbDateUtil.getDateTimeNow();
        try {
            okHttpsImp.getAddBusinessDynamic(
                    BusinessId,//businessId"BD2016052013475900000010"
                    "",//area
                    areaCode,//businessCircle"310117"
                    "",//messageType
                    "",//pushScope
                    pushTime,//pushTime
                    ShopName,//businessName
                    strContactnum,//phone
                    strTitle,//messageTitle
                    strSaysomething,//messageContent
                    shopRovinceid,//provinces"310000"
                    cityCode,//city"310117"
                    address,//address"上海市"
                    uuid,//serNum
                    "app",//source
                    reqTime,//reqTime
                    new MyResultCallback<String>() {
                        @Override
                        public void onResponseResult(Result result) {
                            String reString = result.getData();
                            DialogCommon dialog = new DialogCommon(LeaguesPublishYellActivity.this) {
                                @Override
                                public void onCheckClick() {
                                    dismiss();
                                    Intent intent = new Intent(LeaguesPublishYellActivity.this, LeaguesYellListActivity.class);
                                    setResult(RESULT_OK, intent);
                                    finish();
                                }

                                @Override
                                public void onOkClick() {
                                    dismiss();
                                    etLeaguespublishyellTitle.setText("");
                                    etLeaguespublishyellContactnum.setText("");
                                    etLeaguespublishyellSaysomething.setText("");
                                    strTitle="";
                                    strContactnum="";
                                    strSaysomething="";
                                }
                            };
                            dialog.setCancelable(false);
                            dialog.setCanceledOnTouchOutside(false);
                            dialog.setTextTitle("发布成功");
                            dialog.setTv_dialog_common_ok("再发一条");
                            dialog.setTv_dialog_common_cancel("查看详情");
                            dialog.show();

                        }

                        @Override
                        public void onResponseFailed(String msg) {
                            DialogCommon dialog = new DialogCommon(LeaguesPublishYellActivity.this) {
                                @Override
                                public void onCheckClick() {
                                    dismiss();
                                }
                                @Override
                                public void onOkClick() {
                                    dismiss();

                                }
                            };
                            dialog.setCancelable(false);
                            dialog.setCanceledOnTouchOutside(false);
                            dialog.setTextTitle("发布失败");
                            dialog.setTv_dialog_common_ok("重新发送");
                            dialog.setTv_dialog_common_cancel("取消");
                            dialog.show();

                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
