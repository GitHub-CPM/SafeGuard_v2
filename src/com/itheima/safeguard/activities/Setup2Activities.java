package com.itheima.safeguard.activities;

import com.itheima.safeguard.R;

import android.app.Activity;
import android.os.Bundle;

/**
 * @author CPM
 * 
 *         ������ֻ�������ť֮��,����ĵڶ��������򵼽���
 */
public class Setup2Activities extends BaseSetupActivities {

	/**
	 * ��ʼ���ڶ��������򵼽���
	 */
	public void initView() {
		setContentView(R.layout.activity_setup2);
	}

	/* ������������ý���
	 * @see com.itheima.safeguard.activities.BaseSetupActivities#nextActivity()
	 */
	@Override
	public void nextActivity() {
		startActivity(Setup3Activities.class);
	}

	/* �˻ص���һ�����ý���
	 * @see com.itheima.safeguard.activities.BaseSetupActivities#preActivity()
	 */
	@Override
	public void preActivity() {
		startActivity(Setup1Activities.class);
	}

}
