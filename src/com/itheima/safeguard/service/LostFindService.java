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
 *	这是一个用于开启手机防盗服务的类,用于后台开启监听短信内容,发短信播放音乐,格式化sdCard等操作.
 */
public class LostFindService extends Service {

	private SMSReceiver smsReceiver; // 自定义的短信接收者

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	
	/**
	 * @author CPM
	 *
	 *	自定义的短信广播接收者
	 */
	private class SMSReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			// 当监听到了短信消息,就发送给安全号码,并且不让其显示在手机上.
			Bundle extras = intent.getExtras();
			Object[] datas = (Object[]) extras.get("pdus");
			for (Object data : datas) { // 这个data是一个字节数组
				// 通过smsmessage解析data,获得短信
				SmsMessage message = SmsMessage.createFromPdu((byte[]) data);
				String address = message.getOriginatingAddress(); // 发送者号码
				String messageBody = message.getMessageBody();// 短信内容
			}
		}
		
	}
	
	@Override
	public void onCreate() {
		super.onCreate();
		smsReceiver = new SMSReceiver();
		IntentFilter filter = new IntentFilter("android.provider.Telephony.SMS_RECEIVED");//意图为对短信接收进行监听
		filter.setPriority(Integer.MAX_VALUE);//优先级设计为最高级别
		registerReceiver(smsReceiver, filter); // 注册接收者
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		unregisterReceiver(smsReceiver);// 销毁短信接收者
	}

}
