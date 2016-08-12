package com.lianbi.mezone.b.ui;

import android.os.Bundle;
import android.view.View;
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
 * 购买短信
 *
 * @author
 * @time
 * @date
 */
public class MarketingBuySMSActivity extends BaseActivity {

    @Bind(R.id.txt_alreadysendnum)
    TextView txtAlreadysendnum;
    @Bind(R.id.txt_remainsendnum)
    TextView txtRemainsendnum;
    @Bind(R.id.txt_price)
    TextView txtPrice;
    @Bind(R.id.btn_one)
    TextView btnOne;
    @Bind(R.id.btn_two)
    TextView btnTwo;
    @Bind(R.id.ray_one)
    LinearLayout rayOne;
    @Bind(R.id.btn_tress)
    TextView btnTress;
    @Bind(R.id.btn_four)
    TextView btnFour;
    @Bind(R.id.lay_two)
    LinearLayout layTwo;
    @Bind(R.id.btn_five)
    TextView btnFive;
    @Bind(R.id.btn_six)
    TextView btnSix;
    @Bind(R.id.lay_tress)
    LinearLayout layTress;
    @Bind(R.id.txt_buymsm)
    TextView txtBuymsm;
    private ArrayList<ServiceMallBean> mData = new ArrayList<ServiceMallBean>();
    private ArrayList<ServiceMallBean> mDatas = new ArrayList<ServiceMallBean>();
    HttpDialog dialog;

    @OnClick({R.id.txt_buymsm})
    public void OnClick(View v) {
        switch (v.getId()) {
            case R.id.txt_buymsm:


             break;

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_marketingbuysms, HAVETYPE);
        ButterKnife.bind(this);
        initViewAndData();
    }

    /**
     * 初始化View
     */
    private void initViewAndData() {
        setPageTitle("短信购买");
        dialog = new HttpDialog(this);
    }


}




