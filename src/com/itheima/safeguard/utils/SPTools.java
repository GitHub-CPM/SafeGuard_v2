package com.itheima.safeguard.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * @author CPM
 *
 *�������ݵ�SharedPreference�еĹ�����
 */
public class SPTools {

	// ����Ǵ洢sharedpreference�ķ���
	public static void putString(Context context,String key,String value) {
		SharedPreferences sp = context.getSharedPreferences(MyConstants.SPFILE, Context.MODE_PRIVATE);
		Editor editor = sp.edit();
		editor.putString(key, value);
		editor.commit();
	}
	
	// �������ȡsharedpreference�ķ���
	public static String getString(Context context,String key,String defValue) {
		SharedPreferences sp = context.getSharedPreferences(MyConstants.SPFILE, Context.MODE_PRIVATE);
		return sp.getString(key, defValue);
	}
	
}
