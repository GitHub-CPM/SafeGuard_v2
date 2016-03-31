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
 *         读取手机联系人的业务类
 */
public class ReadContactsEngine {

	/**
	 * 查询短信的记录
	 * 
	 * @param context
	 * @return 返回短信联系记录的list
	 */
	public static List<ContactsBean> readSmslog(Context context) {
		// 创建一个arraylist,用于封装联系人实例对象
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
		return list; // 返回一个列表
	}
	
	/**从通话记录查找,返回值
	 * @param context
	 * @return
	 */
	public static List<ContactsBean> readPhoneLog(Context context) {
		List<ContactsBean> list = new ArrayList<>();
		// 通过内容提供者获得手机通话记录
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
	 * 静态方法,通过手机联系人的内容提供者读取手机联系人的信息,返回一个list,里面封装了各个联系人的实例
	 * 
	 * @param context
	 *            传递上下文,知道是在哪个页面当中显示
	 */
	public static List<ContactsBean> readContacts(Context context) {
		// 创建一个arraylist,用于封装联系人实例对象
		List<ContactsBean> list = new ArrayList<>();

		// 通过前缀与后缀,建立与内容提供者的联系
		Uri uriContacts = Uri.parse("content://com.android.contacts/contacts");
		Uri uriData = Uri.parse("content://com.android.contacts/data");

		// 找到联系人数据库也就是contacts.db里面的contacts这个表,查找里面的"_id"这个列的所有值
		Cursor cursor1 = context.getContentResolver().query(uriContacts,
				new String[] { "_id" }, null, null, null);
		// 遍历这个列
		while (cursor1.moveToNext()) {
			String id = cursor1.getString(0);// 所有id号
			ContactsBean contact = new ContactsBean();// 创建封装联系人信息的实例对象

			// 根据id号码,获取到每个联系人的相关信息
			Cursor cursor2 = context.getContentResolver().query(uriData,
					new String[] { "data1", "mimetype" }, "raw_contact_id = ?",
					new String[] { id }, null);
			// 遍历这两列
			while (cursor2.moveToNext()) {
				String data = cursor2.getString(0);// 联系人的姓名/电话号码/邮件等等
				String mimetype = cursor2.getString(1);// 对应要查询的姓名/电话号/邮件等的相应的代码号

				if (mimetype.equals("vnd.android.cursor.item/name")) {// 联系人的姓名,将其封装在联系人实体类里
					contact.setName(data);
				} else if (mimetype.equals("vnd.android.cursor.item/phone_v2")) {// 联系人的电话号码,将其封装在联系人实体类里
					contact.setPhoneNum(data);
				}
			}
			cursor2.close();// 关闭游标,释放资源
			list.add(contact); // 把联系人信息实例对象添加到list中
		}
		cursor1.close();// 关闭游标,释放资源
		return list; // 返回一个列表
	}

}
