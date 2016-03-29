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
 *         ��̨�ṩGPS�Ķ�λ����,����û�
 */
public class GPSService extends Service {

	private LocationManager lm; // ��λ������
	private LocationListener listener; // ��λ������

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	/*
	 * ��������ʱ��,��ʼGPS��Ϣ�Ļ�ȡ,�����Ͷ���֪ͨ��ȫ����
	 * 
	 * @see android.app.Service#onCreate()
	 */
	@Override
	public void onCreate() {
		super.onCreate();
		lm = (LocationManager) getSystemService(LOCATION_SERVICE);// ��ö�λ������
		String provider = "gps"; // ͨ��gps��λ����þ���λ��
		long minTime = 0L; // ��ʱˢ��GPS��λ
		int minDistance = 0; // ��ʱ�κξ���ˢ��һ�ζ�λ

		// ������λ������,��Ҫ���õ�λ�÷����ı�ʱ��ķ���
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
				// ��λ�ñ������������ı�
				float accuracy = location.getAccuracy();// ���gps��λ�ľ�ȷ��
				double longitude = location.getLongitude(); // ����ֻ��ľ���
				double latitude = location.getLatitude(); // ����ֻ���γ��
				double altitude = location.getAltitude();// ����ֻ��ĺ��θ߶�
				float speed = location.getSpeed();// ����ֻ��ƶ����ٶ�

				// ͨ������,�����ϻ�õ����ݷ��͸���ȫ����
				String safeNum = SPTools.getString(GPSService.this,
						MyConstants.SAFENUMBER, ""); // ��ȫ����
				safeNum = EncryptUtils.decrypt(MyConstants.SEED, safeNum);// ����
				// ��д��������
				String msgContent = "longitude:" + longitude + ",latitude:" + latitude
						+ ",altitude:" + altitude + ",speed:" + speed + ",accuracy:"
						+ accuracy;
				SmsManager sm = SmsManager.getDefault();// ��ö��Ź�����
				// ���Ͷ���
				sm.sendTextMessage(safeNum, "", msgContent, null, null);

				// ֹͣgps��̨����,�������,�رշ�����,����ִ��ondestroy����
				stopSelf();
			}
		};

		// ��λ������������ع���Ҫ��,������λ������
		lm.requestLocationUpdates(provider, minTime, minDistance, listener);

	}

	/*
	 * �ر�gps��λ,�������
	 * 
	 * @see android.app.Service#onDestroy()
	 */
	@Override
	public void onDestroy() {
		super.onDestroy();
		// �رն�λ������,��Ϊ��
		lm.removeUpdates(listener);
		lm = null;
	}

}
