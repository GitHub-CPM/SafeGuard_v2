package com.itheima.safeguard.domain;

/**
 * @author CPM
 * 
 *         对数据库的操作,经常需要用到列名这些常量,因此定义此接口
 */
public interface BlackTable {

	public String PHONE = "phone"; // 黑名单拦截的号码
	public String MODE = "mode"; // 黑名单拦截的模式
	public String BLACKTABLE = "blacktb"; // 黑名单列表的表名
	
	// 定义拦截模式的常量
	int SMS_MODE = 1 << 0; // 只拦截短信
	int TEL_MODE = 1 << 1; // 只拦截电话
	int ALL_MODE = SMS_MODE | TEL_MODE; // 电话/短信都拦截

}
