package com.itheima.safeguard.activities;

import android.app.Activity;
import android.os.Bundle;

import com.itheima.safeguard.R;

/**
 * @author CPM
 *	
 *	�绰��������صĲ�ѯ����
 */
public class PhoneLocationActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initView(); // ��ʼ������
		
	}

	/**
	 * ��ʼ�������ѯ����
	 */
	private void initView() {
		setContentView(R.layout.activity_phonelocation);
	}
	
}
