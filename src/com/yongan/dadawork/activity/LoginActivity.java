package com.yongan.dadawork.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.yongan.dadawork.R;
import com.yongan.dadawork.service.UserService;
import com.yongan.dadawork.utils.Config;
import com.yongan.dadawork.vo.LoginVo;
import com.google.gson.Gson;

public class LoginActivity extends BaseActivity {
	public static Activity instans;
	public Button btnLogin;
	public Button btnRegister;
	private ProgressDialog progressDialog = null;
	private TextView txtName;
	private TextView txtPsd;
	private TextView txtShenMing;

	private void showMessage(String text) {
		Toast.makeText(this, text, Toast.LENGTH_LONG).show();
	}

	public static void showMsg(String text) {
		showMsg(text);
	}

	public void btnLoginClick(View paramView) {
		// if (getApp().localBanBen < getApp().getConfig().serverBanBen)
		// System.exit(0);
		String name = this.txtName.getText().toString().trim();
		String password = this.txtPsd.getText().toString().trim();
		if (("".equals(name)) || ("".equals(password)) || (name.length() < 6)
				|| (password.length() < 6)) {
			showMessage("信息填写不完整！");
		} else {
			progressDialog = ProgressDialog.show(this, "请稍等...", "正在登录...",
					true);
			View localView = this.progressDialog.getWindow().getDecorView();
			getApp().setViewFontSize(localView, 20);
			progressDialog.setCancelable(true);
			progressDialog.setIcon(R.drawable.ic_launcher);
			UserService.getInstans().login(name, password, getApp().getUuid(),
					this, "loginHandler");
		}
	}

	public void btnRegisterClick(View paramView) {
		if (!"".equals(getApp().getUname())) {
			Toast.makeText(this, "已经注册过了,请不要重复注册！", Toast.LENGTH_LONG).show();
		} else {
			startActivityForResult(new Intent(this, RegisterActity.class), 1);
		}
	}

	protected void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);
		setContentView(R.layout.activity_login);
		instans = this;
		this.txtName = ((TextView) findViewById(R.id.txtName));
		this.txtPsd = ((TextView) findViewById(R.id.txtPsd));
		this.txtName.setText(getApp().getUname());
		this.txtPsd.setText(getApp().getPsd());
		this.txtShenMing = ((TextView) findViewById(R.id.txtShenMing));
		// this.txtShenMing.setText(Html.fromHtml(getApp().getConfig().loginText));
		this.btnLogin = ((Button) findViewById(R.id.btnLogin));
		this.btnRegister = ((Button) findViewById(R.id.btnRegister));
		startLoad();
		this.txtShenMing.setText(Html.fromHtml(getApp().getConfig().loginText));
	}

	private void startLoad() {
		SharedPreferences localSharedPreferences = getSharedPreferences(
				"userinfo", Context.MODE_PRIVATE);
		getApp().setUuid(localSharedPreferences.getString("uuid", ""));
		getApp().setUname(localSharedPreferences.getString("uname", ""));
		getApp().setPsd(localSharedPreferences.getString("psd", ""));
		if ("".equals(getApp().getUuid())) {
			// getApp().setUuid(Installation.id(this));
		}
		getApp().setUrl("http://115.28.17.18:8080/");
		readConfig();
	}

	private void loadData() {
		// 检测版本更新
		if (getApp().getConfig() == null) {
			Toast.makeText(this, "网络连接异常", 0).show();
		} else {
			if (getApp().localBanBen < getApp().getConfig().serverBanBen)
				startActivity(new Intent(this, UpdateActivity.class));
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

	public void loginHandler(String jsonString) {
		// 待完善
		progressDialog.dismiss();
		LoginVo localLoginVo = (LoginVo) new Gson().fromJson(jsonString,
				LoginVo.class);
		if (localLoginVo.code == 0) {
			showMessage("亲！欢迎登陆！");
			if (getApp().getOpenSy().equals(""))
				getApp().setOpenSy("1");
			getApp().setLoginVo(localLoginVo);
			getApp().saveShared("uname", localLoginVo.ue.userName);
			getApp().saveShared("psd", localLoginVo.ue.psd);
			startActivity(new Intent(this, MainActivity.class));
		} else {
			Toast.makeText(this, localLoginVo.msg, Toast.LENGTH_LONG).show();
		}
		finish();
	}

	public void registerHandler(String paramString) {
		// 待完善
	}

	protected void onActivityResult(int paramInt1, int paramInt2,
			Intent paramIntent) {
		super.onActivityResult(paramInt1, paramInt2, paramIntent);
		setLoginInfo();
	}

	public void setLoginInfo() {
		this.txtName.setText(getApp().getUname());
		this.txtPsd.setText(getApp().getPsd());
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK)
			getApp().exitApp();
		return super.onKeyDown(keyCode, event);
	}
}
