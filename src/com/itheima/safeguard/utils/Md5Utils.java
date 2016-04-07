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
 *         ����һ����MD5�������ܵķ���,��������������,���ڱ���
 */
public class Md5Utils {

	/**��ȡ�ļ���md5ֵ
	 * @param path
	 *            �ļ���·��
	 * @return �ļ���MD5ֵ
	 */
	public static String getFileMD5(String path) {
		StringBuilder mess = new StringBuilder();
		try {
			FileInputStream fis = new FileInputStream(new File(path));
			// ��ȡMD5������
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] buffer = new byte[1024 * 8];
			int len = fis.read(buffer);
			while (len != -1) {
				md.update(buffer, 0, len);
				len = fis.read(buffer);
			}
			byte[] digest = md.digest();
			for (byte b : digest) {
				// ��ÿ���ֽ�ת��16������
				int d = b & 0xff;// 0x000000ff
				String hexString = Integer.toHexString(d);
				if (hexString.length() == 1) {// �ֽڵĸ�4λΪ0
					hexString = "0" + hexString;
				}
				hexString = hexString.toUpperCase();
				mess.append(hexString);// ��ÿ���ֽڶ�Ӧ��2λʮ�������������ַ���ƴ��һ��
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
