package com.yongan.weiyixiao.activity.source;

import java.util.List;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yongan.weiyixiao.R;
import com.yongan.weiyixiao.activity.BaseActivity;

public class SourceListAdapter extends BaseAdapter {
	private BaseActivity context;
	private List<SourceUserVo> data;

	public SourceListAdapter(BaseActivity activity, List<SourceUserVo> paramList) {
		this.context = activity;
		this.data = paramList;
	}

	public int getCount() {
		return data.size();
	}

	public Object getItem(int position) {
		return data.get(position);
	}

	public long getItemId(int position) {
		return 0L;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.listitem_source_list, parent, false);
			holder = new ViewHolder();
			holder.title = ((TextView) convertView.findViewById(R.id.txtTitle));
			holder.miaoshu = ((TextView) convertView
					.findViewById(R.id.txtMiaoShu));
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		SourceUserVo localSourceUserVo = (SourceUserVo) this.data.get(position);
		if (localSourceUserVo.name.length() <= 1) {
			holder.title.setText(localSourceUserVo.dangkou);
		} else {
			holder.title.setText(localSourceUserVo.name + "  "
					+ localSourceUserVo.dangkou);
		}
		holder.miaoshu.setText(localSourceUserVo.miaoshu);
		return convertView;
	}

	static class ViewHolder {
		TextView miaoshu;
		TextView title;
	}
}