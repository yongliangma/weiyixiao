package com.yongan.dadawork.vo;

import com.yongan.dadawork.entity.Bazaar;
import com.yongan.dadawork.entity.LeiXing;
import com.yongan.dadawork.entity.Link;
import com.yongan.dadawork.entity.PinPai;
import com.yongan.dadawork.entity.XingBie;
import com.yongan.dadawork.entity.clazz.BagQuality;
import com.yongan.dadawork.entity.clazz.BagType;
import com.yongan.dadawork.entity.clazz.BiHe;
import com.yongan.dadawork.entity.clazz.Chip;
import com.yongan.dadawork.entity.clazz.ClothingType;
import com.yongan.dadawork.entity.clazz.MakeupType;
import com.yongan.dadawork.entity.clazz.Material;
import com.yongan.dadawork.entity.clazz.Service;
import com.yongan.dadawork.entity.clazz.ShoesHeelHeight;
import com.yongan.dadawork.entity.clazz.ShoesHeelSize;
import com.yongan.dadawork.entity.clazz.ShoesType;
import com.yongan.dadawork.entity.clazz.Watchband;

import java.util.List;

public class BaseData {
	public List<Bazaar> bazaars;
	public List<BiHe> bhs;//返回结果有，值为空
	public List<BagQuality> bqs;//返回结果有
	public List<BagType> bts;//返回结果有
	public List<Chip> cs;//返回结果暂无
	public List<ClothingType> cts;//返回结果有
	public List<Link> links;//返回结果有
	public List<LeiXing> lxs;//返回结果有
	public List<Material> ms;//返回结果有
	public List<MakeupType> mts;//返回结果有
	public List<PinPai> pps;//返回结果有
	public List<ShoesHeelHeight> shhs;//返回结果有
	public List<ShoesHeelSize> shss;//返回结果有
	public List<Service> ss;//返回结果有
	public List<ShoesType> sts;//返回结果有
	public List<Watchband> ws;//返回结果有
	public List<XingBie> xbs;//返回结果有
}