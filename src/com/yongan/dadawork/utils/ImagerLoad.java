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
			URL localURL = new URL(this.context.getApp().getUrl()
					+ "service/showPic.do?cpid=" + this.dh + "&index="
					+ this.index);
			Object localObject = localURL;
			if ((this.context.getApp().getOpenSy().equals("1"))
					&& (Integer.valueOf(this.dh).intValue() > 13697)) {
				localObject = new URL(this.context.getApp().getUrl()
						+ "service/showPic.do?cpid=" + this.dh + "&index="
						+ this.index + "&shuiyin=1");}
				if (!this.imageFile.exists()) {
					HttpURLConnection localHttpURLConnection = (HttpURLConnection) ((URL) localObject)
							.openConnection();
					localHttpURLConnection.setDoInput(true);
					localHttpURLConnection.connect();
					InputStream localInputStream = localHttpURLConnection
							.getInputStream();
					DisplayMetrics localDisplayMetrics = new DisplayMetrics();
					this.context.getWindowManager().getDefaultDisplay()
							.getMetrics(localDisplayMetrics);
					this.imageFile.createNewFile();
					FileOutputStream localFileOutputStream = new FileOutputStream(
							this.imageFile);
					localFileOutputStream.write(ImageUtils
							.getBytes(localHttpURLConnection.getInputStream()));
					localFileOutputStream.flush();
					localFileOutputStream.close();
					localInputStream.close();
				}
				Message localMessage2 = this.handler.obtainMessage();
				localMessage2.arg1 = this.index;
				this.handler.sendMessage(localMessage2);
				return;
			

		} catch (Exception localException) {
			Message localMessage1 = this.handler.obtainMessage();
			localMessage1.arg1 = this.index;
			this.handler.sendMessage(localMessage1);
			localException.printStackTrace();
		}
	}
}
