package com.itheima.safeguard.utils;

/**
 * @author CPM
 * 
 *         ����һ���򵥵ļ�����,����/���ܾ�Ϊ�������
 */
public class EncryptUtils {

	/**
	 * @param seed
	 *            ���Լ��ܵ�����
	 * @param source
	 *            ������ԭ��
	 * @return ���ؼ��ܺ������
	 */
	public static String encrypt(int seed, String source) {
		byte[] bytes = source.getBytes(); // ��ԭ��ת��Ϊ�ֽ�����
		for (int i = 0; i < bytes.length; i++) { // ����ÿ���ֽ�,��ÿ���ֽڼ���
			bytes[i] ^= seed; // ������,��ԿΪseed
		}
		String dest = new String(bytes); // ����
		return dest;
	}

	/**
	 * @param seed
	 *            ���Խ��ܵ�����
	 * @param dest
	 *            ����������
	 * @return ����ԭ��
	 */
	public static String decrypt(int seed, String dest) {
		byte[] bytes = dest.getBytes(); // ������ת��Ϊ�ֽ�����
		for (int i = 0; i < bytes.length; i++) { // ����ÿ���ֽ�,��ÿ���ֽڽ���
			bytes[i] ^= seed; // ������,��ԿΪseed
		}
		String source = new String(bytes); // ԭ��
		return source;
	}

}
