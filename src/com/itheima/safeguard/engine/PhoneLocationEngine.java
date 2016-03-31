package com.itheima.safeguard.engine;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * @author CPM
 * 
 *         手机号码归属地的业务类,提供查询的方法
 */
public class PhoneLocationEngine {

	/**
	 * 查询电话号码归属地
	 * 
	 * @param phoneNum
	 *            要查询的号码
	 * @return 电话号码归属地
	 */
	public String queryLocation(String phoneNum) {
		// 默认为本号码
		String location = phoneNum;
		// 根据各个电话号码性质区分
		if (phoneNum.length() == 11 && phoneNum.startsWith("1")) { // 手机号码
			location = queryMobil(phoneNum);
		} else if ((phoneNum.length() == 11 || phoneNum.length() == 12)
				&& phoneNum.startsWith("0")) { // 固定电话
			location = queryTel(phoneNum);
		} else { // 服务电话
			location = queryServiceTel(phoneNum);
		}
		return location;
	}

	/**
	 * 传入手机号码,查询手机号码的归属地
	 * 
	 * @param phoneNum
	 *            手机号码
	 * @return 返回该手机号码的归属地
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
	 * 传入固定电话号码,查询固定电话的归属地
	 * 
	 * @param phoneNum
	 *            固定电话
	 * @return 返回该固定电话的归属地
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
	 * 传入服务号码,查询服务号码的归属地
	 * 
	 * @param phoneNum
	 *            服务号码
	 * @return 返回该服务号码的归属地
	 */
	public String queryServiceTel(String phoneNum) {
		// 暂不提供此方法
		return "暂时未收录请号码归属地信息";
	}

}
