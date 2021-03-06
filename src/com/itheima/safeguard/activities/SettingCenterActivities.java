package com.itheima.safeguard.activities;

import android.R.integer;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.itheima.safeguard.R;
import com.itheima.safeguard.service.BlackNumberService;
import com.itheima.safeguard.service.ComingPhoneService;
import com.itheima.safeguard.service.WatchDogService;
import com.itheima.safeguard.utils.MyConstants;
import com.itheima.safeguard.utils.SPTools;
import com.itheima.safeguard.utils.ServiceUtils;
import com.itheima.safeguard.view.SettingCenterItemView;

public class SettingCenterActivities extends Activity {

	private SettingCenterItemView sciv_autoupdate; // 自动更新的条目
	private SettingCenterItemView sciv_blackNum; // 黑名单拦截服务
	private SettingCenterItemView sciv_phonelocation; // 手机归属地查询
	private SettingCenterItemView sciv_watchDog; // 看门狗服务
	private TextView tv_style;
	private RelativeLayout rl_btn;
	private String[] styleName = { "卫士蓝", "金属灰", "苹果绿", "活力橙", "半透明" }; // 自定义吐司的背景样式
	private int[] styleColor = { Color.BLUE, Color.GRAY, Color.GREEN,
			Color.RED, Color.WHITE };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		iniView(); // 初始化界面
		initEvent(); // 初始化事件
		initData(); // 初始化数据
	}

	private void initData() {
		Boolean isCheck = SPTools.getBoolean(getApplicationContext(),
				MyConstants.AUTOUPDATE, false);
		// 设置初次勾选的状态
		sciv_autoupdate.setIsCheck(!isCheck);
		sciv_blackNum.setIsCheck(ServiceUtils.isRunningService(
				SettingCenterActivities.this,
				"com.itheima.safeguard.service.BlackNumberService"));
		sciv_phonelocation.setIsCheck(ServiceUtils.isRunningService(
				getApplicationContext(),
				"com.itheima.safeguard.service.ComingPhoneService"));
		sciv_watchDog.setIsCheck(ServiceUtils.isRunningService(
				getApplicationContext(),
				"com.itheima.safeguard.service.WatchDogService"));
	}

	/**
	 * 监听条目是否被触发
	 */
	private void initEvent() {
		// 开启看门狗服务
		sciv_watchDog.setItemClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (ServiceUtils.isRunningService(getApplicationContext(),
						"com.itheima.safeguard.service.WatchDogService")) {
					Intent watchDog = new Intent(SettingCenterActivities.this,
							WatchDogService.class);
					stopService(watchDog);
					sciv_watchDog.setIsCheck(false);
				} else {
					Intent watchDog = new Intent(SettingCenterActivities.this,
							WatchDogService.class);
					startService(watchDog);
					sciv_watchDog.setIsCheck(true);
				}
			}
		});

		rl_btn.setOnClickListener(new OnClickListener() {
			// 选择切换来电归属地弹出自定义吐司的背景样式
			@Override
			public void onClick(View v) {
				AlertDialog.Builder ab = new AlertDialog.Builder(
						SettingCenterActivities.this);
				ab.setTitle("选择来电归属地背景样式");
				ab.setSingleChoiceItems(styleName, Integer.parseInt(SPTools
						.getString(getApplicationContext(),
								MyConstants.TOAST_STYLE, "0")),
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								SPTools.putString(getApplicationContext(),
										MyConstants.TOAST_STYLE, which + "");
								tv_style.setText(styleName[which]);
								tv_style.setTextColor(styleColor[which]);
								Toast.makeText(getApplicationContext(),
										"已选择" + styleName[which],
										Toast.LENGTH_SHORT).show();
								dialog.dismiss();
							}
						});
				ab.show();
			}
		});

		sciv_phonelocation.setItemClickListener(new OnClickListener() {
			// 监测来电区域显示功能是否被点击
			@Override
			public void onClick(View v) {
				boolean b = ServiceUtils.isRunningService(
						getApplicationContext(),
						"com.itheima.safeguard.service.ComingPhoneService");
				if (b) {
					Intent phoneLocation = new Intent(getApplicationContext(),
							ComingPhoneService.class);
					stopService(phoneLocation);
					sciv_phonelocation.setIsCheck(!b);
				} else {
					Intent phoneLocation = new Intent(getApplicationContext(),
							ComingPhoneService.class);
					startService(phoneLocation);
					sciv_phonelocation.setIsCheck(!b);
				}
			}
		});

		sciv_autoupdate.setItemClickListener(new OnClickListener() {
			// 监听自动更新的条目选项是否被点击
			@Override
			public void onClick(View v) {
				// 如果被点击,则改变该选项的状态,根据复选框是否被勾上判断
				Boolean status = sciv_autoupdate.getIsCheck();
				sciv_autoupdate.setIsCheck(!status);
				// 存值
				SPTools.putBoolean(getApplicationContext(),
						MyConstants.AUTOUPDATE, status);
				if (status) {
					Toast.makeText(getApplicationContext(), "自动更新服务被取消!",
							Toast.LENGTH_SHORT).show();
				}
			}
		});

		sciv_blackNum.setItemClickListener(new OnClickListener() {
			// 监听黑名单拦截服务是否被点击
			@Override
			public void onClick(View v) {
				// 监听黑名单拦截服务的开启与否
				boolean isRunning = ServiceUtils.isRunningService(
						SettingCenterActivities.this,
						"com.itheima.safeguard.service.BlackNumberService");
				if (isRunning) {// 运行,则关闭服务
					Intent blackNumber = new Intent(
							SettingCenterActivities.this,
							BlackNumberService.class);
					stopService(blackNumber);
					sciv_blackNum.setIsCheck(false);
				} else { // 没有运行,开启服务
					Intent blackNumber = new Intent(
							SettingCenterActivities.this,
							BlackNumberService.class);
					startService(blackNumber);
					sciv_blackNum.setIsCheck(true);
				}

			}
		});

		// sciv_autoupdate.setOnClickListener(new OnClickListener() {
		// @Override
		// public void onClick(View v) {
		// // 如果被点击,则改变该选项的状态,根据复选框是否被勾上判断
		// Boolean status = sciv_autoupdate.getIsCheck();
		// sciv_autoupdate.setIsCheck(status);
		// // 存值
		// SPTools.putBoolean(getApplicationContext(), MyConstants.AUTOUPDATE,
		// status);
		// if (!status) {
		// Toast.makeText(getApplicationContext(), "自动更新服务被取消!",
		// Toast.LENGTH_SHORT).show();
		// }
		// }
		// });

	}

	/**
	 * 初始化界面
	 */
	private void iniView() {
		setContentView(R.layout.activity_settingcenter);
		sciv_autoupdate = (SettingCenterItemView) findViewById(R.id.sciv_settringcenter_autoupdate);
		sciv_blackNum = (SettingCenterItemView) findViewById(R.id.sciv_settingcenter_blackNum);
		sciv_phonelocation = (SettingCenterItemView) findViewById(R.id.sciv_settingcenter_phonelocation);
		sciv_watchDog = (SettingCenterItemView) findViewById(R.id.sciv_settingcenter_watchdog);

		// 来电归属地背景样式
		tv_style = (TextView) findViewById(R.id.tv_settingcenter_comingphonestyle_content);
		rl_btn = (RelativeLayout) findViewById(R.id.rl_settingcenter_comingphonestyle_btn);

		// 取存进去的值
		int index = Integer.parseInt(SPTools.getString(getApplicationContext(),
				MyConstants.TOAST_STYLE, "0"));
		tv_style.setText(styleName[index]);
		tv_style.setTextColor(styleColor[index]);
	}

}
