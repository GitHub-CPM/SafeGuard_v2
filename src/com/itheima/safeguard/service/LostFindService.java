package com.itheima.safeguard.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.IBinder;
import android.telephony.SmsMessage;

/**
 * @author CPM
 *
 *	����һ�����ڿ����ֻ������������,���ں�̨����������������,�����Ų�������,��ʽ��sdCard�Ȳ���.
 */
public class LostFindService extends Service {

	private SMSReceiver smsReceiver; // �Զ���Ķ��Ž�����

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	
	/**
	 * @author CPM
	 *
	 *	�Զ���Ķ��Ź㲥������
	 */
	private class SMSReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			// ���������˶�����Ϣ,�ͷ��͸���ȫ����,���Ҳ�������ʾ���ֻ���.
			Bundle extras = intent.getExtras();
			Object[] datas = (Object[]) extras.get("pdus");
			for (Object data : datas) { // ���data��һ���ֽ�����
				// ͨ��smsmessage����data,��ö���
				SmsMessage message = SmsMessage.createFromPdu((byte[]) data);
				String address = message.getOriginatingAddress(); // �����ߺ���
				String messageBody = message.getMessageBody();// ��������
			}
		}
		
	}
	
	@Override
	public void onCreate() {
		super.onCreate();
		smsReceiver = new SMSReceiver();
		IntentFilter filter = new IntentFilter("android.provider.Telephony.SMS_RECEIVED");//��ͼΪ�Զ��Ž��ս��м���
		filter.setPriority(Integer.MAX_VALUE);//���ȼ����Ϊ��߼���
		registerReceiver(smsReceiver, filter); // ע�������
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		unregisterReceiver(smsReceiver);// ���ٶ��Ž�����
	}

}
