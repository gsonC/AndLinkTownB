package com.lianbi.mezone.b.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.lianbi.mezone.b.httpresponse.MyResultCallback;
import com.xizhi.mezone.b.R;

import org.json.JSONObject;

import cn.com.hgh.utils.ContentUtils;
import cn.com.hgh.utils.Result;

/*
* 添加桌面
* */
public class AddTablesetActivity extends BaseActivity {

    private TextView tv_addtableset;
    private EditText et_tablename;
    private EditText et_table_persion_num;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_addtableset, NOTYPE);
        initView();
        setListener();
    }

    private void initView() {
        setPageTitle("添加桌位");
        tv_addtableset = (TextView) findViewById(R.id.tv_addtableset);
        et_tablename = (EditText) findViewById(R.id.et_tablename);
        et_table_persion_num = (EditText) findViewById(R.id.et_table_persion_num);
    }

    private void setListener() {
        tv_addtableset.setOnClickListener(this);
    }

    @Override
    protected void onChildClick(View view) {
        super.onChildClick(view);
        switch (view.getId()) {
            case R.id.tv_addtableset:// 确定添加
                String tablename = et_tablename.getText().toString().trim();
                if (TextUtils.isEmpty(tablename)) {
                    ContentUtils.showMsg(AddTablesetActivity.this, "桌位名称不能为空");
                    return;
                }
                getAddTable(tablename);
                break;

        }
    }

    private void getAddTable(String tablename) {
        okHttpsImp.getAddTable(new MyResultCallback<String>() {

            @Override
            public void onResponseResult(Result result) {
                String reString = result.getData();
                if (reString != null) {
                    JSONObject jsonObject;
                    try {
                        setResult(RESULT_OK);
                        finish();
                        ContentUtils.showMsg(AddTablesetActivity.this, "添加成功");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onResponseFailed(String msg) {

            }
        }, userShopInfoBean.getBusinessId(), tablename);


    }
}
