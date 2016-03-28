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
 *         �����ֻ���������
 */
public class LostFindActivities extends Activity {

	private TextView tv_safeNum;// ���ð�ȫ�������ʾ
	private EditText et_newName; // "�ֻ�����"��������
	private Button btn_cancle;
	private Button btn_modify;
	private PopupWindow pw; // �����Ĵ���
	private RelativeLayout rl_root; // ����ĸ�����
	private View view;
	
//	private boolean showMenu = false;// �Ƿ�����ʾmenu

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// ���û��Ƿ��Ѿ����й��������ж�
		if (SPTools.getBoolean(getApplicationContext(), MyConstants.ISSETUPED,
				false)) {
			// ����Ѿ����ù���,ֱ�ӽ����ֻ���������
			initView(); // ��ʼ���ֻ���������
		} else {
			// �����һ������,�ͽ��������򵼽���
			Intent intent = new Intent(LostFindActivities.this,
					Setup1Activities.class);
			startActivity(intent);
			// �رյ�ǰ�ֻ���������,��Ϊ����������򵼽���֮��,���û����˷��ؼ���ʱ��,Ӧ�ûص�������
			finish();
		}


	}



	/**
	 * ��ʼ���Զ��ĵ�������,�����޸�"�ֻ�����"����
	 */
	private void initPopupView() {
		view = View.inflate(this, R.layout.dialog_lostfind_menu_modify, null);
		pw = new PopupWindow(view, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);//������������
		pw.setFocusable(true);//��ȡ����
		ScaleAnimation sa = new ScaleAnimation(
				1.0F, 1.0F, 0.0F, 1.0F, 
				Animation.RELATIVE_TO_SELF, 0.5F, Animation.RELATIVE_TO_SELF, 0.0F);
		sa.setDuration(200);
		view.startAnimation(sa);//�������Ĵ��ڲ����������Ŷ���
		pw.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));// ���ñ���Ϊ͸��
	}

	/**
	 * ����ı�,���������򵼽���,��������
	 * 
	 * @param view
	 */
	public void enterSetup(View view) {
		Intent intent = new Intent(LostFindActivities.this,
				Setup1Activities.class);
		startActivity(intent);// ת�����õ�һҳ.
		finish();// �ر��Լ�
	}

	/**
	 * ��ʼ���ֻ���������
	 */
	private void initView() {
		setContentView(R.layout.activity_lostfind);
		rl_root = (RelativeLayout) findViewById(R.id.rl_lostfind_root);
		
		tv_safeNum = (TextView) findViewById(R.id.tv_lostfind_safenum);
		// ������ʾ��ȫ����
		tv_safeNum
				.setText(EncryptUtils.decrypt(MyConstants.SEED, SPTools
						.getString(getApplicationContext(),
								MyConstants.SAFENUMBER, "")));

		ImageView iv_lock = (ImageView) findViewById(R.id.iv_lostfind_lock);
		// ����Ѿ������˷���,����ʾ������,û�п�������,��ʾ����
		if (ServiceUtils.isRunningService(this,
				"com.itheima.safeguard.service.LostFindService")) {
			iv_lock.setImageResource(R.drawable.lock);
		} else {
			iv_lock.setImageResource(R.drawable.unlock);
		}
	}

/*	
	 * ����˵��¼�
	 * 
	 * @see android.app.Activity#onMenuItemSelected(int, android.view.MenuItem)
	 
	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_lostfind_modify: // �޸�����
			// ����һ���Զ���Ի���
			showModifyDialog();

			break;
		case R.id.menu_lostfind_settings: // �˵�����
			break;
		default:
			break;
		}

		return super.onMenuItemSelected(featureId, item);
	}*/

	/**
	 * ��ʾ�Զ���Ի���,�����޸�"�ֻ�����"������
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
			public void onClick(View v) {// ������޸�
				String newName = et_newName.getText().toString().trim();
				//�����ж������Ƿ�Ϊ��
				if (TextUtils.isEmpty(newName)) {
					Toast.makeText(LostFindActivities.this, "�����ֲ���Ϊ��", Toast.LENGTH_SHORT).show();
					return;
				}
				SPTools.putString(LostFindActivities.this, MyConstants.LOSTFINDNEWNAME, newName);
				Toast.makeText(LostFindActivities.this, "���޸�����.", Toast.LENGTH_SHORT).show();
				pw.dismiss();
			}
		});
		btn_cancle.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// ȡ������������
				pw.dismiss();
			}
		});

	}
	
	/**
	 * �����Զ��崰��
	 */
	public void popupWindow() {
		initPopupView(); //��ʼ���������ڵ�����
		pw.showAtLocation(rl_root, Gravity.CENTER, 0, 0);//�õ������������м���ʾ
		showModifyDialog();//������ʾ��������
	}
	
	
	/* ���м����µ�ʱ��,ϵͳ�ص��˷���
	 * ������������ⰴ��ȥ����menu��,������ʾ�Զ����menu
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
