package com.lianbi.mezone.b.ui;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lianbi.mezone.b.bean.DiningOrderBean;
import com.lianbi.mezone.b.httpresponse.MyResultCallback;
import com.lzy.okgo.request.BaseRequest;
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
import cn.com.hgh.view.AbPullToRefreshView;

/*
* 桌位详情-已支付
* */
public class TableHasPaidActivity extends BaseActivity implements AbPullToRefreshView.OnFooterLoadListener,
        AbPullToRefreshView.OnHeaderRefreshListener {
    @Bind(R.id.table_name)
    TextView table_name;

    @Bind(R.id.fen_num)
    TextView fen_num;

    @Bind(R.id.num_should_pay)
    TextView num_should_pay;//合计

    @Bind(R.id.actually_paid_amount)
    TextView actually_paid_amount;//实际付款

    @Bind(R.id.has_paid_orders_detail)
    AbPullToRefreshView mPullToRefreshView;

    @Bind(R.id.has_paid_orders_list_view)
    ListView mListView;

    @Bind(R.id.fantai)
    RelativeLayout fantai;

    private String tableId;
    /**
     * 正在下拉刷新.
     */
    private boolean mPullRefreshing = false;
    /**
     * 正在加载更多.
     */
    private boolean mPullLoading = false;

    private QuickAdapter<DiningOrderBean> mAdapter;
    private List<DiningOrderBean> mData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_has_paid, NOTYPE);
        ButterKnife.bind(this);
        setPageTitle("已支付");
        tableId = getIntent().getStringExtra("TABLEID");
//            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.android_robot);
//            RoundedBitmapDrawable mRoundedBitmapDrawable = RoundedBitmapDrawableFactory.create(getResources(), bitmap);
//            mRoundedBitmapDrawable.setCircular(isChecked);
//            ImageView image = (ImageView) findViewById(R.id.image);
//            image.setImageDrawable(mRoundedBitmapDrawable);
        table_name.setText(getIntent().getStringExtra("TABLENAME"));

        setListener();

        initAdapter();

        getTableInfo();
    }

    private void initAdapter() {
        mAdapter = new QuickAdapter<DiningOrderBean>(TableHasPaidActivity.this, R.layout.table_order_list_view_layout, mData) {
            @Override
            protected void convert(BaseAdapterHelper helper, DiningOrderBean item) {
                ((TextView) helper.getView(R.id.time_cn)).setText("支付时间：");
                ImageView avatar = helper.getView(R.id.iv_avatar);//头像
                TextView name = helper.getView(R.id.tv_client_name);
                TextView remark = helper.getView(R.id.remarks);//备注
                TextView order_time = helper.getView(R.id.tv_order_time);//支付时间
                LinearLayout container = helper.getView(R.id.dishes_list_container);
            }
        };
    }

    private void setListener() {
        mPullToRefreshView.setOnHeaderRefreshListener(this);
        mPullToRefreshView.setOnFooterLoadListener(this);
    }

    private void getTableInfo() {
        okHttpsImp.tableInfo(new MyResultCallback<String>() {
            @Override
            public void onAfter(@Nullable String s, @Nullable Exception e) {
                super.onAfter(s, e);
                refreshingFinish();
            }

            @Override
            public void onResponseResult(Result result) {

            }

            @Override
            public void onResponseFailed(String msg) {

            }
        }, userShopInfoBean.getUserId(), userShopInfoBean.getBusinessId(), tableId);
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

    @Override
    public void onFooterLoad(AbPullToRefreshView view) {
        mPullLoading = true;
        getTableInfo();
    }

    @Override
    public void onHeaderRefresh(AbPullToRefreshView view) {
        mPullRefreshing = true;
        getTableInfo();
    }

    private void refreshingFinish() {
        if (mPullRefreshing) {
            mPullToRefreshView.onHeaderRefreshFinish();
            mPullRefreshing = false;
        }
        if (mPullLoading) {
            mPullToRefreshView.onFooterLoadFinish();
            mPullLoading = false;
        }
    }
}
