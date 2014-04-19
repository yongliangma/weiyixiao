package com.yongan.weiyixiao.activity.chanpin;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;

import com.yongan.weiyixiao.R;
import com.yongan.weiyixiao.utils.ImageUtils;

public class GalleryAdapter extends BaseAdapter {
	private GalleryActivity context;
	private List<String> imageUrls;// 图片地址list
	DisplayMetrics dm;

	public GalleryAdapter(List<String> imageUrls, GalleryActivity activity) {
		this.imageUrls = imageUrls;
		this.context = activity;
		dm = new DisplayMetrics();
		context.getWindowManager().getDefaultDisplay().getMetrics(dm);
	}

	public int getCount() {
		return Integer.MAX_VALUE;
	}

	public Object getItem(int pos) {
		return imageUrls.get(pos % imageUrls.size());
	}

	public long getItemId(int pos) {
		return pos;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup paramViewGroup) {
		Bitmap image;
		ImageView view = new ImageView(context);
		image = context.imagesCache.get(imageUrls.get(position
				% imageUrls.size()));
		// 从缓存中读取图片
		if (image == null) {
			image = context.imagesCache.get("background_non_load");
		}
	
		// 设置所有图片的资源地址
		view.setImageBitmap(image);
		view.setScaleType(ImageView.ScaleType.FIT_XY);
		view.setLayoutParams(new Gallery.LayoutParams(dm.widthPixels, dm.heightPixels*3/8));
		/* 设置Gallery背景图 */
		return view;
	}

	class LoadImageTask extends AsyncTask<String, Void, Bitmap> {
		private View resultView;

		LoadImageTask(View view) {
			this.resultView = view;
		}

		protected Bitmap doInBackground(String[] paramArrayOfString) {
			Bitmap localBitmap = null;
			try {
				URLConnection localURLConnection = new URL(
						paramArrayOfString[0]).openConnection();
				localURLConnection.connect();
				InputStream localInputStream = localURLConnection
						.getInputStream();
				byte[] arrayOfByte = ImageUtils.getBytes(localInputStream);
				localBitmap = BitmapFactory.decodeByteArray(arrayOfByte, 0,
						arrayOfByte.length);
				localInputStream.close();
				GalleryAdapter.this.context.bytesCache.put(
						paramArrayOfString[0], arrayOfByte);
				Message localMessage = new Message();
				localMessage.what = 0;
			} catch (Exception localException) {
				localException.printStackTrace();
			}
			return localBitmap;
		}

		protected void onPostExecute(Bitmap paramBitmap) {
			this.resultView.setTag(paramBitmap);
		}
	}

}
