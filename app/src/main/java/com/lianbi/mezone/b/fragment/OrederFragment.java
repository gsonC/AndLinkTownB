package com.lianbi.mezone.b.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lianbi.mezone.b.bean.OrderContent;
import com.lianbi.mezone.b.ui.OrderContentActivity;
import com.lianbi.mezone.b.ui.OrderLookUpActivity;
import com.xizhi.mezone.b.R;

import java.math.BigDecimal;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.com.hgh.baseadapter.BaseAdapterHelper;
import cn.com.hgh.baseadapter.QuickAdapter;
import cn.com.hgh.utils.AbPullHide;
import cn.com.hgh.utils.ContentUtils;
import cn.com.hgh.view.AbPullToRefreshView;
import cn.com.hgh.view.SwipeListView;

public class OrederFragment extends Fragment {

    Activity mActivity;
    OrderLookUpActivity mOrderLookUpActivity;
    OrderContentActivity mOrderContentActivity;
    public QuickAdapter<OrderContent> mAdapter;
    ArrayList<OrderContent> mDatas = new ArrayList<OrderContent>();
    boolean isResh;
    boolean isLoad;
    boolean isDelete;
    int listPosition = -1;
    @Bind(R.id.act_addmembers_listview)
    SwipeListView actOrederListview;
    @Bind(R.id.act_addmembers_abpulltorefreshview)
    AbPullToRefreshView actOrederAbpulltorefreshview;
    @Bind(R.id.fm_orederfragment_iv_empty)
    ImageView fmOrederfragmentIvEmpty;


