package com.yongan.dadawork.activity.chanpin;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.widget.Gallery;

public class GuideGallery extends Gallery {
	private Activity activity;
	Handler handler;
	boolean timeCondition;
	boolean timeFlag;

	public GuideGallery(Context paramContext, Activity paramActivity) {
		super(paramContext);
		this.activity = paramActivity;
	}

	public GuideGallery(Context paramContext, AttributeSet paramAttributeSet) {
		super(paramContext, paramAttributeSet);
	}

	public GuideGallery(Context paramContext, AttributeSet paramAttributeSet,
			int paramInt) {
		super(paramContext, paramAttributeSet, paramInt);
	}
}
