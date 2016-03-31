package com.itheima.safeguard.engine;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.Context;
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
	public String queryLocation(String phoneNum,Context context) {
		String location = phoneNum;
		Pattern p = Pattern.compile("1{1}[3857]{1}[0-9]{9}");
		Matcher m = p.matcher(phoneNum);
		boolean b = m.matches();
		if (b) {
			// 是手机号
			location = queryMobil(phoneNum, context);
		} else if (phoneNum.length() >= 11) {
			// 固定号码
			// 如果是固定号码
			location = queryTel(phoneNum, context);
		} else {

			// 如果是服务号码
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
	public String queryMobil(String phoneNum,Context context) {
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
						"select location from data2 where id = (select outKey from data1 where id=?)",
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
	public String queryTel(String phoneNum,Context context) {
		String location = phoneNum;
		String quHao = "000000000";
		if (phoneNum.charAt(1) == '1' || phoneNum.charAt(1) == '2') {
			// 2位区号
			quHao = phoneNum.substring(1, 3);
		} else {
			// 3位区号
			quHao = phoneNum.substring(1, 4);
		}
		SQLiteDatabase database = SQLiteDatabase.openDatabase(
				"/data/data/com.itheima.safeguard/files/address.db", null,
				SQLiteDatabase.OPEN_READONLY);
		Cursor cursor = database.rawQuery(
				"select location from data2 where area=?",
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
