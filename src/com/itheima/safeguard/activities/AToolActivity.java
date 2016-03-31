package com.itheima.safeguard.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.itheima.safeguard.R;

/**
 * @author CPM
 * 
 *         �߼����ߵĽ���,�ṩ��������ز�ѯ/���ű���/���Ż�ԭ�ȹ���
 */
public class AToolActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		initView(); // ��ʼ������
	}

	/**
	 * ��ʼ���߼����ߵĽ���
	 */
	private void initView() {
		setContentView(R.layout.activity_atool);
	}

	/**
	 * ���õ绰��������ز�ѯ����
	 * 
	 * @param view
	 */
	public void searchPhoneLocation(View view) {
		Intent search = new Intent(AToolActivity.this,
				PhoneLocationActivity.class);
		startActivity(search);
	}

}
