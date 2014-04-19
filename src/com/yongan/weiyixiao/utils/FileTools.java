package com.yongan.weiyixiao.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.http.util.ByteArrayBuffer;

import android.content.Context;

public class FileTools {

	public static String sd_card = "/sdcard/";
	public static String path = "WeiyixiaoPics/";
	public static String FILE_PATH = sd_card + path;

	/**
	 * 创建文件夹
	 * 
	 * @param context
	 */
	public static void mkdir(Context context) {
		File file;
		file = new File(FILE_PATH);
		if (!file.exists()) {
			file.mkdir();
		}
	}

	/**
	 * 保存图片到SD卡
	 * 
	 * @param URL
	 * @param data
	 * @throws IOException
	 */
	public static void saveImage(String URL, byte[] data) throws IOException {
		String name = urlToLocal(URL);
		saveData(FILE_PATH, name, data);
	}

	/**
	 * 读取图片
	 * 
	 * @param filename
	 * @return
	 * @throws IOException
	 */
	public static byte[] readImage(String filename) throws IOException {
		String name = urlToLocal(filename);
		byte[] tmp = readData(FILE_PATH, name);
		return tmp;
	}

	/**
	 * 读取图片工具
	 * 
	 * @param path
	 * @param name
	 * @return
	 * @throws IOException
	 */
	private static byte[] readData(String path, String name) throws IOException {
		ByteArrayBuffer buffer = null;
		String paths = path + name;
		File file = new File(paths);
		if (!file.exists()) {
			return null;
		}
		InputStream inputstream = new FileInputStream(file);
		buffer = new ByteArrayBuffer(1024);
		byte[] tmp = new byte[1024];
		int len;
		while (((len = inputstream.read(tmp)) != -1)) {
			buffer.append(tmp, 0, len);
		}
		inputstream.close();
		return buffer.toByteArray();
	}

	/**
	 * 图片保存工具类
	 * 
	 * @param path
	 * @param fileName
	 * @param data
	 * @throws IOException
	 */
	private static void saveData(String path, String fileName, byte[] data)
			throws IOException {
		// String name = MyHash.mixHashStr(AdName);
		File file = new File(path + fileName);
		if (!file.exists()) {
			file.createNewFile();
		}
		FileOutputStream outStream = new FileOutputStream(file);
		outStream.write(data);
		outStream.close();
	}

	/**
	 * 判断文件是否存在 true存在 false不存在
	 * 
	 * @param url
	 * @return
	 */
	public static boolean compare(String url) {
		String name = urlToLocal(url);
		String paths = FILE_PATH + name;
		File file = new File(paths);
		if (!file.exists()) {
			return false;
		}
		return true;
	}

	public static String urlToLocal(String url) {
		String subStr = url.substring(url.indexOf("download") + 9);
		String[] filename = subStr.split("/");
		String name = "sy_no_" + filename[0] + "_" + filename[1] + ".jpg";
		return name;
	}
}
