package com.itheima.safeguard.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.itheima.safeguard.R;
import com.itheima.safeguard.utils.MyConstants;
import com.itheima.safeguard.utils.SPTools;
import com.itheima.safeguard.view.SettingCenterItemView;

public class SettingCenterActivities extends Activity {

	private SettingCenterItemView sciv_autoupdate; //�Զ����µ���Ŀ

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		iniView(); // ��ʼ������
		initEvent(); // ��ʼ���¼�
		initData(); // ��ʼ������
	}

	private void initData() {
		Boolean isCheck = SPTools.getBoolean(getApplicationContext(), MyConstants.AUTOUPDATE, false);
		sciv_autoupdate.setIsCheck(!isCheck);
	} 

	/**
	 * ������Ŀ�Ƿ񱻴���
	 */
	private void initEvent() {
		//�����Զ����µ���Ŀѡ���Ƿ񱻹���
		sciv_autoupdate.setItemClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// ��������,��ı��ѡ���״̬,���ݸ�ѡ���Ƿ񱻹����ж�
				Boolean status = sciv_autoupdate.getIsCheck();
				sciv_autoupdate.setIsCheck(!status);
				// ��ֵ
				SPTools.putBoolean(getApplicationContext(), MyConstants.AUTOUPDATE, status);
				if (status) {
					Toast.makeText(getApplicationContext(), "�Զ����·���ȡ��!", Toast.LENGTH_SHORT).show();
				}
			}
		});
		
//		sciv_autoupdate.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				// ��������,��ı��ѡ���״̬,���ݸ�ѡ���Ƿ񱻹����ж�
//				Boolean status = sciv_autoupdate.getIsCheck();
//				sciv_autoupdate.setIsCheck(status);
//				// ��ֵ
//				SPTools.putBoolean(getApplicationContext(), MyConstants.AUTOUPDATE, status);
//				if (!status) {
//					Toast.makeText(getApplicationContext(), "�Զ����·���ȡ��!", Toast.LENGTH_SHORT).show();
//				}
//			}
//		});
		
	}

	/**
	 * ��ʼ������
	 */
	private void iniView() {
		setContentView(R.layout.activity_settingcenter);
		sciv_autoupdate = (SettingCenterItemView) findViewById(R.id.sciv_settringcenter_autoupdate);
	}
	
}
