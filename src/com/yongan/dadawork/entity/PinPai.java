package com.yongan.dadawork.entity;

import java.io.Serializable;

@TName(name = "pinpai")
public class PinPai extends EntityBase implements Serializable {// 品牌
	private static final long serialVersionUID = 88252827277371177L;
	public String cname;
	public String ename;
	public Integer id;
	public String pic;
}