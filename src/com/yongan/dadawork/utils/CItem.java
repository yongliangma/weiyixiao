package com.yongan.dadawork.utils;

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

	public int GetID() {
		return this.ID;
	}

	public String GetValue() {
		return this.Value;
	}

	public String toString() {
		return this.Value;
	}
}