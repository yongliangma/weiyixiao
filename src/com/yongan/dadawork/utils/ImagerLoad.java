package com.yongan.dadawork.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;

import com.yongan.dadawork.activity.BaseActivity;

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
			// URL localURL = new URL(this.context.getApp().getUrl()
			// + "service/showPic.do?cpid=" + this.dh + "&index="
			// + this.index);
			//
			// URL localURL = new URL(
			// "http://weiyixiao.aliapp.com/service/interface/download/"
			// + this.dh + "/" + this.index);

			URL localURL = new URL(
					"http://weiyixiao.oss-cn-qingdao.aliyuncs.com/2014-04-02%281%29.jpg");
			Object url = localURL;
			if ((this.context.getApp().getOpenSy().equals("1"))
					&& (Integer.valueOf(this.dh).intValue() > 13697)) {
				url = new URL(this.context.getApp().getUrl()
						+ "service/showPic.do?cpid=" + this.dh + "&index="
						+ this.index + "&shuiyin=1");
			}
			if (!this.imageFile.exists()) {
				HttpURLConnection httpURLConnection = (HttpURLConnection) ((URL) url)
						.openConnection();
				httpURLConnection.setDoInput(true);
				httpURLConnection.connect();
				InputStream localInputStream = httpURLConnection
						.getInputStream();
				DisplayMetrics localDisplayMetrics = new DisplayMetrics();
				this.context.getWindowManager().getDefaultDisplay()
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
			Message localMessage2 = this.handler.obtainMessage();
			localMessage2.arg1 = this.index;
			this.handler.sendMessage(localMessage2);
			return;

		} catch (Exception e) {
			Message message = this.handler.obtainMessage();
			message.arg1 = this.index;
			this.handler.sendMessage(message);
			e.printStackTrace();
		}
	}
}
