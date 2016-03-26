package com.itheima.safeguard.activities;

import com.itheima.safeguard.R;

import android.app.Activity;
import android.os.Bundle;

/**
 * @author CPM
 * 
 *         点击了手机防盗按钮之后,进入的第一个设置向导界面
 */
public class Setup1Activities extends BaseSetupActivities {

	/**
	 * 初始化第一个设置向导界面
	 */
	public void initView() {
		setContentView(R.layout.activity_setup1);
	}

	/* 进入第二个设置页面
	 * @see com.itheima.safeguard.activities.BaseSetupActivities#nextActivity()
	 */
	@Override
	public void nextActivity() {
		startActivity(Setup2Activities.class);
	}

	/* 因为没有上一个页面,所以方法内容为空
	 * @see com.itheima.safeguard.activities.BaseSetupActivities#preActivity()
	 */
	@Override
	public void preActivity() {
		// TODO Auto-generated method stub
		
	}

}
