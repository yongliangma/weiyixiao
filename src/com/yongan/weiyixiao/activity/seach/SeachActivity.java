package com.yongan.weiyixiao.activity.seach;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.yongan.weiyixiao.R;
import com.yongan.weiyixiao.activity.BaseActivity;
import com.yongan.weiyixiao.activity.OurApplication;
import com.yongan.weiyixiao.activity.chanpin.ChanPinActivity;
import com.yongan.weiyixiao.activity.chanpin.ChanPinData;
import com.yongan.weiyixiao.entity.ChanPin;
import com.yongan.weiyixiao.entity.LeiXing;
import com.yongan.weiyixiao.entity.PinPai;
import com.yongan.weiyixiao.entity.XingBie;
import com.yongan.weiyixiao.entity.clazz.Service;
import com.yongan.weiyixiao.service.ChanPinService;
import com.yongan.weiyixiao.utils.CItem;
import com.yongan.weiyixiao.utils.ComparatorObject;
import com.yongan.weiyixiao.vo.LoginVo;
import com.yongan.weiyixiao.vo.SeacheVo;
import com.google.gson.Gson;

public class SeachActivity extends BaseActivity<SeachData> {
	public static SeachActivity instanse;
	private Button btnClear;
	private Button btnFind;
	private Button btnFindCodition;
	private ProgressDialog progressDialog;
	private Spinner spBq;// 品质
	private Spinner spBt;// 包包类型
	private Spinner spChip;// 机芯
	private Spinner spCt;// 服装类型
	private Spinner spMakeupType;// 护肤彩妆类型
	private Spinner spPinPai;// 品牌
	private Spinner spRenQun;// 人群
	private Spinner spService;// 售后
	private Spinner spSt;// 鞋子类型
	private Spinner spWatchband;// 手表表带
	public EditText txtid;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_seach);

		if (savedInstanceState != null) {
			data = ((SeachData) new Gson().fromJson(savedInstanceState.getString("data"),
					SeachData.class));
		} else {
			data = new SeachData();
		}
		instanse = this;
		txtid = ((EditText) findViewById(R.id.txtId));
		btnFind = ((Button) findViewById(R.id.btnFind));
		spRenQun = ((Spinner) findViewById(R.id.spRenQun));
		spPinPai = ((Spinner) findViewById(R.id.spPinPai));
		spService = (Spinner) findViewById(R.id.spService);
		btnClear = ((Button) findViewById(R.id.btnClear));
		btnFindCodition = ((Button) findViewById(R.id.btnFindCodition));
		initSpData();
		initClazz();

	}

	private void initClazz() {
		LinearLayout ll = (LinearLayout) findViewById(R.id.clazzLayout);
		ClassButton btn1 = new ClassButton(this);
		LeiXing leiXing = new LeiXing();
		leiXing.name = "全部";
		leiXing.id = Integer.valueOf(-1);
		btn1.setData(leiXing);
		ll.addView(btn1);
		Iterator iterator = getApp().getLoginVo().baseData.lxs.iterator();
		while (true) {
			if (!iterator.hasNext()) {
				btn1.performClick();
				return;
			}
			LeiXing leiXing2 = (LeiXing) iterator.next();
			ClassButton btn2 = new ClassButton(this);
			btn2.setData(leiXing2);
			ll.addView(btn2);
		}
	}

	private void initSpData() {
		LoginVo lv = getApp().getLoginVo();
		// 试用人群的Spinner
		ArrayList<CItem> arraylistRenqun = new ArrayList<CItem>();
		arraylistRenqun.add(new CItem(-1, "选择适用人群"));
		for (int i = 0; i < lv.baseData.xbs.size(); i++) {
			XingBie xingBie = (XingBie) lv.baseData.xbs.get(i);
			arraylistRenqun.add(new CItem(xingBie.id.intValue(), xingBie.name));
		}
		ArrayAdapter<CItem> arrayAdapterRenqun = new ArrayAdapter<CItem>(this,
				android.R.layout.simple_spinner_item, arraylistRenqun);
		arrayAdapterRenqun
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spRenQun.setAdapter(arrayAdapterRenqun);
		// 产品品牌的Spinner
		ArrayList<CItem> arraylistPinpai = new ArrayList<CItem>();
		arraylistPinpai.add(new CItem(-1, "选择产品品牌"));
		for (int j = 0; j < lv.baseData.pps.size(); j++) {
			PinPai pinPai = (PinPai) lv.baseData.pps.get(j);
			arraylistPinpai.add(new CItem(pinPai.id.intValue(), pinPai.cname
					+ "(" + pinPai.ename + ")"));
		}
		ArrayAdapter<CItem> arrayAdapterPinpai = new ArrayAdapter<CItem>(this,
				android.R.layout.simple_spinner_item, arraylistPinpai);
		arrayAdapterPinpai
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spPinPai.setAdapter(arrayAdapterPinpai);
		// 售后服务的Spinner
		ArrayList<CItem> arraylistService = new ArrayList<CItem>();
		arraylistService.add(new CItem(-1, "选择售后服务"));
		for (int j = 0; j < lv.baseData.ss.size(); j++) {
			Service service = (Service) lv.baseData.ss.get(j);
			arraylistService
					.add(new CItem(service.id.intValue(), service.name));
		}
		ArrayAdapter<CItem> arrayAdapterService = new ArrayAdapter<CItem>(this,
				android.R.layout.simple_spinner_item, arraylistService);
		arrayAdapterService
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spService.setAdapter(arrayAdapterService);

		// 品质
		spBq = new Spinner(this);
		initSpinnerData(lv.baseData.bqs, spBq, 1);

		// 包包类型
		spBt = new Spinner(this);
		initSpinnerData(lv.baseData.bts, spBt, 2);

		// 手表机芯
		this.spChip = new Spinner(this);
		initSpinnerData(lv.baseData.cs, this.spChip, 3);

		// 服装类型
		spCt = new Spinner(this);
		initSpinnerData(lv.baseData.cts, spCt, 4);

		// 鞋子类型
		spSt = new Spinner(this);
		initSpinnerData(lv.baseData.sts, spSt, 9);

		// 手表表带
		spWatchband = new Spinner(this);
		initSpinnerData(lv.baseData.ws, spWatchband, 10);

		// 护肤彩妆类型
		spMakeupType = new Spinner(this);
		initSpinnerData(lv.baseData.mts, spMakeupType, 12);
	}

	private void initSpinnerData(List<?> list, Spinner spinner, int tag) {
		try {
			ArrayList<CItem> al = new ArrayList<CItem>();
			for (int i = 0; i < list.size(); i++) {
				Object object = list.get(i);
				Field field1 = object.getClass().getField("id");
				Field field2 = object.getClass().getField("name");
				al.add(new CItem(Integer.valueOf(field1.get(object).toString())
						.intValue(), field2.get(object).toString()));
			}
			Collections.sort(al, new ComparatorObject());
			ArrayAdapter<CItem> adapter = new ArrayAdapter<CItem>(this,
					android.R.layout.simple_spinner_item, al);
			adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			spinner.setTag(Integer.valueOf(tag));
			spinner.setLayoutParams(new ViewGroup.LayoutParams(-1,
					(int) TypedValue.applyDimension(1, 45.0F, getResources()
							.getDisplayMetrics())));
			spinner.setAdapter(adapter);
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}

	}

	public void clazzClick(LeiXing leiXing) {
		setAttbete(leiXing.name);
		((SeachData) this.data).lx = leiXing;
	}

	private void setAttbete(String name) {
		LinearLayout ll = (LinearLayout) findViewById(R.id.shuxingLayout);
		ll.removeAllViews();
		if (name.equals("包包")) {
			ll.addView(spBt);
			ll.addView(spBq);
		}
		if (name.equals("鞋子")) {
			ll.addView(this.spSt);
		}
		if (name.equals("皮带")) {
			ll.addView(spBq);
		}
		if (name.equals("手表")) {
			ll.addView(spChip);
			ll.addView(spWatchband);
		}
		if (name.equals("衣服")) {
			ll.addView(spCt);
		}
		if (name.equals("护肤彩妆")) {
			ll.addView(spMakeupType);
		}
	}

	private void findNewChanPin() {
		progressDialog = ProgressDialog.show(this, "请稍等...", "获取商品列表...", true);
		View localView = progressDialog.getWindow().getDecorView();
		getApp().setViewFontSize(localView, 20);
		progressDialog.setCancelable(true);
		progressDialog.setIcon(R.drawable.ic_launcher);
		((SeachData) this.data).mapData = new HashMap<String, String>();
		((SeachData) this.data).mapData.put("page", "0");
		((SeachData) this.data).mapData.put("lx", "-1");
		((SeachData) this.data).mapData.put("xb", "-1");
		((SeachData) this.data).mapData.put("ss", "-1");
		((SeachData) this.data).mapData.put("pp", "-1");
		((SeachData) this.data).mapData.put("text", "");
		ChanPinService.getInstans().getChanPin(getApp().getUname(),
				getApp().getUuid(), ((SeachData) this.data).mapData, this,
				"chanPinHandler");
	}

	// 点击 清除所选属性
	public void clearClick(View view) {
		LinearLayout ll = (LinearLayout) findViewById(R.id.shuxingLayout);
		for (int i = 0; i < ll.getChildCount(); ++i) {
			((Spinner) ll.getChildAt(i)).setSelection(0);
		}
		this.spRenQun.setSelection(0);
		this.spPinPai.setSelection(0);
		this.spService.setSelection(0);
	}

	// 点击查询结果 响应的方法
	public void findChanPin(View paramView) {
		progressDialog = ProgressDialog.show(this, "请稍等...", "获取商品列表...", true);
		View localView = progressDialog.getWindow().getDecorView();
		getApp().setViewFontSize(localView, 20);
		progressDialog.setCancelable(true);
		progressDialog.setIcon(R.drawable.ic_launcher);
		String str = txtid.getText().toString().trim();
		if (OurApplication.isNumeric(str)) {
			HashMap<String, String> hashmap = new HashMap<String, String>();
			hashmap.put("cpid", str);
			ChanPinService.getInstans().getChanPinById(getApp().getUname(),
					getApp().getUuid(), hashmap, this, "chanPinHandler");
		} else {
			((SeachData) this.data).mapData = new HashMap<String, String>();
			((SeachData) this.data).mapData.put("page", "0");
			((SeachData) this.data).mapData.put("lx",
					((SeachData) this.data).lx.id.toString());
			((SeachData) this.data).mapData.put("xb", String
					.valueOf(((CItem) spRenQun.getSelectedItem()).getId()));
			((SeachData) this.data).mapData.put("ss", String
					.valueOf(((CItem) spService.getSelectedItem()).getId()));
			((SeachData) this.data).mapData.put("pp", String
					.valueOf(((CItem) spPinPai.getSelectedItem()).getId()));
			((SeachData) this.data).mapData.put("text", getShuxings());
			if (!"".equals(str))
				((SeachData) this.data).mapData.put("miaoshu", str);
			ChanPinService.getInstans().getChanPin(getApp().getUname(),
					getApp().getUuid(), ((SeachData) this.data).mapData, this,
					"chanPinHandler");
		}
	}

	// 点击筛选条件查询 响应的方法
	public void findChanPinCodition(View paramView) {
		progressDialog = ProgressDialog.show(this, "请稍等...", "获取商品列表...", true);
		View localView = progressDialog.getWindow().getDecorView();
		getApp().setViewFontSize(localView, 20);
		progressDialog.setCancelable(true);
		progressDialog.setIcon(R.drawable.ic_launcher);
		String str = txtid.getText().toString().trim();
		((SeachData) this.data).mapData = new HashMap<String, String>();
		((SeachData) this.data).mapData.put("page", "0");
		((SeachData) this.data).mapData.put("lx",
				((SeachData) this.data).lx.id.toString());
		((SeachData) this.data).mapData.put("xb", String
				.valueOf(((CItem) this.spRenQun.getSelectedItem()).getId()));
		((SeachData) this.data).mapData.put("ss", String
				.valueOf(((CItem) spService.getSelectedItem()).getId()));
		((SeachData) this.data).mapData.put("pp", String
				.valueOf(((CItem) this.spPinPai.getSelectedItem()).getId()));
		((SeachData) this.data).mapData.put("text", getShuxings());
		if (!"".equals(str))
			((SeachData) this.data).mapData.put("miaoshu", "");
		ChanPinService.getInstans().getChanPin(getApp().getUname(),
				getApp().getUuid(), ((SeachData) this.data).mapData, this,
				"chanPinHandler");
	}

	public void chanPinHandler(String jsonString) {
		progressDialog.dismiss();
		SeacheVo sv = (SeacheVo) new Gson().fromJson(jsonString,
				SeacheVo.class);
		if (sv.code == 0) {
			if (sv.cps.size() > 0) {
				Intent intent = new Intent(this, ChanPinActivity.class);
				Bundle bundle = new Bundle();
				ChanPinData chanPinData = new ChanPinData();
				chanPinData.cpData = sv.cps;
				chanPinData.mapData = ((SeachData) this.data).mapData;
				if (chanPinData.cpData.size() >= 5) {
					ChanPin chanPin = new ChanPin();
					chanPin.id = Integer.valueOf(-1);
					chanPinData.cpData.add(chanPin);
				}
				bundle.putString("cpd", new Gson().toJson(chanPinData));
				intent.putExtras(bundle);
				startActivity(intent);
			} else {
				Toast.makeText(this, "找不到您要的数据！", Toast.LENGTH_LONG).show();
			}
		} else {
			Toast.makeText(this, sv.msg, Toast.LENGTH_LONG).show();
		}
	}

	private String getShuxings() {
		LinearLayout ll = (LinearLayout) findViewById(R.id.shuxingLayout);
		String str = "";
		for (int i = 0; i < ll.getChildCount(); i++) {
			Spinner spinner = (Spinner) ll.getChildAt(i);
			CItem citem = (CItem) spinner.getSelectedItem();
			if (citem.getId() == -1)
				continue;
			str = str + spinner.getTag().toString() + "_" + citem.getId() + "|";
		}
		if (str != "")
			str = str.substring(0, str.length() - 1);
		return str;
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
	
	class ClassButton extends RelativeLayout {
		private ImageView imageView;
		private LeiXing lx;
		private TextView textView;

		public ClassButton(Context context) {
			super(context);
			((LayoutInflater) context.getSystemService("layout_inflater"))
					.inflate(R.layout.class_button, this);
			imageView = ((ImageView) findViewById(R.id.imgBg));
			textView = ((TextView) findViewById(R.id.txtClazz));
			setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					clazzClick(lx);
					LinearLayout ll = (LinearLayout) getParent();
					for (int i = 0; i < ll.getChildCount(); i++) {
						((ClassButton) ll.getChildAt(i)).imageView
								.setImageResource(R.drawable.button_on);
					}
					imageView.setImageResource(R.drawable.button_over);
				}
			});
		}

		public void setData(LeiXing leiXing) {
			lx = leiXing;
			textView.setText(leiXing.name);
		}
	}
}
