package com.yongan.dadawork.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.ActivityGroup;
import android.app.LocalActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.LinearLayout.LayoutParams;

import com.yongan.dadawork.R;
import com.yongan.dadawork.activity.seach.SeachActivity;
import com.yongan.dadawork.activity.source.SourceActivity;
import com.yongan.dadawork.activity.userinfo.UserInfoActivity;

public class MainActivity extends ActivityGroup {

	public static Context mContext = null;
	private LocalActivityManager localActivityManager = null;
	private LinearLayout mainTab = null;
	private LinearLayout mainTabContainer = null;
	public static int mainTabContainerHeight = 0;
	private Intent mainTabIntent = null;
	int tabSize;
	protected List<Class<?>> mTabActivitys = new ArrayList<Class<?>>();
	protected static List<Integer> mTabNormalImages = new ArrayList<Integer>();
	protected static List<Integer> mTabPressImages = new ArrayList<Integer>();

	// Tab banner title
	private LinearLayout mainTabBanner = null;

	// Tab ImageView
	private List<ImageView> tabImageViews = null;
	public int tabIndex = 1;

	public void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);
		setContentView(R.layout.activity_main);
		mContext = getApplicationContext();
		mainTab = (LinearLayout) findViewById(R.id.main_tab);
		mainTabBanner = (LinearLayout) findViewById(R.id.main_tab_banner);

		mainTabContainer = (LinearLayout) findViewById(R.id.main_tab_container);
		mainTabContainer.getViewTreeObserver().addOnGlobalLayoutListener(
				new OnGlobalLayoutListener() {
					@Override
					public void onGlobalLayout() {
						// compute the mainTabContainer's height after layout
						if (mainTabContainerHeight == 0) {
							mainTabContainerHeight = mainTabContainer
									.getHeight() - mainTabBanner.getHeight();
						}
					}
				});

		initTab();

	}

	@Override
	protected void onResume() {
		super.onResume();
		for (int j = 0; j < tabSize; j++) {
			tabImageViews.get(j).setBackgroundResource(
					getTabNormalImages().get(j));
		}
		int tabnum = PreferenceManager.getDefaultSharedPreferences(mContext)
				.getInt("tabNum", 1);
		tabImageViews.get(tabnum).setBackgroundResource(
				getTabPressImages().get(tabnum));
		localActivityManager = getLocalActivityManager();
		setContainerView("tab" + tabnum, getTabActivitys().get(tabnum));
	}

	public List<Class<?>> getTabActivitys() {
		return mTabActivitys;
	}

	public List<Integer> getTabNormalImages() {
		return mTabNormalImages;
	}

	public List<Integer> getTabPressImages() {
		return mTabPressImages;
	}

	public void fillTabs() {
		mTabActivitys.add(IndexActivity.class);
		mTabActivitys.add(SeachActivity.class);
		mTabActivitys.add(SourceActivity.class);
		mTabActivitys.add(UserInfoActivity.class);

		mTabNormalImages.add(R.drawable.main_on);
		mTabNormalImages.add(R.drawable.goods_on);
		mTabNormalImages.add(R.drawable.source_on);
		mTabNormalImages.add(R.drawable.user_info_on);

		mTabPressImages.add(R.drawable.main_over);
		mTabPressImages.add(R.drawable.goods_over);
		mTabPressImages.add(R.drawable.source_over);
		mTabPressImages.add(R.drawable.user_info_over);
	}

	/**
	 * init the tab
	 * */
	private void initTab() {
		fillTabs();
		mainTab.removeAllViews();
		tabImageViews = new ArrayList<ImageView>();

		ImageView tabImageView;
		LinearLayout.LayoutParams tabLp = new LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 1);

		tabSize = getTabActivitys().size();
		for (int i = 0; i < tabSize; i++) {
			tabImageView = new ImageView(this);
			tabImageView.setTag(i);
			tabImageView.setLayoutParams(tabLp);
			tabImageView.setBackgroundResource(getTabNormalImages().get(i));
			tabImageView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					tabIndex = (Integer) (v.getTag());
					setContainerView("tab" + tabIndex,
							getTabActivitys().get(tabIndex));
					for (int j = 0; j < tabSize; j++) {
						tabImageViews.get(j).setBackgroundResource(
								getTabNormalImages().get(j));
					}
					tabImageViews.get(tabIndex).setBackgroundResource(
							getTabPressImages().get(tabIndex));
				}

			});
			tabImageViews.add(tabImageView);
			mainTab.addView(tabImageView);

		}
		// mainTab.setBackgroundResource(R.drawable.tab_bg);
	}

	public void setContainerView(String id, Class<?> activity) {
		mainTabContainer.removeAllViews();
		mainTabIntent = new Intent(this, activity);
		View view = localActivityManager.startActivity(id, mainTabIntent)
				.getDecorView();
		view.setLayoutParams(new ViewGroup.LayoutParams(
				LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT));
		mainTabContainer.addView(localActivityManager.startActivity(id,
				mainTabIntent).getDecorView());
	}

	private long exitTime = 0;

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK
				&& event.getAction() == KeyEvent.ACTION_DOWN) {
			if ((System.currentTimeMillis() - exitTime) > 2000) {
				Toast.makeText(getApplicationContext(), "再按一次退出程序",
						Toast.LENGTH_SHORT).show();
				exitTime = System.currentTimeMillis();
			} else {
				finish();
				System.exit(0);
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
}