    private boolean isFirstIn = true;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fm_ordercontent, null);
        ButterKnife.bind(this, view);
        mActivity = getActivity();
        if (mActivity instanceof OrderLookUpActivity) {
            mOrderLookUpActivity = (OrderLookUpActivity) mActivity;
        } else if (mActivity instanceof OrderContentActivity) {
            mOrderContentActivity = (OrderContentActivity) mActivity;
        }
        initView(view);
        return view;
    }

    private void initView(View view) {
        actOrederAbpulltorefreshview.setLoadMoreEnable(true);
        actOrederAbpulltorefreshview.setPullRefreshEnable(true);
        actOrederAbpulltorefreshview
                .setOnHeaderRefreshListener(new AbPullToRefreshView.OnHeaderRefreshListener() {

                    @Override
                    public void onHeaderRefresh(AbPullToRefreshView view) {
                        if (mActivity instanceof OrderLookUpActivity) {
                            mOrderLookUpActivity.getOrderInfo(true, false, "Y");
                        } else if (mActivity instanceof OrderContentActivity) {
                            if (mOrderContentActivity.timeNoselected()) {
                                ContentUtils.showMsg(mActivity, "请选择查询时间");
                                hideRefreshView(true);
                                return;
                            }
                            mOrderContentActivity.getOrderInfo(true, false, "Y");
                        }
                    }

                });
        actOrederAbpulltorefreshview
                .setOnFooterLoadListener(new AbPullToRefreshView.OnFooterLoadListener() {

                    @Override
                    public void onFooterLoad(AbPullToRefreshView view) {
                        if (mActivity instanceof OrderLookUpActivity) {
                            mOrderLookUpActivity.getOrderInfo(false, true, "Y");
                        } else if (mActivity instanceof OrderContentActivity) {
                            mOrderContentActivity.getOrderInfo(false, true, "Y");
                        }
                    }
                });
        initListAdapter();
    }

    /**
     * 初始化list Adapter
     */
    private void initListAdapter() {
        mAdapter = new QuickAdapter<OrderContent>(mActivity,
                R.layout.item_order_content, mDatas) {

            @Override
            protected void convert(final BaseAdapterHelper helper,
                                   final OrderContent item) {

                TextView tv_item_orderinfo_num = helper.getView(R.id.tv_item_orderinfo_num);
                TextView tv_item_orderinfo_state = helper.getView(R.id.tv_item_orderinfo_state);
                TextView tv_item_orderinfo_paytime = helper.getView(R.id.tv_item_orderinfo_paytime);
                TextView tv_item_orderinfo_price = helper.getView(R.id.tv_item_orderinfo_price);
                LinearLayout item_left = helper.getView(R.id.item_left);
                LinearLayout item_right = helper.getView(R.id.item_right);
                LinearLayout.LayoutParams lp1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                item_left.setLayoutParams(lp1);
                LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(actOrederListview.getRightViewWidth(), LinearLayout.LayoutParams.MATCH_PARENT);
                item_right.setLayoutParams(lp2);
                tv_item_orderinfo_num.setText(item.getOrderNo());
                tv_item_orderinfo_paytime.setText(item.getTxnTime());
                tv_item_orderinfo_price.setText(String.valueOf(item.getTxnAmt()));
                if (item.getOrderStatus().equals("03")) {
                    tv_item_orderinfo_state.setText("支付成功");
                } else if (item.getOrderStatus().equals("04")) {
                    tv_item_orderinfo_state.setText("支付失败");
                }
                String amt = BigDecimal.valueOf(Long.valueOf(item.getTxnAmt()))
                        .divide(new BigDecimal(100)).toString();
                tv_item_orderinfo_price.setText(String.valueOf(amt));

                String time = item.getTxnTime();
                String year = time.substring(0, 4);
                String months = time.substring(4, 6);
                String daytime = time.substring(6, 8);
                String hour = time.substring(8, 10);
                String minute = time.substring(10, 12);
                String second = time.substring(12, 14);
                tv_item_orderinfo_paytime.setText(year + "-" + months + "-"
                        + daytime + " " + hour + ":" + minute + ":" + second);


                helper.getView(R.id.tv_chdelete).setOnClickListener(// 删除
                        new View.OnClickListener() {

                            @Override
                            public void onClick(View v) {
                                actOrederListview.slideBack();
                                ArrayList<String> ids = new ArrayList<String>();
                                ids.add(String.valueOf(item.getOrderNo()));
                                listPosition = helper.getPosition();
                                if (mActivity instanceof OrderLookUpActivity) {
                                    mOrderLookUpActivity.delteOrderMsg(item.getOrderNo(), listPosition);
                                } else if (mActivity instanceof OrderContentActivity) {
                                    mOrderContentActivity.delteOrderMsg(item.getOrderNo(), listPosition);
                                }

                            }
                        });
            }
        };

        actOrederListview.setAdapter(mAdapter);
    }

    public void doSomthing(ArrayList<OrderContent> cuArrayList, int position) {

        if (cuArrayList != null && cuArrayList.size() > 0) {
            mDatas = cuArrayList;
            mAdapter.replaceAll(mDatas);
            fmOrederfragmentIvEmpty.setVisibility(View.GONE);
            actOrederAbpulltorefreshview.setVisibility(View.VISIBLE);
        } else {
            if (mDatas.size() > 0) {
                fmOrederfragmentIvEmpty.setVisibility(View.GONE);
                actOrederAbpulltorefreshview.setVisibility(View.VISIBLE);
                mAdapter.replaceAll(mDatas);
            } else {
                fmOrederfragmentIvEmpty.setVisibility(View.VISIBLE);
                actOrederAbpulltorefreshview.setVisibility(View.GONE);
            }
        }

    }

    //用于判断是没有查到数据还是没有选时间
    public void timeNoselected(boolean timeNoselect) {
        if (timeNoselect == true) {
            mAdapter.replaceAll(mDatas);
            fmOrederfragmentIvEmpty.setVisibility(View.GONE);
            actOrederAbpulltorefreshview.setVisibility(View.VISIBLE);
        }


    }

    public void hideRefreshView(boolean isResh) {

        AbPullHide.hideRefreshView(isResh,
                actOrederAbpulltorefreshview);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    public void upData() {
        mDatas.remove(listPosition);
        hideRefreshView(true);
        mAdapter.replaceAll(mDatas);
    }


}
