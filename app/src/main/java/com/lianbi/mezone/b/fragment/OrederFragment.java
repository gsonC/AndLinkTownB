package com.lianbi.mezone.b.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lianbi.mezone.b.bean.InfoMessageBean;
import com.lianbi.mezone.b.ui.OrderContentActivity;
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
    private OrderContentActivity mActivity;
    public QuickAdapter<InfoMessageBean> mAdapter;
    ArrayList<InfoMessageBean> mDatas = new ArrayList<InfoMessageBean>();

    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fm_ordercontent, null);
        mActivity = (OrderContentActivity) getActivity();
        initView(view);
        ButterKnife.bind(this, view);
        return view;
    }

    private void initView(View view) {

    }
    /**
     * 初始化list Adapter
     */
    private void initListAdapter() {
        mAdapter = new QuickAdapter<InfoMessageBean>(mActivity,
                R.layout.leavemessageitem, mDatas) {

            @Override
            protected void convert(final BaseAdapterHelper helper,
                                   final InfoMessageBean item) {
                TextView  tv_info = helper.getView(R.id.tv_info);
                ImageView iv_check = helper.getView(R.id.iv_check);
                TextView tv_chshenhe=helper.getView(R.id.tv_chshenhe);
                TextView tv_tablename = helper.getView(R.id.tv_tablename);
                TextView tv_leavemessage = helper.getView(R.id.tv_leavemessage);
                TextView time = helper.getView(R.id.tv_time);
                tv_leavemessage = helper.getView(R.id.tv_leavemessage);

                tv_tablename.setText(item.getTableName());
                time.setText(String.valueOf(item.getCreateTime()));
                tv_leavemessage.setText(item.getContent());

                helper.getView(R.id.tv_chdelete).setOnClickListener(// 删除
                        new View.OnClickListener() {

                            @Override
                            public void onClick(View v) {

                            }
                        });
            }
        };

        fmOrederfragmentListView.setAdapter(mAdapter);
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
