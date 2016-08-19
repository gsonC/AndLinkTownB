package cn.com.hgh.baseadapter.recyclerViewadapter.pullrefreshrecyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xizhi.mezone.b.R;

import java.util.List;

/**
 * Created by oyty on 4/27/16.
 */
public abstract class PullRefreshRecyclerAdapter<T> extends RecyclerView.Adapter<PullRefreshViewHolder> {

    protected static final int VIEW_TYPE_ITEM = 0x01;
    protected static final int VIEW_TYPE_LOADING = 0x02;

    protected Context mContext;
    protected List<T> mDataList;

    private boolean isLoadMoreFooterShow;

    public PullRefreshRecyclerAdapter(Context context, List<T> list) {
        this.mContext = context;
        this.mDataList = list;
    }

    @Override
    public PullRefreshViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == VIEW_TYPE_ITEM) {
            return onCreateNormalViewHolder(parent);
        } else if(viewType == VIEW_TYPE_LOADING) {
            return onCreateLoadMoreFooterViewHolder(parent);
        }
        return null;
    }

    protected abstract PullRefreshViewHolder onCreateNormalViewHolder(ViewGroup parent);

    private PullRefreshViewHolder onCreateLoadMoreFooterViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_pull_to_refresh_footer, parent, false);
        return new LoadMoreFooterViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return mDataList == null ? 0 : mDataList.size() + (isLoadMoreFooterShow ? 1 : 0);
    }

    @Override
    public int getItemViewType(int position) {
        if(isLoadMoreFooterShow && position == getItemCount() - 1) {
            return VIEW_TYPE_LOADING;
        }
        return VIEW_TYPE_ITEM;
    }

    public void enableLoadMore(boolean isShow) {
        this.isLoadMoreFooterShow = isShow;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(PullRefreshViewHolder holder, int position) {
        if(holder instanceof LoadMoreFooterViewHolder) {
            if(isLoadMoreFooterShow && position == getItemCount() - 1) {
                if (holder.itemView.getLayoutParams() instanceof StaggeredGridLayoutManager.LayoutParams) {
                    StaggeredGridLayoutManager.LayoutParams params = (StaggeredGridLayoutManager.LayoutParams) holder.itemView.getLayoutParams();
                    params.setFullSpan(true);
                }
            }
        } else {
            holder.onBindViewHolder(position);
        }
    }

    public void onLoadMoreStateChanged(boolean isShown) {
        this.isLoadMoreFooterShow = isShown;
        if (isShown) {
            notifyItemInserted(getItemCount());
        } else {
            notifyItemRemoved(getItemCount());
        }
    }


    private static class LoadMoreFooterViewHolder extends PullRefreshViewHolder {

        public LoadMoreFooterViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void onBindViewHolder(int position) {
        }

        @Override
        protected void onItemClick(View view, int adapterPosition) {
        }
    }
}
