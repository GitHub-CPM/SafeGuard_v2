package com.itheima.safeguard.activities;

import com.itheima.safeguard.R;

import android.app.Activity;
import android.os.Bundle;

/**
 * @author CPM
 * 
 *         ����������
 */
public class HomeActivities extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initView(); // ��ʼ������
	}

	/**
	 * �ڴ˷����н��г�ʼ������Ĳ���
	 */
	private void initView() {
		setContentView(R.layout.activity_home);
	}
	
}
