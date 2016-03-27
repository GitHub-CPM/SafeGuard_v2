package com.itheima.safeguard.activities;

import com.itheima.safeguard.R;
import com.itheima.safeguard.utils.MyConstants;
import com.itheima.safeguard.utils.SPTools;
import com.itheima.safeguard.utils.ServiceUtils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * @author CPM
 * 
 *         �����ֻ���������
 */
public class LostFindActivities extends Activity {

	private TextView tv_safeNum;// ���ð�ȫ�������ʾ

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
			Intent intent = new Intent(LostFindActivities.this,
					Setup1Activities.class);
			startActivity(intent);
			// �رյ�ǰ�ֻ���������,��Ϊ����������򵼽���֮��,���û����˷��ؼ���ʱ��,Ӧ�ûص�������
			finish();
		}
	}

	/**
	 * ����ı�,���������򵼽���,��������
	 * 
	 * @param view
	 */
	public void enterSetup(View view) {
		Intent intent = new Intent(LostFindActivities.this,
				Setup1Activities.class);
		startActivity(intent);// ת�����õ�һҳ.
		finish();// �ر��Լ�
	}

	/**
	 * ��ʼ���ֻ���������
	 */
	private void initView() {
		setContentView(R.layout.activity_lostfind);

		tv_safeNum = (TextView) findViewById(R.id.tv_lostfind_safenum);
		// ������ʾ��ȫ����
		tv_safeNum.setText(SPTools.getString(getApplicationContext(),
				MyConstants.SAFENUMBER, ""));

		ImageView iv_lock = (ImageView) findViewById(R.id.iv_lostfind_lock);
		// ����Ѿ������˷���,����ʾ������,û�п�������,��ʾ����
		if (ServiceUtils.isRunningService(this,
				"com.itheima.safeguard.service.LostFindService")) {
			iv_lock.setImageResource(R.drawable.lock);
		} else {
			iv_lock.setImageResource(R.drawable.unlock);
		}
	}

}
