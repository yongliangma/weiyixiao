package com.yongan.weiyixiao.service;

import com.yongan.weiyixiao.utils.HttpUtils;
import com.yongan.weiyixiao.utils.ServiceName;

import java.util.HashMap;

public class UserService {
	private static UserService instans;

	public static UserService getInstans() {
		if (instans == null)
			instans = new UserService();
		return instans;
	}

	//下载产品，把产品id写到用户的xzStr字段中，以表明用户下载过这个产品
	public void downLoad(String uname, String uuid, String cpid,
			Object context, String method) {
		HashMap<String, String> hashmap = new HashMap<String, String>();
		hashmap.put("interface", "download");
		hashmap.put("uname", uname);
		hashmap.put("uuid", uuid);
		hashmap.put("cpid", cpid);
		HttpUtils.executePost(context, method, ServiceName.downloadUrl,
				hashmap);
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
		HashMap<String, String> hashmap = new HashMap<String, String>();
		hashmap.put("interface", "config");
		HttpUtils.executePost(context, method, ServiceName.configUrl,
				hashmap);
	}

}