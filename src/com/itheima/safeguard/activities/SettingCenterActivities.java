package com.itheima.safeguard.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.itheima.safeguard.R;
import com.itheima.safeguard.service.BlackNumberService;
import com.itheima.safeguard.service.ComingPhoneService;
import com.itheima.safeguard.utils.MyConstants;
import com.itheima.safeguard.utils.SPTools;
import com.itheima.safeguard.utils.ServiceUtils;
import com.itheima.safeguard.view.SettingCenterItemView;

public class SettingCenterActivities extends Activity {

	private SettingCenterItemView sciv_autoupdate; // �Զ����µ���Ŀ
	private SettingCenterItemView sciv_blackNum; // ���������ط���
	private SettingCenterItemView sciv_phonelocation;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		iniView(); // ��ʼ������
		initEvent(); // ��ʼ���¼�
		initData(); // ��ʼ������
	}

	private void initData() {
		Boolean isCheck = SPTools.getBoolean(getApplicationContext(),
				MyConstants.AUTOUPDATE, false);
		// ���ó��ι�ѡ��״̬
		sciv_autoupdate.setIsCheck(!isCheck);
		sciv_blackNum.setIsCheck(ServiceUtils.isRunningService(
				SettingCenterActivities.this,
				"com.itheima.safeguard.service.BlackNumberService"));
		sciv_phonelocation.setIsCheck(ServiceUtils.isRunningService(
				getApplicationContext(),
				"com.itheima.safeguard.service.ComingPhoneService"));
	}

	/**
	 * ������Ŀ�Ƿ񱻴���
	 */
	private void initEvent() {
		sciv_phonelocation.setItemClickListener(new OnClickListener() {
			// �������������ʾ�����Ƿ񱻵��
			@Override
			public void onClick(View v) {
				boolean b = ServiceUtils.isRunningService(
						getApplicationContext(),
						"com.itheima.safeguard.service.ComingPhoneService");
				if (b) {
					Intent phoneLocation = new Intent(getApplicationContext(),
							ComingPhoneService.class);
					stopService(phoneLocation);
					sciv_phonelocation.setIsCheck(!b);
				} else {
					Intent phoneLocation = new Intent(getApplicationContext(),
							ComingPhoneService.class);
					startService(phoneLocation);
					sciv_phonelocation.setIsCheck(!b);
				}
			}
		});

		sciv_autoupdate.setItemClickListener(new OnClickListener() {
			// �����Զ����µ���Ŀѡ���Ƿ񱻵��
			@Override
			public void onClick(View v) {
				// ��������,��ı��ѡ���״̬,���ݸ�ѡ���Ƿ񱻹����ж�
				Boolean status = sciv_autoupdate.getIsCheck();
				sciv_autoupdate.setIsCheck(!status);
				// ��ֵ
				SPTools.putBoolean(getApplicationContext(),
						MyConstants.AUTOUPDATE, status);
				if (status) {
					Toast.makeText(getApplicationContext(), "�Զ����·���ȡ��!",
							Toast.LENGTH_SHORT).show();
				}
			}
		});

		sciv_blackNum.setItemClickListener(new OnClickListener() {
			// �������������ط����Ƿ񱻵��
			@Override
			public void onClick(View v) {
				// �������������ط���Ŀ������
				boolean isRunning = ServiceUtils.isRunningService(
						SettingCenterActivities.this,
						"com.itheima.safeguard.service.BlackNumberService");
				if (isRunning) {// ����,��رշ���
					Intent blackNumber = new Intent(
							SettingCenterActivities.this,
							BlackNumberService.class);
					stopService(blackNumber);
					sciv_blackNum.setIsCheck(false);
				} else { // û������,��������
					Intent blackNumber = new Intent(
							SettingCenterActivities.this,
							BlackNumberService.class);
					startService(blackNumber);
					sciv_blackNum.setIsCheck(true);
				}

			}
		});

		// sciv_autoupdate.setOnClickListener(new OnClickListener() {
		// @Override
		// public void onClick(View v) {
		// // ��������,��ı��ѡ���״̬,���ݸ�ѡ���Ƿ񱻹����ж�
		// Boolean status = sciv_autoupdate.getIsCheck();
		// sciv_autoupdate.setIsCheck(status);
		// // ��ֵ
		// SPTools.putBoolean(getApplicationContext(), MyConstants.AUTOUPDATE,
		// status);
		// if (!status) {
		// Toast.makeText(getApplicationContext(), "�Զ����·���ȡ��!",
		// Toast.LENGTH_SHORT).show();
		// }
		// }
		// });

	}

	/**
	 * ��ʼ������
	 */
	private void iniView() {
		setContentView(R.layout.activity_settingcenter);
		sciv_autoupdate = (SettingCenterItemView) findViewById(R.id.sciv_settringcenter_autoupdate);
		sciv_blackNum = (SettingCenterItemView) findViewById(R.id.sciv_settingcenter_blackNum);
		sciv_phonelocation = (SettingCenterItemView) findViewById(R.id.sciv_settingcenter_phonelocation);
	}

}
