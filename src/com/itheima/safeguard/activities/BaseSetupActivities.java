package com.itheima.safeguard.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.View;

import com.itheima.safeguard.R;

/**
 * @author CPM
 * 
 *         这是一个从所有设置界面抽取出来的抽象父类
 */
public abstract class BaseSetupActivities extends Activity {

	private GestureDetector gd; // 手势识别器

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		initView(); // 初始化界面
		initGesture(); // 初始化触摸滑动事件
		initData();// 初始化数据
		initEvent();// 初始化事件
	}

	/**
	 * 空的方法,让子类自己选择实现
	 */
	public void initData() {
	}

	/**
	 * 空的方法,让子类自己选择实现
	 */
	public void initEvent() {
	}

	/*
	 * 要实现对手势操作进行监听,必须设置触摸事件的方法,绑定onTouch事件
	 * 
	 * @see android.app.Activity#onTouchEvent(android.view.MotionEvent)
	 */
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		gd.onTouchEvent(event);
		return super.onTouchEvent(event);
	}

	/**
	 * 当用户用手指在屏幕上来回切换的时候,进行界面的切换
	 */
	@SuppressWarnings("deprecation")
	private void initGesture() {
		// 这里每次响应的时候,都new一个对象出来的话,拖慢了相应速度
		// 应该统一用一个bean封装这个对象,需要的时候再复用应该会提高效率和操作体验.
		gd = new GestureDetector(new OnGestureListener() {

			/*
			 * 通过此方法监听用户用手指来回切换屏幕时候的操作
			 * 
			 * @see
			 * android.view.GestureDetector.OnGestureListener#onFling(android
			 * .view.MotionEvent, android.view.MotionEvent, float, float)
			 */
			@Override
			public boolean onFling(MotionEvent e1, MotionEvent e2,
					float velocityX, float velocityY) {
				// 横向切换,因此对velocityX进行判断
				double dist = (e1.getX() - e2.getX());
				if (Math.abs(dist) < 20) {// 对手指滑动的距离也有一个判断,滑动幅度太小不能切换
					return false;
				}
				if (dist > 0) { // 证明这是想要往下一页操作
					next(null);// 非组件触发的事件,不需要传递view,直接填入null
				} else { // 证明这是想要往上一页操作
					prev(null);// 非组件触发的事件,不需要传递view,直接填入null
				}
				return true;
			}

			@Override
			public boolean onSingleTapUp(MotionEvent e) {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public void onShowPress(MotionEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public boolean onScroll(MotionEvent e1, MotionEvent e2,
					float distanceX, float distanceY) {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public void onLongPress(MotionEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public boolean onDown(MotionEvent e) {
				// TODO Auto-generated method stub
				return false;
			}
		});
	}

	/**
	 * 所有子类都需要继承此方法实现界面的初始化
	 */
	public abstract void initView();

	/**
	 * 这是让设置向导回到下一个页面的方法, 1.完成界面的切换; 2.完成动画的显示.
	 * 
	 * @param view
	 */
	public void next(View view) {
		nextActivity();
		nextAnimation();
	}

	/**
	 * 这是让设置向导回到上一个页面的方法,由子类继承实现 1.完成界面的切换; 2.完成动画的显示.
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
		Intent intent = new Intent(this, type);
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
