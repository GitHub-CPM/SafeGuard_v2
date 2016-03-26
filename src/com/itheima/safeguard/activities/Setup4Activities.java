package com.itheima.safeguard.activities;

import com.itheima.safeguard.R;

import android.app.Activity;
import android.os.Bundle;

/**
 * @author CPM
 * 
 *         ������ֻ�������ť֮��,����ĵ��ĸ������򵼽���
 */
public class Setup4Activities extends BaseSetupActivities {

	/**
	 * ��ʼ�����ĸ������򵼽���
	 */
	public void initView() {
		setContentView(R.layout.activity_setup4);
	}

	/* ������ɺ�,��ȥ���ֻ���������
	 * @see com.itheima.safeguard.activities.BaseSetupActivities#nextActivity()
	 */
	@Override
	public void nextActivity() {
		startActivity(LostFindActivities.class);
	}

	/* �˻ص����������ý���
	 * @see com.itheima.safeguard.activities.BaseSetupActivities#preActivity()
	 */
	@Override
	public void preActivity() {
		startActivity(Setup3Activities.class);
	}

}
