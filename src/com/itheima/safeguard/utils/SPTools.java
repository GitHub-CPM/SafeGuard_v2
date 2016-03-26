package com.itheima.safeguard.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * @author CPM
 * 
 *         �������ݵ�SharedPreference�еĹ�����
 */
public class SPTools {

	// ����Ǵ洢sharedpreference�ķ���1
	public static void putString(Context context, String key, String value) {
		SharedPreferences sp = context.getSharedPreferences(MyConstants.SPFILE,
				Context.MODE_PRIVATE);
		Editor editor = sp.edit();
		editor.putString(key, value);
		editor.commit();
	}

	// �������ȡsharedpreference�ķ���1
	public static String getString(Context context, String key, String defValue) {
		SharedPreferences sp = context.getSharedPreferences(MyConstants.SPFILE,
				Context.MODE_PRIVATE);
		return sp.getString(key, defValue);
	}

	// ����Ǵ洢sharedpreference�ķ���2
	public static void putBoolean(Context context, String key, Boolean value) {
		SharedPreferences sp = context.getSharedPreferences(MyConstants.SPFILE,
				Context.MODE_PRIVATE);
		Editor editor = sp.edit();
		editor.putBoolean(key, value);
		editor.commit();
	}

	// �������ȡsharedpreference�ķ���2
	public static Boolean getBoolean(Context context, String key, Boolean defValue) {
		SharedPreferences sp = context.getSharedPreferences(MyConstants.SPFILE,
				Context.MODE_PRIVATE);
		return sp.getBoolean(key, defValue);
	}

}
