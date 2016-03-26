package com.itheima.safeguard.activities;

import com.itheima.safeguard.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * @author CPM
 * 
 *         这是一个从所有设置界面抽取出来的抽象父类
 */
public abstract class BaseSetupActivities extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		initView(); // 初始化界面
	}
	
	/**
	 * 所有子类都需要继承此方法实现界面的初始化
	 */
	public abstract void initView();

	/**
	 * 这是让设置向导回到下一个页面的方法,
	 * 1.完成界面的切换;
	 * 2.完成动画的显示.
	 * 
	 * @param view
	 */
	public void next(View view) {
		nextActivity();
		nextAnimation();
	}


	/**
	 * 这是让设置向导回到上一个页面的方法,由子类继承实现
	 * 1.完成界面的切换;
	 * 2.完成动画的显示.
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
	 * 这是按了下一步之后,出现的动画
	 */
	public void nextAnimation() {
		overridePendingTransition(R.anim.next_in, R.anim.next_out);
	}

	/**
	 * 这是按了上一步之后,出现的动画
	 */
	private void prevAnimation() {
		overridePendingTransition(R.anim.prev_in, R.anim.prev_out);
	}

}
