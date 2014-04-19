package com.yongan.weiyixiao.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.os.Handler;

import com.yongan.weiyixiao.activity.BaseActivity;

public class WritePic {
	private BaseActivity context;
	public int currentIndex;
	public String data;
	public String dh;// 产品id
	public List<File> files = new ArrayList<File>();
	public Handler handler;
	public int oldSize = 0;
	public int size = 0;// 图片序号

	public WritePic(BaseActivity activity) {
		this.context = activity;
	}

	public void startLoad() {
		FileTools.mkdir(context);
		File imageFile;

		if (this.context.getApp().getOpenSy().equals("1")) {
			imageFile = new File(FileTools.FILE_PATH + "sy_yes_" + this.dh
					+ "_" + this.size + ".jpg");
		} else {
			imageFile = new File(FileTools.FILE_PATH + "sy_no_" + this.dh + "_"
					+ this.size + ".jpg");
		}
		ImagerLoad imagerLoad = new ImagerLoad();
		imagerLoad.dh = this.dh;
		imagerLoad.index = this.size;
		imagerLoad.handler = this.handler;
		imagerLoad.context = this.context;
		imagerLoad.wp = this;
		imagerLoad.imageFile = imageFile;
		imagerLoad.start();
		this.files.add(imageFile);
		this.size = (1 + this.size);
		if (this.size >= this.oldSize)
			return;
	}
}
