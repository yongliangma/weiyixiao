package com.yongan.dadawork.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.yongan.dadawork.R;
import com.yongan.dadawork.service.UserService;
import com.yongan.dadawork.vo.ObjectVo;

public class RegisterActity extends BaseActivity {
	private ProgressDialog progressDialog = null;
	private String uname;
	private String psd;
	private String cpsd;
	private String tell;
	private EditText txtConfrim;
	private EditText txtName;
	private EditText txtPsd;
	private EditText txtTell;

	private TextView txtMiaoShu;

	protected void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);
		setContentView(R.layout.activity_register);
		txtName = ((EditText) findViewById(R.id.txtName));
		txtPsd = ((EditText) findViewById(R.id.txtPsd));
		txtConfrim = ((EditText) findViewById(R.id.txtConfrim));
		txtTell = ((EditText) findViewById(R.id.txt_tell));
		txtMiaoShu = ((TextView) findViewById(R.id.txtMiaoShu));
		txtMiaoShu.setText(Html.fromHtml(getApp().getConfig().seachText));
	}

	public void btnRegisterClick(View paramView) {
		if (!"".equals(getApp().getUname())) {
			Toast.makeText(this, "您已经注册过了！请不要重复注册！", Toast.LENGTH_SHORT).show();
		} else {
			txtName.requestFocus();
			uname = txtName.getText().toString().trim();
			psd = txtPsd.getText().toString().trim();
			cpsd = txtConfrim.getText().toString().trim();
			tell = txtTell.getText().toString().trim();
			if ((uname.length() < 6) || (psd.length() < 6)
					|| (cpsd.length() < 6)) {
				Toast.makeText(this, "用户名或密码不能少于6位！", Toast.LENGTH_LONG).show();
				return;
			}
			if (!psd.equals(cpsd)) {
				Toast.makeText(this, "两次密码填写不一致！！", Toast.LENGTH_LONG).show();
				return;
			}
			if (tell.length() != 11) {
				Toast.makeText(this, "手机号码格式错误！", Toast.LENGTH_LONG).show();
				return;
			}
			progressDialog = ProgressDialog.show(this, "请稍等...", "正在提交注册信息...",
					true);
			View localView = progressDialog.getWindow().getDecorView();
			getApp().setViewFontSize(localView, 20);
			progressDialog.setCancelable(true);
			progressDialog.setIcon(R.drawable.ic_launcher);
			UserService.getInstans().register(uname, cpsd, tell,
					getApp().getUuid(), this, "registerHandler");
		}
	}

	public void registerHandler(String jsonString) {
		progressDialog.dismiss();
		ObjectVo objectVo = (ObjectVo) new Gson().fromJson(jsonString,
				ObjectVo.class);
		if (objectVo.code == 0) {
			Toast.makeText(this, "注册成功！您当前为试用用户！", Toast.LENGTH_LONG).show();
			getApp().setUname(uname);
			getApp().setPsd(psd);
			finish();
		} else {
			Toast.makeText(this, objectVo.msg, Toast.LENGTH_LONG).show();
		}
	}

}
