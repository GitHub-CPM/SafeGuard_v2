package com.itheima.safeguard.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.itheima.safeguard.R;
import com.itheima.safeguard.utils.MyConstants;
import com.itheima.safeguard.utils.SPTools;
import com.itheima.safeguard.view.SettingCenterItemView;

public class SettingCenterActivities extends Activity {

	private SettingCenterItemView sciv_autoupdate; //自动更新的条目

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		iniView(); // 初始化界面
		initEvent(); // 初始化事件
		initData(); // 初始化数据
	}

	private void initData() {
		Boolean isCheck = SPTools.getBoolean(getApplicationContext(), MyConstants.AUTOUPDATE, false);
		sciv_autoupdate.setIsCheck(!isCheck);
	} 

	/**
	 * 监听条目是否被触发
	 */
	private void initEvent() {
		//监听自动更新的条目选项是否被勾上
		sciv_autoupdate.setItemClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// 如果被点击,则改变该选项的状态,根据复选框是否被勾上判断
				Boolean status = sciv_autoupdate.getIsCheck();
				sciv_autoupdate.setIsCheck(!status);
				// 存值
				SPTools.putBoolean(getApplicationContext(), MyConstants.AUTOUPDATE, status);
				if (status) {
					Toast.makeText(getApplicationContext(), "自动更新服务被取消!", Toast.LENGTH_SHORT).show();
				}
			}
		});
		
//		sciv_autoupdate.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				// 如果被点击,则改变该选项的状态,根据复选框是否被勾上判断
//				Boolean status = sciv_autoupdate.getIsCheck();
//				sciv_autoupdate.setIsCheck(status);
//				// 存值
//				SPTools.putBoolean(getApplicationContext(), MyConstants.AUTOUPDATE, status);
//				if (!status) {
//					Toast.makeText(getApplicationContext(), "自动更新服务被取消!", Toast.LENGTH_SHORT).show();
//				}
//			}
//		});
		
	}

	/**
	 * 初始化界面
	 */
	private void iniView() {
		setContentView(R.layout.activity_settingcenter);
		sciv_autoupdate = (SettingCenterItemView) findViewById(R.id.sciv_settringcenter_autoupdate);
	}
	
}
