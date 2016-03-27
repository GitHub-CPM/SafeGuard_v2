package com.itheima.safeguard.activities;

import android.content.Intent;
import android.graphics.Color;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;

import com.itheima.safeguard.R;
import com.itheima.safeguard.service.LostFindService;
import com.itheima.safeguard.utils.MyConstants;
import com.itheima.safeguard.utils.SPTools;
import com.itheima.safeguard.utils.ServiceUtils;

/**
 * @author CPM
 * 
 *         点击了手机防盗按钮之后,进入的第四个设置向导界面
 */
public class Setup4Activities extends BaseSetupActivities {

	private CheckBox cb_isselected;// 防盗是否开启的勾选框
	private TextView tv_openProtect;// "手机防盗服务"是否已经开启的文字显示

	/**
	 * 初始化第四个设置向导界面
	 */
	public void initView() {
		setContentView(R.layout.activity_setup4);

		cb_isselected = (CheckBox) findViewById(R.id.cb_set4_isselected);
		tv_openProtect = (TextView) findViewById(R.id.tv_set4_openprotect);
	}

	/*
	 * 初始化checkbox的数据,看是否有服务正在运行,如果有运行,则勾上,如果没有则不勾
	 * 
	 * @see com.itheima.safeguard.activities.BaseSetupActivities#initData()
	 */
	@Override
	public void initData() {
		super.initData();
		if (ServiceUtils.isRunningService(getApplicationContext(),
				"com.itheima.safeguard.service.LostFindService")) { // 如果防盗服务已经在运行,则勾上checkbox
			cb_isselected.setChecked(true);
			setTextContent(true);
		} else { // 如果服务没有运行,则不勾
			cb_isselected.setChecked(false);
			setTextContent(false);
		}

	}

	/**
	 * 用于统一设置防盗保护开启与否的文字提醒显示
	 */
	private void setTextContent(Boolean isChecked) {
		if (isChecked) {
			tv_openProtect.setTextColor(Color.GREEN);
			tv_openProtect.setText("  防盗保护已经开启!");
		} else {
			tv_openProtect.setTextColor(Color.RED);
			tv_openProtect.setText("  为了安全,请勾选打开防盗保护!");
		}
	}

	/*
	 * 初始化checkbox的勾选事件 如果勾选,则开启防盗的后台service; 如果不勾选,则不开启.
	 * 
	 * @see com.itheima.safeguard.activities.BaseSetupActivities#initEvent()
	 */
	@Override
	public void initEvent() {
		super.initEvent();

		// 监听勾选框的勾选情况
		cb_isselected.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				if (isChecked) { // 如果已经勾选了,则开启防盗服务
					setTextContent(true);
					Intent lostFindService = new Intent(Setup4Activities.this,
							LostFindService.class);
					startService(lostFindService);
					Toast.makeText(Setup4Activities.this, "防盗功能已开启.", 0).show();
				} else { // 如果没有勾选,则关闭防盗服务
					setTextContent(false);
					Intent lostFindService = new Intent(Setup4Activities.this,
							LostFindService.class);
					stopService(lostFindService);
					Toast.makeText(Setup4Activities.this, "防盗功能已经关闭!", 0).show();
				}
			}
		});
	}

	/*
	 * 设置完成后,回去到手机防盗界面
	 * 
	 * @see com.itheima.safeguard.activities.BaseSetupActivities#nextActivity()
	 */
	@Override
	public void nextActivity() {
		SPTools.putBoolean(this, MyConstants.ISSETUPED, true);// 如果如果设置完成,则修改标记为true
		startActivity(LostFindActivities.class);// 回到手机防盗界面
	}

	/*
	 * 退回到第三个设置界面
	 * 
	 * @see com.itheima.safeguard.activities.BaseSetupActivities#preActivity()
	 */
	@Override
	public void preActivity() {
		startActivity(Setup3Activities.class);
	}

}
