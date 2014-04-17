package com.yongan.dadawork.activity.userinfo;

import java.io.File;

import android.app.ProgressDialog;
import android.widget.Toast;

import com.yongan.dadawork.utils.WritePic;
import com.nostra13.universalimageloader.core.ImageLoader;

public class CleraDiscFile extends Thread {
	public UserInfoActivity context;
	public ProgressDialog progressDialog;

	public CleraDiscFile(UserInfoActivity activity, ProgressDialog pd) {
		this.context = activity;
		this.progressDialog = pd;
	}

	public void run() {
		ImageLoader.getInstance().clearDiscCache();
		File localFile = new File(WritePic.sdkUrl);
		final File[] files;
		if (localFile.exists()) {
			files = localFile.listFiles();
			for (int i = 0; i < files.length; i++) {
				files[i].delete();
				final int j = i;
				context.handler.post(new Runnable() {
					@Override
					public void run() {
						progressDialog.setMessage("清理磁盘文件" + (1 + j) + "/"
								+ files.length);
					}
				});
			}
		}
		context.handler.post(new Runnable() {
			@Override
			public void run() {
				progressDialog.dismiss();
				Toast.makeText(context, "清理垃圾文件完成！", Toast.LENGTH_LONG).show();
			}
		});
	}
}
