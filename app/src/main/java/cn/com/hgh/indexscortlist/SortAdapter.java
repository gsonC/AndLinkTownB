package cn.com.hgh.indexscortlist;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SectionIndexer;
import android.widget.TextView;
import cn.com.hgh.view.CircularImageView;

import com.bumptech.glide.Glide;
import com.xizhi.mezone.b.R;
import com.lianbi.mezone.b.bean.SalesMan;

public class SortAdapter extends BaseAdapter implements SectionIndexer {
	private List<SalesMan> list = null;
	private Context mContext;

	public SortAdapter(Context mContext, List<SalesMan> list) {
		this.mContext = mContext;
		this.list = list;
	}

	/**
	 * 当ListView数据发生变化时,调用此方法来更新ListView
	 * 
	 * @param list
	 */
	public void updateListView(List<SalesMan> list) {
		this.list = list;
		notifyDataSetChanged();
	}

	public int getCount() {
		return this.list.size();
	}

	public SalesMan getItem(int position) {
		return list.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(final int position, View view, ViewGroup arg2) {
		ViewHolder viewHolder = null;
		final SalesMan mContent = list.get(position);
		if (view == null) {
			viewHolder = new ViewHolder();
			view = LayoutInflater.from(mContext).inflate(
					R.layout.sortadapter_item, null);
			viewHolder.tvTitle = (TextView) view
					.findViewById(R.id.sortadapter_item_name);
			viewHolder.phone = (TextView) view
					.findViewById(R.id.sortadapter_item_phone);
			viewHolder.content = (TextView) view
					.findViewById(R.id.sortadapter_item_content);
			viewHolder.tvLetter = (TextView) view
					.findViewById(R.id.sortadapter_item_title);
			viewHolder.sortadapter_item_iv = (CircularImageView) view
					.findViewById(R.id.sortadapter_item_iv);
			view.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) view.getTag();
		}

		// 根据position获取分类的首字母的Char ascii值
		int section = getSectionForPosition(position);

		// 如果当前位置等于该分类首字母的Char的位置 ，则认为是第一次出现
		if (position == getPositionForSection(section)) {
			viewHolder.tvLetter.setVisibility(View.VISIBLE);
			viewHolder.tvLetter.setText(mContent.getSortLetters());
		} else {
			viewHolder.tvLetter.setVisibility(View.GONE);
		}

		viewHolder.tvTitle.setText(this.list.get(position).getSalesclerk_name());
		viewHolder.phone.setText(this.list.get(position).getSalesclerk_phone());
		viewHolder.content.setText(this.list.get(position).getSalesclerk_job());
		String url = this.list.get(position).getSalesclerk_image();
		Glide.with(mContext).load(url).error(R.mipmap.defaultpeson)
				.into(viewHolder.sortadapter_item_iv);
		return view;

	}

	final static class ViewHolder {
		TextView tvLetter;
		TextView phone;
		TextView content;
		TextView tvTitle;
		CircularImageView sortadapter_item_iv;
	}

	/**
	 * 根据ListView的当前位置获取分类的首字母的Char ascii值
	 */
	public int getSectionForPosition(int position) {
		return list.get(position).getSortLetters().charAt(0);
	}

	/**
	 * 根据分类的首字母的Char ascii值获取其第一次出现该首字母的位置
	 */
	public int getPositionForSection(int section) {
		for (int i = 0; i < getCount(); i++) {
			String sortStr = list.get(i).getSortLetters();
			char firstChar = sortStr.toUpperCase().charAt(0);
			if (firstChar == section) {
				return i;
			}
		}

		return -1;
	}

	@Override
	public Object[] getSections() {
		return null;
	}
}