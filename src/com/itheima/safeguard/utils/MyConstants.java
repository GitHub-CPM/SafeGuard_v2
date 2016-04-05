package com.itheima.safeguard.utils;

/**
 * @author CPM
 * 
 *         将应用中可能用到的常量定义在这个类当中
 */
public interface MyConstants {

	String SPFILE = "config"; // sharedpreference的配置文件
	String PASSWORD = "password"; // sharedpreference文件内的密码
	String ISSETUPED = "is_setuped"; // 是否已经设置过手机防盗的向导
	String SIM = "sim"; // 手机SIM卡信息
	String SAFENUMBER = "safenumber"; // 安全号码
	int SEED = 520; // 用以加密/解密的种子
	String LOSTFIND = "lost_find"; // 是否开机开启手机防盗功能
	String LOSTFINDNEWNAME = "lost_find_new_name"; // 修改了"手机防盗"的新名字
	String AUTOUPDATE = "autoupdate"; // 是否开启自动更新应用程序监测
	String TOAST_X = "toastX";// 自定义吐司的x坐标
	String TOAST_Y = "toastY";// 自定义吐司的y坐标
	String TOAST_STYLE = "toast_style"; //自定义吐司的样式
	String SHOWSYSTEMPROGRESS = "showsystemprogress"; // 显示系统的进程

}
