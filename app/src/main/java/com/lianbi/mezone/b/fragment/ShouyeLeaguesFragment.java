package com.lianbi.mezone.b.fragment;

/*
 * @创建者     master
 * @创建时间   2016/10/19 18:18
 * @描述
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

import com.xizhi.mezone.b.R;

public class ShouyeLeaguesFragment extends Fragment {

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		View view = inflater.inflate(R.layout.fm_shouyeleagues, null);
		return view;

	}
}
