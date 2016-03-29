package com.itheima.safeguard.utils;

/**
 * @author CPM
 * 
 *         这是一个简单的加密类,加密/解密均为异或运算
 */
public class EncryptUtils {

	/**
	 * @param seed
	 *            用以加密的种子
	 * @param source
	 *            操作的原文
	 * @return 返回加密后的密文
	 */
	public static String encrypt(int seed, String source) {
		byte[] bytes = source.getBytes(); // 将原文转换为字节数组
		for (int i = 0; i < bytes.length; i++) { // 遍历每个字节,对每个字节加密
			bytes[i] ^= seed; // 异或加密,密钥为seed
		}
		String dest = new String(bytes); // 密文
		return dest;
	}

	/**
	 * @param seed
	 *            用以解密的种子
	 * @param dest
	 *            操作的密文
	 * @return 返回原文
	 */
	public static String decrypt(int seed, String dest) {
		byte[] bytes = dest.getBytes(); // 将密文转换为字节数组
		for (int i = 0; i < bytes.length; i++) { // 遍历每个字节,对每个字节解密
			bytes[i] ^= seed; // 异或解密,密钥为seed
		}
		String source = new String(bytes); // 原文
		return source;
	}

}
