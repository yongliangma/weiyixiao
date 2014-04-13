package com.yongan.dadawork.activity.chanpin;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.ClipboardManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.yongan.dadawork.R;
import com.yongan.dadawork.activity.BaseActivity;
import com.yongan.dadawork.entity.ChanPin;
import com.yongan.dadawork.entity.PinPai;
import com.yongan.dadawork.entity.UserEntity;
import com.yongan.dadawork.service.ChanPinService;
import com.yongan.dadawork.service.UserService;
import com.yongan.dadawork.utils.WritePic;
import com.yongan.dadawork.vo.ObjectVo;
import com.yongan.dadawork.vo.SeacheVo;
import com.google.gson.Gson;

public class ChanPinActivity extends BaseActivity<ChanPinData> {
	public Handler handler = new Handler();
	public ImageAdapter ia;
	private ListView lv;
	private ProgressDialog progressDialog = null;
	private WritePic wp;

	protected void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		if (bundle != null) {
			this.data = ((ChanPinData) new Gson().fromJson(
					bundle.getString("data"), ChanPinData.class));
		} else {
			Bundle localBundle = getIntent().getExtras();
			this.data = ((ChanPinData) new Gson().fromJson(
					localBundle.getString("cpd"), ChanPinData.class));
		}

		setContentView(R.layout.activity_chanpin);
		lv = ((ListView) findViewById(R.id.lv));
		showList();

