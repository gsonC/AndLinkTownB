package com.lianbi.mezone.b.ui;

import android.app.Dialog;
import android.bluetooth.BluetoothDevice;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.lianbi.mezone.b.bean.OneDishInOrder;
import com.lianbi.mezone.b.bean.TableOrderBean;
import com.lianbi.mezone.b.httpresponse.MyResultCallback;
import com.xizhi.mezone.b.R;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.hgh.baseadapter.BaseAdapterHelper;
import cn.com.hgh.baseadapter.QuickAdapter;
import cn.com.hgh.utils.AbDateUtil;
import cn.com.hgh.utils.ContentUtils;
import cn.com.hgh.utils.EditTextUtills;
import cn.com.hgh.utils.MathExtend;
import cn.com.hgh.utils.Result;
import cn.com.hgh.view.DialogCommon;
import cn.com.hgh.view.DialogQrg;
import cn.com.hgh.view.MyListView;

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

    @Bind(R.id.yuan)
    TextView yuan;

    @Bind(R.id.num_actually_pay)
    TextView num_actually_pay;

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

    private QuickAdapter<TableOrderBean> mAdapter;
    private List<TableOrderBean> mData = new ArrayList<>();

    private AlertDialog dialog;
    private AlertDialog.Builder b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setResult(RESULT_OK, getIntent());
        setContentView(R.layout.activity_table_has_ordered, NOTYPE);
        ButterKnife.bind(this);
        setPageTitle(getString(R.string.activity_tablehasordered_title));
        tableId = getIntent().getStringExtra("TABLEID");
        table_name.setText(getIntent().getStringExtra("TABLENAME"));

        initAdapter();

        addDataToView(getIntent().getStringExtra("DATA"));

        initDialog();
    }

    private void initDialog() {
        b = new AlertDialog.Builder(this);
        b.setTitle("请选择要连接的设备");
        b.setPositiveButton("连接", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                bluetoothAdapterCancelDiscovery();
                mService.connect(deviceIsSelected);
            }
        });
        b.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                bluetoothAdapterCancelDiscovery();
                dialog.dismiss();
            }
        });
        b.setNeutralButton("搜索更多", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                doDiscovery();
            }
        });
        b.setCancelable(false);
    }

    @Override
    protected void connectingBluetoothDialog() {
        deviceIsSelected = mBluetoothDeviceList.get(0);
        String[] deviceArr = new String[mBluetoothDeviceList.size()];
        for (int i = 0; i < mBluetoothDeviceList.size(); i++) {
            BluetoothDevice device = mBluetoothDeviceList.get(i);
            deviceArr[i] = device.getName() + "\n" + device.getAddress();
        }
        b.setSingleChoiceItems(deviceArr, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deviceIsSelected = mBluetoothDeviceList.get(which);
            }
        });
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
        dialog = b.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    private void addDataToView(String data) {
        JSONObject jsonObject = JSON.parseObject(data);
        fen_num.setText(jsonObject.getString("proNum"));//产品数量
        yuan.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);//中划线
        num_should_pay.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);//中划线
        num_should_pay.setText(jsonObject.getString("totalOriginalMoney"));//原始订单总金额(原始总金额)
        num_actually_pay.setText(jsonObject.getString("totalOrderMoney"));//实付金额
        mData = JSON.parseArray(jsonObject.getString("unPaidOrders"), TableOrderBean.class);
