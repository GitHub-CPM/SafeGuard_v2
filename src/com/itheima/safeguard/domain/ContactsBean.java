package com.itheima.safeguard.domain;

/**
 * @author CPM
 * 
 *         这是用于封装手机联系人的各个信息的封装类
 */
public class ContactsBean {

	private String name; // 联系人姓名
	private String phoneNum; // 联系人手机

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

}
