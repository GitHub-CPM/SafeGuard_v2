package com.itheima.safeguard.domain;

/**
 * @author CPM
 * 
 *         �������Է�װ�����������Json���ݵı�׼��
 */
public class UrlBean {

	private int version; // app�İ汾��Ϣ
	private String apkUrl; // �������°汾app�������ַ
	private String desc; // �°汾app��������Ϣ

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
