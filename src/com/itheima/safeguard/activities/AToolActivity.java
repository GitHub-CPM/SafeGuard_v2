package com.itheima.safeguard.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.itheima.safeguard.R;
import com.itheima.safeguard.engine.SMSBackup;

/**
 * @author CPM
 * 
 *         �߼����ߵĽ���,�ṩ��������ز�ѯ/���ű���/���Ż�ԭ�ȹ���
 */
public class AToolActivity extends Activity {

	private ProgressDialog pd;

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
		
		pd = new ProgressDialog(AToolActivity.this);
		pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);// ��������
	}
	
	/**���ֻ����Ž��б��ر��ݵĹ���
	 * @param view
	 */
	public void backupSMS(View view) {
		new Thread(new Runnable() {
			// �������߳�,��Ϊ�����������ں�ʱ����
			@Override
			public void run() {
				SMSBackup.SMSBackupToPhone(AToolActivity.this,pd);
			}
		}).start();
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
