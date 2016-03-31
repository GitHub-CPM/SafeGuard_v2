package com.itheima.safeguard.activities;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.itheima.safeguard.R;
import com.itheima.safeguard.engine.PhoneLocationEngine;

/**
 * @author CPM
 * 
 *         电话号码归属地的查询界面
 */
public class PhoneLocationActivity extends Activity {

	private EditText et_phone;
	private TextView tv_location;
	private PhoneLocationEngine phoneLocationEngine;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initView(); // 初始化界面

	}

	/**
	 * 初始化号码查询界面
	 */
	private void initView() {
		setContentView(R.layout.activity_phonelocation);

		et_phone = (EditText) findViewById(R.id.et_phonelocation_phonenum);
		tv_location = (TextView) findViewById(R.id.tv_phonelocation_location);
		
		phoneLocationEngine = new PhoneLocationEngine();
	}

	/**
	 * 点击按钮查询号码归属地
	 * 
	 * @param view
	 */
	public void searchLocation(View view) {
		String phoneNum = et_phone.getText().toString().trim();
		if (TextUtils.isEmpty(phoneNum)) {
			Toast.makeText(getApplicationContext(), "输入号码不能为空",
					Toast.LENGTH_SHORT).show();
			return;
		}
		String location = phoneLocationEngine.queryLocation(phoneNum);
		tv_location.setText(location);
	}

}
