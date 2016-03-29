package com.itheima.safeguard.activities;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.itheima.safeguard.R;
import com.itheima.safeguard.dao.BlackDao;
import com.itheima.safeguard.domain.BlackBean;
import com.itheima.safeguard.domain.BlackTable;

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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initView();// ��ʼ������
		initData();// ��ʼ������
		initEvent(); // ��ʼ���¼�
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
						Toast.makeText(getApplicationContext(), "�Ѿ�û�и���������~", Toast.LENGTH_SHORT).show();
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
						moreList = blackDao.getMoreDatas(blackList.size(), quantityPerPage);
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
			return blackList.size();
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
		public View getView(final int position, View convertView, ViewGroup parent) {
			ItemView itemView = null;
			if (convertView == null) {
				itemView = new ItemView();
				// ��һ�δ���view
				convertView = View.inflate(getApplicationContext(), R.layout.item_telsms_listview_black, null);
				// ��ø������������
				itemView.tv_phone = (TextView) convertView.findViewById(R.id.tv_item_telsms_phone);
				itemView.tv_mode = (TextView) convertView.findViewById(R.id.tv_item_telsms_mode);
				itemView.iv_lajitong = (ImageView) convertView.findViewById(R.id.iv_item_telsms_lajitong);
				// ��itemview���ڱ����
				convertView.setTag(itemView);
			}else {
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
					AlertDialog.Builder ab = new AlertDialog.Builder(TelSmsActivity.this);
					ab.setTitle("ɾ��");
					ab.setMessage("ȷ��ҪҪɾ����?");
					ab.setPositiveButton("��ɾ~", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// ɾ�����ݿ��Ӧ��Ŀ
							blackDao.delete(bean.getPhone());
							// ɾ�������ж�Ӧ����
							blackList.remove(position);
							// ���½���
							myAdapter.notifyDataSetChanged();
						}
					});
					ab.setNegativeButton("�����", null);
					ab.show();
				}
			});
			return convertView;
//			if (convertView == null) {
//				convertView = View.inflate(getApplicationContext(),
//						R.layout.item_telsms_listview_black, null);
//			}
//			TextView tv_phone = (TextView) convertView
//					.findViewById(R.id.tv_item_telsms_phone);
//			TextView tv_mode = (TextView) convertView
//					.findViewById(R.id.tv_item_telsms_mode);
//			// ImageView iv_delete = (ImageView)
//			// convertView.findViewById(R.id.iv_item_telsms_lajitong);
//
//			BlackBean bean = blackList.get(position);
//			tv_phone.setText(bean.getPhone());
//
//			switch (bean.getMode()) {
//			case BlackTable.SMS_MODE:
//				tv_mode.setText("��������");
//				break;
//			case BlackTable.TEL_MODE:
//				tv_mode.setText("�绰����");
//				break;
//			case BlackTable.ALL_MODE:
//				tv_mode.setText("ȫ������");
//				break;
//			default:
//				break;
//			}
//
//			return convertView;
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
		Toast.makeText(getApplicationContext(), "add", Toast.LENGTH_SHORT)
				.show();
	}

}
