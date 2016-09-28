package com.lianbi.mezone.b.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.lianbi.mezone.b.bean.OrderContent;
import com.lianbi.mezone.b.ui.OrderContentActivity;
import com.lianbi.mezone.b.ui.OrderLookUpActivity;
import com.xizhi.mezone.b.R;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.com.hgh.baseadapter.SlidingRecyclerViewAdapter;
import cn.com.hgh.refreshlayoutsliding.BGARefreshLayout;
import cn.com.hgh.refreshlayoutsliding.DefineBAGRefreshWithLoadView;
import cn.com.hgh.utils.ContentUtils;

public class OrederFragment extends Fragment implements BGARefreshLayout.BGARefreshLayoutDelegate , SlidingRecyclerViewAdapter.IonSlidingViewClickListener {

    Activity mActivity;
    OrderLookUpActivity mOrderLookUpActivity;
    OrderContentActivity mOrderContentActivity;
    ArrayList<OrderContent> mDatas = new ArrayList<OrderContent>();
    boolean isResh;
    boolean isLoad;
    boolean isDelete;
    int listPosition = -1;
    @Bind(R.id.fm_orederfragment_iv_empty)
    ImageView fmOrederfragmentIvEmpty;
    @Bind(R.id.define_sliding_bga)
    BGARefreshLayout mBGARefreshLayout;
    @Bind(R.id.define_sliding_recycler)
    RecyclerView define_sliding_recycler;
    /** 设置刷新和加载 */
    private DefineBAGRefreshWithLoadView mDefineBAGRefreshWithLoadView = null;
    /** 数据填充adapter */
    private SlidingRecyclerViewAdapter mSlidingRecyclerViewAdapter = null;
    private boolean isFirstIn = true;
    private boolean  havedata=false;
    private boolean mIsRefreshing=false;
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
        mBGARefreshLayout.setDelegate(this);
        setRecyclerCommadapter();
        setBgaRefreshLayout();
        setRecyclerView();
    }
    public void  stopScroll(){
        define_sliding_recycler.scrollToPosition(0);
    }
    /** 数据填充 */
    private void setRecyclerCommadapter() {
        mSlidingRecyclerViewAdapter = new SlidingRecyclerViewAdapter(mActivity);
        define_sliding_recycler.setAdapter(mSlidingRecyclerViewAdapter);
        mSlidingRecyclerViewAdapter.setDeleteLister(this);
    }
    /**
     * 设置 BGARefreshLayout刷新和加载
     * */
    private void setBgaRefreshLayout() {
        mDefineBAGRefreshWithLoadView = new DefineBAGRefreshWithLoadView(mActivity, true , true);
        mBGARefreshLayout.setRefreshViewHolder(mDefineBAGRefreshWithLoadView);//设置刷新样式
        mDefineBAGRefreshWithLoadView.updateLoadingMoreText("正在加载...");
    }
    /** 设置RecyclerView的布局方式 */
    private void setRecyclerView(){
        define_sliding_recycler.setOnTouchListener(
                new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        if (mIsRefreshing) {
                            return true;
                        } else {
                            return false;
                        }
                    }
                }
        );
        //垂直listview显示方式
        define_sliding_recycler.setLayoutManager(new LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false));
    }
    /** 刷新 */
    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        mDefineBAGRefreshWithLoadView.updateLoadingMoreText("正在加载...");
        mDefineBAGRefreshWithLoadView.showLoadingMoreImg();
        mIsRefreshing=true;
        if (mActivity instanceof OrderLookUpActivity) {
            mOrderLookUpActivity.getOrderInfo(true, false, "Y");
        } else if (mActivity instanceof OrderContentActivity) {
            if (mOrderContentActivity.timeNoselected()) {
                ContentUtils.showMsg(mActivity, "请选择查询时间");
                hideRefreshView(true,true);
                return;
            }
            mOrderContentActivity.getOrderInfo(true, false, "Y");
        }

    }
    /** 加载 */
    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        mIsRefreshing=true;
        if (mActivity instanceof OrderLookUpActivity) {
            mOrderLookUpActivity.getOrderInfo(false, true, "Y");
        } else if (mActivity instanceof OrderContentActivity) {
            mOrderContentActivity.getOrderInfo(false, true, "Y");
        }
        return true;
    }
    @Override
    public void onItemClick(View view, int position) {
    }
    @Override
    public void onLongItemClick(View view, int position) {
    }
    /** 删除事件 */
    @Override
    public void onDeleteBtnCilck(View view, int position) {
        listPosition=position;
        if (mActivity instanceof OrderLookUpActivity) {
            mOrderLookUpActivity.delteOrderMsg(mDatas.get(position).getOrderNo(), position);
        } else if (mActivity instanceof OrderContentActivity) {
            mOrderContentActivity.delteOrderMsg(mDatas.get(position).getOrderNo(), position);
        }
    }

    public void doSomthing(ArrayList<OrderContent> cuArrayList, int position) {

        if (cuArrayList != null && cuArrayList.size() > 0) {
            mDatas = cuArrayList;
            mSlidingRecyclerViewAdapter.setData(mDatas);
            fmOrederfragmentIvEmpty.setVisibility(View.GONE);
            mBGARefreshLayout.setVisibility(View.VISIBLE);
        } else {
            if (mDatas.size() > 0) {
                fmOrederfragmentIvEmpty.setVisibility(View.GONE);
                mBGARefreshLayout.setVisibility(View.VISIBLE);
                mSlidingRecyclerViewAdapter.setData(mDatas);
            } else {
                fmOrederfragmentIvEmpty.setVisibility(View.VISIBLE);
                mBGARefreshLayout.setVisibility(View.GONE);
            }
        }

    }
    //用于判断是没有查到数据还是没有选时间
    public void timeNoselected(boolean timeNoselect) {
        if (timeNoselect == true) {
            mSlidingRecyclerViewAdapter.setData(mDatas);
            fmOrederfragmentIvEmpty.setVisibility(View.GONE);
            mBGARefreshLayout.setVisibility(View.VISIBLE);
        }


    }

    public void hideRefreshView(boolean isResh,boolean   havedata) {
        this.havedata=havedata;
        mIsRefreshing=false;
        if(havedata==false){
            mDefineBAGRefreshWithLoadView.updateLoadingMoreText("到底啦...");
            mDefineBAGRefreshWithLoadView.hideLoadingMoreImg();
        }
        if(isResh==true&&mBGARefreshLayout!=null){
            mBGARefreshLayout.endRefreshing();
        }  else  if(mBGARefreshLayout!=null){
            mBGARefreshLayout.endLoadingMore();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    public void upData() {
        mDatas.remove(listPosition);
        hideRefreshView(true,true);
        mSlidingRecyclerViewAdapter.setData(mDatas);
    }


}
