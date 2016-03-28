package com.itheima.safeguard.activities;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.itheima.safeguard.R;
import com.itheima.safeguard.utils.EncryptUtils;
import com.itheima.safeguard.utils.MyConstants;
import com.itheima.safeguard.utils.SPTools;

/**
 * @author CPM
 * 
 *         ������ֻ�������ť֮��,����ĵ����������򵼽���
 */
public class Setup3Activities extends BaseSetupActivities {

	private EditText et_safeNumber; // ��ȫ����

	/**
	 * ��ʼ�������������򵼽���
	 */
	public void initView() {
		setContentView(R.layout.activity_setup3);

		et_safeNumber = (EditText) findViewById(R.id.et_set3_safenumber);
	}

	/**
	 * ���"ѡ��ȫ����",�����ֻ���ϵ���б�
	 * �б���ListView��ʾ
	 */ 
	public void selectSafeNumber(View view) {
		Intent friends = new Intent(getApplicationContext(),FriendsActivity.class);
		startActivityForResult(friends, 1);
	}
	
	/* ���շ�����������,������ʾ��edittext����
	 * @see android.app.Activity#onActivityResult(int, int, android.content.Intent)
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (data != null) {
			switch (resultCode) {
			case 1:
				String phoneNum = data.getStringExtra(MyConstants.SAFENUMBER);
				et_safeNumber.setText(phoneNum);
				break;
			default:
				break;
			}
		}
	}
	
	/* 
	 * ��ʼ������,����а�ȫ�����Ѿ�����sp��,�Ǿ�ȡ�����ŵ�edittext��.
	 * @see com.itheima.safeguard.activities.BaseSetupActivities#initData()
	 */
	@Override
	public void initData() {
		String safeNum = SPTools.getString(this, MyConstants.SAFENUMBER, "");
		safeNum = EncryptUtils.decrypt(MyConstants.SEED, safeNum);// ������ʾ
		if (!TextUtils.isEmpty(safeNum)) {
			et_safeNumber.setText(safeNum);
		}
		super.initData();
	}

	/* 
	 * ����û�û�����밲ȫ����,���ܽ�����һ��
	 * @see com.itheima.safeguard.activities.BaseSetupActivities#next(android.view.View)
	 */
	@Override
	public void next(View view) {
		// ��ð�ȫ����
		String safeNumber = et_safeNumber.getText().toString().trim();
		
		if (TextUtils.isEmpty(safeNumber)) {
			// �����ȫ����Ϊ��,����ʾ�û�����Ϊ��
			Toast.makeText(this, "��ȫ���벻��Ϊ��!", Toast.LENGTH_SHORT).show();
			// �����л�����һҳ
			return;
		}else {// ��ȫ���벻Ϊ��,�ͱ��浽sp����
			safeNumber = EncryptUtils.encrypt(MyConstants.SEED, safeNumber);
			SPTools.putString(getApplicationContext(), MyConstants.SAFENUMBER, safeNumber);
			Toast.makeText(this, "��ȫ���뱣��ɹ�!", Toast.LENGTH_SHORT).show();
		}
		super.next(view);
	}
	
	/*
	 * ������ĸ����ý���
	 * 
	 * @see com.itheima.safeguard.activities.BaseSetupActivities#nextActivity()
	 */
	@Override
	public void nextActivity() {
		startActivity(Setup4Activities.class);
	}

	/*
	 * �˻ص��ڶ������ý���
	 * 
	 * @see com.itheima.safeguard.activities.BaseSetupActivities#preActivity()
	 */
	@Override
	public void preActivity() {
		startActivity(Setup2Activities.class);
	}

}
