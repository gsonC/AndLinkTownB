package com.lianbi.mezone.b.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lianbi.mezone.b.app.Constants;
import com.lianbi.mezone.b.bean.OneItemTableSetBean;
import com.lianbi.mezone.b.bean.TableSetBean;
import com.lianbi.mezone.b.bean.WebProductManagementBean;
import com.lianbi.mezone.b.httpresponse.API;
import com.xizhi.mezone.b.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.hgh.baseadapter.BaseAdapterHelper;
import cn.com.hgh.baseadapter.QuickAdapter;
import cn.com.hgh.utils.AbViewUtil;
import cn.com.hgh.utils.ContentUtils;
import cn.com.hgh.utils.JumpIntent;
import cn.com.hgh.view.AbPullToRefreshView;
import cn.com.hgh.view.ClearEditText;

import static cn.com.hgh.utils.CryptTool.encryptionUrl;

/*
* 桌面设置
* */
public class DiningTableSettingActivity extends BluetoothBaseActivity implements
        AbPullToRefreshView.OnHeaderRefreshListener, AbPullToRefreshView.OnFooterLoadListener {
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
    @Bind(R.id.dining_table)
    AbPullToRefreshView pullToRefreshView;
    @Bind(R.id.table_list_view)
    ListView table_list_view;

    private RelativeLayout view1;
    private RelativeLayout switch_state;
    private View in_business_handle;
    private TextView in_business;//营业中
    private View out_business_handle;
    private TextView out_business;//休息中
    private LinearLayout view2;
    private TextView pay_num;
    private TextView call_num;
    private TextView particulars_num;
    private View view3;

    private QuickAdapter<OneItemTableSetBean> adapter;
    private List<OneItemTableSetBean> data = new ArrayList<>();

    private boolean isInBusiness;

    private static final int REQUEST_CODE_ADDTABLE_RESULT = 1010;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dining_table_setting);
        ButterKnife.bind(this);

        isInBusiness = true;

        setListener();

        addHeaderViewToListView();

        initAdapter();
    }

    private void initAdapter() {
        adapter = new QuickAdapter<OneItemTableSetBean>(DiningTableSettingActivity.this, R.layout.tablesetting_listview_item, data) {
            @Override
            protected void convert(BaseAdapterHelper helper, OneItemTableSetBean item) {
                ArrayList<TableSetBean> list = item.getTableSetBeanList();
                if (list != null && !list.isEmpty()) {
                    if (list.size() >= 1) {
                        View table_1 = helper.getView(R.id.item_table_1);
                        TextView table_state_1 = (TextView) table_1.findViewById(R.id.table_state);
                        TextView table_index_1 = (TextView) table_1.findViewById(R.id.table_index);
                        TextView have_new_1 = (TextView) table_1.findViewById(R.id.have_new);
                        TextView table_may_do_1 = (TextView) table_1.findViewById(R.id.curr_table_may_do);
                        TextView pay_number_1 = (TextView) table_1.findViewById(R.id.pay_number);
                        TextView person_num_1 = (TextView) table_1.findViewById(R.id.person_num);
                        TextView unit_1 = (TextView) table_1.findViewById(R.id.unit);
                        ImageView table_delete_1 = (ImageView) table_1.findViewById(R.id.tableset_delete);
                    }

                    if (list.size() >= 2) {
                        View table_2 = helper.getView(R.id.item_table_2);
                        TextView table_state_2 = (TextView) table_2.findViewById(R.id.table_state);
                        TextView table_index_2 = (TextView) table_2.findViewById(R.id.table_index);
                        TextView have_new_2 = (TextView) table_2.findViewById(R.id.have_new);
                        TextView table_may_do_2 = (TextView) table_2.findViewById(R.id.curr_table_may_do);
                        TextView pay_number_2 = (TextView) table_2.findViewById(R.id.pay_number);
                        TextView person_num_2 = (TextView) table_2.findViewById(R.id.person_num);
                        TextView unit_2 = (TextView) table_2.findViewById(R.id.unit);
                        ImageView table_delete_2 = (ImageView) table_2.findViewById(R.id.tableset_delete);
                    }
                    if (list.size() == 3) {
                        View table_3 = helper.getView(R.id.item_table_3);
                        TextView table_state_3 = (TextView) table_3.findViewById(R.id.table_state);
                        TextView table_index_3 = (TextView) table_3.findViewById(R.id.table_index);
                        TextView have_new_3 = (TextView) table_3.findViewById(R.id.have_new);
                        TextView table_may_do_3 = (TextView) table_3.findViewById(R.id.curr_table_may_do);
                        TextView pay_number_3 = (TextView) table_3.findViewById(R.id.pay_number);
                        TextView person_num_3 = (TextView) table_3.findViewById(R.id.person_num);
                        TextView unit_3 = (TextView) table_3.findViewById(R.id.unit);
                        ImageView table_delete_3 = (ImageView) table_3.findViewById(R.id.tableset_delete);
                    }
                }
            }
        };
        table_list_view.setAdapter(adapter);
    }

    private void addHeaderViewToListView() {
        View headerView = LayoutInflater.from(this).inflate(R.layout.table_setting_listview_head, null);
        view1 = (RelativeLayout) headerView.findViewById(R.id.view_1);
        switch_state = (RelativeLayout) headerView.findViewById(R.id.switch_state);
        in_business_handle = headerView.findViewById(R.id.in_business_handle);
        in_business = (TextView) headerView.findViewById(R.id.in_business);//营业中
        out_business_handle = headerView.findViewById(R.id.out_business_handle);
        out_business = (TextView) headerView.findViewById(R.id.out_business);//休息中
        view2 = (LinearLayout) headerView.findViewById(R.id.view_2);
        pay_num = (TextView) headerView.findViewById(R.id.pay_num);
        call_num = (TextView) headerView.findViewById(R.id.call_num);
        particulars_num = (TextView) headerView.findViewById(R.id.particulars_num);
        view3 = headerView.findViewById(R.id.view_3);

        switch_state.setOnClickListener(this);
        headerView.findViewById(R.id.pay).setOnClickListener(this);
        headerView.findViewById(R.id.call).setOnClickListener(this);
        headerView.findViewById(R.id.particulars).setOnClickListener(this);

        initBusinessState();

        table_list_view.addHeaderView(headerView);
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
        view3.setBackgroundColor(getResources().getColor(color));
        table_list_view.setBackgroundColor(getResources().getColor(color));
        add_table.setBackgroundColor(getResources().getColor(color));
    }

    private void setListener() {
        pullToRefreshView.setOnHeaderRefreshListener(this);
        pullToRefreshView.setOnFooterLoadListener(this);
    }

    @Override
    public void onFooterLoad(AbPullToRefreshView view) {
//        int a = data.size();
//        ArrayList<Integer> b = new ArrayList<>();
//        for (int i = a; i < a + 10; i++) {
//            b.add(Integer.valueOf(i));
//        }
//        adapter.addData(b);
        pullToRefreshView.onFooterLoadFinish();
    }

    @Override
    public void onHeaderRefresh(AbPullToRefreshView view) {
//        data.clear();
//        for (int i = 0; i < 10; i++) {
//            data.add(Integer.valueOf(i));
//        }
//        adapter.setData(data);
        pullToRefreshView.onHeaderRefreshFinish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    @Override
    @OnClick({R.id.back, R.id.menu_setting, R.id.add_table, R.id.delete_table})
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.back:
                onTitleLeftClick();
                break;
            case R.id.menu_setting:
                boolean isLogin = ContentUtils.getLoginStatus(DiningTableSettingActivity.this);
                boolean re = JumpIntent
                        .jumpLogin_addShop(isLogin, API.TRADE, DiningTableSettingActivity.this);
                if (re) {
                    boolean hasProduct = ContentUtils.getSharePreBoolean(DiningTableSettingActivity.this,
                            Constants.SHARED_PREFERENCE_NAME,
                            Constants.HAS_PRODUCT);
                    if (hasProduct) {
                        Intent intent_web = new Intent(DiningTableSettingActivity.this,
                                H5WebActivty.class);
                        intent_web.putExtra(Constants.NEDDLOGIN, false);
                        intent_web.putExtra("NEEDNOTTITLE", false);
                        intent_web.putExtra("Re", true);
                        intent_web.putExtra(WebActivty.T, "产品管理");
                        intent_web.putExtra(WebActivty.U, getUrl());
                        DiningTableSettingActivity.this.startActivity(intent_web);
                    } else {
//                        Intent intent_more = new Intent(DiningTableSettingActivity.this,
//                                ServiceMallActivity.class);
//                        DiningTableSettingActivity.this.startActivityForResult(intent_more,
//                                MainActivity.this.SERVICEMALLSHOP_CODE);
//                        ContentUtils.showMsg(mMainActivity, "请下载对应模块进行产品编辑");
                    }
                }
                break;
            case R.id.add_table:
                startActivityForResult(new Intent(this, AddTablesetActivity.class), REQUEST_CODE_ADDTABLE_RESULT);
                break;
            case R.id.delete_table:
                break;
            case R.id.switch_state:
                changeBusinessState();
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
        }
    }

    public String getUrl() {
        String url = API.TOSTORE_PRODUCT_MANAGEMENT;
        String bussniessId = BaseActivity.userShopInfoBean.getBusinessId();
        WebProductManagementBean data = new WebProductManagementBean();
        data.setBusinessId(bussniessId);
        // String dataJson = JSONObject.fromObject(data).toString();
        String dataJson = com.alibaba.fastjson.JSONObject.toJSON(data)
                .toString();
        // JSONObject jsonObject = new JSONObject();
        // jsonObject.

        url = encryptionUrl(url, dataJson);
        return url;
    }

    private void changeBusinessState() {
        int resid;
        int color;
        int in_business_visibility;
        int out_business_visibility;
        if (isInBusiness) {
            resid = R.drawable.switch_background_shape_2;
            in_business_visibility = View.GONE;
            out_business_visibility = View.VISIBLE;
            color = R.color.color_ededed;
            isInBusiness = false;
        } else {
            resid = R.drawable.switch_background_shape_1;
            in_business_visibility = View.VISIBLE;
            out_business_visibility = View.GONE;
            color = android.R.color.white;
            isInBusiness = true;
        }
        switch_state.setBackgroundResource(resid);

        in_business_handle.setVisibility(in_business_visibility);
        in_business.setVisibility(in_business_visibility);//营业中
        out_business_handle.setVisibility(out_business_visibility);
        out_business.setVisibility(out_business_visibility);//休息中

        bottom.setBackgroundColor(getResources().getColor(color));
        view1.setBackgroundColor(getResources().getColor(color));
        view2.setBackgroundColor(getResources().getColor(color));
        view3.setBackgroundColor(getResources().getColor(color));
        table_list_view.setBackgroundColor(getResources().getColor(color));
        add_table.setBackgroundColor(getResources().getColor(color));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CODE_ADDTABLE_RESULT:// 添加桌子
                    break;
            }
        }
    }
}