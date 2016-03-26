package com.itheima.safeguard.activities;

import com.itheima.safeguard.R;

import android.app.Activity;
import android.os.Bundle;

/**
 * @author CPM
 * 
 *         点击了手机防盗按钮之后,进入的第三个设置向导界面
 */
public class Setup3Activities extends BaseSetupActivities {

	/**
	 * 初始化第三个设置向导界面
	 */
	public void initView() {
		setContentView(R.layout.activity_setup3);
	}

	/* 进入第四个设置界面
	 * @see com.itheima.safeguard.activities.BaseSetupActivities#nextActivity()
	 */
	@Override
	public void nextActivity() {
		startActivity(Setup4Activities.class);
	}

	/* 退回到第二个设置界面
	 * @see com.itheima.safeguard.activities.BaseSetupActivities#preActivity()
	 */
	@Override
	public void preActivity() {
		startActivity(Setup2Activities.class);
	}

}
