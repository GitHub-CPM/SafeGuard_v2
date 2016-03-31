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
 *         监听短信/手机的广播,拦截黑名单里的短信/手机
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
	 *         监听短信广播
	 */
	private class SmsBroadcastReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			// 当接收到广播的时候
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
		// 创建黑名单数据库业务类
		blackDao = new BlackDao(BlackNumberService.this);

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
		// 获取手机管理者
		tm = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
		// 创建手机状态监听者
		listener = new PhoneStateListener() {

			@Override
			public void onCallStateChanged(int state, String incomingNumber) {
				switch (state) {
				case TelephonyManager.CALL_STATE_IDLE:// 空闲中
					// 不做处理
					break;
				case TelephonyManager.CALL_STATE_OFFHOOK:// 通话中
					// 不作处理
					break;
				case TelephonyManager.CALL_STATE_RINGING:// 响铃中
					// 先判断是否为黑名单中的拦截
					int mode = blackDao.getMode(incomingNumber);
					if ((mode & BlackTable.TEL_MODE) != 0) {
						// 拦截电话
						abortPhone();
						// 删除电话日志
						deletCalllog(incomingNumber);
					}

					break;
				default:
					break;
				}
				super.onCallStateChanged(state, incomingNumber);
			}

		};
		// 监听电话呼叫的状态
		// 注册监听
		tm.listen(listener, PhoneStateListener.LISTEN_CALL_STATE);

		super.onCreate();
	}

	/**
	 * 删除该号码的通话记录
	 * 
	 * @param incomingNumber
	 *            通话号码
	 */
	protected void deletCalllog(final String incomingNumber) {
		// 根据内容提供者来判断电话日志是否已经生成
		getContentResolver().registerContentObserver(
				Uri.parse("content://call_log/calls"), true,
				new ContentObserver(new Handler()) {
					@Override
					public void onChange(boolean selfChange) {
						// 根据内容提供者的删除方法,删除来电记录
						ContentResolver resolver = getContentResolver();
						Uri uri = Uri.parse("content://call_log/calls");
						resolver.delete(uri, "number =?",
								new String[] { incomingNumber });
						// 取消注册观察者
						getContentResolver().unregisterContentObserver(this);
						
						super.onChange(selfChange);
					}
				});
	}

	/**
	 * 拦截手机进入的方法
	 */
	protected void abortPhone() {
		// 通过反射,获得系统挂断电话的方法
		try {
			Class<?> clazz = Class.forName("android.os.ServiceManager");
			Method method = clazz.getDeclaredMethod("getService", String.class);
			IBinder binder = (IBinder) method.invoke(null,
					Context.TELEPHONY_SERVICE);
			ITelephony iTelephony = ITelephony.Stub.asInterface(binder);
			// 获得系统的endCall方法,挂断电话
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
		// 关闭服务,停止监听
		// 1.关闭短信拦截
		unregisterReceiver(smsBroadcastReceiver);

		// 2.关闭电话拦截
		// 取消监听
		tm.listen(listener, PhoneStateListener.LISTEN_NONE);

		super.onDestroy();
	}

}
