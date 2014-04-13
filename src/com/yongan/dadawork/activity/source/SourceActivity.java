package com.yongan.dadawork.activity.source;

import java.util.ArrayList;
import java.util.Iterator;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.yongan.dadawork.R;
import com.yongan.dadawork.activity.BaseActivity;
import com.yongan.dadawork.entity.Bazaar;
import com.yongan.dadawork.service.ChanPinService;
import com.yongan.dadawork.vo.SourceVo;
import com.google.gson.Gson;

public class SourceActivity extends BaseActivity {
	private boolean isExit = false;
	private SourceListData sld;

	private void getSource() {
		ChanPinService.getInstans().getSourceData(getApp().getUname(),
				getApp().getUuid(), this, "sourceHandler");
	}

	protected void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);
		setContentView(R.layout.activity_source);
		ListView localListView = (ListView) findViewById(R.id.lvSource);
		ArrayList<Bazaar> localArrayList = new ArrayList<Bazaar>();
		Bazaar localBazaar = new Bazaar();
		localBazaar.name = "优质供货商";
		localBazaar.address = "此类供货商均为自行管理商品，一旦断货情况他们会及时更新产品状态。为广大用户提供更好的服务！";
		localArrayList.add(localBazaar);
		localArrayList.addAll(getApp().getLoginVo().baseData.bazaars);
		localListView.setAdapter(new SourceAdapter(this, localArrayList));
		localListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// 待完善

			}
		});
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

	public void sourceHandler(String jsonString) {
		SourceVo localSourceVo = (SourceVo) new Gson().fromJson(jsonString,
				SourceVo.class);
		Iterator localIterator;
		if (localSourceVo.code == 0) {
			sld.list = new ArrayList<SourceUserVo>();
			localIterator = localSourceVo.users.iterator();
		} else {
			Toast.makeText(this, localSourceVo.msg, 0).show();
		}
		// 待完善
	}

}
