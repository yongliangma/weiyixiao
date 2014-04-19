package com.yongan.weiyixiao.activity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.app.Application;
import android.content.SharedPreferences;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.yongan.weiyixiao.activity.source.SourceUserVo;
import com.yongan.weiyixiao.entity.LeiXing;
import com.yongan.weiyixiao.entity.Link;
import com.yongan.weiyixiao.entity.PinPai;
import com.yongan.weiyixiao.entity.XingBie;
import com.yongan.weiyixiao.entity.clazz.BagQuality;
import com.yongan.weiyixiao.entity.clazz.BagType;
import com.yongan.weiyixiao.entity.clazz.Chip;
import com.yongan.weiyixiao.entity.clazz.ClothingType;
import com.yongan.weiyixiao.entity.clazz.MakeupType;
import com.yongan.weiyixiao.entity.clazz.Service;
import com.yongan.weiyixiao.entity.clazz.ShoesType;
import com.yongan.weiyixiao.entity.clazz.Watchband;
import com.yongan.weiyixiao.utils.Config;
import com.yongan.weiyixiao.vo.LoginVo;

public class OurApplication extends Application {
	public static OurApplication instanse;
	public List<BaseActivity> activitys = new ArrayList();
	private Config config;
	public boolean isLoad = false;
	public int localBanBen = 1;
	public LoginVo loginVo;
	private String psd = "";
	private String uname = "";
	private String url = "";
	private String uuid = "";

	private String getObject(String paramString) {
		return getSharedPreferences("userinfo", 0).getString(paramString, "");
	}

	public static boolean isNumeric(String str) {
		final String number = "1234567890";
		if (str.equals("")) {
			return false;
		}
		for (int i = 0; i < str.length(); i++) {
			if (number.indexOf(str.charAt(i)) == -1) {
				return false;
			}
		}
		return true;
	}

	private void savaObject(String key, Object object) {
		SharedPreferences.Editor edit = getSharedPreferences("userinfo", 0)
				.edit();
		edit.putString(key, new Gson().toJson(object));
		edit.commit();
	}

	public void exitApp() {
		Iterator localIterator = this.activitys.iterator();
		while (true) {
			if (!localIterator.hasNext()) {
				if (LoginActivity.instans != null)
					LoginActivity.instans.finish();
				if (LoaderActivity.instanse != null)
					LoaderActivity.instanse.finish();
				System.exit(0);
				return;
			}
			BaseActivity localBaseActivity = (BaseActivity) localIterator
					.next();
			if (localBaseActivity == null)
				continue;
			localBaseActivity.finish();
		}
	}

	public LeiXing findLeiXing(Integer paramInteger) {
		Iterator localIterator = this.loginVo.baseData.lxs.iterator();
		LeiXing lx = null;
		while (localIterator.hasNext()) {
			lx = (LeiXing) localIterator.next();
			if (lx.id.equals(paramInteger)) {
				return lx;
			}
		}
		return lx;
	}

	public Service findServiceById(Integer paramInteger) {
		Iterator localIterator = this.loginVo.baseData.ss.iterator();
		Service ss = null;
		while (localIterator.hasNext()) {
			ss = (Service) localIterator.next();
			if (ss.id.equals(paramInteger)) {
				return ss;
			}
		}
		return ss;
	}

	public ShoesType findShoestypeById(Integer paramInteger) {
		Iterator localIterator = this.loginVo.baseData.sts.iterator();
		ShoesType st = null;
		while (localIterator.hasNext()) {
			st = (ShoesType) localIterator.next();
			if (st.id.equals(paramInteger)) {
				return st;
			}
		}
		return st;
	}

	public Chip findChipById(Integer paramInteger) {
		Iterator localIterator = this.loginVo.baseData.cs.iterator();
		Chip chip = null;
		while (localIterator.hasNext()) {
			chip = (Chip) localIterator.next();
			if (chip.id.equals(paramInteger)) {
				return chip;
			}
		}
		return chip;
	}

	public Watchband findWatchbandById(Integer paramInteger) {
		Iterator localIterator = this.loginVo.baseData.ws.iterator();
		Watchband wb = null;
		while (localIterator.hasNext()) {
			wb = (Watchband) localIterator.next();
			if (wb.id.equals(paramInteger)) {
				return wb;
			}
		}
		return wb;
	}

	public BagQuality findBagQualityById(Integer paramInteger) {
		Iterator localIterator = this.loginVo.baseData.bqs.iterator();
		BagQuality bq = null;
		while (localIterator.hasNext()) {
			bq = (BagQuality) localIterator.next();
			if (bq.id.equals(paramInteger)) {
				return bq;
			}
		}
		return bq;
	}

	public BagType findBagTypeById(Integer paramInteger) {
		Iterator localIterator = this.loginVo.baseData.bts.iterator();
		BagType bt = null;
		while (localIterator.hasNext()) {
			bt = (BagType) localIterator.next();
			if (bt.id.equals(paramInteger)) {
				return bt;
			}
		}
		return bt;
	}

	public ClothingType findClothingtypeById(Integer paramInteger) {
		Iterator localIterator = this.loginVo.baseData.cts.iterator();
		ClothingType ct = null;
		while (localIterator.hasNext()) {
			ct = (ClothingType) localIterator.next();
			if (ct.id.equals(paramInteger)) {
				return ct;
			}
		}
		return ct;
	}