		if (this.data != null)
			lv.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> paramAdapterView,
						View paramView, int postion, long paramLong) {

					((ChanPinData) ChanPinActivity.this.data).currentPostion = postion;
					((ChanPinData) ChanPinActivity.this.data).cp = ((ChanPin) ((ChanPinData) ChanPinActivity.this.data).cpData
							.get(postion));
					if (((ChanPinData) ChanPinActivity.this.data).cp.id
							.intValue() != -1) {
						progressDialog = ProgressDialog
								.show(ChanPinActivity.this,
										"请稍等...",
										"下载图片资源0/"
												+ ((ChanPinData) ChanPinActivity.this.data).cp.pics,
										true);
						View localView = progressDialog.getWindow()
								.getDecorView();
						getApp().setViewFontSize(localView, 20);
						progressDialog.setCancelable(true);
						progressDialog.setIcon(R.drawable.ic_launcher);

						UserService.getInstans().downLoad(
								ChanPinActivity.this.getApp().getUname(),
								ChanPinActivity.this.getApp().getUuid(),
								((ChanPinData) ChanPinActivity.this.data).cp.id
										.toString(), ChanPinActivity.this,
								"downLoadHandler");
					} else {
						progressDialog = ProgressDialog.show(
								ChanPinActivity.this, "请稍等...", "正在获取商品数据...",
								true);
						View localView1 = progressDialog.getWindow()
								.getDecorView();
						getApp().setViewFontSize(localView1, 20);
						progressDialog.setCancelable(true);
						progressDialog.setIcon(R.drawable.ic_launcher);
						nextPage();
					}
				}
			});
	}

	public void resultHandler(String paramString) {
		this.progressDialog.dismiss();
		SeacheVo localSeacheVo = (SeacheVo) new Gson().fromJson(paramString,
				SeacheVo.class);
		if (localSeacheVo.code == 0) {
			((ChanPinData) this.data).cpData.remove(-1
					+ ((ChanPinData) this.data).cpData.size());
			if (localSeacheVo.cps.size() > 0) {
				((ChanPinData) this.data).cpData.addAll(localSeacheVo.cps);
				if (localSeacheVo.cps.size() >= 5) {
					ChanPin localChanPin = new ChanPin();
					localChanPin.id = Integer.valueOf(-1);
					((ChanPinData) this.data).cpData.add(localChanPin);
				}
				this.ia.shuaxinData();
			} else {
				Toast.makeText(this, "找不到数据！", 0).show();
			}
		} else {
			Toast.makeText(this, localSeacheVo.msg, 0).show();
		}
	}

	public void nextPage() {
		if (((ChanPinData) this.data).cpData.size() >= 50) {
			ChanPinData localChanPinData = (ChanPinData) this.data;
			localChanPinData.page = (1 + localChanPinData.page);
			if (!((ChanPinData) this.data).isDangKou) {
				((ChanPinData) this.data).mapData.put("page",
						String.valueOf(((ChanPinData) this.data).page));
				ChanPinService.getInstans().getChanPin(getApp().getUname(),
						getApp().getUuid(), ((ChanPinData) this.data).mapData,
						this, "resultHandler");
			} else {
				HashMap<String, String> localHashMap = new HashMap<String, String>();
				localHashMap.put("page",
						String.valueOf(((ChanPinData) this.data).page));
				localHashMap.put("dangkou",
						(String) ((ChanPinData) this.data).mapData
								.get("dangkou"));
				ChanPinService.getInstans().getChanPinByDangkou(
						getApp().getUname(), getApp().getUuid(), localHashMap,
						this, "resultHandler");

			}
		} else {
			Toast.makeText(this, "没有更多数据", 0).show();
			this.progressDialog.dismiss();
		}
	}

	public void downLoadHandler(String paramString) {
		ObjectVo localObjectVo = (ObjectVo) new Gson().fromJson(paramString,
				ObjectVo.class);
		if (localObjectVo.code == 0) {
			this.wp = new WritePic(this);
			this.wp.oldSize = ((ChanPinData) this.data).cp.pics.intValue();
			this.wp.size = 0;
			this.wp.dh = ((ChanPinData) this.data).cp.id.toString();
			this.wp.handler = new Handler() {
				@Override
				public void handleMessage(Message msg) {
					// TODO Auto-generated method stub
					super.handleMessage(msg);
					ChanPinData localChanPinData = (ChanPinData) ChanPinActivity.this.data;
					localChanPinData.index = (1 + localChanPinData.index);
					progressDialog
							.setMessage("下载图片资源"
									+ ((ChanPinData) ChanPinActivity.this.data).index
									+ "/"
									+ ((ChanPinData) ChanPinActivity.this.data).cp.pics);
					if ((((ChanPinData) ChanPinActivity.this.data).index < ((ChanPinData) ChanPinActivity.this.data).cp.pics
							.intValue())
							&& (((ChanPinData) ChanPinActivity.this.data).index < 9)) {
						wp.startLoad();
						return;
					}
					Toast.makeText(ChanPinActivity.this, "请直接长按文本框粘贴产品描述",
							Toast.LENGTH_LONG).show();
					progressDialog.dismiss();
					shareMultiplePictureToTimeLine(wp.files);
					// ChanPinActivity.access$3(this.this$0,
					// ChanPinActivity.access$2(this.this$0).files);
					((ChanPinData) ChanPinActivity.this.data).index = 0;
					PinPai localPinPai = ChanPinActivity.this
							.getApp()
							.findPinPaiById(
									Integer.valueOf(((ChanPinData) ChanPinActivity.this.data).cp.pinpai
											.intValue()));
					String str = "";
					if (localPinPai.id.intValue() != 0)
						str = "『" + localPinPai.cname + "』" + localPinPai.ename
								+ "  ";
					copyMiaoshu(str
							+ ((ChanPinData) ChanPinActivity.this.data).cp.miaoshu);
					UserEntity localUserEntity = ChanPinActivity.this.getApp()
							.getLoginVo().ue;
					localUserEntity.xzStr = (localUserEntity.xzStr
							+ ((ChanPinData) ChanPinActivity.this.data).cp.id + "|");
					// ChanPinActivity.access$4(this.this$0, null);
					ChanPinActivity.this.ia.shuaxinData();
					ChanPinActivity.this.getApp().setLoginVo(
							ChanPinActivity.this.getApp().getLoginVo());
				}

			};
			this.wp.startLoad();
		} else {
			progressDialog.dismiss();
			Toast.makeText(this, localObjectVo.msg, 0).show();
		}
	}

	private void showList() {
		ia = new ImageAdapter(this, ((ChanPinData) this.data).cpData);
		lv.setAdapter(this.ia);
	}

	public void copyMiaoshu(String paramString) {
		((ClipboardManager) getSystemService("clipboard")).setText(paramString);
	}

	private void shareMultiplePictureToTimeLine(List<File> files) {
		Intent intent = new Intent();
		intent.setComponent(new ComponentName("com.tencent.mm",
				"com.tencent.mm.ui.tools.ShareToTimeLineUI"));
		intent.setAction(Intent.ACTION_SEND_MULTIPLE);
		intent.setType("image/*");
		ArrayList<Uri> localArrayList = new ArrayList<Uri>();
		Iterator<File> iterator = files.iterator();
		while (iterator.hasNext()) {
			localArrayList.add(Uri.fromFile((File) iterator.next()));
		}
		intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, localArrayList);
		startActivity(intent);
	}

	public void switchXiangQing(ChanPin localChanPin) {
		Intent intent = new Intent(this, GalleryActivity.class);
		Bundle bundle = new Bundle();
		GalleryData localGalleryData = new GalleryData();
		localGalleryData.cp = localChanPin;
		bundle.putString("data", new Gson().toJson(localGalleryData));
		intent.putExtras(bundle);
		startActivity(intent);
	}
}
