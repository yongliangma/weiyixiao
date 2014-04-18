package com.yongan.weiyixiao.vo;

import com.yongan.weiyixiao.entity.Bazaar;
import com.yongan.weiyixiao.entity.LeiXing;
import com.yongan.weiyixiao.entity.Link;
import com.yongan.weiyixiao.entity.PinPai;
import com.yongan.weiyixiao.entity.XingBie;
import com.yongan.weiyixiao.entity.clazz.BagQuality;
import com.yongan.weiyixiao.entity.clazz.BagType;
import com.yongan.weiyixiao.entity.clazz.BiHe;
import com.yongan.weiyixiao.entity.clazz.Chip;
import com.yongan.weiyixiao.entity.clazz.ClothingType;
import com.yongan.weiyixiao.entity.clazz.MakeupType;
import com.yongan.weiyixiao.entity.clazz.Material;
import com.yongan.weiyixiao.entity.clazz.Service;
import com.yongan.weiyixiao.entity.clazz.ShoesHeelHeight;
import com.yongan.weiyixiao.entity.clazz.ShoesHeelSize;
import com.yongan.weiyixiao.entity.clazz.ShoesType;
import com.yongan.weiyixiao.entity.clazz.Watchband;

import java.util.List;

public class BaseData {
	public List<Bazaar> bazaars;
	public List<BagQuality> bqs;//返回结果有1
	public List<BagType> bts;//返回结果有1
	public List<Chip> cs;//返回结果有1
	public List<ClothingType> cts;//返回结果有1
	public List<Link> links;//返回结果有1
	public List<LeiXing> lxs;//返回结果有1
	public List<MakeupType> mts;//返回结果有1
	public List<PinPai> pps;//返回结果有1
	public List<Service> ss;//返回结果有1
	public List<ShoesType> sts;//返回结果有1
	public List<Watchband> ws;//返回结果有1
	public List<XingBie> xbs;//返回结果有1
}