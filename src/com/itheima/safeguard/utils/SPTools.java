package com.itheima.safeguard.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * @author CPM
 * 
 *         保存数据到SharedPreference中的工具类
 */
public class SPTools {

	// 这个是存储sharedpreference的方法1
	public static void putString(Context context, String key, String value) {
		SharedPreferences sp = context.getSharedPreferences(MyConstants.SPFILE,
				Context.MODE_PRIVATE);
		Editor editor = sp.edit();
		editor.putString(key, value);
		editor.commit();
	}

	// 这个是提取sharedpreference的方法1
	public static String getString(Context context, String key, String defValue) {
		SharedPreferences sp = context.getSharedPreferences(MyConstants.SPFILE,
				Context.MODE_PRIVATE);
		return sp.getString(key, defValue);
	}

	// 这个是存储sharedpreference的方法2
	public static void putBoolean(Context context, String key, Boolean value) {
		SharedPreferences sp = context.getSharedPreferences(MyConstants.SPFILE,
				Context.MODE_PRIVATE);
		Editor editor = sp.edit();
		editor.putBoolean(key, value);
		editor.commit();
	}

	// 这个是提取sharedpreference的方法2
	public static Boolean getBoolean(Context context, String key, Boolean defValue) {
		SharedPreferences sp = context.getSharedPreferences(MyConstants.SPFILE,
				Context.MODE_PRIVATE);
		return sp.getBoolean(key, defValue);
	}

}