	public MakeupType findMakeuptypeById(Integer paramInteger) {
		Iterator localIterator = this.loginVo.baseData.mts.iterator();
		MakeupType mt = null;
		while (localIterator.hasNext()) {
			mt = (MakeupType) localIterator.next();
			if (mt.id.equals(paramInteger)) {
				return mt;
			}
		}
		return mt;
	}

	public String getLinkStr(String paramString) {
		Iterator localIterator = this.loginVo.baseData.links.iterator();
		String str = "<font color=#ff0000>未知</font>";
		while (localIterator.hasNext()) {
			Link lin = (Link) localIterator.next();
			if (lin.name.equals(paramString)) {
				str = lin.link;
			}
		}
		return str;
	}

	public XingBie findXingBie(Integer paramInteger) {
		Iterator localIterator = this.loginVo.baseData.xbs.iterator();
		XingBie localXingBie = null;
		while (localIterator.hasNext()) {
			localXingBie = (XingBie) localIterator.next();
			if (localXingBie.id.equals(paramInteger))
				return localXingBie;
		}
		return localXingBie;
	}

	public PinPai findPinPaiById(Integer id) {
		Object object = null;
		Iterator iterator = this.loginVo.baseData.pps.iterator();
		while (iterator.hasNext()) {
			PinPai pinpai = (PinPai) iterator.next();
			if (!pinpai.id.equals(id))
				continue;
			object = pinpai;
		}
		if (object == null) {
			object = new PinPai();
			((PinPai) object).id = Integer.valueOf(0);
			((PinPai) object).cname = "重新登录";
			((PinPai) object).ename = "即可显示";
		}
		return (PinPai) object;
	}

	public List<SourceUserVo> getSourceList(String paramString) {
		ArrayList<SourceUserVo> suvList = new ArrayList<SourceUserVo>();
		Iterator localIterator = getLoginVo().baseData.links.iterator();
		while (localIterator.hasNext()) {
			Link link = (Link) localIterator.next();
			if (link.name.indexOf(paramString) != -1) {
				SourceUserVo localSourceUserVo = new SourceUserVo();
				localSourceUserVo.name = "";
				localSourceUserVo.dangkou = link.name;
				localSourceUserVo.miaoshu = "暂未提供任何厂商描述！";
				suvList.add(localSourceUserVo);
			}
		}
		return suvList;
	}

	public Config getConfig() {
		if (config == null)
			config = ((Config) new Gson().fromJson(getObject("config"),
					Config.class));
		return config;
	}

	public LoginVo getLoginVo() {
		if (loginVo == null)
			loginVo = ((LoginVo) new Gson().fromJson(getObject("loginVo"),
					LoginVo.class));
		return loginVo;
	}

	public String getOpenSy() {
		return getSharedPreferences("userinfo", 0).getString("openSy", "0");
	}

	public String getPsd() {
		if ("".equals(psd))
			psd = getSharedPreferences("userinfo", 0).getString("psd", "");
		return psd;
	}

	public String getUname() {
		if ("".equals(uname))
			uname = getSharedPreferences("userinfo", 0).getString("uname", "");
		return uname;
	}

	public String getUrl() {
		if ("".equals(this.url))
			url = getSharedPreferences("userinfo", 0).getString("url", "");
		return url;
	}

	public String getUuid() {
		if ("".equals(uuid))
			uuid = getSharedPreferences("userinfo", 0).getString("uuid", "");
		return this.uuid;
	}

	public void onCreate() {
		super.onCreate();
		instanse = this;
		ImageLoaderConfiguration imageLoaderConfiguration = new ImageLoaderConfiguration.Builder(
				this).threadPoolSize(3).threadPriority(4)
				.tasksProcessingOrder(QueueProcessingType.FIFO)
				.denyCacheImageMultipleSizesInMemory().build();
		ImageLoader.getInstance().init(imageLoaderConfiguration);
	}

	public void saveShared(String key, String value) {
		SharedPreferences.Editor localEditor = getSharedPreferences("userinfo",
				0).edit();
		localEditor.putString(key, value);
		localEditor.commit();
	}

	public void setConfig(Config config) {
		this.config = config;
		savaObject("config", config);
	}

	public void setLoginVo(LoginVo loginVo) {
		this.loginVo = loginVo;
		savaObject("loginVo", loginVo);
	}

	public void setOpenSy(String openSyString) {
		SharedPreferences.Editor editor = getSharedPreferences("userinfo", 0)
				.edit();
		editor.putString("openSy", openSyString);
		editor.commit();
	}

	public void setPsd(String psdString) {
		SharedPreferences.Editor editor = getSharedPreferences("userinfo", 0)
				.edit();
		editor.putString("psd", psdString);
		editor.commit();
	}

	public void setUname(String unameString) {
		SharedPreferences.Editor editor = getSharedPreferences("userinfo", 0)
				.edit();
		editor.putString("uname", unameString);
		editor.commit();
	}

	public void setUrl(String url) {
		saveShared("url", url);
		this.url = url;
	}

	public void setUuid(String uuidString) {
		SharedPreferences.Editor editor = getSharedPreferences("userinfo", 0)
				.edit();
		editor.putString("uuid", uuidString);
		editor.commit();
	}

	public void setViewFontSize(Object paramObject, int paramInt) {
		ViewGroup localViewGroup;
		if (paramObject instanceof ViewGroup) {
			localViewGroup = (ViewGroup) paramObject;
			int i = localViewGroup.getChildCount();
			for (int j = 0; j < i; j++) {
				setViewFontSize(localViewGroup.getChildAt(j), paramInt);
			}
		} else if (paramObject instanceof TextView) {
			((TextView) paramObject).setTextSize(paramInt);
		}
	}
}
