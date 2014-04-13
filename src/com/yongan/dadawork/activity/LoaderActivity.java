package com.yongan.dadawork.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.widget.ImageView;
import android.widget.Toast;

import com.yongan.dadawork.R;
import com.yongan.dadawork.service.UserService;
import com.yongan.dadawork.utils.Config;
import com.google.gson.Gson;

public class LoaderActivity extends BaseActivity {
	private static final long SPLASH_DELAY_MILLIS = 500L;
	public static Activity instanse;

	protected void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);
		setContentView(R.layout.activity_loader);
		instanse = this;
		((ImageView) findViewById(R.id.imageView1))
				.setImageResource(R.drawable.start);
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				startLoad();
			}
		}, SPLASH_DELAY_MILLIS);
	}

	private void startLoad() {
		SharedPreferences localSharedPreferences = getSharedPreferences(
				"userinfo", Context.MODE_PRIVATE);
		getApp().setUuid(localSharedPreferences.getString("uuid", ""));
		getApp().setUname(localSharedPreferences.getString("uname", ""));
		getApp().setPsd(localSharedPreferences.getString("psd", ""));
		if ("".equals(getApp().getUuid())) {
			// getApp().setUuid(Installation.id(this));
			getApp().setUuid("1233jkjekwendmks");
		}
		getApp().setUrl("http://115.28.17.18:8080/");
		readConfig();
	}

	private void loadData() {
		// 检测版本更新
		if (getApp().getConfig() == null) {
			Toast.makeText(this, "网络连接异常", Toast.LENGTH_SHORT).show();
			finish();
		} else {
			if (getApp().localBanBen < getApp().getConfig().serverBanBen) {
				startActivity(new Intent(this, UpdateActivity.class));
				finish();
			} else {
				startActivity(new Intent(this, LoginActivity.class));
				finish();
			}
		}
	}

	public void readConfig() {
		UserService.getInstans().readConfig(this, "readConfigHandler");
	}

	public void readConfigHandler(String jsonString) {
		getApp().setConfig(
				(Config) new Gson().fromJson((String) jsonString, Config.class));
		loadData();
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK)
			getApp().exitApp();
		return super.onKeyDown(keyCode, event);
	}
}
