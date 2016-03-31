package com.itheima.safeguard.engine;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * @author CPM
 * 
 *         �ֻ���������ص�ҵ����,�ṩ��ѯ�ķ���
 */
public class PhoneLocationEngine {

	/**
	 * ��ѯ�绰���������
	 * 
	 * @param phoneNum
	 *            Ҫ��ѯ�ĺ���
	 * @return �绰���������
	 */
	public String queryLocation(String phoneNum) {
		// Ĭ��Ϊ������
		String location = phoneNum;
		// ���ݸ����绰������������
		if (phoneNum.length() == 11 && phoneNum.startsWith("1")) { // �ֻ�����
			location = queryMobil(phoneNum);
		} else if ((phoneNum.length() == 11 || phoneNum.length() == 12)
				&& phoneNum.startsWith("0")) { // �̶��绰
			location = queryTel(phoneNum);
		} else { // ����绰
			location = queryServiceTel(phoneNum);
		}
		return location;
	}

	/**
	 * �����ֻ�����,��ѯ�ֻ�����Ĺ�����
	 * 
	 * @param phoneNum
	 *            �ֻ�����
	 * @return ���ظ��ֻ�����Ĺ�����
	 */
	public String queryMobil(String phoneNum) {
		String location = phoneNum;
		Pattern p = Pattern.compile("(1){1}(34568){1}(123456789){9}");
		Matcher m = p.matcher(phoneNum);
		boolean matches = m.matches();
		if (!matches) {
			return location;
		}
		phoneNum = phoneNum.substring(0, 7);
		SQLiteDatabase database = SQLiteDatabase.openDatabase(
				"/data/data/com.itheima.safeguard/files/address.db", null,
				SQLiteDatabase.OPEN_READONLY);
		Cursor cursor = database
				.rawQuery(
						"select location from data2 where id = 'select outkey from data1 where id = ?'",
						new String[] { phoneNum });
		if (cursor.moveToNext()) {
			location = cursor.getString(0);
		}
		return location;
	}

	/**
	 * ����̶��绰����,��ѯ�̶��绰�Ĺ�����
	 * 
	 * @param phoneNum
	 *            �̶��绰
	 * @return ���ظù̶��绰�Ĺ�����
	 */
	public String queryTel(String phoneNum) {
		String location = phoneNum;
		String quHao = "000000000";
		if (phoneNum.charAt(1) == 1 || phoneNum.charAt(1) == 2) {
			quHao = phoneNum.substring(1, 3);
		} else {
			quHao = phoneNum.substring(1, 4);
		}
		SQLiteDatabase database = SQLiteDatabase.openDatabase(
				"/data/data/com.itheima.safeguard/files/address.db", null,
				SQLiteDatabase.OPEN_READONLY);
		Cursor cursor = database.rawQuery(
				"select location from data2 where area = ?",
				new String[] { quHao });
		if (cursor.moveToNext()) {
			location = cursor.getString(0);
		}
		return location;
	}

	/**
	 * ����������,��ѯ�������Ĺ�����
	 * 
	 * @param phoneNum
	 *            �������
	 * @return ���ظ÷������Ĺ�����
	 */
	public String queryServiceTel(String phoneNum) {
		// �ݲ��ṩ�˷���
		return "��ʱδ��¼������������Ϣ";
	}

}
