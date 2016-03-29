package com.itheima.safeguard.domain;

/**
 * @author Administrator
 * 
 *         封装黑名单数据的封装类
 */
public class BlackBean {

	private String phone; // 黑名单号码
	private int mode; // 黑名单拦截模式
	
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public int getMode() {
		return mode;
	}
	public void setMode(int mode) {
		this.mode = mode;
	}
	
}
