package com.yongan.dadawork.entity;

import java.io.Serializable;

@TName(name = "config")
public class Config extends EntityBase implements Serializable {
	private static final long serialVersionUID = -7447969079445774276L;
	public Integer banben;
	public String updateUrl;
}