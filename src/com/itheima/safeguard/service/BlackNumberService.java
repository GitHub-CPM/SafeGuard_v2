package com.itheima.safeguard.service;

import com.itheima.safeguard.dao.BlackDao;
import com.itheima.safeguard.domain.BlackTable;
import com.itheima.safeguard.domain.ContactsBean;

import android.R.integer;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.telephony.SmsMessage;

/**
 * @author CPM
 * 
 *         ��������/�ֻ��Ĺ㲥,���غ�������Ķ���/�ֻ�
 */
public class BlackNumberService extends Service {

	private SmsBroadcastReceiver smsBroadcastReceiver;

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	/**
	 * @author CPM
	 * 
	 *         �������Ź㲥
	 */
	private class SmsBroadcastReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			// �����յ��㲥��ʱ��
			// �������������ݿ�ҵ����
			BlackDao blackDao = new BlackDao(context);
			// ��intent������������
			Object[] object = (Object[]) intent.getExtras().get("pdus");
			// ��������
			for (Object b : object) {
				// ������smsmessage����
				SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) b);
				// ��÷����˵�ַ,Ҳ���Ǻ���
				String address = smsMessage.getOriginatingAddress();
				// ����ҵ�����ѯ���ֻ����������ģʽ��ʲô
				int mode = blackDao.getMode(address);
				if ((mode & BlackTable.SMS_MODE) != 0) { // ���������
					// ���жϹ㲥
					abortBroadcast();
				}// ���򲻴���
			}
		}

	}

	@Override
	public void onCreate() {
		// ��������,�����㲥,��̬ע��㲥
		// 1.��������
		// 1)ʵ�����㲥������
		smsBroadcastReceiver = new SmsBroadcastReceiver();
		// 2)������ͼΪ�������ŵĽ���
		IntentFilter smsIntentFilter = new IntentFilter(
				"android.provider.Telephony.SMS_RECEIVED");
		// 3)���ù�����ͼΪ���Ȩ��,���һ�����յ���������
		smsIntentFilter.setPriority(Integer.MAX_VALUE);
		// 4)ע��㲥������
		registerReceiver(smsBroadcastReceiver, smsIntentFilter);

		// 2.�绰����

		super.onCreate();
	}

	@Override
	public void onDestroy() {
		// �رշ���,ֹͣ����
		// 1.�رն�������
		unregisterReceiver(smsBroadcastReceiver);

		// 2.�رյ绰����

		super.onDestroy();
	}

}
