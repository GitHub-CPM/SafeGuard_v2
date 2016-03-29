package com.itheima.safeguard.service;

import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.telephony.SmsManager;

import com.itheima.safeguard.utils.EncryptUtils;
import com.itheima.safeguard.utils.MyConstants;
import com.itheima.safeguard.utils.SPTools;

/**
 * @author CPM
 * 
 *         后台提供GPS的定位功能,如果用户
 */
public class GPSService extends Service {

	private LocationManager lm; // 定位管理器
	private LocationListener listener; // 定位监听器

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	/*
	 * 被启动的时候,开始GPS信息的获取,并发送短信通知安全号码
	 * 
	 * @see android.app.Service#onCreate()
	 */
	@Override
	public void onCreate() {
		super.onCreate();
		lm = (LocationManager) getSystemService(LOCATION_SERVICE);// 获得定位管理器
		String provider = "gps"; // 通过gps定位来获得具体位置
		long minTime = 0L; // 即时刷新GPS定位
		int minDistance = 0; // 即时任何距离刷新一次定位

		// 创建定位监听器,主要设置当位置发生改变时候的方法
		listener = new LocationListener() {

			@Override
			public void onStatusChanged(String provider, int status,
					Bundle extras) {
			}

			@Override
			public void onProviderEnabled(String provider) {
			}

			@Override
			public void onProviderDisabled(String provider) {
			}

			@Override
			public void onLocationChanged(Location location) {
				// 当位置被监听到发生改变
				float accuracy = location.getAccuracy();// 获得gps定位的精确度
				double longitude = location.getLongitude(); // 获得手机的经度
				double latitude = location.getLatitude(); // 获得手机的纬度
				double altitude = location.getAltitude();// 获得手机的海拔高度
				float speed = location.getSpeed();// 获得手机移动的速度

				// 通过短信,将以上获得的内容发送给安全号码
				String safeNum = SPTools.getString(GPSService.this,
						MyConstants.SAFENUMBER, ""); // 安全号码
				safeNum = EncryptUtils.decrypt(MyConstants.SEED, safeNum);// 解密
				// 编写短信内容
				String msgContent = "longitude:" + longitude + ",latitude:" + latitude
						+ ",altitude:" + altitude + ",speed:" + speed + ",accuracy:"
						+ accuracy;
				SmsManager sm = SmsManager.getDefault();// 获得短信管理者
				// 发送短信
				sm.sendTextMessage(safeNum, "", msgContent, null, null);

				// 停止gps后台服务,保存电量,关闭服务本身,让其执行ondestroy方法
				stopSelf();
			}
		};

		// 定位管理器设置相关工作要求,开启定位管理器
		lm.requestLocationUpdates(provider, minTime, minDistance, listener);

	}

	/*
	 * 关闭gps定位,保存电量
	 * 
	 * @see android.app.Service#onDestroy()
	 */
	@Override
	public void onDestroy() {
		super.onDestroy();
		// 关闭定位管理器,置为空
		lm.removeUpdates(listener);
		lm = null;
	}

}
