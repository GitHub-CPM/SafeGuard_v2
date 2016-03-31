package com.itheima.safeguard.activities;

import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.itheima.safeguard.R;
import com.itheima.safeguard.domain.ContactsBean;
import com.itheima.safeguard.utils.MyConstants;

public abstract class BaseTelSmsBlackActivity extends ListActivity {

	protected final int LOADING = 1;
	protected final int FINISHED = 2;

	// ���������ֻ���ϵ�˵��б�,�����ȴ�������,��Ϊlistview�������Ǻ���Ÿ��µ�,���ȴ����ͻ��п�ָ���쳣
	private List<ContactsBean> list = new ArrayList<>();
	private ProgressDialog pd; // ���ؽ��ȵĶԻ���
	private MyAdapter mAdapter; // listview��������
	private ListView lv_contacts;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// ��ȡlistview������
		lv_contacts = getListView();

		// ����listview������,��ʾlistview

		mAdapter = new MyAdapter();
		lv_contacts.setAdapter(mAdapter);

		initData(); // ��ʼ������
		initEvent();// ��ʼ������¼�
	}

	/**
	 * ���ѡ�е���Ŀ,�����ݷ��ص�����ҳ��
	 */
	private void initEvent() {
		lv_contacts.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				String phone = list.get(position).getPhoneNum();
				Intent intent = new Intent(BaseTelSmsBlackActivity.this,
						Setup3Activities.class);
				intent.putExtra(MyConstants.SAFENUMBER, phone);// ��ѡ�еĵ绰���뷵�ص�����ҳ��
				setResult(1, intent);
				finish();// �ر��Լ�
			}
		});
	}

	/**
	 * ����listview��������
	 */
	private class MyAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return list.size();// ������ϵ�˵�������ʾ��Ŀ����
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = View.inflate(BaseTelSmsBlackActivity.this,
						R.layout.item_friends_listview, null);
			}

			TextView tv_name = (TextView) convertView
					.findViewById(R.id.tv_item_friends_name);
			TextView tv_phoneNum = (TextView) convertView
					.findViewById(R.id.tv_item_friends_phonenum);

			tv_name.setText(list.get(position).getName());
			tv_phoneNum.setText(list.get(position).getPhoneNum());

			return convertView;
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

	/**
	 * �¿���һ�����߳�,���к�ʱ����
	 */
	private void initData() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				// �ȵ���һ�����ȼ��ضԻ���(��Ϊ�����activity,���Բ��־Ͳ���progressbar)
				sendMessage(LOADING);

//				SystemClock.sleep(500);// ��������0.5����,������ʾ�����Ľ��ȼ��ضԻ���

				list = readData();
				// // ��ȡ�ֻ���ϵ��,����������ɺ�,��ʾ��ListView��
				// list = ReadContactsEngine.readContacts(getApplicationContext());
				sendMessage(FINISHED);
			}

		}).start();
	}

	public abstract List<ContactsBean> readData();

	/**
	 * handler������Ϣ
	 * 
	 * @param what
	 *            ������Ϣ����Ӧ������
	 */
	private void sendMessage(int what) {
		Message msg = Message.obtain();
		msg.what = what;
		mHandler.sendMessage(msg);
	}

	Handler mHandler = new Handler() {

		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case LOADING:// ���ڼ�������
				pd = new ProgressDialog(BaseTelSmsBlackActivity.this);
				pd.setTitle("���Ե�~");
				pd.setMessage("����ƴ�����ذ�......");
				pd.show();// ��ʾ

				break;
			case FINISHED:// �Ѿ�������ݼ���
				// �رռ��ضԻ���
				if (pd != null) {
					pd.dismiss();
					pd = null;
				}
				// ֪ͨ��������
				mAdapter.notifyDataSetChanged();
				break;
			default:
				break;
			}
		};
	};

}
