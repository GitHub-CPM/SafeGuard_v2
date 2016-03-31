package com.itheima.safeguard.service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;

import com.android.internal.telephony.ITelephony;
import com.itheima.safeguard.dao.BlackDao;
import com.itheima.safeguard.domain.BlackTable;
import com.itheima.safeguard.domain.ContactsBean;

import android.R.integer;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.telephony.PhoneStateListener;
import android.telephony.SmsMessage;
import android.telephony.TelephonyManager;

/**
 * @author CPM
 * 
 *         ��������/�ֻ��Ĺ㲥,���غ�������Ķ���/�ֻ�
 */
public class BlackNumberService extends Service {

	private SmsBroadcastReceiver smsBroadcastReceiver;
	private TelephonyManager tm;
	private PhoneStateListener listener;
	private BlackDao blackDao;

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
		// �������������ݿ�ҵ����
		blackDao = new BlackDao(BlackNumberService.this);

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
		// ��ȡ�ֻ�������
		tm = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
		// �����ֻ�״̬������
		listener = new PhoneStateListener() {

			@Override
			public void onCallStateChanged(int state, String incomingNumber) {
				switch (state) {
				case TelephonyManager.CALL_STATE_IDLE:// ������
					// ��������
					break;
				case TelephonyManager.CALL_STATE_OFFHOOK:// ͨ����
					// ��������
					break;
				case TelephonyManager.CALL_STATE_RINGING:// ������
					// ���ж��Ƿ�Ϊ�������е�����
					int mode = blackDao.getMode(incomingNumber);
					if ((mode & BlackTable.TEL_MODE) != 0) {
						// ���ص绰
						abortPhone();
						// ɾ���绰��־
						deletCalllog(incomingNumber);
					}

					break;
				default:
					break;
				}
				super.onCallStateChanged(state, incomingNumber);
			}

		};
		// �����绰���е�״̬
		// ע�����
		tm.listen(listener, PhoneStateListener.LISTEN_CALL_STATE);

		super.onCreate();
	}

	/**
	 * ɾ���ú����ͨ����¼
	 * 
	 * @param incomingNumber
	 *            ͨ������
	 */
	protected void deletCalllog(final String incomingNumber) {
		// ���������ṩ�����жϵ绰��־�Ƿ��Ѿ�����
		getContentResolver().registerContentObserver(
				Uri.parse("content://call_log/calls"), true,
				new ContentObserver(new Handler()) {
					@Override
					public void onChange(boolean selfChange) {
						// ���������ṩ�ߵ�ɾ������,ɾ�������¼
						ContentResolver resolver = getContentResolver();
						Uri uri = Uri.parse("content://call_log/calls");
						resolver.delete(uri, "number =?",
								new String[] { incomingNumber });
						// ȡ��ע��۲���
						getContentResolver().unregisterContentObserver(this);
						
						super.onChange(selfChange);
					}
				});
	}

	/**
	 * �����ֻ�����ķ���
	 */
	protected void abortPhone() {
		// ͨ������,���ϵͳ�Ҷϵ绰�ķ���
		try {
			Class<?> clazz = Class.forName("android.os.ServiceManager");
			Method method = clazz.getDeclaredMethod("getService", String.class);
			IBinder binder = (IBinder) method.invoke(null,
					Context.TELEPHONY_SERVICE);
			ITelephony iTelephony = ITelephony.Stub.asInterface(binder);
			// ���ϵͳ��endCall����,�Ҷϵ绰
			iTelephony.endCall();
		} catch (ClassNotFoundException | NoSuchMethodException
				| IllegalAccessException | IllegalArgumentException
				| InvocationTargetException | RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void onDestroy() {
		// �رշ���,ֹͣ����
		// 1.�رն�������
		unregisterReceiver(smsBroadcastReceiver);

		// 2.�رյ绰����
		// ȡ������
		tm.listen(listener, PhoneStateListener.LISTEN_NONE);

		super.onDestroy();
	}

}
