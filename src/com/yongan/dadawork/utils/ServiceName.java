package com.yongan.dadawork.utils;

import com.yongan.dadawork.activity.OurApplication;

public class ServiceName {
	public static String getInterface() {
		return OurApplication.instanse.getUrl() + "service/interface.do";
	}

	public static final String HOSTNAME = "http://weiyixiao.aliapp.com/service/interface/";;
	public static final String registerUrl = HOSTNAME + "userRegister";
	public static final String configUrl = HOSTNAME + "config";
	public static final String userLoginUrl = HOSTNAME + "userLogin";

}