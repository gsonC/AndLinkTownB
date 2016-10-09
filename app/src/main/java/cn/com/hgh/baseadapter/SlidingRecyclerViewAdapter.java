package cn.com.hgh.baseadapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lianbi.mezone.b.bean.OrderContent;
import com.xizhi.mezone.b.R;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import cn.com.hgh.utils.ScreenUtils;
import cn.com.hgh.view.SlidingButtonView;

/**
 * Created by fml on 2015/12/3 0003.
 */
public class SlidingRecyclerViewAdapter extends RecyclerView.Adapter<SlidingRecyclerViewAdapter.ViewHolder> implements SlidingButtonView.IonSlidingButtonListener{
    private LayoutInflater mLayoutInflater;
    protected List<OrderContent> mListData = new ArrayList<OrderContent>();;
    private Context mContext;
    private SlidingButtonView mMenu = null;
    private IonSlidingViewClickListener mIonSlidingViewClickListener;
    //定义接口
    public interface IonSlidingViewClickListener {
        void onItemClick(View view, int position);
        void onLongItemClick(View view, int position);
        void onDeleteBtnCilck(View view, int position);
    }
    public void setData(List<OrderContent> datas){
        this.mListData = datas;
        notifyDataSetChanged();
    }
    //设置监听
    public void setDeleteLister(IonSlidingViewClickListener iDeleteBtnClickListener){
        if(iDeleteBtnClickListener != null){
            mIonSlidingViewClickListener = iDeleteBtnClickListener;
        }
    }
    public SlidingRecyclerViewAdapter(Context context){
        this.mContext = context;
        mLayoutInflater = LayoutInflater.from(mContext);
    }
    //创建ViewHolder
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = mLayoutInflater.inflate(R.layout.item_sliding_delelte,parent,false);
        ViewHolder viewHolder = new ViewHolder(v);
        ((SlidingButtonView) v).setSlidingButtonListener(SlidingRecyclerViewAdapter.this);
        return viewHolder;
    }
    //绑定ViewHolder
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.tv_item_orderinfo_num.setText(mListData.get(position).getOrderNo());
        if (mListData.get(position).getOrderStatus().equals("03")) {
             holder.tv_item_orderinfo_state.setText("支付成功");
        } else if (mListData.get(position).getOrderStatus().equals("04")) {
             holder.tv_item_orderinfo_state.setText("支付失败");
        }
        String amt = BigDecimal.valueOf(Long.valueOf(mListData.get(position).getTxnAmt()))
                        .divide(new BigDecimal(100)).toString();
        holder.tv_item_orderinfo_price.setText(String.valueOf(amt));
        String time = mListData.get(position).getTxnTime();
        String year = time.substring(0, 4);
        String months = time.substring(4, 6);
        String daytime = time.substring(6, 8);
        String hour = time.substring(8, 10);
        String minute = time.substring(10, 12);
        String second = time.substring(12, 14);
        holder.tv_item_orderinfo_paytime.setText(year + "-" + months + "-"
                 + daytime + " " + hour + ":" + minute + ":" + second);
        //设置内容布局的宽为屏幕宽度
        holder.item_sliding_lay.getLayoutParams().width = ScreenUtils.getScreenWidth(mContext);
        /** 触发点击事件 */
        holder.item_sliding_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //判断是否有删除菜单打开
                if (menuIsOpen()) {
                    Log.i("TAG","菜单处于打开状态");
                    //关闭菜单
                    closeMenu();
                } else {
                    Log.i("TAG","菜单处于关闭状态");
                    int n = holder.getLayoutPosition();
                    mIonSlidingViewClickListener.onItemClick(v, n);
                }
            }
        });
        /** 触发长点击事件 */
        holder.item_sliding_lay.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
            //判断是否有删除菜单打开
            if (menuIsOpen()) {
                Log.i("TAG", "菜单处于打开状态");
                //关闭菜单
                closeMenu();
            } else {
                Log.i("TAG", "菜单处于关闭状态");
                int n = holder.getLayoutPosition();
                mIonSlidingViewClickListener.onLongItemClick(v, n);
            }
            return true;
            }
        });
        /** 触发删除事件 */
        holder.mDeleteText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int n = holder.getLayoutPosition();
                mIonSlidingViewClickListener.onDeleteBtnCilck(v, n);
            }
        });
    }

    @Override
    public int getItemCount() {

        return mListData.size();
    }
    class ViewHolder extends RecyclerView.ViewHolder{
        TextView mDeleteText,tv_item_orderinfo_num,tv_item_orderinfo_state
                ,tv_item_orderinfo_paytime,tv_item_orderinfo_price;
        LinearLayout item_sliding_lay;
        public ViewHolder(View itemView) {
            super(itemView);
            item_sliding_lay = (LinearLayout) itemView.findViewById(R.id.item_sliding_lay);
            mDeleteText = (TextView) itemView.findViewById(R.id.item_sliding_delete);
            tv_item_orderinfo_num = (TextView) itemView.findViewById(R.id.tv_item_orderinfo_num);
            tv_item_orderinfo_state = (TextView) itemView.findViewById(R.id.tv_item_orderinfo_state);
            tv_item_orderinfo_paytime = (TextView) itemView.findViewById(R.id.tv_item_orderinfo_paytime);
            tv_item_orderinfo_price = (TextView) itemView.findViewById(R.id.tv_item_orderinfo_price);
        }
    }



    @Override
    public void onMenuIsOpen(View view) {
        mMenu = (SlidingButtonView) view;
    }

    /**
     * 关闭菜单
     */
    public void closeMenu() {
        Log.i("TAG","关闭菜单");
        mMenu.closeMenu();
        mMenu = null;

    }
    /**
     * 判断是否有菜单打开
     */
    public Boolean menuIsOpen() {
        if(mMenu != null){
            Log.i("asd", "删除菜单处于打开状态");
            return true;
        }
        Log.i("asd", "删除菜单处于关闭状态");
        return false;
    }
    @Override
    public void onDownOrMove(SlidingButtonView slidingButtonView) {
        if(menuIsOpen()){
            if(mMenu != slidingButtonView){
                Log.i("TAG", "关闭删除菜单111");
                closeMenu();
            }
        }
    }
}
