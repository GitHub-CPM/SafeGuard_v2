package com.itheima.safeguard.activities;

import com.itheima.safeguard.R;

import android.app.Activity;
import android.os.Bundle;

/**
 * @author CPM
 * 
 *         ������ֻ�������ť֮��,����ĵ�һ�������򵼽���
 */
public class Setup1Activities extends BaseSetupActivities {

	/**
	 * ��ʼ����һ�������򵼽���
	 */
	public void initView() {
		setContentView(R.layout.activity_setup1);
	}

	/* ����ڶ�������ҳ��
	 * @see com.itheima.safeguard.activities.BaseSetupActivities#nextActivity()
	 */
	@Override
	public void nextActivity() {
		startActivity(Setup2Activities.class);
	}

	/* ��Ϊû����һ��ҳ��,���Է�������Ϊ��
	 * @see com.itheima.safeguard.activities.BaseSetupActivities#preActivity()
	 */
	@Override
	public void preActivity() {
		// TODO Auto-generated method stub
		
	}

}
