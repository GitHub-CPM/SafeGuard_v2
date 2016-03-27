package com.itheima.safeguard.utils;

import java.util.List;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.Context;
import android.content.pm.PackageManager;

/**
 * @author CPM
 * 
 *			这个工具类用于判断是否有查询的服务进程在运行
 */
public class ServiceUtils {

	public static boolean isRunningService(Context context,String serviceName) {
		Boolean isRunning = false; // 默认值为false
		
		// 获得活动的管理者,可以查看到当前正在运行的服务有哪一些
		ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		// 得到一个列表,保存在正在运行的服务进程内容
		List<RunningServiceInfo> runningServices = am.getRunningServices(50);
		// 遍历列表
		for (RunningServiceInfo runningServiceInfo : runningServices) {
			String runningServiceName = runningServiceInfo.service.getClassName();
			// 判断如果跟传入的服务的全类名相同,则跳出循环,返回true
			if (serviceName.equals(runningServiceName)) {
				isRunning = true;
				break;
			}
		}
		return isRunning;
	}
	
}
