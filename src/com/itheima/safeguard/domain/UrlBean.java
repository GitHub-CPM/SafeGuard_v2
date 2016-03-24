package com.itheima.safeguard.domain;

/**
 * @author CPM
 * 
 *         这是用以封装网络解析到的Json内容的标准类
 */
public class UrlBean {

	private int version; // app的版本信息
	private String apkUrl; // 更新最新版本app的网络地址
	private String desc; // 新版本app的描述信息

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public String getApkUrl() {
		return apkUrl;
	}

	public void setApkUrl(String apkUrl) {
		this.apkUrl = apkUrl;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

}
