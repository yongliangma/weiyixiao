package com.yongan.weiyixiao.utils;

public class CItem {
	private int ID;
	private String Value = "";

	public CItem() {
		this.ID = 0;
		this.Value = "";
	}

	public CItem(int paramInt, String paramString) {
		this.ID = paramInt;
		this.Value = paramString;
	}

	public int getId() {
		return ID;
	}

	public String GetValue() {
		return Value;
	}

	public String toString() {
		return Value;
	}
}