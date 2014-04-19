package com.yongan.weiyixiao.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;

import com.yongan.weiyixiao.activity.BaseActivity;

public class ImagerLoad extends Thread {
	public BaseActivity context;
	public String dh;
	public Handler handler;
	public File imageFile;
	public int index;
	public boolean isFald = false;
	public WritePic wp;

	// sy表示水印
	public void run() {
		try {
			URL url = new URL(ServiceName.downloadUrl + "/" + dh + "/" + index);
			if ((context.getApp().getOpenSy().equals("1"))) {
				url = new URL(ServiceName.downloadUrl + "/" + dh + "/" + index
						+ "?shuiyin=1");
			}
			if (!this.imageFile.exists()) {
				HttpURLConnection httpURLConnection = (HttpURLConnection) ((URL) url)
						.openConnection();
				httpURLConnection.setDoInput(true);
				httpURLConnection.connect();
				InputStream localInputStream = httpURLConnection
						.getInputStream();
				DisplayMetrics localDisplayMetrics = new DisplayMetrics();
				context.getWindowManager().getDefaultDisplay()
						.getMetrics(localDisplayMetrics);
				this.imageFile.createNewFile();
				FileOutputStream localFileOutputStream = new FileOutputStream(
						this.imageFile);
				localFileOutputStream.write(ImageUtils
						.getBytes(localInputStream));
				localFileOutputStream.flush();
				localFileOutputStream.close();
				localInputStream.close();
			}
			Message message = this.handler.obtainMessage();
			message.arg1 = this.index;
			this.handler.sendMessage(message);
			return;

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
