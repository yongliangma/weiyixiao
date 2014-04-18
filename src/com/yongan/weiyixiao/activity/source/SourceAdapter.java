package com.yongan.weiyixiao.activity.source;

import java.util.List;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yongan.weiyixiao.R;
import com.yongan.weiyixiao.activity.BaseActivity;
import com.yongan.weiyixiao.entity.Bazaar;

public class SourceAdapter extends BaseAdapter {
	private BaseActivity context;
	private List<Bazaar> data;

	public SourceAdapter(BaseActivity activity, List<Bazaar> dataList) {
		this.context = activity;
		this.data = dataList;
	}

	public int getCount() {
		return data.size();
	}

	public Object getItem(int pos) {
		return data.get(pos);
	}

	public long getItemId(int pos) {
		return 0L;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.listitem_source, parent, false);
			holder = new ViewHolder();
			holder.title = ((TextView) convertView.findViewById(R.id.txtBname));
			holder.address = ((TextView) convertView
					.findViewById(R.id.txtAddress));
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		Bazaar bazaar = (Bazaar) data.get(position);
		holder.title.setText(bazaar.name);
		holder.address.setText(bazaar.address);
		return convertView;
	}

	static class ViewHolder {
		TextView address;
		TextView title;
	}
}