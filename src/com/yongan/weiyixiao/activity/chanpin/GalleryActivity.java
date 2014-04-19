package com.yongan.weiyixiao.activity.chanpin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.StateListDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.ClipboardManager;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.yongan.weiyixiao.R;
import com.yongan.weiyixiao.activity.BaseActivity;
import com.yongan.weiyixiao.entity.PinPai;
import com.yongan.weiyixiao.entity.clazz.Service;
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

	private Context context;
	private TextView mMiaoshuTextView;
	private TextView mDaiLipriceTextView;
	private LinearLayout attrebuteLayoutLinearLayout;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(1);
		setContentView(R.layout.url_connection_image);
		context = this;
		if (savedInstanceState != null) {
			this.data = ((GalleryData) new Gson().fromJson(
					savedInstanceState.getString("data"), GalleryData.class));
		} else {
			Bundle bundle1 = getIntent().getExtras();
			this.data = ((GalleryData) new Gson().fromJson(
					bundle1.getString("data"), GalleryData.class));
		}

		ll_focus_indicator_container = (LinearLayout) findViewById(R.id.gallery_point_linear);
		ll_focus_indicator_container.setBackgroundColor(Color.argb(200, 135,
				135, 152));
		FileTools.mkdir(this);
		init();
		InitFocusIndicatorContainer();
		initData();
	}

	private void initData() {
		mMiaoshuTextView = (TextView) findViewById(R.id.txtMiaoShu);
		mMiaoshuTextView.setText(Html
				.fromHtml(((GalleryData) this.data).cp.miaoshu));
		mDaiLipriceTextView = (TextView) findViewById(R.id.txtPrice);
		mDaiLipriceTextView.setText(Html.fromHtml("代理价格：￥"
				+ ((GalleryData) this.data).cp.price + ".00"));
		attrebuteLayoutLinearLayout = (LinearLayout) findViewById(R.id.attrebuteLayout);
		attrebuteLayoutLinearLayout.removeAllViews();

		StateListDrawable localStateListDrawable = new StateListDrawable();
		int[] arrayOfInt1 = new int[1];
		arrayOfInt1[0] = 16842919;
		localStateListDrawable.addState(arrayOfInt1, getResources()
				.getDrawable(R.drawable.button_noborder_over));
		int[] arrayOfInt2 = new int[1];
		arrayOfInt2[0] = 16842910;
		localStateListDrawable.addState(arrayOfInt2, getResources()
				.getDrawable(R.drawable.button_noborder_on));
		// ImageView iv = new ImageView(context);
		// iv.setImageResource(R.drawable.line_h);
		// attrebuteLayoutLinearLayout.addView(iv);
		Button localButton2 = new Button(this);
		localButton2.setText(Html.fromHtml("<font color=#1a2afe>点击复制供货商"
				+ getApp().getLinkStr(((GalleryData) this.data).cp.dangkouhao)
				+ "</font>"));
		localButton2.setBackgroundDrawable(localStateListDrawable);
		localButton2.setLayoutParams(new ViewGroup.LayoutParams(-1, -2));
		localButton2.setGravity(3);
		localButton2.setGravity(16);
		localButton2.setPadding(1, 1, 1, 1);
		attrebuteLayoutLinearLayout.addView(localButton2);
		localButton2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				copyMiaoshu(getApp().getLinkStr(
						((GalleryData) data).cp.dangkouhao));
				Toast.makeText(GalleryActivity.this, "复制成功！",
						Toast.LENGTH_SHORT).show();
			}
		});

		TextView localTextView1 = new TextView(this);
		localTextView1.setText(Html
				.fromHtml("适应人群："
						+ getApp().findXingBie(
								((GalleryData) this.data).cp.xingbie).name));
		localTextView1.setBackgroundResource(R.drawable.attretube_text);
		localTextView1.setLayoutParams(new ViewGroup.LayoutParams(-1, -2));
		localTextView1.setGravity(3);
		localTextView1.setGravity(16);
		localTextView1.setPadding(1, 1, 1, 1);
		attrebuteLayoutLinearLayout.addView(localTextView1);

		TextView localTextView2 = new TextView(this);
		PinPai localPinPai = getApp().findPinPaiById(
				((GalleryData) this.data).cp.pinpaiId);
		localTextView2.setText(Html.fromHtml("产品品牌：" + localPinPai.cname + "("
				+ localPinPai.ename + ")"));
		localTextView2.setBackgroundResource(R.drawable.attretube_text);
		localTextView2.setLayoutParams(new ViewGroup.LayoutParams(-1, -2));
		localTextView2.setGravity(3);
		localTextView2.setGravity(16);
		localTextView2.setPadding(1, 1, 1, 1);
		attrebuteLayoutLinearLayout.addView(localTextView2);

		TextView localTextView3 = new TextView(this);
		Service service = getApp().findServiceById(
				((GalleryData) this.data).cp.shouhou);
		localTextView3.setText(Html.fromHtml("售后服务：" + service.name));
		localTextView3.setBackgroundResource(R.drawable.attretube_text);
		localTextView3.setLayoutParams(new ViewGroup.LayoutParams(-1, -2));
		localTextView3.setGravity(3);
		localTextView3.setGravity(16);
		localTextView3.setPadding(1, 1, 1, 1);
		attrebuteLayoutLinearLayout.addView(localTextView3);
		String strName = "";
		switch (((GalleryData) this.data).cp.leixing) {
		case 1:// 鞋子
			strName = "鞋子类型："
					+ getApp().findShoestypeById(
							((GalleryData) this.data).cp.shoestype).name;
			break;
		case 2:// 手表
			strName = "手表机芯："
					+ getApp().findChipById(((GalleryData) this.data).cp.jixin).name;
			String watchBand = "手表表带："
					+ getApp().findWatchbandById(
							((GalleryData) this.data).cp.watchband).name;
			TextView localTextView5 = new TextView(this);
			localTextView5.setText(watchBand);
			localTextView5.setBackgroundResource(R.drawable.attretube_text);
			localTextView5.setLayoutParams(new ViewGroup.LayoutParams(-1, -2));
			localTextView5.setGravity(3);
			localTextView5.setGravity(16);
			localTextView5.setPadding(1, 1, 1, 1);
			attrebuteLayoutLinearLayout.addView(localTextView5);
			break;


		case 3:
			strName = "包包类型："
					+ getApp().findBagTypeById(((GalleryData) this.data).cp.bagtype).name;
			
			String bagQuality = "包包品质："
					+ getApp().findBagQualityById(((GalleryData) this.data).cp.bagquality).name;
			TextView localTextView6 = new TextView(this);
			localTextView6.setText(bagQuality);
			localTextView6.setBackgroundResource(R.drawable.attretube_text);
			localTextView6.setLayoutParams(new ViewGroup.LayoutParams(-1, -2));
			localTextView6.setGravity(3);
			localTextView6.setGravity(16);
			localTextView6.setPadding(1, 1, 1, 1);
			attrebuteLayoutLinearLayout.addView(localTextView6);
			break;
			
		case 7:// 衣服
			strName = "服装类型："
					+ getApp().findClothingtypeById(
							((GalleryData) this.data).cp.clothingtype).name;
			break;
		case 11:// 护肤彩妆
			strName = "彩妆类型："
					+ getApp().findMakeuptypeById(
							((GalleryData) this.data).cp.makeuptype).name;
			break;

		default:
			break;

		}

		TextView localTextView4 = new TextView(this);
		localTextView4.setText(strName);
		localTextView4.setBackgroundResource(R.drawable.attretube_text);
		localTextView4.setLayoutParams(new ViewGroup.LayoutParams(-1, -2));
		localTextView4.setGravity(3);
		localTextView4.setGravity(16);
		localTextView4.setPadding(1, 1, 1, 1);
		attrebuteLayoutLinearLayout.addView(localTextView4);

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	public void copyMiaoshu(String text) {
		((ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE))
				.setText(text);
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
		for (int i = 0; i < ((GalleryData) this.data).cp.pics; i++) {
			urls.add(ServiceName.downloadUrl + "/"
					+ ((GalleryData) this.data).cp.id + "/" + i);
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
					byte[] data = netTools.downloadResource(
							GalleryActivity.this, url);
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

	@Override
	public void finish() {
		super.finish();
		Iterator localIterator = this.imagesCache.keySet().iterator();
		while (true) {
			if (!localIterator.hasNext()) {
				this.imagesCache = null;
				this.images_ga = null;
				this.urls = null;
				System.gc();
				return;
			}
			String str = (String) localIterator.next();
			if ((this.imagesCache.get(str) != null)
					&& (!((Bitmap) this.imagesCache.get(str)).isRecycled()))
				((Bitmap) this.imagesCache.get(str)).recycle();
			this.imagesCache.put(str, null);
		}
	}

}
