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
 *         监听短信/手机的广播,拦截黑名单里的短信/手机
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
	 *         监听短信广播
	 */
	private class SmsBroadcastReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			// 当接收到广播的时候
			// 创建黑名单数据库业务类
			BlackDao blackDao = new BlackDao(context);
			// 从intent里获得数据数组
			Object[] object = (Object[]) intent.getExtras().get("pdus");
			// 遍历数组
			for (Object b : object) {
				// 解析成smsmessage对象
				SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) b);
				// 获得发件人地址,也就是号码
				String address = smsMessage.getOriginatingAddress();
				// 调用业务类查询该手机号码的拦截模式是什么
				int mode = blackDao.getMode(address);
				if ((mode & BlackTable.SMS_MODE) != 0) { // 如果拦截了
					// 则中断广播
					abortBroadcast();
				}// 否则不处理
			}
		}

	}

	@Override
	public void onCreate() {
		// 开启服务,监听广播,动态注册广播
		// 1.短信拦截
		// 1)实例化广播接收者
		smsBroadcastReceiver = new SmsBroadcastReceiver();
		// 2)创建意图为监听短信的接收
		IntentFilter smsIntentFilter = new IntentFilter(
				"android.provider.Telephony.SMS_RECEIVED");
		// 3)设置过滤意图为最大权限,则第一个接收到该条短信
		smsIntentFilter.setPriority(Integer.MAX_VALUE);
		// 4)注册广播接收者
		registerReceiver(smsBroadcastReceiver, smsIntentFilter);

		// 2.电话拦截

		super.onCreate();
	}

	@Override
	public void onDestroy() {
		// 关闭服务,停止监听
		// 1.关闭短信拦截
		unregisterReceiver(smsBroadcastReceiver);

		// 2.关闭电话拦截

		super.onDestroy();
	}

}
