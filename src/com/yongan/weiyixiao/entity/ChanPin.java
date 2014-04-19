package com.yongan.weiyixiao.entity;

import java.io.Serializable;

@TName(name = "chanpin")
public class ChanPin extends EntityBase implements Serializable {
	private static final long serialVersionUID = -1489829708045505871L;
	public Integer id; // you
	public Integer pinpaiId;
	public String pinpaiName;
	public Integer leixing;// you
	public Integer jiage;// you
	public Integer pics;// you
	public Integer state;// you
	public String upload;
	public Integer dangkouid;

	public String dangkouname;
	public Integer xingbie;// you
	public String shijian;// you 为null
	public String miaoshu;// you

	public String categorys;// you
	public String createTime;
	public String updateTime;

	public Integer material;
	public Integer shoestype;
	public Integer shoesheelheight;
	public Integer shoesheelsize;
	public Integer jixin;
	public Integer watchband;
	public Integer bagtype;

	public Integer bagquality;
	public Integer clothingtype;
	public Integer makeuptype;
	public Integer shouhou;
	public String dangkouhao;
	public String dangkouhaoname;

	public Integer price;// you 为null

	// public PinPai pinpai;//0415 修改Integer为PinPai

}