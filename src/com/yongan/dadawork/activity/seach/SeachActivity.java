package com.yongan.dadawork.activity.seach;

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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.yongan.dadawork.R;
import com.yongan.dadawork.activity.BaseActivity;
import com.yongan.dadawork.activity.OurApplication;
import com.yongan.dadawork.activity.chanpin.ChanPinActivity;
import com.yongan.dadawork.activity.chanpin.ChanPinData;
import com.yongan.dadawork.entity.ChanPin;
import com.yongan.dadawork.entity.LeiXing;
import com.yongan.dadawork.entity.PinPai;
import com.yongan.dadawork.entity.XingBie;
import com.yongan.dadawork.service.ChanPinService;
import com.yongan.dadawork.utils.CItem;
import com.yongan.dadawork.utils.ComparatorObject;
import com.yongan.dadawork.vo.LoginVo;
import com.yongan.dadawork.vo.SeacheVo;
import com.google.gson.Gson;

public class SeachActivity extends BaseActivity<SeachData> {
	public static SeachActivity instanse;
	private Button btnClear;
	private Button btnFind;
	private Button btnFindCodition;
	private boolean isExit = false;
	private ProgressDialog progressDialog;
	private Spinner spBiHe;// 闭合方式
	private Spinner spBq;// 品质
	private Spinner spBt;// 包包类型
	private Spinner spChip;// 机芯
	private Spinner spCt;// 服装类型
	private Spinner spMakeupType;// 护肤彩妆类型
	private Spinner spMaterial;// 材质
	private Spinner spPinPai;// 品牌
	private Spinner spRenQun;// 人群
	private Spinner spService;// 售后
	private Spinner spShh;// 鞋跟高度
	private Spinner spShs;// 鞋跟粗细
	private Spinner spSt;// 鞋子类型
	private Spinner spWatchband;// 手表表带
	public TextView txtid;

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
		((SeachData) this.data).mapData.put("pp", "-1");
		((SeachData) this.data).mapData.put("text", "");
		ChanPinService.getInstans().getChanPin(getApp().getUname(),
				getApp().getUuid(), ((SeachData) this.data).mapData, this,
				"chanPinHandler");
	}

	protected void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setContentView(R.layout.activity_seach);

		if (bundle != null) {
			data = ((SeachData) new Gson().fromJson(bundle.getString("data"),
					SeachData.class));
		} else {
			data = new SeachData();
		}
		instanse = this;
		txtid = ((TextView) findViewById(R.id.txtId));
		btnFind = ((Button) findViewById(R.id.btnFind));
		spRenQun = ((Spinner) findViewById(R.id.spRenQun));
		spPinPai = ((Spinner) findViewById(R.id.spPinPai));
		btnClear = ((Button) findViewById(R.id.btnClear));
		btnFindCodition = ((Button) findViewById(R.id.btnFindCodition));
		initSpData();
		initClazz();

	}

	private void initClazz() {
		LinearLayout localLinearLayout = (LinearLayout) findViewById(R.id.clazzLayout);
		ClassButton localClassButton1 = new ClassButton(this);
		LeiXing localLeiXing1 = new LeiXing();
		localLeiXing1.name = "全部";
		localLeiXing1.id = Integer.valueOf(-1);
		localClassButton1.setData(localLeiXing1);
		localLinearLayout.addView(localClassButton1);
		Iterator localIterator = getApp().getLoginVo().baseData.lxs.iterator();
		while (true) {
			if (!localIterator.hasNext()) {
				localClassButton1.performClick();
				return;
			}
			LeiXing localLeiXing2 = (LeiXing) localIterator.next();
			ClassButton localClassButton2 = new ClassButton(this);
			localClassButton2.setData(localLeiXing2);
			localLinearLayout.addView(localClassButton2);
		}
	}

	public void clazzClick(LeiXing paramLeiXing) {
		setAttbete(paramLeiXing.name);
		((SeachData) this.data).lx = paramLeiXing;
	}

	private void initSpData() {
		LoginVo localLoginVo = getApp().getLoginVo();
		// List localList =localLoginVo.baseData.pps ;// 品牌列表
		ArrayList<CItem> localArrayList1 = new ArrayList<CItem>();
		localArrayList1.add(new CItem(-1, "选择人群(默认全部 )"));
		for (int i = 0; i < localLoginVo.baseData.xbs.size(); i++) {
			XingBie localXingBie = (XingBie) localLoginVo.baseData.xbs.get(i);
			localArrayList1.add(new CItem(localXingBie.id.intValue(),
					localXingBie.name));
		}
		ArrayAdapter<CItem> localArrayAdapter1 = new ArrayAdapter<CItem>(this,
				android.R.layout.simple_spinner_item, localArrayList1);
		localArrayAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spRenQun.setAdapter(localArrayAdapter1);

		ArrayList<CItem> localArrayList2 = new ArrayList<CItem>();
		localArrayList2.add(new CItem(-1, "选择品牌(默认全部)"));
		for (int j = 0; j < localLoginVo.baseData.pps.size(); j++) {
			PinPai localPinPai = (PinPai) localLoginVo.baseData.pps.get(j);
			localArrayList2.add(new CItem(localPinPai.id.intValue(),
					localPinPai.cname + "(" + localPinPai.ename + ")"));
		}
		ArrayAdapter<CItem> localArrayAdapter2 = new ArrayAdapter<CItem>(this,
				android.R.layout.simple_spinner_item, localArrayList2);
		localArrayAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spPinPai.setAdapter(localArrayAdapter2);

		Spinner localSpinner1 = new Spinner(this);
		this.spBq = localSpinner1;
		initSpinnerData(localLoginVo.baseData.bqs, this.spBq, 1);
		Spinner localSpinner2 = new Spinner(this);
		this.spBt = localSpinner2;
		initSpinnerData(localLoginVo.baseData.bts, this.spBt, 2);
		Spinner localSpinner3 = new Spinner(this);
		this.spWatchband = localSpinner3;
		initSpinnerData(localLoginVo.baseData.ws, this.spWatchband, 10);
		Spinner localSpinner4 = new Spinner(this);
		this.spChip = localSpinner4;
		initSpinnerData(localLoginVo.baseData.cs, this.spChip, 3);
		Spinner localSpinner5 = new Spinner(this);
		this.spMaterial = localSpinner5;
		initSpinnerData(localLoginVo.baseData.ms, this.spMaterial, 5);
		Spinner localSpinner6 = new Spinner(this);
		this.spShh = localSpinner6;
		initSpinnerData(localLoginVo.baseData.shhs, this.spShh, 7);
		Spinner localSpinner7 = new Spinner(this);
		this.spShs = localSpinner7;
		initSpinnerData(localLoginVo.baseData.shss, this.spShs, 8);
		Spinner localSpinner8 = new Spinner(this);
		this.spSt = localSpinner8;
		initSpinnerData(localLoginVo.baseData.sts, this.spSt, 9);
		Spinner localSpinner9 = new Spinner(this);
		this.spCt = localSpinner9;
		initSpinnerData(localLoginVo.baseData.cts, this.spCt, 4);
		Spinner localSpinner10 = new Spinner(this);
		this.spService = localSpinner10;
		initSpinnerData(localLoginVo.baseData.ss, this.spService, 6);
		Spinner localSpinner11 = new Spinner(this);
		this.spBiHe = localSpinner11;
		initSpinnerData(localLoginVo.baseData.bhs, this.spBiHe, 11);
		Spinner localSpinner12 = new Spinner(this);
		this.spMakeupType = localSpinner12;
		initSpinnerData(localLoginVo.baseData.mts, this.spMakeupType, 12);

	}

	private void initSpinnerData(List<?> paramList, Spinner paramSpinner,
			int paramInt) {
		try {
			ArrayList<CItem> localArrayList = new ArrayList<CItem>();
			for (int i = 0; i < paramList.size(); i++) {
				Object localObject = paramList.get(i);
				Field localField1 = localObject.getClass().getField("id");
				Field localField2 = localObject.getClass().getField("name");
				localArrayList.add(new CItem(Integer.valueOf(
						localField1.get(localObject).toString()).intValue(),
						localField2.get(localObject).toString()));
			}
			Collections.sort(localArrayList, new ComparatorObject());
			ArrayAdapter<CItem> localArrayAdapter = new ArrayAdapter<CItem>(
					this, android.R.layout.simple_spinner_item,
					localArrayList);
			localArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			paramSpinner.setTag(Integer.valueOf(paramInt));
			paramSpinner.setLayoutParams(new ViewGroup.LayoutParams(-1,
					(int) TypedValue.applyDimension(1, 45.0F, getResources()
							.getDisplayMetrics())));
			paramSpinner.setAdapter(localArrayAdapter);
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void setAttbete(String paramString) {
		LinearLayout localLinearLayout = (LinearLayout) findViewById(R.id.shuxingLayout);
		localLinearLayout.removeAllViews();
		localLinearLayout.addView(this.spService);
		if (paramString.equals("包包")) {
			localLinearLayout.addView(this.spBt);
			localLinearLayout.addView(this.spBq);
			localLinearLayout.addView(this.spMaterial);
		}
		if (paramString.equals("鞋子")) {
			CItem localCItem = (CItem) this.spRenQun.getSelectedItem();
			localLinearLayout.addView(this.spSt);
			localLinearLayout.addView(this.spMaterial);
			if (localCItem.GetValue().equals("女士")) {
				localLinearLayout.addView(this.spShh);
				localLinearLayout.addView(this.spShs);
			}
			localLinearLayout.addView(this.spBiHe);
		}
		if (paramString.equals("皮带")) {
			localLinearLayout.addView(this.spBq);
			localLinearLayout.addView(this.spMaterial);
		}
		if (paramString.equals("丝巾"))
			localLinearLayout.addView(this.spMaterial);
		if (paramString.equals("手表")) {
			localLinearLayout.addView(this.spChip);
			localLinearLayout.addView(this.spWatchband);
		}
		if (paramString.equals("衣服")) {
			localLinearLayout.addView(this.spCt);
		}
		if (paramString.equals("护肤彩妆")) {
			localLinearLayout.addView(this.spMakeupType);
		}
	}

	// 点击 清除所选属性
	public void clearClick(View view) {
		LinearLayout localLinearLayout = (LinearLayout) findViewById(R.id.shuxingLayout);
		for (int i = 0; i < localLinearLayout.getChildCount(); ++i) {
			((Spinner) localLinearLayout.getChildAt(i)).setSelection(0);
		}
		this.spRenQun.setSelection(0);
		this.spPinPai.setSelection(0);
	}

	// 点击查询结果 响应的方法
	public void findChanPin(View paramView) {
		this.isExit = false;
		this.progressDialog = ProgressDialog.show(this, "请稍等...", "获取商品列表...",
				true);
		View localView = this.progressDialog.getWindow().getDecorView();
		getApp().setViewFontSize(localView, 20);
		this.progressDialog.setCancelable(true);
		this.progressDialog.setIcon(R.drawable.ic_launcher);
		String str = this.txtid.getText().toString().trim();
		if (OurApplication.isNumeric(str)) {
			HashMap<String, String> localHashMap = new HashMap<String, String>();
			localHashMap.put("cpid", str);
			ChanPinService.getInstans().getChanPinById(getApp().getUname(),
					getApp().getUuid(), localHashMap, this, "chanPinHandler");
		} else {
			((SeachData) this.data).mapData = new HashMap<String, String>();
			((SeachData) this.data).mapData.put("page", "0");
			((SeachData) this.data).mapData.put("lx",
					((SeachData) this.data).lx.id.toString());
			((SeachData) this.data).mapData.put("xb", String
					.valueOf(((CItem) spRenQun.getSelectedItem()).GetID()));
			((SeachData) this.data).mapData.put("pp", String
					.valueOf(((CItem) spPinPai.getSelectedItem()).GetID()));
			((SeachData) this.data).mapData.put("text", getText());
			if (!"".equals(str))
				((SeachData) this.data).mapData.put("miaoshu", str);
			ChanPinService.getInstans().getChanPin(getApp().getUname(),
					getApp().getUuid(), ((SeachData) this.data).mapData, this,
					"chanPinHandler");
		}
	}

	// 点击筛选条件查询 响应的方法
	public void findChanPinCodition(View paramView) {
		this.progressDialog = ProgressDialog.show(this, "请稍等...", "获取商品列表...",
				true);
		View localView = this.progressDialog.getWindow().getDecorView();
		getApp().setViewFontSize(localView, 20);
		this.progressDialog.setCancelable(true);
		this.progressDialog.setIcon(R.drawable.ic_launcher);
		String str = this.txtid.getText().toString().trim();
		((SeachData) this.data).mapData = new HashMap<String, String>();
		((SeachData) this.data).mapData.put("page", "0");
		((SeachData) this.data).mapData.put("lx",
				((SeachData) this.data).lx.id.toString());
		((SeachData) this.data).mapData.put("xb", String
				.valueOf(((CItem) this.spRenQun.getSelectedItem()).GetID()));
		((SeachData) this.data).mapData.put("pp", String
				.valueOf(((CItem) this.spPinPai.getSelectedItem()).GetID()));
		((SeachData) this.data).mapData.put("text", getText());
		if (!"".equals(str))
			((SeachData) this.data).mapData.put("miaoshu", "");
		ChanPinService.getInstans().getChanPin(getApp().getUname(),
				getApp().getUuid(), ((SeachData) this.data).mapData, this,
				"chanPinHandler");
	}

	public void chanPinHandler(String paramString) {
		progressDialog.dismiss();
		SeacheVo localSeacheVo = (SeacheVo) new Gson().fromJson(paramString,
				SeacheVo.class);
		if (localSeacheVo.code == 0) {
			if (localSeacheVo.cps.size() > 0) {
				Intent localIntent = new Intent(this, ChanPinActivity.class);
				Bundle localBundle = new Bundle();
				ChanPinData localChanPinData = new ChanPinData();
				localChanPinData.cpData = localSeacheVo.cps;
				localChanPinData.mapData = ((SeachData) this.data).mapData;
				if (localChanPinData.cpData.size() >= 5) {
					ChanPin chanPin = new ChanPin();
					chanPin.id = Integer.valueOf(-1);
					localChanPinData.cpData.add(chanPin);
				}
				localBundle.putString("cpd",
						new Gson().toJson(localChanPinData));
				localIntent.putExtras(localBundle);
				startActivity(localIntent);
			} else {
				Toast.makeText(this, "找不到您要的数据！", Toast.LENGTH_LONG).show();
			}
		} else {
			Toast.makeText(this, localSeacheVo.msg, 1).show();
		}
	}

	private String getText() {
		LinearLayout localLinearLayout = (LinearLayout) findViewById(R.id.shuxingLayout);
		String str = "";
		for (int i = 0; i < localLinearLayout.getChildCount(); i++) {
			Spinner localSpinner = (Spinner) localLinearLayout.getChildAt(i);
			CItem localCItem = (CItem) localSpinner.getSelectedItem();
			if (localCItem.GetID() == -1)
				continue;
			str = str + localSpinner.getTag().toString() + "_"
					+ localCItem.GetID() + "|";
		}
		if (str != "")
			str = str.substring(0, str.length() - 1);
		return str;
	}

	class ClassButton extends RelativeLayout {
		private ImageView imageView;
		private LeiXing lx;
		private TextView textView;

		public ClassButton(Context paramContext) {
			super(paramContext);
			((LayoutInflater) paramContext.getSystemService("layout_inflater"))
					.inflate(R.layout.class_button, this);
			imageView = ((ImageView) findViewById(R.id.imgBg));
			textView = ((TextView) findViewById(R.id.txtClazz));
			setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					clazzClick(lx);
					LinearLayout localLinearLayout = (LinearLayout) getParent();
					for (int i = 0; i < localLinearLayout.getChildCount(); i++) {
						((ClassButton) localLinearLayout.getChildAt(i)).imageView
								.setImageResource(R.drawable.button_on);
					}
					imageView.setImageResource(R.drawable.button_over);
				}
			});
		}

		public void setData(LeiXing paramLeiXing) {
			lx = paramLeiXing;
			textView.setText(paramLeiXing.name);
		}
	}
}
