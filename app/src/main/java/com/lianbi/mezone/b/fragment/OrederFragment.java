package com.lianbi.mezone.b.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lianbi.mezone.b.bean.OrderContent;
import com.lianbi.mezone.b.ui.OrderContentActivity;
import com.lianbi.mezone.b.ui.OrderLookUpActivity;
import com.xizhi.mezone.b.R;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.com.hgh.baseadapter.BaseAdapterHelper;
import cn.com.hgh.baseadapter.QuickAdapter;
import cn.com.hgh.view.SlideListView2;

public class OrederFragment extends Fragment {

    @Bind(R.id.fm_orederfragment_listView)
    SlideListView2 fmOrederfragmentListView;
    @Bind(R.id.fm_orederfragment_iv_empty)
    ImageView fmOrederfragmentIvEmpty;
    Activity  mActivity;
    OrderLookUpActivity  mOrderLookUpActivity;
    OrderContentActivity mOrderContentActivity;
    public QuickAdapter<OrderContent> mAdapter;
    ArrayList<OrderContent> mDatas = new ArrayList<OrderContent>();

    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fm_ordercontent, null);
        ButterKnife.bind(this, view);
        mActivity =getActivity();
        if(mActivity instanceof OrderLookUpActivity){
            mOrderLookUpActivity=(OrderLookUpActivity)mActivity;
        }else
        if(mActivity instanceof OrderContentActivity){
            mOrderContentActivity=(OrderContentActivity)mActivity;
        }
        initView(view);
        return view;
    }

    private void initView(View view) {
//        initListAdapter();
    }
    /**
     * 初始化list Adapter
     */
    private void initListAdapter() {
        mAdapter = new QuickAdapter<OrderContent>(mActivity,
                R.layout.leavemessageitem, mDatas) {

            @Override
            protected void convert(final BaseAdapterHelper helper,
                                   final OrderContent item) {
                TextView  tv_info = helper.getView(R.id.tv_info);
                ImageView iv_check = helper.getView(R.id.iv_check);
                TextView tv_chshenhe=helper.getView(R.id.tv_chshenhe);
                TextView tv_tablename = helper.getView(R.id.tv_tablename);
                TextView tv_leavemessage = helper.getView(R.id.tv_leavemessage);
                TextView time = helper.getView(R.id.tv_time);
                tv_leavemessage = helper.getView(R.id.tv_leavemessage);

                tv_tablename.setText(item.getOrderID());
                time.setText(String.valueOf(item.getPaystate()));
                tv_leavemessage.setText(item.getOrdertime());

                helper.getView(R.id.tv_chdelete).setOnClickListener(// 删除
                        new View.OnClickListener() {

                            @Override
                            public void onClick(View v) {
                            fmOrederfragmentListView.slideBack();
                            mDatas.remove(item);
                            mAdapter.replaceAll(mDatas);
                            ArrayList<String> ids = new ArrayList<String>();
                            ids.add(String.valueOf(item.getOrderID()));
                            if(mActivity instanceof OrderLookUpActivity){
                                mOrderLookUpActivity.delteOrderMsg(ids,true);
                            }else
                            if(mActivity instanceof OrderLookUpActivity){
                                mOrderLookUpActivity.delteOrderMsg(ids,true);
                            }

                            }
                        });
            }
        };

        fmOrederfragmentListView.setAdapter(mAdapter);
    }
    public void doSomthing(ArrayList<OrderContent> cuArrayList, int position) {

        if (cuArrayList != null && cuArrayList.size() > 0) {
            mDatas = cuArrayList;
            mAdapter.replaceAll(mDatas);
            fmOrederfragmentIvEmpty.setVisibility(View.GONE);
            fmOrederfragmentListView.setVisibility(View.VISIBLE);
        } else {
            if (mDatas.size() > 0) {
                fmOrederfragmentIvEmpty.setVisibility(View.GONE);
                fmOrederfragmentListView.setVisibility(View.VISIBLE);
                mAdapter.replaceAll(mDatas);
            } else {
                fmOrederfragmentIvEmpty.setVisibility(View.VISIBLE);
                fmOrederfragmentListView.setVisibility(View.GONE);
            }
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
