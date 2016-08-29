package com.lianbi.mezone.b.ui;

import android.content.Intent;
import android.os.Bundle;
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
    @OnClick({R.id.txt_sendmsg, R.id.tv_choicetemplate, R.id.btn_msgpay, R.id.btn_senduser})
    public void OnClick(View v) {
        switch (v.getId()) {
            case R.id.txt_sendmsg:
                sendShortmsg();
                break;
            case R.id.tv_choicetemplate:

                Intent intenttemplate = new Intent(this, MarketingSMSexampleActivity.class);
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
        try {
            okHttpsImp.smsBulkSend(new MyResultCallback<String>() {

                @Override
                public void onResponseResult(Result result) {
                    String reString = result.getData();
                    if (reString != null) {
                        JSONObject jsonObject;
                        ContentUtils.showMsg(MarketingMsgBulidActivity.this,"发送成功");

                        try {


                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onResponseFailed(String msg) {

                }
            }, userShopInfoBean.getBusinessId(),templateID, "", reqTime, uuid);



        }catch (Exception e){e.printStackTrace();}

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
    }


}




