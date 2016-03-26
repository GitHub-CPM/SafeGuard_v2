package com.itheima.safeguard.activities;

import com.itheima.safeguard.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * @author CPM
 * 
 *         ����һ�����������ý����ȡ�����ĳ�����
 */
public abstract class BaseSetupActivities extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		initView(); // ��ʼ������
	}
	
	/**
	 * �������඼��Ҫ�̳д˷���ʵ�ֽ���ĳ�ʼ��
	 */
	public abstract void initView();

	/**
	 * �����������򵼻ص���һ��ҳ��ķ���,
	 * 1.��ɽ�����л�;
	 * 2.��ɶ�������ʾ.
	 * 
	 * @param view
	 */
	public void next(View view) {
		nextActivity();
		nextAnimation();
	}


	/**
	 * �����������򵼻ص���һ��ҳ��ķ���,������̳�ʵ��
	 * 1.��ɽ�����л�;
	 * 2.��ɶ�������ʾ.
	 * 
	 * @param view
	 */
	public void prev(View view) {
		preActivity();
		prevAnimation();
	}

	public abstract void nextActivity();
	public abstract void preActivity();
	
	public void startActivity(Class type) {
		Intent intent = new Intent(this,type);
		startActivity(intent);
		finish();
	}
	
	/**
	 * ���ǰ�����һ��֮��,���ֵĶ���
	 */
	public void nextAnimation() {
		overridePendingTransition(R.anim.next_in, R.anim.next_out);
	}

	/**
	 * ���ǰ�����һ��֮��,���ֵĶ���
	 */
	private void prevAnimation() {
		overridePendingTransition(R.anim.prev_in, R.anim.prev_out);
	}

}
