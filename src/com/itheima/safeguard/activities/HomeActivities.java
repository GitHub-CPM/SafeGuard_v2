package com.itheima.safeguard.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.itheima.safeguard.R;
import com.itheima.safeguard.utils.Md5Utils;
import com.itheima.safeguard.utils.MyConstants;
import com.itheima.safeguard.utils.SPTools;

/**
 * @author CPM
 * 
 *         这是主界面
 */
public class HomeActivities extends Activity {

	private int[] icons = { R.drawable._0safe, R.drawable._1callmsgsafe,
			R.drawable.btn_home_gv_item3_selector, R.drawable._3taskmanager,
			R.drawable._4netmanager, R.drawable._5trojan,
			R.drawable._6sysoptimize, R.drawable._7atools,
			R.drawable._8settings };// menu菜单的九宫格图标
	private String[] names = { "手机防盗", "通讯卫士", "软件管家", "进程管理", "流量统计", "病毒查杀",
			"缓存清理", "高级工具", "设计中心" };// menu菜单的九宫格名称

	private GridView gv_menu; // 主界面的九宫格菜单
	private MyAdapter myAdapter; // gridview的适配器
	private AlertDialog dialog; // 初次进入手机防盗设置密码的对话框
	private AlertDialog dialog2; // 再次进入已经设置密码的登陆对话框

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initView(); // 初始化界面
		initData();// 初始化gridview的显示数据
		initEvent();// 初始化gridview的item事件
	}

	/**
	 * 初始化gridview的item事件,监听每个按钮点击情况
	 */
	private void initEvent() {
		gv_menu.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// 对每个按钮进行不同的设置
				switch (position) {
				case 0:// 按钮0,即手机防盗
					if (TextUtils.isEmpty(SPTools.getString(
					// 点击手机防盗,判断是否设置过密码,如果没有设置弹出自定义对话框
							getApplicationContext(), MyConstants.PASSWORD, ""))) {
						showSettingPasswordDialog();
					} else {// 如果已经设置了密码,则直接弹出登陆密码的对话框
						showEnterPasswordDialog();
					}
					break;
				case 1:// 进入通讯卫士
					Intent TelSms = new Intent(HomeActivities.this,
							TelSmsActivity.class);
					startActivity(TelSms);
					break;
				case 8:// 按钮8,即设置中心
						// 进入设置中心
					Intent settingcenter = new Intent(HomeActivities.this,
							SettingCenterActivities.class);
					startActivity(settingcenter);
					break;
				default:
					break;
				}

			}

		});
	}

	/**
	 * 点击手机防盗,之前设置了密码,提供登陆的自定义对话窗口
	 */
	private void showEnterPasswordDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		View view = View.inflate(getApplicationContext(),
				R.layout.dialog_enter_home, null);
		builder.setView(view);
		dialog2 = builder.create();
		dialog2.show();

		// 获得自定义窗口文本/按钮的view对象
		final EditText ed_password_enter = (EditText) view
				.findViewById(R.id.ed_home_gv_dialog_enter_password);
		final Button btn_login = (Button) view
				.findViewById(R.id.btn_home_gv_dialog_enter_login);
		final Button btn_cancle = (Button) view
				.findViewById(R.id.btn_home_gv_dialog_enter_cancle);

		// 点击了登陆按钮
		btn_login.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String password = ed_password_enter.getText().toString().trim();

				if (TextUtils.isEmpty(password)) {
					Toast.makeText(getApplicationContext(), "登陆密码不能为空",
							Toast.LENGTH_SHORT).show();
					return;
				} else {

					// 将输入的密码进行md5加密,判断是否为正确密码
					password = Md5Utils.md5(password);
					String passwordMD5 = SPTools.getString(
							getApplicationContext(), MyConstants.PASSWORD, "");
					if (!password.equals(passwordMD5)) {
						// 如果密码错误,提示重新输入,并保留对话框
						Toast.makeText(getApplicationContext(), "登陆密码错误",
								Toast.LENGTH_SHORT).show();
						return;
					} else {
						// 如果密码正确,进入手机防盗界面
						Toast.makeText(getApplicationContext(), "亲,欢迎登陆!",
								Toast.LENGTH_SHORT).show();
						dialog2.dismiss();
						Intent intent = new Intent();
						intent.setClass(HomeActivities.this,
								LostFindActivities.class);
						startActivity(intent);
					}
				}

			}
		});

		// 点击了取消按钮
		btn_cancle.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 退出登陆对话框
				dialog2.dismiss();
			}
		});

	}

	/**
	 * 点击弹出显示设置密码的对话框
	 */
	private void showSettingPasswordDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		View view = View.inflate(this, R.layout.dialog_enter_password, null);// 填充生成自定义的view

		final EditText et_password_1 = (EditText) view
				.findViewById(R.id.ed_home_gv_dialog_password);
		final EditText et_password_2 = (EditText) view
				.findViewById(R.id.ed_home_gv_dialog_confirmpassword);
		final Button btn_setPassword_yes = (Button) view
				.findViewById(R.id.btn_home_gv_dialog_yes);
		final Button btn_setPassword_no = (Button) view
				.findViewById(R.id.btn_home_gv_dialog_no);

		builder.setView(view);// 把自定义的view设置给对话框

		dialog = builder.create();

		dialog.show();// 让对话框显示

		// 为确定按钮设置监听事件
		btn_setPassword_yes.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 获得输入的密码内容
				String password_1 = et_password_1.getText().toString().trim();
				String password_2 = et_password_2.getText().toString().trim();

				// 点击了确认设定的话,就判断
				if (TextUtils.isEmpty(password_1)
						|| TextUtils.isEmpty(password_2)) {
					Toast.makeText(getApplicationContext(), "密码不能为空.",
							Toast.LENGTH_SHORT).show();
					return;// 让用户重新输入
				} else if (!password_1.equals(password_2)) { // 如果两次密码输入内容不一致,提示用户重新输入
					Toast.makeText(getApplicationContext(), "两次密码输入不一致.",
							Toast.LENGTH_SHORT).show();
					return;// 让用户重新输入
				} else {
					// 保存密码,存在sharedpreferences中,创建一个工具类,因为后期还有很多需要保存的内容
					password_1 = Md5Utils.md5(password_1);// 对用户密码进行MD5加密,然后再储存
					SPTools.putString(getApplicationContext(),
							MyConstants.PASSWORD, password_1);
					Toast.makeText(getApplicationContext(), "OK,保存成功",
							Toast.LENGTH_SHORT).show();
				}

				// 退出对话框
				dialog.dismiss();
			}
		});

		// 为取消按钮设置监听事件
		btn_setPassword_no.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 如果取消设置,就关闭对话框,回到主界面
				dialog.dismiss();
			}
		});

	}

	/**
	 * 在此方法中进行初始化界面的操作
	 */
	private void initView() {
		setContentView(R.layout.activity_home);

		gv_menu = (GridView) findViewById(R.id.gv_home_menu);

	}

	/**
	 * 为主界面上的GridView设置布局,初始化数据
	 */
	private void initData() {
		// 创建自定义的适配器,并设置给主界面的gridview控件
		myAdapter = new MyAdapter();
		gv_menu.setAdapter(myAdapter);
	}

	/**
	 * @author Administrator
	 * 
	 *         创建适配器,用以设计九宫格里的每个按钮(按钮的样式是图片+文字形式)
	 */
	private class MyAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			// 此处返回的是九宫格的个数,用以设定每个图标都有点击事件
			return icons.length;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// 将整个item的layout层填充生成新的view
			View view = View.inflate(getApplicationContext(),
					R.layout.item_home_gridview, null);

			// 获得item这个view里面的图片和文字的组件(这时候的view就是新的view了)
			ImageView iv_icon = (ImageView) view
					.findViewById(R.id.iv_item_home_gv_icon);
			TextView tv_name = (TextView) view
					.findViewById(R.id.tv_item_home_gv_name);

			// 为每个icon图标设置内容
			iv_icon.setImageResource(icons[position]);

			// 为每个九宫格的名字设置名字
			tv_name.setText(names[position]);

			if (position == 0) { // 看是否有修改过的名字
				String newName = SPTools.getString(HomeActivities.this,
						MyConstants.LOSTFINDNEWNAME, "");
				if (!TextUtils.isEmpty(newName)) {
					tv_name.setText(newName);
				}
			}

			return view;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

	}

	/*
	 * 通知重新刷新页面数据
	 * 
	 * @see android.app.Activity#onResume()
	 */
	@Override
	protected void onResume() {
		// 主要是更新修改名字了的"手机防盗"
		// 通知适配器更新数据
		myAdapter.notifyDataSetChanged();
		super.onResume();
	}

}
