package com.itheima.safeguard.activities;

import com.itheima.safeguard.R;
import com.itheima.safeguard.utils.MyConstants;
import com.itheima.safeguard.utils.SPTools;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * @author CPM
 * 
 *         �����ֻ���������
 */
public class LostFindActivities extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// ���û��Ƿ��Ѿ����й��������ж�
		if (SPTools.getBoolean(getApplicationContext(), MyConstants.ISSETUPED,
				false)) {
			// ����Ѿ����ù���,ֱ�ӽ����ֻ���������
			initView(); // ��ʼ���ֻ���������
		} else {
			// �����һ������,�ͽ��������򵼽���
			Intent intent = new Intent(LostFindActivities.this, Setup1Activities.class);
			startActivity(intent);
			// �رյ�ǰ�ֻ���������,��Ϊ����������򵼽���֮��,���û����˷��ؼ���ʱ��,Ӧ�ûص�������
			finish();
		}
	}

	/**
	 * ��ʼ���ֻ���������
	 */
	private void initView() {
		setContentView(R.layout.activity_lostfind);
	}

}
