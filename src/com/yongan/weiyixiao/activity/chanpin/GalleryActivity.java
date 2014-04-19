package com.yongan.weiyixiao.activity.chanpin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.AdapterView.OnItemSelectedListener;

import com.google.gson.Gson;
import com.yongan.weiyixiao.R;
import com.yongan.weiyixiao.activity.BaseActivity;
import com.yongan.weiyixiao.entity.ChanPin;
import com.yongan.weiyixiao.utils.FileTools;
import com.yongan.weiyixiao.utils.NetTools;
import com.yongan.weiyixiao.utils.ServiceName;

public class GalleryActivity extends BaseActivity<GalleryData> {
	public HashMap<String, byte[]> bytesCache = new HashMap<String, byte[]>();
	public HashMap<String, Bitmap> imagesCache = new HashMap<String, Bitmap>();// 图片缓存
	private LinearLayout ll_focus_indicator_container = null;
	public GuideGallery images_ga;
	private int num = 0;
	private GalleryAdapter imageAdapter;
	public volatile boolean timeCondition = true;
	public boolean timeFlag = true;
	List<String> urls = new ArrayList<String>(); // 所有图片地址List
	List<String> url = new ArrayList<String>(); // 需要下载图片的url地址
	private int preSelImgIndex = 0;// 存储上一个选择项的Index

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(1);
		setContentView(R.layout.url_connection_image);
		if (savedInstanceState != null) {
			this.data = ((GalleryData) new Gson().fromJson(
					savedInstanceState.getString("data"), GalleryData.class));
		} else {
			Bundle bundle1 = getIntent().getExtras();
			this.data = ((GalleryData) new Gson().fromJson(
					bundle1.getString("data"), GalleryData.class));
		}

		ll_focus_indicator_container = (LinearLayout) findViewById(R.id.gallery_point_linear);
		FileTools.mkdir(this);
		init();
		InitFocusIndicatorContainer();
	}
	
	

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}



	private void InitFocusIndicatorContainer() {
		for (int i = 0; i < urls.size(); i++) {
			ImageView localImageView = new ImageView(this);
			localImageView.setId(i);
			ImageView.ScaleType localScaleType = ImageView.ScaleType.FIT_XY;
			localImageView.setScaleType(localScaleType);
			LinearLayout.LayoutParams localLayoutParams = new LinearLayout.LayoutParams(
					24, 24);
			localImageView.setLayoutParams(localLayoutParams);
			localImageView.setPadding(5, 5, 5, 5);
			localImageView.setImageResource(R.drawable.ic_focus);
			this.ll_focus_indicator_container.addView(localImageView);
		}
	}

	private void init() {
		Bitmap image = BitmapFactory.decodeResource(getResources(),
				R.drawable.loading);
		imagesCache.put("background_non_load", image); // 设置缓存中默认的图片
		images_ga = (GuideGallery) findViewById(R.id.image_wall_gallery);
		ChanPin chanpin = ((GalleryData) this.data).cp;
		for (int i = 0; i < chanpin.pics; i++) {
			urls.add(ServiceName.downloadUrl + "/" + chanpin.id + "/" + i);
		}
		imageAdapter = new GalleryAdapter(urls, this);
		images_ga.setAdapter(imageAdapter);
		images_ga.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int selIndex, long arg3) {
				num = selIndex;
				Log.i("mahua", "ItemSelected==" + selIndex);
				GalleryWhetherStop();
				// 修改上一次选中项的背景
				selIndex = selIndex % urls.size();
				ImageView preSelImg = (ImageView) ll_focus_indicator_container
						.findViewById(preSelImgIndex);
				preSelImg.setImageDrawable(getResources().getDrawable(
						R.drawable.ic_focus));
				// 修改当前选中项的背景
				ImageView curSelImg = (ImageView) ll_focus_indicator_container
						.findViewById(selIndex);
				curSelImg.setImageDrawable(getResources().getDrawable(
						R.drawable.ic_focus_select));
				preSelImgIndex = selIndex;
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {

			}
		});
	}

	/**
	 * 判断Gallery滚动是否停止,如果停止则加载当前页面的图片
	 */
	private void GalleryWhetherStop() {
		Runnable runnable = new Runnable() {
			public void run() {
				try {
					int index = 0;
					index = num;
					Thread.sleep(1000);
					if (index == num) {
						url.add(urls.get(num));
						if (num != 0 && urls.get(num - 1) != null) {
							url.add(urls.get(num - 1));
						}
						if (num != urls.size() - 1 && urls.get(num + 1) != null) {
							url.add(urls.get(num + 1));
						}
						Message m = new Message();
						m.what = 1;
						mHandler.sendMessage(m);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
		new Thread(runnable).start();
	}

	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			try {
				switch (msg.what) {
				case 0: {
					imageAdapter.notifyDataSetChanged();
					break;
				}
				case 1: {
					for (int i = 0; i < url.size(); i++) {
						LoadImageTask task = new LoadImageTask();// 异步加载图片
						task.execute(url.get(i));
						Log.i("mahua", url.get(i));
					}
					url.clear();
				}
				}
				super.handleMessage(msg);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	};

	// 加载图片的异步任务
	class LoadImageTask extends AsyncTask<String, Void, Bitmap> {
		@Override
		protected void onCancelled() {
			super.onCancelled();
		}

		@Override
		protected void onPostExecute(Bitmap result) {
			super.onPostExecute(result);
		}

		@Override
		protected Bitmap doInBackground(String... params) {
			Bitmap bitmap = null;
			try {
				String url = params[0];
				boolean isExists = FileTools.compare(url);
				if (!isExists) {
					NetTools netTools = new NetTools();
					byte[] data = netTools.downloadResource(GalleryActivity.this,
							url);
					bitmap = BitmapFactory
							.decodeByteArray(data, 0, data.length);
					imagesCache.put(url, bitmap); // 把下载好的图片保存到缓存中
					FileTools.saveImage(url, data);
				} else {
					byte[] data = FileTools.readImage(url);
					bitmap = BitmapFactory
							.decodeByteArray(data, 0, data.length);
					imagesCache.put(url, bitmap); // 把下载好的图片保存到缓存中
				}
				Message m = new Message();
				m.what = 0;
				mHandler.sendMessage(m);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return bitmap;
		}
	}
}
