package com.lianbi.mezone.b.ui;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.lianbi.mezone.b.bean.OneDishInOrder;
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
import cn.com.hgh.utils.AbDateUtil;
import cn.com.hgh.utils.AbViewUtil;
import cn.com.hgh.utils.ContentUtils;
import cn.com.hgh.utils.MathExtend;
import cn.com.hgh.utils.Result;
import cn.com.hgh.view.DialogCommon;

/*
* 桌位详情-已点单
* */
public class TableHasOrderedActivity extends BluetoothBaseActivity {
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

    @Bind(R.id.orders_list_view)
    ListView mListView;

    @Bind(R.id.online_pay)
    TextView onlinePay;

    @Bind(R.id.cash_pay)
    TextView cashPay;

    private String tableId;

    private QuickAdapter<UnPaidOrderBean> mAdapter;
    private List<UnPaidOrderBean> mData = new ArrayList<>();

    private String newPrice = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setResult(RESULT_OK, getIntent());
        setContentView(R.layout.activity_table_has_ordered, NOTYPE);
        ButterKnife.bind(this);
        setPageTitle("已点单");
        tableId = getIntent().getStringExtra("TABLEID");
        table_name.setText(getIntent().getStringExtra("TABLENAME"));

        initAdapter();

        addDataToView(getIntent().getStringExtra("DATA"));
    }

    private void addDataToView(String data) {
        JSONObject jsonObject = JSON.parseObject(data);
        fen_num.setText(jsonObject.getString("proNum"));
        num_should_pay.setText(jsonObject.getString("totalOrderMoney"));
        mData = JSON.parseArray(jsonObject.getString("unPaidOrders"), UnPaidOrderBean.class);
        mAdapter.replaceAll(mData);
    }

    private void initAdapter() {
        mAdapter = new QuickAdapter<UnPaidOrderBean>(TableHasOrderedActivity.this, R.layout.table_order_list_view_layout, mData) {
            @Override
            protected void convert(BaseAdapterHelper helper, UnPaidOrderBean item) {
                ((TextView) helper.getView(R.id.time_cn)).setText("下单时间：");
                final ImageView avatar = helper.getView(R.id.iv_avatar);//头像
                TextView name = helper.getView(R.id.tv_client_name);
                TextView remark = helper.getView(R.id.remarks);//备注
                TextView order_time = helper.getView(R.id.tv_order_time);//支付时间
                LinearLayout container = helper.getView(R.id.dishes_list_container);
                Glide.with(TableHasOrderedActivity.this)
                        .load(item.getPhoto())
                        .asBitmap()
                        .centerCrop()
                        .error(R.mipmap.defaultpeson)
                        .into(new BitmapImageViewTarget(avatar) {
                            @Override
                            protected void setResource(Bitmap resource) {
                                RoundedBitmapDrawable circularBitmapDrawable =
                                        RoundedBitmapDrawableFactory.create(TableHasOrderedActivity.this.getResources(), resource);
                                circularBitmapDrawable.setCircular(true);
                                avatar.setImageDrawable(circularBitmapDrawable);
                            }
                        });
                name.setText(item.getUserName());
                remark.setText(item.getDesc());
                order_time.setText(AbDateUtil.exchangeFormat(item.getCreateTime(), "yyyyMMddHHmmss", AbDateUtil.dateFormatHM));

                ArrayList<OneDishInOrder> detailInfo = item.getDetailInfo();
                for (int i = 0; i < detailInfo.size(); i++) {
                    View oneDishLayout = LayoutInflater.from(TableHasOrderedActivity.this).inflate(R.layout.one_dish_layout, null);
                    TextView dish_name = (TextView) oneDishLayout.findViewById(R.id.dish_name);
                    TextView dish_price = (TextView) oneDishLayout.findViewById(R.id.dish_price);
                    TextView tv_dish_num = (TextView) oneDishLayout.findViewById(R.id.tv_dish_num);
                    TextView cancel_dish = (TextView) oneDishLayout.findViewById(R.id.cancel_dish);

                    final OneDishInOrder oneDishInOrder = detailInfo.get(i);
                    dish_name.setText(oneDishInOrder.getProName());
                    dish_price.setText(oneDishInOrder.getPrice());
                    tv_dish_num.setText(oneDishInOrder.getNum());
                    cancel_dish.setVisibility(View.VISIBLE);
                    cancel_dish.setOnClickListener(new cancelProductOnClickListener(oneDishInOrder, item));
                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(screenWidth,
                            (int) AbViewUtil.dip2px(TableHasOrderedActivity.this, 80.0f));
                    oneDishLayout.setLayoutParams(layoutParams);
                    container.addView(oneDishLayout);
                }
            }
        };
        mListView.setAdapter(mAdapter);
    }

    private class cancelProductOnClickListener implements View.OnClickListener {
        private OneDishInOrder oneDishInOrder;
        private UnPaidOrderBean unPaidOrderBean;

        public cancelProductOnClickListener() {
        }

        public cancelProductOnClickListener(OneDishInOrder oneDishInOrder, UnPaidOrderBean unPaidOrderBean) {
            this.oneDishInOrder = oneDishInOrder;
            this.unPaidOrderBean = unPaidOrderBean;
        }

        public OneDishInOrder getOneDishInOrder() {
            return oneDishInOrder;
        }

        public void setOneDishInOrder(OneDishInOrder oneDishInOrder) {
            this.oneDishInOrder = oneDishInOrder;
        }

        public UnPaidOrderBean getUnPaidOrderBean() {
            return unPaidOrderBean;
        }

        public void setUnPaidOrderBean(UnPaidOrderBean unPaidOrderBean) {
            this.unPaidOrderBean = unPaidOrderBean;
        }

        @Override
        public void onClick(View v) {
            DialogCommon dialog = new DialogCommon(TableHasOrderedActivity.this) {
                @Override
                public void onCheckClick() {
                    this.dismiss();
                }

                @Override
                public void onOkClick() {
                    gotoCancelProduct(unPaidOrderBean, oneDishInOrder);
                    this.dismiss();
                }
            };
            dialog.setTextTitle("是否取消");
            dialog.setTv_dialog_common_ok("是");
            dialog.setTv_dialog_common_cancel("否");
            dialog.setCancelable(false);
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
        }
    }

    private void gotoCancelProduct(final UnPaidOrderBean bean, final OneDishInOrder oneDishInOrder) {
        final String price = oneDishInOrder.getPrice();
        okHttpsImp.cancelProduct(new MyResultCallback<String>() {
            @Override
            public void onResponseResult(Result result) {
                int index = mData.indexOf(bean);
                ArrayList<OneDishInOrder> detailInfo = bean.getDetailInfo();
                detailInfo.remove(oneDishInOrder);
                double newPay = Double.parseDouble(num_should_pay.getText().toString()) - Double.parseDouble(price);
                num_should_pay.setText(MathExtend.roundNew(newPay, 2));// 更改应付
                if (detailInfo.isEmpty()) {
                    mData.remove(index);
                    String newFen = Integer.toString(Integer.parseInt(fen_num.getText().toString(), 10) - 1);
                    fen_num.setText(newFen);// 更改份数
                } else {
                    bean.setDetailInfo(detailInfo);
                    mData.remove(index);
                    mData.add(index, bean);
                }
                mAdapter.replaceAll(mData);
            }

            @Override
            public void onResponseFailed(String msg) {

            }
        }, bean.getOrderNo(), oneDishInOrder.getProductId(), tableId, MathExtend.multiply(price, "100"));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
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
                showOnlinePayDialog();
                break;
            case R.id.cash_pay:
                showCashPayDialog();
                break;
        }
    }

    private void showCashPayDialog() {
        DialogCommon dialog = new DialogCommon(TableHasOrderedActivity.this) {
            @Override
            public void onCheckClick() {
                this.dismiss();
            }

            @Override
            public void onOkClick() {
                gotoCashPay();
                this.dismiss();
            }
        };
        dialog.setTextTitle("是否现金收款");
        dialog.setTv_dialog_common_ok("是");
        dialog.setTv_dialog_common_cancel("否");
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    private void showOnlinePayDialog() {
        DialogCommon dialog = new DialogCommon(TableHasOrderedActivity.this) {
            @Override
            public void onCheckClick() {
                this.dismiss();
            }

            @Override
            public void onOkClick() {
                gotoOnlinePay();
                this.dismiss();
            }
        };
        dialog.setTextTitle("是否在线收款");
        dialog.setTv_dialog_common_ok("是");
        dialog.setTv_dialog_common_cancel("否");
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
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
        Window win = dialog.getWindow();
        WindowManager.LayoutParams lp = win.getAttributes();
        lp.gravity = Gravity.CENTER;
        lp.width = (int) (screenWidth * 0.8);
        lp.height = ViewGroup.LayoutParams.WRAP_CONTENT;
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
                ContentUtils.showMsg(TableHasOrderedActivity.this, "订单改价成功");
                num_should_pay.setText(MathExtend.roundNew(Double.parseDouble(newPrice), 2));
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
                ContentUtils.showMsg(TableHasOrderedActivity.this, "取消订单成功");
                TableHasOrderedActivity.this.finish();
            }

            @Override
            public void onResponseFailed(String msg) {
                ContentUtils.showMsg(TableHasOrderedActivity.this, msg);
            }
        }, tableId);
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
