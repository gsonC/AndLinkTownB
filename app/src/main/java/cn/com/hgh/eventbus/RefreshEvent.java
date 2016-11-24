package cn.com.hgh.eventbus;

/*
 * @创建者     master
 * @创建时间   2016/11/12 12:11
 * @描述       Eventbus 用于信息传递
 *
 * @更新者     $Author$ 
 * @更新时间   $Date$
 * @更新描述	   当推送消息为2,3,4,6为0 推送消息为6000是为1 当跳转到客户买单 响应呼叫 消费流水时也为0
 */
public class RefreshEvent {

	private int mRefreshNumber;

	public RefreshEvent(int refreshNumber){
		mRefreshNumber = refreshNumber;
	}

	public int getRefreshNumber(){
		return mRefreshNumber;
	}

}
