package com.itheima.safeguard.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.itheima.safeguard.R;
import com.itheima.safeguard.engine.SMSEngine;
import com.itheima.safeguard.engine.SMSEngine.smsBackupRestoreRecall;

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
	
	/**�����ݵ����ص����ݽ��л�ԭ
	 * @param view
	 */
	public void restoreSMS(View view) {
		SMSEngine.smsRestoreJson(AToolActivity.this, new smsBackupRestoreRecall() {

			@Override
			public void show() {
				pd.show();
			}
			
			@Override
			public void setProgress(int progress) {
				pd.setProgress(progress);
			}
			
			@Override
			public void setMax(int count) {
				pd.setMax(count);
			}
			
			@Override
			public void end() {
				pd.dismiss();
			}
		});
	}

	/**
	 * ���ֻ����Ž��б��ر��ݵĹ���
	 * 
	 * @param view
	 */
	public void backupSMS(View view) {
		SMSEngine.smsBackupJson(AToolActivity.this, new smsBackupRestoreRecall() {
			
			@Override
			public void show() {
				pd.show();
			}
			
			@Override
			public void setProgress(int progress) {
				pd.setProgress(progress);
			}
			
			@Override
			public void setMax(int count) {
				pd.setMax(count);
			}
			
			@Override
			public void end() {
				pd.dismiss();
			}
		});
		
		/*SMSEngine.smsBackupToPhone(AToolActivity.this,
				new smsBackupRestoreRecall() {

					@Override
					public void show() {
						pd.show(); // ��ʾ������
					}

					@Override
					public void setProgress(int progress) {
						pd.setProgress(progress); // ��ʾ�������Ľ���
					}

					@Override
					public void setMax(int count) {
						pd.setMax(count); // ���ý��������ֵ
					}

					@Override
					public void end() {
						pd.dismiss(); // �ý�������ʧ
					}
				});*/
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
