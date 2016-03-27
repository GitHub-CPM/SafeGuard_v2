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
 *         从安装完SafeGuard之后,就开始监听手机开机后 sim卡信息的变化,如有改变,则发送短信去安全号码
 *         使用前,必须要在清单文件静态注册
 */
public class BootReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		// 开机完成后,监测手机SIM卡序列号与之前存储的序列号是否不同
		String oldSIM = SPTools.getString(context, MyConstants.SIM, "");

		// 获得手机管理者,为了查看开机后的当前SIM的信息
		TelephonyManager tm = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		String newSIM = tm.getSimSerialNumber(); // 获得当前SIM卡序列号

		if (!oldSIM.equals(newSIM)) { // 两次号码不一致,则表明SIM卡被换了.
			// 发送短信到安全号码,内容为手机SIM被换了.
			SmsManager sm = SmsManager.getDefault(); // 获得短信管理者
			sm.sendTextMessage(
					SPTools.getString(context, MyConstants.SAFENUMBER, ""),
					null, "手机SIM被更换了!", null, null);
		}

	}
}
