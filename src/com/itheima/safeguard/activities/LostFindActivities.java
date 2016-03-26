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
 *         这是手机防盗界面
 */
public class LostFindActivities extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 对用户是否已经进行过设置向导判断
		if (SPTools.getBoolean(getApplicationContext(), MyConstants.ISSETUPED,
				false)) {
			// 如果已经设置过了,直接进入手机防盗界面
			initView(); // 初始化手机防盗界面
		} else {
			// 如果第一次设置,就进入设置向导界面
			Intent intent = new Intent(LostFindActivities.this, Setup1Activities.class);
			startActivity(intent);
			// 关闭当前手机防盗界面,因为如果进入了向导界面之后,当用户按了返回键的时候,应该回到主界面
			finish();
		}
	}

	/**
	 * 初始化手机防盗界面
	 */
	private void initView() {
		setContentView(R.layout.activity_lostfind);
	}

}
