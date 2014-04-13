package com.yongan.dadawork.utils;

import android.content.Context;
import android.telephony.TelephonyManager;

public class ToolUtils {
	// 获取IMEI
	public static String getIMEI(Context context) {
		try {
			String imei = ((TelephonyManager) context
					.getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
			return imei;
		} catch (Exception e) {
		}
		return "";
	}
}
