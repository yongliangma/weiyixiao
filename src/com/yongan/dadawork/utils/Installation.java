package com.yongan.dadawork.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.UUID;

import android.content.Context;

public class Installation {
	private static String sID = null;

	public static String id(Context paramContext) {
		if (sID == null) {
			File localFile = new File("test");
			try {
				if (!localFile.exists()) {
					writeInstallationFile(localFile);
				}
				sID = readInstallationFile(localFile);
			} catch (Exception localException) {
				throw new RuntimeException(localException);
			}
		}
		return sID;
	}

	private static String readInstallationFile(File paramFile)
			throws IOException {
		RandomAccessFile localRandomAccessFile = new RandomAccessFile(
				paramFile, "r");
		byte[] arrayOfByte = new byte[(int) localRandomAccessFile.length()];
		localRandomAccessFile.readFully(arrayOfByte);
		localRandomAccessFile.close();
		return new String(arrayOfByte);
	}

	private static void writeInstallationFile(File paramFile)
			throws IOException {
		FileOutputStream localFileOutputStream = new FileOutputStream(paramFile);
		localFileOutputStream.write(UUID.randomUUID().toString().getBytes());
		localFileOutputStream.close();
	}
}
