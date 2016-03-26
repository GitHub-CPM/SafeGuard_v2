package com.itheima.safeguard.activities;

import com.itheima.safeguard.R;

import android.app.Activity;
import android.os.Bundle;

/**
 * @author CPM
 * 
 *         点击了手机防盗按钮之后,进入的第二个设置向导界面
 */
public class Setup2Activities extends BaseSetupActivities {

	/**
	 * 初始化第二个设置向导界面
	 */
	public void initView() {
		setContentView(R.layout.activity_setup2);
	}

	/* 进入第三个设置界面
	 * @see com.itheima.safeguard.activities.BaseSetupActivities#nextActivity()
	 */
	@Override
	public void nextActivity() {
		startActivity(Setup3Activities.class);
	}

	/* 退回到第一个设置界面
	 * @see com.itheima.safeguard.activities.BaseSetupActivities#preActivity()
	 */
	@Override
	public void preActivity() {
		startActivity(Setup1Activities.class);
	}

}
