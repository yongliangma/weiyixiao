package com.yongan.dadawork.service;

import com.yongan.dadawork.utils.HttpUtils;
import com.yongan.dadawork.utils.ServiceName;
import java.util.HashMap;

public class UserService {
	private static UserService instans;

	public static UserService getInstans() {
		if (instans == null)
			instans = new UserService();
		return instans;
	}

	public void downLoad(String uname, String uuid, String cpid,
			Object context, String method) {
		HashMap<String, String> localHashMap = new HashMap<String, String>();
		localHashMap.put("interface", "download");
		localHashMap.put("uname", uname);
		localHashMap.put("uuid", uuid);
		localHashMap.put("cpid", cpid);
		HttpUtils.executePost(context, method, ServiceName.getInterface(),
				localHashMap);
	}

	public void login(String paramString1, String paramString2,
			String paramString3, Object paramObject, String paramString4) {
		HashMap<String, String> localHashMap = new HashMap<String,String>();
		localHashMap.put("interface", "userLogin");
		localHashMap.put("uname", paramString1);
		localHashMap.put("uuid", paramString3);
		localHashMap.put("psd", paramString2);
		HttpUtils.executePost(paramObject, paramString4,
				ServiceName.getInterface(), localHashMap);
	}

	public void register(String uname, String password, String tell,
			String uuid, Object context, String method) {
		HashMap<String, String> hashmap = new HashMap<String,String>();
		hashmap.put("interface", "userRegister");
		hashmap.put("uname", uname);
		hashmap.put("uuid", uuid);
		hashmap.put("psd", password);
		hashmap.put("tell", tell);
		HttpUtils.executePost(context, method, ServiceName.registerUrl,
				hashmap);
	}
	
	public void readConfig(Object context, String method) {
		HashMap<String, String> localHashMap = new HashMap<String,String>();
		localHashMap.put("interface", "config");
		HttpUtils.executePost(context, method, ServiceName.configUrl,
				localHashMap);
	}
	
}