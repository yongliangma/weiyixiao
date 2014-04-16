package com.yongan.dadawork.service;

import java.util.HashMap;
import java.util.Iterator;

import com.yongan.dadawork.utils.HttpUtils;
import com.yongan.dadawork.utils.ServiceName;

public class ChanPinService {
	private static ChanPinService instans;

	public static ChanPinService getInstans() {
		if (instans == null)
			instans = new ChanPinService();
		return instans;
	}

	public void getChanPin(String uname, String uuidString,
			HashMap<String, String> paramHashMap, Object paramObject,
			String paramString3) {
		HashMap<String, String> hashMap = new HashMap<String, String>();
		hashMap.put("interface", "getChanPin");
		hashMap.put("uname", uname);
		hashMap.put("uuid", uuidString);
		Iterator<String> localIterator = paramHashMap.keySet().iterator();
		while (localIterator.hasNext()) {
			String keyStr = (String) localIterator.next();
			hashMap.put(keyStr, (String) paramHashMap.get(keyStr));
		}
		HttpUtils.executePost(paramObject, paramString3,
				ServiceName.getChanPinUrl, hashMap);
	}

	public void getChanPinByDangkou(String paramString1, String paramString2,
			HashMap<String, String> paramHashMap, Object paramObject,
			String paramString3) {
		HashMap<String, String> localHashMap = new HashMap<String, String>();
		localHashMap.put("interface", "getChanPinByDangkou");
		localHashMap.put("uname", paramString1);
		localHashMap.put("uuid", paramString2);
		localHashMap.put("dangkou", (String) paramHashMap.get("dangkou"));
		localHashMap.put("page", (String) paramHashMap.get("page"));
		HttpUtils.executePost(paramObject, paramString3,
				ServiceName.getInterface(), localHashMap);
	}

	public void getChanPinById(String uname, String uuid,
			HashMap<String, String> hashMap, Object context, String method) {
		HashMap<String, String> hashMapParams = new HashMap<String, String>();
		hashMapParams.put("interface", "getChanPinById");
		hashMapParams.put("uname", uname);
		hashMapParams.put("uuid", uuid);
		hashMapParams.put("cpid", (String) hashMap.get("cpid"));
		HttpUtils.executePost(context, method, ServiceName.getChanPinByIdUrl,
				hashMapParams);
	}

	public void getSourceData(String uname, String uuidString,
			Object paramObject, String method) {
		HashMap<String, String> paramHashMap = new HashMap<String, String>();
		paramHashMap.put("interface", "findSourceUsers");
		paramHashMap.put("uname", uname);
		paramHashMap.put("uuid", uuidString);
		HttpUtils.executePost(paramObject, method, ServiceName.getInterface(),
				paramHashMap);
	}

}
