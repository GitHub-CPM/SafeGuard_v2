package com.itheima.safeguard.activities;

import android.content.Context;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.itheima.safeguard.R;
import com.itheima.safeguard.utils.Md5Utils;
import com.itheima.safeguard.utils.MyConstants;
import com.itheima.safeguard.utils.SPTools;

/**
 * @author CPM
 * 
 *         ������ֻ�������ť֮��,����ĵڶ��������򵼽���
 */
public class Setup2Activities extends BaseSetupActivities {

	private Button btn_bind;// ��ť
	private ImageView iv_lock; // С��ͼƬ
	private String simCardNum;// ����sp�����sim����������

	/**
	 * ��ʼ���ڶ��������򵼽���
	 */
	public void initView() {
		setContentView(R.layout.activity_setup2);

		btn_bind = (Button) findViewById(R.id.btn_set2_bind);
		iv_lock = (ImageView) findViewById(R.id.iv_set2_lock);
	}

	/*
	 * ��ʼ������
	 * 
	 * @see com.itheima.safeguard.activities.BaseSetupActivities#initData()
	 */
	@Override
	public void initData() {
		super.initData();
		if (TextUtils.isEmpty(SPTools.getString(getApplicationContext(),
				MyConstants.SIM, ""))) {
			// ��û�а�SIM��,С��ͼƬΪ�򿪵�״̬
			iv_lock.setImageResource(R.drawable.unlock);
		} else {
			// �Ѿ���SIM��,С��ͼƬΪ���ϵ�״̬
			iv_lock.setImageResource(R.drawable.lock);
		}
	}

	/*
	 * ��ʼ���¼�
	 * 
	 * @see com.itheima.safeguard.activities.BaseSetupActivities#initEvent()
	 */
	@Override
	public void initEvent() {
		super.initEvent();
		btn_bind.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// �԰�ť���м���
				TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE); // ����ֻ������߶���
				simCardNum = SPTools.getString(getApplicationContext(),
						MyConstants.SIM, "");

				// ��û�а�SIM��
				if (TextUtils.isEmpty(simCardNum)) {
					 String simSerialNumber = tm.getSimSerialNumber();
					// ��ñ��ֻ���SIM����Ϣ
					// �����Ҳ��ԵĻ���û��װSIM��,���û��SIM��ʱ��,������ͻᱨ��,��ȷ���ӦΪ�Ͼ�
					// String simSerialNumber = "333";

					simSerialNumber = Md5Utils.md5(simSerialNumber);// ����֮���ٴ��ȥ
					SPTools.putString(getApplicationContext(), MyConstants.SIM,
							simSerialNumber);// ��SIM�����кŴ��ȥ
					// ���Ҹı�С��ͼƬΪ ������
					iv_lock.setImageResource(R.drawable.lock);
				} else {// �Ѿ��󶨹�SIM����
					SPTools.putString(getApplicationContext(), MyConstants.SIM,
							""); // ��һ����ֵ��ȥ,Ҳ���ǽ��SIM��
					// �ı�С��ͼƬΪ ����
					iv_lock.setImageResource(R.drawable.unlock);
				}

			}
		});
	}

	/*
	 * ����û�û�а�SIM��,�Ͳ����л�����һ��ҳ��
	 * 
	 * @see
	 * com.itheima.safeguard.activities.BaseSetupActivities#next(android.view
	 * .View)
	 */
	@Override
	public void next(View view) {
		if (TextUtils.isEmpty(SPTools.getString(getApplicationContext(),
				MyConstants.SIM, ""))) {
			Toast.makeText(getApplicationContext(), "���Ȱ�SIM��",
					Toast.LENGTH_SHORT).show();
			return;
		}
		super.next(view);
	}

	/*
	 * ������������ý���
	 * 
	 * @see com.itheima.safeguard.activities.BaseSetupActivities#nextActivity()
	 */
	@Override
	public void nextActivity() {
		startActivity(Setup3Activities.class);
	}

	/*
	 * �˻ص���һ�����ý���
	 * 
	 * @see com.itheima.safeguard.activities.BaseSetupActivities#preActivity()
	 */
	@Override
	public void preActivity() {
		startActivity(Setup1Activities.class);
	}

}
