package com.itheima.safeguard.dao;

import java.util.ArrayList;
import java.util.List;

import android.R.integer;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.itheima.safeguard.db.BlackDB;
import com.itheima.safeguard.domain.BlackBean;
import com.itheima.safeguard.domain.BlackTable;

/**
 * @author CPM
 * 
 *         由于需要对数据库进行操作,因此建立数据库与操作之间的dao类,封装操作信息 专门处理业务逻辑
 */
public class BlackDao {

	BlackDB db;

	public BlackDao(Context context) {
		this.db = new BlackDB(context);
	}

	/**
	 * @param startIndex
	 *            开始查询的位置
	 * @param quantityPerPage
	 *            每一页显示的条目数量
	 * @return 封装了以下一页为开始,一页的条目数量黑名单实体的list(如果不足一页的数量,则把剩余的全部显示)
	 */
	public List<BlackBean> getMoreDatas(int startIndex, int quantityPerPage) {
		List<BlackBean> list = new ArrayList<>();
		SQLiteDatabase database = db.getReadableDatabase();
		Cursor cursor = database.rawQuery("select " + BlackTable.PHONE + ","
				+ BlackTable.MODE + " from " + BlackTable.BLACKTABLE
				+ " limit ?,? ", new String[] { startIndex + "",
				quantityPerPage + "" });
		// 遍历游标
		while (cursor.moveToNext()) {
			BlackBean blackBean = new BlackBean();
			String phone = cursor.getString(0);
			int mode = cursor.getInt(1);
			blackBean.setPhone(phone);
			blackBean.setMode(mode);
			list.add(blackBean);
		}
		return list;
	}

	/**
	 * 获取所有黑名单的数据
	 * 
	 * @return 返回黑名单数据列表
	 */
	public List<BlackBean> findAllDatas() {
		List<BlackBean> list = new ArrayList<>();
		// 获得只读的数据库
		SQLiteDatabase database = db.getReadableDatabase();
		// 查询数据库,获得游标
		Cursor cursor = database.rawQuery("select " + BlackTable.PHONE + ","
				+ BlackTable.MODE + " from " + BlackTable.BLACKTABLE, null);
		// 遍历游标,获取数据,将数据封装在blackbean内
		while (cursor.moveToNext()) {
			BlackBean blackBean = new BlackBean(); // 创建对象
			blackBean.setPhone(cursor.getString(0)); // 将第一列的手机信息存进去
			blackBean.setMode(cursor.getInt(1)); // 将第二列的拦截信息存进去
			list.add(blackBean);
		}
		cursor.close(); // 关闭游标
		database.close(); // 关闭数据库
		return list;
	}

	/**
	 * 用以添加黑名单到数据库
	 * 
	 * @param phone
	 *            添加到黑名单的号码
	 * @param mode
	 *            添加的拦截模式
	 */
	public void add(String phone, int mode) {
		// 获取可写的数据库
		SQLiteDatabase database = db.getWritableDatabase();

		// 创建一个列表,把需要添加的黑名单号码和拦截模式加进去
		ContentValues values = new ContentValues();
		values.put(BlackTable.PHONE, phone);
		values.put(BlackTable.MODE, mode);

		// 插入数据到数据库
		database.insert(BlackTable.BLACKTABLE, null, values);
		database.close(); // 关闭数据库
	}

	/**
	 * 重载增加黑名单内容的方法
	 * 
	 * @param bean
	 *            封装黑名单信息的bean
	 */
	public void add(BlackBean bean) {
		add(bean.getPhone(), bean.getMode());
	}

	/**
	 * 用以删除被添加黑名单中的号码
	 * 
	 * @param phoen
	 *            需要删除的号码
	 */
	public void delete(String phone) {
		SQLiteDatabase database = db.getWritableDatabase();
		// 删除号码
		database.delete(BlackTable.BLACKTABLE, BlackTable.PHONE + "=?",
				new String[] { phone });
		database.close();
	}

	/**
	 * 修改黑名单手机的拦截模式
	 * 
	 * @param phone
	 *            修改的手机号码
	 * @param mode
	 *            修改的拦截模式
	 */
	public void update(String phone, int mode) {
		SQLiteDatabase database = db.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(BlackTable.MODE, mode);
		database.update(BlackTable.BLACKTABLE, values, BlackTable.PHONE + "=?",
				new String[] { phone });
		database.close();
	}

}
