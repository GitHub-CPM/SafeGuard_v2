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
 *         点击了手机防盗按钮之后,进入的第二个设置向导界面
 */
public class Setup2Activities extends BaseSetupActivities {

	private Button btn_bind;// 按钮
	private ImageView iv_lock; // 小锁图片
	private String simCardNum;// 存在sp里面的sim卡号码内容

	/**
	 * 初始化第二个设置向导界面
	 */
	public void initView() {
		setContentView(R.layout.activity_setup2);

		btn_bind = (Button) findViewById(R.id.btn_set2_bind);
		iv_lock = (ImageView) findViewById(R.id.iv_set2_lock);
	}

	/*
	 * 初始化数据
	 * 
	 * @see com.itheima.safeguard.activities.BaseSetupActivities#initData()
	 */
	@Override
	public void initData() {
		super.initData();
		if (TextUtils.isEmpty(SPTools.getString(getApplicationContext(),
				MyConstants.SIM, ""))) {
			// 还没有绑定SIM卡,小锁图片为打开的状态
			iv_lock.setImageResource(R.drawable.unlock);
		} else {
			// 已经绑定SIM卡,小锁图片为合上的状态
			iv_lock.setImageResource(R.drawable.lock);
		}
	}

	/*
	 * 初始化事件
	 * 
	 * @see com.itheima.safeguard.activities.BaseSetupActivities#initEvent()
	 */
	@Override
	public void initEvent() {
		super.initEvent();
		btn_bind.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 对按钮进行监听
				TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE); // 获得手机管理者对象
				simCardNum = SPTools.getString(getApplicationContext(),
						MyConstants.SIM, "");

				// 还没有绑定SIM卡
				if (TextUtils.isEmpty(simCardNum)) {
					 String simSerialNumber = tm.getSimSerialNumber();
					// 获得本手机的SIM卡信息
					// 由于我测试的机子没有装SIM卡,因此没有SIM卡时候,这句语句就会报错,正确语句应为上句
					// String simSerialNumber = "333";

					simSerialNumber = Md5Utils.md5(simSerialNumber);// 加密之后再存进去
					SPTools.putString(getApplicationContext(), MyConstants.SIM,
							simSerialNumber);// 把SIM卡序列号存进去
					// 并且改变小锁图片为 锁合上
					iv_lock.setImageResource(R.drawable.lock);
				} else {// 已经绑定过SIM卡了
					SPTools.putString(getApplicationContext(), MyConstants.SIM,
							""); // 存一个空值进去,也就是解绑SIM卡
					// 改变小锁图片为 锁打开
					iv_lock.setImageResource(R.drawable.unlock);
				}

			}
		});
	}

	/*
	 * 如果用户没有绑定SIM卡,就不能切换到下一个页面
	 * 
	 * @see
	 * com.itheima.safeguard.activities.BaseSetupActivities#next(android.view
	 * .View)
	 */
	@Override
	public void next(View view) {
		if (TextUtils.isEmpty(SPTools.getString(getApplicationContext(),
				MyConstants.SIM, ""))) {
			Toast.makeText(getApplicationContext(), "请先绑定SIM卡",
					Toast.LENGTH_SHORT).show();
			return;
		}
		super.next(view);
	}

	/*
	 * 进入第三个设置界面
	 * 
	 * @see com.itheima.safeguard.activities.BaseSetupActivities#nextActivity()
	 */
	@Override
	public void nextActivity() {
		startActivity(Setup3Activities.class);
	}

	/*
	 * 退回到第一个设置界面
	 * 
	 * @see com.itheima.safeguard.activities.BaseSetupActivities#preActivity()
	 */
	@Override
	public void preActivity() {
		startActivity(Setup1Activities.class);
	}

}
