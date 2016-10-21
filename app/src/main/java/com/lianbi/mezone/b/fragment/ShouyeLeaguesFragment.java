package com.lianbi.mezone.b.fragment;

/*
 * @创建者     master
 * @创建时间   2016/10/19 18:18
 * @描述       首页-商圈联盟
 *
 * @更新者     $Author$ 
 * @更新时间   $Date$
 * @更新描述
 */

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lianbi.mezone.b.httpresponse.OkHttpsImp;
import com.lianbi.mezone.b.ui.MainActivity;
import com.xizhi.mezone.b.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ShouyeLeaguesFragment extends Fragment {

    @Bind(R.id.tv_include_title)
    TextView tvIncludeTitle;
    @Bind(R.id.llt_shouyeLeagues_flow_show)
    LinearLayout lltShouyeLeaguesFlowShow;
    @Bind(R.id.tv_shouyeLeagues_flow_detail)
    TextView tvShouyeLeaguesFlowDetail;
    @Bind(R.id.tv_include_othertitle)
    TextView tvIncludeOthertitle;
    @Bind(R.id.ind_shouyeLeagues_area)
    LinearLayout indShouyeLeaguesArea;
    @Bind(R.id.llt_shouyeLeagues_salenum_show)
    LinearLayout lltShouyeLeaguesSalenumShow;
    private MainActivity mActivity;
    private OkHttpsImp mOkHttpsImp;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fm_shouyeleagues, null);
        ButterKnife.bind(this, view);
        mActivity = (MainActivity) getActivity();
        mOkHttpsImp = OkHttpsImp.SINGLEOKHTTPSIMP.newInstance(mActivity);
        initViewAndData();
        return view;
    }

    public void initViewAndData() {


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
