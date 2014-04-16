package com.yongan.dadawork.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.os.Handler;

import com.yongan.dadawork.activity.BaseActivity;

public class WritePic {
	public static String sdkUrl = "/sdcard/YonganDaDaPics/";
	private BaseActivity context;
	public int currentIndex;
	public String data;
	public String dh;//  产品id
	public List<File> files = new ArrayList<File>();
	public Handler handler;
	public int oldSize = 0;
	public int size = 0;//  图片序号

	public WritePic(BaseActivity paramBaseActivity) {
		this.context = paramBaseActivity;
	}

	public void startLoad() {
		File localFile1 = new File(sdkUrl);
		File localFile2;
		if (!localFile1.exists())
			localFile1.mkdir();
		if ((this.context.getApp().getOpenSy().equals("1"))
				&& (Integer.valueOf(this.dh).intValue() > 13697)) {
			localFile2 = new File(sdkUrl + "sy_yes_" + this.dh + "_"
					+ this.size + ".jpg");
		} else {
			localFile2 = new File(sdkUrl + "sy_no_" + this.dh + "_" + this.size
					+ ".jpg");
		}

		ImagerLoad localImagerLoad = new ImagerLoad();
		localImagerLoad.dh = this.dh;
		localImagerLoad.index = this.size;
		localImagerLoad.handler = this.handler;
		localImagerLoad.context = this.context;
		localImagerLoad.wp = this;
		localImagerLoad.imageFile = localFile2;
		localImagerLoad.start();
		this.files.add(localFile2);
		this.size = (1 + this.size);
		// 此类待完善

		if (this.size >= this.oldSize)
			return;

	}
}
