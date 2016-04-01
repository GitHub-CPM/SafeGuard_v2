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
	
	/**对手机短信进行本地备份的功能
	 * @param view
	 */
	public void backupSMS(View view) {
		new Thread(new Runnable() {
			// 开启子线程,因为备份数据属于耗时操作
			@Override
			public void run() {
				SMSBackup.SMSBackupToPhone(AToolActivity.this,pd);
			}
		}).start();
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
