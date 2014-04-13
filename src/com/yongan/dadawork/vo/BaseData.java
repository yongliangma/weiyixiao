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
	public List<BiHe> bhs;
	public List<BagQuality> bqs;
	public List<BagType> bts;
	public List<Chip> cs;
	public List<ClothingType> cts;
	public List<Link> links;
	public List<LeiXing> lxs;
	public List<Material> ms;
	public List<MakeupType> mts;
	public List<PinPai> pps;
	public List<ShoesHeelHeight> shhs;
	public List<ShoesHeelSize> shss;
	public List<Service> ss;
	public List<ShoesType> sts;
	public List<Watchband> ws;
	public List<XingBie> xbs;
}