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
public abstract class DialogLine extends Dialog {


	private TextView tv_dialog_line_title;
	private Context context;


	public DialogLine(Context context) {
		super(context, R.style.DialogStyle_4);
		this.context = context;
		initView();

	}


	TextView tv_dialog_line_cancel;
	TextView tv_dialog_line_ok;

	public void setTv_dialog_line_cancel(String tv_dialog_line_cancel) {
		this.tv_dialog_line_cancel.setText(tv_dialog_line_cancel);
	}

	public void setTv_dialog_line_cancelV(int v) {
		this.tv_dialog_line_cancel.setVisibility(v);
		this.v.setVisibility(v);
	}

	public void setTv_dialog_line_ok(String tv_dialog_line_ok) {
		this.tv_dialog_line_ok.setText(tv_dialog_line_ok);
	}

	View v;

	private void initView() {
		DisplayMetrics dm = new DisplayMetrics();
		getWindow().getWindowManager().getDefaultDisplay().getMetrics(dm);
		int screenWidth = dm.widthPixels;
		View view = View.inflate(context, R.layout.dialog_line, null);
		setContentView(view);
		v = view.findViewById(R.id.line_dialog_common_cancel);
		tv_dialog_line_title = (TextView) view.findViewById(R.id.tv_dialog_line_title);
		tv_dialog_line_cancel = (TextView) view.findViewById(R.id.tv_dialog_line_cancel);
		tv_dialog_line_ok = (TextView) view.findViewById(R.id.tv_dialog_line_ok);
		tv_dialog_line_ok.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				onCheckClick();
			}
		});

		tv_dialog_line_cancel.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				onOkClick();
			}
		});
		Window win = getWindow();
		WindowManager.LayoutParams lp = win.getAttributes();
		lp.gravity = Gravity.CENTER;
		lp.width = (int) (screenWidth * 0.8);
		lp.height = ViewGroup.LayoutParams.WRAP_CONTENT;

	}

	public void setTextTitle(String txtString) {
		tv_dialog_line_title.setText(txtString);
	}

	public abstract void onCheckClick();

	public abstract void onOkClick();


}