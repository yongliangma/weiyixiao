package com.yongan.weiyixiao.activity.chanpin;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.Html;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.yongan.weiyixiao.R;
import com.yongan.weiyixiao.entity.ChanPin;
import com.yongan.weiyixiao.entity.PinPai;
import com.yongan.weiyixiao.entity.UserEntity;
import com.yongan.weiyixiao.utils.ImageUtils;
import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

public class ImageAdapter extends BaseAdapter {
	private static final String TAG = "ImageAdapter";
	public static int setHeght;
	public static int setWidth;
	private ChanPinActivity context;
	public List<ChanPin> cpdata;
	private DisplayImageOptions mDisplayImageOptions;
	private ImageLoadingListenerImpl mImageLoadingListenerImpl;
	private String str;

	public ImageAdapter(ChanPinActivity activity, List<ChanPin> cpdata) {
		this.context = activity;
		this.cpdata = cpdata;
		this.mDisplayImageOptions = new DisplayImageOptions.Builder()
				.showStubImage(R.drawable.bg)
				.showImageForEmptyUri(R.drawable.bg)
				.showImageOnFail(R.drawable.bg)
				.imageScaleType(ImageScaleType.IN_SAMPLE_INT)
				.resetViewBeforeLoading(false).cacheInMemory(true)
				.cacheOnDisc(true).build();
		this.mImageLoadingListenerImpl = new ImageLoadingListenerImpl();
	}

	@Override
	public int getCount() {
		return cpdata.size();
	}

