package cn.com.hgh.view;

import android.app.Dialog;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lianbi.mezone.b.bean.FinancialOfficeAmountBean;
import com.xizhi.mezone.b.R;

import java.math.BigDecimal;
import java.text.DecimalFormat;

import cn.com.hgh.utils.AbStrUtil;
import cn.com.hgh.utils.MathExtend;

/**
 * @创建者 Administration
 * @创建时间 ＄{DATE} ＄{TIME}
 * @描述 ＄{TOOD}
 * @更新者 ＄auther＄
 * @更新时间 ＄Date＄
 * @更新描述 ＄{TOOD}＄
 */
public class DiaqlogNow extends Dialog {
	private Context context;
	private FinancialOfficeAmountBean bean;
	TextView tv_gz_rate,tv_gz_count,tv_Fdiscount_time,tv_Ediscount_time,tv_Ediscount;

	public DiaqlogNow(Context context) {
		super(context, R.style.DialogStyle_4);
		this.context = context;
		initViewT();
	}

	public DiaqlogNow(Context context, FinancialOfficeAmountBean bean){
		super(context, R.style.DialogStyle_4);
		this.context = context;
		this.bean = bean;
		initViewT();
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
		tv_Ediscount= (TextView) view.findViewById(R.id.tv_Ediscount);
		LinearLayout llt_dialoggz = (LinearLayout) view.findViewById(R.id.llt_dialoggz);
		TextView tv_dialog_3 = (TextView) view.findViewById(R.id.tv_dialog_3);
		TextView tv_dialog_4 = (TextView) view.findViewById(R.id.tv_dialog_4);

		String startDate = bean.getStartDate();
		String endDate = bean.getEndDate();


		int cardinal = 100;
		double multiplicativecardinal = cardinal;
		DecimalFormat df = new DecimalFormat("0.00");

		if(null!=bean.getRate()&&0!=bean.getRate().compareTo(BigDecimal.ZERO)){
		//	tv_gz_rate.setText(Double.toString(MathExtend.divide(bean.getRate().doubleValue(),100,2))+"%");
     //format可以将各类数据格式化为字符串并输出
			tv_gz_rate.setText(df.format(MathExtend.multiply(bean.getRate()
					.doubleValue(), multiplicativecardinal)) + "%");

		}else{
			tv_gz_rate.setText("0.00%");
		}

		if(null!=bean.getCheapRate()&&0!=bean.getCheapRate().compareTo(BigDecimal.ZERO)){
			//tv_gz_count.setText(Double.toString(MathExtend.divide(bean.getCheapRate().doubleValue(),100,2))+"%");

			tv_gz_count.setText(df.format(MathExtend.multiply(bean.getCheapRate()
					.doubleValue(), multiplicativecardinal)) + "%");


		}else{
			tv_gz_count.setText("0.00%");
		}

		if(!AbStrUtil.isEmpty(startDate)||!AbStrUtil.isEmpty(endDate)){
			tv_Fdiscount_time.setText(getTime(startDate));
			tv_Ediscount_time.setText(getTime(endDate));
		}else{
			llt_dialoggz.setVisibility(View.GONE);
			tv_dialog_3.setText("2.");
			tv_dialog_4.setText("3.");
		}


		Window win = getWindow();
		WindowManager.LayoutParams lp = win.getAttributes();
		lp.gravity = Gravity.CENTER;
		//设置透明度
		lp.alpha=0.6f;
		/*//点击对话框时消除后面模糊场景
		win.setFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND,
				WindowManager.LayoutParams.FLAG_BLUR_BEHIND);*/
		win.setAttributes(lp);
		lp.width = (int) (screenWidth * 0.8);
		lp.height = ViewGroup.LayoutParams.WRAP_CONTENT;

	}

	private String getTime(String time){
		String year = time.substring(0,4);
		String mouth = time.substring(5,7);
		String day = time.substring(8,time.length());
		return year+"年"+mouth+"月"+day+"日";
	}

}
