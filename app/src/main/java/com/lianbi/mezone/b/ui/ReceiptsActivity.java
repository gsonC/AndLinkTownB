package com.lianbi.mezone.b.ui;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.xizhi.mezone.b.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 收款额度
 */
public class ReceiptsActivity extends BaseActivity {
    @Bind(R.id.background)
    RelativeLayout background;
    @Bind(R.id.today_left_)
    TextView today_left;
    @Bind(R.id.today_total)
    TextView today_total;
    @Bind(R.id.today_has_used)
    TextView today_has_used;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipts, NOTYPE);
        ButterKnife.bind(ReceiptsActivity.this);
        Glide.with(ReceiptsActivity.this).load(R.mipmap.payment).into(new SimpleTarget<GlideDrawable>() {
            @Override
            public void onResourceReady(GlideDrawable glideDrawable, GlideAnimation<? super GlideDrawable> glideAnimation) {
                initBackground(glideDrawable);
            }
        });
    }

    private void initBackground(Drawable drawable) {
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) background.getLayoutParams();
        lp.weight = screenWidth;
        lp.height = (int) ((double) (screenWidth * drawable.getMinimumHeight()) / (double) drawable.getMinimumWidth());
        background.setLayoutParams(lp);
        background.setBackgroundDrawable(drawable);
    }
}
