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
public abstract class DiaqlogNow extends Dialog {
	private Context context;


	public DiaqlogNow(Context context) {
		super(context, R.style.DialogStyle_4);
		this.context = context;
		initViewT();
	}

	public abstract void onCheckClick();

	public abstract void onOkClick();
	TextView tv_gz_rate,tv_gz_count,tv_Fdiscount_time,tv_Ediscount_time;

	public TextView getTv_gz_rate() {
		return tv_gz_rate;
	}

	public void setTv_gz_rate(TextView tv_gz_rate) {
		this.tv_gz_rate = tv_gz_rate;
	}

	public TextView getTv_gz_count() {
		return tv_gz_count;
	}

	public void setTv_gz_count(TextView tv_gz_count) {
		this.tv_gz_count = tv_gz_count;
	}

	public TextView getTv_Fdiscount_time() {
		return tv_Fdiscount_time;
	}

	public void setTv_Fdiscount_time(TextView tv_Fdiscount_time) {
		this.tv_Fdiscount_time = tv_Fdiscount_time;
	}

	public TextView getTv_Ediscount_time() {
		return tv_Ediscount_time;
	}

	public void setTv_Ediscount_time(TextView tv_Ediscount_time) {
		this.tv_Ediscount_time = tv_Ediscount_time;
	}

	/**
	 * 规则说明的对话框
	 */
	private void initViewT(){
		DisplayMetrics dm = new DisplayMetrics();
		getWindow().getWindowManager().getDefaultDisplay().getMetrics(dm);
		int screenWidth = dm.widthPixels;
		View view = View.inflate(context, R.layout.dialog_gz, null);
		setContentView(view);
		tv_gz_rate= (TextView) view.findViewById(R.id.tv_gz_rate);
		tv_gz_count= (TextView) view.findViewById(R.id.tv_gz_count);
		tv_Fdiscount_time= (TextView) view.findViewById(R.id.tv_Fdiscount_time);
		tv_Ediscount_time= (TextView) view.findViewById(R.id.tv_Ediscount_time);
		Window win = getWindow();
		WindowManager.LayoutParams lp = win.getAttributes();
		lp.gravity = Gravity.CENTER;
		lp.width = (int) (screenWidth * 0.8);
		lp.height = ViewGroup.LayoutParams.WRAP_CONTENT;
	}
}
