package com.lianbi.mezone.b.ui;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.lianbi.mezone.b.bean.OneDishInOrder;
import com.lianbi.mezone.b.bean.TableOrderBean;
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

    @Bind(R.id.yuan_1)
    TextView yuan_1;

    @Bind(R.id.actually_paid_amount)
    TextView actually_paid_amount;//实际付款

    @Bind(R.id.has_paid_orders_list_view)
    ListView mListView;

    @Bind(R.id.fantai)
    RelativeLayout fantai;

    private String tableId;

    private QuickAdapter<TableOrderBean> mAdapter;
    private List<TableOrderBean> mData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_has_paid, NOTYPE);
        ButterKnife.bind(this);
        setPageTitle(getString(R.string.activity_tablehaspaid_title));
        tableId = getIntent().getStringExtra("TABLEID");
        table_name.setText(getIntent().getStringExtra("TABLENAME"));

        initAdapter();

        addDataToView(getIntent().getStringExtra("DATA"));
    }

    private void addDataToView(String data) {
        JSONObject jsonObject = JSON.parseObject(data);
        fen_num.setText(jsonObject.getString("proNum"));//产品数量
        yuan_1.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);//中划线
        num_should_pay.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);//中划线
        num_should_pay.setText(jsonObject.getString("totalOriginalMoney"));//原始订单总金额(原始总金额)
        actually_paid_amount.setText(jsonObject.getString("totalOrderMoney"));//实付金额
        mData = JSON.parseArray(jsonObject.getString("alreadyPaidOrders"), TableOrderBean.class);
        mAdapter.replaceAll(mData);
    }

    private void initAdapter() {
        mAdapter = new QuickAdapter<TableOrderBean>(TableHasPaidActivity.this, R.layout.table_has_paid_order_list_view_layout, mData) {
            @Override
            protected void convert(BaseAdapterHelper helper, TableOrderBean item) {
                helper.getView(R.id.dotted_line).setLayerType(View.LAYER_TYPE_SOFTWARE, null);
                final ImageView avatar = helper.getView(R.id.iv_avatar);//头像
                TextView name = helper.getView(R.id.tv_client_name);
                TextView remark = helper.getView(R.id.remarks);//备注
                TextView order_time = helper.getView(R.id.tv_order_time);//支付时间
                ImageView vip = helper.getView(R.id.vip);
                TextView vip_class = helper.getView(R.id.vip_class);
                TextView ordinary_member = helper.getView(R.id.ordinary_member);
                LinearLayout container = helper.getView(R.id.dishes_list_container);
                Glide.with(TableHasPaidActivity.this)
                        .load(item.getPhoto())
                        .asBitmap()
                        .centerCrop()
                        .error(R.mipmap.defaultpeson)
                        .into(new BitmapImageViewTarget(avatar) {
                            @Override
                            protected void setResource(Bitmap resource) {
                                RoundedBitmapDrawable circularBitmapDrawable =
                                        RoundedBitmapDrawableFactory.create(TableHasPaidActivity.this.getResources(), resource);
                                circularBitmapDrawable.setCircular(true);
                                avatar.setImageDrawable(circularBitmapDrawable);
                            }
                        });
                name.setText(item.getUserName());
                remark.setText(item.getDesc());
                order_time.setText(AbDateUtil.exchangeFormat(item.getCreateTime(), "yyyyMMddHHmmss", AbDateUtil.dateFormatHM));
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

                container.removeAllViews();
                ArrayList<OneDishInOrder> detailInfo = item.getDetailInfo();
                for (int i = 0; i < detailInfo.size(); i++) {
                    View oneDishLayout = LayoutInflater.from(TableHasPaidActivity.this).inflate(R.layout.one_dish_layout, null);
                    TextView dish_name = (TextView) oneDishLayout.findViewById(R.id.dish_name);
                    TextView dish_price = (TextView) oneDishLayout.findViewById(R.id.dish_price);
                    TextView tv_dish_num = (TextView) oneDishLayout.findViewById(R.id.tv_dish_num);
                    TextView renminbi_sign_ = (TextView) oneDishLayout.findViewById(R.id.renminbi_sign_);
                    TextView actually_dish_price = (TextView) oneDishLayout.findViewById(R.id.actually_dish_price);

                    OneDishInOrder oneDishInOrder = detailInfo.get(i);
                    dish_name.setText(oneDishInOrder.getProName());
                    dish_price.setText(oneDishInOrder.getPrice());
                    tv_dish_num.setText(oneDishInOrder.getNum());
                    renminbi_sign_.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);//中划线
                    actually_dish_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);//中划线
                    actually_dish_price.setText(oneDishInOrder.getOriginalPrice());
                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(screenWidth,
                            (int) AbViewUtil.dip2px(TableHasPaidActivity.this, 80.0f));
                    oneDishLayout.setLayoutParams(layoutParams);
                    container.addView(oneDishLayout);
                }
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
            checkTableOrder();
        }
    }

    private void checkTableOrder() {
        okHttpsImp.checkTableOrder(new MyResultCallback<String>() {
            @Override
            public void onResponseResult(Result result) {
                String data = result.getData();
                if (TextUtils.isEmpty(data))
                    return;
                com.alibaba.fastjson.JSONObject jsonObject = JSON.parseObject(data);

                switch (jsonObject.getIntValue("reslutCode")) {
                    case 0:
                        showSetTableFreeDialog();
                        break;
                    case 1:
                        showSetTableFreeFailDialog();
                        break;
                }
            }

            @Override
            public void onResponseFailed(String msg) {

            }
        }, userShopInfoBean.getBusinessId(), tableId);
    }

    private void showSetTableFreeFailDialog() {
        Dialog dialog = new Dialog(TableHasPaidActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setContentView(R.layout.fantai_fail_dialog_layout);
        Window win = dialog.getWindow();
        WindowManager.LayoutParams lp = win.getAttributes();
        lp.gravity = Gravity.CENTER;
        lp.width = (int) (screenWidth * 0.8);
        lp.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        dialog.onWindowAttributesChanged(lp);
        dialog.show();
    }

    //是否翻桌
    private void showSetTableFreeDialog() {
        final Dialog dialog = new Dialog(TableHasPaidActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = View.inflate(TableHasPaidActivity.this, R.layout.fantai_dialog_layout, null);
        view.findViewById(R.id.positive_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                setTableFree();//翻桌
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

    //修改桌位订单状态(翻台)
    private void setTableFree() {
        okHttpsImp.setTableFree(new MyResultCallback<String>() {
            @Override
            public void onResponseResult(Result result) {
                ContentUtils.showMsg(TableHasPaidActivity.this, "翻桌成功");
                finish();
            }

            @Override
            public void onResponseFailed(String msg) {
                ContentUtils.showMsg(TableHasPaidActivity.this, msg);
            }
        }, userShopInfoBean.getBusinessId(), tableId);
    }
}
