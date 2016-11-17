package cn.com.hgh.view;

import android.app.Dialog;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.xizhi.mezone.b.R;

public  abstract class DialogShoukuan extends Dialog {
	private Context context;
	private TextView tv_dialog_shoukuan_title, tv_dialog_shoukuan_titlee,tv_dialog_shoukuan_cancel;

	public DialogShoukuan(Context context) {
		super(context, R.style.DialogStyle_4);
		this.context = context;
		initView();
	}
	View v;

	public void setTv_dialog_shoukuan_title(String  tv_dialog_shoukuan_title) {
		this.tv_dialog_shoukuan_title.setText(tv_dialog_shoukuan_title);
	}

	public void setTv_dialog_shoukuan_titlee(String  tv_dialog_shoukuan_titlee) {
		this.tv_dialog_shoukuan_titlee.setText(tv_dialog_shoukuan_titlee);
	}

	public void setTv_dialog_shoukuan_cancel(String  tv_dialog_shoukuan_cancel) {
		this.tv_dialog_shoukuan_cancel.setText(tv_dialog_shoukuan_cancel);
	}

	private void initView() {
		DisplayMetrics dm = new DisplayMetrics();
		getWindow().getWindowManager().getDefaultDisplay().getMetrics(dm);
		int screenWidth = dm.widthPixels;
		View view = View.inflate(context, R.layout.dialog_shoukuan, null);
		setContentView(view);
//		v = view.findViewById(R.id.line_dialog_shoukuan_cancel);
		tv_dialog_shoukuan_title = (TextView) view.findViewById(R.id.tv_dialog_shoukuan_title);
		tv_dialog_shoukuan_titlee = (TextView) view.findViewById(R.id.tv_dialog_shoukuan_titlee);
		tv_dialog_shoukuan_cancel = (TextView) view.findViewById(R.id.tv_dialog_shoukuan_cancel);

		tv_dialog_shoukuan_title.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				onCheckClick();
			}
		});


		tv_dialog_shoukuan_titlee.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				onOkClick();

			}
		});

		tv_dialog_shoukuan_cancel.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				dismiss();
			}
		});
		Window win = getWindow();
		WindowManager.LayoutParams lp = win.getAttributes();
		lp.gravity = Gravity.CENTER;
		lp.width = (int) (screenWidth * 0.8);
		lp.height = ViewGroup.LayoutParams.WRAP_CONTENT;

	}


	public abstract void onCheckClick();

	public abstract void onOkClick();

}