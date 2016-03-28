package com.itheima.safeguard.activities;

import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.itheima.safeguard.R;
import com.itheima.safeguard.utils.EncryptUtils;
import com.itheima.safeguard.utils.MyConstants;
import com.itheima.safeguard.utils.SPTools;
import com.itheima.safeguard.utils.ServiceUtils;

/**
 * @author CPM
 * 
 *         这是手机防盗界面
 */
public class LostFindActivities extends Activity {

	private TextView tv_safeNum;// 设置安全号码的显示
	private EditText et_newName; // "手机防盗"的新名字
	private Button btn_cancle;
	private Button btn_modify;
	private PopupWindow pw; // 弹出的窗口
	private RelativeLayout rl_root; // 本活动的根布局
	private View view;
	
//	private boolean showMenu = false;// 是否有显示menu

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
	 * 初始化自定的弹出窗口,用于修改"手机防盗"名字
	 */
	private void initPopupView() {
		view = View.inflate(this, R.layout.dialog_lostfind_menu_modify, null);
		pw = new PopupWindow(view, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);//创建弹出窗口
		pw.setFocusable(true);//获取焦点
		ScaleAnimation sa = new ScaleAnimation(
				1.0F, 1.0F, 0.0F, 1.0F, 
				Animation.RELATIVE_TO_SELF, 0.5F, Animation.RELATIVE_TO_SELF, 0.0F);
		sa.setDuration(200);
		view.startAnimation(sa);//将弹出的窗口布局设置缩放动画
		pw.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));// 设置背景为透明
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
		rl_root = (RelativeLayout) findViewById(R.id.rl_lostfind_root);
		
		tv_safeNum = (TextView) findViewById(R.id.tv_lostfind_safenum);
		// 设置显示安全号码
		tv_safeNum
				.setText(EncryptUtils.decrypt(MyConstants.SEED, SPTools
						.getString(getApplicationContext(),
								MyConstants.SAFENUMBER, "")));

		ImageView iv_lock = (ImageView) findViewById(R.id.iv_lostfind_lock);
		// 如果已经开启了防护,则显示锁合上,没有开启防护,显示锁打开
		if (ServiceUtils.isRunningService(this,
				"com.itheima.safeguard.service.LostFindService")) {
			iv_lock.setImageResource(R.drawable.lock);
		} else {
			iv_lock.setImageResource(R.drawable.unlock);
		}
	}

/*	
	 * 处理菜单事件
	 * 
	 * @see android.app.Activity#onMenuItemSelected(int, android.view.MenuItem)
	 
	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_lostfind_modify: // 修改名字
			// 弹出一个自定义对话框
			showModifyDialog();

			break;
		case R.id.menu_lostfind_settings: // 菜单设置
			break;
		default:
			break;
		}

		return super.onMenuItemSelected(featureId, item);
	}*/

	/**
	 * 显示自定义对话框,用以修改"手机防盗"的名字
	 */
	private void showModifyDialog() {
		btn_modify = (Button) view
				.findViewById(R.id.btn_lostfind_menu_modify);
		btn_cancle = (Button) view
				.findViewById(R.id.btn_lostfind_menu_cancle);
		et_newName = (EditText) view
				.findViewById(R.id.ed_lostfind_menu_newname);

		btn_modify.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {// 点击了修改
				String newName = et_newName.getText().toString().trim();
				//首先判断名字是否为空
				if (TextUtils.isEmpty(newName)) {
					Toast.makeText(LostFindActivities.this, "新名字不能为空", Toast.LENGTH_SHORT).show();
					return;
				}
				SPTools.putString(LostFindActivities.this, MyConstants.LOSTFINDNEWNAME, newName);
				Toast.makeText(LostFindActivities.this, "已修改名字.", Toast.LENGTH_SHORT).show();
				pw.dismiss();
			}
		});
		btn_cancle.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// 取消设置新名字
				pw.dismiss();
			}
		});

	}
	
	/**
	 * 弹出自定义窗口
	 */
	public void popupWindow() {
		initPopupView(); //初始化弹出窗口的内容
		pw.showAtLocation(rl_root, Gravity.CENTER, 0, 0);//让弹出窗口在正中间显示
		showModifyDialog();//调用显示弹出窗口
	}
	
	
	/* 当有键按下的时候,系统回调此方法
	 * 在这里用来检测按下去的是menu键,用以显示自定义的menu
	 * @see android.app.Activity#onKeyDown(int, android.view.KeyEvent)
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_MENU) {

			if (pw != null && pw.isShowing()) {
				pw.dismiss();
				pw = null;
			}else {
				popupWindow();
			}
		} 
		
		
		
/*		if (keyCode == KeyEvent.KEYCODE_MENU) {
			if (!showMenu) {
				ll_menu.setVisibility(View.VISIBLE);
			}else {
				ll_menu.setVisibility(View.GONE);
			}
			showMenu = !showMenu;
		}*/
		
		return super.onKeyDown(keyCode, event);
	}

}
