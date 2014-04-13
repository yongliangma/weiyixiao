package com.yongan.dadawork.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.yongan.dadawork.R;
import com.yongan.dadawork.service.UserService;
import com.yongan.dadawork.vo.LoginVo;
import com.google.gson.Gson;

public class LoginActivity extends BaseActivity {
	public static Activity instans;
	public Button btnLogin;
	public Button btnRegister;
	private ProgressDialog progressDialog = null;
	private EditText txtName;
	private EditText txtPsd;
	private TextView txtShenMing;

	protected void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);
		setContentView(R.layout.activity_login);
		instans = this;
		txtName = ((EditText) findViewById(R.id.txtName));
		txtPsd = ((EditText) findViewById(R.id.txtPsd));
		txtName.setText(getApp().getUname());
		txtPsd.setText(getApp().getPsd());
		txtShenMing = ((TextView) findViewById(R.id.txtShenMing));
		btnLogin = ((Button) findViewById(R.id.btnLogin));
		btnRegister = ((Button) findViewById(R.id.btnRegister));
		txtShenMing.setText(Html.fromHtml(getApp().getConfig().loginText));
	}

	public void btnLoginClick(View paramView) {
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

	private void showMessage(String text) {
		Toast.makeText(this, text, Toast.LENGTH_LONG).show();
	}

	public static void showMsg(String text) {
		showMsg(text);
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

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		setLoginInfo();
	}

	public void setLoginInfo() {
		txtName.setText(getApp().getUname());
		txtPsd.setText(getApp().getPsd());
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK)
			getApp().exitApp();
		return super.onKeyDown(keyCode, event);
	}
}
