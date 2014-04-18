package com.yongan.dadawork.activity.source;

import java.util.ArrayList;
import java.util.Iterator;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.yongan.dadawork.R;
import com.yongan.dadawork.activity.BaseActivity;
import com.yongan.dadawork.entity.Bazaar;
import com.yongan.dadawork.service.ChanPinService;
import com.yongan.dadawork.vo.SourceVo;

public class SourceActivity extends BaseActivity {
	private SourceListData sld;
	private ListView lv;
	private Context mContext;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_source);
		lv = (ListView) findViewById(R.id.lvSource);
		mContext = this;
		final ArrayList<Bazaar> arrayList = new ArrayList<Bazaar>();
		Bazaar bazaar = new Bazaar();
		bazaar.name = "优质供货商";
		bazaar.address = "此类供货商均为自行管理商品，一旦断货情况他们会及时更新产品状态。为广大用户提供更好的服务！";
		arrayList.add(bazaar);

		Bazaar bazaartest1 = new Bazaar();
		bazaartest1.id = 1;
		bazaartest1.name = "白云";
		bazaartest1.address = "广东省广州市白云区桂花岗白云皮具城";
		arrayList.add(bazaartest1);

		Bazaar bazaartest2 = new Bazaar();
		bazaartest2.id = 2;
		bazaartest2.name = "广大二期";
		bazaartest2.address = "广东省广州市白云区石井街道皇岗路口广大鞋城";
		arrayList.add(bazaartest2);

		Bazaar bazaartest3 = new Bazaar();
		bazaartest3.id = 3;
		bazaartest3.name = "广大一期";
		bazaartest3.address = "广东省广州市白云区石井街道皇岗路口广大鞋城";
		arrayList.add(bazaartest3);

		Bazaar bazaartest4 = new Bazaar();
		bazaartest4.id = 4;
		bazaartest4.name = "广惠";
		bazaartest4.address = "广东省广州市白云区桂花岗金桂皮具城";
		arrayList.add(bazaartest4);

		// arrayList.addAll(getApp().getLoginVo().baseData.bazaars);//这里待完善，需要和服务器交互更新
		lv.setAdapter(new SourceAdapter(this, arrayList));
		lv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> paramAdapterView,
					View paramView, int paramInt, long paramLong) {
				 sld = new SourceListData();
				if (paramInt != 0) {
//					String str = ((Bazaar) getApp().getLoginVo().baseData.bazaars
//							.get(paramInt - 1)).name;//这里待完善，需要和服务器交互更新
					String str = arrayList.get(paramInt).name;
					sld.list = getApp().getSourceList(str);
					if (sld.list.size() > 0) {
						Intent intent = new Intent(mContext,
								SourceListActivity.class);
						Bundle bundle = new Bundle();
						bundle.putString("data", new Gson().toJson(sld));
						intent.putExtras(bundle);
						mContext.startActivity(intent);
					} else {
						Toast.makeText(mContext, "暂无数据", 0).show();
					}
				}
			}
		});

	}

	private void getSource() {
		ChanPinService.getInstans().getSourceData(getApp().getUname(),
				getApp().getUuid(), this, "sourceHandler");
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
