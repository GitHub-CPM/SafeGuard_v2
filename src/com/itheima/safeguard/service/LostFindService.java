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
			System.out.println("监听到了");
			Log.i("System.out", "监听到了1");
			// 当监听到了短信消息,就发送给安全号码,并且不让其显示在手机上.
			Bundle extras = intent.getExtras();
			Object[] datas = (Object[]) extras.get("pdus");
			for (Object data : datas) { // 这个data是一个字节数组
				// 通过smsmessage解析data,获得短信
				SmsMessage message = SmsMessage.createFromPdu((byte[]) data);
				String address = message.getOriginatingAddress(); // 发送者号码
				String messageBody = message.getMessageBody();// 短信内容
				System.out.println(messageBody + " : " + address);
				Log.i("System.out", "监听到了2");
				// 监听信息的内容,如果信息内容有特殊含义,如
				// #*gps*# 激活GPS定位功能
				// #*lockscreen*# 远程锁屏
				// #*wipedata*#  远程清除数据
				// #*music*#  音乐报警
				if(messageBody.equals("#*gps*#")) {
					Log.i("System.out", "监听到了3");
					// 启动gps后台服务,监听位置变化,并发送具体经纬度到安全号码上
					Intent gpsIntent = new Intent(context,GPSService.class);
					startService(gpsIntent);
					abortBroadcast();// 终止广播
				} else if(messageBody.equals("#*lockscreen*#")) {
					// 远程锁屏
					// 获得设备管理器
					DevicePolicyManager dpm = (DevicePolicyManager) getSystemService(Service.DEVICE_POLICY_SERVICE);
					dpm.resetPassword("222", 0); // 重设密码
					dpm.lockNow(); // 一键锁屏
					abortBroadcast();// 终止广播
				}
				
			}
		}
		
	}
	
	@Override
	public void onCreate() {
		smsReceiver = new SMSReceiver();
		IntentFilter filter = new IntentFilter("android.provider.Telephony.SMS_RECEIVED");//意图为对短信接收进行监听
		filter.setPriority(Integer.MAX_VALUE);//优先级设计为最高级别
		registerReceiver(smsReceiver, filter); // 注册接收者
		super.onCreate();
	}
	
	@Override
	public void onDestroy() {
		unregisterReceiver(smsReceiver);// 销毁短信接收者
		super.onDestroy();
	}

}
