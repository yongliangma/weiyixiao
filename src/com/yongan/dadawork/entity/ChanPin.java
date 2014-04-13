package com.yongan.dadawork.entity;

import java.io.Serializable;

@TName(name = "chanpin")
public class ChanPin extends EntityBase implements Serializable {
	private static final long serialVersionUID = -1489829708045505871L;
	public String categorys;
	public String dangkou;
	public Integer id;
	public Integer jiage;
	public Integer leixing;
	public String miaoshu;
	public Integer pics;
	public Integer pinpai;
	public Integer price;
	public String shijian;
	public Integer state;
	public Integer tuijian;
	public Integer xingbie;
}