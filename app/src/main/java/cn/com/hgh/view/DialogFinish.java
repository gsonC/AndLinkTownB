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

/**
 * @创建者 Administration
 * @创建时间 ＄{DATE} ＄{TIME}
 * @描述 ＄{TOOD}
 * @更新者 ＄auther＄
 * @更新时间 ＄Date＄
 * @更新描述 ＄{TOOD}＄
 */
public abstract class DialogFinish extends Dialog {

	private Context context;


	public DialogFinish(Context context) {
		super(context, R.style.DialogStyle_4);
		this.context = context;
		initView();
	}

	TextView tv_dialog_finishing_title, tv_dialog_finishing_titlee;
	public void setTv_dialog_finishing_title(String  tv_dialog_finishing_title) {
		this.tv_dialog_finishing_title.setText(tv_dialog_finishing_title);
	}

	public void setTv_dialog_finishing_titlee(String  tv_dialog_finishing_titlee) {
		this.tv_dialog_finishing_titlee.setText(tv_dialog_finishing_titlee);
	}



	private void initView() {
		DisplayMetrics dm = new DisplayMetrics();
		getWindow().getWindowManager().getDefaultDisplay().getMetrics(dm);
		int screenWidth = dm.widthPixels;
		View view = View.inflate(context, R.layout.dialog_layout, null);
		setContentView(view);

		tv_dialog_finishing_title = (TextView) view.findViewById(R.id.tv_dialog_finishing_title);
		tv_dialog_finishing_titlee = (TextView) view.findViewById(R.id.tv_dialog_finishing_titlee);





		Window win = getWindow();
		WindowManager.LayoutParams lp = win.getAttributes();
		lp.gravity = Gravity.CENTER;
		lp.width = (int) (screenWidth * 0.8);
		lp.height = ViewGroup.LayoutParams.WRAP_CONTENT;

	}


	public abstract void onFinishOkClick();

	public abstract void onFinishClick();



}