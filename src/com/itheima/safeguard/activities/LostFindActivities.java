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
 *         这是手机防盗界面
 */
public class LostFindActivities extends Activity {

	private TextView tv_safeNum;// 设置安全号码的显示

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
			Intent intent = new Intent(LostFindActivities.this,
					Setup1Activities.class);
			startActivity(intent);
			// 关闭当前手机防盗界面,因为如果进入了向导界面之后,当用户按了返回键的时候,应该回到主界面
			finish();
		}
	}

	/**
	 * 点击文本,进入设置向导界面,重新设置
	 * 
	 * @param view
	 */
	public void enterSetup(View view) {
		Intent intent = new Intent(LostFindActivities.this,
				Setup1Activities.class);
		startActivity(intent);// 转到设置第一页.
		finish();// 关闭自己
	}

	/**
	 * 初始化手机防盗界面
	 */
	private void initView() {
		setContentView(R.layout.activity_lostfind);

		tv_safeNum = (TextView) findViewById(R.id.tv_lostfind_safenum);
		// 设置显示安全号码
		tv_safeNum.setText(SPTools.getString(getApplicationContext(),
				MyConstants.SAFENUMBER, ""));

		ImageView iv_lock = (ImageView) findViewById(R.id.iv_lostfind_lock);
		// 如果已经开启了防护,则显示锁合上,没有开启防护,显示锁打开
		if (ServiceUtils.isRunningService(this,
				"com.itheima.safeguard.service.LostFindService")) {
			iv_lock.setImageResource(R.drawable.lock);
		} else {
			iv_lock.setImageResource(R.drawable.unlock);
		}
	}

}
