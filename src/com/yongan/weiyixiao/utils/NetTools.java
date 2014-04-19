package com.yongan.weiyixiao.utils;

import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.util.ByteArrayBuffer;

import android.content.Context;

public class NetTools {
	private InputStream inputstream;
	private DefaultHttpClient httpClient;
	private boolean isStop = false;
	/**
	 * 资源下载
	 * 
	 * @param context
	 *            上下文对象
	 * @param url
	 *            下载url地址
	 * @return
	 */
	public byte[] downloadResource(Context context, String url)
			throws ClientProtocolException, IOException {
		isStop = false;
		ByteArrayBuffer buffer = null;
		HttpGet hp = new HttpGet(url);
		httpClient = new DefaultHttpClient();
		HttpConnectionParams.setConnectionTimeout(httpClient.getParams(),
				5 * 1000);
		HttpConnectionParams.setSoTimeout(httpClient.getParams(), 5 * 1000);
		HttpResponse response = httpClient.execute(hp);
		if (response.getStatusLine().getStatusCode() == 200) {
			inputstream = response.getEntity().getContent();
			if (inputstream != null) {
				buffer = new ByteArrayBuffer(1024);
				byte[] tmp = new byte[1024];
				int len;
				while (((len = inputstream.read(tmp)) != -1)
						&& (false == isStop)) {
					buffer.append(tmp, 0, len);
				}
			}
			cancel();
		}
		return buffer.toByteArray();
	}

	/**
	 * 强制关闭请求
	 */
	public synchronized void cancel() throws IOException {
		if (null != httpClient) {
			isStop = true;
			httpClient.getConnectionManager().shutdown();
			httpClient = null;
		}
		if (inputstream != null) {
			inputstream.close();
		}
	}
}
