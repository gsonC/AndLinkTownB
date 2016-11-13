package com.lianbi.mezone.b.ui;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.lianbi.mezone.b.bean.DiningOrderBean;
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
import cn.com.hgh.view.AbPullToRefreshView;
import cn.com.hgh.view.DialogCommon;

/*
* 桌位详情-已点单
* */
public class TableHasOrderedActivity extends BluetoothBaseActivity implements AbPullToRefreshView.OnFooterLoadListener,
        AbPullToRefreshView.OnHeaderRefreshListener {
    @Bind(R.id.table_name)
    TextView table_name;

    @Bind(R.id.fen_num)
    TextView fen_num;

    @Bind(R.id.num_should_pay)
    TextView num_should_pay;//合计

    @Bind(R.id.cancel_order)
    TextView cancelOrder;

    @Bind(R.id.order_price_change)
    TextView orderPriceChange;

    @Bind(R.id.print_ticket)
    TextView printTicket;

    @Bind(R.id.orders_detail)
    AbPullToRefreshView mPullToRefreshView;

    @Bind(R.id.orders_list_view)
    ListView mListView;

    @Bind(R.id.online_pay)
    TextView onlinePay;

    @Bind(R.id.cash_pay)
    TextView cashPay;

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

    private String newPrice = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_has_ordered, NOTYPE);
        ButterKnife.bind(this);
        setPageTitle("已点单");
        tableId = getIntent().getStringExtra("TABLEID");
        table_name.setText(getIntent().getStringExtra("TABLENAME"));

        setListener();

        initAdapter();

        getTableInfo();
    }

    private void setListener() {
        mPullToRefreshView.setOnHeaderRefreshListener(this);
        mPullToRefreshView.setOnFooterLoadListener(this);
    }

    private void initAdapter() {
        mAdapter = new QuickAdapter<DiningOrderBean>(TableHasOrderedActivity.this, R.layout.table_order_list_view_layout, mData) {
            @Override
            protected void convert(BaseAdapterHelper helper, DiningOrderBean item) {
                ((TextView) helper.getView(R.id.time_cn)).setText("下单时间：");
                ImageView avatar = helper.getView(R.id.iv_avatar);//头像
                TextView name = helper.getView(R.id.tv_client_name);
                TextView remark = helper.getView(R.id.remarks);//备注
                TextView order_time = helper.getView(R.id.tv_order_time);//支付时间
                LinearLayout container = helper.getView(R.id.dishes_list_container);
            }
        };
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

    @Override
    @OnClick({R.id.cancel_order, R.id.order_price_change, R.id.print_ticket,
            R.id.online_pay, R.id.cash_pay})
    protected void onChildClick(View view) {
        super.onChildClick(view);
        switch (view.getId()) {
            case R.id.cancel_order:
                showCancelOrderDialog();
                break;
            case R.id.order_price_change:
                showChangeOrderMoneyDialog();
                break;
            case R.id.print_ticket:
                showPrintTicketDialog(tableId);
                break;
            case R.id.online_pay:
                break;
            case R.id.cash_pay:
                break;
        }
    }

    private void showChangeOrderMoneyDialog() {
        newPrice = "";
        final Dialog dialog = new Dialog(TableHasOrderedActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = View.inflate(TableHasOrderedActivity.this, R.layout.change_order_money_dialog_layout, null);
        EditText change = (EditText) view.findViewById(R.id.change_order_money);
        change.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                newPrice = s.toString();
            }
        });
        view.findViewById(R.id.positive_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                gotoEditPrice();
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

    private void gotoEditPrice() {
        if (newPrice.isEmpty()) {
            ContentUtils.showMsg(TableHasOrderedActivity.this, "请输入新价格");
            return;
        }
        okHttpsImp.editPrice(new MyResultCallback<String>() {
            @Override
            public void onResponseResult(Result result) {

            }

            @Override
            public void onResponseFailed(String msg) {

            }
        }, userShopInfoBean.getUserId(), userShopInfoBean.getBusinessId(), tableId, newPrice);
    }

    private void showCancelOrderDialog() {
        DialogCommon dialog = new DialogCommon(TableHasOrderedActivity.this) {
            @Override
            public void onCheckClick() {
                this.dismiss();
            }

            @Override
            public void onOkClick() {
                gotoCancelOrder();
                this.dismiss();
            }
        };
        dialog.setTextTitle("是否取消订单");
        dialog.setTv_dialog_common_ok("是");
        dialog.setTv_dialog_common_cancel("否");
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    private void gotoCancelOrder() {
        okHttpsImp.tableInfo(new MyResultCallback<String>() {
            @Override
            public void onResponseResult(Result result) {

            }

            @Override
            public void onResponseFailed(String msg) {

            }
        }, userShopInfoBean.getUserId(), userShopInfoBean.getBusinessId(), tableId, "1");
    }

    private void gotoOnlinePay() {
//        okHttpsImp.onlinePay(new MyResultCallback<String>() {
//            @Override
//            public void onResponseResult(Result result) {
//
//            }
//
//            @Override
//            public void onResponseFailed(String msg) {
//
//            }
//        }, userShopInfoBean.getUserId(), userShopInfoBean.getBusinessId(), tableId, );
    }

    //现金支付
    private void gotoCashPay() {
        okHttpsImp.editOrderStatus(new MyResultCallback<String>() {
            @Override
            public void onResponseResult(Result result) {

            }

            @Override
            public void onResponseFailed(String msg) {

            }
        }, userShopInfoBean.getUserId(), userShopInfoBean.getBusinessId(), tableId);
    }
}
