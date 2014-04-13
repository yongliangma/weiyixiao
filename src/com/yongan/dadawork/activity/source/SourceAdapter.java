package com.yongan.dadawork.activity.source;

import java.util.List;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yongan.dadawork.R;
import com.yongan.dadawork.activity.BaseActivity;
import com.yongan.dadawork.entity.Bazaar;

public class SourceAdapter extends BaseAdapter {
	private BaseActivity context;
	private List<Bazaar> data;

	public SourceAdapter(BaseActivity paramBaseActivity, List<Bazaar> paramList) {
		this.context = paramBaseActivity;
		this.data = paramList;
	}

	public int getCount() {
		return data.size();
	}

	public Object getItem(int paramInt) {
		return data.get(paramInt);
	}

	public long getItemId(int paramInt) {
		return 0L;
	}

	public View getView(int position, View convertView, ViewGroup paramViewGroup) {
		ViewHolder holder;
		if (convertView == null) {
			convertView = LayoutInflater.from(this.context).inflate(
					R.layout.listitem_source, paramViewGroup, false);
			holder = new ViewHolder();
			holder.title = ((TextView) convertView.findViewById(R.id.txtBname));
			holder.address = ((TextView) convertView
					.findViewById(R.id.txtAddress));
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		Bazaar localBazaar = (Bazaar) data.get(position);
		holder.title.setText(localBazaar.name);
		holder.address.setText(localBazaar.address);
		return convertView;
	}

	static class ViewHolder {
		TextView address;
		TextView title;
	}
}