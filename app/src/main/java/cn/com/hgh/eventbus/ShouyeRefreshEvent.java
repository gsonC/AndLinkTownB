package cn.com.hgh.eventbus;

/*
 * @创建者     master
 * @创建时间   2016/11/12 12:11
 * @描述       首页刷新Eventbus 用于刷新首页实时消费
 *
 * @更新者     $Author$ 
 * @更新时间   $Date$
 * @更新描述
 */
public class ShouyeRefreshEvent {

	private boolean mIsRefresh;

	public ShouyeRefreshEvent(boolean isRefresh){
		mIsRefresh = isRefresh;
	}

	public boolean getRefresh(){
		return mIsRefresh;
	}
}
