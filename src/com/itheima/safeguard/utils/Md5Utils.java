package com.itheima.safeguard.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author CPM
 * 
 *         这是一个以MD5技术加密的方法,对输入的密码加密,便于保存
 */
public class Md5Utils {

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
