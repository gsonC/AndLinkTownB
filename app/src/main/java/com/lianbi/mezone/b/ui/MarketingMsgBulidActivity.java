package com.lianbi.mezone.b.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lianbi.mezone.b.bean.ServiceMallBean;
import com.lianbi.mezone.b.httpresponse.MyResultCallback;
import com.xizhi.mezone.b.R;

import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.hgh.utils.ContentUtils;
import cn.com.hgh.utils.Result;
import cn.com.hgh.view.DialogCommon;
import cn.com.hgh.view.HttpDialog;

/**
 * 新建营销短信
 *
 * @author
 * @time
 * @date
 */
public class MarketingMsgBulidActivity extends BaseActivity {


    @Bind(R.id.txt_msg)
    TextView txtMsg;
    @Bind(R.id.txt_alreadysendnum)
    TextView txtAlreadysendnum;
    @Bind(R.id.txt_remainsendmsg)
    TextView txtRemainsendmsg;
    @Bind(R.id.txt_remainsendnum)
    TextView txtRemainsendnum;
    @Bind(R.id.btn_msgpay)
    TextView btnMsgpay;
    @Bind(R.id.ray_top)
    LinearLayout rayTop;
    @Bind(R.id.v_01)
    View v01;
    @Bind(R.id.tv_editmsg)
    TextView tvEditmsg;
    @Bind(R.id.tv_choicetemplate)
    TextView tvChoicetemplate;
    @Bind(R.id.et_sendcontext)
    EditText etSendcontext;
    @Bind(R.id.btn_sendobject)
    TextView btnSendobject;
    @Bind(R.id.btn_senduser)
    TextView btnSenduser;
    @Bind(R.id.lay_tagobject)
    LinearLayout layTagobject;
    @Bind(R.id.btn_sendmsgnum)
    TextView btnSendmsgnum;
    @Bind(R.id.btn_msgnum)
    TextView btnMsgnum;
    @Bind(R.id.lay_tagnum)
    LinearLayout layTagnum;
    @Bind(R.id.v_02)
    View v02;
    @Bind(R.id.ray_editarea)
    RelativeLayout rayEditarea;
    @Bind(R.id.txt_sendmsg)
    TextView txtSendmsg;
    private ArrayList<ServiceMallBean> mData = new ArrayList<ServiceMallBean>();
    private ArrayList<ServiceMallBean> mDatas = new ArrayList<ServiceMallBean>();
    HttpDialog dialog;
    //可选模板
    private static final int REQUEST_CODE_TEMPLATE_RESULT = 1009;
    //可选会员
    private static final int REQUEST_CODE_MEMBER_RESULT = 1010;
    private String  smsinfo;
    private String  templateID;

    private String  sendPhones="";
    private String  sendtotal="";

    @OnClick({R.id.txt_sendmsg, R.id.tv_choicetemplate, R.id.btn_msgpay, R.id.btn_senduser})
    public void OnClick(View v) {
        switch (v.getId()) {
            case R.id.txt_sendmsg:
                sendShortmsg();
                break;
            case R.id.tv_choicetemplate:

                Intent intenttemplate = new Intent(this, MarketingSMSexampleActivity.class);
                intenttemplate.putExtra(MarketingSMSexampleActivity.TEMPLATE_TYPE, "B");
                startActivityForResult(intenttemplate, REQUEST_CODE_TEMPLATE_RESULT);
                break;
            case R.id.btn_msgpay:

                simpleJump(MarketingBuySMSActivity.class);


                break;
            case R.id.btn_senduser:

                Intent intentmember = new Intent(this, MarketingSelectMemberActivity.class);
                startActivityForResult(intentmember, REQUEST_CODE_MEMBER_RESULT);
                break;
        }
    }
    private  void  sendShortmsg(){
        Log.i("tag","113-getBusinessId------>"+BusinessId);
        Log.i("tag","113-templateID------>"+templateID);
        Log.i("tag","114-sendPhones------>"+sendPhones);
        Log.i("tag","115----reqTime-->"+reqTime);
        Log.i("tag","116----uuid----->"+uuid);
        Log.i("tag","129--ShopName--->"+ShopName);

        if(TextUtils.isEmpty(templateID)){
            ContentUtils.showMsg(MarketingMsgBulidActivity.this,"请选择短信模板");

            return;
        }
        if(TextUtils.isEmpty(sendPhones)){
            ContentUtils.showMsg(MarketingMsgBulidActivity.this,"请选择发送会员");
            return;
        }
        try {
            okHttpsImp.smsBulkSend(new MyResultCallback<String>() {

                @Override
                public void onResponseResult(Result result) {
                    String reString = result.getData();
                    successDialog();
                    if (reString != null) {
                        JSONObject jsonObject;
//                        ContentUtils.showMsg(MarketingMsgBulidActivity.this,"发送成功");

                        try {


                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onResponseFailed(String msg) {

                }
            },BusinessId,ShopName,templateID,sendPhones, reqTime, uuid);



        }catch (Exception e){e.printStackTrace();}

    }

    private  void  successDialog(){
        DialogCommon dialogCommon = new DialogCommon(
                MarketingMsgBulidActivity.this) {

            @Override
            public void onCheckClick() {
                dismiss();
            }

            @Override
            public void onOkClick() {
                dismiss();
            }

        };
        dialogCommon.setTextTitle("短信发送成功");
        dialogCommon.setTv_dialog_common_ok("确 定");
        dialogCommon.setTv_dialog_common_cancelV(View.GONE);
        dialogCommon.show();
    }

    private void simpleJump(Class activity) {
        Intent intent = new Intent();
        intent.setClass(MarketingMsgBulidActivity.this, activity);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_marketingmsgbulid, HAVETYPE);
        ButterKnife.bind(this);
        initViewAndData();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CODE_TEMPLATE_RESULT:
                    if(data!=null) {
                        smsinfo = data.getStringExtra("info");
                        etSendcontext.setText(smsinfo);
                        templateID= data.getStringExtra("templateID");
                    }
                    break;
                case REQUEST_CODE_MEMBER_RESULT:
                    if(data!=null) {
                        sendPhones = data.getStringExtra("sendphones");
                        sendtotal= data.getStringExtra("sendtotal");
                        btnMsgnum.setText(sendtotal);
                    }
                    break;
                default:
                    break;
            }
        }
    }
    /**
     * 初始化View
     */
    private void initViewAndData() {
        setPageTitle("新建营销短信");
        dialog = new HttpDialog(this);
        String smstotalSendNum=getIntent().getStringExtra("smstotalSendNum");
        String remainSendNum=getIntent().getStringExtra("remainSendNum");
        if(smstotalSendNum!=null&&!smstotalSendNum.equals("")){
            txtAlreadysendnum.setText(smstotalSendNum);
        }
        if(remainSendNum!=null&&!remainSendNum.equals("")){
            txtRemainsendnum.setText(remainSendNum);
        }
    }


}




