package com.yongan.dadawork.utils;

import java.io.File;

import android.content.Context;
import android.os.Handler;
import android.widget.TextView;

public class UpdateThread extends Thread {
	//从服务器下载最新版本的apk，待完善功能
	private static int loadSize;
	private static int size;
	private Context context;
	private Handler handler;
	private TextView txtLoad;
	private String urlStr;

	public UpdateThread(Handler handler, TextView textView, String urlStr,
			Context context) {
		this.handler = handler;
		this.txtLoad = textView;
		this.context = context;
		this.urlStr = urlStr;
	}

	public void run() {
		File localFile1 = new File("/sdcard/");
		if (!localFile1.exists())
			localFile1.mkdir();
		File localFile2 = new File("/sdcard/dada.apk");
		if (localFile2.exists())
			localFile2.delete();
	}
}
