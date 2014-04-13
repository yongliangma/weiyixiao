package com.yongan.dadawork.utils;

import android.os.Handler;
import android.os.Message;
import java.lang.reflect.Method;

public class HttpPostHandler extends Handler {
	private String method;
	private Object obj;

	public HttpPostHandler(Object context, String method) {
		this.obj = context;
		this.method = method;
	}

	public void handleMessage(Message msg) {
		super.handleMessage(msg);
		if (obj != null) {
			try {
				Class localClass = obj.getClass();
				Class[] arrayOfClass = new Class[1];
				arrayOfClass[0] = String.class;
				Method localMethod = localClass.getMethod(method, arrayOfClass);
				Object[] arrayOfObject = new Object[1];
				arrayOfObject[0] = msg.getData().getString("result");
				localMethod.invoke(obj, arrayOfObject);
				return;
			} catch (Exception localException) {
				localException.printStackTrace();
			}
		}
	}
}