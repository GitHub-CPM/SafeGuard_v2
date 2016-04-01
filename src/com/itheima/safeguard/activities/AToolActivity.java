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
 *         高级工具的界面,提供号码归属地查询/短信备份/短信还原等功能
 */
public class AToolActivity extends Activity {

	private ProgressDialog pd;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		initView(); // 初始化界面
	}

	/**
	 * 初始化高级工具的界面
	 */
	private void initView() {
		setContentView(R.layout.activity_atool);

		pd = new ProgressDialog(AToolActivity.this);
		pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);// 横条进度
	}
	
	/**将备份到本地的数据进行还原
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
	 * 对手机短信进行本地备份的功能
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
						pd.show(); // 显示进度条
					}

					@Override
					public void setProgress(int progress) {
						pd.setProgress(progress); // 显示进度条的进度
					}

					@Override
					public void setMax(int count) {
						pd.setMax(count); // 设置进度条最大值
					}

					@Override
					public void end() {
						pd.dismiss(); // 让进度条消失
					}
				});*/
	}

	/**
	 * 启用电话号码归属地查询功能
	 * 
	 * @param view
	 */
	public void searchPhoneLocation(View view) {
		Intent search = new Intent(AToolActivity.this,
				PhoneLocationActivity.class);
		startActivity(search);
	}

}
