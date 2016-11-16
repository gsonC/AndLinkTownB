package com.lianbi.mezone.b.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.lianbi.mezone.b.httpresponse.MyResultCallback;
import com.xizhi.mezone.b.R;

import org.json.JSONObject;

import cn.com.hgh.utils.ContentUtils;
import cn.com.hgh.utils.Result;

/*
* 添加桌位
* */
public class AddTablesetActivity extends BaseActivity implements TextView.OnEditorActionListener {

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
        et_table_persion_num.setOnEditorActionListener(this);

    }

    @Override
    protected void onChildClick(View view) {
        super.onChildClick(view);
        switch (view.getId()) {
            case R.id.tv_addtableset:// 确定添加
                postAddTable();
                break;
        }
    }

    private void postAddTable() {
        String tablename = et_tablename.getText().toString().trim();
        if (TextUtils.isEmpty(tablename)) {
            ContentUtils.showMsg(AddTablesetActivity.this, "桌位名称不能为空");
            et_tablename.requestFocus();
            return;
        }
        String presetCount = et_table_persion_num.getText().toString().trim();
        if (TextUtils.isEmpty(presetCount)) {
            ContentUtils.showMsg(AddTablesetActivity.this, "用餐人数不能为空");
            et_table_persion_num.requestFocus();
            return;
        }
        if (presetCount.startsWith("0")) {
            ContentUtils.showMsg(AddTablesetActivity.this, "用餐人数不能以0开头");
            et_table_persion_num.requestFocus();
            return;
        }
        getAddTable(tablename, presetCount);
    }

    private void getAddTable(String tablename, String presetCount) {
        okHttpsImp.getAddTable(new MyResultCallback<String>() {

            @Override
            public void onResponseResult(Result result) {
                finish();
                ContentUtils.showMsg(AddTablesetActivity.this, "添加成功");
            }

            @Override
            public void onResponseFailed(String msg) {
            }
        }, userShopInfoBean.getBusinessId(), tablename, presetCount, userShopInfoBean.getUserId());
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (v.getId() == R.id.et_table_persion_num && actionId == EditorInfo.IME_ACTION_DONE) {
            postAddTable();
            return true;
        }
        return false;
    }
}
