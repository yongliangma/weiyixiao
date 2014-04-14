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

	public void login(String uname, String password, String uuid,
			Object context, String method) {
		HashMap<String, String> hashmap = new HashMap<String, String>();
		hashmap.put("interface", "userLogin");
		hashmap.put("uname", uname);
		hashmap.put("uuid", uuid);
		hashmap.put("psd", password);
		HttpUtils.executePost(context, method, ServiceName.userLoginUrl,
				hashmap);
	}

	public void register(String uname, String password, String tell,
			String uuid, Object context, String method) {
		HashMap<String, String> hashmap = new HashMap<String, String>();
		hashmap.put("interface", "userRegister");
		hashmap.put("uname", uname);
		hashmap.put("uuid", uuid);
		hashmap.put("psd", password);
		hashmap.put("tell", tell);
		HttpUtils
				.executePost(context, method, ServiceName.registerUrl, hashmap);
	}

	public void readConfig(Object context, String method) {
		HashMap<String, String> localHashMap = new HashMap<String, String>();
		localHashMap.put("interface", "config");
		HttpUtils.executePost(context, method, ServiceName.configUrl,
				localHashMap);
	}

}