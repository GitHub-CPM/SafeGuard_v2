package com.itheima.safeguard.utils;

import android.net.Uri;

/**
 * @author CPM
 * 
 *         ��Ӧ���п����õ��ĳ�������������൱��
 */
public interface MyConstants {

	String SPFILE = "config"; // sharedpreference�������ļ�
	String PASSWORD = "password"; // sharedpreference�ļ��ڵ�����
	String ISSETUPED = "is_setuped"; // �Ƿ��Ѿ����ù��ֻ���������
	String SIM = "sim"; // �ֻ�SIM����Ϣ
	String SAFENUMBER = "safenumber"; // ��ȫ����
	int SEED = 520; // ���Լ���/���ܵ�����
	String LOSTFIND = "lost_find"; // �Ƿ񿪻������ֻ���������
	String LOSTFINDNEWNAME = "lost_find_new_name"; // �޸���"�ֻ�����"��������
	String AUTOUPDATE = "autoupdate"; // �Ƿ����Զ�����Ӧ�ó�����
	String TOAST_X = "toastX";// �Զ�����˾��x����
	String TOAST_Y = "toastY";// �Զ�����˾��y����
	String TOAST_STYLE = "toast_style"; // �Զ�����˾����ʽ
	String SHOWSYSTEMPROGRESS = "showsystemprogress"; // ��ʾϵͳ�Ľ���
	String APPLOCKTABLENAME = "applocktb"; // ���������ݿ�ı���
	String APPLOCKPACKNAME = "packname"; // ���������ݿ������(����)
	Uri URI_APPLOCK = Uri.parse("content://itheima/applock");
	String UPDATE_VIRUS_DATA_URL = "http:10.0.2.2/8080/safeguard/virusdbversion";
}
