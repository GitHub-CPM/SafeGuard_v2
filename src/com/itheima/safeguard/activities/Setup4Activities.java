package com.itheima.safeguard.activities;

import com.itheima.safeguard.R;

import android.app.Activity;
import android.os.Bundle;

/**
 * @author CPM
 * 
 *         点击了手机防盗按钮之后,进入的第四个设置向导界面
 */
public class Setup4Activities extends BaseSetupActivities {

	/**
	 * 初始化第四个设置向导界面
	 */
	public void initView() {
		setContentView(R.layout.activity_setup4);
	}

	/* 设置完成后,回去到手机防盗界面
	 * @see com.itheima.safeguard.activities.BaseSetupActivities#nextActivity()
	 */
	@Override
	public void nextActivity() {
		startActivity(LostFindActivities.class);
	}

	/* 退回到第三个设置界面
	 * @see com.itheima.safeguard.activities.BaseSetupActivities#preActivity()
	 */
	@Override
	public void preActivity() {
		startActivity(Setup3Activities.class);
	}

}
