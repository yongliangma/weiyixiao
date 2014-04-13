package com.yongan.dadawork.activity.chanpin;

import java.util.List;

import android.graphics.Bitmap;
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

import com.yongan.dadawork.R;
import com.yongan.dadawork.entity.ChanPin;
import com.yongan.dadawork.entity.PinPai;
import com.yongan.dadawork.entity.UserEntity;
import com.yongan.dadawork.utils.ImageUtils;
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

	public ImageAdapter(ChanPinActivity paramChanPinActivity,
			List<ChanPin> paramList) {
		this.context = paramChanPinActivity;
		this.cpdata = paramList;
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
		return this.cpdata.size();
	}

	@Override
	public Object getItem(int arg0) {
		return (ChanPin) this.cpdata.get(arg0);
	}

	@Override
	public long getItemId(int position) {
		return 0L;
	}

	@Override
	public View getView(int paramInt, View paramView, ViewGroup paramViewGroup) {
		final ChanPin localChanPin = (ChanPin) this.cpdata.get(paramInt);
		ViewHolder localViewHolder;
		if (paramView == null) {
			paramView = LayoutInflater.from(this.context).inflate(
					R.layout.listitem_chanpin, paramViewGroup, false);
			localViewHolder = new ViewHolder();
			localViewHolder.title = ((TextView) paramView
					.findViewById(R.id.txtTitle));
			localViewHolder.content = ((TextView) paramView
					.findViewById(R.id.txtContent));
			localViewHolder.img1 = ((ImageView) paramView
					.findViewById(R.id.img1));
			localViewHolder.txtLink = ((TextView) paramView
					.findViewById(R.id.txtRiQi));
			localViewHolder.txtGet = ((TextView) paramView
					.findViewById(R.id.txtGet));
			localViewHolder.btnXiangQing = ((Button) paramView
					.findViewById(R.id.btnXiangQing));
			paramView.setTag(localViewHolder);
		} else {
			localViewHolder = (ViewHolder) paramView.getTag();
		}

		ViewGroup.LayoutParams localLayoutParams;
		localLayoutParams = paramView.getLayoutParams();
		if (localChanPin.id.intValue() == -1) {
			DisplayMetrics localDisplayMetrics = new DisplayMetrics();
			this.context.getWindowManager().getDefaultDisplay()
					.getMetrics(localDisplayMetrics);
			localLayoutParams.height = (int) (0.1D * localDisplayMetrics.heightPixels);
			localViewHolder.title.setVisibility(4);
			localViewHolder.content.setVisibility(4);
			localViewHolder.title.setVisibility(4);
			localViewHolder.img1.setVisibility(4);
			localViewHolder.txtGet.setVisibility(0);
			localViewHolder.txtLink.setVisibility(4);
			localViewHolder.btnXiangQing.setVisibility(4);
			paramView.setLayoutParams(localLayoutParams);
		} else {
			localLayoutParams.height = -1;
			localLayoutParams.width = -1;
			localViewHolder.title.setVisibility(0);
			localViewHolder.content.setVisibility(0);
			localViewHolder.title.setVisibility(0);
			localViewHolder.img1.setVisibility(0);
			localViewHolder.txtGet.setVisibility(4);
			localViewHolder.txtLink.setVisibility(0);
			localViewHolder.btnXiangQing.setVisibility(0);
			paramView.setLayoutParams(localLayoutParams);
			String str = "http://115.28.17.18:8080/data/" + localChanPin.id
					+ "/small.jpg";
			localViewHolder.img1.setTag(str);
			localViewHolder.img1.setImageResource(R.drawable.bg);
			setWidth = localViewHolder.img1.getDrawable().getIntrinsicWidth();
			setHeght = localViewHolder.img1.getDrawable().getIntrinsicWidth();
			ImageLoader.getInstance().displayImage(str, localViewHolder.img1,
					this.mDisplayImageOptions, this.mImageLoadingListenerImpl);
			TextView localTextView1 = (TextView) paramView
					.findViewById(R.id.txtTitle);
			TextView localTextView2 = (TextView) paramView
					.findViewById(R.id.txtContent);
			localTextView1.setText(Html.fromHtml(getTitle(this.context.getApp()
					.getLoginVo().ue, localChanPin)));
			localTextView2.setText(Html.fromHtml(getContent(this.context
					.getApp().getLoginVo().ue, localChanPin)));
			localViewHolder.txtLink.setText(Html.fromHtml(getLink(this.context
					.getApp().getLoginVo().ue, localChanPin)));
		}

		localViewHolder.btnXiangQing.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
//				context.switchXiangQing(localChanPin);
			}
		});

		return paramView;
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
		if (paramChanPin.pinpai == null)
			paramChanPin.pinpai = Integer.valueOf(0);

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
			str = "<font>编号："
					+ paramChanPin.id
					+ "  日期："
					+ paramChanPin.shijian.subSequence(0, -8
							+ paramChanPin.shijian.length()) + "</font>";
		} else {
			str = "<font color='#ff0000'>商品已下架  日期："
					+ paramChanPin.shijian.subSequence(0, -8
							+ paramChanPin.shijian.length()) + "</font>";
		}

		return str;
	}

	public String getTitle(UserEntity paramUserEntity, ChanPin paramChanPin) {
		String str;
		int i = paramUserEntity.xzStr.indexOf(paramChanPin.id.toString());
		PinPai localPinPai = this.context.getApp().findPinPaiById(
				paramChanPin.pinpai);
		if (i == -1)
			str = "<b>" + localPinPai.cname + "(" + localPinPai.ename + ")</b>";
		else {
			str = "<font color='#ff0000'><b>" + localPinPai.cname + "("
					+ localPinPai.ename + ")</b></font>";
		}
		return str;
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
