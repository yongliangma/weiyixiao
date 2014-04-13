package com.yongan.dadawork.activity;

import com.yongan.dadawork.R;
import com.yongan.dadawork.utils.UpdateThread;

import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.widget.TextView;

//待完善
public class UpdateActivity extends BaseActivity {
	private TextView txtLoad;
	private Handler uiHandler = new Handler();

	private void startLoad() {
		new UpdateThread(uiHandler, txtLoad, getApp().getUrl()
				+ "/WeiXinSource.apk", this).start();
	}

	protected void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);
		setContentView(R.layout.activity_update);
		txtLoad = ((TextView) findViewById(R.id.txtDown));
		startLoad();
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK)
			getApp().exitApp();
		return super.onKeyDown(keyCode, event);
	}
}
