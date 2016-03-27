package com.itheima.safeguard.activities;

import android.content.Intent;
import android.graphics.Color;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;

import com.itheima.safeguard.R;
import com.itheima.safeguard.service.LostFindService;
import com.itheima.safeguard.utils.MyConstants;
import com.itheima.safeguard.utils.SPTools;
import com.itheima.safeguard.utils.ServiceUtils;

/**
 * @author CPM
 * 
 *         ������ֻ�������ť֮��,����ĵ��ĸ������򵼽���
 */
public class Setup4Activities extends BaseSetupActivities {

	private CheckBox cb_isselected;// �����Ƿ����Ĺ�ѡ��
	private TextView tv_openProtect;// "�ֻ���������"�Ƿ��Ѿ�������������ʾ

	/**
	 * ��ʼ�����ĸ������򵼽���
	 */
	public void initView() {
		setContentView(R.layout.activity_setup4);

		cb_isselected = (CheckBox) findViewById(R.id.cb_set4_isselected);
		tv_openProtect = (TextView) findViewById(R.id.tv_set4_openprotect);
	}

	/*
	 * ��ʼ��checkbox������,���Ƿ��з�����������,���������,����,���û���򲻹�
	 * 
	 * @see com.itheima.safeguard.activities.BaseSetupActivities#initData()
	 */
	@Override
	public void initData() {
		super.initData();
		if (ServiceUtils.isRunningService(getApplicationContext(),
				"com.itheima.safeguard.service.LostFindService")) { // ������������Ѿ�������,����checkbox
			cb_isselected.setChecked(true);
			setTextContent(true);
		} else { // �������û������,�򲻹�
			cb_isselected.setChecked(false);
			setTextContent(false);
		}

	}

	/**
	 * ����ͳһ���÷�������������������������ʾ
	 */
	private void setTextContent(Boolean isChecked) {
		if (isChecked) {
			tv_openProtect.setTextColor(Color.GREEN);
			tv_openProtect.setText("  ���������Ѿ�����!");
		} else {
			tv_openProtect.setTextColor(Color.RED);
			tv_openProtect.setText("  Ϊ�˰�ȫ,�빴ѡ�򿪷�������!");
		}
	}

	/*
	 * ��ʼ��checkbox�Ĺ�ѡ�¼� �����ѡ,���������ĺ�̨service; �������ѡ,�򲻿���.
	 * 
	 * @see com.itheima.safeguard.activities.BaseSetupActivities#initEvent()
	 */
	@Override
	public void initEvent() {
		super.initEvent();

		// ������ѡ��Ĺ�ѡ���
		cb_isselected.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if (isChecked) { // ����Ѿ���ѡ��,������������
					setTextContent(true);
					Intent lostFindService = new Intent(Setup4Activities.this,
							LostFindService.class);
					startService(lostFindService);
					Toast.makeText(Setup4Activities.this, "���������ѿ���.", 0).show();
				} else { // ���û�й�ѡ,��رշ�������
					setTextContent(false);
					Intent lostFindService = new Intent(Setup4Activities.this,
							LostFindService.class);
					stopService(lostFindService);
					Toast.makeText(Setup4Activities.this, "���������Ѿ��ر�!", 0).show();
				}
			}
		});
	}

	/*
	 * ������ɺ�,��ȥ���ֻ���������
	 * 
	 * @see com.itheima.safeguard.activities.BaseSetupActivities#nextActivity()
	 */
	@Override
	public void nextActivity() {
		SPTools.putBoolean(this, MyConstants.ISSETUPED, true);// �������������,���޸ı��Ϊtrue
		startActivity(LostFindActivities.class);// �ص��ֻ���������
	}

	/*
	 * �˻ص����������ý���
	 * 
	 * @see com.itheima.safeguard.activities.BaseSetupActivities#preActivity()
	 */
	@Override
	public void preActivity() {
		startActivity(Setup3Activities.class);
	}

}
