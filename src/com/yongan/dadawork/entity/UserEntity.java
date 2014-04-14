package com.yongan.dadawork.entity;

import java.io.Serializable;

@TName(name = "user")
public class UserEntity extends EntityBase implements Serializable {
	private static final long serialVersionUID = 5328495861128608812L;
	public Long daoqi;//返回结果中无此项
	public Integer id;
	public String psd;
	public Integer qx;
	public String registerTime;
//	public Integer resetUuid;//返回结果中无此项
	public Integer state;
	public Long tell;
	public String userName;
	public String uuid;
	public Integer xzCount;
	public String xzStr;//返回结果中此项为NULL
}