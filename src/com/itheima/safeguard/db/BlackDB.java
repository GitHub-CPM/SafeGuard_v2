package com.itheima.safeguard.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * @author CPM
 * 
 *         c创建应用自己的数据库,用以保存被列入黑名单的数据信息
 */
public class BlackDB extends SQLiteOpenHelper {

	public BlackDB(Context context) {
		super(context, "black.db", null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// 创建表
		db.execSQL("create table blacktb(_id integer primary key autoincrement,phone text,mode integer)");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// 版本升级,重新创建新表
		db.execSQL("drop table blacktb");
		onCreate(db);
	}

}
