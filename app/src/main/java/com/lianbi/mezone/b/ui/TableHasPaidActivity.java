package com.lianbi.mezone.b.ui;

import android.os.Bundle;

import com.xizhi.mezone.b.R;

import butterknife.ButterKnife;

/*
* 桌位详情-已支付
* */
public class TableHasPaidActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_has_paid, NOTYPE);
        ButterKnife.bind(this);
        setPageTitle("已支付");
//            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.android_robot);
//            RoundedBitmapDrawable mRoundedBitmapDrawable = RoundedBitmapDrawableFactory.create(getResources(), bitmap);
//            mRoundedBitmapDrawable.setCircular(isChecked);
//            ImageView image = (ImageView) findViewById(R.id.image);
//            image.setImageDrawable(mRoundedBitmapDrawable);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}
