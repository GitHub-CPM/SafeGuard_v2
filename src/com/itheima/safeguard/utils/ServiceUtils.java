package com.itheima.safeguard.utils;

import java.util.List;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.Context;
import android.content.pm.PackageManager;

/**
 * @author CPM
 * 
 *			��������������ж��Ƿ��в�ѯ�ķ������������
 */
public class ServiceUtils {

	public static boolean isRunningService(Context context,String serviceName) {
		Boolean isRunning = false; // Ĭ��ֵΪfalse
		
		// ��û�Ĺ�����,���Բ鿴����ǰ�������еķ�������һЩ
		ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		// �õ�һ���б�,�������������еķ����������
		List<RunningServiceInfo> runningServices = am.getRunningServices(50);
		// �����б�
		for (RunningServiceInfo runningServiceInfo : runningServices) {
			String runningServiceName = runningServiceInfo.service.getClassName();
			// �ж����������ķ����ȫ������ͬ,������ѭ��,����true
			if (serviceName.equals(runningServiceName)) {
				isRunning = true;
				break;
			}
		}
		return isRunning;
	}
	
}
