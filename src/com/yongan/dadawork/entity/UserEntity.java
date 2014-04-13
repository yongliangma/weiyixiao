package com.yongan.dadawork.entity;

import java.io.Serializable;

@TName(name = "user")
public class UserEntity extends EntityBase implements Serializable {
	private static final long serialVersionUID = 5328495861128608812L;
	public Long daoqi;
	public Integer id;
	public String psd;
	public Integer qx;
	public String registerTime;
	public Integer resetUuid;
	public Integer state;
	public Long tell;
	public String userName;
	public String uuid;
	public Integer xzCount;
	public String xzStr;
}