package com.yongan.weiyixiao.activity.source;

import java.util.HashMap;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.StateListDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.yongan.weiyixiao.R;
import com.yongan.weiyixiao.activity.BaseActivity;
import com.yongan.weiyixiao.activity.chanpin.ChanPinActivity;
import com.yongan.weiyixiao.activity.chanpin.ChanPinData;
import com.yongan.weiyixiao.entity.ChanPin;
import com.yongan.weiyixiao.service.ChanPinService;
import com.yongan.weiyixiao.vo.SeacheVo;

public class SourceListActivity extends BaseActivity<SourceListData> {
	private ListView lv;
	HashMap<String, String> map = null;
	private ProgressDialog progressDialog = null;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_source_list);
		Bundle bundle = getIntent().getExtras();
		if (savedInstanceState != null) {
			data = ((SourceListData) new Gson().fromJson(
					savedInstanceState.getString("data"), SourceListData.class));
		} else {
			data = ((SourceListData) new Gson().fromJson(
					bundle.getString("data"), SourceListData.class));
		}

		lv = ((ListView) findViewById(R.id.lvSourceList));
		SourceListAdapter sourceListAdapter = new SourceListAdapter(this,
				((SourceListData) this.data).list);
		lv.setAdapter(sourceListAdapter);
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int pos,
					long arg3) {

				SourceUserVo sourceUserVo = (SourceUserVo) ((SourceListData) data).list
						.get(pos);
				progressDialog = ProgressDialog.show(SourceListActivity.this,
						"请稍等...", "获取商品列表...", true);
				View localView = progressDialog.getWindow().getDecorView();
				getApp().setViewFontSize(localView, 20);
				progressDialog.setCancelable(true);
				progressDialog.setIcon(R.drawable.ic_launcher);
				getChanPing(sourceUserVo);

			}
		});
		Button localButton = (Button) findViewById(R.id.btnFind);
		StateListDrawable localStateListDrawable = new StateListDrawable();
		int[] arrayOfInt1 = new int[1];
		arrayOfInt1[0] = 16842919;
		localStateListDrawable.addState(arrayOfInt1, getResources()
				.getDrawable(2130837515));
		int[] arrayOfInt2 = new int[1];
		arrayOfInt2[0] = 16842910;
		localStateListDrawable.addState(arrayOfInt2, getResources()
				.getDrawable(2130837514));
		localButton.setBackgroundDrawable(localStateListDrawable);
		((TextView) findViewById(R.id.config_hidden)).requestFocus();
	}

	private void getChanPing(SourceUserVo sourceUserVo) {
		map = new HashMap();
		map.put("page", "0");
		map.put("dangkou", sourceUserVo.dangkou);
		ChanPinService.getInstans().getChanPinByDangkou(getApp().getUname(),
				getApp().getUuid(), this.map, this, "chanPinHandler");
		// 需要和服务器交互更新
	}

	public void chanPinHandler(String jsonString) {
		progressDialog.dismiss();
		SeacheVo sv = (SeacheVo) new Gson()
				.fromJson(jsonString, SeacheVo.class);
		if (sv.code == 0) {
			if (sv.cps.size() > 0) {
				Intent intent = new Intent(this, ChanPinActivity.class);
				Bundle bundle = new Bundle();
				ChanPinData chanPinData = new ChanPinData();
				chanPinData.cpData = sv.cps;
				chanPinData.mapData = this.map;
				chanPinData.isDangKou = true;
				if (chanPinData.cpData.size() >= 5) {
					ChanPin localChanPin = new ChanPin();
					localChanPin.id = Integer.valueOf(-1);
					chanPinData.cpData.add(localChanPin);
				}
				bundle.putString("cpd", new Gson().toJson(chanPinData));
				intent.putExtras(bundle);
				startActivity(intent);
			} else {
				Toast.makeText(this, "找不到您要的数据！", 1).show();
			}
		} else {
			Toast.makeText(this, sv.msg, 1).show();
		}
	}
}
