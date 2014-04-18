package com.yongan.weiyixiao.activity.chanpin;

import java.util.List;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class GalleryAdapter extends BaseAdapter {
	private GalleryActivity context;
	private List<String> imageUrls;
	ImageView imageView;
	private List<String> list;

	public GalleryAdapter(List<String> paramList,
			GalleryActivity paramGalleryActivity) {
		this.list = paramList;
		this.context = paramGalleryActivity;
	}

	public int getCount() {
		return 2147483647;
	}

	public Object getItem(int paramInt) {
		return this.list.get(paramInt % this.list.size());
	}

	public long getItemId(int paramInt) {
		return paramInt;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub
		return null;
	}

}
