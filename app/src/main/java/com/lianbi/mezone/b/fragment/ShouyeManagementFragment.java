package com.lianbi.mezone.b.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.lianbi.mezone.b.httpresponse.FileDialogCallback;
import com.lianbi.mezone.b.httpresponse.OkHttpsImp;
import com.lianbi.mezone.b.ui.MainActivity;
import com.xizhi.mezone.b.R;

import java.io.File;

/*
 * @创建者     master
 * @创建时间   2016/10/19 18:18
 * @描述
 *
 * @更新者     $Author$ 
 * @更新时间   $Date$
 * @更新描述
 */
public class ShouyeManagementFragment extends Fragment {

	private MainActivity mActivity;
	private OkHttpsImp mOkHttpsImp;

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		View view = inflater.inflate(R.layout.fm_shouyemanagement, null);
		mActivity = (MainActivity) getActivity();
		mOkHttpsImp = OkHttpsImp.SINGLEOKHTTPSIMP.newInstance(mActivity);
		intView(view);
		setLinten();
		return view;

	}

	private void intView(View view) {
		Button btn1 = (Button) view.findViewById(R.id.btn1);
		btn1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mOkHttpsImp.getFile(new FileDialogCallback("OkGo.apk") {
					@Override
					public void onResponseResult(File result) {

					}

					@Override
					public void onResponseFailed(String msg) {

					}

					@Override
					public void fileDownloadProgress(long currentSize, long totalSize, float progress, long networkSpeed) {
						System.out.println("downloadProgress -- " + totalSize + "  " + currentSize + "  " + progress + "  " + networkSpeed);
					}
				});
			}
		});
	}

	private void setLinten() {
	}

	private void getBitmap(){

	}
}
