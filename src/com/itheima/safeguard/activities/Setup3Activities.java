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
 *         点击了手机防盗按钮之后,进入的第三个设置向导界面
 */
public class Setup3Activities extends BaseSetupActivities {

	private EditText et_safeNumber; // 安全号码

	/**
	 * 初始化第三个设置向导界面
	 */
	public void initView() {
		setContentView(R.layout.activity_setup3);

		et_safeNumber = (EditText) findViewById(R.id.et_set3_safenumber);
	}

	/**
	 * 点击"选择安全号码",弹出手机联系人列表
	 * 列表以ListView显示
	 */ 
	public void selectSafeNumber(View view) {
		Intent friends = new Intent(getApplicationContext(),FriendsActivity.class);
		startActivityForResult(friends, 1);
	}
	
	/* 接收返回来的数据,将其显示在edittext上面
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
	 * 初始化数据,如果有安全号码已经存在sp了,那就取出来放到edittext中.
	 * @see com.itheima.safeguard.activities.BaseSetupActivities#initData()
	 */
	@Override
	public void initData() {
		String safeNum = SPTools.getString(this, MyConstants.SAFENUMBER, "");
		safeNum = EncryptUtils.decrypt(MyConstants.SEED, safeNum);// 解密显示
		if (!TextUtils.isEmpty(safeNum)) {
			et_safeNumber.setText(safeNum);
		}
		super.initData();
	}

	/* 
	 * 如果用户没有输入安全号码,则不能进行下一步
	 * @see com.itheima.safeguard.activities.BaseSetupActivities#next(android.view.View)
	 */
	@Override
	public void next(View view) {
		// 获得安全号码
		String safeNumber = et_safeNumber.getText().toString().trim();
		
		if (TextUtils.isEmpty(safeNumber)) {
			// 如果安全号码为空,就提示用户不能为空
			Toast.makeText(this, "安全号码不能为空!", Toast.LENGTH_SHORT).show();
			// 不能切换到下一页
			return;
		}else {// 安全号码不为空,就保存到sp当中
			safeNumber = EncryptUtils.encrypt(MyConstants.SEED, safeNumber);
			SPTools.putString(getApplicationContext(), MyConstants.SAFENUMBER, safeNumber);
			Toast.makeText(this, "安全号码保存成功!", Toast.LENGTH_SHORT).show();
		}
		super.next(view);
	}
	
	/*
	 * 进入第四个设置界面
	 * 
	 * @see com.itheima.safeguard.activities.BaseSetupActivities#nextActivity()
	 */
	@Override
	public void nextActivity() {
		startActivity(Setup4Activities.class);
	}

	/*
	 * 退回到第二个设置界面
	 * 
	 * @see com.itheima.safeguard.activities.BaseSetupActivities#preActivity()
	 */
	@Override
	public void preActivity() {
		startActivity(Setup2Activities.class);
	}

}
