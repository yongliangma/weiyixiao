package com.yongan.dadawork.utils;

import java.util.Comparator;

public class ComparatorObject implements Comparator {
	public int compare(Object paramObject1, Object paramObject2) {
		CItem localCItem1 = (CItem) paramObject1;
		CItem localCItem2 = (CItem) paramObject2;
		if (localCItem1.GetID() > localCItem2.GetID()) {
			return 1;
		} else {
			return -1;
		}
	}
}