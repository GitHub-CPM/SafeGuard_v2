package com.itheima.safeguard.activities;

import com.itheima.safeguard.R;

import android.app.Activity;
import android.os.Bundle;

/**
 * @author CPM
 * 
 *         这是主界面
 */
public class HomeActivities extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initView(); // 初始化界面
	}

	/**
	 * 在此方法中进行初始化界面的操作
	 */
	private void initView() {
		setContentView(R.layout.activity_home);
	}
	
}
