package com.yongan.dadawork.activity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.view.KeyEvent;
import android.widget.TextView;

import com.yongan.dadawork.R;

public class UpdateActivity extends BaseActivity {
	private TextView txtLoad;
	private static int loadSize;
	private static int size;
	private String urlStr="http://changephone.duapp.com/CallChangeSecond0412.apk";//需要更新为真正的下载地址
	private static final int DOWNLOAD_APK_ING = 1;
	private static final int DOWNLOAD_APK_FINISH = 2;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_update);
		txtLoad = ((TextView) findViewById(R.id.txtDown));
//		urlStr=getApp().getConfig().dxurl;//获得真正的下载地址
		startLoad();
	}

	private void startLoad() {
		new DownloadApkThread().start();
	}

	private class DownloadApkThread extends Thread {
		@Override
		public void run() {
			try {
				File dir = new File("/sdcard/");
				if (!dir.exists())
					dir.mkdir();
				File apkFile = new File("/sdcard/weiyixiao.apk");
				if (apkFile.exists())
					apkFile.delete();
				HttpURLConnection conn = (HttpURLConnection) new URL(urlStr)
						.openConnection();
				conn.connect();
				size = conn.getContentLength();
				InputStream is = conn.getInputStream();
				FileOutputStream fos = new FileOutputStream(apkFile);
				byte buf[] = new byte[1024];
				loadSize = 0;
				do {
					int numread = is.read(buf);
					loadSize += numread;
					uiHandler.sendEmptyMessage(DOWNLOAD_APK_ING);
					if (numread <= 0) {
						uiHandler.sendEmptyMessage(DOWNLOAD_APK_FINISH);
						break;
					}
					fos.write(buf, 0, numread);
				} while (true);
				fos.close();
				is.close();
			} catch (Exception localException) {
				localException.printStackTrace();
			}

		}
	}

	private Handler uiHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case DOWNLOAD_APK_ING:
				double d = Double.valueOf(
						Double.valueOf(loadSize).doubleValue()
								/ Double.valueOf(size).doubleValue())
						.doubleValue();
				if (d > 1.0D)
					d = 1.0D;
				txtLoad.setText(Html.fromHtml("<font color=#ff0000>下载进度："
						+ (int) (100.0D * d) + "%</font>"));
				break;
			case DOWNLOAD_APK_FINISH:
				txtLoad.setText(Html.fromHtml("<font color=#ff0000>下载进度："
						+ (int) (100.0D * 1) + "%</font>"));
				installApk();
				break;
			default:
				break;
			}
		}
	};

	private void installApk() {
		File apkFile = new File("/sdcard/weiyixiao.apk");
		if (!apkFile.exists()) {
			return;
		}
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setDataAndType(Uri.parse("file://" + apkFile.toString()),
				"application/vnd.android.package-archive");
		startActivity(intent);
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK)
			getApp().exitApp();
		return super.onKeyDown(keyCode, event);
	}
}
