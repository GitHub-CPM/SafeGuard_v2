package com.itheima.safeguard.activities;

import android.app.Activity;
import android.os.Bundle;

import com.itheima.safeguard.R;

/**
 * @author CPM
 *	
 *	电话号码归属地的查询界面
 */
public class PhoneLocationActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initView(); // 初始化界面
		
	}

	/**
	 * 初始化号码查询界面
	 */
	private void initView() {
		setContentView(R.layout.activity_phonelocation);
	}
	
}
