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
