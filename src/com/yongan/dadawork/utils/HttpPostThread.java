package com.yongan.dadawork.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class HttpPostThread extends Thread {
	private HashMap<String, String> data;
	private Handler handler;
	private String url = "";
	public static String DEFAULT_CHARSET = "UTF-8";

	public HttpPostThread(Handler handler, String postUrl,
			HashMap<String, String> paramHashMap) {
		this.url = postUrl;
		this.data = paramHashMap;
		this.handler = handler;
		start();
	}

	public void run() {
		HttpPost httpPost = new HttpPost(url);
		try {
			if (data != null) {
				ArrayList<BasicNameValuePair> arrayList = new ArrayList<BasicNameValuePair>();
				Iterator<String> localIterator = data.keySet().iterator();
				while (localIterator.hasNext()) {
					String key = (String) localIterator.next();
					arrayList.add(new BasicNameValuePair(key, (String) data
							.get(key)));
				}
				httpPost.setEntity(new UrlEncodedFormEntity(arrayList, "UTF-8"));
				HttpResponse httpResponse = new DefaultHttpClient()
						.execute(httpPost);
				if (httpResponse.getStatusLine().getStatusCode() == 200) {
					String result = EntityUtils.toString(httpResponse
							.getEntity());
					Log.e("result", result);
					Message message = handler.obtainMessage();
					Bundle bundle = new Bundle();
					bundle.putString("result", result);
					message.setData(bundle);
					handler.sendMessage(message);
				}
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
