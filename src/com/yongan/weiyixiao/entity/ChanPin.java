package com.yongan.weiyixiao.entity;

import java.io.Serializable;

@TName(name = "chanpin")
public class ChanPin extends EntityBase implements Serializable {
	private static final long serialVersionUID = -1489829708045505871L;
	public String categorys;//you
	public String dangkou;//you
	public Integer id; //you
	public Integer jiage;//you
	public Integer leixing;//you
	public String miaoshu;//you
	public Integer pics;//you

	public Integer price;//you 为null
	public String shijian;//you 为null
	public String createTime;
	public String updateTime;
	public Integer state;//you
//	public Integer tuijian;//wu  0415注释掉
	public Integer xingbie;//you
	public Integer pinpaiId;
//	public PinPai pinpai;//0415 修改Integer为PinPai
}