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
 *         ������Ҫ�����ݿ���в���,��˽������ݿ������֮���dao��,��װ������Ϣ ר�Ŵ���ҵ���߼�
 */
public class BlackDao {

	BlackDB db;

	public BlackDao(Context context) {
		this.db = new BlackDB(context);
	}

	/**
	 * @param startIndex
	 *            ��ʼ��ѯ��λ��
	 * @param quantityPerPage
	 *            ÿһҳ��ʾ����Ŀ����
	 * @return ��װ������һҳΪ��ʼ,һҳ����Ŀ����������ʵ���list(�������һҳ������,���ʣ���ȫ����ʾ)
	 */
	public List<BlackBean> getMoreDatas(int startIndex, int quantityPerPage) {
		List<BlackBean> list = new ArrayList<>();
		SQLiteDatabase database = db.getReadableDatabase();
		Cursor cursor = database.rawQuery("select " + BlackTable.PHONE + ","
				+ BlackTable.MODE + " from " + BlackTable.BLACKTABLE
				+ " limit ?,? ", new String[] { startIndex + "",
				quantityPerPage + "" });
		// �����α�
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
	 * ��ȡ���к�����������
	 * 
	 * @return ���غ����������б�
	 */
	public List<BlackBean> findAllDatas() {
		List<BlackBean> list = new ArrayList<>();
		// ���ֻ�������ݿ�
		SQLiteDatabase database = db.getReadableDatabase();
		// ��ѯ���ݿ�,����α�
		Cursor cursor = database.rawQuery("select " + BlackTable.PHONE + ","
				+ BlackTable.MODE + " from " + BlackTable.BLACKTABLE, null);
		// �����α�,��ȡ����,�����ݷ�װ��blackbean��
		while (cursor.moveToNext()) {
			BlackBean blackBean = new BlackBean(); // ��������
			blackBean.setPhone(cursor.getString(0)); // ����һ�е��ֻ���Ϣ���ȥ
			blackBean.setMode(cursor.getInt(1)); // ���ڶ��е�������Ϣ���ȥ
			list.add(blackBean);
		}
		cursor.close(); // �ر��α�
		database.close(); // �ر����ݿ�
		return list;
	}

	/**
	 * ������Ӻ����������ݿ�
	 * 
	 * @param phone
	 *            ��ӵ��������ĺ���
	 * @param mode
	 *            ��ӵ�����ģʽ
	 */
	public void add(String phone, int mode) {
		// ��ȡ��д�����ݿ�
		SQLiteDatabase database = db.getWritableDatabase();

		// ����һ���б�,����Ҫ��ӵĺ��������������ģʽ�ӽ�ȥ
		ContentValues values = new ContentValues();
		values.put(BlackTable.PHONE, phone);
		values.put(BlackTable.MODE, mode);

		// �������ݵ����ݿ�
		database.insert(BlackTable.BLACKTABLE, null, values);
		database.close(); // �ر����ݿ�
	}

	/**
	 * �������Ӻ��������ݵķ���
	 * 
	 * @param bean
	 *            ��װ��������Ϣ��bean
	 */
	public void add(BlackBean bean) {
		add(bean.getPhone(), bean.getMode());
	}

	/**
	 * ����ɾ������Ӻ������еĺ���
	 * 
	 * @param phoen
	 *            ��Ҫɾ���ĺ���
	 */
	public void delete(String phone) {
		SQLiteDatabase database = db.getWritableDatabase();
		// ɾ������
		database.delete(BlackTable.BLACKTABLE, BlackTable.PHONE + "=?",
				new String[] { phone });
		database.close();
	}

	/**
	 * �޸ĺ������ֻ�������ģʽ
	 * 
	 * @param phone
	 *            �޸ĵ��ֻ�����
	 * @param mode
	 *            �޸ĵ�����ģʽ
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
