package com.itheima.safeguard.engine;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.itheima.safeguard.domain.ContactsBean;

/**
 * @author CPM
 * 
 *         ��ȡ�ֻ���ϵ�˵�ҵ����
 */
public class ReadContactsEngine {

	/**
	 * ��ѯ���ŵļ�¼
	 * 
	 * @param context
	 * @return ���ض�����ϵ��¼��list
	 */
	public static List<ContactsBean> readSmslog(Context context) {
		// ����һ��arraylist,���ڷ�װ��ϵ��ʵ������
		List<ContactsBean> list = new ArrayList<>();

		Uri uri = Uri.parse("content://sms");
		Cursor cursor = context.getContentResolver().query(uri, new String[]{"address","person"}, null, null, " _id desc");
		while (cursor.moveToNext()) {
			ContactsBean bean = new ContactsBean();
			String phoneNumber = cursor.getString(0);
			String name = cursor.getString(1);
			bean.setName(name);
			bean.setPhoneNum(phoneNumber);
			list.add(bean);
		}
		cursor.close();
		return list; // ����һ���б�
	}
	
	/**��ͨ����¼����,����ֵ
	 * @param context
	 * @return
	 */
	public static List<ContactsBean> readPhoneLog(Context context) {
		List<ContactsBean> list = new ArrayList<>();
		// ͨ�������ṩ�߻���ֻ�ͨ����¼
		Uri uri = Uri.parse("content://call_log/calls");
		Cursor cursor = context.getContentResolver().query(uri,new String[]{"name","number"}, null, null, " _id desc");
		while (cursor.moveToNext()) {
			ContactsBean bean = new ContactsBean();
			bean.setName(cursor.getString(0));
			bean.setPhoneNum(cursor.getString(1));
			list.add(bean);
		}
		cursor.close();
		return list;
	}
	
	

	/**
	 * ��̬����,ͨ���ֻ���ϵ�˵������ṩ�߶�ȡ�ֻ���ϵ�˵���Ϣ,����һ��list,�����װ�˸�����ϵ�˵�ʵ��
	 * 
	 * @param context
	 *            ����������,֪�������ĸ�ҳ�浱����ʾ
	 */
	public static List<ContactsBean> readContacts(Context context) {
		// ����һ��arraylist,���ڷ�װ��ϵ��ʵ������
		List<ContactsBean> list = new ArrayList<>();

		// ͨ��ǰ׺���׺,�����������ṩ�ߵ���ϵ
		Uri uriContacts = Uri.parse("content://com.android.contacts/contacts");
		Uri uriData = Uri.parse("content://com.android.contacts/data");

		// �ҵ���ϵ�����ݿ�Ҳ����contacts.db�����contacts�����,���������"_id"����е�����ֵ
		Cursor cursor1 = context.getContentResolver().query(uriContacts,
				new String[] { "_id" }, null, null, null);
		// ���������
		while (cursor1.moveToNext()) {
			String id = cursor1.getString(0);// ����id��
			ContactsBean contact = new ContactsBean();// ������װ��ϵ����Ϣ��ʵ������

			// ����id����,��ȡ��ÿ����ϵ�˵������Ϣ
			Cursor cursor2 = context.getContentResolver().query(uriData,
					new String[] { "data1", "mimetype" }, "raw_contact_id = ?",
					new String[] { id }, null);
			// ����������
			while (cursor2.moveToNext()) {
				String data = cursor2.getString(0);// ��ϵ�˵�����/�绰����/�ʼ��ȵ�
				String mimetype = cursor2.getString(1);// ��ӦҪ��ѯ������/�绰��/�ʼ��ȵ���Ӧ�Ĵ����

				if (mimetype.equals("vnd.android.cursor.item/name")) {// ��ϵ�˵�����,�����װ����ϵ��ʵ������
					contact.setName(data);
				} else if (mimetype.equals("vnd.android.cursor.item/phone_v2")) {// ��ϵ�˵ĵ绰����,�����װ����ϵ��ʵ������
					contact.setPhoneNum(data);
				}
			}
			cursor2.close();// �ر��α�,�ͷ���Դ
			list.add(contact); // ����ϵ����Ϣʵ��������ӵ�list��
		}
		cursor1.close();// �ر��α�,�ͷ���Դ
		return list; // ����һ���б�
	}

}