	@Override
	public Object getItem(int arg0) {
		return (ChanPin) cpdata.get(arg0);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final ChanPin chanpin = (ChanPin) this.cpdata.get(position);
		ViewHolder holder;
		if (convertView == null) {
			convertView = LayoutInflater.from(this.context).inflate(
					R.layout.listitem_chanpin, parent, false);
			holder = new ViewHolder();
			holder.title = ((TextView) convertView.findViewById(R.id.txtTitle));
			holder.content = ((TextView) convertView
					.findViewById(R.id.txtContent));
			holder.img1 = ((ImageView) convertView.findViewById(R.id.img1));
			holder.txtLink = ((TextView) convertView.findViewById(R.id.txtRiQi));
			holder.txtGet = ((TextView) convertView.findViewById(R.id.txtGet));
			holder.btnXiangQing = ((Button) convertView
					.findViewById(R.id.btnXiangQing));
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		ViewGroup.LayoutParams localLayoutParams;
		localLayoutParams = convertView.getLayoutParams();
		if (chanpin.id.intValue() == -1) {
			DisplayMetrics localDisplayMetrics = new DisplayMetrics();
			this.context.getWindowManager().getDefaultDisplay()
					.getMetrics(localDisplayMetrics);
			localLayoutParams.height = (int) (0.1D * localDisplayMetrics.heightPixels);
			holder.title.setVisibility(View.INVISIBLE);
			holder.content.setVisibility(View.INVISIBLE);
			holder.title.setVisibility(View.INVISIBLE);
			holder.img1.setVisibility(View.INVISIBLE);
			holder.txtGet.setVisibility(View.VISIBLE);
			holder.txtLink.setVisibility(View.INVISIBLE);
			holder.btnXiangQing.setVisibility(View.INVISIBLE);
			convertView.setLayoutParams(localLayoutParams);
		} else {
			localLayoutParams.height = -1;
			localLayoutParams.width = -1;
			holder.title.setVisibility(View.VISIBLE);
			holder.content.setVisibility(View.VISIBLE);
			holder.title.setVisibility(View.VISIBLE);
			holder.img1.setVisibility(View.VISIBLE);
			holder.txtGet.setVisibility(View.INVISIBLE);
			holder.txtLink.setVisibility(View.VISIBLE);
			holder.btnXiangQing.setVisibility(View.INVISIBLE);
			convertView.setLayoutParams(localLayoutParams);
			// String str = "http://115.28.17.18:8080/data/" + chanpin.id
			// + "/small.jpg";
			String str = "http://192.168.1.111:8080/DaManager/service/interface/download/"
					+ chanpin.id + "/0?size=1";
			// String str = "http://192.168.1.111:8080/DaManager/uploadfile/"
			// + chanpin.id + "/0_small.jpg";
			holder.img1.setTag(str);
			holder.img1.setImageResource(R.drawable.bg);
			setWidth = holder.img1.getDrawable().getIntrinsicWidth();
			setHeght = holder.img1.getDrawable().getIntrinsicWidth();
			ImageLoader.getInstance().displayImage(str, holder.img1,
					this.mDisplayImageOptions, this.mImageLoadingListenerImpl);
			holder.title.setText(Html.fromHtml(getTitle(this.context.getApp()
					.getLoginVo().ue, chanpin)));
			holder.content.setText(Html.fromHtml(getContent(this.context
					.getApp().getLoginVo().ue, chanpin)));
			holder.txtLink.setText(Html.fromHtml(getLink(this.context.getApp()
					.getLoginVo().ue, chanpin)));
		}

		holder.btnXiangQing.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(context, GalleryActivity.class);
				Bundle bundle = new Bundle();
				GalleryData localGalleryData = new GalleryData();
				localGalleryData.cp = chanpin;
				bundle.putString("data", new Gson().toJson(localGalleryData));
				intent.putExtras(bundle);
				context.startActivity(intent);

				// context.switchXiangQing(chanpin);
				// Toast.makeText(context, "点击了详情", Toast.LENGTH_LONG).show();
			}
		});

		return convertView;
	}

	public String getContent(UserEntity paramUserEntity, ChanPin paramChanPin) {
		String str1 = "";
		if (paramChanPin.xingbie == null)
			paramChanPin.xingbie = Integer.valueOf(1);
		if (paramChanPin.jiage == null)
			paramChanPin.jiage = Integer.valueOf(0);
		if (paramChanPin.price == null) {
			if (paramChanPin.jiage == null) {
				paramChanPin.price = Integer.valueOf(0);
			} else {
				paramChanPin.price = Integer.valueOf(100 + paramChanPin.jiage
						.intValue());
			}
		}
		if (paramChanPin.leixing == null)
			paramChanPin.leixing = Integer.valueOf(0);
		if (paramChanPin.pinpaiId == null)
			paramChanPin.pinpaiId = Integer.valueOf(0);

		if (paramUserEntity.qx.intValue() == 0) {
			str1 = "<b>类型:"
					+ this.context.getApp().findLeiXing(paramChanPin.leixing).name
					+ "  "
					+ "适用:"
					+ this.context.getApp().findXingBie(paramChanPin.xingbie).name
					+ "</b><br>" + paramChanPin.miaoshu;
		}

		if (paramUserEntity.qx.intValue() == 1)
			str1 = "<b><font color=#1200ff>" + paramChanPin.dangkou
					+ "</font></b><br>" + paramChanPin.miaoshu;
		if (paramUserEntity.qx.intValue() == 2)
			str1 = "<b><font color=#1200ff>进价:" + paramChanPin.jiage + "   "
					+ paramChanPin.dangkou + "</font></b><br>"
					+ paramChanPin.miaoshu;
		String str2;
		if (paramChanPin.price.intValue() == 0) {
			str2 = "未知";
		} else {
			str2 = paramChanPin.price.toString();
		}
		if (paramUserEntity.qx.intValue() == 3) {
			str1 = "<b><font color=#1200ff>价格:"
					+ str2
					+ "</font> 类型:"
					+ this.context.getApp().findLeiXing(paramChanPin.leixing).name
					+ "  "
					+ "适用:"
					+ this.context.getApp().findXingBie(paramChanPin.xingbie).name
					+ "</b><br>" + paramChanPin.miaoshu;
		}
		return str1;
	}

	public String getLink(UserEntity paramUserEntity, ChanPin paramChanPin) {
		String str;
		if (paramChanPin.state.intValue() == 1) {
			str = "<font>编号：" + paramChanPin.id + "  日期："
			// + paramChanPin.shijian.subSequence(0, -8
			// + paramChanPin.shijian.length()) +
					+ getShowTime(paramChanPin.createTime) + "</font>";
		} else {
			str = "<font color='#ff0000'>商品已下架  日期："
			// + paramChanPin.shijian.subSequence(0, -8
			// + paramChanPin.shijian.length()) +
					+ getShowTime(paramChanPin.createTime) + "</font>";
		}

		return str;
	}

	public String getTitle(UserEntity ue, ChanPin chanpin) {
		String str;
		int i = ue.xzStr.indexOf(chanpin.id.toString());
		PinPai pinpai = context.getApp().findPinPaiById(chanpin.pinpaiId);
		if (i == -1)
			str = "<b>" + pinpai.cname + "(" + pinpai.ename + ")</b>";
		else {
			str = "<font color='#ff0000'><b>" + pinpai.cname + "("
					+ pinpai.ename + ")</b></font>";
		}
		return str;
	}

	public static final String DATE_FORMAT_TIME = "yyyy-MM-dd";

	public String getShowTime(String sendtime) {
		Long sendDateMillis = Long.parseLong(sendtime);
		Date date = new Date(sendDateMillis);
		SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT_TIME);
		String dateString = formatter.format(date);
		return dateString;
	}

	public void shuaxinData() {
		notifyDataSetChanged();
	}

	public static class ImageLoadingListenerImpl extends
			SimpleImageLoadingListener {
		public void onLoadingComplete(String paramString, View paramView,
				Bitmap paramBitmap) {
			if (paramBitmap == null)
				return;
			((ImageView) paramView).setImageBitmap(ImageUtils.scaleBitmap(
					paramBitmap, ImageAdapter.setWidth, ImageAdapter.setHeght));
		}
	}

	static class ViewHolder {
		Button btnXiangQing;
		TextView content;
		ImageView img1;
		TextView title;
		TextView txtGet;
		TextView txtLink;
	}
}