//        过滤掉已取消
//        for (int i = 0; i < mData.size(); i++) {
//            TableOrderBean bean = mData.get(i);
//            ArrayList<OneDishInOrder> detailInfo = bean.getDetailInfo();
//            for (int j = 0; j < detailInfo.size(); j++) {
//                OneDishInOrder oneDishInOrder = detailInfo.get(j);
//                if (oneDishInOrder.getIsDel().equals("1")) {
//                    detailInfo.remove(j);
//                    j--;
//                }
//            }
//            if (detailInfo.isEmpty()) {
//                mData.remove(i);
//                i--;
//            } else {
//                bean.setDetailInfo(detailInfo);
//                mData.remove(i);
//                mData.add(i, bean);
//            }
//        }
        mAdapter.replaceAll(mData);
    }

    private void initAdapter() {
        mAdapter = new QuickAdapter<TableOrderBean>(TableHasOrderedActivity.this, R.layout.table_has_ordered_order_list_view_layout, mData) {
            @Override
            protected void convert(final BaseAdapterHelper helper_1, final TableOrderBean item) {
                helper_1.getView(R.id.dotted_line).setLayerType(View.LAYER_TYPE_SOFTWARE, null);
                final ImageView avatar = helper_1.getView(R.id.iv_avatar);//头像
                TextView name = helper_1.getView(R.id.tv_client_name);
                TextView remark = helper_1.getView(R.id.remarks);//备注
                TextView order_time = helper_1.getView(R.id.tv_order_time);//支付时间
                TextView waiting_time = helper_1.getView(R.id.waiting_time);
                ImageView vip = helper_1.getView(R.id.vip);
                TextView vip_class = helper_1.getView(R.id.vip_class);
                TextView ordinary_member = helper_1.getView(R.id.ordinary_member);
                MyListView container = helper_1.getView(R.id.dishes_list_container);
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
                waiting_time.setText(item.getOrderCookTime());
                String vipTypeName = item.getVipTypeName();
                if (TextUtils.isEmpty(vipTypeName)) {
                    vip.setVisibility(View.GONE);
                    vip_class.setVisibility(View.GONE);
                    ordinary_member.setVisibility(View.GONE);
                } else {
                    if (vipTypeName.contains("VIP")) {
                        vipTypeName = vipTypeName.substring(3);
                        vip_class.setText(vipTypeName);
                        vip.setVisibility(View.VISIBLE);
                        vip_class.setVisibility(View.VISIBLE);
                        ordinary_member.setVisibility(View.GONE);
                    } else {
                        ordinary_member.setText(vipTypeName);
                        vip.setVisibility(View.GONE);
                        vip_class.setVisibility(View.GONE);
                        ordinary_member.setVisibility(View.VISIBLE);
                    }
                }

                final ArrayList<OneDishInOrder> detailInfo = item.getDetailInfo();
                QuickAdapter<OneDishInOrder> adapter = new QuickAdapter<OneDishInOrder>(TableHasOrderedActivity.this, R.layout.one_dish_layout, detailInfo) {
                    @Override
                    protected void convert(final BaseAdapterHelper helper_2, final OneDishInOrder oneDishInOrder) {
                        TextView dish_name = helper_2.getView(R.id.dish_name);
                        TextView dish_price = helper_2.getView(R.id.dish_price);
                        TextView tv_dish_num = helper_2.getView(R.id.tv_dish_num);
                        TextView cancel_dish = helper_2.getView(R.id.cancel_dish);
                        TextView renminbi_sign_ = helper_2.getView(R.id.renminbi_sign_);
                        TextView actually_dish_price = helper_2.getView(R.id.actually_dish_price);
                        dish_name.setText(oneDishInOrder.getProName());
                        dish_price.setText(oneDishInOrder.getPrice());
                        tv_dish_num.setText(oneDishInOrder.getNum());
                        renminbi_sign_.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);//中划线
                        actually_dish_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);//中划线
                        actually_dish_price.setText(oneDishInOrder.getOriginalPrice());
                        cancel_dish.setVisibility(View.VISIBLE);
                        String isDel = oneDishInOrder.getIsDel();
                        if (isDel.equals("0")) {
                            cancel_dish.setBackgroundResource(R.drawable.activity_table_has_paid_shape);
                            cancel_dish.setText("取消");
                            cancel_dish.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    DialogCommon dialog = new DialogCommon(TableHasOrderedActivity.this) {
                                        @Override
                                        public void onCheckClick() {
                                            this.dismiss();
                                        }

                                        @Override
                                        public void onOkClick() {
                                            gotoCancelProduct(item, helper_1.getPosition(), oneDishInOrder, helper_2.getPosition());
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
                            });
                        } else {
                            cancel_dish.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                            cancel_dish.setText("已取消");
                            cancel_dish.setOnClickListener(null);
                        }
                    }
                };
                container.setAdapter(adapter);
            }
        };
        mListView.setAdapter(mAdapter);
    }

    private void gotoCancelProduct(final TableOrderBean bean, final int beanIndex, final OneDishInOrder oneDishInOrder, final int dishIndex) {
        okHttpsImp.cancelProduct(new MyResultCallback<String>() {
            @Override
            public void onResponseResult(Result result) {
                String fen = fen_num.getText().toString();
                fen_num.setText(Integer.toString(Integer.parseInt(fen, 10) - 1));
                String multiply = MathExtend.multiply(oneDishInOrder.getPrice(), oneDishInOrder.getNum());
                String num = MathExtend.subtract(num_should_pay.getText().toString(), multiply);
                num_should_pay.setText(num);
                oneDishInOrder.setIsDel("1");
                ArrayList<OneDishInOrder> detailInfo = bean.getDetailInfo();
                detailInfo.remove(dishIndex);
                detailInfo.add(dishIndex, oneDishInOrder);
                bean.setDetailInfo(detailInfo);
                mData.remove(beanIndex);
                mData.add(beanIndex, bean);

                boolean isEmpty = true;
                for (TableOrderBean tableOrderBean : mData) {
                    ArrayList<OneDishInOrder> arrayList = tableOrderBean.getDetailInfo();
                    for (OneDishInOrder oneDishOrder : arrayList) {
                        if (oneDishOrder.getIsDel().equals("0")) {
                            isEmpty = false;
                            break;
                        }
                    }
                }

                if (isEmpty)
                    TableHasOrderedActivity.this.finish();
                else
                    mAdapter.replaceAll(mData);
            }

            @Override
            public void onResponseFailed(String msg) {
            }
        }, bean.getOrderNo(), oneDishInOrder.getProductId(), tableId, Integer.toString((int) (Double.parseDouble(oneDishInOrder.getPrice()) * Double.parseDouble(oneDishInOrder.getNum()) * 100.0d)));
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
        final Dialog dialog = new Dialog(TableHasOrderedActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = View.inflate(TableHasOrderedActivity.this, R.layout.change_order_money_dialog_layout, null);
        final EditText change = (EditText) view.findViewById(R.id.change_order_money);
        EditTextUtills.setPricePoint(change);
        view.findViewById(R.id.positive_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                gotoEditPrice(change.getText().toString());
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
        dialog.onWindowAttributesChanged(lp);
        dialog.show();
    }

    private void gotoEditPrice(final String newPrice) {
        if (TextUtils.isEmpty(newPrice)) {
            ContentUtils.showMsg(TableHasOrderedActivity.this, "请输入新价格");
            return;
        }
        if (Double.parseDouble(newPrice) > Double.parseDouble(num_should_pay.getText().toString())) {
            ContentUtils.showMsg(TableHasOrderedActivity.this, "修改价格不能大于订单总价");
            return;
        }
        if (newPrice.contains(".") && (newPrice.indexOf(".") == (newPrice.length() - 3))) {
            ContentUtils.showMsg(TableHasOrderedActivity.this, "修改价格只能包含元和角");
            return;
        }
        if (newPrice.equals("0") || newPrice.equals("0.0")) {
            ContentUtils.showMsg(TableHasOrderedActivity.this, "修改价格必须大于0");
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
        try {
            okHttpsImp.getonlinePay(userShopInfoBean.getUserId(), userShopInfoBean.getBusinessId(), tableId, new MyResultCallback<String>() {
                @Override
                public void onResponseResult(Result result) {
                    String reString = result.getData();
                    if (reString != null) {
                        try {
                            org.json.JSONObject jsonObject = new org.json.JSONObject(reString);
                            String url = jsonObject.getString("payUrl");
                            DialogQrg dialogQrg = new DialogQrg(url, TableHasOrderedActivity.this);
                            dialogQrg.show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onResponseFailed(String msg) {
                    ContentUtils.showMsg(TableHasOrderedActivity.this, msg);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //现金支付
    private void gotoCashPay() {
        okHttpsImp.editOrderStatus(new MyResultCallback<String>() {
            @Override
            public void onResponseResult(Result result) {
                ContentUtils.showMsg(TableHasOrderedActivity.this, result.getMsg());
                finish();
            }

            @Override
            public void onResponseFailed(String msg) {
                ContentUtils.showMsg(TableHasOrderedActivity.this, msg);
            }
        }, userShopInfoBean.getUserId(), userShopInfoBean.getBusinessId(), tableId);
    }
}
