package com.lianbi.mezone.b.ui;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.lianbi.mezone.b.app.Constants;
import com.lianbi.mezone.b.bean.TableSetBean;
import com.lianbi.mezone.b.httpresponse.API;
import com.lianbi.mezone.b.httpresponse.MyResultCallback;
import com.lzy.okgo.request.BaseRequest;
import com.xizhi.mezone.b.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;
import butterknife.OnTextChanged;
import cn.com.hgh.baseadapter.BaseAdapterHelper;
import cn.com.hgh.baseadapter.QuickAdapter;
import cn.com.hgh.eventbus.ShouyeRefreshEvent;
import cn.com.hgh.utils.ContentUtils;
import cn.com.hgh.utils.JumpIntent;
import cn.com.hgh.utils.Result;
import cn.com.hgh.view.ClearEditText;
import cn.com.hgh.view.DialogCommon;
import cn.com.hgh.view.MyGridView;

/*
* 桌面设置
* */
public class DiningTableSettingActivity extends BluetoothBaseActivity implements SwipeRefreshLayout.OnRefreshListener,
        AppBarLayout.OnOffsetChangedListener, AdapterView.OnItemClickListener, TextWatcher {
    @Bind(R.id.back)
    ImageView back;

    @Bind(R.id.menu_setting)
    TextView menu_setting;//菜单设置

    @Bind(R.id.search)
    ClearEditText search;

    @Bind(R.id.table_setting_bottom)
    RelativeLayout bottom;

    @Bind(R.id.add_table)
    LinearLayout add_table;

    @Bind(R.id.delete_table)
    ImageView delete_table;

    @Bind(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    @Bind(R.id.app_bar)
    AppBarLayout app_bar;

    @Bind(R.id.view_1)
    RelativeLayout view1;

    @Bind(R.id.switch_state)
    RelativeLayout switch_state;

    @Bind(R.id.in_business_handle)
    View in_business_handle;

    @Bind(R.id.in_business)
    TextView in_business;//营业中

    @Bind(R.id.out_business_handle)
    View out_business_handle;

    @Bind(R.id.out_business)
    TextView out_business;//休息中

    @Bind(R.id.view_2)
    LinearLayout view2;

    @Bind(R.id.pay)
    LinearLayout pay;

    @Bind(R.id.pay_num)
    TextView pay_num;

    @Bind(R.id.call)
    LinearLayout call;

    @Bind(R.id.call_num)
    TextView call_num;

    @Bind(R.id.particulars)
    LinearLayout particulars;

    @Bind(R.id.particulars_num)
    TextView particulars_num;

    @Bind(R.id.tables_grid_view)
    MyGridView tablesGridView;

    @Bind(R.id.search_result_grid_view)
    GridView searchResultGridView;

    private QuickAdapter<TableSetBean> adapter;
    private ArrayList<TableSetBean> data = new ArrayList<>();

    private QuickAdapter<TableSetBean> searchResultAdapter;
    private ArrayList<TableSetBean> searchResultData = new ArrayList<>();

    private boolean isInBusiness;

    private ArrayList<String> needDelList = new ArrayList<>();

    private boolean isRequestingTablesData;//是否正在进行桌位信息列表网络请求

    private boolean isDeletingTablesData;//是否正在进行删除桌位网络请求

    private boolean isRequestingOtherData;//是否正在进行消费结算、呼叫服务、到店明细总数网络请求

    private boolean isRequestingBeforFanZhuoCheckData;//是否正在进行翻台前校验网络请求（4.19	查询桌位是否有未支付的订单）

    private boolean isRequestingFanZhuoData;//是否正在进行翻桌网络请求

    private boolean isRequestingTableInfoData;//是否正在进行查询单个桌面信息网络请求（用于点击item后的跳转）

    private boolean delSelectButtonIsShowing;//删除选择按钮是否正在显示

    private int verticalOffset;

    private String searchText = "";

    private static final int GOTO_NEW_ORDER_TABLE_ACTIVITY = 1004;

    private ObjectAnimator objectInAnim;
    private ObjectAnimator objectOutAnim;

    private AlertDialog dialog;
    private AlertDialog.Builder b;

    private String tableId;//被点击空桌id

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dining_table_setting);
        EventBus.getDefault().register(this);//注册EventBus
        ButterKnife.bind(this);

        swipeRefreshLayout.setColorSchemeResources(R.color.colores_news_01, R.color.black);

        initObjectAnimator();

        setListener();

        initAdapter();

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

    private void initObjectAnimator() {
        objectInAnim = ObjectAnimator.ofFloat(searchResultGridView, "translationY", searchResultGridView.getHeight(), 0.0f);
        objectInAnim.setDuration(500);
        objectInAnim.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                searchResultGridView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                searchResultGridView.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

        objectOutAnim = ObjectAnimator.ofFloat(searchResultGridView, "translationY", 0.0f, searchResultGridView.getHeight());
        objectOutAnim.setDuration(500);
        objectOutAnim.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                searchResultGridView.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                searchResultGridView.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);//反注册EventBus
        ButterKnife.unbind(this);
    }

    /**
     * EventBus 响应事件
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onShouyeRefreshEvent(ShouyeRefreshEvent event) {
        //false 的时候进行刷新界面操作
        if (!event.getRefresh()) {
            getTableinfo();
        }
    }

    private void initAdapter() {
        adapter = new QuickAdapter<TableSetBean>(DiningTableSettingActivity.this, R.layout.one_table_layout, data) {
            @Override
            protected void convert(BaseAdapterHelper helper, TableSetBean item) {
                adapterConvert(helper, item, 1);
            }
        };
        tablesGridView.setAdapter(adapter);

        searchResultAdapter = new QuickAdapter<TableSetBean>(DiningTableSettingActivity.this, R.layout.one_table_layout, searchResultData) {
            @Override
            protected void convert(BaseAdapterHelper helper, TableSetBean item) {
                adapterConvert(helper, item, 0);
            }
        };
        searchResultGridView.setAdapter(searchResultAdapter);
    }

    private void adapterConvert(BaseAdapterHelper helper, final TableSetBean item, int flag) {
        TextView table_state = helper.getView(R.id.table_state);
        TextView table_index = helper.getView(R.id.table_index);
        TextView have_new = helper.getView(R.id.have_new);
        TextView table_may_do = helper.getView(R.id.curr_table_may_do);
        View pay_number_container = helper.getView(R.id.pay_number_container);
        TextView pay_number = helper.getView(R.id.pay_number);
        TextView person_num = helper.getView(R.id.person_num);
        TextView unit = helper.getView(R.id.unit);
        ImageView table_delete = helper.getView(R.id.tableset_delete);

        if (item.isNew())
            have_new.setVisibility(View.VISIBLE);
        else
            have_new.setVisibility(View.GONE);

        String table_state_Str = "";
        int table_state_drawableResid = 0;
        int text_color = 0;
        int visibility = View.GONE;
        String table_may_do_Str = "";
        int table_may_do_drawableResid = 0;
        String person_num_Str = "";
        String unit_Str = "";
        switch (item.getTableStatus()) {
            case 0://空位
                table_state_Str = "空桌";
                table_state_drawableResid = R.drawable.table_empty_background_shape;
                text_color = Color.parseColor("#3d9684");
                visibility = View.GONE;
                table_may_do_drawableResid = R.drawable.show_qc_code_background_shape;
                table_may_do_Str = "查看二维码";
                person_num_Str = item.getPresetCount();
                unit_Str = "人桌";
                break;
            case 1://已点餐
                table_state_Str = "已点餐";
                table_state_drawableResid = R.drawable.have_ordered_background_shape;
                text_color = Color.parseColor("#e77c8c");
                visibility = View.VISIBLE;
                pay_number.setText(item.getOrderAmt());
                table_may_do_drawableResid = R.drawable.print_ticket_background_shape;
                table_may_do_Str = "打印小票";
                person_num_Str = item.getActualCount();
                unit_Str = "人用餐";
                switch (flag) {
                    case 0:
                        table_may_do.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (checkCanGoNext()) {
                                    showPrintTicketDialog(item.getTableId());
                                }
                            }
                        });
                        break;
                    case 1:
                        if (delSelectButtonIsShowing) {
                            table_may_do.setOnClickListener(null);
                        } else {
                            table_may_do.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (checkCanGoNext()) {
                                        showPrintTicketDialog(item.getTableId());
                                    }
                                }
                            });
                        }
                        break;
                }
                break;
            case 2://已支付
                table_state_Str = "已支付";
                table_state_drawableResid = R.drawable.have_paid_background_shape;
                text_color = Color.parseColor("#4592ca");
                visibility = View.VISIBLE;
                pay_number.setText(item.getOrderAmt());
                table_may_do_drawableResid = R.drawable.reset_table_background_shape;
                table_may_do_Str = "翻桌";
                person_num_Str = item.getActualCount();
                unit_Str = "人用餐";
                switch (flag) {
                    case 0:
                        table_may_do.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (checkCanGoNext()) {
                                    checkTableOrder(item.getTableId());
                                }
                            }
                        });
                        break;
                    case 1:
                        if (delSelectButtonIsShowing) {
                            table_may_do.setOnClickListener(null);
                        } else {
                            table_may_do.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (checkCanGoNext()) {
                                        checkTableOrder(item.getTableId());
                                    }
                                }
                            });
                        }
                        break;
                }
                break;
        }

        table_state.setText(table_state_Str);
        table_state.setBackgroundResource(table_state_drawableResid);
        table_index.setTextColor(text_color);
        table_index.setText(item.getTableName());
        pay_number_container.setVisibility(visibility);
        table_may_do.setBackgroundResource(table_may_do_drawableResid);
        table_may_do.setText(table_may_do_Str);
        table_may_do.setTextColor(text_color);
        person_num.setText(person_num_Str);
        unit.setText(unit_Str);

        if (flag == 1) {
            switch (item.getSelectStatus()) {
                case 0:
                    table_delete.setVisibility(View.GONE);
                    break;
                case 1:
                    table_delete.setImageResource(R.mipmap.message_unchecked);
                    table_delete.setVisibility(View.VISIBLE);
                    break;
                case 2:
                    table_delete.setImageResource(R.mipmap.icon_check);
                    table_delete.setVisibility(View.VISIBLE);
                    break;
            }
        }
    }

    //查询桌位是否有未支付的订单(是否可以翻台,翻台前校验)
    private void checkTableOrder(final String tableId) {
        okHttpsImp.checkTableOrder(new MyResultCallback<String>() {
            @Override
            public void onAfter(@Nullable String s, @Nullable Exception e) {
                super.onAfter(s, e);
                isRequestingBeforFanZhuoCheckData = false;
            }

            @Override
            public void onBefore(BaseRequest request) {
                super.onBefore(request);
                isRequestingBeforFanZhuoCheckData = true;
            }

            @Override
            public void onResponseResult(Result result) {
                String data = result.getData();
                if (TextUtils.isEmpty(data))
                    return;
                com.alibaba.fastjson.JSONObject jsonObject = JSON.parseObject(data);

                switch (jsonObject.getIntValue("reslutCode")) {
                    case 0:
                        showSetTableFreeDialog(tableId);
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
        Dialog dialog = new Dialog(DiningTableSettingActivity.this);
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
    private void showSetTableFreeDialog(final String tableId) {
        final Dialog dialog = new Dialog(DiningTableSettingActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = View.inflate(DiningTableSettingActivity.this, R.layout.fantai_dialog_layout, null);
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
        Window win = dialog.getWindow();
        WindowManager.LayoutParams lp = win.getAttributes();
        lp.gravity = Gravity.CENTER;
        lp.width = (int) (screenWidth * 0.8);
        lp.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        dialog.onWindowAttributesChanged(lp);
        dialog.show();
    }

    //修改桌位订单状态(翻台)
    private void setTableFree(String tableId) {
        okHttpsImp.setTableFree(new MyResultCallback<String>() {
            @Override
            public void onResponseResult(Result result) {
                Dialog dialog = new Dialog(DiningTableSettingActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                TextView textView = new TextView(DiningTableSettingActivity.this);
                textView.setGravity(Gravity.CENTER);
                textView.setText("翻桌成功");
                textView.setBackgroundColor(getResources().getColor(android.R.color.white));
                dialog.setContentView(textView);
                dialog.setCancelable(true);
                dialog.setCanceledOnTouchOutside(true);
                Window win = dialog.getWindow();
                WindowManager.LayoutParams lp = win.getAttributes();
                lp.gravity = Gravity.CENTER;
                lp.width = (int) (screenWidth * 0.8);
                lp.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                dialog.onWindowAttributesChanged(lp);
                dialog.show();
                getTableinfo();
            }

            @Override
            public void onResponseFailed(String msg) {

            }

            @Override
            public void onAfter(@Nullable String s, @Nullable Exception e) {
                super.onAfter(s, e);
                isRequestingFanZhuoData = false;
            }

            @Override
            public void onBefore(BaseRequest request) {
                super.onBefore(request);
                isRequestingFanZhuoData = true;
            }
        }, userShopInfoBean.getBusinessId(), tableId);
    }

    private boolean checkCanGoNext() {
        if (isRequestingTablesData) {
            ContentUtils.showMsg(DiningTableSettingActivity.this, "正在请求桌位列表数据，请稍候...");
            return false;
        }
        if (isDeletingTablesData) {
            ContentUtils.showMsg(DiningTableSettingActivity.this, "正在删除桌位，请稍候...");
            return false;
        }
        if (isRequestingTableInfoData) {
            ContentUtils.showMsg(DiningTableSettingActivity.this, "正在请求单个桌位详情，请稍候...");
            return false;
        }
        if (isRequestingPrintTicketData) {
            ContentUtils.showMsg(DiningTableSettingActivity.this, "正在请求小票数据，请稍候...");
            return false;
        }
        return true;
    }

    private void initBusinessState() {
        int resid;
        int color;
        int in_business_visibility;
        int out_business_visibility;
        if (isInBusiness) {
            resid = R.drawable.switch_background_shape_1;
            color = android.R.color.white;
            in_business_visibility = View.VISIBLE;
            out_business_visibility = View.GONE;
        } else {
            resid = R.drawable.switch_background_shape_2;
            color = R.color.color_ededed;
            in_business_visibility = View.GONE;
            out_business_visibility = View.VISIBLE;
        }
        switch_state.setBackgroundResource(resid);
        in_business_handle.setVisibility(in_business_visibility);
        in_business.setVisibility(in_business_visibility);//营业中
        out_business_handle.setVisibility(out_business_visibility);
        out_business.setVisibility(out_business_visibility);//休息中
        bottom.setBackgroundColor(getResources().getColor(color));
        view1.setBackgroundColor(getResources().getColor(color));
        view2.setBackgroundColor(getResources().getColor(color));
        tablesGridView.setBackgroundColor(getResources().getColor(color));
        add_table.setBackgroundColor(getResources().getColor(color));
    }

    private void setListener() {
        swipeRefreshLayout.setOnRefreshListener(this);
        app_bar.addOnOffsetChangedListener(this);
    }


    @Override
    @OnClick({R.id.back, R.id.menu_setting, R.id.add_table, R.id.delete_table,
            R.id.switch_state, R.id.call, R.id.pay, R.id.particulars, R.id.search})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                onTitleLeftClick();
                break;
            case R.id.menu_setting:
                boolean isLogin = ContentUtils.getLoginStatus(DiningTableSettingActivity.this);
                boolean re = JumpIntent
                        .jumpLogin_addShop(isLogin, API.TRADE, DiningTableSettingActivity.this);
                if (re) {
                    Intent intent_web = new Intent(DiningTableSettingActivity.this,
                            H5WebActivty.class);
                    intent_web.putExtra(Constants.NEDDLOGIN, false);
                    intent_web.putExtra("NEEDNOTTITLE", false);
                    intent_web.putExtra("Re", true);
                    intent_web.putExtra(WebActivty.T, "产品管理");
                    intent_web.putExtra(WebActivty.U, getUrl());
                    DiningTableSettingActivity.this.startActivity(intent_web);
                }
                break;
            case R.id.add_table:
                startActivity(new Intent(this, AddTablesetActivity.class));
                break;
            case R.id.delete_table:
                if (isRequestingTablesData
                        || isRequestingPrintTicketData
                        || isRequestingBeforFanZhuoCheckData
                        || isRequestingTableInfoData
                        || isRequestingFanZhuoData) {
                    ContentUtils.showMsg(DiningTableSettingActivity.this, "正在请求数据，请稍候...");
                    return;
                }
                if (data.isEmpty()) {
                    ContentUtils.showMsg(DiningTableSettingActivity.this, "您的店铺中没有桌位");
                    return;
                }
                if (delSelectButtonIsShowing) {
                    if (needDelList.isEmpty()) {
                        switchDelSelectButton(0);
                    } else {
                        DialogCommon dialog = new DialogCommon(DiningTableSettingActivity.this) {
                            @Override
                            public void onCheckClick() {
                                dismiss();
                            }

                            @Override
                            public void onOkClick() {
                                dismiss();
                                StringBuilder sb = new StringBuilder();
                                for (int i = 0; i < needDelList.size(); i++) {
                                    sb.append(needDelList.get(i));
                                    if (i < needDelList.size() - 1) {
                                        sb.append(",");
                                    }
                                }
                                deleteTableByIds(sb.toString());
                            }
                        };
                        dialog.setCancelable(false);
                        dialog.setCanceledOnTouchOutside(false);
                        dialog.setTextTitle("确定删除桌位？");
                        dialog.setTv_dialog_common_ok("确定");
                        dialog.setTv_dialog_common_cancel("取消");
                        dialog.show();
                    }
                } else {
                    switchDelSelectButton(1);
                }
                break;
            case R.id.switch_state:
                changeBusinessStatus();
                break;
            case R.id.pay://消费结算
                startActivity(new Intent(DiningTableSettingActivity.this, ConsumptionSettlementActivity.class));
                break;
            case R.id.call://呼叫服务
                startActivity(new Intent(DiningTableSettingActivity.this, CallServiceActivity.class));
                break;
            case R.id.particulars://到店明细
                startActivity(new Intent(DiningTableSettingActivity.this, ComeDetailActivity.class));
                break;
            case R.id.search:
                search.setFocusable(true);
                search.setFocusableInTouchMode(true);
                search.requestFocus();
                InputMethodManager inputManager = (InputMethodManager) search.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.showSoftInput(search, 0);
                break;
            default:
                super.onClick(view);
                break;
        }
    }

    //单个桌面详情
    private void getTableInfo(final TableSetBean bean) {
        okHttpsImp.tableInfo(new MyResultCallback<String>() {
            @Override
            public void onBefore(BaseRequest request) {
                super.onBefore(request);
                isRequestingTableInfoData = true;
            }

            @Override
            public void onAfter(@Nullable String s, @Nullable Exception e) {
                super.onAfter(s, e);
                isRequestingTableInfoData = false;
            }

            @Override
            public void onResponseResult(Result result) {
                String data = result.getData();
                if (!TextUtils.isEmpty(data)) {
                    com.alibaba.fastjson.JSONObject jsonObject = JSON.parseObject(data);
                    switch (jsonObject.getIntValue("tableStatus")) {
                        case 0:
                            tableId = bean.getTableId();
                            startActivity(new Intent(DiningTableSettingActivity.this, ScanningQRActivity.class)
                                    .putExtra("TABLENAME", bean.getTableName())
                                    .putExtra("TABLEID", tableId));
                            break;
                        case 1:
                            Intent i = new Intent(DiningTableSettingActivity.this, TableHasOrderedActivity.class);
                            i.putExtra("TABLENAME", bean.getTableName());
                            i.putExtra("TABLEID", bean.getTableId());
                            i.putExtra("DATA", data);
                            if (bean.isNew()) {
                                startActivityForResult(i, GOTO_NEW_ORDER_TABLE_ACTIVITY);
                            } else {
                                startActivity(i);
                            }
                            break;
                        case 2:
                            startActivity(new Intent(DiningTableSettingActivity.this, TableHasPaidActivity.class)
                                    .putExtra("TABLENAME", bean.getTableName())
                                    .putExtra("TABLEID", bean.getTableId())
                                    .putExtra("DATA", data));
                            break;
                    }
                }
            }

            @Override
            public void onResponseFailed(String msg) {

            }
        }, userShopInfoBean.getUserId(), userShopInfoBean.getBusinessId(), bean.getTableId());
    }

    private void switchDelSelectButton(int selectStatus) {
        for (int i = 0; i < data.size(); i++) {
            TableSetBean bean = data.get(i);
            bean.setSelectStatus(selectStatus);
            data.remove(i);
            data.add(i, bean);
        }
        adapter.replaceAll(data);
        if (selectStatus == 0) {
            delSelectButtonIsShowing = false;
            needDelList.clear();
        }
        if (selectStatus == 1) {
            delSelectButtonIsShowing = true;
        }
    }

    public String getUrl() {
        String url = API.TOSTORE_PRODUCT_MANAGEMENT;
        String bussniessId = BaseActivity.userShopInfoBean.getBusinessId();
        url = url + bussniessId;
        return url;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        switch (requestCode) {
            case GOTO_NEW_ORDER_TABLE_ACTIVITY:
                if (resultCode == RESULT_OK) {
                    String tableId = intent.getStringExtra("TABLEID");
                    int index = 0;
                    TableSetBean bean = null;
                    for (int i = 0; i < data.size(); i++) {
                        bean = data.get(i);
                        if (bean.getTableId().equals(tableId)) {
                            index = i;
                            break;
                        }
                    }
                    data.remove(index);
                    bean.setNew(false);
                    data.add(index, bean);
                    adapter.replaceAll(data);
                }
                break;
            default:
                super.onActivityResult(requestCode, resultCode, intent);
                break;
        }
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

    @Override
    protected void onResume() {
        super.onResume();
        getTableinfo();
    }

    /**
     * 消费结算、呼叫服务、到店明细总数接口
     */
    private void getDiningTableSettingDetail() {
        okHttpsImp.getDiningTableSettingDetail(new MyResultCallback<String>() {
            @Override
            public void onBefore(BaseRequest request) {
                super.onBefore(request);
                isRequestingOtherData = true;
            }

            @Override
            public void onAfter(@Nullable String s, @Nullable Exception e) {
                super.onAfter(s, e);
                isRequestingOtherData = false;
                setSwipeRefreshLoadedState();
            }

            @Override
            public void onResponseResult(Result result) {
                String reString = result.getData();
                if (!TextUtils.isEmpty(reString)) {
                    try {
                        JSONObject jsonObject = new JSONObject(reString);
                        String paidCount = jsonObject.getString("paidCount");//消费结算总数
                        String pushCount = jsonObject.getString("pushCount");//呼叫服务总数
                        String orderCount = jsonObject.getString("orderCount");//到店明细总数
                        initValueToText(pay_num, paidCount);
                        initValueToText(call_num, pushCount);
                        initValueToText(particulars_num, orderCount);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onResponseFailed(String msg) {
            }
        }, userShopInfoBean.getBusinessId(), userShopInfoBean.getUserId(), "111");
    }

    private void initValueToText(TextView view, String string) {
        if (string.equals("0")) {
            view.setVisibility(View.GONE);
            view.setText("");
        } else {
            view.setVisibility(View.VISIBLE);
            if (Integer.parseInt(string) > 99)
                view.setText("99+");
            else
                view.setText(string);
        }
    }

    public void getTableinfo() {
        okHttpsImp.getTableInfo(new MyResultCallback<String>() {
            @Override
            public void onAfter(@Nullable String s, @Nullable Exception e) {
                super.onAfter(s, e);
                isRequestingTablesData = false;
                getDiningTableSettingDetail();
            }

            @Override
            public void onBefore(BaseRequest request) {
                super.onBefore(request);
                isRequestingTablesData = true;
            }

            @Override
            public void onResponseResult(Result result) {
                String reString = result.getData();
                delSelectButtonIsShowing = false;
                needDelList.clear();
                data.clear();
                int isfbusiness;
                if (reString != null) {
                    JSONObject jsonObject;
                    try {
                        jsonObject = new JSONObject(reString);

                        isfbusiness = jsonObject.getInt("storeOpenSts");
                        if (isfbusiness == 0) {
                            isInBusiness = false;
                        } else {
                            isInBusiness = true;
                        }
                        initBusinessState();//修改或者确定运营状态

                        reString = jsonObject.getString("tableList");
                        ArrayList<TableSetBean> list = new ArrayList<TableSetBean>();
                        if (!TextUtils.isEmpty(reString)) {
                            list = (ArrayList<TableSetBean>) JSON.parseArray(reString, TableSetBean.class);
                        }
                        adapter.replaceAll(buildAdapterDataFromOriginalData(list));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onResponseFailed(String msg) {
                if (delSelectButtonIsShowing) {
                    switchDelSelectButton(0);
                }
            }
        }, userShopInfoBean.getBusinessId(), "");
    }

    private List<TableSetBean> buildAdapterDataFromOriginalData(ArrayList<TableSetBean> list) {
        ArrayList<TableSetBean> paidList = new ArrayList<TableSetBean>();//已付款
        ArrayList<TableSetBean> orderdList = new ArrayList<TableSetBean>(); //已下单
        ArrayList<TableSetBean> emptyList = new ArrayList<TableSetBean>();//空桌

        for (TableSetBean bean : list) {
            if (bean.getTableStatus() == 0) {//空位
                emptyList.add(bean);
            }
            if (bean.getTableStatus() == 1) {//已点餐
                if (!TextUtils.isEmpty(tableId) && tableId.equals(bean.getTableId()))
                    bean.setNew(true);
                orderdList.add(bean);
            }
            if (bean.getTableStatus() == 2) {//已支付
                paidList.add(bean);
            }
        }

        data.addAll(paidList);
        data.addAll(orderdList);
        data.addAll(emptyList);

        tableId = "";
        return data;
    }

    private boolean compareTo(List<TableSetBean> data, TableSetBean enity) {
        int s = data.size();
        if (enity != null) {
            for (int i = 0; i < s; i++) {
                if (enity.getTableId().equals(data.get(i).getTableId())) {
                    return true;
                }
            }
        }
        return false;
    }

    private void deleteTableByIds(String tableIds) {
        okHttpsImp.getDeleteTableId(new MyResultCallback<String>() {
            @Override
            public void onBefore(BaseRequest request) {
                super.onBefore(request);
                isDeletingTablesData = true;
            }

            @Override
            public void onAfter(@Nullable String s, @Nullable Exception e) {
                super.onAfter(s, e);
                isDeletingTablesData = false;
            }

            @Override
            public void onResponseResult(Result result) {
                ContentUtils.showMsg(DiningTableSettingActivity.this, result.getMsg());
                getTableinfo();
            }

            @Override
            public void onResponseFailed(String msg) {
            }
        }, userShopInfoBean.getBusinessId(), tableIds);
    }

    @Override
    public void onRefresh() {
        if (isDeletingTablesData
                || isRequestingTablesData
                || isRequestingOtherData
                || isRequestingFanZhuoData
                || isRequestingBeforFanZhuoCheckData
                || isRequestingTableInfoData)
            return;

        setSwipeRefreshLoadingState();

        getTableinfo();
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        this.verticalOffset = verticalOffset;
        setSwipeRefreshLoadedState();
    }

    private void changeBusinessStatus() {
        okHttpsImp.getmodifyBusinessStatus(new MyResultCallback<String>() {
            @Override
            public void onResponseResult(Result result) {
                isInBusiness = !isInBusiness;
                initBusinessState();
                ContentUtils.showMsg(DiningTableSettingActivity.this,
                        isInBusiness ? "已切换到营业状态" : "已切换到休息状态");
            }

            @Override
            public void onResponseFailed(String msg) {
            }
        }, userShopInfoBean.getBusinessId(), isInBusiness ? "0" : "1");
    }

    @Override
    @OnItemClick({R.id.tables_grid_view, R.id.search_result_grid_view})
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.tables_grid_view:
                TableSetBean bean = data.get(position);
                if (delSelectButtonIsShowing) {
                    switch (bean.getSelectStatus()) {
                        case 1:
                            bean.setSelectStatus(2);
                            needDelList.add(bean.getTableId());
                            break;
                        case 2:
                            bean.setSelectStatus(1);
                            needDelList.remove(bean.getTableId());
                            break;
                    }
                    data.remove(position);
                    data.add(position, bean);
                    adapter.replaceAll(data);
                } else {
                    getTableInfo(bean);
                }
                break;
            case R.id.search_result_grid_view:
                getTableInfo(searchResultAdapter.getItem(position));
                break;
        }
    }

    /**
     * 设置顶部加载完毕的状态
     */
    private void setSwipeRefreshLoadedState() {
        if (swipeRefreshLayout != null) {
            if (verticalOffset < 0 || isDeletingTablesData) {
                swipeRefreshLayout.setEnabled(false);
            } else {
                swipeRefreshLayout.setEnabled(true);
            }
            swipeRefreshLayout.setRefreshing(false);
        }
    }

    /**
     * 设置顶部正在加载的状态
     */
    private void setSwipeRefreshLoadingState() {
        if (swipeRefreshLayout != null) {
            swipeRefreshLayout.setRefreshing(true);
            // 防止多次重复刷新
            swipeRefreshLayout.setEnabled(false);
        }
    }

    @Override
    @OnTextChanged({R.id.search})
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        search.beforeTextChanged(s, start, count, after);
    }

    @Override
    @OnTextChanged({R.id.search})
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        search.onTextChanged(s, start, before, count);
    }

    @Override
    @OnTextChanged({R.id.search})
    public void afterTextChanged(Editable s) {
        search.afterTextChanged(s);
        searchText = s.toString();
        if (TextUtils.isEmpty(searchText)) {
            objectOutAnim.start();
        } else {
            objectInAnim.start();
            new SearchTableAsyncTask().execute(searchText);
        }
    }

    private class SearchTableAsyncTask extends AsyncTask<String, Void, ArrayList<TableSetBean>> {

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }

        @Override
        protected void onCancelled(ArrayList<TableSetBean> tableSetBeen) {
            super.onCancelled(tableSetBeen);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(ArrayList<TableSetBean> tableSetBeen) {
            super.onPostExecute(tableSetBeen);
            searchResultAdapter.replaceAll(tableSetBeen);
        }

        public SearchTableAsyncTask() {
            super();
        }

        @Override
        protected ArrayList<TableSetBean> doInBackground(String... params) {
            ArrayList<TableSetBean> list = new ArrayList<>();
            for (TableSetBean bean : data) {
                if (bean.getTableName().contains(params[0])) {
                    list.add(bean);
                }
            }
            return list;
        }
    }
}