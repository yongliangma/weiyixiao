package com.yongan.dadawork.activity;

import com.google.gson.Gson;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

public class BaseActivity<T> extends Activity {
	public T data;

	public OurApplication getApp() {
		return (OurApplication) getApplication();
	}

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		getApp().activitys.add(this);
	}

	public void onSaveInstanceState(Bundle bundle) {
		if (data != null)
			bundle.putString("data", new Gson().toJson(data));
		super.onSaveInstanceState(bundle);
	}
}