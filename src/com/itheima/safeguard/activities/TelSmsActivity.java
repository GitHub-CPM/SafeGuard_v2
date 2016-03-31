package com.itheima.safeguard.activities;

import java.util.ArrayList;
import java.util.List;

import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.itheima.safeguard.R;
import com.itheima.safeguard.dao.BlackDao;
import com.itheima.safeguard.domain.BlackBean;
import com.itheima.safeguard.domain.BlackTable;
import com.itheima.safeguard.utils.MyConstants;

/**
 * @author Administrator
 * 
 *         ����ͨѶ��ʿ�Ľ���,��Ҫ����Ӻ�����,���������е��ֻ�/���Ž�������
 */
public class TelSmsActivity extends Activity {

	protected static final int LOADING = 1;
	protected static final int FINISHED = 2;
	protected static final int quantityPerPage = 10; // ÿ�μ���һ�����ݵ���Ŀ��Ϊ10��
	private ProgressBar pb_loading;
	private TextView tv_nodata;
	private ListView lv_black;
	private List<BlackBean> blackList;
	private List<BlackBean> moreList;
	private BlackDao blackDao;
	private MyAdapter myAdapter;
	private AlertDialog dialog;
	private Button btn_add;
	private PopupWindow pw;
	private View contentView;
	private TextView tv_popup_self;
	private TextView tv_popup_contracts;
	private TextView tv_popup_call;
	private TextView tv_popup_sms;
	private ScaleAnimation sa;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initView(); // ��ʼ������
		initData(); // ��ʼ������
		initEvent(); // ��ʼ���¼�
		// initPopup(); // ��ʼ����������
	}

	/**
	 * �����Ӱ�ť�������������嵥
	 */
	private void initPopup() {
		// ����Զ��嵯����view
		contentView = View.inflate(getApplicationContext(),
				R.layout.popup_telsms, null);
		// ��ȡ�ؼ�������
		tv_popup_self = (TextView) contentView
				.findViewById(R.id.tv_telsms_popup_self);
		tv_popup_contracts = (TextView) contentView
				.findViewById(R.id.tv_telsms_popup_contracts);
		tv_popup_call = (TextView) contentView
				.findViewById(R.id.tv_telsms_popup_call);
		tv_popup_sms = (TextView) contentView
				.findViewById(R.id.tv_telsms_popup_sms);

		// Ϊ�ĸ����ͳһ����һ����������¼�,����id�ж����ĸ��ı������
		View.OnClickListener listener = new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				switch (v.getId()) {
				case R.id.tv_telsms_popup_self: // �Լ��ֶ����
					Toast.makeText(getApplicationContext(), "�Լ��ֶ����",
							Toast.LENGTH_SHORT).show();
					showAddBlackDialog("");
					break;
				case R.id.tv_telsms_popup_contracts: // ����ϵ�����
					Intent intent = new Intent(TelSmsActivity.this,
							FriendsActivity.class);
					startActivityForResult(intent, 1);
					break;
				case R.id.tv_telsms_popup_call: // ��ͨ����¼���
					Intent callLog = new Intent(TelSmsActivity.this,
							CallLogActivity.class);
					startActivityForResult(callLog, 1);
					break;
				case R.id.tv_telsms_popup_sms: // �Ӷ��ż�¼���
					Intent smsLog = new Intent(TelSmsActivity.this,
							SmsLogActivity.class);
					startActivityForResult(smsLog, 1);
					break;
				default:
					break;
				}
				// �رմ���
				closePouupWindow();
			}
		};

		// Ϊ�ؼ����ü�����
		tv_popup_self.setOnClickListener(listener);
		tv_popup_contracts.setOnClickListener(listener);
		tv_popup_call.setOnClickListener(listener);
		tv_popup_sms.setOnClickListener(listener);

		// ���õ�������
		pw = new PopupWindow(contentView, LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT);
		// ���ñ�����Դ,��Ȼ��ʾ����
		pw.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
		// ���ö���

		sa = new ScaleAnimation(1.0F, 1.0F, 0.0F, 1.0F,
				Animation.RELATIVE_TO_SELF, 0.5F, Animation.RELATIVE_TO_SELF,
				0.0F);
		sa.setDuration(1000);
		// contentView.setAnimation(sa);

		// ��������
		showPopupWindow();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (data != null) {
			String phoneNumber = data.getStringExtra(MyConstants.SAFENUMBER);
			showAddBlackDialog(phoneNumber);
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	/**
	 * �����Զ��崰��
	 */
	private void showPopupWindow() {
		if (pw != null & pw.isShowing()) {
			pw.dismiss();
		} else {
			// ��������
			contentView.startAnimation(sa);
			// ��ʾ��������
			pw.showAtLocation(btn_add, Gravity.RIGHT | Gravity.TOP, 75, 50);
		}
	}

	/**
	 * �ر��Զ��崰��
	 */
	private void closePouupWindow() {
		if (pw != null && pw.isShowing()) {
			pw.dismiss();
		}
	}

	/**
	 * ��ʼ���¼�,����ҳ�������������
	 */
	private void initEvent() {
		lv_black.setOnScrollListener(new OnScrollListener() {

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				// ����������ֹ֮ͣ��,����ͣ����ʾ���һ����Ŀ��ʱ��,���м��ظ�������
				if (scrollState == OnScrollListener.SCROLL_STATE_IDLE) { // ��listview������ֹ��ʱ��
					// ͣ�������һ����Ŀ
					if (blackList.size() == lv_black.getLastVisiblePosition() + 1) {
						initData(); // ��������
					}
				}
			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
			}
		});
	}

	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case LOADING: // ���ڼ��ص�ҳ�������ʾ
				// ����ҳ����ʾ,û�����ݺͺ������б���ʾ
				pb_loading.setVisibility(View.VISIBLE);
				tv_nodata.setVisibility(View.GONE);
				lv_black.setVisibility(View.GONE);
				break;
			case FINISHED: // ��ʾ����������
				// 1.����������
				if (moreList.size() != 0) {
					lv_black.setVisibility(View.VISIBLE);
					pb_loading.setVisibility(View.GONE);
					tv_nodata.setVisibility(View.GONE);
					// ��������
					myAdapter.notifyDataSetChanged();
				} else {
					// 2.û����������
					if (blackList.size() != 0) {
						Toast.makeText(getApplicationContext(), "�Ѿ�û�и���������~",
								Toast.LENGTH_SHORT).show();
						lv_black.setVisibility(View.VISIBLE);
						pb_loading.setVisibility(View.GONE);
						tv_nodata.setVisibility(View.GONE);
						// ��������
						myAdapter.notifyDataSetChanged();
						return;
					}
					tv_nodata.setVisibility(View.VISIBLE);
					lv_black.setVisibility(View.GONE);
					pb_loading.setVisibility(View.GONE);
				}
				break;
			default:
				break;
			}
		};
	};

	/**
	 * ��ʼ������,��Ϊ��Ҫ�������ݿ��ʵ�����, �����л�û������/������/����������ҳ�����ʾ���
	 */
	private void initData() {

		new Thread(new Runnable() { // �������ݲ�ѯ�ĺ�ʱ����

					@Override
					public void run() {
						// ���в�ѯ����ǰ,����һ����Ϣ��ʾ���ڼ���
						mHandler.obtainMessage(LOADING).sendToTarget();
						// SystemClock.sleep(500);

						// ȡ����
						// 1.��ȡ���������,��Ϊһ��ʼ����ʱ��,sizeΪ0,ÿ�μ��غ�������Ϊ��һ�����׽Ǳ�
						moreList = blackDao.getMoreDatas(blackList.size(),
								quantityPerPage);
						// 2.֮ǰ���ص����ݼ�������������ʾ������,������ʾ
						blackList.addAll(moreList);

						// ��֪ͨ
						mHandler.obtainMessage(FINISHED).sendToTarget();
					}
				}).start();
	}

	/**
	 * @author CPM
	 * 
	 *         �Ż�������ʾ(Listview)�ķ�װ��
	 */
	public class ItemView {
		private TextView tv_phone;
		private TextView tv_mode;
		private ImageView iv_lajitong;
	}

	/**
	 * @author CPM ����������
	 */
	public class MyAdapter extends BaseAdapter {

		private BlackBean bean;

		@Override
		public int getCount() {
			int size = blackList.size();
			if (size == 0) {
				tv_nodata.setVisibility(View.VISIBLE);
				lv_black.setVisibility(View.GONE);
				pb_loading.setVisibility(View.GONE);
			}
			return size;
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(final int position, View convertView,
				ViewGroup parent) {
			ItemView itemView = null;
			if (convertView == null) {
				itemView = new ItemView();
				// ��һ�δ���view
				convertView = View.inflate(getApplicationContext(),
						R.layout.item_telsms_listview_black, null);
				// ��ø������������
				itemView.tv_phone = (TextView) convertView
						.findViewById(R.id.tv_item_telsms_phone);
				itemView.tv_mode = (TextView) convertView
						.findViewById(R.id.tv_item_telsms_mode);
				itemView.iv_lajitong = (ImageView) convertView
						.findViewById(R.id.iv_item_telsms_lajitong);
				// ��itemview���ڱ����
				convertView.setTag(itemView);
			} else {
				// �Ѿ����˻���view
				itemView = (ItemView) convertView.getTag();
			}

			bean = blackList.get(position);
			itemView.tv_phone.setText(bean.getPhone());
			switch (bean.getMode()) {
			case BlackTable.SMS_MODE:
				itemView.tv_mode.setText("��������");
				break;
			case BlackTable.TEL_MODE:
				itemView.tv_mode.setText("�绰����");
				break;
			case BlackTable.ALL_MODE:
				itemView.tv_mode.setText("����&�绰����");
				break;
			default:
				break;
			}
			// ����ɾ���������ĵ���¼�
			itemView.iv_lajitong.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					AlertDialog.Builder ab = new AlertDialog.Builder(
							TelSmsActivity.this);
					ab.setTitle("ɾ��");
					ab.setMessage("ȷ��ҪҪɾ����?");
					ab.setPositiveButton("��ɾ~",
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									// ɾ�����ݿ��Ӧ��Ŀ
									blackDao.delete(bean.getPhone());
									// ɾ�������ж�Ӧ����
									blackList.remove(position);

									if (blackList.size() < quantityPerPage / 2 - 1
											|| position == blackList.size()) {
										initData();
									} else {
										// ���½���
										myAdapter.notifyDataSetChanged();
									}
								}
							});
					ab.setNegativeButton("�����", null);
					ab.show();
				}
			});
			return convertView;
			// if (convertView == null) {
			// convertView = View.inflate(getApplicationContext(),
			// R.layout.item_telsms_listview_black, null);
			// }
			// TextView tv_phone = (TextView) convertView
			// .findViewById(R.id.tv_item_telsms_phone);
			// TextView tv_mode = (TextView) convertView
			// .findViewById(R.id.tv_item_telsms_mode);
			// // ImageView iv_delete = (ImageView)
			// // convertView.findViewById(R.id.iv_item_telsms_lajitong);
			//
			// BlackBean bean = blackList.get(position);
			// tv_phone.setText(bean.getPhone());
			//
			// switch (bean.getMode()) {
			// case BlackTable.SMS_MODE:
			// tv_mode.setText("��������");
			// break;
			// case BlackTable.TEL_MODE:
			// tv_mode.setText("�绰����");
			// break;
			// case BlackTable.ALL_MODE:
			// tv_mode.setText("ȫ������");
			// break;
			// default:
			// break;
			// }
			//
			// return convertView;
		}

	}

	/**
	 * ��ʼ������
	 */
	private void initView() {
		setContentView(R.layout.activity_telsms);

		pb_loading = (ProgressBar) findViewById(R.id.pb_telsms_loading);// ���ڼ���
		tv_nodata = (TextView) findViewById(R.id.tv_telsms_nodata);// û������
		lv_black = (ListView) findViewById(R.id.lv_telsms_black);// �������б�
		btn_add = (Button) findViewById(R.id.btn_telsms_add);

		blackList = new ArrayList<>(); // ʵ��������blackbean��list
		moreList = new ArrayList<>(); // װ�ж�ȡ����ʵ����list
		myAdapter = new MyAdapter(); // ����������
		lv_black.setAdapter(myAdapter); // ��listview����������

		blackDao = new BlackDao(getApplicationContext()); // ����ҵ����
	}

	/**
	 * ��Ӻ������İ�ť�����¼�,���������б�
	 * 
	 * @param view
	 */
	public void addBlack(View view) {
		// showAddBlackDialog(view);
		// showPopupWindow();
		initPopup();
	}

	private void showAddBlackDialog(String PhoneNumber) {
		AlertDialog.Builder adb = new AlertDialog.Builder(this);
		View dialogView = View.inflate(this, R.layout.dialog_telsms_addblack,
				null);
		final EditText et_dialog_phone = (EditText) dialogView
				.findViewById(R.id.et_telsms_dialog_phone);
		et_dialog_phone.setText(PhoneNumber); // ���ô������ĺ������������

		final CheckBox cb_dialog_sms = (CheckBox) dialogView
				.findViewById(R.id.cb_telsms_dialog_smsmode);
		final CheckBox cb_dialog_phone = (CheckBox) dialogView
				.findViewById(R.id.cb_telsms_dialog_telmode);
		final Button btn_dialog_add = (Button) dialogView
				.findViewById(R.id.btn_telsms_dialog_add);
		final Button btn_dialog_cancle = (Button) dialogView
				.findViewById(R.id.btn_telsms_dialog_cancle);

		btn_dialog_cancle.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});

		btn_dialog_add.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// �ж��Ƿ�������绰����
				String phone = et_dialog_phone.getText().toString().trim();
				if (TextUtils.isEmpty(phone)) {
					Toast.makeText(getApplicationContext(), "���벻��Ϊ��~",
							Toast.LENGTH_SHORT).show();
					return;
				}
				// �ж��Ƿ��й�ѡ����һ������ģʽ
				if (!cb_dialog_phone.isChecked() && !cb_dialog_sms.isChecked()) {
					Toast.makeText(getApplicationContext(), "��ѡ������ģʽ~",
							Toast.LENGTH_SHORT).show();
					return;
				}

				int mode = 0;
				if (cb_dialog_sms.isChecked()) {
					mode |= BlackTable.SMS_MODE;
				}
				if (cb_dialog_phone.isChecked()) {
					mode |= BlackTable.TEL_MODE;
				}

				BlackBean bean = new BlackBean();
				bean.setMode(mode);
				bean.setPhone(phone);

				blackDao.add(bean);

				// blackList.remove(bean);
				blackList.add(0, bean);

				myAdapter = new MyAdapter();
				lv_black.setAdapter(myAdapter);

				/*
				 * //��Ӻ�����,�ֱ������&���������,��ʵʱ��ʾ,������������ʾ�ڵ�һ����λ��
				 * Toast.makeText(getApplicationContext(), "������������ӳɹ�~",
				 * Toast.LENGTH_SHORT).show();
				 */
				dialog.dismiss();
				lv_black.setVisibility(View.VISIBLE);
				pb_loading.setVisibility(View.GONE);
				tv_nodata.setVisibility(View.GONE);
			}
		});

		adb.setView(dialogView);
		dialog = adb.create();
		dialog.show();
	}

}
