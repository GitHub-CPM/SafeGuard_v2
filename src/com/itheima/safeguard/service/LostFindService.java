package com.itheima.safeguard.service;

import android.app.Service;
import android.app.admin.DevicePolicyManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.IBinder;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

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
			System.out.println("��������");
			Log.i("System.out", "��������1");
			// ���������˶�����Ϣ,�ͷ��͸���ȫ����,���Ҳ�������ʾ���ֻ���.
			Bundle extras = intent.getExtras();
			Object[] datas = (Object[]) extras.get("pdus");
			for (Object data : datas) { // ���data��һ���ֽ�����
				// ͨ��smsmessage����data,��ö���
				SmsMessage message = SmsMessage.createFromPdu((byte[]) data);
				String address = message.getOriginatingAddress(); // �����ߺ���
				String messageBody = message.getMessageBody();// ��������
				System.out.println(messageBody + " : " + address);
				Log.i("System.out", "��������2");
				// ������Ϣ������,�����Ϣ���������⺬��,��
				// #*gps*# ����GPS��λ����
				// #*lockscreen*# Զ������
				// #*wipedata*#  Զ���������
				// #*music*#  ���ֱ���
				if(messageBody.equals("#*gps*#")) {
					Log.i("System.out", "��������3");
					// ����gps��̨����,����λ�ñ仯,�����;��徭γ�ȵ���ȫ������
					Intent gpsIntent = new Intent(context,GPSService.class);
					startService(gpsIntent);
					abortBroadcast();// ��ֹ�㲥
				} else if(messageBody.equals("#*lockscreen*#")) {
					// Զ������
					// ����豸������
					DevicePolicyManager dpm = (DevicePolicyManager) getSystemService(Service.DEVICE_POLICY_SERVICE);
					dpm.resetPassword("222", 0); // ��������
					dpm.lockNow(); // һ������
					abortBroadcast();// ��ֹ�㲥
				}
				
			}
		}
		
	}
	
	@Override
	public void onCreate() {
		smsReceiver = new SMSReceiver();
		IntentFilter filter = new IntentFilter("android.provider.Telephony.SMS_RECEIVED");//��ͼΪ�Զ��Ž��ս��м���
		filter.setPriority(Integer.MAX_VALUE);//���ȼ����Ϊ��߼���
		registerReceiver(smsReceiver, filter); // ע�������
		super.onCreate();
	}
	
	@Override
	public void onDestroy() {
		unregisterReceiver(smsReceiver);// ���ٶ��Ž�����
		super.onDestroy();
	}

}
