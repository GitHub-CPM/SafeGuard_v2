package com.itheima.safeguard.domain;

/**
 * @author CPM
 * 
 *         �����ݿ�Ĳ���,������Ҫ�õ�������Щ����,��˶���˽ӿ�
 */
public interface BlackTable {

	public String PHONE = "phone"; // ���������صĺ���
	public String MODE = "mode"; // ���������ص�ģʽ
	public String BLACKTABLE = "blacktb"; // �������б�ı���
	
	// ��������ģʽ�ĳ���
	int SMS_MODE = 1 << 0; // ֻ���ض���
	int TEL_MODE = 1 << 1; // ֻ���ص绰
	int ALL_MODE = SMS_MODE | TEL_MODE; // �绰/���Ŷ�����

}
