package com.yongan.dadawork.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.graphics.Bitmap;
import android.graphics.Matrix;

public final class ImageUtils {

	public static Bitmap scaleBitmap(Bitmap paramBitmap, int paramInt1,
			int paramInt2) {
		Bitmap localBitmap = zoomImage(paramBitmap, paramInt1, paramInt2);
		float f1 = localBitmap.getWidth();
		float f2 = localBitmap.getHeight();
		int i = (int) Math.floor((f1 - paramInt1) / 2.0F);
		int j = (int) Math.floor((f2 - paramInt2) / 2.0F);
		new Matrix();
		return Bitmap.createBitmap(localBitmap, i, j, paramInt1, paramInt2);
	}

	public static Bitmap zoomImage(Bitmap bitmap, double paramDouble1,
			double paramDouble2) {
		float f1 = bitmap.getWidth();
		float f2 = bitmap.getHeight();
		float f3;
		if (f1 - paramDouble1 > f2 - paramDouble2) {
			f3 = (float) (paramDouble2 / f2);
		} else {
			f3 = (float) (paramDouble1 / f1);
		}
		float f4 = f3 * f1 / f1;
		float f5 = f3 * f2 / f2;
		Matrix localMatrix = new Matrix();
		localMatrix.postScale(f4, f5);
		return Bitmap.createBitmap(bitmap, 0, 0, (int) f1, (int) f2,
				localMatrix, true);
	}

	public static byte[] getBytes(InputStream paramInputStream) {
		ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
		byte[] arrayOfByte = new byte[2048];
		while (true)
			try {
				int i = paramInputStream.read(arrayOfByte, 0, 2048);
				if (i == -1)
					return localByteArrayOutputStream.toByteArray();
				localByteArrayOutputStream.write(arrayOfByte, 0, i);
				localByteArrayOutputStream.flush();
			} catch (IOException localIOException) {
				localIOException.printStackTrace();
			}
	}
}