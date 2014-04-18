package com.yongan.dadawork.activity.userinfo;

import java.text.SimpleDateFormat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.StateListDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.yongan.dadawork.R;
import com.yongan.dadawork.activity.BaseActivity;
import com.yongan.dadawork.activity.LoginActivity;
import com.yongan.dadawork.entity.UserEntity;

public class UserInfoActivity extends BaseActivity {
	private TextView txtUname;
	private TextView txtShenFen;
	private TextView txtDaoQi;
	private Button btnCloseSy;
	private Button btnStartSy;
	private Button btnClearMemory;// 清理缓存
	private Button btnBackLogin;// 返回登录
	private Button btnExit;
	public Handler handler;
	private ProgressDialog progressDialog = null;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_userinfo);
		UserEntity userEntity = getApp().getLoginVo().ue;
		handler = new Handler();
		txtUname = ((TextView) findViewById(R.id.txtUname));
		txtUname.setText("亲爱的：" + userEntity.userName);
		txtShenFen = ((TextView) findViewById(R.id.txtShenFen));
		txtDaoQi = ((TextView) findViewById(R.id.txtDaoQi));

		btnCloseSy = ((Button) findViewById(R.id.btnCloseSy));
		setButtonSkin(btnCloseSy);
		this.btnCloseSy.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				getApp().setOpenSy("0");
				Toast.makeText(UserInfoActivity.this, "关闭水印成功！",
						Toast.LENGTH_LONG).show();
			}
		});
		this.btnStartSy = ((Button) findViewById(R.id.btnStartSy));
		setButtonSkin(this.btnStartSy);
		this.btnStartSy.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				getApp().setOpenSy("1");
				Toast.makeText(UserInfoActivity.this, "开启水印成功！",
						Toast.LENGTH_LONG).show();
			}
		});
		this.btnClearMemory = ((Button) findViewById(R.id.btnClearMemory));
		setButtonSkin(this.btnClearMemory);
		this.btnClearMemory.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				progressDialog = ProgressDialog.show(UserInfoActivity.this,
						"请稍等...", "正在清理缓存缩略图...", true);
				View localView = progressDialog.getWindow().getDecorView();
				getApp().setViewFontSize(localView, 20);
				progressDialog.setCancelable(true);
				progressDialog.setIcon(R.drawable.ic_launcher);
				new CleraDiscFile(UserInfoActivity.this, progressDialog)
						.start();
			}
		});
		this.btnBackLogin = ((Button) findViewById(R.id.btnBackLogin));
		setButtonSkin(this.btnBackLogin);
		this.btnBackLogin.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(UserInfoActivity.this,
						LoginActivity.class);
				startActivity(intent);
				UserInfoActivity.this.finish();
			}
		});
		this.btnExit = ((Button) findViewById(R.id.btnExit));
		setButtonSkin(this.btnExit);
		this.btnExit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				getApp().exitApp();
			}
		});
		if (userEntity.qx.intValue() == 0) {
			txtShenFen.setText(Html.fromHtml("您当前是：<font>试用用户</font>"));
			txtDaoQi.setText(Html
					.fromHtml("有效时间：<font color=#ff0000>永久</font>"));
		} else {
			txtShenFen.setText(Html.fromHtml("您当前是：<font>正式用户</font>"));
			SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat(
					"yyyy-MM-dd");
//			txtDaoQi.setText(Html.fromHtml("有效时间：<font color=#ff0000>"
//					+ localSimpleDateFormat.format(userEntity.daoqi)
//					+ "</font>"));
			txtDaoQi.setText(Html.fromHtml("有效时间：<font color=#ff0000>"
					+ localSimpleDateFormat.format(Long
							.parseLong("1392683324244")) + "</font>"));
		}
	}

	private void setButtonSkin(Button button) {
		StateListDrawable localStateListDrawable = new StateListDrawable();
		int[] arrayOfInt1 = new int[1];
		arrayOfInt1[0] = 16842919;
		localStateListDrawable.addState(arrayOfInt1, getResources()
				.getDrawable(R.drawable.long_button_over));
		int[] arrayOfInt2 = new int[1];
		arrayOfInt2[0] = 16842910;
		localStateListDrawable.addState(arrayOfInt2, getResources()
				.getDrawable(R.drawable.long_button));
		button.setBackgroundDrawable(localStateListDrawable);
	}

	private long exitTime = 0;

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK
				&& event.getAction() == KeyEvent.ACTION_DOWN) {
			if ((System.currentTimeMillis() - exitTime) > 2000) {
				Toast.makeText(getApplicationContext(), "再按一次退出程序",
						Toast.LENGTH_SHORT).show();
				exitTime = System.currentTimeMillis();
			} else {
				finish();
				System.exit(0);
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
}
