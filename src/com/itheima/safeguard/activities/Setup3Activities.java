package com.itheima.safeguard.activities;

import com.itheima.safeguard.R;

import android.app.Activity;
import android.os.Bundle;

/**
 * @author CPM
 * 
 *         ������ֻ�������ť֮��,����ĵ����������򵼽���
 */
public class Setup3Activities extends BaseSetupActivities {

	/**
	 * ��ʼ�������������򵼽���
	 */
	public void initView() {
		setContentView(R.layout.activity_setup3);
	}

	/* ������ĸ����ý���
	 * @see com.itheima.safeguard.activities.BaseSetupActivities#nextActivity()
	 */
	@Override
	public void nextActivity() {
		startActivity(Setup4Activities.class);
	}

	/* �˻ص��ڶ������ý���
	 * @see com.itheima.safeguard.activities.BaseSetupActivities#preActivity()
	 */
	@Override
	public void preActivity() {
		startActivity(Setup2Activities.class);
	}

}
