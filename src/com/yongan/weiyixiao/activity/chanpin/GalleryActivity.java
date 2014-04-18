package com.yongan.weiyixiao.activity.chanpin;

import java.util.HashMap;
import java.util.List;

import android.graphics.Bitmap;
import android.os.Bundle;

import com.yongan.weiyixiao.R;
import com.yongan.weiyixiao.activity.BaseActivity;

public class GalleryActivity extends BaseActivity<GalleryData> {
	public HashMap<String, byte[]> bytesCache = new HashMap<String, byte[]>();
	public HashMap<String, Bitmap> imagesCache = new HashMap<String, Bitmap>();
	public GuideGallery images_ga;
	private int position = 0;
	private GalleryAdapter self;
	public volatile boolean timeCondition = true;
	public boolean timeFlag = true;
	public List<String> urls;

	protected void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);
		requestWindowFeature(1);
		setContentView(R.layout.url_connection_image);
	}
}
