package com.itheima.safeguard.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author CPM
 * 
 *         这是一个以MD5技术加密的方法,对输入的密码加密,便于保存
 */
public class Md5Utils {

	/**获取文件的md5值
	 * @param path
	 *            文件的路径
	 * @return 文件的MD5值
	 */
	public static String getFileMD5(String path) {
		StringBuilder mess = new StringBuilder();
		try {
			FileInputStream fis = new FileInputStream(new File(path));
			// 获取MD5加密器
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] buffer = new byte[1024 * 8];
			int len = fis.read(buffer);
			while (len != -1) {
				md.update(buffer, 0, len);
				len = fis.read(buffer);
			}
			byte[] digest = md.digest();
			for (byte b : digest) {
				// 把每个字节转成16进制数
				int d = b & 0xff;// 0x000000ff
				String hexString = Integer.toHexString(d);
				if (hexString.length() == 1) {// 字节的高4位为0
					hexString = "0" + hexString;
				}
				hexString = hexString.toUpperCase();
				mess.append(hexString);// 把每个字节对应的2位十六进制数当成字符串拼接一起
			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return mess + "";
	}

	public static String md5(String password) {

		StringBuilder sb = new StringBuilder();

		try {
			MessageDigest digest = MessageDigest.getInstance("MD5");
			byte[] bytes = password.getBytes();
			byte[] digest2 = digest.digest(bytes);
			for (byte b : digest2) {
				int d = b & 0XFF;
				String hexString = Integer.toHexString(d);
				if (hexString.length() == 1) {
					hexString = "0" + hexString;
				}
				sb.append(hexString);
			}

		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return sb + "";
	}

}
