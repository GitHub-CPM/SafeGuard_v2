package com.itheima.safeguard.activities;

import android.app.Activity;
import android.os.Bundle;
import android.os.Vibrator;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.itheima.safeguard.R;
import com.itheima.safeguard.engine.PhoneLocationEngine;

/**
 * @author CPM
 * 
 *         �绰��������صĲ�ѯ����
 */
public class PhoneLocationActivity extends Activity {

	private EditText et_phone;
	private TextView tv_location;
	private PhoneLocationEngine phoneLocationEngine;
	private Button btn_search;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initView(); // ��ʼ������
		initEvent(); // ��ʼ���¼�
	}

	/**
	 * ��ʼ���¼�
	 */
	private void initEvent() {
		btn_search.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				searchLocation();
			}
		});
		et_phone.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				searchLocation();
			}
		});
	}

	/**
	 * ��ʼ�������ѯ����
	 */
	private void initView() {
		setContentView(R.layout.activity_phonelocation);

		et_phone = (EditText) findViewById(R.id.et_phonelocation_phonenum);
		tv_location = (TextView) findViewById(R.id.tv_phonelocation_location);
		btn_search = (Button) findViewById(R.id.btn_phonelocation_search);
		
		phoneLocationEngine = new PhoneLocationEngine();
	}

	/**
	 * �����ť��ѯ���������
	 * 
	 * @param view
	 */
	public void searchLocation() {
		String phoneNum = et_phone.getText().toString().trim();
		if (TextUtils.isEmpty(phoneNum)) {
			
 			//�𶯵�Ч��
			Vibrator vibrator = (Vibrator)getSystemService(VIBRATOR_SERVICE);
 	        //�𶯵Ĳ�������
			vibrator.vibrate(new long[]{300,400,300,400,300,400,300,400}, 4);
			
			Toast.makeText(getApplicationContext(), "������벻��Ϊ��",
					Toast.LENGTH_SHORT).show();
			return;
		}
		String location = phoneLocationEngine.locationQuery(phoneNum,getApplicationContext());
		tv_location.setText(location);
	}
	
	

}
