package com.yongan.dadawork.utils;

import java.util.HashMap;

public class HttpUtils {
	public static void executePost(Object context, String method,
			String postUrl, HashMap<String, String> params) {
		new HttpPostThread(new HttpPostHandler(context, method),
				postUrl, params);
	}
}