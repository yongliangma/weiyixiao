package com.yongan.weiyixiao.activity.chanpin;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.widget.Gallery;

public class GuideGallery extends Gallery {
	private Activity activity;
	Handler handler;
	boolean timeCondition;
	boolean timeFlag;

	private static final int timerAnimation = 1;
	private final Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case timerAnimation:
				int position = getSelectedItemPosition();
				Log.i("msg", "position:" + position);
				if (position >= (getCount() - 1)) {
					onKeyDown(KeyEvent.KEYCODE_DPAD_LEFT, null);
				} else {
					onKeyDown(KeyEvent.KEYCODE_DPAD_RIGHT, null);
				}
				break;
			}
		};
	};

	private final Timer timer = new Timer();
	private final TimerTask task = new TimerTask() {
		public void run() {
			mHandler.sendEmptyMessage(timerAnimation);
		}
	};

	public GuideGallery(Context context, Activity activity) {
		super(context);
		this.activity = activity;
		// timer.schedule(task, 3000, 3000);
	}

	public GuideGallery(Context context, AttributeSet attributeSet) {
		super(context, attributeSet);
		// timer.schedule(task, 3000, 3000);
	}

	public GuideGallery(Context context, AttributeSet attributeSet, int i) {
		super(context, attributeSet, i);
		// timer.schedule(task, 3000, 3000);
	}

	private boolean isScrollingLeft(MotionEvent paramMotionEvent1,
			MotionEvent paramMotionEvent2) {
		if (paramMotionEvent2.getX() > paramMotionEvent1.getX()) {
			return true;
		} else {
			return false;
		}
	}

	public boolean onFling(MotionEvent paramMotionEvent1,
			MotionEvent paramMotionEvent2, float paramFloat1, float paramFloat2) {
		int keyCode;
		if (isScrollingLeft(paramMotionEvent1, paramMotionEvent2)) {
			keyCode = KeyEvent.KEYCODE_DPAD_LEFT;
		} else {
			keyCode = KeyEvent.KEYCODE_DPAD_RIGHT;
		}
		onKeyDown(keyCode, null);
		return true;
	}

	public void destroy() {
		timer.cancel();
	}

	public boolean onScroll(MotionEvent paramMotionEvent1,
			MotionEvent paramMotionEvent2, float paramFloat1, float paramFloat2) {
		this.timeCondition = false;
		return super.onScroll(paramMotionEvent1, paramMotionEvent2,
				paramFloat1, paramFloat2);
	}

	public void setActivity(Activity activity, Handler paramHandler,
			boolean paramBoolean1, boolean paramBoolean2) {
		this.activity = activity;
		this.timeCondition = paramBoolean2;
		this.timeFlag = paramBoolean1;
	}

}
