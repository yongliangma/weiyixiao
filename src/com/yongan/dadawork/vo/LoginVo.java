package com.yongan.dadawork.vo;

import com.yongan.dadawork.entity.UserEntity;
import java.io.Serializable;

public class LoginVo extends ObjectVo implements Serializable {
	private static final long serialVersionUID = -851940452428251054L;
	public BaseData baseData;
	public UserEntity ue;
}