package com.itheima.safeguard.receiver;

import com.itheima.safeguard.utils.MyConstants;
import com.itheima.safeguard.utils.SPTools;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.telephony.TelephonyManager;

/**
 * @author CPM
 * 
 *         �Ӱ�װ��SafeGuard֮��,�Ϳ�ʼ�����ֻ������� sim����Ϣ�ı仯,���иı�,���Ͷ���ȥ��ȫ����
 *         ʹ��ǰ,����Ҫ���嵥�ļ���̬ע��
 */
public class BootReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		// ������ɺ�,����ֻ�SIM�����к���֮ǰ�洢�����к��Ƿ�ͬ
		String oldSIM = SPTools.getString(context, MyConstants.SIM, "");

		// ����ֻ�������,Ϊ�˲鿴������ĵ�ǰSIM����Ϣ
		TelephonyManager tm = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		String newSIM = tm.getSimSerialNumber(); // ��õ�ǰSIM�����к�

		if (!oldSIM.equals(newSIM)) { // ���κ��벻һ��,�����SIM��������.
			// ���Ͷ��ŵ���ȫ����,����Ϊ�ֻ�SIM������.
			SmsManager sm = SmsManager.getDefault(); // ��ö��Ź�����
			sm.sendTextMessage(
					SPTools.getString(context, MyConstants.SAFENUMBER, ""),
					null, "�ֻ�SIM��������!", null, null);
		}

	}
}
