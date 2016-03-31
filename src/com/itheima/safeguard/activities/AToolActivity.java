package com.itheima.safeguard.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.itheima.safeguard.R;

/**
 * @author CPM
 * 
 *         高级工具的界面,提供号码归属地查询/短信备份/短信还原等功能
 */
public class AToolActivity extends Activity {

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
