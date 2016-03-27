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
 *         ����һ�����������ý����ȡ�����ĳ�����
 */
public abstract class BaseSetupActivities extends Activity {

	private GestureDetector gd; // ����ʶ����

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		initView(); // ��ʼ������
		initGesture(); // ��ʼ�����������¼�
		initData();// ��ʼ������
		initEvent();// ��ʼ���¼�
	}

	/**
	 * �յķ���,�������Լ�ѡ��ʵ��
	 */
	public void initData() {
	}

	/**
	 * �յķ���,�������Լ�ѡ��ʵ��
	 */
	public void initEvent() {
	}

	/*
	 * Ҫʵ�ֶ����Ʋ������м���,�������ô����¼��ķ���,��onTouch�¼�
	 * 
	 * @see android.app.Activity#onTouchEvent(android.view.MotionEvent)
	 */
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		gd.onTouchEvent(event);
		return super.onTouchEvent(event);
	}

	/**
	 * ���û�����ָ����Ļ�������л���ʱ��,���н�����л�
	 */
	@SuppressWarnings("deprecation")
	private void initGesture() {
		// ����ÿ����Ӧ��ʱ��,��newһ����������Ļ�,��������Ӧ�ٶ�
		// Ӧ��ͳһ��һ��bean��װ�������,��Ҫ��ʱ���ٸ���Ӧ�û����Ч�ʺͲ�������.
		gd = new GestureDetector(new OnGestureListener() {

			/*
			 * ͨ���˷��������û�����ָ�����л���Ļʱ��Ĳ���
			 * 
			 * @see
			 * android.view.GestureDetector.OnGestureListener#onFling(android
			 * .view.MotionEvent, android.view.MotionEvent, float, float)
			 */
			@Override
			public boolean onFling(MotionEvent e1, MotionEvent e2,
					float velocityX, float velocityY) {
				// �����л�,��˶�velocityX�����ж�
				double dist = (e1.getX() - e2.getX());
				if (Math.abs(dist) < 20) {// ����ָ�����ľ���Ҳ��һ���ж�,��������̫С�����л�
					return false;
				}
				if (dist > 0) { // ֤��������Ҫ����һҳ����
					next(null);// ������������¼�,����Ҫ����view,ֱ������null
				} else { // ֤��������Ҫ����һҳ����
					prev(null);// ������������¼�,����Ҫ����view,ֱ������null
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
	 * �������඼��Ҫ�̳д˷���ʵ�ֽ���ĳ�ʼ��
	 */
	public abstract void initView();

	/**
	 * �����������򵼻ص���һ��ҳ��ķ���, 1.��ɽ�����л�; 2.��ɶ�������ʾ.
	 * 
	 * @param view
	 */
	public void next(View view) {
		nextActivity();
		nextAnimation();
	}

	/**
	 * �����������򵼻ص���һ��ҳ��ķ���,������̳�ʵ�� 1.��ɽ�����л�; 2.��ɶ�������ʾ.
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
