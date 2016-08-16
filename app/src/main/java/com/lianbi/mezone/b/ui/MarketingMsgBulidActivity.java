package com.lianbi.mezone.b.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lianbi.mezone.b.bean.ServiceMallBean;
import com.xizhi.mezone.b.R;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
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
    @Bind(R.id.tv_choicetemplate)
    TextView tv_choicetemplate;
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
    @Bind(R.id.txt_sendmsg)
    TextView txtSendmsg;
    private ArrayList<ServiceMallBean> mData = new ArrayList<ServiceMallBean>();
    private ArrayList<ServiceMallBean> mDatas = new ArrayList<ServiceMallBean>();
    HttpDialog dialog;

    @OnClick({R.id.txt_sendmsg,R.id.tv_choicetemplate})
    public void OnClick(View v) {
        switch (v.getId()) {
            case R.id.txt_sendmsg:



            break;
            case R.id.tv_choicetemplate:

            simpleJump(MarketingSMSexampleActivity.class);

            break;

        }
    }
    private void  simpleJump(Class activity){
        Intent intent=new Intent();
        intent.setClass(MarketingMsgBulidActivity.this,activity);
        startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_marketingmsgbulid, HAVETYPE);
        ButterKnife.bind(this);
        initViewAndData();
    }

    /**
     * 初始化View
     */
    private void initViewAndData() {
        setPageTitle("新建营销短信");
        dialog = new HttpDialog(this);
    }



}




