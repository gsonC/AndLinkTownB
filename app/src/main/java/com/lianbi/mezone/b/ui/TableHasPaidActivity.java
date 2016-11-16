package com.lianbi.mezone.b.ui;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lianbi.mezone.b.bean.UnPaidOrderBean;
import com.lianbi.mezone.b.httpresponse.MyResultCallback;
import com.xizhi.mezone.b.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.hgh.baseadapter.BaseAdapterHelper;
import cn.com.hgh.baseadapter.QuickAdapter;
import cn.com.hgh.utils.ContentUtils;
import cn.com.hgh.utils.Result;

/*
* 桌位详情-已支付
* */
public class TableHasPaidActivity extends BaseActivity {
    @Bind(R.id.table_name)
    TextView table_name;

    @Bind(R.id.fen_num)
    TextView fen_num;

    @Bind(R.id.num_should_pay)
    TextView num_should_pay;//合计

    @Bind(R.id.actually_paid_amount)
    TextView actually_paid_amount;//实际付款

    @Bind(R.id.has_paid_orders_list_view)
    ListView mListView;

    @Bind(R.id.fantai)
    RelativeLayout fantai;

    private String tableId;

    private QuickAdapter<UnPaidOrderBean> mAdapter;
    private List<UnPaidOrderBean> mData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_has_paid, NOTYPE);
        ButterKnife.bind(this);
        setPageTitle("已支付");
        tableId = getIntent().getStringExtra("TABLEID");
        table_name.setText(getIntent().getStringExtra("TABLENAME"));

        initAdapter();

        addDataToView(getIntent().getStringExtra("DATA"));
    }

    private void addDataToView(String data) {
        JSONObject jsonObject = JSON.parseObject(data);
        fen_num.setText(jsonObject.getString("proNum"));
        num_should_pay.setText(jsonObject.getString("totalOrderMoney"));
    }

    private void initAdapter() {
        mAdapter = new QuickAdapter<UnPaidOrderBean>(TableHasPaidActivity.this, R.layout.table_order_list_view_layout, mData) {
            @Override
            protected void convert(BaseAdapterHelper helper, UnPaidOrderBean item) {
                ((TextView) helper.getView(R.id.time_cn)).setText("支付时间：");
                ImageView avatar = helper.getView(R.id.iv_avatar);//头像
                TextView name = helper.getView(R.id.tv_client_name);
                TextView remark = helper.getView(R.id.remarks);//备注
                TextView order_time = helper.getView(R.id.tv_order_time);//支付时间
                LinearLayout container = helper.getView(R.id.dishes_list_container);
            }
        };
        mListView.setAdapter(mAdapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    @Override
    @OnClick({R.id.fantai})
    protected void onChildClick(View view) {
        super.onChildClick(view);
        if (view.getId() == R.id.fantai) {
            showSetTableFreeDialog(tableId);
        }
    }

    //是否翻桌
    private void showSetTableFreeDialog(final String tableId) {
        final Dialog dialog = new Dialog(TableHasPaidActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = View.inflate(TableHasPaidActivity.this, R.layout.fantai_dialog_layout, null);
        view.findViewById(R.id.positive_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                setTableFree(tableId);//翻桌
            }
        });
        view.findViewById(R.id.negative_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.setContentView(view);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    //修改桌位订单状态(翻台)
    private void setTableFree(String tableId) {
        okHttpsImp.setTableFree(new MyResultCallback<String>() {
            @Override
            public void onResponseResult(Result result) {
                AlertDialog.Builder builder = new AlertDialog.Builder(TableHasPaidActivity.this);
                builder.setCancelable(false);
                builder.setMessage("翻桌成功");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        finish();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.setCanceledOnTouchOutside(false);
                dialog.show();
            }

            @Override
            public void onResponseFailed(String msg) {
                ContentUtils.showMsg(TableHasPaidActivity.this, "翻桌成功");
            }
        }, userShopInfoBean.getBusinessId(), tableId);
    }
}
